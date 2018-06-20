/**
 * COPYRIGHT @ Lure Technologies, LLC.
 * ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or
 * form other than in accordance with and subject to the terms of a written
 * license from Lure or with the prior written consent of Lure or as
 * permitted by applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure.  If you are neither the
 * intended recipient, nor an agent, employee, nor independent contractor
 * responsible for delivering this message to the intended recipient, you are
 * prohibited from copying, disclosing, distributing, disseminating, and/or
 * using the information in this email in any manner. If you have received
 * this message in error, please advise us immediately at
 * legal@luretechnologies.com by return email and then delete the message from your
 * computer and all other records (whether electronic, hard copy, or
 * otherwise).
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
import com.luretechnologies.client.restlib.service.filters.TransactionFilter;
import com.luretechnologies.client.restlib.service.model.Transaction;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gemstone
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-09-15T15:20:45.337-04:00")
public class TransactionsApi extends BaseApi {

    private static final String METHOD_TRANSACTIONS = "/transactions";
    private static final String METHOD_SEARCH = "/transactions/search";
    private static final String METHOD_GET_TRANSACTION = "/transactions/{id}";
    private static final String METHOD_ADD_SIGNATURE = "/transactions/{id}/signature";
    private static final String METHOD_GET_SIGNATURE = "/transactions/{id}/signature";

    /**
     * @param apiClient
     */
    public TransactionsApi(ApiClient apiClient) {
        super(apiClient);
    }

    /**
     * List transactions Lists transactions. Will return 50 records if no paging
     * parameters defined
     *
     * @param pageNumber Page number
     * @param rowsPerPage Rows per page
     * @return Transactions list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<Transaction> getTransactions(Integer pageNumber, Integer rowsPerPage) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        String path = METHOD_TRANSACTIONS.replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<List<Transaction>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);
    }

    /**
     * List transactions of the given entity. Will return 50 records if no
     * paging parameters defined
     *
     * @param id the entity id
     * @param pageNumber Page number
     * @param rowsPerPage Rows per page
     * @return Transactions list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<Transaction> getTransactionsByEntity(long id, Integer pageNumber, Integer rowsPerPage) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        String path = "/transactions/byEntity/".replaceAll("\\{format\\}", "json");

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_ID, id));

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

        TypeRef returnType = new TypeRef<List<Transaction>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);
    }

    /**
     * Search transactions Search transactions that match a given filter. Will
     * return 50 records if no paging parameters defined
     *
     * @param filter filter
     * @param pageNumber Page number
     * @param rowsPerPage Rows per page
     * @return Transactions list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<Transaction> searchTransactions(TransactionFilter filter, Integer pageNumber, Integer rowsPerPage) throws ApiException {
        return searchTransactions(filter.toString(), pageNumber, rowsPerPage);
    }

    /**
     * Search transactions Search transactions that match a given filter. Will
     * return 50 records if no paging parameters defined
     *
     * @param filter filter
     * @param pageNumber Page number
     * @param rowsPerPage Rows per page
     * @return Transactions list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<Transaction> searchTransactions(String filter, Integer pageNumber, Integer rowsPerPage) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (filter == null) {
            throw new ApiException(400, "Missing the required parameter 'filter' when calling searchTransactions");
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

        TypeRef returnType = new TypeRef<List<Transaction>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);
    }

    /**
     * Search transactions Search transactions that match a given filter. Will
     * return 50 records if no paging parameters defined
     *
     * @param filter filter
     * @param id the entity id
     * @param pageNumber Page number
     * @param rowsPerPage Rows per page
     * @return Transactions list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<Transaction> searchTransactionsByEntity(Long id, TransactionFilter filter, Integer pageNumber, Integer rowsPerPage) throws ApiException {
        return searchTransactionsByEntity(id, filter.toString(), pageNumber, rowsPerPage);
    }

    /**
     * Search transactions Search transactions that match a given filter. Will
     * return 50 records if no paging parameters defined
     *
     * @param filter filter
     * @param id the entity id
     * @param pageNumber Page number
     * @param rowsPerPage Rows per page
     * @return Transactions list
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public List<Transaction> searchTransactionsByEntity(Long id, String filter, Integer pageNumber, Integer rowsPerPage) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (filter == null) {
            throw new ApiException(400, "Missing the required parameter 'filter' when calling searchTransactions");
        }

        String path = "/transactions/searchByEntity/".replaceAll("\\{format\\}", "json");

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_ID, id));
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
        TypeRef returnType = new TypeRef<List<Transaction>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);
    }

    /**
     * Get transaction Get transaction by id
     *
     * @param id Transaction id
     * @return Transaction
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Transaction getTransaction(String id) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (id == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling getUsingGET9");
        }

        String path = METHOD_GET_TRANSACTION.replaceAll("\\{format\\}", "json").replaceAll("\\{" + CommonConstants.FIELD_ID + "\\}", apiClient.escapeString(id));

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

        TypeRef returnType = new TypeRef<Transaction>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

    }

    /**
     * Get signature Get the transaction signature image
     *
     * @param id Transaction id
     * @return String
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public String getSignature(Long id) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (id == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling getSignatureUsingGET");
        }

        String path = METHOD_GET_SIGNATURE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_ID + "\\}", apiClient.escapeString(id.toString()));

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

        TypeRef returnType = new TypeRef<String>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);
    }

    /**
     * Add signature Add signature image to transaction
     *
     * @param id Transaction id
     * @param image Image file
     * @return Transaction
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public Transaction addSignature(Long id, File image) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        if (id == null) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling addSignatureUsingPOST");
        }

        if (image == null) {
            throw new ApiException(400, "Missing the required parameter 'image' when calling addSignatureUsingPOST");
        }

        String path = METHOD_ADD_SIGNATURE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + CommonConstants.FIELD_ID + "\\}", apiClient.escapeString(id.toString()));

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        formParams.put(CommonConstants.FIELD_IMAGE, image);

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_MULTIPART_FORM_DATA
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<Transaction>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);
    }
}
