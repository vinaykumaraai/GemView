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

import com.luretechnologies.server.data.dao.HeartbeatAuditDAO;
import java.util.List;
import javax.persistence.PersistenceException;
import org.springframework.stereotype.Repository;
import com.luretechnologies.server.data.model.tms.HeartbeatAudit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Vinay
 */
@Repository
public class HeartbeatAuditDAOImpl extends BaseDAOImpl<HeartbeatAudit, Long> implements HeartbeatAuditDAO {

    @Override
    public List<HeartbeatAudit> search(Long entityId, String filter, int firstResult, int lastResult, Date dateFrom, Date dateTo) throws PersistenceException {

        CriteriaQuery<HeartbeatAudit> cq = criteriaQuery();
        Root<HeartbeatAudit> root = getRoot(cq);

        Predicate predicate = criteriaBuilder().conjunction();

        if (filter != null && !filter.isEmpty()) {
            predicate.getExpressions().add(criteriaBuilder().or(
                    criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("description")), "%" + filter.toUpperCase() + "%"),
                    criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("component")), "%" + filter.toUpperCase() + "%"),
                    criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("label")), "%" + filter.toUpperCase() + "%")));
        }

        if (entityId != null && entityId > 0) {
            predicate.getExpressions().add(criteriaBuilder().equal(root.<Long>get("entity"), entityId));
        }

        predicate.getExpressions().add(criteriaBuilder().greaterThanOrEqualTo(root.<Date>get("occurred"), dateFrom));
        predicate.getExpressions().add(criteriaBuilder().lessThanOrEqualTo(root.<Date>get("occurred"), dateTo));

        cq.where(predicate).orderBy(criteriaBuilder().desc(root.get("occurred")));

        return query(cq).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }

    @Override
    public HeartbeatAudit getHeartbeatOdometerBylabel(String label) throws PersistenceException {
        CriteriaQuery<HeartbeatAudit> cq = criteriaQuery();
        Root<HeartbeatAudit> root = getRoot(cq);
        return (HeartbeatAudit) query(cq.where(criteriaBuilder().equal(root.get("label"), label))).getSingleResult();
    }

    @Override
    public void delete(Date date) throws PersistenceException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "DELETE FROM heartbeat_audit WHERE occurred < '" + format.format(date) + "'";
        super.entityManager.createNativeQuery( sql ).executeUpdate();
    }

    
}
