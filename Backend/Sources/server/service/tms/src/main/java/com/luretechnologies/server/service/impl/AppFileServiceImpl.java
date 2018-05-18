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
package com.luretechnologies.server.service.impl;

import com.luretechnologies.server.data.dao.AppDAO;
import com.luretechnologies.server.data.dao.AppFileDAO;
import com.luretechnologies.server.data.dao.AppFileFormatDAO;
import com.luretechnologies.server.data.model.tms.App;
import com.luretechnologies.server.data.model.tms.AppFile;
import com.luretechnologies.server.data.model.tms.AppFileFormat;
import com.luretechnologies.server.service.AppFileService;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Vinay
 */
@Service
@Transactional
public class AppFileServiceImpl implements AppFileService{
    
    @Autowired(required = true)
    AppFileDAO appFileDAO;
    
    @Autowired
    AppDAO appDAO;
    
    @Autowired
    AppFileFormatDAO appFileFormatDAO;
   
    /**
     *
     * @param appFile
     * @return
     * @throws Exception
     */
    @Override
    public AppFile createAppFile(AppFile appFile) throws Exception{
        //AppFile newAppFile = new AppFile();
        App app = appFile.getApp();
        AppFileFormat appFileFormat = appFile.getAppFileFormat();

        // Check app existence
        if (app != null) {
            App existentApp = appDAO.getAppByID(appFile.getApp().getId());

            if (existentApp == null) {
                throw new ObjectRetrievalFailureException(App.class, appFile.getApp().getId());
            }
            appFile.setApp(existentApp);
        } else {
            // If not App defined throw Exception
            throw new Exception("The AppFile need to associated with some valid App.");
        }
        
        //Check appFileFormat existence
        if (appFileFormat != null) {
            AppFileFormat existentAppFileFormat = appFileFormatDAO.getAppFileFormatByID(appFile.getAppFileFormat().getId());

            if (existentAppFileFormat == null) {
                throw new ObjectRetrievalFailureException(AppFileFormat.class, appFile.getAppFileFormat().getId());
            }
            appFile.setAppFileFormat(existentAppFileFormat);
        } else {
            // If not AppFileFormat defined throw Exception
            throw new Exception("The AppFile need to associated with some valid AppFileFormat.");
        }
                
         // Copy properties from -> to
        //BeanUtils.copyProperties(appFile , newAppFile);
        appFileDAO.persist(appFile);
        return appFile;
        
    }
    
    /**
     *
     * @param ID
     * @return
     * @throws Exception
     */
    @Override
    public AppFile updateAppFile(long ID, AppFile appFile) throws Exception{
        AppFile existentAppFile = appFileDAO.getAppFileByID(ID);
                
        App app = appFile.getApp();
        AppFileFormat appFileFormat = appFile.getAppFileFormat();

        // Check app existence
        if (app != null) {
            App existentApp = appDAO.getAppByID(appFile.getApp().getId());

            if (existentApp == null) {
                throw new ObjectRetrievalFailureException(App.class, appFile.getApp().getId());
            }
            appFile.setApp(existentApp);
        } else {
            // If not App defined throw Exception
            throw new Exception("The AppFile need to associated with some valid App.");
        }
        
        //Check appFileFormat existence
        if (appFileFormat != null) {
            AppFileFormat existentAppFileFormat = appFileFormatDAO.getAppFileFormatByID(appFile.getAppFileFormat().getId());

            if (existentAppFileFormat == null) {
                throw new ObjectRetrievalFailureException(AppFileFormat.class, appFile.getAppFileFormat().getId());
            }
            appFile.setAppFileFormat(existentAppFileFormat);
        } else {
            // If not AppFileFormat defined throw Exception
            throw new Exception("The AppFile need to associated with some valid AppFileFormat.");
        }
        
        // Copy properties from -> to
        BeanUtils.copyProperties(appFile, existentAppFile);
        
        //AppFile updatedAppFile=  updateAppFile(appFile);
        return appFileDAO.merge(existentAppFile);
        
    }
    
    /**
     *
     * @param ID
     * @throws Exception
     */
    @Override
    public void deleteAppFile(long ID) throws Exception{
        AppFile deletedAppFile = appFileDAO.getAppFileByID(ID);
        appFileDAO.delete(deletedAppFile);
        
    }
    
    
    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    public Integer doForceUpdate(Integer value) throws Exception{
        Integer forceUpdateValue = appFileDAO.doForceUpdate(value);
        return forceUpdateValue;
        
    }
    
    /**
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public List<AppFile> getAppFileList(List<Long> ids) throws Exception{
        List<AppFile> appFileList = appFileDAO.getAppFileList(ids);
        return appFileList;
        
    }
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public AppFile getAppFileByID(Long id) throws Exception{
        AppFile appFile = appFileDAO.getAppFileByID(id);
        return appFile;
        
    }
    
    /*private AppFile updateAppFile(AppFile appFile)throws Exception{
        appFile.setName("Updated");
        appFileDAO.merge(appFile);
        return appFile;
    }*/
}
