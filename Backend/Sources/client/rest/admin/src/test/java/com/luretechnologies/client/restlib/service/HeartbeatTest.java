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

import java.util.List;
import com.luretechnologies.client.restlib.Utils;
import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.common.CommonConstants;
import com.luretechnologies.client.restlib.service.model.DownloadInfo;
import com.luretechnologies.client.restlib.service.model.Heartbeat;
import com.luretechnologies.client.restlib.service.model.HeartbeatAlert;
import com.luretechnologies.client.restlib.service.model.HeartbeatAppInfo;
import com.luretechnologies.client.restlib.service.model.HeartbeatAudit;
import com.luretechnologies.client.restlib.service.model.HeartbeatOdometer;
import com.luretechnologies.client.restlib.service.model.HeartbeatResponse;
import com.luretechnologies.client.restlib.service.model.HeartbeatUpdateParam;
import com.luretechnologies.client.restlib.service.model.RKIInfo;
import com.luretechnologies.client.restlib.service.model.Terminal;
import com.luretechnologies.client.restlib.service.model.UserSession;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
public class HeartbeatTest {

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
    public void heartbeatSwComponents() {
        try {
            List<Terminal> terminals = service.getTerminalApi().getTerminals(1, 100);
            for (Terminal temp : terminals) {
                Heartbeat heartbeat = new Heartbeat();
                heartbeat.setSerialNumber(temp.getSerialNumber());
                heartbeat.setHwModel("VP5300");
                heartbeat.setStatus(0);
                heartbeat.setMessage("Message test");
                heartbeat.setSequence("102");

                HeartbeatAppInfo heartbeatAppInfo = new HeartbeatAppInfo();
                heartbeatAppInfo.setName("PAYAPP");
                heartbeatAppInfo.setVersion("1.0.2");
                heartbeatAppInfo.setUpdatedAt("2018-05-21 13:56:22");
                heartbeat.getSwComponents().add(heartbeatAppInfo);

                heartbeatAppInfo = new HeartbeatAppInfo();
                heartbeatAppInfo.setName("CONTACT_KERNEL");
                heartbeatAppInfo.setVersion("1.2.34");
                heartbeatAppInfo.setUpdatedAt("2018-05-21 13:56:22");
                heartbeat.getSwComponents().add(heartbeatAppInfo);

                heartbeatAppInfo = new HeartbeatAppInfo();
                heartbeatAppInfo.setName("TOS");
                heartbeatAppInfo.setVersion("1.10.156");
                heartbeatAppInfo.setUpdatedAt("2018-05-21 13:56:22");
                heartbeat.getSwComponents().add(heartbeatAppInfo);

                HeartbeatResponse heartbeatResponse = service.getHeartbeatApi().create(heartbeat);
                assertNotNull(heartbeatResponse);
                System.out.println(heartbeatResponse.toString());
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            fail(ex.getMessage());
        }
    }

    @Test
    public void heartbeatOdometer() {
        try {
            List<Terminal> terminals = service.getTerminalApi().getTerminals(1, 100);
            for (Terminal temp : terminals) {
                Heartbeat heartbeat = new Heartbeat();
                heartbeat.setSerialNumber(temp.getSerialNumber());
                heartbeat.setHwModel("VP5300");
                heartbeat.setStatus(0);
                heartbeat.setMessage("Message test");
                heartbeat.setSequence("102");

                HeartbeatOdometer heartbeatOdometer = new HeartbeatOdometer();

                heartbeatOdometer.setComponent("PAYAPP");
                heartbeatOdometer.setLabel("TOTAL_SWPES");
                heartbeatOdometer.setDescription("Number of cards swipes");
                heartbeatOdometer.setValue("1000");
                heartbeat.getHeartbeatOdometers().add(heartbeatOdometer);

                heartbeatOdometer = new HeartbeatOdometer();
                heartbeatOdometer.setComponent("PAYAPP");
                heartbeatOdometer.setLabel("BAD_SWPES");
                heartbeatOdometer.setDescription("Number of bad cards swipes");
                heartbeatOdometer.setValue("18");
                heartbeat.getHeartbeatOdometers().add(heartbeatOdometer);

                heartbeatOdometer = new HeartbeatOdometer();
                heartbeatOdometer.setComponent("APPMGR");
                heartbeatOdometer.setLabel("REBOOTS");
                heartbeatOdometer.setDescription("Number of terminal restarts");
                heartbeatOdometer.setValue("30");
                heartbeat.getHeartbeatOdometers().add(heartbeatOdometer);

                HeartbeatResponse heartbeatResponse = service.getHeartbeatApi().create(heartbeat);
                assertNotNull(heartbeatResponse);
                System.out.println(heartbeatResponse.toString());
            }

        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void heartbeatUpdateParam() {
        try {
            List<Terminal> terminals = service.getTerminalApi().getTerminals(1, 100);
            for (Terminal temp : terminals) {
                Heartbeat heartbeat = new Heartbeat();
                heartbeat.setSerialNumber(temp.getSerialNumber());
                heartbeat.setHwModel("VP5300");
                heartbeat.setStatus(0);
                heartbeat.setMessage("Message test");
                heartbeat.setSequence("102");

                HeartbeatUpdateParam heartbeatUpdateParam = new HeartbeatUpdateParam();
                heartbeatUpdateParam.setAppName("APPPAY");
                heartbeatUpdateParam.setName("tip");
                heartbeatUpdateParam.setValue("1");
                heartbeat.getHeartbeatUpdateParams().add(heartbeatUpdateParam);

                heartbeatUpdateParam = new HeartbeatUpdateParam();
                heartbeatUpdateParam.setAppName("MASTERAPP");
                heartbeatUpdateParam.setName("BlackAndWhiteScreen");
                heartbeatUpdateParam.setValue("1");
                heartbeat.getHeartbeatUpdateParams().add(heartbeatUpdateParam);

                heartbeatUpdateParam = new HeartbeatUpdateParam();
                heartbeatUpdateParam.setAppName("CONTROLPANEL");
                heartbeatUpdateParam.setName("BlackAndWhiteScreen");
                heartbeatUpdateParam.setValue("12");
                heartbeat.getHeartbeatUpdateParams().add(heartbeatUpdateParam);

                HeartbeatResponse heartbeatResponse = service.getHeartbeatApi().create(heartbeat);
                assertNotNull(heartbeatResponse);
                System.out.println(heartbeatResponse.toString());
            }

        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void heartbeatAuditsAlerts() {
        try {

            List<Terminal> terminals = service.getTerminalApi().getTerminals(1, 100);
            for (Terminal temp : terminals) {

                Heartbeat heartbeat = new Heartbeat();
                heartbeat.setSerialNumber(temp.getSerialNumber());

                heartbeat.setHwModel("VP5300");
                heartbeat.setStatus(0);
                heartbeat.setMessage("Message test");
                heartbeat.setSequence("102");

                HeartbeatAudit heartbeatAudit = new HeartbeatAudit();
                heartbeatAudit.setOccurred(new Timestamp(System.currentTimeMillis()));
                heartbeatAudit.setComponent("PAYAPP");
                heartbeatAudit.setLabel("USER");
                heartbeatAudit.setMessage("Clerk [Joe] was added");
                heartbeatAudit.setDescription("Clerk [Joe] was added");
                heartbeat.getHeartbeatAudits().add(heartbeatAudit);

                heartbeatAudit = new HeartbeatAudit();
                heartbeatAudit.setOccurred(new Timestamp(System.currentTimeMillis()));
                heartbeatAudit.setComponent("PAYAPP");
                heartbeatAudit.setLabel("USER");
                heartbeatAudit.setMessage("Clerk [Sarah] was added");
                heartbeatAudit.setDescription("Clerk [Sarah] was added");
                heartbeat.getHeartbeatAudits().add(heartbeatAudit);

                heartbeatAudit = new HeartbeatAudit();
                heartbeatAudit.setOccurred(new Timestamp(System.currentTimeMillis()));
                heartbeatAudit.setComponent("PAYAPP");
                heartbeatAudit.setLabel("USER");
                heartbeatAudit.setMessage("Clerk [brad] was delete");
                heartbeatAudit.setDescription("Clerk [Sarah] was added Description");
                heartbeat.getHeartbeatAudits().add(heartbeatAudit);

                HeartbeatAlert heartbeatAlert = new HeartbeatAlert();
                heartbeatAlert.setOccurred(new Timestamp(System.currentTimeMillis()));
                heartbeatAlert.setComponent("PAYAPP");
                heartbeatAlert.setLabel("TOO_MANY_VOIDS");
                heartbeat.getHeartbeatAlerts().add(heartbeatAlert);
                
                heartbeatAlert = new HeartbeatAlert();
                heartbeatAlert.setOccurred(new Timestamp(System.currentTimeMillis()));
                heartbeatAlert.setComponent("PAYAPP");
                heartbeatAlert.setLabel("TOO_MANY_VOID");
                heartbeat.getHeartbeatAlerts().add(heartbeatAlert);

                HeartbeatResponse heartbeatResponse = service.getHeartbeatApi().create(heartbeat);
                assertNotNull(heartbeatResponse);
                System.out.println(heartbeatResponse.toString());
            }
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void heartbeatOdometerSearch() {
        try {
            String terminalId = "TERDXJZ899694";
            List<HeartbeatOdometer> HeartbeatOdometerList = service.getHeartbeatApi().searchOdometer(terminalId, null, null, null, null, null);

            assertNotNull(HeartbeatOdometerList);
            assertTrue(HeartbeatOdometerList.size() > 0);

            for (HeartbeatOdometer temp : HeartbeatOdometerList) {
                System.out.println(temp.toString());
            }

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void heartbeatAuditSearch() {
        try {
            String terminalId = "TERDXJZ899694";
            List<HeartbeatAudit> HeartbeatAuditList = service.getHeartbeatApi().searchAudits(terminalId, null, null, null, null, null);

            assertNotNull(HeartbeatAuditList);
            assertTrue(HeartbeatAuditList.size() > 0);

            for (HeartbeatAudit temp : HeartbeatAuditList) {
                System.out.println(temp.toString());
            }

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void heartbeatAlertSearch() {
        try {
            String terminalId = "TERDXJZ899694";
            List<HeartbeatAlert> heartbeatAlertList = service.getHeartbeatApi().searchAlerts(terminalId, "VOID", "170101", "190101", 1, 20);

            assertNotNull(heartbeatAlertList);
            assertTrue(heartbeatAlertList.size() > 0);

            for (HeartbeatAlert temp : heartbeatAlertList) {
                System.out.println(temp.toString());
            }

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void heartbeatSearch() {
        try {
            String terminalId = "TERDXJZ899694";
            List<Heartbeat> heartbeatList = service.getHeartbeatApi().search(terminalId, "FD620", "170101", "190101", 1, 20);

            assertNotNull(heartbeatList);
            assertTrue(heartbeatList.size() > 0);

            for (Heartbeat temp : heartbeatList) {
                System.out.println(temp.toString());
            }

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void createheartbeatResponse() {
        try {
            HeartbeatResponse heartbeatResponse = new HeartbeatResponse();

            DownloadInfo downloadInfo = new DownloadInfo();
            downloadInfo.setUsername("asjhflhdfjahdsfj");
            downloadInfo.setFilename("kljadshfjkasdfd");
            downloadInfo.setPassword("56s6f59f64sfsasdkzxfsljadshfjkasdfd");
            downloadInfo.setFilename("5.kdfhj.njfkh.jadshfjkasdfd");
            downloadInfo.setFtpsUrl("downloadcentral.lure68.net:8980");
            downloadInfo.setHttpsUrl("googlo.downloadcentral.lure68.net:8980");
            heartbeatResponse.setDownloadInfo(downloadInfo);

            RKIInfo rkiInfo = new RKIInfo();
            rkiInfo.setKeyType("0");
            rkiInfo.setUrl("rki.lure68.net:8023");
            heartbeatResponse.setRkiInfo(rkiInfo);

            heartbeatResponse.setDebug(true);
            heartbeatResponse.setInterval(new Long(2549825));
            heartbeatResponse.setMessage("test of save a heartbeat response");
            heartbeatResponse.setStatus(0);

            heartbeatResponse = service.getHeartbeatApi().createheartbeatResponse("FD620700000003", heartbeatResponse);

            assertNotNull(heartbeatResponse);
            System.out.println(heartbeatResponse.toString());

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void deleteOdometer() {
        try {
            List<Terminal> terminals = service.getTerminalApi().getTerminals(1, 100);
            for (Terminal temp : terminals) {
                List<HeartbeatOdometer> HeartbeatOdometerList = service.getHeartbeatApi().searchOdometer(temp.getEntityId(), null, null, null, null, null);
                for (HeartbeatOdometer odometer : HeartbeatOdometerList) {
                    service.getHeartbeatApi().deleteOdometer(odometer.getId());
                    break;
                }
                break;
            }
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    //@Test
    public void getSwComponent() {
        try {
            List<Terminal> terminals = service.getTerminalApi().getTerminals(1, 100);
            for (Terminal temp : terminals) {
                /*
                List<HeartbeatAudit> HeartbeatOdometerList = service.getHeartbeatApi().temp.getEntityId(), null, null, null, null, null);
                for (HeartbeatAudit odometer : HeartbeatOdometerList) {
                    service.getHeartbeatApi().deleteOdometer(odometer.getId());
                    break;
                }
                break;
                 */
            }
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void deleteAudits() {
        try {
            Date dt = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.DATE, -10);
            dt = c.getTime();
            service.getHeartbeatApi().deleteAudits(dt);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void deleteAudit() {
        try {
            List<Terminal> terminals = service.getTerminalApi().getTerminals(1, 100);
            for (Terminal temp : terminals) {
                List<HeartbeatAudit> HeartbeatAuditList = service.getHeartbeatApi().searchAudits(temp.getEntityId(), null, null, null, null, null);
                for (HeartbeatAudit audit : HeartbeatAuditList) {
                    service.getHeartbeatApi().deleteAudit(audit.getId());
                    break;
                }
                break;
            }
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }

        try {
            Date dt = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.DATE, -10);
            dt = c.getTime();
            service.getHeartbeatApi().deleteAudits(dt);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    //@Test
    public void deleteAlert() {
        try {
            List<Terminal> terminals = service.getTerminalApi().getTerminals(1, 100);
            for (Terminal temp : terminals) {
                List<HeartbeatAlert> HeartbeatAlertList = service.getHeartbeatApi().searchAlerts(temp.getEntityId(), null, null, null, null, null);
                for (HeartbeatAlert alert : HeartbeatAlertList) {
                    service.getHeartbeatApi().deleteAlert(alert.getId());
                    break;
                }
                break;
            }
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    //@Test(timeout = 0xf4240)
    public void zalertsProcessing() {
        try {
            service.getHeartbeatApi().alertsProcessing();
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }
}
