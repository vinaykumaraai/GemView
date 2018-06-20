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
import com.luretechnologies.server.data.dao.EntityLevelDAO;
import com.luretechnologies.server.data.model.tms.Action;
import com.luretechnologies.server.data.model.tms.App;
import com.luretechnologies.server.data.model.tms.AppParam;
import com.luretechnologies.server.data.model.tms.AppParamFormat;
import com.luretechnologies.server.data.model.tms.EntityLevel;
import com.luretechnologies.server.service.AppParamService;
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
public class AppParamServiceImpl implements AppParamService{
    
    @Autowired
    AppParamDAO appParamDAO;
    
    @Autowired
    AppDAO appDAO;
    
    @Autowired
    ActionDAO actionDAO;
    
    @Autowired
    EntityLevelDAO entityLevelDAO;
    
    @Autowired
    AppParamFormatDAO appParamFormatDAO;
    
    /**
     *
     * @param appParam
     * @return
     * @throws Exception
     */
    @Override
    public AppParam createAppParam(AppParam appParam) throws Exception{
        AppParam newAppParam = new AppParam();
        
        EntityLevel entityLevel = appParam.getEntityLevel();
        Action action = appParam.getAction();
        AppParamFormat appParamFormat = appParam.getAppParamFormat();

        // Check app existence
        App existentApp = appDAO.getAppByID(appParam.getAppId());

        if (existentApp == null) {
            throw new ObjectRetrievalFailureException(App.class, appParam.getAppId());
        }
        appParam.setAppId(existentApp.getId());
        
        // Check entityLevel existence
        if (entityLevel != null) {
            EntityLevel existentEntityLevel = entityLevelDAO.getEntityLevelByID(appParam.getEntityLevel().getId());

            if (existentEntityLevel == null) {
                throw new ObjectRetrievalFailureException(EntityLevel.class, appParam.getEntityLevel().getId());
            }
            appParam.setEntityLevel(existentEntityLevel);
        } else {
            // If not EntityLevel defined throw Exception
            throw new Exception("The AppParam need to associated with some valid EntityLevel.");
        }
        
        // Check action existence
        if (action != null) {
            Action existentAction = actionDAO.getActionByID(appParam.getAction().getId());

            if (existentAction == null) {
                throw new ObjectRetrievalFailureException(Action.class, appParam.getAction().getId());
            }
            appParam.setAction(existentAction);
        } else {
            // If not Action defined throw Exception
            throw new Exception("The AppParam need to associated with some valid Action.");
        }
        
        // Check appParamFormat existence
        if (appParamFormat != null) {
            AppParamFormat existentAppParamFormat = appParamFormatDAO.getAppParamFormatByID(appParam.getAppParamFormat().getId());

            if (existentAppParamFormat == null) {
                throw new ObjectRetrievalFailureException(AppParamFormat.class, appParam.getAppParamFormat().getId());
            }
            appParam.setAppParamFormat(existentAppParamFormat);
        } else {
            // If not AppParamFormat defined throw Exception
            throw new Exception("The AppParam need to associated with some valid AppParamFormat.");
        }

         // Copy properties from -> to
        BeanUtils.copyProperties(appParam , newAppParam);
        appParamDAO.persist(appParam);
        return appParam;
        
    }
    
    /**
     *
     * @param ID
     * @param appParam
     * @return
     * @throws Exception
     */
    @Override
    public AppParam updateAppParam(long ID, AppParam appParam) throws Exception{
        AppParam existentAppParam = appParamDAO.getAppParamByID(ID);
        
        EntityLevel entityLevel = appParam.getEntityLevel();
        Action action = appParam.getAction();
        AppParamFormat appParamFormat = appParam.getAppParamFormat();

        // Check app existence
        App existentApp = appDAO.getAppByID(appParam.getAppId());

        if (existentApp == null) {
            throw new ObjectRetrievalFailureException(App.class, appParam.getAppId());
        }
        appParam.setAppId(existentApp.getId());
        
        // Check entityLevel existence
        if (entityLevel != null) {
            EntityLevel existentEntityLevel = entityLevelDAO.getEntityLevelByID(appParam.getEntityLevel().getId());

            if (existentEntityLevel == null) {
                throw new ObjectRetrievalFailureException(EntityLevel.class, appParam.getEntityLevel().getId());
            }
            appParam.setEntityLevel(existentEntityLevel);
        } else {
            // If not EntityLevel defined throw Exception
            throw new Exception("The AppParam need to associated with some valid EntityLevel.");
        }
        
        // Check action existence
        if (action != null) {
            Action existentAction = actionDAO.getActionByID(appParam.getAction().getId());

            if (existentAction == null) {
                throw new ObjectRetrievalFailureException(Action.class, appParam.getAction().getId());
            }
            appParam.setAction(existentAction);
        } else {
            // If not Action defined throw Exception
            throw new Exception("The AppParam need to associated with some valid Action.");
        }
        
        // Check appParamFormat existence
        if (appParamFormat != null) {
            AppParamFormat existentAppParamFormat = appParamFormatDAO.getAppParamFormatByID(appParam.getAppParamFormat().getId());

            if (existentAppParamFormat == null) {
                throw new ObjectRetrievalFailureException(AppParamFormat.class, appParam.getAppParamFormat().getId());
            }
            appParam.setAppParamFormat(existentAppParamFormat);
        } else {
            // If not AppParamFormat defined throw Exception
            throw new Exception("The AppParam need to associated with some valid AppParamFormat.");
        }
        //AppParam updatedAppParam=  updateAppParam(appParam);
        
        // Copy properties from -> to
        BeanUtils.copyProperties(appParam , existentAppParam);
        
        return appParamDAO.merge(existentAppParam);
    }
    
    /**
     *
     * @param ID
     * @throws Exception
     */
    @Override
    public void deleteAppParam(long ID) throws Exception{
        AppParam deletedAppFile = appParamDAO.getAppParamByID(ID);
        appParamDAO.delete(deletedAppFile);
        
    }
    
    
    /**
     *
     * @param value
     * @return
     * @throws Exception
     */
    @Override
    public Integer doForceUpdate(Integer value) throws Exception{
        Integer forceUpdateValue = appParamDAO.doForceUpdate(value);
        return forceUpdateValue;
        
    }
    
    /**
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public List<AppParam> getAppParamList(int firstResult, int lastResult) throws Exception{
        List<AppParam> appParamList = appParamDAO.getAppParamList(firstResult, lastResult);
        return appParamList;
        
    }
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public AppParam getAppParamByID(Long id) throws Exception{
        AppParam appParam = appParamDAO.getAppParamByID(id);
        return appParam;
        
    }
    
    private AppParam updateAppParam(AppParam appParam)throws Exception{
        appParam.setName("Updated");
        appParamDAO.merge(appParam);
        return appParam;
    }

    /**
     *
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<AppParam> search(String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        if (filter == null) {
            return appParamDAO.list(firstResult, rowsPerPage);
        } else {
            return appParamDAO.search(filter, firstResult, rowsPerPage);
        }
    }
}
