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

import com.luretechnologies.common.enums.TerminalSettingEnum;
import com.luretechnologies.server.data.display.ErrorResponse;
import com.luretechnologies.server.data.model.Terminal;
import com.luretechnologies.server.data.model.User;
import com.luretechnologies.server.data.model.payment.Host;
import com.luretechnologies.server.data.model.payment.TerminalHost;
import com.luretechnologies.server.data.model.payment.TerminalHostSettingValue;
import com.luretechnologies.server.data.model.payment.TerminalSettingValue;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Terminal Controller Class
 *
 *
 */
@RestController
@RequestMapping("/terminals")
@Api(value = "Terminals")
public class TerminalController {

    @Autowired
    TerminalService service;

    /**
     * Creates a new terminal
     *
     * @param authToken
     * @param terminal
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','CREATE_ENTITY')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Terminals", httpMethod = "POST", value = "Create terminal", notes = "Creates a new terminal")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created", response = Terminal.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Terminal create(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The new terminal object",
                    // examples = @Example(value = { @ExampleProperty(mediaType="application/json", value="[\"a\",\"b\"]")}), 
                    required = true) @RequestBody(required = true) Terminal terminal) throws Exception {

        return service.create(terminal);
    }

    /**
     * Retrieve a terminal information
     *
     * @param authToken
     * @param terminalId
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','READ_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/{terminalId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Terminals", httpMethod = "GET", value = "Get terminal", notes = "Get terminal by id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Terminal.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Terminal get(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The terminal identifier") @PathVariable("terminalId") String terminalId) throws Exception {

        return service.get(terminalId);
    }

    /**
     * Updates a terminal information
     *
     * @param authToken
     * @param terminalId
     * @param terminal
     * @return
     * @throws java.lang.Exception
     */
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/{terminalId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Terminals", httpMethod = "PUT", value = "Update terminal", notes = "Updates a terminal")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Terminal.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Terminal update(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The terminal identifier") @PathVariable("terminalId") String terminalId,
            @ApiParam(value = "The updated terminal object", required = true) @RequestBody(required = true) Terminal terminal) throws Exception {

        return service.update(terminalId, terminal);
    }

    /**
     * Deletes a terminal information
     *
     * @param authToken
     * @param terminalId
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPER')")
    @RequestMapping(value = "/{terminalId}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Terminals", httpMethod = "DELETE", value = "Delete terminal", notes = "Deletes a terminal")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void delete(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The terminal identifier") @PathVariable("terminalId") String terminalId) throws Exception {

        service.delete(terminalId);
    }

    /**
     * List all terminals information.
     *
     * @param authToken
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','READ_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Terminals", httpMethod = "GET", value = "List terminals", notes = "Lists terminals. Will return 50 records if no paging parameters defined", response = Terminal.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<Terminal> list(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "The rows per page", defaultValue = "50") @RequestParam(value = "rowsPerPage", required = false, defaultValue = "50") Integer rowsPerPage) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            UserAuth userAuth = (UserAuth) authentication.getDetails();
            User user = userAuth.getSystemUser();

            return service.list(user.getEntity(), pageNumber, rowsPerPage);
        }

        return new ArrayList<>();
    }

    /**
     * Search terminals by a given filter
     *
     * @param authToken
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','READ_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(name = "Search", value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Terminals", httpMethod = "GET", value = "Search terminals", notes = "Search terminals that match a given filter. Will return 50 records if no paging parameters defined", response = Terminal.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<Terminal> search(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The search filter", required = true) @RequestParam(value = "filter", required = true) String filter,
            @ApiParam(value = "The page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "the rows per page", defaultValue = "50") @RequestParam(value = "rowsPerPage", required = false, defaultValue = "50") Integer rowsPerPage) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            UserAuth userAuth = (UserAuth) authentication.getDetails();
            User user = userAuth.getSystemUser();

            return service.search(user.getEntity(), filter, pageNumber, rowsPerPage);

        }
        return null;
    }

    /**
     * List all Available HostEnum for a given Terminal
     *
     * @param authToken
     * @param terminalId
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','READ_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(name = "List Host", value = "/{terminalId}/hosts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Terminals", httpMethod = "GET", value = "List available host", notes = "List available host for a given terminal", response = Host.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<Host> listAvailableHosts(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The terminal identifier") @PathVariable("terminalId") String terminalId) throws Exception {

        return service.listAvailableHosts(terminalId);
    }

    /**
     * List all Terminal HostEnum Settings for a given HostEnum
     *
     * @param authToken
     * @param terminalId
     * @param hostId
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','READ_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(name = "List Host Settings", value = "/{terminalId}/hosts/{hostEnum}/settings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Terminals", httpMethod = "GET", value = "List host settings", notes = "List all terminal host settings for a given host", response = TerminalHostSettingValue.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<TerminalHostSettingValue> listHostSettings(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The terminal identifier") @PathVariable("terminalId") String terminalId,
            @ApiParam(value = "The host enum") @PathVariable("hostId") Long hostId) throws Exception {

        return service.listHostSettings(terminalId, hostId);
    }

    /**
     * Add host
     *
     * @param authToken
     * @param terminalId
     * @param hostId
     *
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/{terminalId}/addHost/{hostId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Terminals", httpMethod = "POST", value = "Add host", notes = "Add host to a terminal")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TerminalHost.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public TerminalHost addHost(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The terminal identifier") @PathVariable("terminalId") String terminalId,
            @ApiParam(value = "The host identifier") @PathVariable("hostId") Long hostId) throws Exception {

        return service.addHost(terminalId, hostId);
    }

    /**
     * Deletes host
     *
     * @param authToken
     * @param terminalId
     * @param hostId
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/{terminalId}/host/{hostId}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Terminals", httpMethod = "DELETE", value = "Delete host", notes = "Deletes a host")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteHost(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The terminal identifier") @PathVariable("terminalId") String terminalId,
            @ApiParam(value = "The host identifier") @PathVariable("hostId") Long hostId) throws Exception {

        service.deleteHost(terminalId, hostId);
    }

    /**
     * Add terminal host setting values
     *
     * @param authToken
     * @param terminalId
     * @param hostId
     * @param terminalHostSettingValue
     *
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/{terminalId}/host/{hostId}/settings", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Terminals", httpMethod = "POST", value = "Add host setting", notes = "Adds a terminal host setting", response = Terminal.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Terminal.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Terminal addHostSetting(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The terminal identifier") @PathVariable("terminalId") String terminalId,
            @ApiParam(value = "The host enum") @PathVariable("hostId") Long hostId,
            @ApiParam(value = "The new terminal host setting object", required = true) @RequestBody(required = true) TerminalHostSettingValue terminalHostSettingValue) throws Exception {

        return service.addHostSetting(terminalId, hostId, terminalHostSettingValue);
    }

    /**
     * Updates terminal host setting values
     *
     * @param authToken
     * @param terminalId
     * @param hostId
     * @param terminalHostSettingValue
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/{terminalId}/host/{hostId}/settings", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Terminals", httpMethod = "PUT", value = "Update host setting", notes = "Updates a terminal host setting")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Terminal.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Terminal updateHostSetting(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The terminal identifier") @PathVariable("terminalId") String terminalId,
            @ApiParam(value = "The host enum") @PathVariable("hostId") Long hostId,
            @ApiParam(value = "The updated terminal host setting object", required = true) @RequestBody(required = true) TerminalHostSettingValue terminalHostSettingValue) throws Exception {

        return service.updateHostSetting(terminalId, hostId, terminalHostSettingValue);
    }

    /**
     * Deletes terminal setting values
     *
     * @param authToken
     * @param terminalId
     * @param hostId
     * @param terminalHostSettingId
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/{terminalId}/host/{hostId}/settings/{terminalHostSettingId}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Terminals", httpMethod = "DELETE", value = "Delete host setting", notes = "Deletes a terminal host setting")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteHostSetting(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The terminal identifier") @PathVariable("terminalId") String terminalId,
            @ApiParam(value = "The host enum") @PathVariable("hostEnum") Long hostId,
            @ApiParam(value = "The terminal host setting id to be deleted") @PathVariable Long terminalHostSettingId) throws Exception {

        service.deleteHostSetting(terminalId, hostId, terminalHostSettingId);
    }

    /**
     * Add terminal setting values
     *
     * @param authToken
     * @param terminalId
     * @param terminalSettingValue
     *
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/{terminalId}/settings", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Terminals", httpMethod = "POST", value = "Add setting", notes = "Add setting to a terminal", response = Terminal.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Terminal.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Terminal addSetting(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The terminal identifier") @PathVariable("terminalId") String terminalId,
            @ApiParam(value = "The new terminal setting", required = true) @RequestBody(required = true) TerminalSettingValue terminalSettingValue) throws Exception {

        return service.addSetting(terminalId, terminalSettingValue);
    }

    /**
     * Updates terminal setting values
     *
     * @param authToken
     * @param terminalId
     * @param terminalSettingValue
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/{terminalId}/settings", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Terminals", httpMethod = "PUT", value = "Update terminal setting", notes = "Updates terminal values")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Terminal.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Terminal updateSetting(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The terminal identifier") @PathVariable("terminalId") String terminalId,
            @ApiParam(value = "The updated terminal setting", required = true) @RequestBody(required = true) TerminalSettingValue terminalSettingValue) throws Exception {

        return service.updateSetting(terminalId, terminalSettingValue);
    }

    /**
     * Deletes terminal setting values
     *
     * @param authToken
     * @param terminalId
     * @param terminalSettingEnum
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/{terminalId}/settings/{terminalSettingEnum}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Terminals", httpMethod = "DELETE", value = "Delete setting values", notes = "Deletes terminal setting values")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteSetting(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The terminal identifier") @PathVariable("terminalId") String terminalId,
            @ApiParam(value = "The terminal setting enum to be deleted", required = true) @PathVariable TerminalSettingEnum terminalSettingEnum) throws Exception {

        service.deleteSetting(terminalId, terminalSettingEnum);
    }

    /**
     * List all Available Settings for a given Terminal
     *
     * @param authToken
     * @param terminalId
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','READ_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(name = "List Setting", value = "/{terminalId}/settings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Terminals", httpMethod = "GET", value = "List available setting", notes = "List available setting for a given terminal", response = TerminalSettingEnum.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<TerminalSettingEnum> listAvailableSettings(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The terminal identifier") @PathVariable("terminalId") String terminalId) throws Exception {

        return service.listAvailableSettings(terminalId);
    }

//    /**
//     * Add a Payment Application to the Terminal
//     *
//     * @param serialNumber
//     * @param idApp
//     * @param idProfile
//     * @throws java.lang.Exception
//     */
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @RequestMapping(value = "/{serialNumber}/applications/{idApp}/paymentProfiles/{idProfile}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ApiOperation(tags = "Terminals", httpMethod = "POST", value = "Add payment application and profile", notes = "Adds a payment application and profile to a terminal")
//    public void addPaymentApplication(@ApiParam(value = "Terminal serial number", required = true) @PathVariable String serialNumber,
//            @ApiParam(value = "Application id", required = true) @PathVariable long idApp,
//            @ApiParam(value = "Payment profile id", required = true) @PathVariable long idProfile) throws Exception {
//
//        service.addPaymentApplication(serialNumber, idApp, idProfile);
//    }
//    /**
//     * Add a Key to the Terminal
//     *
//     * @param serialNumber
//     * @param key
//     * @return
//     * @throws java.lang.Exception
//     */
//    @RequestMapping(value = "/{serialNumber}/keys", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public Terminal addKey(@PathVariable String serialNumber, @RequestBody KeyBlock key) throws Exception {
//
//        return service.addKey(serialNumber, key);
//    }
}
