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

import com.luretechnologies.common.enums.MerchantSettingEnum;
import com.luretechnologies.common.enums.ModeEnum;
import com.luretechnologies.common.enums.OperationEnum;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.Merchant;
import com.luretechnologies.server.data.model.payment.Host;
import com.luretechnologies.server.data.model.payment.MerchantHost;
import com.luretechnologies.server.data.model.payment.MerchantHostModeOperation;
import com.luretechnologies.server.data.model.payment.MerchantHostSettingValue;
import com.luretechnologies.server.data.model.payment.MerchantSettingValue;
import java.util.List;
import javax.persistence.PersistenceException;

/**
 *
 */
public interface MerchantService {

    /**
     *
     * @param merchant
     * @return
     * @throws Exception
     */
    public Merchant create(Merchant merchant) throws Exception;

    /**
     *
     * @param merchantId
     * @param merchant
     * @return
     * @throws Exception
     */
    public Merchant update(String merchantId, Merchant merchant) throws Exception;

    /**
     *
     * @param merchantId
     * @return
     * @throws Exception
     */
    public Merchant get(String merchantId) throws Exception;

    /**
     *
     * @param merchantId
     * @throws Exception
     */
    public void delete(String merchantId) throws Exception;

    /**
     *
     * @param entity
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    public List<Merchant> list(Entity entity, int pageNumber, int rowsPerPage) throws Exception;

    /**
     *
     * @param entity
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    public List<Merchant> search(Entity entity, String filter, int pageNumber, int rowsPerPage) throws Exception;

    /**
     *
     * @param entity
     * @param filter
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    public int getSearchTotalPages(Entity entity, String filter, int rowsPerPage) throws Exception;

    /**
     *
     * @param merchantId
     * @return
     * @throws Exception
     */
    public List<Host> listAvailableHosts(String merchantId) throws Exception;

    /**
     *
     * @param merchantId
     * @param hostId
     * @return
     * @throws Exception
     */
    public MerchantHost addHost(String merchantId, Long hostId) throws Exception;

    /**
     *
     * @param merchantId
     * @param host
     * @throws Exception
     */
    public void deleteHost(String merchantId, Long hostId) throws Exception;

    /**
     *
     * @param merchantId
     * @param hostId
     * @return
     * @throws Exception
     */
    public List<MerchantHostSettingValue> listHostSettings(String merchantId, Long hostId) throws Exception;

    /**
     *
     * @param merchantId
     * @param hostId
     * @param merchantHostSettingId
     * @param value
     * @return
     * @throws Exception
     */
    public MerchantHostSettingValue setHostSettingValue(String merchantId, Long hostId, Long merchantHostSettingId, String value) throws Exception;


    /**
     *
     * @param merchantId
     * @param merchantSettingValue
     * @return
     * @throws Exception
     */
    public Merchant addSetting(String merchantId, MerchantSettingValue merchantSettingValue) throws Exception;

    /**
     *
     * @param merchantId
     * @param merchantSettingValue
     * @return
     * @throws Exception
     */
    public Merchant updateSetting(String merchantId, MerchantSettingValue merchantSettingValue) throws Exception;

    /**
     *
     * @param merchantId
     * @param merchantSetting
     * @throws Exception
     */
    public void deleteSetting(String merchantId, MerchantSettingEnum merchantSetting) throws Exception;

    /**
     *
     * @param merchantId
     * @return
     * @throws Exception
     */
    public List<MerchantSettingEnum> listAvailableSettings(String merchantId) throws Exception;

    /**
     *
     * @param merchantId
     * @param mode
     * @param operation
     * @return
     * @throws PersistenceException
     */
    public Host getHostByModeOperation(String merchantId, ModeEnum mode, OperationEnum operation) throws PersistenceException;
    
    public List<MerchantHostModeOperation> getHostModeOperationsByMerchantId(String merchantId) throws PersistenceException;
    
    public MerchantHostModeOperation addHostModeOperation(String merchantId, Long hostModeOperationId) throws Exception;
    
    public void deleteHostModeOperation(String merchantId, Long hostModeOperationId) throws Exception;
    
    public MerchantHostModeOperation findHostModeOperation(String merchantId, Long hostModeOperationId);

//    public List<Merchant> searchTree(String filter, int pageNumber, int rowsPerPage) throws Exception;
//
//    public int getSearchTotalPages(String search, int rowsPerPage) throws Exception;
//
//    public List<Terminal> getTerminals(long id) throws Exception;
//
//    
//    public List<Survey> getSurveys(long id) throws Exception;
//    public void addTerminal(EntityRelation entityRelation) throws Exception;
//
//    public void updateTerminal(EntityRelation entityRelation) throws Exception;
//    public void deleteTerminal(long idEntityRelation) throws Exception;
//
//    public int searchTotalPagesSurvey(long idEntity, String search, int rowsPerPage) throws Exception;
//
//    public void addSurvey(EntitySurvey entitySurvey) throws Exception;
//
//    public void updateSurvey(EntitySurvey entitySurvey) throws Exception;
//
//    public void deleteSurvey(long idEntitySurvey) throws Exception;
//
//    public List<EntitySurvey> searchSurveys(long id, String filter, int pageNumber, int rowsPerPage) throws Exception;
//
//    public List<Region> getPrevRegions(long id) throws Exception;

    public Entity copy(String entityId, long parentId) throws Exception;
}
