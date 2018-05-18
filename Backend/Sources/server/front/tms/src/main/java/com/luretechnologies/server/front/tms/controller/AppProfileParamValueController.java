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
import com.luretechnologies.server.data.model.tms.AppProfileFileValue;
import com.luretechnologies.server.data.model.tms.AppProfileParamValue;
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
import com.luretechnologies.server.service.AppProfileParamValueService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/appprofileparamvalue")
@Api(value = "AppProfileParam")
public class AppProfileParamValueController {
    
    @Autowired
    AppProfileParamValueService appProfileParamService;
    
    /**
     * Creates a new appProfileFile
     *
     * @param appProfileParam
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfileParam", httpMethod = "POST",value = "Create AppProfileFile")
    public AppProfileParamValue createAppProfileParam(@RequestBody AppProfileParamValue appProfileParam) throws Exception {

        return appProfileParamService.createAppProfileParam(appProfileParam);
    }
    
    /**
     * Updates an appProfileParam
     *
     * @param  id
     * @param appProfileParamValue
     * @param appProfileParam
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfileParam", httpMethod = "PUT", value = "Update AppProfileParam", notes = "Updates an AppProfileParam")
    public AppProfileParamValue updateAppProfileParam(
            @ApiParam(value = "AppProfileParam id", required = true) @PathVariable("id") long id,
            @ApiParam(value = "AppProfileParam object", required = true) @RequestBody AppProfileParamValue appProfileParamValue) throws Exception {

        return appProfileParamService.updateAppProfileParam(id, appProfileParamValue);
    }
    
    /**
     * Deletes an appProfileParam information
     *
     * @param id
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "AppProfileParam", httpMethod = "DELETE",value = "Delete AppProfileParam")
    public void deleteAppProfileParam(long id) throws Exception {

        appProfileParamService.deleteAppProfileParamvalue(id);
    }
    
    /**
     * Retrieve an appProfileParam information
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfileParam", httpMethod = "GET", value = "Get AppProfileParam", notes = "Get AppProfileParam by id")
    public AppProfileParamValue getAppProfileParamByID(@ApiParam(value = "AppProfileParam ID", required = true) @PathVariable long id) throws Exception {

        return appProfileParamService.getAppProfileParamByID(id);
    }
    
    /**
     * Retrieve an List of AppFile  information
     *
     * @param ids
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfileParam", httpMethod = "GET", value = "Get AppProfileParam List", notes = "Get list of AppProfileParam by ID's")
    public List<AppProfileParamValue> getAppProfileFileList(@ApiParam(value = "AppProfileParam ID", required = true) @PathVariable List<Long> ids) throws Exception {

        return appProfileParamService.getAppProfileParamList(ids);
    }
    
    /**
     * Do Force Update
     *
     * @param  value
     * @return 
     * @throws java.lang.Exception
     */
    
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfileParam", httpMethod = "PUT",value = "Force Update")
    public Integer forceupdate(@RequestBody Integer value) throws Exception {

        return appProfileParamService.doForceUpdate(value);
    }
}
