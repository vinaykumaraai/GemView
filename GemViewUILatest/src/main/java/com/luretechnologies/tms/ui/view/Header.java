package com.luretechnologies.tms.ui.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.luretechnologies.tms.backend.service.UserService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.ViewLeaveAction;
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
	@Autowired
	UserService userService;
	
	public Header(String caption,Component...components) {
		this.setStyleName(ValoTheme.PANEL_WELL);
		this.setCaption(caption);
		Label userName = new Label(userService.getLoggedInUserName());
		Label userRole = new Label(userService.getLoggedInUser().getRole());
		VerticalLayout userLayout = new VerticalLayout(userName,userRole);
		Button logOut = new Button(VaadinIcons.POWER_OFF,click -> {
			ViewLeaveAction doLogout = () -> {
				UI ui = getUI();
				ui.getSession().getSession().invalidate();
				ui.getPage().reload();
			};
		});
		ContextMenuWindow userMenuWindow = new ContextMenuWindow();
		userMenuWindow.addMenuItems(logOut);
		Button userMenuButton = new Button(VaadinIcons.ELLIPSIS_V, click->{
			UI.getCurrent().getWindows().forEach(Window::close);
			UI.getCurrent().addWindow(userMenuWindow);
		});
		GridLayout rightSideLayout = new GridLayout(2, 1);
		rightSideLayout.addComponents(userLayout,userMenuButton);
		this.addComponents(components);
		this.addComponent(rightSideLayout);
		this.setComponentAlignment(rightSideLayout, Alignment.MIDDLE_RIGHT);
	}

}
