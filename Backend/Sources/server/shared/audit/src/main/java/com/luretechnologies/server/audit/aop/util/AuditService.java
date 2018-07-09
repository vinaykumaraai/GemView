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
package com.luretechnologies.server.audit.aop.util;

import com.luretechnologies.common.enums.ActionEnum;
import com.luretechnologies.server.data.dao.AuditLogDAO;
import com.luretechnologies.server.data.dao.TerminalDAO;
import com.luretechnologies.server.data.display.tms.DataPackage;
import com.luretechnologies.server.data.model.AuditLog;
import com.luretechnologies.server.data.model.Terminal;
import java.sql.Timestamp;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 *
 * @author developer
 */
@Service
@Transactional
public class AuditService {

    @Autowired
    private TerminalDAO terminalDAO;

    @Autowired
    private AuditLogDAO auditLogDAO;

    /**
     *
     * @param serialNumber
     * @return
     */
    public Terminal getTerminal(String serialNumber) {
        Terminal terminal = terminalDAO.findBySerialNumber(serialNumber);

        if (terminal == null) {
            throw new ObjectRetrievalFailureException(Terminal.class, serialNumber);
        }

        return terminal;
    }

    /**
     *
     * @param terminal
     * @param dataPackage
     */
    public void saveAuditRequest(Terminal terminal, DataPackage dataPackage) {
        Calendar c = Calendar.getInstance();
        Timestamp time = new Timestamp(c.getTimeInMillis());

        AuditLog request_auditLog = new AuditLog();
        request_auditLog.setTerminal(terminal);
        request_auditLog.setDetails(dataPackage.getData());
        request_auditLog.setAction(ActionEnum.REQUEST);
        request_auditLog.setOccurredAt(time);
        auditLogDAO.merge(request_auditLog);
    }

    /**
     *
     * @param terminal
     * @param resultPackage
     */
    public void saveAuditResponse(Terminal terminal, DataPackage resultPackage) {
        Calendar c = Calendar.getInstance();
        Timestamp time = new Timestamp(c.getTimeInMillis());

        AuditLog response_auditLog = new AuditLog();
        response_auditLog.setTerminal(terminal);
        response_auditLog.setDetails(resultPackage.getData());
        response_auditLog.setAction(ActionEnum.RESPONSE);
        response_auditLog.setOccurredAt(time);
        auditLogDAO.merge(response_auditLog);
    }
}
