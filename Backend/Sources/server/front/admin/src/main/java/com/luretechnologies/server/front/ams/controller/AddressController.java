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
import com.luretechnologies.server.data.model.payment.Address;
import com.luretechnologies.server.data.model.payment.Country;
import com.luretechnologies.server.data.model.payment.State;
import com.luretechnologies.server.service.AddressService;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/address")
@RestController(value = "Address")
@Api(value = "Address")
public class AddressController {

    @Autowired
    AddressService service;

    /**
     * Creates a new Address
     *
     * @param authToken
     * @param address
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/createAddress", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Address", httpMethod = "POST", value = "Create Address", notes = "Creates a new Address")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created", response = Address.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Address createAddress(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The new address object", required = true) @RequestBody(required = true) Address address) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            return service.createAddress(address);
        }
        return null;
    }

    /**
     * Update an Address
     *
     * @param authToken
     * @param addressId
     * @param address
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/updateAddress/{addressId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Address", httpMethod = "POST", value = "Update Address", notes = "Update an Address")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Address.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Address updateAddress(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The address identifier") @PathVariable("addressId") long addressId,
            @ApiParam(value = "The address object", required = true) @RequestBody(required = true) Address address) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            return service.updateAddress(addressId, address);
        }
        return null;
    }

    /**
     * List all countries information.
     *
     * @param authToken
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/countries", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Address", httpMethod = "GET", value = "List countries", notes = "Lists countries. ", response = Country.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<Country> countries(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            return service.getCountries();
        }

        return new ArrayList<>();
    }

    /**
     * List all states by country.
     *
     * @param authToken
     * @param countryId
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/statesByCountry/{countryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Address", httpMethod = "GET", value = "List countries", notes = "Lists states by country. ", response = State.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<State> statesByCountry(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The terminal identifier") @PathVariable("countryId") int countryId) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            return service.getStatesByCountry(countryId);
        }

        return new ArrayList<>();
    }

}
