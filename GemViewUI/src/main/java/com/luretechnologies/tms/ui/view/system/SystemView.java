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
package com.luretechnologies.tms.ui.view.system;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.Systems;
import com.luretechnologies.tms.ui.ComponentUtil;
import com.luretechnologies.tms.ui.NotificationUtil;
import com.luretechnologies.tms.ui.components.ConfirmDialogFactory;
import com.luretechnologies.tms.ui.components.FormFieldType;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component.Focusable;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = SystemView.VIEW_NAME)
public class SystemView extends VerticalLayout implements Serializable, View{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String VIEW_NAME = "system";
	
	Map<Integer, Systems> systemRepo = new LinkedHashMap<>();
	Grid<Systems> systemGrid = new Grid<Systems>();
	private volatile Systems selectedSystem;
	private TextField systemDescription;
	private TextField parameterName;
	//private TextField systemType;
	private TextField systemValue;
	private ComboBox<String> comboBoxType;
	
	@Autowired
	public ConfirmDialogFactory confirmDialogFactory;
	
	
	@Autowired
	public SystemView() {
		selectedSystem = new Systems();
		
		Systems system = new Systems();
		system.setParameterName("SERVER1 IP");
		system.setDescription("Server1 IP Address");
		system.setType("Text");
		system.setSystemValue("1.1.1.1");
		system.setId(1);
		
		Systems system1 = new Systems();
		system1.setParameterName("SERVER2 IP");
		system1.setDescription("Server2 IP Address");
		system1.setType("Text");
		system1.setSystemValue("1.0.1.0");
		system1.setId(2);
		
		Systems system2 = new Systems();
		system2.setParameterName("AVAILABLE CON");
		system2.setDescription("Available Connections");
		system2.setType("Numeric");
		system2.setSystemValue("23");
		system2.setId(3);
		
		Systems system3 = new Systems();
		system3.setParameterName("SYSTEM MESSAGE");
		system3.setDescription("Welcome Message");
		system3.setType("Boolean");
		system3.setSystemValue("Welcome to GemView");
		system3.setId(4);
		
		
		systemRepo.put(system.getId(), system);
		systemRepo.put(system1.getId(), system1);
		systemRepo.put(system2.getId(), system2);
		systemRepo.put(system3.getId(), system3);
		
		for(int index =5; index<=25; index++) {
			Systems systemLoop = new Systems();
			systemLoop.setParameterName("SYSTEM MESSAGE "+index);
			systemLoop.setDescription("Welcome Message "+index);
			systemLoop.setType("Text");
			systemLoop.setSystemValue("Welcome to GemView "+index);
			systemLoop.setId(index);
			systemRepo.put(systemLoop.getId(), systemLoop);
		}
		
	}
	
	@PostConstruct
	private void inti() {
		setHeight("100%");
		setSpacing(false);
		setMargin(false);
		setResponsive(true);
		Panel panel = getAndLoadSystemPanel();
		VerticalLayout verticalLayout = new VerticalLayout();
		panel.setContent(verticalLayout);
		verticalLayout.setSpacing(false);
		verticalLayout.setMargin(false);
		Label availableSystem = new Label("Parameters & Settings", ContentMode.HTML);
		availableSystem.addStyleName("label-style");
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSizeFull();
		
		HorizontalLayout layout1 = new HorizontalLayout();
		layout1.addComponent(availableSystem);
		layout1.setComponentAlignment(availableSystem, Alignment.MIDDLE_LEFT);
		layout1.setSizeFull();
		
		HorizontalLayout layout2 = new HorizontalLayout();
		layout2.setSizeUndefined();
		layout2.setResponsive(true);
		
		verticalLayout.addComponent(horizontalLayout);
		VerticalLayout systemInfoLayout = new VerticalLayout();
		verticalLayout.addComponent(systemInfoLayout);
		systemInfoLayout.setMargin(false);
		systemInfoLayout.setSpacing(true);
		systemInfoLayout.setStyleName("form-layout");
		
		getAndLoadSystemForm(systemInfoLayout, false);
		
		Button cancel = new Button("Cancel");
		cancel.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		cancel.addStyleName("v-button-customstyle");
		cancel.setResponsive(true);
		cancel.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {	
				systemInfoLayout.removeAllComponents();
				systemGrid.getDataProvider().refreshAll();
				systemGrid.deselectAll();
				selectedSystem = new Systems();
				getAndLoadSystemForm(systemInfoLayout, false);	
			}
		});
		layout2.addComponent(cancel);
		
		Button save = new Button("Save");
		save.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		save.addStyleName("v-button-customstyle");
		save.setResponsive(true);
		save.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				String description = systemDescription.getValue();
				String parametername = parameterName.getValue();
				String type = comboBoxType.getValue();
				String value = systemValue.getValue();
				selectedSystem.setParameterName(parametername);
				selectedSystem.setDescription(description);
				selectedSystem.setType(type);
				selectedSystem.setSystemValue(value);
				if(description.isEmpty() || description== null|| parametername.isEmpty() || parametername== null 
						|| type.isEmpty() || type== null || value.isEmpty() || value== null) {
					Notification.show(NotificationUtil.SAVE, Notification.Type.ERROR_MESSAGE);
				} else {
					//systemRepo.co
					Random rand = new Random();
					int  n = rand.nextInt(50) + 1;
					if(selectedSystem.getId()==null) {
						selectedSystem.setId(n);
					}
				systemRepo.put(selectedSystem.getId(), selectedSystem);
				systemGrid.getDataProvider().refreshAll();
				systemGrid.select(selectedSystem);
				systemInfoLayout.removeAllComponents();
				getAndLoadSystemForm(systemInfoLayout, false);
				}
			}
		});
		
		layout2.addComponent(save);
		layout2.setComponentAlignment(cancel, Alignment.MIDDLE_RIGHT);
		layout2.setComponentAlignment(save, Alignment.MIDDLE_RIGHT);
		layout2.setResponsive(true);
		layout2.addStyleNames("save-cancelButtonsAlignment");
		
		horizontalLayout.addComponents(layout1, layout2);
		horizontalLayout.addStyleName("label-SaveCancelAlignment");
		horizontalLayout.setComponentAlignment(layout2, Alignment.MIDDLE_RIGHT);
		
		getSystemGrid(verticalLayout, systemInfoLayout);
	}
	
	public Panel getAndLoadSystemPanel() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setCaption("Systems");
		//panel.setStyleName("h1");
		panel.setResponsive(true);
		panel.setSizeFull();
        addComponent(panel);
       return panel;
	}
	
	private void getAndLoadSystemForm(VerticalLayout verticalLayout , boolean isEditableOnly) {
		FormLayout formLayout = new FormLayout();
		formLayout.addStyleName("system-LabelAlignment");
		
		getSystemParameterName(formLayout, isEditableOnly);
		
		getSystemDescription(formLayout, isEditableOnly);
		
		getSystemType(formLayout, isEditableOnly);
		
		getSystemValue(formLayout, isEditableOnly);
		
		verticalLayout.addComponent(formLayout);
	}
	
	private void getSystemParameterName(FormLayout formLayout , boolean isEditableOnly) {
		
		String parametername = selectedSystem.getParameterName() != null ? selectedSystem.getParameterName(): "";
		parameterName = new TextField("Parameter Name", parametername);
		selectedSystem.setParameterName(parameterName.getValue().toUpperCase());
		parameterName.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		parameterName.setWidth("48%");
		parameterName.setStyleName("role-textbox");
		parameterName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		parameterName.addStyleNames("v-textfield-font", "v-grid-cell");
		parameterName.setEnabled(isEditableOnly);
		formLayout.addComponent(parameterName);
	}
	
	private void getSystemDescription(FormLayout formLayout, boolean isEditableOnly) {
		String description = selectedSystem.getDescription() != null ? selectedSystem.getDescription(): "";
		systemDescription = new TextField("Description", description);
		selectedSystem.setDescription(systemDescription.getValue());
		systemDescription.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		systemDescription.setWidth("48%");
		systemDescription.setStyleName("role-textbox");
		systemDescription.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		systemDescription.addStyleName("v-grid-cell");
		systemDescription.setEnabled(isEditableOnly);
		formLayout.addComponent(systemDescription);
	}
	
	private void getSystemType(FormLayout formLayout, boolean isEditableOnly) {
		String type = selectedSystem.getType() != null ? selectedSystem.getType(): "";
//		systemType = new TextField("Type", type);
//		selectedSystem.setType(systemType.getValue());
//		systemType.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
//		systemType.setWidth("48%");
//		systemType.setStyleName("role-textbox");
//		systemType.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
//		systemType.addStyleName("v-grid-cell");
//		systemType.setEnabled(isEditableOnly);
//		formLayout.addComponent(systemType);
		comboBoxType = (ComboBox<String>)ComponentUtil.getFormFieldWithLabel("", FormFieldType.COMBOBOX);
		comboBoxType.setEnabled(isEditableOnly);
		comboBoxType.setCaptionAsHtml(true);
		comboBoxType.setCaption("Type"); 
		//combobox.addStyleName();
		comboBoxType.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size","system-TypeAlignment");
		comboBoxType.setDataProvider(new ListDataProvider<>(Arrays.asList("Text", "Numeric","Boolean")));
		comboBoxType.setValue(type);
		formLayout.addComponent(comboBoxType);
	}
	
	private void getSystemValue(FormLayout formLayout, boolean isEditableOnly) {
		String value = selectedSystem.getSystemValue() != null ? selectedSystem.getSystemValue(): "";
		systemValue = new TextField("Value", value);
		selectedSystem.setSystemValue(systemValue.getValue());
		systemValue.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		systemValue.setWidth("48%");
		systemValue.setStyleName("role-textbox");
		systemValue.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		systemValue.addStyleName("v-grid-cell");
		systemValue.setEnabled(isEditableOnly);
		formLayout.addComponent(systemValue);
	}
	
	private Focusable getFirstFormField() {
		return parameterName;
	}
	
	private void getSystemGrid(VerticalLayout verticalLayout, VerticalLayout systemInfoLayout) {
		Button addNewDevice = new Button(VaadinIcons.FOLDER_ADD);
		addNewDevice.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		addNewDevice.addStyleName("v-button-customstyle");
		addNewDevice.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				systemGrid.deselectAll();
				systemInfoLayout.removeAllComponents();
				selectedSystem = new Systems();
				getAndLoadSystemForm(systemInfoLayout, true);
				getFirstFormField().focus();
			}
		});
		
		Button editDevice = new Button(VaadinIcons.EDIT);
		editDevice.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		editDevice.addStyleName("v-button-customstyle");
		editDevice.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				//parameterName.focus();
				if(systemDescription.getValue()==null || systemDescription.getValue().isEmpty() || 
						parameterName.getValue()==null ||  parameterName.getValue().isEmpty() ||
								comboBoxType.getValue()==null ||  comboBoxType.getValue().isEmpty() ||
								systemValue.getValue()==null ||  systemValue.getValue().isEmpty()) {
					Notification.show(NotificationUtil.SYSTEM_EDIT, Notification.Type.ERROR_MESSAGE);
				}else {
					systemInfoLayout.removeAllComponents();
					getAndLoadSystemForm(systemInfoLayout, true);	
					getFirstFormField().focus();
				}
			}
		});
		
		Button deleteDevice = new Button(VaadinIcons.TRASH);
		deleteDevice.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		deleteDevice.addStyleName("v-button-customstyle");
		deleteDevice.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(systemDescription.getValue()==null || systemDescription.getValue().isEmpty() || 
						parameterName.getValue()==null ||  parameterName.getValue().isEmpty() ||
								comboBoxType.getValue()==null ||  comboBoxType.getValue().isEmpty() ||
						systemValue.getValue()==null ||  systemValue.getValue().isEmpty()) {
					Notification.show(NotificationUtil.SYSTEM_DELETE, Notification.Type.ERROR_MESSAGE);
				}else {
				
				confirmDialog( systemInfoLayout);
				}
			}
		});
		
		HorizontalLayout buttonGroup =  new HorizontalLayout();
		buttonGroup.addStyleName("system-ButtonLayout");
		buttonGroup.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		buttonGroup.addComponent(addNewDevice);
		buttonGroup.addComponent(editDevice);
		buttonGroup.addComponent(deleteDevice);
		
		systemGrid.setCaptionAsHtml(true);
		systemGrid.addStyleName("v-grid-cell-fontSize");
		systemGrid.setHeightByRows(12);
		systemGrid.addColumn(Systems::getParameterName).setCaption("Paramater");
		systemGrid.addColumn(Systems::getDescription).setCaption("Description");
		systemGrid.addColumn(Systems:: getType).setCaption("Type");
		systemGrid.addColumn(Systems:: getSystemValue).setCaption("Value");
		
		systemGrid.setItems(systemRepo.values());
		systemGrid.setWidth("100%");
		systemGrid.setResponsive(true);
		
		systemGrid.addSelectionListener(e -> {
			if (!e.isUserOriginated()) {
				return;
			}

			if (e.getFirstSelectedItem().isPresent()) {
				//Load and update the data in permission grid.
				systemInfoLayout.removeAllComponents();
				Systems selectedSystem = e.getFirstSelectedItem().get();
				this.selectedSystem = selectedSystem;
				getAndLoadSystemForm(systemInfoLayout, false);
			} else {
				throw new IllegalStateException("This should never happen as deselection is not allowed");
			}
		});
		VerticalLayout systemGridLayout = new VerticalLayout();
		systemGridLayout.addStyleName("system-GridAlignment");
		systemGridLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		systemGridLayout.addComponent(buttonGroup);
		systemGridLayout.addComponent(systemGrid);
		verticalLayout.addComponent(systemGridLayout);
		
	}
	
	private void confirmDialog(VerticalLayout systemInfoLayout) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?",
		        "Ok", "Cancel", new ConfirmDialog.Listener() {

		            public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		                    // Confirmed to continue
		                	systemInfoLayout.removeAllComponents();
		                	systemRepo.remove(selectedSystem.getId());
		                	systemGrid.getDataProvider().refreshAll();
		                	selectedSystem = new Systems();
		                	getAndLoadSystemForm(systemInfoLayout, false);
		                } else {
		                    // User did not confirm
		                    
		                }
		            }
		        });
	}
	
}
