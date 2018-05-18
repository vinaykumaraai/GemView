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

import com.luretechnologies.common.enums.SettingEnum;
import com.luretechnologies.common.enums.SettingGroupEnum;
import com.luretechnologies.server.data.dao.SettingDAO;
import com.luretechnologies.server.data.model.payment.Setting;
import com.luretechnologies.server.service.SettingService;
import java.util.List;
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
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SettingDAO settingDAO;

    /**
     *
     * @param setting
     * @return
     * @throws Exception
     */
    @Override
    public Setting create(Setting setting) throws Exception {
        Setting newSetting = new Setting();

        // Copy properties from -> to
        BeanUtils.copyProperties(setting, newSetting);

        settingDAO.persist(setting);

        return setting;
    }

    /**
     *
     * @param setting
     * @return
     * @throws Exception
     */
    @Override
    public Setting update(Setting setting) throws Exception {
        Setting existentSetting = settingDAO.findByName(setting.getName());

        if (existentSetting == null) {
            throw new ObjectRetrievalFailureException(Setting.class, setting.getName());
        }
        // Copy properties from -> to
        BeanUtils.copyProperties(setting, existentSetting, new String[]{"name"});

        return settingDAO.merge(existentSetting);
    }

    /**
     *
     * @param name
     * @throws Exception
     */
    @Override
    public void delete(SettingEnum name) throws Exception {
        Setting setting = settingDAO.findByName(name);

        if (setting == null) {
            throw new ObjectRetrievalFailureException(Setting.class, name);
        }
        settingDAO.delete(setting.getId());
    }

    /**
     *
     * @param name
     * @return
     * @throws Exception
     */
    @Override
    public Setting get(com.luretechnologies.common.enums.SettingEnum name) throws Exception {
        // Get setting basic data and parent info
        return settingDAO.findByName(name);
    }

    /**
     *
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<Setting> list(int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return settingDAO.list(firstResult, rowsPerPage);
    }

    /**
     *
     * @param group
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<Setting> list(SettingGroupEnum group, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;

        if (group != null) {
            return settingDAO.list(group, firstResult, rowsPerPage);
        } else {
            return list(pageNumber, rowsPerPage);
        }
    }

}
