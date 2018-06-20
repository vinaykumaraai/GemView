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

import com.luretechnologies.server.data.dao.ActionDAO;
import com.luretechnologies.server.data.dao.AppDAO;
import com.luretechnologies.server.data.dao.AppParamDAO;
import com.luretechnologies.server.data.dao.AppParamFormatDAO;
import com.luretechnologies.server.data.dao.AppProfileDAO;
import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.dao.EntityLevelDAO;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.tms.App;
import com.luretechnologies.server.data.model.tms.AppParam;
import com.luretechnologies.server.data.model.tms.AppParamFormat;
import com.luretechnologies.server.data.model.tms.AppProfile;
import com.luretechnologies.server.service.AppService;
import java.io.File;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Vinay
 */
@Service
@Transactional
public class AppServiceImpl implements AppService{
    private final static long TYPE_FILE = 1;
    
    @Autowired
    private AppDAO appDAO;
    
    @Autowired
    private ActionDAO actionDAO;
    
    @Autowired
    private EntityLevelDAO entityLevelDAO;
    
    @Autowired 
    private AppParamDAO appParamDAO;
    
    @Autowired 
    private AppProfileDAO appProfileDAO;
        
    @Autowired
    private EntityDAO entityDAO;
    
    @Autowired
    private AppParamFormatDAO appParamFormatDAO;
    
    /**
     *
     * @param app
     * @return
     * @throws Exception
     */
    
    @Override
    public App create(App app) throws Exception {
        App newApp = new App();        
        
        // Check entity existence
        Entity existentEntity = entityDAO.findById(app.getOwnerId());

        if (existentEntity == null) {
            // If not Entity defined set Default (Enterprise)
            app.setOwnerId(entityDAO.getFirstResult().getId());
        } else
            app.setOwnerId(existentEntity.getId());
        
        // Copy properties from -> to
        BeanUtils.copyProperties(app, newApp);

        appDAO.persist(newApp);

        return newApp;
    }
    
    /**
     *
     * @param appId
     * @param description
     * @param fileContent
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public App addAppFile(Long appId, String description, MultipartFile fileContent, File file) throws Exception{
        AppParam appParam = new AppParam();
        // Throw error if the App does not exists.
        App app = appDAO.findById(appId);
        if (app == null) {
            throw new ObjectRetrievalFailureException(App.class, appId);
        }
        
        //Build appFile object before physical copy.
        appParam.setName(fileContent.getOriginalFilename());
        appParam.setDefaultValue(file.getPath());
        appParam.setDescription(description);
        appParam.setModifiable(true);
        //set AppFileFormat to file value of the table.
        appParam.setAppParamFormat(appParamFormatDAO.getAppParamFormatByID(TYPE_FILE));
        //set Action to default file value of the table
        appParam.setAction(actionDAO.findById(Long.valueOf(1)));
        //set EntityLevel to default file value of the table
        appParam.setEntityLevel(entityLevelDAO.findById(Long.valueOf(1)));
        
        //Validate if App have this file.
        for (AppParam existentAppParam : app.getAppParamCollection()) {
            if(existentAppParam.getName().equals(appParam.getName()))
                throw new Exception("The App have a File with the name: " + existentAppParam.getName());
        }        
        //Update the appParamCollection on App with the new Param of type file.
        return addAppParam(appId, appParam);
    }
    
    /**
     * @id
     * @appParam
     * @return
     * @throws Exception
     */
    @Override
    public App addAppParam(Long id, AppParam appParam) throws Exception {
        // Throw error if the App does not exists.
        App app = appDAO.findById(id);
        if (app == null) {
            throw new ObjectRetrievalFailureException(App.class, id);
        }
        //Validate if App have this param.
        for (AppParam existentAppParam : app.getAppParamCollection()) {
            if(existentAppParam.getName().equals(appParam.getName()))
                throw new Exception("The App have a default param with the name: " + existentAppParam.getName());
        }
        //Set appId on Param
        appParam.setAppId(app.getId());
        
        //Update the appParamCollection on App with the new Param
        //app.getAppParamCollection().add(appParam);
        appParamDAO.persist(appParam);
        return app;
    }
    
    /**
     * @param id
     * @param appProfile
     * @return
     * @throws Exception
     */
    @Override
    public App addAppProfile(Long id, AppProfile appProfile) throws Exception {
        // Throw error if the App does not exists.
        App app = appDAO.findById(id);
        if (app == null) {
            throw new ObjectRetrievalFailureException(App.class, id);
        }
        //Validate if App have this profile.
        for (AppProfile existentAppProfile : app.getAppProfileCollection()) {
            if(existentAppProfile.getName().equals(appProfile.getName()))
                throw new Exception("The App have a profile with the name: " + existentAppProfile.getName());
        }
        //Set appId on Profile
        appProfile.setAppId(app.getId());
        
        //Update the appProfileCollection on App with the new Profile
        //app.getAppProfileCollection().add(appProfile);
        appProfileDAO.persist(appProfile);
        return app;
    }
    
    /**
     *
     * @param appId
     * @param appParamId
     * @throws Exception
     */
    @Override
    public void deleteAppFile(Long appId, Long appParamId) throws Exception {
        // Throw error if the App does not exists.
        App app = appDAO.findById(appId);
        if (app == null) {
            throw new ObjectRetrievalFailureException(App.class, appId);
        }
        // Throw error if the AppParam not exists.
        AppParam appParam = appParamDAO.findById(appParamId);
        if (appParam == null) {
            throw new ObjectRetrievalFailureException(AppParam.class, appParamId);
        }
        
        AppParamFormat appParamFormat = appParamFormatDAO.getAppParamFormatByID(TYPE_FILE);
        
        if(Objects.equals(appParam.getAppId(), app.getId())){
            //Verify if the param is the type file
            if(appParam.getAppParamFormat().equals(appParamFormat)){
                //Delete Physically File
                File file = new File(appParam.getDefaultValue());
                if(file.exists())
                    file.delete();
                
                appParamDAO.delete(appParam);
                return;
            } 
        }
        // Throw error if the App AppParam does not exists.
        throw new ObjectRetrievalFailureException(App.class, appId);
    }
    
    /**
     * @param id
     * @throws Exception
     */
    @Override
    public void deleteAllAppFile(Long id) throws Exception {
        // Throw error if the App does not exists.
        App app = appDAO.findById(id);
        if (app == null) {
            throw new ObjectRetrievalFailureException(App.class, id);
        }
        //Delete all item of App Param collection type File.        
        for (AppParam existentAppParam : app.getAppParamCollection()) {
            if(existentAppParam.getAppParamFormat().getId().equals(TYPE_FILE))
                deleteAppFile(id, existentAppParam.getId());
        }
    }
    
    /**
     * @param id
     * @throws Exception
     */
    @Override
    public void deleteAllAppParam(Long id) throws Exception {
        // Throw error if the App does not exists.
        App app = appDAO.findById(id);
        if (app == null) {
            throw new ObjectRetrievalFailureException(App.class, id);
        }
        
        //Delete all item of App File collection.
        for (AppParam existentAppParam : app.getAppParamCollection()) {
            if(!existentAppParam.getAppParamFormat().getId().equals(TYPE_FILE))
                deleteAppParam(id, existentAppParam.getId());
        }
    }
    
    /**
     *
     * @param appParamId
     * @param appId
     * @throws Exception
     */
    @Override
    public void deleteAppParam(Long appId, Long appParamId) throws Exception {
        // Throw error if the App does not exists.
        App app = appDAO.findById(appId);
        if (app == null) {
            throw new ObjectRetrievalFailureException(App.class, appId);
        }
        // Throw error if the AppParam not exists.
        AppParam appParam = appParamDAO.findById(appParamId);
        if (appParam == null) {
            throw new ObjectRetrievalFailureException(AppParam.class, appParamId);
        }
        
        AppParamFormat appParamFormat = appParamFormatDAO.getAppParamFormatByID(TYPE_FILE);
        
        if(Objects.equals(appParam.getAppId(), app.getId())){
            //Verify if the param is the type file
            if(!appParam.getAppParamFormat().equals(appParamFormat)){
                appParamDAO.delete(appParam);
                return;
            } 
        }
        // Throw error if the App AppParam does not exists.
        throw new ObjectRetrievalFailureException(App.class, appId);
    }
    
    /**
     *
     * @param appProfileId
     * @param appId
     * @throws Exception
     */
    @Override
    public void deleteAppProfile(Long appId, Long appProfileId) throws Exception {
        // Throw error if the App does not exists.
        App app = appDAO.findById(appId);
        if (app == null) {
            throw new ObjectRetrievalFailureException(App.class, appId);
        }
        // Throw error if the AppProfile not exists.
        AppProfile appProfile = appProfileDAO.findById(appProfileId);
        if (appProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, appProfileId);
        }
        
        if(Objects.equals(appProfile.getAppId(), app.getId())){
            appProfileDAO.delete(appProfile);
            return;
        }
         
        // Throw error if the App AppProfile does not exists.
        throw new ObjectRetrievalFailureException(App.class, appId);
    }    
    
    /**
     *
     * @param id
     * @param appParam
     * @return
     * @throws Exception
     */
    @Override
    public App updateAppParam(Long id, AppParam appParam) throws Exception {
        // Throw error if the App does not exists.
        App app = appDAO.findById(id);
        if (app == null) {
            throw new ObjectRetrievalFailureException(App.class, id);
        }

        for (AppParam existentAppParam : app.getAppParamCollection()) {
            // If the AppParam is already set up for this App --> UPDATE
            if (existentAppParam.getId().equals(appParam.getId())) {
                app.getAppParamCollection().remove(existentAppParam);
                BeanUtils.copyProperties(appParam, existentAppParam, "id");
                app.getAppParamCollection().add(existentAppParam);
                
                return appDAO.merge(app);
            }
        }

        // Throw error if the App AppParam does not exists.
        throw new ObjectRetrievalFailureException(App.class, id);
    }

    /**
     * @param id
     * @param app
     * @throws Exception
     */
    @Override
    public App update(long id, App app) throws Exception {
        App existentApp = appDAO.getAppByID(id);        
        
        // Check entity existence
        Entity existentEntity = entityDAO.findById(app.getOwnerId());

        if (existentEntity == null) {
            // If not Entity defined set Default (Enterprise)
            app.setOwnerId(entityDAO.getFirstResult().getId());
        } else
            app.setOwnerId(existentEntity.getId());
        
        // Copy properties from -> to
        BeanUtils.copyProperties(app, existentApp);
        
        return appDAO.merge(existentApp);
    }

    @Override
    public void delete(long id) throws Exception {
        App app = appDAO.getAppByID(id);
        //appDAO.delete(app);
        
        //Not delete the app, just change activate value to false.
        app.setActive(false);
        appDAO.merge(app);
    }
    
     /**
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @Override
    public List<App> list(Entity entity) throws Exception {
        Long entityId = entity.getId();
        return appDAO.list(entityId);
    }
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public App get(long id) throws Exception {
        App app = appDAO.getAppByID(id);
        
        return app;
    }
    
    /*private App updateApp(App app){
        app.setName("Updated");
        appDAO.merge(app);
        return app;
    }*/
    
    /**
     *
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<App> search(String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        if (filter == null) {
            return appDAO.list(firstResult, rowsPerPage);
        } else {
            return appDAO.search(filter, firstResult, rowsPerPage);
        }
    }
    
    /**
     *
     * @param appId
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<AppParam> searchAppParam(Long appId, String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return appDAO.searchAppParam(appId, filter, firstResult, rowsPerPage);
    }
    
    /**
     *
     * @param appId
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<AppParam> searchAppFile(Long appId, String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return appDAO.searchAppFile(appId, filter, firstResult, rowsPerPage);
    }
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public List<AppProfile> getAppProfileList(Long id) throws Exception {
        // Throw error if the App does not exists.
        App app = appDAO.findById(id);
        if (app == null) {
            throw new ObjectRetrievalFailureException(App.class, id);
        }
        List<AppProfile> appProfileList = appDAO.listAppProfile(app.getId());
        return appProfileList;
    }
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public List<AppParam> getAppParamList(Long id) throws Exception {
        // Throw error if the App does not exists.
        App app = appDAO.findById(id);
        if (app == null) {
            throw new ObjectRetrievalFailureException(App.class, id);
        }
        List<AppParam> appParamList = appDAO.listAppParam(app.getId());
        return appParamList;
    }
    
    /**
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public List<AppParam> getAppFileList(Long id) throws Exception {
        // Throw error if the App does not exists.
        App app = appDAO.findById(id);
        if (app == null) {
            throw new ObjectRetrievalFailureException(App.class, id);
        }
        List<AppParam> appFileList = appDAO.listAppFile(app.getId());
        return appFileList;
    }
}



