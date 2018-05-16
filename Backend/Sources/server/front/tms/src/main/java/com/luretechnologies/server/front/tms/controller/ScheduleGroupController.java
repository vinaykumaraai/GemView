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

import com.luretechnologies.server.data.model.tms.ScheduleGroup;
import com.luretechnologies.server.service.ScheduleGroupService;
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
 * Schedule Group Controller Class
 *
 *
 */
@RestController
@RequestMapping("/scheduleGroups")
@Api(value = "Schedule Groups")
public class ScheduleGroupController {

    @Autowired
    ScheduleGroupService service;

    /**
     * Creates a new schedule group
     *
     * @param scheduleGroup
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Schedule Groups", httpMethod = "POST", value = "Create schedule group", notes = "Creates a new schedule group")
    public ScheduleGroup create(@ApiParam(value = "Schedule Group object", required = true) @RequestBody ScheduleGroup scheduleGroup) throws Exception {

        return service.create(scheduleGroup);
    }

    /**
     * Retrieve a schedule group information
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Schedule Groups", httpMethod = "GET", value = "Get schedule group", notes = "Get schedule group by id")
    public ScheduleGroup get(@ApiParam(value = "Schedule Group id", required = true) @PathVariable("id") long id) throws Exception {

        return service.get(id);
    }

    /**
     * Updates a schedule group information
     *
     * @param scheduleGroup
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Schedule Groups", httpMethod = "PUT", value = "Update schedule group", notes = "Updates a schedule group")
    public ScheduleGroup update(@ApiParam(value = "Schedule Group id", required = true) @PathVariable("id") long id,
            @ApiParam(value = "Schedule Group object", required = true) @RequestBody ScheduleGroup scheduleGroup) throws Exception {

        return service.update(id, scheduleGroup);
    }

    /**
     * Deletes a schedule group information
     *
     * @param id
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Schedule Groups", httpMethod = "DELETE", value = "Delete schedule group", notes = "Deletes a schedule group")
    public void delete(@ApiParam(value = "Schedule Group id", required = true) @PathVariable("id") long id) throws Exception {

        service.delete(id);
    }

    /**
     * List all schedule groups information.
     *
     * @param name
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Schedule Groups", httpMethod = "GET", value = "List schedule groups", notes = "Lists schedule groups. Will return 50 records if no paging parameters defined", response = ScheduleGroup.class, responseContainer = "List")
    public List<ScheduleGroup> list(@ApiParam(value = "Schedule group name") @RequestParam(required = false) String name,
            @ApiParam(value = "Page number", required = false, defaultValue = "1") @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "Rows per page", required = false, defaultValue = "50") @RequestParam(name = "rowsPerPage", defaultValue = "50") Integer rowsPerPage) throws Exception {

        return service.list(name, pageNumber, rowsPerPage);
    }

    /**
     * Search schedule groups by a given filter
     *
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(name = "Search", value = "/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Schedule Groups", httpMethod = "POST", value = "Search schedule groups", notes = "Search schedule groups that match a given filter. Will return 50 records if no paging parameters defined", response = ScheduleGroup.class, responseContainer = "List")
    public List<ScheduleGroup> search(@ApiParam(value = "filter", required = true) @RequestParam("filter") String filter,
            @ApiParam(value = "Page number", required = false, defaultValue = "1") @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "Rows per page", required = false, defaultValue = "50") @RequestParam(name = "rowsPerPage", defaultValue = "50") Integer rowsPerPage) throws Exception {

        return service.search(filter, pageNumber, rowsPerPage);
    }
}
