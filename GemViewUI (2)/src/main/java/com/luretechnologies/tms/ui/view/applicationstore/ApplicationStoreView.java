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
package com.luretechnologies.tms.ui.view.applicationstore;


import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.tms.backend.data.entity.App;
import com.luretechnologies.tms.backend.service.AppService;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = ApplicationStoreView.VIEW_NAME)
public class ApplicationStoreView extends VerticalLayout implements Serializable, View {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1714335680383962006L;
	public static final String VIEW_NAME = "applicationstore";

	@Autowired
	public ApplicationStoreView() {
			
	}
	@Autowired
	private AppService appService;
	
	@PostConstruct
	private void init() {
		
		Page.getCurrent().addBrowserWindowResizeListener(r->{
			System.out.println("Height "+ r.getHeight() + "Width:  " + r.getWidth()+ " in pixel");
			if(r.getWidth()<=1450 && r.getWidth()>=700) {
				//tabMode();
			}else if(r.getWidth()<=699 && r.getWidth()> 0){
				//phoneMode();
				
			} else {
				//desktopMode();
			}
		});
		
		setSpacing(false);
		setMargin(false);
		setResponsive(true);
		Panel panel = getAndLoadApplicationStorePanel();
		GridLayout appStoreGridLayout = new GridLayout(2,2,getAppStoreComponents());
		panel.setContent(appStoreGridLayout);
	}
	private Panel getAndLoadApplicationStorePanel() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setCaption("Application Store");
		panel.setResponsive(true);
		panel.setSizeFull();
		addComponent(panel);
		return panel;
	}
	
	private Component[] getAppStoreComponents() {
		Component[] components = {getApplicationListLayout(),getAppicationDetailsLayout(),getApplicationDefaulParametersLayout()};
		return components;
	}
	private VerticalLayout getApplicationListLayout() {
		Grid<App> appGrid = new Grid<>(App.class);
		appGrid.setDataProvider(appService.getListDataProvider());
		appGrid.setColumns("packageName","file","packageVersion");
		appGrid.getColumn("packageName").setCaption("Name");
		appGrid.getColumn("file").setCaption("File");
		appGrid.getColumn("packageVersion").setCaption("Version");
		appGrid.addColumn(c-> c.isActive()?VaadinIcons.CHECK.getHtml():VaadinIcons.CLOSE_CIRCLE_O.getHtml(), new HtmlRenderer()).setCaption("Active");
		TextField applicationSearch = new TextField();
		applicationSearch.setWidth("100%");
		applicationSearch.setIcon(VaadinIcons.SEARCH);
		applicationSearch.setStyleName("small inline-icon search");
		applicationSearch.setPlaceholder("Search");
		applicationSearch.setResponsive(true);
		applicationSearch.addShortcutListener(new ShortcutListener("Clear", KeyCode.ESCAPE, null) {

			@Override
			public void handleAction(Object sender, Object target) {
				if (target == applicationSearch) {
					applicationSearch.clear();
				}

			}
		});
		applicationSearch.addValueChangeListener(valueChange -> {
			String valueInLower = valueChange.getValue().toLowerCase();
			ListDataProvider<App> appDataProvider = (ListDataProvider<App>) appGrid.getDataProvider();
			appDataProvider.setFilter(filter -> {
				String packageNameInLower = filter.getPackageName().toLowerCase();
				String packageVersionInLower = filter.getPackageVersion().toLowerCase();
				String fileInLower = filter.getFile().toLowerCase();
				return packageVersionInLower.equals(valueInLower) || packageNameInLower.contains(valueInLower) || fileInLower.contains(valueInLower);
			});
		});
		Button createAppGridRow = new Button(VaadinIcons.FOLDER_ADD, click -> {
		});
		createAppGridRow.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		createAppGridRow.setResponsive(true);
		Button editAppGridRow = new Button(VaadinIcons.PENCIL, click -> {
		});
		editAppGridRow.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		editAppGridRow.setResponsive(true);
		
		Button deleteAppGridRow = new Button(VaadinIcons.TRASH,clicked -> {
			if(appGrid.getSelectedItems().isEmpty()) {
				Notification.show("Select any App type to delete", Notification.Type.WARNING_MESSAGE).setDelayMsec(3000);;
			}else {
				confirmDeleteApp(appGrid,applicationSearch);
			}

		});
		deleteAppGridRow.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		deleteAppGridRow.setResponsive(true);
		HorizontalLayout appGridMenuLayout = new HorizontalLayout(applicationSearch,createAppGridRow,editAppGridRow,deleteAppGridRow);
		VerticalLayout applicationListLayout = new VerticalLayout(appGridMenuLayout,appGrid);
		return applicationListLayout;
	}

	private Component getAppicationDetailsLayout() {
		VerticalLayout applicationDetailsLayout = new VerticalLayout();
		return applicationDetailsLayout;
	}


	private Component getApplicationDefaulParametersLayout() {
		VerticalLayout applicationDefaultParametersLayout = new VerticalLayout();
		return applicationDefaultParametersLayout;
	}
	
	private void confirmDeleteApp(Grid appGrid,TextField appSearch) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?",
		        "Ok", "Cancel", new ConfirmDialog.Listener() {

		            public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		                    // Confirmed to continue
		                	//appService.removeApp(appGrid.getSelectedItems().iterator().next());
		                	appSearch.clear();
		                	//TODO add Grid reload
		                } else {
		                    // User did not confirm
		                    
		                }
		            }
		        });
	}
}

