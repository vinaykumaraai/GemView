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
import com.luretechnologies.client.restlib.service.model.Region;
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
public class RegionsApi extends BaseApi {

    /**
     *
     * @param apiClient
     */
    public RegionsApi(ApiClient apiClient) {
        super(apiClient);
    }

    /**
     * List regions Lists all regions
     *
     * @param pageNumber
     * @param rowsPerPage
     * @return Region
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<Region> getRegions(Integer pageNumber, Integer rowsPerPage) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // create path and map variables
        String path = "/regions".replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<List<Region>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Create region Creates a new region
     *
     * @param region
     * @return Region
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Region createRegion(Region region) throws ApiException {
        Object postBody = region;
        byte[] postBinaryBody = null;

        String path = "/regions".replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<Region>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Search regions Search reginos that match a given filter
     *
     * @param filter filter
     * @param pageNumber pageNumber
     * @param rowsPerPage rowsPerPage
     * @return Region
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<Region> searchRegions(String filter, Integer pageNumber, Integer rowsPerPage) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (filter == null) {
            throw new ApiException(400, "Missing the required parameter 'filter' when calling searchUsingPOST4");
        }

        String path = "/regions/search".replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<List<Region>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Get region Get region by id
     *
     * @param regionId
     * @return Region
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Region getRegion(String regionId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (regionId == null) {
            throw new ApiException(400, "Missing the required parameter 'regionId' when calling getUsingGET5");
        }

        String path = "/regions/{regionId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_REGION_ID + "\\}", apiClient.escapeString(regionId));

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

        TypeRef returnType = new TypeRef<Region>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Update region Updates a region
     *
     * @param regionId
     * @param region
     * @return Region
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Region updateRegion(String regionId, Region region) throws ApiException {
        Object postBody = region;
        byte[] postBinaryBody = null;

        if (regionId == null) {
            throw new ApiException(400, "Missing the required parameter 'regionId' when calling updateUsingPUT4");
        }

        String path = "/regions/{regionId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "regionId" + "\\}", apiClient.escapeString(regionId));

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

        TypeRef returnType = new TypeRef<Region>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_PUT, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Delete region Deletes a region
     *
     * @param regionId
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void deleteRegion(String regionId) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (regionId == null) {
            throw new ApiException(400, "Missing the required parameter 'regionId' when calling deleteUsingDELETE4");
        }

        String path = "/regions/{regionId}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_REGION_ID + "\\}", apiClient.escapeString(regionId));

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
