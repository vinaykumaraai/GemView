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

import com.luretechnologies.server.data.model.tms.ApplianceAppParamValue;
import com.luretechnologies.server.service.ApplianceAppParamService;
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
@RequestMapping("/applianceappparam")
@Api(value = "ApplianceAppParam")
public class ApplianceAppParamController {
    
    @Autowired
    ApplianceAppParamService applianceAppParamService;
    
     /**
     * Creates a new ApplianceAppParamValue
     *
     * @param applianceAppParam
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "ApplianceAppParam", httpMethod = "POST",value = "Create ApplianceAppParam")
    public ApplianceAppParamValue create(@RequestBody ApplianceAppParamValue applianceAppParam) throws Exception {

        return applianceAppParamService.createApplianceAppParam(applianceAppParam);
    }
    
     /**
     * Updates an ApplianceAppParamValue
     *
     * @param  id
     * @param applianceAppParam
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "ApplianceAppParam", httpMethod = "PUT", value = "Update ApplianceAppParam", notes = "Updates an ApplianceAppParam")
    public ApplianceAppParamValue update(@ApiParam(value = "ApplianceAppParam id", required = true) @PathVariable("id") long id,
            @ApiParam(value = "ApplianceAppParam object", required = true) @RequestBody ApplianceAppParamValue applianceAppParam) throws Exception {

        return applianceAppParamService.updateApplianceAppParam(id);
    }
    
    /**
     * Deletes an ApplianceAppParamValue information
     *
     * @param id
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "ApplianceAppParam", httpMethod = "DELETE",value = "Delete ApplianceAppParam")
    public void delete(long id) throws Exception {

        applianceAppParamService.deleteApplianceAppParam(id);
    }
    
    /**
     * Retrieve an ApplianceAppParamValue information
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "ApplianceAppParam", httpMethod = "GET", value = "Get ApplianceAppParam", notes = "Get ApplianceAppParam by id")
    public ApplianceAppParamValue getApplianceAppParamByID(@ApiParam(value = "ApplianceAppParam ID", required = true) @PathVariable long id) throws Exception {

        return applianceAppParamService.getApplianceAppParamByID(id);
    }
    
    /**
     * Retrieve an List of appProfileFormat  information
     *
     * @param ids
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "ApplianceAppParam", httpMethod = "GET", value = "Get ApplianceAppParam List", notes = "Get list of ApplianceAppParam by ID's")
    public List<ApplianceAppParamValue> getApplianceAppParamList(@ApiParam(value = "ApplianceAppParam ID", required = true) @PathVariable List<Long> ids) throws Exception {

        return applianceAppParamService.getApplianceAppParamList(ids);
    }
    
    /**
     * Get the Value for given ID
     *
     * @param  id
     * @return 
     * @throws java.lang.Exception
     */
    
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "ApplianceAppParam", httpMethod = "GET",value = "get value")
    public String getValueByiD(@RequestBody Long id) throws Exception {

        return applianceAppParamService.getValueByID(id);
    }
}
