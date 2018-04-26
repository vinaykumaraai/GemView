package com.luretechnologies.tms.backend.data.entity;

import java.util.Objects;

/**
 * 
 * @author Vinay
 *
 */
public class Alert extends AbstractEntity {

	private AlertType type;
	private String description, name;
	private boolean active;
	private String email;

	public Alert() {
		// TODO Auto-generated constructor stub
		super(false);
	}

	public Alert(AlertType type, String description, String email, boolean active) {
		super(false);
		Objects.requireNonNull(type);
		Objects.requireNonNull(description);
		Objects.requireNonNull(email);
		Objects.requireNonNull(active);
		this.type = type;
		this.description = description;
		this.email = email;
		this.active = active;

	}

	public AlertType getType() {
		return type;
	}

	public void setType(AlertType type) {
		this.type = type;
	}

	public void setType(String type) {
		switch (type.toLowerCase()) {
		case "void":
			this.setType(AlertType.VOID);
			break;
		case "not active":
		case "notactive":
			this.setType(AlertType.NOT_ACTIVE);
			break;
		case "reboot":
			this.setType(AlertType.REBOOT);
			break;
		case "refund":
			this.setType(AlertType.REFUND);
			break;
		default:
			this.setType(AlertType.MISC);
			break;

		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getName() {
		if (name != null)
			return name;
		else
			return "";
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		Alert other = (Alert) obj;
		if (active != other.active)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Alert [type=" + type + ", description=" + description + ", email=" + email + ", active=" + active + "]";
	}

}