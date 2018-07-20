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

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.HasValue;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.tms.app.HasLogger;
import com.luretechnologies.tms.backend.data.Role;
import com.luretechnologies.tms.backend.data.entity.AbstractEntity;
import com.luretechnologies.tms.backend.data.entity.Node;
import com.luretechnologies.tms.backend.data.entity.NodeLevel;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.service.RolesService;
import com.luretechnologies.tms.backend.service.TreeDataNodeService;
import com.luretechnologies.tms.backend.service.TreeDataService;
import com.luretechnologies.tms.backend.service.UserService;
import com.luretechnologies.tms.ui.NotificationUtil;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Focusable;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
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
@Secured(Role.ADMIN)
public abstract class AbstractCrudView<T extends AbstractEntity> extends VerticalLayout implements Serializable, View, HasLogger {

	public static final String CAPTION_DISCARD = "Cancel";
	public static final String CAPTION_CANCEL = "Cancel";
	public static final String CAPTION_UPDATE = "Save";
	public static final String CAPTION_ADD = "Save";
	public PasswordEncoder passwordEncoder;
	public static Button addTreeNode, deleteTreeNode;
	public static TextField treeNodeInputLabel;
	public static TextField treeNodeSearch;

	@Autowired
	public TreeDataNodeService treeDataService;
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public RolesService rolesService;

	@Override
	public void enter(ViewChangeEvent event) {
		getPresenter().viewEntered(event);
	}

	@Override
	public void beforeLeave(ViewBeforeLeaveEvent event) {
		getPresenter().beforeLeavingView(event);
	}

	public void showInitialState() {
		getForm().setEnabled(false);
		getGrid().deselectAll();
		getGrid().setHeightByRows(5);
		getUpdate().setCaption(CAPTION_UPDATE);
		getCancel().setCaption(CAPTION_DISCARD);
		getTree().setItemIconGenerator(item -> {
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
	private void initLogic() throws ApiException {
		Page.getCurrent().addBrowserWindowResizeListener(r->{
			if(r.getWidth()<=600) {
				treeNodeSearch.setHeight(28, Unit.PIXELS);
				getSearch().setHeight(28, Unit.PIXELS);
			} else if(r.getWidth()>600 && r.getWidth()<=1000){
				treeNodeSearch.setHeight(32, Unit.PIXELS);
				getSearch().setHeight(32, Unit.PIXELS);
			}else {
				treeNodeSearch.setHeight(37, Unit.PIXELS);
				getSearch().setHeight(37, Unit.PIXELS);
			}
		});
		
		
		setHeight("100%");
		//treeNodeInputLabel = new TextField();
		//addTreeNode = new Button("Add");
		//deleteTreeNode = new Button("Delete");
		//HorizontalLayout treeButtonLayout = new HorizontalLayout();
		//treeButtonLayout.addComponent(addTreeNode);
		//treeButtonLayout.addComponent(deleteTreeNode);
		VerticalLayout verticalPanelLayout = new VerticalLayout();
		verticalPanelLayout.setHeight("100%");
		verticalPanelLayout.setStyleName("split-height");
		VerticalLayout treePanelLayout = new VerticalLayout();
		
		//treePanelLayout.addComponentAsFirst(treeNodeInputLabel);
		//treePanelLayout.addComponent(treeButtonLayout);
		treeNodeSearch = new TextField();
		treeNodeSearch.setWidth("100%");
		treeNodeSearch.setIcon(VaadinIcons.SEARCH);
		treeNodeSearch.setStyleName("small inline-icon search");
		treeNodeSearch.addStyleName("v-textfield-font");
		treeNodeSearch.setPlaceholder("Search");
		configureTreeNodeSearch();
		treePanelLayout.addComponentAsFirst(treeNodeSearch);
		Tree<TreeNode> treeComponent = getUserTree(treeDataService.getTreeData());
		treePanelLayout.addComponent(treeComponent);
		treePanelLayout.setComponentAlignment(treeComponent, Alignment.BOTTOM_LEFT);
		treePanelLayout.setStyleName("split-Height-ButtonLayout");
		treePanelLayout.addStyleName("user-treeLayout");
		verticalPanelLayout.addComponent(treePanelLayout);
		//treePanelLayout.addComponent(treeComponent);
		//treePanelLayout.setMargin(true);
		//treePanelLayout.setComponentAlignment(treeComponent, Alignment.BOTTOM_LEFT);
		getSplitScreen().setFirstComponent(verticalPanelLayout);
		getSplitScreen().setSplitPosition(20);
		//getSplitScreen().addComponent(userDataLayout());
//		addTreeNode.addClickListener(click -> {
//			if (getTree().getSelectedItems().size() == 1) {
//				Node selectedNode = getTree().getSelectedItems().iterator().next();
//				Node newNode = new Node();
//				newNode.setLabel(treeNodeInputLabel.getValue().isEmpty()?"Child Node":treeNodeInputLabel.getValue());//Pick name from textfield
//				//FIXME set the userlist according the node level 
//				newNode.setUserList(selectedNode.getUserList());
//				if (selectedNode.getLevel() == NodeLevel.ENTITY)
//					newNode.setLevel(NodeLevel.REGION);
//				if (selectedNode.getLevel() == NodeLevel.REGION)
//					newNode.setLevel(NodeLevel.MERCHANT);
//				if (selectedNode.getLevel() == NodeLevel.MERCHANT)
//					newNode.setLevel(NodeLevel.TERMINAL);
//				if (selectedNode.getLevel() == NodeLevel.TERMINAL)
//					newNode.setLevel(NodeLevel.DEVICE);
//				if(selectedNode.getLevel() == NodeLevel.DEVICE) {
//					Notification.show("Not allowed to add node under Device", Type.ERROR_MESSAGE);
//					return;
//				}
//				
//
//				// switch (selectedNode.getLevel()) {
//				// case ENTITY:
//				// break;
//				// case MERCHANT:
//				// break;
//				// case REGION:
//				// break;
//				// case TERMINAL:
//				// break;
//				// case DEVICE:
//				// break;
//				// default:
//				// break;
//				// }
//				getTree().getTreeData().addItem(selectedNode, newNode);
//				getTree().getDataProvider().refreshAll();
//				
//			}
//		});
		
//		deleteTreeNode.addClickListener(click->{
//			if (getTree().getSelectedItems().size() == 1) {
//				//TODO add yes/no confirmation for delete
//			getTree().getTreeData().removeItem(getTree().getSelectedItems().iterator().next());
//				Notification.show("To be Deleted "+getTree().getSelectedItems().iterator().next(),Type.WARNING_MESSAGE);
//				getTree().getDataProvider().refreshAll();
//			}
//		});
		
		//getTree().getTreeData().addItem(selectedNode, newNode);
		getTree().getDataProvider().refreshAll();
		getEdit().addClickListener(event -> {
		if(getGrid().getSelectedItems().isEmpty()) {
			Notification.show(NotificationUtil.USER_EDIT, Type.ERROR_MESSAGE);
		}else {
			getForm().setEnabled(true);
		}
		});
		getGrid().addSelectionListener(e -> {
			if (!e.isUserOriginated()) {
				return;
			}

			if (e.getFirstSelectedItem().isPresent()) {
				User user = (User) e.getAllSelectedItems().iterator().next();
				//getPresenter().editRequest(e.getFirstSelectedItem().get());
				getUserName().setValue(user.getName());
				getRole().setValue(user.getRole());
				getFirstName().setValue(user.getFirstname());
				getLastName().setValue(user.getLastname());
				getEmail().setValue(user.getEmail());
				getActiveBox().setValue(user.isActive());
				//Need to get from backend
				getFixedIPBox().setValue(false);
				//Need to get from backend
				getIP().setValue("192.0.2.1");
				getPassFreqncy().setValue(String.valueOf(user.getPasswordFrequency()));
				//Need to get from backend
				getVerification().setValue("30");
				//getEdit().addClickListener(event -> getForm().setEnabled(true));
			} else {
				throw new IllegalStateException("This should never happen as deselection is not allowed");
			}
		});

		// Force user to choose save or cancel in form once enabled
		((SingleSelectionModel<T>) getGrid().getSelectionModel()).setDeselectAllowed(false);

		// Button logic
		getUpdate().addClickListener(event -> {
			if(getUserName().getValue()==null || getUserName().getValue().isEmpty() ||
					getFirstName().getValue()==null || getFirstName().isEmpty() ||
					getLastName().getValue()==null || getLastName().getValue().isEmpty() ||
					getPassword().getValue()==null || getPassword().isEmpty()) {
				Notification.show(NotificationUtil.SAVE, Notification.Type.ERROR_MESSAGE);
			}else {
				TreeNode selectedNode = getTree().getSelectedItems().iterator().next();
				getPresenter().updateClicked();
				try {
					DataProvider data = new TreeDataProvider(treeDataService.getTreeData());
					getTree().setDataProvider(data);
					T selectedUser = getGrid().getSelectedItems().iterator().next();
					loadGridData();
					getTree().select(selectedNode);
					getGrid().select(selectedUser);
				} catch (ApiException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

		getTree().addItemClickListener(e -> {
			List<User> userList = userService.getUsersListByEntityId(e.getItem().getId());
			DataProvider data = new ListDataProvider(userList);
			getGrid().setDataProvider(data);
			getSearch().clear();
			/*treeDataService.getTreeData();
			List<Node> nodeList = treeDataService.getUserNodeList();
			for(Node node : nodeList) {
				if(node.getLabel().equals(e.getItem().getLabel())) {
					DataProvider data = new ListDataProvider(node.getEntityList());
					getGrid().setDataProvider(data);
					getSearch().clear();
				}
			}*/
//			DataProvider data = new ListDataProvider(e.getItem().getEntityList());
//			getGrid().setDataProvider(data);
//			getSearch().clear();
			//treeNodeInputLabel.clear();
			// selectedTreeNode=e.getItem();
		});

		//getCancel().addClickListener(event -> getPresenter().cancelClicked());
		getCancel().addClickListener(event -> clearAllData());
		getDelete().addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(getGrid().getSelectedItems().isEmpty()) {
					Notification.show(NotificationUtil.USER_DELETE, Notification.Type.ERROR_MESSAGE);
				}else {
					
					getPresenter().deleteClicked(getGrid().getSelectedItems().iterator().next().getId());
					List<User> userList = userService.getUsersListByEntityId(getTree().getSelectedItems().iterator().next().getId());
					DataProvider data = new ListDataProvider(userList);
					getGrid().setDataProvider(data);
					getSearch().clear();
				}
			}
		});
		getAdd().addClickListener(event -> getPresenter().addNewClicked());

		// Search functionality
		getSearch().addValueChangeListener(event -> getPresenter().filterGrid(event.getValue(),getGrid()));
		
		getButtonsLayout().addStyleName("user-buttonsLayout");
		getSearchlayout().addStyleName("user-searchLayout");
		
		getUserName().setStyleName("role-textbox");
		getUserName().addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS,
				"v-grid-cell", "v-textfield-lineHeight");
		
		getFirstName().setStyleName("role-textbox");
		getFirstName().addStyleNames( ValoTheme.TEXTFIELD_BORDERLESS,
				"v-grid-cell", "v-textfield-lineHeight");
		
		getLastName().setStyleName("role-textbox");
		getLastName().addStyleNames( ValoTheme.TEXTFIELD_BORDERLESS,
				"v-grid-cell", "v-textfield-lineHeight");
		
		getEmail().setStyleName("role-textbox");
		getEmail().addStyleNames( ValoTheme.TEXTFIELD_BORDERLESS,
				"v-grid-cell", "v-textfield-lineHeight");
		
		getIP().setStyleName("role-textbox");
		getIP().addStyleNames( ValoTheme.TEXTFIELD_BORDERLESS,
				"v-grid-cell", "v-textfield-lineHeight");
		
		getPassword().setStyleName("role-textbox");
		getPassword().addStyleNames( ValoTheme.TEXTFIELD_BORDERLESS,
				"v-grid-cell", "v-textfield-lineHeight");
		
		getForm().addStyleNames("system-LabelAlignment", "user-formLayout");
		
		getVerification().setCaptionAsHtml(true);
		getVerification().setCaption("Verification");
		getVerification().addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size"
				, "asset-debugComboBox");
		getVerification().setPlaceholder("Frequency");
		
        getPassFreqncy().addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size",
        		"user-passFRqncyComboBox");
        getPassFreqncy().setPlaceholder("Frequency");
		
//		getRole().addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size"
//				, "asset-debugComboBox", "user-roles");
        getRole().addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size",
        		"user-rolesComboBox");
        getRole().setPlaceholder("Select Role");
        getRole().setItems(rolesService.getRoleNameList());
        //getRole().setValue();
        
		getUpdate().addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		
		getCancel().addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		
		getActiveLayout().addStyleNames("asset-debugComboBox", "user-activeLayout");
		
		getActiveBox().addStyleNames("user-activeBox", "v-textfield-font");
		
		getFixedIPLayout().addStyleNames("asset-debugComboBox", "user-fixedIPLayout");
		
		getFixedIPBox().addStyleNames("user-fixIPBox", "v-textfield-font");
		
		getPassFreqnyLayout().addStyleNames("asset-debugComboBox", "user-passFreqncyLayout");
		
		//getAuthentication().addStyleNames("asset-debugComboBox", "user-roles");
		
		getSaveCancelLayout().addStyleName("user-saveCancelLayout");
		
		getAuthentication().addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		
		getTempPassword().addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		
		getAuthenticationLayout().addStyleName("user-autheticationLayout");
		
		getAuthenticationLabel().addStyleName("user-autheticationLabel");
		
		getAuthentication().addStyleName("user-autheticationButton");
		
		
		
		int width = Page.getCurrent().getBrowserWindowWidth();
//		if(width >=1000) {
//			treeNodeSearch.setHeight(37, Unit.PIXELS);
//			getSearch().setHeight(37, Unit.PIXELS);
//		}
//		else {
//			treeNodeSearch.setHeight(28, Unit.PIXELS);
//			getSearch().setHeight(28, Unit.PIXELS);
//		}
		
		//int width = Page.getCurrent().getBrowserWindowWidth();
		if(width<=600) {
			treeNodeSearch.setHeight(28, Unit.PIXELS);
			getSearch().setHeight(28, Unit.PIXELS);
		} else if(width>600 && width<=1000){
			treeNodeSearch.setHeight(32, Unit.PIXELS);
			getSearch().setHeight(32, Unit.PIXELS);
		}else {
			treeNodeSearch.setHeight(37, Unit.PIXELS);
			getSearch().setHeight(37, Unit.PIXELS);
		}
	}

	public void loadGridData() {
		/*for (Node node : treeDataService.getTreeDataForUser().getRootItems()) {
			DataProvider dataList = new ListDataProvider(node.getEntityList());
			getGrid().setDataProvider(dataList);
		}*/
	}
	
	public void clearAllData() {
		getUserName().clear();
		getRole().clear();
		getFirstName().clear();
		getLastName().clear();
		getEmail().clear();
		getActiveBox().clear();
		//Need to get from backend
		getFixedIPBox().clear();
		//Need to get from backend
		getIP().clear();
		getPassFreqncy().clear();
		//Need to get from backend
		getVerification().clear();
		getPassword().clear();
		getForm().setEnabled(false);
	}

	public void setDataProvider(DataProvider<T, Object> dataProvider) {
		getGrid().setDataProvider(dataProvider);
	}

	/*public void setUpdateEnabled(boolean enabled) {
		getUpdate().setEnabled(enabled);
	}

	public void setCancelEnabled(boolean enabled) {
		getCancel().setEnabled(enabled);
	}*/

	public void focusField(HasValue<?> field) {
		if (field instanceof Focusable) {
			((Focusable) field).focus();
		} else {
			getLogger().warn("Unable to focus field of type " + field.getClass().getName());
		}
	}
	
	private void configureTreeNodeSearch() {
		treeNodeSearch.addValueChangeListener(changed -> {
			String valueInLower = changed.getValue().toLowerCase();
//			getTree().setTreeData(treeDataService.getFilteredTreeByNodeName(treeDataService.getTreeDataForUser(), valueInLower));
			//FIXME: only works for root node labels
//			TreeDataProvider<Node> nodeDataProvider = (TreeDataProvider<Node>) nodeTree.getDataProvider();
//			nodeDataProvider.setFilter(filter -> {
//				return filter.getLabel().toLowerCase().contains(valueInLower);
//			});
		});
		
		treeNodeSearch.addShortcutListener(new ShortcutListener("Clear",KeyCode.ESCAPE,null) {
			
			@Override
			public void handleAction(Object sender, Object target) {
				if (target == treeNodeSearch) {
					treeNodeSearch.clear();
				}
			}
		});
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
	
	protected abstract ComboBox<String> getPassFreqncy();
	
	protected abstract HorizontalLayout getSaveCancelLayout();
	
	protected abstract HorizontalLayout getAuthenticationLayout();
	
	protected abstract Label getAuthenticationLabel();

}
