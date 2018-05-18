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

import com.luretechnologies.client.restlib.Utils;
import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.common.CommonConstants;
import com.luretechnologies.client.restlib.service.model.Host;
import com.luretechnologies.client.restlib.service.model.HostSettingValue;
import com.luretechnologies.client.restlib.service.model.UserSession;
import java.util.List;
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
 *
 * @author developer
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HostTest {

    private static RestClientService service;
    private static UserSession userSession;

    /**
     *
     */
    @BeforeClass
    public static void createService() {

        service = new RestClientService(Utils.serviceUrl + "/admin/api", Utils.serviceUrl + "/payment/api");

        assumeNotNull(service);

        try {
            userSession = service.getAuthApi().login(CommonConstants.testStandardUsername, CommonConstants.testStandardPassword);
            assertTrue("User logged in", userSession != null);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }
//
//    @Test
//    public void addSetting() {
//        try {
//            List<HostSettingValue> list = service.listSettingsHost(HostEnum.WORLDPAY_TCMP);
//
//            HostSettingValue value = null;
//            for (HostSettingValue val : list) {
//                if (val.getSetting() == HostSettingEnum.HOST_IP) {
//                    value = val;
//                    break;
//                }
//            }
//
//            if (value == null) {
//                value = new HostSettingValue();
//                value.setDefaultValue("Default value");
//                value.setValue("Value");
//                value.setSetting(HostSettingEnum.HOST_IP);
//                HostSettingValue res = service.addSettingHost(HostEnum.WORLDPAY_TCMP, value);
//                assertNotNull(res);
//                id = res.getId();
//            } else {
//                id = value.getId();
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//
//    @Test
//    public void updateSetting() {
//        try {
//            List<HostSettingValue> list = service.listSettingsHost(HostEnum.WORLDPAY_TCMP);
//
//            HostSettingValue value = null;
//            for (HostSettingValue val : list) {
//                if (val.getSetting() == HostSettingEnum.HOST_IP) {
//                    value = val;
//                    break;
//                }
//            }
//
//            if (value != null) {
//                String currentDefault = value.getDefaultValue();
//                value.setDefaultValue("Updating value");
//                HostSettingValue res = service.updateSettingHost(HostEnum.WORLDPAY_TCMP, value);
//                assertNotNull(res);
//                value.setDefaultValue(currentDefault);
//                res = service.updateSettingHost(HostEnum.WORLDPAY_TCMP, value);
//                assertNotNull(res);
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }

    //TODO FIXME Test is failing.
    // @Test
    public void hostByKey() {
        try {
            String key = "TSYS_TRANSIT";
            Host res = service.getHostApi().getHostByKey(key);
            assertNotNull(res);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void list() {
        try {
            List<Host> res = service.getHostApi().getHosts();
            assertNotNull(res);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    //TODO FIXME Test is failing.
    //@Test
    public void listSettings() {
        try {
            Host host = service.getHostApi().getHostByKey("FD_RAPIDCONNECT");
            List<HostSettingValue> res = service.getHostApi().getSettingValues(host.getId());
            assertNotNull(res);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }
}
