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

import com.luretechnologies.server.data.model.tms.AppFile;
import com.luretechnologies.server.service.AppFileService;
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
 *
 * @author Vinay
 */
@RestController
@RequestMapping("/appfile")
@Api(value = "AppFile")
public class AppFileController {
    
    @Autowired
    AppFileService appfileService;
    
    /**
     * Creates a new appFile
     *
     * @param appFile
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppFile", httpMethod = "POST",value = "Create AppFile")
    public AppFile create(
            @ApiParam(value = "The new appFile object", required = true) @RequestBody(required = true) AppFile appFile) throws Exception {

        return appfileService.createAppFile(appFile);
    }
    
    /**
     * Updates an appFile
     *
     * @param  id
     * @param appFile
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppFile", httpMethod = "PUT", value = "Update AppFile", notes = "Updates an appfile")
    public AppFile updateAppFile(
            @ApiParam(value = "AppFile id", required = true) @PathVariable("id") long id,
            @ApiParam(value = "AppFile object", required = true) @RequestBody(required = true) AppFile appFile) throws Exception {

        return appfileService.updateAppFile(id, appFile);
    }
    
   /**
     * Deletes a App information
     *
     * @param id
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "AppFile", httpMethod = "DELETE",value = "Delete AppFile")
    public void deleteAppFile(long id) throws Exception {

        appfileService.deleteAppFile(id);
    }
    
    /**
     * Retrieve an AppFile information
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppFile", httpMethod = "GET", value = "Get AppFile", notes = "Get AppFile by id")
    public AppFile getAppFileByID(@ApiParam(value = "AppFile ID", required = true) @PathVariable long id) throws Exception {

        return appfileService.getAppFileByID(id);
    }
    
    /**
     * Retrieve an List of AppFile  information
     *
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppFile", httpMethod = "GET", value = "Get AppFile List", notes = "Get list pf AppFiles by ID's")
    public List<AppFile> getAppFileList(
            @ApiParam(value = "Page number", required = false, defaultValue = "1") @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "Rows per page", required = false, defaultValue = "50") @RequestParam(name = "rowsPerPage", defaultValue = "50") Integer rowsPerPage) throws Exception {

        return appfileService.getAppFileList(pageNumber, rowsPerPage);
    }
    
    /**
     * Do Force Update
     *
     * @param  value
     * @return 
     * @throws java.lang.Exception
     */
    
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppFile", httpMethod = "PUT",value = "Force Update")
    public Integer forceupdate(@RequestBody Integer value) throws Exception {

        return appfileService.doForceUpdate(value);
    }
}
