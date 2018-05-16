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
import com.luretechnologies.server.data.model.payment.Telephone;
import com.luretechnologies.server.service.TelephoneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Address Controller Class
 *
 *
 */
@RequestMapping("/telephone")
@RestController(value = "Telephone")
@Api(value = "Telephone")
public class TelephoneController {

    @Autowired
    TelephoneService service;

    /**
     * Creates a new Telephone
     *
     * @param authToken
     * @param telephone
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_TELEPHONES')")
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Telephone", httpMethod = "POST", value = "Create telephone", notes = "Creates a new Telephone")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created", response = Telephone.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Telephone create(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The new telephone object", required = true) @RequestBody(required = true) Telephone telephone) throws Exception {

        return service.create(telephone);
    }

}
