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
import com.luretechnologies.client.restlib.service.model.UserSession;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNotNull;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore
public class SettingTest {

    private static RestClientService service;
    private static UserSession userSession;

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
//
//    @Test
//    public void addSetting() {
//        try {
//            List<Setting> list = service.listSetting(null, 1, 50);
//
//            Setting value = null;
//            for (Setting val : list) {
//                if (val.getName() == SettingEnum.HSM_BDK) {
//                    value = val;
//                    break;
//                }
//            }
//
//            if (value == null) {
//                Setting temp = new Setting();
//                temp.setName(SettingEnum.HSM_BDK);
//                temp.setValue("Value");
//                temp.setGroup(SettingGroupEnum.DEFAULT);
//                Setting res = service.createSetting(temp);
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
//    public void getSetting() {
//        try {
//            if (id != null) {
//                Setting setting = service.getSetting(SettingEnum.HSM_BDK.toString());
//                assertNotNull(setting);
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void editSetting() {
//        try {
//            Setting value = service.getSetting(SettingEnum.HSM_BDK.toString());
//            
//            if (value != null) {
//                String currentValue = value.getValue();
//                value.setValue("Updated value");
//                Setting res = service.updateSetting(value);
//                assertNotNull(res);
//                value.setValue(currentValue);
//                res = service.updateSetting(value);
//                assertNotNull(res);
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void listSettings() {
//        try {
//            List<Setting> res = service.listSetting(null, 1, 50);
//            assertNotNull(res);
//            assertTrue(res.size()>0);
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
}
