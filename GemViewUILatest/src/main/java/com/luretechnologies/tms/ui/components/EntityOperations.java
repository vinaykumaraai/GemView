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
package com.luretechnologies.tms.ui.components;

import java.util.Arrays;

import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.client.restlib.service.model.EntityTypeEnum;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.service.CommonService;
import com.luretechnologies.tms.backend.service.PersonalizationService;
import com.luretechnologies.tms.backend.service.TreeDataNodeService;
import com.luretechnologies.tms.ui.view.ContextMenuWindow;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TreeGrid;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
public class EntityOperations {
	
	private static ComboBox<EntityTypeEnum> entityTypeTree;
	private static TreeNode selectedNode, selectedNodeForCopy, selectedIntialNode;
	private static ContextMenuWindow createEntityWindow, editEntityWindow;
	private static TextField entityName, entityDescription, serialNumber;
	private static Button save, cancel;
	
	public static void entityOperations(TreeGrid<TreeNode> nodeTree, 
			Button createEntity, Button editEntity, Button deleteEntity, Button copyEntity,
			Button pasteEntity, TreeDataNodeService treeDataNodeService, CommonService commonService, 
			ContextMenuWindow treeContextMenuWindow) {
		createEntity.addClickListener(click -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			treeContextMenuWindow.close();
			createEntityWindow = openEntityWindow(nodeTree, treeDataNodeService, commonService, false);
			if (nodeTree.getSelectedItems().size() == 0) {
				Notification.show(NotificationUtil.PERSONALIZATION_CREATE_ENTITY, Notification.Type.ERROR_MESSAGE);
			} else {
				if (createEntityWindow.getParent() == null)
					UI.getCurrent().addWindow(createEntityWindow);
			}
		});
		
		editEntity.addClickListener( click -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			treeContextMenuWindow.close();
			selectedIntialNode = nodeTree.getSelectedItems().iterator().next();
			if (nodeTree.getSelectedItems().size() == 0) {
				Notification.show(NotificationUtil.PERSONALIZATION_EDIT, Notification.Type.ERROR_MESSAGE);
			} else {
				editEntityWindow = openEntityWindow(nodeTree, treeDataNodeService, commonService,true );
				if (editEntityWindow.getParent() == null)
					UI.getCurrent().addWindow(editEntityWindow);

			}
		});

		deleteEntity.addClickListener(click -> {
			selectedNode= nodeTree.getSelectedItems().iterator().next();
			UI.getCurrent().getWindows().forEach(Window::close);
			treeContextMenuWindow.close();
			if (nodeTree.getSelectedItems().size() == 0) {
				Notification.show(NotificationUtil.PERSONALIZATION_DELETE, Notification.Type.ERROR_MESSAGE);
			} else {
				confirmDeleteEntity(nodeTree, selectedNode, commonService);
			}
		});
		
		copyEntity.addClickListener( click -> {
			//  Copy new entity
			UI.getCurrent().getWindows().forEach(Window::close);
			treeContextMenuWindow.close();
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
		
		pasteEntity.addClickListener(click -> {
			//  Paste new entity
			UI.getCurrent().getWindows().forEach(Window::close);
			treeContextMenuWindow.close();
			if (nodeTree.getSelectionModel().getFirstSelectedItem().isPresent()) {
				if (selectedNodeForCopy != null) {
					TreeNode toPasteNode = nodeTree.getSelectionModel().getFirstSelectedItem().get();
					switch (toPasteNode.getType()) {

					case ENTERPRISE:
						if(selectedNodeForCopy.getType().toString().equals("ORGANIZATION")) {
							
								treeDataNodeService.pasteTreeNode(selectedNodeForCopy, toPasteNode);
								nodeTree.setTreeData(commonService.getTreeData());
						} else {
							Notification.show("Cannot Copy "+selectedNodeForCopy.getType().toString()+" entity to this level", Type.ERROR_MESSAGE);
						}
						break;
					case ORGANIZATION:
						if(selectedNodeForCopy.getType().toString().equals("ORGANIZATION") || 
								selectedNodeForCopy.getType().toString().equals("REGION")) {
								treeDataNodeService.pasteTreeNode(selectedNodeForCopy, toPasteNode);
								nodeTree.setTreeData(commonService.getTreeData());
							} else {
							Notification.show("Cannot Copy "+selectedNodeForCopy.getType().toString()+" entity to this level", Type.ERROR_MESSAGE);
						}
						break;
					case REGION:
						if(selectedNodeForCopy.getType().toString().equals("MERCHANT")) {
								treeDataNodeService.pasteTreeNode(selectedNodeForCopy, toPasteNode);
								nodeTree.setTreeData(commonService.getTreeData());
						} else {
							Notification.show("Cannot Copy "+selectedNodeForCopy.getType().toString()+" entity to this level", Type.ERROR_MESSAGE);
						}
						break;
					case MERCHANT:
						if(selectedNodeForCopy.getType().toString().equals("TERMINAL")) {
								treeDataNodeService.pasteTreeNode(selectedNodeForCopy, toPasteNode);
								nodeTree.setTreeData(commonService.getTreeData());
						} else {
							Notification.show("Cannot Copy "+selectedNodeForCopy.getType().toString()+" entity to this level", Type.ERROR_MESSAGE);
						}
						break;
					case TERMINAL:
						if(selectedNodeForCopy.getType().toString().equals("DEVICE")) {
								treeDataNodeService.pasteTreeNode(selectedNodeForCopy, toPasteNode);
								nodeTree.setTreeData(commonService.getTreeData());
						} else {
							Notification.show("Cannot Copy "+selectedNodeForCopy.getType().toString()+" entity to this level", Type.ERROR_MESSAGE);
						}
						break;
					case DEVICE:
						Notification.show("Cannot Copy "+selectedNodeForCopy.getType().toString()+" entity to this level", Type.ERROR_MESSAGE);
						
					default:
						break;
					}
					}else {
						Notification.show("No Entity is Copied", Type.ERROR_MESSAGE);
					}
					pasteEntity.setEnabled(false);
				}
		});
		
		UI.getCurrent().addClickListener(listener->{
			treeContextMenuWindow.close();
			
			if(createEntityWindow!=null) {
				createEntityWindow.close();
			}
			
	});
		
		Page.getCurrent().addBrowserWindowResizeListener(r->{
			
			if (r.getWidth() <= 600) {
				entityTypeTree.setHeight("28px");
				entityName.setHeight("28px");
				entityDescription.setHeight("28px");
				serialNumber.setHeight("28px");
				save.setHeight("27px");
				cancel.setHeight("27px");
				if(createEntityWindow!=null) {
					createEntityWindow.setPosition(180, 200);
					createEntityWindow.setWidth("180px");
				}
			} else if (r.getWidth() > 600 && r.getWidth() <= 1000) {
				entityTypeTree.setHeight("32px");
				entityName.setHeight("32px");
				entityDescription.setHeight("32px");
				serialNumber.setHeight("32px");
				save.setHeight("32px");
				cancel.setHeight("32px");
				if(createEntityWindow!=null) {
					createEntityWindow.center();
				}
			} else {
				entityTypeTree.setHeight("37px");
				entityName.setHeight("37px");
				entityDescription.setHeight("37px");
				serialNumber.setHeight("37px");
				save.setHeight("37px");
				cancel.setHeight("37px");
				if(createEntityWindow!=null) {
					createEntityWindow.center();
				}
			}
			
		});
	
	}
	private static ContextMenuWindow openEntityWindow(TreeGrid<TreeNode> nodeTree, TreeDataNodeService treeDataNodeService, CommonService commonService, 
			boolean update) {
		ContextMenuWindow entityWindow = new ContextMenuWindow();
		entityTypeTree = new ComboBox<EntityTypeEnum>();
		entityTypeTree.setCaption("Entity Type");
		entityTypeTree.setDataProvider(new ListDataProvider<>(
				Arrays.asList(EntityTypeEnum.ENTERPRISE, EntityTypeEnum.ORGANIZATION, EntityTypeEnum.REGION, EntityTypeEnum.MERCHANT,
						EntityTypeEnum.TERMINAL, EntityTypeEnum.DEVICE)));
		entityTypeTree.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "textfiled-height");
		entityTypeTree.setWidth("100%");
		
		entityName = new TextField("Name");
		entityName.addStyleNames("v-textfield-font", "textfiled-height");
		entityName.focus();
		entityName.setWidth("100%");
		
		entityDescription = new TextField("Description");
		entityDescription.addStyleNames("v-textfield-font", "textfiled-height");
		entityDescription.setWidth("100%");
		
		serialNumber = new TextField("Serial Number");
		serialNumber.addStyleNames("v-textfield-font", "textfiled-height");
		serialNumber.setWidth("100%");
		serialNumber.setEnabled(false);
		
		entityTypeTree.addSelectionListener(selected -> {
			if(entityTypeTree.getValue().toString().equals("TERMINAL") || entityTypeTree.getValue().toString().equals("DEVICE")) {
				serialNumber.setEnabled(true);
			}else {
				serialNumber.setId("ignore");
			}
		});
		
		if(update) {
			TreeNode treeNode = nodeTree.getSelectedItems().iterator().next();
			if(treeNode!=null) {
				entityTypeTree.setEnabled(false);
				entityTypeTree.setValue(treeNode.getType());
				entityName.setValue(treeNode.getLabel());
				entityDescription.setValue(treeNode.getDescription());
				if(treeNode.getSerialNum()!=null) {
					serialNumber.setValue(treeNode.getSerialNum());
					serialNumber.setEnabled(false);
				}
			}
		}
		
		save = new Button("Save", click -> {
			if(createEntityWindow!=null) {
				createEntityWindow.setModal(true);
			}
			
			if(editEntityWindow!=null) {
				editEntityWindow.setModal(true);
			}
			if (nodeTree.getSelectedItems().size() == 1) {
				if (ComponentUtil.validatorComboBox(entityTypeTree) && ComponentUtil.validatorTextField(entityName)
						&& ComponentUtil.validatorTextField(entityDescription) &&
						ComponentUtil.validatorTextField(serialNumber)) {
					TreeNode selectedNode = nodeTree.getSelectedItems().iterator().next();
					if (update) {
						if (selectedIntialNode.getLabel().trim().equals(entityName.getValue().trim())
								&& selectedIntialNode.getDescription().trim()
										.equals(entityDescription.getValue().trim())) {
							editEntityWindow.close();
						} else {

							if (serialNumber.getValue() != null && !serialNumber.isEmpty()) {
								selectedNode.setSerialNum(serialNumber.getValue());
							}
							selectedNode.setDescription(entityDescription.getValue());
							selectedNode.setLabel(entityName.getValue());
							commonService.updateEntity(selectedNode);
							nodeTree.setTreeData(commonService.getTreeData());
							entityWindow.close();

						}
					}else {
					TreeNode newNode = new TreeNode();
					newNode.setLabel(entityName.getValue().isEmpty() ? "Child Node" : entityName.getValue());// Pick
																												// name
					newNode.setDescription(entityDescription.getValue()); // textfield
					if (serialNumber != null && !serialNumber.isEmpty()) {
						newNode.setSerialNum(serialNumber.getValue());
					}
					if (selectedNode.getType() == EntityTypeEnum.ORGANIZATION)

						if (selectedNode.getType() == EntityTypeEnum.REGION)

							if (selectedNode.getType() == EntityTypeEnum.MERCHANT)

								if (selectedNode.getType() == EntityTypeEnum.TERMINAL)

									if (selectedNode.getType() == EntityTypeEnum.DEVICE) {

										return;
									}

					switch (selectedNode.getType()) {
					case ORGANIZATION:
						if (entityTypeTree.getValue().toString().equals("ENTERPRISE")
								|| entityTypeTree.getValue().toString().equals("TERMINAL")
								|| entityTypeTree.getValue().toString().equals("DEVICE")
								|| entityTypeTree.getValue().toString().equals("MERCHANT")) {
							entityWindow.close();
							Notification.show(
									"Cannot create " + entityTypeTree.getValue().toString() + " under Organization",
									Type.ERROR_MESSAGE);
						} else {
							newNode.setType(entityTypeTree.getValue());
							commonService.createEntity(selectedNode, newNode);
							nodeTree.setTreeData(commonService.getTreeData());
							entityWindow.close();
							TreeNode node = treeDataNodeService.findEntityData(selectedNode);
							nodeTree.expand(node);
						}
						break;
					case MERCHANT:
						if (entityTypeTree.getValue().toString().equals("ENTERPRISE")
								|| entityTypeTree.getValue().toString().equals("REGION")
								|| entityTypeTree.getValue().toString().equals("MERCHANT")
								|| entityTypeTree.getValue().toString().equals("ORGANIZARION")
								|| entityTypeTree.getValue().toString().equals("DEVICE")) {
							entityWindow.close();
							Notification.show(
									"Cannot create " + entityTypeTree.getValue().toString() + " under Merchant",
									Type.ERROR_MESSAGE);
						} else {
							newNode.setType(entityTypeTree.getValue());
							commonService.createEntity(selectedNode, newNode);
							nodeTree.setTreeData(commonService.getTreeData());
							entityWindow.close();
						}
						break;
					case REGION:
						if (entityTypeTree.getValue().toString().equals("ENTERPRISE")
								|| entityTypeTree.getValue().toString().equals("REGION")
								|| entityTypeTree.getValue().toString().equals("ORGANIZARION")
								|| entityTypeTree.getValue().toString().equals("DEVICE")
								|| entityTypeTree.getValue().toString().equals("TERMINAL")) {
							entityWindow.close();
							Notification.show("Cannot create " + entityTypeTree.getValue().toString() + " under Region",
									Type.ERROR_MESSAGE);
						} else {
							newNode.setType(entityTypeTree.getValue());
							commonService.createEntity(selectedNode, newNode);
							nodeTree.setTreeData(commonService.getTreeData());
							entityWindow.close();
						}
						break;
					case TERMINAL:
						if (entityTypeTree.getValue().toString().equals("ENTERPRISE")
								|| entityTypeTree.getValue().toString().equals("REGION")
								|| entityTypeTree.getValue().toString().equals("MERCHANT")
								|| entityTypeTree.getValue().toString().equals("ORGANIZARION")
								|| entityTypeTree.getValue().toString().equals("TERMINAL")) {
							entityWindow.close();
							Notification.show(
									"Cannot create " + entityTypeTree.getValue().toString() + " under Terminal",
									Type.ERROR_MESSAGE);
						} else {
							newNode.setType(entityTypeTree.getValue());
							commonService.createEntity(selectedNode, newNode);
							nodeTree.setTreeData(commonService.getTreeData());
							entityWindow.close();
						}
						break;
					case DEVICE:
						entityWindow.close();
						Notification.show(NotificationUtil.PERSONALIZATION_CREATE_ENTITY_UNDER_DEVICE,
								Type.ERROR_MESSAGE);
						return;
					default:
						break;
					}

				}
			}
			}
		});

		save.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		cancel = new Button("Cancel", click -> {
			entityName.clear();
			entityDescription.clear();
		});
		cancel.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		HorizontalLayout buttonLayout = new HorizontalLayout(save, cancel);
		buttonLayout.setComponentAlignment(save, Alignment.BOTTOM_RIGHT);
		buttonLayout.setComponentAlignment(cancel, Alignment.BOTTOM_RIGHT);
		
		VerticalLayout profileFormLayout = new VerticalLayout(entityTypeTree, entityName, entityDescription, serialNumber);
		profileFormLayout.addStyleNames("system-LabelAlignment", "heartbeat-verticalLayout", "header-Components");
		VerticalLayout verticalLayout = new VerticalLayout(profileFormLayout, buttonLayout);
		verticalLayout.addStyleNames("system-LabelAlignment", "heartbeat-verticalLayout", "header-Components");
		
		// Window Setup
		entityWindow.addMenuItems(verticalLayout);
		entityWindow.center();
		entityWindow.setWidth("30%");
		entityWindow.setHeight("42%");
		entityWindow.setResizable(true);
		entityWindow.setClosable(true);
		entityWindow.setDraggable(true);
		return entityWindow;
	}
	
	private static void confirmDeleteEntity(TreeGrid<TreeNode> nodeTree, TreeNode selectedNode, CommonService commonService) {
		ConfirmDialog.show(UI.getCurrent().getUI(), "Please Confirm:", "Are you sure you want to delete?", "Ok", "Cancel",
				new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							if (nodeTree.getSelectedItems().size() == 1) {
									commonService.deleteEntity(selectedNode);
									nodeTree.setTreeData(commonService.getTreeData());
								/*ClearAllComponents();
								ClearGrid();*/
							}
						} else {
							// User did not confirm

						}
					}
				});
	}
}
