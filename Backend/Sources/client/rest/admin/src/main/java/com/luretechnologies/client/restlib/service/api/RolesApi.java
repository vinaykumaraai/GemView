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
import com.luretechnologies.client.restlib.service.model.Role;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Gemstone
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-09-15T15:20:45.337-04:00")
public class RolesApi extends BaseApi {

    /**
     *
     * @param apiClient
     */
    public RolesApi(ApiClient apiClient) {
        super(apiClient);
    }

    /**
     * List roles Lists all roles
     *
     * @param pageNumber
     * @param rowsPerPage
     * @return Role
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<Role> getRoles(Integer pageNumber, Integer rowsPerPage) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // create path and map variables
        String path = "/roles".replaceAll("\\{format\\}", "json");

        // query params
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

        TypeRef returnType = new TypeRef<List<Role>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Create role Creates a new role
     *
     * @param role
     * @return Role
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Role createRole(Role role) throws ApiException {
        Object postBody = role;
        byte[] postBinaryBody = null;

        // create path and map variables
        String path = "/roles".replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<Role>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Search roles Search roles that match a given filter
     *
     * @param filter filter
     * @param pageNumber pageNumber
     * @param rowsPerPage rowsPerPage
     * @return Role
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<Role> searchRoles(String filter, Integer pageNumber, Integer rowsPerPage) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'filter' is set
        if (filter == null) {
            throw new ApiException(400, "Missing the required parameter 'filter' when calling searchUsingPOST5");
        }

        // create path and map variables
        String path = "/roles/search".replaceAll("\\{format\\}", "json");

        // query params
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

        TypeRef returnType = new TypeRef<List<Role>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Get role Get role by id
     *
     * @param id Role id
     * @return Role
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Role getRole(Long id) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (id == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling getUsingGET6");
        }

        String path = "/roles/{id}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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

        TypeRef returnType = new TypeRef<Role>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Update role Updates a role
     *
     * @param id Role id
     * @param role
     * @return Role
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Role updateRole(Long id, Role role) throws ApiException {
        Object postBody = role;
        byte[] postBinaryBody = null;

        if (id == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling updateUsingPUT5");
        }

        String path = "/roles/{id}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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

        TypeRef returnType = new TypeRef<Role>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_PUT, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Delete role Deletes a role
     *
     * @param id Role id
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteRole(Long id) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (id == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling deleteUsingDELETE5");
        }

        String path = "/roles/{id}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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
     * Add permission Add permission to a role
     *
     * @param id Role id
     * @param permission Permission name
     * @return Role
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Role addPermission(Long id, String permission) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (id == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling addPermissionUsingPOST");
        }

        if (permission == null) {
            throw new ApiException(400, "Missing the required parameter 'permission' when calling addPermissionUsingPOST");
        }

        String path = "/roles/{id}/permissions/{permission}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()))
                .replaceAll("\\{" + "permission" + "\\}", apiClient.escapeString(permission));

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

        TypeRef returnType = new TypeRef<Role>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Delete permission Delete permission from a role
     *
     * @param id Role id
     * @param permission Permission name
     * @return Role
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Role removePermission(Long id, String permission) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling removePermissionUsingDELETE");
        }

        // verify the required parameter 'permission' is set
        if (permission == null) {
            throw new ApiException(400, "Missing the required parameter 'permission' when calling removePermissionUsingDELETE");
        }

        // create path and map variables
        String path = "/roles/{id}/permissions/{permission}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()))
                .replaceAll("\\{" + "permission" + "\\}", apiClient.escapeString(permission));

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

        TypeRef returnType = new TypeRef<Role>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_DELETE, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

}
