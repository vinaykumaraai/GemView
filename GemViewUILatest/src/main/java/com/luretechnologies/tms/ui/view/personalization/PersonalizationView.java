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

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.AppParamFormat;
import com.luretechnologies.client.restlib.service.model.EntityTypeEnum;
import com.luretechnologies.tms.backend.data.entity.AppClient;
import com.luretechnologies.tms.backend.data.entity.AppDefaultParam;
import com.luretechnologies.tms.backend.data.entity.ApplicationFile;
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
import com.luretechnologies.tms.ui.components.ComponentUtil;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.luretechnologies.tms.ui.navigation.NavigationManager;
import com.luretechnologies.tms.ui.view.ContextMenuWindow;
import com.luretechnologies.tms.ui.view.Header;
import com.luretechnologies.tms.ui.view.applicationstore.ApplicationStoreView;
import com.vaadin.data.TreeData;
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
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
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
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.TreeContextClickEvent;
import com.vaadin.ui.TreeGrid;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = PersonalizationView.VIEW_NAME)
public class PersonalizationView extends VerticalLayout implements Serializable, View {

	private static final long serialVersionUID = -7795601926506565955L;
	public static final String VIEW_NAME = "personalization";
	private static TreeGrid<TreeNode> nodeTree;
	private static TextField treeNodeSearch, overRideParamSearch;
	private static Devices emptyDevice;
	private static HorizontalSplitPanel splitScreen;
	private static Button createEntity, copyEntity, editEntity, deleteEntity, pasteEntity, addParam, editParam,
			deleteParam, save, cancel, selectProfile, selectFile,clearSearch, clearParamSearch;
	private static TextField entityName, entityDescription;
	private static TextField entitySerialNum ;
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
	private static ComboBox<Boolean> activeBox;
	private static VerticalLayout entityLayout;
	private static VerticalLayout entityGridLayout;
	private static VerticalLayout entityFileLayout;
	private static VerticalLayout entityApplicationLayout;
	private static HorizontalLayout treeSearchLayout;
	private static TreeGrid<TreeNode> modifiedTree = new TreeGrid<>();
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
	private CssLayout treeClearSearchLayout;
	private CssLayout overrideParamSearchCSSLayout;
	private TabSheet personlizationTabSheet;
	private Header header;
	private final NavigationManager navigationManager;
	private Grid<AppClient> appGrid;
	private Grid<AppDefaultParam> appFileGrid;
	private Button fileButton;
	private HorizontalLayout fileButtonLayout;
	private ComboBox<Profile> profileListPersonlization = new ComboBox<Profile>("Profile");
	private ComboBox<AppClient> appDropDownParam;
	private ComboBox<Profile> profileDropDownParam;
	Logger logger = LoggerFactory.getLogger(PersonalizationView.class);
	
	private static final String FILE_CHOOSE_LIST = "fileChooseList";

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
	public PersonalizationView(NavigationManager navigationManager) {

		emptyDevice = new Devices();
		emptyDevice.setManufacturer("");
		emptyDevice.setDeviceName("");
		emptyDevice.setDescription("");
		emptyDevice.setActive(false);
		emptyDevice.setSerialNumber("");
		emptyDevice.setHeartBeat(false);
		emptyDevice.setFrequency("");
		emptyDevice.setLastSeen("");
		this.navigationManager = navigationManager;

	}

	@PostConstruct
	private void inti() {
	  try {
		  header = new Header(userService,navigationManager, "Personlization", new Label());
		Page.getCurrent().addBrowserWindowResizeListener(r -> {
			if (r.getWidth() <= 600) {
				treeNodeSearch.setHeight(28, Unit.PIXELS);
				overRideParamSearch.setHeight(28, Unit.PIXELS);
				clearParamSearch.removeStyleNames("v-button-customstyle", "audit-AuditSearchClearDesktop");
				clearParamSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"odometer-OdometerSearchClearPhone");
				removeComponent(header);
				mainView.getTitle().setValue(userService.getLoggedInUserName());
			} else if (r.getWidth() > 600 && r.getWidth() <= 1000) {
				treeNodeSearch.setHeight(32, Unit.PIXELS);
				overRideParamSearch.setHeight(32, Unit.PIXELS);
				clearParamSearch.removeStyleNames("audit-AuditSearchClearDesktop", "odometer-OdometerSearchClearPhone");
				clearParamSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
				addComponentAsFirst(header);
				mainView.getTitle().setValue("gemView");
			} else {
				treeNodeSearch.setHeight(37, Unit.PIXELS);
				overRideParamSearch.setHeight(37, Unit.PIXELS);
				clearParamSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
				clearParamSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearDesktop");
				addComponentAsFirst(header);
				mainView.getTitle().setValue("gemView");
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
		treeNodeSearch.setMaxLength(50);
		clearSearch = new Button(VaadinIcons.CLOSE);
		clearSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
		configureTreeNodeSearch();

		Panel panel = getAndLoadPersonlizationPanel();
		treeSearchLayout = new HorizontalLayout();
		treeSearchLayout.setWidth("100%");
		getEntityButtonsList( treeSearchLayout);
		VerticalLayout treeLayoutWithButtons = new VerticalLayout();
		treeLayoutWithButtons.setSizeFull();
		VerticalLayout treePanelLayout = new VerticalLayout();

		treeLayoutWithButtons.addComponents(treeSearchLayout);
		nodeTree = new TreeGrid<TreeNode>(TreeNode.class);
		nodeTree.setTreeData(personalizationService.getTreeData());
		modifiedTree.setTreeData(personalizationService.getTreeData());
		nodeTree.setSizeFull();
		
		nodeTree.addColumn(entity -> {
			String iconHtml="";
			switch (entity.getType()){
			case ENTERPRISE:
				iconHtml =  VaadinIcons.ORIENTATION.getHtml();
				break;
			case ORGANIZATION:
				iconHtml = VaadinIcons.BUILDING_O.getHtml();
				break;
			case MERCHANT:
				iconHtml =  VaadinIcons.SHOP.getHtml();
				break;
			case REGION:
				iconHtml = VaadinIcons.OFFICE.getHtml();
				break;
			case TERMINAL:
				iconHtml = VaadinIcons.LAPTOP.getHtml();
				break;
			case DEVICE:
				iconHtml = VaadinIcons.MOBILE_BROWSER.getHtml();
				break;
			default:
				break;
			}
			return iconHtml +" "+ Jsoup.clean(entity.getLabel(),Whitelist.simpleText());
		},new HtmlRenderer()).setCaption("Entity").setId("entity");	
		nodeTree.getColumn("serialNum").setCaption("Serial");
		nodeTree.setHierarchyColumn("entity");
		
		nodeTree.setColumns("entity","serialNum");
		
		nodeTree.addSelectionListener(selection -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			appDropDown.clear();
			appDropDownParam.clear();
			if(!selection.getFirstSelectedItem().isPresent()) {
				ClearAllComponents();
				ClearGrid();
			}else {
			
			entityLayout.setEnabled(false);
			selectedNode = selection.getFirstSelectedItem().get();
			appDropDown.setDataProvider(new ListDataProvider<>(appStoreService.getAppListForGrid()));
//			appDropDownParam.setDataProvider(new ListDataProvider<>(personalizationService.getAppListByLoggedUserEntity(selectedNode.getId())));
//			appGrid.setDataProvider(new ListDataProvider<>(personalizationService.getAppListByLoggedUserEntity(selectedNode.getId())));
			entityLayout.setEnabled(false);
			appDropDown.setDataProvider(new ListDataProvider<>(appStoreService.getAppListForGrid()));
//			appDropDownParam.setDataProvider(new ListDataProvider<>(personalizationService.getAppListByLoggedUserEntity(selectedNode.getId())));
//			appGrid.setDataProvider(new ListDataProvider<>(personalizationService.getAppListByLoggedUserEntity(selectedNode.getId())));
			if (selectedNode.getType().toString()!=null && !selectedNode.getType().toString().isEmpty()
					&& selectedNode.getLabel()!=null && !selectedNode.getLabel().isEmpty()) {
				entityType.setValue(selectedNode.getType());
				entityName.setValue(selectedNode.getLabel());
				entityDescription.setValue(selectedNode.getDescription());
				if(selectedNode.isActive()) {
					activeBox.setValue(true);
				}else {
					activeBox.setValue(false);
				}
				if (selectedNode.getType().equals(EntityTypeEnum.TERMINAL)) {
					entitySerialNum.setEnabled(true);
					activateHeartbeat.setEnabled(true);
					frequencyDropDown.setEnabled(true);
//					selectedNode.setSerialNum(personalizationService.getTerminalSerialNumberByEntityId(selectedNode.getEntityId()));
					entitySerialNum.setValue(selectedNode.getSerialNum());
					activateHeartbeat
							.setValue(personalizationService.getHeartbeatByEntityId(selectedNode.getEntityId()));
					frequencyDropDown.setValue(
							personalizationService.getFrequencyByEntityId(selectedNode.getEntityId()).toString());
				}
				else if (selectedNode.getType().equals(EntityTypeEnum.DEVICE)) {
					entitySerialNum.setValue(selectedNode.getSerialNum());
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
				entityLayout.setEnabled(true);
				overRideParamGrid.setEnabled(true);
			}
			}

		});
		
		pasteEntity = new Button("Paste Entity");
		pasteEntity.setEnabled(false);
		
		nodeTree.addContextClickListener(event -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			ContextMenuWindow treeContextMenuWindow = new ContextMenuWindow();
			treeContextMenuWindow.setPosition(event.getClientX(), event.getClientY());
			
			createEntity = new Button("Add Entity", click -> {
				UI.getCurrent().getWindows().forEach(Window::close);
				treeContextMenuWindow.close();
				Window createProfileWindow = openEntityWindow();
				if (nodeTree.getSelectedItems().size() == 0) {
					Notification.show(NotificationUtil.PERSONALIZATION_CREATE_ENTITY, Notification.Type.ERROR_MESSAGE);
				} else {
					if (createProfileWindow.getParent() == null)
						UI.getCurrent().addWindow(createProfileWindow);
				}
			});
			createEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
			
			editEntity = new Button("Edit Entity", click -> {
				UI.getCurrent().getWindows().forEach(Window::close);
				treeContextMenuWindow.close();
				if (nodeTree.getSelectedItems().size() == 0) {
					Notification.show(NotificationUtil.PERSONALIZATION_EDIT, Notification.Type.ERROR_MESSAGE);
				} else {
					entityLayout.setEnabled(true);
					overRideParamGrid.setEnabled(true);
				}
			});
			editEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
			

			deleteEntity = new Button("Delete Entity", click -> {
				UI.getCurrent().getWindows().forEach(Window::close);
				treeContextMenuWindow.close();
				if (nodeTree.getSelectedItems().size() == 0) {
					Notification.show(NotificationUtil.PERSONALIZATION_DELETE, Notification.Type.ERROR_MESSAGE);
				} else {
					confirmDeleteEntity();
				}
			});
			deleteEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
			
			copyEntity = new Button("Copy Entity", click -> {
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
			
			copyEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
			
			pasteEntity.addClickListener(listener-> {
				//  Paste new entity
				
				treeContextMenuWindow.close();
				
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
						}else {
							Notification.show("No Entity is Copied", Type.ERROR_MESSAGE);
						}
						pasteEntity.setEnabled(false);
						modifiedTree.setTreeData(nodeTree.getTreeData());
				}
			});
			pasteEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
			treeContextMenuWindow.addMenuItems(createEntity,editEntity,deleteEntity,copyEntity,pasteEntity);
			UI.getCurrent().addWindow(treeContextMenuWindow);
			
			UI.getCurrent().addClickListener(listener->{
				treeContextMenuWindow.close();
			});
			

		});
		treeLayoutWithButtons.addComponent(nodeTree);
		treeLayoutWithButtons.setExpandRatio(nodeTree, 14);
		treeLayoutWithButtons.setMargin(true);
		treeLayoutWithButtons.setStyleName("split-Height-ButtonLayout");
		treePanelLayout.addComponent(treeLayoutWithButtons);
		treePanelLayout.setHeight("100%");
		treePanelLayout.setStyleName("split-height");
		splitScreen = new HorizontalSplitPanel();
		splitScreen.setFirstComponent(treePanelLayout);
		splitScreen.setSplitPosition(40);
		splitScreen.setHeight("100%");

		getEntityInformation(splitScreen);
		panel.setContent(splitScreen);

		entityLayout.setEnabled(false);
		deviceDropDown.setVisible(true);
		//overRideParamGrid.setEnabled(false);
		if (nodeTree.getSelectedItems().isEmpty()) {
			entityLayout.setEnabled(false);
			//overRideParamGrid.setEnabled(false);
			ClearAllComponents();
			ClearGrid();
		}

		int width = Page.getCurrent().getBrowserWindowWidth();
		if (width <= 600) {
			treeNodeSearch.setHeight(28, Unit.PIXELS);
			overRideParamSearch.setHeight(28, Unit.PIXELS);
			clearParamSearch.removeStyleNames("v-button-customstyle", "audit-AuditSearchClearDesktop");
			clearParamSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"odometer-OdometerSearchClearPhone");
			mainView.getTitle().setValue(userService.getLoggedInUserName());
			removeComponent(header);
		} else if (width > 600 && width <= 1000) {
			treeNodeSearch.setHeight(32, Unit.PIXELS);
			overRideParamSearch.setHeight(32, Unit.PIXELS);
			clearParamSearch.removeStyleNames("audit-AuditSearchClearDesktop", "odometer-OdometerSearchClearPhone");
			clearParamSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
			mainView.getTitle().setValue("gemView");
			addComponentAsFirst(header);
		} else {
			treeNodeSearch.setHeight(37, Unit.PIXELS);
			overRideParamSearch.setHeight(37, Unit.PIXELS);
			clearParamSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
			clearParamSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearDesktop");
			mainView.getTitle().setValue("gemView");
			addComponentAsFirst(header);
		}
		
		configureTreeNodeSearch();
		
			Permission personalizationPermission = roleService.getLoggedInUserRolePermissions().stream().filter(check -> check.getPageName().equals("PERSONALIZATION")).findFirst().get();
			/*createEntity.setEnabled(personalizationPermission.getAdd());
			copyEntity.setEnabled(personalizationPermission.getEdit());
			editEntity.setEnabled(personalizationPermission.getEdit());
			deleteEntity.setEnabled(personalizationPermission.getDelete());*/
			save.setEnabled(personalizationPermission.getAdd() || personalizationPermission.getEdit());
			cancel.setEnabled(personalizationPermission.getAdd() || personalizationPermission.getEdit());
			
//			personlizationTabSheet.addSelectedTabChangeListener(listener->{
//				if(listener.getTabSheet().getCaption().equals("Parameters")) {
//					appDropDownParam.setValue(selectedApp);
//					profileDropDownParam.setValue(selectedProfile);
//				}
//			});
			
	  }catch(Exception ex){
		  personalizationService.logPersonlizationStoreScreenErrors(ex);
	  }
	  
	  mainView.getPersonlization().setEnabled(true);
	}

	private void configureTreeNodeSearch() {
		treeNodeSearch.addValueChangeListener(changed -> {
			if (changed.getValue().length() == 50) {
				Notification search = Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
				search.addCloseListener(listener->{
					String valueInLower = changed.getValue().toLowerCase();
					if (!valueInLower.isEmpty() && valueInLower != null) {
						nodeTree.setTreeData(treeDataNodeService.searchTreeData(valueInLower));
					} else {
						nodeTree.setTreeData(treeDataNodeService.getTreeData());
					}
				});
			} else {
				String valueInLower = changed.getValue().toLowerCase();
				if (!valueInLower.isEmpty() && valueInLower != null) {
					nodeTree.setTreeData(treeDataNodeService.searchTreeData(valueInLower));
				} else {
					nodeTree.setTreeData(treeDataNodeService.getTreeData());
				}
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
		clearSearch.addClickListener(click ->treeNodeSearch.clear());
	}

	public Panel getAndLoadPersonlizationPanel() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setResponsive(true);
		panel.setSizeFull();
		addComponent(header);
		addComponent(panel);
		setExpandRatio(header, 1);
        setExpandRatio(panel, 14);
		return panel;
	}

	private void getEntityButtonsList(HorizontalLayout treeSearchLayout) {
		treeClearSearchLayout = new CssLayout();
		treeClearSearchLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		treeClearSearchLayout.addComponents(treeNodeSearch,clearSearch);
		treeClearSearchLayout.setWidth("85%");
		
		treeSearchLayout.addComponents(treeClearSearchLayout);
	}

	private void getEntityInformation(HorizontalSplitPanel splitScreen) throws ApiException {
		VerticalLayout entityInformationLayout = new VerticalLayout();
		entityInformationLayout.addStyleName("personlization-verticalFormLayout");
		entityInformationLayout.setWidth("100%");
		HorizontalLayout saveCancelEntityInfoLabelLayout = new HorizontalLayout();
		HorizontalLayout saveCancelButtonLayout = new HorizontalLayout();
		saveCancelEntityInfoLabelLayout.setSizeFull();
		
		entityLayout = new VerticalLayout();
		entityLayout.setWidth("100%");
		entityLayout.addStyleNames("heartbeat-secondComponent");
		
		entityGridLayout = new VerticalLayout();
		entityGridLayout.setWidth("100%");
		entityGridLayout.setHeight("100%");
		entityGridLayout.addStyleNames("personlization-Parameters", "heartbeat-secondComponent");
		
		entityFileLayout= new VerticalLayout();
		entityFileLayout.setWidth("100%");
		entityFileLayout.setHeight("100%");
		entityFileLayout.addStyleNames("personlization-verticalFormLayout", "heartbeat-secondComponent");
		
		entityApplicationLayout = new VerticalLayout();
		entityApplicationLayout.setWidth("100%");
		entityApplicationLayout.setHeight("100%");
		entityApplicationLayout.addStyleNames("personlization-Parameters", "heartbeat-secondComponent",
				"personlization-verticalFormLayout");

		cancel = new Button("Cancel", click -> {
			ClearAllComponents();
			ClearGrid();
			entityLayout.setEnabled(false);
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
				if(node.getLabel().equals(entityName.getValue()) && 
						node.getDescription().equals(entityDescription.getValue()) && node.isActive()==activeBox.getValue() && 
						node.isHeartBeat()==activateHeartbeat.getValue() && node.getFrequency()==frequencyDropDown.getValue()) {
					
				}else {
					node.setType(entityType.getValue());
					node.setLabel(entityName.getValue());
					node.setHeartBeat(activateHeartbeat.getValue());
					node.setFrequency(frequencyDropDown.getValue());
					//node.setApp(appDropDown.getValue());
					node.setProfile(profileDropDown.getValue());
					node.setDescription(entityDescription.getValue());
					node.setActive(activeBox.getValue());
					node.setSerialNum(entitySerialNum.getValue());
					node.setOverRideParamList(
							overRideParamGrid.getDataProvider().fetch(new Query<>()).collect(Collectors.toList()));
					overRideParamGrid.setDataProvider(new ListDataProvider<>(node.getOverRideParamList()));
					overRideParamGrid.getDataProvider().refreshAll();
					overRideParamGrid.deselectAll();
					
					// Calling the service
					personalizationService.updateEntity(node);
					entityLayout.setEnabled(false);
					nodeTree.deselectAll();
					nodeTree.getDataProvider().refreshAll();
					
				}
			} else {
				node = new TreeNode();
			
			if (ComponentUtil.validatorComboBox(entityType) && ComponentUtil.validatorTextField(entityName) && 
					ComponentUtil.validatorTextField(entityDescription)) {
				if(nodeTree.getSelectedItems().iterator().next().getType().toString().equals("TERMINAL") || 
					nodeTree.getSelectedItems().iterator().next().getType().toString().equals("DEVICE")) {
					if(ComponentUtil.validatorTextField(entitySerialNum)){
						
					}
				}
				node.setType(entityType.getValue());
				node.setLabel(entityName.getValue());
				node.setHeartBeat(activateHeartbeat.getValue());
				node.setFrequency(frequencyDropDown.getValue());
				//node.setApp(appDropDown.getValue());
				node.setProfile(profileDropDown.getValue());
				node.setDescription(entityDescription.getValue());
				node.setActive(activeBox.getValue());
				node.setSerialNum(entitySerialNum.getValue());
				node.setOverRideParamList(
						overRideParamGrid.getDataProvider().fetch(new Query<>()).collect(Collectors.toList()));
				overRideParamGrid.setDataProvider(new ListDataProvider<>(node.getOverRideParamList()));
				overRideParamGrid.getDataProvider().refreshAll();
				overRideParamGrid.deselectAll();
				
				// Calling the service
				personalizationService.updateEntity(node);
				entityLayout.setEnabled(false);
				} 
			}
		});
		save.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		save.addStyleName("v-button-customstyle");
		save.setResponsive(true);
		saveCancelButtonLayout.addComponent(save);

		saveCancelButtonLayout.setComponentAlignment(cancel, Alignment.MIDDLE_RIGHT);
		saveCancelButtonLayout.setComponentAlignment(save, Alignment.MIDDLE_RIGHT);
		saveCancelButtonLayout.setResponsive(true);
		saveCancelButtonLayout.setStyleName("save-cancelButtonsAlignment");
		saveCancelEntityInfoLabelLayout.addComponent(saveCancelButtonLayout);

		entityInformationLayout.addComponents(saveCancelEntityInfoLabelLayout, getTabSheet());

		splitScreen.addComponent(entityInformationLayout);
		splitScreen.addStyleName("personlization-SplitBackground");
	}
	
	private TabSheet getTabSheet() throws ApiException {
		personlizationTabSheet = new TabSheet();
		//heartbeatTabSheet.setHeight("100%");
		personlizationTabSheet.addStyleName("applicationStore-TabLayout");
		personlizationTabSheet.addStyleNames(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS, ValoTheme.TABSHEET_CENTERED_TABS,
				ValoTheme.TABSHEET_ICONS_ON_TOP, ValoTheme.TABSHEET_COMPACT_TABBAR, ValoTheme.TABSHEET_PADDED_TABBAR);
		personlizationTabSheet.addTab(getEntityLayout(entityLayout), "Details");
		personlizationTabSheet.addTab(getApplicationSelection(entityApplicationLayout),"Applications");
		personlizationTabSheet.addTab(getOverRideParamGrid(entityGridLayout, selectedApp, selectedProfile),"Parameters");
		personlizationTabSheet.addTab(getFileSelection(entityFileLayout),"Files");
		
		return personlizationTabSheet;
	}

	private VerticalLayout getEntityLayout(VerticalLayout entityLayout) {

		getEntityType(entityLayout);

		getEntityName(entityLayout);

		getDeviceDropdown(entityLayout);

		getEntityDescription(entityLayout);

		getEntityActive(entityLayout);

		getEntitySerialNum(entityLayout);

		getEntityHeartBeat(entityLayout);

		getEntityFrequency(entityLayout);

		/*getApplicationSelection(entityLayout);

		getScheduleUpdate(entityLayout);*/
		
		return entityLayout;
	}

	private void getEntityType(VerticalLayout entityFormLayout) {
		entityType = new ComboBox<EntityTypeEnum>();
		entityType.setCaption("Entity Type");
		entityType.setWidth("100%");
		entityType.setDataProvider(new ListDataProvider<>(
				Arrays.asList(EntityTypeEnum.ENTERPRISE, EntityTypeEnum.REGION, EntityTypeEnum.MERCHANT,
						EntityTypeEnum.TERMINAL, EntityTypeEnum.DEVICE, EntityTypeEnum.ORGANIZATION)));
		entityType.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "textfiled-height");
		entityType.setEnabled(false);

		entityFormLayout.addComponent(entityType);
	}

	private void getEntityName(VerticalLayout entityFormLayout) {

		entityName = new TextField("Name");
		entityName.setWidth("100%");
		entityName.addStyleNames("v-textfield-font", "textfiled-height");
		entityName.setMaxLength(50);
		entityName.addValueChangeListener(listener->{
			if(listener.getValue().length()==50) {
			Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});
		entityFormLayout.addComponent(entityName);
	}

	private void getDeviceDropdown(VerticalLayout entityFormLayout) {

		deviceDropDown = new ComboBox<Devices>();
		deviceDropDown.setCaption("Devices");
		deviceDropDown.setPlaceholder("Select Device");
		deviceDropDown.setDataProvider(personalizationService.getAllDevices());
		deviceDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size",
				"personlization-formAlignment", "small");
	}

	private void getEntityDescription(VerticalLayout entityFormLayout) {

		entityDescription = new TextField("Description");
		entityDescription.addStyleNames("v-textfield-font", "textfiled-height");
		entityDescription.setWidth("100%");
		entityDescription.setMaxLength(50);
		entityDescription.addValueChangeListener(listener->{
			if(listener.getValue().length()==50) {
				Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});

		entityFormLayout.addComponent(entityDescription);
	}

	private void getEntityActive(VerticalLayout entityFormLayout) {

		/*HorizontalLayout entityActiveLayout = new HorizontalLayout();
		Label active = new Label("Active");
		active.setStyleName("role-activeLable");
		active.addStyleNames("v-textfield-font", "role-activeLabel");
		activeCheckbox = new CheckBox();
		activeCheckbox.setCaption("Entity Active");
		activeCheckbox.addStyleNames("v-textfield-font", "personlization-activeCheckbox");

		entityActiveLayout.addComponents(active, activeCheckbox);
		entityActiveLayout.setStyleName("role-activeLable");
		entityActiveLayout.addStyleNames("assetAlert-activeCheckBox", "personlization-formAlignment",
				"personlization-activeLayout");*/
		
		activeBox = new ComboBox<>();
		activeBox.setCaption("Active");
		activeBox.setWidth("100%");
		activeBox.setItems(true, false);
		activeBox.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "textfiled-height");

		entityFormLayout.addComponent(activeBox);
	}

	private void getEntitySerialNum(VerticalLayout entityFormLayout) {
		entitySerialNum = new TextField("Serial Num");
		entitySerialNum.addStyleNames("v-textfield-font", "textfiled-height");
		entitySerialNum.setWidth("100%");
		entitySerialNum.setMaxLength(50);
		entitySerialNum.addValueChangeListener(listener->{
			if(listener.getValue().length()==50) {
				Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});

		entityFormLayout.addComponent(entitySerialNum);
	}

	private void getEntityHeartBeat(VerticalLayout entityFormLayout) {

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

	private void getEntityFrequency(VerticalLayout entityFormLayout) {

		frequencyDropDown = new ComboBox<>();
		frequencyDropDown.setDataProvider(new ListDataProvider<>(
				Arrays.asList("120 Seconds", "90 Seconds", " 30 Seconds", "Daily", "Monthly", "Never")));
		frequencyDropDown.setCaption("Frequency");
		frequencyDropDown.setWidth("100%");
		frequencyDropDown.setPlaceholder("Frequency");
		frequencyDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "textfiled-height");

		entityFormLayout.addComponent(frequencyDropDown);
	}

	private VerticalLayout getApplicationSelection(VerticalLayout entityFormLayout) {

		HorizontalLayout entityApplicationSelection = new HorizontalLayout();
		entityApplicationSelection.addStyleNames("personlization-AppAndProfileAlignment");
		appDropDown = new ComboBox<AppClient>();
		appDropDown.setCaption("");
		appDropDown.setPlaceholder("Application");
		appDropDown.setWidth("100%");
		appDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "textfiled-height");
		
		appDropDown.addSelectionListener(selected -> {
			if(nodeTree.getSelectedItems().size()<=0) {
				Notification.show(NotificationUtil.PERSONALIZATION_APP_SELECTION, Type.ERROR_MESSAGE);
			}else {
			selectedApp = selected.getValue();
			profileDropDown.clear();
			selectedProfile=null;
			if(selectedApp!=null) {
				profileDropDown.setDataProvider(new ListDataProvider<>(personalizationService.getProfileListForEntity(selectedApp.getId(), selectedNode.getId())));
				parameterType.setDataProvider(new ListDataProvider<>(appStoreService.getAppParamTypeList(selectedApp.getId())));
			}else {
				profileDropDown.setItems(new ArrayList());
			}
			}
		});
		
		/*addtnlFilesDropDown = new ComboBox<>();
		addtnlFilesDropDown.setCaption("");
		addtnlFilesDropDown.setPlaceholder("Additional Files");
		addtnlFilesDropDown.setDescription("Select Files");
		addtnlFilesDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "small");*/

		profileDropDown = new ComboBox<Profile>();
		profileDropDown.setCaption("");
		profileDropDown.setPlaceholder("Default Profile");
		profileDropDown.setDescription("Select Profile");
		profileDropDown.setWidth("100%");
		profileDropDown.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "textfiled-height");
		
		selectProfile = new Button("", VaadinIcons.TWIN_COL_SELECT);
		selectProfile.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY, "personlization-plusButtons");
		selectProfile.setDescription("Select Profiles");
		selectFile = new Button("", VaadinIcons.TWIN_COL_SELECT);
		selectFile.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY, "personlization-plusButtons");
		selectFile.setDescription("Select Files");
		
		selectProfile.addClickListener(click ->{
			if(selectedApp != null) {
				selectedProfile=null;
				UI.getCurrent().addWindow(openProfileTwinListToAddWindow());
				profileDropDown.clear();
			}else {
				Notification.show("Please select app to add profile to entity", Type.ERROR_MESSAGE);
			}
		});
		
		/*selectFile.addClickListener(click ->{
			if(selectedProfile!=null) {
				UI.getCurrent().addWindow(openProfileTwinListToAddWindow());
				addtnlFilesDropDown.clear();
			}
		});*/
		
		
		profileDropDown.addSelectionListener(listener->{
			if(profileDropDown.getSelectedItem().isPresent()) {
			/*selectedProfile = profileDropDown.getSelectedItem().get();
			addParam.setEnabled(true);
			deleteParam.setEnabled(true);
			List<AppDefaultParam> fileListWithEntityForDropDown = personalizationService.getFileListWithEntity(selectedProfile.getId(), selectedNode.getId());
			DataProvider dataFromEntityForDropDown = new ListDataProvider<>(fileListWithEntityForDropDown);
			addtnlFilesDropDown.clear();
			addtnlFilesDropDown.setDataProvider(dataFromEntityForDropDown);
			overRideParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(personalizationService.getAppDefaultParamListWithEntity(selectedProfile.getId(),selectedNode.getId())));*/
			}
		});
		entityApplicationSelection.addComponents(appDropDown, selectProfile, profileDropDown);
		entityFormLayout.addComponent(entityApplicationSelection);
		
		

		appGrid = new Grid<>(AppClient.class);
		appGrid.setWidth("100%");
		appGrid.setColumns("packageName", "description", "packageVersion", "profile");
		appGrid.getColumn("packageName").setCaption("Name");
		appGrid.getColumn("description").setCaption("Description");
		appGrid.getColumn("packageVersion").setCaption("Version");
		appGrid.getColumn("profile").setCaption("Profile");
		appGrid.getColumn("profile").setEditorComponent(profileListPersonlization);
		appGrid.setHeightByRows(20);
		appGrid.setSelectionMode(SelectionMode.MULTI);
		entityFormLayout.addComponent(appGrid);
		
		return entityFormLayout;
	}
	
	private VerticalLayout getFileSelection(VerticalLayout entityFormLayout) {

		fileButton = new Button("Files", VaadinIcons.UPLOAD);
		fileButton.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY);
		fileButton.setDescription("Upload Files");
		fileButton.addClickListener(click -> {
			if(appGrid.getSelectedItems().isEmpty())
				Notification.show("Select a app first",Type.ERROR_MESSAGE);
			else {
			Window fileUpload = openFileUploadWindow(appFileGrid);
			if (fileUpload.getParent() == null) {
				UI.getCurrent().addWindow(fileUpload);
			} else if (fileUpload.getComponentCount() > 0) {
				fileUpload.close();
			}
			}
		});
		
		
		fileButtonLayout = new HorizontalLayout();
		fileButtonLayout.setCaption("Upload Files");
		fileButtonLayout.addStyleName("asset-debugComboBox");
		fileButtonLayout.addComponents(fileButton);
	
		appFileGrid = new Grid<>(AppDefaultParam.class);
		appFileGrid.addStyleName("applicationStore-horizontalAlignment");
		appFileGrid.setColumns("parameter", "description");
		appFileGrid.setWidth("100%");
		appFileGrid.setHeightByRows(18);
		appFileGrid.setSelectionMode(SelectionMode.MULTI);
		Button deleteAppFileGridRowMenu = new Button("Delete File", clicked -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			if (appGrid.getSelectedItems().isEmpty() || appFileGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.APPLICATIONSTORE_DELETE, Notification.Type.ERROR_MESSAGE);
			} else {
				confirmDeleteAppProfileFiles();
			}

		});
		deleteAppFileGridRowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		ContextMenuWindow paramContextWindow = new ContextMenuWindow();
		paramContextWindow.addMenuItems(deleteAppFileGridRowMenu);
		appFileGrid.addContextClickListener(click ->{
			UI.getCurrent().getWindows().forEach(Window::close);
			paramContextWindow.setPosition(click.getClientX(), click.getClientY());
			UI.getCurrent().addWindow(paramContextWindow);
		});
		
		UI.getCurrent().addClickListener(listener->{
			paramContextWindow.close();
		});
		
		entityFormLayout.addComponents(fileButtonLayout, appFileGrid);
		return entityFormLayout;
		
	}
	
	private Window openFileUploadWindow(Grid<AppDefaultParam> optionList) {
		LineBreakCounter lineBreakCounter = new LineBreakCounter();
		lineBreakCounter.setSlow(true);

		FileUploadReceiver fileUploadReceiver = new FileUploadReceiver();

		Upload uploadFile = new Upload("Upload File here", lineBreakCounter);
		uploadFile.setImmediateMode(false);
		uploadFile.setButtonCaption("Upload File");
		uploadFile.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle", "applicationStore-UploadButton");
		uploadFile.setReceiver(fileUploadReceiver);
		uploadFile.setDescription("Upload File");

		Window fileUploadWindow = new Window("File Upload", uploadFile);
		UploadInfoWindow uploadInfoWindow = new UploadInfoWindow(uploadFile, lineBreakCounter, fileUploadReceiver,
				optionList, fileUploadWindow,appStoreService);

		uploadFile.addStartedListener(event -> {
			if (uploadInfoWindow.getParent() == null) {
				UI.getCurrent().addWindow(uploadInfoWindow);
			}
			uploadInfoWindow.setClosable(false);
		});
		uploadFile.addFinishedListener(event -> uploadInfoWindow.setClosable(true));
		fileUploadWindow.addStyleName("applicationStore-UploadWindow");
		fileUploadWindow.setWidth(30, Unit.PERCENTAGE);
		fileUploadWindow.setHeight(10, Unit.PERCENTAGE);
		fileUploadWindow.setClosable(true);
		fileUploadWindow.setDraggable(true);
		fileUploadWindow.setResizable(false);
		fileUploadWindow.setModal(true);
		fileUploadWindow.center();
		return fileUploadWindow;
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
			
			/*if(addProfileValue && optionListFromEntity.getSelectedItems().isEmpty()) {
				Notification notification =  Notification.show(NotificationUtil.PERSONALIZATION_ADDING_PROFILES, Type.ERROR_MESSAGE);
				notification.setPosition(Position.TOP_CENTER);
				
			}else*/ if(addProfileValue){
				if(existingAppProfileList.contains(existingAppProfileEntityList)) {
					existingAppProfileList.remove(existingAppProfileEntityList);
				}
					//List<Profile> profileListForUpdate = optionListFromEntity.getValue().stream().collect(Collectors.toList());
					personalizationService.saveProfileForEntity(existingAppProfileList,selectedNode.getId(), selectedApp.getId());
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

	private VerticalLayout getOverRideParamGrid(VerticalLayout entityGridLayout, AppClient app, Profile profile) {
		HorizontalLayout overRideParamLabelLayout = new HorizontalLayout();
		overRideParamLabelLayout.addStyleName("personlization-overRideParamLabelLayout");
		HorizontalLayout overRideParamSearchButtonLayout = new HorizontalLayout();
		overRideParamSearchButtonLayout.setWidth("100%");
		overRideParamSearchButtonLayout.addStyleName("personlization-paramButtonLayout");

		HorizontalLayout overRideParamSearchLayout = new HorizontalLayout();
		overRideParamSearchLayout.setWidth("100%");
		HorizontalLayout overRideParamButtonLayout = new HorizontalLayout();
		HorizontalLayout overRideParamAppAndParamLayout = new HorizontalLayout();

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
		overRideParamSearch.setMaxLength(50);
		
		overrideParamSearchCSSLayout = new CssLayout();
		overrideParamSearchCSSLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		overrideParamSearchCSSLayout.setWidth("90%");
		
		clearParamSearch = new Button(VaadinIcons.CLOSE);
		clearParamSearch.addClickListener(listener->{
			overRideParamSearch.clear();
		});
		overrideParamSearchCSSLayout.addComponents(overRideParamSearch, clearParamSearch);
		
		
		overRideParamSearchLayout.addComponent(overrideParamSearchCSSLayout);
		
		appDropDownParam = new ComboBox<AppClient>();
		if(appDropDownParam!=null) {
			appDropDownParam.clear();
		}
		appDropDownParam.setCaption("");
		appDropDownParam.setPlaceholder("Application");
		appDropDownParam.setWidth("100%");
		appDropDownParam.setValue(app);
		appDropDownParam.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "textfiled-height"
				, "personlization-ParametersComboBox");
		
		profileDropDownParam = new ComboBox<Profile>();
		profileDropDownParam.setCaption("");
		profileDropDownParam.setPlaceholder("Default Profile");
		profileDropDownParam.setDescription("Select Profile");
		profileDropDownParam.setWidth("100%");
		profileDropDownParam.setValue(profile);
		profileDropDownParam.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "textfiled-height",
				"personlization-ParametersComboBox");
		
		appDropDownParam.addSelectionListener(selected -> {
			
			selectedApp = (AppClient) selected.getValue();
			profileDropDownParam.clear();
			selectedProfile=null;
			if(selectedApp!=null) {
				profileDropDownParam.setDataProvider(new ListDataProvider<>(personalizationService.getProfileListWithOutEntity(selectedApp.getId(), selectedNode.getId())));
				parameterType.setDataProvider(new ListDataProvider<>(appStoreService.getAppParamTypeList(selectedApp.getId())));
			}else {
				profileDropDownParam.setItems(new ArrayList());
			}
		});
		
		profileDropDownParam.addSelectionListener(listener->{
			if(profileDropDownParam.getSelectedItem().isPresent()) {
			selectedProfile = profileDropDown.getSelectedItem().get();
			/*addParam.setEnabled(true);
			deleteParam.setEnabled(true);
			List<AppDefaultParam> fileListWithEntityForDropDown = personalizationService.getFileListWithEntity(selectedProfile.getId(), selectedNode.getId());
			DataProvider dataFromEntityForDropDown = new ListDataProvider<>(fileListWithEntityForDropDown);
			addtnlFilesDropDown.clear();
			addtnlFilesDropDown.setDataProvider(dataFromEntityForDropDown);
			overRideParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(personalizationService.getAppDefaultParamListWithEntity(selectedProfile.getId(),selectedNode.getId())));*/
			}
		});
		
		overRideParamAppAndParamLayout.addComponents(appDropDownParam,profileDropDownParam);
		
		addParam = new Button("", VaadinIcons.GRID);
		addParam.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY);
		addParam.setDescription("Select Profiles");
		addParam.setEnabled(true);
		
		addParam.addClickListener(click ->{
			if(selectedProfile != null) {
				UI.getCurrent().addWindow(openProfileParamGridAddWindow());
			}else {
				Notification.show("Select any Profile to add Override Parameters", Type.ERROR_MESSAGE);
			}
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
		overRideParamButtonLayout.addComponents(addParam);

		overRideParamSearchButtonLayout.addComponents(overRideParamSearchLayout, overRideParamButtonLayout, overRideParamAppAndParamLayout);

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
			if(valueChange.getValue().length()==50) {
				Notification search = Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
				search.addCloseListener(listener->{
					if(selectedApp!=null) {
						String valueInLower = valueChange.getValue().toLowerCase();
						List<AppDefaultParam> appDefaultParamList = appStoreService.searchParams(selectedApp.getId(), valueInLower);
						DataProvider data = new ListDataProvider(appDefaultParamList);
						overRideParamGrid.setDataProvider(data);
					}
				});
			}else {
				if(selectedApp!=null) {
					String valueInLower = valueChange.getValue().toLowerCase();
					List<AppDefaultParam> appDefaultParamList = appStoreService.searchParams(selectedApp.getId(), valueInLower);
					DataProvider data = new ListDataProvider(appDefaultParamList);
					overRideParamGrid.setDataProvider(data);
				}
			}
			
			/*ListDataProvider<AppDefaultParam> paramDataProvider = (ListDataProvider<AppDefaultParam>) overRideParamGrid
					.getDataProvider();
			paramDataProvider.setFilter(filter -> {
				String parameterInLower = filter.getParameter().toLowerCase();
				String descriptionInLower = filter.getDescription().toLowerCase();
				String typeLower = filter.getType().toLowerCase();
				String value = filter.getValue().toLowerCase();
				return descriptionInLower.equals(valueInLower) || parameterInLower.contains(valueInLower)
						|| typeLower.contains(valueInLower) || value.contains(valueInLower);
			});*/
		});

		entityGridLayout.addComponents(overRideParamSearchButtonLayout,
				overRideParamGrid);
		
		return entityGridLayout;
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

	private ContextMenuWindow openEntityWindow() {
		ContextMenuWindow entityWindow = new ContextMenuWindow();
		ComboBox<EntityTypeEnum> entityTypeTree = new ComboBox<EntityTypeEnum>();
		entityTypeTree.setCaption("Entity Type");
		entityTypeTree.setDataProvider(new ListDataProvider<>(
				Arrays.asList(EntityTypeEnum.ENTERPRISE, EntityTypeEnum.ORGANIZATION, EntityTypeEnum.REGION, EntityTypeEnum.MERCHANT,
						EntityTypeEnum.TERMINAL, EntityTypeEnum.DEVICE)));
		entityTypeTree.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "textfiled-height");
		entityTypeTree.setWidth("100%");
		
		TextField entityName = new TextField("Name");
		entityName.addStyleNames("v-textfield-font", "textfiled-height");
		entityName.focus();
		entityName.setWidth("100%");
		
		TextField entityDescription = new TextField("Description");
		entityDescription.addStyleNames("v-textfield-font", "textfiled-height");
		entityDescription.setWidth("100%");
		
		TextField entitySerialNum = new TextField("Serial Number");
		entitySerialNum.addStyleNames("v-textfield-font", "textfiled-height");
		entitySerialNum.setWidth("100%");
		entitySerialNum.setEnabled(false);
		
		entityTypeTree.addSelectionListener(selected -> {
			if(entityTypeTree.getValue().toString().equals("TERMINAL") || entityTypeTree.getValue().toString().equals("DEVICE")) {
				entitySerialNum.setEnabled(true);
			}else {
				entitySerialNum.setId("ignore");
			}
		});

		Button saveProfile = new Button("Save", click -> {
			if (nodeTree.getSelectedItems().size() == 1) {
				if (ComponentUtil.validatorComboBox(entityTypeTree) && ComponentUtil.validatorTextField(entityName)
						&& ComponentUtil.validatorTextField(entityDescription) &&
						ComponentUtil.validatorTextField(entitySerialNum)) {
					TreeNode selectedNode = nodeTree.getSelectedItems().iterator().next();
					TreeNode newNode = new TreeNode();
					newNode.setLabel(entityName.getValue().isEmpty() ? "Child Node" : entityName.getValue());// Pick
																												// name
					newNode.setDescription(entityDescription.getValue()); // textfield
					if (entitySerialNum != null && !entitySerialNum.isEmpty()) {
						newNode.setSerialNum(entitySerialNum.getValue());
					}
					// REST code for adding new entity
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
							personalizationService.createEntity(selectedNode, newNode);
							nodeTree.setTreeData(personalizationService.getTreeData());
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
							personalizationService.createEntity(selectedNode, newNode);
							nodeTree.setTreeData(personalizationService.getTreeData());
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
							personalizationService.createEntity(selectedNode, newNode);
							nodeTree.setTreeData(personalizationService.getTreeData());
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
							personalizationService.createEntity(selectedNode, newNode);
							nodeTree.setTreeData(personalizationService.getTreeData());
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
		});

		saveProfile.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		Button cancelProfile = new Button("Reset", click -> {
			entityName.clear();
			entityDescription.clear();
		});
		cancelProfile.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		HorizontalLayout buttonLayout = new HorizontalLayout(saveProfile, cancelProfile);
		VerticalLayout entityFieldsLayout = new VerticalLayout(entityTypeTree, entityName, entityDescription, entitySerialNum);
		VerticalLayout verticalLayout = new VerticalLayout(entityFieldsLayout, buttonLayout);
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

	private void ClearAllComponents() {
		entityName.clear();
		entityDescription.clear();
		entityType.clear();
		deviceDropDown.clear();
		//activeCheckbox.clear();
		entitySerialNum.clear();
		activateHeartbeat.clear();
		frequencyDropDown.clear();
		activeBox.clear();
		//appDropDown.clear();
		//addtnlFilesDropDown.clear();
		//profileDropDown.clear();
		//updateDropDown.clear();
	}

	private void ClearGrid() {
		overRideParamGrid.setDataProvider(new ListDataProvider<>(Arrays.asList()));
		appGrid.setDataProvider(new ListDataProvider<>(Arrays.asList()));
		appFileGrid.setDataProvider(new ListDataProvider<>(Arrays.asList()));
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
			parameterName.setMaxLength(50);
			parameterName.addValueChangeListener(valueChange -> {
				if(valueChange.getValue().length()==50) {
					Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
				}
			});

			parameterDescription = new TextField("Description");
			parameterDescription.setStyleName("role-textbox");
			parameterDescription.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS, "v-textfield-font", "v-grid-cell");
			parameterDescription.setMaxLength(50);
			parameterDescription.addValueChangeListener(valueChange -> {
				if(valueChange.getValue().length()==50) {
					Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
				}
			});
			
			parameterType.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "small");

			parameterValue = new TextField("Value");
			parameterValue.setStyleName("role-textbox");
			parameterValue.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS, "v-textfield-font", "v-grid-cell");
			parameterValue.setMaxLength(50);
			parameterValue.addValueChangeListener(valueChange -> {
				if(valueChange.getValue().length()==50) {
					Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
				}
			});
			
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
	
	private static class LineBreakCounter implements Receiver {
		private int counter;
		private int total;
		private boolean sleep;

		/**
		 * return an OutputStream that simply counts line ends
		 */
		@Override
		public OutputStream receiveUpload(final String filename, final String MIMEType) {
			counter = 0;
			total = 0;

			// Instead of linebreaker use a new receiver for getting the uploaded file.
			return new OutputStream() {
				private static final int searchedByte = '\n';

				@Override
				public void write(final int b) {
					total++;
					if (b == searchedByte) {
						counter++;
					}
					if (sleep && total % 1000 == 0) {
						try {
							Thread.sleep(100);
						} catch (final InterruptedException e) {
							e.printStackTrace();
							
						}
					}

				}
			};
		}

		private int getLineBreakCount() {
			return counter;
		}

		private void setSlow(boolean value) {
			sleep = value;
		}
	}
	
	private static class FileUploadReceiver implements Receiver {
		private ApplicationFile file;

		/**
		 * return an OutputStream that simply counts line ends
		 */
		@Override
		public OutputStream receiveUpload(final String filename, final String MIMEType) {
			// Create upload stream
			FileOutputStream fos = null; // Stream to write to
			try {
				
				file = new ApplicationFile(System.getProperty("java.io.tmpdir")+File.separator+filename);
				ApplicationStoreView.applicationFilePath = file.getAbsolutePath();
				fos = new FileOutputStream(file);
			} catch (final java.io.FileNotFoundException e) {
				new Notification("Could not open file: ", e.getMessage(), Notification.Type.ERROR_MESSAGE)
						.show(Page.getCurrent());
				return null;
			}
			return fos;
		}

	}
	
	private static class UploadInfoWindow extends Window implements Upload.StartedListener, Upload.ProgressListener,
	Upload.FailedListener, Upload.SucceededListener, Upload.FinishedListener {
private final Label state = new Label();
private final Label result = new Label();
private final Label fileName = new Label();
private final Label textualProgress = new Label();

private final ProgressBar progressBar = new ProgressBar();
private final Button cancelButton;
private final LineBreakCounter counter;
private final FileUploadReceiver fileUploadReceiver;
private final Window uploadWindow;
private final ApplicationStoreService applicationStoreService;

private final Grid<AppDefaultParam> optionList;

private UploadInfoWindow(final Upload upload, final LineBreakCounter lineBreakCounter,
		final FileUploadReceiver fileUploadReceiver, final Grid<AppDefaultParam> optionList, Window uploadWindow,final ApplicationStoreService applicationStoreService) {
	super("Status");
	this.counter = lineBreakCounter;
	this.fileUploadReceiver = fileUploadReceiver;
	this.optionList = optionList;
	addStyleName("upload-info");
	this.uploadWindow = uploadWindow;
	this.applicationStoreService = applicationStoreService;

	setResizable(false);
	setDraggable(false);

	final FormLayout uploadInfoLayout = new FormLayout();
	setContent(uploadInfoLayout);
	uploadInfoLayout.setMargin(true);

	final HorizontalLayout stateLayout = new HorizontalLayout();
	stateLayout.setSpacing(true);
	stateLayout.addComponent(state);

	cancelButton = new Button("Cancel");
	cancelButton.addClickListener(event -> upload.interruptUpload());
	cancelButton.setVisible(false);
	cancelButton.setStyleName("small");
	stateLayout.addComponent(cancelButton);

	stateLayout.setCaption("Current state");
	state.setValue("Idle");
	uploadInfoLayout.addComponent(stateLayout);

	fileName.setCaption("File name");
	uploadInfoLayout.addComponent(fileName);

	result.setCaption("Line breaks counted");
	uploadInfoLayout.addComponent(result);

	progressBar.setCaption("Progress");
	progressBar.setVisible(false);
	uploadInfoLayout.addComponent(progressBar);

	textualProgress.setVisible(false);
	uploadInfoLayout.addComponent(textualProgress);

	upload.addStartedListener(this);
	upload.addProgressListener(this);
	upload.addFailedListener(this);
	upload.addSucceededListener(this);
	upload.addFinishedListener(this);

}

@Override
public void uploadFinished(final FinishedEvent event) {
	state.setValue("Idle");
	progressBar.setVisible(false);
	textualProgress.setVisible(false);
	cancelButton.setVisible(false);
}

@Override
public void uploadStarted(final StartedEvent event) {
	// this method gets called immediately after upload is started
	progressBar.setValue(0f);
	progressBar.setVisible(true);
	UI.getCurrent().setPollInterval(500);
	textualProgress.setVisible(true);
	
	// updates to client
	state.setValue("Uploading");
	fileName.setValue(event.getFilename());
	cancelButton.setVisible(true);
}

@Override
public void updateProgress(final long readBytes, final long contentLength) {
	// this method gets called several times during the update
	progressBar.setValue(readBytes / (float) contentLength);
	textualProgress.setValue("Processed " + readBytes + " bytes of " + contentLength);
	result.setValue(counter.getLineBreakCount() + " (counting...)");
}

@Override
public void uploadSucceeded(final SucceededEvent event) {
	result.setValue(counter.getLineBreakCount() + " (total)");
	
	if (optionList.getId().equalsIgnoreCase(FILE_CHOOSE_LIST)) {
		applicationStoreService.uploadAppFiles(ApplicationStoreView.selectedApp.getId(), "Description111", ApplicationStoreView.applicationFilePath);
		optionList.setDataProvider(new ListDataProvider<AppDefaultParam>(applicationStoreService.getAllAppFileList(ApplicationStoreView.selectedApp.getId())));
	} else {

	}
	// close the window
	close();
	uploadWindow.close();
}

@Override
public void uploadFailed(final FailedEvent event) {
	result.setValue(counter.getLineBreakCount() + " (counting interrupted at "
			+ Math.round(100 * progressBar.getValue()) + "%)");
		}
	}
	private void confirmDeleteAppProfileFiles() {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?", "Ok", "Cancel",
				new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							for(AppDefaultParam file : appFileGrid.getSelectedItems()) {
								appStoreService.deleteAppFiles(appGrid.getSelectedItems().iterator().next().getId(), file.getId());
							}
						} else {
							// User did not confirm

						}
					}
				});
	}
}
