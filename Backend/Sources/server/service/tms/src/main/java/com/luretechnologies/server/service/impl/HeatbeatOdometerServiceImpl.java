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
import com.luretechnologies.server.data.dao.HeartbeatOdometerDAO;
import com.luretechnologies.server.data.display.tms.HeartbeatOdometerDisplay;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.tms.HeartbeatOdometer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.luretechnologies.server.service.HeartbeatOdometerService;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author
 */
@Service
@Transactional
public class HeatbeatOdometerServiceImpl implements HeartbeatOdometerService {

    @Autowired
    EntityDAO entityDAO;

    @Autowired
    HeartbeatOdometerDAO heartbeatOdometerDAO;

    @Override
    public HeartbeatOdometerDisplay updateOdometer(String entityId, String component, String label, String description, String value) throws Exception {

        Entity entity = entityDAO.findByEntityId(entityId);
        if (entity == null) {
            throw new ObjectRetrievalFailureException(Entity.class, entityId);
        }
        HeartbeatOdometer heartbeatOdometer = heartbeatOdometerDAO.getHeartbeatOdometerBylabelAndEntity(entity.getId(), label, component);
        if (heartbeatOdometer != null) {
            heartbeatOdometer.setValue(value);
        } else {
            return null;
        }
        HeartbeatOdometerDisplay display = new HeartbeatOdometerDisplay();
        display.setComponent(heartbeatOdometer.getComponent());
        display.setDescription(heartbeatOdometer.getDescription());
        display.setEntity(heartbeatOdometer.getEntity().getId());
        display.setId(heartbeatOdometer.getId());
        display.setLabel(heartbeatOdometer.getLabel());
        display.setName(heartbeatOdometer.getName());
        display.setValue(heartbeatOdometer.getValue());
        display.setUpdatedAt(heartbeatOdometer.getUpdatedAt());
        return display;
    }

    @Override
    public List<HeartbeatOdometerDisplay> search(String entityId, String filter, int pageNumber, int rowsPerPage, Date dateDrom, Date dateTo) throws Exception {
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
    public HeartbeatOdometerDisplay updateOdometer(Long entityid, String component, String label, String description, String value) throws Exception {
        try {
            Entity entity = entityDAO.findById(entityid);
            if (entity == null) {
                throw new ObjectRetrievalFailureException(Entity.class, entityid);
            }
            return updateOdometer(entity.getEntityId(), component, label, description, value);
        } catch (Exception ex) {
        }
        return null;
    }

    @Override
    public List<HeartbeatOdometerDisplay> search(Long entityId, String filter, int pageNumber, int rowsPerPage, Date dateDrom, Date dateTo) throws Exception {
        int firstResult = (((pageNumber - 1) >= 0) ? (pageNumber - 1) : 0) * rowsPerPage;

        List<HeartbeatOdometer> heartbeatOdometers = heartbeatOdometerDAO.search(entityId, filter, firstResult, firstResult + rowsPerPage, dateDrom, dateTo);
        List<HeartbeatOdometerDisplay> displays;
        if (heartbeatOdometers != null && heartbeatOdometers.size() > 0) {
            displays = new ArrayList<>();
            for (HeartbeatOdometer odometer : heartbeatOdometers) {
                HeartbeatOdometerDisplay display = new HeartbeatOdometerDisplay();
                display.setComponent(odometer.getComponent());
                display.setDescription(odometer.getDescription());
                display.setLabel(odometer.getLabel());
                display.setId(odometer.getId());
                display.setName(odometer.getName());
                display.setOccurred(new Date(odometer.getCurrentTime().getTime()));
                display.setValue(odometer.getValue());
                display.setEntity(odometer.getEntity().getId());
                display.setUpdatedAt(odometer.getUpdatedAt());
                displays.add(display);
            }
        } else {
            return null;
        }
        return displays;
    }

    @Override
    public void delete(long id) throws Exception {
        heartbeatOdometerDAO.delete(id);
    }

}
