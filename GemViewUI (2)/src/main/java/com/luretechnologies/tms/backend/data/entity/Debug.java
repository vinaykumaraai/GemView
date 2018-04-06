package com.luretechnologies.tms.backend.data.entity;

import java.util.Objects;
import java.util.UUID;

// "User" is a reserved word in some SQL implementations
public class Debug extends AbstractEntity {

	private DebugType type;
	private String description;
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
	public Debug() {
		// TODO Auto-generated constructor stub
	}
	public Debug(DebugType type, String description) {
		super(false);
		Objects.requireNonNull(type);
		Objects.requireNonNull(description);
		this.type = type;
		this.description = description;
	}
	@Override
	public String toString() {
		return "Debug [type=" + type + ", description=" + description + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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