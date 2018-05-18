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
package com.luretechnologies.tms.ui.view.audit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.vaadin.data.provider.AbstractBackEndDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.spring.annotation.SpringComponent;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.service.MockUserService;

@SpringComponent
@PrototypeScope
public class AuditDataProvider extends AbstractBackEndDataProvider<User, Object> {

	private static final long serialVersionUID = -3459665913532137667L;
	private  MockUserService mockUserService;

	@Autowired
	public AuditDataProvider(MockUserService userService) {
		this.mockUserService = userService;
	}
	
	  @Override
	  public Object getId(User item) {
	    // TODO Auto-generated method stub
	    return item.getId();
	  }
	@Override
	public boolean isInMemory() {
		// TODO Auto-generated method stub
		return true;
	}
   
	@Override
	public  Stream<User> fetch(Query<User,Object> query) {
		return getUsers(query).stream();
	}

	@Override
	protected Stream<User> fetchFromBackEnd(Query<User, Object> query) {
		return getUsers(query).stream();
	}



	@Override
	protected int sizeInBackEnd(Query<User, Object> query) {
		getUsers(query).size();
		return 0;
	}
	
	private List<User> getUsers(Query<User, Object> query)
	{
		List<User> resultList =  mockUserService.getUsers();
		
		if(query.getFilter().isPresent())
			resultList = mockUserService.getUsers().stream().filter(u -> u.getEmail().contains(query.getFilter().get().toString()) ).collect(Collectors.toList());
		return resultList;
	}

}