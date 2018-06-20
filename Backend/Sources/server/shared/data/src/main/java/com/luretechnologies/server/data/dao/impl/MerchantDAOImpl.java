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

import com.luretechnologies.common.enums.ModeEnum;
import com.luretechnologies.common.enums.OperationEnum;
import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.dao.MerchantDAO;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.Merchant;
import com.luretechnologies.server.data.model.payment.Host;
import com.luretechnologies.server.data.model.payment.MerchantHost;
import com.luretechnologies.server.data.model.payment.MerchantHostModeOperation;
import com.luretechnologies.server.data.model.payment.MerchantHostSetting;
import com.luretechnologies.server.data.model.payment.MerchantHostSettingValue;
import com.luretechnologies.server.data.model.payment.MerchantSettingValue;
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
public class MerchantDAOImpl extends BaseDAOImpl<Merchant, Long> implements MerchantDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(MerchantDAO.class);

    @Autowired
    EntityDAO entityDAO;

    @Override
    protected Root getRoot(CriteriaQuery cq) {

        Root<Merchant> root = cq.from(Merchant.class);
        root.fetch("parent", JoinType.INNER);

        return root;
    }

    @Override
    public void persist(Merchant merchant) throws PersistenceException {
        super.persist(merchant);
        merchant.setEntityId(Utils.encodeHashId("MER", merchant.getId()));
    }

    @Override
    public Merchant get(long id) throws PersistenceException {
        try {
            CriteriaQuery<Merchant> cq = criteriaQuery();
            Root<Merchant> root = getRoot(cq);
            root.fetch("merchantHosts", JoinType.LEFT).fetch("host", JoinType.LEFT);
            root.fetch("merchantHosts", JoinType.LEFT).fetch("merchantHostSettingValues", JoinType.LEFT).fetch("setting", JoinType.LEFT);
            root.fetch("addresses", JoinType.LEFT).fetch("state", JoinType.LEFT);
            root.fetch("addresses", JoinType.LEFT).fetch("country", JoinType.LEFT);
            root.fetch("telephones", JoinType.LEFT);
            root.fetch("merchantHostModeOperations", JoinType.LEFT);
            root.fetch("merchantSettingValues", JoinType.LEFT);

            return (Merchant) query(cq.where(criteriaBuilder().equal(root.get("id"), id))).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Merchant not found. id: " + id, e);
            return null;
        }
    }

    @Override
    public Merchant findByMerchantId(String merchantId) throws PersistenceException {
        try {
            CriteriaQuery<Merchant> cq = criteriaQuery();
            Root<Merchant> root = getRoot(cq);
            root.fetch("merchantHosts", JoinType.LEFT).fetch("host", JoinType.LEFT);
            root.fetch("merchantHosts", JoinType.LEFT).fetch("merchantHostSettingValues", JoinType.LEFT).fetch("setting", JoinType.LEFT);
            root.fetch("merchantSettingValues", JoinType.LEFT);
            root.fetch("addresses", JoinType.LEFT).fetch("state", JoinType.LEFT);
            root.fetch("addresses", JoinType.LEFT).fetch("country", JoinType.LEFT);
            root.fetch("telephones", JoinType.LEFT);
            root.fetch("merchantHostModeOperations", JoinType.LEFT);

            return (Merchant) query(cq.where(criteriaBuilder().equal(root.get("id"), Utils.decodeHashId(merchantId)))).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Merchant not found. merchantId: " + merchantId, e);
            return null;
        }
    }

    @Override
    public List<Merchant> list(Entity entity, int firstResult, int lastResult) throws PersistenceException {
        try {
            CriteriaQuery<Merchant> cq = criteriaQuery();
            Root<Merchant> root = getRoot(cq);
            List<Predicate> wherePredicate = new ArrayList<>();

            //Log in at Merchant Level -- Search in current merchant
            if (entity instanceof Merchant) {
                wherePredicate.add(criteriaBuilder().equal(root.get("id"), entity.getId()));
            } else {
                wherePredicate = wherePredicate(root, entity);
            }
            
            wherePredicate.add(criteriaBuilder().equal(root.<Boolean>get("active"), true));
            
            cq.where(criteriaBuilder().and(wherePredicate.toArray(new Predicate[wherePredicate.size()])));

            return query(cq).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
        } catch (NoResultException e) {
            LOGGER.info("List Merchant return an empty list.", e);
            return null;
        }
    }

    @Override
    public List<Merchant> search(Entity entity, String filter, int firstResult, int lastResult) throws PersistenceException {
        try {
            CriteriaQuery<Merchant> cq = criteriaQuery();
            Root<Merchant> root = getRoot(cq);
            List<Predicate> wherePredicate = new ArrayList<>();

            //Log in at Merchant Level -- Search in current merchant
            if (entity instanceof Merchant) {
                wherePredicate.add(criteriaBuilder().equal(root.get("id"), entity.getId()));
            } else {
                wherePredicate = wherePredicate(root, entity);
            }

            Expression<String> upperName = criteriaBuilder().upper((Expression) root.get("name"));
            Expression<String> upperDescription = criteriaBuilder().upper((Expression) root.get("description"));
            Predicate filterPredicate = criteriaBuilder().or(criteriaBuilder().like(upperName, "%" + filter.toUpperCase() + "%"), criteriaBuilder().like(upperDescription, "%" + filter.toUpperCase() + "%"));
            
            wherePredicate.add(criteriaBuilder().equal(root.<Boolean>get("active"), true));
            wherePredicate.add(filterPredicate);

            cq.where(criteriaBuilder().and(wherePredicate.toArray(new Predicate[wherePredicate.size()])));

            return query(cq).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
        } catch (NoResultException e) {
            LOGGER.info("Search Merchant return an empty list.", e);
            return null;
        }
    }

    @Override
    public List<MerchantHost> listHosts(String merchantId) throws PersistenceException {
        try {
            CriteriaQuery<MerchantHost> cq = criteriaBuilder().createQuery(MerchantHost.class);
            Root<MerchantHost> root = cq.from(MerchantHost.class);
            root.fetch("host", JoinType.LEFT);
            root.fetch("merchantHostSettingValues", JoinType.LEFT).fetch("setting", JoinType.LEFT);

            cq.where(criteriaBuilder().and(criteriaBuilder().equal(root.get("merchant").get("id"), Utils.decodeHashId(merchantId))));

            return query(cq).getResultList();
        } catch (NoResultException e) {
            LOGGER.info("listHost return an empty list.", e);
            return null;
        }
    }

    @Override
    public List<MerchantHostSettingValue> listHostSettings(String merchantId, long hostId) throws PersistenceException {
        try {
            CriteriaQuery<MerchantHostSettingValue> q = criteriaBuilder().createQuery(MerchantHostSettingValue.class);
            Root<MerchantHostSettingValue> root = q.from(MerchantHostSettingValue.class);
            root.fetch("merchantHost", JoinType.INNER);
            root.fetch("setting", JoinType.LEFT);

            q.where(criteriaBuilder().and(criteriaBuilder().equal(root.get("merchantHost").get("merchant").get("id"), Utils.decodeHashId(merchantId)), criteriaBuilder().equal(root.get("merchantHost").get("host").get("id"), hostId)));

            return query(q).getResultList();
        } catch (NoResultException e) {
            LOGGER.info("listHost return an empty list.", e);
            return null;
        }
    }

    @Override
    public MerchantHost findByMerchantHost(String merchantId, long hostId) throws PersistenceException {
        try {
            Root root = criteriaQuery().from(MerchantHost.class);
            criteriaQuery().select(root);

            Predicate predicate = criteriaBuilder().and(criteriaBuilder().equal(root.get("host").get("id"), hostId));
            predicate.getExpressions().add(criteriaBuilder().equal(root.get("merchant").get("id"), Utils.decodeHashId(merchantId)));

            criteriaQuery().where(predicate);
            return (MerchantHost) query(criteriaQuery()).getSingleResult();

        } catch (NoResultException e) {
            LOGGER.info("findByMerchantHost return null ", e);
            return null;
        }
    }

    @Override
    public MerchantHostSettingValue findByMerchantHostAndSetting(String merchantId, long hostId, MerchantHostSetting merchantHostSetting) throws PersistenceException {
        try {

            CriteriaQuery criteriaQuery = criteriaQuery();
            Root root = criteriaQuery.from(MerchantHostSettingValue.class);
            criteriaQuery.select(root);

            Predicate predicate = criteriaBuilder().and(criteriaBuilder().equal(root.get("merchantHost").get("host").get("id"), hostId));
            predicate.getExpressions().add(criteriaBuilder().equal(root.get("merchantHost").get("merchant").get("id"), Utils.decodeHashId(merchantId)));
            predicate.getExpressions().add(criteriaBuilder().equal(root.get("setting"), merchantHostSetting));
            criteriaQuery.where(predicate);

            return (MerchantHostSettingValue) query(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("findByMerchantHostAndSetting return null ", e);
            return null;
        }
    }

    @Override
    public List<MerchantSettingValue> listMerchantSettings(String merchantId) throws PersistenceException {
        try {
            CriteriaQuery<MerchantSettingValue> q = criteriaBuilder().createQuery(MerchantSettingValue.class);
            Root<MerchantSettingValue> root = q.from(MerchantSettingValue.class);

            q.where(criteriaBuilder().and(criteriaBuilder().equal(root.get("merchant").get("id"), Utils.decodeHashId(merchantId))));

            return query(q).getResultList();
        } catch (NoResultException e) {
            LOGGER.info("listMerchantSettings return an empty list.", e);
            return null;
        }
    }

    @Override
    public Host getHostByModeOperation(String merchantId, ModeEnum mode, OperationEnum operation) throws PersistenceException {
        try {
            CriteriaQuery<MerchantHostModeOperation> q = criteriaBuilder().createQuery(MerchantHostModeOperation.class);
            Root<MerchantHostModeOperation> root = q.from(MerchantHostModeOperation.class);
            root.fetch("merchant", JoinType.LEFT);            
            root.fetch("hostModeOperation", JoinType.LEFT).fetch("host", JoinType.LEFT);
            
            Predicate predicate = criteriaBuilder().and(criteriaBuilder().equal(root.get("merchant").get("entityId"), merchantId));
            predicate.getExpressions().add(criteriaBuilder().equal(root.get("hostModeOperation").get("mode"), mode.getId()));
            predicate.getExpressions().add(criteriaBuilder().equal(root.get("hostModeOperation").get("operation"), operation.getId()));

            q.where(predicate);

            MerchantHostModeOperation merchantHostModeOperation = (MerchantHostModeOperation) query(q).getSingleResult();

            return merchantHostModeOperation.getHostModeOperation().getHost();
        } catch (Exception e) {
            LOGGER.info("getHostByModeOperation error.", e);            
        }
        return null;
    }

    private List<Predicate> wherePredicate(Root root, Entity entity) {
        List<Predicate> wherePredicate = new ArrayList<>();
        Predicate entPredicate = criteriaBuilder().disjunction();
        entPredicate.getExpressions().add(criteriaBuilder().like(root.<String>get("path"), "%-" + String.valueOf(entity.getId()) + "-%"));
        wherePredicate.add(entPredicate);
        return wherePredicate;
    }
}
