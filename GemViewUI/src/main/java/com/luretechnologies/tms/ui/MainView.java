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

package com.luretechnologies.tms.ui;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.tms.app.Application;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.luretechnologies.tms.ui.navigation.NavigationManager;
import com.luretechnologies.tms.ui.view.admin.roles.RolesView;
import com.luretechnologies.tms.ui.view.admin.user.UserAdminView;
import com.luretechnologies.tms.ui.view.applicationstore.ApplicationStoreView;
import com.luretechnologies.tms.ui.view.assetcontrol.AssetcontrolView;
import com.luretechnologies.tms.ui.view.audit.AuditView;
import com.luretechnologies.tms.ui.view.dashboard.DashboardView;
import com.luretechnologies.tms.ui.view.deviceodometer.DeviceodometerView;
import com.luretechnologies.tms.ui.view.devices.DevicesView;
import com.luretechnologies.tms.ui.view.heartbeat.HeartbeatView;
import com.luretechnologies.tms.ui.view.personalization.PersonalizationView;
import com.luretechnologies.tms.ui.view.security.SecurityView;
import com.luretechnologies.tms.ui.view.system.SystemView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.navigator.ViewLeaveAction;
import com.vaadin.server.Page;
import com.vaadin.spring.access.SecuredViewAccessControl;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

/**
 * The main view containing the menu and the content area where actual views are
 * shown.
 * <p>
 * Created as a single View class because the logic is so simple that using a
 * pattern like MVP would add much overhead for little gain. If more complexity
 * is added to the class, you should consider splitting out a presenter.
 */
@SpringViewDisplay
@UIScope
public class MainView extends MainViewDesign implements ViewDisplay {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Map<Class<? extends View>, Button> navigationButtons = new HashMap<>();
	private final NavigationManager navigationManager;
	private final SecuredViewAccessControl viewAccessControl;

	@Autowired
	public MainView(NavigationManager navigationManager, SecuredViewAccessControl viewAccessControl) {
		this.navigationManager = navigationManager;
		this.viewAccessControl = viewAccessControl;
	}

	@PostConstruct
	public void init() {
		attachNavigation(dashboard, DashboardView.class);
		attachNavigation(users, UserAdminView.class);
		attachNavigation(roles, RolesView.class);
		attachNavigation(applicationstore, ApplicationStoreView.class);
		attachNavigation(personalization, PersonalizationView.class);
		attachNavigation(heartbeat, HeartbeatView.class);
		attachNavigation(assetcontrol, AssetcontrolView.class);
		attachNavigation(deviceodometer, DeviceodometerView.class);
		attachNavigation(security, SecurityView.class);
		attachNavigation(audit, AuditView.class);
		attachNavigation(devices, DevicesView.class);
		attachNavigation(system, SystemView.class);
		
		menubar.setVisible(true);
		administrationButton.addStyleName("submenuIconUp");
		//administrationButton.removeStyleName("hover");
		administrationButton.addClickListener(e->{
			if(menubar.isVisible() && Page.getCurrent().getBrowserWindowWidth() > 1000) {
				menubar.setVisible(false);
			administrationButton.removeStyleName("submenuIconUp");
			administrationButton.addStyleName("submenuIconDown");
			}
			else {
				menubar.setVisible(true);
			administrationButton.removeStyleName("submenuIconDown");
			administrationButton.addStyleName("submenuIconUp");
			}
		});
		
		logout.addClickListener(e -> {
			try {
				logout();
				//Page.getCurrent().setLocation(Application.LOGOUT_API_URL);
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
		boolean hasAccessToView = viewAccessControl.isAccessGranted(targetView);
		navigationButton.setVisible(hasAccessToView);

		if (hasAccessToView) {
			navigationButtons.put(targetView, navigationButton);
			navigationButton.addClickListener(e -> navigationManager.navigateTo(targetView));
		}
	}

	@Override
	public void showView(View view) {
		content.removeAllComponents();
		content.addComponent(view.getViewComponent());

		navigationButtons.forEach((viewClass, button) -> button.setStyleName("selected", viewClass == view.getClass()));

		Button menuItem = navigationButtons.get(view.getClass());
		String viewName = "";
		if (menuItem != null) {
			viewName = menuItem.getCaption();
		}
		activeViewName.setValue(viewName);
	}

	/**
	 * Logs the user out after ensuring the currently open view has no unsaved
	 * changes.
	 * @throws ApiException 
	 */
	public void logout() throws ApiException {
		ViewLeaveAction doLogout = () -> {
			UI ui = getUI();
			ui.getSession().getSession().invalidate();
			ui.getPage().reload();
		};

		navigationManager.runAfterLeaveConfirmation(doLogout);
		
		//RestServiceUtil.getInstance().getClient().getAuthApi().logout();
	}

}