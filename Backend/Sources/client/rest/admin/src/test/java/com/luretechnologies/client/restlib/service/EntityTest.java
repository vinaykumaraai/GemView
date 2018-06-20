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
import com.luretechnologies.client.restlib.service.model.Entity;
import com.luretechnologies.client.restlib.service.model.Organization;
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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntityTest {

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
    public void getEntityChildren() {
        try {
            Organization found = service.getOrganizationApi().getOrganizations(1, 10).get(0);
            List<Entity> children = service.getEntityApi().getEntityChildren(found.getId());
            assertNotNull(children);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void getEntitiesHierarchy() {
        try {
            Entity node = service.getEntityApi().getEntityHierarchy();
            assertNotNull(node);
            
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }

    }

    @Test
    public void getEntityHierarchy() {
        try {
            Organization found = service.getOrganizationApi().getOrganizations(1, 10).get(0);
            Entity node = service.getEntityApi().getEntityHierarchy(found.getId());
            assertNotNull(node);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    // TODO: Fix0 this test. java.lang.AssertionError: {"code":500,"message":"Parent and entity id must be differents.; nested exception is javax.persistence.PersistenceException: Parent and entity id must be differents."}
    // FIXME Fix1 this test. java.lang.AssertionError: {"code":500,"message":"Parent and entity id must be differents.; nested exception is javax.persistence.PersistenceException: Parent and entity id must be differents."}
    // TODO Fix2 this test. java.lang.AssertionError: {"code":500,"message":"Parent and entity id must be differents.; nested exception is javax.persistence.PersistenceException: Parent and entity id must be differents."}
    // @Test
    public void moveEntity() {
        try {
            long newParent = 2;
            Entity entity = service.getEntityApi().moveEntity(2, newParent);

            assertTrue(entity.getParentId() == newParent);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }
}
