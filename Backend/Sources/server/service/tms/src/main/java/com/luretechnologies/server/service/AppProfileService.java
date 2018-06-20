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
package com.luretechnologies.server.service;

import com.luretechnologies.server.data.model.tms.AppParam;
import com.luretechnologies.server.data.model.tms.AppProfile;
import com.luretechnologies.server.data.model.tms.AppProfileParamValue;
import com.luretechnologies.server.data.model.tms.EntityAppProfile;
import com.luretechnologies.server.data.model.tms.EntityAppProfileParam;
import java.util.List;

/**
 *
 * @author Vinay
 */
public interface AppProfileService {
    /**
     *
     * @param appProfile
     * @return
     * @throws Exception
     */
    public AppProfile createAppProfile(AppProfile appProfile) throws Exception;

    /**
     *
     * @param id
     * @param appProfile
     * @return
     * @throws Exception
     */
    public AppProfile updateAppProfile(long id, AppProfile appProfile) throws Exception;

   /**
     *
     * @param id
     * @throws Exception
     */
    public void deleteAppProfile(long id) throws Exception;
   
    /**
     *
     * @param ids
     * @return 
     * @throws Exception
     */
    public List<AppProfile> getAppProfileList(List<Long>  ids) throws Exception;
    
    /**
     *
     * @param id
     * @return 
     * @throws Exception
     */
    public AppProfile getAppProfileByID(Long id) throws Exception;
    
    /**
     *
     * @param id
     * @return 
     * @throws Exception
     */
    public List<AppParam> getAppParamListByAppProfile(Long id) throws Exception;
    
    /**
     *
     * @param id
     * @return 
     * @throws Exception
     */
    public List<AppParam> getAppParamListWithoutAppProfile(Long id) throws Exception;
    
    /**
     *
     * @param id
     * @param appParamId
     * @return 
     * @throws Exception
     */
    public AppProfileParamValue addAppProfileParamValue(Long id, Long appParamId) throws Exception;
    
    /**
     *
     * @param id
     * @param appProfileParamValue
     * @return 
     * @throws Exception
     */
    public AppProfileParamValue updateAppProfileParamValue(Long id, AppProfileParamValue appProfileParamValue) throws Exception;
    
    /**
     *
     * @param id 
     * @param appParamId 
     * @throws Exception
     */
    public void deleteAppProfileParamValue(Long id, Long appParamId) throws Exception;
    
    /**
     * 
     * @param appId
     * @param entityId
     * @return 
     * @throws Exception
     */
    public List<AppProfile> getAppProfileListByEntity(Long appId, Long entityId) throws Exception;
    
    /**
     * 
     * @param appId
     * @param entityId
     * @return 
     * @throws Exception
     */
    public List<AppProfile> getAppProfileListWithoutEntity(Long appId, Long entityId) throws Exception;
    
    /**
     * 
     * @param appProfileId
     * @param entityId
     * @return 
     * @throws Exception
     */
    public EntityAppProfile addEntityAppProfile(Long appProfileId, Long entityId) throws Exception;
    
    /**
     * 
     * @param appProfileId
     * @param entityAppProfile
     * @return 
     * @throws Exception
     */
    public EntityAppProfile updateEntityAppProfile(Long appProfileId, EntityAppProfile entityAppProfile) throws Exception;
    
    /**
     * 
     * @param appProfileId 
     * @param entityId 
     * @throws Exception
     */
    public void deleteEntityAppProfile(Long appProfileId, Long entityId) throws Exception;
    
    /**
     * 
     * @param appProfileId 
     * @param entityId 
     * @return  
     * @throws Exception
     */
    public List<AppParam> getAppParamListByEntity(Long appProfileId, Long entityId) throws Exception;
    
    /**
     * 
     * @param appProfileId 
     * @param entityId 
     * @return  
     * @throws Exception
     */
    public List<AppParam> getAppParamListWithoutEntity(Long appProfileId, Long entityId) throws Exception;
    
    /**
     *
     * @param appProfileId
     * @param entityId 
     * @param appParamId 
     * @return
     * @throws java.lang.Exception
     */
    public EntityAppProfileParam addEntityAppProfileParam(Long appProfileId, Long entityId, Long appParamId) throws Exception;
    
    /**
     *
     * @param appProfileId
     * @param entityId
     * @param entityAppProfileParam  
     * @return
     * @throws java.lang.Exception
     */
    public EntityAppProfileParam updateEntityAppProfileParam(Long appProfileId, Long entityId, EntityAppProfileParam entityAppProfileParam) throws Exception;
    
    /**
     *
     * @param appProfileId
     * @param entityId
     * @param appParamId
     * @throws java.lang.Exception
     */
    public void deleteEntityAppProfileParam(Long appProfileId, Long entityId, Long appParamId) throws Exception;
    
    /**
     *
     * @param id
     * @return 
     * @throws java.lang.Exception
     */
    public List<AppParam> getAppFileListByAppProfile(Long id) throws Exception;
    
    /**
     *
     * @param id
     * @return 
     * @throws java.lang.Exception
     */
    public List<AppParam> getAppFileListWithoutAppProfile(Long id) throws Exception;
    
    /**
     *
     * @param id
     * @param entityId
     * @return 
     * @throws java.lang.Exception
     */
    public List<AppParam> getAppFileListByEntity(Long id, Long entityId) throws Exception;
    
    /**
     *
     * @param id
     * @param entityId
     * @return 
     * @throws java.lang.Exception
     */
    public List<AppParam> getAppFileListWithoutEntity(Long id, Long entityId) throws Exception;
    
    /**
     *
     * @param appProfileId
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return 
     * @throws java.lang.Exception
     */
    public List<AppParam> searchAppParamByProfile(Long appProfileId, String filter, int pageNumber, int rowsPerPage) throws Exception;
       
    /**
     *
     * @param appProfileId
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return 
     * @throws java.lang.Exception
     */        
    public List<AppParam> searchAppFileByProfile(Long appProfileId, String filter, int pageNumber, int rowsPerPage) throws Exception;
}
