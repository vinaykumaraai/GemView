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

import com.luretechnologies.server.data.dao.AppParamDAO;

import com.luretechnologies.server.data.model.tms.AppParam;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class AppParamDAOImpl extends BaseDAOImpl<AppParam, Long> implements AppParamDAO{
    
    private static final long TYPE_FILE = 1; //type file on AppParamFormat
    
    @Override
    public List<AppParam> getAppParamList(int firstResult, int lastResult)throws PersistenceException{
        return query(criteriaQueryComplex()).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }

    /**
     *
     * @param ID
     * @return
     * @throws PersistenceException
     */
    @Override
    public AppParam getAppParamByID(Long ID) throws PersistenceException{
       return  getEntityManager().find(AppParam.class, ID);
        
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
    public List<AppParam> list(int firstResult, int lastResult) throws PersistenceException {
        return query(criteriaQueryComplex()).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }
    
    
    @Override
    public List<AppParam> search(String filter, int firstResult, int lastResult) throws PersistenceException {

        CriteriaBuilder critBuilder = criteriaBuilder();
        CriteriaQuery<AppParam> q = criteriaQuery();
        Root<AppParam> root = getRoot(q);

        Expression<String> upperName = critBuilder.upper((Expression) root.get("name"));
        Expression<String> upperDescription = critBuilder.upper((Expression) root.get("description"));
        
        Predicate namePredicate = critBuilder.like(upperName, "%" + filter.toUpperCase() + "%");
        Predicate descriptionPredicate = critBuilder.like(upperDescription, "%" + filter.toUpperCase() + "%");
        Predicate typeFilePredicate = criteriaBuilder().notEqual(root.get("appParamFormat").get("id"), TYPE_FILE);

        q.where(critBuilder.or(namePredicate, descriptionPredicate),
                criteriaBuilder().and(typeFilePredicate));

        return query(q).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }
}
