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
import com.luretechnologies.server.data.dao.AppProfileDAO;
import com.luretechnologies.server.data.model.tms.App;
import com.luretechnologies.server.data.model.tms.AppFileFormat;
import com.luretechnologies.server.data.model.tms.AppProfile;
import com.luretechnologies.server.service.AppProfileService;
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
public class AppProfileServiceImpl implements AppProfileService{
    @Autowired(required = true)
    AppProfileDAO appProfileDAO;
    
    @Autowired
    AppDAO appDAO;
    
    /**
     *
     * @param appProfile
     * @return
     * @throws Exception
     */
    @Override
    public AppProfile createAppProfile(AppProfile appProfile) throws Exception{
        //AppProfile newAppProfile = new AppProfile();
        App app = appProfile.getApp();

        // Check app existence
        if (app != null) {
            App existentApp = appDAO.getAppByID(appProfile.getApp().getId());

            if (existentApp == null) {
                throw new ObjectRetrievalFailureException(App.class, appProfile.getApp().getId());
            }
            appProfile.setApp(existentApp);
        } else {
            // If not App defined throw Exception
            throw new Exception("The AppProfile need to associated with some valid App.");
        }
        
        
         // Copy properties from -> to
        //BeanUtils.copyProperties(appProfile , newAppProfile);
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
        App app = appProfile.getApp();

        // Check app existence
        if (app != null) {
            App existentApp = appDAO.getAppByID(appProfile.getApp().getId());

            if (existentApp == null) {
                throw new ObjectRetrievalFailureException(App.class, appProfile.getApp().getId());
            }
            appProfile.setApp(existentApp);
        } else {
            // If not App defined throw Exception
            throw new Exception("The AppProfile need to associated with some valid App.");
        }
        
        //AppProfile updatedAppProfile=  updateAppProfile(appProfile);
        
         // Copy properties from -> to
        BeanUtils.copyProperties(appProfile , existentAppProfile);
        return appProfileDAO.merge(existentAppProfile);
        
    }
    
    /**
     *
     * @param ID
     * @return
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
    
    private AppProfile updateAppProfile(AppProfile appProfile)throws Exception{
        appProfile.setName("Updated");
        appProfileDAO.merge(appProfile);
        return appProfile;
    }
}
