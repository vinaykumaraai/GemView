package com.luretechnologies.tms.ui.view;

import java.util.List;

import com.luretechnologies.tms.backend.data.entity.Permission;
import com.luretechnologies.tms.backend.service.RolesService;
import com.luretechnologies.tms.backend.service.UserService;
import com.luretechnologies.tms.ui.navigation.NavigationManager;
import com.luretechnologies.tms.ui.view.admin.roles.RolesView;
import com.luretechnologies.tms.ui.view.system.SystemView;
import com.luretechnologies.tms.ui.view.user.UserView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
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

/**
 * 
 * @author Vinay
 *
 */

public class Header extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	UserService userService;
	NavigationManager navigationManager;
	private Button Users, Roles, System;
	
	public Header(UserService userService, RolesService roleService, NavigationManager navigationManager, String caption,Component...components) {
		
		this.setWidth("99%");
		this.setHeight("90px");
		this.userService = userService;
		this.navigationManager = navigationManager;
		Label headerCaption = new Label(caption);
		headerCaption.addStyleName("header-label");
		Label userName = new Label(userService.getLoggedInUserName());
		userName.addStyleName("header-UserName");
		Label userRole = new Label(userService.getRoleName());
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
		
		List<Permission> loggedInUserPermissionList = roleService.getLoggedInUserRolePermissions();
	
		for(Permission permission: loggedInUserPermissionList) {
			switch(permission.getPageName()) {
			
			case "USER":
				if(permission.getAccess()) {
					Users = new Button("Users",click -> {
						userMenuWindow.close();
						navigationManager.navigateTo(UserView.class);
					});
					Users.addStyleName(ValoTheme.BUTTON_BORDERLESS);
				}
				break;
			case "ROLE":
				if(permission.getAccess()) {
					Roles = new Button("Roles",click -> {
						userMenuWindow.close();
						navigationManager.navigateTo(RolesView.class);
					});
					Roles.addStyleName(ValoTheme.BUTTON_BORDERLESS);
				}
				break;
			case "SYSTEM":
				if(permission.getAccess()) {
					System = new Button("System Parameters",click -> {
						userMenuWindow.close();
						navigationManager.navigateTo(SystemView.class);
					});
					System.addStyleName(ValoTheme.BUTTON_BORDERLESS);
				}
				break;
			default:
				break;
		}
	}
		
		userMenuWindow.addStyleName("header-menuWindow");
		if(Users!=null) {
			userMenuWindow.addMenuItems(Users);
		}
		if(Roles!=null) {
			userMenuWindow.addMenuItems(Roles);
		}
		if(System!=null) {
			userMenuWindow.addMenuItems(System);
		}
		userMenuWindow.addMenuItems(logOut);
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
		componentsLayout.addComponents(components);
		rightSideLayout.addComponent(rightSideLayoutGridLayout);
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
		
		Page.getCurrent().addBrowserWindowResizeListener(r -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			if (r.getWidth() <= 600) {
				if(Users!=null) {
					Users.setIcon(null);
				}
				if(Roles!=null) {
					Roles.setIcon(null);
				}
				if(System!=null) {
					System.setIcon(null);
				}
				
				logOut.setIcon(null);
			} else if (r.getWidth() > 600 && r.getWidth() <= 1000) {
				if(Users!=null) {
					Users.setIcon(VaadinIcons.USERS);
				}
				if(Roles!=null) {
					Roles.setIcon(VaadinIcons.USER);
				}
				if(System!=null) {
					System.setIcon(VaadinIcons.DESKTOP);
				}
				logOut.setIcon(VaadinIcons.SIGN_OUT);
			} else {
				if(Users!=null) {
					Users.setIcon(null);
				}
				if(Roles!=null) {
					Roles.setIcon(null);
				}
				if(System!=null) {
					System.setIcon(null);
				}
				logOut.setIcon(null);
				}
		});
		
		int width = Page.getCurrent().getBrowserWindowWidth();
		
		if (width <= 600) {
			if(Users!=null) {
				Users.setIcon(null);
			}
			if(Roles!=null) {
				Roles.setIcon(null);
			}
			if(System!=null) {
				System.setIcon(null);
			}
			
			logOut.setIcon(null);
		} else if (width > 600 && width <= 1000) {
			if(Users!=null) {
				Users.setIcon(VaadinIcons.USERS);
			}
			if(Roles!=null) {
				Roles.setIcon(VaadinIcons.USER);
			}
			if(System!=null) {
				System.setIcon(VaadinIcons.DESKTOP);
			}
			logOut.setIcon(VaadinIcons.SIGN_OUT);
		} else {
			if(Users!=null) {
				Users.setIcon(null);
			}
			if(Roles!=null) {
				Roles.setIcon(null);
			}
			if(System!=null) {
				System.setIcon(null);
			}
			logOut.setIcon(null);
			}	
	}

}
