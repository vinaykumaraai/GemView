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
import com.luretechnologies.client.restlib.service.model.User;
import com.luretechnologies.client.restlib.service.model.UserSession;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNotNull;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 *
 * @author developer
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {

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
//    public void createUser() {
//        try {
//            User user = new User();
//            user.setFirstName("John");
//            user.setLastName("Doe");
//            user.setUsername("john.doe");
//            user.setPassword("JohnDoe123");
//            user.setEmail("john.doe@luretechnologies.com");
//
////            List<Organization> orgs = service.listOrganizations(1, 50);
////            if (!orgs.isEmpty()){
////                Organization organization = orgs.get(0);
////                user.setEntity(organization);
////            }
//
//            Role role = service.getRole(1);
//            user.setRole(role);
//
//            user = service.createUser(user);
//            id = user.getId();
//
//            assertThat(user, instanceOf(User.class));
//            assertNotNull(user.getId());
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//
//    @Test
//    public void editUser() {
//        try {
//            if (id != null) {
//                User user = service.getUser(id);
//                assertEquals("john.doe", user.getUsername());
//
//                user.setLastName("Updated");
//
//                user = service.updateUser(user.getId(), user);
//
//                assertEquals("Updated", user.getLastName());
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }

    //@Test
    public void getUser() {
        try {
            List<User> users = service.getUserApi().getUsers("admin", "", "", true, 1, 10);
            assertNotNull(users);
            if (!users.isEmpty()) {
                User user = service.getUserApi().getUser(users.get(0).getId());
                assertEquals("admin", user.getUsername());
            }
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    //@Test
    public void listUsers() {
        try {
            List<User> users = service.getUserApi().getUsers("admin", "", "", true, 1, 10);
            assertNotNull(users);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

//    @Test
//    public void removeUser() {
//        try {
//            if (id != null) {
//                User user = service.getUser(id);
//                assertEquals("john.doe@luretechnologies.com", user.getEmail());
//
//                service.deleteUser(user.getId());
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
}
