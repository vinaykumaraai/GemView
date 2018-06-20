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
package com.luretechnologies.server.service.impl;

import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.tms.AlertAction;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.luretechnologies.server.service.AlertActionService;
import com.luretechnologies.server.data.dao.AlertActionDAO;

/**
 *
 * @author
 */
@Service
@Transactional
public class AlertActionServiceImpl implements AlertActionService {

    @Autowired
    EntityDAO entityDAO;

    @Autowired
    AlertActionDAO alertActionDAO;

    @Override
    public List<AlertAction> search(String entityId, String filter, int pageNumber, int rowsPerPage, Date dateDrom, Date dateTo) throws Exception {

        try {
            Entity entity = entityDAO.findByEntityId(entityId);
            if (entity == null) {
                throw new ObjectRetrievalFailureException(Entity.class, entityId);
            }
            return search(entity.getId(), filter, pageNumber, rowsPerPage, dateDrom, dateTo);
        } catch (Exception ex) {
        }
        return null;
    }

    @Override
    public List<AlertAction> search(Long entityId, String filter, int pageNumber, int rowsPerPage, Date dateDrom, Date dateTo) throws Exception {
        int firstResult = (((pageNumber - 1) >= 0) ? (pageNumber - 1) : 0) * rowsPerPage;
        return alertActionDAO.search(entityId, filter, firstResult, firstResult + rowsPerPage, dateDrom, dateTo);
    }

    @Override
    public AlertAction create(String entityId, AlertAction alertAction) throws Exception {
        try {
            Entity entity = entityDAO.findByEntityId(entityId);
            if (entity == null) {
                throw new ObjectRetrievalFailureException(Entity.class, entityId);
            }
            alertAction.setEntity(entity);
            alertAction.setActive(true);
            alertAction.setOccurred(new Timestamp(System.currentTimeMillis()));
            alertActionDAO.persist(alertAction);
        } catch (Exception ex) {
            return null;
        }
        return alertAction;
    }

    @Override
    public void delete(Long id) throws Exception {
        alertActionDAO.delete(id);
    }

    @Override
    public AlertAction update(AlertAction alertAction) throws Exception {
        try {
            AlertAction alertActionModel = alertActionDAO.findById(alertAction.getId());
            if (alertActionModel != null) {
                alertActionModel.setAvailable(alertAction.getAvailable());
                alertActionModel.setEmail(alertAction.getEmail());
                alertActionModel.setDescription(alertAction.getDescription());
                alertActionModel.setName(alertAction.getName());
                alertActionModel.setLabel(alertAction.getLabel());
                return alertActionDAO.merge(alertActionModel);
            }
            return null;
        } catch (Exception ex) {
        }
        return null;
    }
}
