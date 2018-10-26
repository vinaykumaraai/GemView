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
package com.luretechnologies.tms.ui.components;

import org.springframework.beans.factory.annotation.Autowired;

import com.luretechnologies.tms.ui.MainView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class MainViewIconsLoad {
	
	public static void noIconsOnDesktopMode(MainView mainView) {
		mainView.getDashboard().setIcon(null);
		mainView.getBoarding().setIcon(null);
		mainView.getTransactions().setIcon(null);
		mainView.getApplicationStore().setIcon(null);
		mainView.getPersonlization().setIcon(null);
		mainView.getHeartbeat().setIcon(null);
		mainView.getAssetControl().setIcon(null);
		mainView.getDeviceOdometer().setIcon(null);
		mainView.getAudit().setIcon(null);
		mainView.getLogout().setIcon(null);
		mainView.getUsers().setIcon(null);
		mainView.getRoles().setIcon(null);
		mainView.getSystem().setIcon(null);
		mainView.getMenubar().setVisible(false);
		mainView.getLogout().setVisible(false);
		
	}
	
	public static  void iconsOnTabMode(MainView mainView) {
		mainView.getDashboard().setIcon(VaadinIcons.LINE_BAR_CHART);
		mainView.getBoarding().setIcon(VaadinIcons.FLIGHT_LANDING);
		mainView.getTransactions().setIcon(VaadinIcons.EXCHANGE);
		mainView.getApplicationStore().setIcon(VaadinIcons.GRID_SMALL_O);
		mainView.getPersonlization().setIcon(VaadinIcons.GROUP);
		mainView.getHeartbeat().setIcon(VaadinIcons.HEALTH_CARD);
		mainView.getAssetControl().setIcon(VaadinIcons.GRID_BIG_O);
		mainView.getDeviceOdometer().setIcon(VaadinIcons.DASHBOARD);
		mainView.getAudit().setIcon(VaadinIcons.DATABASE);
		mainView.getLogout().setIcon(VaadinIcons.SIGN_OUT);
		mainView.getUsers().setIcon(VaadinIcons.USERS);
		mainView.getRoles().setIcon(VaadinIcons.USER);
		mainView.getSystem().setIcon(VaadinIcons.DESKTOP);
		mainView.getMenubar().setVisible(false);
		mainView.getLogout().setVisible(false);
	}
	
	public static  void iconsOnPhoneMode(MainView mainView) {
		mainView.getDashboard().setIcon(null);
		mainView.getBoarding().setIcon(null);
		mainView.getTransactions().setIcon(null);
		mainView.getApplicationStore().setIcon(null);
		mainView.getPersonlization().setIcon(null);
		mainView.getHeartbeat().setIcon(null);
		mainView.getAssetControl().setIcon(null);
		mainView.getDeviceOdometer().setIcon(null);
		mainView.getAudit().setIcon(null);
		mainView.getLogout().setIcon(null);
		mainView.getUsers().setIcon(null);
		mainView.getRoles().setIcon(null);
		mainView.getSystem().setIcon(null);
		mainView.getMenubar().setVisible(true);
		mainView.getLogout().setVisible(true);
	}
}
