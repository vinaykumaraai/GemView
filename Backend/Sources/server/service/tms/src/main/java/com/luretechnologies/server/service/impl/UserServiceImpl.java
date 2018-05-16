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
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.common.utils.exceptions.CustomException;
import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.dao.UserDAO;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.User;
import com.luretechnologies.server.service.UserService;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.PersistenceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EntityDAO entityDAO;

    /**
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public User create(User user) throws Exception {

        Entity entity = user.getEntity();

        // Check entity existence
        if (entity != null) {
            Entity existentEntity = entityDAO.findByEntityId(user.getEntity().getEntityId());

            if (existentEntity == null) {
                throw new ObjectRetrievalFailureException(Entity.class, user.getEntity().getEntityId());
            }

            if (user.getRole().getName().equalsIgnoreCase("SUPER_ADMINISTRATOR")) {
                throw new Exception("Super administrator already exists");
            }

            user.setEntity(existentEntity);
        } else {
            // If not Entity defined set Default (Enterprise)
            user.setEntity(entityDAO.getFirstResult());
        }

        if (user.getPassword() != null) {
            user.setPassword(Utils.encryptPassword(user.getPassword()));
        }

        user.setLogoutTime(new Timestamp(System.currentTimeMillis()));
        userDAO.persist(user);

        return user;
    }

    /**
     *
     * @param id
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public User update(long id, User user) throws Exception {

        User existentUser = userDAO.findById(id);

        if (user.getPassword() == null) {
            user.setPassword(existentUser.getPassword());
        } else {
            user.setPassword(Utils.encryptPassword(user.getPassword()));
        }

        Entity entity = user.getEntity();

        // Check entity existence
        if (entity != null) {
            Entity existentEntity = entityDAO.findByEntityId(user.getEntity().getEntityId());

            if (existentEntity == null) {
                throw new ObjectRetrievalFailureException(Entity.class, user.getEntity().getEntityId());
            }

            user.setEntity(existentEntity);
        } else {
            // If not Entity defined set Default (Enterprise)
            user.setEntity(entityDAO.getFirstResult());
        }

        // Copy properties from -> to
        BeanUtils.copyProperties(user, existentUser);

        return userDAO.merge(existentUser);
    }

    /**
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void delete(long id) throws Exception {
        User user = userDAO.findById(id);

        // Do not let delete the Admin user
        if (user.getId() == userDAO.getFirstResult().getId()) {
            throw new CustomException(Constants.CODE_CANNOT_BE_DELETED, Messages.CANNOT_BE_DELETED);
        }

        userDAO.delete(user.getId());
    }

    /**
     *
     * @param id
     * @return
     * @throws PersistenceException
     */
    @Override
    public User get(long id) throws PersistenceException {
        return userDAO.get(id);
    }

    /**
     *
     * @param username
     * @return
     * @throws PersistenceException
     */
    @Override
    public User getByUserName(String username) throws PersistenceException {
        return userDAO.getByUserName(username);
    }

    /**
     *
     * @param entity
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<User> list(Entity entity, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return userDAO.list(entity, firstResult, rowsPerPage);
    }

    /**
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @Override
    public List<User> list(Entity entity) throws Exception {
        return userDAO.list(entity);
    }

    /**
     *
     * @param entity
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<User> search(Entity entity, String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return userDAO.search(entity, filter, firstResult, rowsPerPage);
    }

    /**
     *
     * @param entity
     * @param filter
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public int getSearchTotalPages(Entity entity, String filter, int rowsPerPage) throws Exception {
        return Utils.getTotalPages(userDAO.search(entity, filter, -1, -1).size(), rowsPerPage);
    }

    /**
     *
     * @param entity
     * @param username
     * @param firstname
     * @param lastname
     * @param active
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<User> list(Entity entity, String username, String firstname, String lastname, Boolean active, int pageNumber, int rowsPerPage) throws Exception {

        int firstResult = (pageNumber - 1) * rowsPerPage;
        if (username == null && firstname == null && lastname == null && active == null) {

            return userDAO.list(entity, firstResult, rowsPerPage);
        } else {
            return userDAO.list(entity, username, firstname, lastname, active, firstResult, rowsPerPage);
        }
    }
    
     /**
     *
     * @param emailId
     * @return
     * @throws Exception
     */
    @Override
    public User findUserByEmailId(String emailId) throws Exception{
        return userDAO.findUserByEmailId(emailId);
    }

}
