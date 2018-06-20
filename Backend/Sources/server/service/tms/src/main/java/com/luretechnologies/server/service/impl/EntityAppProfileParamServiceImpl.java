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

import com.luretechnologies.server.data.dao.AppProfileParamValueDAO;
import com.luretechnologies.server.data.dao.EntityAppProfileDAO;
import com.luretechnologies.server.data.dao.EntityAppProfileParamDAO;
import com.luretechnologies.server.data.model.tms.AppProfileParamValue;
import com.luretechnologies.server.data.model.tms.EntityAppProfile;
import com.luretechnologies.server.data.model.tms.EntityAppProfileParam;
import com.luretechnologies.server.service.EntityAppProfileParamService;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EntityAppProfileParamServiceImpl implements EntityAppProfileParamService{
    
    @Autowired
    private EntityAppProfileParamDAO entityAppProfileParamDAO;
        
    @Autowired
    private AppProfileParamValueDAO appProfileParamValueDAO;
    
    @Autowired
    private EntityAppProfileDAO entityAppProfileDAO;
    
    /**
     *
     * @param entityAppProfileParam
     * @return
     * @throws Exception
     */
    
    @Override
    public EntityAppProfileParam create(EntityAppProfileParam entityAppProfileParam) throws Exception {
        EntityAppProfileParam newEntityAppProfileParam = new EntityAppProfileParam();    
        
        // Check entity existence
        EntityAppProfile existentEntityAppProfile = entityAppProfileDAO.getEntityAppProfileByID(entityAppProfileParam.getEntityAppProfileId());

        if (existentEntityAppProfile == null) {
            throw new ObjectRetrievalFailureException(EntityAppProfile.class, entityAppProfileParam.getEntityAppProfileId());
        }

        entityAppProfileParam.setEntityAppProfileId(existentEntityAppProfile.getAppProfileId());
        
        // Check appProfileParamValue existence
        AppProfileParamValue existentAppProfileParamValue = appProfileParamValueDAO.getAppProfileParamValueByID(entityAppProfileParam.getAppProfileParamValueId());

        if (existentAppProfileParamValue == null) {
            throw new ObjectRetrievalFailureException(AppProfileParamValue.class, entityAppProfileParam.getAppProfileParamValueId());
        }
        entityAppProfileParam.setAppProfileParamValueId(existentAppProfileParamValue.getId());
        
        // Copy properties from -> to
        BeanUtils.copyProperties(entityAppProfileParam, newEntityAppProfileParam);

        entityAppProfileParamDAO.persist(newEntityAppProfileParam);

        return newEntityAppProfileParam;
    }

    @Override
    public EntityAppProfileParam update(long id, EntityAppProfileParam entityAppProfileParam) throws Exception {
        EntityAppProfileParam existentEntityAppProfileParam = entityAppProfileParamDAO.getEntityAppProfileParamByID(id);
        
        // Check entity existence
        EntityAppProfile existentEntityAppProfile = entityAppProfileDAO.getEntityAppProfileByID(entityAppProfileParam.getEntityAppProfileId());

        if (existentEntityAppProfile == null) {
            throw new ObjectRetrievalFailureException(EntityAppProfile.class, entityAppProfileParam.getEntityAppProfileId());
        }

        entityAppProfileParam.setEntityAppProfileId(existentEntityAppProfile.getAppProfileId());
        
        // Check appProfileParamValue existence
        AppProfileParamValue existentAppProfileParamValue = appProfileParamValueDAO.getAppProfileParamValueByID(entityAppProfileParam.getAppProfileParamValueId());

        if (existentAppProfileParamValue == null) {
            throw new ObjectRetrievalFailureException(AppProfileParamValue.class, entityAppProfileParam.getAppProfileParamValueId());
        }
        entityAppProfileParam.setAppProfileParamValueId(existentAppProfileParamValue.getId());
            
        // Copy properties from -> to
        BeanUtils.copyProperties(entityAppProfileParam, existentEntityAppProfileParam);
        
        return entityAppProfileParamDAO.merge(existentEntityAppProfileParam);
    }

    @Override
    public void delete(long id) throws Exception {
        EntityAppProfileParam entityAppProfileParam = entityAppProfileParamDAO.getEntityAppProfileParamByID(id);
        entityAppProfileParamDAO.delete(entityAppProfileParam);
    }
    
     /**
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public List<EntityAppProfileParam> list(List<Long> ids) throws Exception {
        List<EntityAppProfileParam> entityAppProfileParamList = entityAppProfileParamDAO.getEntityAppProfileParamList(ids);
        return entityAppProfileParamList;
    }
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public EntityAppProfileParam get(long id) throws Exception {
        EntityAppProfileParam entityAppProfileParam = entityAppProfileParamDAO.getEntityAppProfileParamByID(id);
        
        return entityAppProfileParam;
    }
    
    /*private EntityAppProfileParam updateEntityAppProfileParam(EntityAppProfileParam entityAppProfileParam){
        entityAppProfileParam.setName("Updated");
        entityAppProfileParamDAO.merge(entityAppProfileParam);
        return entityAppProfileParam;
    }*/
}



