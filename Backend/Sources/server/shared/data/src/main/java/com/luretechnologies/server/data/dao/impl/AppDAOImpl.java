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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
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
       App app = new App();
       return  getEntityManager().find(App.class, ID);
        
    }
    
    @Override
    public List<App> list(String name, Boolean active, int firstResult, int lastResult) throws PersistenceException {

        CriteriaQuery<App> q = criteriaQuery();
        Root<App> root = getRoot(q);
        Predicate predicate = null;

        if (name != null) {
            Predicate namePredicate = criteriaBuilder().equal(root.get("name"), name);
            predicate = criteriaBuilder().and(namePredicate);
        }

        if (active != null) {
            short appAvailable = 0;
            if(active) 
                appAvailable = (short) 1;  
            Predicate activePredicate = criteriaBuilder().equal(root.get("active"), appAvailable);
            if (predicate != null) {
                predicate.getExpressions().add(activePredicate);
            } else {
                predicate = activePredicate;
            }
        }

        q.where(predicate);

        return query(q).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }
    
    @Override
    public List<App> list(int firstResult, int lastResult) throws PersistenceException {
        return query(criteriaQueryComplex()).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }

    @Override
    public List<App> search(String filter, Boolean active, int firstResult, int lastResult) throws PersistenceException {

        CriteriaBuilder critBuilder = criteriaBuilder();
        CriteriaQuery<App> q = criteriaQuery();
        Root<App> root = getRoot(q);

        Expression<String> upperName = critBuilder.upper((Expression) root.get("name"));
        Expression<String> upperVersion = critBuilder.upper((Expression) root.get("version"));
        
        Predicate namePredicate = critBuilder.like(upperName, "%" + filter.toUpperCase() + "%");
        Predicate versionPredicate = critBuilder.like(upperVersion, "%" + filter.toUpperCase() + "%");
        Predicate activePredicate = null;
        
        if (active != null){
            short appAvailable = 0;
            if(active) 
                appAvailable = (short) 1;            
            activePredicate = criteriaBuilder().equal(root.get("active"), appAvailable);
        }
        
        if(activePredicate != null)
            q.where(critBuilder.or(namePredicate, versionPredicate, activePredicate));
        else
            q.where(critBuilder.or(namePredicate, versionPredicate));

        return query(q).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }
    
}
