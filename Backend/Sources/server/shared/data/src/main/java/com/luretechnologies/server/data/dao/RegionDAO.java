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
package com.luretechnologies.server.data.dao;

import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.Region;
import java.util.List;
import javax.persistence.PersistenceException;

/**
 *
 *
 * @author developer
 */
public interface RegionDAO extends BaseDAO<Region, Long> {

    /**
     *
     * @param id
     * @return
     * @throws PersistenceException
     */
    public Region get(long id) throws PersistenceException;

    /**
     *
     * @param regionId
     * @return
     * @throws PersistenceException
     */
    public Region findByRegionId(String regionId) throws PersistenceException;

    /**
     *
     * @param entity
     * @param firstResult
     * @param lastResult
     * @return @throws PersistenceException
     */
    public List<Region> list(Entity entity, int firstResult, int lastResult) throws PersistenceException;

    /**
     *
     * @param entity
     * @param filter
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    public List<Region> search(Entity entity, String filter, int firstResult, int lastResult) throws PersistenceException;

    /**
     *
     * @param filter
     * @param firstResult
     * @param maxResult
     * @return
     * @throws PersistenceException
     */
//    public List<Region> searchTree(String filter, int firstResult, int maxResult) throws PersistenceException;
    /**
     *
     * @param filter
     * @param firstResult
     * @param maxResult
     * @param parentEntity
     * @param idEntity
     * @return
     * @throws PersistenceException
     */
//    public Criteria searchChild(String filter, int firstResult, int maxResult, Class parentEntity, long idEntity) throws PersistenceException;
    /**
     *
     * @param id
     * @return
     * @throws PersistenceException
     */
//    public List<Merchant> getMerchants(long id) throws PersistenceException;
    /**
     *
     * @param region
     * @return
     * @throws PersistenceException
     */
//    public List<Terminal> getTerminals(Region region) throws PersistenceException;
    //public List<EntityRelation> getEntityRelationMerchants(long id) throws PersistenceException;
    /**
     *
     * @param id
     * @return
     * @throws PersistenceException
     */
//    public List<Survey> getSurveys(long id) throws PersistenceException;
    /**
     *
     * @param merchant
     * @throws PersistenceException
     */
//    public void addMerchant(Merchant merchant) throws PersistenceException;
    /**
     *
     * @param merchant
     * @throws PersistenceException
     */
//    public void updateMerchant(Merchant merchant) throws PersistenceException;
    /**
     *
     * @param idMerchant
     * @throws PersistenceException
     */
//    public void deleteMerchant(long idMerchant) throws PersistenceException;
    /**
     *
     * @param user
     * @throws PersistenceException
     */
//    public void addUser(User user) throws PersistenceException;
    /**
     *
     * @param user
     * @return
     * @throws PersistenceException
     */
//    public User updateUser(User user) throws PersistenceException;
    /**
     *
     * @param user
     * @throws PersistenceException
     */
//    public void deleteUser(User user) throws PersistenceException;
    /**
     *
     * @param survey
     * @throws PersistenceException
     */
//    public void addSurvey(Survey survey) throws PersistenceException;
    /**
     *
     * @param id
     * @param filter
     * @param firstResult
     * @param maxResult
     * @return
     * @throws PersistenceException
     */
//    public List<EntitySurvey> searchSurveys(long id, String filter, int firstResult, int maxResult) throws PersistenceException;
    /**
     *
     * @param id
     * @return
     * @throws PersistenceException
     */
//    public List<Organization> getPrevOrganizations(long id) throws PersistenceException;
    /**
     *
     * @param id
     * @return
     * @throws PersistenceException
     */
//    public List<Survey> getSurveysByRegion(long id) throws PersistenceException;
}
