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

import com.luretechnologies.server.data.dao.ApplianceAppParamDAO;
import com.luretechnologies.server.data.model.tms.ApplianceAppParamValue;
import com.luretechnologies.server.service.ApplianceAppParamService;
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
public class ApplianceAppParamServiceImpl implements ApplianceAppParamService{
    
     @Autowired
    ApplianceAppParamDAO applianceAppParamtDAO;
    
    /**
     *
     * @param applianceAppParam
     * @return
     * @throws Exception
     */
    @Override
    public ApplianceAppParamValue createApplianceAppParam(ApplianceAppParamValue applianceAppParam) throws Exception{
        ApplianceAppParamValue newapplianceAppParam = new ApplianceAppParamValue();
        
         // Copy properties from -> to
        BeanUtils.copyProperties(applianceAppParam , newapplianceAppParam);
        applianceAppParamtDAO.persist(newapplianceAppParam);
        return newapplianceAppParam;
        
    }
    
    /**
     *
     * @param ID
     * @return
     * @throws Exception
     */
    @Override
    public ApplianceAppParamValue updateApplianceAppParam(long ID) throws Exception{
        ApplianceAppParamValue applianceAppParam = applianceAppParamtDAO.getApplianceAppParamByID(ID);
        ApplianceAppParamValue updatedApplianceAppParam =  updateApplianceAppParamValue(applianceAppParam);
        return updatedApplianceAppParam;
        
    }
    
    /**
     *
     * @param ID
     * @throws Exception
     */
    @Override
    public void deleteApplianceAppParam(long ID) throws Exception{
        ApplianceAppParamValue deletedApplianceAppParam = applianceAppParamtDAO.getApplianceAppParamByID(ID);
        applianceAppParamtDAO.delete(deletedApplianceAppParam);
        
    }
    
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public String getValueByID(Long id) throws Exception{
        String value = applianceAppParamtDAO.getValueByID(id);
        return value;
        
    }
    
    /**
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public List<ApplianceAppParamValue> getApplianceAppParamList(List<Long> ids) throws Exception{
        List<ApplianceAppParamValue> applianceAppParamList = applianceAppParamtDAO.getApplianceAppParamLsit(ids);
        return applianceAppParamList;
        
    }
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ApplianceAppParamValue getApplianceAppParamByID(Long id) throws Exception{
        ApplianceAppParamValue applianceAppParam = applianceAppParamtDAO.getApplianceAppParamByID(id);
        return applianceAppParam;
        
    }
    
    private ApplianceAppParamValue updateApplianceAppParamValue(ApplianceAppParamValue applianceAppParam)throws Exception{
        applianceAppParam.setValue("Updated");
        applianceAppParamtDAO.merge(applianceAppParam);
        return applianceAppParam;
    }
}
