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

import com.luretechnologies.server.data.display.ErrorResponse;
import com.luretechnologies.server.data.model.tms.AppParam;
import com.luretechnologies.server.service.AppParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
 *
 * @author Vinay
 */
@RestController
@RequestMapping("/appparam")
@Api(value = "AppParam")
public class AppParamController {
    
    @Autowired(required = true)
    AppParamService appParamService;
    
     /**
     * Creates a new appProfileFile
     *
     * @param appParam
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppParam", httpMethod = "POST",value = "Create AppParam")
    public AppParam createAppParam(@RequestBody AppParam appParam) throws Exception {

        return appParamService.createAppParam(appParam);
    }
    
    /**
     * Updates an appProfileFile
     *
     * @param  id
     * @param appParam
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppParam", httpMethod = "PUT", value = "Update AppParam", notes = "Updates an AppParam")
    public AppParam updateAppParam(
            @ApiParam(value = "AppParam id", required = true) @PathVariable("id") long id,
            @ApiParam(value = "AppParam object", required = true) @RequestBody AppParam appParam) throws Exception {

        return appParamService.updateAppParam(id, appParam);
    }
    
    /**
     * Deletes an appProfileFile information
     *
     * @param id
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "AppParam", httpMethod = "DELETE",value = "Delete AppParam")
    public void deleteAppProfileFile(long id) throws Exception {

        appParamService.deleteAppParam(id);
    }
    
    /**
     * Retrieve an appProfileFile information
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppParam", httpMethod = "GET", value = "Get AppParam", notes = "Get AppParam by id")
    public AppParam getAppParamByID(@ApiParam(value = "AppParam ID", required = true) @PathVariable long id) throws Exception {

        return appParamService.getAppParamByID(id);
    }
    
    /**
     * Retrieve an List of AppFile  information
     *
     * @param ids
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppParam", httpMethod = "GET", value = "Get AppParam List", notes = "Get list of AppParam by ID's")
    public List<AppParam> getAppProfileFileList(@ApiParam(value = "AppParam ID", required = true) @PathVariable List<Long> ids) throws Exception {

        return appParamService.getAppParamList(ids);
    }
    
    /**
     * Do Force Update
     *
     * @param  value
     * @return 
     * @throws java.lang.Exception
     */
    
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppParam", httpMethod = "PUT",value = "Force Update")
    public Integer forceupdate(@RequestBody Integer value) throws Exception {

        return appParamService.doForceUpdate(value);
    }
    
     /**
     * List all applications information.
     *
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @RequestMapping(name = "Search", value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppParam", httpMethod = "GET", value = "Search Apps", notes = "Search apps. Will return 50 records if no paging parameters defined", response = AppParam.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<AppParam> search(
            @ApiParam(value = "The search filter", required = true) @RequestParam(value = "filter", required = true) String filter,
            @ApiParam(value = "The page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "The rows per page", defaultValue = "50") @RequestParam(value = "rowsPerPage", required = false, defaultValue = "50") Integer rowsPerPage) throws Exception {
        return appParamService.search(filter, pageNumber, rowsPerPage);
    }
    
}
