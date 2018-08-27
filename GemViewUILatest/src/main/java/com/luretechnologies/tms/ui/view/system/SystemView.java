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
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.tms.backend.data.entity.Permission;
import com.luretechnologies.tms.backend.data.entity.Systems;
import com.luretechnologies.tms.backend.service.RolesService;
import com.luretechnologies.tms.backend.service.SystemService;
import com.luretechnologies.tms.backend.service.UserService;
import com.luretechnologies.tms.ui.MainView;
import com.luretechnologies.tms.ui.components.ComponentUtil;
import com.luretechnologies.tms.ui.components.ConfirmDialogFactory;
import com.luretechnologies.tms.ui.components.FormFieldType;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.luretechnologies.tms.ui.view.deviceodometer.DeviceodometerView;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
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
import com.vaadin.ui.Notification.Type;
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
	private TextField systemValue;
	private ComboBox<String> comboBoxType;
	private Button save;
	private Button cancel;
	private Button delete;
	private Button edit;
	private Button create;
	
	@Autowired
	public ConfirmDialogFactory confirmDialogFactory;
	
	@Autowired
	public SystemService systemService;
	
	@Autowired
	private RolesService roleService;
	
	@Autowired
	MainView mainView;
	
	@Autowired
	UserService userService;

	@Autowired
	public SystemView() {
		selectedSystem = new Systems();
	}
	
	@PostConstruct
	private void inti() {
		try {
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
		
		cancel = new Button("Cancel");
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
		
		save = new Button("Save");
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
				if(description.isEmpty() || description== null|| parametername.isEmpty() || parametername== null 
						|| type.isEmpty() || type== null || value.isEmpty() || value== null) {
					Notification.show(NotificationUtil.SAVE, Notification.Type.ERROR_MESSAGE);
				} else {
						if(systemGrid.getSelectedItems().size()>0) {
							Systems system = systemGrid.getSelectedItems().iterator().next();
							List<Systems> systemList = systemService.getAllSystemParam();
							if(containsSystemInList(systemList, system)) {
								selectedSystem = systemService.updateSystem(system, description, parametername, type, value);
								DataProvider data = new ListDataProvider(systemService.getAllSystemParam());
								systemGrid.setDataProvider(data);
								systemInfoLayout.removeAllComponents();
								clearParamComponents();
								getAndLoadSystemForm(systemInfoLayout, false);
								systemGrid.select(selectedSystem);
							}
						}else{
							if(!checkParamNameInList( parametername)) {
								selectedSystem = systemService.createSystemParam(parametername, description, value, type);
								DataProvider data = new ListDataProvider(systemService.getAllSystemParam());
								systemGrid.setDataProvider(data);
								systemGrid.select(selectedSystem);
								systemInfoLayout.removeAllComponents();
								clearParamComponents();
								getAndLoadSystemForm(systemInfoLayout, false);
							} else {
								Notification.show(NotificationUtil.SYSTEM_PARAM_EXIST, Type.ERROR_MESSAGE);
								parameterName.focus();
							}
						}
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
		
		Permission appStorePermission = roleService.getLoggedInUserRolePermissions().stream().filter(per -> per.getPageName().equals("SYSTEM")).findFirst().get();
		
			disableAllComponents();
		allowAccessBasedOnPermission(appStorePermission.getAdd(),appStorePermission.getEdit(),appStorePermission.getDelete());
		
		Page.getCurrent().addBrowserWindowResizeListener(r -> {
			System.out.println("Height " + r.getHeight() + "Width:  " + r.getWidth() + " in pixel");
			
			if(r.getWidth()<=600) {
				mainView.getTitle().setValue(userService.getLoggedInUserName());
			} else if(r.getWidth()>600 && r.getWidth()<=1000){
				mainView.getTitle().setValue("gemView  "+ userService.getLoggedInUserName());
			}else {
				mainView.getTitle().setValue("gemView  "+ userService.getLoggedInUserName());
			}
		});
		
		int width = Page.getCurrent().getBrowserWindowWidth();
		if(width<=600) {
			mainView.getTitle().setValue(userService.getLoggedInUserName());
		} else if(width>600 && width<=1000){
			mainView.getTitle().setValue("gemView  "+ userService.getLoggedInUserName());
		}else {
			mainView.getTitle().setValue("gemView  "+ userService.getLoggedInUserName());
		}
		
		}catch(Exception ex) {
			systemService.logSystemScreenErrors(ex);
		}
	}
	
	private void disableAllComponents() throws Exception {
		create.setEnabled(false);
		edit.setEnabled(false);
		delete.setEnabled(false);
		save.setEnabled(false);
		cancel.setEnabled(false);
	}
	
	private void allowAccessBasedOnPermission(Boolean addBoolean, Boolean editBoolean, Boolean deleteBoolean) {
		create.setEnabled(addBoolean);
		edit.setEnabled(editBoolean);
		delete.setEnabled(deleteBoolean);
		save.setEnabled(editBoolean || addBoolean);
		cancel.setEnabled(editBoolean || addBoolean);
	}
	
	public Panel getAndLoadSystemPanel() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setCaption("Systems");
		panel.setResponsive(true);
		panel.setSizeFull();
        addComponent(panel);
       return panel;
	}
	
	private void clearParamComponents() {
		parameterName.clear();
		systemDescription.clear();
		systemValue.clear();
		comboBoxType.clear();
	}
	
	private boolean checkParamNameInList(String parametername) {
		ListDataProvider<Systems> provider = (ListDataProvider<Systems>)systemGrid.getDataProvider();
		for(Systems systemParam : provider.getItems()) {
			String paramName = systemParam.getParameterName();
			if(paramName.equalsIgnoreCase(parametername)) {
				return true;
			}
		}
		return false;
		
	}
	
	private boolean containsSystemInList(List<Systems> systemList, Systems system) {
		for(Systems subSystem : systemList)
		{
			if(subSystem.getId().equals(system.getId())) {
				return true;
			}
		}
		return false;
		
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
		parameterName = new TextField("Parameter Name", parametername.toUpperCase());
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
		comboBoxType = (ComboBox<String>)ComponentUtil.getFormFieldWithLabel("", FormFieldType.COMBOBOX);
		comboBoxType.setEnabled(isEditableOnly);
		comboBoxType.setCaptionAsHtml(true);
		comboBoxType.setCaption("Type"); 
		comboBoxType.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size","system-TypeAlignment", "small");
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
	
	private void getSystemGrid(VerticalLayout verticalLayout, VerticalLayout systemInfoLayout) throws ApiException {
		create = new Button(VaadinIcons.FOLDER_ADD);
		create.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		create.addStyleName("v-button-customstyle");
		create.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				systemGrid.deselectAll();
				systemInfoLayout.removeAllComponents();
				selectedSystem = new Systems();
				getAndLoadSystemForm(systemInfoLayout, true);
				getFirstFormField().focus();
			}
		});
		
		edit = new Button(VaadinIcons.EDIT);
		edit.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		edit.addStyleName("v-button-customstyle");
		edit.addClickListener(new ClickListener() {
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
		
		delete = new Button(VaadinIcons.TRASH);
		delete.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		delete.addStyleName("v-button-customstyle");
		delete.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(systemDescription.getValue()==null || systemDescription.getValue().isEmpty() || 
						parameterName.getValue()==null ||  parameterName.getValue().isEmpty() ||
								comboBoxType.getValue()==null ||  comboBoxType.getValue().isEmpty() ||
						systemValue.getValue()==null ||  systemValue.getValue().isEmpty()) {
					Notification.show(NotificationUtil.SYSTEM_DELETE, Notification.Type.ERROR_MESSAGE);
				}else {
				
				confirmDialog( systemInfoLayout, selectedSystem);
				}
			}
		});
		
		HorizontalLayout buttonGroup =  new HorizontalLayout();
		buttonGroup.addStyleName("system-ButtonLayout");
		buttonGroup.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		buttonGroup.addComponent(create);
		buttonGroup.addComponent(edit);
		buttonGroup.addComponent(delete);
		
		systemGrid.setCaptionAsHtml(true);
		systemGrid.addStyleName("v-grid-cell-fontSize");
		systemGrid.setHeightByRows(12);
		systemGrid.addColumn(Systems::getParameterName).setCaption("Parameter Name");
		systemGrid.addColumn(Systems::getDescription).setCaption("Description");
		systemGrid.addColumn(Systems::getType).setCaption("Type");
		systemGrid.addColumn(Systems::getSystemValue).setCaption("Value");
		
		DataProvider data = new ListDataProvider(systemService.getAllSystemParam());
		systemGrid.setDataProvider(data);
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
				clearParamComponents();
			}
		});
		VerticalLayout systemGridLayout = new VerticalLayout();
		systemGridLayout.addStyleName("system-GridAlignment");
		systemGridLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		systemGridLayout.addComponent(buttonGroup);
		systemGridLayout.addComponent(systemGrid);
		verticalLayout.addComponent(systemGridLayout);
		
	}
	
	private void confirmDialog(VerticalLayout systemInfoLayout, Systems system) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?",
		        "Ok", "Cancel", new ConfirmDialog.Listener() {

		            public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		                    // Confirmed to continue
		                	systemInfoLayout.removeAllComponents();
		                	
								systemService.deleteSystem(system);
								DataProvider data = new ListDataProvider(systemService.getAllSystemParam());
								systemGrid.setDataProvider(data);
								selectedSystem = new Systems();
								systemGrid.getDataProvider().refreshAll();
			                	getAndLoadSystemForm(systemInfoLayout, false);
		                } else {
		                    // User did not confirm
		                    
		                }
		            }
		        });
	}
	
}
