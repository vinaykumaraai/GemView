package com.luretechnologies.tms.backend.data.entity;

import java.util.Objects;

/**
 * 
 * @author sils
 *
 */
public class PackageFile extends AbstractEntity {

	private String description, name;

	public PackageFile() {
		// TODO Auto-generated constructor stub
		super(false);
	}

	public PackageFile(String Name, String description) {
		super(false);
		Objects.requireNonNull(Name);
		Objects.requireNonNull(description);
		this.name=Name;
		this.description = description;

	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		PackageFile other = (PackageFile) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PackageFile [description=" + description + ", name=" + name + "]";
	}

	

}