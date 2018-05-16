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
public class ClientTest {

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
//    public void createClient() {
//
//        Client client = new Client();
//        client.setFirstName("John");
//        client.setLastName("Hernandez");
//        client.setMiddleInitial("Q");
//        client.setEmail("somebody@luretechnologies.com");
//        client.setCompany("Lure");
//        client.setActive(Boolean.TRUE);
//
//        try {
//            client = service.createClient(client);
//            id = client.getId();
//            assertThat(client, instanceOf(Client.class));
//            assertNotNull(client.getId());
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//
//    @Test
//    public void editClient() {
//        try {
////            List<Client> listClients = service.listClients();
////            assertNotNull(service.listClients());
//
////            for (Client listClient : listClients) {
////                if (listClient.getFirstName().equals("John")) {
////                    client = listClient;
////                    break;
////                }
////            }
//            if (id != null) {
//                Client client = service.getClient(id);
//                if (client != null) {
//                    assertEquals("Q", client.getMiddleInitial());
//                    client.setMiddleInitial("C");
//                    client = service.updateClient(client.getId(), client);
//                    assertEquals("C", client.getMiddleInitial());
//                }
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//
//    @Test
//    public void getClient() {
//        try {
////            List<Client> listClients = service.listClients();
////            assertNotNull(service.listClients());
//            if (id != null) {
//                Client client = service.getClient(id);
//                assertNotNull(client);
//            }
////            for (Client listClient : listClients) {
////                if (listClient.getFirstName().equals("John")) {
////                    client = listClient;
////                    break;
////                }
////            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//
//    @Test
//    public void listClients() {
//        try {
//            assertNotNull(service.listClients(1, 50));
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//
//    }
//
//    @Test
//    public void findClients() {
//        try {
//            List<Client> clients = service.searchClients("John", 1, 20);
//            assertNotNull(clients);
//            assertTrue(clients.size() > 0);
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//
//    @Test
//    public void removeClient() {
//        try {
////            List<Client> listClients = service.listClients();
////            assertNotNull(service.listClients());
//
////            for (Client listClient : listClients) {
////                if (listClient.getFirstName().equals("John")) {
////                    client = listClient;
////                    break;
////                }
////            }
//            if (id != null) {
//                Client client = service.getClient(id);
//                if (client != null) {
//                    assertEquals("John", client.getFirstName());
//                    service.deleteClient(client.getId());
//                }
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
}
