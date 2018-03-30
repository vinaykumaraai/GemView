/**
 * 
 */
package com.luretechnologies.tms.backend.data.entity;

/**
 * @author sils
 *
 */
public enum NodeLevel {
ENTITY("Entity"),MERCHANT("Merchant"),REGION("Region"),TERMINAL("Terminal"),DEVICE("Device");
String name;
	NodeLevel(String name) {
		this.name = name;
	}

}
