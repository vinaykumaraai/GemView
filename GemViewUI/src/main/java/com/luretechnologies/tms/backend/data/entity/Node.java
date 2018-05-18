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

import java.util.List;
import java.util.UUID;

import com.vaadin.ui.Label;

/**
 * @author Vinay
 *
 */
public class Node {
	public String id;

	public NodeLevel level;
	
	public String label;
	
	public Devices device;
	
	public String description;
	
	public String serialNum;
	
	public boolean active;
	
	public boolean heartBeat;
	
	public String frequency;
	
	public String additionaFiles;
	
	public String update;
	
	public App app;
	
	public Profile profile;
	
	public List<? extends AbstractEntity> entityList;
	
	public NodeLevel getLevel() {
		return level;
	}

	public void setLevel(NodeLevel level) {
		this.level = level;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		//Label label = new
		this.label = label;
	}

	public List<? extends AbstractEntity> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<? extends AbstractEntity> entityList) {
		this.entityList = entityList;
	}

	
	public Devices getDevice() {
		return device;
	}

	public void setDevice(Devices device) {
		this.device = device;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isHeartBeat() {
		return heartBeat;
	}

	public void setHeartBeat(boolean heartBeat) {
		this.heartBeat = heartBeat;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getAdditionaFiles() {
		return additionaFiles;
	}

	public void setAdditionaFiles(String additionaFiles) {
		this.additionaFiles = additionaFiles;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return label.toString();
	}

	
public Node() {
	this.id = UUID.randomUUID().toString();
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (!(obj instanceof Node))
		return false;
	Node other = (Node) obj;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	return true;
}



	
}
