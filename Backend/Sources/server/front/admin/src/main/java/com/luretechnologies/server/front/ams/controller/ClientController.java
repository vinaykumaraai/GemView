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
import com.luretechnologies.server.data.model.User;
import com.luretechnologies.server.data.model.payment.Client;
import com.luretechnologies.server.service.ClientService;
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
 *
 *
 * @author developer
 */
@RestController
@RequestMapping("/clients")
@Api(value = "Clients")
public class ClientController {

    @Autowired
    ClientService service;

    /**
     * Creates a new client
     *
     * @param authToken
     * @param client
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_CLIENT','CREATE_CLIENT')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Clients", httpMethod = "POST", value = "Create client", notes = "Creates a new client")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created", response = Client.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Client create(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The new client object", required = true) @RequestBody(required = true) Client client) throws Exception {
        return service.create(client);
    }

    /**
     * Retrieve a client information
     *
     * @param authToken
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_CLIENT','UPDATE_CLIENT','CREATE_CLIENT','READ_CLIENT')")
    @RequestMapping(value = "/{clientId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Clients", httpMethod = "GET", value = "Get client", notes = "Get client by id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Client.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Client get(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The client identifier") @PathVariable("clientId") long id) throws Exception {
        return service.get(id);
    }

    /**
     * Updates a client information
     *
     * @param authToken
     * @param client
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_CLIENT','UPDATE_CLIENT','CREATE_CLIENT')")
    @RequestMapping(value = "/{clientId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Clients", httpMethod = "PUT", value = "Update client", notes = "Updates a client")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Client.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Client update(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The client identifier") @PathVariable("clientId") long id,
            @ApiParam(value = "The updated client object", required = true) @RequestBody(required = true) Client client) throws Exception {
        return service.update(id, client);
    }

    /**
     * Deletes a client information
     *
     * @param authToken
     * @param id
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPER')")
    @RequestMapping(value = "/{clientId}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Clients", httpMethod = "DELETE", value = "Delete client", notes = "Deletes a client")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void delete(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The client identifier") @PathVariable("clientId") long id) throws Exception {
        service.delete(id);
    }

    /**
     * List all clients information.
     *
     * @param authToken
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_CLIENT','UPDATE_CLIENT','CREATE_CLIENT','READ_CLIENT')")
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Clients", httpMethod = "GET", value = "List clients", notes = "Lists clients. Will return 50 records if no paging parameters defined", response = Client.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<Client> list(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "The rows per page", defaultValue = "50") @RequestParam(value = "rowsPerPage", required = false, defaultValue = "50") Integer rowsPerPage) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            UserAuth userAuth = (UserAuth) authentication.getDetails();
            User user = userAuth.getSystemUser();

            return service.list(user.getEntity(), pageNumber, rowsPerPage);
        }
        return null;
    }

    /**
     * Search clients by a given filter
     *
     * @param authToken
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_CLIENT','UPDATE_CLIENT','CREATE_CLIENT','READ_CLIENT')")
    @RequestMapping(name = "Search", value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Clients", httpMethod = "GET", value = "Search clients", notes = "Search clients that match a given filter. Will return 50 records if no paging parameters defined", response = Client.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<Client> search(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The search filter", required = true) @RequestParam(value = "filter", required = true) String filter,
            @ApiParam(value = "The page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "The rows per page", defaultValue = "50") @RequestParam(value = "rowsPerPage", required = false, defaultValue = "50") Integer rowsPerPage) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            UserAuth userAuth = (UserAuth) authentication.getDetails();
            User user = userAuth.getSystemUser();

            return service.search(user.getEntity(), filter, pageNumber, rowsPerPage);
        }
        return null;
    }
}
