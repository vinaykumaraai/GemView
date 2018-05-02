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
package com.luretechnologies.tms.ui.view.admin.user;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.TreeData;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringView;
import com.luretechnologies.tms.backend.data.entity.Node;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.service.MockUserService;
import com.luretechnologies.tms.backend.service.TreeDataService;
import com.luretechnologies.tms.ui.view.admin.AbstractCrudView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Focusable;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

@SpringView
public class UserAdminView extends AbstractCrudView<User> {

	private final UserAdminPresenter presenter;

	private final UserAdminViewDesign userAdminViewDesign;

	// private TreeDataEntity treeData;

	private Tree<Node> tree;

	private boolean passwordRequired;

	/**
	 * Custom validator to be able to decide dynamically whether the password field
	 * is required or not (empty value when updating the user is interpreted as 'do
	 * not change the password').
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
	public MockUserService userService;

	@Autowired
	public TreeDataService treeDataService;

	@Autowired
	public UserAdminView(UserAdminPresenter presenter) {
		this.presenter = presenter;
		userAdminViewDesign = new UserAdminViewDesign();
		userAdminViewDesign.setCaption("Users");
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	private void init() {
		presenter.init(this);
		getGrid().setColumns("name", "lastname");
		getGrid().addColumn(c -> c.isActive() ? VaadinIcons.CHECK.getHtml() : VaadinIcons.CLOSE_CIRCLE.getHtml(),
				new HtmlRenderer()).setCaption("active");
		getGrid().addColumn("email");
		getGrid().addColumn("role");
		getGrid().getColumn("lastname").setCaption("Last Name");
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
		Button btn = getViewComponent().add;
		btn.addStyleName("v-button-customstyle");
		return btn;
	}

	@Override
	protected Button getCancel() {
		Button btn = getViewComponent().cancel;
		btn.addStyleName("v-button-customstyle");
		return btn;
	}

	@Override
	protected Button getDelete() {
		Button btn = getViewComponent().delete;
		btn.addStyleName("v-button-customstyle");
		return btn;
	}

	@Override
	protected Button getUpdate() {
		Button btn = getViewComponent().update;
		btn.addStyleName("v-button-customstyle");
		return btn;
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
	protected TextField getUserName() {
		return getViewComponent().name;
	}

	@Override
	protected Button getEdit() {
		Button btn = getViewComponent().edit;
		btn.addStyleName("v-button-customstyle");
		return btn;
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
		this.tree = new Tree<>();
		this.tree.setSelectionMode(SelectionMode.SINGLE);
		TreeDataProvider<Node> dataProvider = new TreeDataProvider<>(treeData);
		tree.setTreeData(treeData);

		return tree;

	}
}
