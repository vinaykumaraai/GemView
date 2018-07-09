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
import com.luretechnologies.server.data.dao.HeartbeatAlertDAO;
import com.luretechnologies.server.data.dao.TerminalDAO;
import com.luretechnologies.server.data.display.tms.HeartbeatAlertDisplay;
import com.luretechnologies.server.data.display.tms.HeartbeatDisplay;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.tms.HeartbeatAlert;
import com.luretechnologies.server.service.HeartbeatAlertService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author
 */
@Service
@Transactional
public class HeatbeatAlertServiceImpl implements HeartbeatAlertService {

    @Autowired
    EntityDAO entityDAO;

    @Autowired
    HeartbeatAlertDAO heartbeatAlertDAO;

    @Autowired
    TerminalDAO terminalDAO;

    @Override
    public List<HeartbeatAlertDisplay> search(String entityId, String filter, int pageNumber, int rowsPerPage, Date dateDrom, Date dateTo) throws Exception {

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
    public List<HeartbeatAlertDisplay> search(Long entityId, String filter, int pageNumber, int rowsPerPage, Date dateDrom, Date dateTo) throws Exception {
        int firstResult = (((pageNumber - 1) >= 0) ? (pageNumber - 1) : 0) * rowsPerPage;
        List<HeartbeatAlert> alerts = heartbeatAlertDAO.search(entityId, filter, firstResult, firstResult + rowsPerPage, dateDrom, dateTo);
        List<HeartbeatAlertDisplay> alertDisplays;
        if (alerts != null && alerts.size() > 0) {
            alertDisplays = new ArrayList<>();
            for (HeartbeatAlert alert : alerts) {
                HeartbeatAlertDisplay alertDisplay = new HeartbeatAlertDisplay();
                alertDisplay.setComponent(alert.getComponent());
                alertDisplay.setEntity(alert.getEntity().getId());
                alertDisplay.setId(alert.getId());
                alertDisplay.setLabel(alert.getLabel());
                alertDisplay.setOccurred(new Date(alert.getOccurred().getTime()));
                alertDisplay.setDone(alert.getDone());
                alertDisplay.setUpdatedAt(new Date(alert.getUpdatedAt().getTime()));
                alertDisplays.add(alertDisplay);
            }
        } else {
            return null;
        }
        return alertDisplays;
    }

    @Override
    public void alertDone(Long alertId) throws Exception {
        HeartbeatAlert heartbeatAlert = null;
        try {
            heartbeatAlert = heartbeatAlertDAO.findById(alertId);
        } catch (Exception ex) {
            ex.getMessage();
        }

        if (heartbeatAlert != null) {
            heartbeatAlert.setDone(true);
            heartbeatAlert.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            heartbeatAlertDAO.merge(heartbeatAlert);
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        heartbeatAlertDAO.delete(id);
    }

    @Override
    public List<HeartbeatAlertDisplay> getAlerts(String serialNumber, String label) {
        try {
            Entity terminal = terminalDAO.findBySerialNumber(serialNumber);
            if (terminal == null) {
                throw new ObjectRetrievalFailureException(Entity.class, serialNumber);
            }
            if (label != null  ) {
                List<HeartbeatAlert> alerts = heartbeatAlertDAO.getAlerts(terminal.getId(), null, label);
                if (alerts != null && alerts.size() > 0) {
                    List<HeartbeatAlertDisplay> alertDisplays = new ArrayList<>();

                    for (HeartbeatAlert temp : alerts) {
                        HeartbeatAlertDisplay display = new HeartbeatAlertDisplay();
                        BeanUtils.copyProperties(temp, display);
                        alertDisplays.add(display);
                    }
                    return alertDisplays;
                }
            }

        } catch (Exception ex) {

        }
        return null;
    }
}
