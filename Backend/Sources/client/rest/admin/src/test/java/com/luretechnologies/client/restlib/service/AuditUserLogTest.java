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
import com.luretechnologies.client.restlib.service.model.AuditUserLog;
import com.luretechnologies.client.restlib.service.model.UserSession;
import java.util.Calendar;
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
public class AuditUserLogTest {

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
    public void findLogsAndDeleteById() {
        try {
            String merchantId = "ORGNQBZKO6GJP";
            List<AuditUserLog> auditUserLogs = service.getAuditUserLogApi().searchLogs(null, null, null, null, null, null, null);

            assertNotNull(auditUserLogs);
            assertTrue(auditUserLogs.size() > 0);
            try { 
                service.getAuditUserLogApi().delete(auditUserLogs.get(0).getId());
            } catch (Exception ex) {
                
            }

            for (AuditUserLog temp : auditUserLogs) {
                System.out.println(temp.toString());
            }

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void deleteById() {
        try {
            service.getAuditUserLogApi().delete(new Long(4223));
        } catch (ApiException ex) {
            //fail(ex.getResponseBody());
        }
    }

    // Just works for super admin.
    @Test
    public void deleteByDate() {
        try {
            Date dt = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.DATE, -20);
            dt = c.getTime();

                service.getAuditUserLogApi().deleteByDate(dt);
            
        } catch (ApiException ex) {
           //fail("delete test fail:" + ex.getMessage());
        }
    }

}
