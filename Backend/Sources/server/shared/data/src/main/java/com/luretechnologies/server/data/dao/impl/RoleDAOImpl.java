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

import com.luretechnologies.server.data.dao.RoleDAO;
import com.luretechnologies.server.data.model.Role;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author developer
 */
@Repository
public class RoleDAOImpl extends BaseDAOImpl<Role, Long> implements RoleDAO {
    
    @Override
    public List<Role> list(int firstResult, int lastResult) throws PersistenceException {
        CriteriaBuilder critBuilder = criteriaBuilder();
        CriteriaQuery<Role> q = criteriaQuery();
        Root<Role> root = getRoot(q);

        Expression<String> upperName = critBuilder.upper((Expression) root.get("name"));
        Predicate namePredicate = critBuilder.notLike(upperName, "%" + Role.SUPER_ADMIN + "%");
        
        return query(q.where(namePredicate)).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }

    @Override
    public List<Role> search(String filter, int firstResult, int lastResult) throws PersistenceException {

        CriteriaBuilder critBuilder = criteriaBuilder();
        CriteriaQuery<Role> q = criteriaQuery();
        Root<Role> root = getRoot(q);

        Expression<String> upperName = critBuilder.upper((Expression) root.get("name"));

        Expression<String> upperDescription = critBuilder.upper((Expression) root.get("description"));

        Predicate namePredicate = critBuilder.like(upperName, "%" + filter.toUpperCase() + "%");
        Predicate descriptionPredicate = critBuilder.like(upperDescription, "%" + filter.toUpperCase() + "%");
        Predicate notAdminPredicate = critBuilder.notLike(upperName, "%" + Role.SUPER_ADMIN + "%");

        q.where(critBuilder.or(namePredicate, descriptionPredicate), critBuilder.and(notAdminPredicate));

        return query(q).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }

//    @Override
//    public List<Role> search(String filter, int firstResult, int maxResult) throws PersistenceException {
//        Criteria criteria = createCriteria();
//
//        if (firstResult >= 0) {
//            criteria.setFirstResult(firstResult);
//            criteria.setMaxResults(maxResult);
//        }
//
//        if (filter != null && !filter.isEmpty()) {
//            Disjunction disjunction = (Disjunction) Restrictions.disjunction()
//                    .add(Restrictions.like("name", filter, MatchMode.ANYWHERE).ignoreCase())
//                    .add(Restrictions.like("description", filter, MatchMode.ANYWHERE).ignoreCase());
//            criteria.add(disjunction);
//        }
//
//        return criteria.list();
//    }
}