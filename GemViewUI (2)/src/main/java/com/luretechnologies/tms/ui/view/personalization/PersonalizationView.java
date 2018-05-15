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
package com.luretechnologies.tms.ui.view.personalization;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.tms.backend.data.entity.App;
import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.Node;
import com.luretechnologies.tms.backend.data.entity.NodeLevel;
import com.luretechnologies.tms.backend.data.entity.OverRideParameters;
import com.luretechnologies.tms.backend.data.entity.ParameterType;
import com.luretechnologies.tms.backend.data.entity.Profile;
import com.luretechnologies.tms.backend.service.AppService;
import com.luretechnologies.tms.backend.service.OdometerDeviceService;
import com.luretechnologies.tms.backend.service.OverRideParamService;
import com.luretechnologies.tms.backend.service.ProfileService;
import com.luretechnologies.tms.backend.service.TreeDataService;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ContextClickEvent;
import com.vaadin.event.ContextClickEvent.ContextClickListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.TreeContextClickEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = PersonalizationView.VIEW_NAME)
public class PersonalizationView extends VerticalLayout implements Serializable, View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7795601926506565955L;
	public static final String VIEW_NAME = "personalization";
	private static Tree<Node> nodeTree;
	private static TextField treeNodeSearch, overRideParamSearch;
	private static Devices emptyDevice;
	private static HorizontalSplitPanel splitScreen;
	private static Button createEntity, copyEntity, editEntity, deleteEntity, pasteEntity, createParam, editParam,
			deleteParam, save, cancel;
	private static TextField entityName, entityDescription, entitySerialNum;
	private static ComboBox<Devices> deviceDropDown;
	private static ComboBox<App> appDropDown;
	private static ComboBox<Profile> profileDropDown;
	private static ComboBox<String> updateDropDown;
	private static ComboBox<String> addtnlFilesDropDown;
	private static ComboBox<String> frequencyDropDown;
	private static CheckBox activeCheckbox, activateHeartbeat;
	private static Grid<OverRideParameters> overRideParamGrid;
	private static Node selectedNodeForCopy;
	private static ComboBox<NodeLevel> entityType;
	private static FormLayout entityFormLayout;

	@Autowired
	public TreeDataService treeDataService;

	@Autowired
	public OverRideParamService overRideParamService;

	@Autowired
	private AppService appService;

	@Autowired
	private OdometerDeviceService deviceService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	public PersonalizationView() {

		emptyDevice = new Devices();
		emptyDevice.setManufacturer("");
		emptyDevice.setDeviceName("");
		emptyDevice.setDescription("");
		emptyDevice.setActive(false);
		emptyDevice.setSerialNumber("");
		emptyDevice.setHeartBeat(false);
		emptyDevice.setFrequency("");
		emptyDevice.setLastSeen("");

	}

	@PostConstruct
	private void inti() {
		setSpacing(false);
		setMargin(false);
		setResponsive(true);
		setHeight("100%");
		treeNodeSearch = new TextField();
		treeNodeSearch.setIcon(VaadinIcons.SEARCH);
		treeNodeSearch.setStyleName("small inline-icon search");
		treeNodeSearch.addStyleName("v-textfield-font");
		treeNodeSearch.setPlaceholder("Search");
		treeNodeSearch.setVisible(true);

		Panel panel = getAndLoadPersonlizationPanel();
		HorizontalLayout treeButtonLayout = new HorizontalLayout();
		getEntityButtonsList(treeButtonLayout);
		VerticalLayout treeLayoutWithButtons = new VerticalLayout();
		VerticalLayout treePanelLayout = new VerticalLayout();

		treeLayoutWithButtons.addComponent(treeButtonLayout);
		nodeTree = new Tree<Node>();
		nodeTree.setTreeData(treeDataService.getTreeDataForDeviceOdometer());
		nodeTree.setItemIconGenerator(item -> {
			switch (item.getLevel()) {
			case ENTITY:
				return VaadinIcons.BUILDING_O;
			case MERCHANT:
				return VaadinIcons.SHOP;
			case REGION:
				return VaadinIcons.OFFICE;
			case TERMINAL:
				return VaadinIcons.LAPTOP;
			case DEVICE:
				return VaadinIcons.MOBILE_BROWSER;
			default:
				return null;
			}
		});

		nodeTree.addItemClickListener(selection -> {
			entityFormLayout.setEnabled(false);
			overRideParamGrid.setEnabled(false);
			treeDataService.getTreeDataForPersonlization();
			List<Node> nodeList = treeDataService.getPersonlizationList();
			for (Node node : nodeList) {
				if (node.getLabel().equals(selection.getItem().getLabel())) {
					if (StringUtils.isNotEmpty(node.getDescription())) {
						entityType.setValue(node.getLevel());
						entityName.setValue(node.getLabel());
						deviceDropDown.setValue(node.getDevice());
						entityDescription.setValue(node.getDescription());
						activeCheckbox.setValue(node.isActive());
						entitySerialNum.setValue(node.getSerialNum());
						activateHeartbeat.setValue(node.isHeartBeat());
						frequencyDropDown.setValue(node.getFrequency());
						appDropDown.setValue(node.getApp());
						addtnlFilesDropDown.setValue(node.getAdditionaFiles());
						profileDropDown.setValue(node.getProfile());
						updateDropDown.setValue(node.getUpdate());
						DataProvider data = new ListDataProvider(node.getEntityList());
						overRideParamGrid.setDataProvider(data);
					} else {
						ClearAllComponents();
						ClearGrid();
						entityFormLayout.setEnabled(true);
						overRideParamGrid.setEnabled(true);
					}
				}
			}

		});
		nodeTree.addContextClickListener( event ->{
			if (!(event instanceof TreeContextClickEvent)) {
				return;
			}
			TreeContextClickEvent treeContextEvent = (TreeContextClickEvent) event;
			Node contextNode = (Node) treeContextEvent.getItem();
			UI.getCurrent().addWindow(openTreeNodeEditWindow(contextNode));
			
		});
		treeLayoutWithButtons.addComponent(nodeTree);
		treeLayoutWithButtons.setComponentAlignment(nodeTree, Alignment.TOP_LEFT);
		treeLayoutWithButtons.setMargin(true);
		// treeLayoutWithButtons.setHeight("100%");
		treeLayoutWithButtons.setStyleName("split-Height-ButtonLayout");
		treePanelLayout.addComponent(treeLayoutWithButtons);
		treePanelLayout.setHeight("100%");
		treePanelLayout.setStyleName("split-height");
		// treeLayoutWithButtons.getsetHeight("0");
		// treePanelLayout.setComponentAlignment(nodeTree, Alignment.TOP_LEFT);
		splitScreen = new HorizontalSplitPanel();
		splitScreen.setFirstComponent(treePanelLayout);
		splitScreen.setSplitPosition(30);
		// splitScreen.addComponent(getTabSheet());
		splitScreen.setHeight("100%");

		getEntityInformation(splitScreen);
		panel.setContent(splitScreen);

		entityFormLayout.setEnabled(false);
		overRideParamGrid.setEnabled(false);
		if (nodeTree.getSelectedItems().isEmpty()) {
			entityFormLayout.setEnabled(false);
			overRideParamGrid.setEnabled(false);
			ClearAllComponents();
			ClearGrid();
		}
	}

	public Panel getAndLoadPersonlizationPanel() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setCaption("Personalization");
		panel.setResponsive(true);
		panel.setSizeFull();
		addComponent(panel);
		return panel;
	}

	private void getEntityButtonsList(HorizontalLayout treeButtonLayout) {
		createEntity = new Button(VaadinIcons.FOLDER_ADD, click -> {
			Window createProfileWindow = openEntityWindow();
			if (createProfileWindow.getParent() == null)
				UI.getCurrent().addWindow(createProfileWindow);

		});
		createEntity.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");

		copyEntity = new Button(VaadinIcons.COPY, click -> {
			// FIXME Copy new entity
			if (nodeTree.getSelectionModel().getFirstSelectedItem().isPresent()) {
				selectedNodeForCopy = nodeTree.getSelectionModel().getFirstSelectedItem().get();
				if (selectedNodeForCopy.getLevel().equals(NodeLevel.ENTITY)) {
					Notification.show("This Node can't be copied", Type.WARNING_MESSAGE);
					selectedNodeForCopy = null;
				} else {
					pasteEntity.setEnabled(true);
				}
			}
		});
		copyEntity.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		pasteEntity = new Button(VaadinIcons.PASTE, click -> {
			// FIXME Paste new entity
			if (nodeTree.getSelectionModel().getFirstSelectedItem().isPresent()) {
				if (selectedNodeForCopy != null) {
					Node toPasteNode = nodeTree.getSelectionModel().getFirstSelectedItem().get();
					if (toPasteNode.getLevel().equals(selectedNodeForCopy.getLevel())) {
						Notification.show("Node can't be pasted on level", Type.WARNING_MESSAGE);
					} else {
						// FIXME: add the REST code copy and paste tree node.
						Node copyPasteNode = new Node();
						copyPasteNode.setLevel(selectedNodeForCopy.getLevel());
						copyPasteNode.setLabel(selectedNodeForCopy.getLabel());
						copyPasteNode.setEntityList(selectedNodeForCopy.getEntityList());
						if (toPasteNode.getLevel().equals(NodeLevel.ENTITY)
								&& copyPasteNode.getLevel().equals(NodeLevel.REGION)) {
							nodeTree.getTreeData().addItem(toPasteNode, copyPasteNode);
							for (Node childRegionNode : nodeTree.getTreeData().getChildren(selectedNodeForCopy)) {
								Node newChildRegionNode = new Node();
								newChildRegionNode.setLevel(childRegionNode.getLevel());
								newChildRegionNode.setLabel(childRegionNode.getLabel());
								newChildRegionNode.setEntityList(childRegionNode.getEntityList());
								nodeTree.getTreeData().addItem(copyPasteNode, newChildRegionNode);
								for (Node childMerchantNode : nodeTree.getTreeData().getChildren(childRegionNode)) {
									Node newChildMerchantNode = new Node();
									newChildMerchantNode.setLevel(childMerchantNode.getLevel());
									newChildMerchantNode.setLabel(childMerchantNode.getLabel());
									newChildMerchantNode.setEntityList(childMerchantNode.getEntityList());
									nodeTree.getTreeData().addItem(newChildRegionNode, newChildMerchantNode);
									for (Node childTerminalNode : nodeTree.getTreeData()
											.getChildren(childMerchantNode)) {
										Node newChildTerminalNode = new Node();
										newChildTerminalNode.setLevel(childTerminalNode.getLevel());
										newChildTerminalNode.setLabel(childTerminalNode.getLabel());
										newChildTerminalNode.setEntityList(childTerminalNode.getEntityList());
										nodeTree.getTreeData().addItem(newChildMerchantNode, newChildTerminalNode);
										for (Node childDeviceNode : nodeTree.getTreeData()
												.getChildren(childTerminalNode)) {
											Node newChildDeviceNode = new Node();
											newChildDeviceNode.setLevel(childDeviceNode.getLevel());
											newChildDeviceNode.setLabel(childDeviceNode.getLabel());
											newChildDeviceNode.setEntityList(childDeviceNode.getEntityList());
											nodeTree.getTreeData().addItem(newChildTerminalNode, newChildDeviceNode);
										}
									}
								}
							}
						} else if (toPasteNode.getLevel().equals(NodeLevel.REGION)
								&& copyPasteNode.getLevel().equals(NodeLevel.MERCHANT)) {
							nodeTree.getTreeData().addItem(toPasteNode, copyPasteNode);
							for (Node childMerchantNode : nodeTree.getTreeData().getChildren(selectedNodeForCopy)) {
								Node newChildMerchantNode = new Node();
								newChildMerchantNode.setLevel(childMerchantNode.getLevel());
								newChildMerchantNode.setLabel(childMerchantNode.getLabel());
								newChildMerchantNode.setEntityList(childMerchantNode.getEntityList());
								nodeTree.getTreeData().addItem(copyPasteNode, newChildMerchantNode);
								for (Node childTerminalNode : nodeTree.getTreeData().getChildren(childMerchantNode)) {
									Node newChildTerminalNode = new Node();
									newChildTerminalNode.setLevel(childTerminalNode.getLevel());
									newChildTerminalNode.setLabel(childTerminalNode.getLabel());
									newChildTerminalNode.setEntityList(childTerminalNode.getEntityList());
									nodeTree.getTreeData().addItem(newChildMerchantNode, newChildTerminalNode);
									for (Node childDeviceNode : nodeTree.getTreeData().getChildren(childTerminalNode)) {
										Node newChildDeviceNode = new Node();
										newChildDeviceNode.setLevel(childDeviceNode.getLevel());
										newChildDeviceNode.setLabel(childDeviceNode.getLabel());
										newChildDeviceNode.setEntityList(childDeviceNode.getEntityList());
										nodeTree.getTreeData().addItem(newChildTerminalNode, newChildDeviceNode);
									}
								}
							}
						} else if (toPasteNode.getLevel().equals(NodeLevel.MERCHANT)
								&& copyPasteNode.getLevel().equals(NodeLevel.TERMINAL)) {
							nodeTree.getTreeData().addItem(toPasteNode, copyPasteNode);
							for (Node childNode : nodeTree.getTreeData().getChildren(selectedNodeForCopy)) {
								// FIXME: provide a common log of copy constructor
								Node newChildNode = new Node();
								newChildNode.setLabel(childNode.getLabel());
								newChildNode.setLevel(childNode.getLevel());
								newChildNode.setEntityList(childNode.getEntityList());
								nodeTree.getTreeData().addItem(copyPasteNode, newChildNode);
							}

						} else if (toPasteNode.getLevel().equals(NodeLevel.TERMINAL)
								&& copyPasteNode.getLevel().equals(NodeLevel.DEVICE))
							nodeTree.getTreeData().addItem(toPasteNode, copyPasteNode);
						else
							Notification.show("The Node " + selectedNodeForCopy.getLabel() + " can't be copied under "
									+ toPasteNode.getLabel());

						nodeTree.getDataProvider().refreshAll();
					}
					pasteEntity.setEnabled(false);
				}
			}
		});
		pasteEntity.setEnabled(false);
		pasteEntity.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		editEntity = new Button(VaadinIcons.EDIT, click -> {
			entityFormLayout.setEnabled(true);
			overRideParamGrid.setEnabled(true);
			if (overRideParamGrid.getSelectedItems().size() > 0)
				overRideParamGrid.getEditor().setEnabled(true);
		});
		editEntity.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");

		deleteEntity = new Button(VaadinIcons.TRASH, click -> {
			confirmDeleteEntity();
		});
		deleteEntity.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");

		treeButtonLayout.addComponents(treeNodeSearch, createEntity, copyEntity, pasteEntity, editEntity, deleteEntity);
	}

	private void getEntityInformation(HorizontalSplitPanel splitScreen) {
		VerticalLayout entityInformationLayout = new VerticalLayout();
		HorizontalLayout saveCancelEntityInfoLabelLayout = new HorizontalLayout();
		HorizontalLayout saveCancelButtonLayout = new HorizontalLayout();
		HorizontalLayout entityLabelLayout = new HorizontalLayout();
		entityLabelLayout.setWidth("100%");
		saveCancelEntityInfoLabelLayout.setSizeFull();
		entityFormLayout = new FormLayout();
		entityFormLayout.setWidth("100%");

		Label entityInformationLabel = new Label("Entity Information");
		entityInformationLabel.addStyleName("label-style");
		entityInformationLabel.addStyleNames(ValoTheme.LABEL_BOLD, ValoTheme.LABEL_H3);
		entityLabelLayout.addComponent(entityInformationLabel);

		cancel = new Button("Cancel", click -> {
			ClearAllComponents();
			ClearGrid();
			entityFormLayout.setEnabled(false);
			overRideParamGrid.setEnabled(false);
			nodeTree.deselect(nodeTree.getSelectedItems().iterator().next());
		});
		cancel.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		cancel.addStyleName("v-button-customstyle");
		cancel.setResponsive(true);
		saveCancelButtonLayout.addComponent(cancel);

		save = new Button("Save", click -> {

		});
		save.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		save.addStyleName("v-button-customstyle");
		save.setResponsive(true);
		saveCancelButtonLayout.addComponent(save);

		saveCancelButtonLayout.setComponentAlignment(cancel, Alignment.MIDDLE_RIGHT);
		saveCancelButtonLayout.setComponentAlignment(save, Alignment.MIDDLE_RIGHT);
		saveCancelButtonLayout.setResponsive(true);
		saveCancelButtonLayout.setStyleName("save-cancelButtonsAlignment");

		saveCancelEntityInfoLabelLayout.addComponents(entityLabelLayout, saveCancelButtonLayout);
		saveCancelEntityInfoLabelLayout.setComponentAlignment(saveCancelButtonLayout, Alignment.MIDDLE_RIGHT);

		getEntityFormLayout(entityFormLayout);
		entityInformationLayout.addComponents(saveCancelEntityInfoLabelLayout, entityFormLayout);
		getOverRideParamGrid(entityInformationLayout);

		splitScreen.addComponent(entityInformationLayout);
	}

	private void getEntityFormLayout(FormLayout entityFormLayout) {

		getEntityType(entityFormLayout);

		getEntityName(entityFormLayout);

		getEntityDescription(entityFormLayout);

		getEntityActive(entityFormLayout);

		getEntitySerialNum(entityFormLayout);

		getEntityHeartBeat(entityFormLayout);

		getEntityFrequency(entityFormLayout);

		getApplicationSelection(entityFormLayout);

		getScheduleUpdate(entityFormLayout);
	}

	private void getEntityType(FormLayout entityFormLayout) {
		entityType = new ComboBox();
		entityType.setCaption("Entity Type");
		entityType.setDataProvider(new ListDataProvider<>(Arrays.asList(NodeLevel.ENTITY, NodeLevel.REGION,
				NodeLevel.MERCHANT, NodeLevel.TERMINAL, NodeLevel.DEVICE)));
		// entityType.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		// entityType.setWidth("48%");
		// entityType.setStyleName("role-textbox");
		// entityType.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		// entityType.addStyleName("v-grid-cell");
		// entityType.setEnabled(false);
		// do Necessary operations for popping data

		entityFormLayout.addComponent(entityType);
	}

	private void getEntityName(FormLayout entityFormLayout) {
		HorizontalLayout entityNameDeviceDropDown = new HorizontalLayout();
		entityNameDeviceDropDown.setCaption("Name");
		entityNameDeviceDropDown.setWidth("100%");
		entityName = new TextField();
		entityName.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		entityName.setWidth("48%");
		entityName.setStyleName("role-textbox");
		entityName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		// entityName.addStyleName("v-grid-cell");

		deviceDropDown = new ComboBox<Devices>();
		deviceDropDown.setCaption("");
		deviceDropDown.setPlaceholder("Device");
		deviceDropDown.setDataProvider(deviceService.getListDataProvider());
		deviceDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size");
		// do Necessary operations for popping data

		entityNameDeviceDropDown.addComponents(entityName, deviceDropDown);

		entityFormLayout.addComponent(entityNameDeviceDropDown);
	}

	private void getEntityDescription(FormLayout entityFormLayout) {

		entityDescription = new TextField("Description");
		entityDescription.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		entityDescription.setWidth("48%");
		entityDescription.setStyleName("role-textbox");
		entityDescription.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);

		// do Necessary operations for popping data

		entityFormLayout.addComponent(entityDescription);
	}

	private void getEntityActive(FormLayout entityFormLayout) {

		HorizontalLayout entityActiveLayout = new HorizontalLayout();
		Label active = new Label("Active");
		active.setStyleName("role-activeLable");
		active.addStyleName("v-textfield-font");
		activeCheckbox = new CheckBox();
		activeCheckbox.setCaption("Entity Active");
		activeCheckbox.addStyleName("v-textfield-font");
		// do Necessary operations for popping data

		entityActiveLayout.addComponents(active, activeCheckbox);
		entityActiveLayout.setStyleName("role-activeLable");
		entityActiveLayout.addStyleName("assetAlert-activeCheckBox");

		entityFormLayout.addComponent(entityActiveLayout);
	}

	private void getEntitySerialNum(FormLayout entityFormLayout) {
		entitySerialNum = new TextField("Serial Num.");
		entitySerialNum.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		entitySerialNum.setWidth("48%");
		entitySerialNum.setStyleName("role-textbox");
		entitySerialNum.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);

		// do Necessary operations for popping data

		entityFormLayout.addComponent(entitySerialNum);
	}

	private void getEntityHeartBeat(FormLayout entityFormLayout) {

		HorizontalLayout entityHeartbeatLayout = new HorizontalLayout();
		Label heartbeat = new Label("HeartBeat");
		heartbeat.setStyleName("role-activeLable");
		heartbeat.addStyleName("v-textfield-font");
		activateHeartbeat = new CheckBox();
		activateHeartbeat.setCaption("Entity Active");
		activateHeartbeat.addStyleName("v-textfield-font");
		// do Necessary operations for popping data

		entityHeartbeatLayout.addComponents(heartbeat, activateHeartbeat);
		entityHeartbeatLayout.setStyleName("role-activeLable");
		entityHeartbeatLayout.addStyleName("assetAlert-activeCheckBox");

		entityFormLayout.addComponent(entityHeartbeatLayout);
	}

	private void getEntityFrequency(FormLayout entityFormLayout) {

		frequencyDropDown = new ComboBox<>();
		frequencyDropDown.setDataProvider(new ListDataProvider<>(
				Arrays.asList("120 Seconds", "90 Seconds", " 30 Seconds", "Daily", "Monthly", "Never")));
		frequencyDropDown.setCaption("Frequency");
		frequencyDropDown.setPlaceholder("Frequency");
		frequencyDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size");
		// do Necessary operations for popping data

		entityFormLayout.addComponent(frequencyDropDown);
	}

	private void getApplicationSelection(FormLayout entityFormLayout) {

		HorizontalLayout entityApplicationSelection = new HorizontalLayout();
		entityApplicationSelection.setCaption("Application Selection");
		appDropDown = new ComboBox<App>();
		// frequencyDropDown.setDataProvider(new ListDataProvider<>(Arrays.asList("24
		// Hours", "1 Hour", " 30 Minutes")));
		appDropDown.setCaption("");
		appDropDown.setPlaceholder("Application");
		appDropDown.setDataProvider(appService.getListDataProvider());
		appDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size");

		// do Necessary operations for popping data

		addtnlFilesDropDown = new ComboBox<>();
		addtnlFilesDropDown.setDataProvider(new ListDataProvider<>(
				Arrays.asList("Entity Files", "Region Files", "Merchant Files", "Terminal Files", "Device Files")));
		addtnlFilesDropDown.setCaption("");
		addtnlFilesDropDown.setPlaceholder("Additional Files");
		addtnlFilesDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size");

		// do Necessary operations for popping data

		profileDropDown = new ComboBox<Profile>();
		// frequencyDropDown.setDataProvider(new ListDataProvider<>(Arrays.asList("24
		// Hours", "1 Hour", " 30 Minutes")));
		profileDropDown.setCaption("");
		profileDropDown.setPlaceholder("Default Profile");
		profileDropDown.setDataProvider(profileService.getListDataProvider());
		profileDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size");

		// do Necessary operations for popping data

		entityApplicationSelection.addComponents(appDropDown, addtnlFilesDropDown, profileDropDown);
		entityFormLayout.addComponent(entityApplicationSelection);
	}

	private void getScheduleUpdate(FormLayout entityFormLayout) {

		updateDropDown = new ComboBox<>();
		updateDropDown.setDataProvider(
				new ListDataProvider<>(Arrays.asList("Update 1", "Update 2", "Update 3", "Update 4", "Update 5")));
		updateDropDown.setCaption("Schedule Update");
		updateDropDown.setPlaceholder("Update");
		updateDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size");

		// do Necessary operations for popping data

		entityFormLayout.addComponent(updateDropDown);
	}

	private void getOverRideParamGrid(VerticalLayout entityInformationLayout) {
		HorizontalLayout overRideParamLabelLayout = new HorizontalLayout();
		HorizontalLayout overRideParamButtonLayout = new HorizontalLayout();

		Label overRideParamLabel = new Label("Override Parameters");
		overRideParamLabel.addStyleName("label-style");
		overRideParamLabel.addStyleNames(ValoTheme.LABEL_BOLD, ValoTheme.LABEL_H3);
		overRideParamLabelLayout.addComponent(overRideParamLabel);

		overRideParamSearch = new TextField();
		overRideParamSearch.setIcon(VaadinIcons.SEARCH);
		overRideParamSearch.setStyleName("small inline-icon search");
		overRideParamSearch.addStyleName("v-textfield-font");
		overRideParamSearch.setPlaceholder("Search");
		overRideParamSearch.setVisible(true);

		createParam = new Button(VaadinIcons.FOLDER_ADD, click -> {
			openOverRideParamWindow(overRideParamGrid);
		});
		createParam.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");

		editParam = new Button(VaadinIcons.EDIT, click -> {
			// Todo Edit new entity
		});
		editParam.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");

		deleteParam = new Button(VaadinIcons.TRASH, click -> {
			if (overRideParamGrid.getSelectedItems().isEmpty()) {
				Notification.show("Select any App Default Parameter type to delete", Notification.Type.WARNING_MESSAGE)
						.setDelayMsec(3000);
			} else {
				confirmDeleteOverRideParam(overRideParamGrid, overRideParamSearch);
			}
		});
		deleteParam.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");

		overRideParamButtonLayout.addComponents(overRideParamSearch, createParam, editParam, deleteParam);

		overRideParamGrid = new Grid<>(OverRideParameters.class);
		overRideParamGrid.setWidth("100%");
		overRideParamGrid.setHeightByRows(4);
		overRideParamGrid.setResponsive(true);
		overRideParamGrid.setSelectionMode(SelectionMode.SINGLE);
		overRideParamGrid.setColumns("parameter", "description", "type", "value");
		overRideParamGrid.getEditor().setEnabled(true).addSaveListener(save -> {
			// Save listner. Typically value is set to selected App
			// If not happening then get the Object from list of Selected App and set the
			// edited value

		});

		overRideParamSearch.addValueChangeListener(valueChange -> {
			String valueInLower = valueChange.getValue().toLowerCase();
			ListDataProvider<OverRideParameters> paramDataProvider = (ListDataProvider<OverRideParameters>) overRideParamGrid
					.getDataProvider();
			paramDataProvider.setFilter(filter -> {
				String parameterInLower = filter.getParameter().toLowerCase();
				String descriptionInLower = filter.getDescription().toLowerCase();
				String typeLower = filter.getType().name().toLowerCase();
				String value = filter.getValue().toLowerCase();
				return descriptionInLower.equals(valueInLower) || parameterInLower.contains(valueInLower)
						|| typeLower.contains(valueInLower) || value.contains(valueInLower);
			});
		});

		entityInformationLayout.addComponents(overRideParamLabelLayout, overRideParamButtonLayout, overRideParamGrid);
	}
	private Window openTreeNodeEditWindow(Node nodeToEdit) {
		Window nodeEditWindow = new Window("Edit Node");
		TextField nodeName = new TextField("Name",nodeToEdit.getLabel());
		Button saveNode = new Button("Save",click->{
			if(StringUtils.isNotEmpty(nodeName.getValue())){
				nodeToEdit.setLabel(nodeName.getValue());
				nodeTree.getDataProvider().refreshAll();
				nodeEditWindow.close();
				
			}else {
				Notification.show("Empty text cant be saved.",Type.ERROR_MESSAGE);
			}
		});
		
		Button cancelNode = new Button("Cancel", click -> {
			nodeEditWindow.close();
		});
		HorizontalLayout buttonLayout = new HorizontalLayout(saveNode, cancelNode);
		FormLayout profileFormLayout = new FormLayout(nodeName);
		
		// Window Setup
		nodeEditWindow.setContent(new VerticalLayout(profileFormLayout, buttonLayout));
		nodeEditWindow.center();
		nodeEditWindow.setModal(true);
		nodeEditWindow.setClosable(true);
		nodeEditWindow.setWidth(30, Unit.PERCENTAGE);
		return nodeEditWindow;
	}
	private Window openEntityWindow() {
		Window entityWindow = new Window("Add Entity");
		TextField entityName = new TextField("Name");

		Button saveProfile = new Button("Save", click -> {
			if (StringUtils.isNotEmpty(entityName.getValue()) && nodeTree.getSelectedItems().size() == 1) {
				Node selectedNode = nodeTree.getSelectedItems().iterator().next();
				Node newNode = new Node();
				newNode.setLabel(entityName.getValue().isEmpty() ? "Child Node" : entityName.getValue());// Pick name
																											// from
																											// textfield
				// FIXME add the REST code for adding new entity
				if (selectedNode.getLevel() == NodeLevel.ENTITY)

					if (selectedNode.getLevel() == NodeLevel.REGION)

						if (selectedNode.getLevel() == NodeLevel.MERCHANT)

							if (selectedNode.getLevel() == NodeLevel.TERMINAL)

								if (selectedNode.getLevel() == NodeLevel.DEVICE) {

									return;
								}

				switch (selectedNode.getLevel()) {
				case ENTITY:
					newNode.setLevel(NodeLevel.REGION);
					break;
				case MERCHANT:
					newNode.setLevel(NodeLevel.TERMINAL);
					break;
				case REGION:
					newNode.setLevel(NodeLevel.MERCHANT);
					break;
				case TERMINAL:
					newNode.setLevel(NodeLevel.DEVICE);
					break;
				case DEVICE:
					entityWindow.close();
					Notification.show("Not allowed to add node under Device", Type.ERROR_MESSAGE);
					return;
				default:
					break;
				}
				nodeTree.getTreeData().addItem(selectedNode, newNode);
				nodeTree.getDataProvider().refreshAll();

			}
			entityWindow.close();
		});

		// nodeTree.getTreeData().addItem(selectedNode, newNode);

		Button cancelProfile = new Button("Reset", click -> {
			entityName.clear();
		});
		HorizontalLayout buttonLayout = new HorizontalLayout(saveProfile, cancelProfile);
		FormLayout profileFormLayout = new FormLayout(entityName);

		// Window Setup
		entityWindow.setContent(new VerticalLayout(profileFormLayout, buttonLayout));
		entityWindow.center();
		entityWindow.setModal(true);
		entityWindow.setClosable(true);
		entityWindow.setWidth(30, Unit.PERCENTAGE);
		return entityWindow;
	}

	private void ClearAllComponents() {
		entityName.clear();
		entityDescription.clear();
		entityType.clear();
		deviceDropDown.clear();
		activeCheckbox.clear();
		entitySerialNum.clear();
		activateHeartbeat.clear();
		frequencyDropDown.clear();
		appDropDown.clear();
		addtnlFilesDropDown.clear();
		profileDropDown.clear();
		updateDropDown.clear();
	}

	private void ClearGrid() {
		// overRideParamGrid
		overRideParamGrid.setDataProvider(new ListDataProvider<>(Arrays.asList()));
	}

	private void confirmDeleteOverRideParam(Grid<OverRideParameters> overRideParamGrid, TextField overRideParamSearch) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?", "Ok", "Cancel",
				new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							// Confirmed to continue
							overRideParamSearch.clear();
							overRideParamGrid.setEnabled(false);
							overRideParamService
									.removeOverRidetParam(overRideParamGrid.getSelectedItems().iterator().next());
							overRideParamGrid.setDataProvider(overRideParamService.getListDataProvider());
							overRideParamGrid.getDataProvider().refreshAll();
							overRideParamGrid.deselectAll();
							// TODO add Grid reload
						} else {
							// User did not confirm

						}
					}
				});
	}

	private void confirmDeleteEntity() {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?", "Ok", "Cancel",
				new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							if (nodeTree.getSelectedItems().size() == 1) {
								// TODO add yes/no confirmation for delete
								nodeTree.getTreeData().removeItem(nodeTree.getSelectedItems().iterator().next());
								// Notification.show("To be Deleted
								// "+nodeTree.getSelectedItems().iterator().next(),Type.WARNING_MESSAGE);
								nodeTree.getDataProvider().refreshAll();
								ClearAllComponents();
								ClearGrid();
							}
						} else {
							// User did not confirm

						}
					}
				});
	}

	private Window openOverRideParamWindow(Grid<OverRideParameters> overRideParamGrid) {
		Window overRideParamWindow = new Window("Add Over Ride Parameter");
		TextField parameterName = new TextField("Parameter");
		TextField parameterDescription = new TextField("Description");
		ComboBox<ParameterType> parameterType = new ComboBox<>("Type");
		parameterType.setDataProvider(new ListDataProvider<>(Arrays.asList(ParameterType.values())));
		TextField parameterValue = new TextField("Value");
		Button saveParameter = new Button("Save", click -> {
			if (StringUtils.isNotEmpty(parameterType.getValue().name())
					&& StringUtils.isNotEmpty(parameterName.getValue())) {
				OverRideParameters overRideParam = new OverRideParameters(parameterName.getValue(),
						parameterDescription.getValue(), parameterType.getValue(), parameterValue.getValue());
				// appDefaultParamService.saveAppDefaultParam(appDefaultParam);
				// nodeTree.getSelectedItems().iterator().next().getEntityList().add(overRideParam);
				// appService.saveApp(app);
				DataProvider data = new ListDataProvider(nodeTree.getSelectedItems().iterator().next().getEntityList());
				overRideParamGrid.setDataProvider(data);
				overRideParamWindow.close();
			}
		});
		Button cancelParameter = new Button("Reset", click -> {
			parameterName.clear();
			parameterValue.clear();
			parameterDescription.clear();
			parameterType.clear();
		});
		HorizontalLayout buttonLayout = new HorizontalLayout(saveParameter, cancelParameter);
		FormLayout profileFormLayout = new FormLayout(parameterName, parameterType, parameterDescription,
				parameterValue);

		// Window Setup
		overRideParamWindow.setContent(new VerticalLayout(profileFormLayout, buttonLayout));
		overRideParamWindow.center();
		overRideParamWindow.setModal(true);
		overRideParamWindow.setClosable(true);
		overRideParamWindow.setWidth(30, Unit.PERCENTAGE);
		return overRideParamWindow;
	}
}
