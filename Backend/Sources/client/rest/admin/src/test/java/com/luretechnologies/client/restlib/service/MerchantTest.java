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
import com.luretechnologies.client.restlib.service.model.Merchant;
import com.luretechnologies.client.restlib.Utils;
import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.common.CommonConstants;
import com.luretechnologies.client.restlib.service.model.UserSession;
import org.junit.Assert;
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
public class MerchantTest {

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

//    @Test
//    public void createMerchant() {
//        Merchant merchant = new Merchant();
//        merchant.setName("Testing Merchant for TSYS");
//        merchant.setDescription("Testing Merchant for TSYS"); 
//        try {
//            List<Organization> organizations = service.getOrganizationApi().getOrganizations(1, 1);
//            if (!organizations.isEmpty()) {
//                merchant.setParentId(organizations.get(0).getId());
//            }
//            merchant = service.getMerchantApi().createMerchant(merchant);
//            assertThat(merchant, IsInstanceOf.instanceOf(Merchant.class));
//            assertNotNull(merchant.getEntityId());
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }

//    @Test
//    public void editMerchant() {
//        try {
//            if (merchantId != null) {
//                Merchant merchant = service.getMerchant(merchantId);
//                if (merchant != null) {
//                    assertEquals("Testing Merchant", merchant.getDescription());
//                    merchant.setDescription("UPDATED");                   
//                  
//                    merchant = service.updateMerchant(merchant.getEntityId(), merchant);
//                    assertEquals("UPDATED", merchant.getDescription());
//                }
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//
//    }
//
//    @Test
//    public void getMerchant() {
//        try {
//            if (merchantId != null) {
//                Merchant merchant = service.getMerchant(merchantId);
//                assertNotNull(merchant);
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }

    @Test
    public void listMerchants() {
        try {
            List<Merchant> merchants = service.getMerchantApi().getMerchants(1, 10);
            Assert.assertNotNull(merchants);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }

    }

//    @Test
//    public void findMerchants() {
//        try {
//            List<Merchant> merchants = service.searchMerchants("Testing Merchant", 1, 20);
//            assertNotNull(merchants);
//            assertTrue(merchants.size()>0);
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void mAddHostAMerchant() {
//        try {
//            if (merchantId!= null){
//            //Merchant merchant = service.getMerchant(merchantId);
//                assertNotNull(service.addHostMerchant(merchantId, HostEnum.WORLDPAY_TCMP));
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void mAddHostSettingMerchant() {
//        try {
//            if (merchantId!= null){
//            //Merchant merchant = service.getMerchant(merchantId);
//                MerchantHostSettingValue value = new MerchantHostSettingValue();
//                value.setDefaultValue("Default");
//                value.setSetting(MerchantHostSettingEnum.HOST_MERCHANT_NUMBER);
//                value.setValue("Value");
//                assertNotNull(service.addHostSettingMerchant(merchantId, HostEnum.WORLDPAY_TCMP, value));
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void mEditHostSettingMerchant() {
//        try {
//            if (merchantId!= null){
//            //Merchant merchant = service.getMerchant(merchantId);
//                MerchantHostSettingValue value = new MerchantHostSettingValue();
//                value.setDefaultValue("DefaultUpdate");
//                value.setSetting(MerchantHostSettingEnum.HOST_MERCHANT_NUMBER);
//                value.setValue("ValueUpdate");
//                assertNotNull(service.updateHostSettingMerchant(HostEnum.WORLDPAY_TCMP, merchantId, value));
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void listAvailableHostsMerchant() {
//        try {
//            //Merchant merchant = service.getMerchant(id);            
//            if (merchantId!= null){
//                assertNotNull(service.listAvailableHostsMerchant(merchantId));
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void listHostSettingsMerchant() {
//        try {
//            //Merchant merchant = service.getMerchant(id);      
//            if (merchantId!= null){
//                assertNotNull(service.listHostSettingsMerchant(merchantId, HostEnum.WORLDPAY_TCMP));
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void mAddSettingMerchant() {
//        try {
//            if (merchantId!= null){
//            //Merchant merchant = service.getMerchant(merchantId);
//                MerchantSettingValue value = new MerchantSettingValue();
//                value.setDefaultValue("Default");
//                value.setMerchantSetting(MerchantSettingEnum.GRATUITY_RATE_1);
//                value.setMerchantSettingGroup(MerchantSettingGroupEnum.DEFAULT);
//                value.setValue("Value");
//                assertNotNull(service.addSettingMerchant(merchantId, value));
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void mEditSettingMerchant() {
//        try {
//            if (merchantId!= null){
//            //Merchant merchant = service.getMerchant(merchantId);
//                MerchantSettingValue value = new MerchantSettingValue();
//                value.setDefaultValue("DefaultUpdate");
//                value.setMerchantSetting(MerchantSettingEnum.GRATUITY_RATE_1);
//                value.setMerchantSettingGroup(MerchantSettingGroupEnum.DEFAULT);
//                value.setValue("ValueUpdate");
//                assertNotNull(service.updateSettingMerchant(merchantId, value));
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void listAvailableSettingsMerchant() {
//        try {
//            if (merchantId!= null){
//                assertNotNull(service.listAvailableSettingsMerchant(merchantId));
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void removeHostASettingMerchant() {
//        try {
//            if (merchantId!= null){
//                service.deleteHostSettingMerchant(merchantId, HostEnum.WORLDPAY_TCMP, MerchantHostSettingEnum.HOST_MERCHANT_NUMBER);
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void removeASettingMerchant() {
//        try {
//            if (merchantId!= null){
//                service.deleteSettingMerchant(merchantId, MerchantSettingEnum.GRATUITY_RATE_1);
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void removeHostMerchant() {
//        try {
//            //Merchant merchant = service.getMerchant(id);
//            if (merchantId!= null){
//                service.deleteHostMerchant(merchantId, HostEnum.WORLDPAY_TCMP);
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//
//    @Test
//    public void removeMerchant() {
//        try {
//            if (merchantId != null) {
//                Merchant merchant = service.getMerchant(merchantId);
//                if (merchant != null) {
//                    assertEquals("Testing Merchant", merchant.getName());
//                    service.deleteMerchant(merchant.getEntityId());
//                }
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }

}
