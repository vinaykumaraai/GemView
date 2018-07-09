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
package com.luretechnologies.server.front.tms.controller;

import com.luretechnologies.server.data.display.ErrorResponse;
import com.luretechnologies.server.data.display.tms.DebugDisplay;
import com.luretechnologies.server.data.display.tms.DebugItemDisplay;
import com.luretechnologies.server.data.display.tms.DebugResponseDisplay;
import com.luretechnologies.server.data.model.Terminal;
import com.luretechnologies.server.service.DebugActionService;
import com.luretechnologies.server.service.DebugService;
import com.luretechnologies.server.service.TerminalService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Debug Controller Class
 *
 *
 */
@RestController
@RequestMapping("/debug")
@Api(value = "Debugs")
public class DebugController {

    @Autowired
    DebugService debugService;

    @Autowired
    TerminalService terminalService;

    @Autowired
    DebugActionService debugActionService;

    /**
     * Creates a new debug
     *
     * @param debug
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(name = "create", value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Debugs", httpMethod = "POST", value = "Create terminal's debug", notes = "Creates a new terminal's debug")
    public DebugResponseDisplay create(@ApiParam(value = "Debug object", required = true) @RequestBody DebugDisplay debug) throws Exception {

        debugService.create(debug);
        Terminal terminal = terminalService.getBySerialNumber(debug.getSerialNumber());
        return new DebugResponseDisplay(0L, terminal.getDebugActive());
    }

    @ResponseStatus(HttpStatus.OK)
    //@PreAuthorize("hasAnyAuthority('SUPER','ALL_ODOMETER','READ_ODOMETER')")
    @RequestMapping(name = "search", value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Debugs", httpMethod = "GET", value = "Search treminal's debugs", notes = "Search terminal debugs that match a given filter. Will return 50 records if no paging parameters defined", response = DebugDisplay.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<DebugDisplay> search(
            //@ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The entity (String ID)", required = true) @RequestParam(value = "entityId", required = true) String entityId,
            @ApiParam(value = "The page number", defaultValue = "1", required = false) @RequestParam(value = "pageNumber", required = true, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "The rows per page", defaultValue = "50", required = false) @RequestParam(value = "rowsPerPage", required = true, defaultValue = "50") Integer rowsPerPage
    ) throws Exception {

        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (rowsPerPage == null) {
            rowsPerPage = Integer.MAX_VALUE;
        }
        return debugService.search(entityId, pageNumber, rowsPerPage);
    }

    @ResponseStatus(HttpStatus.OK)
    //@PreAuthorize("hasAnyAuthority('SUPER','ALL_ODOMETER','READ_ODOMETER')")
    @RequestMapping(name = "searchDebugItems", value = "/searchDebugItems", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Debugs", httpMethod = "GET", value = "Search terminal's debug items", notes = "Search terminal's debuf items that match a given filter. Will return 50 records if no paging parameters defined", response = DebugItemDisplay.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<DebugItemDisplay> searchDebugItems(
            //@ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The entity (String ID)", required = true) @RequestParam(value = "entityId", required = true) String entityId,
            @ApiParam(value = "The search filter", required = false) @RequestParam(value = "filter", required = false) String filter,
            @ApiParam(value = "The page number", defaultValue = "1", required = false) @RequestParam(value = "pageNumber", required = true, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "The rows per page", defaultValue = "50", required = false) @RequestParam(value = "rowsPerPage", required = true, defaultValue = "50") Integer rowsPerPage,
            @ApiParam(value = "The starting date, format:yyMMdd") @RequestParam(value = "dateFrom", required = false) String dateFrom,
            @ApiParam(value = "The ending date, format:yyMMdd") @RequestParam(value = "dateTo", required = false) String dateTo
    ) throws Exception {

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

        return debugService.searchDebugItems(entityId, filter, pageNumber, rowsPerPage, formatDateFrom, formatDateTo);
    }

    /**
     *
     * @param authToken
     * @param id
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@PreAuthorize("hasAnyAuthority('SUPER')")
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Debugs", httpMethod = "DELETE", value = "Delete a terminal's debug", notes = "Delete a terminal's debug")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void delete(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The debug identifier (id)") @PathVariable("id") long id) throws Exception {
        debugService.delete(id);
    }

    /**
     *
     * @param authToken
     * @param id
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@PreAuthorize("hasAnyAuthority('SUPER')")
    @RequestMapping(value = "deleteDebugItem/{id}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Debugs", httpMethod = "DELETE", value = "Delete a terminal's debug Item", notes = "Delete a terminal's debug item")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteDebugItem(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The debugItem identifier (id)") @PathVariable("id") long id) throws Exception {
        debugService.deleteDebugItem(id);
    }

}
