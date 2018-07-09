/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.server.service.impl;

import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.dao.SystemParamsDAO;
import com.luretechnologies.server.data.dao.TerminalDAO;
import com.luretechnologies.server.data.display.tms.AppDisplay;
import com.luretechnologies.server.data.display.tms.AppFileDisplay;
import com.luretechnologies.server.data.display.tms.AppParamDisplay;
import com.luretechnologies.server.data.display.tms.DownloadInfoDisplay;
import com.luretechnologies.server.data.display.tms.HeartbeatAppInfoDisplay;
import com.luretechnologies.server.data.display.tms.HeartbeatResponseDisplay;
import com.luretechnologies.server.service.HeartbeatResponseService;
import com.luretechnologies.server.service.PackagingService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author michael
 */
@Service
@Transactional
public class PackagingServiceImpl implements PackagingService{
    
    
    
    @Autowired
    TerminalDAO terminalDAO;
    
    @Autowired
    SystemParamsDAO systemParamsDAO;
    
    @Autowired 
    EntityDAO entityDAO;
    
    @Autowired
    HeartbeatResponseService heartbeatResponseService;

    
    @Override
    public void createPackage(Long terminalId, List<AppDisplay> appDisplayList, List<HeartbeatAppInfoDisplay> heartbeatAppInfoDisplayList) throws Exception {
    //Compare the AppDisplay Object with each HeartbeatAppInfoDisplay Object.
        for (HeartbeatAppInfoDisplay heartbeatAppInfoDisplay : heartbeatAppInfoDisplayList) {
            String params;
            for (AppDisplay appDisplay : appDisplayList) {
                if(heartbeatAppInfoDisplay.getName().equals(appDisplay.getName())){
                    if(!heartbeatAppInfoDisplay.getVersion().equals(appDisplay.getVersion())){
                        //Create a new Complete Package
                        params = createParamFile(appDisplay.getAppParams());
                        packaging(terminalId, params, appDisplay.getAppFiles());
                    } else {
                        Date heartbeatAppInfoDisplayDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(heartbeatAppInfoDisplay.getUpdatedAt());
                        //Find and Create a Parameter File with Update values date after of heartbeatAppInfoDisplayDate.
                        if(heartbeatAppInfoDisplayDate.before(appDisplay.getUpdatedAt())){
                            List <AppParamDisplay> appParamDisplayList = new ArrayList<>();
                            for(AppParamDisplay appParamDisplay : appDisplay.getAppParams()){
                                if(appParamDisplay.getUpdatedAt().after(heartbeatAppInfoDisplayDate))
                                    appParamDisplayList.add(appParamDisplay);
                            }
                            
                            List <AppFileDisplay> appFileDisplayList = new ArrayList<>();
                            for(AppFileDisplay appFileDisplay : appDisplay.getAppFiles()){
                                if(appFileDisplay.getUpdatedAt().after(heartbeatAppInfoDisplayDate))
                                    appFileDisplayList.add(appFileDisplay);
                            }
                            //Packaging the updates values.
                            params = createParamFile(appParamDisplayList);
                            packaging(terminalId, params, appFileDisplayList);
                            
                        }
                    }
                }
            }
        }
    }
    
    public String createParamFile(List<AppParamDisplay> appParamDisplayList) throws Exception {
        //Create a new stringbuilder
        StringBuilder sb = new StringBuilder();
        for (AppParamDisplay appParamDisplay : appParamDisplayList) 
            sb.append(appParamDisplay.getName()).append(" = ").append(appParamDisplay.getValue()).append("\n");
        return sb.toString();
    }
    
    public void packaging(Long terminalId, String params, List<AppFileDisplay> appDisplayFileList) throws Exception{
        //Generate the random file name.
        String fileName = Utils.getRandomString(16);
        //Create the file on ftp directory.
        String path = systemParamsDAO.getByName("ftp_download").getValue();
        //Create a directory of not exists.
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdir();
        }
        //Create a file
        File file = new File(path + "/" + fileName + ".zip");

        //Start to create a zip.
        try {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                ZipArchiveOutputStream out = new ZipArchiveOutputStream(fos);
                //Add Files to zip.
                if (appDisplayFileList.size() > 0) {
                    for (int i = 0; i < appDisplayFileList.size(); i++) {
                        out.putArchiveEntry(new ZipArchiveEntry(appDisplayFileList.get(i).getName()));
                        try (FileInputStream fis = new FileInputStream(appDisplayFileList.get(i).getValue())) {
                            IOUtils.copy(fis, out);
                        }
                        out.flush();
                        out.closeArchiveEntry();
                    }
                }
                //Add Params to zip
                if (!params.isEmpty()) {
                    out.putArchiveEntry(new ZipArchiveEntry("params.txt"));
                    byte[] bytes = params.getBytes();
                    out.write(bytes);
                    out.flush();
                    out.closeArchiveEntry();
                }
                out.flush();
                out.finish();
            }
            
            
        //Create heartbeatResponse
        DownloadInfoDisplay downloadInfoDisplay = new DownloadInfoDisplay("temp", "pass", fileName, file.getAbsolutePath(), file.getAbsolutePath());
        HeartbeatResponseDisplay heartbeatResponseDisplay = new HeartbeatResponseDisplay(0, "download package", Long.valueOf(50000), Boolean.TRUE);
        heartbeatResponseDisplay.setDownloadInfo(downloadInfoDisplay);
        heartbeatResponseService.create(terminalId, heartbeatResponseDisplay);
            
        } catch (IOException ex) {
            throw new ObjectRetrievalFailureException(AppDisplay.class, ex);
        }
    } 
}
