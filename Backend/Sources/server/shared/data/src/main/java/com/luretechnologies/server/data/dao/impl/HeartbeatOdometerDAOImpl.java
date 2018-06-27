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

import java.util.List;
import javax.persistence.PersistenceException;
import org.springframework.stereotype.Repository;
import com.luretechnologies.server.data.dao.HeartbeatOdometerDAO;
import com.luretechnologies.server.data.model.tms.HeartbeatOdometer;
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
public class HeartbeatOdometerDAOImpl extends BaseDAOImpl<HeartbeatOdometer, Long> implements HeartbeatOdometerDAO {

    @Override
    public List<HeartbeatOdometer> search(Long entityId, String filter, int firstResult, int lastResult, Date dateDrom, Date dateTo) throws PersistenceException {

        CriteriaQuery<HeartbeatOdometer> cq = criteriaQuery();
        Root<HeartbeatOdometer> root = getRoot(cq);
        
         Predicate predicate = criteriaBuilder().conjunction();

        if (filter != null && !filter.isEmpty()) {
            predicate.getExpressions().add(criteriaBuilder().or(
                    criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("description")), "%" + filter.toUpperCase() + "%"),
                    criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("component")), "%" + filter.toUpperCase() + "%"),
                    criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("name")), "%" + filter.toUpperCase() + "%"),
                    criteriaBuilder().like(criteriaBuilder().upper((Expression) root.get("label")), "%" + filter.toUpperCase() + "%")));
        }
        
        if (entityId != null && entityId > 0) {
            predicate.getExpressions().add(criteriaBuilder().equal(root.<Long>get("entity"), entityId));
        }

        cq.where(predicate).orderBy(criteriaBuilder().asc(root.get("label")));

        return query(cq).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }

    @Override
    public HeartbeatOdometer getHeartbeatOdometerBylabelAndEntity(Long entity, String label, String component) throws PersistenceException {
        CriteriaQuery<HeartbeatOdometer> cq = criteriaQuery();
        Root<HeartbeatOdometer> root = getRoot(cq);
        return (HeartbeatOdometer) query(cq.where(criteriaBuilder().and(criteriaBuilder().equal(root.get("label"), label), criteriaBuilder().equal(root.get("entity"), entity), criteriaBuilder().equal(root.get("component"), component)))).getSingleResult();
    }


}