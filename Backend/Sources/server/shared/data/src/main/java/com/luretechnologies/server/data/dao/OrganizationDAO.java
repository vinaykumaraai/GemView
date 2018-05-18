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
import com.luretechnologies.server.data.model.Organization;
import java.util.List;
import javax.persistence.PersistenceException;

/**
 *
 *
 * @author developer
 */
public interface OrganizationDAO extends BaseDAO<Organization, Long> {

    /**
     *
     * @param id
     * @return
     * @throws PersistenceException
     */
    public Organization get(long id) throws PersistenceException;

    /**
     *
     * @param organizationId
     * @return
     * @throws PersistenceException
     */
    public Organization findByOrganizationId(String organizationId) throws PersistenceException;

    /**
     *
     * @param entity
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    public List<Organization> list(Entity entity, int firstResult, int lastResult) throws PersistenceException;

    /**
     *
     * @param entity
     * @param filter
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    public List<Organization> search(Entity entity, String filter, int firstResult, int lastResult) throws PersistenceException;

    /**
     *
     * @param filter
     * @param firstResult
     * @param maxResult
     * @return
     * @throws PersistenceException
     */
//    public List<Organization> searchTree(String filter, int firstResult, int maxResult) throws PersistenceException;
    /**
     *
     * @param filter
     * @param firstResult
     * @param maxResult
     * @param user
     * @return
     * @throws PersistenceException
     */
//    public List<EntityRelation> getEntityRelationOrganizations(String filter, int firstResult, int maxResult, User user) throws PersistenceException;
    /**
     *
     * @param id
     * @return
     * @throws PersistenceException
     */
//    public List<Region> getRegions(long id) throws PersistenceException;
    /**
     *
     * @param organization
     * @return
     * @throws PersistenceException
     */
//    public List<Terminal> getTerminals(Organization organization) throws PersistenceException;
    /**
     *
     * @param id
     * @return
     * @throws PersistenceException
     */
//    public List<Survey> getSurveys(long id) throws PersistenceException;
    /**
     *
     * @param id
     * @return
     * @throws PersistenceException
     */
//    public List<EntityRelation> getEntityRelationRegions(long id) throws PersistenceException;
    /**
     *
     * @param region
     * @throws PersistenceException
     */
//    public void addRegion(Region region) throws PersistenceException;
    /**
     *
     * @param region
     * @throws PersistenceException
     */
//    public void updateRegion(Region region) throws PersistenceException;
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
     * @param survey
     * @throws PersistenceException
     */
//    public void updateSurvey(Survey survey) throws PersistenceException;
    /**
     *
     * @param survey
     * @throws PersistenceException
     */
//    public void deleteSurvey(Survey survey) throws PersistenceException;
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
//    public List<Survey> getSurveysByOrganization(long id) throws PersistenceException;
}
