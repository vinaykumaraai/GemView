package com.discovery.view;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.tms.ui.navigation.NavigationManager;
import com.luretechnologies.tms.ui.view.admin.roles.RolesView;
import com.luretechnologies.tms.ui.view.admin.user.UserAdminView;
import com.luretechnologies.tms.ui.view.applicationstore.ApplicationStoreView;
import com.luretechnologies.tms.ui.view.assetcontrol.AssetcontrolView;
import com.luretechnologies.tms.ui.view.audit.AuditView;
import com.luretechnologies.tms.ui.view.dashboard.DashboardView;
import com.luretechnologies.tms.ui.view.deviceodometer.DeviceodometerView;
import com.luretechnologies.tms.ui.view.heartbeat.HeartbeatView;
import com.luretechnologies.tms.ui.view.personalization.PersonalizationView;
import com.luretechnologies.tms.ui.view.system.SystemView;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;

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
	attachNavigation(assetControlView, AssertControlView.class);
	logout.addClickListener(e -> {
		try {
			logout();
		} catch (ApiException e1) {
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

}
