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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.UserSession;
import com.luretechnologies.common.enums.PermissionEnum;
import com.luretechnologies.tms.app.Application;
import com.luretechnologies.tms.app.security.RedirectAuthenticationSuccessHandler;
import com.luretechnologies.tms.backend.data.entity.Permission;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.luretechnologies.tms.backend.service.RolesService;
import com.luretechnologies.tms.backend.service.UserService;
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
import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.navigator.ViewLeaveAction;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.access.SecuredViewAccessControl;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
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
	Logger logger = LoggerFactory.getLogger(MainView.class);
	
	@Autowired
	private ServletContext servletContext;

	@Autowired
	public MainView(NavigationManager navigationManager, SecuredViewAccessControl viewAccessControl) {
		this.navigationManager = navigationManager;
		this.viewAccessControl = viewAccessControl;
	}
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	UserService userService;

	@PostConstruct
	public void init(){
		try {
		List<Permission> loggedInUserPermissionList = null;
		UserSession session = RestServiceUtil.getSESSION();
		if(session!=null) {
			if(session.isRequirePasswordUpdate()) {
				loggedInUserPermissionList = new ArrayList();
			}else {
				loggedInUserPermissionList = rolesService.getLoggedInUserRolePermissions();
			}
		}
		for(Permission permission: loggedInUserPermissionList) {
			switch(permission.getPageName()) {
			case "DASHBOARD":
				if(permission.getAccess()) {
					dashboard.setVisible(true);
					dashboard.setDisableOnClick(true);
					dashboard.addStyleName("menu-ButtonsLabelSize");
					attachNavigation(dashboard, DashboardView.class);
				}
				break;
			case "APPSTORE":
				if(permission.getAccess()) {
				applicationstore.setVisible(true);
				applicationstore.setDisableOnClick(true);
				applicationstore.addStyleName("menu-ButtonsLabelSize");
				attachNavigation(applicationstore, ApplicationStoreView.class);
				}
				break;
			case "PERSONALIZATION":
				if(permission.getAccess()) {
				personalization.setVisible(true);
				personalization.setDisableOnClick(true);
				personalization.addStyleName("menu-ButtonsLabelSize");
				attachNavigation(personalization, PersonalizationView.class);
				}
				break;
			case "HEARTBEAT":
				if(permission.getAccess()) {
				heartbeat.setVisible(true);
				heartbeat.setDisableOnClick(true);
				heartbeat.addStyleName("menu-ButtonsLabelSize");
				attachNavigation(heartbeat, HeartbeatView.class);
				}
				break;
			case "ASSET":
				if(permission.getAccess()) {
				assetcontrol.setVisible(true);
				assetcontrol.setDisableOnClick(true);
				assetcontrol.addStyleName("menu-ButtonsLabelSize");
				attachNavigation(assetcontrol, AssetcontrolView.class);
				}
				break;
			case "ODOMETER":
				if(permission.getAccess()) {
				deviceodometer.setVisible(true);
				deviceodometer.setDisableOnClick(true);
				deviceodometer.addStyleName("menu-ButtonsLabelSize");
				attachNavigation(deviceodometer, DeviceodometerView.class);
				}
				break;
			case "AUDIT":
				if(permission.getAccess()) {
				audit.setVisible(true);
				audit.setDisableOnClick(true);
				audit.addStyleName("menu-ButtonsLabelSize");
				attachNavigation(audit, AuditView.class);
				}
				break;
			case "USER":
				if(permission.getAccess()) {
				users.setVisible(true);
				users.setDisableOnClick(true);
				//administrationButton.setVisible(true);
				users.addStyleName("menu-ButtonsLabelSize");
				attachNavigation(users, UserAdminView.class);
				}
				break;
			case "ROLE":
				if(permission.getAccess()) {
				roles.setVisible(true);
				roles.setDisableOnClick(true);
				//administrationButton.setVisible(true);
				roles.addStyleName("menu-ButtonsLabelSize");
				attachNavigation(roles, RolesView.class);
				}
				break;
			case "SYSTEM":
				if(permission.getAccess()) {
				system.setVisible(true);
				system.setDisableOnClick(true);
				system.addStyleName("menu-ButtonsLabelSize");
				//administrationButton.setVisible(true);
				attachNavigation(system, SystemView.class);
				}
				break;
			default:
				break;
		}
	}
		
		/*menubar.setVisible(true);
		administrationButton.addStyleName("submenuIconUp");
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
		});*/
		
		gemViewTitle.setCaptionAsHtml(true);
		gemViewTitle.addStyleNames("v-caption-logo");
		gemViewTitle.setValue("gemView");
		
		boarding.addStyleName("menu-ButtonsLabelSize");
		transactions.addStyleName("menu-ButtonsLabelSize");
		logout.addStyleName("menu-ButtonsLabelSize");
		
		logout.addClickListener(e -> {
			try {
				logout();
				String URL = Page.getCurrent().getLocation().toString();
				if(URL.contains("home")) {
					int index = URL.indexOf("home");
					String url = URL.substring(0, index);
					Page.getCurrent().setLocation(url+Application.LOGIN_URL);
				}
			} catch (ApiException e1) {
				e1.printStackTrace();
			}
		});
		}catch(Exception ex) {
			logger.info(ex.getMessage());
		}
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
	public void attachNavigation(Button navigationButton, Class<? extends View> targetView) {
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
	
	public Label getTitle(){
		return gemViewTitle;
	}
	
	public Button getDashboard() {
		return dashboard;
	}
	
	
	public Button getApplicationStore() {
		return applicationstore;
	}
	
	public Button getPersonlization() {
		return personalization;
	}
	
	public Button getHeartbeat() {
		return heartbeat;
	}
	
	public Button getAssetControl() {
		return assetcontrol;
	}
	
	public Button getDeviceOdometer() {
		return deviceodometer;
	}
	
	public Button getAudit() {
		return audit;
	}
	
	public Button getUsers() {
		return users;
	}
	
	public Button getRoles() {
		return roles;
	}
	
	public Button getSystem() {
		return system;
	}
	
	public Button getBoarding() {
		return boarding;
	}
	
	public Button getTransactions() {
		return transactions;
	}
	
	public Button getLogout() {
		return logout;
	}
	
	private String getAbsoluteUrl(String url) {
		final String relativeUrl;
		if (url.startsWith("/")) {
			relativeUrl = url.substring(1);
		} else {
			relativeUrl = url;
		}
		return servletContext.getContextPath() + "/" + relativeUrl;
	}


}