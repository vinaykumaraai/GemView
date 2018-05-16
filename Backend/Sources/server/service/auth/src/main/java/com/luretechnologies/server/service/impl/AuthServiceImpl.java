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

import com.luretechnologies.common.Constants;
import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.dao.UserDAO;
import com.luretechnologies.server.data.model.User;
import com.luretechnologies.server.service.AuthService;
import java.sql.Timestamp;
import javax.ws.rs.NotAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDAO userDAO;

    /**
     *
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    private User validateCredentials(String username, String password) throws Exception {
        User user = userDAO.validateCredentials(username, Utils.encryptPassword(password));

        if (user == null) {
            throw new NotAuthorizedException(Constants.CODE_INVALID_USER, new Throwable());
        }
       
        return user;
    }

    /**
     *
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    @Override
    public User login(String username, String password) throws Exception {
        return validateCredentials(username, password);
    }

    /**
     *
     * @param user
     * @throws Exception
     */
    @Override
    public void logout(User user) throws Exception {

        User updateUser = userDAO.findById(user.getId());
        updateUser.setLogoutTime(new Timestamp(System.currentTimeMillis()));
        userDAO.merge(updateUser);
    }
}
