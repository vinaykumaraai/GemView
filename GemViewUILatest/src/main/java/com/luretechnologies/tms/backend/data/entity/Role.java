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

package com.luretechnologies.tms.backend.data.entity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.luretechnologies.common.enums.PermissionEnum;

public class Role {
	public static final String IT = "IT";
	public static final String HR = "HR";
	public static final String ADMIN = "admin";
	
	private Long id;
	private String description;
	private String name;
	private boolean available;
	private List<Permission> permissions = new ArrayList<>();
	
	public Role() {
		permissions.add(new Permission("DASHBOARD", false, false, false, false));
		permissions.add( new Permission("APPSTORE",false, false, false, false));
		permissions.add( new Permission("PERSONALIZATION",false, false, false, false));
		permissions.add( new Permission("HEARTBEAT", false, false, false, false));
		permissions.add(new Permission("ASSET CONTROL",false, false, false, false));
		permissions.add( new Permission("DEVICE ODOMETER",false, false, false, false));
		permissions.add( new Permission("AUDIT",false, false, false, false));
		permissions.add( new Permission("USER",false, false, false, false));
		permissions.add( new Permission("ROLE",false, false, false, false));
		permissions.add( new Permission("SYSTEM", false, false, false, false));
	}
	
	
	public Role(Long id, String description, String name, boolean available, List<Permission> permissions) {
		super();
		this.id = id;
		this.description = description;
		this.name = name;
		this.available = available;
		this.permissions = permissions;
	}



	public static String[] getAllRoles() {
		return new String[] { IT, HR, ADMIN };
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	

}
