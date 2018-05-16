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

import com.luretechnologies.server.data.dao.PaymentProductDAO;
import com.luretechnologies.server.data.model.tms.PaymentProduct;
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
public class PaymentProductDAOImpl extends BaseDAOImpl<PaymentProduct, Long> implements PaymentProductDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProductDAO.class);

    @Override
    public PaymentProduct findByAID(String aid) throws PersistenceException {
        try {
            return (PaymentProduct) findByProperty("aid", aid).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("PaymentProduct not found. aid: " + aid, e);
            return null;
        }
    }

    @Override
    public List<PaymentProduct> list(int firstResult, int lastResult) throws PersistenceException {
        return query(criteriaQueryComplex()).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }

    @Override
    public List<PaymentProduct> list(String name, String aid, int firstResult, int lastResult) throws PersistenceException {

        CriteriaQuery<PaymentProduct> q = criteriaQuery();
        Root<PaymentProduct> root = getRoot(q);
        Predicate predicate = null;

        if (name != null) {
            Predicate namePredicate = criteriaBuilder().equal(root.get("name"), name);
            predicate = criteriaBuilder().and(namePredicate);
        }

        if (aid != null) {
            Predicate aidPredicate = criteriaBuilder().equal(root.get("aid"), aid);
            if (predicate != null) {
                predicate.getExpressions().add(aidPredicate);
            } else {
                predicate = aidPredicate;
            }
        }

        q.where(predicate);

        return query(q).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }

    @Override
    public List<PaymentProduct> search(String filter, int firstResult, int lastResult) throws PersistenceException {

        CriteriaBuilder critBuilder = criteriaBuilder();
        CriteriaQuery<PaymentProduct> q = criteriaQuery();
        Root<PaymentProduct> root = getRoot(q);

        Expression<String> upperName = critBuilder.upper((Expression) root.get("name"));
        Expression<String> upperDescription = critBuilder.upper((Expression) root.get("description"));
        Expression<String> upperAid = critBuilder.upper((Expression) root.get("aid"));
        Expression<String> upperTerminalAppLabel = critBuilder.upper((Expression) root.get("terminalAppLabel"));

        Predicate namePredicate = critBuilder.like(upperName, "%" + filter.toUpperCase() + "%");
        Predicate descriptionPredicate = critBuilder.like(upperDescription, "%" + filter.toUpperCase() + "%");
        Predicate AidPredicate = critBuilder.like(upperAid, "%" + filter.toUpperCase() + "%");
        Predicate TerminalAppLabelPredicate = critBuilder.like(upperTerminalAppLabel, "%" + filter.toUpperCase() + "%");

        q.where(critBuilder.or(namePredicate, descriptionPredicate, AidPredicate, TerminalAppLabelPredicate));

        return query(q).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }
}
