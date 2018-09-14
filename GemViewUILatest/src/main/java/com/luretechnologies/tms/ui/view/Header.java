package com.luretechnologies.tms.ui.view;

import com.luretechnologies.tms.backend.service.UserService;
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
	
	public Header(UserService userService,String caption,Component...components) {
//		this.setStyleName(ValoTheme.PANEL_WELL);
//		this.setCaption(caption);
		this.setWidth("100%");
		this.setHeight("20%");
		this.userService = userService;
		Label headerCaption = new Label(caption);
		headerCaption.setStyleName(ValoTheme.LABEL_LARGE);
		Label userName = new Label(userService.getLoggedInUserName());
		Label userRole = new Label(userService.getLoggedInUser().getRole());
		VerticalLayout userLayout = new VerticalLayout(userName,userRole);
		Button logOut = new Button(VaadinIcons.POWER_OFF,click -> {
				UI ui = getUI();
				ui.getSession().getSession().invalidate();
				ui.getPage().reload();
		});
		ContextMenuWindow userMenuWindow = new ContextMenuWindow();
		userMenuWindow.addMenuItems(logOut);
		Button userMenuButton = new Button(VaadinIcons.ELLIPSIS_V, click->{
			UI.getCurrent().getWindows().forEach(Window::close);
			userMenuWindow.setPosition(click.getClientX(), click.getClientY());
			UI.getCurrent().addWindow(userMenuWindow);
		});
		GridLayout rightSideLayout = new GridLayout(2, 1);
		rightSideLayout.addComponents(userLayout,userMenuButton);
		HorizontalLayout leftSideLayout = new HorizontalLayout(headerCaption);
		this.addComponent(leftSideLayout);
		this.addComponents(components);
		this.addComponent(rightSideLayout);
		this.setComponentAlignment(leftSideLayout, Alignment.BOTTOM_LEFT);
		this.setComponentAlignment(rightSideLayout, Alignment.BOTTOM_RIGHT);
		this.addLayoutClickListener(click ->{
			
			if(!click.getClickedComponent().equals(userMenuButton) || !click.getClickedComponent().equals(userMenuWindow)) {
				UI.getCurrent().getWindows().forEach(Window::close);
			}
		});
	}

}
