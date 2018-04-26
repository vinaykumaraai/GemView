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
package com.luretechnologies.tms.backend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.luretechnologies.tms.backend.data.Role;
import com.luretechnologies.tms.backend.data.entity.User;
import com.vaadin.data.provider.Query;
import com.vaadin.spring.annotation.SpringComponent;


@SpringComponent
public class MockUserService extends CrudService<User>{
	private List<User> users = new ArrayList<User>();
	private Map<Long,User> userDirectory = new HashMap<Long, User>();
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostConstruct
	public void createInitialUsers()
	{
		User user = new User("serafin@gmail.com", "serafin", passwordEncoder.encode("serafin"), Role.IT, "Serafin", "Fuente", true);
		user.setLocked(true);
		userDirectory.put(user.getId(), user);
		users.add(user);
		userDirectory.put(user.getId(), user);
		user = new User("vinay@gmail.com", "Vinay", passwordEncoder.encode("admin"), Role.ADMIN, "Vinay", "Raai", true);
		user.setLocked(true);
		users.add(user);
		userDirectory.put(user.getId(), user);
		user = new User("testuser@gmail.com", "Test", passwordEncoder.encode("admin"), Role.ADMIN, "Test", "Test", true);
		user.setLocked(true);
		users.add(user);
		userDirectory.put(user.getId(), user);
		user = new User("sanky@gmail.com", "Sanky", passwordEncoder.encode("admin"), Role.ADMIN, "Sanky", "Ven", true);
		user.setLocked(true);
		users.add(user);
		userDirectory.put(user.getId(), user);
		for(int j=1; j<=10;j++) {
			user = new User("Mock"+j+"@gmail.com", "Mock"+j, passwordEncoder.encode("admin"), Role.HR, "Vinay", "Raai", true);
			users.add(user);
			userDirectory.put(user.getId(), user);
		}
		
	}
	
	public User getUserByEmail(String email)
	{
		Optional<User> userMatch = users.stream().filter(u -> u.getEmail().equals(email)).findFirst();
		if(userMatch.isPresent())
		    return userMatch.get();
		 else
			return null;
	}
	
	public void addUser(User user)
	{
		if(userDirectory.get(user.getEmail()) == null)
		{
			users.add(user);
			userDirectory.put(user.getId(), user);
		}
		else
			throw new RuntimeException("User with emailid "+user.getEmail()+"already present");
		
	}
	
	public void deleteUser(String emailAddress)
	{
		User user = userDirectory.get(emailAddress);
		users.remove(user);
		userDirectory.remove(user.getId());
	}

	public List<User> getUsers() {
		return users;
	}
	
 
	public String encodePassword(String value) {
		return passwordEncoder.encode(value);
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	protected Map<Long, User> getRepository() {
		// TODO Auto-generated method stub
		return userDirectory;
	}

	@Override
	public Stream<User> fetchFromBackEnd(Query<User, String> query) {
		// TODO Auto-generated method stub
		return users.stream();
	}

	@Override
	protected int sizeInBackEnd(Query<User, String> query) {
		// TODO Auto-generated method stub
		return users.size();
	}

	@Override
	protected List<User> getSavedList() {
		// TODO Auto-generated method stub
		return users;
	}



	

}
