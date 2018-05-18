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

import com.luretechnologies.common.enums.PermissionEnum;
import com.luretechnologies.server.data.display.ErrorResponse;
import com.luretechnologies.server.data.model.Role;
import com.luretechnologies.server.service.RoleService;
import com.luretechnologies.server.utils.UserAuth;
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
 * Role Controller Class
 *
 *
 */
@RestController
@RequestMapping("/roles")
@Api(value = "Roles")
public class RoleController {

    @Autowired
    RoleService service;

    /**
     * Creates a new role
     *
     * @param authToken
     * @param role
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ROLE','CREATE_ROLE')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Roles", httpMethod = "POST", value = "Create role", notes = "Creates a new role")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created", response = Role.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Role create(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The new role object", required = true) @RequestBody(required = true) Role role) throws Exception {

        return service.create(role);
    }

    /**
     * Retrieve a role information
     *
     * @param authToken
     * @param roleId
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Roles", httpMethod = "GET", value = "Get role", notes = "Get role by id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Role.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Role get(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The role identifier") @PathVariable("roleId") long roleId) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getDetails() instanceof UserAuth) {
            return service.get(roleId);
        }
        return null;
    }

    /**
     * Updates a role information
     *
     * @param authToken
     * @param roleId
     * @param role
     * @return
     * @throws java.lang.Exception
     */
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ROLE','UPDATE_ROLE','CREATE_ROLE')")
    @RequestMapping(value = "/{roleId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Roles", httpMethod = "PUT", value = "Update role", notes = "Updates a role")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Role.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Role update(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The role identifier") @PathVariable("roleId") long roleId,
            @ApiParam(value = "The updated role object", required = true) @RequestBody(required = true) Role role) throws Exception {

        return service.update(roleId, role);
    }

    /**
     * Deletes a role information
     *
     * @param authToken
     * @param roleId
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ROLE','DELETE_ROLE')")
    @RequestMapping(value = "/{roleId}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Roles", httpMethod = "DELETE", value = "Delete role", notes = "Deletes a role")
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
            @ApiParam(value = "The role identifier") @PathVariable("roleId") long roleId) throws Exception {

        service.delete(roleId);
    }

    /**
     * Adds a permission to a role
     *
     * @param authToken
     * @param roleId
     * @param permissionEnum
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ROLE','UPDATE_ROLE','CREATE_ROLE')")
    @RequestMapping(value = "/{roleId}/permissions/{permissionEnum}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Roles", httpMethod = "POST", value = "Add permission", notes = "Add permission to a role")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Role.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Role addPermission(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The role identifier") @PathVariable("roleId") long roleId,
            @ApiParam(value = "The permission enum to be added") @PathVariable("permissionEnum") PermissionEnum permissionEnum) throws Exception {

        return service.addPermission(roleId, permissionEnum);

    }

    /**
     * Removes a permission from a role
     *
     * @param authToken
     * @param roleId
     * @param permissionEnum
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ROLE','UPDATE_ROLE','CREATE_ROLE')")
    @RequestMapping(value = "/{roleId}/permissions/{permissionEnum}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Roles", httpMethod = "DELETE", value = "Delete permission", notes = "Delete permission from a role")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Role.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Role removePermission(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The role identifier") @PathVariable("roleId") long roleId,
            @ApiParam(value = "The permission enum to be removed") @PathVariable("permissionEnum") PermissionEnum permissionEnum) throws Exception {

        return service.removePermission(roleId, permissionEnum);
    }

    /**
     * List all roles information.
     *
     * @param authToken
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ROLE','READ_ROLE','UPDATE_ROLE','CREATE_ROLE')")
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Roles", httpMethod = "GET", value = "List roles", notes = "Lists roles. Will return 50 records if no paging parameters defined", response = Role.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<Role> list(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "the rows per page", defaultValue = "50") @RequestParam(value = "rowsPerPage", required = false, defaultValue = "50") Integer rowsPerPage) throws Exception {

        return service.list(pageNumber, rowsPerPage);
    }

    /**
     * Search roles by a given filter
     *
     * @param authToken
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ROLE','READ_ROLE','UPDATE_ROLE','CREATE_ROLE')")
    @RequestMapping(name = "Search", value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Roles", httpMethod = "GET", value = "Search roles", notes = "Search roles that match a given filter. Will return 50 records if no paging parameters defined", response = Role.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<Role> search(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The search filter", required = true) @RequestParam(value = "filter", required = true) String filter,
            @ApiParam(value = "The page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "The rows per page", defaultValue = "50") @RequestParam(value = "rowsPerPage", required = false, defaultValue = "50") Integer rowsPerPage) throws Exception {

        return service.search(filter, pageNumber, rowsPerPage);
    }
}
