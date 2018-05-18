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

import com.luretechnologies.server.data.model.Role;
import com.luretechnologies.server.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
     * @param role
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Roles", httpMethod = "POST", value = "Create role", notes = "Creates a new role")
    public Role create(@ApiParam(value = "Role object") @RequestBody(required = true) Role role) throws Exception {

        return service.create(role);
    }

    /**
     * Retrieve a role information
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Roles", httpMethod = "GET", value = "Get role", notes = "Get role by id")
    public Role get(@ApiParam(value = "Role id", required = true) @PathVariable("id") long id) throws Exception {

        return service.get(id);
    }

    /**
     * Updates a role information
     *
     * @param role
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Roles", httpMethod = "PUT", value = "Update role", notes = "Updates a role")
    public Role update(@ApiParam(value = "Role id", required = true) @PathVariable("id") long id,
            @ApiParam(value = "Role object") @RequestBody(required = true) Role role) throws Exception {

        return service.update(id, role);
    }

    /**
     * Deletes a role information
     *
     * @param id
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Roles", httpMethod = "DELETE", value = "Delete role", notes = "Deletes a role")
    public void delete(@ApiParam(value = "Role id", required = true) @PathVariable("id") long id) throws Exception {

        service.delete(id);
    }

    /**
     * List all roles information.
     *
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Roles", httpMethod = "GET", value = "List roles", notes = "Lists roles. Will return 50 records if no paging parameters defined", response = Role.class, responseContainer = "List")
    public List<Role> list(@ApiParam(value = "Page number", required = false, defaultValue = "1") @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "Rows per page", required = false, defaultValue = "50") @RequestParam(name = "rowsPerPage", defaultValue = "50") Integer rowsPerPage) throws Exception {

        return service.list(pageNumber, rowsPerPage);
    }

//    /**
//     * Adds a privilege to a role
//     *
//     * @param id
//     * @param permission
//     * @return
//     * @throws java.lang.Exception
//     */
//    @RequestMapping(value = "/{id}/permissions/{permission}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//     @ApiOperation(tags = "Roles", httpMethod = "POST", value = "Add permission", notes = "Add permission to a role")
//    public Role addPermission(@PathVariable("id") long id, @PathVariable("permission") PermissionEnum permission) throws Exception {
//
//        return service.addPermission(id, permission);
//
//    }
//
//    /**
//     * Removes a privilege from a role
//     *
//     * @param id
//     * @param permission
//     * @throws java.lang.Exception
//     */
//    @RequestMapping(value = "/{id}/permissions/{permission}", method = RequestMethod.DELETE)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deletePermission(@PathVariable("id") long id, @PathVariable("permission") PermissionEnum permission) throws Exception {
//
//        service.deletePermission(id, permission);
//    }
}
