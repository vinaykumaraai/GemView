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

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.client.restlib.service.model.AlertAction;
import com.luretechnologies.tms.backend.data.entity.Alert;
import com.luretechnologies.tms.backend.data.entity.AlertType;
import com.luretechnologies.tms.backend.data.entity.ExtendedNode;
import com.luretechnologies.tms.backend.data.entity.Node;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.service.AlertService;
import com.luretechnologies.tms.backend.service.AssetControlService;
import com.luretechnologies.tms.backend.service.TreeDataService;
import com.luretechnologies.tms.ui.ComponentUtil;
import com.luretechnologies.tms.ui.NotificationUtil;
import com.luretechnologies.tms.ui.components.FormFieldType;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class AlertTab {
	Button createAlertGridRow, editAlertGridRow, deleteAlertGridRow, saveAlertForm, cancelAlertForm;
	Grid<Alert> alertGrid;
	AlertService alertService;
	Tree<TreeNode> nodeTree;
	UI assetControlUI;
	AssetControlService assetControlService;
	//public AssetControlView assetView ;
	public AlertTab(Grid<Alert> alertGrid, AlertService alertService,Tree<TreeNode> nodeTree,UI assetControlUI, AssetControlService assetControlService, Button... buttons) {
		createAlertGridRow = buttons[0];
		editAlertGridRow = buttons[1];
		deleteAlertGridRow = buttons[2];
		saveAlertForm = buttons[3];
		cancelAlertForm = buttons[4];
		this.alertGrid = alertGrid;
		this.alertService = alertService;
		this.nodeTree = nodeTree;
		this.assetControlUI = assetControlUI;
		this.assetControlService = assetControlService;
		
	}
	
//	@Autowired
//	public TreeDataService treeDataService;

	@Autowired
	public AssetcontrolView assetView ;
	
	public VerticalLayout getAlert() {
		
		VerticalLayout alertLayout = new VerticalLayout();
		alertLayout.setWidth("100%");
		//alertLayout.addStyleName(ValoTheme.LAYOUT_CARD);
		alertLayout.addStyleName("audit-DeviceVerticalAlignment");
		VerticalLayout alertVerticalButtonLayout = new VerticalLayout();
		HorizontalLayout alertCommandLabel = new HorizontalLayout();
		HorizontalLayout saveCancelLayout = new HorizontalLayout();
		HorizontalLayout alertSaveCancleAndLabelLayout = new HorizontalLayout();
		
		alertCommandLabel.setWidth("100%");
		alertSaveCancleAndLabelLayout.setWidth("100%");
		
		
		alertVerticalButtonLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		alertVerticalButtonLayout.addStyleName("heartbeat-verticalLayout");
		VerticalLayout formLayout = new VerticalLayout();
		formLayout.addStyleNames("heartbeat-verticalLayout", "asset-alertformLayout");
		alertLayout.addStyleName("heartbeat-verticalLayout");
		Label alertCommands = new Label("Alert Commands");
		alertCommands.addStyleName("label-style");
		alertCommands.addStyleNames(ValoTheme.LABEL_BOLD, ValoTheme.LABEL_H3);
		alertCommandLabel.addComponent(alertCommands);
		
		saveAlertForm = new Button("Save");
		cancelAlertForm = new Button("Cancel");
		saveCancelLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		saveCancelLayout.addComponents( cancelAlertForm,saveAlertForm);
		saveCancelLayout.setResponsive(true);
		saveCancelLayout.setStyleName("save-cancelButtonsAlignment");
		
		alertSaveCancleAndLabelLayout.addComponents(alertCommandLabel, saveCancelLayout);
		alertSaveCancleAndLabelLayout.setComponentAlignment(saveCancelLayout, Alignment.MIDDLE_RIGHT);
	
		
		alertLayout.addComponent(alertSaveCancleAndLabelLayout);
		
//		HorizontalLayout activeBoxLayout = new HorizontalLayout();
//		//activeBoxLayout.addStyleName("heartbeat-activeLayout");
//		Label active = new Label("Active");
//		active.setStyleName("role-activeLable");
//		active.addStyleNames("v-textfield-font");
//		active.addStyleName("heartbeat-checkbox");
//		CheckBox activeCheckBox = new CheckBox();
//		activeBoxLayout.addComponents(active, activeCheckBox);
		Component[] alertFormComponentArray = {
				ComponentUtil.getFormFieldWithLabel("Alert Type", FormFieldType.TEXTBOX),
				ComponentUtil.getFormFieldWithLabel("Name", FormFieldType.TEXTBOX),
				ComponentUtil.getFormFieldWithLabel("Description", FormFieldType.TEXTBOX),
				ComponentUtil.getFormFieldWithLabel("", FormFieldType.HORIZONTALLAYOUT),
				ComponentUtil.getFormFieldWithLabel("Email to:", FormFieldType.TEXTBOX) };

		FormLayout alertFormLayout = new FormLayout(alertFormComponentArray);
		alertFormLayout.addStyleName("system-LabelAlignment");
		formLayout.addComponent(alertFormLayout);
		alertLayout.addComponent(formLayout);
		// Add,Delete,Edit Button Layout
		alertVerticalButtonLayout.addComponent(getAlertGridButtonLayout(alertFormComponentArray));
		alertLayout.addComponent(alertVerticalButtonLayout);
		alertLayout.setComponentAlignment(alertLayout.getComponent(1), Alignment.TOP_RIGHT);
		// Alert Grid
		alertLayout.addComponent(getAlertGrid());
		alertGrid.addItemClickListener(item -> {

			if (item.getItem() != null) {
				((TextField) alertFormComponentArray[0]).setValue(item.getItem().getType());
				((TextField) alertFormComponentArray[1]).setValue(item.getItem().getName());
				((TextField) alertFormComponentArray[2]).setValue(item.getItem().getDescription());
				HorizontalLayout HL= (HorizontalLayout)alertFormComponentArray[3];
				CheckBox checkbox = (CheckBox) HL.getComponent(1);
				checkbox.setValue(item.getItem().isActive());
				((TextField) alertFormComponentArray[4]).setValue(item.getItem().getEmail());
			}
		});
		alertGrid.addSelectionListener(selection -> {
			if (selection.getFirstSelectedItem().isPresent()) {
				editAlertGridRow.setEnabled(true);
				deleteAlertGridRow.setEnabled(true);
				cancelAlertForm.setEnabled(true);
				saveAlertForm.setEnabled(true);
			} else {
				for (Component component : alertFormComponentArray) {
					if (component.isEnabled())
						component.setEnabled(false);

					if (component instanceof TextField) {
						TextField textField = (TextField) component;
						textField.clear();
					} else if (component instanceof HorizontalLayout) {
						HorizontalLayout HL = (HorizontalLayout) component;
						CheckBox checkbox = (CheckBox) HL.getComponent(1);
						checkbox.clear();
					}
				}
				createAlertGridRow.setEnabled(true);
				editAlertGridRow.setEnabled(true);
				deleteAlertGridRow.setEnabled(true);
				cancelAlertForm.setEnabled(true);
				saveAlertForm.setEnabled(true);
			}
		});
		//alertLayout.setExpandRatio(alertGrid, 1);
		return alertLayout;

	}

	private void loadAlertGrid(String id) {
		DataProvider data = new ListDataProvider(assetControlService.getAlertGridData(id));
		alertGrid.setDataProvider(data);
	}
	
	private Grid<Alert> getAlertGrid() {
		//alertGrid = new Grid<>(Alert.class);
		alertGrid.setWidth("100%");
		alertGrid.setHeight("100%");
		alertGrid.addStyleName("grid-AuditOdometerAlignment");
		alertGrid.setResponsive(true);
		alertGrid.setSelectionMode(SelectionMode.SINGLE);
		alertGrid.setColumns("type", "description", "active", "email");
		alertGrid.getColumn("type").setCaption("Alert Type");

		return alertGrid;
	}

	private HorizontalLayout getAlertGridButtonLayout(Component[] componentArray) {
		HorizontalLayout alertGridButtonLayout = new HorizontalLayout();
		alertGridButtonLayout.addStyleName("asset-alertDeleteLayout");
		alertGridButtonLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		createAlertGridRow = new Button(VaadinIcons.FOLDER_ADD, click -> {
			TextField type = (TextField) componentArray[0];
			for (Component component : componentArray) {
				if (!component.isEnabled())
					component.setEnabled(true);
					type.focus();
				if (component instanceof TextField) {
					TextField textField = (TextField) component;
					textField.clear();
					textField.setEnabled(true);
					type.focus();
				} else if (component instanceof HorizontalLayout) {
					HorizontalLayout HL = (HorizontalLayout) component;
					CheckBox checkbox = (CheckBox) HL.getComponent(1);
					checkbox.clear();
					type.focus();
				}
			}
			saveAlertForm.setEnabled(true);
			cancelAlertForm.setEnabled(true);
			alertGrid.deselectAll();
		});
		createAlertGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
		createAlertGridRow.addStyleName("v-button-customstyle");
		editAlertGridRow = new Button(VaadinIcons.EDIT, click -> {
			if(alertGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.ASSET_ALERT_EDIT, Notification.Type.ERROR_MESSAGE);
			}else {
			if (alertGrid.getSelectedItems().size() > 0) {
				TextField type = (TextField) componentArray[0];
				for (Component component : componentArray) {
					if (!component.isEnabled())
						component.setEnabled(true);
						type.focus();
				}

				deleteAlertGridRow.setEnabled(true);
				editAlertGridRow.setEnabled(true);
				createAlertGridRow.setEnabled(true);
			}
			}

		});
		editAlertGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
		editAlertGridRow.addStyleName("v-button-customstyle");
		editAlertGridRow.setEnabled(true);
		deleteAlertGridRow = new Button(VaadinIcons.TRASH, click -> {
			if(alertGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.ASSET_ALERT_DELETE, Notification.Type.ERROR_MESSAGE);
			}else {
			ConfirmDialog.show(assetControlUI, "Please Confirm:", "Are you sure you want to delete?",
			        "Ok", "Cancel", dialog -> 
			            {
			                if (dialog.isConfirmed()) {
			    				Long id = alertGrid.getSelectedItems().iterator().next().getId();
			    				String entityId = nodeTree.getSelectedItems().iterator().next().getEntityId();
			    				assetControlService.deleteAlertCommands(id);
			    				loadAlertGrid(entityId);
			                } else {
			                    // User did not confirm
			                    
			                }
			            });
		}

		});
		deleteAlertGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		deleteAlertGridRow.addStyleName("v-button-customstyle");
		deleteAlertGridRow.setEnabled(true);
		saveAlertForm.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {	
				HorizontalLayout HL= (HorizontalLayout)componentArray[3];
				if (nodeTree.getSelectedItems().size() <= 0) {
					Notification.show(NotificationUtil.ASSET_ALERT_SAVETONODE, Notification.Type.ERROR_MESSAGE);
				} else if(((TextField) componentArray[0]).getValue()==null ||
						((TextField) componentArray[0]).getValue().isEmpty() || 
						((TextField) componentArray[1]).getValue()==null ||
						((TextField) componentArray[1]).getValue().isEmpty() ||
						((TextField) componentArray[2]).getValue()==null ||
						((TextField) componentArray[2]).getValue().isEmpty() ||
						((CheckBox) HL.getComponent(1)).getValue() == null ||
						((TextField) componentArray[4]).getValue()==null ||
						((TextField) componentArray[4]).getValue().isEmpty()) {
					Notification.show(NotificationUtil.SAVE, Notification.Type.ERROR_MESSAGE);
				}else {
			for (Component component : componentArray) {
				if (component.isEnabled())
					component.setEnabled(false);

			}
			saveAlertForm.addStyleName("v-button-customstyle");
			AlertAction alert = new AlertAction();
			alert.setLabel(((TextField) componentArray[0]).getValue());
			alert.setName(((TextField) componentArray[1]).getValue());
			alert.setDescription(((TextField) componentArray[2]).getValue());
			CheckBox checkbox = (CheckBox) HL.getComponent(1);
			alert.setAvailable(checkbox.getValue());
			alert.setEmail(((TextField) componentArray[4]).getValue());
			cancelAlertForm.setEnabled(true);
			editAlertGridRow.setEnabled(true);
			deleteAlertGridRow.setEnabled(true);
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
			alertGrid.select(gridAlert);
	
			/*alertGrid.getDataProvider().refreshAll();
			alertGrid.deselectAll();*/
			}
			}
		});
		saveAlertForm.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		saveAlertForm.addStyleName("v-button-customstyle");
		saveAlertForm.setEnabled(true);
		cancelAlertForm.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {	
			for (Component component : componentArray) {
				component.setEnabled(false);
				if (component instanceof TextField) {
					TextField textField = (TextField) component;
					textField.clear();
				} else if (component instanceof HorizontalLayout) {
					HorizontalLayout HL = (HorizontalLayout) component;
					CheckBox checkbox = (CheckBox) HL.getComponent(1);
					checkbox.clear();
				}

			}
			if (alertGrid.getSelectedItems().size() > 0) {
				alertGrid.deselectAll();
			}
			cancelAlertForm.setEnabled(true);
			editAlertGridRow.setEnabled(true);
			deleteAlertGridRow.setEnabled(true);
			saveAlertForm.setEnabled(true);
			}
		});
		cancelAlertForm.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		cancelAlertForm.addStyleName("v-button-customstyle");
		cancelAlertForm.setEnabled(true);
		alertGridButtonLayout.addComponent(createAlertGridRow);
		alertGridButtonLayout.addComponent(editAlertGridRow);
		alertGridButtonLayout.addComponent(deleteAlertGridRow);
		

		return alertGridButtonLayout;
	}
}
