package com.luretechnologies.tms.backend.data.entity;

import java.util.Objects;

/**
 * 
 * @author Vinay
 *
 */
public class AppDefaultParam extends AbstractEntity {
	private String parameter, description;
	private ParameterType type;
	private boolean active;
	public AppDefaultParam() {
		// TODO Auto-generated constructor stub
		super(false);
	}

	public AppDefaultParam(String parameter, String description, ParameterType type, boolean active) {
		super(false);
		Objects.requireNonNull(parameter);
		Objects.requireNonNull(description);
		Objects.requireNonNull(type);
		Objects.requireNonNull(active);
		this.parameter = parameter;
		this.description = description;
		this.type = type;
		this.active = active;

	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ParameterType getType() {
		return type;
	}

	public void setType(ParameterType type) {
		this.type = type;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "AppDefaultParam [parameter=" + parameter + ", description=" + description + ", type=" + type
				+ ", active=" + active + "]";
	}
	 
}