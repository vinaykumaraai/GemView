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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.tms.app.security.BackendAuthenticationProvider;
import com.luretechnologies.tms.backend.data.Role;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.vaadin.data.provider.Query;
import com.vaadin.spring.annotation.SpringComponent;

@Service
@SpringComponent
public class UserService extends CrudService<User>{
	private List<User> users = new ArrayList<User>();
	private Map<Long,User> userDirectory = new HashMap<Long, User>();
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostConstruct
	public List<User> getUsersList()
	{
		//Get users from Backend
		List<User> users = new ArrayList<User>();
		if(RestServiceUtil.getSESSION() != null) {
			try {
				List<com.luretechnologies.client.restlib.service.model.User> userList = RestServiceUtil.getInstance().getClient().getUserApi().getUsers();
				for (com.luretechnologies.client.restlib.service.model.User user : userList) {
					User clientUser = new User(user.getId(),user.getEmail(),user.getUsername(),"",user.getRole().getName(),user.getFirstName(), user.getLastName(),user.getAvailable(), user.getPasswordFrequency());
					clientUser.setLocked(true);
					users.add(clientUser);
					userDirectory.put(clientUser.getId(), clientUser);
				}
				return users;
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return users;
	}
	
	public List<User> getUsersListByEntityId(Long id)
	{
		//Get users from Backend
		List<User> users = new ArrayList<User>();
		if(RestServiceUtil.getSESSION() != null) {
			try {
				List<com.luretechnologies.client.restlib.service.model.User> userList = RestServiceUtil.getInstance().getClient().getUserApi().getUsers();
				for (com.luretechnologies.client.restlib.service.model.User user : userList) {
					if(id.equals(user.getEntity().getId())) {
					User clientUser = new User(user.getId(),user.getEmail(),user.getUsername(),"",user.getRole().getName(),user.getFirstName(), user.getLastName(),user.getAvailable(), user.getPasswordFrequency());
					clientUser.setLocked(true);
					users.add(clientUser);
					}
				}
				return users;
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return users;
	}
	
	public User getUserByEmail(String email)
	{
		User clientUser = new User();
		if(RestServiceUtil.getSESSION() != null) {
			try {
				com.luretechnologies.client.restlib.service.model.User user = RestServiceUtil.getInstance().getClient().getUserApi().getUserByEmail(email);
				
					clientUser = new User(user.getId(),user.getEmail(),user.getUsername(),"",user.getRole().getName(),user.getFirstName(), user.getLastName(),user.getAvailable(), user.getPasswordFrequency());
					clientUser.setLocked(true);
					return clientUser;
					
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return clientUser;
	}
	
	public User getUserbyId(Long id) {
		User clientUser = new User();
		if(RestServiceUtil.getSESSION() != null) {
			try {
				com.luretechnologies.client.restlib.service.model.User user = RestServiceUtil.getInstance().getClient().getUserApi().getUser(id);
				
					clientUser = new User(user.getId(),user.getEmail(),user.getUsername(),"",user.getRole().getName(),user.getFirstName(), user.getLastName(),user.getAvailable(), user.getPasswordFrequency());
					clientUser.setLocked(true);
					return clientUser;
					
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return clientUser;
	}
	
	public User getUserbyUserName(String username) {
		User clientUser = new User();
		if(RestServiceUtil.getSESSION() != null) {
			try {
				com.luretechnologies.client.restlib.service.model.User user = RestServiceUtil.getInstance().getClient().getUserApi().getUserByUserName(username);
				
					clientUser = new User(user.getId(),user.getEmail(),user.getUsername(),"",user.getRole().getName(),user.getFirstName(), user.getLastName(),user.getAvailable(), user.getPasswordFrequency());
					clientUser.setLocked(true);
					return clientUser;
					
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return clientUser;
	}
	
	public void createUser(User user) throws ApiException {
		User clientUser = new User();
		com.luretechnologies.client.restlib.service.model.User userServer = new com.luretechnologies.client.restlib.service.model.User();
		if(RestServiceUtil.getSESSION() != null) {
			try {
				com.luretechnologies.client.restlib.service.model.Role role = RestServiceUtil.getInstance().getClient().getRoleApi().getRolebyName(user.getRole());
				userServer.setUsername(user.getName());
				userServer.setFirstName(user.getFirstname());
				userServer.setLastName(user.getLastname());
				userServer.setRole(role);
				userServer.setEmail(user.getEmail());
				userServer.setAvailable(user.isActive());
				userServer.setPasswordFrequency(user.getPasswordFrequency());
				userServer.setPassword(user.getPassword());
				if(user.getId()!=null) {
					com.luretechnologies.client.restlib.service.model.User updatedUser  = RestServiceUtil.getInstance().getClient().getUserApi().updateUser(user.getId(), userServer);
					clientUser = new User(updatedUser.getId(),updatedUser.getEmail(),updatedUser.getUsername(),"",updatedUser.getRole().getName(),updatedUser.getFirstName(), updatedUser.getLastName(),updatedUser.getAvailable(), updatedUser.getPasswordFrequency());
					clientUser.setLocked(true);	
				}else {
					com.luretechnologies.client.restlib.service.model.User savedUser  = RestServiceUtil.getInstance().getClient().getUserApi().createUser(userServer);
					
					clientUser = new User(savedUser.getId(),savedUser.getEmail(),savedUser.getUsername(),"",savedUser.getRole().getName(),savedUser.getFirstName(), savedUser.getLastName(),savedUser.getAvailable(), savedUser.getPasswordFrequency());
					clientUser.setLocked(true);	
				}
					
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void deleteUser(Long id) throws ApiException {
		
		if(RestServiceUtil.getSESSION() != null) {
			try {
				
				RestServiceUtil.getInstance().getClient().getUserApi().deleteUser(id);
					
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public User getUserByEmailClient(String email)
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
			if(RestServiceUtil.getSESSION() != null) {
			users.add(user);
			userDirectory.put(user.getId(), user);
			com.luretechnologies.client.restlib.service.model.User serverUser = new com.luretechnologies.client.restlib.service.model.User();
			serverUser.setAvailable(user.isActive());
			serverUser.setEmail(user.getEmail());
			serverUser.setFirstName(user.getFirstname());
			serverUser.setLastName(user.getLastname());
			serverUser.setPassword(user.getPassword());
			serverUser.setUsername(user.getName());
			com.luretechnologies.client.restlib.service.model.Role serverRole = new com.luretechnologies.client.restlib.service.model.Role();
			serverRole.setName(user.getRole());
			//FIXME: get permission enum to create proper Role
			//serverRole.setPermissions(Arrays.asList());
			serverUser.setRole(serverRole);
			try {
				RestServiceUtil.getInstance().getClient().getUserApi().createUser(serverUser);
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		else
			throw new RuntimeException("User with emailid "+user.getEmail()+"already present");
		
	}
	
	public void deleteUser(String emailAddress)
	{
		User user = userDirectory.get(emailAddress);
		users.remove(user);
		userDirectory.remove(user.getId());
		try {
			com.luretechnologies.client.restlib.service.model.User serverUser = RestServiceUtil.getInstance().getClient().getUserApi().getUserByUserName(user.getName());
			RestServiceUtil.getInstance().getClient().getUserApi().deleteUser(serverUser.getId());
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public String getLoggedInUserName() {
		BackendAuthenticationProvider provider = new BackendAuthenticationProvider();
		String username = provider.loggedInUserName();
		return username;
				
	}

	

}
