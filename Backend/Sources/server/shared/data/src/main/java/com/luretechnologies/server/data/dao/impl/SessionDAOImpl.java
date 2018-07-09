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
package com.luretechnologies.server.data.dao.impl;

import com.luretechnologies.common.Constants;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.dao.SessionDAO;
import com.luretechnologies.server.data.model.Session;
import com.luretechnologies.server.data.model.User;
import java.sql.Timestamp;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.ws.rs.NotAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class SessionDAOImpl extends BaseDAOImpl<Session, Long> implements SessionDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionDAO.class);

    @Override
    public Session getByUser(User user) {
        try {
            return (Session) findByProperty("user", user).getSingleResult();
        } catch (NoResultException | NullPointerException e) {
            LOGGER.info("User session not found.", e);
            return null;
        } catch (Exception e) {
            LOGGER.info("User session not found.", e);
            return null;
        }
    }

    @Override
    public Session getByToken(String token) throws PersistenceException {
        try {
            return (Session) findByProperty("token", Utils.encryptPassword(token)).getSingleResult();
        } catch (NoResultException | NullPointerException e) {
            LOGGER.info("Token session not found.", e);
            return null;
        } catch (Exception e) {
            LOGGER.info("Token session not found.", e);
            return null;
        }
    }

    @Override
    public void deleteSession(String token) {
        LOGGER.info("Deleting user session.");
        Session session = getByToken(token);
        if (session != null) {
            delete(session);
        }
    }

    @Override
    public Session storeSession(User user, String token) throws Exception {

        Session session = getByUser(user);
        boolean newSession = (session == null);

        if (newSession) {
            session = new Session();
            session.setUser(user);
        }

        session.setToken(Utils.encryptPassword(token));
        session.setSessionInfo(Constants.CODE_SESSION_VALID);
        session.setLastUpdated(new Timestamp(Utils.fromNow(Utils.USER_SESSION_TIME_LIMIT)));

        if (newSession) {
            LOGGER.info("Storing user session.");
            persist(session);
        } else {
            LOGGER.info("Updating user session.");
            session = merge(session);
        }

        return session;
    }

    @Override
    public Session validateSession(String token) throws PersistenceException {
        LOGGER.info("Validating user session. " + token);
        Session session = getByToken(token);
        if (Utils.isCurrent(session.getLastUpdated().getTime())) {
            session.setSessionInfo(Constants.CODE_SESSION_VALID);
            session.setLastUpdated(new Timestamp(Utils.fromNow(Utils.USER_SESSION_TIME_LIMIT)));
            merge(session);
            return session;
        } else {
            throw new NotAuthorizedException(Messages.EXPIRED_TOKEN, new Throwable());
        }
    }
}
