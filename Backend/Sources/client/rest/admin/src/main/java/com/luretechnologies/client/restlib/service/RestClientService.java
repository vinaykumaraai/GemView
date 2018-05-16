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
import com.luretechnologies.client.restlib.service.api.AddressesApi;
import com.luretechnologies.client.restlib.service.api.AuthApi;
import com.luretechnologies.client.restlib.service.api.ClientsApi;
import com.luretechnologies.client.restlib.service.api.DashboardApi;
import com.luretechnologies.client.restlib.service.api.DevicesApi;
import com.luretechnologies.client.restlib.service.api.EntitiesApi;
import com.luretechnologies.client.restlib.service.api.HostsApi;
import com.luretechnologies.client.restlib.service.api.MerchantsApi;
import com.luretechnologies.client.restlib.service.api.OrganizationsApi;
import com.luretechnologies.client.restlib.service.api.RegionsApi;
import com.luretechnologies.client.restlib.service.api.RolesApi;
import com.luretechnologies.client.restlib.service.api.SettingsApi;
import com.luretechnologies.client.restlib.service.api.TelephonesApi;
import com.luretechnologies.client.restlib.service.api.TerminalsApi;
import com.luretechnologies.client.restlib.service.api.UsersApi;

public class RestClientService {

    private final ApiClient adminApi;
    private final ApiClient tmsApi;

    private final AddressesApi addressApi;
    private final AuthApi authApi;
    private final ClientsApi clientApi;
    private final DashboardApi dashboardApi;
    private final DevicesApi deviceApi;
    private final EntitiesApi entityApi;
    private final HostsApi hostApi;
    private final MerchantsApi merchantApi;
    private final OrganizationsApi organizationApi;
    private final RegionsApi regionApi;
    private final RolesApi roleApi;
    private final SettingsApi settingApi;
    private final TelephonesApi telephoneApi;
    private final TerminalsApi terminalApi;
    private final UsersApi userApi;

    /**
     *
     * @param adminUrl
     * @param tmsUrl
     */
    public RestClientService(String adminUrl, String tmsUrl) {

        adminApi = new ApiClient();
        adminApi.setBasePath(adminUrl);

        addressApi = new AddressesApi(adminApi);
        authApi = new AuthApi(adminApi);
        clientApi = new ClientsApi(adminApi);
        dashboardApi = new DashboardApi(adminApi);
        deviceApi = new DevicesApi(adminApi);
        entityApi = new EntitiesApi(adminApi);
        hostApi = new HostsApi(adminApi);
        merchantApi = new MerchantsApi(adminApi);
        organizationApi = new OrganizationsApi(adminApi);
        regionApi = new RegionsApi(adminApi);
        roleApi = new RolesApi(adminApi);
        settingApi = new SettingsApi(adminApi);
        telephoneApi = new TelephonesApi(adminApi);
        terminalApi = new TerminalsApi(adminApi);
        userApi = new UsersApi(adminApi);

        tmsApi = new ApiClient();
        tmsApi.setBasePath(tmsUrl);
    }

    public AddressesApi getAddressApi() {
        return addressApi;
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

    public HostsApi getHostApi() {
        return hostApi;
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

    public TelephonesApi getTelephoneApi() {
        return telephoneApi;
    }

    public UsersApi getUserApi() {
        return userApi;
    }
}
