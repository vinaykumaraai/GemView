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

import com.luretechnologies.client.restlib.common.ApiClient;
import com.luretechnologies.client.restlib.service.api.ActionApi;
import com.luretechnologies.client.restlib.service.api.AddressesApi;
import com.luretechnologies.client.restlib.service.api.AppApi;
import com.luretechnologies.client.restlib.service.api.AppFileApi;
import com.luretechnologies.client.restlib.service.api.AppFileFormatApi;
import com.luretechnologies.client.restlib.service.api.AppParamApi;
import com.luretechnologies.client.restlib.service.api.AppParamFormatApi;
import com.luretechnologies.client.restlib.service.api.AppProfileApi;
import com.luretechnologies.client.restlib.service.api.AuditUserLogApi;
import com.luretechnologies.client.restlib.service.api.AuthApi;
import com.luretechnologies.client.restlib.service.api.ClientsApi;
import com.luretechnologies.client.restlib.service.api.DashboardApi;
import com.luretechnologies.client.restlib.service.api.DevicesApi;
import com.luretechnologies.client.restlib.service.api.EntitiesApi;
import com.luretechnologies.client.restlib.service.api.EntityLevelApi;
import com.luretechnologies.client.restlib.service.api.HeartbeatApi;
import com.luretechnologies.client.restlib.service.api.AlertActionApi;
import com.luretechnologies.client.restlib.service.api.MerchantsApi;
import com.luretechnologies.client.restlib.service.api.ModelApi;
import com.luretechnologies.client.restlib.service.api.OrganizationsApi;
import com.luretechnologies.client.restlib.service.api.RegionsApi;
import com.luretechnologies.client.restlib.service.api.RolesApi;
import com.luretechnologies.client.restlib.service.api.SettingsApi;
import com.luretechnologies.client.restlib.service.api.SystemParamsApi;
import com.luretechnologies.client.restlib.service.api.TelephonesApi;
import com.luretechnologies.client.restlib.service.api.TerminalsApi;
import com.luretechnologies.client.restlib.service.api.TestApi;
import com.luretechnologies.client.restlib.service.api.UsersApi;

public class RestClientService {

    /**
     * @return the systemParamsApi
     */
    public SystemParamsApi getSystemParamsApi() {
        return systemParamsApi;
    }

    private final ApiClient adminApi;
    private final ApiClient tmsApi;

    private final AddressesApi addressApi;
    private final AppApi appApi;
    private final AppFileFormatApi appFileFormatApi;
    private final AppFileApi appFileApi;
    private final AppParamFormatApi appParamFormatApi;
    private final ActionApi actionApi;
    private final AppParamApi appParamApi;
    private final AppProfileApi appProfileApi;
    private final EntityLevelApi entityLevelApi;
    private final AuditUserLogApi auditUserLogApi;
    private final AuthApi authApi;
    private final ClientsApi clientApi;
    private final DashboardApi dashboardApi;
    private final DevicesApi deviceApi;
    private final EntitiesApi entityApi;
    private final MerchantsApi merchantApi;
    private final OrganizationsApi organizationApi;
    private final RegionsApi regionApi;
    private final RolesApi roleApi;
    private final SettingsApi settingApi;
    private final TelephonesApi telephoneApi;
    private final TerminalsApi terminalApi;
    private final TestApi testApi;
    private final UsersApi userApi;
    private final HeartbeatApi heartbeatApi;
    private final AlertActionApi alertActionApi;
    private final SystemParamsApi systemParamsApi;
    private final ModelApi modelApi;
    

    /**
     *
     * @param adminUrl
     * @param tmsUrl
     */
    public RestClientService(String adminUrl, String tmsUrl) {

        adminApi = new ApiClient();
        adminApi.setBasePath(adminUrl);

        addressApi = new AddressesApi(adminApi);
        auditUserLogApi = new AuditUserLogApi(adminApi);
        authApi = new AuthApi(adminApi);
        clientApi = new ClientsApi(adminApi);
        dashboardApi = new DashboardApi(adminApi);
        deviceApi = new DevicesApi(adminApi);
        entityApi = new EntitiesApi(adminApi);
        merchantApi = new MerchantsApi(adminApi);
        organizationApi = new OrganizationsApi(adminApi);
        regionApi = new RegionsApi(adminApi);
        roleApi = new RolesApi(adminApi);
        settingApi = new SettingsApi(adminApi);
        telephoneApi = new TelephonesApi(adminApi);
        terminalApi = new TerminalsApi(adminApi);
        testApi = new TestApi(adminApi);
        userApi = new UsersApi(adminApi);

        tmsApi = new ApiClient();
        tmsApi.setBasePath(tmsUrl);

        appApi = new AppApi(tmsApi);
        appFileFormatApi = new AppFileFormatApi(tmsApi);
        appFileApi = new AppFileApi(tmsApi);
        appParamFormatApi = new AppParamFormatApi(tmsApi);
        actionApi = new ActionApi(tmsApi);
        entityLevelApi = new EntityLevelApi(tmsApi);
        appParamApi = new AppParamApi(tmsApi);
        appProfileApi = new AppProfileApi(tmsApi);
        heartbeatApi = new HeartbeatApi(tmsApi);
        alertActionApi = new AlertActionApi(tmsApi);
        systemParamsApi = new SystemParamsApi(adminApi);
        modelApi = new ModelApi(adminApi);
    }

    public AddressesApi getAddressApi() {
        return addressApi;
    }

    public AppApi getAppApi() {
        return appApi;
    }

    public AuditUserLogApi getAuditUserLogApi() {
        return auditUserLogApi;
    }

    public AuthApi getAuthApi() {
        return authApi;
    }

    public ClientsApi getClientApi() {
        return clientApi;
    }

    public DashboardApi getDashboardApi() {
        return dashboardApi;
    }

    public DevicesApi getDeviceApi() {
        return deviceApi;
    }

    public EntitiesApi getEntityApi() {
        return entityApi;
    }

    public MerchantsApi getMerchantApi() {
        return merchantApi;
    }

    public OrganizationsApi getOrganizationApi() {
        return organizationApi;
    }

    public RegionsApi getRegionApi() {
        return regionApi;
    }

    public RolesApi getRoleApi() {
        return roleApi;
    }

    public SettingsApi getSettingApi() {
        return settingApi;
    }

    public TerminalsApi getTerminalApi() {
        return terminalApi;
    }

    public TestApi getTestApi() {
        return testApi;
    }

    public TelephonesApi getTelephoneApi() {
        return telephoneApi;
    }

    public UsersApi getUserApi() {
        return userApi;
    }

    /**
     * @return the appFileFormatApi
     */
    public AppFileFormatApi getAppFileFormatApi() {
        return appFileFormatApi;
    }

    /**
     * @return the appFileApi
     */
    public AppFileApi getAppFileApi() {
        return appFileApi;
    }

    /**
     * @return the appParamFormatApi
     */
    public AppParamFormatApi getAppParamFormatApi() {
        return appParamFormatApi;
    }

    /**
     * @return the actionApi
     */
    public ActionApi getActionApi() {
        return actionApi;
    }

    /**
     * @return the entityLevelApi
     */
    public EntityLevelApi getEntityLevelApi() {
        return entityLevelApi;
    }

    /**
     * @return the appParamApi
     */
    public AppParamApi getAppParamApi() {
        return appParamApi;
    }

    /**
     * @return the appProfileApi
     */
    public AppProfileApi getAppProfileApi() {
        return appProfileApi;
    }

    /**
     * @return the heartbeatApi
     */
    public HeartbeatApi getHeartbeatApi() {
        return heartbeatApi;
    }

    /**
     *
     * @return
     */
    public AlertActionApi getAlertActionApi() {
        return alertActionApi;
    }

    /**
     * @return the modelApi
     */
    public ModelApi getModelApi() {
        return modelApi;
    }
}
