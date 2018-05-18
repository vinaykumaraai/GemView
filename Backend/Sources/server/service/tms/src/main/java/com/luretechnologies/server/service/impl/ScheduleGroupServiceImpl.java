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
import com.luretechnologies.server.data.dao.ScheduleGroupDAO;
import com.luretechnologies.server.data.model.tms.ScheduleGroup;
import com.luretechnologies.server.service.ScheduleGroupService;
import java.util.List;
import org.springframework.beans.BeanUtils;
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
public class ScheduleGroupServiceImpl implements ScheduleGroupService {

    @Autowired
    private ScheduleGroupDAO scheduleGroupDAO;

    /**
     *
     * @param scheduleGroup
     * @return
     * @throws Exception
     */
    @Override
    public ScheduleGroup create(ScheduleGroup scheduleGroup) throws Exception {
        ScheduleGroup newScheduleGroup = new ScheduleGroup();

        // Copy properties from -> to
        BeanUtils.copyProperties(scheduleGroup, newScheduleGroup);

        scheduleGroupDAO.persist(newScheduleGroup);

        return newScheduleGroup;
    }

    /**
     *
     * @param id
     * @param scheduleGroup
     * @return
     * @throws Exception
     */
    @Override
    public ScheduleGroup update(long id, ScheduleGroup scheduleGroup) throws Exception {
        ScheduleGroup existentScheduleGroup = scheduleGroupDAO.findById(id);

        // Copy properties from -> to
        BeanUtils.copyProperties(scheduleGroup, existentScheduleGroup);

        return scheduleGroupDAO.merge(existentScheduleGroup);
    }

    /**
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void delete(long id) throws Exception {
        ScheduleGroup scheduleGroup = scheduleGroupDAO.findById(id);

        // Do not let delete the Default schedule group
        if (scheduleGroup.getId() == scheduleGroupDAO.getFirstResult().getId()) {
            throw new CustomException(Constants.CODE_CANNOT_BE_DELETED, Messages.CANNOT_BE_DELETED);
        }

        scheduleGroupDAO.delete(scheduleGroup.getId());
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ScheduleGroup get(long id) throws Exception {
        return scheduleGroupDAO.findById(id);
    }

    /**
     *
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<ScheduleGroup> list(int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return scheduleGroupDAO.list(firstResult, rowsPerPage);
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
    public List<ScheduleGroup> search(String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return scheduleGroupDAO.search(filter, firstResult, rowsPerPage);
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
        return Utils.getTotalPages(scheduleGroupDAO.search(filter, -1, -1).size(), rowsPerPage);
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
    public List<ScheduleGroup> list(String name, int pageNumber, int rowsPerPage) throws Exception {

        int firstResult = (pageNumber - 1) * rowsPerPage;

        if (name == null) {
            return scheduleGroupDAO.list(firstResult, rowsPerPage);
        } else {
            return scheduleGroupDAO.list(name, firstResult, rowsPerPage);
        }
    }
}
