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
package com.luretechnologies.server.service;

import com.luretechnologies.common.enums.PermissionEnum;
import com.luretechnologies.server.data.model.Role;
import java.util.List;

/**
 *
 */
public interface RoleService {

    /**
     *
     * @param role
     * @return
     * @throws Exception
     */
    public Role create(Role role) throws Exception;

    /**
     *
     * @param id
     * @param role
     * @return
     * @throws Exception
     */
    public Role update(long id, Role role) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Role get(long id) throws Exception;

    /**
     *
     * @param roleName
     * @return
     * @throws Exception
     */
    public Role getByName(String roleName) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Role deleteRole(long id) throws Exception;

    /**
     *
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    public List<Role> list(int pageNumber, int rowsPerPage) throws Exception;

    /**
     *
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    public List<Role> search(String filter, int pageNumber, int rowsPerPage) throws Exception;

    /**
     *
     * @param filter
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    public int getSearchTotalPages(String filter, int rowsPerPage) throws Exception;

    /**
     *
     * @param id
     * @param permission
     * @return
     * @throws Exception
     */
    public Role addPermission(long id, PermissionEnum permission) throws Exception;

    /**
     *
     * @param id
     * @param permission
     * @return
     * @throws Exception
     */
    public Role removePermission(long id, PermissionEnum permission) throws Exception;
}
