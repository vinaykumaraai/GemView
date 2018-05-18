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
import com.luretechnologies.client.restlib.service.model.Terminal;
import com.luretechnologies.client.restlib.service.model.TerminalHost;
import com.luretechnologies.client.restlib.service.model.TerminalHostSettingValue;
import com.luretechnologies.client.restlib.service.model.TerminalSettingValue;
import com.luretechnologies.common.enums.TerminalSettingEnum;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Gemstone
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-09-15T15:20:45.337-04:00")
public class TerminalsApi extends BaseApi {

    /**
     *
     * @param apiClient
     */
    public TerminalsApi(ApiClient apiClient) {
        super(apiClient);
    }

//    /**
//     * List terminals Lists all terminals
//     *
//     * @param name Terminal name
//     * @param serialnumber Terminal serial number
//     * @param active Terminal status
//     * @return Terminal
//     * @throws com.luretechnologies.client.restlib.generated.ApiException
//     */
//    public List<Terminal> list(String name, String serialnumber, Boolean active) throws ApiException {
//        Object postBody = null;
//        byte[] postBinaryBody = null;
//
//        // create path and map variables
//        String path = "/terminals".replaceAll("\\{format\\}", "json");
//
//        // query params
//        List<Pair> queryParams = new ArrayList<>();
//        Map<String, String> headerParams = new HashMap<>();
//        Map<String, Object> formParams = new HashMap<>();
//
//        queryParams.addAll(apiClient.parameterToPairs("", "name", name));
//
//        queryParams.addAll(apiClient.parameterToPairs("", "serialnumber", serialnumber));
//
//        queryParams.addAll(apiClient.parameterToPairs("", "active", active));
//
//        final String[] accepts = {
//            CommonConstants.HEADER_APP_JSON
//        };
//        final String accept = apiClient.selectHeaderAccept(accepts);
//
//        final String[] contentTypes = {
//            CommonConstants.HEADER_APP_JSON
//        };
//        final String contentType = apiClient.selectHeaderContentType(contentTypes);
//
//        
//
//        TypeRef returnType = new TypeRef<List<Terminal>>() {
//        };
//        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);
//
//    }
    /**
     * Lists terminals. Will return 50 records if no paging parameters defined
     *
     * @param pageNumber Page number
     * @param rowsPerPage Rows per page
     * @return Terminals list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<Terminal> getTerminals(Integer pageNumber, Integer rowsPerPage) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        String path = "/terminals".replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<List<Terminal>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Create terminal Creates a new terminal
     *
     * @param terminal
     * @return Terminal
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Terminal createTerminal(Terminal terminal) throws ApiException {
        Object postBody = terminal;
        byte[] postBinaryBody = null;

        if (terminal == null) {
            throw new ApiException(400, "Missing the required parameter 'terminal' when calling createUsingPOST7");
        }

        String path = "/terminals".replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<Terminal>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Search terminals Search terminals that match a given filter. Will return
     * 50 records if no paging parameters defined
     *
     * @param filter filter
     * @param pageNumber Page number
     * @param rowsPerPage Rows per page
     * @return Terminals list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<Terminal> searchTerminals(String filter, Integer pageNumber, Integer rowsPerPage) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (filter == null) {
            throw new ApiException(400, "Missing the required parameter 'filter' when calling searchUsingPOST6");
        }

        String path = "/terminals/search".replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<List<Terminal>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Get terminal Get terminal by serial number
     *
     * @param terminalId Terminal serial number
     *
     * @return Terminal
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Terminal getTerminal(String terminalId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'serialNumber' is set
        if (terminalId == null) {
            throw new ApiException(400, "Missing the required parameter 'serialNumber' when calling getUsingGET8");
        }

        // create path and map variables
        String path = "/terminals/{terminalId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "terminalId" + "\\}", apiClient.escapeString(terminalId));

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

        TypeRef returnType = new TypeRef<Terminal>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Update terminal Updates a terminal
     *
     * @param terminalId Terminal identifier
     * @param terminal Terminal object
     * @return Terminal
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Terminal updateTerminal(String terminalId, Terminal terminal) throws ApiException {
        Object postBody = terminal;
        byte[] postBinaryBody = null;

        // verify the required parameter 'serialNumber' is set
        if (terminalId == null) {
            throw new ApiException(400, "Missing the required parameter 'serialNumber' when calling updateUsingPUT7");
        }

        // verify the required parameter 'terminal' is set
        if (terminal == null) {
            throw new ApiException(400, "Missing the required parameter 'terminal' when calling updateUsingPUT7");
        }

        // create path and map variables
        String path = "/terminals/{terminalId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_TERMINAL_ID + "\\}", apiClient.escapeString(terminalId));

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

        TypeRef returnType = new TypeRef<Terminal>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_PUT, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Delete terminal Deletes a terminal
     *
     * @param serialNumber Terminal serial number
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteTerminal(String serialNumber) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (serialNumber == null) {
            throw new ApiException(400, "Missing the required parameter 'serialNumber' when calling deleteUsingDELETE7");
        }

        String path = "/terminals/{serialNumber}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "serialNumber" + "\\}", apiClient.escapeString(serialNumber));

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
     * Add host Add host to a terminal
     *
     * @param terminalId Terminal id
     * @param hostId
     * @return Terminal
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public TerminalHost addHost(String terminalId, Long hostId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'terminalId' is set
        if (terminalId == null) {
            throw new ApiException(400, "Missing the required parameter 'terminalId' when calling addHostUsingPOST1");
        }

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'host' when calling addHostUsingPOST1");
        }

        String path = "/terminals/{terminalId}/addHost/{hostId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_TERMINAL_ID + "\\}", apiClient.escapeString(terminalId))
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

        TypeRef returnType = new TypeRef<TerminalHost>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Delete host Deletes host
     *
     * @param terminalId Terminal id
     * @param hostId
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteHost(String terminalId, Long hostId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (terminalId == null) {
            throw new ApiException(400, "Missing the required parameter 'terminalId' when calling deleteHostUsingDELETE1");
        }

        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'host' when calling deleteHostUsingDELETE1");
        }

        String path = "/terminals/{terminalId}/host/{hostId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_TERMINAL_ID + "\\}", apiClient.escapeString(terminalId))
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
     * Update host setting U Updates terminal host setting
     *
     * @param hostId
     * @param terminalId Terminal id
     * @param terminalHostSettingValue
     * @return Terminal
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Terminal updateHostSetting(Long hostId, String terminalId, TerminalHostSettingValue terminalHostSettingValue) throws ApiException {
        Object postBody = terminalHostSettingValue;
        byte[] postBinaryBody = null;

        // verify the required parameter 'host' is set
        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'host' when calling updateHostSettingUsingPUT1");
        }

        // verify the required parameter 'terminalId' is set
        if (terminalId == null) {
            throw new ApiException(400, "Missing the required parameter 'terminalId' when calling updateHostSettingUsingPUT1");
        }

        // verify the required parameter 'terminalHostSettingValue' is set
        if (terminalHostSettingValue == null) {
            throw new ApiException(400, "Missing the required parameter 'terminalHostSettingValue' when calling updateHostSettingUsingPUT1");
        }

        // create path and map variables
        String path = "/terminals/{terminalId}/host/{host}/settings".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "host" + "\\}", apiClient.escapeString(hostId.toString()))
                .replaceAll("\\{" + "terminalId" + "\\}", apiClient.escapeString(terminalId));

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

        TypeRef returnType = new TypeRef<Terminal>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_PUT, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Add host setting Add terminal host setting
     *
     * @param terminalId Terminal id
     * @param hostId
     * @param terminalHostSettingValue HostEnum Setting
     * @return Terminal
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Terminal addHostSetting(String terminalId, Long hostId, TerminalHostSettingValue terminalHostSettingValue) throws ApiException {
        Object postBody = terminalHostSettingValue;
        byte[] postBinaryBody = null;

        // verify the required parameter 'terminalId' is set
        if (terminalId == null) {
            throw new ApiException(400, "Missing the required parameter 'terminalId' when calling addHostSettingUsingPOST1");
        }

        // verify the required parameter 'host' is set
        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'host' when calling addHostSettingUsingPOST1");
        }

        // verify the required parameter 'terminalHostSettingValue' is set
        if (terminalHostSettingValue == null) {
            throw new ApiException(400, "Missing the required parameter 'terminalHostSettingValue' when calling addHostSettingUsingPOST1");
        }

        // create path and map variables
        String path = "/terminals/{terminalId}/host/{host}/settings".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "terminalId" + "\\}", apiClient.escapeString(terminalId))
                .replaceAll("\\{" + "host" + "\\}", apiClient.escapeString(hostId.toString()));

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

        TypeRef returnType = new TypeRef<Terminal>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Delete host setting Deletes terminal host setting
     *
     * @param terminalId Terminal id
     * @param hostId
     * @param setting HostEnum Setting
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteHostSetting(String terminalId, Long hostId, Long settingId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'terminalId' is set
        if (terminalId == null) {
            throw new ApiException(400, "Missing the required parameter 'terminalId' when calling deleteHostSettingUsingDELETE1");
        }

        // verify the required parameter 'host' is set
        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'host' when calling deleteHostSettingUsingDELETE1");
        }

        // verify the required parameter 'setting' is set
        if (settingId == null) {
            throw new ApiException(400, "Missing the required parameter 'setting' when calling deleteHostSettingUsingDELETE1");
        }

        // create path and map variables
        String path = "/terminals/{terminalId}/host/{host}/settings/{setting}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "terminalId" + "\\}", apiClient.escapeString(terminalId))
                .replaceAll("\\{" + "host" + "\\}", apiClient.escapeString(hostId.toString()))
                .replaceAll("\\{" + "settingId" + "\\}", apiClient.escapeString(settingId.toString()));

        // query params
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
     * List available host List available host for a given terminal
     *
     * @param terminalId Terminal id
     * @return Hosts list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<Host> listAvailableHosts(String terminalId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'terminalId' is set
        if (terminalId == null) {
            throw new ApiException(400, "Missing the required parameter 'terminalId' when calling listAvailableHostsUsingGET1");
        }

        // create path and map variables
        String path = "/terminals/{terminalId}/hosts".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "terminalId" + "\\}", apiClient.escapeString(terminalId));

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

        TypeRef returnType = new TypeRef<List<Host>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * List host settings List all terminal host settings for a given host
     *
     * @param terminalId Terminal id
     * @param hostId
     * @return TerminalHost settings list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<TerminalHostSettingValue> allHostSettings(String terminalId, Long hostId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'terminalId' is set
        if (terminalId == null) {
            throw new ApiException(400, "Missing the required parameter 'terminalId' when calling listHostSettingsUsingGET1");
        }

        // verify the required parameter 'host' is set
        if (hostId == null) {
            throw new ApiException(400, "Missing the required parameter 'host' when calling listHostSettingsUsingGET1");
        }

        // create path and map variables
        String path = "/terminals/{terminalId}/hosts/{host}/settings".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "terminalId" + "\\}", apiClient.escapeString(terminalId))
                .replaceAll("\\{" + "host" + "\\}", apiClient.escapeString(hostId.toString()));

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

        TypeRef returnType = new TypeRef<List<TerminalHostSettingValue>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * List available setting List available setting for a given terminal
     *
     * @param terminalId Terminal id
     * @return Terminal settings list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<TerminalSettingEnum> allAvailableSettings(String terminalId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'terminalId' is set
        if (terminalId == null) {
            throw new ApiException(400, "Missing the required parameter 'terminalId' when calling listAvailableSettingsUsingGET1");
        }

        // create path and map variables
        String path = "/terminals/{terminalId}/settings".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "terminalId" + "\\}", apiClient.escapeString(terminalId));

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

        TypeRef returnType = new TypeRef<List<TerminalSettingEnum>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Update terminal setting Updates terminal values
     *
     * @param terminalId Terminal Id
     * @param terminalSettingValue Terminal Setting
     * @return Terminal
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Terminal updateSetting(String terminalId, TerminalSettingValue terminalSettingValue) throws ApiException {
        Object postBody = terminalSettingValue;
        byte[] postBinaryBody = null;

        // verify the required parameter 'terminalId' is set
        if (terminalId == null) {
            throw new ApiException(400, "Missing the required parameter 'terminalId' when calling updateSettingUsingPUT2");
        }

        // verify the required parameter 'terminalSettingValue' is set
        if (terminalSettingValue == null) {
            throw new ApiException(400, "Missing the required parameter 'terminalSettingValue' when calling updateSettingUsingPUT2");
        }

        // create path and map variables
        String path = "/terminals/{terminalId}/settings".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "terminalId" + "\\}", apiClient.escapeString(terminalId));

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

        TypeRef returnType = new TypeRef<Terminal>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_PUT, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Add setting Add setting to a terminal
     *
     * @param terminalId Terminal Id
     * @param terminalSettingValue Terminal Setting
     * @return Terminal
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Terminal addSetting(String terminalId, TerminalSettingValue terminalSettingValue) throws ApiException {
        Object postBody = terminalSettingValue;
        byte[] postBinaryBody = null;

        // verify the required parameter 'terminalId' is set
        if (terminalId == null) {
            throw new ApiException(400, "Missing the required parameter 'terminalId' when calling addSettingUsingPOST2");
        }

        // verify the required parameter 'terminalSettingValue' is set
        if (terminalSettingValue == null) {
            throw new ApiException(400, "Missing the required parameter 'terminalSettingValue' when calling addSettingUsingPOST2");
        }

        // create path and map variables
        String path = "/terminals/{terminalId}/settings".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "terminalId" + "\\}", apiClient.escapeString(terminalId));

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

        TypeRef returnType = new TypeRef<Terminal>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Delete setting values Deletes terminal setting values
     *
     * @param terminalId Terminal Id
     * @param terminalSetting Terminal Setting
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteSetting(String terminalId, TerminalSettingEnum terminalSetting) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'terminalId' is set
        if (terminalId == null) {
            throw new ApiException(400, "Missing the required parameter 'terminalId' when calling deleteSettingUsingDELETE2");
        }

        // verify the required parameter 'terminalSetting' is set
        if (terminalSetting == null) {
            throw new ApiException(400, "Missing the required parameter 'terminalSetting' when calling deleteSettingUsingDELETE2");
        }

        // create path and map variables
        String path = "/terminals/{terminalId}/settings/{terminalSetting}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "terminalId" + "\\}", apiClient.escapeString(terminalId))
                .replaceAll("\\{" + "terminalSetting" + "\\}", apiClient.escapeString(terminalSetting.toString()));

        // query params
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
