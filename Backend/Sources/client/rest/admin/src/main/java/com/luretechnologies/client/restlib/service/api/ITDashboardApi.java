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
import com.luretechnologies.client.restlib.service.model.Transaction;
import com.luretechnologies.client.restlib.service.model.TransactionSpeed;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Gemstone
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-12-08T17:48:07.189-05:00")
public class ITDashboardApi extends BaseApi {

    /**
     * @param apiClient
     */
    public ITDashboardApi(ApiClient apiClient) {
        super(apiClient);
    }

    /**
     * Get transaction speed
     *
     * @return TransactionSpeed
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public TransactionSpeed getTransactionSpeed() throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        String path = "/itdashboard/speed_transaction".replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<TransactionSpeed>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);
    }

    /**
     * Get transaction speed by Host
     *
     * @param host Host
     * @return TransactionSpeed
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public TransactionSpeed getTransactionSpeed(String host) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (host == null) {
            throw new ApiException(400, "Missing the required parameter 'host' when calling getTransactionSpeedUsingGET");
        }

        String path = "/itdashboard/speed_transaction/{host}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_HOST + "\\}", apiClient.escapeString(host));

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

        TypeRef returnType = new TypeRef<TransactionSpeed>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);
    }

    /**
     * List latest transactions by host
     *
     * @param host Host
     * @param count Count
     * @return Transaction list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<Transaction> listLatestTransactionsHost(String host, Integer count) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (host == null) {
            throw new ApiException(400, "Missing the required parameter 'host' when calling listLatestTransactionsHostUsingGET");
        }

        if (count == null) {
            throw new ApiException(400, "Missing the required parameter 'count' when calling listLatestTransactionsHostUsingGET");
        }

        String path = "/itdashboard/transactions/latest/{host}/{count}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_HOST + "\\}", apiClient.escapeString(host))
                .replaceAll("\\{" + CommonConstants.FIELD_COUNT + "\\}", apiClient.escapeString(count.toString()));

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

        TypeRef returnType = new TypeRef<List<Transaction>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);
    }
}
