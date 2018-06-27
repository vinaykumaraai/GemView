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
package com.luretechnologies.server.service.impl;

import com.luretechnologies.server.data.dao.EntityLevelDAO;
import com.luretechnologies.server.data.model.tms.EntityLevel;
import com.luretechnologies.server.service.EntityLevelService;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Vinay
 */
@Service
@Transactional
public class EntityLevelServiceImpl implements EntityLevelService{
    
    @Autowired(required=true)
    EntityLevelDAO entityLevelDAO;
    
    /**
     *
     * @param entityLevel
     * @return
     * @throws Exception
     */
    @Override
    public EntityLevel createEntityLevel(EntityLevel entityLevel) throws Exception{
        //EntityLevel newEntityLevel = new EntityLevel();
        
         // Copy properties from -> to
        //BeanUtils.copyProperties(entityLevel , newEntityLevel);
        entityLevelDAO.persist(entityLevel);
        return entityLevel;
        
    }
    
    /**
     *
     * @param ID
     * @return
     * @throws Exception
     */
    @Override
    public EntityLevel updateEntityLevel(long ID, EntityLevel entityLevel) throws Exception{
        EntityLevel existentEntityLevel = entityLevelDAO.getEntityLevelByID(ID);
        
        // Copy properties from -> to
        BeanUtils.copyProperties(entityLevel, existentEntityLevel);
        
        //EntityLevel updatedEntityLevel=  updateEntityLevel(entityLevel);
        return entityLevelDAO.merge(existentEntityLevel);
        
    }
    
    /**
     *
     * @param ID
     * @throws Exception
     */
    @Override
    public void deleteEntityLevel(long ID) throws Exception{
        EntityLevel entityLevel = entityLevelDAO.getEntityLevelByID(ID);
        entityLevelDAO.delete(entityLevel);
        
    }
    
    /**
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public List<EntityLevel> getEntityLevelList(List<Long> ids) throws Exception{
        List<EntityLevel> entityLevel = entityLevelDAO.getEntityLevelList(ids);
        return entityLevel;
        
    }
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public EntityLevel getEntityLevelByID(Long id) throws Exception{
        EntityLevel entityLevel = entityLevelDAO.getEntityLevelByID(id);
        return entityLevel;
        
    }
    
    private EntityLevel updateEntityLevel(EntityLevel entityLevel)throws Exception{
        entityLevel.setName("Updated");
        entityLevelDAO.merge(entityLevel);
        return entityLevel;
    }
}