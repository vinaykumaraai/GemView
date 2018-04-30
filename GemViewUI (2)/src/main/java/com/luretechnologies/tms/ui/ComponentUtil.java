/**
 * 
 */
package com.luretechnologies.tms.ui;

import com.luretechnologies.tms.ui.components.FormFieldType;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author sils
 *
 */
public class ComponentUtil {

	public static Component getFormFieldWithLabel(String labelName, FormFieldType type) {
		Component component = null;
		switch (type) {
		case TEXTBOX:
			TextField textField = new TextField(labelName, "");
			textField.setWidth("60%");
			textField.addStyleNames(ValoTheme.TEXTFIELD_INLINE_ICON, "role-textbox", "v-grid-cell",
					ValoTheme.TEXTFIELD_BORDERLESS);
			textField.setResponsive(true);
			textField.setEnabled(false);
			component = textField;
			break;
		case CHECKBOX:
			CheckBox checkBox = new CheckBox(labelName, false);
			checkBox.setEnabled(false);
			checkBox.setSizeFull();
			component = checkBox;
			break;
		case COMBOBOX:
			ComboBox<String> combobox = new ComboBox<String>(labelName);
			combobox.setSizeFull();
			combobox.setEnabled(false);
			component = combobox;
		default:
			break;
		}
		return component;
	}
}
