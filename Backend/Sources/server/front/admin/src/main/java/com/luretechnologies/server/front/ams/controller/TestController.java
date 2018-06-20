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

import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.display.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Authentication Controller Class
 *
 *
 */
@RequestMapping("/test")
@RestController(value = "Testing")
@Api(value = "Testing")
public class TestController {

    private final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    /**
     *
     * @param uniqueId
     * @param file
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(name = "FileUpload", value = "/fileUpload", method = RequestMethod.POST)
    @ApiOperation(tags = "Testing", httpMethod = "POST", value = "File upload", notes = "Upload file to server")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "OK"),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public String fileUpload(
            @ApiParam(value = "The unique id", required = true) @RequestParam("uniqueId") String uniqueId,
            @ApiParam(value = "The file", required = true) @RequestParam("file") MultipartFile file) throws Exception {

        LOGGER.info(String.format("fileUpload: %s [%s]", file.getOriginalFilename(), uniqueId));

        if (!file.isEmpty()) {

            try {

                String fileUploadTempDir = "/tmp";
                String val = System.getProperty("FILE_UPLOAD_TEMP_DIR");

                if (val != null) {
                    fileUploadTempDir = val;
                }

                String newFilename = file.getOriginalFilename();
                int index = newFilename.lastIndexOf(".");
                String baseName = newFilename.substring(0, index);
                String extension = newFilename.substring(index);
                newFilename = baseName + "-" + Utils.getRandomString(10) + extension;

                String fullPathFilename = fileUploadTempDir + "/" + uniqueId + "/" + newFilename;
                Path pathToFile = Paths.get(fullPathFilename);
                Files.createDirectories(pathToFile.getParent());

                File diskfile = new File(fullPathFilename);
                FileOutputStream fos = new FileOutputStream(diskfile);

                byte[] bytes = file.getBytes();
                try (BufferedOutputStream stream = new BufferedOutputStream(fos)) {
                    stream.write(bytes);
                }

                return String.format("Upload Complete: %s (%d bytes)", file.getOriginalFilename(), file.getSize());

            } catch (IOException ioex) {
                return "Upload Failed: " + ioex.getMessage();
            }

        } else {
            return "Upload Failed: Empty file.";
        }
    }
}
