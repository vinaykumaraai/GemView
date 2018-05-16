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

import com.luretechnologies.server.data.model.Terminal;
import com.luretechnologies.server.data.model.User;
import com.luretechnologies.server.service.TerminalService;
import com.luretechnologies.server.utils.UserAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
     * Creates a new Terminal
     *
     * @param terminal
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Terminals", httpMethod = "POST", value = "Create terminal", notes = "Creates a new terminal")
    public Terminal create(@ApiParam(value = "Terminal object", required = true) @RequestBody Terminal terminal) throws Exception {

        return service.create(terminal);
    }

    /**
     * Finds a Terminal given the Serial Number
     *
     * @param serialNumber
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{serialNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Terminals", httpMethod = "GET", value = "Get terminal", notes = "Get terminal by serial number")
    public Terminal get(@ApiParam(value = "Terminal serial number", required = true) @PathVariable String serialNumber) throws Exception {

        return service.get(serialNumber);
    }

    /**
     * Updates a terminal information
     *
     * @param terminal
     * @param serialNumber
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{serialNumber}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Terminals", httpMethod = "PUT", value = "Update terminal", notes = "Updates a terminal")
    public Terminal update(@ApiParam(value = "Terminal serial number", required = true) @PathVariable("serialNumber") String serialNumber,
            @ApiParam(value = "Terminal object", required = true) @RequestBody Terminal terminal) throws Exception {

        return service.update(serialNumber, terminal);
    }

    /**
     * Deletes a terminal information
     *
     * @param serialNumber
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{serialNumber}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Terminals", httpMethod = "DELETE", value = "Delete terminal", notes = "Deletes a terminal")
    public void delete(@ApiParam(value = "Terminal serial number", required = true) @PathVariable("serialNumber") String serialNumber) throws Exception {

        service.delete(serialNumber);
    }

    /**
     * List all the Terminals
     *
     * @param name
     * @param serialnumber
     * @param active
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Terminals", httpMethod = "GET", value = "List terminals", notes = "Lists terminals. Will return 50 records if no paging parameters defined", response = Terminal.class, responseContainer = "List")
    public List<Terminal> list(@ApiParam(value = "Terminal name") @RequestParam(required = false) String name,
            @ApiParam(value = "Terminal serial number") @RequestParam(required = false) String serialnumber,
            @ApiParam(value = "Terminal status") @RequestParam(required = false) Boolean active,
            @ApiParam(value = "Page number", required = false, defaultValue = "1") @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "Rows per page", required = false, defaultValue = "50") @RequestParam(name = "rowsPerPage", defaultValue = "50") Integer rowsPerPage) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            UserAuth userAuth = (UserAuth) authentication.getDetails();
            User user = userAuth.getSystemUser();

            return service.list(user.getEntity(), name, serialnumber, active);
        }
        return null;
    }

    /**
     * Add a Payment Application to the Terminal
     *
     * @param serialNumber
     * @param idApp
     * @param idProfile
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{serialNumber}/applications/{idApp}/paymentProfiles/{idProfile}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Terminals", httpMethod = "POST", value = "Add payment application and profile", notes = "Adds a payment application and profile to a terminal")
    public void addPaymentApplication(@ApiParam(value = "Terminal serial number", required = true) @PathVariable String serialNumber,
            @ApiParam(value = "Application id", required = true) @PathVariable long idApp,
            @ApiParam(value = "Payment profile id", required = true) @PathVariable long idProfile) throws Exception {

        service.addPaymentApplication(serialNumber, idApp, idProfile);
    }

    /**
     * Search terminals by a given filter
     *
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(name = "Search", value = "/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Terminals", httpMethod = "POST", value = "Search terminals", notes = "Search terminals that match a given filter. Will return 50 records if no paging parameters defined", response = Terminal.class, responseContainer = "List")
    public List<Terminal> search(@ApiParam(value = "filter", required = true) @RequestParam("filter") String filter,
            @ApiParam(value = "Page number", required = false, defaultValue = "1") @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "Rows per page", required = false, defaultValue = "50") @RequestParam(name = "rowsPerPage", defaultValue = "50") Integer rowsPerPage) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getDetails() instanceof UserAuth) {
            UserAuth userAuth = (UserAuth) authentication.getDetails();
            User user = userAuth.getSystemUser();

            return service.search(user.getEntity(), filter, pageNumber, rowsPerPage);
        }
        return null;
    }

}
