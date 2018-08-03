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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.common.enums.PermissionAccessEnum;
import com.luretechnologies.common.enums.PermissionEnum;
import com.luretechnologies.common.enums.PermissionGroupEnum;
import com.luretechnologies.tms.app.security.BackendAuthenticationProvider;
import com.luretechnologies.tms.backend.data.Role;
import com.luretechnologies.tms.backend.data.entity.Permission;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;

@Service
public class RolesService {
	private List<String> screen = Arrays.asList("DASHBOARD", "APPSTORE", "PERSONALIZATION", "HEARTBEAT",
			"ASSET", "ODOMETER", "AUDIT", "USER", "ROLE", "SYSTEM");
	private static final String CREATE ="CREATE";
	private static final String UPDATE ="UPDATE";
	private static final String READ ="READ";
	private static final String DELETE ="DELETE";
	private static final String ALL ="ALL";
	
	private static final String entityAllValue= "ALL_ENTITY";
	private static final String entityReadValue= "READ_ENTITY";
	private static final String entityCreateValue= "CREATE_ENTITY";
	private static final String entityEditValue= "UPDATE_ENTITY";
	private static final String entityDeleteValue= "DELETE_ENTITY";
	
	//private static List<PermissionEnum> entityPermission = new ArrayList<>();
	
	@Autowired
	UserService userService;
	
	public List<Role> getRoleList(){
		List<Role> roleClientList = new ArrayList();
		//Map<String, Permission> permissionListClient = new HashMap<>();
		//List<String> groupOccuranceList = new ArrayList<>();
		//Permission permission = new Permission();
		if(RestServiceUtil.getSESSION() != null) {
			try {
				List<com.luretechnologies.client.restlib.service.model.Role> roleList = RestServiceUtil.getInstance().getClient().getRoleApi().getRoles(null, null);
				for (com.luretechnologies.client.restlib.service.model.Role role : roleList) {
					/*List<PermissionEnum> permissions = role.getPermissions();
					permissionListClient = new HashMap<>();
					for (PermissionEnum permissionEnum : permissions) {
						PermissionAccessEnum permissionAccessEnum = permissionEnum.getAccess();
						PermissionGroupEnum permissionGroupEnum = permissionEnum.getGroup();
						if(screen.contains(permissionGroupEnum.toString())) {
							if(!groupOccuranceList.contains(permissionGroupEnum.toString())) {
								permission = new Permission();
								groupOccuranceList.add(permissionGroupEnum.toString());
								Permission permissionClient = getPermission(permissionGroupEnum, permissionAccessEnum, permission);
								permissionListClient.put(permissionGroupEnum.toString(), permissionClient);
							}else {
								permission = permissionListClient.get(permissionGroupEnum.toString());
								permissionListClient.remove(permissionGroupEnum.toString());
								permission = getPermission(permissionGroupEnum, permissionAccessEnum, permission);
								permissionListClient.put(permissionGroupEnum.toString(), permission);
							}
						}
					}
					groupOccuranceList.clear();
					List<Permission> permissionListClientSorted = sortPermissionList(permissionListClient.values());*/
					//Role roleClient = new Role(role.getId(),role.getDescription(),role.getName(), role.getAvailable(), permissionListClientSorted);
					Role roleClient = getClientRole(role);
					roleClientList.add(roleClient);
					}
				return roleClientList;
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return roleClientList;
	}
	
	private Role getClientRole(com.luretechnologies.client.restlib.service.model.Role role) {
		Map<String, Permission> permissionListClient = new HashMap<>();
		List<PermissionEnum> permissions = role.getPermissions();
		List<String> groupOccuranceList = new ArrayList<>();
		Permission permission = new Permission();
		permissionListClient = new HashMap<>();
		for (PermissionEnum permissionEnum : permissions) {
			PermissionAccessEnum permissionAccessEnum = permissionEnum.getAccess();
			PermissionGroupEnum permissionGroupEnum = permissionEnum.getGroup();
			if(screen.contains(permissionGroupEnum.toString())) {
				if(!groupOccuranceList.contains(permissionGroupEnum.toString())) {
					permission = new Permission();
					groupOccuranceList.add(permissionGroupEnum.toString());
					Permission permissionClient = getPermission(permissionGroupEnum, permissionAccessEnum, permission);
					permissionListClient.put(permissionGroupEnum.toString(), permissionClient);
				}else {
					permission = permissionListClient.get(permissionGroupEnum.toString());
					permissionListClient.remove(permissionGroupEnum.toString());
					permission = getPermission(permissionGroupEnum, permissionAccessEnum, permission);
					permissionListClient.put(permissionGroupEnum.toString(), permission);
				}
			}
		}
		groupOccuranceList.clear();
		List<Permission> permissionListClientSorted = sortPermissionList(permissionListClient.values());
		Role roleClient = new Role(role.getId(),role.getDescription(),role.getName(), role.getAvailable(), permissionListClientSorted);
		return roleClient;
		
	}
	
	public List<Permission> sortPermissionList(Collection<Permission> collection){
		List<Permission> permissionListClientSortedAll = new LinkedList<>();
		List<String> screenNameServer = new ArrayList<>();
		List<String> screenNameServerContainsAll = new ArrayList<>();
		List<Permission> permissionListClientSorted = new LinkedList<>();
		for(String string : screen) {
			for(Permission permission : collection) {
				if(permission.getPageName().equals(string)) {
					screenNameServer.add(permission.getPageName());
					permissionListClientSorted.add(permission);
					break;
				}
			}
		}
		if(!(screen.size() == screenNameServer.size())) {
			for(String missingScreen : screen) {
				if(screenNameServer.contains(missingScreen)) {

				}else {
					Permission missingPermission = new Permission(missingScreen, false, false, false, false);
					permissionListClientSorted.add(missingPermission);
				}
			}
			for(String string : screen) {
				for(Permission permission : permissionListClientSorted) {
					if(permission.getPageName().equals(string)) {
						screenNameServerContainsAll.add(permission.getPageName());
						permissionListClientSortedAll.add(permission);
						break;
					}
				}
			}
			return permissionListClientSortedAll;
		}	
		return permissionListClientSorted;
	}
	
	public List<Permission> alignPermissions(List<String> screen, List<String> serverScreen){
		return null;
		
	}
	
	public List<String> getRoleNameList(){
		List<String> roleNameList = new ArrayList<>();
		if(RestServiceUtil.getSESSION() != null) {
			try {
				List<com.luretechnologies.client.restlib.service.model.Role> roleList = RestServiceUtil.getInstance().getClient().getRoleApi().getRoles(null, null);
				for (com.luretechnologies.client.restlib.service.model.Role role : roleList) {
					roleNameList.add(role.getName());
					}
				return roleNameList;
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return roleNameList;
	}
	
	public Permission getPermission(PermissionGroupEnum permissionGroupEnum, PermissionAccessEnum permissionAccessEnum, Permission permission) {
		switch(permissionAccessEnum.toString()) {
		case UPDATE:
			if(permission.getPageName()==null) {
				permission= new Permission(permissionGroupEnum.toString(), false, true, false, false);
			}else {
				permission.setEdit(true);
			}
			break;
		case READ:
			if(permission.getPageName()==null) {
				permission= new Permission(permissionGroupEnum.toString(), true, false, false, false);
			}else {
				permission.setAccess(true);
			}
			break;
		case CREATE:
			if(permission.getPageName()==null) {
				permission= new Permission(permissionGroupEnum.toString(), false, false, false, true);
			}else {
				permission.setAdd(true);
			}
			break;
		case DELETE:
			if(permission.getPageName()==null) {
				permission= new Permission(permissionGroupEnum.toString(), false, false, true, false);
			}else {
				permission.setDelete(true);
			}
			break;
		case ALL:
			permission= new Permission(permissionGroupEnum.toString(), true, true, true, true);
			break;
		default:
			break;
		} 
		return permission;
		
	}
	
	public void createUpdateRole(Role role) {
		com.luretechnologies.client.restlib.service.model.Role roleServer = new com.luretechnologies.client.restlib.service.model.Role();
		List<PermissionEnum> permissionsListServer = new ArrayList<>();
		List<Permission> permissionListClient = new ArrayList<>();
		if(RestServiceUtil.getSESSION() != null) {
			try {
				if(role!=null) {
					roleServer.setName(role.getName());
					roleServer.setDescription(role.getDescription());
					roleServer.setAvailable(role.isAvailable());
					permissionListClient = role.getPermissions();
					for(Permission permissionClient : permissionListClient) {
						permissionsListServer=getPermissionEnumsList(permissionClient, permissionsListServer);
						
						
					}
					List<PermissionEnum> entityPermission = addEntityPermissions(permissionsListServer);
					permissionsListServer.addAll(entityPermission);
					roleServer.setPermissions(permissionsListServer);
					if(role.getId()==null) {
						RestServiceUtil.getInstance().getClient().getRoleApi().createRole(roleServer);
					}else {
						RestServiceUtil.getInstance().getClient().getRoleApi().updateRole(role.getId(), roleServer);
					}
				}
				
				
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public List<PermissionEnum> addEntityPermissions(List<PermissionEnum> permissionsListServer){
		List<PermissionEnum> entityPermission = new ArrayList<>();
		for(PermissionEnum permissionEnum: permissionsListServer) {
			String all=permissionEnum.toString().substring(0, 3);
			String access=permissionEnum.toString().substring(0, 4);
			String create=permissionEnum.toString().substring(0, 6);
			String update=permissionEnum.toString().substring(0, 6);
			String delete=permissionEnum.toString().substring(0, 6);
			
			if(!entityPermission.contains(PermissionEnum.valueOf(entityAllValue)) && all.equals("ALL")) {
				entityPermission.add(PermissionEnum.valueOf(entityAllValue));
				break;
			}
			else if(!entityPermission.contains(PermissionEnum.valueOf(entityReadValue)) && access.equals("READ")) {
				entityPermission.add(PermissionEnum.valueOf(entityReadValue));
			}
			else if(!entityPermission.contains(PermissionEnum.valueOf(entityCreateValue)) && create.equals("CREATE")) {
				entityPermission.add(PermissionEnum.valueOf(entityCreateValue));
			}
			else if(!entityPermission.contains(PermissionEnum.valueOf(entityEditValue)) && update.equals("UPDATE")) {
				entityPermission.add(PermissionEnum.valueOf(entityEditValue));
			}
			else if(!entityPermission.contains(PermissionEnum.valueOf(entityDeleteValue)) && delete.equals("DELETE")) {
				entityPermission.add(PermissionEnum.valueOf(entityDeleteValue));
			}
		}
		return entityPermission;
	}
	
	public Role deleteRole(Long id) {
		if(RestServiceUtil.getSESSION() != null) {
			try {
				RestServiceUtil.getInstance().getClient().getRoleApi().deleteRole(id);
			
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return null;
		
	}
	
	public List<PermissionEnum>  getPermissionEnumsList(Permission permissionClient, List<PermissionEnum> permissionsListServer) {
		boolean add = permissionClient.getAdd();
		boolean access = permissionClient.getAccess();
		boolean delete = permissionClient.getDelete();
		boolean edit = permissionClient.getEdit();
		
		switch(permissionClient.getPageName()) {
		case "DASHBOARD":
			permissionsListServer.add(PermissionEnum.READ_DASHBOARD);
			break;
		case "APPSTORE":
			addPermissionsToRole("APPSTORE", permissionsListServer, add, access, delete, edit);
			break;
		case "PERSONALIZATION":
			addPermissionsToRole("PERSONALIZATION", permissionsListServer, add, access, delete, edit);
			break;
		case "HEARTBEAT":
			addPermissionsToRole("HEARTBEAT", permissionsListServer, add, access, delete, edit);
			break;
		case "ASSET":
			addPermissionsToRole("ASSET", permissionsListServer, add, access, delete, edit);
			break;
		case "ODOMETER":
			addPermissionsToRole("ODOMETER", permissionsListServer, add, access, delete, edit);
			break;
		case "AUDIT":
			addPermissionsToRole("AUDIT", permissionsListServer, add, access, delete, edit);
			break;
		case "USER":
			addPermissionsToRole("USER", permissionsListServer, add, access, delete, edit);
			break;
		case "ROLE":
			addPermissionsToRole("ROLE", permissionsListServer, add, access, delete, edit);
			break;
		case "SYSTEM":
			addPermissionsToRole("SYSTEM", permissionsListServer, add, access, delete, edit);
			break;
		default:
			break;
		
		}
		return permissionsListServer;
		
		
	}
	
	private void addPermissionsToRole(String screenName, List<PermissionEnum> permissionsListServer, boolean add, boolean access,
			boolean delete, boolean edit) {
		String allValue = "ALL_"+screenName;
		String readValue = "READ_"+screenName;
		String createValue = "CREATE_"+screenName;
		String editValue = "UPDATE_"+screenName;
		String deleteValue = "DELETE_"+screenName;
		
		if(add && access && delete && edit ) {
			permissionsListServer.add(PermissionEnum.valueOf(allValue));
			
		}else if(add==true && access==true && delete==true && edit==false) {
			permissionsListServer.add(PermissionEnum.valueOf(createValue));
			permissionsListServer.add(PermissionEnum.valueOf(readValue));
			permissionsListServer.add(PermissionEnum.valueOf(deleteValue));
		}else if(add==true && access==true && delete==false && edit==true) {
			permissionsListServer.add(PermissionEnum.valueOf(createValue));
			permissionsListServer.add(PermissionEnum.valueOf(readValue));
			permissionsListServer.add(PermissionEnum.valueOf(editValue));
		}else if(add==true && access==false && delete==true && edit==true) {
			permissionsListServer.add(PermissionEnum.valueOf(createValue));
			permissionsListServer.add(PermissionEnum.valueOf(deleteValue));
			permissionsListServer.add(PermissionEnum.valueOf(editValue));
		}else if(add==false && access==true && delete==true && edit==true) {
			permissionsListServer.add(PermissionEnum.valueOf(readValue));
			permissionsListServer.add(PermissionEnum.valueOf(deleteValue));
			permissionsListServer.add(PermissionEnum.valueOf(editValue));
		}else if(add==true && access==true && delete==false && edit==false) {
			permissionsListServer.add(PermissionEnum.valueOf(readValue));
			permissionsListServer.add(PermissionEnum.valueOf(createValue));
		}else if(add==true && access==false && delete==true && edit==false) {
			permissionsListServer.add(PermissionEnum.valueOf(deleteValue));
			permissionsListServer.add(PermissionEnum.valueOf(createValue));
		}else if(add==true && access==false && delete==false && edit==true) {
			permissionsListServer.add(PermissionEnum.valueOf(editValue));
			permissionsListServer.add(PermissionEnum.valueOf(createValue));
		}else if(add==false && access==true && delete==false && edit==true) {
			permissionsListServer.add(PermissionEnum.valueOf(editValue));
			permissionsListServer.add(PermissionEnum.valueOf(readValue));
		}else if(add==false && access==true && delete==true && edit==false) {
			permissionsListServer.add(PermissionEnum.valueOf(deleteValue));
			permissionsListServer.add(PermissionEnum.valueOf(readValue));
		}else if(add==false && access==false && delete==true && edit==true) {
			permissionsListServer.add(PermissionEnum.valueOf(deleteValue));
			permissionsListServer.add(PermissionEnum.valueOf(editValue));
		}else if(add==true && access==false && delete==false && edit==false) {
			permissionsListServer.add(PermissionEnum.valueOf(createValue));
		}else if(add==false && access==true && delete==false && edit==false) {
			permissionsListServer.add(PermissionEnum.valueOf(readValue));
		}else if(add==false && access==false && delete==true && edit==false) {
			permissionsListServer.add(PermissionEnum.valueOf(deleteValue));
		}else if(add==false && access==false && delete==false && edit==true) {
			permissionsListServer.add(PermissionEnum.valueOf(editValue));
		}else if(add==false && access==false && delete==false && edit==false) {
		}
	}
	
	public Role getRoleByName(String roleName) throws ApiException {
		Role role = new Role();
		if(RestServiceUtil.getSESSION() != null) {
			com.luretechnologies.client.restlib.service.model.Role roleServer = 
					RestServiceUtil.getInstance().getClient().getRoleApi().getRolebyName(roleName);
			role = getClientRole(roleServer);
			
		}
		return role;
		
	}
	
	public List<Permission> getLoggedInUserRolePermissions() throws ApiException {
		User user = new User();
		List<Permission> permissionList = new ArrayList<>();
		BackendAuthenticationProvider provider = new BackendAuthenticationProvider();
		String username = provider.loggedInUserName();
		if(!username.isEmpty() && username !=null) {
			 user = userService.getUserbyUserName(username);
			 if(user!=null) {
				 String userRole = user.getRole();
				 Role roleClient = getRoleByName(userRole);
				 if(roleClient!=null) {
					 permissionList = roleClient.getPermissions();
				 }
			 }	
	}
		return permissionList;

	}
}
