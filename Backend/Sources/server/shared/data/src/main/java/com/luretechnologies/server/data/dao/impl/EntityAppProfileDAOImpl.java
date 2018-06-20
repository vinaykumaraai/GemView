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
package com.luretechnologies.server.data.dao.impl;

import com.luretechnologies.server.data.dao.EntityAppProfileDAO;
import com.luretechnologies.server.data.model.tms.EntityAppProfile;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class EntityAppProfileDAOImpl extends BaseDAOImpl<EntityAppProfile, Long> implements EntityAppProfileDAO{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityAppProfileDAOImpl.class);
    
    /**
     *
     * @param IDS
     * @return
     */
    @Override
    public List<EntityAppProfile> getEntityAppProfileList(List<Long> IDS)throws PersistenceException{
        List<EntityAppProfile> entityAppProfileList = new ArrayList<>();
        for(Long ID : IDS) {
            entityAppProfileList.add(getEntityManager().find(EntityAppProfile.class, ID));
        }
        return entityAppProfileList;
    }
    
    /**
     *
     * @param ID
     * @return
     */
    @Override
    public EntityAppProfile getEntityAppProfileByID(Long ID)throws PersistenceException{
        try{
            CriteriaQuery<EntityAppProfile> cq = criteriaQuery();
            Root<EntityAppProfile> root = getRoot(cq);   
            root.fetch("entityappprofileparamCollection", JoinType.LEFT);
            
            EntityAppProfile entityAppProfile = (EntityAppProfile) query(cq.where(criteriaBuilder().equal(root.get("id"), ID))).getSingleResult();
            return entityAppProfile;
        } catch (NoResultException e) {
            LOGGER.info("EntityAppProfile not found. id: " + ID, e);
            return null;
        }
    };
    
    /**
     *
     * @param value
     * @return
     */
    @Override
    public Integer doForceUpdate(Integer value)throws PersistenceException{
        Integer forceUpdate = null;
        if(value == 0){
            forceUpdate = 1;
        } 
        return  forceUpdate;
    };
    
    /**
     *
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    @Override
    public List<EntityAppProfile> list(int firstResult, int lastResult) throws PersistenceException{
        return query(criteriaQueryComplex()).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    };
    
    @Override
    public EntityAppProfile findByAppProfileAndEntity(Long appProfileId, Long entityId) throws PersistenceException{
        try {
            CriteriaQuery q = criteriaBuilder().createQuery(EntityAppProfile.class);
            Root root = q.from(EntityAppProfile.class);
            root.fetch("entity", JoinType.INNER);
            q.where(criteriaBuilder().and(criteriaBuilder().equal(root.get("appProfileId"), appProfileId),
                    criteriaBuilder().equal(root.get("entity").get("id"), entityId)));

            return (EntityAppProfile) query(q).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("EntityAppProfile findByAppProfileAndEntity.", e);
            return null;
        }
    }
    
}