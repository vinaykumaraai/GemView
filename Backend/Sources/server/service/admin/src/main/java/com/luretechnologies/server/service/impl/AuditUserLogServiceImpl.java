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

import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.dao.AuditActionDAO;
import com.luretechnologies.server.data.dao.AuditUserLogDAO;
import com.luretechnologies.server.data.dao.AuditUserLogTypeDAO;
import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.dao.UserDAO;
import com.luretechnologies.server.data.model.AuditAction;
import com.luretechnologies.server.data.model.AuditUserLog;
import com.luretechnologies.server.data.model.AuditUserLogType;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.luretechnologies.server.service.AuditUserLogService;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.PersistenceException;
import org.hibernate.HibernateException;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author
 */
@Service
@Transactional
public class AuditUserLogServiceImpl implements AuditUserLogService {

    @Autowired
    AuditActionDAO auditActionDAO;
    @Autowired
    AuditUserLogTypeDAO auditUserLogTypeDAO;
    @Autowired
    EntityDAO entityDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    AuditUserLogDAO auditUserLogDAO;

    private static final String SUPER_USERNAME = "super";

    /**
     *
     * @param auditUserLogIn
     * @return
     * @throws Exception
     */
    @Override
    public AuditUserLog createAuditUserLog(AuditUserLog auditUserLogIn) throws Exception {
        auditUserLogDAO.persist(auditUserLogIn);
        return auditUserLogIn;
    }

    /**
     *
     * @return @throws Exception
     */
    @Override
    public Integer doForceUpdate(Integer value) throws Exception {
        Integer forceUpdateValue = auditUserLogDAO.doForceUpdate(value);
        return forceUpdateValue;

    }

    /**
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public List<AuditUserLog> getAuditUserLogList(List<Long> ids) throws Exception {
        List<AuditUserLog> auditUserLogList = auditUserLogDAO.getAuditUserLogList(ids);
        return auditUserLogList;

    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public AuditUserLog getAuditUserLogByID(Long id) throws Exception {
        AuditUserLog auditUserLog = auditUserLogDAO.getAuditUserLogByID(id);
        return auditUserLog;
    }

    @Override
    public AuditUserLog createAuditUserLog(Long userId, Long entityId, String type, String action, String details) throws Exception {

        Entity entity = null;
        try {
            entity = entityDAO.findById(entityId);
            if (entity == null) {
                return createAuditUserLog(userId, new String(), type, action, details);
            }
        } catch (Exception ex) {
            entity = null;
        }
        return createAuditUserLog(userId, ((entity != null) ? entity.getEntityId() : null), type, action, details);
    }

    @Override
    public AuditUserLog createAuditUserLog(Long userId, String entityId,
            String type, String action,
            String details) throws Exception {

        AuditUserLog auditUserLog = new AuditUserLog();
        Entity entity = null;

        try {
            User user = userDAO.findById(userId);
            if (user == null) {
                throw new ObjectRetrievalFailureException(User.class, userId);
            }
            auditUserLog.setUser(user);

            if (entityId != null && !entityId.isEmpty()) {
                entity = entityDAO.findByEntityId(entityId);
                if (entity == null) {
                    throw new ObjectRetrievalFailureException(Entity.class, userId);
                }
            }
            auditUserLog.setEntity(entity);

            AuditAction auditAction = (AuditAction) auditActionDAO.findByProperty("name", action).getSingleResult();
            if (auditAction == null) {
                throw new ObjectRetrievalFailureException(AuditAction.class, action);
            }
            auditUserLog.setAuditAction(auditAction);

            AuditUserLogType auditUserLogType = (AuditUserLogType) auditUserLogTypeDAO.findByProperty("name", type).getSingleResult();
            if (auditUserLogType == null) {
                throw new ObjectRetrievalFailureException(AuditUserLogType.class, type);
            }
            auditUserLog.setAuditUserLogType(auditUserLogType);

            java.sql.Timestamp timeStamp = new Timestamp(System.currentTimeMillis());

            auditUserLog.setDescription(auditAction.getDescription() + ", " + user.getFirstName() + " " + user.getLastName() + ", " + ((entity != null) ? entity.getDescription() : ""));

            auditUserLog.setDetails(details);

            auditUserLog.setDateAt(timeStamp);

            auditUserLogDAO.persist(auditUserLog);

        } catch (Exception ex) {
            throw new HibernateException(AuditUserLogType.class.toString() + ex.getMessage());
        }

        return auditUserLog;
    }

    @Override
    public List<AuditUserLog> search(Long userLongId, Long userId, String entityId, String filter, int pageNumber, int rowsPerPage, Date dateDrom, Date dateTo) throws Exception {
        int firstResult = (((pageNumber - 1) >= 0) ? (pageNumber - 1) : 0) * rowsPerPage;

        User userLongin;
        Long lEntityId;
        if (userLongId == null) {
            return null;
        }

        try {
            userLongin = userDAO.get(userLongId);
        } catch (PersistenceException ex) {
            userLongin = null;
        }

        if (userLongin != null && userLongin.getUsername().equals(SUPER_USERNAME)) {
            return auditUserLogDAO.search(userId, (entityId != null) ? Utils.decodeHashId(entityId) : null, filter, firstResult, firstResult + rowsPerPage, dateDrom, dateTo);
        } else if (userLongin != null) {
            userId = userLongin.getId();
            return auditUserLogDAO.search(userId, (entityId != null) ? Utils.decodeHashId(entityId) : null, filter, firstResult, firstResult + rowsPerPage, dateDrom, dateTo);
        } else {
            return null;
        }
    }

    @Override
    public void deleteLogs(Date date) throws Exception {
        auditUserLogDAO.delete(date);
    }

    @Override
    public void delete(long id) throws Exception {
        auditUserLogDAO.delete(id);
    }
}
