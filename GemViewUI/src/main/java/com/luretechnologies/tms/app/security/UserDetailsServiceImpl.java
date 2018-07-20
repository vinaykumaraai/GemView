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

package com.luretechnologies.tms.app.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.UserSession;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.luretechnologies.tms.backend.service.MockUserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final MockUserService userService;

	@Autowired
	public UserDetailsServiceImpl(MockUserService userService) {
		this.userService = userService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
//		System.out.println(RestServiceUtil.getInstance().login("vinay_standard", "TestPassword123!").getMaskedEmailAddress());
		//User user = userService.getUserByEmail(username);
			com.luretechnologies.client.restlib.service.model.User restUser = RestServiceUtil.getInstance().getClient().getUserApi().getUserByUserName(username);
			//restUser.get
//			user.setEmail(restUser.getEmail());
			if (null == restUser) {
				throw new UsernameNotFoundException("No user present with username: " + username);
			} else {
				return new org.springframework.security.core.userdetails.User(username, "",
						Collections.singletonList(new SimpleGrantedAuthority(restUser.getRole().toString())));
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new UsernameNotFoundException("No user present with username: " + username);
		}
		//FIXME: use rest service for geting user. convert response JSON using GSON api
		
	}
}