package com.luretechnologies.tms.backend.data.entity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Roles {

	/**
	 * 
	 */
	private String roleName;

	private String description;

	private boolean active;
	
	Map<String, Permission> permissionMap = new LinkedHashMap<>();

	
	public Roles() {
		permissionMap.put("Dashboard", new Permission("Dashboard", false, false, false, false));
		permissionMap.put("Application Store", new Permission("Application Store",false, false, false, false));
		permissionMap.put("Personalization", new Permission("Personalization",false, false, false, false));
		permissionMap.put("Heartbeat", new Permission("Heartbeat", false, false, false, false));
		permissionMap.put("Asset Control", new Permission("Asset Control",false, false, false, false));
		permissionMap.put("Device Odometer", new Permission("Device Odometer",false, false, false, false));
		permissionMap.put("Security", new Permission("Security", false, false, false, false));
		permissionMap.put("Audit", new Permission("Audit",false, false, false, false));
		permissionMap.put("Users", new Permission("Users",false, false, false, false));
		permissionMap.put("Roles", new Permission("Roles",false, false, false, false));
		permissionMap.put("Devices", new Permission("Devices",false, false, false, false));
		permissionMap.put("System", new Permission("System", false, false, false, false));
	}

	
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the active
	 */
	public boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the permissionMap
	 */
	public Map<String, Permission> getPermissionMap() {
		return permissionMap;
	}

	/**
	 * @param permissionMap the permissionMap to set
	 */
	public void setPermissionMap(Map<String, Permission> permissionMap) {
		this.permissionMap = permissionMap;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Roles [roleName=" + roleName + ", description=" + description + ", active=" + active + "]";
	}
	
	
}
