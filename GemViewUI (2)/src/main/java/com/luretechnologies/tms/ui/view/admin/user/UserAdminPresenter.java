package com.luretechnologies.tms.ui.view.admin.user;

import java.io.Serializable;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.service.MockUserService;
import com.luretechnologies.tms.ui.navigation.NavigationManager;
import com.luretechnologies.tms.ui.view.admin.AbstractCrudPresenter;

@SpringComponent
@ViewScope
public class UserAdminPresenter extends AbstractCrudPresenter<User, MockUserService, UserAdminView>
		implements Serializable {

	@Autowired
	public UserAdminPresenter(UserAdminDataProvider userAdminDataProvider, NavigationManager navigationManager,
			MockUserService service, BeanFactory beanFactory) {
		super(navigationManager, service, User.class, userAdminDataProvider, beanFactory);
	}

	public String encodePassword(String value) {
		return getService().encodePassword(value);
	}

	@Override
	protected void editItem(User item) {
		super.editItem(item);
		getView().setPasswordRequired(item.isNew());
	}
}