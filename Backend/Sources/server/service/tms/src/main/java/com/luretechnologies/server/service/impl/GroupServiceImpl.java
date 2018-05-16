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
import com.luretechnologies.server.data.dao.GroupDAO;
import com.luretechnologies.server.data.model.tms.Group;
import com.luretechnologies.server.service.GroupService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 *
 * @author developer
 */
@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDAO groupDAO;

    /**
     *
     * @param group
     * @return
     * @throws Exception
     */
    @Override
    public Group save(Group group) throws Exception {
        groupDAO.persist(group);
        return group;
    }

    /**
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void delete(long id) throws Exception {
        // Do not let delete Default group
        if (id == groupDAO.getFirstResult().getId()) {
            throw new CustomException(Constants.CODE_CANNOT_BE_DELETED, Messages.CANNOT_BE_DELETED);
        }

        groupDAO.delete(id);
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Group get(long id) throws Exception {
        return groupDAO.findById(id);
    }

    /**
     *
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<Group> list(int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return groupDAO.list(firstResult, rowsPerPage);
    }

    /**
     *
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<Group> search(String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return groupDAO.search(filter, firstResult, rowsPerPage);
    }

    /**
     *
     * @param filter
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public int getSearchTotalPages(String filter, int rowsPerPage) throws Exception {
        return Utils.getTotalPages(groupDAO.search(filter, -1, -1).size(), rowsPerPage);
    }

    /**
     *
     * @param name
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<Group> list(String name, int pageNumber, int rowsPerPage) throws Exception {
        if (name == null) {
            return groupDAO.list(pageNumber, rowsPerPage);
        } else {
            return groupDAO.list(name, pageNumber, rowsPerPage);
        }
    }
}
