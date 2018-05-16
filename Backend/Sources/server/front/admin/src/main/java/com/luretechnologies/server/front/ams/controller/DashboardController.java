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

import com.luretechnologies.server.constraints.DateFormatValid;
import com.luretechnologies.server.data.display.ErrorResponse;
import com.luretechnologies.server.data.display.payment.dashboard.SalesData;
import com.luretechnologies.server.data.display.payment.dashboard.TransactionData;
import com.luretechnologies.server.data.model.User;
import com.luretechnologies.server.data.model.payment.Transaction;
import com.luretechnologies.server.service.TransactionService;
import com.luretechnologies.server.utils.UserAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author developer
 */
@RestController
@RequestMapping("/dashboard")
@Api(value = "Dashboard")
@Validated
public class DashboardController {

    @Autowired
    TransactionService transactionService;

    /**
     * Retrieve the latest transactions
     *
     * @param authToken
     * @param total The total transactions to retrieve.
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','READ_DASHBOARD')")
    @RequestMapping(value = "/transactions/latest/{total}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Dashboard", httpMethod = "GET", value = "List latest transactions", notes = "List latest transactions", response = Transaction.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<Transaction> listLatestTransactions(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The total transactions to retrieve", required = true) @PathVariable("total") int total) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            UserAuth userAuth = (UserAuth) authentication.getDetails();
            User user = userAuth.getSystemUser();

            return transactionService.listLatestTransactions(user.getEntity(), total);
        }

        return new ArrayList<>();
    }

    /**
     * Retrieve the total transaction count
     *
     * @param authToken
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','READ_DASHBOARD')")
    @RequestMapping(value = "/transactions/total", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Dashboard", httpMethod = "GET", value = "Get total transaction count", notes = "Get total transaction count")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Long getTotalTransactions(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            UserAuth userAuth = (UserAuth) authentication.getDetails();
            User user = userAuth.getSystemUser();

            return transactionService.getTotalTransactions(user.getEntity());
        }

        return 0l;
    }

    /**
     * Retrieve Void count and amount
     *
     * @param authToken
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','READ_DASHBOARD')")
    @RequestMapping(value = "/transactions/void", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Dashboard", httpMethod = "GET", value = "Get Void count and amount", notes = "Get Void count and amount")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public TransactionData getVoidInformation(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            UserAuth userAuth = (UserAuth) authentication.getDetails();
            User user = userAuth.getSystemUser();

            return transactionService.getVoidInformation(user.getEntity());
        }
        return null;
    }

    /**
     * Retrieve Void count and amount
     *
     * @param authToken
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','READ_DASHBOARD')")
    @RequestMapping(value = "/transactions/refund", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Dashboard", httpMethod = "GET", value = "Get Refund count and amount", notes = "Get Refund count and amount")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public TransactionData getRefundInformation(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            UserAuth userAuth = (UserAuth) authentication.getDetails();
            User user = userAuth.getSystemUser();

            return transactionService.getRefundInformation(user.getEntity());
        }
        return null;
    }

    /**
     * Get sales summary for a given day
     *
     * @param authToken
     * @param date The date. Format: yyMMdd
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','READ_DASHBOARD')")
    @RequestMapping(value = "/sales/day", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Dashboard", httpMethod = "GET", value = "Get day sales", notes = "Gets the sales summary for a given day")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public SalesData getTodaySales(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The date in format: yyMMdd") @DateFormatValid @RequestParam(value = "date", required = true) String date) throws Exception {

        DateFormat format = new SimpleDateFormat("yyMMdd");
        Date formatDate = format.parse(date);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            UserAuth userAuth = (UserAuth) authentication.getDetails();
            User user = userAuth.getSystemUser();
            return transactionService.getTodaySales(user.getEntity(), formatDate);
        }

        return null;
    }

    /**
     * Get sales summary for a given month
     *
     * @param authToken
     * @param date The date. Format: yyMMdd
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','READ_DASHBOARD')")
    @RequestMapping(value = "/sales/month", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Dashboard", httpMethod = "GET", value = "Get month sales", notes = "Gets the sales summary for a given month")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public SalesData getMonthlySales(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The date in format: yyMMdd") @DateFormatValid @RequestParam(value = "date", required = true) String date) throws Exception {

        DateFormat format = new SimpleDateFormat("yyMMdd");
        Date formatDate = format.parse(date);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            UserAuth userAuth = (UserAuth) authentication.getDetails();
            User user = userAuth.getSystemUser();
            return transactionService.getMonthlySales(user.getEntity(), formatDate);
        }

        return null;
    }
}
