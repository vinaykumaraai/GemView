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

import com.luretechnologies.server.data.model.tms.AppProfileParamValue;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceException;
import org.springframework.stereotype.Repository;
import com.luretechnologies.server.data.dao.AppProfileParamValueDAO;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Vinay
 */
@Repository
public class AppProfileParamValueDAOImpl extends BaseDAOImpl<AppProfileParamValue, Long> implements AppProfileParamValueDAO{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AppProfileParamValueDAOImpl.class);
    
    @Override
    public List<AppProfileParamValue> getAppProfileParamValueList(List<Long> IDS)throws PersistenceException{
        List<AppProfileParamValue> appProfileParamValueList = new ArrayList<>();
        for(Long ID : IDS) {
            appProfileParamValueList.add(getEntityManager().find(AppProfileParamValue.class, ID));
        }
        return appProfileParamValueList;
    }

    /**
     *
     * @param ID
     * @return
     * @throws PersistenceException
     */
    @Override
    public AppProfileParamValue getAppProfileParamValueByID(Long ID) throws PersistenceException{
       return  getEntityManager().find(AppProfileParamValue.class, ID);
        
    }
    
    @Override
    public Integer doForceUpdate(Integer value) throws PersistenceException{
       Integer forceUpdate = null;
        if(value == 0){
            forceUpdate = 1;
       } 
       return  forceUpdate;
        
    }
    
    @Override
    public AppProfileParamValue findByAppProfileAndAppParam(Long appProfileId, Long appParamId) {
        try {
            CriteriaQuery q = criteriaBuilder().createQuery(AppProfileParamValue.class);
            Root root = q.from(AppProfileParamValue.class);
            q.where(criteriaBuilder().and(criteriaBuilder().equal(root.get("appProfileId"), appProfileId),
                    criteriaBuilder().equal(root.get("appParamId"), appParamId)));

            return (AppProfileParamValue) query(q).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("AppProfileParamValue findByAppProfileAndAppParam.", e);
            return null;
        }
    }
}
