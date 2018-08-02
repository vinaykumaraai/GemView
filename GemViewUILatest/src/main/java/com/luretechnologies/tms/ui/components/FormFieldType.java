/**
 * 
 */
package com.luretechnologies.tms.ui.components;

/**
 * @author sils
 *
 */
public enum FormFieldType {
	TEXTBOX("TextBox"), CHECKBOX("CheckBox"), COMBOBOX("Combobox"), HORIZONTALLAYOUT("HorizontalLayout");
	String type;

	FormFieldType(String type) {
		this.type = type;
	}
}
