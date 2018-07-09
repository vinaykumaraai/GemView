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
import com.luretechnologies.client.restlib.service.model.AppParam;
import com.luretechnologies.client.restlib.service.model.AppProfile;
import com.luretechnologies.client.restlib.service.model.AppProfileParamValue;
import com.luretechnologies.client.restlib.service.model.EntityAppProfile;
import com.luretechnologies.client.restlib.service.model.EntityAppProfileParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-09-18T13:16:30.027-04:00")
public class AppProfileApi extends BaseApi {

    private static final String METHOD_GET = "/appprofile/{id}";
    private static final String METHOD_GET_APPPROFILEPARAM_LIST = "/appprofile/{id}/appprofileparam";
    private static final String METHOD_GET_APPPROFILEWPARAM_LIST = "/appprofile/{id}/appparam";
    private static final String METHOD_GET_APPPROFILEFILE_LIST = "/appprofile/{id}/appprofileparam/appfile";
    private static final String METHOD_GET_APPPROFILEWFILE_LIST = "/appprofile/{id}/appparam/appfile";
    private static final String METHOD_ADD_APPPROFILEPARAMVALUE = "/appprofile/{id}/appparam/{appParamId}";
    private static final String METHOD_UPDATE_APPPROFILEPARAMVALUE = "/appprofile/{id}/appprofileparamvalue";
    private static final String METHOD_DELETE_APPPROFILEPARAMVALUE = "/appprofile/{id}/appparam/{appParamId}";
    private static final String METHOD_GET_APPPROFILEENTITY_LIST = "/appprofile/app/{appId}/entities/{entityId}/entityAppProfile";
    private static final String METHOD_GET_APPPROFILEWENTITY_LIST = "/appprofile/app/{appId}/entities/{entityId}/appprofile";
    private static final String METHOD_ADD_ENTITYAPPPROFILE = "/appprofile/{id}/entities/{entityId}";
    private static final String METHOD_UPDATE_ENTITYAPPPROFILE = "/appprofile/{id}/entities";
    private static final String METHOD_DELETE_ENTITYAPPPROFILE = "/appprofile/{id}/entities/{entityId}";
    private static final String METHOD_GET_APPPARAMENTITY_LIST = "/appprofile/{id}/entities/{entityId}/entityAppProfileParam";
    private static final String METHOD_GET_APPPARAMWENTITY_LIST = "/appprofile/{id}/entities/{entityId}";
    private static final String METHOD_ADD_ENTITYAPPPROFILEPARAM = "/appprofile/{id}/entities/{entityId}/appparam/{appParamId}";
    private static final String METHOD_UPDATE_ENTITYAPPPROFILEPARAM = "/appprofile/{id}/entities/{entityId}/entityAppProfileParam";
    private static final String METHOD_DELETE_ENTITYAPPPROFILEPARAM = "/appprofile/{id}/entities/{entityId}/appparam/{appParamId}";
    private static final String METHOD_SEARCH_FILES_PROFILE = "/appprofile/{id}/searchFilesByProfile";
    private static final String METHOD_SEARCH_PARAMS_PROFILE = "/appprofile/{id}/searchParamsByProfile";

    /**
     * @param apiClient
     */
    public AppProfileApi(ApiClient apiClient) {
        super(apiClient);
    }

    /**
     * Get AppProfile Get AppProfile
     *
     * @param id AppParam id
     * @return AppParam
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public AppProfile getAppProfile(Long id) throws ApiException {
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

        TypeRef returnType = new TypeRef<AppProfile>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }
    
    /**
     * Get all appParams by a appProfile. Will return 50 records if no paging parameters defined
     *
     * @param appProfileId
     * @return AppParams list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<AppParam> getAppParamListByAppProfile(Long appProfileId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;
        
        // verify the required parameter 'id' is set
        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileId' when calling getUsingGET10");
        }

        // create path and map variables
        String path = METHOD_GET_APPPROFILEPARAM_LIST.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appProfileId.toString()));

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
     * Get all appParams without appProfile. Will return 50 records if no paging parameters defined
     *
     * @param appProfileId
     * @return AppParams list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<AppParam> getAppParamListWithoutAppProfile(Long appProfileId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;
        
        // verify the required parameter 'id' is set
        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileId' when calling getUsingGET10");
        }

        // create path and map variables
        String path = METHOD_GET_APPPROFILEWPARAM_LIST.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appProfileId.toString()));

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
     * Get all appFiles by a appProfile. Will return 50 records if no paging parameters defined
     *
     * @param appProfileId
     * @return AppFiles list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<AppParam> getAppFileListByAppProfile(Long appProfileId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;
        
        // verify the required parameter 'id' is set
        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileId' when calling getUsingGET10");
        }

        // create path and map variables
        String path = METHOD_GET_APPPROFILEFILE_LIST.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appProfileId.toString()));

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
     * Get all appParams without appProfile. Will return 50 records if no paging parameters defined
     *
     * @param appProfileId
     * @return AppParams list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<AppParam> getAppFileListWithoutAppProfile(Long appProfileId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;
        
        // verify the required parameter 'id' is set
        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileId' when calling getUsingGET10");
        }

        // create path and map variables
        String path = METHOD_GET_APPPROFILEWFILE_LIST.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appProfileId.toString()));

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
     * Add appProfileParamValue Add appProfileParamValue
     *
     * @param appProfileId
     * @param appParamId
     * @return AppProfileParamValue
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public AppProfileParamValue addAppProfileParamValue(Long appProfileId, Long appParamId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'id' is set
        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileId' when calling getUsingGET10");
        }
        
        // verify the required parameter 'appParamId' is set
        if (appParamId == null) {
            throw new ApiException(400, "Missing the required parameter 'appParamId' when calling getUsingGET10");
        }

        // create path and map variables
        String path = METHOD_ADD_APPPROFILEPARAMVALUE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appProfileId.toString()))
                .replaceAll("\\{" + "appParamId" + "\\}", apiClient.escapeString(appParamId.toString()));

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

        TypeRef returnType = new TypeRef<AppProfileParamValue>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }
    
    /**
     * Add appProfileFileValue Add appProfileFileValue
     *
     * @param appProfileId
     * @param appParamId
     * @return AppProfileParamValue
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public AppProfileParamValue addAppProfileFileValue(Long appProfileId, Long appParamId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'id' is set
        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileId' when calling getUsingGET10");
        }
        
        // verify the required parameter 'appParamId' is set
        if (appParamId == null) {
            throw new ApiException(400, "Missing the required parameter 'appParamId' when calling getUsingGET10");
        }

        // create path and map variables
        String path = METHOD_ADD_APPPROFILEPARAMVALUE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appProfileId.toString()))
                .replaceAll("\\{" + "appParamId" + "\\}", apiClient.escapeString(appParamId.toString()));

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

        TypeRef returnType = new TypeRef<AppProfileParamValue>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }
    
    /**
     * Update appProfileParamValue Updates appProfileParamValue
     *
     * @param appProfileId
     * @param appProfileParamValue
     * @return AppProfileParamValue
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public AppProfileParamValue updateAppProfileParamValue(Long appProfileId, AppProfileParamValue appProfileParamValue) throws ApiException {
        Object postBody = appProfileParamValue;
        byte[] postBinaryBody = null;

        // verify the required parameter 'appId' is set
        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileId' when calling updateUsingPUT7");
        }

        // verify the required parameter 'appProfileParamValue' is set
        if (appProfileParamValue == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileParamValue' when calling updateUsingPUT7");
        }

        // create path and map variables
        String path = METHOD_UPDATE_APPPROFILEPARAMVALUE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appProfileId.toString()));

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

        TypeRef returnType = new TypeRef<AppProfileParamValue>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_PUT, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }
    
    /**
     * Delete appProfileParamValue Deletes a appProfileParamValue
     *
     * @param appProfileId
     * @param appParamId
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteAppProfileParamValue(Long appProfileId, Long appParamId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileId' when calling deleteUsingDELETE7");
        }

        if (appParamId == null) {
            throw new ApiException(400, "Missing the required parameter 'appFileId' when calling deleteUsingDELETE7");
        }

        String path = METHOD_DELETE_APPPROFILEPARAMVALUE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appProfileId.toString()))
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
     * Delete appProfileFileValue Deletes a appProfileFileValue
     *
     * @param appProfileId
     * @param appParamId
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteAppProfileFileValue(Long appProfileId, Long appParamId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileId' when calling deleteUsingDELETE7");
        }

        if (appParamId == null) {
            throw new ApiException(400, "Missing the required parameter 'appFileId' when calling deleteUsingDELETE7");
        }

        String path = METHOD_DELETE_APPPROFILEPARAMVALUE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appProfileId.toString()))
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
     * Get all appProfiles by a entity. Will return 50 records if no paging parameters defined
     *
     * @param appId
     * @param entityId
     * @return AppProfiles list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<AppProfile> getAppProfileListByEntity(Long appId, Long entityId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;
        
        // verify the required parameter 'appId' is set
        if (appId == null) {
            throw new ApiException(400, "Missing the required parameter 'appId' when calling getUsingGET10");
        }
        
        // verify the required parameter 'entityId' is set
        if (entityId == null) {
            throw new ApiException(400, "Missing the required parameter 'entityId' when calling getUsingGET10");
        }

        // create path and map variables
        String path = METHOD_GET_APPPROFILEENTITY_LIST.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "appId" + "\\}", apiClient.escapeString(appId.toString()))
                .replaceAll("\\{" + "entityId" + "\\}", apiClient.escapeString(entityId.toString()));

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
     * Get all appProfiles without entity. Will return 50 records if no paging parameters defined
     *
     * @param appId
     * @param entityId
     * @return AppProfiles list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<AppProfile> getAppProfileListWithoutEntity(Long appId, Long entityId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;
        
        // verify the required parameter 'appId' is set
        if (appId == null) {
            throw new ApiException(400, "Missing the required parameter 'appId' when calling getUsingGET10");
        }
        
        // verify the required parameter 'entityId' is set
        if (entityId == null) {
            throw new ApiException(400, "Missing the required parameter 'entityId' when calling getUsingGET10");
        }

        // create path and map variables
        String path = METHOD_GET_APPPROFILEWENTITY_LIST.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "appId" + "\\}", apiClient.escapeString(appId.toString()))
                .replaceAll("\\{" + "entityId" + "\\}", apiClient.escapeString(entityId.toString()));

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
     * Add entityAppProfile Add entityAppProfile
     *
     * @param appProfileId
     * @param entityId
     * @return EntityAppProfile
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public EntityAppProfile addEntityAppProfile(Long appProfileId, Long entityId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'appProfileId' is set
        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileId' when calling getUsingGET10");
        }
        
        // verify the required parameter 'entityId' is set
        if (entityId == null) {
            throw new ApiException(400, "Missing the required parameter 'entityId' when calling getUsingGET10");
        }

        // create path and map variables
        String path = METHOD_ADD_ENTITYAPPPROFILE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appProfileId.toString()))
                .replaceAll("\\{" + "entityId" + "\\}", apiClient.escapeString(entityId.toString()));

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

        TypeRef returnType = new TypeRef<EntityAppProfile>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }
    
    /**
     * Update entityAppProfile Updates entityAppProfile
     *
     * @param appProfileId
     * @param entityAppProfile
     * @return EntityAppProfile
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public EntityAppProfile updateEntityAppProfile(Long appProfileId, EntityAppProfile entityAppProfile) throws ApiException {
        Object postBody = entityAppProfile;
        byte[] postBinaryBody = null;

        // verify the required parameter 'appProfileId' is set
        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileId' when calling updateUsingPUT7");
        }

        // verify the required parameter 'entityAppProfile' is set
        if (entityAppProfile == null) {
            throw new ApiException(400, "Missing the required parameter 'entityAppProfile' when calling updateUsingPUT7");
        }

        // create path and map variables
        String path = METHOD_UPDATE_ENTITYAPPPROFILE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appProfileId.toString()));

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

        TypeRef returnType = new TypeRef<EntityAppProfile>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_PUT, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }
    
    /**
     * Delete entityAppProfile Deletes a entityAppProfile
     *
     * @param appProfileId
     * @param entityId
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteEntityAppProfile(Long appProfileId, Long entityId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileId' when calling deleteUsingDELETE7");
        }

        if (entityId == null) {
            throw new ApiException(400, "Missing the required parameter 'entityId' when calling deleteUsingDELETE7");
        }

        String path = METHOD_DELETE_ENTITYAPPPROFILE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appProfileId.toString()))
                .replaceAll("\\{" + "entityId" + "\\}", apiClient.escapeString(entityId.toString()));

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
     * Get all appParams by a entity. Will return 50 records if no paging parameters defined
     *
     * @param appProfileId
     * @param entityId
     * @return AppParams list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<AppParam> getAppParamListByEntity(Long appProfileId, Long entityId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;
        
        // verify the required parameter 'appProfileId' is set
        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileId' when calling getUsingGET10");
        }
        
        // verify the required parameter 'entityId' is set
        if (entityId == null) {
            throw new ApiException(400, "Missing the required parameter 'entityId' when calling getUsingGET10");
        }

        // create path and map variables
        String path = METHOD_GET_APPPARAMENTITY_LIST.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appProfileId.toString()))
                .replaceAll("\\{" + "entityId" + "\\}", apiClient.escapeString(entityId.toString()));

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
     * Get all appParams without entity. Will return 50 records if no paging parameters defined
     *
     * @param appProfileId
     * @param entityId
     * @return AppParams list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<AppParam> getAppParamListWithoutEntity(Long appProfileId, Long entityId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;
        
        // verify the required parameter 'appProfileId' is set
        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileId' when calling getUsingGET10");
        }
        
        // verify the required parameter 'entityId' is set
        if (entityId == null) {
            throw new ApiException(400, "Missing the required parameter 'entityId' when calling getUsingGET10");
        }

        // create path and map variables
        String path = METHOD_GET_APPPARAMWENTITY_LIST.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appProfileId.toString()))
                .replaceAll("\\{" + "entityId" + "\\}", apiClient.escapeString(entityId.toString()));

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
     * Add entityAppProfileParam Add entityAppProfileParam
     *
     * @param appProfileId
     * @param entityId
     * @param appParamId
     * @return EntityAppProfileParam
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public EntityAppProfileParam addEntityAppProfileParam(Long appProfileId, Long entityId, Long appParamId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'appProfileId' is set
        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileId' when calling getUsingGET10");
        }
        
        // verify the required parameter 'entityId' is set
        if (entityId == null) {
            throw new ApiException(400, "Missing the required parameter 'entityId' when calling getUsingGET10");
        }
        
        // verify the required parameter 'appParamId' is set
        if (appParamId == null) {
            throw new ApiException(400, "Missing the required parameter 'appParamId' when calling getUsingGET10");
        }

        // create path and map variables
        String path = METHOD_ADD_ENTITYAPPPROFILEPARAM.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appProfileId.toString()))
                .replaceAll("\\{" + "entityId" + "\\}", apiClient.escapeString(entityId.toString()))
                .replaceAll("\\{" + "appParamId" + "\\}", apiClient.escapeString(appParamId.toString()));

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

        TypeRef returnType = new TypeRef<EntityAppProfileParam>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }
    
    /**
     * Add addEntityAppProfileFile Add addEntityAppProfileFile
     *
     * @param appProfileId
     * @param entityId
     * @param appFileId
     * @return EntityAppProfileParam
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public EntityAppProfileParam addEntityAppProfileFile(Long appProfileId, Long entityId, Long appFileId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'appProfileId' is set
        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileId' when calling getUsingGET10");
        }
        
        // verify the required parameter 'entityId' is set
        if (entityId == null) {
            throw new ApiException(400, "Missing the required parameter 'entityId' when calling getUsingGET10");
        }
        
        // verify the required parameter 'appFileId' is set
        if (appFileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appFileId' when calling getUsingGET10");
        }

        // create path and map variables
        String path = METHOD_ADD_ENTITYAPPPROFILEPARAM.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appProfileId.toString()))
                .replaceAll("\\{" + "entityId" + "\\}", apiClient.escapeString(entityId.toString()))
                .replaceAll("\\{" + "appParamId" + "\\}", apiClient.escapeString(appFileId.toString()));

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

        TypeRef returnType = new TypeRef<EntityAppProfileParam>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }
    
    /**
     * Update entityAppProfileParam Updates entityAppProfileParam
     *
     * @param appProfileId
     * @param entityId
     * @param entityAppProfileParam
     * @return EntityAppProfileParam
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public EntityAppProfileParam updateEntityAppProfileParam(Long appProfileId, Long entityId, EntityAppProfileParam entityAppProfileParam) throws ApiException {
        Object postBody = entityAppProfileParam;
        byte[] postBinaryBody = null;

        // verify the required parameter 'appProfileId' is set
        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileId' when calling updateUsingPUT7");
        }
        
        // verify the required parameter 'entityId' is set
        if (entityId == null) {
            throw new ApiException(400, "Missing the required parameter 'entityId' when calling updateUsingPUT7");
        }

        // verify the required parameter 'entityAppProfileParam' is set
        if (entityAppProfileParam == null) {
            throw new ApiException(400, "Missing the required parameter 'entityAppProfileParam' when calling updateUsingPUT7");
        }

        // create path and map variables
        String path = METHOD_UPDATE_ENTITYAPPPROFILEPARAM.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appProfileId.toString()))
                .replaceAll("\\{" + "entityId" + "\\}", apiClient.escapeString(entityId.toString()));

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

        TypeRef returnType = new TypeRef<EntityAppProfileParam>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_PUT, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }
    
    /**
     * Delete entityAppProfile Deletes a entityAppProfile
     *
     * @param appProfileId
     * @param entityId
     * @param appParamId
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteEntityAppProfileParam(Long appProfileId, Long entityId, Long appParamId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileId' when calling deleteUsingDELETE7");
        }

        if (entityId == null) {
            throw new ApiException(400, "Missing the required parameter 'entityId' when calling deleteUsingDELETE7");
        }
        
        if (appParamId == null) {
            throw new ApiException(400, "Missing the required parameter 'appParamId' when calling deleteUsingDELETE7");
        }

        String path = METHOD_DELETE_ENTITYAPPPROFILEPARAM.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appProfileId.toString()))
                .replaceAll("\\{" + "entityId" + "\\}", apiClient.escapeString(entityId.toString()))
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
     * Delete entityAppProfileFile Deletes a entityAppProfileFile
     *
     * @param appProfileId
     * @param entityId
     * @param appFileId
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteEntityAppProfileFile(Long appProfileId, Long entityId, Long appFileId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileId' when calling deleteUsingDELETE7");
        }

        if (entityId == null) {
            throw new ApiException(400, "Missing the required parameter 'entityId' when calling deleteUsingDELETE7");
        }
        
        if (appFileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appFileId' when calling deleteUsingDELETE7");
        }

        String path = METHOD_DELETE_ENTITYAPPPROFILEPARAM.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appProfileId.toString()))
                .replaceAll("\\{" + "entityId" + "\\}", apiClient.escapeString(entityId.toString()))
                .replaceAll("\\{" + "appParamId" + "\\}", apiClient.escapeString(appFileId.toString()));

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
     *
     * @param appProfileId
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws ApiException
     */
    public List<AppParam> searchAppFileByProfile(Long appProfileId, String filter, Integer pageNumber, Integer rowsPerPage) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;
        
        // verify the required parameter 'appProfileId' is set
        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileId' when calling searchUsingPOST1");
        }

        if (filter == null) {
            throw new ApiException(400, "Missing the required parameter 'filter' when calling searchUsingPOST2");
        }

        String path = METHOD_SEARCH_FILES_PROFILE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appProfileId.toString()));

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
     *
     * @param appProfileId
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws ApiException
     */
    public List<AppParam> searchAppParamByProfile(Long appProfileId, String filter, Integer pageNumber, Integer rowsPerPage) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;
        
        // verify the required parameter 'appProfileId' is set
        if (appProfileId == null) {
            throw new ApiException(400, "Missing the required parameter 'appProfileId' when calling searchUsingPOST1");
        }

        if (filter == null) {
            throw new ApiException(400, "Missing the required parameter 'filter' when calling searchUsingPOST2");
        }

        String path = METHOD_SEARCH_PARAMS_PROFILE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(appProfileId.toString()));

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
}
