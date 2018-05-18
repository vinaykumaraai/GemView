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
package com.luretechnologies.server.data.dao.impl;

import com.luretechnologies.server.data.dao.ClientDAO;
import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.Merchant;
import com.luretechnologies.server.data.model.Organization;
import com.luretechnologies.server.data.model.Region;
import com.luretechnologies.server.data.model.payment.Client;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author developer
 */
@Repository
public class ClientDAOImpl extends BaseDAOImpl<Client, Long> implements ClientDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDAO.class);

    @Autowired
    EntityDAO entityDAO;

    @Override
    protected Root getRoot(CriteriaQuery cq) {

        Root<Client> root = cq.from(Client.class);
        root.fetch("entity", JoinType.LEFT);
        root.fetch("addresses", JoinType.LEFT).fetch("state", JoinType.LEFT);
        root.fetch("addresses", JoinType.LEFT).fetch("country", JoinType.LEFT);
        root.fetch("telephones", JoinType.LEFT);

        return root;
    }

    @Override
    public Client findByEmail(String email) throws PersistenceException {
        try {
            CriteriaQuery<Client> cq = criteriaQuery();
            Root<Client> root = getRoot(cq);

            return (Client) query(cq.where(criteriaBuilder().equal(root.get("email"), email))).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Client not found. email: " + email, e);
            return null;
        }
    }

    @Override
    public List<Client> list(Entity entity, int firstResult, int lastResult) throws PersistenceException {
        CriteriaQuery<Client> cq = criteriaQuery();
        Root<Client> root = getRoot(cq);

        cq.where(criteriaBuilder().and(wherePredicate(root, entity).toArray(new Predicate[wherePredicate(root, entity).size()])));
        return query(cq).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }

    @Override
    public List<Client> search(Entity entity, String filter, int firstResult, int lastResult) throws PersistenceException {

        CriteriaQuery<Client> q = criteriaQuery();
        Root<Client> root = getRoot(q);

        q.where(criteriaBuilder().or(
                criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("firstName")), "%" + filter.toUpperCase() + "%"),
                criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("lastName")), "%" + filter.toUpperCase() + "%"),
                criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("middleInitial")), "%" + filter.toUpperCase() + "%"),
                criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("email")), "%" + filter.toUpperCase() + "%"),
                criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("company")), "%" + filter.toUpperCase() + "%")));

        return query(q).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }
    
    private List<Predicate> wherePredicate(Root root, Entity entity) {
        List<Predicate> wherePredicate = new ArrayList<>();
        Predicate entPredicate = criteriaBuilder().disjunction();
        entPredicate.getExpressions().add(criteriaBuilder().equal(root.get("entity").<Long>get("id"), entity.getId()));
        entPredicate.getExpressions().add(criteriaBuilder().like(root.get("entity").<String>get("path"), "%-" + String.valueOf(entity.getId()) + "-%"));
        wherePredicate.add(entPredicate);
        return wherePredicate;
    }

}
