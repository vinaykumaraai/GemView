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

/**
 * 
 * @author Vinay
 *
 */

public class TerminalClient {
	
	private Long id;
	
	private String type;
	
	private String label;
	
	private String description;
	
	private String serialNumber;
	
	private boolean active;
	
	private boolean activateHeartbeat;
	
	private boolean debugActive;

	private String duration;
	
	private Long frequency;
	
	private String lastSeen;
	
	private String entityId;
	
	public TerminalClient() {
	}
	
	public TerminalClient(String serialNumber, boolean debugActive, String duration) {
		super();
		Objects.requireNonNull(serialNumber);
		Objects.requireNonNull(debugActive);
		Objects.requireNonNull(duration);
		this.serialNumber=serialNumber;
		this.debugActive = debugActive;
		this.duration = duration;
	}

	public TerminalClient(Long id, String type, String label, String description, String serialNumber, boolean active,
			boolean activateHeartbeat, boolean debugActive,  Long frequency, String lastSeen, String entityId) {
		super();
		this.id = id;
		this.type = type;
		this.label = label;
		this.description = description;
		this.serialNumber = serialNumber;
		this.active = active;
		this.activateHeartbeat = activateHeartbeat;
		this.frequency = frequency;
		this.lastSeen = lastSeen;
		this.entityId=entityId;
		this.debugActive = debugActive;
	}

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getLabel() {
		return label;
	}



	public void setLabel(String label) {
		this.label = label;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public boolean isActivateHeartbeat() {
		return activateHeartbeat;
	}



	public void setActivateHeartbeat(boolean activateHeartbeat) {
		this.activateHeartbeat = activateHeartbeat;
	}



	public Long getFrequency() {
		return frequency;
	}



	public void setFrequency(Long frequency) {
		this.frequency = frequency;
	}



	public String getLastSeen() {
		return lastSeen;
	}



	public void setLastSeen(String lastSeen) {
		this.lastSeen = lastSeen;
	}



	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean debugActive) {
		this.active = debugActive;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	
	public boolean isDebugActive() {
		return debugActive;
	}

	public void setDebugActive(boolean debugActive) {
		this.debugActive = debugActive;
	}
	
}
