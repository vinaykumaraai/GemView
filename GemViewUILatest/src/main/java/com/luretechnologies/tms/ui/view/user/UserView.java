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
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.tms.backend.data.entity.Permission;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.luretechnologies.tms.backend.service.AuditService;
import com.luretechnologies.tms.backend.service.RolesService;
import com.luretechnologies.tms.backend.service.TreeDataNodeService;
import com.luretechnologies.tms.backend.service.UserService;
import com.luretechnologies.tms.ui.MainView;
import com.luretechnologies.tms.ui.components.ButtonsHeight;
import com.luretechnologies.tms.ui.components.ComponentUtil;
import com.luretechnologies.tms.ui.components.EntityOperations;
import com.luretechnologies.tms.ui.components.MainViewIconsLoad;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.luretechnologies.tms.ui.navigation.NavigationManager;
import com.luretechnologies.tms.ui.view.ContextMenuWindow;
import com.luretechnologies.tms.ui.view.Header;
import com.vaadin.addon.charts.model.Buttons;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
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

/**
 * 
 * @author Vinay
 *
 */

@SpringView(name = UserView.VIEW_NAME)
public class UserView extends VerticalLayout implements Serializable, View {

	public static final String VIEW_NAME = "user";
	private Grid<User> userGrid;
	private TreeGrid<TreeNode> nodeTreeGrid;
	private Button clearSearch, clearUserSearch, save, cancel, resendPassword;
	private TextField treeNodeSearch, userSearch;
	private HorizontalSplitPanel splitScreen;
	private VerticalLayout userGirdAndSearchLayout;
	private  HorizontalLayout header;
	private  ComboBox<String> roles;
	private TextField userName, firstName, lastName, email, password, ipAddress;
	private ContextMenuWindow userMenuContextWindow, createEntityWindow, editEntityWindow, entityWindow;
	private CheckBox userActive, fixedIp;
	private HorizontalLayout firstAndLastNameHL, passwordAndIPAddressHL, buttonLayout;
	private VerticalLayout firstAndLastNameVL, passwordAndIPAddressVL, profileFormLayout;
	private User selectedUser;
	private boolean  addEntity, updateEntity, accessEntity, removeEntity;
	private Permission userPermission ;

	private Button createEntity, editEntity, deleteEntity, copyEntity, pasteEntity, createUser, editUser, deleteUser;
	private Button[] buttons= {createEntity, editEntity, deleteEntity, copyEntity, pasteEntity, createUser, editUser, deleteUser};
	
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
			header = new Header(userService, roleService, navigationManager, "Users", new Label());
			
			userName = new TextField("User Name");
			firstName = new TextField("First Name");
			lastName = new TextField("Last Name");
			email = new TextField("Email");
			ipAddress = new TextField("IP Address");
			resendPassword = new Button("Resend Password");
			fixedIp = new CheckBox("Fixed Ip");
			
			
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
					
					userName.setHeight(28, Unit.PIXELS);
					roles.setHeight(28, Unit.PIXELS);
					firstName.setHeight(28, Unit.PIXELS);
					lastName.setHeight(28, Unit.PIXELS);
					email.setHeight(28, Unit.PIXELS);
					ipAddress.setHeight(28, Unit.PIXELS);
					resendPassword.setHeight(28, Unit.PIXELS);
//					save.setHeight(28, Unit.PIXELS);
//					cancel.setHeight(28, Unit.PIXELS);
					MainViewIconsLoad.iconsOnPhoneMode(mainView);
					
					if(createEntityWindow!=null) {
						createEntityWindow.setPosition(180, 200);
						createEntityWindow.setWidth("200px");
					}
					
					if(editEntityWindow!=null) {
						editEntityWindow.setPosition(180, 200);
						editEntityWindow.setWidth("200px");
					}
				} else if (r.getWidth() > 600 && r.getWidth() <= 1000) {
					mainView.getTitle().setValue("gemView");
					addComponentAsFirst(header);
					MainViewIconsLoad.iconsOnTabMode(mainView);
					treeNodeSearch.setHeight(32, Unit.PIXELS);
					userSearch.setHeight(32, Unit.PIXELS);
					
					userName.setHeight(32, Unit.PIXELS);
					roles.setHeight(32, Unit.PIXELS);
					firstName.setHeight(32, Unit.PIXELS);
					lastName.setHeight(32, Unit.PIXELS);
					email.setHeight(32, Unit.PIXELS);
					ipAddress.setHeight(32, Unit.PIXELS);
					resendPassword.setHeight(32, Unit.PIXELS);
//					save.setHeight(32, Unit.PIXELS);
//					cancel.setHeight(32, Unit.PIXELS);
					MainViewIconsLoad.iconsOnTabMode(mainView);
					ButtonsHeight.setButtonsHeight(601, buttons);
					
					if(createEntityWindow!=null) {
						createEntityWindow.center();
					}
					
					if(editEntityWindow!=null) {
						editEntityWindow.center();
					}
				} else {
					mainView.getTitle().setValue("gemView");
					addComponentAsFirst(header);
					splitScreen.setSplitPosition(30);
					MainViewIconsLoad.noIconsOnDesktopMode(mainView);
					treeNodeSearch.setHeight(37, Unit.PIXELS);
					userSearch.setHeight(37, Unit.PIXELS);
					
					userName.setHeight(37, Unit.PIXELS);
					roles.setHeight(37, Unit.PIXELS);
					firstName.setHeight(37, Unit.PIXELS);
					lastName.setHeight(37, Unit.PIXELS);
					email.setHeight(37, Unit.PIXELS);
					ipAddress.setHeight(37, Unit.PIXELS);
					resendPassword.setHeight(37, Unit.PIXELS);
//					save.setHeight(37, Unit.PIXELS);
//					cancel.setHeight(37, Unit.PIXELS);
					MainViewIconsLoad.noIconsOnDesktopMode(mainView);
					ButtonsHeight.setButtonsHeight(1001, buttons);
					
					if(createEntityWindow!=null) {
						createEntityWindow.center();
						createEntityWindow.setWidth("30%");
						createEntityWindow.setHeight("55%");
					}
					
					if(editEntityWindow!=null) {
						editEntityWindow.center();
						editEntityWindow.setWidth("30%");
						editEntityWindow.setHeight("55%");
					}
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
			

			Permission EntityPermission = roleService.getLoggedInUserRolePermissions().stream()
					.filter(per -> per.getPageName().equals("ENTITY")).findFirst().get();
			addEntity = EntityPermission.getAdd();
			updateEntity = EntityPermission.getEdit();
			accessEntity = EntityPermission.getAccess();
			removeEntity = EntityPermission.getDelete();

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
			createEntity.addStyleNames(ValoTheme.BUTTON_BORDERLESS);
			createEntity.setEnabled(addEntity);
			
			editEntity = new Button("Edit Entity");
			editEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
			editEntity.setEnabled(updateEntity);
			
			deleteEntity = new Button("Delete Entity");
			deleteEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
			deleteEntity.setEnabled(removeEntity);
			
			copyEntity = new Button("Copy Entity");
			copyEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
			copyEntity.setEnabled(addEntity || updateEntity);
			
			pasteEntity = new Button("Paste Entity");
			pasteEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
			pasteEntity.setEnabled(false);
			
			ContextMenuWindow userTreeGridMenu = new ContextMenuWindow();
			userTreeGridMenu.addMenuItems(createEntity,editEntity,deleteEntity, copyEntity, pasteEntity);
			nodeTreeGrid.addContextClickListener(click->{
				UI.getCurrent().getWindows().forEach(Window::close);
				if(click.getClientY() > 750) {
					userTreeGridMenu.setPosition(click.getClientX(), click.getClientY()-220);
				}else {
					userTreeGridMenu.setPosition(click.getClientX(), click.getClientY());
				}
				Button[] buttonsList = {createEntity,editEntity,deleteEntity, copyEntity, pasteEntity};
				ButtonsHeight.setButtonsHeight(Page.getCurrent().getBrowserWindowWidth(), buttonsList);
				UI.getCurrent().addWindow(userTreeGridMenu);
			});
			EntityOperations.entityOperations(nodeTreeGrid, createEntity, editEntity, deleteEntity, copyEntity, pasteEntity, treeDataNodeService, auditService, userTreeGridMenu);
			
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
			roles.setRequiredIndicatorVisible(true);
			
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
				splitScreen.setSplitPosition(30);
				MainViewIconsLoad.noIconsOnDesktopMode(mainView);
				treeNodeSearch.setHeight(28, Unit.PIXELS);
				userSearch.setHeight(28, Unit.PIXELS);
				
				userName.setHeight(28, Unit.PIXELS);
				roles.setHeight(28, Unit.PIXELS);
				firstName.setHeight(28, Unit.PIXELS);
				lastName.setHeight(28, Unit.PIXELS);
				email.setHeight(28, Unit.PIXELS);
				ipAddress.setHeight(28, Unit.PIXELS);
				resendPassword.setHeight(28, Unit.PIXELS);
				ButtonsHeight.setButtonsHeight(600, buttons);
				
				if(createEntityWindow!=null) {
					createEntityWindow.setPosition(180, 200);
					createEntityWindow.setWidth("200px");
					save.setHeight(28, Unit.PIXELS);
					cancel.setHeight(28, Unit.PIXELS);
				}
				
				if(editEntityWindow!=null) {
					editEntityWindow.setPosition(180, 200);
					editEntityWindow.setWidth("200px");
					save.setHeight(28, Unit.PIXELS);
					cancel.setHeight(28, Unit.PIXELS);
				}
			} else if (width > 600 && width <= 1000) {
				mainView.getTitle().setValue("gemView");
				addComponentAsFirst(header);
				MainViewIconsLoad.iconsOnTabMode(mainView);
				treeNodeSearch.setHeight(32, Unit.PIXELS);
				userSearch.setHeight(32, Unit.PIXELS);
				
				userName.setHeight(32, Unit.PIXELS);
				roles.setHeight(32, Unit.PIXELS);
				firstName.setHeight(32, Unit.PIXELS);
				lastName.setHeight(32, Unit.PIXELS);
				email.setHeight(32, Unit.PIXELS);
				ipAddress.setHeight(32, Unit.PIXELS);
				resendPassword.setHeight(32, Unit.PIXELS);
				ButtonsHeight.setButtonsHeight(601, buttons);
				
				if(createEntityWindow!=null) {
					createEntityWindow.center();
					save.setHeight(32, Unit.PIXELS);
					cancel.setHeight(32, Unit.PIXELS);
				}
				
				if(editEntityWindow!=null) {
					editEntityWindow.center();
					save.setHeight(32, Unit.PIXELS);
					cancel.setHeight(32, Unit.PIXELS);
				}
			} else {
				mainView.getTitle().setValue("gemView");
				addComponentAsFirst(header);
				splitScreen.setSplitPosition(30);
				MainViewIconsLoad.noIconsOnDesktopMode(mainView);
				treeNodeSearch.setHeight(37, Unit.PIXELS);
				userSearch.setHeight(37, Unit.PIXELS);
				
				userName.setHeight(37, Unit.PIXELS);
				roles.setHeight(37, Unit.PIXELS);
				firstName.setHeight(37, Unit.PIXELS);
				lastName.setHeight(37, Unit.PIXELS);
				email.setHeight(37, Unit.PIXELS);
				ipAddress.setHeight(37, Unit.PIXELS);
				resendPassword.setHeight(37, Unit.PIXELS);
				ButtonsHeight.setButtonsHeight(1001, buttons);
				
				if(createEntityWindow!=null) {
					createEntityWindow.center();
					createEntityWindow.setWidth("30%");
					createEntityWindow.setHeight("55%");
					save.setHeight(37, Unit.PIXELS);
					cancel.setHeight(37, Unit.PIXELS);
				}
				
				if(editEntityWindow!=null) {
					editEntityWindow.center();
					editEntityWindow.setWidth("30%");
					editEntityWindow.setHeight("55%");
					save.setHeight(37, Unit.PIXELS);
					cancel.setHeight(37, Unit.PIXELS);
				}
				
			}

			userPermission = roleService.getLoggedInUserRolePermissions().stream()
					.filter(per -> per.getPageName().equals("USER")).findFirst().get();
			disableAllComponents();
			allowAccessBasedOnPermission(userPermission.getAdd(), userPermission.getEdit(),
					userPermission.getDelete());
		} catch (Exception ex) {
			auditService.logAuditScreenErrors(ex);
		}
		
		mainView.getAudit().setEnabled(true);
	}

	private void disableAllComponents() throws Exception {
		createUser.setEnabled(false); 
		editUser.setEnabled(false);
		deleteUser.setEnabled(false);
	}

	private void allowAccessBasedOnPermission(Boolean addBoolean, Boolean editBoolean, Boolean deleteBoolean) {
		createUser.setEnabled(addBoolean); 
		editUser.setEnabled(editBoolean);
		deleteUser.setEnabled(deleteBoolean);
	}

	private void tabMode() {
		
		if(createEntityWindow!=null || editEntityWindow!=null) {
			if(profileFormLayout.getComponentIndex(firstAndLastNameHL) !=-1 && 
					profileFormLayout.getComponentIndex(passwordAndIPAddressHL) !=-1) {
				
				firstName = new TextField("First Name");
				firstName.addStyleNames("v-textfield-font", "textfiled-height");
				firstName.setWidth("100%");
				firstName.setRequiredIndicatorVisible(true);
				
				lastName = new TextField("Last Name");
				lastName.addStyleNames("v-textfield-font", "textfiled-height");
				lastName.setWidth("100%");
				lastName.setRequiredIndicatorVisible(true);
				
				if(selectedUser!=null) {
					firstName.setValue(selectedUser.getFirstname());
					lastName.setValue(selectedUser.getLastname());
				}else {
					firstName.clear();
					lastName.clear();
				}
				
				firstAndLastNameVL = new VerticalLayout(firstName, lastName);
				firstAndLastNameVL.addStyleNames("personlization-formAlignment", "system-GridAlignment");
				firstAndLastNameVL.setWidth("100%");
			
				passwordAndIPAddressVL = new VerticalLayout(resendPassword, ipAddress, fixedIp);
				passwordAndIPAddressVL.addStyleNames("personlization-formAlignment", "system-GridAlignment");
				profileFormLayout.replaceComponent(firstAndLastNameHL, firstAndLastNameVL);
				profileFormLayout.replaceComponent(passwordAndIPAddressHL, passwordAndIPAddressVL);
			}
		}
	}

	private void phoneMode() {
		
		if(createEntityWindow!=null || editEntityWindow!=null) {
			if(profileFormLayout.getComponentIndex(firstAndLastNameHL) !=-1 && 
					profileFormLayout.getComponentIndex(passwordAndIPAddressHL) !=-1) {
				
				firstName = new TextField("First Name");
				firstName.addStyleNames("v-textfield-font", "textfiled-height");
				firstName.setWidth("100%");
				firstName.setRequiredIndicatorVisible(true);
				
				lastName = new TextField("Last Name");
				lastName.addStyleNames("v-textfield-font", "textfiled-height");
				lastName.setWidth("100%");
				lastName.setRequiredIndicatorVisible(true);
				
				if(selectedUser!=null) {
					firstName.setValue(selectedUser.getFirstname());
					lastName.setValue(selectedUser.getLastname());
				}else {
					firstName.clear();
					lastName.clear();
				}
				
				firstAndLastNameVL = new VerticalLayout(firstName, lastName);
				firstAndLastNameVL.addStyleNames("personlization-formAlignment", "system-GridAlignment");
				firstAndLastNameVL.setWidth("100%");
			
				passwordAndIPAddressVL = new VerticalLayout(resendPassword, ipAddress, fixedIp);
				passwordAndIPAddressVL.addStyleNames("personlization-formAlignment", "system-GridAlignment");
				profileFormLayout.replaceComponent(firstAndLastNameHL, firstAndLastNameVL);
				profileFormLayout.replaceComponent(passwordAndIPAddressHL, passwordAndIPAddressVL);
			}
		}
	}

	private void desktopMode() {
		if(createEntityWindow!=null || editEntityWindow!=null) {
			if(profileFormLayout.getComponentIndex(firstAndLastNameVL) !=-1 && 
					profileFormLayout.getComponentIndex(passwordAndIPAddressVL) !=-1) {
				
				firstName = new TextField("First Name");
				firstName.addStyleNames("v-textfield-font", "textfiled-height");
				firstName.setWidth("100%");
				firstName.setRequiredIndicatorVisible(true);
				
				lastName = new TextField("Last Name");
				lastName.addStyleNames("v-textfield-font", "textfiled-height");
				lastName.setWidth("100%");
				lastName.setRequiredIndicatorVisible(true);
				
				if(selectedUser!=null) {
					firstName.setValue(selectedUser.getFirstname());
					lastName.setValue(selectedUser.getLastname());
				}else {
					firstName.clear();
					lastName.clear();
				}
			
			firstAndLastNameHL = new HorizontalLayout(firstName, lastName);
			firstAndLastNameHL.addStyleName("personlization-formAlignment");
			firstAndLastNameHL.setWidth("100%");
			
			passwordAndIPAddressHL = new HorizontalLayout(resendPassword, ipAddress, fixedIp);
			passwordAndIPAddressHL.addStyleName("personlization-formAlignment");
		
			profileFormLayout.replaceComponent(firstAndLastNameVL, firstAndLastNameHL);
			profileFormLayout.replaceComponent(passwordAndIPAddressVL, passwordAndIPAddressHL);
			}
		}
		
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
		userSearch.setCursorPosition(0);
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
		
		userGrid.addSelectionListener(listener->{
			UI.getCurrent().getWindows().forEach(Window::close);
			if(listener.getAllSelectedItems().size()==0) {
				selectedUser=null;
			}
		});
		
		createUser = new Button("Add User", click -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			userMenuContextWindow.close();
			createEntityWindow = openEntityWindow(false);
			resendPassword.setEnabled(false);
			if (nodeTreeGrid.getSelectedItems().size() == 0) {
				Notification.show("Select any entity to create user", Notification.Type.ERROR_MESSAGE);
			} else {
				if (createEntityWindow.getParent() == null) {
					int width = Page.getCurrent().getBrowserWindowWidth();
					if (width > 0 && width <= 699) {
						phoneMode();
					} else if (width >= 700 && width <= 1400) {
						tabMode();
					} 
					clearAllData();
					UI.getCurrent().addWindow(createEntityWindow);
				}
			}
		});
		createUser.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		
		editUser = new Button("Edit User", click -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			userMenuContextWindow.close();
			editEntityWindow = openEntityWindow(true);
			resendPassword.setEnabled(true);
			if (nodeTreeGrid.getSelectedItems().size() == 0) {
				Notification.show("Select any entity to create user", Notification.Type.ERROR_MESSAGE);
			} else {
				if (editEntityWindow.getParent() == null) {
					int width = Page.getCurrent().getBrowserWindowWidth();
					if (width > 0 && width <= 699) {
						phoneMode();
					} else if (width >= 700 && width <= 1400) {
						tabMode();
					} 
					UI.getCurrent().addWindow(editEntityWindow);
				}
			}

		});
		editUser.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		
		
		deleteUser = new Button("Delete User", click -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			userMenuContextWindow.close();
			confirmDialog(userGrid.getSelectedItems());
		});
		deleteUser.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		
		
		userMenuContextWindow = new ContextMenuWindow();
		userMenuContextWindow.addMenuItems(createUser, editUser, deleteUser);
		userGrid.addContextClickListener(click->{
			deleteUser.setEnabled(userPermission.getDelete());
			editUser.setEnabled(userPermission.getEdit());
			createUser.setEnabled(userPermission.getAdd());
			
			if(userGrid.getSelectedItems().size()>0 && deleteUser.isEnabled()) {
				deleteUser.setEnabled(true);
			}else {
				deleteUser.setEnabled(false);
			}
			
			if(userGrid.getSelectedItems().size()==1 && editUser.isEnabled()) {
				editUser.setEnabled(true);
			}else {
				editUser.setEnabled(false);
			}
	
			Button[] buttonList = {createUser,editUser,deleteUser};
			UI.getCurrent().getWindows().forEach(Window::close);
			userMenuContextWindow.setPosition(click.getClientX(), click.getClientY());
			ButtonsHeight.setButtonsHeight(Page.getCurrent().getBrowserWindowWidth(), buttonList);
			UI.getCurrent().addWindow(userMenuContextWindow);
			
		});
		
		UI.getCurrent().addClickListener(listener->{
			UI.getCurrent().getWindows().forEach(Window::close);
		});
		
		userGirdAndSearchLayout.addComponents(searchLayout, userGrid);
		userGirdAndSearchLayout.setExpandRatio(userGrid, 14);
		
		resendPassword.addClickListener(listener->{
			if(email!=null && email.getValue()!=null && !email.getValue().isEmpty()) {
				RestServiceUtil.getInstance().resendPassword(email.getValue());
				editEntityWindow.close();
			}
		});
		
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
		entityWindow = new ContextMenuWindow();
		
		userName.addStyleNames("v-textfield-font", "textfiled-height");
		userName.focus();
		userName.setWidth("100%");
		userName.setRequiredIndicatorVisible(true);
		
		firstName.addStyleNames("v-textfield-font", "textfiled-height");
		firstName.setWidth("100%");
		firstName.setRequiredIndicatorVisible(true);
		
		lastName.addStyleNames("v-textfield-font", "textfiled-height");
		lastName.setWidth("100%");
		lastName.setRequiredIndicatorVisible(true);
		
		email.addStyleNames("v-textfield-font", "textfiled-height");
		email.setWidth("100%");
		email.setRequiredIndicatorVisible(true);
		
		password = new TextField("Password");
		password.addStyleNames("v-textfield-font", "textfiled-height");
		password.setWidth("100%");
		password.setRequiredIndicatorVisible(true);
		
		ipAddress.addStyleNames("v-textfield-font", "textfiled-height");
		ipAddress.setWidth("100%");
		ipAddress.setRequiredIndicatorVisible(true);
		
		userActive = new CheckBox("User Active");
		userActive.addStyleNames( "v-textfield-font", "personlization-formAlignment");
		userActive.setRequiredIndicatorVisible(true);
		
		fixedIp.addStyleNames("user-activeBox", "v-textfield-font", "personlization-formAlignment");
		fixedIp.setRequiredIndicatorVisible(true);
		
		resendPassword.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle", "personlization-plusButtons");
		
		firstAndLastNameHL = new HorizontalLayout(firstName, lastName);
		firstAndLastNameHL.addStyleName("personlization-formAlignment");
		firstAndLastNameHL.setWidth("100%");
		
		passwordAndIPAddressHL = new HorizontalLayout(resendPassword, ipAddress, fixedIp);
		passwordAndIPAddressHL.addStyleName("personlization-formAlignment");
		
		if(edit) {
			selectedUser = userGrid.getSelectedItems().iterator().next();
			userName.setValue(selectedUser.getUsername());
			firstName.setValue(selectedUser.getFirstname());
			lastName.setValue(selectedUser.getLastname());
			email.setValue(selectedUser.getEmail());
			roles.setValue(selectedUser.getRole());
			userName.setEnabled(false);
			email.setEnabled(false);
			if(selectedUser.getIpAddress()!=null) {
				ipAddress.setValue(selectedUser.getIpAddress());
			}else {
				ipAddress.clear();
			}
			userActive.setValue(selectedUser.isActive());
		}else {
			clearAllData();
			userName.setEnabled(true);
			email.setEnabled(true);
		}
		
		save = new Button("Save");
		cancel = new Button("Cancel");
		save.addClickListener(click -> {
			
			if(nodeTreeGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.USER_CREATE, Notification.Type.ERROR_MESSAGE);
			}else {
				if(!(ComponentUtil.validateUserName(userName) && 
						ComponentUtil.validatorComboBox(roles) &&
						ComponentUtil.validatorTextField(firstName) &&
						ComponentUtil.validatorTextField(lastName) &&
						ComponentUtil.validatorEmailId(email) &&
						ComponentUtil.validatorCheckBox(userActive))) {
				}else {
					TreeNode selectedNode = nodeTreeGrid.getSelectedItems().iterator().next();
					if(edit) {
						try {
							User existingUserCheck = userGrid.getSelectedItems().iterator().next();
							existingUserCheck.setFirstname(firstName.getValue());
							existingUserCheck.setLastname(lastName.getValue());
							existingUserCheck.setRole(roles.getValue());
							existingUserCheck.setIpAddress(ipAddress.getValue());
							existingUserCheck.setActive(userActive.getValue());
							
							userService.createUser(existingUserCheck, selectedNode.getId());
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
			
			UI.getCurrent().getWindows().forEach(Window::close);
		});

		save.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		cancel.addClickListener(click -> {
			clearAllData();
		});
		cancel.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		buttonLayout = new HorizontalLayout(save, cancel);
		buttonLayout.setComponentAlignment(save, Alignment.BOTTOM_RIGHT);
		buttonLayout.setComponentAlignment(cancel, Alignment.BOTTOM_RIGHT);
		
		profileFormLayout = new VerticalLayout(userName,firstAndLastNameHL , email, roles, passwordAndIPAddressHL, userActive);
		profileFormLayout.addStyleNames("system-LabelAlignment", "heartbeat-verticalLayout", "header-Components");
		
		VerticalLayout verticalLayout = new VerticalLayout(profileFormLayout, buttonLayout);
		verticalLayout.addStyleNames("system-LabelAlignment", "heartbeat-verticalLayout", "header-Components");
		Button[] buttonList = {save, cancel};
		ButtonsHeight.setButtonsHeight(Page.getCurrent().getBrowserWindowWidth(), buttonList);
		
		// Window Setup
		entityWindow.addMenuItems(verticalLayout);
		entityWindow.center();
		entityWindow.setWidth("30%");
		entityWindow.setHeight("52%");
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
	
	public void confirmDialog(Set<User> userList) {
		ConfirmDialog.show(this.getViewComponent().getUI(), "Please Confirm:",
				"Are you sure you want to delete?", "Ok", "Cancel", null, new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
								boolean delete = false;
								for(User user : userList) {
									delete = userService.deleteUser(user.getId(), delete);
									if(!delete) {
										Notification.show(NotificationUtil.SELF_USER_DELETE, Type.ERROR_MESSAGE);
										break;
									}
								}
									List<User> userList = userService.getUsersListByEntityId(nodeTreeGrid.getSelectedItems().iterator().next().getEntityId());
									DataProvider data = new ListDataProvider(userList);
									userGrid.setDataProvider(data);
									userSearch.clear();
								
								
						} else {
							// User did not confirm

						}
					}
				});
	}
}
