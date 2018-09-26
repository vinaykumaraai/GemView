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
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.luretechnologies.tms.ui.navigation.NavigationManager;
import com.luretechnologies.tms.ui.view.ContextMenuWindow;
import com.luretechnologies.tms.ui.view.Header;
import com.vaadin.annotations.JavaScript;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
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
	private ComboBox<Devices> devices;
	private CheckBox activeApplication;
	private TextField packageName;
	private Button  saveForm;
	private Button cancelForm;
	private Button fileButton, clearAppSearch, clearParamSearch;
	private TextField applicationSearch, appDefaultParamSearch;
	private HorizontalLayout appGridMenuHorizontalLayout;
	private VerticalLayout appGridMenuVerticalLayout;
	private VerticalLayout applicationListLayout;
	private HorizontalLayout appSearchLayout ;
	private ListSelect optionList;
	private TextField profileField = new TextField();
	private TextField profileName = new TextField("Name");
	private ComboBox<String> parameterType = new ComboBox<String>("Type");
	private ComboBox<Profile> profileSelect;
	private TextField parameterName = new TextField("Name");
	private TextField parameterDescription = new TextField("Description");
	private Button clearAllParams;
	private Button profileDropDown;
	private Button clearProfile;
	private Button createAppDefaultParamGridRow, deleteAppDefaultParamGridRow;
	private boolean access=false, add=false, edit=false, delete=false;
	private TextField description, packageVersion;
	private HorizontalLayout activeBoxLayout;
	private HorizontalLayout fileButtonLayout;
	private HorizontalLayout appParamSearchLayout;
	private TabSheet applicationStoreTabSheet;
	private final NavigationManager navigationManager;
	private Grid<ApplicationFile> appFileGrid;
	
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
		Panel panel = getAndLoadApplicationStorePanel();
		appStoreGridLayout = new GridLayout(2, 1, getAppStoreComponents());
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
			} else if(r.getWidth()>600 && r.getWidth()<=1000){
				applicationSearch.setHeight("32px");
				appDefaultParamSearch.setHeight("32px");
				appParamSearchLayout.setWidth("100%");
				clearAppSearch.removeStyleNames("audit-AuditSearchClearDesktop", "odometer-OdometerSearchClearPhone");
				clearAppSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
				clearParamSearch.removeStyleNames("audit-AuditSearchClearDesktop", "odometer-OdometerSearchClearPhone");
				clearParamSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
				mainView.getTitle().setValue("gemView"/*+ userService.getLoggedInUserName()*/);
			}else {
				applicationSearch.setHeight(37, Unit.PIXELS);
				appDefaultParamSearch.setHeight("37px");
				appParamSearchLayout.setWidth("98%");
				clearAppSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
				clearAppSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearDesktop");
				clearParamSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
				clearParamSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearDesktop");
				mainView.getTitle().setValue("gemView"/*+ userService.getLoggedInUserName()*/);
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
		Permission appStorePermission = roleService.getLoggedInUserRolePermissions().stream().filter(per -> per.getPageName().equals("APPSTORE")).findFirst().get();
			disableAllComponents();
		access = appStorePermission.getAccess();
		add = appStorePermission.getAdd();
		edit = appStorePermission.getEdit();
		delete = appStorePermission.getDelete();
		
		allowAccessBasedOnPermission(access, add, edit, delete);
		}catch(Exception ex) {
			appStoreService.logApplicationStoreScreenErrors(ex);
		}
			mainView.getApplicationStore().setEnabled(true);
	}

	private void allowAccessBasedOnPermission(Boolean access, Boolean add, Boolean edit, Boolean delete) {
		/*HorizontalLayout appButtons = ((HorizontalLayout)((HorizontalLayout)((VerticalLayout)appStoreGridLayout.getComponent(0, 0)).getComponent(0)).getComponent(1));
		((Button)appButtons.getComponent(0)).setEnabled(add);
		((Button)appButtons.getComponent(1)).setEnabled(edit);
		((Button)appButtons.getComponent(2)).setEnabled(delete);*/
		fileButton.setEnabled(true);
		//profileDropDown.setEnabled(true);
		saveForm.setEnabled(edit || add);
		cancelForm.setEnabled(edit || add);
	}

	private void differentModesLoad() throws ApiException {
		int width = Page.getCurrent().getBrowserWindowWidth();
		if (width <= 1300 && width >= 700) {
			phoneAndTabMode();

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
			mainView.getTitle().setValue(userService.getLoggedInUserName());
		} else if(width>600 && width<=1000){
			applicationSearch.setHeight("32px");
			appDefaultParamSearch.setHeight("32px");
			appParamSearchLayout.setWidth("100%");
			clearAppSearch.removeStyleNames("audit-AuditSearchClearDesktop", "odometer-OdometerSearchClearPhone");
			clearAppSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
			clearParamSearch.removeStyleNames("audit-AuditSearchClearDesktop", "odometer-OdometerSearchClearPhone");
			clearParamSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
			mainView.getTitle().setValue("gemView"/*+ userService.getLoggedInUserName()*/);
		}else {
			applicationSearch.setHeight(37, Unit.PIXELS);
			appDefaultParamSearch.setHeight("37px");
			appParamSearchLayout.setWidth("98%");
			clearAppSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
			clearAppSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearDesktop");
			clearParamSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
			clearParamSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearDesktop");
			mainView.getTitle().setValue("gemView"/*+ userService.getLoggedInUserName()*/);
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
		//appStoreGridLayout.getComponent(0, 1).addStyleName("applicationStore-gridLayoutHeight");
	}

	private Panel getAndLoadApplicationStorePanel() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setResponsive(true);
		panel.setSizeFull();
		addComponent(new Header(userService,navigationManager, "Application Store", new Label()));
		addComponent(panel);
		return panel;
	}
	
	private VerticalLayout getTabSheet() throws ApiException {
		VerticalLayout tabVerticalLayout = new VerticalLayout();
		applicationStoreTabSheet = new TabSheet();
		applicationStoreTabSheet.setHeight("100%");
		applicationStoreTabSheet.addStyleName("applicationStore-TabLayout");
		applicationStoreTabSheet.addStyleNames(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS, ValoTheme.TABSHEET_CENTERED_TABS,
				ValoTheme.TABSHEET_ICONS_ON_TOP, ValoTheme.TABSHEET_COMPACT_TABBAR, ValoTheme.TABSHEET_PADDED_TABBAR);
		applicationStoreTabSheet.addTab(getAppicationDetailsLayout(), "Details");
		applicationStoreTabSheet.addTab(getApplicationProfileLayout(),"Profile");
		applicationStoreTabSheet.addTab(getApplicationDefaulParametersLayout(), "Parameters");
		applicationStoreTabSheet.addTab(getApplicationFileLayout(), "Files");
		tabVerticalLayout.addComponents(buttonLayout, applicationStoreTabSheet);
		tabVerticalLayout.addStyleName("heartbeat-verticalLayout");
		tabVerticalLayout.setComponentAlignment(buttonLayout, Alignment.BOTTOM_RIGHT);
		
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

	private VerticalLayout getEmptyLayout() {
		VerticalLayout vl = new VerticalLayout();
		vl.addStyleName("applicationStore-GridLayout");
		return vl;
	}
private void disableAllComponents() throws Exception {
	/*HorizontalLayout appButtons = ((HorizontalLayout)((HorizontalLayout)((VerticalLayout)appStoreGridLayout.getComponent(0, 0)).getComponent(0)).getComponent(1));
	((Button)appButtons.getComponent(0)).setEnabled(false);
	((Button)appButtons.getComponent(1)).setEnabled(false);
	((Button)appButtons.getComponent(2)).setEnabled(false);*/
	saveForm.setEnabled(false);
	cancelForm.setEnabled(false);
}
	private VerticalLayout getApplicationListLayout() {
		appGrid = new Grid<>(AppClient.class);
		appGrid.setWidth("100%");
		appGrid.setColumns("packageName", "description", "packageVersion");
		appGrid.getColumn("packageName").setCaption("Name");
		appGrid.getColumn("description").setCaption("Description");
		appGrid.getColumn("packageVersion").setCaption("Version");
		appGrid.addColumn("available").setCaption("Available");
		appGrid.setHeightByRows(21);
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
			UI.getCurrent().getWindows().forEach(Window::close);
			setApplicationFormComponentsEnable(access, add, edit, delete);
			appGrid.deselectAll();
			packageName.focus();
			fileButton.setEnabled(false);
			//profileDropDown.setEnabled(false);
			//clearAllParams.setEnabled(false);
			applicationDetailsForm.setEnabled(true);
		});
		createAppGridRowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
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
		Button deleteAppGridRowMenu = new Button("Delete Application", clicked -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			if (appGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.APPLICATIONSTORE_DELETE, Notification.Type.ERROR_MESSAGE);
			} else {
				confirmDeleteApp(applicationSearch);
			}

		});
		deleteAppGridRowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		Button createAppGridRow = new Button(VaadinIcons.FOLDER_ADD, click -> {			
			setApplicationFormComponentsEnable(access, add, edit, delete);
			appGrid.deselectAll();
			packageName.focus();
			fileButton.setEnabled(false);
			//profileDropDown.setEnabled(false);
			//clearAllParams.setEnabled(false);
			applicationDetailsForm.setEnabled(true);
		});
		createAppGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		createAppGridRow.setResponsive(true);
		createAppGridRow.setDescription("Create App");
		Button editAppGridRow = new Button(VaadinIcons.EDIT, click -> {
			if (appGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.APPLICATIONSTORE_EDIT, Notification.Type.ERROR_MESSAGE);
			} else {
				setApplicationFormComponentsEnable(access, add, edit, delete);
				int defaultParamCount = ((VerticalLayout) appDefaultParamGrid.getParent()).getComponentCount();
				for (int i = 0; i < defaultParamCount; i++) {
					Component component = ((VerticalLayout) appDefaultParamGrid.getParent()).getComponent(i);
					component.setEnabled(true);
				}
			}

		});
		editAppGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		editAppGridRow.setResponsive(true);
		editAppGridRow.setDescription("Edit App");

		Button deleteAppGridRow = new Button(VaadinIcons.TRASH, clicked -> {
			if (appGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.APPLICATIONSTORE_DELETE, Notification.Type.ERROR_MESSAGE);
			} else {
				confirmDeleteApp(applicationSearch);
			}

		});
		deleteAppGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		deleteAppGridRow.setResponsive(true);
		deleteAppGridRow.setDescription("Delete APP");
		ContextMenuWindow appGridContextMenu = new ContextMenuWindow();
		appGridContextMenu.addMenuItems(createAppGridRowMenu,editAppGridRowMenu,deleteAppGridRowMenu);
		appGrid.addContextClickListener(click->{
			UI.getCurrent().getWindows().forEach(Window::close);
			appGridContextMenu.setPosition(click.getClientX(), click.getClientY());
			UI.getCurrent().addWindow(appGridContextMenu);
			
		});
		UI.getCurrent().addClickListener(Listener->{
			appGridContextMenu.close();
			
		});
		
		appSearchLayout = new HorizontalLayout(applicationSearchCSSLayout);
		appSearchLayout.setWidth("98%");
		appButtonsLayout = new HorizontalLayout(createAppGridRow, editAppGridRow, deleteAppGridRow);
		appGridMenuHorizontalLayout = new HorizontalLayout(appSearchLayout);
		appGridMenuHorizontalLayout.setWidth("100%");
		applicationListLayout = new VerticalLayout(appGridMenuHorizontalLayout, appGrid);
		applicationListLayout.addStyleName("applicationStore-ApplicationStoreLayout");
		appGrid.addSelectionListener(selection -> {
			if (selection.getFirstSelectedItem().isPresent()) {
				selectedApp = selection.getFirstSelectedItem().get();
				((TextField) applicationDetailsForm.getComponent(0)).setValue(selectedApp.getPackageName());
				((TextField) applicationDetailsForm.getComponent(1)).setValue(selectedApp.getDescription());
				((TextField) applicationDetailsForm.getComponent(2)).setValue(selectedApp.getPackageVersion());
				((ComboBox) applicationDetailsForm.getComponent(3)).setValue(selectedApp.getOwner());
				HorizontalLayout HL = (HorizontalLayout) applicationDetailsForm.getComponent(4);
				CheckBox checkbox = (CheckBox) HL.getComponent(1);
				checkbox.setValue(selectedApp.isAvailable());
				profileSelect.clear();
				selectedProfile=null;
				appDefaultParamGrid.setEnabled(true);
//					appDefaultParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(appStoreService.getAppDefaultParamListByAppId(selectedApp.getId())));
				profileSelect.setDataProvider(appStoreService.getAppProfileListDataProvider(selectedApp.getId()));
				appDefaultParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(appStoreService.getAppDefaultParamListByAppId(selectedApp.getId())));
				appProfileGrid.setDataProvider(appStoreService.getAppProfileListDataProvider(selectedApp.getId()));
				appFileGrid.setDataProvider(new ListDataProvider<>(appStoreService.getAllAppFileList(selectedApp.getId())));
					parameterType.setDataProvider(new ListDataProvider<>(appStoreService.getAppParamTypeList(selectedApp.getId())));
					if(profileField.getValue()!=null && profileField.getValue()!="") {
						profileField.clear();
					}
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
				/*((TextField) ((HorizontalLayout) ((VerticalLayout) appDefaultParamGrid.getParent()).getComponent(1))
						.getComponent(2)).clear();*/
				setApplicationFormComponentsEnable(false, false, false, false);
			}
		});
		return applicationListLayout;
	}

	private void setApplicationFormComponentsEnable(boolean access, boolean add, boolean edit, boolean delete) {
		
			fileButtonLayout.setEnabled(access);
			//profileDropDown.setEnabled(access);
			applicationDetailsForm.getComponent(4).setEnabled(access);
		
			if(add) {
			createAppDefaultParamGridRow.setEnabled(true);
			packageName.setEnabled(true);
			description.setEnabled(true);
			packageVersion.setEnabled(true);
			applicationOwner.setEnabled(true);
			activeBoxLayout.setEnabled(true);
			activeApplication.setEnabled(true);
			fileButton.setEnabled(true);
			deleteAppDefaultParamGridRow.setEnabled(false);
			//clearAllParams.setEnabled(false);
			createAppDefaultParamGridRow.setEnabled(true);
			
		}else {
			packageName.setEnabled(false);
			description.setEnabled(false);
			packageVersion.setEnabled(false);
			applicationOwner.setEnabled(false);
			activeBoxLayout.setEnabled(false);
			activeApplication.setEnabled(false);
			deleteAppDefaultParamGridRow.setEnabled(false);
			//clearAllParams.setEnabled(false);
			fileButton.setEnabled(true);
		}
			
			if(delete) {
				//clearAllParams.setEnabled(true);
				deleteAppDefaultParamGridRow.setEnabled(true);
				createAppDefaultParamGridRow.setEnabled(false);
			}
			

			if(edit) {
				applicationDetailsForm.setEnabled(true);
				packageName.setEnabled(true);
				description.setEnabled(true);
				packageVersion.setEnabled(true);
				applicationOwner.setEnabled(true);
				activeBoxLayout.setEnabled(true);
				activeApplication.setEnabled(true);
				//clearAllParams.setEnabled(false);
				deleteAppDefaultParamGridRow.setEnabled(false);
				createAppDefaultParamGridRow.setEnabled(false);
			}
			
			if(add && edit) {
				createAppDefaultParamGridRow.setEnabled(true);
			}
			
			if(delete && edit) {
				deleteAppDefaultParamGridRow.setEnabled(true);
				//clearAllParams.setEnabled(true);
			}
			
			if(delete && add) {
				createAppDefaultParamGridRow.setEnabled(true);
			}
	}
	
	private void setApplicationFormComponentsEnableForAPPClick(boolean access, boolean add, boolean edit, boolean delete) {
		
			fileButtonLayout.setEnabled(access);
			//profileDropDown.setEnabled(access);
			applicationDetailsForm.getComponent(4).setEnabled(access);
			//appDefaultParamGrid.setEnabled(false);
		
			if(add) {
			createAppDefaultParamGridRow.setEnabled(true);
			packageName.setEnabled(false);
			description.setEnabled(false);
			packageVersion.setEnabled(false);
			applicationOwner.setEnabled(false);
			activeBoxLayout.setEnabled(false);
			activeApplication.setEnabled(false);
			deleteAppDefaultParamGridRow.setEnabled(false);
			//clearAllParams.setEnabled(false);
			fileButton.setEnabled(true);
			//deleteAppDefaultParamGridRow.setEnabled(false);
			//clearAllParams.setEnabled(false);
			createAppDefaultParamGridRow.setEnabled(true);
			}
			
			if(delete) {
				//clearAllParams.setEnabled(true);
				deleteAppDefaultParamGridRow.setEnabled(true);
				createAppDefaultParamGridRow.setEnabled(false);
			}
			

			if(edit) {
				applicationDetailsForm.setEnabled(false);
				createAppDefaultParamGridRow.setEnabled(true);
				packageName.setEnabled(false);
				description.setEnabled(false);
				packageVersion.setEnabled(false);
				applicationOwner.setEnabled(false);
				activeBoxLayout.setEnabled(false);
				activeApplication.setEnabled(false);
				//clearAllParams.setEnabled(false);
				deleteAppDefaultParamGridRow.setEnabled(false);
				createAppDefaultParamGridRow.setEnabled(false);
			}
			
			if(add && edit) {
				createAppDefaultParamGridRow.setEnabled(true);
			}
			
			if(delete && edit) {
				deleteAppDefaultParamGridRow.setEnabled(true);
				//clearAllParams.setEnabled(true);
			}
			
			if(delete && add) {
				createAppDefaultParamGridRow.setEnabled(true);
			}
	}
	
	private VerticalLayout getAppicationDetailsLayout() throws ApiException {

		packageName = new TextField("Application Package Name");
		packageName.addStyleNames("v-textfield-font", "textfiled-height");
		packageName.setMaxLength(50);
		packageName.setCaptionAsHtml(true);
		packageName.setEnabled(false);
		packageName.setWidth("96%");
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
		packageVersion.addValueChangeListener(listener->{
			if(listener.getValue().length()==50) {
				Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});
		applicationOwner = new ComboBox<TreeNode>("Application Owner");
		applicationOwner.setEnabled(false);
		applicationOwner.setCaptionAsHtml(true);
		applicationOwner.setWidth("96%");
		applicationOwner.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "textfiled-height");
		applicationOwner.setDataProvider(new ListDataProvider<>(appStoreService.getOwnerList()));
		devices = new ComboBox<Devices>("Device");
		devices.setEnabled(false);
		devices.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size");
		
		activeBoxLayout = new HorizontalLayout();
		activeBoxLayout.setId(ACTIVE_LAYOUT);
		Label active = new Label("");
		active.setStyleName("role-activeLable");
		active.addStyleNames("v-textfield-font");
		activeApplication = new CheckBox("Application Available", false);
		activeApplication.setEnabled(false);
		activeApplication.addStyleNames("v-textfield-font");
		activeBoxLayout.addComponents(active, activeApplication);
		activeBoxLayout.setStyleName("role-activeLable");
		activeBoxLayout.addStyleNames("applicationStore-activeCheckBox", "asset-debugComboBox");
		saveForm = new Button("Save");
		saveForm.addClickListener(click -> {
			if(!(ComponentUtil.validatorTextField(packageName) && ComponentUtil.validatorTextField(description) && 
					ComponentUtil.validatorTextField(packageVersion) && ComponentUtil.validatorComboBox(applicationOwner))) {
				//Notification.show(NotificationUtil.SAVE, Type.ERROR_MESSAGE);
			}
			else {
				AppClient app;
			if (appGrid.getSelectedItems().size() > 0) {
			app = appGrid.getSelectedItems().iterator().next();
			app.setPackageName(packageName.getValue());
			app.setDescription(description.getValue());
			app.setPackageVersion(packageVersion.getValue());
			app.setOwner(applicationOwner.getValue());
			app.setDevice(devices.getValue());
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
				devices.clear();
				activeApplication.clear();
			} else {
				app = new AppClient();
				app.setActive(true);
				app.setId(null);
				app.setPackageName(packageName.getValue());
				app.setDescription(description.getValue());
				app.setPackageVersion(packageVersion.getValue());
				app.setOwner(applicationOwner.getValue());
				app.setDevice(devices.getValue());
				app.setAvailable(activeApplication.getValue());
				app.setAppDefaultParamList(
						appDefaultParamGrid.getDataProvider().fetch(new Query<>()).collect(Collectors.toList()));
				app.setProfile(new ArrayList());
				appStoreService.saveApp(app);
				appGrid.setDataProvider(appStoreService.getAppListDataProvider());
				appGrid.getDataProvider().refreshAll();
				appGrid.deselectAll();
				packageName.clear();
				description.clear();
				packageVersion.clear();
				applicationOwner.clear();
				devices.clear();
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
			devices.clear();
			activeApplication.clear();
			applicationDetailsForm.setEnabled(false);
			
			setApplicationFormComponentsEnable(access, false, false, false);
			
			appDefaultParamGrid.setDataProvider(new ListDataProvider<>(Arrays.asList()));
			((TextField) ((HorizontalLayout) ((VerticalLayout) appDefaultParamGrid.getParent()).getComponent(1))
					.getComponent(2)).clear();
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
				 activeBoxLayout);//fileButtonLayout);
		//applicationDetailsForm.addStyleNames("applicationStore-FormLayout", "system-LabelAlignment");
		applicationDetailsForm.addStyleNames("heartbeat-verticalDetailLayout");
		applicationDetailsForm.setCaptionAsHtml(true);
		applicationDetailsLabel = new HorizontalLayout();
		applicationDetailsLabel.setWidth("100%");
		/*Label appDetailsLabel = new Label("Application Details");
		appDetailsLabel.addStyleName("label-style");
		appDetailsLabel.addStyleNames(ValoTheme.LABEL_BOLD, ValoTheme.LABEL_H3);
		applicationDetailsLabel.addComponent(appDetailsLabel);*/
		buttonLayout = new HorizontalLayout();
		buttonLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		buttonLayout.addStyleName("applicationStore-buttonsLayout");
		buttonLayout.addComponents(cancelForm, saveForm);
		buttonLayout.setResponsive(true);

	/*	appDetailsSaveCancleAndLabelLayout.addComponents(applicationDetailsLabel, buttonLayout);
		appDetailsSaveCancleAndLabelLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_RIGHT);*/
		VerticalLayout applicationDetailsLayout = new VerticalLayout(
				applicationDetailsForm);
		applicationDetailsLayout.addStyleNames("applicationStore-ApplicationDetailsLayout");
		//applicationDetailsLayout.setComponentAlignment(appDetailsSaveCancleAndLabelLayout, Alignment.TOP_RIGHT);
		return applicationDetailsLayout;
	}
	
	private VerticalLayout getApplicationFileLayout() {
		
		fileButton = new Button("Files", VaadinIcons.UPLOAD);
		fileButton.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY);
		fileButton.setEnabled(false);
		fileButton.setDescription("Upload Files");
		fileButton.addClickListener(click -> {
			if(appGrid.getSelectedItems().isEmpty())
				Notification.show("Select a app first",Type.WARNING_MESSAGE);
			else {
			Window fileUpload = openFileUploadWindow(appFileGrid);
			if (fileUpload.getParent() == null) {
				UI.getCurrent().addWindow(fileUpload);
			} else if (fileUpload.getComponentCount() > 0) {
				fileUpload.close();
			}
			}
		});
		fileButtonLayout = new HorizontalLayout();
		fileButtonLayout.setCaption("Upload Files");
		fileButtonLayout.addStyleName("asset-debugComboBox");
		fileButtonLayout.addComponent(fileButton);
		
		appFileGrid = new Grid<>(ApplicationFile.class);
		appFileGrid.addStyleName("applicationStore-horizontalAlignment");
		appFileGrid.setColumns("name", "description");
		appFileGrid.setWidth("100%");
		appFileGrid.setHeightByRows(17);
		appFileGrid.setId(FILE_CHOOSE_LIST);
		Button deleteAppFileGridRowMenu = new Button("Delete File", clicked -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			if (appGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.APPLICATIONSTORE_DELETE, Notification.Type.ERROR_MESSAGE);
			} else {
//				confirmDeleteApp(applicationSearch); put file delete confirmation code
			}

		});
		deleteAppFileGridRowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		ContextMenuWindow paramContextWindow = new ContextMenuWindow();
		paramContextWindow.addMenuItems(deleteAppFileGridRowMenu);
		appFileGrid.addContextClickListener(click ->{
			UI.getCurrent().getWindows().forEach(Window::close);
			paramContextWindow.setPosition(click.getClientX(), click.getClientY());
			UI.getCurrent().addWindow(paramContextWindow);
		});
		return new VerticalLayout(fileButtonLayout,appFileGrid);
	}

	private VerticalLayout getApplicationDefaulParametersLayout() throws ApiException {
		appDefaultParamGrid = new Grid<>(AppDefaultParam.class);
		appDefaultParamGrid.addStyleName("applicationStore-horizontalAlignment");
		appDefaultParamGrid.setColumns("parameter", "description", "type", "value");
		appDefaultParamGrid.setWidth("100%");
		appDefaultParamGrid.setHeightByRows(18);
		appDefaultParamGrid.getColumn("parameter").setEditorComponent(new TextField());
		appDefaultParamGrid.getColumn("description").setEditorComponent(new TextField());
		appDefaultParamGrid.getColumn("type").setEditorComponent(parameterType);
		appDefaultParamGrid.getColumn("value").setEditorComponent(new TextField());
		appDefaultParamGrid.setSelectionMode(SelectionMode.MULTI);

		appDefaultParamGrid.addItemClickListener(event->{
			if(appDefaultParamGrid.getEditor().isEnabled()) {
				appDefaultParamGrid.getColumn("parameter").setEditable(false);
				appDefaultParamGrid.getColumn("description").setEditable(false);
				parameterType.setEnabled(false);
			}
		});
		
		appDefaultParamGrid.getEditor().addSaveListener(save -> {
			if(selectedProfile.getId()!=null) {
					AppParamFormat appParamFormat = appStoreService.getAppParamFormatByType(parameterType.getValue());
					appStoreService.updateAppParamOfAppProfile(selectedProfile,  save.getBean(), appParamFormat);
			}

		});
		
		ContextMenuWindow paramContextWindow = new ContextMenuWindow();
		
		Button createAppDefaultParamGridRowMenu = new Button("Create DefaultParams",click->{
			UI.getCurrent().getWindows().forEach(Window::close);
			try {
				Window createParamGridWindow = openAppDefaultParamWindow(appDefaultParamGrid);
				if(appGrid.getSelectedItems().size()==0) {
					Notification.show(NotificationUtil.APPLICATIONSTORE_PARAM_ADD, Type.ERROR_MESSAGE);
				}else {
				appDefaultParamGrid.deselectAll();
				if (createParamGridWindow.getParent() == null)
					UI.getCurrent().addWindow(createParamGridWindow);
				}
				paramContextWindow.close();
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				appStoreService.logApplicationStoreScreenErrors(e);
			}
		});
		createAppDefaultParamGridRowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		Button editAppProfileGridRowMenu = new Button("Edit Application",click->{
			UI.getCurrent().getWindows().forEach(Window::close);
			if (profileSelect.getSelectedItem().toString().isEmpty()) {
				Notification.show(NotificationUtil.APPLICATIONSTORE_EDIT_PARAM, Notification.Type.ERROR_MESSAGE);
			} else {
				appDefaultParamGrid.getEditor().addSaveListener(save -> {
					if(selectedProfile.getId()!=null) {
							AppParamFormat appParamFormat = appStoreService.getAppParamFormatByType(parameterType.getValue());
							appStoreService.updateAppParamOfAppProfile(selectedProfile,  save.getBean(), appParamFormat);
					}

				});
			}
			paramContextWindow.close();
		});
		editAppProfileGridRowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		editAppProfileGridRowMenu.setEnabled(false);
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
		deleteAppDefautParamGridRowMenu.setEnabled(false);
		paramContextWindow.addMenuItems(createAppDefaultParamGridRowMenu,deleteAppDefautParamGridRowMenu);
		
		
		appDefaultParamGrid.addContextClickListener(click->{
			UI.getCurrent().getWindows().forEach(Window::close);
			paramContextWindow.setPosition(click.getClientX(), click.getClientY());
			
			if(appDefaultParamGrid.getSelectedItems().size()==1) {
				deleteAppDefautParamGridRowMenu.setEnabled(true);
				editAppProfileGridRowMenu.setEnabled(true);
				UI.getCurrent().addWindow(paramContextWindow);
			}else if(appDefaultParamGrid.getSelectedItems().size()>1) {
				deleteAppDefautParamGridRowMenu.setEnabled(true);
				editAppProfileGridRowMenu.setEnabled(false);
				UI.getCurrent().addWindow(paramContextWindow);
			}else {
				editAppProfileGridRowMenu.setEnabled(false);
				deleteAppDefautParamGridRowMenu.setEnabled(false);
				UI.getCurrent().addWindow(paramContextWindow);
			}
			
		});
		
		UI.getCurrent().addClickListener(listener->{
			paramContextWindow.close();
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
			DataProvider data = new ListDataProvider(appDefaultParamList);
			appDefaultParamGrid.setDataProvider(data);
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
		profileSelect.setPlaceholder("Select Profile");
		profileSelect.addSelectionListener(select ->{
			appDefaultParamGrid.setEnabled(true);
			if(select.getValue()!=null) {
				appDefaultParamGrid.getEditor().setEnabled(true);
				selectedProfile = select.getValue();
				appDefaultParamGrid.setDataProvider(new ListDataProvider<>(appStoreService.getAppParamListByAppProfileId(select.getValue().getId())));
			}else {
				appDefaultParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(appStoreService.getAppDefaultParamListByAppId(selectedApp.getId())));
			}
		});
		HorizontalLayout parameterLoadLayout = new HorizontalLayout(profileSelect,parameterSearchCSSLayout);
		parameterLoadLayout.setWidth("100%");
		parameterLoadLayout.setExpandRatio(parameterSearchCSSLayout, 3);
		createAppDefaultParamGridRow = new Button(VaadinIcons.FOLDER_ADD, click -> {
			try {
				Window createParamGridWindow = openAppDefaultParamWindow(appDefaultParamGrid);
				if(appGrid.getSelectedItems().size()==0) {
					Notification.show(NotificationUtil.APPLICATIONSTORE_PARAM_ADD, Type.ERROR_MESSAGE);
				}else {
				appDefaultParamGrid.deselectAll();
				if (createParamGridWindow.getParent() == null)
					UI.getCurrent().addWindow(createParamGridWindow);
				}
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				appStoreService.logApplicationStoreScreenErrors(e);
			}

		});
		createAppDefaultParamGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		createAppDefaultParamGridRow.setResponsive(true);
		createAppDefaultParamGridRow.setEnabled(false);
		createAppDefaultParamGridRow.setDescription("Create Parameters");

			deleteAppDefaultParamGridRow = new Button(VaadinIcons.TRASH, clicked -> {
			if(appGrid.getSelectedItems().size()==0) {
				Notification.show(NotificationUtil.APPLICATIONSTORE_PARAM_DELETE_APPGRID, Type.ERROR_MESSAGE);
			}else {
			if (appDefaultParamGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.APPLICATIONSTORE_PARAM_DELETE_PARAMGRID, Type.ERROR_MESSAGE);
			}else if(selectedProfile!=null) {
				confirmDeleteAppProfileParam(appDefaultParamGrid.getSelectedItems().iterator().next().getId(), selectedProfile.getId(), appDefaultParamSearch);
			} else {
				confirmDeleteAppDefaultParam(appDefaultParamGrid.getSelectedItems().iterator().next().getId(), selectedApp.getId(), appDefaultParamSearch);
			}
			}
		});
		deleteAppDefaultParamGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		deleteAppDefaultParamGridRow.setResponsive(true);
		deleteAppDefaultParamGridRow.setEnabled(delete);
		deleteAppDefaultParamGridRow.setDescription("Delete Parameters");
		

		appParamSearchLayout = new HorizontalLayout(parameterLoadLayout);
		HorizontalLayout appParamGridMenuLayout = new HorizontalLayout(appParamSearchLayout);
		appParamGridMenuLayout.setWidth("100%");
		appParamGridMenuLayout.addStyleName("applicationStore-horizontalAlignment");

		applicationParamLabelLayout = new HorizontalLayout();
		applicationParamLabelLayout.setWidth("100%");
		Label appParamLabel = new Label("Application Default Parameters");
		appParamLabel.addStyleName("label-style");
		appParamLabel.addStyleNames(ValoTheme.LABEL_BOLD, ValoTheme.LABEL_H3);
		applicationParamLabelLayout.addComponents(appParamLabel);
		appParamGridMenuLayout.setEnabled(true);
		/*appDefaultParamGrid.setEnabled(false);*/
		VerticalLayout applicationDefaultParametersLayout = new VerticalLayout(applicationParamLabelLayout, appParamGridMenuLayout, appDefaultParamGrid);
		applicationDefaultParametersLayout.setCaptionAsHtml(true);
		applicationDefaultParametersLayout.addStyleName("applicationStore-VerticalLayout");
		return applicationDefaultParametersLayout;
	}
private HorizontalLayout getApplicationProfileLayout() {

	appProfileGrid = new Grid<>(Profile.class);
	appProfileGrid.addStyleName("applicationStore-horizontalAlignment");
	appProfileGrid.setColumns("name");
	appProfileGrid.setWidth("98%");
	appProfileGrid.setHeightByRows(20);
	TextField appProfile = new TextField();
	appProfile.setMaxLength(50);
	appProfileGrid.getColumn("name").setEditorComponent(appProfile);
	appProfileGrid.getColumn("name").setCaption("Profile Name");
	//appProfileGrid.getColumn("active").setEditorComponent(new CheckBox());
	appProfileGrid.getEditor().setEnabled(true);
	appProfileGrid.setSelectionMode(SelectionMode.MULTI);
	
	ContextMenuWindow profileContextWindow = new ContextMenuWindow();
	
	Button createAppProfileGridRowMenu = new Button("Add Profile",click->{
		UI.getCurrent().getWindows().forEach(Window::close);
		if (appGrid.getSelectedItems().size() > 0) {
			Window openProfileWindow = openProfileWindow(false);
			if (openProfileWindow.getParent() == null) {
				UI.getCurrent().addWindow(openProfileWindow);
				profileContextWindow.close();
			}
			}else {
				Notification.show(NotificationUtil.APPLICATIONSTORE_PROFILE_DROPDOWN_CHECK, Type.ERROR_MESSAGE);
			}
	});
	createAppProfileGridRowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
//	Button editAppProfileGridRowMenu = new Button("Edit Application",click->{
//		UI.getCurrent().getWindows().forEach(Window::close);
//		if (appProfileGrid.getSelectedItems().isEmpty()) {
//			Notification.show(NotificationUtil.APPLICATIONSTORE_EDIT, Notification.Type.ERROR_MESSAGE);
//		} else {
//			appProfileGrid.getEditor().editRow();
//		}
//	});
//	editAppProfileGridRowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
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
	deleteAppProfileGridRowMenu.setEnabled(false);
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
	editAppProfileGridRowMenu.setEnabled(false);
	
	profileContextWindow.addMenuItems(createAppProfileGridRowMenu,editAppProfileGridRowMenu,deleteAppProfileGridRowMenu);
	
	
	appProfileGrid.addContextClickListener(click -> {
		UI.getCurrent().getWindows().forEach(Window::close);
		profileContextWindow.setPosition(click.getClientX(), click.getClientY());
		if(appProfileGrid.getSelectedItems().size()==1) {
			deleteAppProfileGridRowMenu.setEnabled(true);
			editAppProfileGridRowMenu.setEnabled(true);
			UI.getCurrent().addWindow(profileContextWindow);
		}else if(appProfileGrid.getSelectedItems().size()>1) {
			deleteAppProfileGridRowMenu.setEnabled(true);
			editAppProfileGridRowMenu.setEnabled(false);
			UI.getCurrent().addWindow(profileContextWindow);
		}else {
			editAppProfileGridRowMenu.setEnabled(false);
			deleteAppProfileGridRowMenu.setEnabled(false);
			UI.getCurrent().addWindow(profileContextWindow);
		}
		
	});
	
	UI.getCurrent().addClickListener(Listener->{
		profileContextWindow.close();
		
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
	
	private void confirmDeleteAppProfile() {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?", "Ok", "Cancel",
				new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							for(Profile appProfile : appProfileGrid.getSelectedItems()) {
							appStoreService.removeAppProfile(selectedApp.getId(), appProfile.getId());
							}
							appProfileGrid.setDataProvider(appStoreService.getAppProfileListDataProvider(selectedApp.getId()));
						} else {
							// User did not confirm

						}
					}
				});
	}

	private Window getSmallListWindow(boolean isFileChoose, TextField field) throws ApiException {

		optionList = new ListSelect<>();
		optionList.setRows(3);
		optionList.setResponsive(true);
		optionList.setWidth(100, Unit.PERCENTAGE);
		Button addNewButton, deleteButton;
		if (isFileChoose) {
			if(selectedProfile!=null) {
				if (appGrid.getSelectedItems().size() > 0) {
					optionList.setDataProvider(new ListDataProvider<AppDefaultParam>(appStoreService.getAppFileListByAppProfileId(selectedProfile.getId())));
					optionList.setId(FILE_CHOOSE_LIST);
				}
			}else {
				if (appGrid.getSelectedItems().size() > 0) {
					optionList.setDataProvider(new ListDataProvider<ApplicationFile>(appStoreService.getAllAppFileList(appGrid.getSelectedItems().iterator().next().getId())));
					optionList.setId(FILE_CHOOSE_LIST);
				}
			}
			

			addNewButton = new Button("Add New File", click -> {
				/*Window fileUpload = openFileUploadWindow(optionList);
				if (fileUpload.getParent() == null) {
					UI.getCurrent().addWindow(fileUpload);
				} else if (fileUpload.getComponentCount() > 0) {
					fileUpload.close();
				}*/
			});
			addNewButton.setDescription("Add New File");
			
			if(add) {
				addNewButton.setEnabled(add);
			}else {
				addNewButton.setEnabled(false);
			}
			deleteButton = new Button("Delete File", click -> {
				if (optionList.getSelectedItems().size() == 1) {
					if(selectedProfile!=null) {
						AppDefaultParam fileParam = (AppDefaultParam) optionList.getSelectedItems().iterator().next();
							appStoreService.deleteAppProfileFiles(selectedProfile.getId(), fileParam.getId());
							optionList.setDataProvider(new ListDataProvider<AppDefaultParam>(appStoreService.getAppFileListByAppProfileId(selectedProfile.getId())));
						
					}else {
						ApplicationFile file = (ApplicationFile) optionList.getSelectedItems().iterator().next();
						appStoreService.deleteAppFiles(appGrid.getSelectedItems().iterator().next().getId(), file.getId());
						optionList.setDataProvider(new ListDataProvider<ApplicationFile>(appStoreService.getAllAppFileList(appGrid.getSelectedItems().iterator().next().getId())));
					}
				}
			});
			deleteButton.setDescription("Delete File");
			if(delete) {
				deleteButton.setEnabled(delete);
			}else {
				deleteButton.setEnabled(false);
			}
			
		} else {
		
			optionList.setDataProvider(appStoreService.getAppProfileListDataProvider(selectedApp.getId()));
			optionList.addSelectionListener(selection -> {
				if (selection.getFirstSelectedItem().isPresent()) {
					if (field != null) {
						String value = selection.getValue().toString();
						field.setValue(value.substring(1, value.length() - 1));
						clearProfile.setEnabled(true);
					}
					if (selection.getFirstSelectedItem().get() instanceof Profile) {
						selectedProfile = (Profile) selection.getFirstSelectedItem().get();
						
						appDefaultParamGrid.getEditor().setEnabled(true);
							//clearAllParams.setEnabled(false);
							appDefaultParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(appStoreService.getAppParamListByAppProfileId(selectedProfile.getId())));
					}
				}
			});
			
			
			Window createProfileWindow = openProfileWindow(optionList);
			createProfileWindow.setCaption("Create Profile");
			addNewButton = new Button("Add New Profile", click -> {
				profileName.clear();
				if (createProfileWindow.getParent() == null)
					UI.getCurrent().addWindow(createProfileWindow);

			});
			addNewButton.setDescription("Add New Profile");
			if(add) {
				addNewButton.setEnabled(add);
			}else {
				addNewButton.setEnabled(false);
			}
			deleteButton = new Button("Delete Profile", click -> {
				if (optionList.getSelectedItems().size() == 1) {
					Profile selected = (Profile) optionList.getSelectedItems().iterator().next();
					appStoreService.removeAppProfile(selectedApp.getId(), selected.getId());
						optionList.setDataProvider(appStoreService.getAppProfileListDataProvider(selectedApp.getId()));
					field.clear();

				}
			});
			deleteButton.setDescription("Delete Profile");;
			if(delete) {
				deleteButton.setEnabled(delete);
			}else {
				deleteButton.setEnabled(false);
			}
		}
		addNewButton.addStyleNames(ValoTheme.BUTTON_SMALL, "v-button-customstyle", ValoTheme.BUTTON_FRIENDLY);
		deleteButton.addStyleNames(ValoTheme.BUTTON_SMALL, "v-button-customstyle", ValoTheme.BUTTON_FRIENDLY);

		VerticalLayout listLayout = new VerticalLayout(optionList, addNewButton, deleteButton);
		listLayout.addStyleName("applicationStore-ListLayout");
		Window smaillListWindow = new Window("", listLayout);
		smaillListWindow.setWidth(250, Unit.PIXELS);
		smaillListWindow.setHeight(220, Unit.PIXELS);
		smaillListWindow.setClosable(true);
		smaillListWindow.setDraggable(true);
		smaillListWindow.setModal(true);
		smaillListWindow.setResizable(true);
		smaillListWindow.center();
		return smaillListWindow;
	}
	
	private Window openProfileWindow(ListSelect optionList) {
		Window profileWindow = new Window("Add Profile");
		profileName.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS, "role-textbox","v-textfield-font", "v-grid-cell");

		Button saveProfile = new Button("Save", click -> {
			if (StringUtils.isNotEmpty(profileName.getValue())) {
				Profile pToSave = new Profile(profileName.getValue());
				appStoreService.saveAppProfile(selectedApp, pToSave);
				
					optionList.setDataProvider(new ListDataProvider<>(appStoreService.getAppProfileListByAppId(selectedApp.getId())));
				profileWindow.close();
			}else {
				Notification.show(NotificationUtil.PROFILE_SAVE, Type.ERROR_MESSAGE);
			}
		});
		saveProfile.setDescription("Save");
		saveProfile.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		Button cancelProfile = new Button("Reset", click -> {
			profileName.clear();
		});
		cancelProfile.setDescription("Reset");
		cancelProfile.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		HorizontalLayout buttonLayout = new HorizontalLayout(saveProfile, cancelProfile);
		FormLayout profileFormLayout = new FormLayout(profileName);

		// Window Setup
		profileWindow.setContent(new VerticalLayout(profileFormLayout, buttonLayout));
		profileWindow.center();
		profileWindow.setModal(true);
		profileWindow.setClosable(true);
		profileWindow.setWidth(30, Unit.PERCENTAGE);
		return profileWindow;
	}
	private Window openProfileWindow(boolean editOnly) {
		Window profileWindow = new Window("Add Profile");
		profileName.addStyleNames("v-textfield-font", "textfiled-height");
		
		if(editOnly) {
			String value = appProfileGrid.getSelectedItems().iterator().next().toString();
			profileName.setValue(value);
		}else {
			profileName.clear();
		}
		
		Button saveProfile = new Button("Save", click -> {
			if (StringUtils.isNotEmpty(profileName.getValue())) {
				Profile pToSave = new Profile(profileName.getValue());
				appStoreService.saveAppProfile(selectedApp, pToSave);
				
				appProfileGrid.setDataProvider(appStoreService.getAppProfileListDataProvider(selectedApp.getId()));
				profileWindow.close();
			}else {
				Notification.show(NotificationUtil.PROFILE_SAVE, Type.ERROR_MESSAGE);
			}
		});
		saveProfile.setDescription("Save");
		saveProfile.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		Button cancelProfile = new Button("Reset", click -> {
			profileName.clear();
		});
		cancelProfile.setDescription("Reset");
		cancelProfile.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		HorizontalLayout buttonLayout = new HorizontalLayout(saveProfile, cancelProfile);
		FormLayout profileFormLayout = new FormLayout(profileName);

		// Window Setup
		profileWindow.setContent(new VerticalLayout(profileFormLayout, buttonLayout));
		profileWindow.center();
		profileWindow.setModal(true);
		profileWindow.setClosable(true);
		profileWindow.setWidth(30, Unit.PERCENTAGE);
		return profileWindow;
	}


	private Window openAppDefaultParamWindow(Grid<AppDefaultParam> appDefaultParamGrid) throws ApiException {
		Window appDefaultWindow = new Window("Add Default Parameter");
		parameterName.setEnabled(true);
		parameterName.addStyleNames("textfiled-height","v-textfield-font");
		parameterName.setWidth("95%");
		parameterName.setMaxLength(50);
		parameterName.addValueChangeListener(listener->{
			if(listener.getValue().length()==50) {
				Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});
		
		parameterDescription.setEnabled(true);
		parameterDescription.addStyleNames("textfiled-height","v-textfield-font");
		parameterDescription.setWidth("95%");
		parameterDescription.setMaxLength(50);
		parameterDescription.addValueChangeListener(listener->{
			if(listener.getValue().length()==50) {
				Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});
		
		
		parameterType.setEnabled(true);
		parameterType.setCaptionAsHtml(true);
		parameterType.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "textfiled-height");
		parameterType.setWidth("95%");
		
		
		TextField parameterActive = new TextField("Value");
		parameterActive.addStyleNames("textfiled-height","v-textfield-font");
		parameterActive.setWidth("95%");
		parameterActive.setMaxLength(50);
		parameterActive.addValueChangeListener(listener->{
			if(listener.getValue().length()==50) {
				Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});
		
		ComboBox<String> parameterValue = new ComboBox<String>("Value");
		parameterValue.setEnabled(true);
		parameterValue.setCaptionAsHtml(true);
		parameterValue.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "textfiled-height");
		parameterValue.setDataProvider(new ListDataProvider<>(Arrays.asList("True", "False")));
		parameterValue.setWidth("95%");
		
		//Clear Previously Entered Values
		parameterName.clear();
		parameterDescription.clear();
		parameterType.clear();
		
		Button saveParameter = new Button("Save", click -> {
			if (ComponentUtil.validatorTextField(parameterName)
					&& ComponentUtil.validatorTextField(parameterDescription)
					&& ComponentUtil.validatorComboBox(parameterType)) {
				boolean textValue = parameterActive.getValue()!=null ? true : false;
				boolean comboValue = parameterValue.getValue()!=null ? true : false;
					AppDefaultParam appDefaultParam = new AppDefaultParam(null, parameterName.getValue(),
							parameterDescription.getValue(), parameterType.getValue(), parameterActive.getValue());

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
		Button cancelParameter = new Button("Reset", click -> {
			parameterName.clear();
			parameterActive.clear();
			parameterDescription.clear();
			parameterType.clear();
		});
		cancelParameter.setDescription("Reset");
		cancelParameter.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		HorizontalLayout buttonLayout = new HorizontalLayout(saveParameter, cancelParameter);
		FormLayout profileFormLayout = new FormLayout(parameterName, parameterDescription,parameterType);
		
		parameterType.addSelectionListener(listener->{
			if(listener.getValue().equalsIgnoreCase("boolean")) {
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
		appDefaultWindow.setModal(true);
		appDefaultWindow.setClosable(true);
		appDefaultWindow.setWidth(30, Unit.PERCENTAGE);
		return appDefaultWindow;
	}
	
	private Window openFileUploadWindow(Grid<ApplicationFile> optionList) {
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

		private final Grid<ApplicationFile> optionList;

		private UploadInfoWindow(final Upload upload, final LineBreakCounter lineBreakCounter,
				final FileUploadReceiver fileUploadReceiver, final Grid<ApplicationFile> optionList, Window uploadWindow,final ApplicationStoreService applicationStoreService) {
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
				optionList.setDataProvider(new ListDataProvider<ApplicationFile>(applicationStoreService.getAllAppFileList(ApplicationStoreView.selectedApp.getId())));
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
}
