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

import com.luretechnologies.server.data.display.ModelDisplay;
import com.luretechnologies.server.service.ModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Model Controller Class
 *
 *
 */
@RestController
@RequestMapping("/models")
@Api(value = "Models")
public class ModelController {

    @Autowired
    ModelService service;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(name = "get", value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Models", httpMethod = "GET", value = "Get model", notes = "Get model by id")
    public ModelDisplay get(@ApiParam(value = "Model id", required = true) @RequestParam(name = "id") Long id) throws Exception {
        ModelDisplay display = service.get(id);
        return display;
    }

    /**
     * Search models by a given filter
     *
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(name = "search", value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Models", httpMethod = "GET", value = "Search models", notes = "Search models that match a given filter. Will return 50 records if no paging parameters defined", response = ModelDisplay.class, responseContainer = "List")
    public List<ModelDisplay> search(@ApiParam(value = "filter", required = false) @RequestParam(name = "filter", required = false) String filter,
            @ApiParam(value = "Page number", required = false, defaultValue = "1") @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "Rows per page", required = false, defaultValue = "50") @RequestParam(name = "rowsPerPage", defaultValue = "50") Integer rowsPerPage) throws Exception {

        return service.search(filter, pageNumber, rowsPerPage);
    }

    /**
     *
     * @param modelDisplay
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(name = "update", value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Models", httpMethod = "POST", value = "Update a Model", notes = "Update a Model")
    public ModelDisplay update(
            @ApiParam(value = "Model object", required = true) @RequestBody ModelDisplay modelDisplay
    ) throws Exception {
        ModelDisplay display = service.update(modelDisplay);
        return display;
    }

    /**
     *
     * @param modelDisplay
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(name = "create", value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Models", httpMethod = "POST", value = "Create a Model", notes = "Update a Model")
    public ModelDisplay create(
            @ApiParam(value = "Model object", required = true) @RequestBody ModelDisplay modelDisplay
    ) throws Exception {
        return service.create(modelDisplay);
    }

    /**
     *
     * @param id
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(name = "delete", value = "/delete", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Models", httpMethod = "POST", value = "Delete a Model", notes = "Delete a Model")
    public void delete(
            @ApiParam(value = "Model id", required = true) @RequestParam(name = "id", required = true) Long id
    ) throws Exception {
        service.delete(id);
    }

}
