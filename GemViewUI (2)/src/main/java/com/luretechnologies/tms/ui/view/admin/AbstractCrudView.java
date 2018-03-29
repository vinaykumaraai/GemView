package com.luretechnologies.tms.ui.view.admin;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.HasValue;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.luretechnologies.tms.app.HasLogger;
import com.luretechnologies.tms.backend.data.Role;
import com.luretechnologies.tms.backend.data.entity.AbstractEntity;
import com.luretechnologies.tms.backend.data.entity.Node;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.service.TreeDataService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Focusable;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
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
		getSplitScreen().setFirstComponent(getUserTree(treeDataService.getTreeData()));
		getSplitScreen().setSplitPosition(20);
		getSplitScreen().addComponent(userDataLayout());
		getForm().setEnabled(false);
		getGrid().deselectAll();
		getUpdate().setCaption(CAPTION_UPDATE);
		getCancel().setCaption(CAPTION_DISCARD);
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
		//getSplitScreen().setFirstComponent(getUserTree(treeDataService.getTreeData()));
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
		
		getTree().addSelectionListener(e -> {
			Notification.show("click listner");
			for(User user: e.getFirstSelectedItem().get().getUserList()) {
				System.out.println(user.getEmail());
			}
			DataProvider data = new ListDataProvider(e.getFirstSelectedItem().get().getUserList());
			getGrid().setDataProvider(data);
			
		});
		
		// Force user to choose save or cancel in form once enabled
		((SingleSelectionModel<T>) getGrid().getSelectionModel()).setDeselectAllowed(false);

		// Button logic
		getUpdate().addClickListener(event -> {
			getPresenter().updateClicked();
			//showInitialState();
			//getUserTree().setDataProvider(new TreeDataProvider(treeDataService.getTreeData()));
			
			Notification.show("Click Happend");
			List<Node> nodelist = treeDataService.getTreeData().getRootItems();
			for(Node node : nodelist) {
				List<User> userList = node.getUserList();
				for(User user: userList) {
					System.out.println(user.getEmail());
				}
			}
			Page.getCurrent().reload();
//			System.out.println("Begining of tree data setup");
//			DataProvider data = new TreeDataProvider(treeDataService.getTreeData());
//			getTree().setDataProvider(data);
//			//getTree().setTreeData(treeDataService.getTreeData());
//			for(Node node : getTree().getTreeData().getRootItems()) {
//				for(User user: node.getUserList()) {
//					System.out.println(user.getEmail());
//				}
//			}
//			//getUserTree(treeDataService.getTreeData());//.setTreeData(treeDataService.getTreeData());
//			System.out.println("Ending of tree data");
			});
		getCancel().addClickListener(event -> getPresenter().cancelClicked());
		getDelete().addClickListener(event -> getPresenter().deleteClicked());
		getAdd().addClickListener(event -> getPresenter().addNewClicked());

		// Search functionality
		getSearch().addValueChangeListener(event -> getPresenter().filterGrid(event.getValue()));

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
