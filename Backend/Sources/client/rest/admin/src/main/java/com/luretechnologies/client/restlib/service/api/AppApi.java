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
import com.luretechnologies.client.restlib.service.model.App;
import com.luretechnologies.client.restlib.service.model.AppFile;
import com.luretechnologies.client.restlib.service.model.AppParam;
import com.luretechnologies.client.restlib.service.model.AppProfile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-09-18T13:16:30.027-04:00")
public class AppApi extends BaseApi {

    private static final String METHOD_SEARCH = "/app/search";
    private static final String METHOD_SEARCH_PARAMS = "/app/{id}/searchParams";
    private static final String METHOD_GET = "/app/{id}";
    private static final String METHOD_LIST = "/app/app";
    private static final String METHOD_LIST_BY_ENTITY = "/app/entities/{entityId}";
    private static final String METHOD_CREATE = "/app/app";
    private static final String METHOD_DELETE = "/app/{id}";
    private static final String METHOD_UPDATE = "/app/{id}";
    private static final String METHOD_ADD_APPFILE = "/app/{id}/appFile";
    private static final String METHOD_DELETE_APPFILE = "/app/{id}/appFile/{appFileId}";
    private static final String METHOD_ALL_DELETE_APPFILE = "/app/{id}/appFile";
    private static final String METHOD_ADD_APPPARAM = "/app/{id}/appparam";
    private static final String METHOD_UPDATE_APPPARAM = "/app/{id}/appparam";
    private static final String METHOD_DELETE_APPPARAM = "/app/{id}/appparam/{appParamId}";
    private static final String METHOD_ALL_DELETE_APPPARAM = "/app/{id}/appparam";
    private static final String METHOD_ADD_APPPROFILE = "/app/{id}/appprofile";
    private static final String METHOD_DELETE_APPPROFILE = "/app/{id}/appprofile/{appProfileId}";
    private static final String METHOD_ADDFILE = "/app/{id}";
    private static final String METHOD_GET_APPPROFILE_LIST = "/app/{id}/appprofile";
    private static final String METHOD_GET_APPPARAM_LIST = "/app/{id}/appparam";
    private static final String METHOD_GET_APPFILE_LIST = "/app/{id}/appFile";

    /**
     * @param apiClient
     */
    public AppApi(ApiClient apiClient) {
        super(apiClient);
    }

    /**
     *
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws ApiException
     */
    public List<App> searchApps(String filter, Integer pageNumber, Integer rowsPerPage) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (filter == null) {
            throw new ApiException(400, "Missing the required parameter 'filter' when calling searchUsingPOST2");
        }

        String path = METHOD_SEARCH.replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<List<App>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);
    }
    
    /**
     *
     * @param id
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws ApiException
     */
    public List<AppParam> searchAppParam(Long id, String filter, Integer pageNumber, Integer rowsPerPage) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;
        
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling searchUsingPOST1");
        }

        if (filter == null) {
            throw new ApiException(400, "Missing the required parameter 'filter' when calling searchUsingPOST2");
        }

        String path = METHOD_SEARCH_PARAMS.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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

        TypeRef returnType = new TypeRef<List<AppParam>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);
    }

    /**
     * Get user Get app by id
     *
     * @param id App id
     * @return App
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public App getApp(Long id) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling getUsingGET10");
        }

        // create path and map variables
        String path = METHOD_GET.replaceAll("\\{format\\}", "json")
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

        TypeRef returnType = new TypeRef<App>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Get all apps. Will return 50 records if no paging parameters defined
     *
     * @return Apps list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<App> getApps() throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        String path = METHOD_LIST.replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<List<App>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }
    
    /**
     * Get all apps. Will return 50 records if no paging parameters defined
     *
     * @param id
     * @return Apps list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<App> getAppsByEntityHierarchy(Long id) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;
        
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling getUsingGET10");
        }

        // create path and map variables
        String path = METHOD_LIST_BY_ENTITY.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "entityId" + "\\}", apiClient.escapeString(id.toString()));

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

        TypeRef returnType = new TypeRef<List<App>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Create user Creates a new user
     *
     * @param app App object
     * @return App
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public App createApp(App app) throws ApiException {
        Object postBody = app;
        byte[] postBinaryBody = null;

        if (app == null) {
            throw new ApiException(400, "Missing the required parameter 'app' when calling createUsingPOST8");
        }

        String path = METHOD_CREATE.replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<App>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Update app Updates a app
     *
     * @param appId
     * @param app
     * @return App
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public App updateApp(Long appId, App app) throws ApiException {
        Object postBody = app;
        byte[] postBinaryBody = null;

        // verify the required parameter 'appId' is set
        if (appId == null) {
            throw new ApiException(400, "Missing the required parameter 'appId' when calling updateUsingPUT7");
        }

        // verify the required parameter 'app' is set
        if (app == null) {
            throw new ApiException(400, "Missing the required parameter 'app' when calling updateUsingPUT7");
        }

        // create path and map variables
        String path = METHOD_UPDATE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appId.toString()));

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

        TypeRef returnType = new TypeRef<App>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_PUT, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Delete app Deletes a app
     *
     * @param appId
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteApp(Long appId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (appId == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling deleteUsingDELETE7");
        }

        String path = METHOD_DELETE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appId.toString()));

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
     * Create user Creates a new user
     *
     * @param appId
     * @param appFile
     * @return App
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public App addAppFile(Long appId, AppFile appFile) throws ApiException {
        Object postBody = appFile;
        byte[] postBinaryBody = null;

        if (appFile == null) {
            throw new ApiException(400, "Missing the required parameter 'appFile' when calling createUsingPOST8");
        }

        String path = METHOD_ADD_APPFILE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appId.toString()));

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

        TypeRef returnType = new TypeRef<App>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Delete appFile Deletes a appFile
     *
     * @param appId
     * @param appFileId
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteAppFile(Long appId, Long appFileId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (appId == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling deleteUsingDELETE7");
        }

        if (appFileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appFileId' when calling deleteUsingDELETE7");
        }

        String path = METHOD_DELETE_APPFILE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appId.toString()))
                .replaceAll("\\{" + "appFileId" + "\\}", apiClient.escapeString(appFileId.toString()));

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
     * Delete allAppFile Deletes a allAppFile
     *
     * @param appId
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteAllAppFile(Long appId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (appId == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling deleteUsingDELETE7");
        }

        String path = METHOD_ALL_DELETE_APPFILE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appId.toString()));

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
     * Create user Creates a new user
     *
     * @param appId
     * @param appParam
     * @return App
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public App addAppParam(Long appId, AppParam appParam) throws ApiException {
        Object postBody = appParam;
        byte[] postBinaryBody = null;

        if (appParam == null) {
            throw new ApiException(400, "Missing the required parameter 'appFile' when calling createUsingPOST8");
        }

        String path = METHOD_ADD_APPPARAM.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appId.toString()));

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

        TypeRef returnType = new TypeRef<App>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Update app Updates a app
     *
     * @param appId
     * @param appParam
     * @return App
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public App updateAppParam(Long appId, AppParam appParam) throws ApiException {
        Object postBody = appParam;
        byte[] postBinaryBody = null;

        // verify the required parameter 'appId' is set
        if (appId == null) {
            throw new ApiException(400, "Missing the required parameter 'appId' when calling updateUsingPUT7");
        }

        // verify the required parameter 'app' is set
        if (appParam == null) {
            throw new ApiException(400, "Missing the required parameter 'appParam' when calling updateUsingPUT7");
        }

        // create path and map variables
        String path = METHOD_UPDATE_APPPARAM.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appId.toString()));

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

        TypeRef returnType = new TypeRef<App>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_PUT, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Delete appParam Deletes a appParam
     *
     * @param appId
     * @param appParamId
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteAppParam(Long appId, Long appParamId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (appId == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling deleteUsingDELETE7");
        }

        if (appParamId == null) {
            throw new ApiException(400, "Missing the required parameter 'appFileId' when calling deleteUsingDELETE7");
        }

        String path = METHOD_DELETE_APPPARAM.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appId.toString()))
                .replaceAll("\\{" + "appParamId" + "\\}", apiClient.escapeString(appParamId.toString()));

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
     * Delete allAppParam Deletes a allAppParam
     *
     * @param appId
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteAllAppParam(Long appId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (appId == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling deleteUsingDELETE7");
        }

        String path = METHOD_ALL_DELETE_APPPARAM.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appId.toString()));

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
     * Create user Creates a new user
     *
     * @param appId
     * @param appProfile
     * @return AppProfile
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public App addAppProfile(Long appId, AppProfile appProfile) throws ApiException {
        Object postBody = appProfile;
        byte[] postBinaryBody = null;

        if (appProfile == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfile' when calling createUsingPOST8");
        }

        String path = METHOD_ADD_APPPROFILE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appId.toString()));

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

        TypeRef returnType = new TypeRef<App>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Delete appParam Deletes a appParam
     *
     * @param appId
     * @param appProfileId
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteAppProfile(Long appId, Long appProfileId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (appId == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling deleteUsingDELETE7");
        }

        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appFileId' when calling deleteUsingDELETE7");
        }

        String path = METHOD_DELETE_APPPROFILE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appId.toString()))
                .replaceAll("\\{" + "appProfileId" + "\\}", apiClient.escapeString(appProfileId.toString()));

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
     * Login user Logs user into the system
     *
     * @param appId
     * @param description
     * @param fileName
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void addFile(Long appId, String description, String fileName) throws ApiException {

        String token = apiClient.getAuthToken();

        String queryParams = "?description=" + description;

        String path = METHOD_ADDFILE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appId.toString()));

        path += queryParams;

        if (appId == null) {
            throw new ApiException(400, "Missing the required parameter 'appId' when calling createUsingPOST8");
        }

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost uploadFile = new HttpPost(apiClient.getBasePath() + path);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            File f = new File(fileName);
            builder.addBinaryBody(
                    "file",
                    new FileInputStream(f),
                    ContentType.APPLICATION_OCTET_STREAM,
                    f.getName()
            );

            HttpEntity multipart = builder.build();
            uploadFile.setEntity(multipart);
            uploadFile.setHeader("X-Auth-Token", token);

            CloseableHttpResponse response = httpClient.execute(uploadFile);

            int code = response.getStatusLine().getStatusCode();
            String reason = response.getStatusLine().getReasonPhrase();

            setToken(response.getAllHeaders());

            // if good, just return
            if (code >= 200 && code <= 204) {
                return;
            }

            String hostErrorMessage = String.format("File Upload Failed: %d [%s]", code, reason);
            throw new ApiException(hostErrorMessage);

        } catch (IOException ex) {
            String hostErrorMessage = String.format("File Upload Failed: %d [%s]", 999, ex.getMessage());
            throw new ApiException(hostErrorMessage);
        }
    }

    private void setToken(Header[] headers) {
        for (Header header : headers) {
            if (header.getName().equalsIgnoreCase("x-auth-token")) {
                apiClient.setAuthToken(header.getValue());
            }
        }
    }
    
    /**
     * Get getAppProfileList Get getAppProfileList by id
     *
     * @param id App id
     * @return getAppProfileList
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<AppProfile> getAppProfileList(Long id) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling getUsingGET10");
        }

        // create path and map variables
        String path = METHOD_GET_APPPROFILE_LIST.replaceAll("\\{format\\}", "json")
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

        TypeRef returnType = new TypeRef<List<AppProfile>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }
    
    /**
     * Get getAppParamList Get getAppParamList by id
     *
     * @param id App id
     * @return getAppParamList
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<AppParam> getAppParamList(Long id) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling getUsingGET10");
        }

        // create path and map variables
        String path = METHOD_GET_APPPARAM_LIST.replaceAll("\\{format\\}", "json")
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

        TypeRef returnType = new TypeRef<List<AppParam>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }
    
    /**
     * Get getAppFileList Get getAppFileList by id
     *
     * @param id App id
     * @return getAppFileList
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<AppFile> getAppFileList(Long id) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling getUsingGET10");
        }

        // create path and map variables
        String path = METHOD_GET_APPFILE_LIST.replaceAll("\\{format\\}", "json")
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

        TypeRef returnType = new TypeRef<List<AppFile>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }
}
