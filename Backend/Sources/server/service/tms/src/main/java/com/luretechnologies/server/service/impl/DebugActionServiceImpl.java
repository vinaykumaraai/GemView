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
import com.luretechnologies.server.data.dao.DebugActionDAO;
import com.luretechnologies.server.data.dao.TerminalDAO;
import com.luretechnologies.server.data.display.tms.DebugActionDisplay;
import com.luretechnologies.server.data.model.Terminal;
import com.luretechnologies.server.data.model.tms.DebugAction;
import com.luretechnologies.server.service.DebugActionService;
import java.util.ArrayList;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author
 */
@Service
@Transactional
public class DebugActionServiceImpl implements DebugActionService {

    @Autowired
    EntityDAO entityDAO;
    
    @Autowired
    TerminalDAO terminalDAO;
    

    @Autowired
    DebugActionDAO debugActionDAO;

    @Override
    public List<DebugActionDisplay> search(String entityId, String filter, int pageNumber, int rowsPerPage ) throws Exception {

        try {
            Entity entity = entityDAO.findByEntityId(entityId);
            if (entity == null) {
                throw new ObjectRetrievalFailureException(Entity.class, entityId);
            }
            return search(entity.getId(), filter, pageNumber, rowsPerPage );
        } catch (Exception ex) {
        }
        return null;
    }

    @Override
    public List<DebugActionDisplay> search(Long entityId, String filter, int pageNumber, int rowsPerPage ) throws Exception {
        int firstResult = (((pageNumber - 1) >= 0) ? (pageNumber - 1) : 0) * rowsPerPage;
        List<DebugAction> debugActions =  debugActionDAO.search(entityId, filter, firstResult, firstResult + rowsPerPage );
        List<DebugActionDisplay> actionDisplays = null;
        if ( debugActions != null && debugActions.size() >0 ){
            actionDisplays = new ArrayList<>();
            for ( DebugAction temp: debugActions ){
                DebugActionDisplay actionDisplay = new DebugActionDisplay();
                 BeanUtils.copyProperties( temp, actionDisplay);
                 actionDisplay.setEntity(temp.getEntity().getId());
                 actionDisplays.add(actionDisplay);
            }
        }
        return actionDisplays;
    }

    @Override
    public DebugActionDisplay create(DebugActionDisplay actionDisplay) throws Exception {
        try {
            Terminal terminal = terminalDAO.findBySerialNumber(actionDisplay.getSerialNumber());
            if (terminal == null) {
                throw new ObjectRetrievalFailureException(Entity.class, actionDisplay.getSerialNumber());
            }
            DebugAction debugAction = new DebugAction();
            BeanUtils.copyProperties(  actionDisplay, debugAction);
            debugAction.setEntity(terminal);
            debugAction.setActive(true);
            debugAction.setOccurred(new Timestamp(System.currentTimeMillis()));
            debugActionDAO.persist(debugAction);
            actionDisplay.setId(debugAction.getId());
            actionDisplay.setEntity(terminal.getId());
        } catch (Exception ex) {
            throw ex;
        }
        return actionDisplay;
    }

    @Override
    public void delete(Long id) throws Exception {
        debugActionDAO.delete(id);
    }

    @Override
    public DebugActionDisplay update(DebugActionDisplay debugActionDisplay) throws Exception {
        try {
            DebugAction debugAction = debugActionDAO.findById(debugActionDisplay.getId());
            if (debugAction != null) {
                debugAction.setAvailable(debugActionDisplay.getAvailable());
                debugAction.setDebug(debugActionDisplay.getDebug());
                debugAction.setDescription(debugActionDisplay.getDescription());
                debugAction.setName(debugActionDisplay.getName());
                debugAction.setDebugDuration(debugActionDisplay.getDebugDuration());
                debugActionDAO.merge(debugAction);
                return debugActionDisplay;
            }
            return null;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
