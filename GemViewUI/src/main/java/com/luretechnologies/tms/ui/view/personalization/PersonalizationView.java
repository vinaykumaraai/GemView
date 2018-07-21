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
import com.luretechnologies.tms.backend.data.entity.ApplicationFile;
import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.Node;
import com.luretechnologies.tms.backend.data.entity.Profile;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.service.AppService;
import com.luretechnologies.tms.backend.service.ApplicationStoreService;
import com.luretechnologies.tms.backend.service.PersonalizationService;
import com.luretechnologies.tms.backend.service.TreeDataNodeService;
import com.luretechnologies.tms.ui.NotificationUtil;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = -7795601926506565955L;
	public static final String VIEW_NAME = "personalization";
	private static Tree<TreeNode> nodeTree;
	private static TextField treeNodeSearch, overRideParamSearch;
	private static Devices emptyDevice;
	private static HorizontalSplitPanel splitScreen;
	private static Button createEntity, copyEntity, editEntity, deleteEntity, pasteEntity, createParam, editParam,
			deleteParam, save, cancel, selectProfile, selectFile;
	private static TextField entityName, entityDescription;
	private static TextField entitySerialNum = new TextField("Serial Number");
	private static ComboBox<Devices> deviceDropDown;
	private static ComboBox<AppClient> appDropDown;
	private static ComboBox<Profile> profileDropDown;
	private static ComboBox<String> updateDropDown;
	private static ComboBox<ApplicationFile> addtnlFilesDropDown;
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

	@Autowired
	public TreeDataNodeService treeDataNodeService;


	@Autowired
	private AppService appService;

	@Autowired
	private PersonalizationService personalizationService;
	
	@Autowired
	private ApplicationStoreService appStoreService;

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

		Page.getCurrent().addBrowserWindowResizeListener(r -> {
			if (r.getWidth() <= 600) {
				treeNodeSearch.setHeight(28, Unit.PIXELS);
				overRideParamSearch.setHeight(28, Unit.PIXELS);
			} else if (r.getWidth() > 600 && r.getWidth() <= 1000) {
				treeNodeSearch.setHeight(32, Unit.PIXELS);
				overRideParamSearch.setHeight(32, Unit.PIXELS);
			} else {
				treeNodeSearch.setHeight(37, Unit.PIXELS);
				overRideParamSearch.setHeight(37, Unit.PIXELS);
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
		try {
			nodeTree.setTreeData(personalizationService.getTreeData());
			modifiedTree.setTreeData(personalizationService.getTreeData());
		} catch (Exception e) {
			e.printStackTrace();
		}
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

		nodeTree.addItemClickListener(selection -> {
			entityFormLayout.setEnabled(false);
			overRideParamGrid.setEnabled(false);
			// treeDataService.getTreeDataForPersonlization();
			selectedNode = selection.getItem();
			appDropDown.setDataProvider(new ListDataProvider<>(personalizationService.getAppListByLoggedUserEntity(selectedNode.getId())));
			// treeDataService.getTreeDataForPersonlization();
			// List<Node> nodeList = treeDataService.getPersonlizationList();
			// for(Node node : nodeList) {
			// if(node.getLabel().equals(selection.getItem().getLabel())) {
			if (selectedNode.getDescription() != null) {
				entityType.setValue(selectedNode.getType());
				entityName.setValue(selectedNode.getLabel());
				entityDescription.setValue(selectedNode.getDescription());
				activeCheckbox.setValue(selectedNode.isActive());
				if (selectedNode.getType().equals(EntityTypeEnum.TERMINAL)) {
					entitySerialNum.setValue(
							personalizationService.getTerminalSerialNumberByEntityId(selectedNode.getEntityId()));
					activateHeartbeat
							.setValue(personalizationService.getHeartbeatByEntityId(selectedNode.getEntityId()));
					frequencyDropDown.setValue(
							personalizationService.getFrequencyByEntityId(selectedNode.getEntityId()).toString());
					// FIXME: no specific app, appfile, profile is selected
					// appDropDown.setValue(value);
					// addtnlFilesDropDown.setValue(selectedNode.getAdditionaFiles());
					// profileDropDown.setValue(selectedNode.getProfile());
					if (appDropDown.getDataProvider().size(new Query<>()) > 0) {
						// overRideParamGrid.setDataProvider();
						appDropDown.setValue(selectedNode.getApp());
						addtnlFilesDropDown.setValue(selectedNode.getAdditionaFile());
						profileDropDown.setValue(selectedNode.getProfile());
						if(selectedNode.getOverRideParamList()!=null)
						overRideParamGrid.setItems(selectedNode.getOverRideParamList());
					}

				}
				if (selectedNode.getType().equals(EntityTypeEnum.DEVICE)) {
					deviceDropDown.setValue(personalizationService.getDevicesByEntityId(selectedNode.getEntityId()));
					entitySerialNum.setValue(
							personalizationService.getDeviceSerialNumberByEntityId(selectedNode.getEntityId()));
					appDropDown.setValue(selectedNode.getApp());
					addtnlFilesDropDown.setValue(selectedNode.getAdditionaFile());
					profileDropDown.setValue(selectedNode.getProfile());
					overRideParamGrid.setItems(selectedNode.getOverRideParamList());
				}

				// updateDropDown.setValue(selectedNode.getUpdate());
			} else {
				ClearAllComponents();
				ClearGrid();
				entityType.setValue(selectedNode.getType());
				entityName.setValue(selectedNode.getLabel());
				entityFormLayout.setEnabled(true);
				overRideParamGrid.setEnabled(true);
			}
			// }
			// }

		});
		nodeTree.addContextClickListener(event -> {
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
		splitScreen.setSplitPosition(20);
		// splitScreen.addComponent(getTabSheet());
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
		} else if (width > 600 && width <= 1000) {
			treeNodeSearch.setHeight(32, Unit.PIXELS);
			overRideParamSearch.setHeight(32, Unit.PIXELS);
		} else {
			treeNodeSearch.setHeight(37, Unit.PIXELS);
			overRideParamSearch.setHeight(37, Unit.PIXELS);
		}

	}

	private void configureTreeNodeSearch() {
		// FIXME Not able to put Tree Search since its using a Hierarchical
		// Dataprovider.
		treeNodeSearch.addValueChangeListener(changed -> {
			String valueInLower = changed.getValue().toLowerCase();
			nodeTree.setTreeData(
					treeDataNodeService.getFilteredTreeByNodeName(modifiedTree.getTreeData(), valueInLower));
			if (StringUtils.isEmpty(valueInLower)) {
				createEntity.setEnabled(true);
				copyEntity.setEnabled(true);
				pasteEntity.setEnabled(true);
				editEntity.setEnabled(true);
				deleteEntity.setEnabled(true);
			} else {
				createEntity.setEnabled(false);
				copyEntity.setEnabled(false);
				pasteEntity.setEnabled(false);
				editEntity.setEnabled(false);
				deleteEntity.setEnabled(false);
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
			// FIXME Copy new entity
			if (nodeTree.getSelectionModel().getFirstSelectedItem().isPresent()) {
				selectedNodeForCopy = nodeTree.getSelectionModel().getFirstSelectedItem().get();
				if (selectedNodeForCopy.getType().equals(EntityTypeEnum.ENTERPRISE)) {
					Notification.show(NotificationUtil.PERSONALIZATION_ENTERPRISE_COPY, Type.ERROR_MESSAGE);
					selectedNodeForCopy = null;
				} else {
					treeDataNodeService.copyTreeNode(selectedNodeForCopy);
					pasteEntity.setEnabled(true);
				}
			} else {
				Notification.show(NotificationUtil.PERSONALIZATION_COPTY_ENTITY, Notification.Type.ERROR_MESSAGE);
			}
		});
		copyEntity.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		copyEntity.setDescription("Copy Entities", ContentMode.HTML);

		pasteEntity = new Button(VaadinIcons.PASTE, click -> {
			// FIXME Paste new entity
			if (nodeTree.getSelectionModel().getFirstSelectedItem().isPresent()) {
				if (selectedNodeForCopy != null) {
					TreeNode toPasteNode = nodeTree.getSelectionModel().getFirstSelectedItem().get();
					if (toPasteNode.getType().equals(selectedNodeForCopy.getType())) {
						Notification.show(NotificationUtil.PERSONALIZATION_PASTE, Type.ERROR_MESSAGE);
					} else {
						// FIXME: add the REST code copy and paste tree node.
						treeDataNodeService.pasteTreeNode(selectedNodeForCopy, toPasteNode);
						// TreeNode copyPasteNode = new TreeNode();
						// copyPasteNode.setLevel(selectedNodeForCopy.getLevel());
						// copyPasteNode.setLabel(selectedNodeForCopy.getLabel()+" - Copy");
						// copyPasteNode.setEntityList(selectedNodeForCopy.getEntityList());
						// if (toPasteNode.getLevel().equals(NodeLevel.ENTITY)
						// && copyPasteNode.getLevel().equals(NodeLevel.REGION)) {
						// nodeTree.getTreeData().addItem(toPasteNode, copyPasteNode);
						// for (Node childRegionNode :
						// nodeTree.getTreeData().getChildren(selectedNodeForCopy)) {
						// Node newChildRegionNode = new Node();
						// newChildRegionNode.setLevel(childRegionNode.getLevel());
						// newChildRegionNode.setLabel(childRegionNode.getLabel()+" - Copy");
						// newChildRegionNode.setEntityList(childRegionNode.getEntityList());
						// nodeTree.getTreeData().addItem(copyPasteNode, newChildRegionNode);
						// for (Node childMerchantNode :
						// nodeTree.getTreeData().getChildren(childRegionNode)) {
						// Node newChildMerchantNode = new Node();
						// newChildMerchantNode.setLevel(childMerchantNode.getLevel());
						// newChildMerchantNode.setLabel(childMerchantNode.getLabel()+" - Copy");
						// newChildMerchantNode.setEntityList(childMerchantNode.getEntityList());
						// nodeTree.getTreeData().addItem(newChildRegionNode, newChildMerchantNode);
						// for (Node childTerminalNode : nodeTree.getTreeData()
						// .getChildren(childMerchantNode)) {
						// Node newChildTerminalNode = new Node();
						// newChildTerminalNode.setLevel(childTerminalNode.getLevel());
						// newChildTerminalNode.setLabel(childTerminalNode.getLabel()+" - Copy");
						// newChildTerminalNode.setEntityList(childTerminalNode.getEntityList());
						// nodeTree.getTreeData().addItem(newChildMerchantNode, newChildTerminalNode);
						// for (Node childDeviceNode : nodeTree.getTreeData()
						// .getChildren(childTerminalNode)) {
						// Node newChildDeviceNode = new Node();
						// newChildDeviceNode.setLevel(childDeviceNode.getLevel());
						// newChildDeviceNode.setLabel(childDeviceNode.getLabel()+" - Copy");
						// newChildDeviceNode.setEntityList(childDeviceNode.getEntityList());
						// nodeTree.getTreeData().addItem(newChildTerminalNode, newChildDeviceNode);
						// }
						// }
						// }
						// }
						// } else if (toPasteNode.getLevel().equals(NodeLevel.REGION)
						// && copyPasteNode.getLevel().equals(NodeLevel.MERCHANT)) {
						// nodeTree.getTreeData().addItem(toPasteNode, copyPasteNode);
						// for (Node childMerchantNode :
						// nodeTree.getTreeData().getChildren(selectedNodeForCopy)) {
						// Node newChildMerchantNode = new Node();
						// newChildMerchantNode.setLevel(childMerchantNode.getLevel());
						// newChildMerchantNode.setLabel(childMerchantNode.getLabel()+" - Copy");
						// newChildMerchantNode.setEntityList(childMerchantNode.getEntityList());
						// nodeTree.getTreeData().addItem(copyPasteNode, newChildMerchantNode);
						// for (Node childTerminalNode :
						// nodeTree.getTreeData().getChildren(childMerchantNode)) {
						// Node newChildTerminalNode = new Node();
						// newChildTerminalNode.setLevel(childTerminalNode.getLevel());
						// newChildTerminalNode.setLabel(childTerminalNode.getLabel()+" - Copy");
						// newChildTerminalNode.setEntityList(childTerminalNode.getEntityList());
						// nodeTree.getTreeData().addItem(newChildMerchantNode, newChildTerminalNode);
						// for (Node childDeviceNode :
						// nodeTree.getTreeData().getChildren(childTerminalNode)) {
						// Node newChildDeviceNode = new Node();
						// newChildDeviceNode.setLevel(childDeviceNode.getLevel());
						// newChildDeviceNode.setLabel(childDeviceNode.getLabel()+" - Copy");
						// newChildDeviceNode.setEntityList(childDeviceNode.getEntityList());
						// nodeTree.getTreeData().addItem(newChildTerminalNode, newChildDeviceNode);
						// }
						// }
						// }
						// } else if (toPasteNode.getLevel().equals(NodeLevel.MERCHANT)
						// && copyPasteNode.getLevel().equals(NodeLevel.TERMINAL)) {
						// nodeTree.getTreeData().addItem(toPasteNode, copyPasteNode);
						// for (Node childNode :
						// nodeTree.getTreeData().getChildren(selectedNodeForCopy)) {
						// // FIXME: provide a common log of copy constructor
						// Node newChildNode = new Node();
						// newChildNode.setLabel(childNode.getLabel()+" - Copy");
						// newChildNode.setLevel(childNode.getLevel());
						// newChildNode.setEntityList(childNode.getEntityList());
						// nodeTree.getTreeData().addItem(copyPasteNode, newChildNode);
						// }
						//
						// } else if (toPasteNode.getLevel().equals(NodeLevel.TERMINAL)
						// && copyPasteNode.getLevel().equals(NodeLevel.DEVICE))
						// nodeTree.getTreeData().addItem(toPasteNode, copyPasteNode);
						// else
						// Notification.show("The entity " + selectedNodeForCopy.getLabel() + " can't be
						// copied under "
						// + toPasteNode.getLabel(), Type.ERROR_MESSAGE);

						// nodeTree.getDataProvider().refreshAll();
						try {
							nodeTree.setTreeData(personalizationService.getTreeData());
						} catch (ApiException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					pasteEntity.setEnabled(false);
					modifiedTree.setTreeData(nodeTree.getTreeData());
				}
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
			// if (overRideParamGrid.getSelectedItems().size() > 0)
			// overRideParamGrid.getEditor().setEnabled(true);
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
			if (/*deviceDropDown.getValue() != null && */entityType.getValue().equals(EntityTypeEnum.TERMINAL)) {
				node.setDevice(deviceDropDown.getValue());
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

			// node.setUpdate(updateDropDown.getValue());
			node.setOverRideParamList(
					overRideParamGrid.getDataProvider().fetch(new Query<>()).collect(Collectors.toList()));
			overRideParamGrid.setDataProvider(new ListDataProvider<>(node.getOverRideParamList()));
			overRideParamGrid.getDataProvider().refreshAll();
			overRideParamGrid.deselectAll();
			// Calling service
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

		entityName = new TextField("Name");
		entityName.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		entityName.setWidth("48%");
		entityName.setStyleName("role-textbox");
		entityName.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS, "personlization-formAlignment",
				"v-textfield-lineHeight");
		// entityName.addStyleName("v-grid-cell");

		entityFormLayout.addComponent(entityName);
	}

	private void getDeviceDropdown(FormLayout entityFormLayout) {

		deviceDropDown = new ComboBox<Devices>();
		deviceDropDown.setCaption("Devices");
		deviceDropDown.setPlaceholder("Select Device");
		deviceDropDown.setDataProvider(personalizationService.getAllDevices());
		deviceDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size",
				"personlization-formAlignment", "small");
		// do Necessary operations for popping data

		entityFormLayout.addComponent(deviceDropDown);
	}

	private void getEntityDescription(FormLayout entityFormLayout) {

		entityDescription = new TextField("Description");
		entityDescription.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		entityDescription.setWidth("48%");
		entityDescription.setStyleName("role-textbox");
		entityDescription.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS, "personlization-formAlignment",
				"v-textfield-lineHeight");

		// do Necessary operations for popping data

		entityFormLayout.addComponent(entityDescription);
	}

	private void getEntityActive(FormLayout entityFormLayout) {

		HorizontalLayout entityActiveLayout = new HorizontalLayout();
		// entityActiveLayout.addStyleNames("personlization-formAlignment");
		Label active = new Label("Active");
		active.setStyleName("role-activeLable");
		active.addStyleNames("v-textfield-font", "role-activeLabel");
		activeCheckbox = new CheckBox();
		activeCheckbox.setCaption("Entity Active");
		activeCheckbox.addStyleNames("v-textfield-font", "personlization-activeCheckbox");
		// do Necessary operations for popping data

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

		// do Necessary operations for popping data

		entityFormLayout.addComponent(entitySerialNum);
	}

	private void getEntityHeartBeat(FormLayout entityFormLayout) {

		HorizontalLayout entityHeartbeatLayout = new HorizontalLayout();
		// entityHeartbeatLayout.addStyleNames("personlization-formAlignment");
		Label heartbeat = new Label("Heartbeat");
		heartbeat.setStyleName("role-activeLable");
		heartbeat.addStyleNames("v-textfield-font", "role-activeLabel");
		activateHeartbeat = new CheckBox();
		activateHeartbeat.setCaption("Entity Active");
		activateHeartbeat.addStyleNames("v-textfield-font", "personlization-heartbeatCheckbox");
		// do Necessary operations for popping data

		entityHeartbeatLayout.addComponents(heartbeat, activateHeartbeat);
		entityHeartbeatLayout.setStyleName("role-activeLable");
		entityHeartbeatLayout.addStyleNames("assetAlert-activeCheckBox", "personlization-formAlignment",
				"personlization-heartbeatLayout");

		entityFormLayout.addComponent(entityHeartbeatLayout);
	}

	private void getEntityFrequency(FormLayout entityFormLayout) {

		frequencyDropDown = new ComboBox<>();
		// TODO: find the frequency list from Integration
		frequencyDropDown.setDataProvider(new ListDataProvider<>(
				Arrays.asList("120 Seconds", "90 Seconds", " 30 Seconds", "Daily", "Monthly", "Never")));
		frequencyDropDown.setCaption("Frequency");
		frequencyDropDown.setPlaceholder("Frequency");
		frequencyDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size",
				"personlization-formAlignment", "small");
		// do Necessary operations for popping data

		entityFormLayout.addComponent(frequencyDropDown);
	}

	private void getApplicationSelection(FormLayout entityFormLayout) {

		HorizontalLayout entityApplicationSelection = new HorizontalLayout();
		entityApplicationSelection.addStyleNames("personlization-formAlignment",
				"personlization-applicationSelectionLayout");
		entityApplicationSelection.setCaption("Application Selection");
		appDropDown = new ComboBox<AppClient>();
		// frequencyDropDown.setDataProvider(new ListDataProvider<>(Arrays.asList("24
		// Hours", "1 Hour", " 30 Minutes")));
		appDropDown.setCaption("");
		appDropDown.setPlaceholder("Application");
		appDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "small");
		// do Necessary operations for popping data

		addtnlFilesDropDown = new ComboBox<>();
		// addtnlFilesDropDown.setDataProvider(new ListDataProvider<>(
		// Arrays.asList("Entity Files", "Region Files", "Merchant Files", "Terminal
		// Files", "Device Files")));
		addtnlFilesDropDown.setCaption("");
		addtnlFilesDropDown.setPlaceholder("Additional Files");
		addtnlFilesDropDown.setDescription("Files");
		addtnlFilesDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "small");

		profileDropDown = new ComboBox<Profile>();
		// frequencyDropDown.setDataProvider(new ListDataProvider<>(Arrays.asList("24
		// Hours", "1 Hour", " 30 Minutes")));
		profileDropDown.setCaption("");
		profileDropDown.setDescription("Profile");
		profileDropDown.setPlaceholder("Default Profile");
		profileDropDown.setDataProvider(personalizationService.getProfileForEntityDataProvider(selectedApp.getId(),selectedNode.getId()));
		profileDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "small");
		
		selectProfile = new Button("", VaadinIcons.PLUS_CIRCLE);
		selectProfile.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY, "personlization-plusButtons");
		selectProfile.setDescription("Add Profiles");
		
		selectFile = new Button("", VaadinIcons.PLUS_CIRCLE);
		selectFile.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY, "personlization-plusButtons");
		selectFile.setDescription("Add Files");
		
		selectProfile.addClickListener(click ->{
			if(selectedApp != null)
				UI.getCurrent().addWindow(openProfileListToAddWindow());
		});
		appDropDown.addSelectionListener(selected -> {
			selectedApp = selected.getValue();
			addtnlFilesDropDown.clear();
			profileDropDown.clear();
//			addtnlFilesDropDown
//					.setDataProvider(personalizationService.getApplicationFileDataProvider(selectedApp.getId()));
//			profileDropDown.setDataProvider(personalizationService.getProfileDataProvider(selectedApp.getId()));
			
			try {
				overRideParamGrid.setDataProvider(new ListDataProvider<>(appStoreService.getAppDefaultParamListByAppId(selectedApp.getId())));
				parameterType.setDataProvider(new ListDataProvider<>(appStoreService.getAppParamTypeList(selectedApp.getId())));
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		entityApplicationSelection.addComponents(appDropDown, selectProfile, profileDropDown, selectFile, addtnlFilesDropDown);
		entityFormLayout.addComponent(entityApplicationSelection);
	}

	private void getScheduleUpdate(FormLayout entityFormLayout) {

		updateDropDown = new ComboBox<>();
		updateDropDown.setDataProvider(
				new ListDataProvider<>(Arrays.asList("Update 1", "Update 2", "Update 3", "Update 4", "Update 5")));
		updateDropDown.setCaption("Schedule Update");
		updateDropDown.setPlaceholder("Update");
		updateDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size",
				"personlization-formAlignment", "small");

		// do Necessary operations for popping data

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

		createParam = new Button(VaadinIcons.FOLDER_ADD, click -> {
			clearParamValues();
			Window createParamGridWindow = openOverRideParamWindow(overRideParamGrid);
			if (createParamGridWindow.getParent() == null)
				UI.getCurrent().addWindow(createParamGridWindow);
		});
		createParam.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		createParam.setDescription("Create New Parameter", ContentMode.HTML);

		// editParam = new Button(VaadinIcons.EDIT, click -> {
		// //Todo Edit new entity
		// });
		// editParam.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");

		deleteParam = new Button(VaadinIcons.TRASH, click -> {
			if (nodeTree.getSelectedItems().size() == 0) {
				Notification.show(NotificationUtil.PERSONALIZATION_DELETE, Notification.Type.ERROR_MESSAGE);
			} else if (overRideParamGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.PERSONALIZATION_PARAM_DELETE, Notification.Type.ERROR_MESSAGE);
			} else {
				confirmDeleteOverRideParam(overRideParamGrid, overRideParamSearch);
			}
		});
		deleteParam.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		deleteParam.setDescription("Delete Params", ContentMode.HTML);
		overRideParamButtonLayout.addComponents(createParam, deleteParam);

		overRideParamSearchButtonLayout.addComponents(overRideParamSearchLayout, overRideParamButtonLayout);

		overRideParamGrid = new Grid<>(AppDefaultParam.class);
		overRideParamGrid.addStyleNames("personlization-gridHeight");
		overRideParamGrid.setWidth("100%");
		overRideParamGrid.setHeightByRows(5);
		overRideParamGrid.setResponsive(true);
		overRideParamGrid.setSelectionMode(SelectionMode.SINGLE);
		overRideParamGrid.setColumns("parameter", "description", "type", "value");
		overRideParamGrid.getColumn("parameter").setEditorComponent(new TextField());
		overRideParamGrid.getColumn("description").setEditorComponent(new TextField());
		overRideParamGrid.getColumn("value").setEditorComponent(new TextField());
		overRideParamGrid.getColumn("type").setEditorComponent(parameterType);
		overRideParamGrid.getEditor().setEnabled(true).addSaveListener(save -> {
			// FIXME: update code for save mechanism
			AppDefaultParam param = save.getBean();
			if (param.getParameter().isEmpty() || param.getDescription().isEmpty() || param.getType() == null
					|| param.getValue().isEmpty()) {
				Notification.show("Data cannot be empty in a selected row");
			} else if (selectedApp != null)
				//personalizationService.updateOverRideParam(selectedApp, param);
				try {
					AppParamFormat appParamFormat = appStoreService.getAppParamFormatByType(parameterType.getValue());
					appStoreService.saveAppDefaultParam(selectedApp, save.getBean(), appParamFormat);
				} catch (ApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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

	private Window openTreeNodeEditWindow(Node nodeToEdit) {
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
				// FIXME: for update
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
				Arrays.asList(EntityTypeEnum.ENTERPRISE, EntityTypeEnum.REGION, EntityTypeEnum.MERCHANT,
						EntityTypeEnum.TERMINAL, EntityTypeEnum.DEVICE, EntityTypeEnum.ORGANIZATION)));
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
				// FIXME add the REST code for adding new entity
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
							entityTypeTree.getValue().toString().equals("DEVICE")) {
						entityWindow.close();
						 Notification.show("Cannot Select this entity type for Organization", Type.ERROR_MESSAGE);
					}else {
						newNode.setType(entityTypeTree.getValue());
						personalizationService.createEntity(selectedNode, newNode);
					}
					break;
				case MERCHANT:
					if(entityTypeTree.getValue().toString().equals("ENTERPRISE") || entityTypeTree.getValue().toString().equals("REGION") ||
							entityTypeTree.getValue().toString().equals("MERCHANT") || entityTypeTree.getValue().toString().equals("ORGANIZARION")||
							entityTypeTree.getValue().toString().equals("DEVICE")) {
						entityWindow.close();
						 Notification.show("Cannot Select this entity type for Merchant", Type.ERROR_MESSAGE);
					}else {
						newNode.setType(entityTypeTree.getValue());
						personalizationService.createEntity(selectedNode, newNode);
					}
					break;
				case REGION:
					if(entityTypeTree.getValue().toString().equals("ENTERPRISE") || entityTypeTree.getValue().toString().equals("REGION") ||
							entityTypeTree.getValue().toString().equals("MERCHANT") || entityTypeTree.getValue().toString().equals("ORGANIZARION")||
							entityTypeTree.getValue().toString().equals("DEVICE")) {
						entityWindow.close();
						 Notification.show("Cannot Select this entity type for Merchant", Type.ERROR_MESSAGE);
					}else {
						newNode.setType(entityTypeTree.getValue());
						personalizationService.createEntity(selectedNode, newNode);
					}
					break;
				case TERMINAL:
					if(entityTypeTree.getValue().toString().equals("ENTERPRISE") || entityTypeTree.getValue().toString().equals("REGION") ||
							entityTypeTree.getValue().toString().equals("MERCHANT") || entityTypeTree.getValue().toString().equals("ORGANIZARION")||
							entityTypeTree.getValue().toString().equals("TERMINAL")) {
						entityWindow.close();
						 Notification.show("Cannot Select this entity type for Merchant", Type.ERROR_MESSAGE);
					}else {
						newNode.setType(entityTypeTree.getValue());
						personalizationService.createEntity(selectedNode, newNode);
					}
					break;
				case DEVICE:
					entityWindow.close();
					Notification.show(NotificationUtil.PERSONALIZATION_CREATE_ENTITY_UNDER_DEVICE, Type.ERROR_MESSAGE);
					return;
				default:
					break;
				}
				try {
					nodeTree.setTreeData(personalizationService.getTreeData());
				} catch (ApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// nodeTree.getDataProvider().refreshAll();
				entityWindow.close();

			} else {
				Notification.show(NotificationUtil.PERSONALIZATION_NEW_ENTITY_NAME, Type.ERROR_MESSAGE);
			}
			// entityWindow.close();
		});

		saveProfile.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		// nodeTree.getTreeData().addItem(selectedNode, newNode);

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
	
	private Window openProfileListToAddWindow() {
		Window openProfileListWindow = new Window("App Profile List");
		
		ListSelect<Profile> optionList = new ListSelect<>();
		optionList.setRows(3);
		optionList.setResponsive(true);
		optionList.setWidth(100, Unit.PERCENTAGE);
		optionList.setDataProvider(personalizationService.getProfileDataProvider(selectedApp.getId()));
		Button saveNode = new Button("Add", click -> {
			if (optionList.getValue() != null) {
				List<Profile> profileList = profileDropDown.getDataProvider().fetch(new Query<>()).collect(Collectors.toList());
				profileList.addAll(optionList.getValue());
				profileDropDown.setDataProvider(new ListDataProvider<>(profileList));
				personalizationService.saveProfileForEntity(optionList.getValue(),selectedNode.getId());
				openProfileListWindow.close();
				// FIXME: for update
			} else {
				Notification notification = Notification.show("No Profile Selected",
						Type.ERROR_MESSAGE);
				
			}
		});

		saveNode.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");

		Button cancelNode = new Button("Cancel", click -> {
			openProfileListWindow.close();
		});
		cancelNode.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		HorizontalLayout buttonLayout = new HorizontalLayout(saveNode, cancelNode);
		FormLayout profileFormLayout = new FormLayout(optionList);

		// Window Setup
		openProfileListWindow.setContent(new VerticalLayout(profileFormLayout, buttonLayout));
		openProfileListWindow.center();
		openProfileListWindow.setModal(true);
		openProfileListWindow.setClosable(true);
		openProfileListWindow.setWidth(30, Unit.PERCENTAGE);
		return openProfileListWindow;
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

	private void confirmDeleteOverRideParam(Grid<AppDefaultParam> overRideParamGrid, TextField overRideParamSearch) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?", "Ok", "Cancel",
				new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							// Confirmed to continue
							overRideParamSearch.clear();
							overRideParamGrid.setEnabled(false);
							try {
								appStoreService.removeAPPParam(selectedApp.getId(), overRideParamGrid.getSelectedItems().iterator().next().getId());
								overRideParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(appStoreService.getAppDefaultParamListByAppId(selectedApp.getId())));
							} catch (ApiException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//personalizationService.deleteOverRideParam(selectedApp,overRideParamGrid.getSelectedItems().iterator().next());
							//overRideParamGrid.setDataProvider(personalizationService.getOverrideParamDataProvider(selectedApp.getId()));
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
//								nodeTree.getTreeData().removeItem(nodeTree.getSelectedItems().iterator().next());
//								// Notification.show("To be Deleted
//								// "+nodeTree.getSelectedItems().iterator().next(),Type.WARNING_MESSAGE);
//								nodeTree.getDataProvider().refreshAll();
								
								try {
									personalizationService.deleteEntity(nodeTree.getSelectedItems().iterator().next());
									nodeTree.setTreeData(personalizationService.getTreeData());
								} catch (ApiException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
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
			//parameterType.setDataProvider(new ListDataProvider<>(Arrays.asList(ParameterType.values())));

			parameterValue = new TextField("Value");
			parameterValue.setStyleName("role-textbox");
			parameterValue.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS, "v-textfield-font", "v-grid-cell");

			Button saveParameter = new Button("Save", click -> {
				if (parameterType.getValue() != null && StringUtils.isNotEmpty(parameterName.getValue())
						&& StringUtils.isNotEmpty(parameterValue.getValue())
						&& StringUtils.isNotEmpty(parameterDescription.getValue())) {
					AppDefaultParam appDefaultParam = new AppDefaultParam(null, parameterName.getValue(),
							parameterDescription.getValue(), parameterType.getValue(), parameterValue.getValue());
					
					//personalizationService.createOverRideParam(selectedApp,appDefaultParam);
					try {
						AppParamFormat appParamFormat = appStoreService.getAppParamFormatByType(parameterType.getValue());
						appStoreService.saveAppDefaultParam(selectedApp, appDefaultParam, appParamFormat);

					} catch (ApiException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					// FIXME : write create mechanism for override

					// overRideParamService.saveOverRideParam(overRideParam);
					//
					// treeDataService.getTreeDataForPersonlization();
					// List<Node> nodeList = treeDataService.getPersonlizationList();
					// for(Node node : nodeList) {
					// if(node.getLabel().equals(nodeTree.getSelectedItems().iterator().next().getLabel()))
					// {
					//
					// DataProvider data = new ListDataProvider(node.getOverRideParamList());
					// overRideParamGrid.setDataProvider(data);
					// overRideParamGrid.select(overRideParam);
					//
					//
					// }
					// }
					// appDefaultParamService.saveAppDefaultParam(appDefaultParam);
					// List<OverRideParameters> list = new ArrayList<OverRideParameters>();
					// list.addAll(nodeTree.getSelectedItems().iterator().next().getOverRideParamList());
					// list.add(overRideParam);

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

					try {
						overRideParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(appStoreService.getAppDefaultParamListByAppId(selectedApp.getId())));
					} catch (ApiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
