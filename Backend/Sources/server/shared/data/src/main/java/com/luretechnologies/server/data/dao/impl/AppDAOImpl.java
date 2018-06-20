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


import com.luretechnologies.server.data.dao.AppDAO;
import com.luretechnologies.server.data.model.tms.App;
import com.luretechnologies.server.data.model.tms.AppParam;
import com.luretechnologies.server.data.model.tms.AppProfile;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Vinay
 */
@Repository
public class AppDAOImpl extends BaseDAOImpl<App, Long> implements AppDAO{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AppDAO.class);
    private static final long TYPE_FILE = 1; //type file on AppParamFormat
    
    @Override
    public List<App> getApps(List<Long> IDS)throws PersistenceException{
        List<App> appList = new ArrayList<>();
        for(Long ID : IDS) {
            appList.add(getEntityManager().find(App.class, ID));
        }
        return appList;
    }

    @Override
    public App getAppByID(Long ID) throws PersistenceException{
       /*App app = new App();
       return  getEntityManager().find(App.class, ID);*/
        try {
            CriteriaQuery<App> cq = criteriaQuery();
            Root<App> root = getRoot(cq);   
            //root.fetch("appfileCollection", JoinType.LEFT);
            root.fetch("appprofileCollection", JoinType.LEFT);
            root.fetch("appparamCollection", JoinType.LEFT);
            App app = (App) query(cq.where(criteriaBuilder().equal(root.get("id"), ID))).getSingleResult();
            return app;
        } catch (NoResultException e) {
            LOGGER.info("App not found. id: " + ID, e);
            return null;
        }
        
    }
        
    @Override
    public List<App> list(Long entityId) throws PersistenceException {

        CriteriaBuilder critBuilder = criteriaBuilder();
        CriteriaQuery<App> q = criteriaQuery();
        Root<App> root = getRoot(q);
        
        Predicate ownerIdPredicate = criteriaBuilder().equal(root.get("ownerId"), entityId);
        Predicate activePredicate = criteriaBuilder().equal(root.get("active"), true);
                
        q.where(critBuilder.and(activePredicate, ownerIdPredicate));

        return query(q).getResultList();
    }
    
    @Override
    public List<App> list(int firstResult, int lastResult) throws PersistenceException {
        CriteriaBuilder critBuilder = criteriaBuilder();
        CriteriaQuery<App> q = criteriaQuery();
        Root<App> root = getRoot(q);
        Predicate activePredicate = criteriaBuilder().equal(root.get("active"), true);
        
        q.where(critBuilder.and(activePredicate));
        
        return query(q).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }

    @Override
    public List<App> search(String filter, int firstResult, int lastResult) throws PersistenceException {

        CriteriaBuilder critBuilder = criteriaBuilder();
        CriteriaQuery<App> q = criteriaQuery();
        Root<App> root = getRoot(q);

        Expression<String> upperName = critBuilder.upper((Expression) root.get("name"));
        Expression<String> upperVersion = critBuilder.upper((Expression) root.get("version"));
        Expression<String> upperDescription = critBuilder.upper((Expression) root.get("description"));
        
        Predicate namePredicate = critBuilder.like(upperName, "%" + filter.toUpperCase() + "%");
        Predicate versionPredicate = critBuilder.like(upperVersion, "%" + filter.toUpperCase() + "%");
        Predicate descriptionPredicate = critBuilder.like(upperDescription, "%" + filter.toUpperCase() + "%");
        Predicate activePredicate = criteriaBuilder().equal(root.get("active"), true);
                
        q.where(critBuilder.or(namePredicate, versionPredicate, descriptionPredicate), 
                critBuilder.and(activePredicate));

        return query(q).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }
    
    @Override
    public List<AppParam> searchAppParam(Long appId, String filter, int firstResult, int lastResult) throws PersistenceException {
        try{
            CriteriaQuery<AppParam> cq = criteriaBuilder().createQuery(AppParam.class);
            Root<AppParam> root = cq.from(AppParam.class);

            Predicate filterPredicate = criteriaBuilder().conjunction();

            if (filter != null && !filter.isEmpty()) {
                filterPredicate.getExpressions().add(criteriaBuilder().or(
                    criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("name")), "%" + filter.toUpperCase() + "%"),
                    criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("description")), "%" + filter.toUpperCase() + "%")));
            }

            if(appId != null && appId > 0)
                filterPredicate.getExpressions().add(criteriaBuilder().equal(root.get("appId"), appId));
            
            filterPredicate.getExpressions().add(criteriaBuilder().notEqual(root.<Long>get("appParamFormat"), TYPE_FILE));
            
            cq.where(filterPredicate);

            return query(cq).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
            
        } catch (NoResultException e) {
            LOGGER.info("Search Params return an empty list", e);
            return null;
        }
    }
    
    @Override
    public List<AppParam> searchAppFile(Long appId, String filter, int firstResult, int lastResult) throws PersistenceException {
        try{
            CriteriaQuery<AppParam> cq = criteriaBuilder().createQuery(AppParam.class);
            Root<AppParam> root = cq.from(AppParam.class);

            Predicate filterPredicate = criteriaBuilder().conjunction();

            if (filter != null && !filter.isEmpty()) {
                filterPredicate.getExpressions().add(criteriaBuilder().or(
                    criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("name")), "%" + filter.toUpperCase() + "%"),
                    criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("description")), "%" + filter.toUpperCase() + "%")));
            }

            if(appId != null && appId > 0)
                filterPredicate.getExpressions().add(criteriaBuilder().equal(root.get("appId"), appId));
            
            filterPredicate.getExpressions().add(criteriaBuilder().equal(root.<Long>get("appParamFormat"), TYPE_FILE));
            
            cq.where(filterPredicate);

            return query(cq).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
            
        } catch (NoResultException e) {
            LOGGER.info("Search Files return an empty list", e);
            return null;
        }
    }

    @Override
    public List<AppParam> listAppParam(Long id) throws PersistenceException {
        try {
            CriteriaQuery<AppParam> q = criteriaBuilder().createQuery(AppParam.class);
            Root<AppParam> root = q.from(AppParam.class);

            q.where(criteriaBuilder().and(criteriaBuilder().equal(root.get("appId"), id),
                    criteriaBuilder().notEqual(root.get("appParamFormat").get("id"), TYPE_FILE)));

            return query(q).getResultList();
        } catch (NoResultException e) {
            LOGGER.info("listAppParam return an empty list.", e);
            return null;
        }
    }
    
    @Override
    public List<AppProfile> listAppProfile(Long id) throws PersistenceException {
        try {
            CriteriaQuery<AppProfile> q = criteriaBuilder().createQuery(AppProfile.class);
            Root<AppProfile> root = q.from(AppProfile.class);

            q.where(criteriaBuilder().and(criteriaBuilder().equal(root.get("appId"), id)));

            return query(q).getResultList();
        } catch (NoResultException e) {
            LOGGER.info("listAppProfile return an empty list.", e);
            return null;
        }
    }
    
    @Override
    public List<AppParam> listAppFile(Long id) throws PersistenceException {
        try {
            CriteriaQuery<AppParam> q = criteriaBuilder().createQuery(AppParam.class);
            Root<AppParam> root = q.from(AppParam.class);

            q.where(criteriaBuilder().and(criteriaBuilder().equal(root.get("appId"), id),
                    criteriaBuilder().equal(root.get("appParamFormat").get("id"), TYPE_FILE)));

            return query(q).getResultList();
        } catch (NoResultException e) {
            LOGGER.info("listAppFile return an empty list.", e);
            return null;
        }
    }
    
}
