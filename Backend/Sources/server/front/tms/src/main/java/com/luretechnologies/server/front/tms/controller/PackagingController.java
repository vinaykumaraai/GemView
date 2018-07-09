/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.server.front.tms.controller;

import com.luretechnologies.server.data.display.ErrorResponse;
import com.luretechnologies.server.data.display.tms.AppDisplay;
import com.luretechnologies.server.data.display.tms.HeartbeatAppInfoDisplay;
import com.luretechnologies.server.data.model.SystemParam;
import com.luretechnologies.server.service.AppProfileService;
import com.luretechnologies.server.service.HeartbeatService;
import com.luretechnologies.server.service.PackagingService;
import com.luretechnologies.server.service.SystemParamsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author michael
 */
@RestController
@RequestMapping("/packaging")
@Api(value = "Packaging")
public class PackagingController {
    
    @Autowired
    PackagingService packagingService;
    
    @Autowired
    HeartbeatService heartbeatService;
    
    @Autowired
    AppProfileService appProfileService;
    
    @Autowired
    SystemParamsService systemParamsService;
    
    /**
     * Creates a new Package
     *
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "createPackaging", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Packaging", httpMethod = "POST", value = "Create Package", notes = "Creates a new package")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class)})
    public void createPackage() throws Exception {
        //Taked the current date/time. 
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        List<Long> terminalIdList;
        //Obtain the last packaging date.
        SystemParam systemParam = systemParamsService.getByName("lastPackaging");
        
        if (systemParam == null) 
            terminalIdList = heartbeatService.getByHeartbeatSwComponentLastUpdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1980-01-01 00:00:00"));
        else 
            terminalIdList = heartbeatService.getByHeartbeatSwComponentLastUpdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(systemParam.getValue()));
        
        
        //Register the new last packaging date on SystemParams.
        systemParamsService.create("lastPackaging", "last packaging", timeStamp, String.valueOf(8));
        
        if(terminalIdList != null){
            for (Long terminalId : terminalIdList) {
                //Get AppDisplay List by TerminalId.
                List<AppDisplay> appDisplayList = appProfileService.getAppDisplayList(terminalId); //terminalId
                if(appDisplayList.size() > 0){
                    //Get HeartbeatAppInfoDisplay by Terminal
                    List<HeartbeatAppInfoDisplay> heartbeatAppInfoDisplayList = heartbeatService.getSwComponents(terminalId);  

                    packagingService.createPackage(terminalId, appDisplayList, heartbeatAppInfoDisplayList); 
                }
            }
        }
    }
}
