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
import com.luretechnologies.client.restlib.service.model.Device;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Gemstone
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-09-15T15:20:45.337-04:00")
public class DevicesApi extends BaseApi {

    /**
     * @param apiClient
     */
    public DevicesApi(ApiClient apiClient) {
        super(apiClient);
    }

    /**
     * List devices Lists all devices
     *
     * @param pageNumber
     * @param rowsPerPage
     * @return Device
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<Device> getDevices(Integer pageNumber, Integer rowsPerPage) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        String path = "/devices".replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<List<Device>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Create device Creates a new device
     *
     * @param device
     * @return Device
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Device createDevice(Device device) throws ApiException {
        Object postBody = device;
        byte[] postBinaryBody = null;

        String path = "/devices".replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<Device>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Search devices Search devices that match a given filter
     *
     * @param filter filter
     * @param pageNumber pageNumber
     * @param rowsPerPage rowsPerPage
     * @return Device
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<Device> searchDevices(String filter, Integer pageNumber, Integer rowsPerPage) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (filter == null) {
            throw new ApiException(400, "Missing the required parameter 'filter' when calling searchUsingPOST1");
        }

        String path = "/devices/search".replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<List<Device>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Get device Get device by id
     *
     * @param deviceId
     * @return Device
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Device getDevice(String deviceId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (deviceId == null) {
            throw new ApiException(400, "Missing the required parameter 'deviceId' when calling getUsingGET1");
        }

        String path = "/devices/{deviceId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_DEVICE_ID + "\\}", apiClient.escapeString(deviceId));

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

        TypeRef returnType = new TypeRef<Device>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Update device Updates a device
     *
     * @param deviceId
     * @param device
     * @return Device
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Device updateDevice(String deviceId, Device device) throws ApiException {
        Object postBody = device;
        byte[] postBinaryBody = null;

        if (deviceId == null) {
            throw new ApiException(400, "Missing the required parameter 'deviceId' when calling updateUsingPUT1");
        }

        String path = "/devices/{deviceId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_DEVICE_ID + "\\}", apiClient.escapeString(deviceId));

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

        TypeRef returnType = new TypeRef<Device>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_PUT, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Delete device Deletes a device
     *
     * @param deviceId
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteDevice(String deviceId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (deviceId == null) {
            throw new ApiException(400, "Missing the required parameter 'deviceId' when calling deleteUsingDELETE1");
        }

        String path = "/devices/{deviceId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_DEVICE_ID + "\\}", apiClient.escapeString(deviceId));

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

}
