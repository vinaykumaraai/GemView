/**
 * 
 */
package com.luretechnologies.tms.backend.data.entity;

import java.io.File;
import java.net.URI;

/**
 * @author sils
 *
 */
public class ApplicationFile extends File {

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
}
