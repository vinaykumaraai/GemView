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

import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.dao.UserDAO;
import com.luretechnologies.server.data.model.Role;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.User;
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
import org.springframework.transaction.annotation.Transactional;

/**
 *
 *
 * @author developer
 */
@Repository
public class UserDAOImpl extends BaseDAOImpl<User, Long> implements UserDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);

    @Autowired
    EntityDAO entityDAO;
    
    @Override
    protected Root getRoot(CriteriaQuery cq) {

        Root<User> root = cq.from(User.class);
        root.fetch("entity", JoinType.LEFT);
        root.fetch("role", JoinType.LEFT);

        return root;
    }

    @Override
    public User get(Long userId) throws PersistenceException {
        try {
            CriteriaQuery<User> cq = criteriaQuery();
            Root<User> root = getRoot(cq);

            return (User) query(cq.where(criteriaBuilder().equal(root.get("id"), userId))).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("User not found. userId: " + userId, e);
            return null;
        }
    }
    
    @Override
    public User getByUserName(String username) throws PersistenceException {
        try {
            CriteriaQuery<User> cq = criteriaQuery();
            Root<User> root = getRoot(cq);

            return (User) query(cq.where(criteriaBuilder().like(root.<String>get("username"), username))).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("User not found. username: " + username, e);
            return null;
        }
    }

    @Override
    @Transactional
    public User findByUsername(String username) throws PersistenceException {
        try {
            CriteriaQuery<User> cq = criteriaQuery();
            Root<User> root = getRoot(cq);

            return (User) query(cq.where(criteriaBuilder().equal(root.get("username"), username))).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("User not found. userName: " + username, e);
            return null;
        }
    }
    
    @Override
    public List<User> list(Entity entity) throws PersistenceException {

        CriteriaQuery<User> cq = criteriaQuery();
        Root<User> root = getRoot(cq);

        List<Predicate> predicates = wherePredicate(root, entity);
        cq.where(criteriaBuilder().and(predicates.toArray(new Predicate[predicates.size()])));

        return query(cq).getResultList();
    }

    @Override
    public List<User> list(Entity entity, int firstResult, int lastResult) throws PersistenceException {

        CriteriaQuery<User> cq = criteriaQuery();
        Root<User> root = getRoot(cq);

        List<Predicate> predicates = wherePredicate(root, entity);
        cq.where(criteriaBuilder().and(predicates.toArray(new Predicate[predicates.size()])));

        return query(cq).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }

    @Override
    public List<User> search(Entity entity, String filter, int firstResult, int lastResult) throws PersistenceException {
        try {
            CriteriaQuery<User> cq = criteriaQuery();
            Root<User> root = getRoot(cq);

            Predicate filterPredicate = criteriaBuilder().or(
                    criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("username")), "%" + filter.toUpperCase() + "%"),
                    criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("firstName")), "%" + filter.toUpperCase() + "%"),
                    criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("lastName")), "%" + filter.toUpperCase() + "%"));

            List<Predicate> wherePredicate = wherePredicate(root, entity);
            wherePredicate.add(filterPredicate);

            cq.where(criteriaBuilder().and(wherePredicate.toArray(new Predicate[wherePredicate.size()])));

            return query(cq).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();

        } catch (NoResultException e) {
            LOGGER.info("Search User return an empty list", e);
            return null;
        }
    }

    @Override
    public List<User> list(Entity entity, String username, String firstname, String lastname, Boolean active, int firstResult, int lastResult) throws PersistenceException {
        try {

            CriteriaQuery<User> cq = criteriaQuery();
            Root<User> root = getRoot(cq);
            Predicate filterPredicate = null;

            if (username != null) {
                filterPredicate = criteriaBuilder().and(criteriaBuilder().equal(root.get("username"), username));
            }
            if (firstname != null) {
                if (filterPredicate != null) {
                    filterPredicate.getExpressions().add(criteriaBuilder().equal(root.get("firstName"), firstname));
                } else {
                    filterPredicate = criteriaBuilder().equal(root.get("firstName"), firstname);
                }
            }
            if (lastname != null) {
                if (filterPredicate != null) {
                    filterPredicate.getExpressions().add(criteriaBuilder().equal(root.get("lastName"), lastname));
                } else {
                    filterPredicate = criteriaBuilder().equal(root.get("lastName"), lastname);
                }
            }
            if (active != null) {
                if (filterPredicate != null) {
                    filterPredicate.getExpressions().add(criteriaBuilder().equal(root.get("active"), active));
                } else {
                    filterPredicate = criteriaBuilder().equal(root.get("active"), active);
                }
            }

            List<Predicate> wherePredicate = wherePredicate(root, entity);
            wherePredicate.add(filterPredicate);
            cq.where(criteriaBuilder().and(wherePredicate.toArray(new Predicate[wherePredicate.size()])));

            return query(cq).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();

        } catch (NoResultException e) {
            LOGGER.info("Search User return an empty list", e);
            return null;
        }
    }

    @Override
    public User validateCredentials(String username, String password) throws PersistenceException {

        try {

            CriteriaQuery<User> q = criteriaQuery();
            Root<User> root = getRoot(q);

            Predicate predicate = criteriaBuilder().and(
                    criteriaBuilder().equal(root.get("username"), username),
                    criteriaBuilder().equal(root.get("password"), password),
                    criteriaBuilder().equal(root.get("active"), true));

            q.where(predicate);

            return (User) query(q).getSingleResult();

        } catch (NoResultException e) {
            LOGGER.info("validateCredentials is null. username: " + username, e);
            return null;
        }
    }
    
    private List<Predicate> wherePredicate(Root root, Entity entity) {
        List<Predicate> wherePredicate = new ArrayList<>();
        Predicate entPredicate = criteriaBuilder().disjunction();
                
        entPredicate.getExpressions().add(criteriaBuilder().equal(root.get("entity").<Long>get("id"), entity.getId()));
        entPredicate.getExpressions().add(criteriaBuilder().like(root.get("entity").<String>get("path"), "%-" + String.valueOf(entity.getId()) + "-%"));
        wherePredicate.add(entPredicate);
        
        Expression<String> roleName = criteriaBuilder().upper((Expression) root.get("role").get("name"));
        wherePredicate.add(criteriaBuilder().and(criteriaBuilder().notLike(roleName, "%" + Role.SUPER_ADMIN + "%")));
        
        return wherePredicate;
    }
    
    public User findUserByEmailId(String emailId) {
        System.out.println("email id ..." + emailId);
        return (User) findByProperty("email", emailId).getSingleResult();
    }
}
