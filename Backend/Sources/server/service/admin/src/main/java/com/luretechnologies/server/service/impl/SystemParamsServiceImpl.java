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

import com.luretechnologies.server.data.dao.SystemParamsDAO;
import com.luretechnologies.server.data.dao.SystemParamsTypeDAO;
import com.luretechnologies.server.data.model.SystemParam;
import com.luretechnologies.server.data.model.SystemParamType;

import com.luretechnologies.server.service.SystemParamsService;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author
 */
@Service
@Transactional
public class SystemParamsServiceImpl implements SystemParamsService {

    @Autowired
    SystemParamsDAO systemParamsDAO;

    @Autowired
    SystemParamsTypeDAO systemParamsTypeDAO;

    @Override
    public List<SystemParam> search(String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (((pageNumber - 1) >= 0) ? (pageNumber - 1) : 0) * rowsPerPage;
        return systemParamsDAO.search(filter, firstResult, firstResult + rowsPerPage);
    }

    @Override
    public SystemParam create(SystemParam systemParam) throws Exception {
        try {
            SystemParam systemParamDataBase = null;
            try {
                systemParamDataBase = getByName(systemParam.getName());
            } catch (Exception ex) {

            }
            if (systemParamDataBase != null) {
                systemParam.setId(systemParamDataBase.getId());
                return update(systemParam);
            }
            SystemParamType systemParamType = null;
            systemParam.setActive(true);
            systemParam.setAvailable(true);
            systemParam.setOccurred(new Timestamp(System.currentTimeMillis()));

            try {
                systemParamType = systemParamsTypeDAO.getByName(systemParam.getSystemParamType().getName());
            } catch (Exception exception) {
            }
            if (systemParamType == null) {
                systemParamType = new SystemParamType();
                systemParamType.setName(systemParam.getSystemParamType().getName());
                systemParamType.setDescription(systemParam.getSystemParamType().getName());
                systemParamsTypeDAO.persist(systemParamType);
            }
            systemParam.setSystemParamType(systemParamType);
            systemParamsDAO.persist(systemParam);
        } catch (Exception ex) {
            return null;
        }
        return systemParam;
    }

    @Override
    public void delete(Long id) throws Exception {
        systemParamsDAO.delete(id);
    }

    @Override
    public SystemParam update(SystemParam systemParam) throws Exception {
        try {
            SystemParam systemParamModel = systemParamsDAO.findById(systemParam.getId());
            if (systemParamModel != null) {
                if ( systemParam.getAvailable()!= null) systemParamModel.setAvailable(systemParam.getAvailable());
                systemParamModel.setDescription(systemParam.getDescription());
                systemParamModel.setValue(systemParam.getValue());
                systemParamModel.setOccurred(new Timestamp(System.currentTimeMillis()));
                return systemParamsDAO.merge(systemParamModel);
            }
            return null;
        } catch (Exception ex) {
        }
        return null;
    }

    @Override
    public SystemParam get(Long id) throws Exception {
        return systemParamsDAO.findById(id);
    }

    @Override
    public SystemParam getByName(String name) throws Exception {
        return systemParamsDAO.getByName(name);
    }

    @Override
    public List<SystemParamType> getSystemParamsTypes() throws Exception {
        return systemParamsTypeDAO.search(null, 0, 300);
    }

    @Override
    public SystemParam create(String name, String description, String value, String systemParamsType) throws Exception {
        try {
            SystemParamType paramType = new SystemParamType();
            paramType.setName(systemParamsType);
            SystemParam systemParam = new SystemParam();
            systemParam.setDescription(description);
            systemParam.setName(name);
            systemParam.setValue(value);
            systemParam.setSystemParamType(paramType);
            return create(systemParam);
        } catch (Exception ex) {
            return null;
        }
    }
}
