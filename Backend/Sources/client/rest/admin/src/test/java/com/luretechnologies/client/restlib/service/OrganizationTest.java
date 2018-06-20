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
import com.luretechnologies.client.restlib.service.model.Organization;
import com.luretechnologies.client.restlib.service.model.UserSession;
import java.util.List;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNotNull;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrganizationTest {

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
    public void createOrganization() {

        Organization organization = new Organization();
        organization.setName("Testing Organization");
        organization.setDescription("Testing Organization");

        try {
            List<Organization> organizations = service.getOrganizationApi().getOrganizations(1, 1);
            if (!organizations.isEmpty()) {
                organization.setParentId(organizations.get(0).getId());
            }

            //Entity entityRoot = service.getEntityRoot();
            //organization.setParent(entityRoot);
            organization = service.getOrganizationApi().createOrganization(organization);
            String id = organization.getEntityId();
            Assert.assertThat(organization, IsInstanceOf.instanceOf(Organization.class));
            assertNotNull(organization.getEntityId());
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void editOrganization() {
        try {
            List<Organization> listOrganizations = service.getOrganizationApi().getOrganizations(1, 100);
            assertNotNull(listOrganizations);
            Organization organization = null;

            for (Organization listOrganization : listOrganizations) {
                if (listOrganization.getName().equals("Test Organization")) {
                    organization = listOrganization;
                    break;
                }
            }

            if (organization != null) {
                organization.setDescription("UPDATED");

                organization = service.getOrganizationApi().updateOrganization(organization.getEntityId(), organization);
                assertEquals("UPDATED", organization.getDescription());
            }
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }

    }

    @Test
    public void getOrganization() {
        try {

            List<Organization> organizations = service.getOrganizationApi().getOrganizations(1, Integer.MAX_VALUE);
            assertNotNull(organizations);

            Organization organization1 = null;

            for (Organization o : organizations) {
                if (o.getName().equals("Test Organization")) {
                    organization1 = o;
                    break;
                }
            }

            assertNotNull(organization1);

            Organization organization2 = service.getOrganizationApi().getOrganization(organization1.getEntityId());
            assertNotNull(organization2);
            assertEquals("Test Organization", organization2.getName());

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void listOrganizations() {
        try {
            List<Organization> organizations = service.getOrganizationApi().getOrganizations(1, Integer.MAX_VALUE);
            assertNotNull(organizations);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void findOrganizations() {
        try {

            List<Organization> organizations = service.getOrganizationApi().searchOrganizations("Org", 1, Integer.MAX_VALUE);
            assertNotNull(organizations);
            assertTrue(organizations.size() > 0);

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void removeOrganization() {
        try {

            List<Organization> organizations = service.getOrganizationApi().getOrganizations(1, Integer.MAX_VALUE);
            assertNotNull(organizations);

            Organization organization1 = null;

            for (Organization o : organizations) {

                if (o.getName().equals("Test Organization")) {
                    organization1 = o;
                    break;
                }
            }

            assertNotNull(organization1);
            Organization organization2 = service.getOrganizationApi().getOrganization(organization1.getEntityId());
            assertNotNull(organization2);
            assertEquals("Test Organization", organization2.getName());
            // service.deleteOrganization(organization.getEntityId());

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }
}
