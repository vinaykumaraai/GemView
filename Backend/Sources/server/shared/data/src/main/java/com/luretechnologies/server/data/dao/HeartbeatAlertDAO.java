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
package com.luretechnologies.server.data.dao;

import com.luretechnologies.server.data.model.tms.HeartbeatAlert;
import java.util.Date;
import java.util.List;
import javax.persistence.PersistenceException;

/**
 *
 * @author
 */
public interface HeartbeatAlertDAO extends BaseDAO<HeartbeatAlert, Long> {

    /**
     *
     * @param entityId
     * @param label
     * @param component
     * @return
     * @throws PersistenceException
     */
    public HeartbeatAlert getByLabelAndEntity(Long entityId, String label, String component) throws PersistenceException;

    /**
     *
     * @param entityId
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @param dateFrom
     * @param dateTo
     * @return
     * @throws PersistenceException
     */
    public List<HeartbeatAlert> search(Long entityId, String filter, int pageNumber, int rowsPerPage, Date dateFrom, Date dateTo) throws PersistenceException;

    /**
     * Get the list of alerts using terminalId, component, label
     *
     * @param TerminalID Terminal ID
     * @param component Component name
     * @param label Alert label
     * @return A list of alert otherwise null
     * @throws PersistenceException
     */
    public List<HeartbeatAlert> getAlerts(Long TerminalID, String component, String label) throws PersistenceException;

    /**
     * A alert was sent to the client
     * @param id Heartbeat alert identifier
     * @throws PersistenceException
     */
    public void alertDone(Long id) throws PersistenceException;

}
