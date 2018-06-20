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
import com.luretechnologies.common.enums.TwoFactorFreqEnum;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.common.utils.exceptions.CustomException;
import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.dao.RoleDAO;
import com.luretechnologies.server.data.dao.UserDAO;
import com.luretechnologies.server.data.dao.UserLastPasswordDAO;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.Role;
import com.luretechnologies.server.data.model.User;
import com.luretechnologies.server.data.model.UserLastPassword;
import com.luretechnologies.server.data.model.tms.Email;
import com.luretechnologies.server.jms.utils.CorrelationIdPostProcessor;
import com.luretechnologies.server.service.UserService;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.PersistenceException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Autowired
    private UserLastPasswordDAO userLastPasswordDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    @Qualifier("jmsEmailTemplate")
    JmsTemplate jmsEmailTemplate;

    static final int PASSWORD_MIN_LEN = 8;
    static final int PASSWORD_MAX_LEN = 64;
    static final boolean PASSWORD_ALLOW_UPPERCASE = true;
    static final boolean PASSWORD_REQUIRE_UPPERCASE = true;
    static final boolean PASSWORD_ALLOW_LOWERCASE = true;
    static final boolean PASSWORD_REQUIRE_LOWERCASE = true;
    static final boolean PASSWORD_ALLOW_NUMBER = true;
    static final boolean PASSWORD_REQUIRE_NUMBER = true;
    static final boolean PASSWORD_ALLOW_SYMBOL = true;
    static final boolean PASSWORD_REQUIRE_SYMBOL = true;
    static final boolean PASSWORD_ALLOW_SPACE = true;
    static final boolean PASSWORD_REQUIRE_SPACE = false;

    /**
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public User create(User user) throws Exception {

        Entity entity = user.getEntity();
        UserLastPassword userLastPassword = new UserLastPassword();

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

        // send the temporary password to user.
        String tempPassword = Utils.generatePassword(PASSWORD_MIN_LEN);
        user.setPassword(Utils.encryptPassword(tempPassword));
        user.setLogoutTime(new Timestamp(System.currentTimeMillis()));

        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = format.parse("19800101");
        user.setTwoFactorAt(new Timestamp(date.getTime()));
        user.setTwoFactorFrequency(TwoFactorFreqEnum.OFF);
        user.setActive(true);

        if (user.getAvailable() == null) {
            user.setAvailable(true);
        }
        if (user.getPasswordFrequency() == null || user.getPasswordFrequency() > 0) {
            user.setPasswordFrequency(30); // 30 days
        }
        userDAO.persist(user);

        sendTemporaryPassword(user, tempPassword);

        userLastPassword.setUser(user);
        userLastPassword.setPassword1(user.getPassword());
        userLastPassword.setPassword2(user.getPassword());
        userLastPassword.setPassword3(user.getPassword());
        userLastPassword.setPassword4(user.getPassword());
        userLastPassword.setPassword5(user.getPassword());
        userLastPasswordDAO.persist(userLastPassword);

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
        UserLastPassword existentUserLastPassword = userLastPasswordDAO.findByUserId(id);
        List<String> listPasw;

        if (user.getPassword() == null) {
            user.setPassword(existentUser.getPassword());
        } else {

            if (!Utils.enforcePasswordComplexity(user.getPassword(),
                    PASSWORD_MIN_LEN, PASSWORD_MAX_LEN,
                    PASSWORD_ALLOW_UPPERCASE, PASSWORD_REQUIRE_UPPERCASE,
                    PASSWORD_ALLOW_LOWERCASE, PASSWORD_REQUIRE_LOWERCASE,
                    PASSWORD_ALLOW_NUMBER, PASSWORD_REQUIRE_NUMBER,
                    PASSWORD_ALLOW_SPACE, PASSWORD_REQUIRE_SPACE,
                    PASSWORD_ALLOW_SYMBOL, PASSWORD_REQUIRE_SYMBOL)) {
                throw new Exception("A password must be eight characters including one uppercase letter, one special character and alphanumeric characters");
            }

            user.setPassword(Utils.encryptPassword(user.getPassword()));
            user.setRequirePasswordUpdate(false);
            // TODO - set password limit as configurable
            user.setPasswordExpiration(new Timestamp(Utils.fromNow(Utils.PASSWORD_TIME_LIMIT)));

            // Check password existence
            if (existentUserLastPassword != null) {
                listPasw = new ArrayList<>();
                listPasw.add(existentUserLastPassword.getPassword1());
                listPasw.add(existentUserLastPassword.getPassword2());
                listPasw.add(existentUserLastPassword.getPassword3());
                listPasw.add(existentUserLastPassword.getPassword4());
                listPasw.add(existentUserLastPassword.getPassword5());

                if (listPasw.contains(user.getPassword())) {
                    throw new Exception("The password was used on the last five password of the user");
                } else {
                    for (int i = listPasw.size() - 1; i >= 0; i--) {
                        if (i != 0) {
                            listPasw.set(i, listPasw.get(i - 1));
                        }
                        if (i == 0) {
                            listPasw.set(i, user.getPassword());
                        }
                    }
                    existentUserLastPassword.setPassword1(listPasw.get(0));
                    existentUserLastPassword.setPassword2(listPasw.get(1));
                    existentUserLastPassword.setPassword3(listPasw.get(2));
                    existentUserLastPassword.setPassword4(listPasw.get(3));
                    existentUserLastPassword.setPassword5(listPasw.get(4));
                    userLastPasswordDAO.merge(existentUserLastPassword);
                }
            }
        }

        Entity entity = user.getEntity();

        // Check entity existence
        if (entity != null) {
            Entity existentEntity = entityDAO.findById(user.getEntity().getId());

            if (existentEntity == null) {
                throw new ObjectRetrievalFailureException(Entity.class, user.getEntity().getEntityId());
            }

            user.setEntity(existentEntity);
        } else {
            // If not Entity defined set Default (Enterprise)
            user.setEntity(entityDAO.getFirstResult());
        }

        // Copy properties from -> to
        //BeanUtils.copyProperties(user, existentUser);
        existentUser.setAssignedIP(user.getAssignedIP());
        if (user.getAvailable() != null) {
            existentUser.setAvailable(user.getAvailable());
        }
        existentUser.setFirstName(user.getFirstName());
        existentUser.setLastName(user.getLastName());
        existentUser.setLastAccessIP(user.getLastAccessIP());
        // update user Role
        if (user.getRole() != null && existentUser.getRole().getId() != user.getRole().getId()) {
            try {
                Role role = roleDAO.findById(user.getRole().getId());
                if (role != null) {
                    existentUser.setRole(role);
                }
            } catch (Exception ex) {
                ex.getMessage();
            }
        }
        if (user.getPasswordFrequency() != null && user.getPasswordFrequency() > 0) {
            existentUser.setPasswordFrequency(user.getPasswordFrequency()); // 30 days
        }

        existentUser.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        try {
            User user2 = userDAO.merge(existentUser);
            return user2;
        } catch (Exception ex) {
            return null;
        }
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
        if (user.getActive() == true) {
            user.setActive(false);
            user.setUsername(user.getUsername() + "_" + Long.toString(System.currentTimeMillis()));
            user.setEmail(Long.toString(System.currentTimeMillis()) + "_" + user.getEmail());
            userDAO.merge(user);
        }
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
        int firstResult = (((pageNumber - 1) >= 0) ? (pageNumber - 1) : 0) * rowsPerPage;
        return userDAO.list(entity, firstResult, firstResult + rowsPerPage);
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
        int firstResult = (((pageNumber - 1) >= 0) ? (pageNumber - 1) : 0) * rowsPerPage;
        return userDAO.search(entity, filter, firstResult, firstResult + rowsPerPage);
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
     * @param name
     * @param available
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<User> list(Entity entity, String name, Boolean available, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (((pageNumber - 1) >= 0) ? (pageNumber - 1) : 0) * rowsPerPage;
        return userDAO.list(entity, name, available, firstResult, firstResult + rowsPerPage);
    }

    /**
     *
     * @param emailId
     * @return
     * @throws Exception
     */
    @Override
    public User findUserByEmailId(String emailId) throws Exception {
        return userDAO.findUserByEmailId(emailId);
    }

    /**
     *
     * @param user
     */
    @Override
    public void sendUsername(User user) {

        Email email = new Email();

        String fromName = "DoNotReply";
        String subject = "Username Reminder";

        String bodyMessage
                = "Dear Member," + "<br>"
                + "<br>"
                + "For Security Reasons, we have sent your username in this email." + "<br>"
                + "<br>"
                + "Username: " + user.getUsername() + "<br>"
                + "<br>"
                + "Click " + "<a href=" + "http://www.gemstonepay.com/gem/" + "> here</a> to login." + "<br>"
                + "<br>";

        email.setBody(bodyMessage);
        email.setContentType("text/html");
        email.setTo(user.getEmail());
        email.setSubject(subject);
        email.setFromName(fromName);

        jmsEmailTemplate.convertAndSend(email, new CorrelationIdPostProcessor(Utils.generateGUID()));
    }

    @Override
    public void sendTemporaryPassword(User user) {
        sendTemporaryPassword(user, Utils.generatePassword(8));
    }

    @Override
    public void sendTemporaryPassword(User user, String tempPassword) {

        Email email = new Email();
        User updateUser = userDAO.findById(user.getId());

        String fromName = "DoNotReply";
        String subject = "Temporary Password";

        String bodyMessage
                = "Dear Member," + "<br>"
                + "<br>"
                + "For Security Reasons, we have sent you a new temporary password." + "<br>"
                + "<br>"
                + "Temporary Password: " + tempPassword + "<br>"
                + "<br>"
                + "Click " + "<a href=" + "http://www.gemstonepay.com/gem/" + "> here</a> to create login." + "<br>"
                + "<br>"
                + "Important note:"
                + "<br>"
                + "The link above will only work once. If you clicked on the link but didn't create a new password, click" + "<a href=" + "http://www.gemstonepay.com/gem/" + "> here</a> to request another email like this one.";

        email.setBody(bodyMessage);
        email.setContentType("text/html");
        email.setTo(user.getEmail());
        email.setSubject(subject);
        email.setFromName(fromName);

        updateUser.setRequirePasswordUpdate(true);
        updateUser.setPassword(Utils.encryptPassword(tempPassword));
        updateUser.setPasswordExpiration(new Timestamp(System.currentTimeMillis()));
        userDAO.persist(updateUser);

        jmsEmailTemplate.convertAndSend(email, new CorrelationIdPostProcessor(Utils.generateGUID()));
    }

    @Override
    public User updatePassword(User user, String tempPassword, String newPassword) throws Exception {
        User updateUser = userDAO.findById(user.getId());
        if (!updateUser.getPassword().equalsIgnoreCase(Utils.encryptPassword(tempPassword))) {
            throw new Exception("Failed to authenticate.");
        } else {
            updateUser.setPassword(newPassword);
            return update(updateUser.getId(), updateUser);
        }
    }
}
