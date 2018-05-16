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

import com.luretechnologies.server.data.dao.AppProfileFileValueDAO;
import com.luretechnologies.server.data.model.tms.AppProfileFileValue;
import com.luretechnologies.server.service.AppProfileFileValueService;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    AppProfileFileValueDAO appProfileFileDAO;
    
     /**
     *
     * @param appProfileFileValue
     * @return
     * @throws Exception
     */
    @Override
    public AppProfileFileValue createAppProfileFile(AppProfileFileValue appProfileFileValue) throws Exception{
        AppProfileFileValue newAppProfileFileValue = new AppProfileFileValue();
        
         // Copy properties from -> to
        BeanUtils.copyProperties(appProfileFileValue , newAppProfileFileValue);
        appProfileFileDAO.persist(newAppProfileFileValue);
        return newAppProfileFileValue;
        
    }
    
    /**
     *
     * @param ID
     * @return
     * @throws Exception
     */
    @Override
    public AppProfileFileValue updateAppProfileFile(long ID) throws Exception{
        AppProfileFileValue appProfileFileValue = appProfileFileDAO.getAppProfileFileByID(ID);
        AppProfileFileValue updatedAppProfileFile=  updateAppProfileFile(appProfileFileValue);
        return updatedAppProfileFile;
        
    }
    
    /**
     *
     * @param ID
     * @return
     * @throws Exception
     */
    @Override
    public void deleteAppProfileFile(long ID) throws Exception{
        AppProfileFileValue deletedAppProfileFile = appProfileFileDAO.getAppProfileFileByID(ID);
        appProfileFileDAO.delete(deletedAppProfileFile);
        
    }
    
    
    /**
     *
     * @param value
     * @return
     * @throws Exception
     */
    @Override
    public Integer doForceUpdate(Integer value) throws Exception{
        Integer forceUpdateValue = appProfileFileDAO.doForceUpdate(value);
        return forceUpdateValue;
        
    }
    
    /**
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public List<AppProfileFileValue> getAppProfileFileList(List<Long> ids) throws Exception{
        List<AppProfileFileValue> appFileList = appProfileFileDAO.getAppProfileFileList(ids);
        return appFileList;
        
    }
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public AppProfileFileValue getAppProfileFileByID(Long id) throws Exception{
        AppProfileFileValue appFile = appProfileFileDAO.getAppProfileFileByID(id);
        return appFile;
        
    }
    
    private AppProfileFileValue updateAppProfileFile(AppProfileFileValue appProfileFile)throws Exception{
        appProfileFile.setDefaultValue("Updated");
        appProfileFileDAO.merge(appProfileFile);
        return appProfileFile;
    }
}
