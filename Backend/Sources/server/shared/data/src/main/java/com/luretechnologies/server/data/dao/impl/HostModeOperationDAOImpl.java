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

import com.luretechnologies.common.enums.ModeEnum;
import com.luretechnologies.common.enums.OperationEnum;
import com.luretechnologies.server.data.dao.HostModeOperationDAO;
import com.luretechnologies.server.data.model.payment.Host;
import com.luretechnologies.server.data.model.payment.HostModeOperation;
import java.util.List;
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
public class HostModeOperationDAOImpl extends BaseDAOImpl<HostModeOperation, Long> implements HostModeOperationDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(HostModeOperationDAO.class);

    @Override
    public Host getHostByModeOperation(ModeEnum mode, OperationEnum operation) throws PersistenceException {
        CriteriaQuery<HostModeOperation> q = criteriaQuery();
        Root<HostModeOperation> root = getRoot(q);
        Predicate predicate = null;

        if (mode != null) {
            Predicate modePredicate = criteriaBuilder().equal(root.get("mode"), mode.getId());
            predicate = criteriaBuilder().and(modePredicate);
        }

        if (operation != null) {
            Predicate operationPredicate = criteriaBuilder().equal(root.get("operation"), operation.getId());
            if (predicate != null) {
                predicate.getExpressions().add(operationPredicate);
            } else {
                predicate = operationPredicate;
            }
        }

        q.where(predicate);

        HostModeOperation hostModeOperation = (HostModeOperation) query(q).getSingleResult();

        return hostModeOperation.getHost();
    }

    @Override
    public List<HostModeOperation> findByHostId(Long hostId) {
        CriteriaQuery<HostModeOperation> cq = criteriaQuery();
        Root<HostModeOperation> root = getRoot(cq);
        root.fetch("host", JoinType.INNER);

        return query(cq.where(criteriaBuilder().equal(root.get("host").get("id"), hostId))).getResultList();
    }
    
    @Override
    public HostModeOperation findById(Long hostModeOperationId) {
        CriteriaQuery<HostModeOperation> cq = criteriaQuery();
        Root<HostModeOperation> root = getRoot(cq);
        root.fetch("host", JoinType.INNER);

        return (HostModeOperation)query(cq.where(criteriaBuilder().equal(root.get("id"), hostModeOperationId))).getSingleResult();
    }

}
