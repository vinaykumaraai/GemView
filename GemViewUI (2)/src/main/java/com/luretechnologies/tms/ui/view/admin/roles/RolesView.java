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
package com.luretechnologies.tms.ui.view.admin.roles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.grid.cellrenderers.editoraware.CheckboxRenderer;

import com.luretechnologies.tms.backend.data.entity.Permission;
import com.luretechnologies.tms.backend.data.entity.Roles;
import com.luretechnologies.tms.backend.service.UserFriendlyDataException;
import com.luretechnologies.tms.ui.components.ConfirmDialogFactory;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;


@SpringView(name = RolesView.VIEW_NAME)
public class RolesView extends VerticalLayout implements Serializable, View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5530768165606875735L;
	
	public static final String VIEW_NAME = "roles";

	Map<Integer, Roles> rolesRepo = new HashMap<>();
	Grid<Roles> roleGrid = new Grid<Roles>();
	private volatile Roles selectedRole;
	private TextField descriptions;
	private TextField roleName;
	private CheckBox activeBox;
	private Grid<Permission> permissionGrid;
	private Label label;
	
	@Autowired
	public ConfirmDialogFactory confirmDialogFactory;
	
	@Autowired
	public RolesView() {
		selectedRole = new Roles();
		Roles hd = new Roles();
		hd.setActive(true);
		hd.setDescription("Help Desk Access");
		hd.setRoleName("HD");
		hd.setId(1);
		Map<String, Permission> permissionMap = new LinkedHashMap<>();
		permissionMap.put("Dashboard", new Permission("Dashboard", true, true, true, true));
		permissionMap.put("Application Store", new Permission("Application Store",true, true, true, true));
		permissionMap.put("Personalization", new Permission("Personalization",true, true, true, true));
		permissionMap.put("Heartbeat", new Permission("Heartbeat", true, true, true, true));
		permissionMap.put("Asset Control", new Permission("Asset Control",true, false, false, false));
		permissionMap.put("Device Odometer", new Permission("Device Odometer",true, false, false, false));
		permissionMap.put("Security", new Permission("Security", true, true, false, false));
		permissionMap.put("Audit", new Permission("Audit",true, false, false, false));
		permissionMap.put("Users", new Permission("Users",true, false, false, false));
		permissionMap.put("Roles", new Permission("Roles",true, false, false, false));
		permissionMap.put("Devices", new Permission("Devices",true, false, false, false));
		permissionMap.put("System", new Permission("System", true, false, false, false));
		hd.setPermissionMap(permissionMap);
		
		Roles admin = new Roles();
		admin.setActive(true);
		admin.setDescription("Administration Access");
		admin.setRoleName("ADMIN");
		admin.setId(2);
		Map<String, Permission> permissionMapAdmin = new LinkedHashMap<>();
		permissionMapAdmin.put("Dashboard", new Permission("Dashboard", true, false, true, false));
		permissionMapAdmin.put("Application Store", new Permission("Application Store",false, true, false, true));
		permissionMapAdmin.put("Personalization", new Permission("Personalization",true, true, false, true));
		permissionMapAdmin.put("Heartbeat", new Permission("Heartbeat", true, true, false, false));
		permissionMapAdmin.put("Asset Control", new Permission("Asset Control",true, false, false, false));
		permissionMapAdmin.put("Device Odometer", new Permission("Device Odometer",true, false, true, false));
		permissionMapAdmin.put("Security", new Permission("Security", true, true, false, false));
		permissionMapAdmin.put("Audit", new Permission("Audit",true, false, true, false));
		permissionMapAdmin.put("Users", new Permission("Users",true, false, true, false));
		permissionMapAdmin.put("Roles", new Permission("Roles",true, true, false, false));
		permissionMapAdmin.put("Devices", new Permission("Devices",true, false, true, false));
		permissionMapAdmin.put("System", new Permission("System", true, false, false, false));
		admin.setPermissionMap(permissionMapAdmin);

		Roles customer = new Roles();
		customer.setActive(true);
		customer.setDescription("Customer Service Access");
		customer.setRoleName("CUST SERV");
		customer.setId(3);
		Map<String, Permission> permissionMapCustomer = new LinkedHashMap<>();
		permissionMapCustomer.put("Dashboard", new Permission("Dashboard", false, true, false, false));
		permissionMapCustomer.put("Application Store", new Permission("Application Store",false, true, false, true));
		permissionMapCustomer.put("Personalization", new Permission("Personalization",true, false, false, true));
		permissionMapCustomer.put("Heartbeat", new Permission("Heartbeat", true, true, false, false));
		permissionMapCustomer.put("Asset Control", new Permission("Asset Control",true, false, false, false));
		permissionMapCustomer.put("Device Odometer", new Permission("Device Odometer",true, false, false, false));
		permissionMapCustomer.put("Security", new Permission("Security", true, true, false, false));
		permissionMapCustomer.put("Audit", new Permission("Audit",true, false, false, true));
		permissionMapCustomer.put("Users", new Permission("Users",false, true, false, false));
		permissionMapCustomer.put("Roles", new Permission("Roles",true, true, false, false));
		permissionMapCustomer.put("Devices", new Permission("Devices",false, false, true, false));
		permissionMapCustomer.put("System", new Permission("System", false, true, false, false));
		customer.setPermissionMap(permissionMapCustomer);
		
		Roles it = new Roles();
		it.setActive(true);
		it.setDescription("Information Technology Access");
		it.setRoleName("IT");
		it.setId(4);
		Map<String, Permission> permissionMapIT = new LinkedHashMap<>();
		permissionMapIT.put("Dashboard", new Permission("Dashboard", false, true, false, true));
		permissionMapIT.put("Application Store", new Permission("Application Store",false, true, true, true));
		permissionMapIT.put("Personalization", new Permission("Personalization",true, true, true, true));
		permissionMapIT.put("Heartbeat", new Permission("Heartbeat", true, true, false, true));
		permissionMapIT.put("Asset Control", new Permission("Asset Control",true, false, true, false));
		permissionMapIT.put("Device Odometer", new Permission("Device Odometer",true, false, true, false));
		permissionMapIT.put("Security", new Permission("Security", true, true, false, true));
		permissionMapIT.put("Audit", new Permission("Audit",true, false, true, true));
		permissionMapIT.put("Users", new Permission("Users",false, true, true, false));
		permissionMapIT.put("Roles", new Permission("Roles",true, true, true, false));
		permissionMapIT.put("Devices", new Permission("Devices",false, false, true, false));
		permissionMapIT.put("System", new Permission("System", false, true, false, true));
		it.setPermissionMap(permissionMapIT);
		
		rolesRepo.put(hd.getId(), hd);
		rolesRepo.put(admin.getId(), admin);
		rolesRepo.put(customer.getId(), customer);
		rolesRepo.put(it.getId(), it);
	}

	@PostConstruct
	private void init() {
		setSpacing(true);
		setMargin(false);
		setResponsive(true);
		setHeight("100%");
		Panel panel = getAndLoadRolesPanel();	
		VerticalLayout verticalLayout = new VerticalLayout();
		panel.setContent(verticalLayout);
		verticalLayout.setSpacing(false);
		verticalLayout.setMargin(false);
		Label roleInfo = new Label("Role Information", ContentMode.HTML);
		roleInfo.addStyleName("label-style");
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSizeFull();
		HorizontalLayout layout1 = new HorizontalLayout();
		layout1.addComponent(roleInfo);
		layout1.setComponentAlignment(roleInfo, Alignment.MIDDLE_LEFT);
		layout1.setSizeFull();
		
		
		verticalLayout.addComponent(layout);	
		VerticalLayout dynamicVerticalLayout = new VerticalLayout();
		verticalLayout.addComponent(dynamicVerticalLayout);
		dynamicVerticalLayout.setMargin(false);
		dynamicVerticalLayout.setSpacing(true);
		
		getAndLoadPermissionGrid(dynamicVerticalLayout, false);
		
		Button cancel = new Button("Cancel");
		cancel.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		cancel.addStyleName("v-button-customstyle");
		cancel.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {	
				dynamicVerticalLayout.removeAllComponents();
				roleGrid.getDataProvider().refreshAll();
				roleGrid.deselectAll();
				selectedRole = new Roles();
				getAndLoadPermissionGrid(dynamicVerticalLayout, false);	
			}
		});
		
		HorizontalLayout layout2 = new HorizontalLayout();
		layout2.setSizeUndefined();
		layout2.setResponsive(true);
		cancel.setResponsive(true);
		layout2.addComponent(cancel);
		Button save = new Button("Save");
		save.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		save.addStyleName("v-button-customstyle");
		save.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				String description = descriptions.getValue();
				String rolename = roleName.getValue();
				boolean activeValue = activeBox.getValue();
				selectedRole.setDescription(description);
				selectedRole.setRoleName(rolename);
				selectedRole.setActive(activeValue);
				if(description.isEmpty() || description== null|| rolename.isEmpty() || rolename== null) {
					Notification.show("Fill all details", Notification.Type.WARNING_MESSAGE).setDelayMsec(3000);
				} else {
					Random rand = new Random();
					int  n = rand.nextInt(50) + 1;
					if(selectedRole.getId()==null) {
						selectedRole.setId(n);
					}
				rolesRepo.put(selectedRole.getId(), selectedRole);
				roleGrid.getDataProvider().refreshAll();
				roleGrid.select(selectedRole);
				dynamicVerticalLayout.removeAllComponents();
				getAndLoadPermissionGrid(dynamicVerticalLayout, false);
				}
			}
		});
		save.setResponsive(true);
		layout2.addComponent(save);
		layout2.setComponentAlignment(cancel, Alignment.MIDDLE_RIGHT);
		layout2.setComponentAlignment(save, Alignment.MIDDLE_RIGHT);
		layout2.setResponsive(true);
		layout2.setSizeUndefined();
		layout2.setStyleName("save-cancelButtonsAlignment");
		
		//fill data to grid
		layout.addComponents(layout1, layout2);
		layout.setComponentAlignment(layout2, Alignment.MIDDLE_RIGHT);
		getAndLoadRolesGrid(verticalLayout, dynamicVerticalLayout);
	}

	/**
	 * Get the load
	 * @return
	 */
	
	public Panel getAndLoadRolesPanel() {
		Panel panel = new Panel();
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setCaption("Roles");
		panel.setResponsive(true);
		panel.setSizeFull();
        addComponent(panel);
       return panel;
	}
	
	
	public void getAndLoadPermissionGrid(VerticalLayout verticalLayout, boolean isEditableOnly) {
				
		FormLayout formLayout = new FormLayout();
		
		String role = selectedRole.getRoleName() != null ? selectedRole.getRoleName(): "";
		roleName = new TextField("Role Name", role);
		roleName.setValue(role);
		roleName.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		roleName.setWidth("109%");
		roleName.setStyleName("role-textbox");
		roleName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		roleName.addStyleName("v-textfield-font");
		selectedRole.setRoleName(roleName.getValue());
		roleName.setEnabled(isEditableOnly);
		activeBox = new CheckBox("Allow Access");
		activeBox.addStyleName("v-textfield-font");
		activeBox.setEnabled(isEditableOnly);
		label = new Label("Active");
		label.setStyleName("role-activeLable");
		label.addStyleName("v-textfield-font");

		permissionGrid = new Grid<Permission>();
		permissionGrid.setEnabled(isEditableOnly);
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		FormLayout roleNameFL = new FormLayout();
		HorizontalLayout activeCheck = new HorizontalLayout();
		activeCheck.addStyleName("role-activePadding");
		horizontalLayout.setWidth("100%");
		horizontalLayout.setSpacing(false);
		horizontalLayout.setMargin(false);
		roleNameFL.addComponent(roleName);
		roleNameFL.setComponentAlignment(roleName, Alignment.MIDDLE_LEFT);
		roleNameFL.setSpacing(false);
		roleNameFL.setMargin(false);
		roleNameFL.setStyleName("role-name-layout");
		activeCheck.setSpacing(false);
		activeCheck.setMargin(false);
		activeCheck.addComponent(label);
		activeCheck.addComponent(activeBox);
		activeCheck.setSizeUndefined();
		horizontalLayout.addComponent(roleNameFL);
		horizontalLayout.addComponent(activeCheck);
		horizontalLayout.setComponentAlignment(roleNameFL, Alignment.MIDDLE_LEFT);
		horizontalLayout.setComponentAlignment(activeCheck, Alignment.MIDDLE_LEFT);
		formLayout.addComponent(horizontalLayout);
		String description = selectedRole.getDescription() != null ? selectedRole.getDescription(): "";
		descriptions = new TextField("Description", description);
		selectedRole.setDescription(descriptions.getValue());
		descriptions.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		descriptions.setWidth("48%");
		descriptions.setStyleName("role-textbox");
		descriptions.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		descriptions.addStyleName("v-textfield-font");
		descriptions.setEnabled(isEditableOnly);
		formLayout.addComponent(descriptions);
		formLayout.setStyleName("role-description-layout");
		formLayout.setSpacing(false);
		formLayout.setMargin(false);
		verticalLayout.addComponent(formLayout);
		List<Permission> beanList = new ArrayList<>(selectedRole.getPermissionMap().values());
		permissionGrid.setHeightByRows(11);
		permissionGrid.addStyleName(ValoTheme.TABLE_BORDERLESS);
		permissionGrid.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
		permissionGrid.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
		permissionGrid.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		permissionGrid.setHeaderVisible(false);
		permissionGrid.setWidth("50%");
		permissionGrid.setItems(beanList);
		
		CheckBox access = new CheckBox();
		access.addStyleName("v-textfield-font");
		access.setEnabled(isEditableOnly);
		
		CheckBox edit = new CheckBox();
		edit.addStyleName("v-textfield-font");
		edit.setEnabled(isEditableOnly);
		
		CheckBox delete = new CheckBox();
		delete.addStyleName("v-textfield-font");
		delete.setEnabled(isEditableOnly);

		CheckBox add = new CheckBox();
		add.addStyleName("v-textfield-font");
		add.setEnabled(isEditableOnly);
		
		permissionGrid.addColumn(Permission::getPageName).setCaption("");

		permissionGrid.addColumn(Permission::getAccess, new CheckboxRenderer<>(Permission::setAccess))
				.setCaption("Access")
				.setEditorComponent(access, Permission::setAccess).setEditable(isEditableOnly);
		permissionGrid.addColumn(Permission::getEdit, new CheckboxRenderer<>(Permission::setEdit))
		.setCaption("Edit")
		.setEditorComponent(edit, Permission::setEdit).setEditable(isEditableOnly);
		
		permissionGrid.addColumn(Permission::getDelete, new CheckboxRenderer<>(Permission::setDelete))
		.setCaption("Delete")
		.setEditorComponent(delete, Permission::setDelete).setEditable(isEditableOnly);
		
		permissionGrid.addColumn(Permission::getAdd, new CheckboxRenderer<>(Permission::setAdd))
		.setCaption("Add")
		.setEditorComponent(add, Permission::setAdd).setEditable(isEditableOnly);
		
		permissionGrid.getEditor().setEnabled(isEditableOnly);
		permissionGrid.getEditor().setBuffered(false);
		permissionGrid.setSizeFull();
		verticalLayout.addComponent(permissionGrid);
		verticalLayout.setComponentAlignment(permissionGrid
				, Alignment.MIDDLE_CENTER);
	}
	
	public void getAndLoadRolesGrid(VerticalLayout verticalLayout, VerticalLayout dynamicVerticalLayout) {
		Button addNewRole = new Button(VaadinIcons.FOLDER_ADD);
		addNewRole.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		addNewRole.addStyleName("v-button-customstyle");
		addNewRole.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				dynamicVerticalLayout.removeAllComponents();
				selectedRole = new Roles();
				getAndLoadPermissionGrid(dynamicVerticalLayout, true);	
				roleName.focus();
			}
		});
		Button editRole = new Button(VaadinIcons.EDIT);
		editRole.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		editRole.addStyleName("v-button-customstyle");
		editRole.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				if(descriptions.getValue()==null || descriptions.getValue().isEmpty() || 
						roleName.getValue()==null ||  roleName.getValue().isEmpty()) {
					Notification.show("Select any Role to Modify", Notification.Type.WARNING_MESSAGE).setDelayMsec(3000);;
				}else {
				dynamicVerticalLayout.removeAllComponents();
				getAndLoadPermissionGrid(dynamicVerticalLayout, true);		
				roleName.focus();
				}
			}
		});
		Button deleteRole = new Button(VaadinIcons.TRASH);
		deleteRole.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		deleteRole.addStyleName("v-button-customstyle");
		deleteRole.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(descriptions.getValue()==null || descriptions.getValue().isEmpty() || 
						roleName.getValue()==null ||  roleName.getValue().isEmpty()) {
					Notification.show("Select role to delete", Notification.Type.ERROR_MESSAGE);
				}else {
				
				confirmDialog( dynamicVerticalLayout);
				}
			}
		});
		HorizontalLayout buttonGroup =  new HorizontalLayout();
		buttonGroup.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		buttonGroup.addComponent(addNewRole);
		buttonGroup.addComponent(editRole);
		buttonGroup.addComponent(deleteRole);
		
		roleGrid.setCaptionAsHtml(true);
		roleGrid.addColumn(Roles::getRoleName).setCaption("Role Name");
		roleGrid.addColumn(Roles::getDescription).setCaption("Description");
		roleGrid.addColumn(Roles::getActive).setCaption("Active");
		
		roleGrid.setHeightByRows(4);
		roleGrid.setItems(rolesRepo.values());
		roleGrid.setWidth("100%");
		roleGrid.setHeightByRows(6);;
		roleGrid.setResponsive(true);
		
		roleGrid.addSelectionListener(e -> {
			if (!e.isUserOriginated()) {
				return;
			}

			if (e.getFirstSelectedItem().isPresent()) {
				//Load and update the data in permission grid.
				dynamicVerticalLayout.removeAllComponents();
				Roles selectedRole = e.getFirstSelectedItem().get();
				this.selectedRole = selectedRole;
				getAndLoadPermissionGrid(dynamicVerticalLayout, false);
			} else {
				throw new IllegalStateException("This should never happen as deselection is not allowed");
			}
		});
		VerticalLayout roleGridLayout = new VerticalLayout();
		roleGridLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		roleGridLayout.addComponent(buttonGroup);
		roleGridLayout.addComponent(roleGrid);
		verticalLayout.addComponent(roleGridLayout);
	}
	
	
	
	public void confirmDialog(VerticalLayout dynamicVerticalLayout) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?",
		        "Ok", "Cancel", new ConfirmDialog.Listener() {

		            public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		                    // Confirmed to continue
		                	dynamicVerticalLayout.removeAllComponents();
		    				rolesRepo.remove(selectedRole.getRoleName());
		    				roleGrid.getDataProvider().refreshAll();
		    				selectedRole = new Roles();
		    				getAndLoadPermissionGrid(dynamicVerticalLayout, false);
		                } else {
		                    // User did not confirm
		                    
		                }
		            }
		        });
	}
	

	 @Override
	 public void enter(ViewChangeEvent event) {
	        // This view is constructed in the init() method()
	 }
}
