/**
 * 
 */
package com.luretechnologies.tms.ui.components;

/**
 * @author sils
 *
 */
public enum FormFieldType {
	TEXTBOX("TextBox"), CHECKBOX("CheckBox");
	String type;

	FormFieldType(String type) {
		this.type = type;
	}
}
