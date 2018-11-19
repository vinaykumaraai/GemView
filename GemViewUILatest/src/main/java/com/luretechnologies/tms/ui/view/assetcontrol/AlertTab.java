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
package com.luretechnologies.tms.ui.view.assetcontrol;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.extension.gridscroll.GridScrollExtension;

import com.luretechnologies.client.restlib.service.model.AlertAction;
import com.luretechnologies.tms.backend.data.entity.Alert;
import com.luretechnologies.tms.backend.data.entity.Permission;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.service.AssetControlService;
import com.luretechnologies.tms.ui.components.ComponentUtil;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.luretechnologies.tms.ui.view.ContextMenuWindow;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TreeGrid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class AlertTab {
	Button saveAlertForm, cancelAlertForm;
	Grid<Alert> alertGrid;
	TreeGrid<TreeNode> nodeTree;
	UI assetControlUI;
	AssetControlService assetControlService;
	Permission assetControlPermission;
	TextField alertType, name, description, email;
	CheckBox activeCheckBox;
	HorizontalLayout horizontalLayout;
	ContextMenuWindow alertFormWindow ;
	GridScrollExtension extension;
	public AlertTab(Grid<Alert> alertGrid, TreeGrid<TreeNode> nodeTree,UI assetControlUI, AssetControlService assetControlService,Permission assetControlpermission, Button... buttons) {
		saveAlertForm = buttons[0];
		cancelAlertForm = buttons[1];
		this.alertGrid = alertGrid;
		this.nodeTree = nodeTree;
		this.assetControlUI = assetControlUI;
		this.assetControlService = assetControlService;
		this.assetControlPermission = assetControlpermission;
	}
	
	public VerticalLayout getAlert() {
		
		Page.getCurrent().addBrowserWindowResizeListener(r->{
			
			if(r.getWidth()<=600) {
				alertType.setHeight(28, Unit.PIXELS);
				name.setHeight("28px");
				description.setHeight("28px");
				email.setHeight("28px");
				saveAlertForm.setHeight("28px");
				cancelAlertForm.setHeight("28px");
				if(alertFormWindow!=null) {
					alertFormWindow.setPosition(180, 200);
					alertFormWindow.setWidth("220px");
				}
				
			} else if(r.getWidth()>600 && r.getWidth()<=1000){
				alertType.setHeight(32, Unit.PIXELS);
				name.setHeight("32px");
				description.setHeight("32px");
				email.setHeight("32px");
				saveAlertForm.setHeight("32px");
				cancelAlertForm.setHeight("32px");
				if(alertFormWindow!=null) {
					alertFormWindow.center();
					alertFormWindow.setSizeUndefined();
				}
			}else {
				alertType.setHeight(37, Unit.PIXELS);
				name.setHeight("37px");
				description.setHeight("37px");
				email.setHeight("37px");
				saveAlertForm.setHeight("37px");
				cancelAlertForm.setHeight("37px");
				if(alertFormWindow!=null) {
					alertFormWindow.center();
					alertFormWindow.setWidth("30%");
					alertFormWindow.setHeight("54%");
				}
			}
		});
				
		ContextMenuWindow assestControlAlertGridMenu = new ContextMenuWindow();
		VerticalLayout alertLayout = new VerticalLayout();
		alertLayout.setWidth("100%");
		alertLayout.setHeight("100%");
		alertLayout.addStyleName("audit-DeviceVerticalAlignment");
		VerticalLayout alertVerticalButtonLayout = new VerticalLayout();
		HorizontalLayout saveCancelLayout = new HorizontalLayout();
		
		
		alertVerticalButtonLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		alertVerticalButtonLayout.addStyleName("heartbeat-verticalLayout");
		VerticalLayout formLayout = new VerticalLayout();
		formLayout.addStyleNames("system-LabelAlignment", "heartbeat-verticalLayout", "header-Components");
		alertLayout.addStyleName("heartbeat-verticalLayout");
		
		saveAlertForm = new Button("Save");
		saveAlertForm.setDescription("Save");
		cancelAlertForm = new Button("Cancel");
		cancelAlertForm.setDescription("Cancel");
		saveCancelLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		saveCancelLayout.addComponents( cancelAlertForm,saveAlertForm);
		saveCancelLayout.setResponsive(true);
		saveCancelLayout.setStyleName("save-cancelButtonsAlignment");
		saveCancelLayout.setComponentAlignment(cancelAlertForm, Alignment.BOTTOM_RIGHT);
		saveCancelLayout.setComponentAlignment(saveAlertForm, Alignment.BOTTOM_RIGHT);
		
		alertType = new TextField("Alert Type");
		alertType.setWidth("100%");
		alertType.addStyleNames("v-textfield-font","textfiled-height");
		alertType.setMaxLength(50);
		alertType.setRequiredIndicatorVisible(true);;
		
		name = new TextField("Name");
		name.setWidth("100%");
		name.addStyleNames("v-textfield-font","textfiled-height");
		name.setMaxLength(50);
		name.setRequiredIndicatorVisible(true);;
		
		description = new TextField("Description");
		description.setWidth("100%");
		description.addStyleNames("v-textfield-font","textfiled-height");
		description.setMaxLength(50);
		description.setRequiredIndicatorVisible(true);;
		
		horizontalLayout = new HorizontalLayout();
		Label active = new Label("Active");
		active.setStyleName("role-activeLable");
		active.addStyleNames("v-textfield-font");
		active.addStyleName("asset-alertCheckbox");
		activeCheckBox = new CheckBox();
		activeCheckBox.addStyleName("v-textfield-font");
		horizontalLayout.addComponents(active, activeCheckBox);
		horizontalLayout.addStyleName("asset-alertActiveLayout");
		activeCheckBox.setRequiredIndicatorVisible(true);;
		
		email = new TextField("Email");
		email.setWidth("100%");
		email.addStyleNames("v-textfield-font","textfiled-height");
		email.setMaxLength(50);
		email.setRequiredIndicatorVisible(true);
		

		VerticalLayout alertFormLayout = new VerticalLayout(alertType, name, description, horizontalLayout, email);
		alertFormLayout.addStyleName("system-LabelAlignment");
		formLayout.addComponents(alertFormLayout, saveCancelLayout);
		
		// Alert Grid
		alertLayout.addComponent(getAlertGrid());
		
		alertGrid.addSelectionListener(selection -> {
			assestControlAlertGridMenu.close();
			if (selection.getAllSelectedItems().size()==1) {
			Alert selectedAlert = alertGrid.getSelectedItems().iterator().next();
				cancelAlertForm.setEnabled(true);
				saveAlertForm.setEnabled(true);
				
				alertType.setValue(selectedAlert.getType());
				name.setValue(selectedAlert.getName());
				description.setValue(selectedAlert.getDescription());
				activeCheckBox.setValue(selectedAlert.isActive());
				email.setValue(selectedAlert.getEmail());
			} else {
				clearAllComponents();
				cancelAlertForm.setEnabled(true);
				saveAlertForm.setEnabled(true);
			}
		});
		
		
		Button createAlertGridRowMenu = new Button("Add Alert", click -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			if (nodeTree.getSelectedItems().size() == 0) {
				Notification.show(NotificationUtil.ASSET_ALERT_SAVETONODE, Notification.Type.ERROR_MESSAGE);
			} else {
				clearAllComponents();
				UI.getCurrent().getWindows().forEach(Window::close);
				UI.getCurrent().addWindow(window(formLayout));
				
			}
		});
		createAlertGridRowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		
		Button editAlertGridRowMenu = new Button("Edit Alert", click->{
			UI.getCurrent().getWindows().forEach(Window::close);
			if (alertGrid.getSelectedItems().size() == 0) {
				Notification.show(NotificationUtil.ASSET_ALERT_EDIT, Notification.Type.ERROR_MESSAGE);
			} else {
				UI.getCurrent().getWindows().forEach(Window::close);
				UI.getCurrent().addWindow(window(formLayout));
			}
		});
		editAlertGridRowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		
		Button deleteAlertGridRowMenu = new Button("Delete Alert", click-> {
			UI.getCurrent().getWindows().forEach(Window::close);
			if (alertGrid.getSelectedItems().size() == 0) {
				Notification.show(NotificationUtil.ASSET_ALERT_DELETE, Notification.Type.ERROR_MESSAGE);
			} else {
				ConfirmDialog.show(assetControlUI, "Please Confirm:", "Are you sure you want to delete?",
				        "Ok", "Cancel", dialog -> 
				            {
				                if (dialog.isConfirmed()) {
				    				Set<Alert> alertSet = alertGrid.getSelectedItems();
				    				String entityId = nodeTree.getSelectedItems().iterator().next().getEntityId();
				    				for(Alert alert : alertSet) {
				    					assetControlService.deleteAlertCommands(alert.getId());
				    				}
				    				loadAlertGrid(entityId);
				                } else {
				                    // User did not confirm
				                    
				                }
				            });
			}
		});
		deleteAlertGridRowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		
		assestControlAlertGridMenu.addMenuItems(createAlertGridRowMenu,editAlertGridRowMenu,deleteAlertGridRowMenu);
		alertGrid.addContextClickListener(click->{
			deleteAlertGridRowMenu.setEnabled(assetControlPermission.getDelete());
			editAlertGridRowMenu.setEnabled(assetControlPermission.getEdit());
			createAlertGridRowMenu.setEnabled(assetControlPermission.getAdd());
			
			if(alertGrid.getSelectedItems().size()>0 && deleteAlertGridRowMenu.isEnabled()) {
				deleteAlertGridRowMenu.setEnabled(true);
			}else {
				deleteAlertGridRowMenu.setEnabled(false);
			}
			
			if(alertGrid.getSelectedItems().size()==1 && editAlertGridRowMenu.isEnabled()) {
				editAlertGridRowMenu.setEnabled(true);
			}else {
				editAlertGridRowMenu.setEnabled(false);
			}
			
			UI.getCurrent().getWindows().forEach(Window::close);
			assestControlAlertGridMenu.setPosition(click.getClientX(), click.getClientY());
			UI.getCurrent().addWindow(assestControlAlertGridMenu);
		});
		
		UI.getCurrent().addClickListener(listener->{
			if(assestControlAlertGridMenu!=null) {
				assestControlAlertGridMenu.close();
			}
			if(alertFormWindow!=null) {
				alertFormWindow.close();
			}
		});
		
		cancelAlertForm.setEnabled(assetControlPermission.getAdd() || assetControlPermission.getEdit());
		saveAlertForm.setEnabled(assetControlPermission.getAdd() || assetControlPermission.getEdit());
		
		saveAlertForm.addClickListener(listener-> {
			alertFormWindow.setModal(true);
			if (nodeTree.getSelectedItems().size() <= 0) {
				Notification.show(NotificationUtil.ASSET_ALERT_SAVETONODE, Notification.Type.ERROR_MESSAGE);
			} else if(!(ComponentUtil.validatorTextField(alertType) &&
					ComponentUtil.validatorTextField(name) &&
					ComponentUtil.validatorTextField(description)  &&
					ComponentUtil.validatorCheckBox(activeCheckBox) &&
					ComponentUtil.validatorEmailId(email))) {
			}else {
				
		saveAlertForm.addStyleName("v-button-customstyle");
		AlertAction alert = new AlertAction();
		alert.setLabel(alertType.getValue());
		alert.setName(name.getValue());
		alert.setDescription(description.getValue());
		alert.setAvailable(activeCheckBox.getValue());
		alert.setEmail(email.getValue());
		cancelAlertForm.setEnabled(true);
		saveAlertForm.setEnabled(true);
		
		Alert gridAlert = new Alert();
		if (alertGrid.getSelectedItems().size() > 0) {
			if(alertGrid.getSelectedItems().iterator().next().getId()!=null && 
					!alertGrid.getSelectedItems().iterator().next().getId().toString().isEmpty()) {
				alert.setId(alertGrid.getSelectedItems().iterator().next().getId());
			}
			gridAlert = assetControlService.updateAlertCommands(alert);
		} else {
			gridAlert = assetControlService.createAlertCommands(nodeTree.getSelectedItems().iterator().next().getEntityId(), alert);
		}
		
		loadAlertGrid(nodeTree.getSelectedItems().iterator().next().getEntityId());
		alertFormWindow.close();
		}
	});
	saveAlertForm.addStyleName(ValoTheme.BUTTON_FRIENDLY);
	saveAlertForm.addStyleName("v-button-customstyle");
	saveAlertForm.setEnabled(true);
	cancelAlertForm.addClickListener(new ClickListener() {
		public void buttonClick(ClickEvent event) {	
		clearAllComponents();
		if (alertGrid.getSelectedItems().size() > 0) {
			alertGrid.deselectAll();
		}
		cancelAlertForm.setEnabled(true);
		saveAlertForm.setEnabled(true);
		}
	});
	cancelAlertForm.addStyleName(ValoTheme.BUTTON_FRIENDLY);
	cancelAlertForm.addStyleName("v-button-customstyle");
	cancelAlertForm.setEnabled(true);
	
	int width = Page.getCurrent().getBrowserWindowWidth();
	if(width<=600) {
		alertType.setHeight(28, Unit.PIXELS);
		name.setHeight("28px");
		description.setHeight("28px");
		email.setHeight("28px");
		saveAlertForm.setHeight("28px");
		cancelAlertForm.setHeight("28px");
		if(alertFormWindow!=null) {
			alertFormWindow.setPosition(180, 200);
			alertFormWindow.setWidth("220px");
		}
	} else if(width>600 && width<=1000){
		alertType.setHeight(32, Unit.PIXELS);
		name.setHeight("32px");
		description.setHeight("32px");
		email.setHeight("32px");
		saveAlertForm.setHeight("32px");
		cancelAlertForm.setHeight("32px");
		alertFormWindow.center();
		if(alertFormWindow!=null) {
			alertFormWindow.center();
			alertFormWindow.setSizeUndefined();
		}
	}else {
		alertType.setHeight(37, Unit.PIXELS);
		name.setHeight("37px");
		description.setHeight("37px");
		email.setHeight("37px");
		saveAlertForm.setHeight("37px");
		cancelAlertForm.setHeight("37px");
		if(alertFormWindow!=null) {
			alertFormWindow.center();
			alertFormWindow.setWidth("30%");
			alertFormWindow.setHeight("54%");
		}
	}
	
	return alertLayout;
		

	}

	private void loadAlertGrid(String id) {
		DataProvider data = new ListDataProvider(assetControlService.getAlertGridData(id));
		alertGrid.setDataProvider(data);
	}
	
	private void clearAllComponents() {
		alertType.clear();
		name.clear();
		description.clear();
		email.clear();
		activeCheckBox.clear();
	}
	
	private ContextMenuWindow window(VerticalLayout formLayout) {
		alertFormWindow = new ContextMenuWindow();
		alertFormWindow.addMenuItems(formLayout);
		alertFormWindow.center();
		alertFormWindow.setWidth("30%");
		alertFormWindow.setHeight("54%");
		alertFormWindow.setResizable(true);
		alertFormWindow.setClosable(true);
		alertFormWindow.setDraggable(true);
		
		alertFormWindow.addCloseListener(listener->{
			alertGrid.deselectAll();
		});
		
		return alertFormWindow;
	}
	
	private Grid<Alert> getAlertGrid() {
		alertGrid.setWidth("100%");
		alertGrid.setHeight("100%");
		alertGrid.addStyleName("grid-AuditOdometerAlignment");
		alertGrid.setResponsive(true);
		alertGrid.setSelectionMode(SelectionMode.MULTI);
		alertGrid.setColumns("type", "description", "active", "email");
		alertGrid.getColumn("type").setCaption("Alert Type");
		
		extension = new GridScrollExtension(alertGrid);
		extension.addGridScrolledListener(event -> {
		    UI.getCurrent().getWindows().forEach(Window::close);
		});
		
		return alertGrid;
	}
}
