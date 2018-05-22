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

import com.luretechnologies.common.enums.SettingEnum;
import com.luretechnologies.common.enums.SettingGroupEnum;
import com.luretechnologies.server.data.display.ErrorResponse;
import com.luretechnologies.server.data.model.payment.Setting;
import com.luretechnologies.server.service.SettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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
 */
@RestController
@RequestMapping("/settings")
@Api(value = "Settings")
public class SettingController {

    @Autowired
    SettingService service;

    /**
     * Creates a new setting
     *
     * @param authToken
     * @param setting
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_SETTING','CREATE_SETTING')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Settings", httpMethod = "POST", value = "Create setting", notes = "Creates a new setting")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created", response = Setting.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Setting create(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The new setting object", required = true) @RequestBody(required = true) Setting setting) throws Exception {

        return service.create(setting);
    }

    /**
     * Retrieve a setting information
     *
     * @param authToken
     * @param settingEnum
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_SETTING','READ_SETTING')")
    @RequestMapping(value = "/{settingEnum}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Settings", httpMethod = "GET", value = "Get setting", notes = "Get setting by enum")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Setting.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Setting get(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The setting enum") @PathVariable("settingId") SettingEnum settingEnum) throws Exception {

        return service.get(settingEnum);
    }

    /**
     * Updates a setting information
     *
     * @param authToken
     * @param setting
     * @return
     * @throws java.lang.Exception
     */
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_SETTING','UPDATE_SETTING','CREATE_SETTING')")
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Settings", httpMethod = "PUT", value = "Update setting", notes = "Updates a setting")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Setting.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Setting update(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The updated setting object", required = true) @RequestBody(required = true) Setting setting) throws Exception {

        return service.update(setting);
    }

    /**
     * Deletes a setting information
     *
     * @param authToken
     * @param settingEnum
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_SETTING','DELETE_SETTING')")
    @RequestMapping(value = "/{settingEnum}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Settings", httpMethod = "DELETE", value = "Delete setting", notes = "Deletes a setting")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void delete(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The setting enum to be deleted") @PathVariable("settingEnum") SettingEnum settingEnum) throws Exception {

        service.delete(settingEnum);
    }

    /**
     * List all settings information.
     *
     * @param authToken
     * @param groupEnum
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_SETTING','READ_SETTING')")
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Settings", httpMethod = "GET", value = "List settings", notes = "Lists settings. Will return 50 records if no paging parameters defined", response = Setting.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<Setting> list(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The group enum") @RequestParam(value = "groupEnum", required = false) SettingGroupEnum groupEnum,
            @ApiParam(value = "The page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "The rows per page", defaultValue = "50") @RequestParam(value = "rowsPerPage", required = false, defaultValue = "50") Integer rowsPerPage) throws Exception {

        return service.list(groupEnum, pageNumber, rowsPerPage);
    }

}