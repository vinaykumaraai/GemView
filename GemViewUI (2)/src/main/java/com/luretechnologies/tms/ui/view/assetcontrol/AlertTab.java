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

import com.luretechnologies.tms.backend.data.entity.Alert;
import com.luretechnologies.tms.backend.data.entity.ExtendedNode;
import com.luretechnologies.tms.backend.service.AlertService;
import com.luretechnologies.tms.backend.service.TreeDataService;
import com.luretechnologies.tms.ui.ComponentUtil;
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
	Tree<ExtendedNode> nodeTree;
	UI assetControlUI;
	//public AssetControlView assetView ;
	public AlertTab(Grid<Alert> alertGrid, AlertService alertService,Tree<ExtendedNode> nodeTree,UI assetControlUI, Button... buttons) {
		createAlertGridRow = buttons[0];
		editAlertGridRow = buttons[1];
		deleteAlertGridRow = buttons[2];
		saveAlertForm = buttons[3];
		cancelAlertForm = buttons[4];
		this.alertGrid = alertGrid;
		this.alertService = alertService;
		this.nodeTree = nodeTree;
		this.assetControlUI = assetControlUI;
		
	}
	
	@Autowired
	public TreeDataService treeDataService;

	@Autowired
	public AssetControlView assetView ;
	
	public VerticalLayout getAlert() {
		
		VerticalLayout alertLayout = new VerticalLayout();
		VerticalLayout alertVerticalButtonLayout = new VerticalLayout();
		HorizontalLayout alertCommandLabel = new HorizontalLayout();
		HorizontalLayout saveCancelLayout = new HorizontalLayout();
		HorizontalLayout alertSaveCancleAndLabelLayout = new HorizontalLayout();
		
		alertCommandLabel.setWidth("100%");
		alertSaveCancleAndLabelLayout.setWidth("100%");
		
		
		alertVerticalButtonLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		alertVerticalButtonLayout.addStyleName("heartbeat-verticalLayout");
		VerticalLayout formLayout = new VerticalLayout();
		formLayout.addStyleNames("heartbeat-verticalLayout", "assertAlert-formLayout");
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
		
		HorizontalLayout activeBoxLayout = new HorizontalLayout();
		Label active = new Label("Active");
		alertCommands.addStyleName("label-style");
		CheckBox activeCheckBox = new CheckBox();
		activeBoxLayout.addComponents(active, activeCheckBox);
		Component[] alertFormComponentArray = {
				ComponentUtil.getFormFieldWithLabel("Alert Type", FormFieldType.TEXTBOX),
				ComponentUtil.getFormFieldWithLabel("Name", FormFieldType.TEXTBOX),
				ComponentUtil.getFormFieldWithLabel("Description", FormFieldType.TEXTBOX),
				ComponentUtil.getFormFieldWithLabel("", FormFieldType.HORIZONTALLAYOUT),
				ComponentUtil.getFormFieldWithLabel("Email to:", FormFieldType.TEXTBOX) };

		FormLayout alertFormLayout = new FormLayout(alertFormComponentArray);
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
				((TextField) alertFormComponentArray[0]).setValue(item.getItem().getType().name());
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
				editAlertGridRow.setEnabled(false);
				deleteAlertGridRow.setEnabled(false);
				cancelAlertForm.setEnabled(false);
				saveAlertForm.setEnabled(false);
			}
		});
		return alertLayout;

	}
	
	public void confirmAlertDialog() {
		ConfirmDialog.show(assetView.getUI(), "Please Confirm:", "Are you sure you want to delete?",
		        "Ok", "Cancel", new ConfirmDialog.Listener() {

		            public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		                    // Confirmed to continue
		                	alertService.removeAlert(alertGrid.getSelectedItems().iterator().next());
		    				nodeTree.getDataProvider().refreshAll();
		    				loadAlertGrid();
		                } else {
		                    // User did not confirm
		                    
		                }
		            }
		        });
	}
	private void loadAlertGrid() {
		treeDataService.getTreeDataForDebugAndAlert();
		List<ExtendedNode> nodeList = treeDataService.getDebugAndAlertNodeList();
		Set<ExtendedNode> nodeSet = nodeTree.getSelectionModel().getSelectedItems();
		ExtendedNode nodeSelected = nodeSet.iterator().next();
		for(ExtendedNode node : nodeList) {
			if(node.getLabel().equals(nodeSelected.getLabel())) {
				DataProvider data = new ListDataProvider(node.getExtendedList());
				alertGrid.setDataProvider(data);
			}
		}
		
	}
	
	private Grid<Alert> getAlertGrid() {
		//alertGrid = new Grid<>(Alert.class);
		alertGrid.setWidth("100%");
		alertGrid.setResponsive(true);
		alertGrid.setSelectionMode(SelectionMode.SINGLE);
		alertGrid.setColumns("type", "description", "active", "email");
		alertGrid.getColumn("type").setCaption("Alert Type");
		//alertGrid.setDataProvider(alertService.getListDataProvider());

		return alertGrid;
	}

	private HorizontalLayout getAlertGridButtonLayout(Component[] componentArray) {
		HorizontalLayout alertGridButtonLayout = new HorizontalLayout();
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
				} else if (component instanceof HorizontalLayout) {
					HorizontalLayout HL = (HorizontalLayout) component;
					CheckBox checkbox = (CheckBox) HL.getComponent(1);
					checkbox.clear();
				}
			}
			saveAlertForm.setEnabled(true);
			cancelAlertForm.setEnabled(true);
		});
		createAlertGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
		createAlertGridRow.addStyleName("v-button-customstyle");
		editAlertGridRow = new Button(VaadinIcons.PENCIL, click -> {
			if (alertGrid.getSelectedItems().size() > 0) {
				for (Component component : componentArray) {
					if (!component.isEnabled())
						component.setEnabled(true);
				}

				deleteAlertGridRow.setEnabled(false);
				editAlertGridRow.setEnabled(false);
				createAlertGridRow.setEnabled(false);
			}

		});
		editAlertGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
		editAlertGridRow.addStyleName("v-button-customstyle");
		editAlertGridRow.setEnabled(false);
		deleteAlertGridRow = new Button(VaadinIcons.TRASH, click -> {
			if(alertGrid.getSelectedItems().isEmpty()) {
				Notification.show("Select any Debug type to delete", Notification.Type.WARNING_MESSAGE).setDelayMsec(3000);;
			}else {
			ConfirmDialog.show(assetControlUI, "Please Confirm:", "Are you sure you want to delete?",
			        "Ok", "Cancel", dialog -> 
			            {
			                if (dialog.isConfirmed()) {
			    				alertService.removeAlert(alertGrid.getSelectedItems().iterator().next());
			    				nodeTree.getDataProvider().refreshAll();
			    				//loadGrid();
			    				ListDataProvider<Alert> refreshAlertDataProvider = alertService.getListDataProvider();
			    				alertGrid.setDataProvider(refreshAlertDataProvider);
			    				// Refreshing
			    				alertGrid.getDataProvider().refreshAll();
			    				deleteAlertGridRow.setEnabled(false);
			                } else {
			                    // User did not confirm
			                    
			                }
			            });
		}

		});
		deleteAlertGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
		deleteAlertGridRow.addStyleName("v-button-customstyle");
		deleteAlertGridRow.setEnabled(false);
		saveAlertForm.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {	
			for (Component component : componentArray) {
				if (component.isEnabled())
					component.setEnabled(false);

			}
			saveAlertForm.addStyleName("v-button-customstyle");
			Alert alert;
			if (alertGrid.getSelectedItems().size() > 0) {
				alert = alertGrid.getSelectedItems().iterator().next();
			} else {
				alert = new Alert();
			}
			alert.setType(((TextField) componentArray[0]).getValue());
			alert.setName(((TextField) componentArray[1]).getValue());
			alert.setDescription(((TextField) componentArray[2]).getValue());
			HorizontalLayout HL= (HorizontalLayout)componentArray[3];
			CheckBox checkbox = (CheckBox) HL.getComponent(1);
			alert.setActive(checkbox.getValue());
			alert.setEmail(((TextField) componentArray[4]).getValue());
			cancelAlertForm.setEnabled(false);
			editAlertGridRow.setEnabled(false);
			deleteAlertGridRow.setEnabled(false);
			saveAlertForm.setEnabled(false);
			alertService.saveAlert(alert);
			if (nodeTree.getSelectedItems().size() <= 0) {
				alertGrid.setData(alertService.getListDataProvider());
			} else {
				alertGrid.setData(nodeTree.getSelectedItems().iterator().next().getExtendedList());
			}
			alertGrid.getDataProvider().refreshAll();
			alertGrid.deselectAll();
			}
		});
		saveAlertForm.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		saveAlertForm.addStyleName("v-button-customstyle");
		saveAlertForm.setEnabled(false);
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
			cancelAlertForm.setEnabled(false);
			editAlertGridRow.setEnabled(false);
			deleteAlertGridRow.setEnabled(false);
			saveAlertForm.setEnabled(false);
			}
		});
		cancelAlertForm.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		cancelAlertForm.addStyleName("v-button-customstyle");
		cancelAlertForm.setEnabled(false);
		alertGridButtonLayout.addComponent(createAlertGridRow);
		alertGridButtonLayout.addComponent(editAlertGridRow);
		alertGridButtonLayout.addComponent(deleteAlertGridRow);

		return alertGridButtonLayout;
	}
}
