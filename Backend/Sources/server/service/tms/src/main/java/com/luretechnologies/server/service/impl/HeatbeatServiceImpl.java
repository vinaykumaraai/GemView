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
import com.luretechnologies.server.data.dao.HeartbeatAppInfoDAO;
import com.luretechnologies.server.data.dao.HeartbeatAuditDAO;
import com.luretechnologies.server.data.dao.HeartbeatDAO;
import com.luretechnologies.server.data.dao.HeartbeatOdometerDAO;
import com.luretechnologies.server.data.dao.TerminalDAO;
import com.luretechnologies.server.data.display.tms.HeartbeatDisplay;
import com.luretechnologies.server.data.display.tms.HeartbeatAlertDisplay;
import com.luretechnologies.server.data.display.tms.HeartbeatAppInfoDisplay;
import com.luretechnologies.server.data.display.tms.HeartbeatAuditDisplay;
import com.luretechnologies.server.data.display.tms.HeartbeatOdometerDisplay;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.tms.Heartbeat;
import com.luretechnologies.server.data.model.tms.HeartbeatAlert;
import com.luretechnologies.server.data.model.tms.HeartbeatAppInfo;
import com.luretechnologies.server.data.model.tms.HeartbeatAudit;
import com.luretechnologies.server.data.model.tms.HeartbeatOdometer;
import com.luretechnologies.server.service.HeartbeatService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import javax.persistence.PersistenceException;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author
 */
@Service
@Transactional
public class HeatbeatServiceImpl implements HeartbeatService {

    @Autowired
    EntityDAO entityDAO;

    @Autowired
    HeartbeatDAO heartbeatDAO;

    @Autowired
    TerminalDAO terminalDAO;

    @Autowired
    HeartbeatAppInfoDAO heartbeatAppInfoDAO;

    @Autowired
    HeartbeatOdometerDAO heartbeatOdometerDAO;

    @Autowired
    HeartbeatAuditDAO heartbeatAuditDAO;

    @Autowired
    HeartbeatAlertDAO heartbeatAlertDAO;

    @Override
    public List<HeartbeatDisplay> search(String entityId, String filter, int pageNumber, int rowsPerPage, Date dateDrom, Date dateTo) throws Exception {

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
    public List<HeartbeatDisplay> search(Long entityId, String filter, int pageNumber, int rowsPerPage, Date dateDrom, Date dateTo) throws Exception {

        int firstResult = (((pageNumber - 1) >= 0) ? (pageNumber - 1) : 0) * rowsPerPage;
        List<Heartbeat> heartbeats = heartbeatDAO.search(entityId, filter, firstResult, firstResult + rowsPerPage, dateDrom, dateTo);
        List<HeartbeatDisplay> heartbeatDisplays = new ArrayList<>();

        if (heartbeats != null && heartbeats.size() > 0) {
            for (Heartbeat heartbeat : heartbeats) {
                HeartbeatDisplay display = new HeartbeatDisplay();
                display.setEntity(heartbeat.getEntity().getId());
                display.setMessage(heartbeat.getMessage());
                display.setHwModel(heartbeat.getHwModel());
                display.setId(heartbeat.getId());
                display.setIp(heartbeat.getIp());
                display.setOccurred(new Date(heartbeat.getOccurred().getTime()));
                display.setSequence(heartbeat.getSequence());
                display.setSerialNumber(heartbeat.getSerialNumber());
                display.setStatus(heartbeat.getStatus());

                if (heartbeat.getHeartbeatAudits() != null && heartbeat.getHeartbeatAudits().size() > 0) {
                    for (HeartbeatAudit audit : heartbeat.getHeartbeatAudits()) {
                        HeartbeatAuditDisplay auditDisplay = new HeartbeatAuditDisplay();
                        auditDisplay.setComponent(audit.getComponent());
                        auditDisplay.setDescription(audit.getDescription());
                        auditDisplay.setLabel(audit.getLabel());
                        auditDisplay.setMessage(audit.getMessage());
                        auditDisplay.setId(audit.getId());
                        auditDisplay.setUpdatedAt(new Date(audit.getUpdatedAt().getTime()));
                        auditDisplay.setOccurred(new Date(audit.getOccurred().getTime()));
                        display.getHeartbeatAudits().add(auditDisplay);
                    }
                }
                heartbeatDisplays.add(display);
            }
        } else {
            return null;
        }
        return heartbeatDisplays;
    }

    @Override
    public HeartbeatDisplay create(HeartbeatDisplay heartbeatDisplay) throws Exception {

        Heartbeat heartbeat = new Heartbeat();

        try {
            Entity terminal = terminalDAO.findBySerialNumber(heartbeatDisplay.getSerialNumber());
            if (terminal == null) {
                throw new ObjectRetrievalFailureException(Entity.class, heartbeatDisplay.getSerialNumber());
            }
            heartbeat.setMessage(heartbeatDisplay.getMessage());
            heartbeat.setIp(heartbeatDisplay.getIp());
            heartbeat.setSequence(heartbeatDisplay.getSequence());
            heartbeat.setEntity(terminal);
            heartbeat.setSerialNumber(heartbeatDisplay.getSerialNumber());
            heartbeat.setStatus(heartbeatDisplay.getStatus());
            heartbeat.setHwModel(heartbeatDisplay.getHwModel());
            heartbeat.setOccurred(new Timestamp(System.currentTimeMillis()));
            heartbeatDAO.persist(heartbeat);

            if (heartbeatDisplay.getSwComponents() != null && heartbeatDisplay.getSwComponents().size() > 0) {
                for (HeartbeatAppInfoDisplay temp : heartbeatDisplay.getSwComponents()) {
                    HeartbeatAppInfo heartbeatAppInfo = null;
                    try {
                        heartbeatAppInfo = heartbeatAppInfoDAO.getByNameAndEntity(terminal.getId(), temp.getName());
                    } catch (Exception ex) {
                        ex.getMessage();
                    }
                    if (heartbeatAppInfo != null) {
                        heartbeatAppInfo.setVersion(temp.getVersion());
                        heartbeatAppInfo.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        heartbeatAppInfoDAO.merge(heartbeatAppInfo);
                    } else {
                        heartbeatAppInfo = new HeartbeatAppInfo();
                        heartbeatAppInfo.setName(temp.getName());
                        heartbeatAppInfo.setVersion(temp.getVersion());
                        heartbeatAppInfo.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        heartbeatAppInfo.setEntity(terminal);
                        heartbeatAppInfoDAO.persist(heartbeatAppInfo);
                    }
                    temp.setId(heartbeatAppInfo.getId());
                }
            }
            if (heartbeatDisplay.getHeartbeatOdometers() != null && heartbeatDisplay.getHeartbeatOdometers().size() > 0) {
                for (HeartbeatOdometerDisplay temp : heartbeatDisplay.getHeartbeatOdometers()) {
                    HeartbeatOdometer heartbeatOdometer = null;
                    try {
                        heartbeatOdometer = heartbeatOdometerDAO.getHeartbeatOdometerBylabelAndEntity(terminal.getId(), temp.getLabel(), temp.getComponent());
                    } catch (Exception ex) {
                        ex.getMessage();
                    }
                    if (heartbeatOdometer != null) {
                        heartbeatOdometer.setValue(temp.getValue());
                        heartbeatOdometer.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        heartbeatOdometerDAO.merge(heartbeatOdometer);
                        temp.setId(heartbeatOdometer.getId());
                    } else {
                        heartbeatOdometer = new HeartbeatOdometer();
                        heartbeatOdometer.setEntity(terminal);
                        heartbeatOdometer.setComponent(temp.getComponent());
                        heartbeatOdometer.setLabel(temp.getLabel());
                        heartbeatOdometer.setDescription(temp.getDescription());
                        heartbeatOdometer.setValue(temp.getValue());
                        heartbeatOdometer.setCurrentTime(new Timestamp(System.currentTimeMillis()));
                        heartbeatOdometer.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        heartbeatOdometerDAO.persist(heartbeatOdometer);
                        temp.setId(heartbeatOdometer.getId());
                    }
                }
            }
            if (heartbeatDisplay.getHeartbeatAudits() != null && heartbeatDisplay.getHeartbeatAudits().size() > 0) {
                for (HeartbeatAuditDisplay temp : heartbeatDisplay.getHeartbeatAudits()) {
                    HeartbeatAudit heartbeatAudit = new HeartbeatAudit();
                    heartbeatAudit.setComponent(temp.getComponent());
                    heartbeatAudit.setDescription(temp.getDescription());
                    heartbeatAudit.setEntity(terminal);
                    heartbeatAudit.setHeartbeat(heartbeat);
                    heartbeatAudit.setLabel(temp.getLabel());
                    heartbeatAudit.setMessage(temp.getMessage());
                    heartbeatAudit.setOccurred(new Timestamp(System.currentTimeMillis()));
                    heartbeatAudit.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                    heartbeatAuditDAO.persist(heartbeatAudit);
                    temp.setId(heartbeatAudit.getId());
                }
            }
            if (heartbeatDisplay.getHeartbeatAlerts() != null && heartbeatDisplay.getHeartbeatAlerts().size() > 0) {
                for (HeartbeatAlertDisplay temp : heartbeatDisplay.getHeartbeatAlerts()) {
                    HeartbeatAlert heartbeatAlert = null;

                    try {
                        heartbeatAlert = heartbeatAlertDAO.getByLabelAndEntity(terminal.getId(), temp.getLabel(), temp.getComponent());
                    } catch (Exception ex) {
                        ex.getMessage();
                    }

                    if (heartbeatAlert != null) {
                        if (temp.getDone() != null) {
                            heartbeatAlert.setDone(temp.getDone());
                        }
                        heartbeatAlert.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        heartbeatAlertDAO.merge(heartbeatAlert);
                    } else {
                        heartbeatAlert = new HeartbeatAlert();
                        heartbeatAlert.setComponent(temp.getComponent());
                        heartbeatAlert.setLabel(temp.getLabel());
                        heartbeatAlert.setEntity(terminal);
                        heartbeatAlert.setDone(false);
                        heartbeatAlert.setOccurred(new Timestamp(System.currentTimeMillis()));
                        heartbeatAlert.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        heartbeatAlertDAO.persist(heartbeatAlert);
                    }
                    temp.setId(heartbeatAlert.getId());
                }
            }
        } catch (Exception ex) {
            throw new PersistenceException("Creating heartbeat error " + ex.getMessage());
        }
        heartbeatDisplay.setId(heartbeat.getId());
        return heartbeatDisplay;
    }

}
