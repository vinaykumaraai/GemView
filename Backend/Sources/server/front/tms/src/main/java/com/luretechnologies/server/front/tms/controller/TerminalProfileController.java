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

import com.luretechnologies.server.data.model.tms.TerminalProfile;
import com.luretechnologies.server.service.TerminalProfileService;
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
 * Terminal Profile Controller Class
 *
 *
 */
@RestController
@RequestMapping("/terminalProfiles")
@Api(hidden = true)
public class TerminalProfileController {

    @Autowired
    TerminalProfileService service;

    /**
     * Saves a terminal profile information
     *
     * @param terminalProfile
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Save terminal profile", hidden = true)
    public TerminalProfile save(@RequestBody TerminalProfile terminalProfile) throws Exception {

        return service.save(terminalProfile);
    }

    /**
     * Deletes a terminal profile information
     *
     * @param id
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete terminal profile", hidden = true)
    public void delete(@PathVariable("id") long id) throws Exception {

        service.delete(id);
    }

    /**
     * Retrieve a terminal profile information
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get terminal profile", hidden = true)
    public TerminalProfile get(@PathVariable("id") long id) throws Exception {

        return service.get(id);
    }

    /**
     * List all terminal profiles information.
     *
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(hidden = true, tags = "Terminal Profiles", httpMethod = "GET", value = "List terminals", notes = "Lists terminals.  Will return 50 records if no paging parameters defined", response = TerminalProfile.class, responseContainer = "List")
    public List<TerminalProfile> list(@ApiParam(value = "Page number", required = false, defaultValue = "1") @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "Rows per page", required = false, defaultValue = "50") @RequestParam(name = "rowsPerPage", defaultValue = "50") Integer rowsPerPage) throws Exception {
        return service.list(pageNumber, rowsPerPage);
    }

    /**
     * Adds an Application and Payment Profile to a terminal profile
     *
     * @param id
     * @param idApplication
     * @param idPaymentProfile
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}/application/{idApplication}/paymentProfile/{idPaymentProfile}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add application and payment profile", hidden = true)
    public TerminalProfile addApplicationPaymentProfile(@PathVariable long id, @PathVariable long idApplication, @PathVariable long idPaymentProfile) throws Exception {

        return service.addApplicationPaymentProfile(id, idApplication, idPaymentProfile);
    }

    /**
     * Deletes a product from a terminal profile
     *
     * @param id
     * @param idApplication
     * @param idPaymentProfile
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}/application/{idApplication}/paymentProfile/{idPaymentProfile}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Removes application and payment profile", hidden = true)
    public TerminalProfile deleteApplicationPaymentProfile(@PathVariable long id, @PathVariable long idApplication, @PathVariable long idPaymentProfile) throws Exception {

        return service.deleteApplicationPaymentProfile(id, idApplication, idPaymentProfile);
    }

    /**
     * Search terminal profiles by a given filter
     *
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(name = "Search", value = "/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(hidden = true, tags = "Terminal Profiles", httpMethod = "POST", value = "Search terminal profiles", notes = "Search terminal profiles that match a given filter.  Will return 50 records if no paging parameters defined", response = TerminalProfile.class, responseContainer = "List")
    public List<TerminalProfile> search(@ApiParam(value = "filter", required = true) @RequestParam("filter") String filter,
            @ApiParam(value = "Page number", required = false, defaultValue = "1") @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "Rows per page", required = false, defaultValue = "50") @RequestParam(name = "rowsPerPage", defaultValue = "50") Integer rowsPerPage) throws Exception {

        return service.search(filter, pageNumber, rowsPerPage);
    }
}
