/**
 * 
 */
package com.luretechnologies.tms.backend.data.entity;

/**
 * @author sils
 *
 */
public enum ProfileType {
	FOOD("Food"),RETAIL("Retail"),MOTO("Moto");
	String value;
	ProfileType(String value) {
			this.value = value;
		}

}
