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
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.luretechnologies.tms.app.HasLogger;
import com.luretechnologies.tms.backend.data.Role;
import com.luretechnologies.tms.backend.data.entity.AbstractEntity;
import com.luretechnologies.tms.backend.data.entity.Node;
import com.luretechnologies.tms.backend.data.entity.NodeLevel;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.service.TreeDataService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Focusable;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.SingleSelectionModel;

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
public abstract class AbstractCrudView<T extends AbstractEntity> implements Serializable, View, HasLogger {

	public static final String CAPTION_DISCARD = "Discard";
	public static final String CAPTION_CANCEL = "Cancel";
	public static final String CAPTION_UPDATE = "Update";
	public static final String CAPTION_ADD = "Add";
	public PasswordEncoder passwordEncoder;
	public static Button addTreeNode, deleteTreeNode;
	public static TextField treeNodeInputLabel;

	@Autowired
	public TreeDataService treeDataService;

	@Override
	public void enter(ViewChangeEvent event) {
		getPresenter().viewEntered(event);
	}

	@Override
	public void beforeLeave(ViewBeforeLeaveEvent event) {
		getPresenter().beforeLeavingView(event);
	}

	public void showInitialState() {
		// getSplitScreen().setFirstComponent(getUserTree(treeDataService.getTreeData()));
		// getSplitScreen().setSplitPosition(20);
		// getSplitScreen().addComponent(userDataLayout());
		getForm().setEnabled(false);
		getGrid().deselectAll();
		getUpdate().setCaption(CAPTION_UPDATE);
		getCancel().setCaption(CAPTION_DISCARD);
		getTree().setItemIconGenerator(item -> {
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
		getDelete().setEnabled(!isNew);
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	private void initLogic() {
		treeNodeInputLabel = new TextField();
		addTreeNode = new Button("Add");
		deleteTreeNode = new Button("Delete");
		HorizontalLayout treeButtonLayout = new HorizontalLayout();
		treeButtonLayout.addComponent(addTreeNode);
		treeButtonLayout.addComponent(deleteTreeNode);
		VerticalLayout treePanelLayout = new VerticalLayout();
		treePanelLayout.addComponentAsFirst(treeNodeInputLabel);
		treePanelLayout.addComponent(treeButtonLayout);
		Tree<Node> treeComponent = getUserTree(treeDataService.getTreeData());
		treePanelLayout.addComponent(treeComponent);
		treePanelLayout.setMargin(true);
		treePanelLayout.setComponentAlignment(treeComponent, Alignment.BOTTOM_LEFT);
		getSplitScreen().setFirstComponent(treePanelLayout);
		getSplitScreen().setSplitPosition(35);
		getSplitScreen().addComponent(userDataLayout());
		addTreeNode.addClickListener(click -> {
			if (getTree().getSelectedItems().size() == 1) {
				Node selectedNode = getTree().getSelectedItems().iterator().next();
				Node newNode = new Node();
				newNode.setLabel(treeNodeInputLabel.getValue().isEmpty()?"Child Node":treeNodeInputLabel.getValue());//Pick name from textfield
				//FIXME set the userlist according the node level 
				newNode.setUserList(selectedNode.getUserList());
				if (selectedNode.getLevel() == NodeLevel.ENTITY)
					newNode.setLevel(NodeLevel.REGION);
				if (selectedNode.getLevel() == NodeLevel.REGION)
					newNode.setLevel(NodeLevel.MERCHANT);
				if (selectedNode.getLevel() == NodeLevel.MERCHANT)
					newNode.setLevel(NodeLevel.TERMINAL);
				if (selectedNode.getLevel() == NodeLevel.TERMINAL)
					newNode.setLevel(NodeLevel.DEVICE);
				if(selectedNode.getLevel() == NodeLevel.DEVICE) {
					Notification.show("Not allowed to add node under Device", Type.ERROR_MESSAGE);
					return;
				}
				
				getTree().getTreeData().addItem(selectedNode, newNode);
				getTree().getDataProvider().refreshAll();
				
			}
		});
		
		deleteTreeNode.addClickListener(click->{
			if (getTree().getSelectedItems().size() == 1) {
				//TODO add yes/no confirmation for delete
			getTree().getTreeData().removeItem(getTree().getSelectedItems().iterator().next());
				Notification.show("To be Deleted "+getTree().getSelectedItems().iterator().next(),Type.WARNING_MESSAGE);
				getTree().getDataProvider().refreshAll();
			}
		});
		getGrid().addSelectionListener(e -> {
			if (!e.isUserOriginated()) {
				return;
			}

			if (e.getFirstSelectedItem().isPresent()) {
				getPresenter().editRequest(e.getFirstSelectedItem().get());
				getEdit().addClickListener(event -> getForm().setEnabled(true));
			} else {
				throw new IllegalStateException("This should never happen as deselection is not allowed");
			}
		});

		// Force user to choose save or cancel in form once enabled
		((SingleSelectionModel<T>) getGrid().getSelectionModel()).setDeselectAllowed(false);

		// Button logic
		getUpdate().addClickListener(event -> {
			getPresenter().updateClicked();
			DataProvider data = new TreeDataProvider(treeDataService.getTreeData());
			getTree().setDataProvider(data);
			loadGridData();

		});

		getTree().addItemClickListener(e -> {
			DataProvider data = new ListDataProvider(e.getItem().getUserList());
			getGrid().setDataProvider(data);
			treeNodeInputLabel.clear();
			// selectedTreeNode=e.getItem();
		});

		getTree().addContextClickListener(e -> {
			
			// VerticalLayout menuLayout = new VerticalLayout();
			// Button addLayer = new Button("Add Node");
			// Button deleteLayer = new Button("Delete Node");
			// menuLayout.addComponents(addLayer, deleteLayer);
			// menuLayout.setHeight("20px");
			// menuLayout.setWidthUndefined();
			// Notification.show("Right Click Allowed");
			// this.getTree().addC
		});

		getCancel().addClickListener(event -> getPresenter().cancelClicked());
		getDelete().addClickListener(event -> getPresenter().deleteClicked());
		getAdd().addClickListener(event -> getPresenter().addNewClicked());

		// Search functionality
		getSearch().addValueChangeListener(event -> getPresenter().filterGrid(event.getValue()));

	}

	public void loadGridData() {
		for (Node node : treeDataService.getTreeData().getRootItems()) {
			DataProvider dataList = new ListDataProvider(node.getUserList());
			getGrid().setDataProvider(dataList);
		}
	}

	public void setDataProvider(DataProvider<T, Object> dataProvider) {
		getGrid().setDataProvider(dataProvider);
	}

	public void setUpdateEnabled(boolean enabled) {
		getUpdate().setEnabled(enabled);
	}

	public void setCancelEnabled(boolean enabled) {
		getCancel().setEnabled(enabled);
	}

	public void focusField(HasValue<?> field) {
		if (field instanceof Focusable) {
			((Focusable) field).focus();
		} else {
			getLogger().warn("Unable to focus field of type " + field.getClass().getName());
		}
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

	protected abstract Tree<Node> getUserTree(TreeData<Node> treeData);

	protected abstract VerticalLayout userDataLayout();

	protected abstract Tree<Node> getTree();

}
