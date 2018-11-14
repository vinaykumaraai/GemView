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

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.AppParamFormat;
import com.luretechnologies.tms.backend.data.entity.AppClient;
import com.luretechnologies.tms.backend.data.entity.AppDefaultParam;
import com.luretechnologies.tms.backend.data.entity.ApplicationFile;
import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.Permission;
import com.luretechnologies.tms.backend.data.entity.Profile;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.service.ApplicationStoreService;
import com.luretechnologies.tms.backend.service.RolesService;
import com.luretechnologies.tms.backend.service.UserService;
import com.luretechnologies.tms.ui.MainView;
import com.luretechnologies.tms.ui.components.ComponentUtil;
import com.luretechnologies.tms.ui.components.MainViewIconsLoad;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.luretechnologies.tms.ui.navigation.NavigationManager;
import com.luretechnologies.tms.ui.view.ContextMenuWindow;
import com.luretechnologies.tms.ui.view.Header;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 * 
 * @author Vinay
 *
 */

@SpringView(name = ApplicationStoreView.VIEW_NAME)
public class ApplicationStoreView extends VerticalLayout implements Serializable, View {
	private static final String DESCRIPTION = "description";
	private static final String ACTIVE_LAYOUT = "ActiveLayout";
	private static final String FILE_CHOOSE_LIST = "fileChooseList";
	public static String applicationFilePath = "";
	private static final long serialVersionUID = -1714335680383962006L;
	public static final String VIEW_NAME = "applicationstore";
	private VerticalLayout applicationDetailsForm;
	private HorizontalLayout buttonLayout,  appButtonsLayout;
	private HorizontalLayout applicationDetailsLabel;
	private HorizontalLayout applicationParamLabelLayout;
	private Grid<AppDefaultParam> appDefaultParamGrid;
	private Grid<Profile> appProfileGrid;
	private GridLayout appStoreGridLayout;
	private static List<ApplicationFile> uploadedFileList = new ArrayList<>();
	private Grid<AppClient> appGrid;
	public static AppClient selectedApp;
	private Profile selectedProfile;
	private ComboBox<TreeNode> applicationOwner;
	private CheckBox activeApplication;
	private TextField packageName;
	private Button  saveForm;
	private Button cancelForm;
	private Button fileButton, clearAppSearch, clearParamSearch, saveProfile, cancelProfile, saveParameter, cancelParameter;
	private TextField applicationSearch, appDefaultParamSearch;
	private HorizontalLayout appGridMenuHorizontalLayout;
	private VerticalLayout appGridMenuVerticalLayout;
	private VerticalLayout applicationListLayout;
	private HorizontalLayout appSearchLayout ;
	private ListSelect optionList;
	private TextField profileField = new TextField();
	private TextField profileName = new TextField("Name");
	private ComboBox<String> parameterType =new ComboBox<String>("Type");;
	private ComboBox<Profile> profileSelect;
	private ComboBox<Profile> profileSelectFiles;
	private TextField parameterName;
	private TextField parameterDescription;
	private boolean access=false, add=false, edit=false, delete=false;
	private TextField description, packageVersion;
	private HorizontalLayout activeBoxLayout;
	private HorizontalLayout fileButtonLayout;
	private HorizontalLayout appParamSearchLayout;
	private TabSheet applicationStoreTabSheet;
	private final NavigationManager navigationManager;
	private Grid<AppDefaultParam> appFileGrid;
	private Header header;
	private TextField parameterActive;
	private ComboBox<String> parameterValue;
	private ContextMenuWindow openProfileWindow, createParamGridWindow;
	private static List<AppDefaultParam> appParamWithEntity = new ArrayList<>();
	
	@Autowired
	public ApplicationStoreView(NavigationManager navigationManager) {
		this.navigationManager = navigationManager;
	}
	
	@Autowired
	private ApplicationStoreService appStoreService;
	
	@Autowired
	private RolesService roleService;
	
	@Autowired
	MainView mainView;
	
	@Autowired
	UserService userService;

	@PostConstruct
	private void init() {
		try {
		setSpacing(false);
		setMargin(false);
		setResponsive(true);
		header = new Header(userService, roleService, navigationManager, "Application Store", new Label());
		
		Permission appStorePermission = roleService.getLoggedInUserRolePermissions().stream().filter(per -> per.getPageName().equals("APPSTORE")).findFirst().get();
		access = appStorePermission.getAccess();
		add = appStorePermission.getAdd();
		edit = appStorePermission.getEdit();
		delete = appStorePermission.getDelete();
		
		Panel panel = getAndLoadApplicationStorePanel();
		appStoreGridLayout = new GridLayout();
		appStoreGridLayout.setWidth("100%");
		appStoreGridLayout.setHeight("100%");
		appStoreGridLayout.setMargin(true);
		appStoreGridLayout.addStyleNames("applicationStore-GridLayout", "applicationStore-GridLayoutOverflow");
		panel.setContent(appStoreGridLayout);
		
		Page.getCurrent().addBrowserWindowResizeListener(r -> {
			System.out.println("Height " + r.getHeight() + "Width:  " + r.getWidth() + " in pixel");
			if (r.getWidth() <= 1300 && r.getWidth() >= 700) {
				try {
					phoneAndTabMode();
					appGridMenuVerticalLayout = new VerticalLayout();
					appGridMenuVerticalLayout.addStyleName("heartbeat-verticalLayout");
					appGridMenuVerticalLayout.addComponents(appSearchLayout, appButtonsLayout);
					applicationListLayout.removeComponent(appGridMenuHorizontalLayout);
					applicationListLayout.removeComponent(appGrid);
					applicationListLayout.addComponents(appGridMenuVerticalLayout, appGrid);
				} catch (ApiException e) {
					appStoreService.logApplicationStoreScreenErrors(e);
				}
				
			} else if (r.getWidth() <= 699 && r.getWidth() > 0) {
				try {
					phoneAndTabMode();
				} catch (ApiException e) {
					appStoreService.logApplicationStoreScreenErrors(e);
				}
			} else {
				try {
					desktopMode();
				} catch (ApiException e) {
					appStoreService.logApplicationStoreScreenErrors(e);
				}

			}
			
			if(r.getWidth()<=600) {
				applicationSearch.setHeight("28px");
				appDefaultParamSearch.setHeight("28px");
				appParamSearchLayout.setWidth("100%");
				clearAppSearch.removeStyleNames("v-button-customstyle", "audit-AuditSearchClearDesktop");
				clearAppSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"odometer-OdometerSearchClearPhone");
				clearParamSearch.removeStyleNames("v-button-customstyle", "audit-AuditSearchClearDesktop");
				clearParamSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"odometer-OdometerSearchClearPhone");
				mainView.getTitle().setValue(userService.getLoggedInUserName());
				applicationStoreTabSheet.getTab(0).setCaption("<h3 style=font-weight:400;width:90px;height:15px;>Details</h3>");
				applicationStoreTabSheet.getTab(1).setCaption("<h3 style=font-weight:400;width:90px;height:15px;>Profiles</h3>");
				applicationStoreTabSheet.getTab(2).setCaption("<h3 style=font-weight:400;width:90px;height:15px;>Parameters</h3>");
				applicationStoreTabSheet.getTab(3).setCaption("<h3 style=font-weight:400;width:90px;height:15px;>Files</h3>");
				removeComponent(header);
				MainViewIconsLoad.iconsOnPhoneMode(mainView);
				
				if(openProfileWindow!=null) {
					openProfileWindow.setPosition(180, 200);
					openProfileWindow.setWidth("180px");
					profileName.setHeight("28px");
					saveProfile.setHeight("28px");
					cancelProfile.setHeight("28px");
				}
				
			} else if(r.getWidth()>600 && r.getWidth()<=1000){
				applicationSearch.setHeight("32px");
				appDefaultParamSearch.setHeight("32px");
				appParamSearchLayout.setWidth("100%");
				clearAppSearch.removeStyleNames("audit-AuditSearchClearDesktop", "odometer-OdometerSearchClearPhone");
				clearAppSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
				clearParamSearch.removeStyleNames("audit-AuditSearchClearDesktop", "odometer-OdometerSearchClearPhone");
				clearParamSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
				mainView.getTitle().setValue("gemView");
				addComponentAsFirst(header);
				applicationStoreTabSheet.getTab(0).setCaption("<h3 style=font-weight:400;width:120px;height:20px;>Details</h3>");
				applicationStoreTabSheet.getTab(1).setCaption("<h3 style=font-weight:400;width:120px;height:20px;>Profiles</h3>");
				applicationStoreTabSheet.getTab(2).setCaption("<h3 style=font-weight:400;width:120px;height:20px;>Parameters</h3>");
				applicationStoreTabSheet.getTab(3).setCaption("<h3 style=font-weight:400;width:120px;height:20px;>Files</h3>");
				MainViewIconsLoad.iconsOnTabMode(mainView);
				
				if(openProfileWindow!=null) {
					openProfileWindow.center();
					openProfileWindow.setWidth("25%");
					profileName.setHeight("32px");
					saveProfile.setHeight("32px");
					cancelProfile.setHeight("32px");
				}
				
			}else {
				applicationSearch.setHeight(37, Unit.PIXELS);
				appDefaultParamSearch.setHeight("37px");
				appParamSearchLayout.setWidth("98%");
				clearAppSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
				clearAppSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearDesktop");
				clearParamSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
				clearParamSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearDesktop");
				mainView.getTitle().setValue("gemView");
				addComponentAsFirst(header);
				applicationStoreTabSheet.getTab(0).setCaption("<h3 style=font-weight:400;width:150px;height:25px;>Details</h3>");
				applicationStoreTabSheet.getTab(1).setCaption("<h3 style=font-weight:400;width:150px;height:25px;>Profiles</h3>");
				applicationStoreTabSheet.getTab(2).setCaption("<h3 style=font-weight:400;width:150px;height:25px;>Parameters</h3>");
				applicationStoreTabSheet.getTab(3).setCaption("<h3 style=font-weight:400;width:150px;height:25px;>Files</h3>");
				MainViewIconsLoad.noIconsOnDesktopMode(mainView);
				if(openProfileWindow!=null) {
					openProfileWindow.center();
					openProfileWindow.setWidth("30%");
					profileName.setHeight("37px");
					saveProfile.setHeight("37px");
					cancelProfile.setHeight("37px");
				}
			}
			
			if(r.getWidth()<=730) {
				appGridMenuVerticalLayout = new VerticalLayout();
				appGridMenuVerticalLayout.addStyleName("heartbeat-verticalLayout");
				appGridMenuVerticalLayout.addComponents(appSearchLayout, appButtonsLayout);
				applicationListLayout.removeComponent(appGridMenuHorizontalLayout);
				applicationListLayout.removeComponent(appGrid);
				applicationListLayout.addComponents(appGridMenuVerticalLayout, appGrid);
			}
		});
		differentModesLoad();
		disableAllComponents();
		allowAccessBasedOnPermission(access, add, edit, delete);
		
		UI.getCurrent().addShortcutListener(new ShortcutListener("", KeyCode.ENTER, null) {
			
			@Override
			public void handleAction(Object sender, Object target) {
				applicationSearch.focus();
				
			}
		});
		
		}catch(Exception ex) {
			appStoreService.logApplicationStoreScreenErrors(ex);
		}
			mainView.getApplicationStore().setEnabled(true);
	}

	private void allowAccessBasedOnPermission(Boolean access, Boolean add, Boolean edit, Boolean delete) {
		saveForm.setEnabled(edit || add);
		cancelForm.setEnabled(edit || add);
	}

	private void differentModesLoad() throws ApiException {
		int width = Page.getCurrent().getBrowserWindowWidth();
		if (width <= 1300 && width >= 700) {
			phoneAndTabMode();
			appGridMenuVerticalLayout = new VerticalLayout();
			appGridMenuVerticalLayout.addStyleName("heartbeat-verticalLayout");
			appGridMenuVerticalLayout.addComponents(appSearchLayout, appButtonsLayout);
			applicationListLayout.removeComponent(appGridMenuHorizontalLayout);
			applicationListLayout.removeComponent(appGrid);
			applicationListLayout.addComponents(appGridMenuVerticalLayout, appGrid);

		} else if (width <= 699 && width > 0) {
			phoneAndTabMode();

		} else {
			desktopMode();
		}
		
		if(width<=600) {
			applicationSearch.setHeight("28px");
			appDefaultParamSearch.setHeight("28px");
			appParamSearchLayout.setWidth("100%");
			clearAppSearch.removeStyleNames("v-button-customstyle", "audit-AuditSearchClearDesktop");
			clearAppSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"odometer-OdometerSearchClearPhone");
			clearParamSearch.removeStyleNames("v-button-customstyle", "audit-AuditSearchClearDesktop");
			clearParamSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"odometer-OdometerSearchClearPhone");
			removeComponent(header);
			mainView.getTitle().setValue(userService.getLoggedInUserName());
			applicationStoreTabSheet.getTab(0).setCaption("<h3 style=font-weight:400;width:90px;height:15px;>Details</h3>");
			applicationStoreTabSheet.getTab(1).setCaption("<h3 style=font-weight:400;width:90px;height:15px;>Profiles</h3>");
			applicationStoreTabSheet.getTab(2).setCaption("<h3 style=font-weight:400;width:90px;height:15px;>Parameters</h3>");
			applicationStoreTabSheet.getTab(3).setCaption("<h3 style=font-weight:400;width:90px;height:15px;>Files</h3>");
			MainViewIconsLoad.iconsOnPhoneMode(mainView);
			if(openProfileWindow!=null) {
				openProfileWindow.setPosition(180, 200);
				openProfileWindow.setWidth("180px");
				profileName.setHeight("28px");
				saveProfile.setHeight("28px");
				cancelProfile.setHeight("28px");
			}
			
		} else if(width>600 && width<=1000){
			applicationSearch.setHeight("32px");
			appDefaultParamSearch.setHeight("32px");
			appParamSearchLayout.setWidth("100%");
			clearAppSearch.removeStyleNames("audit-AuditSearchClearDesktop", "odometer-OdometerSearchClearPhone");
			clearAppSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
			clearParamSearch.removeStyleNames("audit-AuditSearchClearDesktop", "odometer-OdometerSearchClearPhone");
			clearParamSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
			mainView.getTitle().setValue("gemView");
			addComponentAsFirst(header);
			applicationStoreTabSheet.getTab(0).setCaption("<h3 style=font-weight:400;width:120px;height:20px;>Details</h3>");
			applicationStoreTabSheet.getTab(1).setCaption("<h3 style=font-weight:400;width:120px;height:20px;>Profiles</h3>");
			applicationStoreTabSheet.getTab(2).setCaption("<h3 style=font-weight:400;width:120px;height:20px;>Parameters</h3>");
			applicationStoreTabSheet.getTab(3).setCaption("<h3 style=font-weight:400;width:120px;height:20px;>Files</h3>");
			MainViewIconsLoad.iconsOnTabMode(mainView);
			if(openProfileWindow!=null) {
				openProfileWindow.center();
				openProfileWindow.setWidth("25%");
				profileName.setHeight("32px");
				saveProfile.setHeight("32px");
				cancelProfile.setHeight("32px");
			}
		
		}else {
			applicationSearch.setHeight(37, Unit.PIXELS);
			appDefaultParamSearch.setHeight("37px");
			appParamSearchLayout.setWidth("98%");
			clearAppSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
			clearAppSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearDesktop");
			clearParamSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
			clearParamSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearDesktop");
			mainView.getTitle().setValue("gemView"/*+ userService.getLoggedInUserName()*/);
			addComponentAsFirst(header);
			applicationStoreTabSheet.getTab(0).setCaption("<h3 style=font-weight:400;width:150px;height:25px;>Details</h3>");
			applicationStoreTabSheet.getTab(1).setCaption("<h3 style=font-weight:400;width:150px;height:25px;>Profiles</h3>");
			applicationStoreTabSheet.getTab(2).setCaption("<h3 style=font-weight:400;width:150px;height:25px;>Parameters</h3>");
			applicationStoreTabSheet.getTab(3).setCaption("<h3 style=font-weight:400;width:150px;height:25px;>Files</h3>");
			MainViewIconsLoad.noIconsOnDesktopMode(mainView);
			if(openProfileWindow!=null) {
				openProfileWindow.center();
				openProfileWindow.setWidth("30%");
				profileName.setHeight("37px");
				saveProfile.setHeight("37px");
				cancelProfile.setHeight("37px");
			}
		}
		
		if(width<=730) {
			appGridMenuVerticalLayout = new VerticalLayout();
			appGridMenuVerticalLayout.addStyleName("heartbeat-verticalLayout");
			appGridMenuVerticalLayout.addComponents(appSearchLayout, appButtonsLayout);
			applicationListLayout.removeComponent(appGridMenuHorizontalLayout);
			applicationListLayout.removeComponent(appGrid);
			applicationListLayout.addComponents(appGridMenuVerticalLayout, appGrid);
		}
	}

	private void phoneAndTabMode() throws ApiException {
		appStoreGridLayout.removeAllComponents();
		appStoreGridLayout.setColumns(1);
		appStoreGridLayout.setRows(4);
		appStoreGridLayout.addComponents(getAppStoreComponentsPhoneAndTabMode());
		buttonLayout.removeAllComponents();
		appButtonsLayout.addComponents(cancelForm, saveForm);
	}

	private void desktopMode() throws ApiException {
		appStoreGridLayout.removeAllComponents();
		appStoreGridLayout.setColumns(2);
		appStoreGridLayout.setRows(2);
		appStoreGridLayout.addComponents(getAppStoreComponents());
	}

	private Panel getAndLoadApplicationStorePanel() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setResponsive(true);
		panel.setSizeFull();
		addComponent(header);
		addComponent(panel);
		return panel;
	}
	
	private VerticalLayout getTabSheet() throws ApiException {
		VerticalLayout tabVerticalLayout = new VerticalLayout();
		tabVerticalLayout.setHeight("100%");
		applicationStoreTabSheet = new TabSheet();
		applicationStoreTabSheet.setCaptionAsHtml(true);
		applicationStoreTabSheet.setTabCaptionsAsHtml(true);
		applicationStoreTabSheet.setHeight("91%");
		applicationStoreTabSheet.addStyleName("applicationStore-TabLayout");
		applicationStoreTabSheet.addStyleNames(ValoTheme.TABSHEET_FRAMED, ValoTheme.TABSHEET_PADDED_TABBAR);
		applicationStoreTabSheet.addTab(getAppicationDetailsLayout());
		applicationStoreTabSheet.addTab(getApplicationProfileLayout());
		applicationStoreTabSheet.addTab(getApplicationDefaulParametersLayout());
		applicationStoreTabSheet.addTab(getApplicationFileLayout());
		tabVerticalLayout.addComponents(buttonLayout, applicationStoreTabSheet);
		tabVerticalLayout.addStyleName("heartbeat-verticalLayout");
		tabVerticalLayout.setComponentAlignment(buttonLayout, Alignment.BOTTOM_RIGHT);
		
		applicationStoreTabSheet.addSelectedTabChangeListener(new SelectedTabChangeListener() {
			
			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				selectedProfile=null;
				profileSelectFiles.clear();
				
			}
		});
		
		return tabVerticalLayout;
	}

	private Component[] getAppStoreComponents() throws ApiException {
		Component[] components = { getApplicationListLayout(), getTabSheet()};
		return components;
	}
	
	private Component[] getAppStoreComponentsPhoneAndTabMode() throws ApiException {
		Component[] components = { getApplicationListLayout(), getTabSheet()};
		return components;
	}
	
	private void disableAllComponents() throws Exception {
		saveForm.setEnabled(false);
		cancelForm.setEnabled(false);
	}
	private VerticalLayout getApplicationListLayout() {
		appGrid = new Grid<>(AppClient.class);
		appGrid.setWidth("100%");
		appGrid.setHeightByRows(21);
		appGrid.setRowHeight(36);
		appGrid.setColumns("packageName", "description", "packageVersion");
		appGrid.getColumn("packageName").setCaption("Name");
		appGrid.getColumn("description").setCaption("Description");
		appGrid.getColumn("packageVersion").setCaption("Version");
		appGrid.addColumn("available").setCaption("Available");
		appGrid.setDataProvider(appStoreService.getAppListDataProvider());
		appGrid.setSelectionMode(SelectionMode.SINGLE);
		
		applicationSearch = new TextField();
		applicationSearch.setWidth("100%");
		applicationSearch.setMaxLength(50);
		applicationSearch.setIcon(VaadinIcons.SEARCH);
		applicationSearch.setStyleName("small inline-icon search");
		applicationSearch.addStyleNames("v-textfield-font");
		applicationSearch.setHeight("37px");
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
			if(valueChange.getValue().length()==50) {
				Notification search = Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
				search.addCloseListener(listener->{
					String valueInLower = valueChange.getValue().toLowerCase();
					List<AppClient> appClientList = appStoreService.searchApps(valueInLower);
					DataProvider data = new ListDataProvider(appClientList);
					appGrid.setDataProvider(data);
				});
			}else if(valueChange.getValue()!=null && valueChange.getValue().toLowerCase().isEmpty()){
				List<AppClient> appClientList = appStoreService.getAppListForGrid();
				DataProvider data = new ListDataProvider(appClientList);
				appGrid.setDataProvider(data);
			}else {
				String valueInLower = valueChange.getValue().toLowerCase();
				List<AppClient> appClientList = appStoreService.searchApps(valueInLower);
				DataProvider data = new ListDataProvider(appClientList);
				appGrid.setDataProvider(data);
			}
			
			//Searching on grid on UI Side. May be useful in future
			
			/*ListDataProvider<AppClient> appDataProvider = (ListDataProvider<AppClient>) appGrid.getDataProvider();
			appDataProvider.setFilter(filter -> {
				String packageNameInLower = filter.getPackageName().toLowerCase();
				String packageVersionInLower = filter.getPackageVersion().toLowerCase();
				String fileInLower = filter.getDescription().toLowerCase();
				return packageVersionInLower.equals(valueInLower) || packageNameInLower.contains(valueInLower)
						|| fileInLower.contains(valueInLower);
			});*/
		});
		
		CssLayout applicationSearchCSSLayout = new CssLayout();
		applicationSearchCSSLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		applicationSearchCSSLayout.setWidth("90%");
		
		clearAppSearch = new Button(VaadinIcons.CLOSE);
		clearAppSearch.addClickListener(listener->{
			applicationSearch.clear();
		});
		applicationSearchCSSLayout.addComponents(applicationSearch, clearAppSearch);
		Button createAppGridRowMenu = new Button("Create Application",click->{
		
			setApplicationFormComponentsEnable(access, add, edit, delete);
			appGrid.deselectAll();
			clearDetailsFields();
			applicationDetailsForm.setEnabled(true);
			packageName.focus();
			fileButton.setEnabled(false);
			UI.getCurrent().getWindows().forEach(Window::close);
		});
		createAppGridRowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		createAppGridRowMenu.setEnabled(add);
		
		Button editAppGridRowMenu = new Button("Edit Application",click->{
			UI.getCurrent().getWindows().forEach(Window::close);
			if (appGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.APPLICATIONSTORE_EDIT, Notification.Type.ERROR_MESSAGE);
			} else {
				setApplicationFormComponentsEnable(access, add, edit, delete);
				int defaultParamCount = ((VerticalLayout) appDefaultParamGrid.getParent()).getComponentCount();
				for (int i = 0; i < defaultParamCount; i++) {
					Component component = ((VerticalLayout) appDefaultParamGrid.getParent()).getComponent(i);
					component.setEnabled(true);
					packageName.focus();
				}
			}
		});
		editAppGridRowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		editAppGridRowMenu.setEnabled(edit);
		
		Button deleteAppGridRowMenu = new Button("Delete Application", clicked -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			if (appGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.APPLICATIONSTORE_DELETE, Notification.Type.ERROR_MESSAGE);
			} else {
				confirmDeleteApp(applicationSearch);
			}

		});
		deleteAppGridRowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		deleteAppGridRowMenu.setEnabled(delete);

		ContextMenuWindow appGridContextMenu = new ContextMenuWindow();
		appGridContextMenu.addMenuItems(createAppGridRowMenu,editAppGridRowMenu,deleteAppGridRowMenu);
		appGrid.addContextClickListener(click->{
			UI.getCurrent().getWindows().forEach(Window::close);
			if(click.getClientY() > 750) {
				appGridContextMenu.setPosition(click.getClientX(), click.getClientY()-220);
			}else {
				appGridContextMenu.setPosition(click.getClientX(), click.getClientY());
			}
			
			if(appGrid.getSelectedItems().size()==1 && edit) {
				editAppGridRowMenu.setEnabled(edit);
				createAppGridRowMenu.setEnabled(false);
			}else {
				editAppGridRowMenu.setEnabled(false);
				createAppGridRowMenu.setEnabled(add);
			}
			
			if(appGrid.getSelectedItems().size()>0 && delete) {
				deleteAppGridRowMenu.setEnabled(delete);
			}else {
				deleteAppGridRowMenu.setEnabled(false);
			}
			UI.getCurrent().addWindow(appGridContextMenu);
			
		});
		UI.getCurrent().addClickListener(Listener->{
			appGridContextMenu.close();
			
		});
		
		appSearchLayout = new HorizontalLayout(applicationSearchCSSLayout);
		appSearchLayout.setWidth("98%");
		appButtonsLayout = new HorizontalLayout();
		appGridMenuHorizontalLayout = new HorizontalLayout(appSearchLayout);
		appGridMenuHorizontalLayout.setWidth("100%");
		applicationListLayout = new VerticalLayout(appGridMenuHorizontalLayout, appGrid);
		applicationListLayout.addStyleName("applicationStore-ApplicationStoreLayout");
		appGrid.addSelectionListener(selection -> {
			if (selection.getFirstSelectedItem().isPresent()) {
				UI.getCurrent().getWindows().forEach(Window::close);
				selectedApp = selection.getFirstSelectedItem().get();
				((TextField) applicationDetailsForm.getComponent(0)).setValue(selectedApp.getPackageName());
				((TextField) applicationDetailsForm.getComponent(1)).setValue(selectedApp.getDescription());
				((TextField) applicationDetailsForm.getComponent(2)).setValue(selectedApp.getPackageVersion());
				((ComboBox) applicationDetailsForm.getComponent(3)).setValue(appStoreService.getOwner(selectedApp.getOwnerId()));
				HorizontalLayout HL = (HorizontalLayout) applicationDetailsForm.getComponent(4);
				CheckBox checkbox = (CheckBox) HL.getComponent(1);
				checkbox.setValue(selectedApp.isAvailable());
				profileSelect.clear();
				profileSelectFiles.clear();
				selectedProfile=null;
				appDefaultParamGrid.setEnabled(true);
				fileButton.setEnabled(add);
				ListDataProvider<Profile> profileListDatProvider = appStoreService.getAppProfileListDataProvider(selectedApp.getId());
				
				profileSelect.setDataProvider(profileListDatProvider);
				profileSelectFiles.setDataProvider(profileListDatProvider);
				appDefaultParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(appStoreService.getAppDefaultParamListByAppId(selectedApp.getId())));
				appProfileGrid.setDataProvider(appStoreService.getAppProfileListDataProvider(selectedApp.getId()));
				appFileGrid.setDataProvider(new ListDataProvider<>(appStoreService.getAllAppFileList(selectedApp.getId())));
					parameterType.setDataProvider(new ListDataProvider<>(appStoreService.getAppParamTypeList(selectedApp.getId())));
				setApplicationFormComponentsEnableForAPPClick(access, add, edit, delete);
			} else {
				selectedProfile=null;
				((TextField) applicationDetailsForm.getComponent(0)).clear();
				((TextField) applicationDetailsForm.getComponent(1)).clear();
				((TextField) applicationDetailsForm.getComponent(2)).clear();
				((ComboBox) applicationDetailsForm.getComponent(3)).clear();
				HorizontalLayout HL = (HorizontalLayout) applicationDetailsForm.getComponent(4);
				CheckBox checkbox = (CheckBox) HL.getComponent(1);
				checkbox.clear();
				appDefaultParamGrid.setDataProvider(new ListDataProvider<>(Arrays.asList()));
				appProfileGrid.setDataProvider(new ListDataProvider<>(Arrays.asList()));
				appFileGrid.setDataProvider(new ListDataProvider<>(Arrays.asList()));
				setApplicationFormComponentsEnable(false, false, false, false);
			}
		});
		return applicationListLayout;
	}

	private void setApplicationFormComponentsEnable(boolean access, boolean add, boolean edit, boolean delete) {
		
			fileButtonLayout.setEnabled(access);
			applicationDetailsForm.getComponent(4).setEnabled(access);
		
			if(add) {
			packageName.setEnabled(add);
			description.setEnabled(add);
			packageVersion.setEnabled(add);
			applicationOwner.setEnabled(add);
			activeBoxLayout.setEnabled(add);
			fileButton.setEnabled(add);
			activeApplication.setEnabled(add);
			
		}else {
			packageName.setEnabled(false);
			description.setEnabled(false);
			packageVersion.setEnabled(false);
			applicationOwner.setEnabled(false);
			activeBoxLayout.setEnabled(false);
			fileButton.setEnabled(false);
			activeApplication.setEnabled(false);
		}
		
			if(edit) {
				applicationDetailsForm.setEnabled(edit);
				packageName.setEnabled(edit);
				description.setEnabled(edit);
				packageVersion.setEnabled(edit);
				applicationOwner.setEnabled(edit);
				activeBoxLayout.setEnabled(edit);
				fileButton.setEnabled(edit);
				activeApplication.setEnabled(edit);
				
			}
	}
	
	private void setApplicationFormComponentsEnableForAPPClick(boolean access, boolean add, boolean edit, boolean delete) {
		
			fileButtonLayout.setEnabled(access);
			packageName.setEnabled(false);
			description.setEnabled(false);
			packageVersion.setEnabled(false);
			applicationOwner.setEnabled(false);
			activeBoxLayout.setEnabled(false);
			fileButton.setEnabled(add || edit);
			activeApplication.setEnabled(false);
	}
	
	private VerticalLayout getAppicationDetailsLayout() throws ApiException {

		packageName = new TextField("Application Package Name");
		packageName.addStyleNames("v-textfield-font", "textfiled-height");
		packageName.setMaxLength(50);
		packageName.setCaptionAsHtml(true);
		packageName.setEnabled(false);
		packageName.setWidth("96%");
		packageName.setRequiredIndicatorVisible(true);
		packageName.addValueChangeListener(listener->{
			if(listener.getValue().length()==50) {
				Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});
		
		description = new TextField("Description");
		description.addStyleNames("v-textfield-font", "textfiled-height");
		description.setEnabled(false);
		description.setId(DESCRIPTION);
		description.setWidth("96%");
		description.setMaxLength(50);
		description.setRequiredIndicatorVisible(true);
		description.addValueChangeListener(listener->{
			if(listener.getValue().length()==50) {
				Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});
		
		packageVersion = new TextField("Version");
		packageVersion.addStyleNames("v-textfield-font", "textfiled-height");
		packageVersion.setEnabled(false);
		packageVersion.setWidth("96%");
		packageVersion.setMaxLength(50);
		packageVersion.setRequiredIndicatorVisible(true);
		packageVersion.addValueChangeListener(listener->{
			if(listener.getValue().length()==50) {
				Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});
		applicationOwner = new ComboBox<TreeNode>("Application Owner");
		applicationOwner.setEnabled(false);
		applicationOwner.setCaptionAsHtml(true);
		applicationOwner.setWidth("96%");
		applicationOwner.setRequiredIndicatorVisible(true);
		applicationOwner.setCaptionAsHtml(true);
		applicationOwner.setPlaceholder("Select Owner");
		applicationOwner.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "textfiled-height");
		applicationOwner.setDataProvider(new ListDataProvider<>(appStoreService.getOwnerList()));
		
		activeBoxLayout = new HorizontalLayout();
		activeBoxLayout.setId(ACTIVE_LAYOUT);
		Label active = new Label("");
		active.setStyleName("role-activeLable");
		active.addStyleNames("v-textfield-font");
		activeApplication = new CheckBox("Application Available", false);
		activeApplication.setEnabled(false);
		activeApplication.setRequiredIndicatorVisible(true);
		activeApplication.addStyleNames("v-textfield-font");
		activeBoxLayout.addComponents(active, activeApplication);
		activeBoxLayout.setStyleName("role-activeLable");
		activeBoxLayout.addStyleNames("applicationStore-activeCheckBox", "asset-debugComboBox");
		saveForm = new Button("Save");
		saveForm.addClickListener(click -> {
			if(!(ComponentUtil.validatorTextField(packageName) && ComponentUtil.validatorTextField(description) && 
					ComponentUtil.validatorTextField(packageVersion) && ComponentUtil.validatorComboBox(applicationOwner))) {
			}
			else {
				AppClient app;
			if (appGrid.getSelectedItems().size() > 0) {
			app = appGrid.getSelectedItems().iterator().next();
			app.setPackageName(packageName.getValue());
			app.setDescription(description.getValue());
			app.setPackageVersion(packageVersion.getValue());
			app.setOwnerId(applicationOwner.getValue().getId());
			app.setAvailable(activeApplication.getValue());
			app.setAppFileList(appStoreService.getAllAppFileList(app.getId()));
				appStoreService.saveApp(app);
				appGrid.setDataProvider(appStoreService.getAppListDataProvider());
				appGrid.getDataProvider().refreshAll();
				appGrid.deselectAll();
				packageName.clear();
				description.clear();
				packageVersion.clear();
				applicationOwner.clear();
				activeApplication.clear();
			} else {
				app = new AppClient();
				app.setActive(true);
				app.setId(null);
				app.setPackageName(packageName.getValue());
				app.setDescription(description.getValue());
				app.setPackageVersion(packageVersion.getValue());
				app.setOwnerId(applicationOwner.getValue().getId());
				app.setAvailable(activeApplication.getValue());
				app.setAppDefaultParamList(
						appDefaultParamGrid.getDataProvider().fetch(new Query<>()).collect(Collectors.toList()));
				app.setProfileList(new ArrayList());
				appStoreService.saveApp(app);
				appGrid.setDataProvider(appStoreService.getAppListDataProvider());
				appGrid.getDataProvider().refreshAll();
				appGrid.deselectAll();
				packageName.clear();
				description.clear();
				packageVersion.clear();
				applicationOwner.clear();
				activeApplication.clear();
			}
			setApplicationFormComponentsEnable(access, false, false, false);
			fileButton.setEnabled(true);
			//profileDropDown.setEnabled(true);

			}
		});
		saveForm.setDescription("Save");
		saveForm.setEnabled(true );
		saveForm.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY);
		cancelForm = new Button("Cancel", click -> {
			packageName.clear();
			description.clear();
			packageVersion.clear();
			applicationOwner.clear();
			activeApplication.clear();
			applicationDetailsForm.setEnabled(false);
			
			setApplicationFormComponentsEnable(access, false, false, false);
			
			appDefaultParamGrid.setDataProvider(new ListDataProvider<>(Arrays.asList()));

			appDefaultParamGrid.setEnabled(false);
			appGrid.deselectAll();

		});
		cancelForm.setDescription("Cancel");
		cancelForm.setEnabled(true);
		cancelForm.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY);
		HorizontalLayout appDetailsSaveCancleAndLabelLayout = new HorizontalLayout();
		appDetailsSaveCancleAndLabelLayout.addStyleName("applicationStore-cancelsaveApplicationLabelLayout");
		appDetailsSaveCancleAndLabelLayout.setWidth("100%");
		applicationDetailsForm = new VerticalLayout(packageName, description, packageVersion, applicationOwner,
				 activeBoxLayout);
		applicationDetailsForm.addStyleNames("heartbeat-verticalDetailLayout");
		applicationDetailsForm.setCaptionAsHtml(true);
		applicationDetailsForm.setHeight("100%");
		applicationDetailsLabel = new HorizontalLayout();
		
		buttonLayout = new HorizontalLayout();
		buttonLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		buttonLayout.addStyleName("applicationStore-buttonsLayout");
		buttonLayout.addComponents(cancelForm, saveForm);
		buttonLayout.setResponsive(true);
		
		VerticalLayout applicationDetailsLayout = new VerticalLayout(
				applicationDetailsForm);
		applicationDetailsLayout.setHeight("100%");
		applicationDetailsLayout.addStyleNames("applicationStore-ApplicationDetailsLayout");
		return applicationDetailsLayout;
	}
	
	private VerticalLayout getApplicationFileLayout() {
		
		fileButton = new Button("Files", VaadinIcons.UPLOAD);
		fileButton.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY);
		fileButton.setDescription("Upload Files");
		fileButton.addClickListener(click -> {
			if(appGrid.getSelectedItems().isEmpty())
				Notification.show("Select a app first",Type.ERROR_MESSAGE);
			else {
			Window fileUpload = openFileUploadWindow(appFileGrid);
			if (fileUpload.getParent() == null) {
				UI.getCurrent().addWindow(fileUpload);
			} else if (fileUpload.getComponentCount() > 0) {
				fileUpload.close();
			}
			}
		});
		
		profileSelectFiles = new ComboBox<Profile>();
		profileSelectFiles.setEmptySelectionAllowed(true);
		profileSelectFiles.setCaptionAsHtml(true);
		profileSelectFiles.setEmptySelectionCaption("(Select Profile)");
		profileSelectFiles.addSelectionListener(select ->{
			if(select.getValue()!=null) {
				selectedProfile = select.getValue();
				appFileGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(appStoreService.getAppFileListByAppProfileId(selectedProfile.getId())));
			}else {
				appFileGrid.setDataProvider(new ListDataProvider<>(appStoreService.getAllAppFileList(selectedApp.getId())));
			}
		});
		profileSelectFiles.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "textfiled-height");
		
		fileButtonLayout = new HorizontalLayout();
		fileButtonLayout.setCaptionAsHtml(true);
		fileButtonLayout.setCaption("<span style=margin-left:15px;>Upload Files</span>");
		fileButtonLayout.addStyleNames("asset-debugComboBox");
		fileButtonLayout.addComponents(fileButton, profileSelectFiles);
	
		appFileGrid = new Grid<>(AppDefaultParam.class);
		appFileGrid.setColumns("parameter");
		appFileGrid.setWidth("100%");
		appFileGrid.setHeightByRows(16);
		appFileGrid.setId(FILE_CHOOSE_LIST);
		appFileGrid.setSelectionMode(SelectionMode.MULTI);
		appFileGrid.addSelectionListener(listener->{
			UI.getCurrent().getWindows().forEach(Window::close);
		});
		
		Button deleteAppFileGridRowMenu = new Button("Delete File", clicked -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			if (appGrid.getSelectedItems().isEmpty() || appFileGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.APPLICATIONSTORE_DELETE, Notification.Type.ERROR_MESSAGE);
			} else {
				confirmDeleteAppProfileFiles(selectedProfile);
			}

		});
		deleteAppFileGridRowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		deleteAppFileGridRowMenu.setEnabled(delete);
		
		ContextMenuWindow paramContextWindow = new ContextMenuWindow();
		paramContextWindow.addMenuItems(deleteAppFileGridRowMenu);
		appFileGrid.addContextClickListener(click ->{
			UI.getCurrent().getWindows().forEach(Window::close);
			if(click.getClientY() > 750) {
				paramContextWindow.setPosition(click.getClientX(), click.getClientY()-220);
			}else {
				paramContextWindow.setPosition(click.getClientX(), click.getClientY());
			}
			
			if(appFileGrid.getSelectedItems().size()>0 && delete) {
				deleteAppFileGridRowMenu.setEnabled(edit);
			}else {
				deleteAppFileGridRowMenu.setEnabled(false);
			}
			
			UI.getCurrent().addWindow(paramContextWindow);
		});
		
		UI.getCurrent().addClickListener(listener->{
			paramContextWindow.close();
		});
		return new VerticalLayout(fileButtonLayout,appFileGrid);
	}

	private VerticalLayout getApplicationDefaulParametersLayout() throws ApiException {
		appDefaultParamGrid = new Grid<>(AppDefaultParam.class);
		appDefaultParamGrid.setColumns("parameter", "description", "type", "value");
		appDefaultParamGrid.setWidth("100%");
		appDefaultParamGrid.setHeightByRows(17);
		appDefaultParamGrid.getColumn("parameter").setEditorComponent(new TextField());
		appDefaultParamGrid.getColumn("description").setEditorComponent(new TextField());
		appDefaultParamGrid.getColumn("type").setEditorComponent(new ComboBox<String>("Type"));
		appDefaultParamGrid.getColumn("value").setEditorComponent(new TextField());
		appDefaultParamGrid.setSelectionMode(SelectionMode.MULTI);
		appDefaultParamGrid.getColumn("parameter").setEditable(false);
		appDefaultParamGrid.getColumn("description").setEditable(false);
		appDefaultParamGrid.getColumn("type").setEditable(false);
		appDefaultParamGrid.addSelectionListener(listener->{
			UI.getCurrent().getWindows().forEach(Window::close);
			if(listener!=null && listener.getAllSelectedItems().size()!=0){
				AppDefaultParam selectedParam =  appDefaultParamGrid.getSelectedItems().iterator().next();
				parameterType.setValue(selectedParam.getType());
			}
		});
		
		appDefaultParamGrid.getEditor().addSaveListener(save -> {
			if(selectedProfile.getId()!=null) {
					AppParamFormat appParamFormat = appStoreService.getAppParamFormatByType(parameterType.getValue());
					appStoreService.updateAppParamOfAppProfile(selectedProfile,  save.getBean(), appParamFormat);
			}

		});
		
		ContextMenuWindow paramContextWindow = new ContextMenuWindow();
		createParamGridWindow=  new ContextMenuWindow(); 
		Button createAppDefaultParamGridRowMenu = new Button("Create Parameters",click->{
			UI.getCurrent().getWindows().forEach(Window::close);
				if(appGrid.getSelectedItems().size()==0) {
					Notification.show(NotificationUtil.APPLICATIONSTORE_PARAM_ADD, Type.ERROR_MESSAGE);
				}else {
				appDefaultParamGrid.deselectAll();
				if (createParamGridWindow.getParent() == null)
					try {
						createParamGridWindow = openAppDefaultParamWindow(appDefaultParamGrid);
						UI.getCurrent().addWindow(createParamGridWindow);
					} catch (ApiException e) {
						e.printStackTrace();
					}
				}
				paramContextWindow.close();
		});
		createAppDefaultParamGridRowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		createAppDefaultParamGridRowMenu.setEnabled(add);
		
		Button editAppDefaultParamGridRowMenu = new Button("Edit Parameter",click->{
			UI.getCurrent().getWindows().forEach(Window::close);
			if (profileSelect.getSelectedItem().toString().isEmpty() || 
					profileSelect.getSelectedItem().toString().equalsIgnoreCase("Optional.empty")) {
				Notification.show(NotificationUtil.APPLICATIONSTORE_EDIT_PARAM, Notification.Type.ERROR_MESSAGE);
			} else {
				List<AppDefaultParam> items = appDefaultParamGrid.getDataCommunicator().fetchItemsWithRange(0,appDefaultParamGrid.getDataCommunicator().getDataProviderSize());
				int rowNumber = items.indexOf(appDefaultParamGrid.getSelectedItems().iterator().next());
				appDefaultParamGrid.getEditor().editRow(rowNumber);
			}
			paramContextWindow.close();
		});
		editAppDefaultParamGridRowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		editAppDefaultParamGridRowMenu.setEnabled(edit);
		
		Button deleteAppDefautParamGridRowMenu = new Button("Delete Parameter", clicked -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			if (appGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.APPLICATIONSTORE_DELETE, Notification.Type.ERROR_MESSAGE);
			} else {
				if (appDefaultParamGrid.getSelectedItems().isEmpty()) {
					Notification.show(NotificationUtil.APPLICATIONSTORE_PARAM_DELETE_PARAMGRID, Type.ERROR_MESSAGE);
				}else if(selectedProfile!=null) {
					confirmDeleteAppProfileParam(appDefaultParamGrid.getSelectedItems().iterator().next().getId(), selectedProfile.getId(), appDefaultParamSearch);
				} else {
					confirmDeleteAppDefaultParam(appDefaultParamGrid.getSelectedItems().iterator().next().getId(), selectedApp.getId(), appDefaultParamSearch);
				}
			}
			
			paramContextWindow.close();

		});
		deleteAppDefautParamGridRowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		deleteAppDefautParamGridRowMenu.setEnabled(delete);
		
		paramContextWindow.addMenuItems(createAppDefaultParamGridRowMenu,editAppDefaultParamGridRowMenu,deleteAppDefautParamGridRowMenu);
		
		
		appDefaultParamGrid.addContextClickListener(click->{
			UI.getCurrent().getWindows().forEach(Window::close);
			
			if(click.getClientY() > 750) {
				paramContextWindow.setPosition(click.getClientX(), click.getClientY()-220);
			}else {
				paramContextWindow.setPosition(click.getClientX(), click.getClientY());
			}
			
			if(appDefaultParamGrid.getSelectedItems().size()==1 && edit) {
				editAppDefaultParamGridRowMenu.setEnabled(edit);
			}else {
				editAppDefaultParamGridRowMenu.setEnabled(false);
			}
			
			if(appDefaultParamGrid.getSelectedItems().size()>0 && delete) {
				deleteAppDefautParamGridRowMenu.setEnabled(delete);
			}else {
				deleteAppDefautParamGridRowMenu.setEnabled(false);
			}
			UI.getCurrent().addWindow(paramContextWindow);
			
		});
		
		UI.getCurrent().addClickListener(listener->{
			paramContextWindow.close();
			
			if(createParamGridWindow!=null) {
				createParamGridWindow.close();
			}
		});

		appDefaultParamSearch = new TextField();
		appDefaultParamSearch.setWidth("100%");
		appDefaultParamSearch.setIcon(VaadinIcons.SEARCH);
		appDefaultParamSearch.setStyleName("small inline-icon search");
		appDefaultParamSearch.addStyleNames("v-textfield-font");
		appDefaultParamSearch.setHeight("37px");
		appDefaultParamSearch.setPlaceholder("Search");
		appDefaultParamSearch.setResponsive(true);
		appDefaultParamSearch.setMaxLength(50);
		appDefaultParamSearch.addShortcutListener(new ShortcutListener("Clear", KeyCode.ESCAPE, null) {

			@Override
			public void handleAction(Object sender, Object target) {
				if (target == appDefaultParamSearch) {
					appDefaultParamSearch.clear();
				}

			}
		});
		
		appDefaultParamSearch.addValueChangeListener(valueChange -> {
			if(valueChange.getValue().length()==50) {
				Notification search = Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
				search.addCloseListener(listener->{
					String valueInLower = valueChange.getValue().toLowerCase();
					List<AppDefaultParam> appDefaultParamList = appStoreService.searchParams(selectedApp.getId(), valueInLower);
					DataProvider data = new ListDataProvider(appDefaultParamList);
					appDefaultParamGrid.setDataProvider(data);
				});
			}else {
			/*String valueInLower = valueChange.getValue().toLowerCase();
			ListDataProvider<AppDefaultParam> appDataProvider = (ListDataProvider<AppDefaultParam>) appDefaultParamGrid
					.getDataProvider();
			appDataProvider.setFilter(filter -> {
				String parameterInLower = filter.getParameter().toLowerCase();
				String descriptionInLower = filter.getDescription().toLowerCase();
				String typeLower = filter.getType().toLowerCase();
				String valueLower = filter.getValue().toLowerCase();
				return descriptionInLower.equals(valueInLower) || parameterInLower.contains(valueInLower)
						|| typeLower.contains(valueInLower) || valueLower.contains(valueInLower);
			});*/
			
			String valueInLower = valueChange.getValue().toLowerCase();
			List<AppDefaultParam> appDefaultParamList = appStoreService.searchParams(selectedApp.getId(), valueInLower);
			if(appParamWithEntity.containsAll(appDefaultParamList)) {
				DataProvider data = new ListDataProvider(appDefaultParamList);
				appDefaultParamGrid.setDataProvider(data);
			}else if(valueInLower.isEmpty()){
				DataProvider data = new ListDataProvider(appParamWithEntity);
				appDefaultParamGrid.setDataProvider(data);
				profileSelect.clear();
			}
			else {
				DataProvider data = new ListDataProvider(Collections.EMPTY_LIST);
				appDefaultParamGrid.setDataProvider(data);
			}
			
			}
		});
		
		CssLayout parameterSearchCSSLayout = new CssLayout();
		parameterSearchCSSLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		parameterSearchCSSLayout.setWidth("93%");
		
		clearParamSearch = new Button(VaadinIcons.CLOSE);
		clearParamSearch.addClickListener(listener->{
			appDefaultParamSearch.clear();
		});
		parameterSearchCSSLayout.addComponents(appDefaultParamSearch, clearParamSearch);
		
		profileSelect = new ComboBox<Profile>();
		profileSelect.setEmptySelectionAllowed(true);
		profileSelect.setEmptySelectionCaption("(Select Profile)");
		profileSelect.addSelectionListener(select ->{
			appDefaultParamGrid.setEnabled(true);
			appDefaultParamGrid.deselectAll();
			if(select.getValue()!=null) {
				selectedProfile = select.getValue();
				appParamWithEntity = appStoreService.getAppParamListByAppProfileId(select.getValue().getId());
				appDefaultParamGrid.setDataProvider(new ListDataProvider<>(appParamWithEntity));
			}else {
				appDefaultParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(appStoreService.getAppDefaultParamListByAppId(selectedApp.getId())));
			}
			
			if(select.getValue()!=null && (add || edit)) {
				appDefaultParamGrid.getEditor().setEnabled(true);
			}
		});
		profileSelect.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "textfiled-height");
		HorizontalLayout parameterLoadLayout = new HorizontalLayout(profileSelect,parameterSearchCSSLayout);
		parameterLoadLayout.setWidth("100%");
		parameterLoadLayout.setExpandRatio(parameterSearchCSSLayout, 3);

		appParamSearchLayout = new HorizontalLayout(parameterLoadLayout);
		HorizontalLayout appParamGridMenuLayout = new HorizontalLayout(appParamSearchLayout);
		appParamGridMenuLayout.setWidth("100%");
		appParamGridMenuLayout.addStyleName("applicationStore-horizontalAlignment");

		applicationParamLabelLayout = new HorizontalLayout();
		applicationParamLabelLayout.setWidth("100%");
		appParamGridMenuLayout.setEnabled(true);
		VerticalLayout applicationDefaultParametersLayout = new VerticalLayout(parameterLoadLayout, appDefaultParamGrid);
		applicationDefaultParametersLayout.setCaptionAsHtml(true);
		applicationDefaultParametersLayout.addStyleName("applicationStore-VerticalLayout");
		return applicationDefaultParametersLayout;
	}
	private HorizontalLayout getApplicationProfileLayout() {

	appProfileGrid = new Grid<>(Profile.class);
	appProfileGrid.addStyleName("applicationStore-horizontalAlignment");
	appProfileGrid.setColumns("name");
	appProfileGrid.setWidth("100%");
	appProfileGrid.setHeightByRows(19);
	TextField appProfile = new TextField();
	appProfile.setMaxLength(50);
	appProfileGrid.getColumn("name").setEditorComponent(appProfile);
	appProfileGrid.getColumn("name").setCaption("Profile Name");
	appProfileGrid.setSelectionMode(SelectionMode.MULTI);
	appProfileGrid.addSelectionListener(listener->{
		UI.getCurrent().getWindows().forEach(Window::close);
	});
	
	ContextMenuWindow profileContextWindow = new ContextMenuWindow();
	
	Button createAppProfileGridRowMenu = new Button("Add Profile",click->{
		UI.getCurrent().getWindows().forEach(Window::close);
		if (appGrid.getSelectedItems().size() > 0) {
			openProfileWindow = openProfileWindow(false);
			if (openProfileWindow.getParent() == null) {
				UI.getCurrent().addWindow(openProfileWindow);
				profileContextWindow.close();
			}
			}else {
				Notification.show(NotificationUtil.APPLICATIONSTORE_PROFILE_DROPDOWN_CHECK, Type.ERROR_MESSAGE);
			}
	});
	createAppProfileGridRowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
	createAppProfileGridRowMenu.setEnabled(add);

	Button deleteAppProfileGridRowMenu = new Button("Delete Profile", clicked -> {
		UI.getCurrent().getWindows().forEach(Window::close);
		if (appGrid.getSelectedItems().size() > 0) {
			profileContextWindow.close();
				confirmDeleteAppProfile();
			}else {
				Notification.show(NotificationUtil.APPLICATIONSTORE_PROFILE_DROPDOWN_CHECK, Type.ERROR_MESSAGE);
			}

	});
	deleteAppProfileGridRowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
	deleteAppProfileGridRowMenu.setEnabled(delete);
	
	Button editAppProfileGridRowMenu = new Button("Edit Profile", clicked -> {
		if (appGrid.getSelectedItems().size() > 0) {
			Window openProfileWindow = openProfileWindow(true);
			if (openProfileWindow.getParent() == null) {
				UI.getCurrent().addWindow(openProfileWindow);
				profileContextWindow.close();
			}
			}else {
				Notification.show(NotificationUtil.APPLICATIONSTORE_PROFILE_DROPDOWN_CHECK, Type.ERROR_MESSAGE);
			}

	});
	editAppProfileGridRowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
	editAppProfileGridRowMenu.setEnabled(edit);
	
	profileContextWindow.addMenuItems(createAppProfileGridRowMenu,editAppProfileGridRowMenu, deleteAppProfileGridRowMenu);
	
	
	appProfileGrid.addContextClickListener(click -> {
		UI.getCurrent().getWindows().forEach(Window::close);
		
		if(click.getClientY() > 750) {
			profileContextWindow.setPosition(click.getClientX(), click.getClientY()-220);
		}else {
			profileContextWindow.setPosition(click.getClientX(), click.getClientY());
		}
		
		if(appProfileGrid.getSelectedItems().size()==1 && edit) {
			editAppProfileGridRowMenu.setEnabled(edit);
		}else {
			editAppProfileGridRowMenu.setEnabled(false);
		}
		
		if(appProfileGrid.getSelectedItems().size()>0 && delete) {
			deleteAppProfileGridRowMenu.setEnabled(delete);
		}else {
			deleteAppProfileGridRowMenu.setEnabled(false);
		}
		UI.getCurrent().addWindow(profileContextWindow);
	});
	
	UI.getCurrent().addClickListener(Listener->{
		profileContextWindow.close();
		
		if(openProfileWindow!=null) {
			openProfileWindow.close();
		}
		
	});
	HorizontalLayout profileLayout = new HorizontalLayout();
	profileLayout.setWidth("100%");
	profileLayout.addComponent(appProfileGrid);
	return profileLayout;
}
	private void confirmDeleteApp(TextField appSearch) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?", "Ok", "Cancel",
				new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							// Confirmed to continue
							appSearch.clear();
							setApplicationFormComponentsEnable(access, add, edit, delete);
							appStoreService.removeApp(appGrid.getSelectedItems().iterator().next());
							appGrid.setDataProvider(appStoreService.getAppListDataProvider());
							appGrid.getDataProvider().refreshAll();
							appGrid.deselectAll();
						} else {
							// User did not confirm

						}
					}
				});
	}

	private void confirmDeleteAppDefaultParam(Long appParamId, Long appId, TextField appParamSearch) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?", "Ok", "Cancel",
				new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							// Confirmed to continue
							 for(AppDefaultParam param : appDefaultParamGrid.getSelectedItems()) {
								 appStoreService.removeAPPParam(appId, param.getId());
							 }
								appDefaultParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(appStoreService.getAppDefaultParamListByAppId(appId)));
							 appParamSearch.clear();
						} else {
							// User did not confirm

						}
					}
				});
	}
	
	private void confirmDeleteAppProfileParam(Long appParamId, Long appProfileId, TextField appParamSearch) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?", "Ok", "Cancel",
				new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							// Confirmed to continue
							 for(AppDefaultParam param : appDefaultParamGrid.getSelectedItems()) {
								 appStoreService.removeAppProfileParam(appProfileId, param.getId());
							 }
								appDefaultParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(appStoreService.getAppParamListByAppProfileId(appProfileId)));
							 appParamSearch.clear();
						} else {
							// User did not confirm

						}
					}
				});
	}
	
	private void confirmDeleteAppProfileFiles(Profile selectedProfile) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?", "Ok", "Cancel",
				new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
								if(selectedProfile!=null) {
									for(AppDefaultParam file : appFileGrid.getSelectedItems()) {
										appStoreService.deleteAppProfileFiles(selectedProfile.getId(), file.getId());
									}
									appFileGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(appStoreService.getAppFileListByAppProfileId(selectedProfile.getId())));
									
								}else {
									for(AppDefaultParam file : appFileGrid.getSelectedItems()) {
										appStoreService.deleteAppFiles(appGrid.getSelectedItems().iterator().next().getId(), file.getId());
									}
									appFileGrid.setDataProvider(new ListDataProvider<>(appStoreService.getAllAppFileList(selectedApp.getId())));
								}
						} else {
							// User did not confirm

						}
					}
				});
	}
	
	private void confirmDeleteAppProfile() {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?", "Ok", "Cancel",
				new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							for(Profile appProfile : appProfileGrid.getSelectedItems()) {
							appStoreService.removeAppProfile(selectedApp.getId(), appProfile.getId());
							}
							appFileGrid.setDataProvider(new ListDataProvider<>(appStoreService.getAllAppFileList(selectedApp.getId())));
							
						} else {
							// User did not confirm

						}
					}
				});
	}

	private ContextMenuWindow openProfileWindow(boolean editOnly) {
		ContextMenuWindow profileWindow = new ContextMenuWindow();
		profileName.addStyleNames("v-textfield-font", "textfiled-height");
		profileName.setWidth("100%");
		profileName.setRequiredIndicatorVisible(true);
		profileName.setMaxLength(50);
		
		if(editOnly) {
			String value = appProfileGrid.getSelectedItems().iterator().next().toString();
			profileName.setValue(value);
		}else {
			profileName.clear();
		}
		
		saveProfile = new Button("Save", click -> {
			if (StringUtils.isNotEmpty(profileName.getValue())) {
				if(!editOnly) {	
					Profile pToSave = new Profile(profileName.getValue());
					appStoreService.saveAppProfile(selectedApp, pToSave);
				}else {
					appStoreService.updateAppProfile(profileName.getValue(), appProfileGrid.getSelectedItems().iterator().next());
				}
				
				appProfileGrid.setDataProvider(appStoreService.getAppProfileListDataProvider(selectedApp.getId()));
				profileSelect.setDataProvider(appStoreService.getAppProfileListDataProvider(selectedApp.getId()));
				profileSelectFiles.setDataProvider(appStoreService.getAppProfileListDataProvider(selectedApp.getId()));
				profileWindow.close();
			}else {
				Notification.show(NotificationUtil.PROFILE_SAVE, Type.ERROR_MESSAGE);
			}
		});
		saveProfile.setDescription("Save");
		saveProfile.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		cancelProfile = new Button("Reset", click -> {
			profileName.clear();
		});
		cancelProfile.setDescription("Reset");
		cancelProfile.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		HorizontalLayout buttonLayout = new HorizontalLayout(saveProfile, cancelProfile);
		buttonLayout.addStyleName("personlization-applicationSaveCancel");
		VerticalLayout profileFormLayout = new VerticalLayout(profileName);
		profileFormLayout.addStyleName("role-gridLayout");

		// Window Setup
		profileWindow.setContent(new VerticalLayout(profileFormLayout, buttonLayout));
		profileWindow.center();
		profileWindow.setResizable(true);
		profileWindow.setClosable(true);
		profileWindow.setWidth(30, Unit.PERCENTAGE);
		return profileWindow;
	}


	private ContextMenuWindow openAppDefaultParamWindow(Grid<AppDefaultParam> appDefaultParamGrid) throws ApiException {
		ContextMenuWindow appDefaultWindow = new ContextMenuWindow();
		parameterName = new TextField("Name");
		parameterName.setEnabled(true);
		parameterName.addStyleNames("textfiled-height","v-textfield-font");
		parameterName.setWidth("95%");
		parameterName.setMaxLength(50);
		parameterName.setRequiredIndicatorVisible(true);
		parameterName.addValueChangeListener(listener->{
			if(listener.getValue().length()==50) {
				Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});
		
		parameterDescription = new TextField("Description");
		parameterDescription.setEnabled(true);
		parameterDescription.addStyleNames("textfiled-height","v-textfield-font");
		parameterDescription.setWidth("95%");
		parameterDescription.setMaxLength(50);
		parameterDescription.setRequiredIndicatorVisible(true);
		parameterDescription.addValueChangeListener(listener->{
			if(listener.getValue().length()==50) {
				Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});
		
		
		parameterType.setEnabled(true);
		parameterType.setCaptionAsHtml(true);
		parameterType.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "textfiled-height");
		parameterType.setWidth("95%");
		parameterType.setRequiredIndicatorVisible(true);
		parameterType.setEmptySelectionAllowed(true);
		parameterType.setEmptySelectionCaption("(Select Type)");
		
		
		parameterActive = new TextField("Value");
		parameterActive.addStyleNames("textfiled-height","v-textfield-font");
		parameterActive.setWidth("95%");
		parameterActive.setMaxLength(50);
		parameterActive.setRequiredIndicatorVisible(true);
		parameterActive.addValueChangeListener(listener->{
			if(listener.getValue().length()==50) {
				Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});
		
		parameterValue = new ComboBox<String>("Value");
		parameterValue.setEnabled(true);
		parameterValue.setCaptionAsHtml(true);
		parameterValue.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "textfiled-height");
		parameterValue.setDataProvider(new ListDataProvider<>(Arrays.asList("True", "False")));
		parameterValue.setWidth("95%");
		parameterValue.setRequiredIndicatorVisible(true);
		
		//Clear Previously Entered Values
		parameterName.clear();
		parameterDescription.clear();
		parameterType.clear();
		
		saveParameter = new Button("Save", click -> {
			if (ComponentUtil.validatorTextField(parameterName)
					&& ComponentUtil.validatorTextField(parameterDescription)
					&& ComponentUtil.validatorComboBox(parameterType)) {
				String textValue = parameterActive.getValue();
				String comboValue = parameterValue.getValue();
				AppDefaultParam appDefaultParam = null;
				if(textValue!=null && !textValue.isEmpty()) {
					appDefaultParam = new AppDefaultParam(null, parameterName.getValue(),
							parameterDescription.getValue(), parameterType.getValue(), textValue);
				}else if(comboValue!=null && !comboValue.isEmpty()) {
					appDefaultParam = new AppDefaultParam(null, parameterName.getValue(),
							parameterDescription.getValue(), parameterType.getValue(), comboValue);
				}else {
					Notification.show("Value Field Cannot be Empty",Type.ERROR_MESSAGE);
				}

					try {
						AppParamFormat appParamFormat = appStoreService
								.getAppParamFormatByType(parameterType.getValue());
						appStoreService.saveAppDefaultParam(selectedApp, appDefaultParam, appParamFormat);
					} catch (NumberFormatException e1) {
						appStoreService.logApplicationStoreScreenErrors(e1);
					}
					if (selectedProfile != null) {
						appDefaultParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(
								appStoreService.getAppParamListByAppProfileId(selectedProfile.getId())));
					} else {
						appDefaultParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(
								appStoreService.getAppDefaultParamListByAppId(selectedApp.getId())));
					}
					appDefaultWindow.close();
					parameterType.setParent(null);
				}
		});
		saveParameter.setDescription("Save");
		saveParameter.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		cancelParameter = new Button("Reset", click -> {
			parameterName.clear();
			parameterActive.clear();
			parameterDescription.clear();
			parameterType.clear();
		});
		cancelParameter.setDescription("Reset");
		cancelParameter.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		HorizontalLayout buttonLayout = new HorizontalLayout(saveParameter, cancelParameter);
		buttonLayout.addStyleName("personlization-applicationSaveCancel");
		VerticalLayout profileFormLayout = new VerticalLayout(parameterName, parameterDescription,parameterType);
		profileFormLayout.addStyleName("role-gridLayout");
		
		parameterType.addSelectionListener(listener->{
			if(listener.getValue()!=null && listener.getValue().equalsIgnoreCase("boolean")) {
				parameterActive.clear();
				profileFormLayout.removeComponent(parameterActive);
				parameterValue.clear();
				profileFormLayout.addComponent(parameterValue);
			}else {
				parameterValue.clear();
				profileFormLayout.removeComponent(parameterValue);
				parameterActive.clear();
				profileFormLayout.addComponent(parameterActive);
			}
		});

		// Window Setup
		appDefaultWindow.setContent(new VerticalLayout(profileFormLayout, buttonLayout));
		appDefaultWindow.center();
		appDefaultWindow.setResizable(true);
		appDefaultWindow.setClosable(true);
		appDefaultWindow.setWidth(30, Unit.PERCENTAGE);
		return appDefaultWindow;
	}
	
	private Window openFileUploadWindow(Grid<AppDefaultParam> optionList) {
		LineBreakCounter lineBreakCounter = new LineBreakCounter();
		lineBreakCounter.setSlow(true);

		FileUploadReceiver fileUploadReceiver = new FileUploadReceiver();

		Upload uploadFile = new Upload("Upload File here", lineBreakCounter);
		uploadFile.setImmediateMode(false);
		uploadFile.setButtonCaption("Upload File");
		uploadFile.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle", "applicationStore-UploadButton");
		uploadFile.setReceiver(fileUploadReceiver);
		uploadFile.setDescription("Upload File");

		Window fileUploadWindow = new Window("File Upload", uploadFile);
		UploadInfoWindow uploadInfoWindow = new UploadInfoWindow(uploadFile, lineBreakCounter, fileUploadReceiver,
				optionList, fileUploadWindow,appStoreService);

		uploadFile.addStartedListener(event -> {
			if (uploadInfoWindow.getParent() == null) {
				UI.getCurrent().addWindow(uploadInfoWindow);
			}
			uploadInfoWindow.setClosable(false);
		});
		uploadFile.addFinishedListener(event -> uploadInfoWindow.setClosable(true));
		fileUploadWindow.addStyleName("applicationStore-UploadWindow");
		fileUploadWindow.setWidth(30, Unit.PERCENTAGE);
		fileUploadWindow.setHeight(10, Unit.PERCENTAGE);
		fileUploadWindow.setClosable(true);
		fileUploadWindow.setDraggable(true);
		fileUploadWindow.setResizable(false);
		fileUploadWindow.setModal(true);
		fileUploadWindow.center();
		return fileUploadWindow;
	}

	private static class UploadInfoWindow extends Window implements Upload.StartedListener, Upload.ProgressListener,
			Upload.FailedListener, Upload.SucceededListener, Upload.FinishedListener {
		private final Label state = new Label();
		private final Label result = new Label();
		private final Label fileName = new Label();
		private final Label textualProgress = new Label();

		private final ProgressBar progressBar = new ProgressBar();
		private final Button cancelButton;
		private final LineBreakCounter counter;
		private final FileUploadReceiver fileUploadReceiver;
		private final Window uploadWindow;
		private final ApplicationStoreService applicationStoreService;

		private final Grid<AppDefaultParam> optionList;

		private UploadInfoWindow(final Upload upload, final LineBreakCounter lineBreakCounter,
				final FileUploadReceiver fileUploadReceiver, final Grid<AppDefaultParam> optionList, Window uploadWindow,final ApplicationStoreService applicationStoreService) {
			super("Status");
			this.counter = lineBreakCounter;
			this.fileUploadReceiver = fileUploadReceiver;
			this.optionList = optionList;
			addStyleName("upload-info");
			this.uploadWindow = uploadWindow;
			this.applicationStoreService = applicationStoreService;

			setResizable(false);
			setDraggable(false);

			final FormLayout uploadInfoLayout = new FormLayout();
			setContent(uploadInfoLayout);
			uploadInfoLayout.setMargin(true);

			final HorizontalLayout stateLayout = new HorizontalLayout();
			stateLayout.setSpacing(true);
			stateLayout.addComponent(state);

			cancelButton = new Button("Cancel");
			cancelButton.addClickListener(event -> upload.interruptUpload());
			cancelButton.setVisible(false);
			cancelButton.setStyleName("small");
			stateLayout.addComponent(cancelButton);

			stateLayout.setCaption("Current state");
			state.setValue("Idle");
			uploadInfoLayout.addComponent(stateLayout);

			fileName.setCaption("File name");
			uploadInfoLayout.addComponent(fileName);

			result.setCaption("Line breaks counted");
			uploadInfoLayout.addComponent(result);

			progressBar.setCaption("Progress");
			progressBar.setVisible(false);
			uploadInfoLayout.addComponent(progressBar);

			textualProgress.setVisible(false);
			uploadInfoLayout.addComponent(textualProgress);

			upload.addStartedListener(this);
			upload.addProgressListener(this);
			upload.addFailedListener(this);
			upload.addSucceededListener(this);
			upload.addFinishedListener(this);

		}

		@Override
		public void uploadFinished(final FinishedEvent event) {
			state.setValue("Idle");
			progressBar.setVisible(false);
			textualProgress.setVisible(false);
			cancelButton.setVisible(false);
		}

		@Override
		public void uploadStarted(final StartedEvent event) {
			// this method gets called immediately after upload is started
			progressBar.setValue(0f);
			progressBar.setVisible(true);
			UI.getCurrent().setPollInterval(500);
			textualProgress.setVisible(true);
			
			// updates to client
			state.setValue("Uploading");
			fileName.setValue(event.getFilename());
			cancelButton.setVisible(true);
		}

		@Override
		public void updateProgress(final long readBytes, final long contentLength) {
			// this method gets called several times during the update
			progressBar.setValue(readBytes / (float) contentLength);
			textualProgress.setValue("Processed " + readBytes + " bytes of " + contentLength);
			result.setValue(counter.getLineBreakCount() + " (counting...)");
		}

		@Override
		public void uploadSucceeded(final SucceededEvent event) {
			result.setValue(counter.getLineBreakCount() + " (total)");
			
			if (optionList.getId().equalsIgnoreCase(FILE_CHOOSE_LIST)) {
				applicationStoreService.uploadAppFiles(ApplicationStoreView.selectedApp.getId(), "Description111", ApplicationStoreView.applicationFilePath);
				optionList.setDataProvider(new ListDataProvider<AppDefaultParam>(applicationStoreService.getAllAppFileList(ApplicationStoreView.selectedApp.getId())));
			} else {

			}
			// close the window
			close();
			uploadWindow.close();
		}

		@Override
		public void uploadFailed(final FailedEvent event) {
			result.setValue(counter.getLineBreakCount() + " (counting interrupted at "
					+ Math.round(100 * progressBar.getValue()) + "%)");
		}
	}

	private static class LineBreakCounter implements Receiver {
		private int counter;
		private int total;
		private boolean sleep;

		/**
		 * return an OutputStream that simply counts line ends
		 */
		@Override
		public OutputStream receiveUpload(final String filename, final String MIMEType) {
			counter = 0;
			total = 0;

			// Instead of linebreaker use a new receiver for getting the uploaded file.
			return new OutputStream() {
				private static final int searchedByte = '\n';

				@Override
				public void write(final int b) {
					total++;
					if (b == searchedByte) {
						counter++;
					}
					if (sleep && total % 1000 == 0) {
						try {
							Thread.sleep(100);
						} catch (final InterruptedException e) {
							e.printStackTrace();
							
						}
					}

				}
			};
		}

		private int getLineBreakCount() {
			return counter;
		}

		private void setSlow(boolean value) {
			sleep = value;
		}
	}

	private static class FileUploadReceiver implements Receiver {
		private ApplicationFile file;

		/**
		 * return an OutputStream that simply counts line ends
		 */
		@Override
		public OutputStream receiveUpload(final String filename, final String MIMEType) {
			// Create upload stream
			FileOutputStream fos = null; // Stream to write to
			try {
				
				file = new ApplicationFile(System.getProperty("java.io.tmpdir")+File.separator+filename);
				ApplicationStoreView.applicationFilePath = file.getAbsolutePath();
				fos = new FileOutputStream(file);
			} catch (final java.io.FileNotFoundException e) {
				new Notification("Could not open file: ", e.getMessage(), Notification.Type.ERROR_MESSAGE)
						.show(Page.getCurrent());
				return null;
			}
			return fos;
		}
		
	}
	
	private void clearDetailsFields() {
		packageName.clear();
		description.clear();
		packageVersion.clear();
		applicationOwner.clear();
		activeApplication.clear();
	}

}
