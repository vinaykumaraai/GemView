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
import com.luretechnologies.client.restlib.service.model.Region;
import com.luretechnologies.client.restlib.service.model.UserSession;
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
public class RegionTest {

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
    public void createAndDeleteRegion() {

        Region region = new Region();
        region.setName("Testing Region");
        region.setDescription("Testing Region");

        try {
            List<Organization> organizations = service.getOrganizationApi().getOrganizations(1, 1);
            if (!organizations.isEmpty()) {
                region.setParentId(organizations.get(0).getId());
            }
            region = service.getRegionApi().createRegion(region);
            Assert.assertThat(region, IsInstanceOf.instanceOf(Region.class));
            assertNotNull(region.getEntityId());
            System.out.println(region.toString());

            service.getRegionApi().deleteRegion(region.getEntityId());

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void editRegion() {
        try {

            Region region = service.getRegionApi().getRegion("REGXJZ8EOD694");
            if (region != null) {
                region.setDescription("UPDATED");

                region = service.getRegionApi().updateRegion(region.getEntityId(), region);
                Assert.assertEquals("UPDATED", region.getDescription());
                System.out.println(region.toString());
            }
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }

    }

    @Test
    public void getRegion() {
        try {
            Region region = service.getRegionApi().getRegion("REG0EZ04K46R4");
            assertNotNull(region);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }

    }

    @Test
    public void listRegions() {
        try {
            assertNotNull(service.getRegionApi().getRegions(1, 10));
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void findRegions() {
        try {
            List<Region> regions = service.getRegionApi().searchRegions("Testing Region", 1, 20);
            assertNotNull(regions);
            Assert.assertTrue(regions.size() > 0);
            for (Region temp : regions) {
                System.out.println(temp.toString());
            }

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }
//
//    @Test
//    public void removeRegion() {
//        try {
////            List<Region> listRegions = service.listRegions();
////            assertNotNull(service.listRegions());
////            Region region = null;
////
////            for (Region listRegion : listRegions) {
////                if (listRegion.getName().equals("Testing Region")) {
////                    region = listRegion;
////                    break;
////                }
////            }
//            if (id != null) {
//                Region region = service.getRegion(id);
//                if (region != null) {
//                    assertEquals("Testing Region", region.getName());
//                    service.deleteRegion(region.getEntityId());
//                }
//            }
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//
//    }
}
