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
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.User;
import com.luretechnologies.server.data.model.tms.App;
import com.luretechnologies.server.data.model.tms.AppParam;
import com.luretechnologies.server.data.model.tms.AppProfile;
import com.luretechnologies.server.service.AppProfileService;
import com.luretechnologies.server.service.AppService;
import com.luretechnologies.server.service.AuditUserLogService;
import com.luretechnologies.server.service.EntityService;
import com.luretechnologies.server.utils.TokenAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/app")
@Api(value = "Apps")
public class AppController {

    private final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

    @Autowired(required = true)
    AppService appService;

    @Autowired
    TokenAuthService tokenAuthService;

    @Autowired
    ServletContext context;

    @Autowired
    AuditUserLogService auditUserLogService;

    @Autowired
    EntityService entityService;
    
    /**
     * Creates a new App
     *
     * @param app
     * @param httpRequest
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/createApp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Apps", httpMethod = "POST", value = "Create App", notes = "Create a new app")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created", response = App.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public App createApp(
            @ApiParam(value = "The new app object", required = true) @RequestBody(required = true) App app,
            @ApiParam(hidden = true) HttpServletRequest httpRequest) throws Exception {

        app = appService.create(app);
        User user = tokenAuthService.getUser(httpRequest).getSystemUser();
        auditUserLogService.createAuditUserLog(user.getId(), app.getId(), "info", "create", "Create a new app");
        return app;
    }

    /**
     * Add appFile to app
     *
     * @param id
     * @param appFile
     *
     * @return
     * @throws java.lang.Exception
     */
    /*@ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/appFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Apps", httpMethod = "POST", value = "Add AppFile", notes = "Add appFile to a app", response = App.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = App.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public App addAppFile(
            @ApiParam(value = "The app id") @PathVariable("id") Long id,
            @ApiParam(value = "The new app file object", required = true) @RequestBody(required = true) AppFile appFile) throws Exception {

        return appService.addAppFile(id, appFile);
    }*/

    /**
     * Add appParam to app
     *
     * @param id
     * @param appParam
     *
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/appparam", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Apps", httpMethod = "POST", value = "Add AppParam", notes = "Add appParam to a app", response = App.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = App.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public App addAppParam(
            @ApiParam(value = "The app id") @PathVariable("id") Long id,
            @ApiParam(value = "The new app param object", required = true) @RequestBody(required = true) AppParam appParam) throws Exception {

        return appService.addAppParam(id, appParam);
    }

    /**
     * Add appProfile to app
     *
     * @param id
     * @param appProfile
     *
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/appprofile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Apps", httpMethod = "POST", value = "Add AppProfile", notes = "Add appProfile to a app", response = App.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = App.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public App addAppProfile(
            @ApiParam(value = "The app id") @PathVariable("id") Long id,
            @ApiParam(value = "The new app profile object", required = true) @RequestBody(required = true) AppProfile appProfile) throws Exception {

        App app = appService.addAppProfile(id, appProfile);
        return app;
    }

    /**
     *
     * @param id
     * @param description
     * @param fileContent
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    @ApiOperation(tags = "Apps", httpMethod = "POST", value = "File Upload", notes = "Upload file to server")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "OK"),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public String fileUpload(
            @ApiParam(value = "App id", required = true) @PathVariable("id") long id,
            @ApiParam(value = "The description", required = true) @RequestParam("description") String description,
            @ApiParam(value = "The file content", required = true) @RequestParam("file") MultipartFile fileContent) throws Exception {

        LOGGER.info(String.format("fileUpload: %s [%s]", fileContent.getName(), id));

        if (!fileContent.isEmpty()) {

            try {
                String fileUploadTempDir = "/tmp";
                String val = System.getProperty("FILE_UPLOAD_TEMP_DIR");

                if (val != null) {
                    fileUploadTempDir = val;
                }

                //Add to the path the id and check if directory exists and
                //else create that.
                File directory = new File(fileUploadTempDir + "/" + id);
                if (!directory.exists()) {
                    directory.mkdir();
                }

                byte[] bytes = fileContent.getBytes();
                File diskfile = new File(directory + "/" + fileContent.getOriginalFilename());

                if (appService.addAppFile(id, description, fileContent, diskfile) != null) {
                    FileOutputStream fos = new FileOutputStream(diskfile);

                    try (BufferedOutputStream stream = new BufferedOutputStream(fos)) {
                        stream.write(bytes);
                    }
                }

                return String.format("Upload Complete: %s (%d bytes)", fileContent.getOriginalFilename(), fileContent.getSize());

            } catch (IOException ioex) {
                return "Upload Failed: " + ioex.getMessage();
            }

        } else {
            return "Upload Failed: Empty file.";
        }
    }

    /**
     * Deletes app file value
     *
     * @param id
     * @param appFileId
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}/appFile/{appFileId}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Apps", httpMethod = "DELETE", value = "Delete AppFile", notes = "Delete file of app")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteAppFile(
            @ApiParam(value = "App id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "AppFile id", required = true) @PathVariable("appFileId") Long appFileId) throws Exception {

        appService.deleteAppFile(id, appFileId);
    }

    /**
     * Deletes all app file value by app
     *
     * @param id
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}/appFile", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Apps", httpMethod = "DELETE", value = "Delete All AppFiles", notes = "Delete all files of app")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteAllAppFile(
            @ApiParam(value = "App id", required = true) @PathVariable("id") Long id) throws Exception {

        appService.deleteAllAppFile(id);
    }

    /**
     * Deletes all app param value by app
     *
     * @param id
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}/appparam", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Apps", httpMethod = "DELETE", value = "Delete All AppParams", notes = "Delete all params of app")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteAllAppParam(
            @ApiParam(value = "App id", required = true) @PathVariable("id") Long id) throws Exception {

        appService.deleteAllAppParam(id);
    }

    /**
     * Deletes app param value
     *
     * @param id
     * @param appParamId
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}/appparam/{appParamId}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Apps", httpMethod = "DELETE", value = "Delete AppParam", notes = "Delete param of app")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteAppParam(
            @ApiParam(value = "App id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "AppParam id", required = true) @PathVariable("appParamId") Long appParamId) throws Exception {

        appService.deleteAppParam(id, appParamId);
    }

    /**
     * Deletes app profile value
     *
     * @param id
     * @param appProfileId
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}/appprofile/{appProfileId}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Apps", httpMethod = "DELETE", value = "Delete AppProfile", notes = "Delete profile of app")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void deleteAppProfile(
            @ApiParam(value = "App id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "AppProfile id", required = true) @PathVariable("appProfileId") Long appProfileId) throws Exception {

        appService.deleteAppProfile(id, appProfileId);
    }

    /**
     * List all applications information.
     *
     * @param authToken
     * @param httpRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAppList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Apps", httpMethod = "GET", value = "List Apps", notes = "Lists apps. Will return 50 records if no paging parameters defined", response = App.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<App> getAppList(
            @ApiParam(value = "The authentication token") @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
            @ApiParam(hidden = true) HttpServletRequest httpRequest) throws Exception {

        User user = tokenAuthService.getUser(httpRequest).getSystemUser();
        return appService.list(user.getEntity());
    }
    
    /**
     * List all applications information.
     *
     * @param entityId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "entities/{entityId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Apps", httpMethod = "GET", value = "List Apps by Entity", notes = "Lists apps by entity. Will return 50 records if no paging parameters defined", response = App.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<App> getAppListByEntityAndHierarchyEntity(
            @ApiParam(value = "The Entity identifier") @PathVariable("entityId") long entityId) throws Exception {

        List<App> appList = new ArrayList<>();
        Entity entity = entityService.findById(entityId);
        while (entity != null) {
            for (App app : appService.list(entity)) {
                if (app.getOwnerId().equals(entity.getId())) {
                    appList.add(app);
                }
            }
            if(entity.getParentId() != null)
                entity = entityService.findById(entity.getParentId());
            else
                entity = null;
        }
        return appList;
    }

    /**
     * Retrieve an App information
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}/getAppByID" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Apps", httpMethod = "GET", value = "Get App", notes = "Get App by id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = App.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public App getAppByID(
            @ApiParam(value = "The app id", required = true) @PathVariable("id") long id) throws Exception {

        return appService.get(id);
    }

    /**
     * Updates an appParam information
     *
     * @param id
     * @param appParam
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}/appparam", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Apps", httpMethod = "PUT", value = "Update AppParam", notes = "Updates an appParam")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = App.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public App updateAppParam(
            @ApiParam(value = "App id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "AppParam object", required = true) @RequestBody(required = true) AppParam appParam) throws Exception {

        return appService.updateAppParam(id, appParam);
    }

    /**
     * Updates an app information
     *
     * @param id
     * @param app
     * @param httpRequest
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Apps", httpMethod = "PUT", value = "Update App", notes = "Updates an app")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = User.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public App update(
            @ApiParam(value = "App id", required = true) @PathVariable("id") long id,
            @ApiParam(value = "App object", required = true) @RequestBody(required = true) App app,
            @ApiParam(hidden = true) HttpServletRequest httpRequest) throws Exception {

        User user = tokenAuthService.getUser(httpRequest).getSystemUser();
        auditUserLogService.createAuditUserLog(user.getId(), app.getId(), "info", "update", "Updated an app");
        return appService.update(id, app);
    }

    /**
     * Deletes a App information
     *
     * @param id
     * @param httpRequest
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Apps", httpMethod = "DELETE", value = "Delete App", notes = "Delete an app")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Deleted"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void delete(
            @ApiParam(value = "The App identifier") @PathVariable("id") long id,
            @ApiParam(hidden = true) HttpServletRequest httpRequest) throws Exception {

        User user = tokenAuthService.getUser(httpRequest).getSystemUser();
        auditUserLogService.createAuditUserLog(user.getId(), id, "info", "delete", "Deleted an app.");
        appService.delete(id);
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
    @ApiOperation(tags = "Apps", httpMethod = "GET", value = "Search Apps", notes = "Search apps. Will return 50 records if no paging parameters defined", response = App.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<App> search(
            @ApiParam(value = "The search filter", required = true) @RequestParam(value = "filter", required = true) String filter,
            @ApiParam(value = "The page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "The rows per page", defaultValue = "50") @RequestParam(value = "rowsPerPage", required = false, defaultValue = "50") Integer rowsPerPage) throws Exception {

        return appService.search(filter, pageNumber, rowsPerPage);
    }
    
    /**
     * List all params information by app.
     *
     * @param id
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @RequestMapping(name = "Search Params", value = "/{id}/searchParams", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Apps", httpMethod = "GET", value = "Search Params", notes = "Search params. Will return 50 records if no paging parameters defined", response = App.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<AppParam> searchAppParams(
            @ApiParam(value = "App id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "The search filter", required = true) @RequestParam(value = "filter", required = true) String filter,
            @ApiParam(value = "The page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "The rows per page", defaultValue = "50") @RequestParam(value = "rowsPerPage", required = false, defaultValue = "50") Integer rowsPerPage) throws Exception {

        return appService.searchAppParam(id, filter, pageNumber, rowsPerPage);
    }
    
    /**
     * List all files information by app.
     *
     * @param id
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @RequestMapping(name = "Search Files", value = "/{id}/searchFiles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Apps", httpMethod = "GET", value = "Search Files", notes = "Search files. Will return 50 records if no paging parameters defined", response = App.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<AppParam> searchAppFiles(
            @ApiParam(value = "App id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "The search filter", required = true) @RequestParam(value = "filter", required = true) String filter,
            @ApiParam(value = "The page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "The rows per page", defaultValue = "50") @RequestParam(value = "rowsPerPage", required = false, defaultValue = "50") Integer rowsPerPage) throws Exception {

        return appService.searchAppFile(id, filter, pageNumber, rowsPerPage);
    }

    /**
     * List all appFiles information by app id.
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "{id}/appFile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Apps", httpMethod = "GET", value = "List AppFiles", notes = "Lists appFiles. Will return 50 records if no paging parameters defined", response = App.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<AppParam> getAppFileList(
            @ApiParam(value = "The app id", required = true) @PathVariable("id") Long id) throws Exception {

        return appService.getAppFileList(id);
    }

    /**
     * List all appParams information by app id.
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "{id}/appparam", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Apps", httpMethod = "GET", value = "List AppParams", notes = "Lists appParams. Will return 50 records if no paging parameters defined", response = App.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<AppParam> getAppParamList(
            @ApiParam(value = "The app id", required = true) @PathVariable("id") Long id) throws Exception {

        return appService.getAppParamList(id);
    }

    /**
     * List all appProfiles information by app id.
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "{id}/appprofile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Apps", httpMethod = "GET", value = "List AppProfiles", notes = "Lists appProfiles. Will return 50 records if no paging parameters defined", response = App.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public List<AppProfile> getAppProfileList(
            @ApiParam(value = "The app id", required = true) @PathVariable("id") Long id) throws Exception {

        return appService.getAppProfileList(id);
    }
}
