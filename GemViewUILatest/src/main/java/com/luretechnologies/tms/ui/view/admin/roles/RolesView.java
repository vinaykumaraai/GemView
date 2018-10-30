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
package com.luretechnologies.tms.ui.view.admin.roles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.grid.cellrenderers.editoraware.CheckboxRenderer;

import com.luretechnologies.tms.backend.data.entity.Permission;
import com.luretechnologies.tms.backend.data.entity.Role;
import com.luretechnologies.tms.backend.service.RolesService;
import com.luretechnologies.tms.backend.service.UserService;
import com.luretechnologies.tms.ui.MainView;
import com.luretechnologies.tms.ui.components.ComponentUtil;
import com.luretechnologies.tms.ui.components.ConfirmDialogFactory;
import com.luretechnologies.tms.ui.components.MainViewIconsLoad;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.luretechnologies.tms.ui.navigation.NavigationManager;
import com.luretechnologies.tms.ui.view.ContextMenuWindow;
import com.luretechnologies.tms.ui.view.Header;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = RolesView.VIEW_NAME)
public class RolesView extends VerticalLayout implements Serializable, View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5530768165606875735L;

	public static final String VIEW_NAME = "roles";

	Map<Long, Role> rolesRepo = new HashMap<>();
	Grid<Role> roleGrid = new Grid<Role>();
	private Role selectedRole;
	private TextField descriptions;
	private TextField roleName;
	private CheckBox activeBox;
	private Grid<Permission> permissionGrid;
	private Label label;
	private static Permission rolesViewPermission;
	private static HorizontalLayout header;
	Logger logger = LoggerFactory.getLogger(RolesView.class);
	private Button createRoleMenu, editRoleMenu, deleteRoleMenu;
	private static VerticalLayout roleInfoFormLayout;
	private ContextMenuWindow rolesWindow;
	private HorizontalLayout labelLayoutLeft;
	private HorizontalLayout labelLayoutRight;
	private Button save, cancel;
	private Permission pagePermission = null;
	
	@Autowired
	public ConfirmDialogFactory confirmDialogFactory;

	@Autowired
	public RolesService rolesService;

	@Autowired
	public UserService userService;
	@Autowired
	public NavigationManager navigationManager;

	@Autowired
	public RolesView() {
		selectedRole = new Role();
	}

	@Autowired
	private MainView mainView;

	@PostConstruct
	private void init() {
		try {
			rolesViewPermission = rolesService.getLoggedInUserRolePermissions().stream()
					.filter(perm -> perm.getPageName().equals("ROLE")).findFirst().get();
			header = new Header(userService, rolesService, navigationManager, "Roles", new Label());
			
			Page.getCurrent().addBrowserWindowResizeListener(r -> {

				if (r.getWidth() <= 600) {
					mainView.getTitle().setValue(userService.getLoggedInUserName());
					removeComponent(header);
					MainViewIconsLoad.iconsOnPhoneMode(mainView);
				} else if (r.getWidth() > 600 && r.getWidth() <= 1000) {
					mainView.getTitle().setValue("gemView");
					addComponentAsFirst(header);
					MainViewIconsLoad.iconsOnTabMode(mainView);
				} else {
					mainView.getTitle().setValue("gemView");
					addComponentAsFirst(header);
					MainViewIconsLoad.noIconsOnDesktopMode(mainView);
				}
			});
			
			setSpacing(true);
			setMargin(false);
			setResponsive(true);
			setHeight("100%");
			Panel panel = getAndLoadRolesPanel();
			VerticalLayout verticalLayout = new VerticalLayout();
			panel.setContent(verticalLayout);
			verticalLayout.setSpacing(false);
			verticalLayout.setMargin(false);
			Label roleInfo = new Label("Role Information", ContentMode.HTML);
			roleInfo.addStyleName("label-style");
			HorizontalLayout layout = new HorizontalLayout();
			layout.setSizeFull();
			HorizontalLayout layout1 = new HorizontalLayout();
			layout1.addComponent(roleInfo);
			layout1.setComponentAlignment(roleInfo, Alignment.MIDDLE_LEFT);
			layout1.setSizeFull();

			verticalLayout.addComponent(layout);
			VerticalLayout dynamicVerticalLayout = new VerticalLayout();
			verticalLayout.addComponent(dynamicVerticalLayout);
			dynamicVerticalLayout.setMargin(false);
			dynamicVerticalLayout.setSpacing(true);

			 getAndLoadPermissionGrid(dynamicVerticalLayout, false);

			getAndLoadRolesGrid(verticalLayout, dynamicVerticalLayout);
		} catch (Exception e) {
			rolesService.logRoleScreenErrors(e);
		}

		mainView.getRoles().setEnabled(true);
	}

	public Panel getAndLoadRolesPanel() {
		Panel panel = new Panel();
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

	public void getAndLoadPermissionGrid(VerticalLayout verticalLayout, boolean isEditableOnly) {

		roleInfoFormLayout = new VerticalLayout();

		String role = selectedRole.getName() != null ? selectedRole.getName() : "";
		roleName = new TextField("Role Name", role);
		roleName.setValue(role);
		roleName.setWidth("100%");
		roleName.addStyleNames("v-textfield-font", "textfiled-height");
		roleName.setMaxLength(50);
		roleName.addValueChangeListener(listener -> {
			if (listener.getValue().length() == 50) {
				Notification search = Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});

		selectedRole.setName(roleName.getValue());
		boolean active = selectedRole.isAvailable();
		activeBox = new CheckBox("Allow Access", active);
		activeBox.addStyleName("v-textfield-font");
		label = new Label("Active");
		label.setStyleName("role-activeLable");
		label.addStyleNames("v-textfield-font", "role-activeLabel");

		permissionGrid = new Grid<Permission>();
		permissionGrid.setEnabled(isEditableOnly);
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		FormLayout roleNameFL = new FormLayout();
		HorizontalLayout activeCheck = new HorizontalLayout();
		activeCheck.addStyleName("role-activePadding");
		horizontalLayout.setWidth("100%");
		horizontalLayout.setSpacing(false);
		horizontalLayout.setMargin(false);
		roleNameFL.addComponent(roleName);
		roleNameFL.setComponentAlignment(roleName, Alignment.MIDDLE_LEFT);
		roleNameFL.setSpacing(false);
		roleNameFL.setMargin(false);
		roleNameFL.setStyleName("role-name-layout");
		activeCheck.setSpacing(false);
		activeCheck.setMargin(false);
		activeCheck.addComponent(label);
		activeCheck.addComponent(activeBox);
		activeCheck.setSizeUndefined();
		activeCheck.addStyleName("role-activeLayout");
		horizontalLayout.addComponent(roleNameFL);
		horizontalLayout.addComponent(activeCheck);
		horizontalLayout.setComponentAlignment(roleNameFL, Alignment.MIDDLE_LEFT);
		horizontalLayout.setComponentAlignment(activeCheck, Alignment.MIDDLE_LEFT);
		roleInfoFormLayout.addComponent(roleName);
		String description = selectedRole.getDescription() != null ? selectedRole.getDescription() : "";
		descriptions = new TextField("Description", description);
		selectedRole.setDescription(descriptions.getValue());
		descriptions.setWidth("100%");
		descriptions.addStyleNames("v-textfield-font", "textfiled-height");
		descriptions.setMaxLength(50);
		descriptions.addValueChangeListener(listener -> {
			if (listener.getValue().length() == 50) {
				Notification search = Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});
		roleInfoFormLayout.addComponent(descriptions);
		//roleInfoFormLayout.addComponent(activeCheck);
		roleInfoFormLayout.setStyleName("role-gridLayout");
		
		GridLayout permissionGridLayout = new GridLayout(1, 11);
		//permissionGridLayout.setWidth("90%");
		permissionGridLayout.addStyleName("role-createdeleteButtonLayout");
		permissionGridLayout.addComponent(getLabelForPermissionLeft(),0,0);
//		permissionGridLayout.addComponent(getLabelForPermissionRight(),1,0);
		permissionGridLayout.addComponent(getPermissionCheckBoxLayout("DashBoard"), 0, 1);
		permissionGridLayout.addComponent(getPermissionCheckBoxLayout("AppStore"), 0, 2);
		permissionGridLayout.addComponent(getPermissionCheckBoxLayout("Personalization"), 0, 3);
		permissionGridLayout.addComponent(getPermissionCheckBoxLayout("HeartBeat"), 0, 4);
		permissionGridLayout.addComponent(getPermissionCheckBoxLayout("Asset"), 0, 5);
		permissionGridLayout.addComponent(getPermissionCheckBoxLayout("Odometer"), 0, 6);
		permissionGridLayout.addComponent(getPermissionCheckBoxLayout("Audit"), 0, 7);
		permissionGridLayout.addComponent(getPermissionCheckBoxLayout("User"), 0, 8);
		permissionGridLayout.addComponent(getPermissionCheckBoxLayout("Role"), 0, 9);
		permissionGridLayout.addComponent(getPermissionCheckBoxLayout("System"), 0, 10);
//		permissionGridLayout.addComponent(getPermissionCheckBoxLayout("Schedule"), 1, 4);
//		permissionGridLayout.addComponent(getPermissionCheckBoxLayout("Boarding"), 1, 5);
//		permissionGridLayout.addComponent(getPermissionCheckBoxLayout("Transactions"), 1, 6);
		//permissionGridLayout.setComponentAlignment(labelLayoutLeft, Alignment.TOP_RIGHT);
		//permissionGridLayout.setComponentAlignment(labelLayoutRight, Alignment.TOP_RIGHT);
		roleInfoFormLayout.addComponent(permissionGridLayout);
		cancel = new Button("Cancel");
		cancel.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		cancel.addStyleName("v-button-customstyle");
		cancel.setDescription("Cancel");
		cancel.addClickListener(event -> {
			roleGrid.getDataProvider().refreshAll();
			roleGrid.deselectAll();
			selectedRole = new Role();
			clearAllComponents();
			permissionGridLayout.iterator().forEachRemaining(layout -> {
				((HorizontalLayout) layout).iterator().forEachRemaining(layout1 -> {
					if(layout1 instanceof HorizontalLayout) {
						((HorizontalLayout) layout1).iterator().forEachRemaining(component->{
							if (component instanceof CheckBox)
								((CheckBox) component).clear();
						});
					}
					
					
				});
			});
		});
		HorizontalLayout layout2 = new HorizontalLayout();
		layout2.setResponsive(true);
		cancel.setResponsive(true);
		layout2.addComponent(cancel);
		save = new Button("Save");
		save.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		save.addStyleName("v-button-customstyle");
		save.setDescription("Save");
		save.addClickListener(event -> {
			String descriptionOfrole = descriptions.getValue();
			String rolename = roleName.getValue();
			activeBox.setValue(true);
			boolean activeValue = activeBox.getValue();
			selectedRole.setDescription(descriptionOfrole);
			selectedRole.setName(rolename);
			selectedRole.setAvailable(activeValue);
			List<Permission> listOfPermission = new ArrayList<>();
			permissionGridLayout.forEach(component -> {
				HorizontalLayout layout = ((HorizontalLayout) component);
				if(StringUtils.isNotBlank(layout.getId())){
				Permission pagePermission = new Permission(((Label) layout.getComponent(0)).getValue(),
						((CheckBox) layout.getComponent(1)).getValue(), ((CheckBox) layout.getComponent(2)).getValue(),
						((CheckBox) layout.getComponent(3)).getValue(), ((CheckBox) layout.getComponent(4)).getValue());
				listOfPermission.add(pagePermission);
				}
			});

			selectedRole.setPermissions(listOfPermission);
			if (!(ComponentUtil.validatorTextField(roleName) && ComponentUtil.validatorTextField(descriptions)
					&& ComponentUtil.validatorCheckBox(activeBox))) {
			} else {
				rolesRepo.put(selectedRole.getId(), selectedRole);
				if (roleGrid.getSelectedItems().size() <= 0) {
					rolesService.createUpdateRole(selectedRole);
				} else {
					rolesService.createUpdateRole(roleGrid.getSelectedItems().iterator().next());
				}
				DataProvider data = new ListDataProvider(rolesService.getRoleList());
				roleGrid.setDataProvider(data);
				roleGrid.select(selectedRole);
				selectedRole = new Role();

			}
			
			rolesWindow.close();
		});
		save.setResponsive(true);
		save.setEnabled(rolesViewPermission.getAdd() || rolesViewPermission.getEdit());
		layout2.addComponent(save);
		layout2.setComponentAlignment(cancel, Alignment.MIDDLE_RIGHT);
		layout2.setComponentAlignment(save, Alignment.MIDDLE_RIGHT);
		layout2.setStyleName("role-createdeleteButtonLayout");

		// fill data to grid
		roleInfoFormLayout.addComponents(layout2);
		roleInfoFormLayout.setComponentAlignment(layout2, Alignment.BOTTOM_RIGHT);

//		verticalLayout.addComponent(roleInfoFormLayout);
		List<Permission> beanList = new ArrayList<>(selectedRole.getPermissions());
		permissionGrid.setHeightByRows(10);
		permissionGrid.addStyleName(ValoTheme.TABLE_BORDERLESS);
		permissionGrid.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
		permissionGrid.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
		permissionGrid.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		permissionGrid.setHeaderVisible(false);
		permissionGrid.setId("permissionGrid");

		CheckBox access = new CheckBox();
		access.addStyleName("v-textfield-font");
		access.setEnabled(isEditableOnly);

		CheckBox edit = new CheckBox();
		edit.addStyleName("v-textfield-font");
		edit.setEnabled(isEditableOnly);
		edit.addValueChangeListener(change -> {
			if (change.getValue() && !access.getValue())
				access.setValue(true);
		});

		CheckBox delete = new CheckBox();
		delete.addStyleName("v-textfield-font");
		delete.setEnabled(isEditableOnly);
		delete.addValueChangeListener(change -> {
			if (change.getValue() && !access.getValue())
				access.setValue(true);
		});

		CheckBox add = new CheckBox();
		add.addStyleName("v-textfield-font");
		add.setEnabled(isEditableOnly);
		add.addValueChangeListener(change -> {
			if (change.getValue() && !access.getValue())
				access.setValue(true);
		});

		permissionGrid.addColumn(Permission::getPageName).setCaption("");

		permissionGrid.addColumn(Permission::getAccess, new CheckboxRenderer<>(Permission::setAccess))
				.setCaption("Access").setEditorComponent(access, Permission::setAccess).setEditable(isEditableOnly);
		permissionGrid.addColumn(Permission::getEdit, new CheckboxRenderer<>(Permission::setEdit)).setCaption("Edit")
				.setEditorComponent(edit, Permission::setEdit).setEditable(isEditableOnly);

		permissionGrid.addColumn(Permission::getDelete, new CheckboxRenderer<>(Permission::setDelete))
				.setCaption("Delete").setEditorComponent(delete, Permission::setDelete).setEditable(isEditableOnly);

		permissionGrid.addColumn(Permission::getAdd, new CheckboxRenderer<>(Permission::setAdd)).setCaption("Add")
				.setEditorComponent(add, Permission::setAdd).setEditable(isEditableOnly);

		permissionGrid.getEditor().setEnabled(isEditableOnly);
		permissionGrid.getEditor().setBuffered(false);
//		permissionGrid.getEditor().addSaveListener(save -> {
//			if (save.getBean().getAdd() || save.getBean().getEdit() || save.getBean().getDelete()) {
//				save.getBean().setAccess(true);
//			}
//		});
		permissionGrid.setWidth("100%");
		permissionGrid.setItems(beanList);
		//verticalLayout.addComponent(permissionGrid);
		//verticalLayout.setComponentAlignment(permissionGrid, Alignment.MIDDLE_CENTER);
	}

	public void getAndLoadRolesGrid(VerticalLayout verticalLayout, VerticalLayout dynamicVerticalLayout) {
//		Button addNewRole = new Button(VaadinIcons.FOLDER_ADD);
//		addNewRole.addStyleName(ValoTheme.BUTTON_FRIENDLY);
//		addNewRole.addStyleName("v-button-customstyle");
//		addNewRole.addClickListener(new ClickListener() {
//			public void buttonClick(ClickEvent event) {
//				dynamicVerticalLayout.removeAllComponents();
//				selectedRole = new Role();
//				getAndLoadPermissionGrid(dynamicVerticalLayout, true);
//				roleName.focus();
//			}
//		});
//		addNewRole.setEnabled(rolesViewPermission.getAdd());
//		addNewRole.setDescription("Create Role");
//		Button editRole = new Button(VaadinIcons.EDIT);
//		editRole.addStyleName(ValoTheme.BUTTON_FRIENDLY);
//		editRole.addStyleName("v-button-customstyle");
//		editRole.addClickListener(new ClickListener() {
//			public void buttonClick(ClickEvent event) {
//				if (descriptions.getValue() == null || descriptions.getValue().isEmpty() || roleName.getValue() == null
//						|| roleName.getValue().isEmpty()) {
//					Notification.show(NotificationUtil.ROLES_EDIT, Notification.Type.ERROR_MESSAGE);
//				} else {
//					dynamicVerticalLayout.removeAllComponents();
//					getAndLoadPermissionGrid(dynamicVerticalLayout, true);
//					roleName.focus();
//				}
//			}
//		});
//		editRole.setEnabled(rolesViewPermission.getEdit());
//		editRole.setDescription("Edit Role");
//		Button deleteRole = new Button(VaadinIcons.TRASH);
//		deleteRole.addStyleName(ValoTheme.BUTTON_FRIENDLY);
//		deleteRole.addStyleName("v-button-customstyle");
//		deleteRole.addClickListener(new ClickListener() {
//
//			@Override
//			public void buttonClick(ClickEvent event) {
//				if (descriptions.getValue() == null || descriptions.getValue().isEmpty() || roleName.getValue() == null
//						|| roleName.getValue().isEmpty()) {
//					Notification.show(NotificationUtil.ROLES_DELETE, Notification.Type.ERROR_MESSAGE);
//				} else {
//
//					confirmDialog(dynamicVerticalLayout);
//				}
//			}
//		});
//		deleteRole.setEnabled(rolesViewPermission.getDelete());
//		deleteRole.setDescription("Delete Role");
//		HorizontalLayout buttonGroup = new HorizontalLayout();
//		buttonGroup.addStyleName("role-createdeleteButtonLayout");
//		buttonGroup.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
//		buttonGroup.addComponent(addNewRole);
//		buttonGroup.addComponent(editRole);
//		buttonGroup.addComponent(deleteRole);

		roleGrid.setCaptionAsHtml(true);
		roleGrid.addColumn(Role::getName).setCaption("Role Name");
		roleGrid.addColumn(Role::getDescription).setCaption("Description");
		roleGrid.addColumn(Role::isAvailable).setCaption("Active");

		roleGrid.setHeightByRows(6);
		DataProvider data = new ListDataProvider(rolesService.getRoleList());
		roleGrid.setDataProvider(data);
		roleGrid.setWidth("100%");
		roleGrid.setResponsive(true);

		rolesWindow = new ContextMenuWindow();
		rolesWindow.addMenuItems(roleInfoFormLayout);
		rolesWindow.center();
		rolesWindow.setWidth("20%");
		rolesWindow.setHeight("55%");
		rolesWindow.setResizable(true);
		rolesWindow.setClosable(true);
		rolesWindow.setDraggable(true);
		
		createRoleMenu = new Button("Create Role", click -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			roleInfoFormLayout.iterator().forEachRemaining(component -> {
				if (component instanceof TextField)
					((TextField) component).clear();
				if (component instanceof CheckBox)
					((CheckBox) component).clear();
			});
			UI.getCurrent().addWindow(rolesWindow);

		});
		createRoleMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);

		editRoleMenu = new Button("Edit Role", click -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			if (!roleGrid.getSelectionModel().getFirstSelectedItem().isPresent())
				Notification.show("Please Select a Role", Type.ERROR_MESSAGE);
			else {
				selectedRole = roleGrid.getSelectedItems().iterator().next();
				assignValues(selectedRole);
				UI.getCurrent().addWindow(rolesWindow);
			}
		});
		editRoleMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);

		deleteRoleMenu = new Button("Delete Role", click -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			confirmDialog(dynamicVerticalLayout);
		});
		deleteRoleMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		ContextMenuWindow roleGridContextMenuWindow = new ContextMenuWindow();
		roleGridContextMenuWindow.addMenuItems(createRoleMenu, editRoleMenu, deleteRoleMenu);
	
		roleGrid.addContextClickListener(click -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			roleGridContextMenuWindow.setPosition(click.getClientX(), click.getClientY());
			UI.getCurrent().addWindow(roleGridContextMenuWindow);
		});

		UI.getCurrent().addClickListener(listener->{
			if(rolesWindow!=null) {
				rolesWindow.close();
			}
			
			if(roleGridContextMenuWindow!=null) {
				roleGridContextMenuWindow.close();
			}
		});
		
		roleGrid.addSelectionListener(e -> {
			if (!e.isUserOriginated()) {
				return;
			}

			if (e.getFirstSelectedItem().isPresent()) {
				
				// Load and update the data in permission grid.
				dynamicVerticalLayout.removeAllComponents();
				Role selectedRole = e.getFirstSelectedItem().get();
				this.selectedRole = selectedRole;
				getAndLoadPermissionGrid(dynamicVerticalLayout, false);
				((GridLayout) roleInfoFormLayout.getComponent(2)).iterator().forEachRemaining(layout->{
					((HorizontalLayout) layout).iterator().forEachRemaining(layout1 -> {
						if(layout1 instanceof HorizontalLayout) {
							String pageName = layout1.getId();
							if(pageName!=null && !pageName.isEmpty()) {
								pagePermission = selectedRole.getPermissions().stream()
									.filter(perm -> perm.getPageName().equalsIgnoreCase(pageName)).findFirst().get();
							}
							
							((HorizontalLayout) layout1).iterator().forEachRemaining(component->{
								if (component instanceof CheckBox) {
									CheckBox permissionCheck = ((CheckBox) component);
									switch (permissionCheck.getId()) {
									case "view":
										permissionCheck.setValue(pagePermission.getAccess());
										break;
									case "add":
										permissionCheck.setValue(pagePermission.getAdd());
										break;
									case "edit":
										permissionCheck.setValue(pagePermission.getEdit());
										break;
									case "delete":
										permissionCheck.setValue(pagePermission.getDelete());
										break;

									default:
										break;
									}
								}
							});
						}
						
						
					});
				});
				
				
//				((GridLayout) roleInfoFormLayout.getComponent(2)).forEach(component -> {
//					String pageName = ((HorizontalLayout) component).getComponent(0).getId();
//					Permission pagePermission = selectedRole.getPermissions().stream()
//							.filter(perm -> perm.getPageName().equalsIgnoreCase(pageName)).findFirst().get();
//					((HorizontalLayout) component).getComponent(1).forEach(subComponent -> {
//						if (subComponent instanceof CheckBox) {
//							CheckBox permissionCheck = ((CheckBox) subComponent);
//							switch (permissionCheck.getId()) {
//							case "view":
//								permissionCheck.setValue(pagePermission.getAccess());
//								break;
//							case "add":
//								permissionCheck.setValue(pagePermission.getAdd());
//								break;
//							case "edit":
//								permissionCheck.setValue(pagePermission.getEdit());
//								break;
//							case "delete":
//								permissionCheck.setValue(pagePermission.getDelete());
//								break;
//
//							default:
//								break;
//							}
//						}
//
//					});
//				});
			} else {
				selectedRole = null;
			}
		});
		VerticalLayout roleGridLayout = new VerticalLayout();
		roleGridLayout.addStyleName("role-gridLayout");
		roleGridLayout.setSizeFull();
		roleGridLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		roleGridLayout.addComponent(roleGrid);
		verticalLayout.addComponent(roleGridLayout);
	}
	
	private void clearAllComponents() {
		roleName.clear();
		activeBox.clear();
		descriptions.clear();
	}
	
	private void assignValues(Role selectRole) {
		roleName.setValue(selectRole.getName());
		activeBox.setValue(selectRole.isAvailable());
		descriptions.setValue(selectRole.getDescription());
	}

	public void confirmDialog(VerticalLayout dynamicVerticalLayout) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?", "Ok", "Cancel",
				new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							// Confirmed to continue
							rolesService.deleteRole(selectedRole.getId());
							dynamicVerticalLayout.removeAllComponents();
							roleGrid.setItems(rolesService.getRoleList());
							selectedRole = new Role();
							getAndLoadPermissionGrid(dynamicVerticalLayout, false);
						} else {
							// User did not confirm

						}
					}
				});
	}

	private HorizontalLayout getLabelForPermissionLeft() {
		labelLayoutLeft = new HorizontalLayout();
		Label view = new Label("View");
		Label add = new Label("Add");
		Label edit = new Label("Edit");
		Label delete = new Label("Del.");
		labelLayoutLeft.addComponents(view,add,edit,delete);
		labelLayoutLeft.setDefaultComponentAlignment(Alignment.TOP_RIGHT);
		labelLayoutLeft.addStyleName("role-lableLayout");
		return labelLayoutLeft;
	}
	
	private HorizontalLayout getLabelForPermissionRight() {
		labelLayoutRight = new HorizontalLayout();
		Label view = new Label("View");
		Label add = new Label("Add");
		Label edit = new Label("Edit");
		Label delete = new Label("Del.");
		labelLayoutRight.addComponents(view,add,edit,delete);
		labelLayoutRight.setDefaultComponentAlignment(Alignment.TOP_RIGHT);
		labelLayoutRight.addStyleName("role-lableLayout");
		return labelLayoutRight;
	}
	private HorizontalLayout getPermissionCheckBoxLayout(String permissionPage) {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setWidth("100%");
		HorizontalLayout horizontallabelLayout = new HorizontalLayout();
		HorizontalLayout horizontalCheckBoxLayout = new HorizontalLayout();
		horizontalCheckBoxLayout.addStyleName("role-checkBoxLayout");
		horizontallabelLayout.setId(permissionPage.toLowerCase());
		Label pageLabel = new Label(permissionPage);
		pageLabel.addStyleName("role-pageLabel");
		CheckBox viewPermission = new CheckBox();
		CheckBox addPermission = new CheckBox();
		CheckBox editPermission = new CheckBox();
		CheckBox deletePermission = new CheckBox();
		// FIXME : The logic of checkbox auto value change
		viewPermission.setId("view");
		addPermission.setId("add");
		editPermission.setId("edit");
		deletePermission.setId("delete");
		
		viewPermission.addStyleName("role-checkBoxes");
		addPermission.addStyleName("role-checkBoxes");
		editPermission.addStyleName("role-checkBoxes");
		deletePermission.addStyleName("role-checkBoxes");
		
		addPermission.addValueChangeListener(value -> {
			if (!viewPermission.getValue())
				viewPermission.setValue(value.getValue());
		});
		editPermission.addValueChangeListener(value -> {
			if (!viewPermission.getValue())
				viewPermission.setValue(value.getValue());
		});
		deletePermission.addValueChangeListener(value -> {
			if (!viewPermission.getValue())
				viewPermission.setValue(value.getValue());
		});
		horizontallabelLayout.addComponents(pageLabel);
		horizontalCheckBoxLayout.addComponents(viewPermission, addPermission, editPermission, deletePermission);
		horizontallabelLayout.setComponentAlignment(pageLabel, Alignment.MIDDLE_LEFT);
//		horizontallabelLayout.setExpandRatio(pageLabel, 2);
//		horizontallabelLayout.setExpandRatio(viewPermission, 1);
//		horizontallabelLayout.setExpandRatio(addPermission, 1);
//		horizontallabelLayout.setExpandRatio(editPermission, 1);
//		horizontallabelLayout.setExpandRatio(deletePermission, 1);
		horizontalLayout.addComponents(horizontallabelLayout, horizontalCheckBoxLayout);
		return horizontalLayout;
	}
}
