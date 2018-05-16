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
package com.luretechnologies.server.utils;

import com.luretechnologies.common.enums.PermissionEnum;
import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.dao.UserDAO;
import com.luretechnologies.server.data.model.Role;
import com.luretechnologies.server.data.model.tms.Email;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 *
 * @author developer
 */
@Transactional
public class UserAuthService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    /**
     *
     * @param user
     * @return
     * @throws Exception
     */
    public UserAuth loadUser(com.luretechnologies.server.data.model.User user) throws Exception {
        // Get granted authorities (User role permissions)
        List<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(user.getRole());

        UserAuth userAuth = buildUserForAuthentication(user, grantedAuthorities);

        detailsChecker.check(userAuth);

        return userAuth;
    }

    private UserAuth buildUserForAuthentication(com.luretechnologies.server.data.model.User user, List<GrantedAuthority> authorities) {
        String username = user.getUsername();
        String password = user.getPassword();

        boolean enabled = user.getActive();
        boolean accountNonExpired = user.getActive();
        boolean credentialsNonExpired = user.getActive();
        boolean accountNonLocked = user.getActive();

        return new UserAuth(user, username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    private List<GrantedAuthority> getGrantedAuthorities(Role role) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        // Set Role Permissions
        for (PermissionEnum permission : role.getPermissions()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(permission.toString()));
        }

        return grantedAuthorities;
    }

    /**
     *
     * @param username
     * @return
     */
    @Override
    public UserAuth loadUserByUsername(String username) {
        // User model
        com.luretechnologies.server.data.model.User user = userDAO.findByUsername(username);

        if (user == null) {
            throw new ObjectRetrievalFailureException(User.class, username);
        }

        // Get granted authorities (User role permissions)
        List<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(user.getRole());

        UserAuth userAuth = buildUserForAuthentication(user, grantedAuthorities);

        if (userAuth == null) {
            throw new ObjectRetrievalFailureException(User.class, username);
        }

        detailsChecker.check(userAuth);

        return userAuth;
    }
}
