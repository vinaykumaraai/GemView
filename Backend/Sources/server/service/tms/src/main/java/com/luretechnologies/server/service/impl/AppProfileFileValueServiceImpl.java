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

import com.luretechnologies.server.data.dao.AppFileDAO;
import com.luretechnologies.server.data.dao.AppProfileDAO;
import com.luretechnologies.server.data.dao.AppProfileFileValueDAO;
import com.luretechnologies.server.data.model.tms.AppFile;
import com.luretechnologies.server.data.model.tms.AppProfile;
import com.luretechnologies.server.data.model.tms.AppProfileFileValue;
import com.luretechnologies.server.service.AppProfileFileValueService;
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
public class AppProfileFileValueServiceImpl implements AppProfileFileValueService{
    
    @Autowired
    AppProfileFileValueDAO appProfileFileValueDAO;
    
    @Autowired
    AppProfileDAO appProfileDAO;
    
    @Autowired
    AppFileDAO appFileDAO;
    
     /**
     *
     * @param appProfileFileValue
     * @return
     * @throws Exception
     */
    @Override
    public AppProfileFileValue createAppProfileFileValue(AppProfileFileValue appProfileFileValue) throws Exception{
        AppProfileFileValue newAppProfileFileValue = new AppProfileFileValue();
        
        // Check appFile existence
        AppFile existentAppFile = appFileDAO.getAppFileByID(appProfileFileValue.getAppFileId());

        if (existentAppFile == null) {
            throw new ObjectRetrievalFailureException(AppFile.class, appProfileFileValue.getAppFileId());
        }
        appProfileFileValue.setAppFileId(existentAppFile.getId());
        
        // Check appProfile existence        
        
        AppProfile existentAppProfile = appProfileDAO.getAppProfileByID(appProfileFileValue.getAppProfileId());

        if (existentAppProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, appProfileFileValue.getAppProfileId());
        }
        appProfileFileValue.setAppProfileId(existentAppProfile.getId());
        
         // Copy properties from -> to
        BeanUtils.copyProperties(appProfileFileValue , newAppProfileFileValue);
        appProfileFileValueDAO.persist(newAppProfileFileValue);
        return newAppProfileFileValue;
        
    }
    
    /**
     *
     * @param ID
     * @param appProfileFileValue
     * @return
     * @throws Exception
     */
    @Override
    public AppProfileFileValue updateAppProfileFileValue(long ID, AppProfileFileValue appProfileFileValue) throws Exception{
        AppProfileFileValue existentAppProfileFileValue = appProfileFileValueDAO.getAppProfileFileByID(ID);
        
        // Check appFile existence
        AppFile existentAppFile = appFileDAO.getAppFileByID(appProfileFileValue.getAppFileId());

        if (existentAppFile == null) {
            throw new ObjectRetrievalFailureException(AppFile.class, appProfileFileValue.getAppFileId());
        }
        appProfileFileValue.setAppFileId(existentAppFile.getId());
        
        // Check appProfile existence        
        
        AppProfile existentAppProfile = appProfileDAO.getAppProfileByID(appProfileFileValue.getAppProfileId());

        if (existentAppProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, appProfileFileValue.getAppProfileId());
        }
        appProfileFileValue.setAppProfileId(existentAppProfile.getId());
        
        // Copy properties from -> to
        BeanUtils.copyProperties(appProfileFileValue, existentAppProfileFileValue);
        
        //AppProfileFileValue updatedAppProfileFileValue=  updateAppProfileFileValue(appProfileFileValue);
        return appProfileFileValueDAO.merge(existentAppProfileFileValue);
        
    }
    
    /**
     *
     * @param ID
     * @throws Exception
     */
    @Override
    public void deleteAppProfileFileValue(long ID) throws Exception{
        AppProfileFileValue deletedAppProfileFileValue = appProfileFileValueDAO.getAppProfileFileByID(ID);
        appProfileFileValueDAO.delete(deletedAppProfileFileValue);
        
    }
    
    
    /**
     *
     * @param value
     * @return
     * @throws Exception
     */
    @Override
    public Integer doForceUpdate(Integer value) throws Exception{
        Integer forceUpdateValue = appProfileFileValueDAO.doForceUpdate(value);
        return forceUpdateValue;
        
    }
    
    /**
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public List<AppProfileFileValue> getAppProfileFileValueList(List<Long> ids) throws Exception{
        List<AppProfileFileValue> appFileList = appProfileFileValueDAO.getAppProfileFileList(ids);
        return appFileList;
        
    }
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public AppProfileFileValue getAppProfileFileValueByID(Long id) throws Exception{
        AppProfileFileValue appFile = appProfileFileValueDAO.getAppProfileFileByID(id);
        return appFile;
        
    }
    
    private AppProfileFileValue updateAppProfileFileValue(AppProfileFileValue appProfileFileValue)throws Exception{
        appProfileFileValue.setDefaultValue("Updated");
        appProfileFileValueDAO.merge(appProfileFileValue);
        return appProfileFileValue;
    }
}
