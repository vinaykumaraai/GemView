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

import com.luretechnologies.server.data.dao.PaymentSystemDAO;
import com.luretechnologies.server.data.model.tms.PaymentSystem;
import java.util.List;
import javax.persistence.NoResultException;
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
 *
 * @author developer
 */
@Repository
public class PaymentSystemDAOImpl extends BaseDAOImpl<PaymentSystem, Long> implements PaymentSystemDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentSystemDAO.class);

    @Override
    public PaymentSystem findByRID(String rid) throws PersistenceException {
        try {
            return (PaymentSystem) findByProperty("rid", rid).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("PaymentSystem not found. rid: " + rid, e);
            return null;
        }
    }

    @Override
    public List<PaymentSystem> list(int firstResult, int lastResult) throws PersistenceException {
        return query(criteriaQueryComplex()).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }

    @Override
    public List<PaymentSystem> list(String name, String rid, int firstResult, int lastResult) throws PersistenceException {

        CriteriaQuery<PaymentSystem> q = criteriaQuery();

        Root<PaymentSystem> root = getRoot(q);
        Predicate predicate = null;

        if (name != null) {
            Predicate namePredicate = criteriaBuilder().equal(root.get("name"), name);
            predicate = criteriaBuilder().and(namePredicate);
        }

        if (rid != null) {
            Predicate ridPredicate = criteriaBuilder().equal(root.get("rid"), rid);
            if (predicate != null) {
                predicate.getExpressions().add(ridPredicate);
            } else {
                predicate = ridPredicate;
            }
        }

        q.where(predicate);

        return query(q).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }

    @Override
    public List<PaymentSystem> search(String filter, int firstResult, int lastResult) throws PersistenceException {

        CriteriaBuilder critBuilder = criteriaBuilder();
        CriteriaQuery<PaymentSystem> q = criteriaQuery();
        Root<PaymentSystem> root = getRoot(q);

        Expression<String> upperName = critBuilder.upper((Expression) root.get("name"));
        Expression<String> upperDescription = critBuilder.upper((Expression) root.get("description"));
        Expression<String> upperRid = critBuilder.upper((Expression) root.get("rid"));
        Expression<String> upperTacDefault = critBuilder.upper((Expression) root.get("tacDefault"));
        Expression<String> upperTacDenial = critBuilder.upper((Expression) root.get("tacDenial"));
        Expression<String> upperTacOnline = critBuilder.upper((Expression) root.get("tacOnline"));

        Predicate namePredicate = critBuilder.like(upperName, "%" + filter.toUpperCase() + "%");
        Predicate descriptionPredicate = critBuilder.like(upperDescription, "%" + filter.toUpperCase() + "%");
        Predicate ridPredicate = critBuilder.like(upperRid, "%" + filter.toUpperCase() + "%");
        Predicate tacDefaultPredicate = critBuilder.like(upperTacDefault, "%" + filter.toUpperCase() + "%");
        Predicate tacDenialPredicate = critBuilder.like(upperTacDenial, "%" + filter.toUpperCase() + "%");
        Predicate tacOnlinePredicate = critBuilder.like(upperTacOnline, "%" + filter.toUpperCase() + "%");

        q.where(critBuilder.or(namePredicate, descriptionPredicate, ridPredicate, tacDefaultPredicate, tacDenialPredicate, tacOnlinePredicate));

        return query(q).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }
}
