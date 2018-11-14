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

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.Entity;
import com.luretechnologies.tms.app.security.BackendAuthenticationProvider;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.rest.util.RestClient;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.luretechnologies.tms.ui.components.ComponentUtil;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.vaadin.data.provider.Query;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

/**
 * 
 * @author Vinay
 *
 */

@Service
@SpringComponent
public class UserService extends CrudService<User>{
	private final static Logger userLogger = Logger.getLogger(UserService.class);
	private List<User> users = new ArrayList<User>();
	private Map<Long,User> userDirectory = new HashMap<Long, User>();
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> getUsersList()
	{
		//Get users from Backend
		List<User> users = new ArrayList<User>();
		if(RestServiceUtil.getSESSION() != null) {
			try {
				List<com.luretechnologies.client.restlib.service.model.User> userList = RestServiceUtil.getInstance().getClient().getUserApi().getUsers();
				for (com.luretechnologies.client.restlib.service.model.User user : userList) {
					User clientUser = new User(user.getId(),user.getEmail(),user.getUsername(),user.getRole().getName(),user.getFirstName(), user.getLastName(),user.getAvailable(), user.getPasswordFrequency(), user.getEntity().getId(), user.getAssignedIP());
					clientUser.setLocked(true);
					users.add(clientUser);
					userDirectory.put(clientUser.getId(), clientUser);
				}
				return users;
			} catch (ApiException ae) {
				if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
					Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}else {
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  retrieving Users List",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
				userLogger.error("API Error Occured while retrieving Users List",ae);
				RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
			} catch (Exception e) {
				userLogger.error("Error Occured while retrieving Users List",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  retrieving Users List",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			
		}
		return users;
	}
	
	public List<User> getUsersListByEntityId(String entityId)
	{
		//Get users from Backend
		List<User> users = new ArrayList<User>();
		if(RestServiceUtil.getSESSION() != null) {
			try {
				List<com.luretechnologies.client.restlib.service.model.User> userList = RestServiceUtil.getInstance().getClient().getUserApi().getUsersByEntity(entityId);
				for (com.luretechnologies.client.restlib.service.model.User user : userList) {
					User clientUser = new User(user.getId(),user.getEmail(),user.getUsername(),user.getRole().getName(),user.getFirstName(), user.getLastName(),user.getAvailable(), user.getPasswordFrequency(), user.getEntity().getId(), user.getAssignedIP());
					users.add(clientUser);
				}
				return users;
			} catch (ApiException ae) {
				if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
					Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}else {
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  retrieving Users List",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
				userLogger.error("API Error Occured while retrieving Users List by Entity ID",ae);
				RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
			} catch (Exception e) {
				userLogger.error("Error Occured while retrieving Users List by Entity ID",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  retrieving Users List",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
				
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
				
					clientUser = new User(user.getId(),user.getEmail(),user.getUsername(),user.getRole().getName(),user.getFirstName(), user.getLastName(),user.getAvailable(), user.getPasswordFrequency(),user.getEntity().getId(), user.getAssignedIP());
					clientUser.setLocked(true);
					return clientUser;
					
			} catch (ApiException ae) {
				if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
					Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}else {
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  retrieving User by Email ID",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
				userLogger.error("API Error Occured while retrieving User by Email ID",ae);
				RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
			} catch (Exception e) {
				userLogger.error("Error Occured while retrieving User by Email ID",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  retrieving User by Email ID",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			
			
		}
		return clientUser;
	}
	
	public User getUserbyId(Long id) {
		User clientUser = new User();
		if(RestServiceUtil.getSESSION() != null) {
			try {
				com.luretechnologies.client.restlib.service.model.User user = RestServiceUtil.getInstance().getClient().getUserApi().getUser(id);
				
					clientUser = new User(user.getId(),user.getEmail(),user.getUsername(),user.getRole().getName(),user.getFirstName(), user.getLastName(),user.getAvailable(), user.getPasswordFrequency(),user.getEntity().getId(), user.getAssignedIP());
					clientUser.setLocked(true);
					return clientUser;
					
			} catch (ApiException ae) {
				if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
					Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}else {
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  retrieving User by ID",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
				userLogger.error("API Error Occured while retrieving User by ID",ae);
				RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
			} catch (Exception e) {
				userLogger.error("Error Occured while retrieving User by ID",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  retrieving User by ID",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			
			
		}
		return clientUser;
	}
	
	public User getUserbyUserName(String username) {
		User clientUser = new User();
		if(RestServiceUtil.getSESSION() != null) {
			try {
				com.luretechnologies.client.restlib.service.model.User user = RestServiceUtil.getInstance().getClient().getUserApi().getUserByUserName(username);
				
					clientUser = new User(user.getId(),user.getEmail(),user.getUsername(),user.getRole().getName(),user.getFirstName(), user.getLastName(),user.getAvailable(), user.getPasswordFrequency(),user.getEntity().getId(), user.getAssignedIP());
					clientUser.setLocked(true);
					return clientUser;
					
			} catch (ApiException ae) {
				if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
					Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}else {
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  retrieving User by Username",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
				userLogger.error("API Error Occured while retrieving User by Username",ae);
				RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
			} catch (Exception e) {
				userLogger.error("Error Occured while retrieving User by Username",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  retrieving User by Username",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			
			
		}
		return clientUser;
	}
	
	public void createUser(User user, Long entityId) {
		User clientUser = new User();
		com.luretechnologies.client.restlib.service.model.User userServer = new com.luretechnologies.client.restlib.service.model.User();
		if(RestServiceUtil.getSESSION() != null) {
			try {
				com.luretechnologies.client.restlib.service.model.Role role = RestServiceUtil.getInstance().getClient().getRoleApi().getRolebyName(user.getRole());
				Entity entityServer = RestServiceUtil.getInstance().getClient().getEntityApi().getEntityById(entityId);
				userServer.setUsername(user.getUsername());
				userServer.setFirstName(user.getFirstname());
				userServer.setLastName(user.getLastname());
				userServer.setRole(role);
				userServer.setEmail(user.getEmail());
				userServer.setAvailable(user.isActive());
				userServer.setEntity(entityServer);
				userServer.setAssignedIP(user.getIpAddress());
				
				if(user.getId()!=null) {
					com.luretechnologies.client.restlib.service.model.User updatedUser  = RestServiceUtil.getInstance().getClient().getUserApi().updateUser(user.getId(), userServer);
					clientUser = new User(updatedUser.getId(),updatedUser.getEmail(),updatedUser.getUsername(),updatedUser.getRole().getName(),updatedUser.getFirstName(), updatedUser.getLastName(),updatedUser.getAvailable(), updatedUser.getPasswordFrequency(),updatedUser.getEntity().getId(), updatedUser.getAssignedIP());
				}else {
					com.luretechnologies.client.restlib.service.model.User savedUser  = RestServiceUtil.getInstance().getClient().getUserApi().createUser(userServer);
					
					clientUser = new User(savedUser.getId(),savedUser.getEmail(),savedUser.getUsername(),savedUser.getRole().getName(),savedUser.getFirstName(), savedUser.getLastName(),savedUser.getAvailable(), savedUser.getPasswordFrequency(), savedUser.getEntity().getId(), savedUser.getAssignedIP());
				}
					
			} catch (ApiException ae) {
				if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
					Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}if(ae.getMessage().contains("USERNAME INVALID DATA ENTRY")) {
					Notification.show(NotificationUtil.USER_NAME_CHECK1, Type.ERROR_MESSAGE);
				}else if(ae.getMessage().contains("EMAIL INVALID DATA ENTRY")) {
					Notification.show(NotificationUtil.USER_NAME_CHECK2, Type.ERROR_MESSAGE);
				}else if(ae.getMessage().contains("DATA INTEGRITY VIOLATION")) {
					Notification.show(NotificationUtil.USER_NAME_CHECK3, Type.ERROR_MESSAGE);
				}else {
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  creating user",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
				userLogger.error("API Error Occured while creating user",ae);
				RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
				
			} catch (Exception e) {
				userLogger.error("Error Occured while creating user",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  creating user",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			
		}
	}
	
	public boolean deleteUser(Long id, boolean delete) {
		if(RestServiceUtil.getSESSION() != null) {
			try {
				User user = getLoggedInUser();
				if(user.getId().equals(id)) {
					delete=false;
					return delete;
				}else {
				RestServiceUtil.getInstance().getClient().getUserApi().deleteUser(id);
				delete = true;
				return delete;
				}
					
			} catch (ApiException ae) {
				if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
					Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}else {
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  deleting User",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
				userLogger.error("API Error Occured while deleting User",ae);
				RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
			} catch (Exception e) {
				userLogger.error("Error Occured while deleting User",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  deleting User",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			
		}
		return delete;
	}
	
	public User getUserByEmailClient(String email)
	{
		Optional<User> userMatch = users.stream().filter(u -> u.getEmail().equals(email)).findFirst();
		if(userMatch.isPresent())
		    return userMatch.get();
		 else
			return null;
	}
	
	public List<User> searchUsers(String filter)
	{
		//Get users from Backend
		List<User> users = new ArrayList<User>();
		if(RestServiceUtil.getSESSION() != null) {
			try {
				List<com.luretechnologies.client.restlib.service.model.User> userList = RestServiceUtil.getInstance().getClient().getUserApi().searchUsers(filter, null, null);
				for (com.luretechnologies.client.restlib.service.model.User user : userList) {
					User clientUser = new User(user.getId(),user.getEmail(),user.getUsername(),user.getRole().getName(),user.getFirstName(), user.getLastName(),user.getAvailable(), user.getPasswordFrequency(), user.getEntity().getId(), user.getAssignedIP());
					clientUser.setLocked(true);
					users.add(clientUser);
				}
				return users;
			} catch (ApiException ae) {
				if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
					Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}else {
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  searching User",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
				userLogger.error("API Error Occured while searching User",ae);
				RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
			} catch (Exception e) {
				userLogger.error("Error Occured while searching User",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  searching User",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			
		}
		return users;
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
	
	public String getRoleName() {
		return RestServiceUtil.getSESSION().getRoleName();
	}
	
	public User getLoggedInUser() {
		User user = new User();
		BackendAuthenticationProvider provider = new BackendAuthenticationProvider();
		String username = provider.loggedInUserName();
		if(!username.isEmpty() && username !=null) {
			 user = getUserbyUserName(username);
		}
		return user;		
	}
	
	public void logUserScreenErrors(Exception e) {
		userLogger.error("Error Occured", e);
	}
}
