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
import com.luretechnologies.client.restlib.service.model.Merchant;
import com.luretechnologies.client.restlib.service.model.MerchantHost;
import com.luretechnologies.client.restlib.service.model.MerchantHostModeOperation;
import com.luretechnologies.client.restlib.service.model.MerchantHostSettingValue;
import com.luretechnologies.client.restlib.service.model.MerchantSettingValue;
import com.luretechnologies.common.enums.MerchantSettingEnum;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

/**
 *
 * Gemstone
 */
@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-09-15T15:20:45.337-04:00")
public class MerchantsApi extends BaseApi {

    /**
     * @param apiClient
     */
    public MerchantsApi(ApiClient apiClient) {
        super(apiClient);
    }

    /**
     * List merchants Lists all merchants
     *
     * @param pageNumber
     * @param rowsPerPage
     * @return Merchant
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<Merchant> getMerchants(Integer pageNumber, Integer rowsPerPage) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        String path = "/merchants".replaceAll("\\{format\\}", "json");

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_PAGE_NUMBER, pageNumber));

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_ROWS_PER_PAGE, rowsPerPage));

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<List<Merchant>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Create merchant Creates a new merchant
     *
     * @param merchant
     * @return Merchant
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Merchant createMerchant(Merchant merchant) throws ApiException {
        Object postBody = merchant;
        byte[] postBinaryBody = null;

        String path = "/merchants".replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<Merchant>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Search merchants Search merchants that match a given filter
     *
     * @param filter filter
     * @param pageNumber pageNumber
     * @param rowsPerPage rowsPerPage
     * @return Merchant
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<Merchant> searchMerchants(String filter, Integer pageNumber, Integer rowsPerPage) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (filter == null) {
            throw new ApiException(400, "Missing the required parameter 'filter' when calling searchUsingPOST2");
        }

        String path = "/merchants/search".replaceAll("\\{format\\}", "json");

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_FILTER, filter));

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_PAGE_NUMBER, pageNumber));

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_ROWS_PER_PAGE, rowsPerPage));

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<List<Merchant>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Get merchant Get merchant by id
     *
     * @param merchantId
     * @return Merchant
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Merchant getMerchant(String merchantId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (merchantId == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantId' when calling getUsingGET3");
        }

        String path = "/merchants/{merchantId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_MERCHANT_ID + "\\}", apiClient.escapeString(merchantId));

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

        TypeRef returnType = new TypeRef<Merchant>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Update merchant Updates a merchant
     *
     * @param merchantId
     * @param merchant
     * @return Merchant
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Merchant updateMerchant(String merchantId, Merchant merchant) throws ApiException {
        Object postBody = merchant;
        byte[] postBinaryBody = null;

        if (merchantId == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantId' when calling updateUsingPUT2");
        }

        String path = "/merchants/{merchantId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_MERCHANT_ID + "\\}", apiClient.escapeString(merchantId));

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

        TypeRef returnType = new TypeRef<Merchant>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_PUT, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Delete merchant Deletes a merchant
     *
     * @param merchantId
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteMerchant(String merchantId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (merchantId == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantId' when calling deleteUsingDELETE2");
        }

        String path = "/merchants/{merchantId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_MERCHANT_ID + "\\}", apiClient.escapeString(merchantId));

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

    /**
     * Add host to a merchant
     *
     * @param merchantId Merchant id
     * @param hostId
     * @return Merchant
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public MerchantHost addHost(String merchantId, Long hostId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (merchantId == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantId' when calling addHostUsingPOST");
        }

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'host' when calling addHostUsingPOST");
        }

        String path = "/merchants/{merchantId}/addHost/{hostId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_MERCHANT_ID + "\\}", apiClient.escapeString(merchantId))
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

        TypeRef returnType = new TypeRef<MerchantHost>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Delete host Deletes host
     *
     * @param merchantId Merchant id
     * @param hostId
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteHost(String merchantId, Long hostId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (merchantId == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantId' when calling deleteHostUsingDELETE");
        }

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'host' when calling deleteHostUsingDELETE");
        }

        String path = "/merchants/{merchantId}/host/{hostId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_MERCHANT_ID + "\\}", apiClient.escapeString(merchantId))
                .replaceAll("\\{" + CommonConstants.FIELD_HOST_ID + "\\}", apiClient.escapeString(hostId.toString()));

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

    /**
     * Update host setting Updates merchant host setting
     *
     * @param hostId
     * @param merchantId Merchant id
     * @param merchantHostSettingValue
     * @return Merchant
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Merchant updateHostSetting(Long hostId, String merchantId, MerchantHostSettingValue merchantHostSettingValue) throws ApiException {
        Object postBody = merchantHostSettingValue;
        byte[] postBinaryBody = null;

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'host' when calling updateHostSetting");
        }

        if (merchantId == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantId' when calling updateHostSetting");
        }

        if (merchantHostSettingValue == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantHostSettingValue' when calling updateHostSetting");
        }

        String path = "/merchants/{merchantId}/host/{host}/settings".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_HOST + "\\}", apiClient.escapeString(hostId.toString()))
                .replaceAll("\\{" + CommonConstants.FIELD_MERCHANT_ID + "\\}", apiClient.escapeString(merchantId));

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

        TypeRef returnType = new TypeRef<Merchant>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_PUT, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Add host setting Add merchant host setting
     *
     * @param merchantId Merchant id
     * @param hostId
     * @param merchantHostSettingValue
     * @return Merchant
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Merchant addHostSetting(String merchantId, Long hostId, MerchantHostSettingValue merchantHostSettingValue) throws ApiException {
        Object postBody = merchantHostSettingValue;
        byte[] postBinaryBody = null;

        if (merchantId == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantId' when calling addHostSetting");
        }

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'host' when calling addHostSetting");
        }

        if (merchantHostSettingValue == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantHostSettingValue' when calling addHostSetting");
        }

        String path = "/merchants/{merchantId}/host/{host}/settings".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_MERCHANT_ID + "\\}", apiClient.escapeString(merchantId))
                .replaceAll("\\{" + CommonConstants.FIELD_HOST + "\\}", apiClient.escapeString(hostId.toString()));

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

        TypeRef returnType = new TypeRef<Merchant>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Delete host setting Deletes merchant host setting
     *
     * @param merchantId Merchant id
     * @param hostId HostEnum
     * @param settingId HostEnum Setting
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteHostSetting(String merchantId, Long hostId, Long settingId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (merchantId == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantId' when calling deleteHostSetting");
        }

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'host' when calling deleteHostSetting");
        }

        if (settingId == null) {
            throw new ApiException(400, "Missing the required parameter 'setting' when calling deleteHostSetting");
        }

        String path = "/merchants/{merchantId}/host/{host}/settings/{setting}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_MERCHANT_ID + "\\}", apiClient.escapeString(merchantId))
                .replaceAll("\\{" + CommonConstants.FIELD_HOST + "\\}", apiClient.escapeString(hostId.toString()))
                .replaceAll("\\{" + CommonConstants.FIELD_SETTINGS + "\\}", apiClient.escapeString(settingId.toString()));

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

    /**
     * List available host List available host for a given merchant
     *
     * @param merchantId Merchant id
     * @return String
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<Host> getAvailableHosts(String merchantId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (merchantId == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantId' when calling listAvailableHostsUsingGET");
        }

        String path = "/merchants/{merchantId}/hosts".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_MERCHANT_ID + "\\}", apiClient.escapeString(merchantId));

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
     * List host settings List all merchant host settings for a given host
     *
     * @param merchantId Merchant id
     * @param hostId
     * @return MerchantHost
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<MerchantHostSettingValue> getHostSettings(String merchantId, Long hostId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (merchantId == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantId' when calling listHostSettingsUsingGET");
        }

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'host' when calling listHostSettingsUsingGET");
        }

        String path = "/merchants/{merchantId}/hosts/{host}/settings".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_MERCHANT_ID + "\\}", apiClient.escapeString(merchantId))
                .replaceAll("\\{" + CommonConstants.FIELD_HOST + "\\}", apiClient.escapeString(hostId.toString()));

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

        TypeRef returnType = new TypeRef<List<MerchantHostSettingValue>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Get Host by Mode and Operation Get the host by a given mode and operation
     *
     * @param merchantId Merchant id
     * @param mode Mode
     * @param operation Operation
     * @return String
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public String getHostByModeOperation(String merchantId, String mode, String operation) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (merchantId == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantId' when calling getHostByModeOperationUsingGET");
        }

        if (mode == null) {
            throw new ApiException(400, "Missing the required parameter 'mode' when calling getHostByModeOperationUsingGET");
        }

        if (operation == null) {
            throw new ApiException(400, "Missing the required parameter 'operation' when calling getHostByModeOperationUsingGET");
        }

        String path = "/merchants/{merchantId}/modes/{mode}/operations/{operation}/host".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_MERCHANT_ID + "\\}", apiClient.escapeString(merchantId))
                .replaceAll("\\{" + CommonConstants.FIELD_MODE + "\\}", apiClient.escapeString(mode))
                .replaceAll("\\{" + CommonConstants.FIELD_OPERATION + "\\}", apiClient.escapeString(operation));

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

        TypeRef returnType = new TypeRef<String>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * List available setting List available setting for a given merchant
     *
     * @param merchantId Merchant id
     * @return String
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<MerchantSettingEnum> getAvailableSettings(String merchantId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (merchantId == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantId' when calling listAvailableSettingsUsingGET");
        }

        String path = "/merchants/{merchantId}/settings".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_MERCHANT_ID + "\\}", apiClient.escapeString(merchantId));

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

        TypeRef returnType = new TypeRef<List<MerchantSettingEnum>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Update setting Updates merchant setting value
     *
     * @param merchantId Merchant Id
     * @param merchantSettingValue
     * @return Merchant
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Merchant updateSetting(String merchantId, MerchantSettingValue merchantSettingValue) throws ApiException {
        Object postBody = merchantSettingValue;
        byte[] postBinaryBody = null;

        if (merchantId == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantId' when calling updateSettingUsingPUT1");
        }

        if (merchantSettingValue == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantSettingValue' when calling updateSettingUsingPUT1");
        }

        String path = "/merchants/{merchantId}/settings".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_MERCHANT_ID + "\\}", apiClient.escapeString(merchantId));

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

        TypeRef returnType = new TypeRef<Merchant>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_PUT, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Add setting Add setting to a merchant
     *
     * @param merchantId Merchant Id
     * @param merchantSettingValue
     * @return Merchant
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Merchant addSetting(String merchantId, MerchantSettingValue merchantSettingValue) throws ApiException {
        Object postBody = merchantSettingValue;
        byte[] postBinaryBody = null;

        if (merchantId == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantId' when calling addSettingUsingPOST1");
        }

        if (merchantSettingValue == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantSettingValue' when calling addSettingUsingPOST1");
        }

        String path = "/merchants/{merchantId}/settings".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_MERCHANT_ID + "\\}", apiClient.escapeString(merchantId));

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

        TypeRef returnType = new TypeRef<Merchant>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Delete setting Deletes merchant setting
     *
     * @param merchantId Merchant Id
     * @param merchantSetting Merchant Setting
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteSetting(String merchantId, MerchantSettingEnum merchantSetting) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (merchantId == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantId' when calling deleteSettingUsingDELETE1");
        }

        if (merchantSetting == null) {
            throw new ApiException(400, "Missing the required parameter 'merchantSetting' when calling deleteSettingUsingDELETE1");
        }

        String path = "/merchants/{merchantId}/settings/{merchantSetting}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_MERCHANT_ID + "\\}", apiClient.escapeString(merchantId))
                .replaceAll("\\{" + CommonConstants.FIELD_MERCHANT_SETTINGS + "\\}", apiClient.escapeString(merchantSetting.toString()));

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

    /**
     * List all Host Mode Operations by Merchant id
     *
     * @param merchantId
     *
     * @return
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<MerchantHostModeOperation> getHostModeOperations(String merchantId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (merchantId == null) {
            throw new ApiException(400, "Missing the required parameter 'hostId' when calling listSettingsUsingGET");
        }

        String path = "/merchants/getHostModeOperations/{merchantId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_MERCHANT_ID + "\\}", apiClient.escapeString(merchantId));

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

        TypeRef returnType = new TypeRef<List<MerchantHostModeOperation>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);
    }

    /**
     * Find Host Mode Operations by Merchant id and hostModeOperationId
     *
     * @param merchantId
     * @param hostModeOperationId
     *
     * @return
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public MerchantHostModeOperation findHostModeOperation(String merchantId, Long hostModeOperationId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (merchantId == null) {
            throw new ApiException(400, "Missing the required parameter 'hostId'");
        }
        if (hostModeOperationId == null) {
            throw new ApiException(400, "Missing the required parameter 'hostModeOperationId'");
        }

        String path = "/merchants/findHostModeOperation/{merchantId}/{hostModeOperationId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_MERCHANT_ID + "\\}", apiClient.escapeString(merchantId))
                .replaceAll("\\{hostModeOperationId\\}", apiClient.escapeString(hostModeOperationId.toString()));

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

        TypeRef returnType = new TypeRef<MerchantHostModeOperation>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);
    }
    
    /**
     * Add new Host Mode Operations to a merchant
     *
     * @param merchantId
     * @param hostModeOperationId
     *
     * @return
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public MerchantHostModeOperation addHostModeOperation(String merchantId, Long hostModeOperationId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (merchantId == null) {
            throw new ApiException(400, "Missing the required parameter 'hostId'");
        }
        if (hostModeOperationId == null) {
            throw new ApiException(400, "Missing the required parameter 'hostModeOperationId'");
        }

        String path = "/merchants/{merchantId}/addHostModeOperation/{hostModeOperationId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_MERCHANT_ID + "\\}", apiClient.escapeString(merchantId))
                .replaceAll("\\{hostModeOperationId\\}", apiClient.escapeString(hostModeOperationId.toString()));

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

        TypeRef returnType = new TypeRef<MerchantHostModeOperation>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);
    }
    
    /**
     * Delete Host Mode Operations from a merchant
     *
     * @param merchantId
     * @param hostModeOperationId
     *
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteHostModeOperation(String merchantId, Long hostModeOperationId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (merchantId == null) {
            throw new ApiException(400, "Missing the required parameter 'hostId'");
        }
        if (hostModeOperationId == null) {
            throw new ApiException(400, "Missing the required parameter 'hostModeOperationId'");
        }

        String path = "/merchants/{merchantId}/deleteHostModeOperation/{hostModeOperationId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_MERCHANT_ID + "\\}", apiClient.escapeString(merchantId))
                .replaceAll("\\{hostModeOperationId\\}", apiClient.escapeString(hostModeOperationId.toString()));

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
        
        apiClient.invokeAPI(path, CommonConstants.METHOD_DELETE, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, null);
    }
}
