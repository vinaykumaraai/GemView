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

import com.luretechnologies.server.data.dao.AppParamFormatDAO;
import com.luretechnologies.server.data.model.tms.AppParamFormat;
import com.luretechnologies.server.service.AppParamFormatService;
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
public class AppParamFormatServiceImpl implements AppParamFormatService{
    
    @Autowired
    AppParamFormatDAO appParamFormatDAO;
    
    /**
     *
     * @param appParamFormat
     * @return
     * @throws Exception
     */
    @Override
    public AppParamFormat createAppParamFormat(AppParamFormat appParamFormat) throws Exception{
        AppParamFormat newAppParamFormat = new AppParamFormat();
        
         // Copy properties from -> to
        BeanUtils.copyProperties(appParamFormat , newAppParamFormat);
        appParamFormatDAO.persist(newAppParamFormat);
        return newAppParamFormat;
        
    }
    
    /**
     *
     * @param ID
     * @return
     * @throws Exception
     */
    @Override
    public AppParamFormat updateAppParamFormat(long ID, AppParamFormat appParamFormat) throws Exception{
        AppParamFormat existentAppParamFormat = appParamFormatDAO.getAppParamFormatByID(ID);
        //AppParamFormat updatedAppParamFormat =  updateAppParamFormat(appParamFormat);
        
        // Copy properties from -> to
        BeanUtils.copyProperties(appParamFormat, existentAppParamFormat);
        return appParamFormatDAO.merge(existentAppParamFormat);
        
    }
    
    /**
     *
     * @param ID
     * @return
     * @throws Exception
     */
    @Override
    public void deleteAppParamFormat(long ID) throws Exception{
        AppParamFormat deletedAppParamFormat = appParamFormatDAO.getAppParamFormatByID(ID);
        appParamFormatDAO.delete(deletedAppParamFormat);
        
    }
    
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public String getValueByID(Long id) throws Exception{
        String value = appParamFormatDAO.getValueByID(id);
        return value;
        
    }
    
    /**
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public List<AppParamFormat> getAppParamFormatList(List<Long> ids) throws Exception{
        List<AppParamFormat> appParamFormatList = appParamFormatDAO.getAppParamFormatLsit(ids);
        return appParamFormatList;
        
    }
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public AppParamFormat getAppParamFormatByID(Long id) throws Exception{
        AppParamFormat appParamFormat = appParamFormatDAO.getAppParamFormatByID(id);
        return appParamFormat;
        
    }
    
    private AppParamFormat updateAppParamFormat(AppParamFormat appParamFormat)throws Exception{
        appParamFormat.setName("Updated");
        appParamFormatDAO.merge(appParamFormat);
        return appParamFormat;
    }
}
