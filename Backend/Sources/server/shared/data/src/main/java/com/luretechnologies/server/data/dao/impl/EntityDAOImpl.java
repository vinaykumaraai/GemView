/**
 * COPYRIGHT @ Lure Technologies, LLC. ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or form
 * other than in accordance with and subject to the terms of a written license
 * from Lure or with the prior written consent of Lure or as permitted by
 * applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure. If you are neither the intended
 * recipient, nor an agent, employee, nor independent contractor responsible for
 * delivering this message to the intended recipient, you are prohibited from
 * copying, disclosing, distributing, disseminating, and/or using the
 * information in this email in any manner. If you have received this message in
 * error, please advise us immediately at legal@luretechnologies.com by return
 * email and then delete the message from your computer and all other records
 * (whether electronic, hard copy, or otherwise).
 *
 * Any copies or reproductions of this software program (in whole or in part)
 * made by any method must also include a copy of this legend.
 *
 * Inquiries should be made to legal@luretechnologies.com
 *
 */
package com.luretechnologies.server.data.dao.impl;

import com.luretechnologies.common.enums.EntityTypeEnum;
import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.model.Device;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.EntityNode;
import com.luretechnologies.server.data.model.Merchant;
import com.luretechnologies.server.data.model.Organization;
import com.luretechnologies.server.data.model.Region;
import com.luretechnologies.server.data.model.Terminal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author developer
 */
@Repository
public class EntityDAOImpl extends BaseDAOImpl<Entity, Long> implements EntityDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityDAO.class);

    /**
     *
     * @param entityId
     * @return
     * @throws PersistenceException
     */
    @Override
    public Entity findByEntityId(String entityId) throws PersistenceException {
        try {
            return (Entity) findByProperty("id", Utils.decodeHashId(entityId), "parent", JoinType.LEFT).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Entity not found. entityId: " + entityId, e);
            return null;
        }
    }
    
    /**
     *
     * @param entity
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    @Override
    public List<Entity> listChildren(Entity entity, int firstResult, int lastResult) throws PersistenceException {
        CriteriaBuilder critBuilder = criteriaBuilder();
        CriteriaQuery<Entity> query = criteriaQuery();
        Root<Entity> root = getRoot(query);
        query.select(root).where(critBuilder.equal(root.get("parent"), entity.getId()));
        return query(query).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }
    
    /**
     *
     * @param entity
     * @return
     * @throws PersistenceException
     */
    @Override
    public List<Entity> listChildren(Entity entity) throws PersistenceException {
        CriteriaBuilder critBuilder = criteriaBuilder();
        CriteriaQuery<Entity> query = criteriaQuery();
        Root<Entity> root = getRoot(query);
        query.select(root).where(critBuilder.equal(root.get("parent"), entity.getId()));
        return query(query).getResultList();
    }

    /**
     * @param entity
     * @param id
     * @return
     * @throws PersistenceException
     */
    @Override
    public EntityNode getEntityHierarchy(Entity entity, long id) throws PersistenceException {
        if (!isDescendant(entity, id)) {
            throw new PersistenceException("Not found");
        }
        return getEntityManager().find(EntityNode.class, id);
    }

    /**
     *
     * @param entity
     * @param id
     * @return
     * @throws PersistenceException
     */
    @Override
    public List<Entity> getEntityChildren(Entity entity, long id) throws PersistenceException {
        if (!isDescendant(entity, id)) {
            throw new PersistenceException("Not found");
        }
        CriteriaBuilder critBuilder = criteriaBuilder();
        CriteriaQuery<Entity> query = criteriaQuery();
        Root<Entity> root = query.from(Entity.class);
                
        query.select(root);
        query.where(critBuilder.like(root.<String>get("path"), "-" + String.valueOf(id) + "-%"));
        return query(query).getResultList();
    }

    /**
     *
     * @param entity
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    @Override
    public List<Organization> listOrganizations(Entity entity, int firstResult, int lastResult) throws PersistenceException {
        List<Organization> result = new ArrayList<>();
        if (entity instanceof Organization) {
            result.add((Organization) entity);
            return result;
        } else {
            List<Entity> children = listChildren(entity, firstResult, lastResult);
            for (Entity child : children) {
                if (child instanceof Organization) {
                    result.add((Organization) child);
                } else {
                    result.addAll(listOrganizations(child, firstResult, lastResult));
                }
            }
        }
        return result;
    }

    /**
     *
     * @param entity
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    @Override
    public List<Region> listRegions(Entity entity, int firstResult, int lastResult) throws PersistenceException {
        List<Region> result = new ArrayList<>();
        if (entity instanceof Region) {
            result.add((Region) entity);
            return result;
        } else {
            List<Entity> children = listChildren(entity, firstResult, lastResult);
            for (Entity child : children) {
                if (child instanceof Region) {
                    result.add((Region) child);
                } else {
                    result.addAll(listRegions(child, firstResult, lastResult));
                }
            }
        }
        return result;
    }

    /**
     *
     * @param entity
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    @Override
    public List<Merchant> listMerchants(Entity entity, int firstResult, int lastResult) throws PersistenceException {
        List<Merchant> result = new ArrayList<>();
        if (entity instanceof Merchant) {
            result.add((Merchant) entity);
            return result;
        } else {
            List<Entity> children = listChildren(entity, firstResult, lastResult);
            for (Entity child : children) {
                if (child instanceof Merchant) {
                    result.add((Merchant) child);
                } else {
                    result.addAll(listMerchants(child, firstResult, lastResult));
                }
            }
        }
        return result;
    }

    /**
     *
     * @param entity
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    @Override
    public List<Terminal> listTerminals(Entity entity, int firstResult, int lastResult) throws PersistenceException {
        List<Terminal> result = new ArrayList<>();
        if (entity instanceof Terminal) {
            result.add((Terminal) entity);
            return result;
        } else {
            List<Entity> children = listChildren(entity, firstResult, lastResult);
            for (Entity child : children) {
                if (child instanceof Terminal) {
                    result.add((Terminal) child);
                } else {
                    result.addAll(listTerminals(child, firstResult, lastResult));
                }
            }
        }
        return result;
    }

    /**
     *
     * @param entity
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    @Override
    public List<Device> listDevices(Entity entity, int firstResult, int lastResult) throws PersistenceException {
        List<Device> result = new ArrayList<>();
        if (entity instanceof Device) {
            result.add((Device) entity);
            return result;
        } else {
            List<Entity> children = listChildren(entity, firstResult, lastResult);
            for (Entity child : children) {
                if (child instanceof Device) {
                    result.add((Device) child);
                } else {
                    result.addAll(listDevices(child, firstResult, lastResult));
                }
            }
        }
        return result;
    }

    /**
     *
     * @param entity
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    @Override
    public List<Long> listTerminalIds(Entity entity, int firstResult, int lastResult) throws PersistenceException {
        List<Long> result = new ArrayList<>();
        if (entity instanceof Terminal) {
            result.add(entity.getId());
            return result;
        } else {
            List<Entity> children = listChildren(entity, firstResult, lastResult);
            for (Entity child : children) {
                if (child instanceof Terminal) {
                    result.add(child.getId());
                } else {
                    result.addAll(listTerminalIds(child, firstResult, lastResult));
                }
            }
        }
        return result;
    }

    /**
     * @param entityType
     * @param parent
     *
     * @return boolean
     */
    @Override
    public boolean isParentCorrect(EntityTypeEnum entityType, Entity parent) {
        if (parent == null) {
            return true;
        }
        EntityTypeEnum parentType = parent.getType();
        return entityType.isParentCorrect(parentType);
    }

    /**
     * @param entity
     * @param id
     *
     * @return
     */
    @Override
    public boolean isDescendant(Entity entity, long id) {
        Entity currentEntity = getEntityManager().find(Entity.class, id);
        while (currentEntity != null) {
            if (currentEntity.getId() == entity.getId()) {
                return true;
            }
            currentEntity = currentEntity.getParent();
        }
        return false;
    }

    /**
     * @param entity
     * @param isNew
     */
    @Override
    public void updatePath(Entity entity, boolean isNew) {
        String path = "";
        Entity parent = entity.getParent();
        if (parent != null) {
            parent = findById(parent.getId());
            String parentPath = (!parent.getPath().isEmpty()) ? parent.getPath() : "-";
            path = "-" + String.valueOf(parent.getId()) + parentPath;
        }
        entity.setPath(path);

        if (!isNew) {
            List<Entity> subTree = getEntityHierarchyList(entity.getId());
            for (Entity item : subTree) {
                LOGGER.info("updatePath: " + item.getName());
                path = getEntityPath(item);
                super.merge(item);
                item.setPath(path);
            }
        }
    }
    
    /**
     * @param entity
     */
    @Override
    public void updateActive(Entity entity) {
        List<Entity> entities = getEntityHierarchyList(entity.getId());
        for (Entity item : entities) {
            item.setActive(entity.getActive());
            merge(item);
        }
    }

    /**
     * @param id
     * @param parentId
     *
     * @return
     */
    @Override
    public Entity move(long id, long parentId) throws PersistenceException {
        if(id == parentId) {
            throw new PersistenceException("Parent and entity id must be differents.");
        }
        Entity parent = findById(parentId);
        Entity entity = findById(id);
        if (parent == null || entity == null) {
            throw new PersistenceException("Entity or parent not exists.");
        }
        if (id == 1) {
            throw new PersistenceException("Cannot move root entity.");
        }
        if (entity.getParent() != null && entity.getParent().getId() == parentId) {
            return entity;
        }
        if (!isParentCorrect(entity.getType(), parent)) {
            throw new PersistenceException("Incorrect parent type.");
        }
        String parentPath = getEntityPath(parent);
        if(parentPath.contains("-" + String.valueOf(id) + "-")) {
            throw new PersistenceException("Entity is already a parent ancestor.");
        }
        super.merge(entity);
        entity.setParent(parent);
        entity.setParentId(parent.getId());
        updatePath(entity, false);        
        return entity;
    }

    /**
     * @param expression
     * @param entity
     *
     * @return
     */
    @Override
    public List<Predicate> wherePredicate(Expression expression, Entity entity) {
        List<Predicate> predicates = new ArrayList<>();
        Predicate predicate;
        if (entity.getType().equals(EntityTypeEnum.TERMINAL)) {
            predicate = criteriaBuilder().like(expression, "%-" + entity.getId() + "-%");
        } else {
            predicate = criteriaBuilder().like(expression, "%-" + entity.getId() + "-%");
        }
        predicates.add(predicate);
        return predicates;
    }

    /**
     *
     * @param expression
     * @param entity
     * @return
     * @throws PersistenceException
     */
    private Predicate getLevelEntites(Expression expression, Entity entity, int level) throws PersistenceException {
        if (level == 1) {
            return criteriaBuilder().in(expression).value(entity.getId());
        }

        Subquery<Long> subquery = criteriaQuery().subquery(Long.class);
        Root sqRoot = subquery.from(Entity.class);
        subquery.select(sqRoot.get("id"));
        subquery.where(criteriaBuilder().in(sqRoot.get("parent")).value(entity));

        for (int i = 2; i < level; i++) {
            Subquery<Long> currentSubquery = criteriaQuery().subquery(Long.class);
            sqRoot = currentSubquery.from(Entity.class);
            currentSubquery.select(sqRoot.get("id"));
            currentSubquery.where(criteriaBuilder().in(sqRoot.get("parent")).value(subquery));

            subquery = currentSubquery;
        }
        return criteriaBuilder().in(expression).value(subquery);
    }

    public List<Entity> getEntityHierarchyList(long id) throws PersistenceException {
        CriteriaBuilder critBuilder = criteriaBuilder();
        CriteriaQuery<Entity> query = criteriaQuery();
        Root<Entity> root = getRoot(query);
        query.select(root).where(critBuilder.like(root.<String>get("path"), "%-" + String.valueOf(id) + "-%"));
        return query(query).getResultList();
    }

    private void updateAllPaths() {
        List<Entity> entities = super.list();
        for (Entity entity : entities) {
            String path = getEntityPath(entity);
            super.entityManager.createNativeQuery("UPDATE entity SET path = '" + path + "' WHERE id=" + entity.getId()).executeUpdate();
            //entity.setPath(path);
            LOGGER.debug(path);
        }
    }

    private String getEntityPath(Entity entity) {
        String path = "";
        int i = 0;
        Entity parent = entity.getParent();
        while (parent != null) {
            if (i == 0) {
                path = "-";
            }
            path = path + String.valueOf(parent.getId()) + "-";
            parent = parent.getParent();
            i++;
        }
        return path;
    }
}
