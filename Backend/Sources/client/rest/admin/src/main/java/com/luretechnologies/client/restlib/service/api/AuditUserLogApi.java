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
import com.luretechnologies.client.restlib.service.model.AuditUserLog;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-09-18T13:16:30.027-04:00")
public class AuditUserLogApi extends BaseApi {

    private static final String METHOD_SEARCH = "/auditUserLog/search";
    private static final String AUDIT_USER_LOG_DELETE_BY_DATE = "/auditUserLog/deleteByDate/{date}";
    private static final String AUDIT_USER_LOG_DELETE = "/auditUserLog/delete/{id}";

    /**
     * @param apiClient
     */
    public AuditUserLogApi(ApiClient apiClient) {
        super(apiClient);
    }

    /**
     *
     * @param userId
     * @param entityId
     * @param filter
     * @param dateFrom
     * @param dateTo
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws ApiException
     */
    public List<AuditUserLog> searchLogs(String userId, String entityId, String filter, String dateFrom, String dateTo, Integer pageNumber, Integer rowsPerPage) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        /* if (filter == null) {
            throw new ApiException(400, "Missing the required parameter 'filter' when calling searchUsingPOST2");
        }*/
        String path = METHOD_SEARCH.replaceAll("\\{format\\}", "json");

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_USER_ID, userId));

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_ENTITY_ID, entityId));

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_FILTER, filter));

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_PAGE_NUMBER, pageNumber));

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_ROWS_PER_PAGE, rowsPerPage));

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_DATE_FROM, dateFrom));

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_DATE_TO, dateTo));

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_JSON
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<List<AuditUserLog>>() {
        };
        return apiClient.invokeAPI(path, CommonConstants.METHOD_GET, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);
    }

    /**
     * Delete user audit log to date
     *
     * @param date
     * @throws ApiException
     */
    public void deleteByDate(Date date) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'id' is set
        if (date == null ) {
            throw new ApiException(400, "Missing the required parameter 'date' when calling delete");
        }

        DateFormat format = new SimpleDateFormat("yyMMdd");
        String sData = format.format(date);
        // create path and map variables
        String path = AUDIT_USER_LOG_DELETE_BY_DATE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "date" + "\\}", apiClient.escapeString(sData));

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
     * Delete user audit
     *
     * @param id Audit lod id
     * @throws ApiException
     */
    public void delete(Long id) throws ApiException {
        Object postBody = null;
        byte[] postBinaryBody = null;

        // verify the required parameter 'id' is set
        if (id == null ) {
            throw new ApiException(400, "Missing the required parameter 'id' when calling delete");
        }

        // create path and map variables
        String path = AUDIT_USER_LOG_DELETE.replaceAll("\\{format\\}", "json")
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));;

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
