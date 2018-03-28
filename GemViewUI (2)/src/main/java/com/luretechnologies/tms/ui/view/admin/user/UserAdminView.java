package com.luretechnologies.tms.ui.view.admin.user;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.TreeData;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringView;
import com.luretechnologies.tms.backend.data.entity.Node;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.service.UserService;
import com.luretechnologies.tms.ui.view.admin.AbstractCrudView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Focusable;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.IconGenerator;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

@SpringView
public class UserAdminView extends AbstractCrudView<User> {

	private final UserAdminPresenter presenter;

	private final UserAdminViewDesign userAdminViewDesign;
	
	private boolean passwordRequired;

	/**
	 * Custom validator to be able to decide dynamically whether the password
	 * field is required or not (empty value when updating the user is
	 * interpreted as 'do not change the password').
	 */
	private Validator<String> passwordValidator = new Validator<String>() {

		BeanValidator passwordBeanValidator = new BeanValidator(User.class, "password");

		@Override
		public ValidationResult apply(String value, ValueContext context) {
			if (!passwordRequired && value.isEmpty()) {
				// No password required and field is empty
				// OK regardless of other restrictions as the empty value will
				// not be used
				return ValidationResult.ok();
			} else {
				return passwordBeanValidator.apply(value, context);
			}
		}
	};

	@Autowired
	public UserService userService;
	
	@Autowired
	public UserAdminView(UserAdminPresenter presenter) {
		this.presenter = presenter;
		userAdminViewDesign = new UserAdminViewDesign();
	}

	@PostConstruct
	private void init() {
		presenter.init(this);
		getGrid().setColumns("name", "lastname", "active","email","role");
	}

	@Override
	public void bindFormFields(BeanValidationBinder<User> binder) {
		binder.forField(getViewComponent().password).withValidator(passwordValidator).bind(bean -> "",
				(bean, value) -> {
					if (value.isEmpty()) {
						// If nothing is entered in the password field, do
						// nothing
					} else {
						bean.setPassword(presenter.encodePassword(value));
					}
				});
		binder.bindInstanceFields(getViewComponent());
	}

	public void setPasswordRequired(boolean passwordRequired) {
		this.passwordRequired = passwordRequired;
		getViewComponent().password.setRequiredIndicatorVisible(passwordRequired);
	}

	@Override
	public UserAdminViewDesign getViewComponent() {
		return userAdminViewDesign;
	}

	@Override
	protected UserAdminPresenter getPresenter() {
		return presenter;
	}

	@Override
	protected Grid<User> getGrid() {
		return getViewComponent().list;
	}

	@Override
	protected void setGrid(Grid<User> grid) {
		getViewComponent().list = grid;
	}

	@Override
	protected Component getForm() {
		return getViewComponent().form;
	}

	@Override
	protected Button getAdd() {
		return getViewComponent().add;
	}

	@Override
	protected Button getCancel() {
		return getViewComponent().cancel;
	}

	@Override
	protected Button getDelete() {
		return getViewComponent().delete;
	}

	@Override
	protected Button getUpdate() {
		return getViewComponent().update;
	}

	@Override
	protected TextField getSearch() {
		return getViewComponent().search;
	}

	@Override
	protected Focusable getFirstFormField() {
		return getViewComponent().name;
	}
	
	@Override
	protected Button getEdit() {
		return getViewComponent().edit;
	}
	
	@Override
	protected HorizontalSplitPanel getSplitScreen() {
		return getViewComponent().splitscreen;
	}
	
	@Override
	protected VerticalLayout userDataLayout() {
		return getViewComponent().userdatalayout;
	}
	
	@Override
	protected Tree<Node> getUserTree() {
			Tree<Node> tree = new Tree<>();
			TreeData<Node> treeData = new TreeData<>();
			List<User> userList = userService.userList();
			userList.remove(1);
			
			Node node = new Node();
			node.setLabel("Enterprise Entity");
			node.setLevel("1");
			node.setUserList(userList);
			
			Node node1 = new Node();
			node1.setLabel("Region West");
			node1.setLevel("2");
			node1.setUserList(userList);

			Node node2 = new Node();
			node2.setLabel("Merchant 1");
			node2.setLevel("3");
			node2.setUserList(userList);
			
			
			treeData.addItem(null,node);
			treeData.addItem(node,node1);
			treeData.addItem(node1,node2);
			/*treeData.addItem("Enterprise Entity","Region West");
			treeData.addItem("Enterprise Entity","Region East");
			treeData.addItem("Enterprise Entity","Region North");
			treeData.addItem("Enterprise Entity","Region South");
			treeData.addItem("Region North","Merchant 1");
			treeData.addItem("Region North","Merchant 2");
			treeData.addItem("Region North","Merchant 3");*/
			
			TreeDataProvider<Node> inMemoryDataProvider = new TreeDataProvider<>(treeData);
			tree.setDataProvider(inMemoryDataProvider);
			/*tree.setItemIconGenerator(e ->{
				
			});*/
			tree.setIcon(VaadinIcons.BUILDING_O);
			tree.addItemClickListener(e -> {
				Notification.show("Begin"+ e, Notification.Type.ERROR_MESSAGE).setDelayMsec(5000);
				System.out.println("Begining");
				//getPresenter().getLevelUsers(e.getItem());
				//getGrid().setData(new User("carlos@gmail.com", "carlos", passwordEncoder.encode("carlos"), Role.HR, "carlos", "romero", true));
				e.getItem().getUserList();
				Grid grid = new Grid<>();
				DataProvider data = new ListDataProvider(e.getItem().getUserList());
				grid.setDataProvider(data);
				//setGrid(grid);
				getGrid().setDataProvider(data);
				System.out.println("end");
				Notification.show("Success"+ e.getMouseEventDetails().getButtonName()).setDelayMsec(5000);;
			});
			
			return tree;
	}

}
