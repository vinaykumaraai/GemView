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

import com.luretechnologies.common.enums.EncoderEnum;
import com.luretechnologies.server.data.model.tms.Application;
import com.luretechnologies.server.data.model.tms.Parameter;
import com.luretechnologies.server.data.model.tms.Software;
import com.luretechnologies.server.service.ApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Application Controller Class
 *
 *
 */
@RestController
@RequestMapping("/applications")
@Api(value = "Applications")
public class ApplicationController {

    @Autowired
    ApplicationService service;

//    /**
//     * Creates a new application
//     *
//     * @param application
//     * @return
//     * @throws java.lang.Exception
//     */
//    @ResponseStatus(HttpStatus.CREATED)
//    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ApiOperation(httpMethod = "POST",value = "Create application")
//    public Application create(@RequestBody Application application) throws Exception {
//
//        return service.create(application);
//    }
    /**
     * Retrieve an application information
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Applications", httpMethod = "GET", value = "Get application", notes = "Get application by id")
    public Application get(@ApiParam(value = "Application id", required = true) @PathVariable long id) throws Exception {

        return service.get(id);
    }

    /**
     * Updates an application information
     *
     * @param application
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Applications", httpMethod = "PUT", value = "Update application", notes = "Updates an application")
    public Application update(@ApiParam(value = "Application id", required = true) @PathVariable("id") long id,
            @ApiParam(value = "Application object", required = true) @RequestBody Application application) throws Exception {

        return service.update(id, application);
    }

//    /**
//     * Deletes a application information
//     *
//     * @param id
//     * @throws java.lang.Exception
//     */
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    @ApiOperation(httpMethod = "DELETE",value = "Delete application")
//    public void delete(@PathVariable long id) throws Exception {
//
//        service.delete(id);
//    }
    /**
     * List all applications information.
     *
     * @param name
     * @param active
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Applications", httpMethod = "GET", value = "List applications", notes = "Lists applications. Will return 50 records if no paging parameters defined", response = Application.class, responseContainer = "List")
    public List<Application> list(
            @ApiParam(value = "Application name") @RequestParam(required = false) String name,
            @ApiParam(value = "Application status") @RequestParam(required = false) Boolean active,
            @ApiParam(value = "Page number", required = false, defaultValue = "1") @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "Rows per page", required = false, defaultValue = "50") @RequestParam(name = "rowsPerPage", defaultValue = "50") Integer rowsPerPage) throws Exception {
        return service.list(name, active, pageNumber, rowsPerPage);
    }

    /**
     * Upload the application software/firmware file
     *
     * @param id
     * @param version
     * @param file
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}/software", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(tags = "Applications", httpMethod = "POST", value = "Save software/firmware", notes = "Save application software or firmware")
    public Application saveSoftware(@ApiParam(value = "Application id", required = true) @PathVariable long id,
            @ApiParam(value = "File to upload", required = true) @RequestParam MultipartFile file,
            @ApiParam(value = "File version") @RequestParam(required = true) String version) throws Exception {

        return service.saveSoftware(id, file, version, EncoderEnum.NONE);
    }

    /**
     * Retrieve an application software/firmware information
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}/software", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Applications", httpMethod = "GET", value = "Get software/firmware info", notes = "Get an application software/firmware information")
    public Software getSoftware(@ApiParam(value = "Application id", required = true) @PathVariable long id) throws Exception {

        return service.getSoftware(id);
    }

    /**
     * Retrieve an application software/firmware file
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}/software/file", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Applications", httpMethod = "GET", value = "Get software/firmware file", notes = "Get an application software/firmware file")
    public String getSoftwareFile(@ApiParam(value = "Application id", required = true) @PathVariable long id) throws Exception {

        return service.getSoftwareFile(id);
    }

//
//    /**
//     * Upload an application file
//     *
//     * @param id
//     * @param file
//     * @return
//     * @throws java.lang.Exception
//     */
//    @RequestMapping(value = "/{id}/files", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public Application addFile(@PathVariable long id, @RequestParam MultipartFile file) throws Exception {
//
//        return service.addFile(id, file);
//    }
//    /**
//     * Deletes an application file
//     *
//     * @param id
//     * @param idFile
//     * @return
//     * @throws java.lang.Exception
//     */
//    @RequestMapping(value = "/{id}/files/{idFile}", method = RequestMethod.DELETE)
//    public Application deleteFile(@PathVariable long id, @PathVariable long idFile) throws Exception {
//
//        return service.deleteFile(id, idFile);
//    }
    /**
     * Save the list of parameters of the application
     *
     * @param id
     * @param parameters
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}/parameters", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Applications", httpMethod = "POST", value = "Save parameters", notes = "Saves the parameters pertaining to an application", response = Application.class, responseContainer = "List")
    public Application saveParameters(@ApiParam(value = "Application id", required = true) @PathVariable long id,
            @ApiParam(value = "List of parameters", required = true) @RequestBody List<Parameter> parameters) throws Exception {

        return service.saveParameters(id, parameters);
    }

    /**
     * Search applications by a given filter
     *
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(name = "Search", value = "/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Applications", httpMethod = "POST", value = "Search applications", notes = "Search applications that match a given filter. Will return 50 records if no paging parameters defined", response = Application.class, responseContainer = "List")
    public List<Application> search(@ApiParam(value = "filter", required = true) @RequestParam("filter") String filter,
            @ApiParam(value = "Page number", required = false, defaultValue = "1") @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "Rows per page", required = false, defaultValue = "50") @RequestParam(name = "rowsPerPage", defaultValue = "50") Integer rowsPerPage) throws Exception {

        return service.search(filter, pageNumber, rowsPerPage);
    }
//    /**
//     * Deletes an application parameter
//     *
//     * @param id
//     * @param idParameter
//     * @return
//     * @throws java.lang.Exception
//     */
//    @RequestMapping(value = "/{id}/parameters/{idParameter}", method = RequestMethod.DELETE)
//    public Application deleteParameter(@PathVariable long id, @PathVariable long idParameter) throws Exception {
//
//        return service.deleteParameter(id, idParameter);
//    }
}
