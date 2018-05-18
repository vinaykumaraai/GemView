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
import com.luretechnologies.server.data.model.payment.Host;
import com.luretechnologies.server.data.model.payment.HostModeOperation;
import com.luretechnologies.server.data.model.payment.HostSettingValue;
import com.luretechnologies.server.data.model.payment.MerchantHostSetting;
import com.luretechnologies.server.data.model.payment.TerminalHostSetting;
import com.luretechnologies.server.service.HostService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/hosts")
@Api(value = "Hosts")
public class HostController {

    @Autowired
    HostService service;

    /**
     * Retrieve a host information by key
     *
     * @param authToken
     * @param key
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','READ_HOST','CREATE_HOST','UPDATE_HOST')")
    @RequestMapping(value = "/getHostByKey/{key}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Hosts", httpMethod = "GET", value = "Get host by key", notes = "Get host by key")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Host.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Host getHostbyKey(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The host key") @PathVariable("key") String key) throws Exception {

        return service.findByKey(key);
    }
    
    /**
     * List all host.
     *
     * @param authToken
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','READ_HOST','CREATE_HOST','UPDATE_HOST')")
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Hosts", httpMethod = "GET", value = "List hosts", notes = "Lists hosts.", response = Host.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<Host> list(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken) throws Exception {

        return service.list();
    }
    
    /**
     * Creates a new host
     *
     * @param authToken
     * @param host
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','CREATE_HOST')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Hosts", httpMethod = "POST", value = "Create host", notes = "Creates a new host")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created", response = Host.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Host create(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The new host object", required = true) @RequestBody(required = true) Host host) throws Exception {

        return service.create(host);
    }
    
    /**
     * Updates a host information
     *
     * @param authToken
     * @param host
     * @param hostId
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','CREATE_HOST','UPDATE_HOST')")
    @RequestMapping(value = "/{hostId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Hosts", httpMethod = "PUT", value = "Update host", notes = "Updates a host")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Host.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Host update(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The host identifier") @PathVariable("hostId") Long hostId,
            @ApiParam(value = "The updated merchant object", required = true) @RequestBody(required = true) Host host) throws Exception {

        return service.update(hostId, host);
    }
    
    // <editor-fold desc="Settings" defaultstate="collapsed">

    /**
     * List all HostEnum Settings
     *
     * @param authToken
     * @param hostId
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','READ_HOST','CREATE_HOST','UPDATE_HOST')")
    @RequestMapping(value = "/{hostId}/settingValues", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Hosts", httpMethod = "GET", value = "List settings", notes = "List settings pertaining to a host", response = HostSettingValue.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<HostSettingValue> getSettingValues(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The host id") @PathVariable("hostId") Long hostId) throws Exception {

        return service.findSettingValues(hostId);
    }

    /**
     * Add host setting values
     *
     * @param authToken
     * @param hostId
     * @param hostSettingValue
     *
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','CREATE_HOST','UPDATE_HOST')")
    @RequestMapping(value = "/{hostId}/settings", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Hosts", httpMethod = "POST", value = "Add setting", notes = "Add setting to a host", response = HostSettingValue.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = HostSettingValue.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public HostSettingValue addSetting(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The host id") @PathVariable("hostId") Long hostId,
            @ApiParam(value = "The new host setting object", required = true) @RequestBody(required = true) HostSettingValue hostSettingValue) throws Exception {

        return service.addSetting(hostId, hostSettingValue);
    }

    /**
     * Updates host setting values
     *
     * @param authToken
     * @param hostId
     * @param hostSettingValue
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','CREATE_HOST','UPDATE_HOST')")
    @RequestMapping(value = "/{hostId}/settings", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Hosts", httpMethod = "PUT", value = "Update host setting", notes = "Updates host setting values")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = HostSettingValue.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public HostSettingValue updateSetting(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The host id") @PathVariable("hostId") Long hostId,
            @ApiParam(value = "The updated host setting object", required = true) @RequestBody(required = true) HostSettingValue hostSettingValue) throws Exception {

        return service.updateSetting(hostId, hostSettingValue);
    }

    /**
     * Deletes host setting values
     *
     * @param authToken
     * @param hostId
     * @param key
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','CREATE_HOST','UPDATE_HOST')")
    @RequestMapping(value = "/{hostId}/settings/{key}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Hosts", httpMethod = "DELETE", value = "Delete setting values", notes = "Deletes host setting values")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteSetting(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The host id") @PathVariable("hostId") Long hostId,
            @ApiParam(value = "The host key to be deleted") @PathVariable("key") String key) throws Exception {

        service.deleteSetting(hostId, key);
    }
    
    // </editor-fold>
        
    // <editor-fold desc="Merchant settings" defaultstate="collapsed">
    
    /**
     * List all merchant Settings by host id
     *
     * @param authToken
     * @param hostId
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','READ_HOST','CREATE_HOST','UPDATE_HOST')")
    @RequestMapping(value = "/{hostId}/merchantSettings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Hosts", httpMethod = "GET", value = "List merchant settings", notes = "List all merchant Settings by host id", response = MerchantHostSetting.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<MerchantHostSetting> listMerchantSettings(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The host id") @PathVariable("hostId") Long hostId) throws Exception {

        return service.listMerchantSettings(hostId);
    }
    
    /**
     * Get merchant host setting 
     *
     * @param authToken
     * @param hostId
     * @param key
     *
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','READ_HOST','CREATE_HOST','UPDATE_HOST')")
    @RequestMapping(value = "/{hostId}/getMerchantSetting/{key}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Hosts", httpMethod = "GET", value = "Get merchant setting", notes = "Get merchant setting")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = MerchantHostSetting.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public MerchantHostSetting getMerchantSetting(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The host id") @PathVariable("hostId") Long hostId,
            @ApiParam(value = "The setting key") @PathVariable("key") String key) throws Exception {

        return service.getMerchantSetting(hostId, key);
    }
    
    /**
     * Add merchant host setting 
     *
     * @param authToken
     * @param hostId
     * @param merchantSetting
     *
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','CREATE_HOST','UPDATE_HOST')")
    @RequestMapping(value = "/{hostId}/addMerchantSetting", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Hosts", httpMethod = "POST", value = "Add merchant setting", notes = "Add merchant setting to a host", response = MerchantHostSetting.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = MerchantHostSetting.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public MerchantHostSetting addMerchantSetting(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The host id") @PathVariable("hostId") Long hostId,
            @ApiParam(value = "The new merchant setting", required = true) @RequestBody(required = true) MerchantHostSetting merchantSetting) throws Exception {

        return service.addMerchantSetting(hostId, merchantSetting);
    }
    
    /**
     * Delete merchant host setting 
     *
     * @param authToken
     * @param merchantHostSettingId
     *
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','CREATE_HOST','UPDATE_HOST')")
    @RequestMapping(value = "/deleteMerchantSetting/{merchantHostSettingId}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Hosts", httpMethod = "DELETE", value = "Delete merchant setting", notes = "Delete merchant setting")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteMerchantSetting(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The merchant host setting id") @PathVariable("merchantHostSettingId") Long merchantHostSettingId) throws Exception {

        service.deleteMerchantSetting(merchantHostSettingId);
    }

    /**
     * Updates host merchant setting 
     *
     * @param authToken
     * @param hostId
     * @param setting
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','CREATE_HOST','UPDATE_HOST')")
    @RequestMapping(value = "/{hostId}/updateMerchantSetting", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Hosts", httpMethod = "PUT", value = "Update merchant setting", notes = "Updates merchant setting")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = MerchantHostSetting.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public MerchantHostSetting updateMerchantSetting(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The host id") @PathVariable("hostId") Long hostId,
            @ApiParam(value = "The updated merchant setting object", required = true) @RequestBody(required = true) MerchantHostSetting setting) throws Exception {

        return service.updateMerchantSetting(hostId, setting);
    }
    
    // </editor-fold>
    
    // <editor-fold desc="Terminal settings" defaultstate="collapsed">
    
    /**
     * List all Terminal Settings by host id
     *
     * @param authToken
     * @param hostId
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','READ_HOST','CREATE_HOST','UPDATE_HOST')")
    @RequestMapping(value = "/{hostId}/terminalSettings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Hosts", httpMethod = "GET", value = "List Terminal settings", notes = "List all Terminal Settings by host id", response = TerminalHostSetting.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<TerminalHostSetting> listTerminalSettings(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The host id") @PathVariable("hostId") Long hostId) throws Exception {

        return service.listTerminalSettings(hostId);
    }
    
    /**
     * Get terminal host setting 
     *
     * @param authToken
     * @param hostId
     * @param key
     *
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','READ_HOST','CREATE_HOST','UPDATE_HOST')")
    @RequestMapping(value = "/{hostId}/getTerminalSetting/{key}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Hosts", httpMethod = "GET", value = "Get terminal setting", notes = "Get terminal setting")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TerminalHostSetting.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public TerminalHostSetting getTerminalSetting(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The host id") @PathVariable("hostId") Long hostId,
            @ApiParam(value = "The setting key") @PathVariable("key") String key) throws Exception {

        return service.getTerminalSetting(hostId, key);
    }
    
    /**
     * Add terminal host setting 
     *
     * @param authToken
     * @param hostId
     * @param merchantSetting
     *
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','CREATE_HOST','UPDATE_HOST')")
    @RequestMapping(value = "/{hostId}/addTerminalSetting", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Hosts", httpMethod = "POST", value = "Add terminal setting", notes = "Add terminal setting to a host", response = TerminalHostSetting.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TerminalHostSetting.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public TerminalHostSetting addTerminalSetting(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The host id") @PathVariable("hostId") Long hostId,
            @ApiParam(value = "The new terminal setting", required = true) @RequestBody(required = true) TerminalHostSetting merchantSetting) throws Exception {

        return service.addTerminalSetting(hostId, merchantSetting);
    }
    
    /**
     * Delete terminal host setting 
     *
     * @param authToken
     * @param terminalHostSettingId
     *
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','CREATE_HOST','UPDATE_HOST')")
    @RequestMapping(value = "/deleteTerminalSetting/{terminalHostSettingId}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Hosts", httpMethod = "DELETE", value = "Delete merchant setting", notes = "Delete merchant setting")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteTerminalSetting(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The terminal host setting id") @PathVariable("terminalHostSettingId") Long terminalHostSettingId) throws Exception {

        service.deleteTerminalSetting(terminalHostSettingId);
    }

    /**
     * Updates host terminal setting 
     *
     * @param authToken
     * @param hostId
     * @param setting
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','CREATE_HOST','UPDATE_HOST')")
    @RequestMapping(value = "/{hostId}/updateTerminalSetting", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Hosts", httpMethod = "PUT", value = "Update terminal setting", notes = "Updates terminal setting")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TerminalHostSetting.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public TerminalHostSetting updateTerminalSetting(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The host id") @PathVariable("hostId") Long hostId,
            @ApiParam(value = "The updated terminal setting object", required = true) @RequestBody(required = true) TerminalHostSetting setting) throws Exception {

        return service.updateTerminalSetting(hostId, setting);
    }
    
    // </editor-fold>
    
    // <editor-fold desc="Modes and Operations" defaultstate="collapsed">
    
    /**
     * List all Mode Operations by Host id
     *
     * @param authToken
     * @param hostId
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','READ_HOST','CREATE_HOST','UPDATE_HOST')")
    @RequestMapping(value = "/getModeOperations/{hostId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Hosts", httpMethod = "GET", value = "List all Mode Operations by Host id", notes = "List all Mode Operations by Host id", response = HostModeOperation.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<HostModeOperation> getModeOperations(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The host id") @PathVariable("hostId") Long hostId) throws Exception {

        return service.getModeOperationsByHostId(hostId);
    }
    
    /**
     * Add host mode operation 
     *
     * @param authToken
     * @param hostId
     * @param modeOper
     *
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','CREATE_HOST','UPDATE_HOST')")
    @RequestMapping(value = "/{hostId}/addModeOperation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Hosts", httpMethod = "POST", value = "Add host mode operation", notes = "Add host mode operation", response = HostModeOperation.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = HostModeOperation.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public HostModeOperation addModeOperation(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The host id") @PathVariable("hostId") Long hostId,
            @ApiParam(value = "The new host mode operation", required = true) @RequestBody(required = true) HostModeOperation modeOper) throws Exception {

        return service.addModeOperation(hostId, modeOper);
    }
    
    /**
     * Delete host mode operation
     *
     * @param authToken
     * @param modeOperId
     *
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','CREATE_HOST','UPDATE_HOST')")
    @RequestMapping(value = "/deleteModeOperation/{modeOperId}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Hosts", httpMethod = "DELETE", value = "Delete host mode operation", notes = "Delete host mode operation")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteModeOperation(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The terminal host setting id") @PathVariable("modeOperId") 
                    Long modeOperId) throws Exception {
        service.deleteModeOperation(modeOperId);
    }

    /**
     * Updates host mode operation
     *
     * @param authToken
     * @param hostId
     * @param modeOper
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_HOST','CREATE_HOST','UPDATE_HOST')")
    @RequestMapping(value = "/{hostId}/updateModeOperation", method = RequestMethod.PUT, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Hosts", httpMethod = "PUT", value = "Update host mode operation", notes = "Updates host mode operation")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = HostModeOperation.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public HostModeOperation updateModeOperation(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The host id") @PathVariable("hostId") Long hostId,
            @ApiParam(value = "The updated host mode operation", required = true) @RequestBody(required = true) 
                    HostModeOperation modeOper) throws Exception {
        return service.updateModeOperation(hostId, modeOper);
    }
    
    //</editor-fold>
}
