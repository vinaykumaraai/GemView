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
import com.luretechnologies.client.restlib.service.model.Model;
import com.luretechnologies.client.restlib.service.model.UserSession;
import java.util.List;
import java.util.Random;
import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
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
public class ModelTest {

    private static RestClientService service;
    private static UserSession userSession;

    /**
     *
     */
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
    public void createAndUpdateandDelete() {
        try {
            Model model = new Model();
            Random rand = new Random();
            String name = "Model name test 001 " + (Long.toString(rand.nextLong()));
            model.setName(name);
            model.setDescription("description test 001");
            model.setAvailable(true);
            model.setManufacturer("test");
            model.setRkiCapable(true);
            model.setOsUpdate(true);
            model = service.getModelApi().create(model);
            assertNotNull(model);
            assertNotNull(model.getId());
            System.out.println(model.toString());
            // Update
            model.setDescription("Update");
            model.setOsUpdate(false);
            model = service.getModelApi().update(model);
            assertNotNull(model);
            assertNotNull(model.getId());
            System.out.println(model.toString());
            // Delete 
            service.getModelApi().delete(model.getId());

        } catch (ApiException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void get() {
        try {
            Model model = service.getModelApi().get(new Long(3));
            assertNotNull(model);
            assertNotNull(model.getId());
            System.out.println(model.toString());
        } catch (ApiException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void search() {
        try {
            List<Model> modelList;
            modelList = service.getModelApi().search(null, 1, 20);
            assertNotNull(modelList);
            Assert.assertTrue(modelList.size() > 0);

            for (Model temp : modelList) {
                System.out.println(temp.toString());
            }

            modelList = service.getModelApi().search("Update", 1, 20);
            assertNotNull(modelList);
            Assert.assertTrue(modelList.size() > 0);

            for (Model temp : modelList) {
                System.out.println(temp.toString());
            }

        } catch (ApiException ex) {
            fail(ex.getMessage());
        }
    }
    
}
