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
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.luretechnologies.tms.backend.ITreeDataEntity;
import com.luretechnologies.tms.backend.data.entity.Node;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.service.TreeDataService;
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
//@SpringComponent
public class UserAdminView extends AbstractCrudView<User> {

	private final UserAdminPresenter presenter;

	private final UserAdminViewDesign userAdminViewDesign;
	
	//private TreeDataEntity treeData;
	
	private Tree<Node> tree;
	
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
	public TreeDataService treeDataService;
	
	
	@Autowired
	public UserAdminView(UserAdminPresenter presenter) {
		this.presenter = presenter;
		userAdminViewDesign = new UserAdminViewDesign();
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	private void init() {
		presenter.init(this);
		getGrid().setColumns("name", "lastname", "active","email","role");
		/*getAdd().addClickListener(e -> {
			
			//TreeData<Node> treeData = getUserTree().getTreeData();
			List<Node> nodeList = treeData.getRootItems();
			List<User> userList = userService.userList();
			getUserTree().setTreeData(treeDataService.getTreeData());
		});*/
		
		//getUserTree(treeDataService.getTreeData()).ad
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
	public Grid<User> getGrid() {
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
	protected Tree<Node> getTree() {
		return this.tree;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Tree<Node> getUserTree(TreeData<Node> treeData) {
			//getViewComponent().lo
			this.tree = new Tree<>();
			
			System.out.println("Click Started in tree");
			List<Node> nodelist1 =treeData.getRootItems();
			for(Node node : nodelist1) {
				List<User> userList = node.getUserList();
				for(User user: userList) {
					System.out.println(user.getEmail());
				}
			}
			
			TreeDataProvider<Node> dataProvider = new TreeDataProvider<>(treeData);
			//TreeDataProvider<Node> dataProvider = new TreeDataProvider<>(treeDataService.getTreeData());
			//tree.setDataProvider(dataProvider);
			tree.setIcon(VaadinIcons.BUILDING_O);
			//tree.getDataProvider().refreshAll();
			tree.setTreeData(treeData);
//			tree.addSelectionListener(e -> {
//				System.out.println("Click on click");
//				//getPresenter().getLevelUsers(e.getItem());
//				//getGrid().setData(new User("carlos@gmail.com", "carlos", passwordEncoder.encode("carlos"), Role.HR, "carlos", "romero", true));
//				List<User> userList = e.getFirstSelectedItem().get().getUserList();
//				//e.getItem().getUserList()
//				//e.getItem().getUserList().d
//				DataProvider data = new ListDataProvider(e.getFirstSelectedItem().get().getUserList());
//				/*data.addDataProviderListener(event -> {
//					List<Node> nodelist=treeDataService.getTreeData().getRootItems();
//					for(Node node : nodelist) {
//						List<User> userList1 = node.getUserList();
//						
//					}
//				});*/
//				List<Node> nodelist = treeDataService.getTreeData().getRootItems();
//				for(Node node : nodelist) {
//					List<User> userList1 = node.getUserList();
//				}
//				
////				grid.setDataProvider(data);
////				setGrid(grid);
//				for(User user : userList) {
//					System.out.println(user.getEmail());
//				}
//				getGrid().setDataProvider(data);
//				getGrid().getDataProvider().refreshAll();
//				System.out.println("end");
//				//Notification.show("Success"+ e.getMouseEventDetails().getButtonName()).setDelayMsec(5000);
//			});
			
			return tree;
	}

}
