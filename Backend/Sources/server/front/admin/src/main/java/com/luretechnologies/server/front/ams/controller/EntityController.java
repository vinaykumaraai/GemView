/**
 * COPYRIGHT @ Lure Technologies, LLC. ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or form
 * other than in accordance with and subject to the terms of a written license
 * from Lure or with the prior written consent of Lure or as permitted by
 * applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure. If you are neither the intended
 * recipient, nor an agent, employee, nor independent contractor responsible for
 * delivering this message to the intended recipient, you are prohibited from
 * copying, disclosing, distributing, disseminating, and/or using the
 * information in this email in any manner. If you have received this message in
 * error, please advise us immediately at legal@luretechnologies.com by return
 * email and then delete the message from your computer and all other records
 * (whether electronic, hard copy, or otherwise).
 *
 * Any copies or reproductions of this software program (in whole or in part)
 * made by any method must also include a copy of this legend.
 *
 * Inquiries should be made to legal@luretechnologies.com
 *
 */
package com.luretechnologies.server.front.ams.controller;

import com.luretechnologies.server.data.display.ErrorResponse;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.EntityNode;
import com.luretechnologies.server.data.model.User;
import com.luretechnologies.server.service.DeviceService;
import com.luretechnologies.server.service.EntityService;
import com.luretechnologies.server.service.MerchantService;
import com.luretechnologies.server.service.OrganizationService;
import com.luretechnologies.server.service.RegionService;
import com.luretechnologies.server.service.TerminalService;
import com.luretechnologies.server.utils.UserAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.luretechnologies.server.service.AuditUserLogService;

/**
 *
 *
 * @author developer
 */
@RestController
@RequestMapping("/entities")
@Api(value = "Entities")
public class EntityController {

    @Autowired
    EntityService service;

    @Autowired
    DeviceService deviceService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    TerminalService terminalService;

    @Autowired
    RegionService regionService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    AuditUserLogService auditUserLogService;

    /**
     * Retrieve an entity
     *
     * @param authToken
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','READ_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Entities", httpMethod = "GET", value = "Get entity by id", notes = "Get entity by id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = EntityNode.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Entity findById(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The id") @PathVariable("id") long id) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            UserAuth userAuth = (UserAuth) authentication.getDetails();
            User user = userAuth.getSystemUser();

            return service.findById(id);
        }
        return null;
    }

    /**
     * Move entity to new parent
     *
     * @param authToken
     * @param id
     * @param parentId
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/move", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Entities", httpMethod = "POST", value = "Move entity to new parent", notes = "Move entity to new parent")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Entity.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Entity move(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The entity id") @RequestParam("id") long id,
            @ApiParam(value = "The new parent id") @RequestParam("parentId") long parentId) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            UserAuth userAuth = (UserAuth) authentication.getDetails();
            User user = userAuth.getSystemUser();
            auditUserLogService.createAuditUserLog(user.getId(), id, "info", "move", "Move entity to new parent");
        }

        if (authentication.getDetails() instanceof UserAuth) {
            return service.move(id, parentId);
        }

        return null;
    }

    /**
     * Copy entity to new parent
     *
     * @param authToken
     * @param entityId
     * @param parentId
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/copy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Entities", httpMethod = "POST", value = "Copy entity to new parent", notes = "Copy entity to new parent")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Entity.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Entity copy(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The entity id") @RequestParam("id") String entityId,
            @ApiParam(value = "The new parent id") @RequestParam("parentId") long parentId) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            Entity copy = service.findByEntityId(entityId);
            Entity paste = null;
            switch (copy.getType()) {
                case ORGANIZATION:
                    paste = organizationService.copy(entityId, parentId);
                    break;
                case REGION:
                    paste = regionService.copy(entityId, parentId);
                    break;
                case MERCHANT:
                    paste = merchantService.copy(entityId, parentId);
                    break;
                case TERMINAL:
                    paste = terminalService.copy(entityId, parentId);
                    break;
                case DEVICE:
                    paste = deviceService.copy(entityId, parentId);
                    break;
            }
            if (paste != null) {
                paste.setParentId(parentId);
            }
            if (authentication.getDetails() instanceof UserAuth) {
                UserAuth userAuth = (UserAuth) authentication.getDetails();
                User user = userAuth.getSystemUser();
                auditUserLogService.createAuditUserLog(user.getId(), entityId , "info", "copy", "Copy entity to new parent");
            }

            return paste;
        }
        return null;
    }

    /**
     * Retrieve an entity hierarchy
     *
     * @param authToken
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','READ_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/entityHierarchy/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Entities", httpMethod = "GET", value = "Get entity hierarchyby id", notes = "Get entity hierarchy by id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = EntityNode.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public EntityNode getEntityHierarchy(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The entity id") @PathVariable("id") long id) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            UserAuth userAuth = (UserAuth) authentication.getDetails();
            User user = userAuth.getSystemUser();

            return service.getEntityHierarchy(user.getEntity(), id);
        }
        return null;
    }

    /**
     * Retrieve an user entity hierarchy
     *
     * @param authToken
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','READ_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/entityHierarchy", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Entities", httpMethod = "GET", value = "Get entity hierarchy", notes = "Get logged user's entity hierarchy")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = EntityNode.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public EntityNode getEntityHierarchy(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            UserAuth userAuth = (UserAuth) authentication.getDetails();
            User user = userAuth.getSystemUser();

            return service.getEntityHierarchy(user.getEntity(), user.getEntity().getId());
        }
        return null;
    }

    /**
     * Get entity children
     *
     * @param authToken
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','READ_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/entityChildren/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Entities", httpMethod = "GET", value = "Get entity children", notes = "Get entity children by id", response = Entity.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<Entity> getEntityChildren(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The entity id") @PathVariable("id") long id) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            UserAuth userAuth = (UserAuth) authentication.getDetails();
            User user = userAuth.getSystemUser();

            return service.getEntityChildren(user.getEntity(), id);
        }
        return new ArrayList<>();
    }

}
