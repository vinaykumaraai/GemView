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
import com.luretechnologies.common.enums.PermissionEnum;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.common.utils.exceptions.CustomException;
import com.luretechnologies.server.data.dao.RoleDAO;
import com.luretechnologies.server.data.model.Role;
import com.luretechnologies.server.service.RoleService;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDAO roleDAO;

    /**
     *
     * @param role
     * @return
     * @throws Exception
     */
    @Override
    public Role create(Role role) throws Exception {
        Role newRole = new Role();

        // Copy properties from -> to
        BeanUtils.copyProperties(role, newRole);

        return roleDAO.merge(newRole);
    }

    /**
     *
     * @param id
     * @param role
     * @return
     * @throws Exception
     */
    @Override
    public Role update(long id, Role role) throws Exception {
        Role existentRole = roleDAO.findById(id);

        // Copy properties from -> to
        BeanUtils.copyProperties(role, existentRole);

        return roleDAO.merge(existentRole);
    }

    /**
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void delete(long id) throws Exception {
        Role role = roleDAO.findById(id);

        // Do not let delete the Admin role
        if (role.getId() == roleDAO.getFirstResult().getId()) {
            throw new CustomException(Constants.CODE_CANNOT_BE_DELETED, Messages.CANNOT_BE_DELETED);
        }

        roleDAO.delete(role.getId());
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Role get(long id) throws Exception {
        return roleDAO.findById(id);
    }

    /**
     *
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<Role> list(int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return roleDAO.list(firstResult, rowsPerPage);
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
    public List<Role> search(String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return roleDAO.search(filter, firstResult, rowsPerPage);
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
        return Utils.getTotalPages(roleDAO.search(filter, -1, -1).size(), rowsPerPage);
    }

    /**
     *
     * @param id
     * @param permission
     * @return
     * @throws Exception
     */
    @Override
    public Role addPermission(long id, PermissionEnum permission) throws Exception {
        if(permission.equals(PermissionEnum.SUPER)) {
            throw new Exception("Super permission cannot be added");
        }
        Role role = roleDAO.findById(id);
        role.getPermissions().add(permission);

        return roleDAO.merge(role);
    }

    /**
     *
     * @param idRole
     * @param permission
     * @return
     * @throws Exception
     */
    @Override
    public Role removePermission(long idRole, PermissionEnum permission) throws Exception {
        Role role = roleDAO.findById(idRole);
        role.getPermissions().remove(permission);

        return roleDAO.merge(role);
    }
}
