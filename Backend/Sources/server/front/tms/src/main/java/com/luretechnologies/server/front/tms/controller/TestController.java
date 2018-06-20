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

import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.display.ErrorResponse;
import com.luretechnologies.server.data.model.tms.AppParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
        @ApiResponse(code = 204, message = "OK")
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
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

    /**
     *
     * @param file
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(name = "fileImportXls", value = "/fileImportXls", method = RequestMethod.POST)
    @ApiOperation(tags = "Testing", httpMethod = "POST", value = "File import Excel", notes = "Import Excel file to server")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "OK")
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public String fileImportXls(
            @ApiParam(value = "The file", required = true) @RequestParam("file") MultipartFile file) throws Exception {

        System.out.println(String.format("fileImportXls: %s", file.getOriginalFilename()));

        if (!file.isEmpty()) {

            try {

                String lowerName = file.getOriginalFilename().toLowerCase();
                Workbook workbook;

                InputStream is = new ByteArrayInputStream(file.getBytes());

                if (lowerName.endsWith(".xlsx")) {
                    workbook = new XSSFWorkbook(is);
                } else if (lowerName.endsWith(".xls")) {
                    workbook = new HSSFWorkbook(is);
                } else {
                    return "Xls Import Failed: Unsupported file format. Must be .xls or .xlsx.";
                }

                Sheet worksheet = workbook.getSheet("Params");

                if (worksheet != null) {

                    Iterator<Row> iterator = worksheet.iterator();

                    while (iterator.hasNext()) {

                        Row currentRow = iterator.next();
                        Iterator<Cell> cellIterator = currentRow.iterator();

                        StringBuilder sb = new StringBuilder();

                        while (cellIterator.hasNext()) {

                            Cell currentCell = cellIterator.next();

                            if (currentCell.getCellTypeEnum() != null) {

                                switch (currentCell.getCellTypeEnum()) {
                                    case STRING:
                                        sb.append(" [STRING] ");
                                        sb.append(currentCell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        sb.append(" [NUMERIC] ");
                                        sb.append(currentCell.getNumericCellValue());
                                        break;
                                    case BOOLEAN:
                                        sb.append(" [BOOLEAN] ");
                                        sb.append(currentCell.getBooleanCellValue());
                                        break;
                                    default:
                                        continue;
                                }

                                sb.append(" -- ");
                            }
                        }

                        System.out.println(sb.toString());
                    }

                    return String.format("Xls Import Complete: %s (%d bytes)", file.getOriginalFilename(), file.getSize());
                }

                return "Xls Import Failed: No worksheet named [params] was found.";

            } catch (IOException ioex) {
                return "Xls Import Failed: " + ioex.getMessage();
            }

        } else {
            return "Xls Import Failed: Empty file.";
        }
    }

    /**
     *
     * @param file
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(name = "fileImportParamXls", value = "/fileImportParamXls", method = RequestMethod.POST)
    @ApiOperation(tags = "Testing", httpMethod = "POST", value = "File import Parameter Excel", notes = "Import Parameter Excel file to server")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "OK")
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public String fileImportParamXls(
            @ApiParam(value = "The file", required = true) @RequestParam("file") MultipartFile file) throws Exception {

        System.out.println(String.format("fileImportXls: %s", file.getOriginalFilename()));

        if (!file.isEmpty()) {

            int row;
            int column;
            String title;
            AppParam param = null;
            List<AppParam> params = new ArrayList<>();

            try {

                String lowerName = file.getOriginalFilename().toLowerCase();
                Workbook workbook;

                InputStream is = new ByteArrayInputStream(file.getBytes());

                if (lowerName.endsWith(".xlsx")) {
                    workbook = new XSSFWorkbook(is);
                } else if (lowerName.endsWith(".xls")) {
                    workbook = new HSSFWorkbook(is);
                } else {
                    return "Xls Param Import Failed: Unsupported file format. Must be .xls or .xlsx.";
                }

                Sheet worksheet = workbook.getSheet("Params");

                if (worksheet != null) {

                    Iterator<Row> rowIterator = worksheet.iterator();
                    row = 0;

                    while (rowIterator.hasNext()) {

                        Row currentRow = rowIterator.next();
                        Iterator<Cell> columnIterator = currentRow.iterator();
                        column = 0;

                        while (columnIterator.hasNext()) {

                            switch (column) {

                                case 0:
                                    title = "id";
                                    break;
                                case 1:
                                    title = "name";
                                    break;
                                case 2:
                                    title = "description";
                                    break;
                                case 3:
                                    title = "format";
                                    break;
                                case 4:
                                    title = "default";
                                    break;
                                default:
                                    // only process five columns
                                    continue;
                            }

                            if (column == 0) {
                                System.out.println("Skipping column: " + title);
                                column++;
                                continue;
                            }

                            param = new AppParam();

                            Cell currentCell = columnIterator.next();

                            if (currentCell.getCellTypeEnum() != null) {

                                switch (currentCell.getCellTypeEnum()) {

                                    case STRING:

                                        switch (column) {

                                            case 1:
                                                param.setName(currentCell.getStringCellValue());
                                                break;
                                            case 2:
                                                param.setDescription(currentCell.getStringCellValue());
                                                break;
                                            case 3:
                                                //param.getAppParamFormat(currentCell.getStringCellValue());
                                                break;
                                            case 4:
                                                param.setDefaultValue(currentCell.getStringCellValue());
                                                break;
                                        }

                                        break;

                                    case NUMERIC:

                                        switch (column) {

                                            case 4:
                                                param.setDefaultValue(Double.toString(currentCell.getNumericCellValue()));
                                                break;

                                            default:
                                                throw new Exception("Invalid numeric value in column: " + title);
                                        }

                                        break;

                                    case BOOLEAN:

                                        switch (column) {

                                            case 4:
                                                param.setDefaultValue(currentCell.getBooleanCellValue() ? "1" : "0");
                                                break;

                                            default:
                                                throw new Exception("Invalid boolean value in column: " + title);
                                        }

                                        break;

                                    default:
                                        continue;
                                }
                            }

                            column++;
                        }

                        params.add(param);
                        row++;
                    }

                    for (AppParam p : params) {
                        String output = String.format("%s [%s] = %s", p.getName(), p.getDescription(), p.getDefaultValue());
                        System.out.println(output);
                    }

                    return String.format("Xls Param Import Complete: %s (%d bytes)", file.getOriginalFilename(), file.getSize());
                }

                return "Xls Param Import Failed: No worksheet named [params] was found.";

            } catch (IOException ioex) {
                return "Xls Param Import Failed: " + ioex.getMessage();
            }

        } else {
            return "Xls Param Import Failed: Empty file.";
        }
    }

    /**
     *
     * @param file
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(name = "fileImportZip", value = "/fileImportZip", method = RequestMethod.POST)
    @ApiOperation(tags = "Testing", httpMethod = "POST", value = "File import Zip", notes = "Import Zip file to server")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "OK")
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
        ,
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public String fileImportZip(
            @ApiParam(value = "The file", required = true) @RequestParam("file") MultipartFile file) throws Exception {

        System.out.println(String.format("fileImportZip: %s", file.getOriginalFilename()));

        if (!file.isEmpty()) {

            try {

                InputStream is = new ByteArrayInputStream(file.getBytes());

                try (ZipArchiveInputStream zip = new ZipArchiveInputStream(is)) {

                    ZipArchiveEntry entry = zip.getNextZipEntry();

                    while (entry != null) {
                        System.out.println(String.format("Zip Entry: %s (%d bytes)", entry.getName(), entry.getSize()));
                        entry = zip.getNextZipEntry();
                    }

                    return String.format("Zip Import Complete: %s (%d bytes)", file.getOriginalFilename(), file.getSize());
                }

            } catch (IOException ioex) {
                return "Zip Import Failed: " + ioex.getMessage();
            }

        } else {
            return "Zip Import Failed: Empty file.";
        }
    }
}
