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

import com.luretechnologies.common.enums.EntityTypeEnum;
import com.luretechnologies.server.data.model.Device;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.EntityNode;
import com.luretechnologies.server.data.model.Merchant;
import com.luretechnologies.server.data.model.Organization;
import com.luretechnologies.server.data.model.Region;
import com.luretechnologies.server.data.model.Terminal;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 *
 *
 * @author developer
 */
public interface EntityDAO extends BaseDAO<Entity, Long> {

    /**
     *
     * @param entityId
     * @return
     * @throws PersistenceException
     */
    public Entity findByEntityId(String entityId) throws PersistenceException;
    
    /**
     *
     * @param entity
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    public List<Entity> listChildren(Entity entity, int firstResult, int lastResult) throws PersistenceException;
    
    public List<Entity> listChildren(Entity entity) throws PersistenceException;
    
    public List<Entity> getEntityChildren(Entity entity, long id) throws PersistenceException;

    /**
     * @param entity
     * @param id
     * @return
     * @throws PersistenceException
     */
    public EntityNode getEntityHierarchy(Entity entity, long id);
    
    /**
     *
     * @param entity
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    public List<Organization> listOrganizations(Entity entity, int firstResult, int lastResult) throws PersistenceException;

    /**
     *
     * @param entity
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    public List<Region> listRegions(Entity entity, int firstResult, int lastResult) throws PersistenceException;

    /**
     *
     * @param entity
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    public List<Merchant> listMerchants(Entity entity, int firstResult, int lastResult) throws PersistenceException;

    /**
     *
     * @param entity
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    public List<Terminal> listTerminals(Entity entity, int firstResult, int lastResult) throws PersistenceException;

    /**
     *
     * @param entity
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    public List<Device> listDevices(Entity entity, int firstResult, int lastResult) throws PersistenceException;

    /**
     *
     * @param entity
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    public List<Long> listTerminalIds(Entity entity, int firstResult, int lastResult) throws PersistenceException;
    
    public List<Predicate> wherePredicate(Expression expression, Entity entity) throws PersistenceException;
    
    public boolean isDescendant(Entity entity, long id);
    
    public void updatePath(Entity entity, boolean isNew);
    
    public void updateActive(Entity entity);
    
    public Entity move(long id, long parentId);
    
    public boolean isParentCorrect(EntityTypeEnum entityType, Entity parent);
}
