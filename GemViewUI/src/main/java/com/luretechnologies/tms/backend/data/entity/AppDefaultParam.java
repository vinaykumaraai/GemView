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
	private boolean value;
	//Add the value And App Formats
	public AppDefaultParam() {
		// TODO Auto-generated constructor stub
		super(false);
	}

	public AppDefaultParam(Long id, String parameter, String description, ParameterType type, boolean value) {
		Objects.requireNonNull(parameter);
		Objects.requireNonNull(description);
		Objects.requireNonNull(type);
		Objects.requireNonNull(value);
		this.parameter = parameter;
		this.description = description;
		this.type = type;
		this.value = value;
		this.setId(id);

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

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "AppDefaultParam [parameter=" + parameter + ", description=" + description + ", type=" + type
				+ ", active=" + value + "]";
	}
	 
}