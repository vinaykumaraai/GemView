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
import com.luretechnologies.client.restlib.service.model.Device;
import com.luretechnologies.client.restlib.service.model.Terminal;
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
public class DeviceTest {

    private static RestClientService service;
    private static UserSession userSession;
    private static String id;

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
    public void createAndDeleteDevice() {
        Device device = new Device();
        device.setName("Testing Device");
        device.setDescription("Testing Device");

        device.setSerialNumber(Long.toString(System.currentTimeMillis()));
        device.setAvailable(Boolean.TRUE);
        try {
            List<Terminal> terminals = service.getTerminalApi().getTerminals(1, 10);
            if (!terminals.isEmpty()) {
                device.setParentId(terminals.get(0).getId());
            }
            device = service.getDeviceApi().createDevice(device);
            id = device.getEntityId();
            Assert.assertThat(device, IsInstanceOf.instanceOf(Device.class));
            assertNotNull(device.getEntityId());
            System.out.println(device.toString());

            service.getDeviceApi().deleteDevice(id);

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void editDevice() {
        try {
            if (id != null) {
                Device device = service.getDeviceApi().getDevice(id);
                if (device != null) {
                    device.setDescription("UPDATED");
                    device = service.getDeviceApi().updateDevice(device.getEntityId(), device);
                    Assert.assertEquals("UPDATED", device.getDescription());
                }
            } else {
                fail("the device id is null");
            }
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void getDevice() {
        try {
            if (id != null) {
                Device device = service.getDeviceApi().getDevice(id);
                assertNotNull(device);
                System.out.println(device.toString());
            }
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void listDevices() {
        try {
            List<Device> devices = service.getDeviceApi().getDevices(1, 50);

            assertNotNull(devices);
            for (Device temp : devices) {
                System.out.println("User: " + temp.toString());
            }

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }

    }

    @Test
    public void findDevices() {
        try {
            List<Device> devices = service.getDeviceApi().searchDevices("Testing Device", 1, 20);
            assertNotNull(devices);
            Assert.assertTrue(devices.size()>0);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }
}
