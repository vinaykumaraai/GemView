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
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

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
     *
     * @param terminalSerialNumber The terminal serial number
     * @return
     * @throws ApiException
     */
    public Terminal getBySerialNumber(String terminalSerialNumber) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'serialNumber' is set
        if (terminalSerialNumber == null) {
            throw new ApiException(400, "Missing the required parameter 'serialNumber' when calling getUsingGET8");
        }

        // create path and map variables
        String path = "/terminals/getBySerialNumber".replaceAll("\\{format\\}", "json");

        // query params
        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_TERMINAL_SERIAL_NUMBER, terminalSerialNumber));

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

}
