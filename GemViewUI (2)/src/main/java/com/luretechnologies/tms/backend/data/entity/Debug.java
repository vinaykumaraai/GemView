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

import java.util.Date;
import java.util.Objects;

import com.vaadin.ui.DateTimeField;

public class Debug extends AbstractEntity {

	private DebugType type;
	private String description, dateTime, name;
	private Date dateOfDebug;
	private boolean active, debug;

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public DebugType getType() {
		return type;
	}

	public void setType(DebugType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateOfDebug() {
		return dateOfDebug;
	}

	public void setDateOfDebug(Date dateOfDebug) {
		this.dateOfDebug = dateOfDebug;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Debug() {
		// TODO Auto-generated constructor stub
	}

	public Debug(DebugType type, String description, Date dateOfDebug, String dateTime,String name,boolean active,boolean debug) {
		super(false);
		Objects.requireNonNull(type);
		Objects.requireNonNull(description);
		Objects.requireNonNull(dateOfDebug);
		Objects.requireNonNull(dateTime);
		Objects.requireNonNull(name);
		Objects.requireNonNull(active);
		Objects.requireNonNull(debug);
		this.type = type;
		this.description = description;
		this.dateOfDebug = dateOfDebug;
		this.dateTime = dateTime;
		this.active = active;
		this.debug = debug;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Debug [type=" + type + ", description=" + description + ", dateOfDebug=" + dateOfDebug + ", dateTime="
				+ dateTime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dateOfDebug == null) ? 0 : dateOfDebug.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Debug other = (Debug) obj;
		if (dateOfDebug == null) {
			if (other.dateOfDebug != null)
				return false;
		} else if (!dateOfDebug.equals(other.dateOfDebug))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}