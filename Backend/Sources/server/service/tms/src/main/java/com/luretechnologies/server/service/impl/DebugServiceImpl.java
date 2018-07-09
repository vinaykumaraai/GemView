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

import com.luretechnologies.server.data.dao.DebugDAO;
import com.luretechnologies.server.data.dao.DebugItemDAO;
import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.dao.TerminalDAO;
import com.luretechnologies.server.data.display.tms.DebugDisplay;
import com.luretechnologies.server.data.display.tms.DebugItemDisplay;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.Terminal;
import com.luretechnologies.server.data.model.tms.Debug;
import com.luretechnologies.server.data.model.tms.DebugItem;
import com.luretechnologies.server.service.DebugService;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
public class DebugServiceImpl implements DebugService {

    @Autowired
    EntityDAO entityDAO;

    @Autowired
    DebugDAO debugDAO;

    @Autowired
    DebugItemDAO debugItemDAO;

    @Autowired
    TerminalDAO terminalDAO;

    @Override
    public List<DebugDisplay> search(String entityId, int pageNumber, int rowsPerPage) throws Exception {

        try {
            Entity entity = entityDAO.findByEntityId(entityId);
            if (entity == null) {
                throw new ObjectRetrievalFailureException(Entity.class, entityId);
            }
            return search(entity.getId(), pageNumber, rowsPerPage);
        } catch (Exception ex) {
        }
        return null;
    }

    @Override
    public List<DebugDisplay> search(Long entityId, int pageNumber, int rowsPerPage) throws Exception {

        int firstResult = (((pageNumber - 1) >= 0) ? (pageNumber - 1) : 0) * rowsPerPage;
        List<Debug> debugs = debugDAO.search(entityId, firstResult, firstResult + rowsPerPage);
        List<DebugDisplay> itemDisplays = new ArrayList<>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (debugs != null && debugs.size() > 0) {
            for (Debug debug : debugs) {
                DebugDisplay debugDisplay = new DebugDisplay();
                debugDisplay.setSerialNumber(((Terminal) debug.getEntity()).getSerialNumber());
                debugDisplay.setDebugItems(new ArrayList<DebugItemDisplay>());
                debugDisplay.setId(debug.getId());

                if (debug.getDebugItems() != null && debug.getDebugItems().size() > 0) {
                    for (DebugItem debugItem : debug.getDebugItems()) {
                        DebugItemDisplay debugItemDisplay = new DebugItemDisplay();
                        debugItemDisplay.setComponent(debugItem.getComponent());
                        debugItemDisplay.setMessage(debugItem.getMessage());
                        debugItemDisplay.setLevel(debugItem.getLevel());
                        debugItemDisplay.setId(debugItem.getId());
                        debugItemDisplay.setOccurred(format.format(new Date(debugItem.getOccurred().getTime())));
                        debugDisplay.getDebugItems().add(debugItemDisplay);
                    }
                }
                itemDisplays.add(debugDisplay);
            }
        } else {
            return null;
        }
        return itemDisplays;
    }

    @Override
    public DebugDisplay create(DebugDisplay debugDisplay) throws Exception {

        Debug debug = new Debug();

        try {
            Terminal terminal = terminalDAO.findBySerialNumber(debugDisplay.getSerialNumber());
            if (terminal == null) {
                throw new ObjectRetrievalFailureException(Entity.class, debugDisplay.getSerialNumber());
            }
            // Set debug to false if teh expiration date 
            if ( terminal.getDebugExpirationDate().before(new Timestamp(System.currentTimeMillis()))){
                terminal.setDebugActive(Boolean.FALSE);
                terminalDAO.merge(terminal);
            }
            debug.setEntity(terminal);
            debugDAO.persist(debug);

            if (debugDisplay.getDebugItems() != null && debugDisplay.getDebugItems().size() > 0) {
                for (DebugItemDisplay temp : debugDisplay.getDebugItems()) {
                    DebugItem debugItem = new DebugItem();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Timestamp dt;
                    try {
                        dt = new Timestamp(format.parse(temp.getOccurred()).getTime());
                    } catch (Exception ex) {
                        throw new Exception("Date format is: yyyy-MM-dd HH:mm:ss " + ex.getMessage());
                    }
                    debugItem.setMessage(temp.getMessage());
                    debugItem.setLevel(temp.getLevel());
                    debugItem.setComponent(temp.getComponent());
                    debugItem.setOccurred(dt);
                    debugItem.setEntity(terminal);
                    debugItem.setDebug(debug);
                    debugItemDAO.persist(debugItem);
                    temp.setId(debugItem.getId());
                }
            }
        } catch (Exception ex) {
            throw new PersistenceException("Creating debug error " + ex.getMessage());
        }
        return debugDisplay;
    }

    /**
     *
     * @param terminalId
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @param dateDrom
     * @param dateTo
     * @return
     * @throws Exception
     */
    @Override
    public List<DebugItemDisplay> searchDebugItems(String terminalId, String filter, int pageNumber, int rowsPerPage, Date dateDrom, Date dateTo) throws Exception {
        int firstResult = (((pageNumber - 1) >= 0) ? (pageNumber - 1) : 0) * rowsPerPage;
        Entity terminal;
        try {
            terminal = terminalDAO.findByTerminalId(terminalId);
            if (terminal == null) {
                throw new ObjectRetrievalFailureException(Entity.class, terminalId);
            }
        } catch (Exception ex) {
            throw new ObjectRetrievalFailureException(Entity.class, terminalId);
        }
        List<DebugItem> debugs = debugItemDAO.search(terminal.getId(), filter, firstResult, firstResult + rowsPerPage, dateDrom, dateTo);
        List<DebugItemDisplay> debugItemDisplays = new ArrayList<>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (debugs != null && debugs.size() > 0) {
            for (DebugItem debugItem : debugs) {
                DebugItemDisplay debugItemDisplay = new DebugItemDisplay();
                debugItemDisplay.setComponent(debugItem.getComponent());
                debugItemDisplay.setMessage(debugItem.getMessage());
                debugItemDisplay.setLevel(debugItem.getLevel());
                debugItemDisplay.setId(debugItem.getId());
                debugItemDisplay.setOccurred(format.format(new Date(debugItem.getOccurred().getTime())));
                debugItemDisplays.add(debugItemDisplay);
            }
        }
        return debugItemDisplays;
    }

    @Override
    public void delete(Long id) throws Exception {
        debugDAO.delete(id);
    }

    @Override
    public void deleteDebugItem(Long id) throws Exception {
        debugItemDAO.delete(id);
    }

}
