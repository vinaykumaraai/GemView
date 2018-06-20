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

import com.luretechnologies.server.data.model.tms.AppProfile;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceException;
import org.springframework.stereotype.Repository;
import com.luretechnologies.server.data.dao.AppProfileDAO;
import com.luretechnologies.server.data.model.tms.AppParam;
import com.luretechnologies.server.data.model.tms.AppProfileParamValue;
import com.luretechnologies.server.data.model.tms.EntityAppProfile;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Vinay
 */
@Repository
public class AppProfileDAOImpl extends BaseDAOImpl<AppProfile, Long> implements AppProfileDAO{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AppProfileDAO.class);
    private static final long TYPE_FILE = 1; //type file on AppParamFormat
    
    @Override
    public List<AppProfile> getAppProfileList(List<Long> IDS)throws PersistenceException{
        List<AppProfile> appProfileList = new ArrayList<>();
        for(Long ID : IDS) {
            appProfileList.add(getEntityManager().find(AppProfile.class, ID));
        }
        return appProfileList;
    }

    @Override
    public AppProfile getAppProfileByID(Long ID) throws PersistenceException{
        try{
            CriteriaQuery<AppProfile> cq = criteriaQuery();
            Root<AppProfile> root = getRoot(cq);   
            root.fetch("appprofileparamvalueCollection", JoinType.LEFT);
            root.fetch("entityappprofileCollection", JoinType.LEFT);
            root.fetch("entityappprofileCollection", JoinType.LEFT).fetch("entityappprofileparamCollection", JoinType.LEFT);
            //root.fetch("appprofilefilevalueCollection", JoinType.LEFT);
            
            AppProfile appProfile = (AppProfile) query(cq.where(criteriaBuilder().equal(root.get("id"), ID))).getSingleResult();
            return appProfile;
        } catch (NoResultException e) {
            LOGGER.info("AppProfile not found. id: " + ID, e);
            return null;
        }
    }
    
    @Override
    public List<AppParam> getAppParamList(Long id) throws PersistenceException {
        List<AppParam> appParamList = new ArrayList<>();
        try {
            CriteriaQuery<AppProfile> cq = criteriaQuery();
            Root<AppProfile> root = getRoot(cq); 
            
            AppProfile appProfile = (AppProfile) query(cq.where(criteriaBuilder().equal(root.get("id"), id))).getSingleResult();
            for (AppProfileParamValue existentAppProfileParamValue : appProfile.getAppProfileParamValueCollection()) {
                AppParam appParam = getEntityManager().find(AppParam.class, existentAppProfileParamValue.getAppParamId());
                if(!appParam.getAppParamFormat().getId().equals(TYPE_FILE))
                    appParamList.add(appParam);
            }
        } catch (NoResultException e) {
            LOGGER.info("AppParam not found. id: " + id, e);
            return null;
        }
        return appParamList;
    }
    
    @Override
    public List<AppParam> getAppFileList(Long id) throws PersistenceException {
        List<AppParam> appParamList = new ArrayList<>();
        try {
            CriteriaQuery<AppProfile> cq = criteriaQuery();
            Root<AppProfile> root = getRoot(cq); 
            
            AppProfile appProfile = (AppProfile) query(cq.where(criteriaBuilder().equal(root.get("id"), id))).getSingleResult();
            for (AppProfileParamValue existentAppProfileParamValue : appProfile.getAppProfileParamValueCollection()) {
                AppParam appParam = getEntityManager().find(AppParam.class, existentAppProfileParamValue.getAppParamId());
                if(appParam.getAppParamFormat().getId().equals(TYPE_FILE))
                    appParamList.add(appParam);
            }
        } catch (NoResultException e) {
            LOGGER.info("AppParam not found. id: " + id, e);
            return null;
        }
        return appParamList;
    }
    
    @Override
    public List<AppProfile> getAppProfileList(Long appId, Long entityId) throws PersistenceException {
        List<AppProfile> appProfileListbyEntity = new ArrayList<>();
        try {
            CriteriaQuery<AppProfile> cq = criteriaQuery();
            Root<AppProfile> root = getRoot(cq);

            cq.where(criteriaBuilder().and(criteriaBuilder().equal(root.get("appId"), appId)));
            List<AppProfile> appProfileList = query(cq).getResultList();
            for (AppProfile appProfile : appProfileList) {
                for (EntityAppProfile entityAppProfile : appProfile.getEntityAppProfileCollection()) {
                    if(entityAppProfile.getEntity().getId() == entityId)
                        appProfileListbyEntity.add(getAppProfileByID(entityAppProfile.getAppProfileId()));
                }
            }
            return appProfileListbyEntity;
            
        } catch (NoResultException e) {
            LOGGER.info("getAppProfileList return an empty list", e);
            return null;
        }
    }
}
