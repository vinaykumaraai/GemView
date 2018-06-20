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
import com.luretechnologies.server.data.model.tms.AppProfile;
import com.luretechnologies.server.data.model.tms.AppProfileParamValue;
import com.luretechnologies.server.data.model.tms.EntityAppProfile;
import com.luretechnologies.server.data.model.tms.EntityAppProfileParam;
import com.luretechnologies.server.service.AppProfileService;
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
import org.springframework.web.bind.annotation.RequestHeader;
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
@RequestMapping("/appprofile")
@Api(value = "AppProfile")
public class AppProfileController {
    
    @Autowired
    AppProfileService appProfileService;
    
    /**
     * Creates a new appProfile
     *
     * @param appProfile
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "POST",value = "Create AppProfile")
    public AppProfile create(
            @RequestBody AppProfile appProfile) throws Exception {

        return appProfileService.createAppProfile(appProfile);
    }
    
    /**
     * Updates an appFile
     *
     * @param  id
     * @param appProfile
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "PUT", value = "Update AppProfile", notes = "Updates an appProfile")
    public AppProfile updateAppProfile(
            @ApiParam(value = "AppProfile id", required = true) @PathVariable("id") long id,
            @ApiParam(value = "AppProfile object", required = true) @RequestBody(required = true) AppProfile appProfile) throws Exception {
        
        return appProfileService.updateAppProfile(id, appProfile);
    }
    
    /**
     * Deletes a App information
     *
     * @param id
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "AppProfile", httpMethod = "DELETE",value = "Delete AppProfile")
    public void deleteAppProfile(
            @ApiParam(value = "AppProfile id", required = true) @PathVariable("id") long id) throws Exception {

        appProfileService.deleteAppProfile(id);
    }
    
    
    /**
     * Retrieve an AppFile information
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "GET", value = "Get AppProfile", notes = "Get AppProfile by id")
    public AppProfile getAppProfileByID(@ApiParam(value = "AppProfile ID", required = true) @PathVariable long id) throws Exception {

        return appProfileService.getAppProfileByID(id);
    }
    
    /**
     * Retrieve an List of AppFile  information
     *
     * @param ids
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "GET", value = "Get AppProfile List", notes = "Get list pf AppProfiles by ID's")
    public List<AppProfile> getAppProfileList(@ApiParam(value = "AppProfile ID", required = true) @PathVariable List<Long> ids) throws Exception {

        return appProfileService.getAppProfileList(ids);
    }
    
    /**
     * Retrieve an List of AppParams information
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "{id}/appprofileparam", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "GET", value = "Get AppParam List by AppProfile", notes = "Get list of AppParam by AppProfile ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<AppParam> getAppParamListByAppProfile(
            @ApiParam(value = "The appProfile id", required = true) @PathVariable("id") Long id) throws Exception {

        return appProfileService.getAppParamListByAppProfile(id);
    }
    
    /**
     * Retrieve an List of AppParams without AppProfile relation information
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "{id}/appparam", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "GET", value = "Get AppParam List without AppProfile", notes = "Get list of AppParam without AppProfile ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<AppParam> getAppParamListWithoutAppProfile(
            @ApiParam(value = "The appProfile id", required = true) @PathVariable("id") Long id) throws Exception {

        return appProfileService.getAppParamListWithoutAppProfile(id);
    }
    
    /**
     * Retrieve an List of AppFiles information
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "{id}/appprofileparam/appfile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "GET", value = "Get AppFile List by AppProfile", notes = "Get list of AppFile by AppProfile ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<AppParam> getAppFileListByAppProfile(
            @ApiParam(value = "The appProfile id", required = true) @PathVariable("id") Long id) throws Exception {

        return appProfileService.getAppFileListByAppProfile(id);
    }
    
    /**
     * Retrieve an List of AppFiles without AppProfile relation information
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "{id}/appparam/appfile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "GET", value = "Get AppFile List without AppProfile", notes = "Get list of AppFile without AppProfile ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<AppParam> getAppFileListWithoutAppProfile(
            @ApiParam(value = "The appProfile id", required = true) @PathVariable("id") Long id) throws Exception {

        return appProfileService.getAppFileListWithoutAppProfile(id);
    }
    
    /**
     * Add param value
     *
     * @param id
     * @param appParamId
     *
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/appparam/{appParamId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "POST", value = "Add AppParam to AppProfile", notes = "Add param to a profile", response = AppProfile.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = AppProfile.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public AppProfileParamValue addAppProfileParamValue(
            @ApiParam(value = "The profile identifier", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "The param identifier", required = true) @PathVariable("appParamId") Long appParamId) throws Exception {

        return appProfileService.addAppProfileParamValue(id, appParamId);
    }
    
    /**
     * Updates appProfileParam values
     *
     * @param id
     * @param appProfileParamValue
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}/appprofileparamvalue", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "PUT", value = "Update AppParam to AppProfile", notes = "Updates appProfileParam values")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = AppProfile.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public AppProfileParamValue updateAppProfileParamValue(
            @ApiParam(value = "The Approfile id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "The AppProfileParamValue object", required = true) @RequestBody(required = true) AppProfileParamValue appProfileParamValue) throws Exception {

        AppProfileParamValue existentAppProfileParamValue = appProfileService.updateAppProfileParamValue(id, appProfileParamValue);
        return existentAppProfileParamValue;
    }
    
    /**
     * Deletes appProfileParam value
     *
     * @param id
     * @param appParamId
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}/appparam/{appParamId}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "AppProfile", httpMethod = "DELETE", value = "Delete AppParam to AppProfile", notes = "Delete the relation between Param and Profile")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteAppProfileParamValue(
            @ApiParam(value = "The AppProfile id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "The AppParam id", required = true) @PathVariable("appParamId") Long appParamId) throws Exception {

        appProfileService.deleteAppProfileParamValue(id, appParamId);
    }
    
    /**
     * Retrieve an List of AppProfile information
     *
     * @param appId
     * @param entityId
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "app/{appId}/entities/{entityId}/entityAppProfile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "GET", value = "Get AppProfile List By Entity", notes = "Get list of AppProfile by entity")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<AppProfile> getAppProfileListByEntity(
            @ApiParam(value = "The app id", required = true) @PathVariable("appId") Long appId,
            @ApiParam(value = "The entity id", required = true) @PathVariable("entityId") Long entityId) throws Exception {

        List<AppProfile> appProfileList = appProfileService.getAppProfileListByEntity(appId, entityId);
        return appProfileList;
    }
    
    /**
     * Retrieve an List of AppParams without AppProfile relation information
     *
     * @param appId
     * @param entityId
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "app/{appId}/entities/{entityId}/appprofile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "GET", value = "Get AppProfile List Without Entity", notes = "Get list of AppProfile without entity")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<AppProfile> getAppProfileListWithoutEntity(
            @ApiParam(value = "The app id", required = true) @PathVariable("appId") Long appId,
            @ApiParam(value = "The entity id", required = true) @PathVariable("entityId") Long entityId) throws Exception {

        return appProfileService.getAppProfileListWithoutEntity(appId, entityId);
    }
    
    /**
     * Add appProfile value to entity
     *
     * @param id
     * @param entityId
     *
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/entities/{entityId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "POST", value = "Add AppProfile to Entity", notes = "Add profile to a entity", response = AppProfile.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = AppProfile.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public EntityAppProfile addEntityAppProfile(
            @ApiParam(value = "The profile identifier", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "The entity identifier", required = true) @PathVariable("entityId") Long entityId) throws Exception {

        return appProfileService.addEntityAppProfile(id, entityId);
    }
    
    /**
     * Updates entityAppProfile values
     *
     * @param id
     * @param entityAppProfile
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}/entities", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "PUT", value = "Update AppProfile to Entity", notes = "Updates entityAppProfile values")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = AppProfile.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public EntityAppProfile updateEntityAppProfile(
            @ApiParam(value = "The Approfile id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "The EntityAppProfile object", required = true) @RequestBody(required = true) EntityAppProfile entityAppProfile) throws Exception {

        EntityAppProfile existentEntityAppProfile = appProfileService.updateEntityAppProfile(id, entityAppProfile);
        return existentEntityAppProfile;
    }
    
    /**
     * Deletes entityAppProfile value
     *
     * @param id
     * @param entityId
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}/entities/{entityId}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "AppProfile", httpMethod = "DELETE", value = "Delete AppProfile to Entity", notes = "Delete the relation between Profile and Entity")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteEntityAppProfile(
            @ApiParam(value = "The AppProfile id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "The Entity id", required = true) @PathVariable("entityId") Long entityId) throws Exception {

        appProfileService.deleteEntityAppProfile(id, entityId);
    }
    
    /**
     * Retrieve an List of AppParam information by entity
     *
     * @param id
     * @param entityId
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "{id}/entities/{entityId}/entityAppProfileParam", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "GET", value = "Get AppParam List By Entity", notes = "Get list of AppParam by entity")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<AppParam> getAppParamListByEntity(
            @ApiParam(value = "The appProfile id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "The entity id", required = true) @PathVariable("entityId") Long entityId) throws Exception {

        List<AppParam> appParamList = appProfileService.getAppParamListByEntity(id, entityId);
        return appParamList;
    }
    
    /**
     * Retrieve an List of AppParams without AppProfile relation information
     *
     * @param id
     * @param entityId
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "{id}/entities/{entityId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "GET", value = "Get Appparam List Without Entity", notes = "Get list of AppParam without entity")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<AppParam> getAppParamListWithoutEntity(
            @ApiParam(value = "The appProfile id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "The entity id", required = true) @PathVariable("entityId") Long entityId) throws Exception {

      List<AppParam> appParamList = appProfileService.getAppParamListWithoutEntity(id, entityId);
      return appParamList;
    }
    
    /**
     * Retrieve an List of AppFile information by entity
     *
     * @param id
     * @param entityId
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "{id}/entities/{entityId}/entityAppProfileParam/appfile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "GET", value = "Get AppFile List By Entity", notes = "Get list of AppFile by entity")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<AppParam> getAppFileListByEntity(
            @ApiParam(value = "The appProfile id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "The entity id", required = true) @PathVariable("entityId") Long entityId) throws Exception {

        List<AppParam> appParamList = appProfileService.getAppFileListByEntity(id, entityId);
        return appParamList;
    }
    
    /**
     * Retrieve an List of AppFiles without AppProfile relation information
     *
     * @param id
     * @param entityId
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "{id}/entities/{entityId}/appfile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "GET", value = "Get AppFile List Without Entity", notes = "Get list of AppFile without entity")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<AppParam> getAppFileListWithoutEntity(
            @ApiParam(value = "The appProfile id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "The entity id", required = true) @PathVariable("entityId") Long entityId) throws Exception {

      List<AppParam> appParamList = appProfileService.getAppFileListWithoutEntity(id, entityId);
      return appParamList;  
    }
    
    /**
     * Add entityAppProfileParam value 
     *
     * @param id
     * @param entityId
     * @param appParamId
     *
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/entities/{entityId}/appparam/{appParamId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "POST", value = "Add AppParam to EntityAppProfile", notes = "Add param to a entityAppProfile", response = AppProfile.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = AppProfile.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public EntityAppProfileParam addEntityAppProfileParam(
            @ApiParam(value = "The profile identifier", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "The entity identifier", required = true) @PathVariable("entityId") Long entityId,
            @ApiParam(value = "The param identifier", required = true) @PathVariable("appParamId") Long appParamId) throws Exception {

        return appProfileService.addEntityAppProfileParam(id, entityId, appParamId);
    }
    
    
    /**
     * Updates entityAppProfileParam values
     *
     * @param id
     * @param entityId
     * @param entityAppProfileParam
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}/entities/{entityId}/entityAppProfileParam", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "PUT", value = "Update AppParam to EntityAppProfile", notes = "Update param to a entityAppProfile")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = AppProfile.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public EntityAppProfileParam updateEntityAppProfileParam(
            @ApiParam(value = "The Approfile id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "The entity identifier", required = true) @PathVariable("entityId") Long entityId,
            @ApiParam(value = "The EntityAppProfileParam object", required = true) @RequestBody(required = true) EntityAppProfileParam entityAppProfileParam) throws Exception {

        EntityAppProfileParam existentEntityAppProfileParam = appProfileService.updateEntityAppProfileParam(id, entityId, entityAppProfileParam);
        return existentEntityAppProfileParam;
    }
    
    /**
     * Deletes entityAppProfileParam value
     *
     * @param id
     * @param entityId
     * @param appParamId
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}/entities/{entityId}/appparam/{appParamId}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "AppProfile", httpMethod = "DELETE", value = "Delete AppParam to EntityAppProfile", notes = "Delete the relation between Param and Entity")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteEntityAppProfileParam(
            @ApiParam(value = "The AppProfile id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "The Entity id", required = true) @PathVariable("entityId") Long entityId,
            @ApiParam(value = "The Param id", required = true) @PathVariable("appParamId") Long appParamId) throws Exception {

        appProfileService.deleteEntityAppProfileParam(id, entityId, appParamId);
    }
    
    /**
     * List all files information by appProfile.
     *
     * @param id
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @RequestMapping(name = "Search Files by Profile", value = "/{id}/searchFilesByProfile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "GET", value = "Search Files by Profile", notes = "Search files by Profile. Will return 50 records if no paging parameters defined", response = AppProfile.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<AppParam> searchAppFilesByProfile(
            @ApiParam(value = "AppProfile id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "The search filter", required = true) @RequestParam(value = "filter", required = true) String filter,
            @ApiParam(value = "The page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "The rows per page", defaultValue = "50") @RequestParam(value = "rowsPerPage", required = false, defaultValue = "50") Integer rowsPerPage) throws Exception {

        return appProfileService.searchAppFileByProfile(id, filter, pageNumber, rowsPerPage);
    }
    
    /**
     * List all params information by appProfile.
     *
     * @param id
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @RequestMapping(name = "Search Params by Profile", value = "/{id}/searchParamsByProfile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "AppProfile", httpMethod = "GET", value = "Search Params by Profile", notes = "Search params by Profile. Will return 50 records if no paging parameters defined", response = AppProfile.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<AppParam> searchAppParamsByProfile(
            @ApiParam(value = "AppProfile id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "The search filter", required = true) @RequestParam(value = "filter", required = true) String filter,
            @ApiParam(value = "The page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "The rows per page", defaultValue = "50") @RequestParam(value = "rowsPerPage", required = false, defaultValue = "50") Integer rowsPerPage) throws Exception {

        return appProfileService.searchAppParamByProfile(id, filter, pageNumber, rowsPerPage);
    }
}
