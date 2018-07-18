/**
 * 
 */
package com.luretechnologies.tms.backend.data.entity;

import java.io.File;
import java.net.URI;

/**
 * @author Vinay
 *
 */
public class ApplicationFile extends File {
	String name,value,description;
	Long id;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ApplicationFile(File parent, String child) {
		super(parent, child);
		// TODO Auto-generated constructor stub
	}

	public ApplicationFile(String parent, String child) {
		super(parent, child);
		// TODO Auto-generated constructor stub
	}

	public ApplicationFile(String pathname) {
		super(pathname);
		// TODO Auto-generated constructor stub
	}

	public ApplicationFile(URI uri) {
		super(uri);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
public ApplicationFile(Long id,String name,String description,String value) {
	super(name);
	this.id=id;
	this.name=name;
	this.description=description;
	this.value=value;
}

}
