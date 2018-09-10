package com.luretechnologies.tms.backend.data.entity;

import java.util.Objects;

/**
 * 
 * @author Vinay
 *
 */
public class AppDefaultParam extends AbstractEntity {
	private String parameter, description;
	private String type;
	private String value;
	//Add the value And App Formats
	public AppDefaultParam() {
		// TODO Auto-generated constructor stub
		super(false);
	}

	public AppDefaultParam(Long id, String parameter, String description, String type, String value) {
		/*Objects.requireNonNull(parameter);
		Objects.requireNonNull(description);
		Objects.requireNonNull(type);
		Objects.requireNonNull(value);*/
		this.parameter = parameter;
		this.description = description;
		this.type = type;
		this.value = value;
		this.setId(id);

	}
	public AppDefaultParam(String parameter, String description, String type, String value) {
		super(false);
		Objects.requireNonNull(parameter);
		Objects.requireNonNull(description);
		Objects.requireNonNull(type);
		Objects.requireNonNull(value);
		this.parameter = parameter;
		this.description = description;
		this.type = type;
		this.value = value;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return parameter;
	}
	 
}