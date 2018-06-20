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

import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.dao.TerminalDAO;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.Merchant;
import com.luretechnologies.server.data.model.Terminal;
import com.luretechnologies.server.data.model.payment.Host;
import com.luretechnologies.server.data.model.payment.TerminalHost;
import com.luretechnologies.server.data.model.payment.TerminalHostSetting;
import com.luretechnologies.server.data.model.payment.TerminalHostSettingValue;
import com.luretechnologies.server.data.model.payment.TerminalSettingValue;
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
import org.springframework.util.StringUtils;

/**
 *
 *
 * @author developer
 */
@Repository
public class TerminalDAOImpl extends BaseDAOImpl<Terminal, Long> implements TerminalDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TerminalDAO.class);

    @Autowired
    EntityDAO entityDAO;

    @Override
    protected Root getRoot(CriteriaQuery cq) {
        Root<Terminal> root = cq.from(Terminal.class);
        root.fetch("parent", JoinType.INNER);
        root.fetch("model", JoinType.LEFT);
        root.fetch("scheduleGroup", JoinType.LEFT);
        // root.fetch("keyBlock", JoinType.LEFT);
        return root;
    }

    @Override
    public void persist(Terminal terminal) throws PersistenceException {
        super.persist(terminal);
        terminal.setEntityId(Utils.encodeHashId("TER", terminal.getId()));
        if (StringUtils.isEmpty(terminal.getSerialNumber())) {
            terminal.setSerialNumber(terminal.getEntityId());
        }
    }

    @Override
    public Terminal get(long id) throws PersistenceException {
        try {
            CriteriaQuery<Terminal> cq = criteriaQuery();
            Root<Merchant> root = getRoot(cq);
            root.fetch("terminalHosts", JoinType.LEFT).fetch("host", JoinType.LEFT);
            root.fetch("terminalHosts", JoinType.LEFT).fetch("terminalHostSettingValues", JoinType.LEFT).fetch("setting", JoinType.LEFT);
            root.fetch("terminalSettingValues", JoinType.LEFT);

            return (Terminal) query(cq.where(criteriaBuilder().equal(root.get("id"), id))).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Terminal not found. id: " + id, e);
            return null;
        }
    }

    @Override
    public Terminal findBySerialNumber(String serialNumber) throws PersistenceException {
        try {
            CriteriaQuery<Terminal> cq = criteriaQuery();
            Root<Terminal> root = getRoot(cq);

            return (Terminal) query(cq.where(criteriaBuilder().equal(root.get("serialNumber"), serialNumber))).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Terminal not found. serialNumber: " + serialNumber, e);
            return null;
        }
    }

    @Override
    public Terminal findByTerminalId(String terminalId) throws PersistenceException {
        try {
            CriteriaQuery<Terminal> cq = criteriaQuery();
            Root<Terminal> root = getRoot(cq);
            root.fetch("terminalHosts", JoinType.LEFT).fetch("host", JoinType.LEFT);
            root.fetch("terminalHosts", JoinType.LEFT).fetch("terminalHostSettingValues", JoinType.LEFT).fetch("setting", JoinType.LEFT);
            root.fetch("terminalSettingValues", JoinType.LEFT);

            return (Terminal) query(cq.where(criteriaBuilder().equal(root.get("id"), Utils.decodeHashId(terminalId)))).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Terminal not found. terminalId: " + terminalId, e);
            return null;
        }
    }

    @Override
    public List<Terminal> list(Entity entity, int firstResult, int lastResult) throws PersistenceException {
        try {
            CriteriaQuery<Terminal> cq = criteriaQuery();
            Root<Terminal> root = getRoot(cq);

            cq.where(criteriaBuilder().and(wherePredicate(root, entity).toArray(new Predicate[wherePredicate(root, entity).size()])));

            return query(cq).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
        } catch (NoResultException e) {
            LOGGER.info("List Terminal return an empty list", e);
            return null;
        }
    }

    @Override
    public List<Terminal> list(Entity entity, String name, String serialNumber, Boolean active) throws PersistenceException {
        try {
            CriteriaQuery<Terminal> cq = criteriaQuery();
            Root<Terminal> root = getRoot(cq);

            Predicate filerPredicate = null;

            if (name != null) {
                filerPredicate = criteriaBuilder().and(criteriaBuilder().equal(root.get("name"), name));
            }
            if (serialNumber != null) {
                if (filerPredicate != null) {
                    filerPredicate.getExpressions().add(criteriaBuilder().equal(root.get("serialNumber"), serialNumber));
                } else {
                    filerPredicate = criteriaBuilder().equal(root.get("serialNumber"), serialNumber);
                }
            }
            if (active != null) {
                if (filerPredicate != null) {
                    filerPredicate.getExpressions().add(criteriaBuilder().equal(root.get("active"), active));
                } else {
                    filerPredicate = criteriaBuilder().equal(root.get("active"), active);
                }
            }

            // activate
            if (filerPredicate != null) {
                filerPredicate.getExpressions().add(criteriaBuilder().equal(root.<Boolean>get("active"), true));
            } else {
                filerPredicate = criteriaBuilder().equal(root.<Boolean>get("active"), true);
            }

            List<Predicate> wherePredicate = wherePredicate(root, entity);
            wherePredicate.add(filerPredicate);

            cq.where(criteriaBuilder().and(wherePredicate.toArray(new Predicate[wherePredicate.size()])));

            return query(cq).getResultList();
        } catch (NoResultException e) {
            LOGGER.info("List Terminal return an empty list", e);
            return null;
        }
    }

    @Override
    public List<Terminal> search(Entity entity, String filter, int firstResult, int lastResult) throws PersistenceException {
        try {
            CriteriaQuery<Terminal> cq = criteriaQuery();
            Root<Terminal> root = getRoot(cq);

            Predicate filterPredicate = criteriaBuilder().or(
                    criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("name")), "%" + filter.toUpperCase() + "%"),
                    criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("description")), "%" + filter.toUpperCase() + "%"),
                    criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("serialNumber")), "%" + filter.toUpperCase() + "%"));

            List<Predicate> wherePredicate = wherePredicate(root, entity);
            wherePredicate.add(filterPredicate);
            wherePredicate.add(criteriaBuilder().equal(root.<Boolean>get("active"), true));

            cq.where(criteriaBuilder().and(wherePredicate.toArray(new Predicate[wherePredicate.size()])));

            return query(cq).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
        } catch (NoResultException e) {
            LOGGER.info("Search Terminal return an empty list", e);
            return null;
        }
    }

    @Override
    public List<TerminalHost> listHosts(String terminalId) throws PersistenceException {
        try {
            CriteriaQuery<TerminalHost> cq = criteriaBuilder().createQuery(TerminalHost.class);
            Root<TerminalHost> root = cq.from(TerminalHost.class);

            cq.where(criteriaBuilder().and(criteriaBuilder().equal(root.get("terminal").get("id"), Utils.decodeHashId(terminalId))));

            return query(cq).getResultList();

        } catch (NoResultException e) {
            LOGGER.info("listHost return an empty list", e);
            return null;
        }
    }

    @Override
    public List<TerminalHostSettingValue> listHostSettings(String terminalId, Host host) throws PersistenceException {
        try {
            CriteriaQuery<TerminalHostSettingValue> cq = criteriaBuilder().createQuery(TerminalHostSettingValue.class);
            Root<TerminalHostSettingValue> root = cq.from(TerminalHostSettingValue.class);
            root.fetch("terminalHost", JoinType.INNER);
            root.fetch("setting", JoinType.LEFT);

            cq.where(criteriaBuilder().and(criteriaBuilder().equal(root.get("terminalHost").get("terminal").get("id"), Utils.decodeHashId(terminalId)),
                    criteriaBuilder().equal(root.get("terminalHost").get("host"), host),
                    criteriaBuilder().notEqual(root.get("setting").get("key"), "HOST_SEQUENCE_NUMBER")));

            return query(cq).getResultList();
        } catch (NoResultException e) {
            LOGGER.info("listHostSettings return an empty list.", e);
            return null;
        }
    }

    @Override
    public TerminalHost findByTerminalHost(String terminalId, Host host) throws PersistenceException {
        try {

            CriteriaQuery cq = criteriaQuery();
            Root root = cq.from(TerminalHost.class);
            cq.select(root);

            Predicate predicate = criteriaBuilder().and(criteriaBuilder().equal(root.get("host"), host));
            predicate.getExpressions().add(criteriaBuilder().equal(root.get("terminal").get("id"), Utils.decodeHashId(terminalId)));

            cq.where(predicate);

            return (TerminalHost) query(cq).getSingleResult();

        } catch (NoResultException e) {
            LOGGER.info("findByTerminalHost return null.", e);
            return null;
        }
    }

    @Override
    public TerminalHostSettingValue findByTerminalHostAndSetting(String terminalId, Host host, TerminalHostSetting terminalHostSetting) throws PersistenceException {
        try {

            CriteriaQuery cq = criteriaQuery();
            Root root = cq.from(TerminalHostSettingValue.class);
            cq.select(root);

            Predicate predicate = criteriaBuilder().and(criteriaBuilder().equal(root.get("terminalHost").get("host"), host));
            predicate.getExpressions().add(criteriaBuilder().equal(root.get("terminalHost").get("terminal").get("id"), Utils.decodeHashId(terminalId)));
            predicate.getExpressions().add(criteriaBuilder().equal(root.get("setting"), terminalHostSetting));

            cq.where(predicate);

            return (TerminalHostSettingValue) query(cq).getSingleResult();

        } catch (NoResultException e) {
            LOGGER.info("findByTerminalHostAndSetting return null.", e);
            return null;
        }
    }

    @Override
    public List<TerminalSettingValue> listTerminalSettings(String terminalId) throws PersistenceException {
        try {
            CriteriaQuery<TerminalSettingValue> cq = criteriaBuilder().createQuery(TerminalSettingValue.class);
            Root<TerminalSettingValue> root = cq.from(TerminalSettingValue.class);

            Predicate terminalIdPredicate = criteriaBuilder().equal(root.get("terminal").get("id"), Utils.decodeHashId(terminalId));
            cq.where(criteriaBuilder().and(terminalIdPredicate));

            return query(cq).getResultList();

        } catch (NoResultException e) {
            LOGGER.info("listTerminalSettings return an empty list.", e);
            return null;
        }
    }

    private List<Predicate> wherePredicate(Root root, Entity entity) {
        List<Predicate> wherePredicate = new ArrayList<>();
        Predicate entPredicate = criteriaBuilder().disjunction();
        entPredicate.getExpressions().add(criteriaBuilder().like(root.<String>get("path"), "%-" + String.valueOf(entity.getId()) + "-%"));
        wherePredicate.add(entPredicate);
        wherePredicate.add(criteriaBuilder().equal(root.<Boolean>get("active"), true));

        return wherePredicate;
    }

    @Override
    public Terminal getBySerialNumber(String terminalSerialNumber) throws PersistenceException {
        return (Terminal)findByProperty("serialNumber", terminalSerialNumber).getSingleResult();
    }
}
