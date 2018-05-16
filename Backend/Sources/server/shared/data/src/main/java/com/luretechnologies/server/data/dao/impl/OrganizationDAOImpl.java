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

import com.luretechnologies.common.enums.EntityTypeEnum;
import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.dao.OrganizationDAO;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.Organization;
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
public class OrganizationDAOImpl extends BaseDAOImpl<Organization, Long> implements OrganizationDAO {

    @Autowired
    EntityDAO entityDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationDAO.class);

    @Override
    protected Root getRoot(CriteriaQuery cq) {

        Root<Organization> root = cq.from(Organization.class);
        root.fetch("parent", JoinType.INNER);
        return root;
    }

    @Override
    public void persist(Organization organization) throws PersistenceException {     
        super.persist(organization);       
        organization.setEntityId(Utils.encodeHashId("ORG", organization.getId())); 
    }

    @Override
    public Organization get(long id) throws PersistenceException {
        try {
            CriteriaQuery<Organization> cq = criteriaQuery();
            Root<Organization> root = getRoot(cq);

            return (Organization) query(cq.where(criteriaBuilder().equal(root.get("id"), id))).getSingleResult();

        } catch (NoResultException e) {
            LOGGER.info("Organization not found. id: " + id, e);
            return null;
        }
    }

    @Override
    public Organization findByOrganizationId(String organizationId) throws PersistenceException {
        try {
            CriteriaQuery<Organization> cq = criteriaQuery();
            Root<Organization> root = getRoot(cq);

            return (Organization) query(cq.where(criteriaBuilder().equal(root.get("id"), Utils.decodeHashId(organizationId)))).getSingleResult();

        } catch (NoResultException e) {
            LOGGER.info("Organization not found. organizationId: " + organizationId, e);
            return null;
        }
    }

    @Override
    public List<Organization> list(Entity entity, int firstResult, int lastResult) throws PersistenceException {

        CriteriaQuery<Organization> cq = criteriaQuery();
        Root<Organization> root = getRoot(cq);
        List<Predicate> wherePredicate = new ArrayList<>();

        //Log in at Organization Level -- Search in  current Organization  
        if (entity instanceof Organization) {
            wherePredicate.add(criteriaBuilder().equal(root.get("id"), entity.getId()));
        } else {
            wherePredicate = wherePredicate(root, entity);
        }

        cq.where(criteriaBuilder().and(wherePredicate.toArray(new Predicate[wherePredicate.size()])));

        return query(cq).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();

    }

    @Override
    public List<Organization> search(Entity entity, String filter, int firstResult, int lastResult) throws PersistenceException {

        CriteriaQuery<Organization> cq = criteriaQuery();
        Root<Organization> root = getRoot(cq);

        List<Predicate> wherePredicate = new ArrayList<>();

        //Log in at Organization Level -- Search in  current Organization  
        if (entity instanceof Organization) {
            wherePredicate.add(criteriaBuilder().equal(root.get("id"), entity.getId()));
        } else {
            wherePredicate = wherePredicate(root, entity);
        }

        Predicate filterPredicate = criteriaBuilder().or(
                criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("name")), "%" + filter.toUpperCase() + "%"),
                criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("description")), "%" + filter.toUpperCase() + "%"));
        wherePredicate.add(filterPredicate);

        cq.where(criteriaBuilder().and(wherePredicate.toArray(new Predicate[wherePredicate.size()])));

        return query(cq).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }

    private List<Predicate> wherePredicate(Root root, Entity entity) {
        List<Predicate> wherePredicate = new ArrayList<>();
        Predicate entPredicate = criteriaBuilder().disjunction();
        entPredicate.getExpressions().add(criteriaBuilder().like(root.<String>get("path"), "%-" + String.valueOf(entity.getId()) + "-%"));
        wherePredicate.add(entPredicate);
        return wherePredicate;
    }
}
