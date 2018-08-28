/**
 * COPYRIGHT @ Lure Technologies, LLC.
 * ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or
 * form other than in accordance with and subject to the terms of a written
 * license from Lure or with the prior written consent of Lure or as
 * permitted by applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure.  If you are neither the
 * intended recipient, nor an agent, employee, nor independent contractor
 * responsible for delivering this message to the intended recipient, you are
 * prohibited from copying, disclosing, distributing, disseminating, and/or
 * using the information in this email in any manner. If you have received
 * this message in error, please advise us immediately at
 * legal@luretechnologies.com by return email and then delete the message from your
 * computer and all other records (whether electronic, hard copy, or
 * otherwise).
 *
 * Any copies or reproductions of this software program (in whole or in part)
 * made by any method must also include a copy of this legend.
 *
 * Inquiries should be made to legal@luretechnologies.com
 *
 */
package com.luretechnologies.tms.ui.components;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.tms.app.Application;
import com.luretechnologies.tms.ui.MainView;
import com.luretechnologies.tms.ui.navigation.NavigationManager;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Notification.CloseEvent;
import com.vaadin.ui.Notification.CloseListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Vinay
 *
 */
@SpringComponent
public class ComponentUtil {
	
	@Autowired
	public static MainView mainView;
	
	private static ServletContext servletContext;
	

	@Autowired
	public ComponentUtil(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public static Component getFormFieldWithLabel(String labelName, FormFieldType type) {
		Component component = null;
		switch (type) {
		case TEXTBOX:
			TextField textField = new TextField(labelName, "");
			textField.setWidth("60%");
			textField.addStyleNames("role-textbox", "v-grid-cell",
					ValoTheme.TEXTFIELD_BORDERLESS, "v-textfield-lineHeight");
			textField.setResponsive(true);
			textField.setEnabled(false);
			component = textField;
			if(labelName.equals("Debug Duration")) {
				textField.setPlaceholder("MM/DD/YYYY");
				textField.setEnabled(true);
			}
			break;
		case HORIZONTALLAYOUT:
			HorizontalLayout activeBoxLayout = new HorizontalLayout();
			if(labelName.equals("Entity Active")) {
				Label active = new Label("Active");
				active.setStyleName("role-activeLable");
				active.addStyleNames("v-textfield-font");
				active.addStyleName("asset-alertCheckbox");
				CheckBox activeCheckBox = new CheckBox();
				activeCheckBox.setCaption(labelName);
				activeCheckBox.addStyleName("v-textfield-font");
				activeBoxLayout.addComponents(active, activeCheckBox);
				//activeBoxLayout.setStyleName("role-activeLable");
				activeBoxLayout.addStyleName("asset-alertActiveLayout");
				activeBoxLayout.setEnabled(false);
				component =activeBoxLayout;
			} else if(labelName.equals("Receive Debug")) {
				Label active = new Label("Debug");
				active.setStyleName("role-activeLable");
				active.addStyleNames("v-textfield-font");
				active.addStyleName("asset-debugCheckbox");
				CheckBox activeCheckBox = new CheckBox();
				activeCheckBox.setCaption(labelName);
				activeCheckBox.addStyleName("v-textfield-font");
				activeBoxLayout.addComponents(active, activeCheckBox);
				//activeBoxLayout.setStyleName("role-activeLable");
				activeBoxLayout.addStyleName("asset-debugCheckboxLayout");
				activeBoxLayout.setEnabled(true);
				component =activeBoxLayout;
			}else if(labelName.isEmpty()){
				Label active = new Label("Active");
				active.setStyleName("role-activeLable");
				active.addStyleNames("v-textfield-font");
				active.addStyleName("asset-alertCheckbox");
				CheckBox activeCheckBox = new CheckBox();
				activeCheckBox.addStyleName("v-textfield-font");
				activeBoxLayout.addComponents(active, activeCheckBox);
				activeBoxLayout.addStyleName("asset-alertActiveLayout");
				activeBoxLayout.setEnabled(false);
				component =activeBoxLayout;
			}
			break;
		case CHECKBOX:
			CheckBox checkBox = new CheckBox(labelName, false);
			checkBox.setEnabled(false);
			checkBox.setSizeFull();
			component = checkBox;
			break;
		case COMBOBOX:
			ComboBox<String> combobox = new ComboBox<String>(labelName);
			combobox.setEnabled(true);
			combobox.addStyleName("asset-debugComboBox");
			component = combobox;
		default:
			break;
		}
		return component;
	}
	
	public static void addValidator(AbstractField field, Validator validator) {
        field.addValueChangeListener(event -> {
            ValidationResult result = validator.apply(event.getValue(), new ValueContext(field));

            if (result.isError()) {
                UserError error = new UserError(result.getErrorMessage());
                field.setComponentError(error);
            } else {
                field.setComponentError(null);
            }
        });
    }
	
	public static void sessionExpired(Notification notification) {
		notification.setPosition(Position.TOP_CENTER);
		notification.setDelayMsec(5000);
		notification.addCloseListener(new CloseListener() {
		
			@Override
			public void notificationClose(CloseEvent e) {
				Page.getCurrent().setLocation(getAbsoluteUrl(Application.LOGIN_URL));
			
			}
		});
	}
	
	private static String getAbsoluteUrl(String url) {
		final String relativeUrl;
		if (url.startsWith("/")) {
			relativeUrl = url.substring(1);
		} else {
			relativeUrl = url;
		}
		return servletContext.getContextPath() + "/" + relativeUrl;
	}
}
