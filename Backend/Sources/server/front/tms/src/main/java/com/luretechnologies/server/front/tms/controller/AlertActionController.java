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
import com.luretechnologies.server.data.model.tms.AlertAction;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.luretechnologies.server.service.AlertActionService;

/**
 * Heartbeat Controller Class
 *
 *
 */
@RestController
@RequestMapping("/alerts")
@Api(value = "Alerts")
public class AlertActionController {

    @Autowired
    AlertActionService alertActionService;

    @Qualifier("jmsHeartbeatTemplate")
    JmsTemplate jmsHeartbeatTemplate;

    /**
     * Create a new alert
     *
     * @param entityId
     * @param alertAction
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(name = "create", value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Alerts", httpMethod = "POST", value = "Create alert", notes = "Creates a new alert")
    public AlertAction create(
            @ApiParam(value = "The entity (String ID)", required = true) @RequestParam(value = "entityId", required = true) String entityId,
            @ApiParam(value = "Alert object", required = true) @RequestBody AlertAction alertAction
    ) throws Exception {
        return alertActionService.create(entityId, alertAction);
    }

    /**
     *
     * @param alertAction
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(name = "update", value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Alerts", httpMethod = "POST", value = "Update an alert", notes = "Update an alert")
    public AlertAction update(
            @ApiParam(value = "Alert object", required = true) @RequestBody AlertAction alertAction
    ) throws Exception {

        alertAction = alertActionService.update(alertAction);
        return alertAction;
    }

    /**
     *
     * @param id
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(name = "delete", value = "/delete", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Alerts", httpMethod = "DELETE", value = "Delete an alert", notes = "Delete an alert")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void delete(
            @ApiParam(value = "The alert id", required = true) @RequestParam(value = "id", required = true) Long id
    ) throws Exception {
        alertActionService.delete(id);
    }

    /**
     *
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
    // @PreAuthorize("hasAnyAuthority('SUPER','ALL_CLIENT','UPDATE_CLIENT','CREATE_CLIENT','READ_CLIENT')")
    @RequestMapping(name = "search", value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Alerts", httpMethod = "GET", value = "Search alerts", notes = "Search alerts that match a given filter. Will return 50 records if no paging parameters defined", response = AlertAction.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<AlertAction> search(
            @ApiParam(value = "The entity (String ID)", required = true) @RequestParam(value = "entityId", required = true) String entityId,
            @ApiParam(value = "The search filter", required = false) @RequestParam(value = "filter", required = false) String filter,
            @ApiParam(value = "The page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "The rows per page", defaultValue = "50") @RequestParam(value = "rowsPerPage", required = false, defaultValue = "50") Integer rowsPerPage,
            @ApiParam(value = "The starting date") @RequestParam(value = "dateFrom", required = false) String dateFrom,
            @ApiParam(value = "The ending date") @RequestParam(value = "dateTo", required = false) String dateTo
    ) throws Exception {

        DateFormat format = new SimpleDateFormat("yyMMdd");
        if (dateTo == null || dateTo.isEmpty()) {
            Date a = new Date();
            dateTo = format.format(a);
        }
        if (dateFrom == null || dateFrom.isEmpty()) {
            dateFrom = "180501";
        }
        Date formatDateFrom = format.parse(dateFrom);
        Date formatDateTo = format.parse(dateTo);

        return alertActionService.search(entityId, filter, pageNumber, rowsPerPage, formatDateFrom, formatDateTo);
    }

}
