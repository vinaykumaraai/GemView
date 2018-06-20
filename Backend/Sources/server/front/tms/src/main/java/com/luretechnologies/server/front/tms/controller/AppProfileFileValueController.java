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

import com.luretechnologies.server.data.model.tms.AppProfileFileValue;
import com.luretechnologies.server.service.AppProfileFileValueService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Vinay
 */
@RestController
@RequestMapping("/appprofilefilevalue")
@Api(value = "AppProfileFile")
public class AppProfileFileValueController {
    
    @Autowired
    AppProfileFileValueService appProfileFileService;
    
    /**
     * Creates a new appProfileFile
     *
     * @param appProfileFileValue
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfileFile", httpMethod = "POST",value = "Create AppProfileFile")
    public AppProfileFileValue createAppProfileFile(
            @RequestBody AppProfileFileValue appProfileFileValue) throws Exception {

        return appProfileFileService.createAppProfileFileValue(appProfileFileValue);
    }
    
    /**
     * Updates an appProfileFile
     *
     * @param  id
     * @param appProfileFileValue
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfileFile", httpMethod = "PUT", value = "Update AppProfileFile", notes = "Updates an AppProfileFile")
    public AppProfileFileValue updateAppProfileFile(
            @ApiParam(value = "AppProfileFile id", required = true) @PathVariable("id") long id,
            @ApiParam(value = "AppProfileFile object", required = true) @RequestBody AppProfileFileValue appProfileFileValue) throws Exception {

        return appProfileFileService.updateAppProfileFileValue(id, appProfileFileValue);
    }
    
    /**
     * Deletes an appProfileFile information
     *
     * @param id
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "AppProfileFile", httpMethod = "DELETE",value = "Delete AppProfileFile")
    public void deleteAppProfileFile(long id) throws Exception {

        appProfileFileService.deleteAppProfileFileValue(id);
    }
    
    /**
     * Retrieve an appProfileFile information
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfileFile", httpMethod = "GET", value = "Get AppProfileFile", notes = "Get AppProfileFile by id")
    public AppProfileFileValue getAppProfileFileByID(@ApiParam(value = "AppProfileFile ID", required = true) @PathVariable long id) throws Exception {

        return appProfileFileService.getAppProfileFileValueByID(id);
    }
    
    /**
     * Retrieve an List of AppFile  information
     *
     * @param ids
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfileFile", httpMethod = "GET", value = "Get AppProfileFileValue List", notes = "Get list of AppProfileFileValue by ID's")
    public List<AppProfileFileValue> getAppProfileFileList(@ApiParam(value = "AppProfileFileValue ID", required = true) @PathVariable List<Long> ids) throws Exception {

        return appProfileFileService.getAppProfileFileValueList(ids);
    }
    
    /**
     * Do Force Update
     *
     * @param  value
     * @return 
     * @throws java.lang.Exception
     */
    
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfileFile", httpMethod = "PUT",value = "Force Update")
    public Integer forceupdate(@RequestBody Integer value) throws Exception {

        return appProfileFileService.doForceUpdate(value);
    }
}
