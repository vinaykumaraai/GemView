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

import com.luretechnologies.server.data.display.ErrorResponse;
import com.luretechnologies.server.data.model.AuditUserLog;
import com.luretechnologies.server.data.model.User;
import com.luretechnologies.server.service.AuditUserLogService;
import com.luretechnologies.server.utils.UserAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Vinay
 */
@RestController
@RequestMapping("/auditUserLog")
@Api(value = "Audit User Logs")
public class AuditUserLogController {

    @Autowired
    AuditUserLogService auditUserLogService;

    /**
     * Creates a new auditUserLog
     *
     * @param auditUserLog
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_AUDIT','CREATE_AUDIT')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AuditUserLog", httpMethod = "POST", value = "Create AudirUserLog")
    public AuditUserLog create(
            @ApiParam(value = "The new auditUserLog object", required = true) @RequestBody(required = true) AuditUserLog auditUserLog) throws Exception {

        return auditUserLogService.createAuditUserLog(auditUserLog);
    }

    /**
     * Retrieve an AppFile information
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_AUDIT','READ_AUDIT')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AuditUserLog", httpMethod = "GET", value = "Get AuditUserLog", notes = "Get AuditUserLog by id")
    public AuditUserLog getAuditUserLogByID(@ApiParam(value = "AuditUserLog ID", required = true) @PathVariable long id) throws Exception {

        return auditUserLogService.getAuditUserLogByID(id);
    }

    /**
     * Retrieve an List of AppFile information
     *
     * @param ids
     * @return
     * @throws java.lang.Exception
     */
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_AUDIT','READ_AUDIT')")
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AuditUserLog", httpMethod = "GET", value = "Get AuditUserLog List", notes = "Get list pf AuditUserLog by ID's")
    public List<AuditUserLog> getAuditUserLogList(@ApiParam(value = "AuditUserLog ID", required = true) @PathVariable List<Long> ids) throws Exception {

        return auditUserLogService.getAuditUserLogList(ids);
    }

    /**
     * Search user audit log
     *
     * @param authToken
     * @param userId
     * @param entityId
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @param dateFrom
     * @param dateTo
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_AUDIT','READ_AUDIT')")
    @RequestMapping(name = "Search", value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AuditUserLog", httpMethod = "GET", value = "Search User Logs", notes = "Search logs that match a given filter. Will return 50 records if no paging parameters defined", response = AuditUserLog.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<AuditUserLog> search(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The user", required = false) @RequestParam(value = "userId", required = false) String userId,
            @ApiParam(value = "The entity", required = false) @RequestParam(value = "entityId", required = false) String entityId,
            @ApiParam(value = "The search filter", required = false) @RequestParam(value = "filter", required = false) String filter,
            @ApiParam(value = "The page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "The rows per page", defaultValue = "50") @RequestParam(value = "rowsPerPage", required = false, defaultValue = "50") Integer rowsPerPage,
            @ApiParam(value = "The starting date, format:yyMMdd") @RequestParam(value = "dateFrom", required = false) String dateFrom,
            @ApiParam(value = "The ending date, format:yyMMdd") @RequestParam(value = "dateTo", required = false) String dateTo
    ) throws Exception {

        Long lUserId = null;
        Long lUserLoginId = null;

        if (pageNumber == null) {
            pageNumber = 1;
        }

        if (rowsPerPage == null) {
            rowsPerPage = Integer.MAX_VALUE;
        }
        DateFormat formatHour = new SimpleDateFormat("yyMMddHHmm");
        DateFormat format = new SimpleDateFormat("yyMMdd");
        if (dateTo == null || dateTo.isEmpty()) {
            Date a = new Date();
            dateTo = format.format(a);
        }

        if (dateFrom == null || dateFrom.isEmpty()) {
            dateFrom = "180501";
        }
        Date formatDateFrom = format.parse(dateFrom);
        Date formatDateTo = formatHour.parse(dateTo + "2359");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            UserAuth userAuth = (UserAuth) authentication.getDetails();
            User user = userAuth.getSystemUser();
            lUserLoginId = user.getId();
        }
        if (userId != null) {
            lUserId = Long.parseLong(userId, 10);
        }

        return auditUserLogService.search(lUserLoginId, lUserId, entityId, filter, pageNumber, rowsPerPage, formatDateFrom, formatDateTo);
    }

    /**
     *
     * @param authToken
     * @param date
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@PreAuthorize("hasAnyAuthority('SUPER')")
    @RequestMapping( value = "deleteByDate/{date}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "AuditUserLog", httpMethod = "DELETE", value = "Delete users logs", notes = "Deletes users logs")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteByName(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(name = "name", value = "All logs older than date are going to delete. Format: yyMMdd", required = true) @PathVariable("date") String date) throws Exception {

        DateFormat format = new SimpleDateFormat("yyMMdd");
        if (date == null || date.isEmpty()) {
            throw new Exception("Date has to be set");
        }
        Date dateValue = format.parse(date);

        auditUserLogService.deleteLogs(dateValue);
    }
   /**
     * Delete an odometer.
     *
     * @param authToken
     * @param id
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@PreAuthorize("hasAnyAuthority('SUPER')")
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "AuditUserLog", httpMethod = "DELETE", value = "Delete an audit user log", notes = "Delete an audit user log")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deletelog(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The odometer identifier (id)") @PathVariable("id") long id) throws Exception {
        auditUserLogService.delete(id);
    }        
}
