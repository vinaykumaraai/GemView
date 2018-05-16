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
import com.luretechnologies.client.restlib.service.model.SalesData;
import com.luretechnologies.client.restlib.service.model.Transaction;
import com.luretechnologies.client.restlib.service.model.TransactionData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gemstone
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-11-12T15:10:02.808-05:00")
public class DashboardApi extends BaseApi {

    /**
     *
     * @param apiClient
     */
    public DashboardApi(ApiClient apiClient) {
        super(apiClient);
    }

    /**
     * Get monthly sales
     *
     * @param date date
     * @return SalesData
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public SalesData getMonthlySales(String date) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (date == null) {
            throw new ApiException(400, "Missing the required parameter 'date' when calling getMonthlySalesUsingGET");
        }

        String path = "/dashboard/sales/month".replaceAll("\\{format\\}", "json");

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_DATE, date));

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<SalesData>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Get today's sales
     *
     * @param date date
     * @return SalesData
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public SalesData getTodaySales(String date) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (date == null) {
            throw new ApiException(400, "Missing the required parameter 'date' when calling getTodaySalesUsingGET");
        }

        String path = "/dashboard/sales/day".replaceAll("\\{format\\}", "json");

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_DATE, date));

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<SalesData>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Retrieve the latest transactions
     *
     * @param total total
     * @return List Transactions
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<Transaction> listLatestTransactions(Integer total) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (total == null) {
            throw new ApiException(400, "Missing the required parameter 'total' when calling listLatestTransactionsUsingGET");
        }

        String path = "/dashboard/transactions/latest/{total}".replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_TOTAL + "\\}", apiClient.escapeString(total.toString()));

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

    /**
     * Get Refund count and amount
     *
     * @return TransactionData
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public TransactionData getRefundInformation() throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        String path = "/dashboard/transactions/refund".replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<TransactionData>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Get total transaction count
     *
     * @return Long
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Long getTotalTransactions() throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        String path = "/dashboard/transactions/total".replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<Long>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Get Void count and amount
     *
     * @return TransactionData
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public TransactionData getVoidInformation() throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        String path = "/dashboard/transactions/void".replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<TransactionData>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }
}
