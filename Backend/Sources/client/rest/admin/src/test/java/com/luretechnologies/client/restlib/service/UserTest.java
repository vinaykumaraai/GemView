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
import com.luretechnologies.client.restlib.service.model.Role;
import com.luretechnologies.client.restlib.service.model.User;
import com.luretechnologies.client.restlib.service.model.UserSession;
import java.util.List;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNotNull;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {

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
    public void createAndActivateAndDeleteAnUser() {
        try {
            String username = "john.doe";
            User user = service.getUserApi().getUserByUserName(username);
            if ( user != null ){
                service.getUserApi().deleteUser(user.getId());
            }
            user = new User();
            user.setFirstName("John");
            user.setLastName("Doe");
            user.setUsername(username);
            user.setPassword("JohnDoe123");
            user.setEmail("john.doe@luretechnologies.com");
            // entity id =3 or "ORGNQBZKO6GJP"  is Test Organization  
            Organization org = service.getOrganizationApi().getOrganization("ORGNQBZKO6GJP");
            if (org != null) {
                user.setEntity(org);
            } else {
                fail("it doesn't getting the organization");
            }

            // Rolo 2 it is the test rolo
            Role role = service.getRoleApi().getRole(new Long(2));
            user.setRole(role);

            user = service.getUserApi().createUser(user);

            Assert.assertThat(user, IsInstanceOf.instanceOf(User.class));
            assertNotNull(user.getId());

            // active the user
            // Delete de user.
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void editUser() {
        try {
            Long id = 20L;
            User user = service.getUserApi().getUser(id);

            user.setLastName("Updated");
            user.setPasswordFrequency(20);

            user = service.getUserApi().updateUser(user.getId(), user);

            assertEquals("Updated", user.getLastName());

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void getUser() {
        try {
            String name = CommonConstants.testStandardUsername;
            List<User> users = service.getUserApi().getUsers(name, true, 1, 10);
            assertNotNull(users);
            for (User user : users) {
                System.out.println("User: " + user.getUsername());
            }

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void listUsers() {
        try {
            List<User> users = service.getUserApi().getUsers();
            assertNotNull(users);
            for (User user : users) {
                System.out.println("User: " + user.getUsername());
            }
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void removeUser() {
        try {
            User user = service.getUserApi().getUser(new Long(20));
            assertNotNull(user);
            service.getUserApi().deleteUser(new Long(20));
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }
    @Test
    public void findUserByEmail() {
        try {
            User user = service.getUserApi().getUserByEmail("test_standard@gemstonepay.com");
            assertNotNull(user);
            System.out.println("User: " + user.toString());
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }
    
    
}
