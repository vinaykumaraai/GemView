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

import com.luretechnologies.server.data.dao.ApplianceAppFileDAO;
import com.luretechnologies.server.data.model.tms.ApplianceAppFileValue;
import com.luretechnologies.server.service.ApplianceAppFileService;
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
public class ApplianceAppFileServiceImpl implements ApplianceAppFileService{
    
     @Autowired
    ApplianceAppFileDAO applianceAppFileDAO;
    
    /**
     *
     * @param applianceAppFile
     * @return
     * @throws Exception
     */
    @Override
    public ApplianceAppFileValue createApplianceAppFile(ApplianceAppFileValue applianceAppFile) throws Exception{
        ApplianceAppFileValue newApplianceAppFile = new ApplianceAppFileValue();
        
         // Copy properties from -> to
        BeanUtils.copyProperties(applianceAppFile , newApplianceAppFile);
        applianceAppFileDAO.persist(newApplianceAppFile);
        return newApplianceAppFile;
        
    }
    
    /**
     *
     * @param ID
     * @return
     * @throws Exception
     */
    @Override
    public ApplianceAppFileValue updateApplianceAppFile(long ID) throws Exception{
        ApplianceAppFileValue applianceAppFile = applianceAppFileDAO.getApplianceAppFileByID(ID);
        ApplianceAppFileValue updatedApplianceAppFile =  updateApplianceAppFileValue(applianceAppFile);
        return updatedApplianceAppFile;
        
    }
    
    /**
     *
     * @param ID
     * @throws Exception
     */
    @Override
    public void deleteApplianceAppFile(long ID) throws Exception{
        ApplianceAppFileValue deletedApplianceAppFile = applianceAppFileDAO.getApplianceAppFileByID(ID);
        applianceAppFileDAO.delete(deletedApplianceAppFile);
        
    }
    
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public String getValueByID(Long id) throws Exception{
        String value = applianceAppFileDAO.getValueByID(id);
        return value;
        
    }
    
    /**
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public List<ApplianceAppFileValue> getApplianceAppFileList(List<Long> ids) throws Exception{
        List<ApplianceAppFileValue> applianceAppFileList = applianceAppFileDAO.getApplianceAppFileList(ids);
        return applianceAppFileList;
        
    }
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ApplianceAppFileValue getApplianceAppFileByID(Long id) throws Exception{
        ApplianceAppFileValue applianceAppFile = applianceAppFileDAO.getApplianceAppFileByID(id);
        return applianceAppFile;
        
    }
    
    private ApplianceAppFileValue updateApplianceAppFileValue(ApplianceAppFileValue applianceAppFile)throws Exception{
        applianceAppFile.setValue("Updated");
        applianceAppFileDAO.merge(applianceAppFile);
        return applianceAppFile;
    }
}
