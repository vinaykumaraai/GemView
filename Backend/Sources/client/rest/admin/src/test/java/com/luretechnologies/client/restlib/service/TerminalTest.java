/**
 * COPYRIGHT @ Lure Technologies, LLC. ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or form
 * other than in accordance with and subject to the terms of a written license
 * from Lure or with the prior written consent of Lure or as permitted by
 * applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure. If you are neither the intended
 * recipient, nor an agent, employee, nor independent contractor responsible for
 * delivering this message to the intended recipient, you are prohibited from
 * copying, disclosing, distributing, disseminating, and/or using the
 * information in this email in any manner. If you have received this message in
 * error, please advise us immediately at legal@luretechnologies.com by return
 * email and then delete the message from your computer and all other records
 * (whether electronic, hard copy, or otherwise).
 *
 * Any copies or reproductions of this software program (in whole or in part)
 * made by any method must also include a copy of this legend.
 *
 * Inquiries should be made to legal@luretechnologies.com
 *
 */
package com.luretechnologies.client.restlib.service;

import com.luretechnologies.client.restlib.Utils;
import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.common.CommonConstants;
import com.luretechnologies.client.restlib.service.model.Merchant;
import com.luretechnologies.client.restlib.service.model.Terminal;
import com.luretechnologies.client.restlib.service.model.UserSession;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNotNull;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TerminalTest {

    private static RestClientService service;
    private static UserSession userSession;
    private static String terminalId;

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
    public void createTerminal() {
        try {
            Terminal terminal = new Terminal();
            Long serialNumber = new Date().getTime();
            terminal.setSerialNumber(serialNumber.toString());
            terminal.setName("Testing Terminal fro TSYS");
            terminal.setDescription("Testing Terminal fro TSYS");
            terminal.setParentId(Long.valueOf(2441));

            List<Merchant> merchants = service.getMerchantApi().getMerchants(1, 50);
            if (!merchants.isEmpty()) {
                Merchant merchant = merchants.get(0);
                terminal.setParentId(merchant.getId());
            }
            terminal = service.getTerminalApi().createTerminal(terminal);

            Assert.assertNotNull(terminal.getEntityId());
            
            System.out.println(terminal.toString());
            terminalId = terminal.getEntityId();
            
            
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }
    @Test
    public void editTerminal() {
        try {
            Terminal terminal = service.getTerminalApi().getBySerialNumber("1528920782215");
            Assert.assertEquals("1528920782215", terminal.getSerialNumber());

            terminal.setDescription("Updated");

            terminal = service.getTerminalApi().updateTerminal(terminal.getEntityId(), terminal);
            Assert.assertEquals("Updated", terminal.getDescription());
            
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void getTerminal() {
        try {
            Terminal terminal = service.getTerminalApi().getTerminal("TERL0ZW8RPAJ4");
            Assert.assertEquals("TERL0ZW8RPAJ4", terminal.getEntityId());
            System.out.println(terminal.toString());
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void listTerminals() {
        try {
            List<Terminal> terminals = service.getTerminalApi().getTerminals(1, 10);
            assertNotNull(terminals);
            Assert.assertTrue(terminals.size()>0);
            for (Terminal temp : terminals) {
                System.out.println(temp.toString());
            }
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }
    
    @Test
    public void findTerminals() {
        try {
            List<Terminal> terminals = service.getTerminalApi().searchTerminals("PowaPIN 01234543210", 1, 20);
            assertNotNull(terminals);
            Assert.assertTrue(terminals.size()>0);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }
    

}
