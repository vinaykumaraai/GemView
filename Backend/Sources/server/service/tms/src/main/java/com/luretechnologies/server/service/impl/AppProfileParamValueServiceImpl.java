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

import com.luretechnologies.server.data.dao.AppParamDAO;
import com.luretechnologies.server.data.dao.AppProfileDAO;
import com.luretechnologies.server.data.dao.AppProfileParamValueDAO;
import com.luretechnologies.server.data.model.tms.AppParam;
import com.luretechnologies.server.data.model.tms.AppProfile;
import com.luretechnologies.server.data.model.tms.AppProfileParamValue;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.luretechnologies.server.service.AppProfileParamValueService;

/**
 *
 * @author Vinay
 */
@Service
@Transactional
public class AppProfileParamValueServiceImpl implements AppProfileParamValueService{
    
    @Autowired
    private AppProfileParamValueDAO appProfileParamValueDAO;
    
    @Autowired
    private AppParamDAO appParamDAO;
    
    @Autowired
    private AppProfileDAO appProfileDAO;
    
    /**
     *
     * @param appProfileParamValue
     * @return
     * @throws Exception
     */
    @Override
    public AppProfileParamValue createAppProfileParam(AppProfileParamValue appProfileParamValue) throws Exception{
        AppProfileParamValue newAppProfileParamValue = new AppProfileParamValue();
        
        // Check appParam existence
        AppParam existentAppParam = appParamDAO.getAppParamByID(appProfileParamValue.getAppParamId());

        if (existentAppParam == null) {
            throw new ObjectRetrievalFailureException(AppParam.class, appProfileParamValue.getAppParamId());
        }
        appProfileParamValue.setAppParamId(existentAppParam.getId());
        
        // Check appProfile existence
        AppProfile existentAppProfile = appProfileDAO.getAppProfileByID(appProfileParamValue.getAppProfileId());

        if (existentAppProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, appProfileParamValue.getAppProfileId());
        }
        appProfileParamValue.setAppProfileId(existentAppProfile.getId());
        
         // Copy properties from -> to
        BeanUtils.copyProperties(appProfileParamValue , newAppProfileParamValue);
        appProfileParamValueDAO.persist(appProfileParamValue);
        return appProfileParamValue;
    }
    
    /**
     *
     * @param ID
     * @return
     * @throws Exception
     */
    @Override
    public AppProfileParamValue updateAppProfileParam(long ID, AppProfileParamValue appProfileParamValue) throws Exception{
        AppProfileParamValue existentAppProfileParamValue = appProfileParamValueDAO.getAppProfileParamValueByID(ID);
        
        // Check appParam existence
        AppParam existentAppParam = appParamDAO.getAppParamByID(appProfileParamValue.getAppParamId());

        if (existentAppParam == null) {
            throw new ObjectRetrievalFailureException(AppParam.class, appProfileParamValue.getAppParamId());
        }
        appProfileParamValue.setAppParamId(existentAppParam.getId());
        
        // Check appProfile existence
        AppProfile existentAppProfile = appProfileDAO.getAppProfileByID(appProfileParamValue.getAppProfileId());

        if (existentAppProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, appProfileParamValue.getAppProfileId());
        }
        appProfileParamValue.setAppProfileId(existentAppProfile.getId());
        
        //AppProfileParamValue updatedAppProfileParamValue=  updateAppProfileParamValue(appProfileParamValue);
         // Copy properties from -> to
        BeanUtils.copyProperties(appProfileParamValue , existentAppProfileParamValue);
        return appProfileParamValueDAO.merge(existentAppProfileParamValue);
        
    }
    
    /**
     *
     * @param ID
     * @throws Exception
     */
    @Override
    public void deleteAppProfileParamvalue(long ID) throws Exception{
        AppProfileParamValue deletedAppProfileParamValue = appProfileParamValueDAO.getAppProfileParamValueByID(ID);
        appProfileParamValueDAO.delete(deletedAppProfileParamValue);
        
    }
    
    
    /**
     *
     * @param value
     * @return
     * @throws Exception
     */
    @Override
    public Integer doForceUpdate(Integer value) throws Exception{
        Integer forceUpdateValue = appProfileParamValueDAO.doForceUpdate(value);
        return forceUpdateValue;
        
    }
    
    /**
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public List<AppProfileParamValue> getAppProfileParamList(List<Long> ids) throws Exception{
        List<AppProfileParamValue> appProfileParamValueList = appProfileParamValueDAO.getAppProfileParamValueList(ids);
        return appProfileParamValueList;
        
    }
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public AppProfileParamValue getAppProfileParamByID(Long id) throws Exception{
        AppProfileParamValue appProfileParam = appProfileParamValueDAO.getAppProfileParamValueByID(id);
        return appProfileParam;
        
    }
    
    private AppProfileParamValue updateAppProfileParamValue(AppProfileParamValue appProfileParamValue)throws Exception{
        appProfileParamValue.setDefaultValue("Updated");
        appProfileParamValueDAO.merge(appProfileParamValue);
        return appProfileParamValue;
    }
}
