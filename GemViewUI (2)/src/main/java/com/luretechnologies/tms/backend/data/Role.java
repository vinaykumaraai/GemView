package com.luretechnologies.tms.backend.data;

public class Role {
	public static final String IT = "IT";
	public static final String HR = "HR";
	public static final String ADMIN = "admin";

	private Role() {
		// Static methods and fields only
	}

	public static String[] getAllRoles() {
		return new String[] { IT, HR, ADMIN };
	}

}
