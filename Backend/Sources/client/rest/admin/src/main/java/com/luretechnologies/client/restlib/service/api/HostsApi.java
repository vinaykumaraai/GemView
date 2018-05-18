/**
 * COPYRIGHT @ Lure Technologies, LLC. ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or form
 * other than in accordance with and subject to the terms of a written license
 * from Lure or with the prior written consent of Lure or as permitted by
 * applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure. If you are neither the intended
 * recipient, nor an agent, employee, nor independent contractor responsible for
 * delivering this message to the intended recipient, you are prohibited from
 * copying, disclosing, distributing, disseminating, and/or using the
 * information in this email in any manner. If you have received this message in
 * error, please advise us immediately at legal@luretechnologies.com by return
 * email and then delete the message from your computer and all other records
 * (whether electronic, hard copy, or otherwise).
 *
 * Any copies or reproductions of this software program (in whole or in part)
 * made by any method must also include a copy of this legend.
 *
 * Inquiries should be made to legal@luretechnologies.com
 *
 */
package com.luretechnologies.client.restlib.service.api;

import com.luretechnologies.client.restlib.common.ApiClient;
import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.common.CommonConstants;
import com.luretechnologies.client.restlib.common.Pair;
import com.luretechnologies.client.restlib.common.TypeRef;
import com.luretechnologies.client.restlib.service.model.Host;
import com.luretechnologies.client.restlib.service.model.HostModeOperation;
import com.luretechnologies.client.restlib.service.model.HostSettingValue;
import com.luretechnologies.client.restlib.service.model.MerchantHostSetting;
import com.luretechnologies.client.restlib.service.model.TerminalHostSetting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author developer
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-11-18T10:00:02.092-05:00")
public class HostsApi extends BaseApi {

    /**
     * @param apiClient
     */
    public HostsApi(ApiClient apiClient) {
        super(apiClient);
    }

    /**
     * Get Host by key.
     *
     * @param key
     * @return Host
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Host getHostByKey(String key) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (key == null) {
            throw new ApiException(400, "Missing the required parameter 'key'");
        }

        String path = "/getHostByKey/{key}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_KEY_ID + "\\}", apiClient.escapeString(key));

        // query params
        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<Host>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * List hosts.
     *
     * @return String
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<Host> getHosts() throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        String path = "/hosts".replaceAll("\\{format\\}", "json");

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<List<Host>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Update host values
     *
     * @param hostId
     * @param host
     *
     * @return Host
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Host updateHost(Long hostId, Host host) throws ApiException {
        Object postBody = host;
        byte[] postBinaryBody = null;

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'hostId'");
        }

        if (host == null) {
            throw new ApiException(400, "Missing the required parameter 'host'");
        }

        String path = "/hosts/{hostId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_HOST_ID + "\\}", apiClient.escapeString(hostId.toString()));

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<Host>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_PUT, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    // <editor-fold desc="Settings" defaultstate="collapsed">
    /**
     * List settings pertaining to a host
     *
     * @param hostId HostEnum
     * @return HostSettingValue
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<HostSettingValue> getSettingValues(Long hostId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'hostId'");
        }

        String path = "/hosts/{hostId}/settingValues".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_HOST_ID + "\\}", apiClient.escapeString(hostId.toString()));

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<List<HostSettingValue>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Add setting to a host
     *
     * @param hostId
     * @param hostSettingValue
     * @return HostSettingValue
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public HostSettingValue addSetting(Long hostId, HostSettingValue hostSettingValue) throws ApiException {
        Object postBody = hostSettingValue;
        byte[] postBinaryBody = null;

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'host'");
        }

        if (hostSettingValue == null) {
            throw new ApiException(400, "Missing the required parameter 'hostSettingValue'");
        }

        String path = "/hosts/{hostId}/settings".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_HOST_ID + "\\}", apiClient.escapeString(hostId.toString()));

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<HostSettingValue>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Update host setting values
     *
     * @param hostId HostEnum
     * @param hostSettingValue
     * @return HostSettingValue
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public HostSettingValue updateSetting(Long hostId, HostSettingValue hostSettingValue) throws ApiException {
        Object postBody = hostSettingValue;
        byte[] postBinaryBody = null;

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'host' when calling updateSettingUsingPUT");
        }

        if (hostSettingValue == null) {
            throw new ApiException(400, "Missing the required parameter 'hostSettingValue' when calling updateSettingUsingPUT");
        }

        String path = "/hosts/{hostId}/settings".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_HOST_ID + "\\}", apiClient.escapeString(hostId.toString()));

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<HostSettingValue>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_PUT, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Deletes host setting values
     *
     * @param hostId HostEnum
     * @param key
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteSetting(Long hostId, String key) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'host' when calling deleteSettingUsingDELETE");
        }

        if (key == null) {
            throw new ApiException(400, "Missing the required parameter 'hostSetting' when calling deleteSettingUsingDELETE");
        }

        String path = "/hosts/{hostId}/settings/{key}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_HOST_ID + "\\}", apiClient.escapeString(hostId.toString()))
                .replaceAll("\\{" + CommonConstants.FIELD_KEY + "\\}", apiClient.escapeString(key));

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_ALL
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        apiClient.invokeAPI(path, CommonConstants.METHOD_DELETE, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, null);

    }

    // </editor-fold>
    // <editor-fold desc="Merchant settings" defaultstate="collapsed">
    /**
     * List merchant settings pertaining to a host
     *
     * @param hostId
     * @return
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<MerchantHostSetting> getMerchantSettings(Long hostId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'hostId'");
        }

        String path = "/hosts/{hostId}/merchantSettings".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_HOST_ID + "\\}", apiClient.escapeString(hostId.toString()));

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<List<MerchantHostSetting>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Get merchant setting by key.
     *
     * @param hostId
     * @param key
     *
     * @return MerchantHostSetting
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public MerchantHostSetting getMerchantSetting(Long hostId, String key) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'hostId'");
        }

        if (key == null) {
            throw new ApiException(400, "Missing the required parameter 'key'");
        }

        String path = "/hosts/{hostId}/getMerchantSetting/{key}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_HOST_ID + "\\}", apiClient.escapeString(hostId.toString()))
                .replaceAll("\\{" + CommonConstants.FIELD_KEY_ID + "\\}", apiClient.escapeString(key));

        // query params
        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<MerchantHostSetting>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Add merchant setting to a host
     *
     * @param hostId
     * @param setting
     * @return HostSettingValue
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public MerchantHostSetting addMerchantSetting(Long hostId, MerchantHostSetting setting) throws ApiException {
        Object postBody = setting;
        byte[] postBinaryBody = null;

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'host'");
        }

        if (setting == null) {
            throw new ApiException(400, "Missing the required parameter 'setting'");
        }

        String path = "/hosts/{hostId}/addMerchantSetting".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_HOST_ID + "\\}", apiClient.escapeString(hostId.toString()));

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<MerchantHostSetting>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Update merchant setting values
     *
     * @param hostId
     * @param setting
     * @return HostSettingValue
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public MerchantHostSetting updateMerchantSetting(Long hostId, MerchantHostSetting setting) throws ApiException {
        Object postBody = setting;
        byte[] postBinaryBody = null;

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'host'");
        }

        if (setting == null) {
            throw new ApiException(400, "Missing the required parameter 'setting'");
        }

        String path = "/hosts/{hostId}/updateMerchantSetting".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_HOST_ID + "\\}", apiClient.escapeString(hostId.toString()));

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<MerchantHostSetting>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_PUT, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Deletes merchant setting
     *
     * @param merchantHostSettingId
     *
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteMerchantSetting(Long merchantHostSettingId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (merchantHostSettingId == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantHostSettingId'");
        }

        String path = "/hosts/deleteMerchantSetting/{merchantHostSettingId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{merchantHostSettingId\\}", apiClient.escapeString(merchantHostSettingId.toString()));

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_ALL
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        apiClient.invokeAPI(path, CommonConstants.METHOD_DELETE, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, null);

    }

    // </editor-fold>
    // <editor-fold desc="Terminal settings" defaultstate="collapsed">
    /**
     * List terminal settings pertaining to a host
     *
     * @param hostId
     * @return
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<TerminalHostSetting> getTerminalSettings(Long hostId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'hostId'");
        }

        String path = "/hosts/{hostId}/terminalSettings".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_HOST_ID + "\\}", apiClient.escapeString(hostId.toString()));

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<List<TerminalHostSetting>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Get terminal setting by key.
     *
     * @param hostId
     * @param key
     *
     * @return
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public TerminalHostSetting getTerminalSetting(Long hostId, String key) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'hostId'");
        }

        if (key == null) {
            throw new ApiException(400, "Missing the required parameter 'key'");
        }

        String path = "/hosts/{hostId}/getTerminalSetting/{key}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_HOST_ID + "\\}", apiClient.escapeString(hostId.toString()))
                .replaceAll("\\{" + CommonConstants.FIELD_KEY_ID + "\\}", apiClient.escapeString(key));

        // query params
        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<TerminalHostSetting>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Add terminal setting to a host
     *
     * @param hostId
     * @param setting
     *
     * @return
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public TerminalHostSetting addTerminalSetting(Long hostId, TerminalHostSetting setting) throws ApiException {
        Object postBody = setting;
        byte[] postBinaryBody = null;

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'host'");
        }

        if (setting == null) {
            throw new ApiException(400, "Missing the required parameter 'setting'");
        }

        String path = "/hosts/{hostId}/addTerminalSetting".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_HOST_ID + "\\}", apiClient.escapeString(hostId.toString()));

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<TerminalHostSetting>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Update merchant setting values
     *
     * @param hostId
     * @param setting
     *
     * @return
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public TerminalHostSetting updateTerminalSetting(Long hostId, TerminalHostSetting setting) throws ApiException {
        Object postBody = setting;
        byte[] postBinaryBody = null;

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'host'");
        }

        if (setting == null) {
            throw new ApiException(400, "Missing the required parameter 'setting'");
        }

        String path = "/hosts/{hostId}/updateTerminalSetting".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_HOST_ID + "\\}", apiClient.escapeString(hostId.toString()));

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<TerminalHostSetting>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_PUT, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Deletes terminal setting
     *
     * @param terminalHostSettingId
     *
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteTerminalSetting(Long terminalHostSettingId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (terminalHostSettingId == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantHostSettingId'");
        }

        String path = "/hosts/deleteTerminalSetting/{terminalHostSettingId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{terminalHostSettingId\\}", apiClient.escapeString(terminalHostSettingId.toString()));

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_ALL
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        apiClient.invokeAPI(path, CommonConstants.METHOD_DELETE, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, null);

    }

    // </editor-fold>
    // <editor-fold desc="Modes and Operations" defaultstate="collapsed">
    /**
     * List all Mode Operations by Host id
     *
     * @param hostId
     * @return HostSettingValue
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<HostModeOperation> getModeOperations(Long hostId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'hostId' when calling listSettingsUsingGET");
        }

        String path = "/hosts/getModeOperations/{hostId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_HOST_ID + "\\}", apiClient.escapeString(hostId.toString()));

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<List<HostModeOperation>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Add host mode operation
     *
     * @param hostId
     * @param setting
     *
     * @return
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public HostModeOperation addModeOperation(Long hostId, HostModeOperation setting) throws ApiException {
        Object postBody = setting;
        byte[] postBinaryBody = null;

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'host'");
        }

        if (setting == null) {
            throw new ApiException(400, "Missing the required parameter 'setting'");
        }

        String path = "/hosts/{hostId}/addModeOperation".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_HOST_ID + "\\}", apiClient.escapeString(hostId.toString()));

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<HostModeOperation>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Update host mode operation
     *
     * @param hostId
     * @param setting
     *
     * @return
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public HostModeOperation updateModeOperation(Long hostId, HostModeOperation setting) throws ApiException {
        Object postBody = setting;
        byte[] postBinaryBody = null;

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'host'");
        }

        if (setting == null) {
            throw new ApiException(400, "Missing the required parameter 'setting'");
        }

        String path = "/hosts/{hostId}/updateModeOperation".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_HOST_ID + "\\}", apiClient.escapeString(hostId.toString()));

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<HostModeOperation>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_PUT, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Deletes host mode operation
     *
     * @param modeOperId
     *
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteModeOperation(Long modeOperId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;
        if (modeOperId == null) {
            throw new ApiException(400, "Missing the required parameter 'modeOperationId'");
        }
        String path = "/hosts/deleteModeOperation/{modeOperId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{modeOperId\\}", apiClient.escapeString(modeOperId.toString()));
        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_ALL
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);
        apiClient.invokeAPI(path, CommonConstants.METHOD_DELETE, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, null);
    }

    // </editor-fold>
}
