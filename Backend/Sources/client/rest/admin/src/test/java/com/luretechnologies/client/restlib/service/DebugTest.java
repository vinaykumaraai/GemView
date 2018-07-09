/**
 * COPYRIGHT @ Lure Technologies, LLC.
 * ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or
 * form other than in accordance with and subject to the terms of a written
 * license from Lure or with the prior written consent of Lure or as
 * permitted by applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure.  If you are neither the
 * intended recipient, nor an agent, employee, nor independent contractor
 * responsible for delivering this message to the intended recipient, you are
 * prohibited from copying, disclosing, distributing, disseminating, and/or
 * using the information in this email in any manner. If you have received
 * this message in error, please advise us immediately at
 * legal@luretechnologies.com by return email and then delete the message from your
 * computer and all other records (whether electronic, hard copy, or
 * otherwise).
 *
 * Any copies or reproductions of this software program (in whole or in part)
 * made by any method must also include a copy of this legend.
 *
 * Inquiries should be made to legal@luretechnologies.com
 *
 */
package com.luretechnologies.client.restlib.service;

import java.util.List;
import com.luretechnologies.client.restlib.Utils;
import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.common.CommonConstants;
import com.luretechnologies.client.restlib.service.model.Debug;
import com.luretechnologies.client.restlib.service.model.DebugItem;
import com.luretechnologies.client.restlib.service.model.DebugResponse;
import com.luretechnologies.client.restlib.service.model.Terminal;
import com.luretechnologies.client.restlib.service.model.UserSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNotNull;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DebugTest {

    private static RestClientService service;
    private static UserSession userSession;

    /**
     *
     */
    @BeforeClass
    public static void createService() {

        service = new RestClientService(Utils.ADMIN_SERVICE_URL, Utils.TMS_SERVICE_URL);

        assumeNotNull(service);

        try {
            userSession = service.getAuthApi().login(CommonConstants.testStandardUsername, CommonConstants.testStandardPassword);
            assertNotNull("User failed login", userSession);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void createDebug() {
        try {
            List<Terminal> terminals = service.getTerminalApi().getTerminals(1, 100);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (Terminal temp : terminals) {
                Debug debug = new Debug();
                debug.setSerialNumber(temp.getSerialNumber());
                debug.setDebugItems(new ArrayList<DebugItem>());
                DebugItem debugItem = new DebugItem();
                debugItem.setComponent("PAYAPP");
                debugItem.setLevel(6);
                debugItem.setMessage("Connecting to host");
                debugItem.setOccurred("2018-05-04 21:22:22");
                debug.getDebugItems().add(debugItem);

                debugItem = new DebugItem();
                debugItem.setComponent("PAYAPP");
                debugItem.setLevel(6);
                debugItem.setMessage("Retrying connect to host");
                debugItem.setOccurred("2018-05-02 23:22:22");
                debug.getDebugItems().add(debugItem);

                debugItem = new DebugItem();
                debugItem.setComponent("PAYAPP");
                debugItem.setLevel(7);
                debugItem.setMessage("Host connect time out");
                debugItem.setOccurred("2018-05-01 22:04:25");
                debug.getDebugItems().add(debugItem);

                debugItem = new DebugItem();
                debugItem.setComponent("PAYAPP");
                debugItem.setLevel(3);
                debugItem.setMessage("Retrying connect to host");
                debugItem.setOccurred("2018-05-01 22:02:02");
                debug.getDebugItems().add(debugItem);

                debugItem = new DebugItem();
                debugItem.setComponent("PAYAPP");
                debugItem.setLevel(6);
                debugItem.setMessage("Host connect time out");
                debugItem.setOccurred("2018-05-01 22:32:42");
                debug.getDebugItems().add(debugItem);

                debugItem = new DebugItem();
                debugItem.setComponent("PAYAPP");
                debugItem.setLevel(6);
                debugItem.setMessage("Host connect time out");
                debugItem.setOccurred(dateFormat.format(new Date (System.currentTimeMillis())));
                debug.getDebugItems().add(debugItem);

                DebugResponse debugResponse = service.getDebugApi().create(debug);
                assertNotNull(debugResponse);
                System.out.println(debug.toString());
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            fail(ex.getMessage());
        }
    }

    @Test
    public void debugSearch() {
        try {
            String terminalId = "TERG0EZ00PZR4";
            List<Debug> debugList = service.getDebugApi().search(terminalId, null, null);

            assertNotNull(debugList);
            assertTrue(debugList.size() > 0);

            for (Debug temp : debugList) {
                System.out.println(temp.toString());
            }

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void debugItemsSearch() {
        try {
            String terminalId = "TERG0EZ00PZR4";
            List<DebugItem> debugItems = service.getDebugApi().searchDebugItems(terminalId, null, "180605", "180627", null, null);

            assertNotNull(debugItems);
            assertTrue(debugItems.size() > 0);

            for (DebugItem temp : debugItems) {
                System.out.println(temp.toString());
            }

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void debugItemDelete() {
        try {
            String terminalId = "TERDXJZ899694";
            List<DebugItem> debugItems = service.getDebugApi().searchDebugItems(terminalId, null, null, null, null, null);

            assertNotNull(debugItems);
            assertTrue(debugItems.size() > 0);

            service.getDebugApi().deleteDebugItem(debugItems.get(0).getId());

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void debugDelete() {
        try {
            String terminalId = "TERG0EZ00PZR4";
            List<Debug> debugList = service.getDebugApi().search(terminalId, null, null);

            assertNotNull(debugList);
            assertTrue(debugList.size() > 0);

            service.getDebugApi().delete(debugList.get(0).getId());

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }
}
