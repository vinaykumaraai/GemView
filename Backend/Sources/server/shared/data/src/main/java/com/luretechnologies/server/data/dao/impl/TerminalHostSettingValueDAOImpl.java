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

import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.dao.TerminalHostSettingValueDAO;
import com.luretechnologies.server.data.model.payment.Host;
import com.luretechnologies.server.data.model.payment.MerchantHostSetting;
import com.luretechnologies.server.data.model.payment.TerminalHostSetting;
import com.luretechnologies.server.data.model.payment.TerminalHostSettingValue;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
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
public class TerminalHostSettingValueDAOImpl extends BaseDAOImpl<TerminalHostSettingValue, Long> implements TerminalHostSettingValueDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TerminalHostSettingValueDAO.class);

    @Override
    public List<TerminalHostSetting> findByHostId(Long hostId) {
        try {
            CriteriaQuery q = criteriaBuilder().createQuery(MerchantHostSetting.class);
            Root root = q.from(TerminalHostSetting.class);
            root.fetch("host", JoinType.INNER);

            q.where(criteriaBuilder().equal(root.get("host").get("id"), hostId));

            return query(q).getResultList();
        } catch (NoResultException e) {
            LOGGER.info("TerminalHostSetting findByHostId.", e);
        }
        return new ArrayList<>();
    }
    
    /**
     *
     * @param terminalId
     * @param host
     * @return
     * @throws PersistenceException
     */
    @Override
    public TerminalHostSettingValue getSecuenceNumberByHost(String terminalId, Host host) throws PersistenceException {
        try {
            CriteriaQuery<TerminalHostSettingValue> q = criteriaBuilder().createQuery(TerminalHostSettingValue.class);
            Root<TerminalHostSettingValue> root = q.from(TerminalHostSettingValue.class);
            root.fetch("terminalHost", JoinType.INNER);

            Predicate terminalIdPredicate = criteriaBuilder().equal(root.get("terminalHost").get("terminal").get("id"), Utils.decodeHashId(terminalId));
            Predicate hostPredicate = criteriaBuilder().equal(root.get("terminalHost").get("host"), host);
            Predicate settingPredicate = criteriaBuilder().equal(root.get("setting").get("key"), "HOST_SEQUENCE_NUMBER");
            q.where(criteriaBuilder().and(terminalIdPredicate, hostPredicate, settingPredicate));

            return (TerminalHostSettingValue) query(q).setLockMode(LockModeType.PESSIMISTIC_WRITE).getSingleResult();

        } catch (NoResultException e) {
            LOGGER.info("getSecuenceNumberByHost return null.", e);
            return null;
        }
    }

    /**
     *
     * @param terminalId
     * @param terminalHostSettingEnum
     * @param hostEnum
     * @return
     * @throws PersistenceException
     */
    @Override
    public TerminalHostSettingValue getTerminalHostSettingValue(String terminalId, TerminalHostSetting terminalHostSettingEnum, Host hostEnum) throws PersistenceException {
        try {
            CriteriaQuery<TerminalHostSettingValue> q = criteriaBuilder().createQuery(TerminalHostSettingValue.class);
            Root<TerminalHostSettingValue> root = q.from(TerminalHostSettingValue.class);
            root.fetch("terminalHost", JoinType.INNER);

            Predicate terminalIdPredicate = criteriaBuilder().equal(root.get("terminalHost").get("terminal").get("id"), Utils.decodeHashId(terminalId));
            Predicate hostPredicate = criteriaBuilder().equal(root.get("terminalHost").get("host"), hostEnum);
            Predicate settingPredicate = criteriaBuilder().equal(root.get("setting"), terminalHostSettingEnum);
            q.where(criteriaBuilder().and(terminalIdPredicate, hostPredicate, settingPredicate));

            return (TerminalHostSettingValue) query(q).setLockMode(LockModeType.PESSIMISTIC_WRITE).getSingleResult();

        } catch (NoResultException e) {
            LOGGER.info("getTerminalHostSettingValue return null.", e);
            return null;
        }
    }

}