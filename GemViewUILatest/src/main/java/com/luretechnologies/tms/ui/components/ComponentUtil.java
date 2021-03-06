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

import com.luretechnologies.tms.app.Application;
import com.luretechnologies.tms.ui.MainView;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringComponent;
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
			textField.setWidth("100%");
			textField.addStyleNames("v-textfield-font","textfiled-height");
			textField.setResponsive(true);
			textField.setEnabled(false);
			textField.setMaxLength(50);
			component = textField;
			if(labelName.equals("Debug Duration")) {
				textField.setPlaceholder("MM/DD/YYYY");
				textField.setEnabled(true);
			}
			textField.addValueChangeListener(listener->{
				if(listener.getValue().length()==50) {
					Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
				}
			});
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
	
	public static boolean validatorTextField(TextField component) {
		if (component != null) {
			if (component.getValue() == null || component.getValue().trim().isEmpty()) {
				if(component.getId() == "ignore"){
					return true;
				}else {
				Notification.show("Enter " + component.getCaption() + " field", Type.ERROR_MESSAGE);
				return false;
				}
			} 
		}else {
			return true;
		}
		return true;	
	}
	
	public static boolean validatorEmailId(TextField component) {
		if (component != null) {
			if (component.getValue() == null || component.getValue().trim().isEmpty()) {
				if(component.getId() == "ignore"){
					return true;
				}else {
				Notification.show("Enter " + component.getCaption() + " field", Type.ERROR_MESSAGE);
				return false;
				}
			} else {
				if(!component.getValue().contains("@")) {
					Notification.show("Enter valid email Id in " + component.getCaption() + " field", Type.ERROR_MESSAGE);
					component.focus();
					return false;
				}
			}
		}else {
			return true;
		}
		return true;	
	}
	
	public static boolean validateUserName(TextField component) {
		if (component != null) {
			if (component.getValue() == null || component.getValue().trim().isEmpty()) {
				if(component.getId() == "ignore"){
					return true;
				}else {
				Notification.show("Enter " + component.getCaption() + " field", Type.ERROR_MESSAGE);
				return false;
				}
			} else {
				if(component.getValue().contains(" ")) {
					Notification.show("No Spaces are Allowed in " + component.getCaption() + " field", Type.ERROR_MESSAGE);
					component.focus();
					return false;
				}
			}
		}else {
			return true;
		}
		return true;	
	}
	
	public static boolean validatorComboBox(ComboBox component) {
		if (component != null) {
			if (component.getValue() == null || component.getValue().toString().trim().isEmpty()) {
				if(component.getId() == "Password Frequency") {
					Notification.show("Select Password field", Type.ERROR_MESSAGE);
					return false;
				}else {
					Notification.show("Enter " + component.getCaption() + " field", Type.ERROR_MESSAGE);
				return false;
				}
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
	
	public static boolean validatorCheckBox(CheckBox component) {
		if (component != null) {
			if (component.getValue() == null || component.getValue() == false) {
				Notification.show("Check Active checkbox", Type.ERROR_MESSAGE);
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
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
