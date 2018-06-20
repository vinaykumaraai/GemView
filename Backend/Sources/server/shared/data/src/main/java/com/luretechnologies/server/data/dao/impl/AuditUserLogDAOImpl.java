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

import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceException;
import org.springframework.stereotype.Repository;
import com.luretechnologies.server.data.dao.AuditUserLogDAO;
import com.luretechnologies.server.data.model.AuditUserLog;
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
public class AuditUserLogDAOImpl extends BaseDAOImpl<AuditUserLog, Long> implements AuditUserLogDAO {

    @Override
    public List<AuditUserLog> getAuditUserLogList(List<Long> IDS) throws PersistenceException {
        List<AuditUserLog> auditUserLoglist = new ArrayList<>();
        for (Long ID : IDS) {
            auditUserLoglist.add(getEntityManager().find(AuditUserLog.class, ID));
        }
        return auditUserLoglist;
    }

    @Override
    public AuditUserLog getAuditUserLogByID(Long ID) throws PersistenceException {
        CriteriaQuery<AuditUserLog> cq = criteriaQuery();
        Root<AuditUserLog> root = getRoot(cq);
        return (AuditUserLog) query(cq.where(criteriaBuilder().equal(root.get("id"), ID))).getSingleResult();
    }

    @Override
    public Integer doForceUpdate(Integer value) throws PersistenceException {
        Integer forceUpdate = null;
        if (value == 0) {
            forceUpdate = 1;
        }
        return forceUpdate;

    }

    @Override
    public List<AuditUserLog> search(Long userId, Long entityId, String filter, int firstResult, int lastResult, Date dateFrom, Date dateTo) throws PersistenceException {

        CriteriaQuery<AuditUserLog> cq = criteriaQuery();
        Root<AuditUserLog> root = getRoot(cq);

        Predicate predicate = criteriaBuilder().conjunction();

        
        if (filter != null && !filter.isEmpty()) {
             Expression<String> dateStringExpr = criteriaBuilder().function("DATE_FORMAT", String.class, root.get("dateAt"), criteriaBuilder().literal("'%d/%m/%Y %r'"));
            predicate.getExpressions().add(criteriaBuilder().or(
                    criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("description")), "%" + filter.toUpperCase() + "%"),
                    criteriaBuilder().like(criteriaBuilder().upper(dateStringExpr), "%" + filter.toUpperCase() + "%"),
                    criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("details")), "%" + filter.toUpperCase() + "%")));
        }

        if (userId != null && userId > 0) {
            predicate.getExpressions().add(criteriaBuilder().equal(root.<Long>get("user"), userId));
        }
        if (entityId != null && entityId > 0) {
            predicate.getExpressions().add(criteriaBuilder().equal(root.<Long>get("entity"), entityId));
        }

        predicate.getExpressions().add(criteriaBuilder().greaterThanOrEqualTo(root.<Date>get("dateAt"), dateFrom));
        predicate.getExpressions().add(criteriaBuilder().lessThanOrEqualTo(root.<Date>get("dateAt"), dateTo));

        cq.where(predicate).orderBy(criteriaBuilder().desc(root.get("dateAt")));

        return query(cq).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }

    /**
     * Delete user audit log 
     * @param date
     * @throws PersistenceException
     */
    @Override
    public void delete(Date date) throws PersistenceException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "DELETE FROM audit_user_log WHERE date_at < '" + format.format(date) + "'";
        super.entityManager.createNativeQuery( sql ).executeUpdate();
    }
}
