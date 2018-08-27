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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.AppParamFormat;
import com.luretechnologies.client.restlib.service.model.EntityTypeEnum;
import com.luretechnologies.tms.backend.data.entity.AppClient;
import com.luretechnologies.tms.backend.data.entity.AppDefaultParam;
import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.Permission;
import com.luretechnologies.tms.backend.data.entity.Profile;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.service.ApplicationStoreService;
import com.luretechnologies.tms.backend.service.PersonalizationService;
import com.luretechnologies.tms.backend.service.RolesService;
import com.luretechnologies.tms.backend.service.TreeDataNodeService;
import com.luretechnologies.tms.backend.service.UserService;
import com.luretechnologies.tms.ui.MainView;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.luretechnologies.tms.ui.view.applicationstore.ApplicationStoreView;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
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
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.CloseEvent;
import com.vaadin.ui.Notification.CloseListener;
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

	private static final long serialVersionUID = -7795601926506565955L;
	public static final String VIEW_NAME = "personalization";
	private static Tree<TreeNode> nodeTree;
	private static TextField treeNodeSearch, overRideParamSearch;
	private static Devices emptyDevice;
	private static HorizontalSplitPanel splitScreen;
	private static Button createEntity, copyEntity, editEntity, deleteEntity, pasteEntity, addParam, editParam,
			deleteParam, save, cancel, selectProfile, selectFile;
	private static TextField entityName, entityDescription;
	private static TextField entitySerialNum = new TextField("Serial Number");
	private static ComboBox<Devices> deviceDropDown;
	private static ComboBox<AppClient> appDropDown;
	private static ComboBox<Profile> profileDropDown;
	private static ComboBox<String> updateDropDown;
	private static ComboBox<AppDefaultParam> addtnlFilesDropDown;
	private static ComboBox<String> frequencyDropDown;
	private static CheckBox activeCheckbox, activateHeartbeat;
	private static Grid<AppDefaultParam> overRideParamGrid;
	private static TreeNode selectedNode, selectedNodeForCopy;
	private static ComboBox<EntityTypeEnum> entityType;
	private static ComboBox<EntityTypeEnum> entityTypeTree;
	private static FormLayout entityFormLayout;
	private static HorizontalLayout treeButtonLayout;
	private static HorizontalLayout treeSearchLayout;
	private static Tree<TreeNode> modifiedTree = new Tree<>();
	private static TextField parameterName;
	private static TextField parameterDescription;
	private static ComboBox<String> parameterType =  new ComboBox<>("Type");
	private static TextField parameterValue;
	private static Window overRideParamWindow;
	private static AppClient selectedApp;
	private static Profile selectedProfile;
	private boolean deleteProfileValue = false;
	private boolean addProfileValue = false;
	private boolean deleteFileValue = false;
	private boolean addFileValue = false;
	private List<Profile> existingAppProfileEntityList;
	private List<Profile> existingAppProfileList;
	private List<AppDefaultParam> existingProfileFileEntityList;
	private List<AppDefaultParam> existingProfileFileList;
	Logger logger = LoggerFactory.getLogger(PersonalizationView.class);

	@Autowired
	public TreeDataNodeService treeDataNodeService;

	@Autowired
	private PersonalizationService personalizationService;
	
	@Autowired
	private ApplicationStoreService appStoreService;
	
	@Autowired
	private RolesService roleService;
	
	@Autowired
	MainView mainView;
	
	@Autowired
	UserService userService;

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
	  try {
		Page.getCurrent().addBrowserWindowResizeListener(r -> {
			if (r.getWidth() <= 600) {
				treeNodeSearch.setHeight(28, Unit.PIXELS);
				overRideParamSearch.setHeight(28, Unit.PIXELS);
				mainView.getTitle().setValue(userService.getLoggedInUserName());
			} else if (r.getWidth() > 600 && r.getWidth() <= 1000) {
				treeNodeSearch.setHeight(32, Unit.PIXELS);
				overRideParamSearch.setHeight(32, Unit.PIXELS);
				mainView.getTitle().setValue("gemView  "+ userService.getLoggedInUserName());
			} else {
				treeNodeSearch.setHeight(37, Unit.PIXELS);
				overRideParamSearch.setHeight(37, Unit.PIXELS);
				mainView.getTitle().setValue("gemView  "+ userService.getLoggedInUserName());
			}
		});

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
		treeNodeSearch.setHeight(37, Unit.PIXELS);
		treeNodeSearch.setWidth("100%");
		configureTreeNodeSearch();

		Panel panel = getAndLoadPersonlizationPanel();
		treeButtonLayout = new HorizontalLayout();
		treeButtonLayout.setWidth("100%");
		treeSearchLayout = new HorizontalLayout();
		getEntityButtonsList(treeButtonLayout, treeSearchLayout);
		VerticalLayout treeLayoutWithButtons = new VerticalLayout();
		treeLayoutWithButtons.setWidth("100%");
		VerticalLayout treePanelLayout = new VerticalLayout();

		treeLayoutWithButtons.addComponents(treeButtonLayout, treeSearchLayout);
		nodeTree = new Tree<TreeNode>();
			nodeTree.setTreeData(personalizationService.getTreeData());
			modifiedTree.setTreeData(personalizationService.getTreeData());
		nodeTree.setItemIconGenerator(item -> {
			switch (item.getType()) {
			case ENTERPRISE:
				return VaadinIcons.ORIENTATION;
			case ORGANIZATION:
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

		nodeTree.addSelectionListener(selection -> {
			addParam.setEnabled(false);
			deleteParam.setEnabled(false);
			appDropDown.clear();
			if(!selection.getFirstSelectedItem().isPresent()) {
				ClearAllComponents();
				ClearGrid();
			}else {
			
			entityFormLayout.setEnabled(false);
			overRideParamGrid.setEnabled(false);
			selectedNode = selection.getFirstSelectedItem().get();
			appDropDown.setDataProvider(new ListDataProvider<>(personalizationService.getAppListByLoggedUserEntity(selectedNode.getId())));
			if (selectedNode.getType().toString()!=null && !selectedNode.getType().toString().isEmpty()
					&& selectedNode.getLabel()!=null && !selectedNode.getLabel().isEmpty()) {
				entityType.setValue(selectedNode.getType());
				entityName.setValue(selectedNode.getLabel());
				entityDescription.setValue(selectedNode.getDescription());
				activeCheckbox.setValue(selectedNode.isActive());
				if (selectedNode.getType().equals(EntityTypeEnum.TERMINAL)) {
					entitySerialNum.setEnabled(true);
					activateHeartbeat.setEnabled(true);
					frequencyDropDown.setEnabled(true);
					selectedNode.setSerialNum(personalizationService.getTerminalSerialNumberByEntityId(selectedNode.getEntityId()));
					entitySerialNum.setValue(
							personalizationService.getTerminalSerialNumberByEntityId(selectedNode.getEntityId()));
					activateHeartbeat
							.setValue(personalizationService.getHeartbeatByEntityId(selectedNode.getEntityId()));
					frequencyDropDown.setValue(
							personalizationService.getFrequencyByEntityId(selectedNode.getEntityId()).toString());
				}
				else if (selectedNode.getType().equals(EntityTypeEnum.DEVICE)) {
					entitySerialNum.setValue(
							personalizationService.getDeviceSerialNumberByEntityId(selectedNode.getEntityId()));
					activateHeartbeat.setEnabled(false);
					frequencyDropDown.setEnabled(false);
				}else {
					entitySerialNum.clear();
					activateHeartbeat.clear();
					frequencyDropDown.clear();
					entitySerialNum.setEnabled(false);
					activateHeartbeat.setEnabled(false);
					frequencyDropDown.setEnabled(false);
				}

			} else  {
				ClearAllComponents();
				ClearGrid();
				entityType.setValue(selectedNode.getType());
				entityName.setValue(selectedNode.getLabel());
				entityFormLayout.setEnabled(true);
				overRideParamGrid.setEnabled(true);
			}
			}

		});
		
		nodeTree.addContextClickListener(event -> {
			if (!(event instanceof TreeContextClickEvent)) {
				return;
			}
			TreeContextClickEvent treeContextEvent = (TreeContextClickEvent) event;
			TreeNode contextNode = (TreeNode) treeContextEvent.getItem();
			UI.getCurrent().addWindow(openTreeNodeEditWindow(contextNode));

		});
		treeLayoutWithButtons.addComponent(nodeTree);
		treeLayoutWithButtons.setComponentAlignment(nodeTree, Alignment.TOP_LEFT);
		treeLayoutWithButtons.setMargin(true);
		treeLayoutWithButtons.setStyleName("split-Height-ButtonLayout");
		treePanelLayout.addComponent(treeLayoutWithButtons);
		treePanelLayout.setHeight("100%");
		treePanelLayout.setStyleName("split-height");
		splitScreen = new HorizontalSplitPanel();
		splitScreen.setFirstComponent(treePanelLayout);
		splitScreen.setSplitPosition(20);
		splitScreen.setHeight("100%");

		getEntityInformation(splitScreen);
		panel.setContent(splitScreen);

		entityFormLayout.setEnabled(false);
		deviceDropDown.setVisible(true);
		overRideParamGrid.setEnabled(false);
		if (nodeTree.getSelectedItems().isEmpty()) {
			entityFormLayout.setEnabled(false);
			overRideParamGrid.setEnabled(false);
			ClearAllComponents();
			ClearGrid();
		}

		int width = Page.getCurrent().getBrowserWindowWidth();
		if (width <= 600) {
			treeNodeSearch.setHeight(28, Unit.PIXELS);
			overRideParamSearch.setHeight(28, Unit.PIXELS);
			mainView.getTitle().setValue(userService.getLoggedInUserName());
		} else if (width > 600 && width <= 1000) {
			treeNodeSearch.setHeight(32, Unit.PIXELS);
			overRideParamSearch.setHeight(32, Unit.PIXELS);
			mainView.getTitle().setValue("gemView  "+ userService.getLoggedInUserName());
		} else {
			treeNodeSearch.setHeight(37, Unit.PIXELS);
			overRideParamSearch.setHeight(37, Unit.PIXELS);
			mainView.getTitle().setValue("gemView  "+ userService.getLoggedInUserName());
		}
		
		configureTreeNodeSearch();
		
			Permission personalizationPermission = roleService.getLoggedInUserRolePermissions().stream().filter(check -> check.getPageName().equals("PERSONALIZATION")).findFirst().get();
			createEntity.setEnabled(personalizationPermission.getAdd());
			copyEntity.setEnabled(personalizationPermission.getEdit());
			editEntity.setEnabled(personalizationPermission.getEdit());
			deleteEntity.setEnabled(personalizationPermission.getDelete());
			save.setEnabled(personalizationPermission.getAdd() || personalizationPermission.getEdit());
			cancel.setEnabled(personalizationPermission.getAdd() || personalizationPermission.getEdit());
			
	  }catch(Exception ex){
		  personalizationService.logPersonlizationStoreScreenErrors(ex);
	  }
	}

	private void configureTreeNodeSearch() {
		treeNodeSearch.addValueChangeListener(changed -> {
			String valueInLower = changed.getValue().toLowerCase();
				if(!valueInLower.isEmpty() && valueInLower!=null) {
					nodeTree.setTreeData(treeDataNodeService.searchTreeData(valueInLower));
				}else {
					nodeTree.setTreeData(treeDataNodeService.getTreeData());
				}

		});

		treeNodeSearch.addShortcutListener(new ShortcutListener("Clear", KeyCode.ESCAPE, null) {

			@Override
			public void handleAction(Object sender, Object target) {
				if (target == treeNodeSearch) {
					treeNodeSearch.clear();

				}
			}
		});
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

	private void getEntityButtonsList(HorizontalLayout treeButtonLayout, HorizontalLayout treeSearchLayout) {
		createEntity = new Button(VaadinIcons.FOLDER_ADD, click -> {
			Window createProfileWindow = openEntityWindow();
			if (nodeTree.getSelectedItems().size() == 0) {
				Notification.show(NotificationUtil.PERSONALIZATION_CREATE_ENTITY, Notification.Type.ERROR_MESSAGE);
			} else {
				if (createProfileWindow.getParent() == null)
					UI.getCurrent().addWindow(createProfileWindow);
			}

		});
		createEntity.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		createEntity.setDescription("Create New Entity", ContentMode.HTML);

		copyEntity = new Button(VaadinIcons.COPY, click -> {
			//  Copy new entity
			if (nodeTree.getSelectionModel().getFirstSelectedItem().isPresent()) {
				selectedNodeForCopy = nodeTree.getSelectionModel().getFirstSelectedItem().get();
				if (selectedNodeForCopy.getType().equals(EntityTypeEnum.ENTERPRISE)) {
					Notification.show(NotificationUtil.PERSONALIZATION_ENTERPRISE_COPY, Type.ERROR_MESSAGE);
					selectedNodeForCopy = null;
				} else {
					pasteEntity.setEnabled(true);
				}
			} else {
				Notification.show(NotificationUtil.PERSONALIZATION_COPTY_ENTITY, Notification.Type.ERROR_MESSAGE);
			}
		});
		copyEntity.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		copyEntity.setDescription("Copy Entities", ContentMode.HTML);

		pasteEntity = new Button(VaadinIcons.PASTE, click -> {
			//  Paste new entity
			if (nodeTree.getSelectionModel().getFirstSelectedItem().isPresent()) {
				if (selectedNodeForCopy != null) {
					TreeNode toPasteNode = nodeTree.getSelectionModel().getFirstSelectedItem().get();
					switch (toPasteNode.getType()) {

					case ENTERPRISE:
						if(selectedNodeForCopy.getType().toString().equals("ORGANIZATION")) {
							
								treeDataNodeService.pasteTreeNode(selectedNodeForCopy, toPasteNode);
								nodeTree.setTreeData(personalizationService.getTreeData());
						} else {
							Notification.show("Cannot Copy "+selectedNodeForCopy.getType().toString()+" entity to this level", Type.ERROR_MESSAGE);
						}
						break;
					case ORGANIZATION:
						if(selectedNodeForCopy.getType().toString().equals("ORGANIZATION") || 
								selectedNodeForCopy.getType().toString().equals("REGION")) {
								treeDataNodeService.pasteTreeNode(selectedNodeForCopy, toPasteNode);
								nodeTree.setTreeData(personalizationService.getTreeData());
							} else {
							Notification.show("Cannot Copy "+selectedNodeForCopy.getType().toString()+" entity to this level", Type.ERROR_MESSAGE);
						}
						break;
					case REGION:
						if(selectedNodeForCopy.getType().toString().equals("MERCHANT")) {
								treeDataNodeService.pasteTreeNode(selectedNodeForCopy, toPasteNode);
								nodeTree.setTreeData(personalizationService.getTreeData());
						} else {
							Notification.show("Cannot Copy "+selectedNodeForCopy.getType().toString()+" entity to this level", Type.ERROR_MESSAGE);
						}
						break;
					case MERCHANT:
						if(selectedNodeForCopy.getType().toString().equals("TERMINAL")) {
								treeDataNodeService.pasteTreeNode(selectedNodeForCopy, toPasteNode);
								nodeTree.setTreeData(personalizationService.getTreeData());
						} else {
							Notification.show("Cannot Copy "+selectedNodeForCopy.getType().toString()+" entity to this level", Type.ERROR_MESSAGE);
						}
						break;
					case TERMINAL:
						if(selectedNodeForCopy.getType().toString().equals("DEVICE")) {
								treeDataNodeService.pasteTreeNode(selectedNodeForCopy, toPasteNode);
								nodeTree.setTreeData(personalizationService.getTreeData());
						} else {
							Notification.show("Cannot Copy "+selectedNodeForCopy.getType().toString()+" entity to this level", Type.ERROR_MESSAGE);
						}
						break;
					case DEVICE:
						Notification.show("Cannot Copy "+selectedNodeForCopy.getType().toString()+" entity to this level", Type.ERROR_MESSAGE);
						
					default:
						break;
					}
					}
					pasteEntity.setEnabled(false);
					modifiedTree.setTreeData(nodeTree.getTreeData());
				}
		});
		pasteEntity.setEnabled(false);
		pasteEntity.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		pasteEntity.setDescription("Paste Entities", ContentMode.HTML);

		editEntity = new Button(VaadinIcons.EDIT, click -> {
			if (nodeTree.getSelectedItems().size() == 0) {
				Notification.show(NotificationUtil.PERSONALIZATION_EDIT, Notification.Type.ERROR_MESSAGE);
			} else {
				entityFormLayout.setEnabled(true);
				overRideParamGrid.setEnabled(true);
			}
		});
		editEntity.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		editEntity.setDescription("Edit Entities", ContentMode.HTML);

		deleteEntity = new Button(VaadinIcons.TRASH, click -> {
			if (nodeTree.getSelectedItems().size() == 0) {
				Notification.show(NotificationUtil.PERSONALIZATION_DELETE, Notification.Type.ERROR_MESSAGE);
			} else {
				confirmDeleteEntity();
			}
		});
		deleteEntity.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		deleteEntity.setDescription("Delete Entities", ContentMode.HTML);

		treeButtonLayout.addComponents(treeNodeSearch);
		treeSearchLayout.addComponents(createEntity, copyEntity, pasteEntity, editEntity, deleteEntity);
	}

	private void getEntityInformation(HorizontalSplitPanel splitScreen) {
		VerticalLayout entityInformationLayout = new VerticalLayout();
		entityInformationLayout.addStyleName("personlization-verticalFormLayout");
		HorizontalLayout saveCancelEntityInfoLabelLayout = new HorizontalLayout();
		HorizontalLayout saveCancelButtonLayout = new HorizontalLayout();
		HorizontalLayout entityLabelLayout = new HorizontalLayout();
		entityLabelLayout.setWidth("100%");
		saveCancelEntityInfoLabelLayout.setSizeFull();
		entityFormLayout = new FormLayout();
		entityFormLayout.setWidth("100%");
		entityFormLayout.addStyleNames("personlization-formLayout", "system-LabelAlignment");

		Label entityInformationLabel = new Label("Entity Information");
		entityInformationLabel.addStyleName("label-style");
		entityInformationLabel.addStyleNames(ValoTheme.LABEL_BOLD, ValoTheme.LABEL_H3);
		entityLabelLayout.addComponent(entityInformationLabel);

		cancel = new Button("Cancel", click -> {
			ClearAllComponents();
			ClearGrid();
			entityFormLayout.setEnabled(false);
			overRideParamGrid.setEnabled(false);
			if (nodeTree.getSelectedItems().size() > 0) {
				nodeTree.deselect(nodeTree.getSelectedItems().iterator().next());
			} else {

			}
		});
		cancel.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		cancel.addStyleName("v-button-customstyle");
		cancel.setResponsive(true);
		saveCancelButtonLayout.addComponent(cancel);

		save = new Button("Save", click -> {
			TreeNode node;
			if (nodeTree.getSelectedItems().size() > 0) {
				node = nodeTree.getSelectedItems().iterator().next();
			} else {
				node = new TreeNode();
			}

			node.setType(entityType.getValue());
			node.setLabel(entityName.getValue());
			if (entityType.getValue()!=null && entityName.getValue()!=null && entityName.getValue()!="") {
				node.setHeartBeat(activateHeartbeat.getValue());
				node.setFrequency(frequencyDropDown.getValue());
				node.setApp(appDropDown.getValue());
				node.setAdditionaFile(addtnlFilesDropDown.getValue());
				node.setProfile(profileDropDown.getValue());
			} else {
				Notification.show(NotificationUtil.SAVE, Type.ERROR_MESSAGE);
			}

			node.setDescription(entityDescription.getValue());
			node.setActive(activeCheckbox.getValue());
			node.setSerialNum(entitySerialNum.getValue());
			node.setOverRideParamList(
					overRideParamGrid.getDataProvider().fetch(new Query<>()).collect(Collectors.toList()));
			overRideParamGrid.setDataProvider(new ListDataProvider<>(node.getOverRideParamList()));
			overRideParamGrid.getDataProvider().refreshAll();
			overRideParamGrid.deselectAll();
			
			// Calling the service
			personalizationService.updateEntity(node);
			entityFormLayout.setEnabled(false);
			overRideParamGrid.setEnabled(false);

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

		getDeviceDropdown(entityFormLayout);

		getEntityDescription(entityFormLayout);

		getEntityActive(entityFormLayout);

		getEntitySerialNum(entityFormLayout);

		getEntityHeartBeat(entityFormLayout);

		getEntityFrequency(entityFormLayout);

		getApplicationSelection(entityFormLayout);

		getScheduleUpdate(entityFormLayout);
	}

	private void getEntityType(FormLayout entityFormLayout) {
		entityType = new ComboBox<EntityTypeEnum>();
		entityType.setCaption("Entity Type");
		entityType.setDataProvider(new ListDataProvider<>(
				Arrays.asList(EntityTypeEnum.ENTERPRISE, EntityTypeEnum.REGION, EntityTypeEnum.MERCHANT,
						EntityTypeEnum.TERMINAL, EntityTypeEnum.DEVICE, EntityTypeEnum.ORGANIZATION)));
		entityType.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size",
				"personlization-formAlignment", "small");

		entityFormLayout.addComponent(entityType);
	}

	private void getEntityName(FormLayout entityFormLayout) {

		entityName = new TextField("Name");
		entityName.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		entityName.setWidth("48%");
		entityName.setStyleName("role-textbox");
		entityName.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS, "personlization-formAlignment",
				"v-textfield-lineHeight");
		entityFormLayout.addComponent(entityName);
	}

	private void getDeviceDropdown(FormLayout entityFormLayout) {

		deviceDropDown = new ComboBox<Devices>();
		deviceDropDown.setCaption("Devices");
		deviceDropDown.setPlaceholder("Select Device");
		deviceDropDown.setDataProvider(personalizationService.getAllDevices());
		deviceDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size",
				"personlization-formAlignment", "small");
	}

	private void getEntityDescription(FormLayout entityFormLayout) {

		entityDescription = new TextField("Description");
		entityDescription.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		entityDescription.setWidth("48%");
		entityDescription.setStyleName("role-textbox");
		entityDescription.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS, "personlization-formAlignment",
				"v-textfield-lineHeight");

		entityFormLayout.addComponent(entityDescription);
	}

	private void getEntityActive(FormLayout entityFormLayout) {

		HorizontalLayout entityActiveLayout = new HorizontalLayout();
		Label active = new Label("Active");
		active.setStyleName("role-activeLable");
		active.addStyleNames("v-textfield-font", "role-activeLabel");
		activeCheckbox = new CheckBox();
		activeCheckbox.setCaption("Entity Active");
		activeCheckbox.addStyleNames("v-textfield-font", "personlization-activeCheckbox");

		entityActiveLayout.addComponents(active, activeCheckbox);
		entityActiveLayout.setStyleName("role-activeLable");
		entityActiveLayout.addStyleNames("assetAlert-activeCheckBox", "personlization-formAlignment",
				"personlization-activeLayout");

		entityFormLayout.addComponent(entityActiveLayout);
	}

	private void getEntitySerialNum(FormLayout entityFormLayout) {
		entitySerialNum.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		entitySerialNum.setWidth("48%");
		entitySerialNum.setStyleName("role-textbox");
		entitySerialNum.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS, "v-textfield-font",
				"personlization-formAlignment", "v-textfield-lineHeight");

		entityFormLayout.addComponent(entitySerialNum);
	}

	private void getEntityHeartBeat(FormLayout entityFormLayout) {

		HorizontalLayout entityHeartbeatLayout = new HorizontalLayout();
		Label heartbeat = new Label("Heartbeat");
		heartbeat.setStyleName("role-activeLable");
		heartbeat.addStyleNames("v-textfield-font", "role-activeLabel");
		activateHeartbeat = new CheckBox();
		activateHeartbeat.setCaption("Entity Active");
		activateHeartbeat.addStyleNames("v-textfield-font", "personlization-heartbeatCheckbox");

		entityHeartbeatLayout.addComponents(heartbeat, activateHeartbeat);
		entityHeartbeatLayout.setStyleName("role-activeLable");
		entityHeartbeatLayout.addStyleNames("assetAlert-activeCheckBox", "personlization-formAlignment",
				"personlization-heartbeatLayout");

		entityFormLayout.addComponent(entityHeartbeatLayout);
	}

	private void getEntityFrequency(FormLayout entityFormLayout) {

		frequencyDropDown = new ComboBox<>();
		frequencyDropDown.setDataProvider(new ListDataProvider<>(
				Arrays.asList("120 Seconds", "90 Seconds", " 30 Seconds", "Daily", "Monthly", "Never")));
		frequencyDropDown.setCaption("Frequency");
		frequencyDropDown.setPlaceholder("Frequency");
		frequencyDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size",
				"personlization-formAlignment", "small");

		entityFormLayout.addComponent(frequencyDropDown);
	}

	private void getApplicationSelection(FormLayout entityFormLayout) {

		HorizontalLayout entityApplicationSelection = new HorizontalLayout();
		entityApplicationSelection.addStyleNames("personlization-formAlignment",
				"personlization-applicationSelectionLayout");
		entityApplicationSelection.setCaption("Application Selection");
		appDropDown = new ComboBox<AppClient>();
		appDropDown.setCaption("");
		appDropDown.setPlaceholder("Application");
		appDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "small");
		addtnlFilesDropDown = new ComboBox<>();
		addtnlFilesDropDown.setCaption("");
		addtnlFilesDropDown.setPlaceholder("Additional Files");
		addtnlFilesDropDown.setDescription("Select Files");
		addtnlFilesDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "small");

		profileDropDown = new ComboBox<Profile>();
		profileDropDown.setCaption("");
		profileDropDown.setPlaceholder("Default Profile");
		profileDropDown.setDescription("Select Profile");
		profileDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "small");
		
		selectProfile = new Button("", VaadinIcons.TWIN_COL_SELECT);
		selectProfile.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY, "personlization-plusButtons");
		selectProfile.setDescription("Select Profiles");
		selectFile = new Button("", VaadinIcons.TWIN_COL_SELECT);
		selectFile.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY, "personlization-plusButtons");
		selectFile.setDescription("Add Files");
		
		selectProfile.addClickListener(click ->{
			if(selectedApp != null)
				selectedProfile=null;
				UI.getCurrent().addWindow(openProfileTwinListToAddWindow());
				profileDropDown.clear();
		});
		
		selectFile.addClickListener(click ->{
			if(selectedProfile!=null) {
				UI.getCurrent().addWindow(openProfileTwinListToAddWindow());
				addtnlFilesDropDown.clear();
			}
		});
		
		appDropDown.addSelectionListener(selected -> {
			
			selectedApp = selected.getValue();
			addtnlFilesDropDown.clear();
			profileDropDown.clear();
			selectedProfile=null;
				profileDropDown.setDataProvider(personalizationService.getProfileForEntityDataProvider(selectedApp.getId(),selectedNode.getId()));
				parameterType.setDataProvider(new ListDataProvider<>(appStoreService.getAppParamTypeList(selectedApp.getId())));
			
		});
		
		profileDropDown.addSelectionListener(listener->{
			if(profileDropDown.getSelectedItem().isPresent()) {
			selectedProfile = profileDropDown.getSelectedItem().get();
			addParam.setEnabled(true);
			deleteParam.setEnabled(true);
			List<AppDefaultParam> fileListWithEntityForDropDown = personalizationService.getFileListWithEntity(selectedProfile.getId(), selectedNode.getId());
			DataProvider dataFromEntityForDropDown = new ListDataProvider<>(fileListWithEntityForDropDown);
			addtnlFilesDropDown.clear();
			addtnlFilesDropDown.setDataProvider(dataFromEntityForDropDown);
			overRideParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(personalizationService.getAppDefaultParamListWithEntity(selectedProfile.getId(),selectedNode.getId())));
			}
		});
		entityApplicationSelection.addComponents(appDropDown, selectProfile, profileDropDown, selectFile, addtnlFilesDropDown);
		entityFormLayout.addComponent(entityApplicationSelection);
	}
	
	private Window openProfileTwinListToAddWindow() {
		
		if(selectedProfile!=null) {
			Window openProfileListWindow = new Window("File List Window");
			
			ListSelect<AppDefaultParam> optionListFromProfile = new ListSelect<>();
			optionListFromProfile.setRows(8);
			optionListFromProfile.setResponsive(true);
			optionListFromProfile.setWidth("280px");
			optionListFromProfile.setCaption("Files of Selected Profile");
			List<AppDefaultParam> fileListWithOutEntity = personalizationService.getFileListWithOutEntity(selectedProfile.getId(), selectedNode.getId());
			DataProvider data = new ListDataProvider<>(fileListWithOutEntity);
			optionListFromProfile.setDataProvider(data);
			
			ListSelect<AppDefaultParam> optionListFromEntity = new ListSelect<>();
			optionListFromEntity.setRows(8);
			optionListFromEntity.setResponsive(true);
			optionListFromEntity.setWidth("280px");
			optionListFromEntity.setCaption("Files of Selected Entity");
			List<AppDefaultParam> fileListWithEntity = personalizationService.getFileListWithEntity(selectedProfile.getId(), selectedNode.getId());
			DataProvider dataFromEntity = new ListDataProvider<>(fileListWithEntity);
			optionListFromEntity.setDataProvider(dataFromEntity);
			Button rightArrow = new Button("",click ->  {
				if (optionListFromProfile.getSelectedItems().size() > 0) {
					addFileValue=true;
					existingProfileFileList = optionListFromProfile.getSelectedItems().stream().collect(Collectors.toList());
					fileListWithEntity.addAll(existingProfileFileList);
					optionListFromEntity.setDataProvider(new ListDataProvider<>(fileListWithEntity));
					fileListWithOutEntity.removeAll(existingProfileFileList);
					DataProvider dataNew = new ListDataProvider<>(fileListWithOutEntity);
					optionListFromProfile.setDataProvider(dataNew);
				}
			});
			rightArrow.setIcon(VaadinIcons.ANGLE_RIGHT);
			rightArrow.setEnabled(false);
			Button leftArrow = new Button("",click-> {
				if(optionListFromEntity.getSelectedItems().size() >0) {
					deleteFileValue=true;
					existingProfileFileEntityList = optionListFromEntity.getSelectedItems().stream().collect(Collectors.toList());
					fileListWithOutEntity.addAll(existingProfileFileEntityList);
					optionListFromProfile.setDataProvider(new ListDataProvider<>(fileListWithOutEntity));
					fileListWithEntity.removeAll(existingProfileFileEntityList);
					optionListFromEntity.setDataProvider(new ListDataProvider<>(fileListWithEntity));
				}
			});
			leftArrow.setIcon(VaadinIcons.ANGLE_LEFT);
			leftArrow.setEnabled(false);
			VerticalLayout arrowCol = new VerticalLayout(rightArrow,leftArrow);
			arrowCol.addStyleNames("personlization-leftAndRightArrowButtons");
			HorizontalLayout twinColLayout = new HorizontalLayout(optionListFromProfile,arrowCol,optionListFromEntity);
			
			optionListFromEntity.addSelectionListener(listener->{
				leftArrow.setEnabled(true);
				rightArrow.setEnabled(false);
			});
			
			optionListFromProfile.addSelectionListener(listener->{
				leftArrow.setEnabled(false);
				rightArrow.setEnabled(true);
			});
			
			Button saveNode = new Button("Save", click -> {
				if(!addFileValue &&! deleteFileValue){
					Notification notification =  Notification.show(NotificationUtil.PERSONALIZATION_DUMMY_SAVE, Type.ERROR_MESSAGE);
					notification.setPosition(Position.TOP_CENTER);
				}
				
				if(deleteFileValue) {
					personalizationService.deleteFileForEntity(selectedProfile.getId(), existingProfileFileEntityList, selectedNode.getId());
					List<AppDefaultParam> fileListWithEntityForDropDown = personalizationService.getFileListWithEntity(selectedProfile.getId(), selectedNode.getId());
					DataProvider dataFromEntityForDropDown = new ListDataProvider<>(fileListWithEntityForDropDown);
					addtnlFilesDropDown.setDataProvider(dataFromEntityForDropDown);
					deleteFileValue=false;
					openProfileListWindow.close();
				} 
				
				if(addProfileValue  && optionListFromEntity.getSelectedItems().isEmpty()) {
					Notification notification =  Notification.show(NotificationUtil.PERSONALIZATION_ADDING_FILES, Type.ERROR_MESSAGE);
					notification.setPosition(Position.TOP_CENTER);
				}else if(addFileValue){
					List<AppDefaultParam> fileListForUpdate = optionListFromEntity.getValue().stream().collect(Collectors.toList());
					personalizationService.saveFileForEntity(selectedProfile.getId(), fileListForUpdate,selectedNode.getId());
					List<AppDefaultParam> fileListWithEntityForDropDown = personalizationService.getFileListWithEntity(selectedProfile.getId(), selectedNode.getId());
					DataProvider dataFromEntityForDropDown = new ListDataProvider<>(fileListWithEntityForDropDown);
					addtnlFilesDropDown.setDataProvider(dataFromEntityForDropDown);
					addFileValue=false;
					openProfileListWindow.close();
				}
			});

			saveNode.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");

			Button cancelNode = new Button("Cancel", click -> {
				openProfileListWindow.close();
			});
			cancelNode.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
			HorizontalLayout buttonLayout = new HorizontalLayout(saveNode, cancelNode);
			FormLayout profileFormLayout = new FormLayout(twinColLayout);

			// Window Setup
			openProfileListWindow.setContent(new VerticalLayout(profileFormLayout, buttonLayout));
			openProfileListWindow.center();
			openProfileListWindow.setModal(true);
			openProfileListWindow.setClosable(true);
			openProfileListWindow.setWidth(37, Unit.PERCENTAGE);
			return openProfileListWindow;
			
		}else {
		Window openProfileListWindow = new Window("Profile List Window");
		
		ListSelect<Profile> optionListFromApp = new ListSelect<>();
		optionListFromApp.setRows(8);
		optionListFromApp.setResponsive(true);
		optionListFromApp.setWidth("280px");
		optionListFromApp.setCaption("Profiles of Selected App");
		List<Profile> profileListWithOutEntity = personalizationService.getProfileListWithOutEntity(selectedApp.getId(), selectedNode.getId());
		DataProvider data = new ListDataProvider<>(profileListWithOutEntity);
		optionListFromApp.setDataProvider(data);
		
		ListSelect<Profile> optionListFromEntity = new ListSelect<>();
		optionListFromEntity.setRows(8);
		optionListFromEntity.setResponsive(true);
		optionListFromEntity.setWidth("280px");
		optionListFromEntity.setCaption("Profiles of Selected Entity");
		List<Profile> profileListWithEntity = personalizationService.getProfileListForEntity(selectedApp.getId(), selectedNode.getId());
		DataProvider dataFromEntity = new ListDataProvider<>(profileListWithEntity);
		optionListFromEntity.setDataProvider(dataFromEntity);
		Button rightArrow = new Button("",click ->  {
			if (optionListFromApp.getSelectedItems().size() > 0) {
				addProfileValue=true;
				existingAppProfileList = optionListFromApp.getSelectedItems().stream().collect(Collectors.toList());
				profileListWithEntity.addAll(existingAppProfileList);
				optionListFromEntity.setDataProvider(new ListDataProvider<>(profileListWithEntity));
				profileListWithOutEntity.removeAll(existingAppProfileList);
				DataProvider dataNew = new ListDataProvider<>(profileListWithOutEntity);
				optionListFromApp.setDataProvider(dataNew);
			}
		});
		rightArrow.setIcon(VaadinIcons.ANGLE_RIGHT);
		rightArrow.setEnabled(false);
		Button leftArrow = new Button("",click-> {
			if(optionListFromEntity.getSelectedItems().size() >0) {
				deleteProfileValue=true;
				existingAppProfileEntityList = optionListFromEntity.getSelectedItems().stream().collect(Collectors.toList());
				profileListWithOutEntity.addAll(existingAppProfileEntityList);
				optionListFromApp.setDataProvider(new ListDataProvider<>(profileListWithOutEntity));
				profileListWithEntity.removeAll(existingAppProfileEntityList);
				optionListFromEntity.setDataProvider(new ListDataProvider<>(profileListWithEntity));
			}
		});
		leftArrow.setEnabled(false);
		leftArrow.setIcon(VaadinIcons.ANGLE_LEFT);
		VerticalLayout arrowCol = new VerticalLayout(rightArrow,leftArrow);
		arrowCol.addStyleNames("personlization-leftAndRightArrowButtons");
		HorizontalLayout twinColLayout = new HorizontalLayout(optionListFromApp,arrowCol,optionListFromEntity);
		
		optionListFromEntity.addSelectionListener(listener->{
			leftArrow.setEnabled(true);
			rightArrow.setEnabled(false);
		});
		
		optionListFromApp.addSelectionListener(listener->{
			leftArrow.setEnabled(false);
			rightArrow.setEnabled(true);
		});
		
		Button saveNode = new Button("Save", click -> {
			if(!addProfileValue &&! deleteProfileValue){
				Notification notification =  Notification.show(NotificationUtil.PERSONALIZATION_DUMMY_SAVE, Type.ERROR_MESSAGE);
				notification.setPosition(Position.TOP_CENTER);
			}
			
			if(deleteProfileValue) {
				personalizationService.deleteProfileForEntity(existingAppProfileEntityList, selectedNode.getId());
				List<Profile> profileListWithEntityForDropDown = personalizationService.getProfileListForEntity(selectedApp.getId(), selectedNode.getId());
				DataProvider dataFromEntityForDropDown = new ListDataProvider<>(profileListWithEntityForDropDown);
				profileDropDown.setDataProvider(dataFromEntityForDropDown);
				deleteProfileValue=false;
				openProfileListWindow.close();
			} 
			
			if(addProfileValue && optionListFromEntity.getSelectedItems().isEmpty()) {
				Notification notification =  Notification.show(NotificationUtil.PERSONALIZATION_ADDING_PROFILES, Type.ERROR_MESSAGE);
				notification.setPosition(Position.TOP_CENTER);
				
			}else if(addProfileValue){
				List<Profile> profileListForUpdate = optionListFromEntity.getValue().stream().collect(Collectors.toList());
				personalizationService.saveProfileForEntity(profileListForUpdate,selectedNode.getId(), selectedApp.getId());
				List<Profile> profileListWithEntityForDropDown = personalizationService.getProfileListForEntity(selectedApp.getId(), selectedNode.getId());
				DataProvider dataFromEntityForDropDown = new ListDataProvider<>(profileListWithEntityForDropDown);
				profileDropDown.setDataProvider(dataFromEntityForDropDown);
				addProfileValue=false;
				openProfileListWindow.close();
			}
		});

		saveNode.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");

		Button cancelNode = new Button("Cancel", click -> {
			openProfileListWindow.close();
		});
		cancelNode.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		HorizontalLayout buttonLayout = new HorizontalLayout(saveNode, cancelNode);
		FormLayout profileFormLayout = new FormLayout(twinColLayout);

		// Window Setup
		openProfileListWindow.setContent(new VerticalLayout(profileFormLayout, buttonLayout));
		openProfileListWindow.center();
		openProfileListWindow.setModal(true);
		openProfileListWindow.setClosable(true);
		openProfileListWindow.setWidth(37, Unit.PERCENTAGE);
		return openProfileListWindow;
	}
}
	
	private Window openProfileParamGridAddWindow() {
		Window openProfileParamGridWindow =  new Window("App Profile Param Grid");
		
		Grid<AppDefaultParam> profileParamGrid = new Grid<>(AppDefaultParam.class);
			profileParamGrid.setDataProvider(new ListDataProvider<>(personalizationService.getAppDefaultParamListWithoutEntity(selectedProfile.getId(), selectedNode.getId())));
		profileParamGrid.setSelectionMode(SelectionMode.MULTI);
		profileParamGrid.setColumns("parameter", "description", "type", "value");
		profileParamGrid.getColumn("parameter").setEditorComponent(new TextField());
		profileParamGrid.getColumn("description").setEditorComponent(new TextField());
		profileParamGrid.getColumn("value").setEditorComponent(new TextField());
		profileParamGrid.getColumn("type").setEditorComponent(parameterType);
		Button saveNode = new Button("Save", click -> {
			if (profileParamGrid.getSelectedItems().size() > 0) {
					Set<AppDefaultParam> selectedParamSet = profileParamGrid.getSelectedItems();
					personalizationService.addProfileParam(selectedProfile.getId(), selectedNode.getId(), selectedParamSet);
					overRideParamGrid.setDataProvider(new ListDataProvider<>(personalizationService.getAppDefaultParamListWithEntity(selectedProfile.getId(), selectedNode.getId())));
				openProfileParamGridWindow.close();
			} else {
				Notification notification = Notification.show(NotificationUtil.PERSONALIZATION_ADDING_PARAMS,
						Type.ERROR_MESSAGE);
				
			}
		});

		saveNode.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");

		Button cancelNode = new Button("Cancel", click -> {
			openProfileParamGridWindow.close();
		});
		cancelNode.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		HorizontalLayout buttonLayout = new HorizontalLayout(saveNode, cancelNode);
		buttonLayout.addStyleNames("personlization-horizontalLayout");
		FormLayout profileFormLayout = new FormLayout(profileParamGrid);

		// Window Setup
		VerticalLayout gridLayout = new VerticalLayout();
		gridLayout.addStyleNames("personlization-verticalFormLayout");
		gridLayout.addComponents(profileFormLayout, buttonLayout);
		openProfileParamGridWindow.setContent(gridLayout);
		openProfileParamGridWindow.center();
		openProfileParamGridWindow.setModal(true);
		openProfileParamGridWindow.setClosable(true);
		openProfileParamGridWindow.setWidth(28, Unit.PERCENTAGE);
		return openProfileParamGridWindow;
	}


	private void getScheduleUpdate(FormLayout entityFormLayout) {

		updateDropDown = new ComboBox<>();
		updateDropDown.setDataProvider(
				new ListDataProvider<>(Arrays.asList("Update 1", "Update 2", "Update 3", "Update 4", "Update 5")));
		updateDropDown.setCaption("Schedule Update");
		updateDropDown.setPlaceholder("Update");
		updateDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size",
				"personlization-formAlignment", "small");

		entityFormLayout.addComponent(updateDropDown);
	}

	private void getOverRideParamGrid(VerticalLayout entityInformationLayout) {
		HorizontalLayout overRideParamLabelLayout = new HorizontalLayout();
		overRideParamLabelLayout.addStyleName("personlization-overRideParamLabelLayout");
		HorizontalLayout overRideParamSearchButtonLayout = new HorizontalLayout();
		overRideParamSearchButtonLayout.setWidth("100%");
		overRideParamSearchButtonLayout.addStyleName("personlization-paramButtonLayout");

		HorizontalLayout overRideParamSearchLayout = new HorizontalLayout();
		overRideParamSearchLayout.setWidth("100%");
		HorizontalLayout overRideParamButtonLayout = new HorizontalLayout();

		Label overRideParamLabel = new Label("Override Parameters");
		overRideParamLabel.addStyleName("label-style");
		overRideParamLabel.addStyleNames(ValoTheme.LABEL_BOLD, ValoTheme.LABEL_H3);
		overRideParamLabelLayout.addComponent(overRideParamLabel);

		overRideParamSearch = new TextField();
		overRideParamSearch.setWidth("100%");
		overRideParamSearch.setHeight(37, Unit.PIXELS);
		overRideParamSearch.setIcon(VaadinIcons.SEARCH);
		overRideParamSearch.setStyleName("small inline-icon search");
		overRideParamSearch.addStyleName("v-textfield-font");
		overRideParamSearch.setPlaceholder("Search");
		overRideParamSearch.setVisible(true);
		overRideParamSearchLayout.addComponent(overRideParamSearch);
		
		addParam = new Button("", VaadinIcons.GRID);
		addParam.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY);
		addParam.setDescription("Select Profiles");
		addParam.setEnabled(false);
		
		addParam.addClickListener(click ->{
			if(selectedProfile != null)
				UI.getCurrent().addWindow(openProfileParamGridAddWindow());
		});

		deleteParam = new Button(VaadinIcons.TRASH, click -> {
			if (nodeTree.getSelectedItems().size() == 0) {
				Notification.show(NotificationUtil.PERSONALIZATION_DELETE, Notification.Type.ERROR_MESSAGE);
			} else if (overRideParamGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.PERSONALIZATION_PARAM_DELETE, Notification.Type.ERROR_MESSAGE);
			} else {
				confirmDeleteOverRideParam(overRideParamGrid, overRideParamSearch);
			}
		});
		deleteParam.setEnabled(false);
		deleteParam.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		deleteParam.setDescription("Delete Params", ContentMode.HTML);
		overRideParamButtonLayout.addComponents(addParam, deleteParam);

		overRideParamSearchButtonLayout.addComponents(overRideParamSearchLayout, overRideParamButtonLayout);

		overRideParamGrid = new Grid<>(AppDefaultParam.class);
		overRideParamGrid.addStyleNames("personlization-gridHeight");
		overRideParamGrid.setWidth("100%");
		overRideParamGrid.setHeightByRows(7);
		overRideParamGrid.setResponsive(true);
		overRideParamGrid.setSelectionMode(SelectionMode.SINGLE);
		overRideParamGrid.setColumns("parameter", "description", "type", "value");
		overRideParamGrid.getColumn("parameter").setEditorComponent(new TextField());
		overRideParamGrid.getColumn("description").setEditorComponent(new TextField());
		overRideParamGrid.getColumn("value").setEditorComponent(new TextField());
		overRideParamGrid.getColumn("type").setEditorComponent(parameterType);
		overRideParamGrid.getEditor().setEnabled(true).addSaveListener(save -> {
			if(selectedProfile.getId()!=null) {
			AppDefaultParam param = save.getBean();
				AppParamFormat appParamFormat = appStoreService.getAppParamFormatByType(parameterType.getValue());
				personalizationService.updateParamOfEntity(selectedProfile.getId(), selectedNode.getId(), param, selectedApp.getId());
				overRideParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(personalizationService.getAppDefaultParamListWithEntity(selectedProfile.getId(),selectedNode.getId())));
			}
		});
		
		overRideParamGrid.addItemClickListener(event->{
			if(overRideParamGrid.getEditor().isEnabled()) {
				overRideParamGrid.getColumn("parameter").setEditable(false);
				overRideParamGrid.getColumn("description").setEditable(false);
				parameterType.setEnabled(false);
			}
		});
		

		overRideParamSearch.addValueChangeListener(valueChange -> {
			String valueInLower = valueChange.getValue().toLowerCase();
			ListDataProvider<AppDefaultParam> paramDataProvider = (ListDataProvider<AppDefaultParam>) overRideParamGrid
					.getDataProvider();
			paramDataProvider.setFilter(filter -> {
				String parameterInLower = filter.getParameter().toLowerCase();
				String descriptionInLower = filter.getDescription().toLowerCase();
				String typeLower = filter.getType().toLowerCase();
				String value = filter.getValue().toLowerCase();
				return descriptionInLower.equals(valueInLower) || parameterInLower.contains(valueInLower)
						|| typeLower.contains(valueInLower) || value.contains(valueInLower);
			});
		});

		entityInformationLayout.addComponents(overRideParamLabelLayout, overRideParamSearchButtonLayout,
				overRideParamGrid);
	}

	private Window openTreeNodeEditWindow(TreeNode nodeToEdit) {
		Window nodeEditWindow = new Window("Edit Node");
		String label = nodeToEdit.getLabel();
		TextField nodeName = new TextField("Name", nodeToEdit.getLabel());
		nodeName.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS, "role-textbox", "v-textfield-font", "v-grid-cell");
		Button saveNode = new Button("Save", click -> {
			if (StringUtils.isNotEmpty(nodeName.getValue())) {
				nodeToEdit.setLabel(nodeName.getValue());
				entityName.setValue(nodeName.getValue());
				nodeTree.getDataProvider().refreshAll();
				nodeEditWindow.close();
			} else {
				Notification notification = Notification.show(NotificationUtil.PERSONALIZATION_EDIT_ENTITY_NAME,
						Type.ERROR_MESSAGE);
				notification.addCloseListener(new CloseListener() {

					@Override
					public void notificationClose(CloseEvent e) {
						nodeName.setValue(label);

					}
				});
			}
		});

		saveNode.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");

		Button cancelNode = new Button("Cancel", click -> {
			nodeEditWindow.close();
		});
		cancelNode.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
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
		entityTypeTree = new ComboBox<EntityTypeEnum>();
		entityTypeTree.setCaption("Entity Type");
		entityTypeTree.setDataProvider(new ListDataProvider<>(
				Arrays.asList(EntityTypeEnum.ENTERPRISE, EntityTypeEnum.ORGANIZATION, EntityTypeEnum.REGION, EntityTypeEnum.MERCHANT,
						EntityTypeEnum.TERMINAL, EntityTypeEnum.DEVICE)));
		entityTypeTree.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size",
				"personlization-formAlignment", "small");
		TextField entityName = new TextField("Name");
		entityName.setStyleName("role-textbox");
		entityName.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS, "v-textfield-font");
		entityName.focus();
		TextField entityDescription = new TextField("Description");
		entityDescription.setStyleName("role-textbox");
		entityDescription.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS, "v-textfield-font");
		TextField serialNumber = new TextField("Serial Number");
		serialNumber.setStyleName("role-textbox");
		serialNumber.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS, "v-textfield-font");
		serialNumber.setEnabled(false);
		
		entityTypeTree.addSelectionListener(selected -> {
			if(entityTypeTree.getValue().toString().equals("TERMINAL") || entityTypeTree.getValue().toString().equals("DEVICE")) {
				serialNumber.setEnabled(true);
			}
		});

		Button saveProfile = new Button("Save", click -> {
			if (StringUtils.isNotEmpty(entityName.getValue()) && nodeTree.getSelectedItems().size() == 1) {
				TreeNode selectedNode = nodeTree.getSelectedItems().iterator().next();
				TreeNode newNode = new TreeNode();
				newNode.setLabel(entityName.getValue().isEmpty() ? "Child Node" : entityName.getValue());// Pick name
				newNode.setDescription(entityDescription.getValue()); // textfield
				if(!serialNumber.isEmpty() && serialNumber!=null) {
					newNode.setSerialNum(serialNumber.getValue());
				}
				//REST code for adding new entity
				if (selectedNode.getType() == EntityTypeEnum.ORGANIZATION)

					if (selectedNode.getType() == EntityTypeEnum.REGION)

						if (selectedNode.getType() == EntityTypeEnum.MERCHANT)

							if (selectedNode.getType() == EntityTypeEnum.TERMINAL)

								if (selectedNode.getType() == EntityTypeEnum.DEVICE) {

									return;
								}

				switch (selectedNode.getType()) {
				case ORGANIZATION:
					if(entityTypeTree.getValue().toString().equals("ENTERPRISE") || entityTypeTree.getValue().toString().equals("TERMINAL") ||
							entityTypeTree.getValue().toString().equals("DEVICE") ||entityTypeTree.getValue().toString().equals("MERCHANT")) {
						entityWindow.close();
						 Notification.show("Cannot create "+entityTypeTree.getValue().toString()+" under Organization", Type.ERROR_MESSAGE);
					}else {
							newNode.setType(entityTypeTree.getValue());
							personalizationService.createEntity(selectedNode, newNode);
							nodeTree.setTreeData(personalizationService.getTreeData());
							entityWindow.close();
					}
					break;
				case MERCHANT:
					if(entityTypeTree.getValue().toString().equals("ENTERPRISE") || entityTypeTree.getValue().toString().equals("REGION") ||
							entityTypeTree.getValue().toString().equals("MERCHANT") || entityTypeTree.getValue().toString().equals("ORGANIZARION")||
							entityTypeTree.getValue().toString().equals("DEVICE")) {
						entityWindow.close();
						 Notification.show("Cannot create "+entityTypeTree.getValue().toString()+" under Merchant", Type.ERROR_MESSAGE);
					}else {
							newNode.setType(entityTypeTree.getValue());
							personalizationService.createEntity(selectedNode, newNode);
							nodeTree.setTreeData(personalizationService.getTreeData());
							entityWindow.close();
					}
					break;
				case REGION:
					if(entityTypeTree.getValue().toString().equals("ENTERPRISE") || entityTypeTree.getValue().toString().equals("REGION") ||
							entityTypeTree.getValue().toString().equals("ORGANIZARION")|| entityTypeTree.getValue().toString().equals("DEVICE") ||
							entityTypeTree.getValue().toString().equals("TERMINAL")) {
						entityWindow.close();
						 Notification.show("Cannot create "+entityTypeTree.getValue().toString()+" under Region", Type.ERROR_MESSAGE);
					}else {
							newNode.setType(entityTypeTree.getValue());
							personalizationService.createEntity(selectedNode, newNode);
							nodeTree.setTreeData(personalizationService.getTreeData());
							entityWindow.close();
					}
					break;
				case TERMINAL:
					if(entityTypeTree.getValue().toString().equals("ENTERPRISE") || entityTypeTree.getValue().toString().equals("REGION") ||
							entityTypeTree.getValue().toString().equals("MERCHANT") || entityTypeTree.getValue().toString().equals("ORGANIZARION")||
							entityTypeTree.getValue().toString().equals("TERMINAL")) {
						entityWindow.close();
						 Notification.show("Cannot create "+entityTypeTree.getValue().toString()+" under Terminal", Type.ERROR_MESSAGE);
					}else {
							newNode.setType(entityTypeTree.getValue());
							personalizationService.createEntity(selectedNode, newNode);
							nodeTree.setTreeData(personalizationService.getTreeData());
							entityWindow.close();
					}
					break;
				case DEVICE:
					entityWindow.close();
					Notification.show(NotificationUtil.PERSONALIZATION_CREATE_ENTITY_UNDER_DEVICE, Type.ERROR_MESSAGE);
					return;
				default:
					break;
				}

			} else {
				Notification.show(NotificationUtil.PERSONALIZATION_NEW_ENTITY_NAME, Type.ERROR_MESSAGE);
			}
		});

		saveProfile.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		Button cancelProfile = new Button("Reset", click -> {
			entityName.clear();
			entityDescription.clear();
		});
		cancelProfile.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		HorizontalLayout buttonLayout = new HorizontalLayout(saveProfile, cancelProfile);
		FormLayout profileFormLayout = new FormLayout(entityTypeTree, entityName, entityDescription, serialNumber);

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
		overRideParamGrid.setDataProvider(new ListDataProvider<>(Arrays.asList()));
	}

	private void confirmDeleteOverRideParam(Grid<AppDefaultParam> overRideParamGrid, TextField overRideParamSearch) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?", "Ok", "Cancel",
				new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							// Confirmed to continue
							overRideParamSearch.clear();
							List<AppDefaultParam> selectedParamList = overRideParamGrid.getSelectedItems().stream().collect(Collectors.toList());
								personalizationService.deleteEntityAppProfileParam(selectedProfile.getId(), selectedParamList, selectedNode.getId());
								overRideParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(personalizationService.getAppDefaultParamListWithEntity(selectedProfile.getId(),selectedNode.getId())));
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
									personalizationService.deleteEntity(selectedNode);
									nodeTree.setTreeData(personalizationService.getTreeData());
								ClearAllComponents();
								ClearGrid();
							}
						} else {
							// User did not confirm

						}
					}
				});
	}
	
	private void clearParamValues() {
		parameterName.clear();
		parameterValue.clear();
		parameterDescription.clear();
		parameterType.clear();
	}

	private Window openOverRideParamWindow(Grid<AppDefaultParam> overRideParamGrid) {

		if (nodeTree.getSelectedItems().size() == 0) {
			Notification.show(NotificationUtil.PERSONALIZATION_CREATE_PARAM, Type.ERROR_MESSAGE);
		} else {
			overRideParamWindow = new Window("Add Over Ride Parameter");
			parameterName = new TextField("Parameter");
			parameterName.setStyleName("role-textbox");
			parameterName.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS, "v-textfield-font", "v-grid-cell");
			parameterName.focus();

			parameterDescription = new TextField("Description");
			parameterDescription.setStyleName("role-textbox");
			parameterDescription.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS, "v-textfield-font", "v-grid-cell");

			parameterType.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "small");

			parameterValue = new TextField("Value");
			parameterValue.setStyleName("role-textbox");
			parameterValue.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS, "v-textfield-font", "v-grid-cell");

			Button saveParameter = new Button("Save", click -> {
				if (parameterType.getValue() != null && StringUtils.isNotEmpty(parameterName.getValue())
						&& StringUtils.isNotEmpty(parameterValue.getValue())
						&& StringUtils.isNotEmpty(parameterDescription.getValue())) {
					AppDefaultParam appDefaultParam = new AppDefaultParam(null, parameterName.getValue(),
							parameterDescription.getValue(), parameterType.getValue(), parameterValue.getValue());
					
						AppParamFormat appParamFormat = appStoreService.getAppParamFormatByType(parameterType.getValue());
						appStoreService.saveAppDefaultParam(selectedApp, appDefaultParam, appParamFormat);

					
					TreeNode node;
					if (nodeTree.getSelectedItems().size() > 0) {
						node = nodeTree.getSelectedItems().iterator().next();
						// node.getOverRideParamList().add(overRideParam);
					} else {
						node = new TreeNode();
						List<AppDefaultParam> paramList = new ArrayList<>();
						paramList.add(appDefaultParam);
						// node.setOverRideParamList(paramList);
					}

						overRideParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(appStoreService.getAppDefaultParamListByAppId(selectedApp.getId())));
					overRideParamGrid.select(appDefaultParam);
					overRideParamWindow.close();
				} else {
					Notification.show(NotificationUtil.SAVE, Type.ERROR_MESSAGE);
				}
			});
			saveParameter.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");

			Button cancelParameter = new Button("Reset", click -> {
				clearParamValues();
			});
			cancelParameter.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");

			HorizontalLayout buttonLayout = new HorizontalLayout(saveParameter, cancelParameter);
			FormLayout profileFormLayout = new FormLayout(parameterName, parameterType, parameterDescription,
					parameterValue);

			// Window Setup
			overRideParamWindow.setContent(new VerticalLayout(profileFormLayout, buttonLayout));
			overRideParamWindow.center();
			overRideParamWindow.setModal(true);
			overRideParamWindow.setClosable(true);
			overRideParamWindow.setWidth(30, Unit.PERCENTAGE);
		}
		return overRideParamWindow;
	}
}
