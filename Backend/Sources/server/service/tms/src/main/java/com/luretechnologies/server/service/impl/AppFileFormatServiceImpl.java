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

import com.luretechnologies.server.data.dao.AppFileFormatDAO;
import com.luretechnologies.server.data.model.tms.AppFileFormat;
import com.luretechnologies.server.service.AppFileFormatService;
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
public class AppFileFormatServiceImpl implements AppFileFormatService{
    
    @Autowired
    AppFileFormatDAO appFileFormatDAO;
    
     /**
     *
     * @param appFileFormat
     * @return
     * @throws Exception
     */
    @Override
    public AppFileFormat createAppFileFormat(AppFileFormat appFileFormat) throws Exception{
        AppFileFormat newAppFileFormat = new AppFileFormat();
        
         // Copy properties from -> to
        BeanUtils.copyProperties(appFileFormat , newAppFileFormat);
        appFileFormatDAO.persist(newAppFileFormat);
        return newAppFileFormat;
        
    }
    
    /**
     *
     * @param ID
     * @return
     * @throws Exception
     */
    @Override
    public AppFileFormat updateAppFileFormat(long ID) throws Exception{
        AppFileFormat appFileFormat = appFileFormatDAO.getAppFileFormatByID(ID);
        AppFileFormat updatedAppFileFormat=  updateAppFileFormat(appFileFormat);
        return updatedAppFileFormat;
        
    }
    
    /**
     *
     * @param ID
     * @return
     * @throws Exception
     */
    @Override
    public void deleteAppFileFormat(long ID) throws Exception{
        AppFileFormat deletedAppFileFormat = appFileFormatDAO.getAppFileFormatByID(ID);
        appFileFormatDAO.delete(deletedAppFileFormat);
        
    }
    
    /**
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public List<AppFileFormat> getAppFileFormatList(List<Long> ids) throws Exception{
        List<AppFileFormat> appFileFormatList = appFileFormatDAO.getAppFileFormatList(ids);
        return appFileFormatList;
        
    }
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public AppFileFormat getAppFileFormatByID(Long id) throws Exception{
        AppFileFormat appProfile = appFileFormatDAO.getAppFileFormatByID(id);
        return appProfile;
        
    }
    
    private AppFileFormat updateAppFileFormat(AppFileFormat appFileFormat)throws Exception{
        appFileFormat.setName("Updated");
        appFileFormatDAO.merge(appFileFormat);
        return appFileFormat;
    }
}
