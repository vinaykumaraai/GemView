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
import com.luretechnologies.client.restlib.service.model.Action;
import com.luretechnologies.client.restlib.service.model.App;
import com.luretechnologies.client.restlib.service.model.AppParam;
import com.luretechnologies.client.restlib.service.model.AppParamFormat;
import com.luretechnologies.client.restlib.service.model.AppProfile;
import com.luretechnologies.client.restlib.service.model.AppProfileParamValue;
import com.luretechnologies.client.restlib.service.model.EntityAppProfile;
import com.luretechnologies.client.restlib.service.model.EntityAppProfileParam;
import com.luretechnologies.client.restlib.service.model.EntityLevel;
import com.luretechnologies.client.restlib.service.model.User;
import com.luretechnologies.client.restlib.service.model.UserSession;
import static org.junit.Assert.assertEquals;
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
public class AppTest {

    private static RestClientService service;
    private static UserSession userSession;
    private static Long appId;

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
    public void app_001a_searchApps() {
        try {
            List<App> apps = service.getAppApi().searchApps("Test", 1, 50);
            for (App app : apps) {
                System.out.println("App Search: " + app.toString());
            }
            assertNotNull(apps);

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001b_getApps() {
        try {
            List<App> apps = service.getAppApi().getApps();
            for (App app : apps) {
                System.out.println("Get Apps: " + app.toString());
            }
            assertNotNull(apps);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001c_getApp() {
        try {
            List<App> apps = service.getAppApi().getApps();
            assertNotNull(apps);
            if (!apps.isEmpty()) {
                App app = service.getAppApi().getApp(apps.get(0).getId());
                System.out.println("Get App: " + app.toString());
            }
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001d_createApp() {
        try {
            App app = new App();
            app.setName("TestAppLast");
            app.setDescription("TestAppLast Description");
            app.setVersion("00.00.01");
            app.setActive(Boolean.TRUE);
            app.setAvailable(Boolean.TRUE);

            User user = service.getUserApi().getUserByUserName(CommonConstants.testStandardUsername);
            app.setOwnerId(user.getEntity().getId());
            app = service.getAppApi().createApp(app);
            appId = app.getId();
            System.out.println("Create App: " + app.toString());

            assertNotNull(app.getId());
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001e_editApp() {
        try {
            App app = service.getAppApi().getApp(appId);
            app.setDescription("Updated");
            app = service.getAppApi().updateApp(app.getId(), app);
            System.out.println("Updated App: " + app.toString());
            assertEquals("Updated", app.getDescription());
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001f_getAppsByEntityHierarchy() {
        try {
            Long entityId = service.getAppApi().getApp(appId).getOwnerId();
            List<App> apps = service.getAppApi().getAppsByEntityHierarchy(entityId);
            for (App app : apps) {
                System.out.println("Get Apps by Entity Hierarchy: " + app.toString());
            }
            assertNotNull(apps);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001g_addAppParam() {
        try {
            AppParam appParam = new AppParam();
            appParam.setName("TestAppParam");
            appParam.setDescription("TestAppParam Description");
            appParam.setDefaultValue("defaultValue");
            appParam.setAppId(appId);
            appParam.setForceUpdate(Boolean.FALSE);
            appParam.setModifiable(Boolean.TRUE);

            AppParamFormat appParamFormat = service.getAppParamFormatApi().getAppParamFormat(Long.valueOf(1));
            appParam.setAppParamFormat(appParamFormat);

            Action action = service.getActionApi().getAction(Long.valueOf(1));
            appParam.setAction(action);

            EntityLevel entityLevel = new EntityLevel();
            entityLevel.setId(Long.valueOf(1));
            entityLevel.setName("EntityLevel1");
            appParam.setEntityLevel(entityLevel);

            App app = service.getAppApi().addAppParam(appId, appParam);
            System.out.println("Add AppParam: " + appParam.toString());

            assertNotNull(app.getId());
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }
    
    @Test
    public void app_001g_searchAppParam() {
        try {
            List<AppParam> appParams = service.getAppApi().searchAppParam(appId, "Test", 1, 50);
            for (AppParam appParam : appParams) {
                System.out.println("AppParam Search: " + appParam.toString());
            }
            assertNotNull(appParams);

        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001h_editAppParam() {
        try {
            AppParam appParam = service.getAppApi().getAppParamList(appId).get(0);
            appParam.setDescription("Updated");
            service.getAppApi().updateAppParam(appId, appParam);

            System.out.println("Update AppParam: " + appParam.toString());
            assertEquals("Updated", appParam.getDescription());
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001i_addAppProfile() {
        try {
            AppProfile appProfile = new AppProfile();
            appProfile.setName("TestAppProfile");
            appProfile.setAppId(appId);
            appProfile.setActive(Boolean.TRUE);

            App app = service.getAppApi().addAppProfile(appId, appProfile);
            System.out.println("Add AppProfile: " + app.toString());

            assertNotNull(app.getId());
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001j_addAppProfileParamValue() {
        try {
            Long appProfileId = service.getAppApi().getApp(appId).getAppprofileCollection().get(0).getId();
            Long appParamId = service.getAppApi().getApp(appId).getAppparamCollection().get(0).getId();

            AppProfileParamValue appProfileParamValue = service.getAppProfileApi().addAppProfileParamValue(appProfileId, appParamId);
            System.out.println("Add AppProfileParamValue: " + appProfileParamValue.toString());

            assertNotNull(appProfileParamValue.getId());
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001k_editAppProfileParamValue() {
        try {
            Long appProfileId = service.getAppApi().getApp(appId).getAppprofileCollection().get(0).getId();
            AppProfile appProfile = service.getAppProfileApi().getAppProfile(appProfileId);
            AppProfileParamValue existentAppProfileParamValue = appProfile.getAppProfileParamValueCollection().get(0);
            existentAppProfileParamValue.setDefaultValue("Updated");

            AppProfileParamValue appProfileParamValue = service.getAppProfileApi().updateAppProfileParamValue(appProfileId, existentAppProfileParamValue);
            System.out.println("Update AppProfileParamValue: " + appProfileParamValue.toString());
            assertEquals("Updated", appProfileParamValue.getDefaultValue());
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001l_addEntityAppProfile() {
        try {
            Long appProfileId = service.getAppApi().getApp(appId).getAppprofileCollection().get(0).getId();
            Long entityId = service.getAppApi().getApp(appId).getOwnerId();

            EntityAppProfile entityAppProfile = service.getAppProfileApi().addEntityAppProfile(appProfileId, entityId);
            System.out.println("Add EntityAppProfile: " + entityAppProfile.toString());

            assertNotNull(entityAppProfile.getId());
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001m_editEntityAppProfile() {
        try {
            Long appProfileId = service.getAppApi().getApp(appId).getAppprofileCollection().get(0).getId();
            AppProfile appProfile = service.getAppProfileApi().getAppProfile(appProfileId);
            EntityAppProfile existentEntityAppProfile = appProfile.getEntityAppProfileCollection().get(0);

            EntityAppProfile entityAppProfile = service.getAppProfileApi().updateEntityAppProfile(appProfileId, existentEntityAppProfile);
            System.out.println("Update EntityAppProfile: " + entityAppProfile.toString());
            assertEquals(appProfileId, entityAppProfile.getAppProfileId());
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001n_addEntityAppProfileParam() {
        try {
            Long appProfileId = service.getAppApi().getApp(appId).getAppprofileCollection().get(0).getId();
            Long entityId = service.getAppApi().getApp(appId).getOwnerId();
            Long appParamId = service.getAppApi().getApp(appId).getAppparamCollection().get(0).getId();

            EntityAppProfileParam entityAppProfileParam = service.getAppProfileApi().addEntityAppProfileParam(appProfileId, entityId, appParamId);
            System.out.println("Add EntityAppProfileParam: " + entityAppProfileParam.toString());

            assertNotNull(entityAppProfileParam.getId());
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001o_editEntityAppProfileParam() {
        try {
            Long appProfileId = service.getAppApi().getApp(appId).getAppprofileCollection().get(0).getId();
            Long entityId = service.getAppApi().getApp(appId).getOwnerId();
            AppProfile appProfile = service.getAppProfileApi().getAppProfile(appProfileId);
            EntityAppProfile entityAppProfile = appProfile.getEntityAppProfileCollection().get(0);
            EntityAppProfileParam existentEntityAppProfileParam = entityAppProfile.getEntityappprofileparamCollection().get(0);
            existentEntityAppProfileParam.setValue("Updated");

            EntityAppProfileParam entityAppProfileParam = service.getAppProfileApi().updateEntityAppProfileParam(appProfileId, entityId, existentEntityAppProfileParam);
            System.out.println("Update EntityAppProfileParam: " + entityAppProfileParam.toString());
            assertEquals("Updated", entityAppProfileParam.getValue());
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001p_getAppParamListByAppProfile() {
        try {
            Long appProfileId = service.getAppApi().getApp(appId).getAppprofileCollection().get(0).getId();
            List<AppParam> appParams = service.getAppProfileApi().getAppParamListByAppProfile(appProfileId);
            for (AppParam appParam : appParams) {
                System.out.println("Get AppParams by AppProfile: " + appParam.toString());
            }
            assertNotNull(appParams);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001q_getAppParamListWithoutAppProfile() {
        try {
            Long appProfileId = service.getAppApi().getApp(appId).getAppprofileCollection().get(0).getId();
            List<AppParam> appParams = service.getAppProfileApi().getAppParamListWithoutAppProfile(appProfileId);
            for (AppParam appParam : appParams) {
                System.out.println("Get AppParams without AppProfile: " + appParam.toString());
            }
            assertNotNull(appParams);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001r_getAppProfileListByEntity() {
        try {
            Long entityId = service.getAppApi().getApp(appId).getOwnerId();
            List<AppProfile> appProfiles = service.getAppProfileApi().getAppProfileListByEntity(appId, entityId);
            for (AppProfile appProfile : appProfiles) {
                System.out.println("Get AppProfiles by Entity: " + appProfile.toString());
            }
            assertNotNull(appProfiles);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001s_getAppProfileListWithoutEntity() {
        try {
            Long entityId = service.getAppApi().getApp(appId).getOwnerId();
            List<AppProfile> appProfiles = service.getAppProfileApi().getAppProfileListWithoutEntity(appId, entityId);
            for (AppProfile appProfile : appProfiles) {
                System.out.println("Get AppProfiles without Entity: " + appProfile.toString());
            }
            assertNotNull(appProfiles);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001t_getAppParamListByEntity() {
        try {
            Long entityId = service.getAppApi().getApp(appId).getOwnerId();
            Long appProfileId = service.getAppApi().getApp(appId).getAppprofileCollection().get(0).getId();
            List<AppParam> appParams = service.getAppProfileApi().getAppParamListByEntity(appProfileId, entityId);
            for (AppParam appParam : appParams) {
                System.out.println("Get AppParam by Entity: " + appParam.toString());
            }
            assertNotNull(appParams);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001u_getAppParamListWithoutEntity() {
        try {
            Long entityId = service.getAppApi().getApp(appId).getOwnerId();
            Long appProfileId = service.getAppApi().getApp(appId).getAppprofileCollection().get(0).getId();
            List<AppParam> appParams = service.getAppProfileApi().getAppParamListWithoutEntity(appProfileId, entityId);
            for (AppParam appParam : appParams) {
                System.out.println("Get AppParam without Entity: " + appParam.toString());
            }
            assertNotNull(appParams);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001v_removeEntityAppProfileParam() {
        try {
            Long appProfileId = service.getAppApi().getApp(appId).getAppprofileCollection().get(0).getId();
            Long entityId = service.getAppApi().getApp(appId).getOwnerId();
            Long appParamId = service.getAppApi().getApp(appId).getAppparamCollection().get(0).getId();

            service.getAppProfileApi().deleteEntityAppProfileParam(appProfileId, entityId, appParamId);
            System.out.println("Deleted EntityAppProfileParam successful");
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001w_removeEntityAppProfile() {
        try {
            Long appProfileId = service.getAppApi().getApp(appId).getAppprofileCollection().get(0).getId();
            Long entityId = service.getAppApi().getApp(appId).getOwnerId();

            service.getAppProfileApi().deleteEntityAppProfile(appProfileId, entityId);
            System.out.println("Deleted EntityAppProfile successful");
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001x_removeAppProfileParamValue() {
        try {
            Long appProfileId = service.getAppApi().getApp(appId).getAppprofileCollection().get(0).getId();
            Long appParamId = service.getAppApi().getApp(appId).getAppparamCollection().get(0).getId();

            service.getAppProfileApi().deleteAppProfileParamValue(appProfileId, appParamId);
            System.out.println("Deleted AppProfileParamValue successful");
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001y_removeAppProfile() {
        try {
            Long appProfileId = service.getAppApi().getAppProfileList(appId).get(0).getId();

            service.getAppApi().deleteAppProfile(appId, appProfileId);
            System.out.println("Deleted appProfile by App successful");
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_001z_removeAppParam() {
        try {
            Long appParamId = service.getAppApi().getAppParamList(appId).get(0).getId();

            service.getAppApi().deleteAppParam(appId, appParamId);
            System.out.println("Deleted appParam by App successful");
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_002a_removeAllAppParam() {
        try {
            service.getAppApi().deleteAllAppParam(appId);
            System.out.println("Deleted all appParams by App successful");
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_002b_addAppFile() {

        String fileName = "/tmp/zip.zip";
        try {
            service.getAppApi().addFile(appId, Utils.generateRandomString(10), fileName);
            System.out.println("Add AppFile by App successful");
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_002c_removeAppFile() {
        try {
            Long appFileId = service.getAppApi().getAppFileList(appId).get(0).getId();
            service.getAppApi().deleteAppFile(appId, appFileId);
            System.out.println("Deleted appFile by App successful");
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_002d_removeAllAppFile() {
        try {
            service.getAppApi().deleteAllAppFile(appId);
            System.out.println("Deleted all appFiles by App successful");
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }

    @Test
    public void app_002e_removeApp() {
        try {
            App app = service.getAppApi().getApp(appId);
            service.getAppApi().deleteApp(app.getId());
            System.out.println("Deleted app successful");
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }
}
