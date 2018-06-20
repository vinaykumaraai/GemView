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
import com.luretechnologies.client.restlib.service.model.Role;
import com.luretechnologies.client.restlib.service.model.UserSession;
import com.luretechnologies.common.enums.PermissionEnum;
import java.util.ArrayList;
import java.util.List;
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
public class RoleTest {

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
    public void createRoleAndgetByNameAndDelte() {
        try {
            //service.getRoleApi().addPermission(new Long(2), PermissionEnum.ALL_ENTITY.toString());
            //service.getRoleApi().addPermission(new Long(2), PermissionEnum.ALL_SYSTEM.toString());
            //service.getRoleApi().addPermission(new Long(2), PermissionEnum.ALL_ODOMETER.toString());
           // service.getRoleApi().addPermission(new Long(2), PermissionEnum.ALL_ASSET.toString());
            //service.getRoleApi().addPermission(new Long(2), PermissionEnum.ALL_HEARTBEAT.toString());
            //service.getRoleApi().addPermission(new Long(2), PermissionEnum.ALL_PERSONALIZATION.toString());
            
            String roleName = "Testing Role 01";
            Role role = null;
            try {
                role = service.getRoleApi().getRolebyName(roleName);
            } catch (ApiException ex) {
                System.out.println(ex.getResponseBody());
            }

            if (role != null && role.getId() != null) {
                service.getRoleApi().deleteRole(role.getId());
                System.out.println("deleted:" + roleName);
            }
            role = new Role();
            role.setName(roleName);
            role.setDescription("Testing Role 01");
            role.setAvailable(true);
            List<PermissionEnum> permissionEnums = new ArrayList<>();

            permissionEnums.add(PermissionEnum.ALL_CLIENT);
            permissionEnums.add(PermissionEnum.ALL_ENTITY);
            role.setPermissions(permissionEnums);

            role = service.getRoleApi().createRole(role);
            assertNotNull(role.getId());
            Assert.assertThat(role, IsInstanceOf.instanceOf(Role.class));

            System.out.println(role.toString());

            role.setDescription("New description updatate");
            role = service.getRoleApi().updateRole(role.getId(), role);
            Assert.assertThat(role, IsInstanceOf.instanceOf(Role.class));
            System.out.println(role.toString());
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void search() {
        try {
            List<Role> listRoles = service.getRoleApi().searchRoles(null, 1, 20);
            assertNotNull(listRoles);
            for (Role temp : listRoles) {
                System.out.println(temp.toString());
            }

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void editRole() {
        try {
            List<Role> listRoles = service.getRoleApi().getRoles(1, 10);
            assertNotNull(listRoles);
            Role role = null;

            for (Role listRole : listRoles) {
                if (listRole.getName().equals("Testing Role 01")) {
                    role = listRole;
                    break;
                }
            }
            if (role != null) {
                role.setDescription("UPDATED");

                role = service.getRoleApi().updateRole(role.getId(), role);
                Assert.assertEquals("UPDATED", role.getDescription());
            }
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }
    @Test
    public void getRole() {
        try {
            long id = 0;
            List<Role> listRoles = service.getRoleApi().getRoles(1, 10);
            assertNotNull(listRoles);

            for (Role listRole : listRoles) {
                if (listRole.getName().equals("Testing Role 01")) {
                    id = listRole.getId();
                    break;
                }
            }

            Role role = service.getRoleApi().getRole(id);
            assertNotNull(role);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void listRoles() {
        try {
            List<Role> listRoles = service.getRoleApi().getRoles(1, 10);
            assertNotNull(listRoles);
            for (Role temp : listRoles) {
                System.out.println(temp.toString());
            }

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void addAndRemovePermission() {
        try {
            List<Role> listRoles = service.getRoleApi().getRoles(1, 10);
            assertNotNull(listRoles);
            Role role = null;
            for (Role listRole : listRoles) {
                if (listRole.getName().equals("Testing Role 01")) {
                    role = listRole;
                    break;
                }
            }
            if (role != null) {
                service.getRoleApi().addPermission(role.getId(), PermissionEnum.ALL_USER.toString());
                System.out.println(role.toString());
                service.getRoleApi().removePermission(role.getId(), PermissionEnum.ALL_USER.toString());
                System.out.println(role.toString());
            }
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }

    }

}
