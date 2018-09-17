package com.luretechnologies.tms.ui.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.luretechnologies.tms.backend.service.UserService;
import com.luretechnologies.tms.ui.MainView;
import com.luretechnologies.tms.ui.view.admin.user.UserAdminView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class Header extends HorizontalLayout {

	UserService userService;
	MainView mainView;
	
	public Header(UserService userService,MainView mainView, String caption,Component...components) {
//		this.setStyleName(ValoTheme.PANEL_WELL);
//		this.setCaption(caption);
		this.setWidth("100%");
		this.setHeight("90px");
		this.userService = userService;
		this.mainView = mainView;
		Label headerCaption = new Label(caption);
		headerCaption.addStyleName("header-label");
		//headerCaption.setStyleName(ValoTheme.LABEL_LARGE);
		Label userName = new Label(userService.getLoggedInUserName());
		userName.addStyleName("header-UserName");
		Label userRole = new Label(userService.getLoggedInUser().getRole());
		userRole.addStyleName("header-UserRole");
		VerticalLayout userLayout = new VerticalLayout(userName,userRole);
		userLayout.addStyleName("header-UserNameLayout");
		ContextMenuWindow userMenuWindow = new ContextMenuWindow();
		Button logOut = new Button("Logout",click -> {
				UI ui = getUI();
				ui.getSession().getSession().invalidate();
				ui.getPage().reload();
		});
		logOut.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		logOut.setIcon(VaadinIcons.SIGN_OUT);
		Button Users = new Button("Users",click -> {
			Button us = mainView.getUsers();
			mainView.attachNavigation(us, UserAdminView.class);
			userMenuWindow.close();
		});
		Users.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		Users.setIcon(VaadinIcons.USERS);
		Button Roles = new Button("Roles",click -> {
			
		});
		Roles.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		Roles.setIcon(VaadinIcons.USER);
		Button System = new Button("System Parameters",click -> {
			
		});
		System.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		System.setIcon(VaadinIcons.DESKTOP);
		userMenuWindow.addStyleName("header-menuWindow");
		userMenuWindow.addMenuItems(Users);
		userMenuWindow.addMenuItems(Roles);
		userMenuWindow.addMenuItems(System);
		userMenuWindow.addMenuItems(logOut);
		Button userMenuButton = new Button(VaadinIcons.ELLIPSIS_DOTS_V, click->{
			UI.getCurrent().getWindows().forEach(Window::close);
			userMenuWindow.setPosition(click.getClientX(), click.getClientY());
			UI.getCurrent().addWindow(userMenuWindow);
		});
		userMenuButton.addStyleNames(ValoTheme.BUTTON_BORDERLESS, "header-menuButton");
		GridLayout rightSideLayout = new GridLayout(2, 1);
		rightSideLayout.addComponents(userLayout,userMenuButton);
		HorizontalLayout leftSideLayout = new HorizontalLayout(headerCaption);
		this.addComponent(leftSideLayout);
		this.addComponents(components);
		this.addComponent(rightSideLayout);
		this.setComponentAlignment(leftSideLayout, Alignment.BOTTOM_LEFT);
		this.setComponentAlignment(rightSideLayout, Alignment.BOTTOM_RIGHT);
		this.addLayoutClickListener(click ->{
			
			if(click.getClickedComponent() == null || !click.getClickedComponent().equals(userMenuButton)) {
				UI.getCurrent().getWindows().forEach(Window::close);
			}
		});
		
	}

}
