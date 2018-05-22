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
package com.luretechnologies.server.front.ams.controller;

import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.display.ErrorResponse;
import com.luretechnologies.server.data.model.User;
import com.luretechnologies.server.data.model.payment.LoginResponse;
import com.luretechnologies.server.data.model.tms.Email;
import com.luretechnologies.server.jms.utils.CorrelationIdPostProcessor;
import com.luretechnologies.server.service.AuthService;
import com.luretechnologies.server.service.SessionService;
import com.luretechnologies.server.service.UserService;
import com.luretechnologies.server.service.VerificationCodeService;
import com.luretechnologies.server.utils.TokenAuthService;
import com.luretechnologies.server.utils.UserAuth;
import com.luretechnologies.server.utils.UserAuthService;
import com.luretechnologies.server.utils.UserAuthentication;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication Controller Class
 *
 *
 */
@RequestMapping("/auth")
@RestController(value = "Authentication")
@Api(value = "Authentication")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UserAuthService userAuthService;

    @Autowired
    TokenAuthService tokenAuthService;

    @Autowired
    VerificationCodeService verificationCodeService;

    @Autowired
    UserService userService;

    @Autowired
    SessionService sessionService;

    @Autowired
    @Qualifier("jmsEmailTemplate")
    JmsTemplate jmsEmailTemplate;

    /**
     * Authenticate a User
     *
     * @param username
     * @param password
     * @param httpResponse
     * @return
     * @throws java.lang.Exception
     */
//    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(name = "Login", value = "/login", params = {"username", "password"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Authentication", httpMethod = "POST", value = "Login user", notes = "Logs user into the system")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", responseHeaders = @ResponseHeader(name = "X-Auth-Token", description = "Authentication token returned by the system", response = String.class), response = LoginResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public LoginResponse login(
            @ApiParam(value = "The username", required = true) @RequestParam("username") String username,
            @ApiParam(value = "The password", required = true) @RequestParam("password") String password,
            @ApiParam(hidden = true) HttpServletResponse httpResponse) throws Exception {

        // Validate user credentials
        User user = authService.login(username, password);

        if (user.getActive() == false) {
            throw new Exception("User is not active!");
        }

        // Create the Authentication User
        UserAuthentication userAuth = new UserAuthentication(userAuthService.loadUser(user));

        boolean requireTwoFactorAuth = user.isRequireTwoFactor();
        boolean requirePasswordUpdate = user.isRequirePasswordUpdate();

        String audience;

        if (requirePasswordUpdate) {

            audience = tokenAuthService.AUDIENCE_PASSWORD_UPDATE;

        } else if (requireTwoFactorAuth) {

            // Send Email with Verification Code
            audience = tokenAuthService.AUDIENCE_TWO_FACTOR;

            try {
                Email email = verificationCodeService.sendVerifyCode(user);
                jmsEmailTemplate.convertAndSend(email, new CorrelationIdPostProcessor(Utils.generateGUID()));
            } catch (JmsException ex) {
                throw new NoSuchElementException("No Data exist " + ex.getMessage());
            }
        } else {
            audience = tokenAuthService.AUDIENCE_GENERAL_USE;
        }

        // Set X-Auth-Token header
        String token = tokenAuthService.createToken(userAuth, audience);
        httpResponse.addHeader(TokenAuthService.AUTH_HEADER_NAME, token);

        sessionService.createSession(user, token);

        LoginResponse response = new LoginResponse();
        response.setPerformTwoFactor(requireTwoFactorAuth);
        response.setRequirePasswordUpdate(requirePasswordUpdate);
        response.setMaskedEmailAddress(Utils.maskEmailAddress(user.getEmail()));

        return response;
    }

    /**
     *
     * @param authToken
     * @param httpRequest
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(name = "Logout", value = "/logout", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Authentication", httpMethod = "PUT", value = "Logout user", notes = "Logout user")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "OK")
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void logout(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(hidden = true) HttpServletRequest httpRequest) throws Exception {
        sessionService.deleteSession(authToken);
    }

    /**
     *
     * @param authToken
     * @param httpRequest
     * @param httpResponse
     * @param code
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(name = "Verification", value = "/verifyCode", params = {"code"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Authentication", httpMethod = "POST", value = "Verification code", notes = "Verification code an user")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void verifyCode(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(hidden = true) HttpServletRequest httpRequest,
            @ApiParam(hidden = true) HttpServletResponse httpResponse,
            @ApiParam(value = "The verification code", required = true) @RequestParam("code") String code) throws Exception {

        UserAuth userAuth = tokenAuthService.getUser(httpRequest);

        if (userAuth != null) {
            if (verificationCodeService.validateVerificationCode(userAuth.getSystemUser(), code)) {
                User user = userAuth.getSystemUser();
                UserAuthentication userAuthentication = new UserAuthentication(userAuthService.loadUser(user));
                String audience = tokenAuthService.AUDIENCE_GENERAL_USE;
                String token = tokenAuthService.createToken(userAuthentication, audience);
                httpResponse.addHeader(TokenAuthService.AUTH_HEADER_NAME, token);
                sessionService.createSession(user, token);
            }
        }
    }

    /**
     *
     * @param emailId
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(name = "Forgot", value = "/forgotPassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Authentication", httpMethod = "POST", value = "Forgot password", notes = "Send temporary password by email to user")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "OK")
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void forgotPassword(
            @ApiParam(value = "The user email", required = true) @RequestParam("emailId") String emailId) throws Exception {
        try {
            User retrievedUser = userService.findUserByEmailId(emailId);
            if (null != retrievedUser && null != retrievedUser.getEmail() && !retrievedUser.getEmail().isEmpty()) {
                userService.sendTemporaryPassword(retrievedUser);
            }
        } catch (Exception ex) {
            throw new NoSuchElementException("No Data exist " + ex.getMessage());
        }
    }

    /**
     *
     * @param emailId
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(name = "ForgotUser", value = "/forgotUsername", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Authentication", httpMethod = "POST", value = "Forgot username", notes = "Send username by email to user")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "OK")
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void forgotUsername(
            @ApiParam(value = "The user email", required = true) @RequestParam("emailId") String emailId) throws Exception {
        try {
            User retrievedUser = userService.findUserByEmailId(emailId);
            if (retrievedUser != null) {
                userService.sendUsername(retrievedUser);
            }
        } catch (Exception ex) {
            throw new NoSuchElementException("No Data exist " + ex.getMessage());
        }
    }

    /**
     * @param emailId
     * @param tempPassword
     * @param newPassword
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(name = "Update", value = "/updatePassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Authentication", httpMethod = "POST", value = "Update Password", notes = "Validate the temporary password and post the new password by the user")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "OK")
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public User updatePassword(
            @ApiParam(value = "The user email", required = true) @RequestParam("emailId") String emailId,
            @ApiParam(value = "The temporary password", required = true) @RequestParam("tempPassword") String tempPassword,
            @ApiParam(value = "The new password", required = true) @RequestParam("newPassword") String newPassword) throws Exception {

        User retrievedUser = userService.findUserByEmailId(emailId);
        if (null != retrievedUser && null != retrievedUser.getEmail() && !retrievedUser.getEmail().isEmpty()) {
            return userService.updatePassword(retrievedUser, tempPassword, newPassword);
        }
        return null;
    }
}