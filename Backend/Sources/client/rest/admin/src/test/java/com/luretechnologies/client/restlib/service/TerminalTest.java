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
import com.luretechnologies.client.restlib.service.model.Device;
import com.luretechnologies.client.restlib.service.model.Entity;
import com.luretechnologies.client.restlib.service.model.Organization;
import com.luretechnologies.client.restlib.service.model.Terminal;
import com.luretechnologies.client.restlib.service.model.UserSession;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNotNull;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 *
 * @author developer
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore public class TerminalTest {

    private static RestClientService service;
    private static UserSession userSession;
    private static String terminalId;

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

    @Test
    public void createTerminal() {
        try {
            Terminal terminal = new Terminal();
            terminal.setSerialNumber("01234543299");
            terminal.setName("Testing Terminal fro TSYS");
            terminal.setDescription("Testing Terminal fro TSYS");
            terminal.setParentId(Long.valueOf(2441));
            
//            List<Merchant> merchants = service.getMerchantApi().getMerchants(1, 50);
//            if (!merchants.isEmpty()) {
//                Merchant merchant = merchants.get(0);
//                terminal.setParentId(merchant.getId());
//            }

            terminal = service.getTerminalApi().createTerminal(terminal);

            Assert.assertNotNull(terminal.getEntityId());
            terminalId = terminal.getEntityId();
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void createTerminalIncorrentParent() {
        Terminal terminal = new Terminal();
        terminal.setSerialNumber("01234543210");
        terminal.setName("PowaPIN 01234543210 Nuevo");
        terminal.setDescription("PowaPIN 01234543210 Nuevo");
        try {
            List<Device> parents = service.getDeviceApi().getDevices(1, 5);
            if (!parents.isEmpty()) {
                Entity parent = parents.get(0);
                terminal.setParentId(parent.getId());
            }

            service.getTerminalApi().createTerminal(terminal);

            fail("test fails");
        } catch (ApiException ex) {
            Assert.assertTrue("incorrent parent", (ex.getMessage().contains("Incorrect parent type")));
        }
        try {
            List<Organization> parents = service.getOrganizationApi().getOrganizations(1, 5);
            if (!parents.isEmpty()) {
                Entity parent = parents.get(0);
                terminal.setParentId(parent.getId());
            }

            service.getTerminalApi().createTerminal(terminal);

            fail("test fails");
        } catch (ApiException ex) {
            Assert.assertTrue("incorrent parent", (ex.getMessage().contains("Incorrect parent type")));
        }
    }
//
//    @Test
//    public void editTerminal() {
//        try {
//            Terminal terminal = service.getTerminal("01234543210");
//            assertEquals("01234543210", terminal.getSerialNumber());
//
//            terminal.setDescription("Updated");
//
//            terminal = service.updateTerminal(terminal.getSerialNumber(), terminal);
//
//            assertEquals("Updated", terminal.getDescription());
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//
//    @Test
//    public void getTerminal() {
//        try {
//            Terminal terminal = service.getTerminal("01234543210");
//            assertEquals("01234543210", terminal.getSerialNumber());
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//
//    @Test
//    public void listTerminals() {
//        try {
//            assertNotNull(service.listTerminal(1, 10));
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void findTerminals() {
//        try {
//            List<Terminal> terminals = service.searchTerminal("PowaPIN 01234543210", 1, 20);
//            assertNotNull(terminals);
//            assertTrue(terminals.size()>0);
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void mAddSettingTerminal() {
//        try {
//            if (terminalId!= null){
//                TerminalSettingValue value = new TerminalSettingValue();
//                value.setDefaultValue("Default");
//                value.setTerminalSetting(TerminalSettingEnum.TIME_OUT);
//                value.setTerminalSettingGroup(TerminalSettingGroupEnum.DEFAULT);
//                value.setValue("Value");
//                assertNotNull(service.addSettingTerminal(terminalId, value));
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void mEditSettingTerminal() {
//        try {
//            if (terminalId!= null){
//                TerminalSettingValue value = new TerminalSettingValue();
//                value.setDefaultValue("DefaultUpdate");
//                value.setTerminalSetting(TerminalSettingEnum.TIME_OUT);
//                value.setTerminalSettingGroup(TerminalSettingGroupEnum.DEFAULT);
//                value.setValue("ValueUpdate");
//                assertNotNull(service.updateSettingTerminal(terminalId, value));
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void listAvailableSettingsTerminal() {
//        try {
//            if (terminalId!= null){
//                assertNotNull(service.listAvailableSettingsTerminal(terminalId));
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void mAddHostATerminal() {
//        try {
//            assertNotNull(service.addHostTerminal(terminalId, HostEnum.WORLDPAY_TCMP));
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void nlistHostSettingsTerminal() {
//        try {
//            if (terminalId != null){
//                assertNotNull(service.listHostSettingsTerminal(terminalId, HostEnum.WORLDPAY_TCMP));
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void mAddHostSettingTerminal() {
//        try {
//            if (terminalId != null){
//                TerminalHostSettingValue value = new TerminalHostSettingValue();
//                value.setDefaultValue("Default");
//                value.setSetting(TerminalHostSettingEnum.HOST_TERMINAL_ID);
//                value.setValue("Value");
//                assertNotNull(service.addHostSettingTerminal(terminalId, HostEnum.WORLDPAY_TCMP, value));
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void mEditHostSettingTerminal() {
//        try {
//            if (terminalId != null){
//                TerminalHostSettingValue value = new TerminalHostSettingValue();
//                value.setDefaultValue("DefaultUpdate");
//                value.setSetting(TerminalHostSettingEnum.HOST_TERMINAL_ID);
//                value.setValue("ValueUpdate");
//                assertNotNull(service.updateHostSettingTerminal(HostEnum.WORLDPAY_TCMP, terminalId, value));
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void nlistAvailableHostsTerminal() {
//        try {
//            if (terminalId!= null){
//                assertNotNull(service.listAvailableHostsTerminal(terminalId));
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void removeASettingTerminal() {
//        try {
//            if (terminalId!= null){
//                service.deleteSettingTerminal(terminalId, TerminalSettingEnum.TIME_OUT);
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void removeHostTerminal() {
//        try {
//            if (terminalId != null){
//                service.deleteHostTerminal(terminalId, HostEnum.WORLDPAY_TCMP);
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void removeHostSettingTerminal() {
//        try {
//            if (terminalId != null){
//                service.deleteHostSettingTerminal(terminalId, HostEnum.WORLDPAY_TCMP, TerminalHostSettingEnum.HOST_TERMINAL_ID);
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//
//    @Test
//    public void removeTerminal() {
//        try {
//            Terminal terminal = service.getTerminal("01234543210");
//            assertEquals("01234543210", terminal.getSerialNumber());
//
//            service.deleteTerminal(terminal.getSerialNumber());
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
}
