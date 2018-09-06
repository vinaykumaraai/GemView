package com.discovery.view;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.discovery.ui.navigation.NavigationManager;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewLeaveAction;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringViewDisplay
@UIScope
public class MenuView extends VerticalLayout implements View {
private Button userView,assetControlView,logout;
private final Map<Class<? extends View>, Button> navigationButtons = new HashMap<>();
private GridLayout menuLayout;
private final NavigationManager navigationManager;
@Autowired
public MenuView(NavigationManager navigationManager) {
	this.navigationManager = navigationManager;
}

@PostConstruct
public void init() {
	attachNavigation(userView, UserView.class);
	attachNavigation(assetControlView, AssetControlView.class);
	logout.addClickListener(e -> {
		try {
			logout();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	});
}

/**
 * Makes clicking the given button navigate to the given view if the user
 * has access to the view.
 * <p>
 * If the user does not have access to the view, hides the button.
 *
 * @param navigationButton
 *            the button to use for navigatio
 * @param targetView
 *            the view to navigate to when the user clicks the button
 */
private void attachNavigation(Button navigationButton, Class<? extends View> targetView) {
		navigationButtons.put(targetView, navigationButton);
		navigationButton.addClickListener(e -> navigationManager.navigateTo(targetView));
}
public void logout() {
	ViewLeaveAction doLogout = () -> {
		UI ui = getUI();
		ui.getSession().getSession().invalidate();
		ui.getPage().reload();
	};

	navigationManager.runAfterLeaveConfirmation(doLogout);
	
	//RestServiceUtil.getInstance().getClient().getAuthApi().logout();
}

}
