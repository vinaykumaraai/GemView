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
package com.luretechnologies.tms.ui.view.user;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;

import com.luretechnologies.client.restlib.service.model.EntityTypeEnum;
import com.luretechnologies.tms.backend.data.entity.Audit;
import com.luretechnologies.tms.backend.data.entity.Permission;
import com.luretechnologies.tms.backend.data.entity.Role;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.service.AuditService;
import com.luretechnologies.tms.backend.service.CommonService;
import com.luretechnologies.tms.backend.service.RolesService;
import com.luretechnologies.tms.backend.service.TreeDataNodeService;
import com.luretechnologies.tms.backend.service.UserService;
import com.luretechnologies.tms.ui.MainView;
import com.luretechnologies.tms.ui.components.ComponentUtil;
import com.luretechnologies.tms.ui.components.EntityOperations;
import com.luretechnologies.tms.ui.components.MainViewIconsLoad;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.luretechnologies.tms.ui.navigation.NavigationManager;
import com.luretechnologies.tms.ui.view.ContextMenuWindow;
import com.luretechnologies.tms.ui.view.Header;
import com.luretechnologies.tms.ui.view.audit.AuditView;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TreeGrid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = UserView.VIEW_NAME)
public class UserView extends VerticalLayout implements Serializable, View {

	public static final String VIEW_NAME = "user";
	private Grid<User> userGrid;
	private TreeGrid<TreeNode> nodeTreeGrid;
	private Button deleteGridRow;
	private Button clearSearch, clearUserSearch, save, cancel, resendPassword;
	private TextField treeNodeSearch, userSearch;
	private HorizontalSplitPanel splitScreen;
	private VerticalLayout userGirdAndSearchLayout;
	private  HorizontalLayout header;
	private  ComboBox<String> roles;
	private TextField userName, firstName, lastName, email, password, ipAddress;
	private ContextMenuWindow userMenuContextWindow, createEntityWindow, editEntityWindow;
	private CheckBox userActive, fixedIp;

	private Button createEntity, editEntity, deleteEntity, copyEntity, pasteEntity, createUser, editUser, deleteUser;

	
	@Autowired
	public UserView() {

	}

	@Autowired
	private RolesService roleService;

	@Autowired
	public AuditService auditService;

	@Autowired
	public MainView mainView;

	@Autowired
	public UserService userService;

	@Autowired
	TreeDataNodeService treeDataNodeService;
	
	@Autowired
	NavigationManager navigationManager;
	
	@Autowired
	public RolesService rolesService;

	@PostConstruct
	private void init() {
		try {
			header = new Header(userService, navigationManager, "Users", new Label());
			Page.getCurrent().addBrowserWindowResizeListener(r -> {
				if (r.getWidth() <= 1400 && r.getWidth() >= 700) {
					tabMode();
					splitScreen.setSplitPosition(30);
				} else if (r.getWidth() <= 699 && r.getWidth() > 0) {
					phoneMode();

				} else {
					desktopMode();
				}

				if (r.getWidth() <= 600) {
					mainView.getTitle().setValue(userService.getLoggedInUserName());
					removeComponent(header);
					splitScreen.setSplitPosition(20);
					MainViewIconsLoad.noIconsOnDesktopMode(mainView);
					treeNodeSearch.setHeight(28, Unit.PIXELS);
					userSearch.setHeight(28, Unit.PIXELS);
				} else if (r.getWidth() > 600 && r.getWidth() <= 1000) {
					mainView.getTitle().setValue("gemView");
					addComponentAsFirst(header);
					MainViewIconsLoad.iconsOnTabMode(mainView);
					treeNodeSearch.setHeight(32, Unit.PIXELS);
					userSearch.setHeight(32, Unit.PIXELS);
				} else {
					mainView.getTitle().setValue("gemView");
					addComponentAsFirst(header);
					splitScreen.setSplitPosition(30);
					MainViewIconsLoad.noIconsOnDesktopMode(mainView);
					treeNodeSearch.setHeight(37, Unit.PIXELS);
					userSearch.setHeight(37, Unit.PIXELS);
				}
			});
			setSpacing(false);
			setMargin(false);
			setResponsive(true);
			setHeight("100%");
			treeNodeSearch = new TextField();
			treeNodeSearch.setWidth("100%");
			treeNodeSearch.setIcon(VaadinIcons.SEARCH);
			treeNodeSearch.setStyleName("small inline-icon search");
			treeNodeSearch.addStyleName("v-textfield-font");
			treeNodeSearch.setPlaceholder("Search");
			treeNodeSearch.setMaxLength(50);
			clearSearch = new Button(VaadinIcons.CLOSE);
			clearSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
			configureTreeNodeSearch();

			Panel panel = getAndLoadAuditPanel();
			VerticalLayout verticalPanelLayout = new VerticalLayout();
			verticalPanelLayout.setHeight("100%");
			verticalPanelLayout.setStyleName("split-height");
			CssLayout searchLayout = new CssLayout();
			searchLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
			searchLayout.addComponents(treeNodeSearch, clearSearch);
			searchLayout.setWidth("85%");
			VerticalLayout treeSearchPanelLayout = new VerticalLayout(searchLayout);
			treeSearchPanelLayout.setHeight("100%");
			nodeTreeGrid = new TreeGrid<TreeNode>(TreeNode.class);
			nodeTreeGrid.setHeight("100%");
			nodeTreeGrid.setTreeData(treeDataNodeService.getTreeData());
			nodeTreeGrid.addColumn(entity -> {
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
			nodeTreeGrid.setColumns("entity","serialNum");
			nodeTreeGrid.getColumn("serialNum").setCaption("Serial");
			nodeTreeGrid.setHierarchyColumn("entity");
			
			createEntity = new Button("Add Entity");
			createEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
			
			editEntity = new Button("Edit Entity");
			editEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
			
			deleteEntity = new Button("Delete Entity");
			deleteEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
			
			copyEntity = new Button("Copy Entity");
			copyEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
			
			pasteEntity = new Button("Paste Entity");
			pasteEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
			pasteEntity.setEnabled(false);
			
			ContextMenuWindow odometerTreeGridMenu = new ContextMenuWindow();
			odometerTreeGridMenu.addMenuItems(createEntity,editEntity,deleteEntity, copyEntity, pasteEntity);
			nodeTreeGrid.addContextClickListener(click->{
				UI.getCurrent().getWindows().forEach(Window::close);
				odometerTreeGridMenu.setPosition(click.getClientX(), click.getClientY());
				UI.getCurrent().addWindow(odometerTreeGridMenu);
				EntityOperations.entityOperations(nodeTreeGrid, createEntity, editEntity, deleteEntity, copyEntity, pasteEntity, treeDataNodeService, auditService, odometerTreeGridMenu);
			});
			treeSearchPanelLayout.addComponent(nodeTreeGrid);
			treeSearchPanelLayout.setExpandRatio(nodeTreeGrid, 14);
			treeSearchPanelLayout.setMargin(true);
			treeSearchPanelLayout.setStyleName("split-Height-ButtonLayout");
			verticalPanelLayout.addComponent(treeSearchPanelLayout);
			splitScreen = new HorizontalSplitPanel();
			splitScreen.setFirstComponent(verticalPanelLayout);
			splitScreen.setSplitPosition(20);
			splitScreen.addComponent(getUserGridAndSearch());
			splitScreen.setHeight("100%");
			panel.setContent(splitScreen);
			
			roles = new ComboBox<>();
			roles.setCaption("Roles");
			roles.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "textfiled-height");
			roles.setWidth("100%");
			
			nodeTreeGrid.addItemClickListener(e -> {
				List<User> userList = userService.getUsersListByEntityId(e.getItem().getEntityId());
				DataProvider data = new ListDataProvider(userList);
				userGrid.setDataProvider(data);
				userSearch.clear();
				 DataProvider rolesData = new ListDataProvider<>(rolesService.getRoleNameList());
			    roles.setDataProvider(rolesData);
			});

			
			int width = Page.getCurrent().getBrowserWindowWidth();
			if (width > 0 && width <= 699) {
				phoneMode();
				splitScreen.setSplitPosition(35);
			} else if (width >= 700 && width <= 1400) {
				tabMode();
			} else {
				desktopMode();
			}

			if (width <= 600) {
				mainView.getTitle().setValue(userService.getLoggedInUserName());
				removeComponent(header);
				splitScreen.setSplitPosition(20);
				MainViewIconsLoad.noIconsOnDesktopMode(mainView);
				treeNodeSearch.setHeight(28, Unit.PIXELS);
				userSearch.setHeight(28, Unit.PIXELS);
			} else if (width > 600 && width <= 1000) {
				mainView.getTitle().setValue("gemView");
				addComponentAsFirst(header);
				MainViewIconsLoad.iconsOnTabMode(mainView);
				treeNodeSearch.setHeight(32, Unit.PIXELS);
				userSearch.setHeight(32, Unit.PIXELS);
			} else {
				mainView.getTitle().setValue("gemView");
				addComponentAsFirst(header);
				splitScreen.setSplitPosition(30);
				MainViewIconsLoad.noIconsOnDesktopMode(mainView);
				treeNodeSearch.setHeight(37, Unit.PIXELS);
				userSearch.setHeight(37, Unit.PIXELS);
				
			}

//			Permission appStorePermission = roleService.getLoggedInUserRolePermissions().stream()
//					.filter(per -> per.getPageName().equals("AUDIT")).findFirst().get();
//			disableAllComponents();
//			allowAccessBasedOnPermission(appStorePermission.getAdd(), appStorePermission.getEdit(),
//					appStorePermission.getDelete());
		} catch (Exception ex) {
			auditService.logAuditScreenErrors(ex);
		}
		
		mainView.getAudit().setEnabled(true);
	}

	private void disableAllComponents() throws Exception {
		deleteGridRow.setEnabled(false);
	}

	private void allowAccessBasedOnPermission(Boolean addBoolean, Boolean editBoolean, Boolean deleteBoolean) {
		deleteGridRow.setEnabled(deleteBoolean);
	}

	private void tabMode() {
//		optionsLayoutVerticalTab.addStyleName("audit-tabAlignment");
//		dateDeleteLayout.addComponent(debugStartDateField);
//		dateDeleteLayout.setComponentAlignment(debugStartDateField, Alignment.TOP_RIGHT);
//		dateDeleteLayout.addComponent(debugEndDateField);
//		dateDeleteLayout.setComponentAlignment(debugEndDateField, Alignment.TOP_RIGHT);
////		dateDeleteLayout.addComponent(deleteGridRow);
////		dateDeleteLayout.setComponentAlignment(deleteGridRow, Alignment.TOP_LEFT);
//		optionsLayoutVerticalTab.addComponents(debugSearchLayout, dateDeleteLayout);
//
//		optionsLayoutHorizontalDesktop.setVisible(false);
//		optionsLayoutVerticalPhone.setVisible(false);
//		optionsLayoutVerticalTab.setVisible(true);
	}

	private void phoneMode() {
//		dateDeleteLayoutPhone.addComponents(debugStartDateField, debugEndDateField);
//		optionsLayoutVerticalPhone.addStyleName("audit-phoneAlignment");
//		debugSearchLayout.addStyleName("audit-phoneAlignment");
//		optionsLayoutVerticalPhone.addComponents(debugSearchLayout, dateDeleteLayoutPhone);
//		optionsLayoutVerticalPhone.setVisible(true);
//		optionsLayoutHorizontalDesktop.setVisible(false);
//		optionsLayoutVerticalTab.setVisible(false);
	}

	private void desktopMode() {
//		optionsLayoutHorizontalDesktop.addComponent(debugSearchLayout);
//		optionsLayoutHorizontalDesktop.setComponentAlignment(debugSearchLayout, Alignment.MIDDLE_LEFT);
//		dateDeleteLayout.addComponent(debugStartDateField);
//		//dateDeleteLayout.setComponentAlignment(debugStartDateField, Alignment.TOP_RIGHT);
//		dateDeleteLayout.addComponent(debugEndDateField);
//		//dateDeleteLayout.setComponentAlignment(debugEndDateField, Alignment.TOP_RIGHT);
////		dateDeleteLayout.addComponent(deleteGridRow);
////		dateDeleteLayout.setComponentAlignment(deleteGridRow, Alignment.TOP_LEFT);
//		optionsLayoutHorizontalDesktop.addComponent(dateDeleteLayout);
//		// optionsLayoutHorizontalDesktop.addComponent(dateDeleteLayout);
//		//optionsLayoutHorizontalDesktop.setComponentAlignment(dateDeleteLayout, Alignment.TOP_RIGHT);
//		optionsLayoutHorizontalDesktop.setVisible(true);
//		optionsLayoutVerticalTab.setVisible(false);
//		optionsLayoutVerticalPhone.setVisible(false);
	}

	public Panel getAndLoadAuditPanel() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setResponsive(true);
		panel.setSizeFull();
		addComponent(header);
		addComponent(panel);
		setExpandRatio(header, 1);
		setExpandRatio(panel, 11);
		return panel;
	}
	
	private VerticalLayout  getUserGridAndSearch() {
		userGirdAndSearchLayout = new VerticalLayout();
		userGirdAndSearchLayout.setSizeFull();
		userGirdAndSearchLayout.addStyleName("role-gridLayout");
		
		userSearch = new TextField();
		userSearch.setWidth("100%");
		userSearch.setIcon(VaadinIcons.SEARCH);
		userSearch.setStyleName("small inline-icon search");
		userSearch.addStyleName("v-textfield-font");
		userSearch.setPlaceholder("Search");
		userSearch.setMaxLength(50);
		clearUserSearch = new Button(VaadinIcons.CLOSE);
		clearUserSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		
		CssLayout searchLayout = new CssLayout();
		searchLayout.addStyleNames(ValoTheme.LAYOUT_COMPONENT_GROUP, "device-activeButton");
		searchLayout.addComponents(userSearch, clearUserSearch);
		searchLayout.setWidth("95%");
		
		userGrid = new Grid<>(User.class);
		userGrid.setSizeFull();
		userGrid.setColumns("username", "firstname", "lastname", "active", "email", "role");
		userGrid.getColumn("lastname").setCaption("Last Name");
		userGrid.getColumn("firstname").setCaption("First Name");
		userGrid.getColumn("username").setCaption("User Name");
		userGrid.setSelectionMode(SelectionMode.MULTI);
		
		createUser = new Button("Add User", click -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			userMenuContextWindow.close();
			createEntityWindow = openEntityWindow(false);
			if (nodeTreeGrid.getSelectedItems().size() == 0) {
				Notification.show("Select any entity to create user", Notification.Type.ERROR_MESSAGE);
			} else {
				if (createEntityWindow.getParent() == null)
					UI.getCurrent().addWindow(createEntityWindow);
			}
		});
		createUser.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		
		editUser = new Button("Edit User", click -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			userMenuContextWindow.close();
			editEntityWindow = openEntityWindow(true);
			if (nodeTreeGrid.getSelectedItems().size() == 0) {
				Notification.show("Select any entity to create user", Notification.Type.ERROR_MESSAGE);
			} else {
				if (editEntityWindow.getParent() == null)
					UI.getCurrent().addWindow(editEntityWindow);
			}

		});
		editUser.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		editUser.setEnabled(false);
		
		deleteUser = new Button("Delete User", click -> {
			userMenuContextWindow.close();
		});
		deleteUser.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		deleteUser.setEnabled(false);
		
		userMenuContextWindow = new ContextMenuWindow();
		userMenuContextWindow.addMenuItems(createUser, editUser, deleteUser);
		userGrid.addContextClickListener(click->{
			if(userGrid.getSelectedItems().size()>0) {
				editUser.setEnabled(true);
				deleteUser.setEnabled(true);
			}else {
				editUser.setEnabled(false);
				deleteUser.setEnabled(false);
			}
	
			UI.getCurrent().getWindows().forEach(Window::close);
			userMenuContextWindow.setPosition(click.getClientX(), click.getClientY());
			UI.getCurrent().addWindow(userMenuContextWindow);
			
		});
		
		UI.getCurrent().addClickListener(listener->{
			if(userMenuContextWindow!=null) {
				userMenuContextWindow.close();
			}
			
			if(createEntityWindow!=null) {
				createEntityWindow.close();
			}
		});
		
		userGirdAndSearchLayout.addComponents(searchLayout, userGrid);
		userGirdAndSearchLayout.setExpandRatio(userGrid, 14);
		
		userSearch.addValueChangeListener(event -> {
			if (event.getValue().length() == 50) {
				if (nodeTreeGrid.getSelectedItems().size() > 0) {
					Notification search = Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
					search.addCloseListener(listener -> {
						String filter = event.getValue().toLowerCase();
						List<User> searchList = userService.getUsersListByEntityId(nodeTreeGrid.getSelectedItems().iterator().next().getEntityId());
						DataProvider data = new ListDataProvider(searchList);
						nodeTreeGrid.setDataProvider(data);
					});
				} else {
					Notification.show("Select any Entity to search users", Type.ERROR_MESSAGE);
				}
			} else if(event.getValue()==null || event.getValue().isEmpty()){
				List<User> searchList = userService.getUsersListByEntityId(nodeTreeGrid.getSelectedItems().iterator().next().getEntityId());
				DataProvider data = new ListDataProvider(searchList);
				userGrid.setDataProvider(data);
			}else {
				if (nodeTreeGrid.getSelectedItems().size() > 0) {
					String filter = event.getValue().toLowerCase();
					List<User> searchList = userService.searchUsers(filter);
					DataProvider data = new ListDataProvider(searchList);
					userGrid.setDataProvider(data);
				} else {
					Notification.show("Select any Entity to search users", Type.ERROR_MESSAGE);
				}
			}
		});
		
		clearUserSearch.addClickListener(listener->{userSearch.clear();});
		
		return userGirdAndSearchLayout;
	}
	
	private void configureTreeNodeSearch() {
		treeNodeSearch.addValueChangeListener(changed -> {
			if (changed.getValue().length() == 50) {
				Notification search = Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
				search.addCloseListener(listener -> {
					String valueInLower = changed.getValue().toLowerCase();
					if (!valueInLower.isEmpty() && valueInLower != null) {
						nodeTreeGrid.setTreeData(treeDataNodeService.searchTreeData(valueInLower));
					} else {
						nodeTreeGrid.setTreeData(treeDataNodeService.getTreeData());
					}
				});
			} else {
				String valueInLower = changed.getValue().toLowerCase();
				if (!valueInLower.isEmpty() && valueInLower != null) {
					nodeTreeGrid.setTreeData(treeDataNodeService.searchTreeData(valueInLower));
				} else {
					nodeTreeGrid.setTreeData(treeDataNodeService.getTreeData());
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
		clearSearch.addClickListener(click -> treeNodeSearch.clear());
	}
	
	private ContextMenuWindow openEntityWindow(boolean edit) {
		ContextMenuWindow entityWindow = new ContextMenuWindow();
		
		userName = new TextField("User Name");
		userName.addStyleNames("v-textfield-font", "textfiled-height");
		userName.focus();
		userName.setWidth("100%");
		userName.setRequiredIndicatorVisible(true);
		
		firstName = new TextField("First Name");
		firstName.addStyleNames("v-textfield-font", "textfiled-height");
		firstName.setWidth("100%");
		firstName.setRequiredIndicatorVisible(true);
		
		lastName = new TextField("Last Name");
		lastName.addStyleNames("v-textfield-font", "textfiled-height");
		lastName.setWidth("100%");
		lastName.setRequiredIndicatorVisible(true);
		
		email = new TextField("Email");
		email.addStyleNames("v-textfield-font", "textfiled-height");
		email.setWidth("100%");
		email.setRequiredIndicatorVisible(true);
		
		password = new TextField("Password");
		password.addStyleNames("v-textfield-font", "textfiled-height");
		password.setWidth("100%");
		password.setRequiredIndicatorVisible(true);
		
		ipAddress = new TextField("IP Address");
		ipAddress.addStyleNames("v-textfield-font", "textfiled-height");
		ipAddress.setWidth("100%");
		ipAddress.setRequiredIndicatorVisible(true);
		
		userActive = new CheckBox("User Active");
		userActive.addStyleNames("user-activeBox", "v-textfield-font", "personlization-formAlignment");
		userActive.setRequiredIndicatorVisible(true);
		
		fixedIp = new CheckBox("Fixed Ip");
		fixedIp.addStyleNames("user-activeBox", "v-textfield-font", "personlization-formAlignment");
		fixedIp.setRequiredIndicatorVisible(true);
		
		resendPassword = new Button("Resend Password");
		resendPassword.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle", "personlization-plusButtons");
		
		HorizontalLayout firstAndLastNameLayout = new HorizontalLayout(firstName, lastName);
		firstAndLastNameLayout.addStyleName("personlization-formAlignment");
		firstAndLastNameLayout.setWidth("100%");
		
		HorizontalLayout passwordAndIPAddress = new HorizontalLayout(resendPassword, ipAddress);
		passwordAndIPAddress.addStyleName("personlization-formAlignment");
		
		if(edit) {
			User selectedUser = userGrid.getSelectedItems().iterator().next();
			userName.setValue(selectedUser.getUsername());
			firstName.setValue(selectedUser.getFirstname());
			lastName.setValue(selectedUser.getLastname());
			email.setValue(selectedUser.getEmail());
			roles.setValue(selectedUser.getRole());
			if(selectedUser.getIpAddress()!=null) {
				ipAddress.setValue(selectedUser.getIpAddress());
			}
			userActive.setValue(selectedUser.isActive());
		}else {
			clearAllData();
		}
		
		save = new Button("Save", click -> {
			if(createEntityWindow!=null) {
				createEntityWindow.setModal(true);
			}
			
			if(editEntityWindow!=null) {
				editEntityWindow.setModal(true);
			}
			
			if(nodeTreeGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.USER_CREATE, Notification.Type.ERROR_MESSAGE);
			}else {
				if(!(ComponentUtil.validatorTextField(userName) && 
						ComponentUtil.validatorComboBox(roles) &&
						ComponentUtil.validatorTextField(firstName) &&
						ComponentUtil.validatorTextField(lastName) &&
						ComponentUtil.validatorTextField(email) 
						/*ComponentUtil.validatorCheckBox(getActiveBox()) && 
						ComponentUtil.validatorComboBox(getPassFreqncy())*/)) {
				}else {
					User existingUserCheck = new User();
					if(!userGrid.getSelectedItems().isEmpty() && userGrid.getSelectedItems().iterator().next().getId()!=null) {
					existingUserCheck = userService.getUserbyId(userGrid.getSelectedItems().iterator().next().getId());
					}
					TreeNode selectedNode = nodeTreeGrid.getSelectedItems().iterator().next();
					if(existingUserCheck!=null && existingUserCheck.getId()!=null) {
						try {
							User clientUserUpdate = new User(existingUserCheck.getId(), existingUserCheck.getEmail(), existingUserCheck.getUsername(), existingUserCheck.getRole(),
									existingUserCheck.getFirstname(), existingUserCheck.getLastname(), existingUserCheck.isActive(), 0, selectedNode.getId(), existingUserCheck.getIpAddress());
							userService.createUser(clientUserUpdate, selectedNode.getId());
							loadGridData();
							nodeTreeGrid.select(selectedNode);
							clearAllData();
						} catch (Exception e1) {
							if(e1.getMessage().contains("USERNAME INVALID DATA ENTRY")) {
								Notification.show(NotificationUtil.USER_NAME_CHECK1, Type.ERROR_MESSAGE);
							}else if(e1.getMessage().contains("EMAIL INVALID DATA ENTRY")) {
								Notification.show(NotificationUtil.USER_NAME_CHECK2, Type.ERROR_MESSAGE);
							}else if(e1.getMessage().contains("DATA INTEGRITY VIOLATION")) {
								Notification.show(NotificationUtil.USER_NAME_CHECK3, Type.ERROR_MESSAGE);
							}
						}
					}else {
				
						try {
							User clientUser = new User(email.getValue(), userName.getValue(), roles.getValue(),
									firstName.getValue(), lastName.getValue(), userActive.getValue(), 0, selectedNode.getId(), ipAddress.getValue());
							userService.createUser(clientUser, selectedNode.getId());
							loadGridData();
							nodeTreeGrid.select(selectedNode);
	
							clearAllData();
								
						} catch (Exception e1) {
							if(e1.getMessage().contains("USERNAME INVALID DATA ENTRY")) {
								Notification.show(NotificationUtil.USER_NAME_CHECK1, Type.ERROR_MESSAGE);
							}else if(e1.getMessage().contains("EMAIL INVALID DATA ENTRY")) {
								Notification.show(NotificationUtil.USER_NAME_CHECK2, Type.ERROR_MESSAGE);
							}else if(e1.getMessage().contains("DATA INTEGRITY VIOLATION")) {
								Notification.show(NotificationUtil.USER_NAME_CHECK3, Type.ERROR_MESSAGE);
							}
						}
					}
				}
			}
		});

		save.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		cancel = new Button("Cancel", click -> {
			clearAllData();
		});
		cancel.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		HorizontalLayout buttonLayout = new HorizontalLayout(save, cancel);
		buttonLayout.setComponentAlignment(save, Alignment.BOTTOM_RIGHT);
		buttonLayout.setComponentAlignment(cancel, Alignment.BOTTOM_RIGHT);
		
		VerticalLayout profileFormLayout = new VerticalLayout(userName,firstAndLastNameLayout , email, roles, passwordAndIPAddress, fixedIp, userActive);
		profileFormLayout.addStyleNames("system-LabelAlignment", "heartbeat-verticalLayout", "header-Components");
		VerticalLayout verticalLayout = new VerticalLayout(profileFormLayout, buttonLayout);
		verticalLayout.addStyleNames("system-LabelAlignment", "heartbeat-verticalLayout", "header-Components");
		
		// Window Setup
		entityWindow.addMenuItems(verticalLayout);
		entityWindow.center();
		entityWindow.setWidth("30%");
		entityWindow.setHeight("55%");
		entityWindow.setResizable(true);
		entityWindow.setClosable(true);
		entityWindow.setDraggable(true);
		return entityWindow;
	}
	
	private void loadGridData() {
		List<User> searchList = userService.getUsersListByEntityId(nodeTreeGrid.getSelectedItems().iterator().next().getEntityId());
		DataProvider data = new ListDataProvider(searchList);
		userGrid.setDataProvider(data);
	}
	
	private void clearAllData() {
		userName.clear();
		roles.clear();
		firstName.clear();
		lastName.clear();
		email.clear();
		userActive.clear();
		ipAddress.clear();
		fixedIp.clear();
	}
}
