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

import java.util.Objects;

// "User" is a reserved word in some SQL implementations
/**
 * @author Vinay
 *
 */
public class User extends AbstractEntity {

	private String email;
	
	private String password;

	private String name;

	private String role;
	
	private String firstname;

	private String lastname;
	
	private boolean active;

	private boolean locked = false;
	
	private int passwordFrequency;
	
	private int verficationFrequency;
	
	private Long entityId;
	
	private String ipAddress;

	public User() {
		// An empty constructor is needed for all beans
	}

	public User(Long id,String email, String name, String role, String firstname, String lastname, boolean active, int passwordFrequncy, Long entityId, String ipAddress) {
		Objects.requireNonNull(email);
		Objects.requireNonNull(name);
		Objects.requireNonNull(role);
		Objects.requireNonNull(firstname);
		Objects.requireNonNull(lastname);
		Objects.requireNonNull(active);
		Objects.requireNonNull(passwordFrequncy);

		this.email = email;
		this.name = name;
		this.role = role;
		this.firstname = firstname;
		this.lastname= lastname;
		this.active=active;
		this.setId(id);
		this.passwordFrequency= passwordFrequncy;
		this.entityId=entityId;
		this.ipAddress=ipAddress;
	}
	
	public User(String email, String username, String role, String firstname, String lastname, boolean active, int passwordFrequncy, Long entityId, String ipaddress) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(role);
		Objects.requireNonNull(firstname);
		Objects.requireNonNull(lastname);

		this.email = email;
		this.name = username;
		this.role = role;
		this.firstname = firstname;
		this.lastname= lastname;
		this.active=active;
		this.passwordFrequency= passwordFrequncy;
		this.entityId=entityId;
		this.ipAddress=ipAddress;
	}
	
	public int getPasswordFrequency() {
		return passwordFrequency;
	}

	public void setPasswordFrequency(int passwordFrequency) {
		this.passwordFrequency = passwordFrequency;
	}

	public int getVerficationFrequency() {
		return verficationFrequency;
	}

	public void setVerficationFrequency(int verficationFrequency) {
		this.verficationFrequency = verficationFrequency;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	@Override
	public String toString() {
		return name;
	}

}
