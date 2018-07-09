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

import com.luretechnologies.common.enums.EntityTypeEnum;
import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.display.ErrorResponse;
import com.luretechnologies.server.data.display.tms.HeartbeatDisplay;
import com.luretechnologies.server.data.display.tms.HeartbeatAlertDisplay;
import com.luretechnologies.server.data.display.tms.HeartbeatAuditDisplay;
import com.luretechnologies.server.data.display.tms.HeartbeatOdometerDisplay;
import com.luretechnologies.server.data.display.tms.HeartbeatResponseDisplay;
import com.luretechnologies.server.data.model.Terminal;
import com.luretechnologies.server.data.model.tms.AlertAction;
import com.luretechnologies.server.data.model.tms.Email;
import com.luretechnologies.server.jms.utils.CorrelationIdPostProcessor;
import com.luretechnologies.server.service.AlertActionService;
import com.luretechnologies.server.service.EntityService;
import com.luretechnologies.server.service.HeartbeatAlertService;
import com.luretechnologies.server.service.HeartbeatAuditService;
import com.luretechnologies.server.service.HeartbeatOdometerService;
import com.luretechnologies.server.service.HeartbeatResponseService;
import com.luretechnologies.server.service.HeartbeatService;
import com.luretechnologies.server.service.SystemParamsService;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Heartbeat Controller Class
 *
 *
 */
@RestController
@RequestMapping("/heartbeat")
@Api(value = "Heartbeats")
public class HeartbeatController {

    @Autowired
    HeartbeatOdometerService heartbeatOdometerService;

    @Autowired
    HeartbeatAlertService heartbeatAlertService;

    @Autowired
    HeartbeatAuditService heartbeatAuditService;

    @Autowired
    HeartbeatService heartbeatService;

    @Autowired
    HeartbeatResponseService heartbeatResponseService;

    @Autowired
    EntityService entityService;

    @Autowired
    TerminalService terminalService;

    @Autowired
    AlertActionService alertActionService;

    @Autowired
    SystemParamsService systemParamsService;

    @Autowired
    @Qualifier("jmsHeartbeatTemplate")
    JmsTemplate jmsHeartbeatTemplate;

    @Autowired
    @Qualifier("jmsEmailTemplate")
    JmsTemplate jmsEmailTemplate;

    private static final String ALERT_EMAIL_BODY = "Alert Email Body";

    /**
     * Creates a new heartbeat
     *
     * @param heartbeat
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(name = "create", value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Heartbeats", httpMethod = "POST", value = "Create heartbeat", notes = "Creates a new heartbeat")
    public HeartbeatResponseDisplay create(@ApiParam(value = "Heartbeat object", required = true) @RequestBody HeartbeatDisplay heartbeat) throws Exception {

        try {
            jmsHeartbeatTemplate.convertAndSend(heartbeat, new CorrelationIdPostProcessor(Utils.generateGUID()));
        } catch (Exception exception) {
            //TODO it is cause it doesn't work fo me (remove this comment latter)
            heartbeatService.create(heartbeat);
        }

        HeartbeatResponseDisplay heartbeatResponse = heartbeatResponseService.getLastOne(heartbeat.getSerialNumber());
        if (heartbeatResponse != null) {
            heartbeatResponseService.delete(heartbeatResponse.getId());
            return heartbeatResponse;
        } else {
            return new HeartbeatResponseDisplay(0, "OK", Utils.MS_MINUTE * 15, false);
        }
    }

    /**
     *
     * @param terminalSerialNumber
     * @param heartbeatResponse
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(name = "createHeartbeatResponse", value = "/createHeartbeatResponse", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Heartbeats", httpMethod = "POST", value = "(It is test purpose) Create heartbeat reponse ", notes = "Creates a new heartbeat response (test purpose)")
    public HeartbeatResponseDisplay createHeartResponse(
            @ApiParam(value = "The entity (Terminal's serial number)", required = true) @RequestParam(value = "terminalSerialNumber", required = true) String terminalSerialNumber,
            @ApiParam(value = "Heartbeat Response  object", required = true) @RequestBody HeartbeatResponseDisplay heartbeatResponse
    ) throws Exception {
        return heartbeatResponseService.create(terminalSerialNumber, heartbeatResponse);
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
    //@PreAuthorize("hasAnyAuthority('SUPER','ALL_ODOMETER','READ_ODOMETER')")
    @RequestMapping(name = "searchOdometers", value = "/searchOdometers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Heartbeats", httpMethod = "GET", value = "Search heartbeat Odometers", notes = "Search odometers that match a given filter. Will return 50 records if no paging parameters defined", response = HeartbeatOdometerDisplay.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<HeartbeatOdometerDisplay> searchOdometers(
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

        return heartbeatOdometerService.search(entityId, filter, pageNumber, rowsPerPage, formatDateFrom, formatDateTo);
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
    //@PreAuthorize("hasAnyAuthority('SUPER','ALL_ASSET','READ_ASSET')")
    @RequestMapping(name = "searchAlerts", value = "/searchAlerts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Heartbeats", httpMethod = "GET", value = "Search heartbeat Alerts", notes = "Search alerts that match a given filter. Will return 50 records if no paging parameters defined", response = HeartbeatAlertDisplay.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<HeartbeatAlertDisplay> searchAlerts(
            //@ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The entity (String ID)", required = true) @RequestParam(value = "entityId", required = true) String entityId,
            @ApiParam(value = "The search filter", required = false) @RequestParam(value = "filter", required = false) String filter,
            @ApiParam(value = "The page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "The rows per page", defaultValue = "50") @RequestParam(value = "rowsPerPage", required = false, defaultValue = "50") Integer rowsPerPage,
            @ApiParam(value = "The starting date, format:yyMMdd") @RequestParam(value = "dateFrom", required = false) String dateFrom,
            @ApiParam(value = "The ending date format:yyMMdd") @RequestParam(value = "dateTo", required = false) String dateTo
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

        return heartbeatAlertService.search(entityId, filter, pageNumber, rowsPerPage, formatDateFrom, formatDateTo);
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
    //@PreAuthorize("hasAnyAuthority('SUPER','ALL_ASSET','READ_ASSET')")
    @RequestMapping(name = "searchAudits", value = "/searchAudits", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Heartbeats", httpMethod = "GET", value = "Search heartbeat audits", notes = "Search audits that match a given filter. Will return 50 records if no paging parameters defined", response = HeartbeatAuditDisplay.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<HeartbeatAuditDisplay> searchAudits(
            //@ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The entity (String ID)", required = true) @RequestParam(value = "entityId", required = true) String entityId,
            @ApiParam(value = "The search filter", required = false) @RequestParam(value = "filter", required = false) String filter,
            @ApiParam(value = "The page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "The rows per page", defaultValue = "50") @RequestParam(value = "rowsPerPage", required = false, defaultValue = "50") Integer rowsPerPage,
            @ApiParam(value = "The starting date format:yyMMdd") @RequestParam(value = "dateFrom", required = false) String dateFrom,
            @ApiParam(value = "The ending date format:yyMMdd") @RequestParam(value = "dateTo", required = false) String dateTo
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

        return heartbeatAuditService.search(entityId, filter, pageNumber, rowsPerPage, formatDateFrom, formatDateTo);
    }

    @ResponseStatus(HttpStatus.OK)
    //@PreAuthorize("hasAnyAuthority('SUPER','ALL_HEARTBEAT','READ_HEARTBEAT')")
    @RequestMapping(name = "searchHeartbeats", value = "/searchHeartbeats", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Heartbeats", httpMethod = "GET", value = "Search heartbeat ", notes = "Search heartbeats that match a given filter. Will return 50 records if no paging parameters defined", response = HeartbeatDisplay.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<HeartbeatDisplay> searchHeartbeats(
            //@ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The entity (String ID)", required = true) @RequestParam(value = "entityId", required = true) String entityId,
            @ApiParam(value = "The search filter", required = false) @RequestParam(value = "filter", required = false) String filter,
            @ApiParam(value = "The page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "The rows per page", defaultValue = "50") @RequestParam(value = "rowsPerPage", required = false, defaultValue = "50") Integer rowsPerPage,
            @ApiParam(value = "The starting date format:yyMMdd") @RequestParam(value = "dateFrom", required = false) String dateFrom,
            @ApiParam(value = "The ending date format:yyMMdd") @RequestParam(value = "dateTo", required = false) String dateTo
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

        return heartbeatService.search(entityId, filter, pageNumber, rowsPerPage, formatDateFrom, formatDateTo);
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
    @RequestMapping(value = "deleteOdometer/{id}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Heartbeats", httpMethod = "DELETE", value = "Delete an odometer", notes = "Delete an odometer")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteOdometer(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The odometer identifier (id)") @PathVariable("id") long id) throws Exception {
        heartbeatOdometerService.delete(id);
    }

    /**
     * Delete all heartbeat audit older than date
     *
     * @param authToken
     * @param date Death line
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@PreAuthorize("hasAnyAuthority('SUPER')")
    @RequestMapping(value = "deleteAudits/{date}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Heartbeats", httpMethod = "DELETE", value = "Delete heartbeat audits older than date", notes = "Delete heartbeat audits older than date")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteAudits(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "Death line, format:yyMMdd") @PathVariable("date") String date) throws Exception {

        DateFormat format = new SimpleDateFormat("yyMMdd");
        if (date == null) {
            throw new Exception("The has be int the parameters");
        }
        Date formatDateFrom = format.parse(date);

        heartbeatAuditService.delete(formatDateFrom);
    }

    /**
     * Delete an alert.
     *
     * @param authToken
     * @param id
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@PreAuthorize("hasAnyAuthority('SUPER')")
    @RequestMapping(value = "deleteAlert/{id}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Heartbeats", httpMethod = "DELETE", value = "Delete a heartbeat alert", notes = "Delete a heartbeat alert")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteAlert(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The odometer identifier (id)") @PathVariable("id") long id) throws Exception {
        heartbeatAlertService.delete(id);
    }

    /**
     * Delete an audit.
     *
     * @param authToken
     * @param id
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@PreAuthorize("hasAnyAuthority('SUPER')")
    @RequestMapping(value = "deleteAudit/{id}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Heartbeats", httpMethod = "DELETE", value = "Delete a heartbeat audit log", notes = "Delete a heartbeat audit log")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteAudit(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The odometer identifier (id)") @PathVariable("id") long id) throws Exception {
        heartbeatAuditService.delete(id);
    }

    /**
     * Test
     *
     * @param authToken
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "alertsProcessing", method = RequestMethod.POST)
    @ApiOperation(tags = "Heartbeats", httpMethod = "POST", value = "Alerts processing (just a test purposes web-service)", notes = "Alerts processing (just a test purposes web-service)")
    public void alertsProcessing(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken) throws Exception {

        List<AlertAction> alertActions = alertActionService.getAlerts();

        for (AlertAction alertAction : alertActions) {
            if (alertAction.getEntity().getType() == EntityTypeEnum.TERMINAL) {
                List<HeartbeatAlertDisplay> alertDisplays = heartbeatAlertService.getAlerts(((Terminal) alertAction.getEntity()).getSerialNumber(), alertAction.getLabel());
                if (alertDisplays != null && alertDisplays.size() > 0) {
                    for (HeartbeatAlertDisplay temp : alertDisplays) {
                        sendAlertEmail(alertAction.getEmail(), ((Terminal) alertAction.getEntity()).getEntityId(), temp.getComponent(), temp.getLabel(), temp.getOccurred().toString());
                        heartbeatAlertService.alertDone(temp.getId());
                    }
                }
            } else {
                List<Terminal> terminals = terminalService.search(alertAction.getEntity(), null, 0, Integer.MAX_VALUE);
                for (Terminal terminal : terminals) {
                    List<HeartbeatAlertDisplay> alertDisplays = heartbeatAlertService.getAlerts(terminal.getSerialNumber(), alertAction.getLabel());
                    if (alertDisplays != null && alertDisplays.size() > 0) {
                        for (HeartbeatAlertDisplay temp : alertDisplays) {
                            sendAlertEmail(alertAction.getEmail(), terminal.getEntityId(), temp.getComponent(), temp.getLabel(), temp.getOccurred().toString());
                            heartbeatAlertService.alertDone(temp.getId());
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param emailAddress
     * @param terminalId
     * @param component
     * @param alertLabel
     * @param occurredAt
     */
    private void sendAlertEmail(String emailAddress, String terminalId, String component, String alertLabel, String occurredAt) throws Exception {

        Email email = new Email();

        String fromName = "DoNotReply";
        String subject = "Alert: {ALERT_LABEL}".replaceAll("\\{ALERT_LABEL\\}", alertLabel);

        String bodyMessage = null;
        try {
            bodyMessage = systemParamsService.getByName(ALERT_EMAIL_BODY).getValue();
        } catch (Exception ex) {

        }
        if ( bodyMessage == null || bodyMessage.isEmpty() ){
            throw new Exception("The bodyMessage couldn't be empty.Please define \"Alert Email Body\" system param");
        }
         
        bodyMessage = bodyMessage.replaceAll("\\{TERMINAL_ID\\}", terminalId);
        bodyMessage = bodyMessage.replaceAll("\\{COMPONENT\\}", component);
        bodyMessage = bodyMessage.replaceAll("\\{ALERT_LABEL\\}", alertLabel);
        bodyMessage = bodyMessage.replaceAll("\\{OCCURRED_AT\\}", occurredAt);

        email.setBody(bodyMessage);
        email.setContentType("text/html");
        email.setTo(emailAddress);
        email.setSubject(subject);
        email.setFromName(fromName);

        jmsEmailTemplate.convertAndSend(email, new CorrelationIdPostProcessor(Utils.generateGUID()));
    }
}
