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
import com.luretechnologies.client.restlib.service.model.Organization;
import com.luretechnologies.client.restlib.service.model.UserSession;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNotNull;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MerchantTest {

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

    @Test
    public void createAndDeleteMerchant() {
        Merchant merchant = new Merchant();
        merchant.setName("Testing Merchant for TSYS");
        merchant.setDescription("Testing Merchant for TSYS");
        try {
            List<Organization> organizations = service.getOrganizationApi().getOrganizations(1, 1);
            if (!organizations.isEmpty()) {
                merchant.setParentId(organizations.get(0).getId());
            }
            merchant = service.getMerchantApi().createMerchant(merchant);
            Assert.assertThat(merchant, IsInstanceOf.instanceOf(Merchant.class));
            assertNotNull(merchant.getEntityId());
            
            service.getMerchantApi().deleteMerchant(merchant.getEntityId());
            //merchant = service.getMerchantApi().getMerchant(merchant.getEntityId());
           // Assert.assertEquals( merchant, null );
            
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void editMerchant() {
        try {
            String merchantId = "MERY0DA74ARN4";
            Merchant merchant = service.getMerchantApi().getMerchant(merchantId);
            if (merchant != null) {
                // Assert.assertEquals("Testing Merchant", merchant.getDescription());
                merchant.setDescription("UPDATED");

                merchant = service.getMerchantApi().updateMerchant(merchant.getEntityId(), merchant);
                Assert.assertEquals("UPDATED", merchant.getDescription());
            }
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }

    }

    @Test
    public void getMerchant() {
        try {
            String merchantId = "MERY0DA74ARN4";
            if (merchantId != null) {
                Merchant merchant = service.getMerchantApi().getMerchant(merchantId);
                assertNotNull(merchant);
                
            }
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void listMerchants() {
        try {
            List<Merchant> merchants = service.getMerchantApi().getMerchants(1, 10);
            Assert.assertNotNull(merchants);
            for (Merchant temp : merchants) {
                System.out.println(temp.toString());
            }
            
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }

    }

    @Test
    public void findMerchants() {
        try {
            List<Merchant> merchants = service.getMerchantApi().searchMerchants("Testing Merchant", 1, 20);
            assertNotNull(merchants);
            Assert.assertTrue(merchants.size() > 0);
            for (Merchant temp : merchants) {
                System.out.println(temp.toString());
            }
            
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

}
