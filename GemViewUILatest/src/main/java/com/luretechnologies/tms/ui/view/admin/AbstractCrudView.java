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
package com.luretechnologies.tms.ui.view.admin;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.tms.app.HasLogger;
import com.luretechnologies.tms.backend.data.entity.AbstractEntity;
import com.luretechnologies.tms.backend.data.entity.AppClient;
import com.luretechnologies.tms.backend.data.entity.Permission;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.service.RolesService;
import com.luretechnologies.tms.backend.service.TreeDataNodeService;
import com.luretechnologies.tms.backend.service.UserService;
import com.luretechnologies.tms.ui.MainView;
import com.luretechnologies.tms.ui.components.ComponentUtil;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.HasValue;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.SingleSelectionModel;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Base class for a CRUD (Create, read, update, delete) view.
 * <p>
 * The view has three states it can be in and the user can navigate between the
 * states with the controls present:
 * <ol>
 * <li>Initial state
 * <ul>
 * <li>Form is disabled
 * <li>Nothing is selected in grid
 * </ul>
 * <li>Adding an entity
 * <ul>
 * <li>Form is enabled
 * <li>"Delete" has no function
 * <li>"Discard" moves to the "Initial state"
 * <li>"Save" creates the entity and moves to the "Updating an entity" state
 * </ul>
 * <li>Updating an entity
 * <ul>
 * <li>Entity highlighted in Grid
 * <li>Form is enabled
 * <li>"Delete" deletes the entity from the database
 * <li>"Discard" resets the form contents to what is in the database
 * <li>"Save" updates the entity and keeps the form open
 * <li>"Save" and "Discard" are only enabled when changes have been made
 * </ol>
 *
 * @param <T>
 *            the type of entity which can be edited in the view
 */
public abstract class AbstractCrudView<T extends AbstractEntity> extends VerticalLayout implements Serializable, View, HasLogger {

	public static final String CAPTION_DISCARD = "Cancel";
	public static final String CAPTION_CANCEL = "Cancel";
	public static final String CAPTION_UPDATE = "Save";
	public static final String CAPTION_ADD = "Save";
	public PasswordEncoder passwordEncoder;
	public static Button addTreeNode, deleteTreeNode,clearSearch, clearUserSearch;
	public static TextField treeNodeInputLabel;
	public static TextField treeNodeSearch;
	Logger logger = LoggerFactory.getLogger(AbstractCrudView.class);

	@Autowired
	public TreeDataNodeService treeDataService;
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public RolesService rolesService;
	
	@Autowired
	MainView mainView;


	public void showInitialState() {
		getForm().setEnabled(false);
		getGrid().deselectAll();
		getGrid().setHeightByRows(7);
		getUpdate().setCaption(CAPTION_UPDATE);
		getCancel().setCaption(CAPTION_DISCARD);
//		getTree().setItemIconGenerator(item -> {
//			switch (item.getType()) {
//			case ENTERPRISE:
//				return VaadinIcons.ORIENTATION;
//			case ORGANIZATION:
//				return VaadinIcons.BUILDING_O;
//			case MERCHANT:
//				return VaadinIcons.SHOP;
//			case REGION:
//				return VaadinIcons.OFFICE;
//			case TERMINAL:
//				return VaadinIcons.LAPTOP;
//			case DEVICE:
//				return VaadinIcons.MOBILE_BROWSER;
//			default:
//				return null;
//			}
//		});
	}

	public void editItem(boolean isNew) {
		if (isNew) {
			getGrid().deselectAll();
			getUpdate().setCaption(CAPTION_ADD);
			getCancel().setCaption(CAPTION_CANCEL);
			getFirstFormField().focus();
			getForm().setEnabled(true);
		} else {
			getUpdate().setCaption(CAPTION_UPDATE);
			getCancel().setCaption(CAPTION_DISCARD);
			getForm().setEnabled(false);
		}
		getDelete().setEnabled(true);
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	private void initLogic(){
		try {
		Page.getCurrent().addBrowserWindowResizeListener(r->{
			if(r.getWidth()<=600) {
				treeNodeSearch.setHeight(28, Unit.PIXELS);
				getSearch().setHeight(28, Unit.PIXELS);
				clearSearch.removeStyleNames("v-button-customstyle", "audit-AuditSearchClearDesktop");
				clearSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearPhone");
				clearUserSearch.removeStyleNames("v-button-customstyle", "audit-AuditSearchClearDesktop");
				clearUserSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearPhone");
				mainView.getTitle().setValue(userService.getLoggedInUserName());
			} else if(r.getWidth()>600 && r.getWidth()<=1000){
				treeNodeSearch.setHeight(32, Unit.PIXELS);
				getSearch().setHeight(32, Unit.PIXELS);
				clearSearch.removeStyleNames("audit-AuditSearchClearDesktop", "audit-AuditSearchClearPhone");
				clearSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
				clearUserSearch.removeStyleNames("audit-AuditSearchClearDesktop", "audit-AuditSearchClearPhone");
				clearUserSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
				mainView.getTitle().setValue("gemView  "+ userService.getLoggedInUserName());
			}else {
				treeNodeSearch.setHeight(37, Unit.PIXELS);
				getSearch().setHeight(37, Unit.PIXELS);
				clearSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
				clearSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearDesktop");
				clearUserSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
				clearUserSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearDesktop");
				mainView.getTitle().setValue("gemView  "+ userService.getLoggedInUserName());
			}
		});
		
		
		setHeight("100%");
		VerticalLayout verticalPanelLayout = new VerticalLayout();
		verticalPanelLayout.setHeight("100%");
		verticalPanelLayout.setStyleName("split-height");
		VerticalLayout treePanelLayout = new VerticalLayout();
		
		treeNodeSearch = new TextField();
		treeNodeSearch.setWidth("100%");
		treeNodeSearch.setIcon(VaadinIcons.SEARCH);
		treeNodeSearch.setStyleName("small inline-icon search");
		treeNodeSearch.addStyleName("v-textfield-font");
		treeNodeSearch.setPlaceholder("Search");
		treeNodeSearch.setMaxLength(50);
		clearSearch = new Button(VaadinIcons.CLOSE);
		clearSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		CssLayout searchLayout = new CssLayout();
		searchLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		searchLayout.addComponents(treeNodeSearch,clearSearch);
		searchLayout.setWidth("85%");
		configureTreeNodeSearch();
		treePanelLayout.addComponentAsFirst(searchLayout);
		Tree<TreeNode> treeComponent = null;/*getUserTree(treeDataService.getTreeData());*/
		treePanelLayout.addComponent(treeComponent);
		treePanelLayout.setComponentAlignment(treeComponent, Alignment.BOTTOM_LEFT);
		treePanelLayout.setStyleName("split-Height-ButtonLayout");
		treePanelLayout.addStyleName("user-treeLayout");
		verticalPanelLayout.addComponent(treePanelLayout);
		getSplitScreen().setFirstComponent(verticalPanelLayout);
		getSplitScreen().setSplitPosition(20);
		getTree().getDataProvider().refreshAll();
		getEdit().addClickListener(event -> {
		if(getGrid().getSelectedItems().isEmpty()) {
			Notification.show(NotificationUtil.USER_EDIT, Type.ERROR_MESSAGE);
		}else {
			getForm().setEnabled(true);
			getUserName().setEnabled(false);
			getEmail().setEnabled(false);
		}
		});
		getGrid().addSelectionListener(e -> {
			if (!e.isUserOriginated()) {
				return;
			}

			if (e.getFirstSelectedItem().isPresent()) {
				User user = (User) e.getAllSelectedItems().iterator().next();
				getUserName().setValue(user.getName());
				getRole().setValue(user.getRole());
				getFirstName().setValue(user.getFirstname());
				getLastName().setValue(user.getLastname());
				getEmail().setValue(user.getEmail());
				getActiveBox().setValue(user.isActive());
				if(user.getIpAddress()!=null && !user.getIpAddress().isEmpty()) {
					getIP().setValue(user.getIpAddress());
				}else {
					getIP().clear();
				}
				getPassFreqncy().setValue(user.getPasswordFrequency());
				//Need to get from backend
				getFixedIPBox().setValue(false);
				//Need to get from backend
				getVerification().setValue("30");
			} else {
				throw new IllegalStateException("This should never happen as deselection is not allowed");
			}
		});

		// Force user to choose save or cancel in form once enabled
		((SingleSelectionModel<T>) getGrid().getSelectionModel()).setDeselectAllowed(false);

		// Button logic
		getUpdate().addClickListener(event -> {
			if(getTree().getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.USER_CREATE, Notification.Type.ERROR_MESSAGE);
			}else {
				if(!(ComponentUtil.validatorTextField(getUserName()) && 
						ComponentUtil.validatorComboBox(getRole()) &&
						ComponentUtil.validatorTextField(getFirstName()) &&
						ComponentUtil.validatorTextField(getLastName()) &&
						ComponentUtil.validatorTextField(getEmail()) &&
						ComponentUtil.validatorCheckBox(getActiveBox()) && 
						ComponentUtil.validatorComboBox(getPassFreqncy()))) {
				}else {
					User existingUserCheck = new User();
					if(!getGrid().getSelectedItems().isEmpty() && getGrid().getSelectedItems().iterator().next().getId()!=null) {
					existingUserCheck = userService.getUserbyId(getGrid().getSelectedItems().iterator().next().getId());
					}
					TreeNode selectedNode = getTree().getSelectedItems().iterator().next();
					if(existingUserCheck!=null && existingUserCheck.getId()!=null) {
						try {
							User clientUserUpdate = new User(existingUserCheck.getId(), getEmail().getValue(), getUserName().getValue(), getRole().getValue(),
									getFirstName().getValue(), getLastName().getValue(), getActiveBox().getValue(), getPassFreqncy().getValue(), selectedNode.getId(), getIP().getValue());
							userService.createUser(clientUserUpdate, selectedNode.getId());
							loadGridData(clientUserUpdate);
							getTree().select(selectedNode);
							getForm().setEnabled(false);
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
							User clientUser = new User(getEmail().getValue(), getUserName().getValue(), getRole().getValue(),
									getFirstName().getValue(), getLastName().getValue(), getActiveBox().getValue(), getPassFreqncy().getValue(), selectedNode.getId(), getIP().getValue());
							userService.createUser(clientUser, selectedNode.getId());
							loadGridData(clientUser);
							getTree().select(selectedNode);
							getForm().setEnabled(false);
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

		getTree().addItemClickListener(e -> {
			List<User> userList = userService.getUsersListByEntityId(e.getItem().getEntityId());
			DataProvider data = new ListDataProvider(userList);
			getGrid().setDataProvider(data);
			getSearch().clear();
		});

		getCancel().addClickListener(event -> {
			clearAllData();
			getGrid().deselectAll();
		});
		getDelete().addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(getGrid().getSelectedItems().isEmpty()) {
					Notification.show(NotificationUtil.USER_DELETE, Notification.Type.ERROR_MESSAGE);
				}else {
					confirmDialog(getGrid().getSelectedItems().iterator().next().getId());
					
				}
			}
		});

		getAdd().addClickListener(event -> {
			clearAllData();
			getForm().setEnabled(true);
			getUserName().setEnabled(true);
			getEmail().setEnabled(true);
			getUserName().focus();
			getGrid().deselectAll();
		});

		// Search functionality
			getSearch().addValueChangeListener(event -> {
				if (event.getValue().length() == 50) {
					if (getTree().getSelectedItems().size() > 0) {
						Notification search = Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
						search.addCloseListener(listener -> {
							String filter = event.getValue().toLowerCase();
							List<User> searchList = userService.searchUsers(filter);
							DataProvider data = new ListDataProvider(searchList);
							getGrid().setDataProvider(data);
						});
					} else {
						Notification.show("Select any Entity to search users", Type.ERROR_MESSAGE);
					}
				} else {
					if (getTree().getSelectedItems().size() > 0) {
						String filter = event.getValue().toLowerCase();
						List<User> searchList = userService.searchUsers(filter);
						DataProvider data = new ListDataProvider(searchList);
						getGrid().setDataProvider(data);
					} else {
						Notification.show("Select any Entity to search users", Type.ERROR_MESSAGE);
					}
				}
			});
		
		getUserSearchLayout().addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		getUserSearchLayout().setWidth("96%");
		
		clearUserSearch = new Button(VaadinIcons.CLOSE);
		clearUserSearch.addClickListener(listener->{
			getSearch().clear();
		});
		getUserSearchLayout().addComponent(clearUserSearch);
		
		getButtonsLayout().addStyleName("user-buttonsLayout");
		getSearchlayout().addStyleName("user-searchLayout");
		
		getUserName().setStyleName("role-textbox");
		getUserName().addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS,
				"v-grid-cell", "v-textfield-lineHeight");
		getUserName().addValueChangeListener(listener->{
			if(listener.getValue().length()==50) {
				Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});
		
		getFirstName().setStyleName("role-textbox");
		getFirstName().addStyleNames( ValoTheme.TEXTFIELD_BORDERLESS,
				"v-grid-cell", "v-textfield-lineHeight");
		getFirstName().addValueChangeListener(listener->{
			if(listener.getValue().length()==50) {
				Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});
		
		getLastName().setStyleName("role-textbox");
		getLastName().addStyleNames( ValoTheme.TEXTFIELD_BORDERLESS,
				"v-grid-cell", "v-textfield-lineHeight");
		getLastName().addValueChangeListener(listener->{
			if(listener.getValue().length()==50) {
				Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});
		
		getEmail().setStyleName("role-textbox");
		getEmail().addStyleNames( ValoTheme.TEXTFIELD_BORDERLESS,
				"v-grid-cell", "v-textfield-lineHeight");
		getEmail().addValueChangeListener(listener->{
			if(listener.getValue().length()==50) {
				Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});
		
		getIP().setStyleName("role-textbox");
		getIP().addStyleNames( ValoTheme.TEXTFIELD_BORDERLESS,
				"v-grid-cell", "v-textfield-lineHeight");
		getIP().addValueChangeListener(listener->{
			if(listener.getValue().length()==50) {
				Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});
		
		getForm().addStyleNames("system-LabelAlignment", "user-formLayout");
		
		getVerification().setCaptionAsHtml(true);
		getVerification().setCaption("Verification");
		getVerification().addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size"
				, "asset-debugComboBox");
		getVerification().setPlaceholder("Frequency");
		
        getPassFreqncy().addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size",
        		"user-passFRqncyComboBox");
        getPassFreqncy().setPlaceholder("Frequency");
        getPassFreqncy().setItems(10, 20, 30);
		

        getRole().addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size",
        		"user-rolesComboBox");
        getRole().setPlaceholder("Select Role");
        DataProvider data = new ListDataProvider<>(rolesService.getRoleNameList());
        getRole().setDataProvider(data);
        
		getUpdate().addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		
		getCancel().addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		
		getActiveLayout().addStyleNames("asset-debugComboBox", "user-activeLayout");
		
		getActiveBox().addStyleNames("user-activeBox", "v-textfield-font");
		
		getFixedIPLayout().addStyleNames("asset-debugComboBox", "user-fixedIPLayout");
		
		getFixedIPBox().addStyleNames("user-fixIPBox", "v-textfield-font");
		
		getPassFreqnyLayout().addStyleNames("asset-debugComboBox", "user-passFreqncyLayout");
		
		getSaveCancelLayout().addStyleName("user-saveCancelLayout");
		
		getAuthentication().addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		
		getTempPassword().addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		
		getAuthenticationLayout().addStyleName("user-autheticationLayout");
		
		getAuthenticationLabel().addStyleName("user-autheticationLabel");
		
		getAuthentication().addStyleName("user-autheticationButton");
		
		
		
		int width = Page.getCurrent().getBrowserWindowWidth();
		if(width<=600) {
			treeNodeSearch.setHeight(28, Unit.PIXELS);
			getSearch().setHeight(28, Unit.PIXELS);
			mainView.getTitle().setValue(userService.getLoggedInUserName());
			clearSearch.removeStyleNames("v-button-customstyle", "audit-AuditSearchClearDesktop");
			clearSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearPhone");
			clearUserSearch.removeStyleNames("v-button-customstyle", "audit-AuditSearchClearDesktop");
			clearUserSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearPhone");
		} else if(width>600 && width<=1000){
			treeNodeSearch.setHeight(32, Unit.PIXELS);
			getSearch().setHeight(32, Unit.PIXELS);
			clearSearch.removeStyleNames("audit-AuditSearchClearDesktop", "audit-AuditSearchClearPhone");
			clearSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
			clearUserSearch.removeStyleNames("audit-AuditSearchClearDesktop", "audit-AuditSearchClearPhone");
			clearUserSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
			mainView.getTitle().setValue("gemView  "+ userService.getLoggedInUserName());
		}else {
			treeNodeSearch.setHeight(37, Unit.PIXELS);
			getSearch().setHeight(37, Unit.PIXELS);
			clearSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
			clearSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearDesktop");
			clearUserSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
			clearUserSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearDesktop");
			mainView.getTitle().setValue("gemView  "+ userService.getLoggedInUserName());
		}
		
		Permission userScreenPermission = rolesService.getLoggedInUserRolePermissions().stream().filter(permisson -> permisson.getPageName().equals("USER")).findFirst().get();
		getEdit().setEnabled(userScreenPermission.getEdit());
		getAdd().setEnabled(userScreenPermission.getAdd());
		getCancel().setEnabled(userScreenPermission.getAdd() || userScreenPermission.getEdit());
		getDelete().setEnabled(userScreenPermission.getDelete());
		getUpdate().setEnabled(userScreenPermission.getAdd() || userScreenPermission.getEdit());
		}catch(Exception ex) {
			userService.logUserScreenErrors(ex);
		}
		
		mainView.getUsers().setEnabled(true);
	}

	public void confirmDialog(Long id) {
		ConfirmDialog.show(this.getViewComponent().getUI(), "Please Confirm:",
				"Are you sure you want to delete?", "Ok", "Cancel", null, new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
								boolean delete = false;
								delete = userService.deleteUser(id, delete);
								if(delete) {
									List<User> userList = userService.getUsersListByEntityId(getTree().getSelectedItems().iterator().next().getEntityId());
									DataProvider data = new ListDataProvider(userList);
									getGrid().setDataProvider(data);
									getSearch().clear();
								}else {
									Notification.show(NotificationUtil.SELF_USER_DELETE, Type.ERROR_MESSAGE);
								}
								
								
						} else {
							// User did not confirm

						}
					}
				});
	}
	public void loadGridData(User user) {
		if(getTree().getSelectedItems().size() == 1) {
			getGrid().setDataProvider(new ListDataProvider(userService.getUsersListByEntityId(getTree().getSelectionModel().getFirstSelectedItem().get().getEntityId())));
			getGrid().select((T) user);
		}
	}
	
	
	public void clearAllData() {
		getUserName().clear();
		getRole().clear();
		getFirstName().clear();
		getLastName().clear();
		getEmail().clear();
		getActiveBox().clear();
		getIP().clear();
		//Need to get from backend
		getFixedIPBox().clear();
		getPassFreqncy().clear();
		//Need to get from backend
		getVerification().clear();
		getForm().setEnabled(false);
	}

	public void setDataProvider(DataProvider<T, Object> dataProvider) {
		getGrid().setDataProvider(dataProvider);
	}

	public void focusField(HasValue<?> field) {
		if (field instanceof Focusable) {
			((Focusable) field).focus();
		} else {
			getLogger().warn("Unable to focus field of type " + field.getClass().getName());
		}
	}
	
	private void configureTreeNodeSearch() {
		treeNodeSearch.addValueChangeListener(changed -> {
			if (changed.getValue().length() == 50) {
				Notification search = Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
				search.addCloseListener(listener -> {
					String valueInLower = changed.getValue().toLowerCase();
					if (!valueInLower.isEmpty() && valueInLower != null) {
						getTree().setTreeData(treeDataService.searchTreeData(valueInLower));
					} else {
						getTree().setTreeData(treeDataService.getTreeData());
					}
				});
			} else {
				String valueInLower = changed.getValue().toLowerCase();
				if (!valueInLower.isEmpty() && valueInLower != null) {
					getTree().setTreeData(treeDataService.searchTreeData(valueInLower));
				} else {
					getTree().setTreeData(treeDataService.getTreeData());
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

	protected abstract AbstractCrudPresenter<T, ?, ? extends AbstractCrudView<T>> getPresenter();

	protected abstract Grid<T> getGrid();

	protected abstract void setGrid(Grid<T> grid);

	protected abstract Component getForm();

	protected abstract Button getAdd();

	protected abstract Button getCancel();

	protected abstract Button getDelete();

	protected abstract Button getUpdate();

	protected abstract TextField getSearch();

	protected abstract Focusable getFirstFormField();

	public abstract void bindFormFields(BeanValidationBinder<T> binder);

	protected abstract Button getEdit();

	protected abstract HorizontalSplitPanel getSplitScreen();

	protected abstract Tree<TreeNode> getUserTree(TreeData<TreeNode> treeData);

	protected abstract VerticalLayout userDataLayout();

	protected abstract Tree<TreeNode> getTree();

	protected abstract TextField getUserName();
	
	protected abstract HorizontalLayout getButtonsLayout();
	
	protected abstract HorizontalLayout getSearchlayout();
	
	protected abstract TextField getLastName();
	
	protected abstract TextField getFirstName();
	
	protected abstract TextField getEmail();
	
	protected abstract TextField getIP();
	
	protected abstract TextField getPassword();
	
	protected abstract ComboBox<String> getVerification();
	
	protected abstract ComboBox<String> getRole(); 
	
	protected abstract HorizontalLayout getActiveLayout();
	
	protected abstract HorizontalLayout getFixedIPLayout();
	
	protected abstract HorizontalLayout getPassFreqnyLayout();
	
	protected abstract Button getAuthentication();
	
	protected abstract Button getTempPassword();
	
	protected abstract com.vaadin.ui.CheckBox getActiveBox();
	
	protected abstract CheckBox getFixedIPBox();
	
	protected abstract ComboBox<Integer> getPassFreqncy();
	
	protected abstract HorizontalLayout getSaveCancelLayout();
	
	protected abstract HorizontalLayout getAuthenticationLayout();
	
	protected abstract Label getAuthenticationLabel();

	protected abstract CssLayout getUserSearchLayout();

}
