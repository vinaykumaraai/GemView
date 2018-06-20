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
import com.luretechnologies.client.restlib.service.model.LoginRequest;
import com.luretechnologies.client.restlib.service.model.LoginResponse;
import com.luretechnologies.client.restlib.service.model.PasswordUpdate;
import com.luretechnologies.client.restlib.service.model.UserSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-09-18T13:16:30.027-04:00")
public class AuthApi extends BaseApi {

    private static final String METHOD_LOGIN = "/auth/login";
    private static final String METHOD_LOGIN_EX = "/auth/loginEx";
    private static final String METHOD_LOGOUT = "/auth/userlogout";
    private static final String METHOD_FORGOT_PASSWORD = "/auth/forgotPassword";
    private static final String METHOD_FORGOT_USERNAME = "/auth/forgotUsername";
    private static final String METHOD_RESEND_CODE = "/auth/resendCode";
    private static final String METHOD_UPDATE_PASSWORD = "/auth/updatePassword";
    private static final String METHOD_UPDATE_PASSWORD_EX = "/auth/updatePasswordEx";
    private static final String METHOD_VERIFY_CODE = "/auth/verifyCode";

    /**
     * @param apiClient
     */
    public AuthApi(ApiClient apiClient) {
        super(apiClient);
    }

    /**
     * Login user Logs user into the system
     *
     * @param username Username
     * @param password Password
     * @return the user session
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public UserSession login(String username, String password) throws ApiException {

        Object postBody = null;
        byte[] postBinaryBody = null;

        if (username == null) {
            throw new ApiException(400, "Missing the required parameter 'username' when calling login");
        }

        if (password == null) {
            throw new ApiException(400, "Missing the required parameter 'password' when calling login");
        }

        String path = METHOD_LOGIN.replaceAll("\\{format\\}", "json");

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_USER_NAME, username));
        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_PASSWORD, password));

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_URLENCODED
        };

        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        TypeRef returnType = new TypeRef<LoginResponse>() {
        };

        LoginResponse loginResponse = apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

        if (loginResponse == null || !apiClient.hasAuthToken()) {
            throw new ApiException(401, "Authentication failed.");
        }

        return new UserSession(loginResponse);
    }

    /**
     * Login user Logs user into the system
     *
     * @param username Username
     * @param password Password
     * @return the user session
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public UserSession loginEx(String username, String password) throws ApiException {

        Object postBody = new LoginRequest(username, password);
        byte[] postBinaryBody = null;

        if (username == null) {
            throw new ApiException(400, "Missing the required parameter 'username' when calling loginEx");
        }

        if (password == null) {
            throw new ApiException(400, "Missing the required parameter 'password' when calling loginEx");
        }

        String path = METHOD_LOGIN_EX.replaceAll("\\{format\\}", "json");

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

        TypeRef returnType = new TypeRef<LoginResponse>() {
        };

        LoginResponse loginResponse = apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, returnType);

        if (loginResponse == null || !apiClient.hasAuthToken()) {
            throw new ApiException(401, "Authentication failed.");
        }

        return new UserSession(loginResponse);
    }

    /**
     * Resets password and sends email with temporary password
     *
     * @param email User email
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void forgotPassword(String email) throws ApiException {

        Object postBody = null;
        byte[] postBinaryBody = null;

        if (email == null) {
            throw new ApiException(400, "Missing the required parameter 'emailAddress' when calling forgotPassword");
        }

        String path = METHOD_FORGOT_PASSWORD.replaceAll("\\{format\\}", "json");

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_EMAIL, email));

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_URLENCODED
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, null);
    }

    /**
     * Sends email with username
     *
     * @param email User email
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void forgotUsername(String email) throws ApiException {

        Object postBody = null;
        byte[] postBinaryBody = null;

        if (email == null) {
            throw new ApiException(400, "Missing the required parameter 'email' when calling forgotUsername");
        }

        String path = METHOD_FORGOT_USERNAME.replaceAll("\\{format\\}", "json");

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_EMAIL, email));

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_URLENCODED
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, null);
    }

    /**
     * Resends verification code to user
     *
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void resendCode() throws ApiException {

        Object postBody = null;
        byte[] postBinaryBody = null;

        String path = METHOD_RESEND_CODE.replaceAll("\\{format\\}", "json");

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_URLENCODED
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, null);
    }

    /**
     * Verifies two factor code
     *
     * @param code The code
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void verifyCode(String code) throws ApiException {

        Object postBody = null;
        byte[] postBinaryBody = null;

        if (code == null) {
            throw new ApiException(400, "Missing the required parameter 'code' when calling verifyCode");
        }

        String path = METHOD_VERIFY_CODE.replaceAll("\\{format\\}", "json");

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_CODE, code));

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_URLENCODED
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, null);
    }

    /**
     * Updates current password
     *
     * @param currentPassword
     * @param newPassword
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void updatePassword(String email, String currentPassword, String newPassword) throws ApiException {

        Object postBody = null;
        byte[] postBinaryBody = null;

        if (email == null) {
            throw new ApiException(400, "Missing the required parameter 'email' when calling updatePassword");
        }

        if (currentPassword == null) {
            throw new ApiException(400, "Missing the required parameter 'currentPassword' when calling updatePassword");
        }

        if (newPassword == null) {
            throw new ApiException(400, "Missing the required parameter 'newPassword' when calling updatePassword");
        }

        String path = METHOD_UPDATE_PASSWORD.replaceAll("\\{format\\}", "json");

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_EMAIL, email));
        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_CURRENT_PASSWORD, currentPassword));
        queryParams.addAll(apiClient.parameterToPairs("", CommonConstants.FIELD_NEW_PASSWORD, newPassword));

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_URLENCODED
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, null);
    }

    /**
     * Updates current password
     *
     * @param email
     * @param currentPassword
     * @param newPassword
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void updatePasswordEx(String email, String currentPassword, String newPassword) throws ApiException {

        Object postBody = new PasswordUpdate(email, currentPassword, newPassword);
        byte[] postBinaryBody = null;

        if (email == null) {
            throw new ApiException(400, "Missing the required parameter 'email' when calling updatePassword");
        }

        if (currentPassword == null) {
            throw new ApiException(400, "Missing the required parameter 'currentPassword' when calling updatePassword");
        }

        if (newPassword == null) {
            throw new ApiException(400, "Missing the required parameter 'newPassword' when calling updatePassword");
        }

        String path = METHOD_UPDATE_PASSWORD_EX.replaceAll("\\{format\\}", "json");

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

        apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, null);
    }

    /**
     * Updates current password
     *
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void logout() throws ApiException {

        Object postBody = null;
        byte[] postBinaryBody = null;

        String path = METHOD_LOGOUT.replaceAll("\\{format\\}", "json");

        List<Pair> queryParams = new ArrayList<>();
        Map<String, String> headerParams = new HashMap<>();
        Map<String, Object> formParams = new HashMap<>();

        final String[] accepts = {
            CommonConstants.HEADER_APP_JSON
        };
        final String accept = apiClient.selectHeaderAccept(accepts);

        final String[] contentTypes = {
            CommonConstants.HEADER_APP_URLENCODED
        };
        final String contentType = apiClient.selectHeaderContentType(contentTypes);

        apiClient.invokeAPI(path, CommonConstants.METHOD_POST, queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, null);
    }
}
