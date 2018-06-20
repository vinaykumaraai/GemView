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
import com.luretechnologies.server.data.model.tms.EntityAppProfile;
import com.luretechnologies.server.service.EntityAppProfileService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/entityAppProfile")
@Api(value = "EntityAppProfile")
public class EntityAppProfileController {
    
    @Autowired
    EntityAppProfileService entityAppProfileService;
    
   /**
     * Creates a new EntityAppProfile
     *
     * @param entityAppProfile
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "EntityAppProfile", httpMethod = "POST", value = "Create EntityAppProfile", notes = "Creates a new entityAppProfile" )
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created", response = User.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public EntityAppProfile create(
            @ApiParam(value = "The new entityAppProfile object", required = true) @RequestBody(required = true) EntityAppProfile entityAppProfile) throws Exception {

        return entityAppProfileService.create(entityAppProfile);
    }
    
     /**
     * List all entityAppProfiles information.
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "EntityAppProfile", httpMethod = "GET", value = "List EntityAppProfiles", notes = "Get list of EntityAppProfile by ID's", response = EntityAppProfile.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<EntityAppProfile> getEntityAppProfileList(
            @ApiParam(value = "EntityAppProfile ID", required = true) @PathVariable List<Long> ids) throws Exception {
        return entityAppProfileService.list(ids);
    }
    
    /**
     * Retrieve an EntityAppProfile information
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "EntityAppProfile", httpMethod = "GET", value = "Get EntityAppProfile", notes = "Get entityAppProfile by id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = User.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public EntityAppProfile getAppByID(
            @ApiParam(value = "The EntityAppProfile id", required = true) @PathVariable("id") long id) throws Exception {

        return entityAppProfileService.get(id);
    }
    
    /**
     * Updates an EntityAppProfile information
     * @param id
     * @param entityAppProfile
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "EntityAppProfile", httpMethod = "PUT", value = "Update EntityAppProfile", notes = "Updates an entityAppProfile")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = User.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public EntityAppProfile update(
            @ApiParam(value = "EntityAppProfile id", required = true) @PathVariable("id") long id,
            @ApiParam(value = "EntityAppProfile object", required = true) @RequestBody(required = true) EntityAppProfile entityAppProfile) throws Exception {

        return entityAppProfileService.update(id, entityAppProfile);
    }
    
    /**
     * Deletes a EntityAppProfile information
     *
     * @param id
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "EntityAppProfile", httpMethod = "DELETE", value = "Delete EntityAppProfile", notes = "Delete an entityAppProfile")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void delete(
            @ApiParam(value = "The EntityAppProfile identifier") @PathVariable("id") long id) throws Exception {

        entityAppProfileService.delete(id);
    }
}

