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

import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.tms.App;
import com.luretechnologies.server.data.model.tms.AppFile;
import com.luretechnologies.server.data.model.tms.AppParam;
import com.luretechnologies.server.data.model.tms.AppProfile;
import java.io.File;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Vinay
 */
public interface AppService {
    
    /**
     *
     * @param app
     * @return
     * @throws Exception
     */
    public App create(App app) throws Exception;

    /**
     *
     * @param id
     * @param description
     * @param fileContent
     * @param file
     * @return
     * @throws Exception
     */
    public App addAppFile(Long id, String description, MultipartFile fileContent, File file) throws Exception;
    
    /**
     *
     * @param id
     * @param appParamId
     * @throws Exception
     */
    public void deleteAppFile(Long id, Long appParamId) throws Exception;
    
    /**
     *
     * @param id
     * @throws Exception
     */
    public void deleteAllAppFile(Long id) throws Exception;
    
    /**
     *
     * @param id
     * @throws Exception
     */
    public void deleteAllAppParam(Long id) throws Exception;
    
    /**
     *
     * @param id
     * @param app
     * @return
     * @throws Exception
     */
    public App update(long id, App app) throws Exception;

   /**
     *
     * @param id
     * @throws Exception
     */
    public void delete(long id) throws Exception;
    
    /**
     *
     * @param entity
     * @return
     * @throws Exception
     */
    public List<App> list(Entity entity) throws Exception;
    
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public App get(long id) throws Exception;
    
    /**
     *
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    public List<App> search(String filter,  int pageNumber, int rowsPerPage) throws Exception;

    /**
     *
     * @param id
     * @param appParam
     * @return
     * @throws Exception
     */
    public App addAppParam(Long id, AppParam appParam) throws Exception;
    
    /**
     *
     * @param id
     * @param appParamId
     * @throws Exception
     */
    public void deleteAppParam(Long id, Long appParamId) throws Exception;
    
    /**
     *
     * @param id
     * @param appParam
     * @return 
     * @throws Exception
     */
    public App updateAppParam(Long id, AppParam appParam) throws Exception;
    
    /**
     *
     * @param id
     * @param appProfile
     * @return
     * @throws Exception
     */
    public App addAppProfile(Long id, AppProfile appProfile) throws Exception;

    /**
     *
     * @param id
     * @param appProfileId
     * @throws Exception
     */
    public void deleteAppProfile(Long id, Long appProfileId) throws Exception;
    
    /**
     *
     * @param id
     * @return 
     * @throws Exception
     */
    public List<AppParam> getAppParamList(Long id) throws Exception;
    
     /**
     *
     * @param id
     * @return 
     * @throws Exception
     */
    public List<AppProfile> getAppProfileList(Long id) throws Exception;
    
     /**
     *
     * @param id
     * @return 
     * @throws Exception
     */
    public List<AppParam>  getAppFileList(Long id) throws Exception;
    
     /**
     *
     * @param appId
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return 
     * @throws Exception
     */
    public List<AppParam> searchAppParam(Long appId, String filter, int pageNumber, int rowsPerPage) throws Exception;
    
     /**
     *
     * @param appId
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return 
     * @throws Exception
     */
    public List<AppParam> searchAppFile(Long appId, String filter, int pageNumber, int rowsPerPage) throws Exception;
}
