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
import com.luretechnologies.server.data.dao.HeartbeatAuditDAO;
import com.luretechnologies.server.data.display.tms.HeartbeatAuditDisplay;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.tms.HeartbeatAudit;
import com.luretechnologies.server.service.HeartbeatAuditService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author
 */
@Service
@Transactional
public class HeatbeatAuditServiceImpl implements HeartbeatAuditService {

    @Autowired
    EntityDAO entityDAO;

    @Autowired
    HeartbeatAuditDAO heartbeatAuditDAO;

    @Override
    public List<HeartbeatAuditDisplay> search(String entityId, String filter, int pageNumber, int rowsPerPage, Date dateDrom, Date dateTo) throws Exception {

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
    public List<HeartbeatAuditDisplay> search(Long entityId, String filter, int pageNumber, int rowsPerPage, Date dateDrom, Date dateTo) throws Exception {
        int firstResult = (((pageNumber - 1) >= 0) ? (pageNumber - 1) : 0) * rowsPerPage;
        List<HeartbeatAudit> audits = heartbeatAuditDAO.search(entityId, filter, firstResult, firstResult + rowsPerPage, dateDrom, dateTo);
        List<HeartbeatAuditDisplay> auditDisplays;
        if (audits != null && audits.size() > 0) {
            auditDisplays = new ArrayList<>();
            for (HeartbeatAudit audit : audits) {
                HeartbeatAuditDisplay auditDisplay = new HeartbeatAuditDisplay();
                auditDisplay.setComponent(audit.getComponent());
                auditDisplay.setMessage(audit.getMessage());
                auditDisplay.setId(audit.getId());
                auditDisplay.setLabel(audit.getLabel());
                auditDisplay.setOccurred(new Date(audit.getOccurred().getTime()));
                auditDisplay.setEntity(audit.getEntity().getId());
                auditDisplay.setDescription(audit.getDescription());
                auditDisplays.add(auditDisplay);
            }
        } else {
            return null;
        }
        return auditDisplays;
    }

    @Override
    public void delete(Date date) throws Exception {
        heartbeatAuditDAO.delete(date);
    }

    @Override
    public void delete(long id) throws Exception {
        heartbeatAuditDAO.delete(id);
    }


}
