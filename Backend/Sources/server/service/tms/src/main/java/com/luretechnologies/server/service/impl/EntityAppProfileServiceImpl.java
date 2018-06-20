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

import com.luretechnologies.server.data.dao.AppProfileDAO;
import com.luretechnologies.server.data.dao.EntityAppProfileDAO;
import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.tms.AppProfile;
import com.luretechnologies.server.data.model.tms.EntityAppProfile;
import com.luretechnologies.server.service.EntityAppProfileService;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EntityAppProfileServiceImpl implements EntityAppProfileService{
    
    @Autowired
    EntityAppProfileDAO entityAppProfileDAO;
    
    @Autowired
    EntityDAO entityDAO;
    
    @Autowired
    AppProfileDAO appProfileDAO;
    
    /**
     *
     * @param entityAppProfile
     * @return
     * @throws Exception
     */
    
    @Override
    public EntityAppProfile create(EntityAppProfile entityAppProfile) throws Exception {
        EntityAppProfile newEntityAppProfile = new EntityAppProfile();   
        
        Entity entity = entityAppProfile.getEntity();
        
        // Check entity existence
        if (entity != null) {
            Entity existentEntity = entityDAO.findByEntityId(entityAppProfile.getEntity().getEntityId());

            if (existentEntity == null) {
                throw new ObjectRetrievalFailureException(Entity.class, entityAppProfile.getEntity().getEntityId());
            }

            entityAppProfile.setEntity(existentEntity);
        } else {
            // If not Entity defined set Default (Enterprise)
            entityAppProfile.setEntity(entityDAO.getFirstResult());
        }
        
        // Check appProfile existence
        AppProfile existentAppProfile = appProfileDAO.getAppProfileByID(entityAppProfile.getAppProfileId());

        if (existentAppProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, entityAppProfile.getAppProfileId());
        }
        entityAppProfile.setAppProfileId(existentAppProfile.getId());
        
        // Copy properties from -> to
        BeanUtils.copyProperties(entityAppProfile, newEntityAppProfile);

        entityAppProfileDAO.persist(newEntityAppProfile);

        return newEntityAppProfile;
    }

    @Override
    public EntityAppProfile update(long id, EntityAppProfile entityAppProfile) throws Exception {
        EntityAppProfile existentEntityAppProfile = entityAppProfileDAO.getEntityAppProfileByID(id);
        Entity entity = entityAppProfile.getEntity();
        
        // Check entity existence
        if (entity != null) {
            Entity existentEntity = entityDAO.findByEntityId(entityAppProfile.getEntity().getEntityId());

            if (existentEntity == null) {
                throw new ObjectRetrievalFailureException(Entity.class, entityAppProfile.getEntity().getEntityId());
            }

            entityAppProfile.setEntity(existentEntity);
        } else {
            // If not Entity defined set Default (Enterprise)
            entityAppProfile.setEntity(entityDAO.getFirstResult());
        }
        
        // Check appProfile existence
        AppProfile existentAppProfile = appProfileDAO.getAppProfileByID(entityAppProfile.getAppProfileId());

        if (existentAppProfile == null) {
            throw new ObjectRetrievalFailureException(AppProfile.class, entityAppProfile.getAppProfileId());
        }
        entityAppProfile.setAppProfileId(existentAppProfile.getId());
            
        // Copy properties from -> to
        BeanUtils.copyProperties(entityAppProfile, existentEntityAppProfile);
        
        return entityAppProfileDAO.merge(existentEntityAppProfile);
    }

    @Override
    public void delete(long id) throws Exception {
        EntityAppProfile entityAppProfile = entityAppProfileDAO.getEntityAppProfileByID(id);
        entityAppProfileDAO.delete(entityAppProfile);
    }
    
     /**
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public List<EntityAppProfile> list(List<Long> ids) throws Exception {
        List<EntityAppProfile> entityAppProfileList = entityAppProfileDAO.getEntityAppProfileList(ids);
        return entityAppProfileList;
    }
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public EntityAppProfile get(long id) throws Exception {
        EntityAppProfile entityAppProfile = entityAppProfileDAO.getEntityAppProfileByID(id);
        
        return entityAppProfile;
    }
    
    /*private EntityAppProfile updateEntityAppProfile(EntityAppProfile entityAppProfile){
        entityAppProfile.setName("Updated");
        entityAppProfileDAO.merge(entityAppProfile);
        return entityAppProfile;
    }*/
}



