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
import com.luretechnologies.client.restlib.service.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Gemstone
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-09-15T15:20:45.337-04:00")
public class UsersApi {

    private final ApiClient apiClient;

    /**
     *
     * @param apiClient
     */
    public UsersApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Lists users. Will return 50 records if no paging parameters
     * defined
     *
     * @param username Login name
     * @param firstname User first name
     * @param lastname User last name
     * @param active User status
     * @param pageNumber Page number
     * @param rowsPerPage Rows per page
     * @return Users list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<User> getUsers(String username, String firstname, String lastname, Boolean active, Integer pageNumber, Integer rowsPerPage) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        String path = "/users".replaceAll("\\{format\\}", "json");

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        if (username != null && !username.isEmpty()) {
            queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_USER_NAME, username));
        }
        if (firstname != null && !firstname.isEmpty()) {
            queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_FIRST_NAME, firstname));
        }
        if (lastname != null && !lastname.isEmpty()) {
            queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_LAST_NAME, lastname));
        }

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_ACTIVE, active));

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

        TypeRef returnType = new TypeRef<List<User>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Create user Creates a new user
     *
     * @param user User object
     * @return User
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public User createUser(User user) throws ApiException {
        Object postBody = user;
        byte[] postBinaryBody = null;

        if (user == null) {
            throw new ApiException(400, "Missing the required parameter 'user' when calling createUsingPOST8");
        }

        String path = "/users".replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<User>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }
    
    /**
     * Get all users. Will return 50
     * records if no paging parameters defined
     *
     * @return Users list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<User> getUsers() throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        String path = "/users/list".replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<List<User>>() { 
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Search users Search users that match a given filter. Will return 50
     * records if no paging parameters defined
     *
     * @param filter filter
     * @param pageNumber Page number
     * @param rowsPerPage Rows per page
     * @return Users list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<User> searchUsers(String filter, Integer pageNumber, Integer rowsPerPage) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (filter == null) {
            throw new ApiException(400, "Missing the required parameter 'filter' when calling searchUsingPOST8");
        }

        String path = "/users/search".replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<List<User>>() { 
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Get user Get user by id
     *
     * @param id User id
     * @return User
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public User getUser(Long id) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling getUsingGET10");
        }

        // create path and map variables
        String path = "/users/{id}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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

        TypeRef returnType = new TypeRef<User>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }
    
    /**
     * Get user by username
     *
     * @param username User name
     * @return User
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public User getUserByUserName(String username) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'id' is set
        if (username == null) {
            throw new ApiException(400, "Missing the required parameter 'username' when calling getUserByUsername");
        }

        // create path and map variables
        String path = "/users/getbyusername/{username}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "username" + "\\}", apiClient.escapeString(username));

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

        TypeRef returnType = new TypeRef<User>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Update user Updates a user
     *
     * @param id User id
     * @param user User object
     * @return User
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public User updateUser(Long id, User user) throws ApiException {
        Object postBody = user;
        byte[] postBinaryBody = null;

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling updateUsingPUT8");
        }

        // verify the required parameter 'user' is set
        if (user == null) {
            throw new ApiException(400, "Missing the required parameter 'user' when calling updateUsingPUT8");
        }

        // create path and map variables
        String path = "/users/{id}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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

        TypeRef returnType = new TypeRef<User>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_PUT, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Delete user Deletes a user
     *
     * @param id User id
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteUser(Long id) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling deleteUsingDELETE8");
        }

        // create path and map variables
        String path = "/users/{id}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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
