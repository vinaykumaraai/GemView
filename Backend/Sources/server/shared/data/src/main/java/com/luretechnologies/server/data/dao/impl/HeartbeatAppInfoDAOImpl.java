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

import com.luretechnologies.server.data.dao.HeartbeatAppInfoDAO;
import com.luretechnologies.server.data.model.tms.HeartbeatAppInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

/**
 *
 * @author
 */
@Repository
public class HeartbeatAppInfoDAOImpl extends BaseDAOImpl<HeartbeatAppInfo, Long> implements HeartbeatAppInfoDAO {

    @Override
    public HeartbeatAppInfo getByNameAndEntity(Long entityId, String name) throws PersistenceException {
        CriteriaQuery<HeartbeatAppInfo> cq = criteriaQuery();

        Root<HeartbeatAppInfo> root = getRoot(cq);

        Predicate predicate = criteriaBuilder().conjunction();

        if (name != null && !name.isEmpty()) {
            predicate.getExpressions().add(criteriaBuilder().equal(root.<String>get("name"), name));
        }

        if (entityId != null && entityId > 0) {
            predicate.getExpressions().add(criteriaBuilder().equal(root.<Long>get("entity"), entityId));
        }
        cq.where(predicate);

        return (HeartbeatAppInfo) query(cq).getSingleResult();
    }

    @Override
    public List<HeartbeatAppInfo> getAppInfos(Long id) throws PersistenceException {
        CriteriaQuery<HeartbeatAppInfo> cq = criteriaQuery();

        Root<HeartbeatAppInfo> root = getRoot(cq);

        Predicate predicate = criteriaBuilder().conjunction();

        if (id != null && id > 0) {
            predicate.getExpressions().add(criteriaBuilder().equal(root.<Long>get("entity"), id));
        }
        cq.where(predicate);

        return query(cq).getResultList();
    }

    @Override
    public List<Long> getTerminalIdsByLastUpdate(Date lastUpdate) throws PersistenceException {
        CriteriaQuery<HeartbeatAppInfo> cq = criteriaQuery();

        Root<HeartbeatAppInfo> root = getRoot(cq);

        Predicate predicate = criteriaBuilder().conjunction();

        predicate.getExpressions().add(criteriaBuilder().greaterThanOrEqualTo(root.<Date>get("lastUpdateAt"), lastUpdate));

        cq.where(predicate);

        List<HeartbeatAppInfo> appInfos = query(cq).getResultList();
        if (appInfos != null && appInfos.size() > 0) {
            List<Long> longs = new ArrayList<>();
            for (HeartbeatAppInfo appInfo : appInfos) {
                if (!longs.contains(appInfo.getEntity().getId())) {
                    longs.add(appInfo.getEntity().getId());
                }
            }
            return longs;
        }
        return null;

    }

}
