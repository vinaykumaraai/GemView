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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.luretechnologies.tms.backend.data.entity.App;
import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.ExtendedNode;
import com.luretechnologies.tms.backend.data.entity.Node;
import com.luretechnologies.tms.backend.data.entity.OverRideParameters;
import com.luretechnologies.tms.backend.data.entity.Profile;
import com.luretechnologies.tms.backend.service.OverRideParamService;
import com.luretechnologies.tms.backend.service.TreeDataService;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = PersonalizationView.VIEW_NAME)
public class PersonalizationView extends VerticalLayout implements Serializable, View{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7795601926506565955L;
	public static final String VIEW_NAME = "personalization";
	private static Tree<Node> nodeTree;
	private static TextField treeNodeSearch, overRideParamSearch;
	private static Devices emptyDevice;
	private static HorizontalSplitPanel splitScreen;
	private static Button createEntity, copyEntity, editEntity, deleteEntity, createParam, editParam, deleteParam;
	private static TextField entityType, entityName, entityDescription, entitySerialNum;
	private static ComboBox<Devices> deviceDropDown;
	private static ComboBox<App> appDropDown;
	private static ComboBox<Profile> profileDropDown;
	private static ComboBox<String> updateDropDown;
	private static ComboBox<String> addtnlFilesDropDown;
	private static ComboBox<String> frequencyDropDown;
	private static CheckBox activeCheckbox, activateHeartbeat;
	private static Grid<OverRideParameters> overRideParamGrid;
	
	@Autowired
	public TreeDataService treeDataService;
	
	@Autowired
	public OverRideParamService overRideParamService;
	
	@Autowired
	public PersonalizationView() {
		emptyDevice= new Devices();
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
		treeLayoutWithButtons.addComponent(nodeTree);
		treeLayoutWithButtons.setComponentAlignment(nodeTree, Alignment.TOP_LEFT);
		treeLayoutWithButtons.setMargin(true);
		//treeLayoutWithButtons.setHeight("100%");
		treeLayoutWithButtons.setStyleName("split-Height-ButtonLayout");
		treePanelLayout.addComponent(treeLayoutWithButtons);
		treePanelLayout.setHeight("100%");
		treePanelLayout.setStyleName("split-height");
		//treeLayoutWithButtons.getsetHeight("0");
		//treePanelLayout.setComponentAlignment(nodeTree, Alignment.TOP_LEFT);
		splitScreen = new HorizontalSplitPanel();
		splitScreen.setFirstComponent(treePanelLayout);
		splitScreen.setSplitPosition(30);
		//splitScreen.addComponent(getTabSheet());
		splitScreen.setHeight("100%");
		
		getEntityInformation(splitScreen);
		panel.setContent(splitScreen);
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
			//Todo create new entity
		});
		createEntity.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		
		copyEntity = new Button(VaadinIcons.COPY, click -> {
			//Todo Copy new entity
		});
		copyEntity.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		
		editEntity = new Button(VaadinIcons.EDIT, click -> {
			//Todo Edit new entity
		});
		editEntity.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		
		deleteEntity = new Button(VaadinIcons.TRASH, click -> {
			//Todo Delete new entity
		});
		deleteEntity.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		
		treeButtonLayout.addComponents(treeNodeSearch,createEntity,copyEntity,editEntity,deleteEntity);
	}
	
	private void getEntityInformation(HorizontalSplitPanel splitScreen) {
		VerticalLayout entityInformationLayout = new VerticalLayout();
		HorizontalLayout entityLabelLayout = new HorizontalLayout();
		FormLayout entityFormLayout = new FormLayout();
		entityFormLayout.setWidth("100%");
		
		Label entityInformationLabel = new Label("Entity Information");
		entityInformationLabel.addStyleName("label-style");
		entityInformationLabel.addStyleNames(ValoTheme.LABEL_BOLD, ValoTheme.LABEL_H3);
		entityLabelLayout.addComponent(entityInformationLabel);
		
		getEntityFormLayout(entityFormLayout);
		entityInformationLayout.addComponents(entityLabelLayout, entityFormLayout);
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
		entityType = new TextField("Entity Type");
		entityType.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		entityType.setWidth("48%");
		entityType.setStyleName("role-textbox");
		entityType.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		//entityType.addStyleName("v-grid-cell");
		//entityType.setEnabled(false);
		//do Necessary operations for popping data
		
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
		//entityName.addStyleName("v-grid-cell");
		
		deviceDropDown = new ComboBox<>();
		deviceDropDown.setCaption("");
		deviceDropDown.setPlaceholder("Device");
		deviceDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size");
		//do Necessary operations for popping data
		
		entityNameDeviceDropDown.addComponents(entityName, deviceDropDown);
		
		entityFormLayout.addComponent(entityNameDeviceDropDown);
	}
	
	private void getEntityDescription(FormLayout entityFormLayout) {
		
		entityDescription = new TextField("Description");
		entityDescription.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		entityDescription.setWidth("48%");
		entityDescription.setStyleName("role-textbox");
		entityDescription.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		
		//do Necessary operations for popping data
		
		
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
		//do Necessary operations for popping data
		
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
		
		//do Necessary operations for popping data
		
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
		//do Necessary operations for popping data
		
		entityHeartbeatLayout.addComponents(heartbeat, activateHeartbeat);
		entityHeartbeatLayout.setStyleName("role-activeLable");
		entityHeartbeatLayout.addStyleName("assetAlert-activeCheckBox");
		
		entityFormLayout.addComponent(entityHeartbeatLayout);
	}
	
	private void getEntityFrequency(FormLayout entityFormLayout) {
		
		frequencyDropDown = new ComboBox<>();
		frequencyDropDown.setDataProvider(new ListDataProvider<>(Arrays.asList("24 Hours", "1 Hour", " 30 Minutes")));
		frequencyDropDown.setCaption("Frequency");
		frequencyDropDown.setPlaceholder("Frequency");
		frequencyDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size");
		//do Necessary operations for popping data
		
		entityFormLayout.addComponent(frequencyDropDown);
	}
	
	private void getApplicationSelection(FormLayout entityFormLayout) {
		
		HorizontalLayout entityApplicationSelection = new HorizontalLayout();
		entityApplicationSelection.setCaption("Application Selection");
		appDropDown = new ComboBox<>();
		//frequencyDropDown.setDataProvider(new ListDataProvider<>(Arrays.asList("24 Hours", "1 Hour", " 30 Minutes")));
		appDropDown.setCaption("");
		appDropDown.setPlaceholder("Application");
		appDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size");
		
		//do Necessary operations for popping data
		
		addtnlFilesDropDown = new ComboBox<>();
		//frequencyDropDown.setDataProvider(new ListDataProvider<>(Arrays.asList("24 Hours", "1 Hour", " 30 Minutes")));
		addtnlFilesDropDown.setCaption("");
		addtnlFilesDropDown.setPlaceholder("Additional Files");
		addtnlFilesDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size");
		
		//do Necessary operations for popping data
		
		profileDropDown = new ComboBox<>();
		//frequencyDropDown.setDataProvider(new ListDataProvider<>(Arrays.asList("24 Hours", "1 Hour", " 30 Minutes")));
		profileDropDown.setCaption("");
		profileDropDown.setPlaceholder("Default Profile");
		profileDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size");
		
		//do Necessary operations for popping data
		
		
		entityApplicationSelection.addComponents(appDropDown,addtnlFilesDropDown,profileDropDown);
		entityFormLayout.addComponent(entityApplicationSelection);
	}
	
	private void getScheduleUpdate(FormLayout entityFormLayout) {
	
		updateDropDown = new ComboBox<>();
		//frequencyDropDown.setDataProvider(new ListDataProvider<>(Arrays.asList("24 Hours", "1 Hour", " 30 Minutes")));
		updateDropDown.setCaption("Application");
		updateDropDown.setPlaceholder("Application");
		updateDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size");
		
		//do Necessary operations for popping data
	
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
			//Todo create new entity
		});
		createParam.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		
		editParam = new Button(VaadinIcons.EDIT, click -> {
			//Todo Edit new entity
		});
		editParam.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		
		deleteParam = new Button(VaadinIcons.TRASH, click -> {
			//Todo Delete new entity
		});
		deleteParam.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		
		overRideParamButtonLayout.addComponents(overRideParamSearch, createParam, editParam, deleteParam);
		
		overRideParamGrid = new Grid<>(OverRideParameters.class);
		overRideParamGrid.setWidth("100%");
		overRideParamGrid.setHeightByRows(4);
		overRideParamGrid.setResponsive(true);
		overRideParamGrid.setSelectionMode(SelectionMode.SINGLE);
		overRideParamGrid.setColumns("parameter", "description", "type", "value");
		
		entityInformationLayout.addComponents(overRideParamLabelLayout, overRideParamButtonLayout, overRideParamGrid);
	}
}
