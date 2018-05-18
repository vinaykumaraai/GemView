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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNotNull;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrganizationTest {

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
//    public void createOrganization() {
//
//        Organization organization = new Organization();
//        organization.setName("Testing Organization");
//        organization.setDescription("Testing Organization");
//
//        try {
//            //Entity entityRoot = service.getEntityRoot();
//            //organization.setParent(entityRoot);
//            organization = service.createOrganization(organization);
//            id = organization.getEntityId();
//            assertThat(organization, instanceOf(Organization.class));
//            assertNotNull(organization.getEntityId());
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//
//    @Test
//    public void editOrganization() {
//        try {
////            List<Organization> listOrganizations = service.listOrganizations();
////            assertNotNull(service.listOrganizations());
////            Organization organization = null;
////            
////            for (Organization listOrganization : listOrganizations) {
////                if (listOrganization.getName().equals("Testing Organization")) {
////                    organization = listOrganization;
////                    break;
////                }
////            }
//
//            if (id != null) {
//                Organization organization = service.getOrganization(id);
//
//                if (organization != null) {
//                    assertEquals("Testing Organization", organization.getDescription());
//                    organization.setDescription("UPDATED");
//
//                    organization = service.updateOrganization(organization.getEntityId(), organization);
//                    assertEquals("UPDATED", organization.getDescription());
//                }
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//
//    }
//
//    @Test
//    public void getOrganization() {
//        try {
////            List<Organization> listOrganizations = service.listOrganizations();
////            assertNotNull(service.listOrganizations());
////            for (Organization listOrganization : listOrganizations) {
////                if (listOrganization.getName().equals("Testing Organization")) {
////                    Organization organization = listOrganization;
////                    assertEquals("Testing Organization", organization.getName());
////                    break;
////                }
////            }
//            if (id != null) {
//                Organization organization = service.getOrganization(id);
//                assertNotNull(organization);
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//
//    @Test
//    public void listOrganizations() {
//        try {
//            assertNotNull(service.listOrganizations(1, 10));
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void findOrganizations() {
//        try {
//            List<Organization> orgs = service.searchOrganization("Testing Organization", 1, 20);
//            assertNotNull(orgs);
//            assertTrue(orgs.size()>0);
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//
//    @Test
//    public void removeOrganization() {
//        try {
////            List<Organization> listOrganizations = service.listOrganizations();
////            assertNotNull(service.listOrganizations());
////            Organization organization = null;
////            
////            for (Organization listOrganization : listOrganizations) {
////                if (listOrganization.getName().equals("Testing Organization")) {
////                    organization = listOrganization;
////                    break;
////                }
////            }
//            if (id != null) {
//                Organization organization = service.getOrganization(id);
//                if (organization != null) {
//                    assertEquals("Testing Organization", organization.getName());
//                    service.deleteOrganization(organization.getEntityId());
//                }
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
}
