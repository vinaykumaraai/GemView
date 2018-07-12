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
import java.util.Objects;

import com.luretechnologies.client.restlib.service.model.Entity;
import com.luretechnologies.common.enums.EntityTypeEnum;

public class TreeNode {
	
	private String label;
	
	private String entityId;
	
	private String description;
	
	private Long id;
	
	private com.luretechnologies.client.restlib.service.model.EntityTypeEnum type;
	
	public List<Entity> entityList;
	
	public boolean active;

	public TreeNode(String label, Long id, com.luretechnologies.client.restlib.service.model.EntityTypeEnum entityTypeEnum, String entityId, String description, boolean active) {
		super();
		Objects.requireNonNull(label);
		Objects.requireNonNull(id);
		Objects.requireNonNull(entityTypeEnum);
		Objects.requireNonNull(entityId);
		Objects.requireNonNull(description);
		this.entityId = entityId;
		this.label = label;
		this.id = id;
		this.type = entityTypeEnum;
		this.description=description;
		this.active=active;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public com.luretechnologies.client.restlib.service.model.EntityTypeEnum getType() {
		return type;
	}

	public void setType(com.luretechnologies.client.restlib.service.model.EntityTypeEnum type) {
		this.type = type;
	}

	public List<Entity> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<Entity> entityList) {
		this.entityList = entityList;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return label;
	}
	
	
	
}
