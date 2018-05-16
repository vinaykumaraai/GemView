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
import com.luretechnologies.server.data.model.User;
import com.luretechnologies.server.data.model.tms.App;
import com.luretechnologies.server.service.AppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
@Api(value = "Apps")
public class AppController {
    
    @Autowired
    AppService appService;
    
   /**
     * Creates a new App
     *
     * @param app
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Apps", httpMethod = "POST", value = "Create App", notes = "Creates a new app" )
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created", response = User.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public App create(
            @ApiParam(value = "The new app object", required = true) @RequestBody(required = true) App app) throws Exception {

        return appService.create(app);
    }
    
     /**
     * List all applications information.
     *
     * @param name
     * @param active
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Apps", httpMethod = "GET", value = "List Apps", notes = "Lists apps. Will return 50 records if no paging parameters defined", response = App.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<App> getAppList(
            @ApiParam(value = "App name") @RequestParam(required = false) String name,
            @ApiParam(value = "App status") @RequestParam(required = false) Boolean active,
            @ApiParam(value = "Page number", required = false, defaultValue = "1") @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "Rows per page", required = false, defaultValue = "50") @RequestParam(name = "rowsPerPage", defaultValue = "50") Integer rowsPerPage) throws Exception {
        return appService.list(name, active, pageNumber, rowsPerPage);
    }
    
    /**
     * Retrieve an App information
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Apps", httpMethod = "GET", value = "Get App", notes = "Get App by id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = User.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public App getAppByID(
            @ApiParam(value = "The app id", required = true) @PathVariable("id") long id) throws Exception {

        return appService.get(id);
    }
    
    /**
     * Updates an app information
     * @param id
     * @param app
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Apps", httpMethod = "PUT", value = "Update App", notes = "Updates an app")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = User.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public App update(
            @ApiParam(value = "App id", required = true) @PathVariable("id") long id,
            @ApiParam(value = "App object", required = true) @RequestBody(required = true) App app) throws Exception {

        return appService.update(id, app);
    }
    
    /**
     * Deletes a App information
     *
     * @param id
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Apps", httpMethod = "DELETE", value = "Delete App", notes = "Delete an app")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void delete(
            @ApiParam(value = "The App identifier") @PathVariable("id") long id) throws Exception {

        appService.delete(id);
    }
    
     /**
     * List all applications information.
     *
     * @param filter
     * @param active
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @RequestMapping(name = "Search", value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Apps", httpMethod = "GET", value = "Search Apps", notes = "Search apps. Will return 50 records if no paging parameters defined", response = App.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<App> search(
            @ApiParam(value = "The search filter", required = true) @RequestParam(value = "filter", required = true) String filter,
            @ApiParam(value = "App status", required = false) @RequestParam(required = false) Boolean active,
            @ApiParam(value = "The page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "The rows per page", defaultValue = "50") @RequestParam(value = "rowsPerPage", required = false, defaultValue = "50") Integer rowsPerPage) throws Exception {
        return appService.search(filter, active, pageNumber, rowsPerPage);
    }
}
