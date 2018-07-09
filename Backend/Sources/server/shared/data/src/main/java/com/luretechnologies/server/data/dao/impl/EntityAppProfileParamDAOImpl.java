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


import com.luretechnologies.server.data.dao.EntityAppProfileParamDAO;
import com.luretechnologies.server.data.model.tms.EntityAppProfileParam;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class EntityAppProfileParamDAOImpl extends BaseDAOImpl<EntityAppProfileParam, Long> implements EntityAppProfileParamDAO{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityAppProfileParamDAOImpl.class);
    
    /**
     *
     * @param IDS
     * @return
     */
    @Override
    public List<EntityAppProfileParam> getEntityAppProfileParamList(List<Long> IDS)throws PersistenceException{
        List<EntityAppProfileParam> entityAppProfileParamList = new ArrayList<>();
        for(Long ID : IDS) {
            entityAppProfileParamList.add(getEntityManager().find(EntityAppProfileParam.class, ID));
        }
        return entityAppProfileParamList;
    }
    
    /**
     *
     * @param ID
     * @return
     */
    @Override
    public EntityAppProfileParam getEntityAppProfileParamByID(Long ID)throws PersistenceException{
        return  getEntityManager().find(EntityAppProfileParam.class, ID);
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
     * @param entityAppProfileId 
     * @param appProfileParamValueId 
     * @return
     */
    @Override
    public EntityAppProfileParam findByEntityAppProfileAndAppProfileParamValue(Long entityAppProfileId, Long appProfileParamValueId)throws PersistenceException{
        try {
            CriteriaQuery q = criteriaBuilder().createQuery(EntityAppProfileParam.class);
            Root root = q.from(EntityAppProfileParam.class);
            q.where(criteriaBuilder().and(criteriaBuilder().equal(root.get("entityAppProfileId"), entityAppProfileId),
                    criteriaBuilder().equal(root.get("appProfileParamValueId"), appProfileParamValueId)));

            return (EntityAppProfileParam) query(q).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("EntityAppProfileParam findByEntityAppProfileAndAppProfileParamValue.", e);
            return null;
        }
    }
    
}