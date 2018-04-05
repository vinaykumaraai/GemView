/**
 * 
 */
package com.luretechnologies.tms.backend.data.entity;

/**
 * @author sils
 *
 */
public enum DebugType {
INFO("Info"),WARN("Warning"),ERROR("Error");
String value;
	DebugType(String value) {
		this.value = value;
	}

}
