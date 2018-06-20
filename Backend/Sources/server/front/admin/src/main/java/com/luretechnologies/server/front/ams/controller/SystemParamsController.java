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
import com.luretechnologies.server.data.model.tms.App;
import com.luretechnologies.server.data.model.tms.AlertAction;
import com.luretechnologies.server.data.model.SystemParam;
import com.luretechnologies.server.service.SystemParamsService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author gemstone09
 */
@RestController
@RequestMapping("/systemParams")
@Api(value = "SystemParams")
public class SystemParamsController {

    @Autowired
    SystemParamsService systemParamsService;

    /**
     *
     * @param param
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_SYSTEM','CREATE_SYSTEM')")
    @RequestMapping(name = "create", value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "SystemParams", httpMethod = "POST", value = "Create system param", notes = "Create system param")
    public SystemParam create(
            @ApiParam(value = "System param object", required = true) @RequestBody SystemParam param
    ) throws Exception {
        return systemParamsService.create(param);
    }

    /**
     *
     * @param name
     * @param description
     * @param value
     * @param systemParamType
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_SYSTEM','CREATE_SYSTEM')")
    @RequestMapping(name = "createByParams", value = "/createByParams", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "SystemParams", httpMethod = "POST", value = "Create system param by params", notes = "Create system param by params")
    public SystemParam createByParams(
            @ApiParam(value = "The system param name", required = true) @RequestParam(value = "name", required = true) String name,
            @ApiParam(value = "The system param description", required = true) @RequestParam(value = "description", required = false) String description,
            @ApiParam(value = "The system param value", required = true) @RequestParam(value = "value", required = true) String value,
            @ApiParam(value = "The system param type (String,Integer,Boolean, Date, Float,..., by default: String)", required = false) @RequestParam(value = "systemParamType", required = false) String systemParamType
    ) throws Exception {
        if (systemParamType == null || systemParamType.isEmpty()) {
            systemParamType = "String";
        }
        return systemParamsService.create(name, description, value, systemParamType);
    }

    /**
     *
     * @param param
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_SYSTEM','UPDATE_SYSTEM')")
    @RequestMapping(name = "update", value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "SystemParams", httpMethod = "POST", value = "Update a system param", notes = "Update a system param")
    public SystemParam update(
            @ApiParam(value = "System param object", required = true) @RequestBody SystemParam param
    ) throws Exception {

        return systemParamsService.update(param);
    }

    /**
     *
     * @param id
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_SYSTEM','DELETE_SYSTEM')")
    @RequestMapping(name = "delete", value = "/delete", method = RequestMethod.DELETE)
    @ApiOperation(tags = "SystemParams", httpMethod = "DELETE", value = "Delete a system param", notes = "Delete system param")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void delete(
            @ApiParam(value = "The system param id", required = true) @RequestParam(value = "id", required = true) Long id
    ) throws Exception {
        systemParamsService.delete(id);
    }

    /**
     *
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_SYSTEM','READ_SYSTEM')")
    @RequestMapping(name = "search", value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "SystemParams", httpMethod = "GET", value = "Search system params", notes = "Search system params that match a given filter. Will return 50 records if no paging parameters defined", response = AlertAction.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<SystemParam> search(
            @ApiParam(value = "The search filter", required = false) @RequestParam(value = "filter", required = false) String filter,
            @ApiParam(value = "The page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "The rows per page", defaultValue = "50") @RequestParam(value = "rowsPerPage", required = false, defaultValue = "50") Integer rowsPerPage
    ) throws Exception {

        return systemParamsService.search(filter, pageNumber, rowsPerPage);
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_SYSTEM','READ_SYSTEM')")
    @RequestMapping(name = "getById", value = "/getById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "SystemParams", httpMethod = "GET", value = "Get system param by id", notes = "Get system param by id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = App.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public SystemParam getByID(
            @ApiParam(value = "The system param id", required = true) @RequestParam("id") Long id) throws Exception {

        return systemParamsService.get(id);
    }

    /**
     *
     * @param name
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_SYSTEM','READ_SYSTEM')")
    @RequestMapping(name = "getByName", value = "/getByName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "SystemParams", httpMethod = "GET", value = "Get system param by name", notes = "Get system param by name")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = App.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public SystemParam getByName(
            @ApiParam(value = "The system param name", required = true) @RequestParam("name") String name) throws Exception {

        return systemParamsService.getByName(name);
    }

}
