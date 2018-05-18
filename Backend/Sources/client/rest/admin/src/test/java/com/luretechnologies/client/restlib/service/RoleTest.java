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
public class RoleTest {

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
//    public void createRole() {
//        Role role = new Role();
//        role.setName("Testing Role");
//        role.setDescription("Testing Role");
//        try {
//            role = service.createRole(role);
//            assertThat(role, instanceOf(Role.class));
//            assertNotNull(role.getId());
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//
//    @Test
//    public void editRole() {
//        try {
//            List<Role> listRoles = service.listRoles(1, 10);
//            assertNotNull(listRoles);
//            Role role = null;
//
//            for (Role listRole : listRoles) {
//                if (listRole.getName().equals("Testing Role")) {
//                    role = listRole;
//                    break;
//                }
//            }
//            if (role != null) {
//                assertEquals("Testing Role", role.getDescription());
//                role.setDescription("UPDATED");
//
//                role = service.updateRole(role.getId(), role);
//                assertEquals("UPDATED", role.getDescription());
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//
//    @Test
//    public void getRole() {
//        try {
//            List<Role> listRoles = service.listRoles(1, 10);
//            assertNotNull(listRoles);
//
//            for (Role listRole : listRoles) {
//                if (listRole.getName().equals("Testing Role")) {
//                    Role role = listRole;
//                    break;
//                }
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//
//    @Test
//    public void listRoles() {
//        try {
//            List<Role> listRoles = service.listRoles(1, 10);
//            assertNotNull(listRoles);
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//
//    @Test
//    public void addPermission() {
//        try {
//            List<Role> listRoles = service.listRoles(1, 10);
//            assertNotNull(listRoles);
//            Role role = null;
//            for (Role listRole : listRoles) {
//                if (listRole.getName().equals("Testing Role")) {
//                    role = listRole;
//                    break;
//                }
//            }
//            if (role != null) {
//                assertEquals("Testing Role", role.getName());
//                service.addPermission(role.getId(), PermissionEnum.ALL_USER);
//
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//
//    }
//
//    @Test
//    public void removePermission() {
//        try {
//            List<Role> listRoles = service.listRoles(1, 10);
//            assertNotNull(listRoles);
//            Role role = null;
//            for (Role listRole : listRoles) {
//                if (listRole.getName().equals("Testing Role")) {
//                    role = listRole;
//                    break;
//                }
//            }
//            if (role != null) {
//                assertEquals("Testing Role", role.getName());
//                service.removePermission(role.getId(), PermissionEnum.ALL_USER);
//
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//
//    }
//
//    @Test
//    public void removeRole() {
//        try {
//            List<Role> listRoles = service.listRoles(1, 10);
//            assertNotNull(listRoles);
//            Role role = null;
//            for (Role listRole : listRoles) {
//                if (listRole.getName().equals("Testing Role")) {
//                    role = listRole;
//                    break;
//                }
//            }
//            if (role != null) {
//                assertEquals("Testing Role", role.getName());
//                service.deleteRole(role.getId());
//                
//            }
//        } catch (ApiException ex) {
//           fail(ex.getResponseBody());
//        }
//    }
}
