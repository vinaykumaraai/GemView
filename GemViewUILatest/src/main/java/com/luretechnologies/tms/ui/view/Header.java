package com.luretechnologies.tms.ui.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.luretechnologies.tms.backend.service.UserService;
import com.luretechnologies.tms.ui.MainView;
import com.luretechnologies.tms.ui.navigation.NavigationManager;
import com.luretechnologies.tms.ui.view.admin.roles.RolesView;
import com.luretechnologies.tms.ui.view.admin.user.UserAdminView;
import com.luretechnologies.tms.ui.view.system.SystemView;
import com.luretechnologies.tms.ui.view.user.UserView;
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
	NavigationManager navigationManager;
	
	public Header(UserService userService,NavigationManager navigationManager, String caption,Component...components) {
		this.setWidth("99%");
		this.setHeight("90px");
		this.userService = userService;
		this.navigationManager = navigationManager;
		Label headerCaption = new Label(caption);
		headerCaption.addStyleName("header-label");
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
			userMenuWindow.close();
			navigationManager.navigateTo(UserView.class);
		});
		Users.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		Users.setIcon(VaadinIcons.USERS);
		Button Roles = new Button("Roles",click -> {
			userMenuWindow.close();
			navigationManager.navigateTo(RolesView.class);
		});
		Roles.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		Roles.setIcon(VaadinIcons.USER);
		Button System = new Button("System Parameters",click -> {
			userMenuWindow.close();
			navigationManager.navigateTo(SystemView.class);
		});
		System.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		System.setIcon(VaadinIcons.DESKTOP);
		userMenuWindow.addStyleName("header-menuWindow");
		userMenuWindow.addMenuItems(Users);
		userMenuWindow.addMenuItems(Roles);
		userMenuWindow.addMenuItems(System);
		userMenuWindow.addMenuItems(logOut);
		userMenuWindow.setHeight("230px");
		userMenuWindow.setWidth("210px");
		Button userMenuButton = new Button(VaadinIcons.ELLIPSIS_DOTS_V, click->{
			UI.getCurrent().getWindows().forEach(Window::close);
			userMenuWindow.setPosition(click.getClientX()-10, click.getClientY());
			UI.getCurrent().addWindow(userMenuWindow);
		});
		userMenuButton.addStyleNames(ValoTheme.BUTTON_BORDERLESS, "header-menuButton");
		HorizontalLayout rightSideLayout = new HorizontalLayout();
		HorizontalLayout componentsLayout = new HorizontalLayout();
		GridLayout rightSideLayoutGridLayout = new GridLayout(2, 1);
		rightSideLayoutGridLayout.addComponents(userLayout,userMenuButton);
		rightSideLayoutGridLayout.addStyleName("header-GridLayout");
		HorizontalLayout leftSideLayout = new HorizontalLayout(headerCaption);
		//this.addComponent(leftSideLayout);
		componentsLayout.addComponents(components);
		//rightSideLayout.addComponents(components);
		rightSideLayout.addComponent(rightSideLayoutGridLayout);
		//this.addComponent(rightSideLayout);
		//this.setComponentAlignment(leftSideLayout, Alignment.BOTTOM_LEFT);
		//this.setComponentAlignment(rightSideLayout, Alignment.BOTTOM_RIGHT);
		for(Component comp: components) {
			comp.addStyleName("header-Components");
			comp.setWidth("100%");
			componentsLayout.setComponentAlignment(comp, Alignment.BOTTOM_RIGHT);
		}
		this.addComponent(leftSideLayout);
		this.addComponents(components);
		this.addComponent(rightSideLayoutGridLayout);
		this.setComponentAlignment(leftSideLayout, Alignment.BOTTOM_LEFT);
		this.setComponentAlignment(rightSideLayoutGridLayout, Alignment.BOTTOM_RIGHT);
		this.addLayoutClickListener(click ->{
			
			if(click.getClickedComponent() == null || !click.getClickedComponent().equals(userMenuButton)) {
				UI.getCurrent().getWindows().forEach(Window::close);
			}
		});
		
		UI.getCurrent().addClickListener(listener->{
			userMenuWindow.close();
		});
		
	}

}
