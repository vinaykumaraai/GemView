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
	String value,description;

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
		return super.getName();
	}
public ApplicationFile(Long id,String name,String description,String value) {
	super(name);
	
}

}
