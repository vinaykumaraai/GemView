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
import com.luretechnologies.server.data.dao.AppParamDAO;
import com.luretechnologies.server.data.dao.AppProfileDAO;
import com.luretechnologies.server.data.dao.AppProfileParamValueDAO;
import com.luretechnologies.server.data.dao.EntityAppProfileDAO;
import com.luretechnologies.server.data.dao.EntityAppProfileParamDAO;
import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.display.tms.AppDisplay;
import com.luretechnologies.server.data.display.tms.AppFileDisplay;
import com.luretechnologies.server.data.display.tms.AppParamDisplay;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.tms.App;
import com.luretechnologies.server.data.model.tms.AppParam;
import com.luretechnologies.server.data.model.tms.AppProfile;
import com.luretechnologies.server.data.model.tms.AppProfileParamValue;
import com.luretechnologies.server.data.model.tms.EntityAppProfile;
import com.luretechnologies.server.data.model.tms.EntityAppProfileParam;
import com.luretechnologies.server.service.AppProfileService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
public class AppProfileServiceImpl implements AppProfileService{
    private static final long TYPE_FILE = 1; //type file on AppParamFormat
    
    @Autowired
    AppProfileDAO appProfileDAO;
    
    @Autowired
    AppDAO appDAO;
    
    @Autowired
    AppProfileParamValueDAO appProfileParamValueDAO;
    
    @Autowired
    AppParamDAO appParamDAO;
    
    @Autowired
    EntityDAO entityDAO;
    
    @Autowired
    EntityAppProfileDAO entityAppProfileDAO;
    
    @Autowired
    EntityAppProfileParamDAO entityAppProfileParamDAO;
    
    /**
     *
     * @param appProfile
     * @return
     * @throws Exception
     */
    @Override
    public AppProfile createAppProfile(AppProfile appProfile) throws Exception {
        AppProfile newAppProfile = new AppProfile();
        
        // Check app existence
        App existentApp = appDAO.getAppByID(appProfile.getAppId());

        if (existentApp == null) {
            throw new ObjectRetrievalFailureException(App.class, appProfile.getAppId());
        }
        appProfile.setAppId(existentApp.getId());
        
        
         // Copy properties from -> to
        BeanUtils.copyProperties(appProfile , newAppProfile);
        appProfileDAO.persist(appProfile);
        return appProfile;
        
    }
    
    /**
     *
     * @param ID
     * @param appProfile
     * @return
     * @throws Exception
     */
    @Override
    public AppProfile updateAppProfile(long ID, AppProfile appProfile) throws Exception{
        AppProfile existentAppProfile = appProfileDAO.getAppProfileByID(ID);
        
        // Check app existence
        App existentApp = appDAO.getAppByID(appProfile.getAppId());

        if (existentApp == null) {
            throw new ObjectRetrievalFailureException(App.class, appProfile.getAppId());
        }
        appProfile.setAppId(existentApp.getId());
        
         // Copy properties from -> to
        BeanUtils.copyProperties(appProfile , existentAppProfile);
        return appProfileDAO.merge(existentAppProfile);
        
    }
    
    /**
     *
     * @param ID
     * @throws Exception
     */
    @Override
    public void deleteAppProfile(long ID) throws Exception{
        AppProfile deletedAppProfile = appProfileDAO.getAppProfileByID(ID);
        appProfileDAO.delete(deletedAppProfile);
        
    }
    
    /**
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public List<AppProfile> getAppProfileList(List<Long> ids) throws Exception{
        List<AppProfile> appProfileList = appProfileDAO.getAppProfileList(ids);
        return appProfileList;
        
    }
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public AppProfile getAppProfileByID(Long id) throws Exception{
        AppProfile appProfile = appProfileDAO.getAppProfileByID(id);
        return appProfile;
        
    }
    
    /*private AppProfile updateAppProfile(AppProfile appProfile)throws Exception{
        appProfile.setName("Updated");
        appProfileDAO.merge(appProfile);
        return appProfile;
    }*/
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public List<AppParam> getAppParamListByAppProfile(Long id) throws Exception {
        // Throw error if the AppProfile does not exists.
        AppProfile appProfile = appProfileDAO.getAppProfileByID(id);
        if (appProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, id);
        }
        List<AppParam> appParamList = appProfileDAO.getAppParamList(appProfile.getId());
        return appParamList;
    }
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public List<AppParam> getAppParamListWithoutAppProfile(Long id) throws Exception {
        // Throw error if the AppProfile does not exists.
        AppProfile appProfile = appProfileDAO.getAppProfileByID(id);
        List<AppParam> appParamList = new ArrayList<>();
        if (appProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, id);
        }
        App app = appDAO.getAppByID(appProfile.getAppId());
        List<AppParam> existentAppParamList = getAppParamListByAppProfile(appProfile.getId());
        for (AppParam appParam : app.getAppParamCollection()) {
            if(!existentAppParamList.contains(appParam) && 
                    !appParam.getAppParamFormat().getId().equals(TYPE_FILE))
                appParamList.add(appParam);
        }
        return appParamList;
    }
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public List<AppParam> getAppFileListByAppProfile(Long id) throws Exception {
        // Throw error if the AppProfile does not exists.
        AppProfile appProfile = appProfileDAO.getAppProfileByID(id);
        if (appProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, id);
        }
        List<AppParam> appParamList = appProfileDAO.getAppFileList(appProfile.getId());
        return appParamList;
    }
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public List<AppParam> getAppFileListWithoutAppProfile(Long id) throws Exception {
        // Throw error if the AppProfile does not exists.
        AppProfile appProfile = appProfileDAO.getAppProfileByID(id);
        List<AppParam> appParamList = new ArrayList<>();
        if (appProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, id);
        }
        App app = appDAO.getAppByID(appProfile.getAppId());
        List<AppParam> existentAppParamList = getAppFileListByAppProfile(appProfile.getId());
        for (AppParam appParam : app.getAppParamCollection()) {
            if(!existentAppParamList.contains(appParam) &&
                    appParam.getAppParamFormat().getId().equals(TYPE_FILE))
                appParamList.add(appParam);
        }
        return appParamList;
    }
    
    /**
     *
     * @param id
     * @param appParamId 
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public AppProfileParamValue addAppProfileParamValue(Long id, Long appParamId) throws Exception {
        // Throw error if the AppProfile does not exists.
        AppProfile appProfile = appProfileDAO.getAppProfileByID(id);
        if (appProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, id);
        }
        
        AppProfileParamValue appProfileParamValue = appProfileParamValueDAO.findByAppProfileAndAppParam(appProfile.getId(), appParamId);
        if (appProfileParamValue != null) {
            throw new Exception("Already exists");
        }
        
        AppParam appParam = appParamDAO.findById(appParamId);
        if (appParam == null) {
            throw new ObjectRetrievalFailureException(AppParam.class, appParamId);
        }
        
        appProfileParamValue = new AppProfileParamValue();
        appProfileParamValue.setAppParamId(appParam.getId());
        appProfileParamValue.setDefaultValue(appParam.getDefaultValue());
        appProfileParamValue.setAppProfileId(appProfile.getId());        
        appProfileParamValueDAO.persist(appProfileParamValue);
        
        return appProfileParamValue;
    }
    
    /**
     *
     * @param id
     * @param appProfileParamValue 
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public AppProfileParamValue updateAppProfileParamValue(Long id, AppProfileParamValue appProfileParamValue) throws Exception {
        
        // Throw error if the AppProfile does not exists.
        AppProfile appProfile = appProfileDAO.getAppProfileByID(id);
        if (appProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, id);
        }
        
        // If the appProfileParamValue is already set up for this AppProfile  --> UPDATE
        for (AppProfileParamValue existentAppProfileParamValue : appProfile.getAppProfileParamValueCollection()) {
            if (existentAppProfileParamValue.getAppParamId().equals(appProfileParamValue.getAppParamId())) {

                appProfile.getAppProfileParamValueCollection().remove(existentAppProfileParamValue);
                BeanUtils.copyProperties(appProfileParamValue, existentAppProfileParamValue, "id", "app_param", "app_profile");
                appProfile.getAppProfileParamValueCollection().add(existentAppProfileParamValue);

                appProfileDAO.merge(appProfile);
                return existentAppProfileParamValue;
            }
        }
        // Throw error if the AppProfile does not exists.
        throw new ObjectRetrievalFailureException(AppProfile.class, id);
    }
    
    /**
     *
     * @param id
     * @param appParamId
     * @throws Exception
     */
    @Override
    public void deleteAppProfileParamValue(Long id, Long appParamId) throws Exception {

        // Throw error if the AppProfile does not exists.
        AppProfile appProfile = appProfileDAO.getAppProfileByID(id);
        if (appProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, id);
        }
        AppProfileParamValue appProfileParamValue = appProfileParamValueDAO.findByAppProfileAndAppParam(appProfile.getId(), appParamId);
        if (appProfileParamValue == null) {
            throw new ObjectRetrievalFailureException(AppParam.class, appParamId);
        }
        appProfileParamValueDAO.delete(appProfileParamValue);
    }
    
    /**
     *
     * @param appId
     * @param entityId
     * @return
     * @throws Exception
     */
    @Override
    public List<AppProfile> getAppProfileListByEntity(Long appId, Long entityId) throws Exception {
        // Throw error if the App does not exists.
        App app = appDAO.getAppByID(appId);
        if (app == null) {
            throw new ObjectRetrievalFailureException(App.class, appId);
        }
        
        // Throw error if the Entity does not exists.
        Entity entity = entityDAO.findById(entityId);
        if (entity == null) {
            throw new ObjectRetrievalFailureException(Entity.class, entityId);
        }
        
        return appProfileDAO.getAppProfileList(appId, entityId);
    }
    
    /**
     *
     * @param entityId
     * @return
     * @throws Exception
     */
    @Override
    public List<AppDisplay> getAppDisplayList(Long entityId) throws Exception {
        List<AppDisplay> appDisplayList = new ArrayList<>();
        // Throw error if the Entity does not exists.
        Entity entity = entityDAO.findById(entityId);
        if (entity == null) {
            throw new ObjectRetrievalFailureException(Entity.class, entityId);
        }
        //Obtain the entityAppProfileList by Entity Id.
        List<EntityAppProfile> entityAppProfileList = appProfileDAO.getEntityAppProfileListByTerminal(entityId);
        
        for (EntityAppProfile entityAppProfile : entityAppProfileList) {
            //Obtain the AppProfile Object by AppProfileId of entityAppProfile.
            AppProfile appProfile = appProfileDAO.getAppProfileByID(entityAppProfile.getAppProfileId());
            //Obtain the App Object by AppId of AppProfile.
            App app = appDAO.getAppByID(appProfile.getAppId());
            //Create a new AppDisplay Object.
            AppDisplay appDisplay = new AppDisplay();
            appDisplay.setId(app.getId());
            appDisplay.setName(app.getName());
            appDisplay.setVersion(app.getVersion());
            
            //Load the collection of AppDisplay Object with a defaults params and files.
            appDisplay = loadAppDisplayDefaultCollections(appDisplay, app);
            //Update the collection of AppDisplay Object with a AppProfileParamValues.
            appDisplay = loadAppDisplayAppProfileCollections(entityAppProfile.getAppProfileId(), appDisplay);
            //Update the collection of AppDisplay Object with a EntityAppProfileParams.
            appDisplay = loadAppDisplayEntityCollections(entityAppProfile.getAppProfileId(), entityAppProfile.getEntity().getId(), appDisplay);
            
            appDisplayList.add(appDisplay);
        }
        return appDisplayList;
    }
    
    public AppDisplay loadAppDisplayEntityCollections(Long appProfileId, Long entityId, AppDisplay appDisplay) throws Exception {
        //Update the appParamDisplay object with values of EntityAppProfileParam.
        for (AppParam appParam : getAppParamListByEntity(appProfileId, entityId)) {
            for (AppParamDisplay appParamDisplay : appDisplay.getAppParams()) {
                if(appParamDisplay.getName().equals(appParam.getName())){
                    if(appParamDisplay.getUpdatedAt().before(appParam.getUpdatedAt())){
                        appParamDisplay.setValue(appParam.getDefaultValue());
                        appParamDisplay.setUpdatedAt(appParam.getUpdatedAt());
                        appDisplay = recordLastUpdateAt(appDisplay, appParamDisplay.getUpdatedAt());
                    }
                }
            }
        }
        
        //Update the appFileDisplay object with values of EntityAppProfileParam.
        for (AppParam appFile : getAppFileListByEntity(appProfileId, entityId)) {
            for (AppFileDisplay appFileDisplay : appDisplay.getAppFiles()) {
                if(appFileDisplay.getName().equals(appFile.getName())){
                    if(appFileDisplay.getUpdatedAt().before(appFile.getUpdatedAt())){
                        appFileDisplay.setValue(appFile.getDefaultValue());
                        appFileDisplay.setUpdatedAt(appFile.getUpdatedAt());
                        appDisplay = recordLastUpdateAt(appDisplay, appFileDisplay.getUpdatedAt());
                    }
                }
            }
        }
        return appDisplay;
    }
    
    //Aux service implementation
    public AppDisplay loadAppDisplayAppProfileCollections(Long appProfileId, AppDisplay appDisplay) throws Exception {
        //Update the appParamDisplay object with values of AppProfileParamValue.
        for (AppParam appParam : getAppParamListByAppProfile(appProfileId)) {
            for (AppParamDisplay appParamDisplay : appDisplay.getAppParams()) {
                if(appParamDisplay.getName().equals(appParam.getName())){
                    if(appParamDisplay.getUpdatedAt().before(appParam.getUpdatedAt())){
                        appParamDisplay.setValue(appParam.getDefaultValue());
                        appParamDisplay.setUpdatedAt(appParam.getUpdatedAt());
                        appDisplay = recordLastUpdateAt(appDisplay, appParamDisplay.getUpdatedAt());
                    }
                }
            }
        }
        
        //Update the appFileDisplay object with values of AppProfileParamValue.
        for (AppParam appFile : getAppFileListByAppProfile(appProfileId)) {
            for (AppFileDisplay appFileDisplay : appDisplay.getAppFiles()) {
                if(appFileDisplay.getName().equals(appFile.getName())){
                    if(appFileDisplay.getUpdatedAt().before(appFile.getUpdatedAt())){
                        appFileDisplay.setValue(appFile.getDefaultValue());
                        appFileDisplay.setUpdatedAt(appFile.getUpdatedAt());
                        appDisplay = recordLastUpdateAt(appDisplay, appFileDisplay.getUpdatedAt());
                    }
                }
            }
        }
        return appDisplay;
    }
    
    //Aux service implementation
    public AppDisplay recordLastUpdateAt(AppDisplay appDisplay, Date date){
        if (appDisplay.getUpdatedAt()!= null) {
            if(appDisplay.getUpdatedAt().before(date))
                appDisplay.setUpdatedAt(date);
        } else 
            appDisplay.setUpdatedAt(date);
        
        return appDisplay;
    }
    
    //Aux service implementation
    public AppDisplay loadAppDisplayDefaultCollections(AppDisplay appDisplay, App app) throws Exception {
        
        for (AppParam appParam : app.getAppParamCollection()) {
            if (!appParam.getAppParamFormat().getId().equals(TYPE_FILE)) {
                //set the values on appParamDisplay with of appParam
                AppParamDisplay appParamDisplay = new AppParamDisplay();
                appParamDisplay.setId(appParam.getId());
                appParamDisplay.setName(appParam.getName());
                appParamDisplay.setValue(appParam.getDefaultValue());
                appParamDisplay.setUpdatedAt(appParam.getUpdatedAt());
                appDisplay = recordLastUpdateAt(appDisplay, appParamDisplay.getUpdatedAt());
                //Asign to Collection of AppDisplay the AppParamDisplay Object.
                appDisplay.getAppParams().add(appParamDisplay);
            } else {
                //set the values on appFileDisplay with of appFile
                AppFileDisplay appFileDisplay = new AppFileDisplay();
                appFileDisplay.setId(appParam.getId());
                appFileDisplay.setName(appParam.getName());
                appFileDisplay.setValue(appParam.getDefaultValue());
                appFileDisplay.setUpdatedAt(appParam.getUpdatedAt());
                appDisplay = recordLastUpdateAt(appDisplay, appFileDisplay.getUpdatedAt());
                //Asign to Collection of AppDisplay the AppFileDisplay Object.
                appDisplay.getAppFiles().add(appFileDisplay);
            }
        }
        return appDisplay;
    }
    
    
    /**
     *
     * @param appId
     * @param entityId
     * @return
     * @throws Exception
     */
    @Override
    public List<AppProfile> getAppProfileListWithoutEntity(Long appId, Long entityId) throws Exception {
        List<AppProfile> appProfileList = new ArrayList<>();
        // get the list of appProfile associate to entity.
        List<AppProfile> existentAppProfileList = getAppProfileListByEntity(appId, entityId);
        App app = appDAO.getAppByID(appId);
        
        for (AppProfile appProfile : app.getAppProfileCollection()) {
            if(!existentAppProfileList.contains(appProfile))
                appProfileList.add(appProfile);
        }
        return appProfileList;
    }
    
    /**
     *
     * @param appProfileId
     * @param entityId 
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public EntityAppProfile addEntityAppProfile(Long appProfileId, Long entityId) throws Exception {
        // Throw error if the AppProfile does not exists.
        AppProfile appProfile = appProfileDAO.getAppProfileByID(appProfileId);
        if (appProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, appProfileId);
        }
        
        // Throw error if the Entity does not exists
        Entity entity = entityDAO.findById(entityId);
        if (entity == null) {
            throw new ObjectRetrievalFailureException(Entity.class, entityId);
        }
        
        EntityAppProfile entityAppProfile = entityAppProfileDAO.findByAppProfileAndEntity(appProfile.getId(), entityId);
        if (entityAppProfile != null) {
            throw new Exception("Already exists");
        }
        
        entityAppProfile = new EntityAppProfile();
        entityAppProfile.setAppProfileId(appProfile.getId());
        entityAppProfile.setEntity(entity);        
        entityAppProfileDAO.persist(entityAppProfile);
        
        return entityAppProfile;
    }
    
    /**
     *
     * @param appProfileId
     * @param entityAppProfile 
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public EntityAppProfile updateEntityAppProfile(Long appProfileId, EntityAppProfile entityAppProfile) throws Exception {
        
        // Throw error if the AppProfile does not exists.
        AppProfile appProfile = appProfileDAO.getAppProfileByID(appProfileId);
        if (appProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, appProfileId);
        }
        
        // If the entityAppProfile is already set up for this AppProfile  --> UPDATE
        for (EntityAppProfile existentEntityAppProfile : appProfile.getEntityAppProfileCollection()) {
            if (existentEntityAppProfile.getEntity().getId() == entityAppProfile.getEntity().getId()) {

                appProfile.getEntityAppProfileCollection().remove(existentEntityAppProfile);
                BeanUtils.copyProperties(entityAppProfile, existentEntityAppProfile, "id", "entity", "app_profile");
                appProfile.getEntityAppProfileCollection().add(existentEntityAppProfile);

                appProfileDAO.merge(appProfile);
                return existentEntityAppProfile;
            }
        }
        // Throw error if the AppProfile does not exists.
        throw new ObjectRetrievalFailureException(AppProfile.class, appProfileId);
    }
    
    /**
     *
     * @param appProfileId
     * @param entityId
     * @throws Exception
     */
    @Override
    public void deleteEntityAppProfile(Long appProfileId, Long entityId) throws Exception {

        // Throw error if the AppProfile does not exists.
        AppProfile appProfile = appProfileDAO.getAppProfileByID(appProfileId);
        if (appProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, appProfileId);
        }
        EntityAppProfile entityAppProfile = entityAppProfileDAO.findByAppProfileAndEntity(appProfile.getId(), entityId);
        if (entityAppProfile == null) {
            throw new ObjectRetrievalFailureException(Entity.class, entityId);
        }
        entityAppProfileDAO.delete(entityAppProfile);
    }
    
    /**
     *
     * @param appProfileId
     * @param entityId
     * @return
     * @throws Exception
     */
    @Override
    public List<AppParam> getAppParamListByEntity(Long appProfileId, Long entityId) throws Exception {
        // Throw error if the AppProfile does not exists.
        List<AppParam> appParamList = new ArrayList<>();
        AppProfile appProfile = appProfileDAO.getAppProfileByID(appProfileId);
        if (appProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, appProfileId);
        }
        
        // Throw error if the Entity does not exists.
        Entity entity = entityDAO.findById(entityId);
        if (entity == null) {
            throw new ObjectRetrievalFailureException(Entity.class, entityId);
        }
        
        while (entity != null) {          
            EntityAppProfile entityAppProfile = entityAppProfileDAO.findByAppProfileAndEntity(appProfile.getId(), entity.getId());
            if (entityAppProfile != null) {
                for (EntityAppProfileParam entityAppProfileParam : entityAppProfile.getEntityappprofileparamCollection()) {
                    AppProfileParamValue appProfileParamValue = appProfileParamValueDAO.getAppProfileParamValueByID(entityAppProfileParam.getAppProfileParamValueId());
                    AppParam appParam = appParamDAO.getAppParamByID(appProfileParamValue.getAppParamId());
                    if(!appParam.getAppParamFormat().getId().equals(TYPE_FILE)){
                        //Update the value and date of the param object with the values of EntityAppProfileParam.
                        appParam.setDefaultValue(entityAppProfileParam.getValue());
                        appParam.setUpdatedAt(entityAppProfileParam.getUpdatedAt());
                        appParamList.add(appParam);
                    }
                }
            }
            if(entity.getParentId() != null)
                entity = entityDAO.findById(entity.getParentId());
            else
                entity = null;
        }
        return appParamList;
    }
    
    /**
     *
     * @param appProfileId
     * @param entityId
     * @return
     * @throws Exception
     */
    @Override
    public List<AppParam> getAppParamListWithoutEntity(Long appProfileId, Long entityId) throws Exception{
        List<AppParam> appParamList = new ArrayList<>();
        // get the list of appParam associate to entity.
        List<AppParam> existentAppParamList = getAppParamListByEntity(appProfileId, entityId);
        AppProfile appProfile = appProfileDAO.getAppProfileByID(appProfileId);
        
        
        for (AppProfileParamValue existentAppProfileParamValue : appProfile.getAppProfileParamValueCollection()) {
            AppParam appParam = appParamDAO.getAppParamByID(existentAppProfileParamValue.getAppParamId());
            if(!existentAppParamList.contains(appParam) && !appParam.getAppParamFormat().getId().equals(TYPE_FILE)){
                //Update the value and date of the param object with the values of AppProfileParamValue.
                appParam.setDefaultValue(existentAppProfileParamValue.getDefaultValue());
                appParam.setUpdatedAt(existentAppProfileParamValue.getUpdatedAt());
                appParamList.add(appParam);
            }
        }
        return appParamList;
    }
    
    /**
     *
     * @param appProfileId
     * @param entityId
     * @return
     * @throws Exception
     */
    @Override
    public List<AppParam> getAppFileListByEntity(Long appProfileId, Long entityId) throws Exception {
        List<AppParam> appParamList = new ArrayList<>();
        // Throw error if the AppProfile does not exists.
        AppProfile appProfile = appProfileDAO.getAppProfileByID(appProfileId);
        if (appProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, appProfileId);
        }
        
        // Throw error if the Entity does not exists.
        Entity entity = entityDAO.findById(entityId);
        if (entity == null) {
            throw new ObjectRetrievalFailureException(Entity.class, entityId);
        }
        
        
        while (entity != null) {  
            EntityAppProfile entityAppProfile = entityAppProfileDAO.findByAppProfileAndEntity(appProfile.getId(), entity.getId());
            if (entityAppProfile != null) {
                for (EntityAppProfileParam entityAppProfileParam : entityAppProfile.getEntityappprofileparamCollection()) {
                    AppProfileParamValue appProfileParamValue = appProfileParamValueDAO.getAppProfileParamValueByID(entityAppProfileParam.getAppProfileParamValueId());
                    AppParam appParam = appParamDAO.getAppParamByID(appProfileParamValue.getAppParamId());
                    if(appParam.getAppParamFormat().getId().equals(TYPE_FILE)){
                        //Update the value and date of the file object with the values of EntityAppProfileParam.
                        appParam.setDefaultValue(entityAppProfileParam.getValue());
                        appParam.setUpdatedAt(entityAppProfileParam.getUpdatedAt());
                        appParamList.add(appParam);
                    }
                }
            }
            if(entity.getParentId() != null)
                entity = entityDAO.findById(entity.getParentId());
            else
                entity = null;
        }
        return appParamList;
    }
    
    /**
     *
     * @param appProfileId
     * @param entityId
     * @return
     * @throws Exception
     */
    @Override
    public List<AppParam> getAppFileListWithoutEntity(Long appProfileId, Long entityId) throws Exception{
        List<AppParam> appParamList = new ArrayList<>();
        // get the list of appParam associate to entity.
        List<AppParam> existentAppParamList = getAppFileListByEntity(appProfileId, entityId);
        AppProfile appProfile = appProfileDAO.getAppProfileByID(appProfileId);
        
        
        for (AppProfileParamValue existentAppProfileParamValue : appProfile.getAppProfileParamValueCollection()) {
            AppParam appParam = appParamDAO.getAppParamByID(existentAppProfileParamValue.getAppParamId());
            if(!existentAppParamList.contains(appParam) && appParam.getAppParamFormat().getId().equals(TYPE_FILE)){
                //Update the value and date of the file object with the values of AppProfileParamValue.
                appParam.setDefaultValue(existentAppProfileParamValue.getDefaultValue());
                appParam.setUpdatedAt(existentAppProfileParamValue.getUpdatedAt());
                appParamList.add(appParam);
            }
        }
        return appParamList;
    }
    
    /**
     *
     * @param appProfileId
     * @param entityId 
     * @param appParamId 
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public EntityAppProfileParam addEntityAppProfileParam(Long appProfileId, Long entityId, Long appParamId) throws Exception {
        // Throw error if the AppProfile does not exists.
        AppProfile appProfile = appProfileDAO.getAppProfileByID(appProfileId);
        if (appProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, appProfileId);
        }
        
        // Throw error if the Entity does not exists
        Entity entity = entityDAO.findById(entityId);
        if (entity == null) {
            throw new ObjectRetrievalFailureException(Entity.class, entityId);
        }
        
        EntityAppProfile entityAppProfile = entityAppProfileDAO.findByAppProfileAndEntity(appProfile.getId(), entityId);
        if (entityAppProfile == null) {
            throw new Exception("EntityAppProfile not exists");
        }
        
        AppProfileParamValue appProfileParamValue = appProfileParamValueDAO.findByAppProfileAndAppParam(appProfileId, appParamId);
        if (appProfileParamValue == null) {
            throw new Exception("AppProfileParamValue not exists");
        }
        
        EntityAppProfileParam entityAppProfileParam = entityAppProfileParamDAO.findByEntityAppProfileAndAppProfileParamValue(entityAppProfile.getId(), appProfileParamValue.getId());
        if (entityAppProfileParam != null) {
            throw new Exception("EntityAppProfileParam Already exists");
        }
        
        entityAppProfileParam = new EntityAppProfileParam();
        entityAppProfileParam.setEntityAppProfileId(entityAppProfile.getId());
        entityAppProfileParam.setAppProfileParamValueId(appProfileParamValue.getId());  
        entityAppProfileParam.setValue(appProfileParamValue.getDefaultValue()); 
        entityAppProfileParamDAO.persist(entityAppProfileParam);
        
        return entityAppProfileParam;
    }
    
    /**
     *
     * @param appProfileId 
     * @param entityId 
     * @param entityAppProfileParam 
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public EntityAppProfileParam updateEntityAppProfileParam(Long appProfileId, Long entityId, EntityAppProfileParam entityAppProfileParam) throws Exception {
        
        // Throw error if the AppProfile does not exists.
        AppProfile appProfile = appProfileDAO.getAppProfileByID(appProfileId);
        if (appProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, appProfileId);
        }
        
        // Throw error if the Entity does not exists
        Entity entity = entityDAO.findById(entityId);
        if (entity == null) {
            throw new ObjectRetrievalFailureException(Entity.class, entityId);
        }
        
        EntityAppProfile entityAppProfile = entityAppProfileDAO.findByAppProfileAndEntity(appProfile.getId(), entityId);
        if (entityAppProfile == null) {
            throw new Exception("EntityAppProfile not exists");
        }
        
        // If the entityAppProfileParam is already set up for this EntityAppProfile  --> UPDATE
        for (EntityAppProfileParam existentEntityAppProfileParam : entityAppProfile.getEntityappprofileparamCollection()) {
            if(Objects.equals(existentEntityAppProfileParam.getAppProfileParamValueId(), entityAppProfileParam.getAppProfileParamValueId())){
                entityAppProfile.getEntityappprofileparamCollection().remove(existentEntityAppProfileParam);
                BeanUtils.copyProperties(entityAppProfileParam, existentEntityAppProfileParam, "id", "entity_app_profile", "app_profile_param_value");
                entityAppProfile.getEntityappprofileparamCollection().add(existentEntityAppProfileParam);

                entityAppProfileDAO.merge(entityAppProfile);
                return existentEntityAppProfileParam;   
            }
        }
            
        // Throw error if the AppProfile does not exists.
        throw new ObjectRetrievalFailureException(AppProfile.class, appProfileId);
    }
    
    /**
     *
     * @param appProfileId
     * @param entityId
     * @param appParamId
     * @throws Exception
     */
    @Override
    public void deleteEntityAppProfileParam(Long appProfileId, Long entityId, Long appParamId) throws Exception {

        // Throw error if the AppProfile does not exists.
        AppProfile appProfile = appProfileDAO.getAppProfileByID(appProfileId);
        if (appProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, appProfileId);
        }
        EntityAppProfile entityAppProfile = entityAppProfileDAO.findByAppProfileAndEntity(appProfile.getId(), entityId);
        if (entityAppProfile == null) {
            throw new ObjectRetrievalFailureException(Entity.class, entityId);
        }
        
        AppProfileParamValue appProfileParamValue = appProfileParamValueDAO.findByAppProfileAndAppParam(appProfileId, appParamId);
        if (appProfileParamValue == null) {
            throw new Exception("AppProfileParamValue not exists");
        }
        
        EntityAppProfileParam entityAppProfileParam = entityAppProfileParamDAO.findByEntityAppProfileAndAppProfileParamValue(entityAppProfile.getId(), appProfileParamValue.getId());
        if (entityAppProfileParam == null) {
            throw new Exception("EntityAppProfileParam not exists");
        }
        
        entityAppProfileParamDAO.delete(entityAppProfileParam);
    }
    
    /**
     *
     * @param appProfileId
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return 
     * @throws Exception
     */
    @Override
    public List<AppParam> searchAppParamByProfile(Long appProfileId, String filter, int pageNumber, int rowsPerPage) throws Exception {
        List<AppParam> appParamList = new ArrayList<>();
        int firstResult = (pageNumber - 1) * rowsPerPage;
        // Throw error if the AppProfile does not exists.
        AppProfile appProfile = appProfileDAO.getAppProfileByID(appProfileId);
        if (appProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, appProfileId);
        }
        
        List<AppParam> defaultParamList = appDAO.searchAppParam(appProfile.getAppId(), filter, firstResult, rowsPerPage);
        List<AppParam> profileParamList = appProfileDAO.getAppParamList(appProfileId);
        for (AppParam appParam : defaultParamList) {
            if(profileParamList.contains(appParam))
                appParamList.add(appParam);
        }
        return appParamList;
    }
    
    /**
     *
     * @param appProfileId
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return 
     * @throws Exception
     */
    @Override
    public List<AppParam> searchAppFileByProfile(Long appProfileId, String filter, int pageNumber, int rowsPerPage) throws Exception {
        List<AppParam> appFileList = new ArrayList<>();
        int firstResult = (pageNumber - 1) * rowsPerPage;
        // Throw error if the AppProfile does not exists.
        AppProfile appProfile = appProfileDAO.getAppProfileByID(appProfileId);
        if (appProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, appProfileId);
        }
        
        
        List<AppParam> defaultParamList = appDAO.searchAppFile(appProfile.getAppId(), filter, firstResult, rowsPerPage);
        List<AppParam> profileFileList = appProfileDAO.getAppFileList(appProfileId);
        for (AppParam appParam : defaultParamList) {
            if(profileFileList.contains(appParam))
                appFileList.add(appParam);
        }
        return appFileList;
    }
}
