package com.discovery.view;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.discovery.ui.dialog.DialogManager;
import com.discovery.ui.navigation.NavigationManager;
import com.discovery.view.dialog.AssetControl;
import com.discovery.view.dialog.DialogWindow;
import com.discovery.view.dialog.User;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewLeaveAction;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
@SpringViewDisplay
@UIScope
public class MenuView extends VerticalLayout implements View {
private Button userView,assetControlView,logout;
private final Map<Window, Button> dialogButtons = new HashMap<>();
private GridLayout menuLayout;
private final NavigationManager navigationManager;
private final DialogManager dialogManager;
private final TabSheet tabs = new TabSheet();

@Autowired
public MenuView(NavigationManager navigationManager,DialogManager dialogManager) {
	this.navigationManager = navigationManager;
	this.dialogManager = dialogManager;
}

@PostConstruct
public void init() {
	userView = new Button("User");
	assetControlView = new Button("AssetControl");
	logout = new Button("Logout");
	menuLayout = new GridLayout(2,2,userView, assetControlView, logout);
	addComponents(tabs,menuLayout);
	
	attachDialogs(userView, new User());
	attachDialogs(assetControlView, new AssetControl());
	logout.addClickListener(e -> {
		try {
			logout();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	});
	tabs.addSelectedTabChangeListener(tab ->{
		//DO something with tab selection
	});
}

/**
 * Makes clicking the given button navigate to the given view if the user
 * has access to the view.
 * <p>
 * If the user does not have access to the view, hides the button.
 *
 * @param dialogButtons
 *            the button to use for navigation
 * @param targetView
 *            the view to navigate to when the user clicks the button
 */
private void attachDialogs(Button dialogButton, DialogWindow dialog) {
		dialogButtons.put(dialog, dialogButton);
		dialogButton.addClickListener(e -> {
			dialogManager.open(dialog);
			if(tabs.getTab(dialog.getContent()) == null) {
				tabs.addTab(dialog.getContent(),dialog.getCaption());
			}else {
				tabs.setSelectedTab(tabs.getTab(dialog.getContent()));
			}
		});
}
public void logout() {
	ViewLeaveAction doLogout = () -> {
		UI ui = getUI();
		ui.getSession().getSession().invalidate();
		ui.getPage().reload();
	};

	navigationManager.runAfterLeaveConfirmation(doLogout);
}

}
