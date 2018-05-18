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

import com.luretechnologies.common.enums.MerchantSettingEnum;
import com.luretechnologies.common.enums.ModeEnum;
import com.luretechnologies.common.enums.OperationEnum;
import com.luretechnologies.server.data.display.ErrorResponse;
import com.luretechnologies.server.data.model.Merchant;
import com.luretechnologies.server.data.model.User;
import com.luretechnologies.server.data.model.payment.Host;
import com.luretechnologies.server.data.model.payment.MerchantHost;
import com.luretechnologies.server.data.model.payment.MerchantHostModeOperation;
import com.luretechnologies.server.data.model.payment.MerchantHostSettingValue;
import com.luretechnologies.server.data.model.payment.MerchantSettingValue;
import com.luretechnologies.server.service.MerchantService;
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
 *
 *
 * @author developer
 */
@RestController
@RequestMapping("/merchants")
@Api(value = "Merchants")
public class MerchantController {

    @Autowired
    MerchantService service;

    /**
     * Creates a new merchant
     *
     * @param authToken
     * @param merchant
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','CREATE_ENTITY')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Merchants", httpMethod = "POST", value = "Create merchant", notes = "Creates a new merchant")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created", response = Merchant.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Merchant create(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The new merchant object", required = true) @RequestBody(required = true) Merchant merchant) throws Exception {

        return service.create(merchant);
    }

    /**
     * Retrieve a merchant information
     *
     * @param authToken
     * @param merchantId
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','READ_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/{merchantId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Merchants", httpMethod = "GET", value = "Get merchant", notes = "Get merchant by id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Merchant.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Merchant get(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The merchant identifier") @PathVariable("merchantId") String merchantId) throws Exception {

        return service.get(merchantId);
    }

    /**
     * Updates a merchant information
     *
     * @param authToken
     * @param merchant
     * @param merchantId
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/{merchantId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Merchants", httpMethod = "PUT", value = "Update merchant", notes = "Updates a merchant")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Merchant.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Merchant update(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The merchant identifier") @PathVariable("merchantId") String merchantId,
            @ApiParam(value = "The updated merchant object", required = true) @RequestBody(required = true) Merchant merchant) throws Exception {

        return service.update(merchantId, merchant);
    }

    /**
     * Deletes a merchant information
     *
     * @param authToken
     * @param merchantId
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPER')")
    @RequestMapping(value = "/{merchantId}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Merchants", httpMethod = "DELETE", value = "Delete merchant", notes = "Deletes a merchant")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void delete(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The merchant identifier") @PathVariable("merchantId") String merchantId) throws Exception {

        service.delete(merchantId);
    }

    /**
     * List all merchants information.
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
    @ApiOperation(tags = "Merchants", httpMethod = "GET", value = "List merchants", notes = "Lists merchants. Will return 50 records if no paging parameters defined", response = Merchant.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<Merchant> list(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "the rows per page", defaultValue = "50") @RequestParam(value = "rowsPerPage", required = false, defaultValue = "50") Integer rowsPerPage) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            UserAuth userAuth = (UserAuth) authentication.getDetails();
            User user = userAuth.getSystemUser();

            return service.list(user.getEntity(), pageNumber, rowsPerPage);
        }

        return new ArrayList<>();
    }

    /**
     * Search merchants by a given filter
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
    @ApiOperation(tags = "Merchants", httpMethod = "GET", value = "Search merchants", notes = "Search merchants that match a given filter. Will return 50 records if no paging parameters defined", response = Merchant.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<Merchant> search(
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

    /**
     * List all Available Hosts for a given Merchant
     *
     * @param authToken
     * @param merchantId
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','READ_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(name = "List Host", value = "/{merchantId}/hosts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Merchants", httpMethod = "GET", value = "List available host", notes = "List available host for a given merchant", response = Host.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<Host> listAvailableHosts(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The merchant identifier") @PathVariable("merchantId") String merchantId) throws Exception {

        return service.listAvailableHosts(merchantId);
    }

    /**
     * List all Merchant Host Settings for a given Host
     *
     * @param authToken
     * @param merchantId
     * @param hostId
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','READ_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(name = "List Host Settings", value = "/{merchantId}/hosts/{hostId}/settings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Merchants", httpMethod = "GET", value = "List host settings", notes = "List all merchant host settings for a given host", response = MerchantHostSettingValue.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<MerchantHostSettingValue> listHostSettings(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The merchant identifier") @PathVariable("merchantId") String merchantId,
            @ApiParam(value = "The host enum") @PathVariable("hostId") Long hostId) throws Exception {

        return service.listHostSettings(merchantId, hostId);
    }

    /**
     * Add host
     *
     * @param authToken
     * @param merchantId
     * @param hostId
     *
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/{merchantId}/addHost/{hostId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Merchants", httpMethod = "POST", value = "Add host", notes = "Adds a host to a merchant")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = MerchantHost.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public MerchantHost addHost(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The merchant identifier") @PathVariable("merchantId") String merchantId,
            @ApiParam(value = "The host id") @PathVariable("hostId") Long hostId) throws Exception {

        return service.addHost(merchantId, hostId);
    }

    /**
     * Deletes host
     *
     * @param authToken
     * @param merchantId˙
     * @param hostId
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/{merchantId}/host/{hostId}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Merchants", httpMethod = "DELETE", value = "Delete host", notes = "Deletes the association between a host and a merchant")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteHost(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The merchant identifier") @PathVariable("merchantId") String merchantId,
            @ApiParam(value = "The host enum") @PathVariable("hostId") Long hostId) throws Exception {

        service.deleteHost(merchantId, hostId);
    }

    /**
     * Sets merchant host setting value
     *
     * @param authToken
     * @param merchantId
     * @param hostId
     * @param settingId
     * @param value
     *
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/{merchantId}/host/{hostId}/setting/{settingId}/value/{value}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Merchants", httpMethod = "POST", value = "Set host setting value", notes = "Sets a merchant host setting")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = MerchantHostSettingValue.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public MerchantHostSettingValue setHostSettingValue(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The merchant identifier") @PathVariable("merchantId") String merchantId,
            @ApiParam(value = "The host id") @PathVariable("hostId") Long hostId,
            @ApiParam(value = "The setting id") @PathVariable("settingId") Long settingId,
            @ApiParam(value = "The value") @PathVariable("value") String value) throws Exception {

        return service.setHostSettingValue(merchantId, hostId, settingId, value);
    }

    /**
     * Add merchant setting values
     *
     * @param authToken
     * @param merchantId
     * @param merchantSettingValue
     *
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/{merchantId}/settings", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Merchants", httpMethod = "POST", value = "Add setting", notes = "Adds a setting to a merchant", response = Merchant.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Merchant.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Merchant addSetting(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The merchant identifier") @PathVariable("merchantId") String merchantId,
            @ApiParam(value = "The new merchant setting", required = true) @RequestBody(required = true) MerchantSettingValue merchantSettingValue) throws Exception {

        return service.addSetting(merchantId, merchantSettingValue);
    }

    /**
     * Updates merchant setting values
     *
     * @param authToken
     * @param merchantId
     * @param merchantSettingValue
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/{merchantId}/settings", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Merchants", httpMethod = "PUT", value = "Update setting", notes = "Updates a merchant setting value")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Merchant.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Merchant updateSetting(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The merchant identifier") @PathVariable("merchantId") String merchantId,
            @ApiParam(value = "The updated merchant setting", required = true) @RequestBody(required = true) MerchantSettingValue merchantSettingValue) throws Exception {

        return service.updateSetting(merchantId, merchantSettingValue);
    }

    /**
     * Deletes merchant setting values
     *
     * @param authToken
     * @param merchantId
     * @param merchantSettingEnum
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/{merchantId}/settings/{merchantSettingEnum}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Merchants", httpMethod = "DELETE", value = "Delete setting", notes = "Deletes a merchant setting")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteSetting(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The merchant identifier") @PathVariable("merchantId") String merchantId,
            @ApiParam(value = "The merchant setting enum to be deleted") @PathVariable("merchantSettingEnum") MerchantSettingEnum merchantSettingEnum) throws Exception {

        service.deleteSetting(merchantId, merchantSettingEnum);
    }

    /**
     * List all Available Settings for a given Merchant
     *
     * @param authToken
     * @param merchantId
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','READ_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(name = "List Setting", value = "/{merchantId}/settings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Merchants", httpMethod = "GET", value = "List available setting", notes = "List available setting for a given merchant", response = MerchantSettingEnum.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<MerchantSettingEnum> listAvailableSettings(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The merchant identifier") @PathVariable("merchantId") String merchantId) throws Exception {

        return service.listAvailableSettings(merchantId);
    }

    /**
     * Get host by a given mode and operation
     *
     * @param authToken
     * @param merchantId
     * @param modeEnum
     * @param operationEnum
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','READ_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(name = "Get Host by Mode and Operation", value = "/{merchantId}/modes/{modeEnum}/operations/{operationEnum}/host", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Merchants", httpMethod = "GET", value = "Get Host by Mode and Operation", notes = "Get the host by a given mode and operation", response = Host.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Host.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public Host getHostByModeOperation(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The merchant identifier") @PathVariable("merchantId") String merchantId,
            @ApiParam(value = "The mode enum") @PathVariable("modeEnum") ModeEnum modeEnum,
            @ApiParam(value = "The operation enum") @PathVariable("operationEnum") OperationEnum operationEnum) throws Exception {

        return service.getHostByModeOperation(merchantId, modeEnum, operationEnum);
    }
    
    /**
     * List all HostModeOperations for a given Merchant
     *
     * @param authToken
     * @param merchantId
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','READ_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(name = "List all HostModeOperations for a given Merchant", value = "/getHostModeOperations/{merchantId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Merchants", httpMethod = "GET", value = "List all HostModeOperations for a given Merchant", notes = "List all HostModeOperations for a given Merchant", response = MerchantHostModeOperation.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<MerchantHostModeOperation> getHostModeOperations(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The merchant identifier") @PathVariable("merchantId") String merchantId) throws Exception {
        return service.getHostModeOperationsByMerchantId(merchantId);
    }
    
    /**
     * Get MerchantHostModeOperation by a merchant and hostModeOperation
     *
     * @param authToken
     * @param merchantId
     * @param hostModeOperationId
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','READ_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(name = "Get MerchantHostModeOperation by a merchant and hostModeOperation", value = "/findHostModeOperation/{merchantId}/{hostModeOperationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Merchants", httpMethod = "GET", value = "Get MerchantHostModeOperation by a merchant and hostModeOperation", notes = "Get MerchantHostModeOperation by a merchant and hostModeOperation", response = MerchantHostModeOperation.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = MerchantHostModeOperation.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public MerchantHostModeOperation findHostModeOperation(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The merchant identifier") @PathVariable("merchantId") String merchantId,
            @ApiParam(value = "The hostModeOperation identifier") @PathVariable("hostModeOperationId") Long hostModeOperationId) throws Exception {

        return service.findHostModeOperation(merchantId, hostModeOperationId);
    }
    
    /**
     * Creates a new host mode operation
     *
     * @param authToken
     * @param merchantId
     * @param hostModeOperationId
     * 
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/{merchantId}/addHostModeOperation/{hostModeOperationId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Merchants", httpMethod = "POST", value = "Create host mode operation", notes = "Creates a new host mode operation")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created", response = MerchantHostModeOperation.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public MerchantHostModeOperation addHostModeOperation(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The merchant identifier") @PathVariable("merchantId") String merchantId,
            @ApiParam(value = "The hostModeOperation identifier") @PathVariable("hostModeOperationId") Long hostModeOperationId) throws Exception {

        return service.addHostModeOperation(merchantId, hostModeOperationId);
    }
    
    /**
     * Deletes merchant host Mode Operation
     *
     * @param authToken
     * @param merchantId
     * @param hostModeOperationId
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPER','ALL_ENTITY','UPDATE_ENTITY','CREATE_ENTITY')")
    @RequestMapping(value = "/{merchantId}/deleteHostModeOperation/{hostModeOperationId}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Merchants", httpMethod = "DELETE", value = "Delete host Mode Operation", notes = "Deletes a host Mode Operation")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteHostModeOperation(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(value = "The merchant identifier") @PathVariable("merchantId") String merchantId,
            @ApiParam(value = "The hostModeOperation identifier") @PathVariable("hostModeOperationId") Long hostModeOperationId) throws Exception {

        service.deleteHostModeOperation(merchantId, hostModeOperationId);
    }
}
