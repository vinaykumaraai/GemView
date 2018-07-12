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
import com.luretechnologies.tms.backend.data.entity.AppMock;
import com.luretechnologies.tms.backend.data.entity.ApplicationFile;
import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.ParameterType;
import com.luretechnologies.tms.backend.data.entity.Profile;
import com.luretechnologies.tms.backend.data.entity.ProfileType;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.rest.util.TestRestService;
import com.luretechnologies.tms.backend.service.AppDefaultParamService;
import com.luretechnologies.tms.backend.service.AppService;
import com.luretechnologies.tms.backend.service.ApplicationStoreService;
import com.luretechnologies.tms.backend.service.MockUserService;
import com.luretechnologies.tms.backend.service.OdometerDeviceService;
import com.luretechnologies.tms.backend.service.ProfileService;
import com.luretechnologies.tms.ui.NotificationUtil;
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
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressBar;
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
	/**
	 * 
	 */
	private static final long serialVersionUID = -1714335680383962006L;
	public static final String VIEW_NAME = "applicationstore";
	private FormLayout applicationDetailsForm;
	private HorizontalLayout buttonLayout,  appButtonsLayout;
	private HorizontalLayout applicationDetailsLabel;
	private HorizontalLayout applicationParamLabelLayout;
	private Grid<AppDefaultParam> appDefaultParamGrid;
	private GridLayout appStoreGridLayout;
	private static List<ApplicationFile> uploadedFileList = new ArrayList<>();
	private Grid<AppClient> appGrid;
	public static AppClient selectedApp;
	private Profile selectedProfile;
	private ComboBox<User> applicationOwner;
	private ComboBox<Devices> devices;
	private CheckBox activeApplication;
	private TextField packageName;
	private Button  saveForm;
	private Button cancelForm;
	private Button fileButton;
	private TextField applicationSearch, appDefaultParamSearch;
	private HorizontalLayout appGridMenuHorizontalLayout;
	private VerticalLayout appGridMenuVerticalLayout;
	private VerticalLayout applicationListLayout;
	private HorizontalLayout appSearchLayout ;
	private ListSelect optionList = new ListSelect<>();
	TextField profileField = new TextField();
	TextField profileName = new TextField("Name");
	ComboBox<String> parameterType = new ComboBox<String>("Type");
	
	@Autowired
	public ApplicationStoreView() {

	}
	
	@Autowired
	private AppService appService;
	
	@Autowired
	private ApplicationStoreService appStoreService;
	
	@Autowired
	private OdometerDeviceService deviceService;

	@Autowired
	private MockUserService userService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private AppDefaultParamService appDefaultParamService;

	@PostConstruct
	private void init() throws ApiException {
		setHeight("100%");
		setSpacing(false);
		setMargin(false);
		setResponsive(true);
		Panel panel = getAndLoadApplicationStorePanel();
		appStoreGridLayout = new GridLayout(2, 2, getAppStoreComponents());
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else if (r.getWidth() <= 699 && r.getWidth() > 0) {
				try {
					phoneAndTabMode();
				} catch (ApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					desktopMode();
				} catch (ApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			if(r.getWidth()<=600) {
				applicationSearch.setHeight("28px");
				appDefaultParamSearch.setHeight("28px");
			} else if(r.getWidth()>600 && r.getWidth()<=1000){
				applicationSearch.setHeight("32px");
				appDefaultParamSearch.setHeight("32px");
			}else {
				applicationSearch.setHeight(37, Unit.PIXELS);
				appDefaultParamSearch.setHeight("37px");
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
		} else if(width>600 && width<=1000){
			applicationSearch.setHeight("32px");
			appDefaultParamSearch.setHeight("32px");
		}else {
			applicationSearch.setHeight(37, Unit.PIXELS);
			appDefaultParamSearch.setHeight("37px");
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
		appStoreGridLayout.addComponents(getAppStoreComponents());
		buttonLayout.removeAllComponents();
		appButtonsLayout.addComponents(cancelForm, saveForm);
		//appGridMenuLayout = new VerticalLayout()
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
		panel.setCaption("Application Store");
		panel.setResponsive(true);
		panel.setSizeFull();
		addComponent(panel);
		return panel;
	}

	private Component[] getAppStoreComponents() throws ApiException {
		Component[] components = { getApplicationListLayout(), getAppicationDetailsLayout(), getEmptyLayout(),
				getApplicationDefaulParametersLayout() };
		return components;
	}

	private VerticalLayout getEmptyLayout() {
		VerticalLayout vl = new VerticalLayout();
		vl.addStyleName("applicationStore-GridLayout");
		return vl;
	}

	private VerticalLayout getApplicationListLayout() {
		appGrid = new Grid<>(AppClient.class);
		appGrid.setWidth("100%");
		appGrid.setColumns("packageName", "description", "packageVersion");
		appGrid.getColumn("packageName").setCaption("Name");
		appGrid.getColumn("description").setCaption("Description");
		appGrid.getColumn("packageVersion").setCaption("Version");
		appGrid.addColumn("available").setCaption("Available");
		appGrid.setDataProvider(appStoreService.getAppListDataProvider());
		appGrid.setSelectionMode(SelectionMode.SINGLE);
		
		applicationSearch = new TextField();
		applicationSearch.setWidth("100%");
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
			String valueInLower = valueChange.getValue().toLowerCase();
			List<AppClient> appClientList = appStoreService.searchApps(valueInLower);
			DataProvider data = new ListDataProvider(appClientList);
			appGrid.setDataProvider(data);
			/*ListDataProvider<AppClient> appDataProvider = (ListDataProvider<AppClient>) appGrid.getDataProvider();
			appDataProvider.setFilter(filter -> {
				String packageNameInLower = filter.getPackageName().toLowerCase();
				String packageVersionInLower = filter.getPackageVersion().toLowerCase();
				String fileInLower = filter.getDescription().toLowerCase();
				return packageVersionInLower.equals(valueInLower) || packageNameInLower.contains(valueInLower)
						|| fileInLower.contains(valueInLower);
			});*/
		});
		Button createAppGridRow = new Button(VaadinIcons.FOLDER_ADD, click -> {
			setApplicationFormComponentsEnable(true);
			appGrid.deselectAll();
			packageName.focus();
			fileButton.setEnabled(false);
			
		});
		createAppGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		createAppGridRow.setResponsive(true);
		Button editAppGridRow = new Button(VaadinIcons.EDIT, click -> {
			if (appGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.APPLICATIONSTORE_EDIT, Notification.Type.ERROR_MESSAGE);
			} else {
				setApplicationFormComponentsEnable(true);
				fileButton.setEnabled(true);
			}

		});
		editAppGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		editAppGridRow.setResponsive(true);

		Button deleteAppGridRow = new Button(VaadinIcons.TRASH, clicked -> {
			if (appGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.APPLICATIONSTORE_DELETE, Notification.Type.ERROR_MESSAGE);
			} else {
				confirmDeleteApp(applicationSearch);
			}

		});
		deleteAppGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		deleteAppGridRow.setResponsive(true);
		appSearchLayout = new HorizontalLayout(applicationSearch);
		appSearchLayout.setWidth("100%");
		appButtonsLayout = new HorizontalLayout(createAppGridRow, editAppGridRow, deleteAppGridRow);
		appGridMenuHorizontalLayout = new HorizontalLayout(appSearchLayout, appButtonsLayout);
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
				try {
					appDefaultParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(appStoreService.getAppDefaultParamListNew(selectedApp.getId())));
					optionList.setDataProvider(appStoreService.getAppProfileListDataProvider(selectedApp.getId()));
					parameterType.setDataProvider(new ListDataProvider<>(appStoreService.getAppParamTypeList(selectedApp.getId())));
					if(profileField.getValue()!=null && profileField.getValue()!="") {
						profileField.clear();
					}
					List<Profile> profileList = appStoreService.getAppProfileListByAppId(selectedApp.getId());
					for(Profile profile: profileList) {
						profileField.setValue(profile.getName());
					}
				} catch (ApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			} else {
				((TextField) applicationDetailsForm.getComponent(0)).clear();
				((TextField) applicationDetailsForm.getComponent(1)).clear();
				((TextField) applicationDetailsForm.getComponent(2)).clear();
				((ComboBox) applicationDetailsForm.getComponent(3)).clear();
				HorizontalLayout HL = (HorizontalLayout) applicationDetailsForm.getComponent(4);
				CheckBox checkbox = (CheckBox) HL.getComponent(1);
				checkbox.clear();
				appDefaultParamGrid.setDataProvider(new ListDataProvider<>(Arrays.asList()));
				((TextField) ((HorizontalLayout) ((VerticalLayout) appDefaultParamGrid.getParent()).getComponent(1))
						.getComponent(2)).clear();
				setApplicationFormComponentsEnable(false);
			}
		});
		return applicationListLayout;
	}

	/**
	 * 
	 */
	private void setApplicationFormComponentsEnable(boolean flag) {
		int count = applicationDetailsForm.getComponentCount();
		for (int i = 0; i < count; i++) {
			Component component = applicationDetailsForm.getComponent(i);
			if (component.getId() == null) {
				component.setEnabled(flag);

			} else if (component.getId().equalsIgnoreCase(DESCRIPTION)) {
				component.setEnabled(true);

			} else if (component.getId().equalsIgnoreCase(ACTIVE_LAYOUT)) {
				HorizontalLayout HL = (HorizontalLayout) component;
				CheckBox checkbox = (CheckBox) HL.getComponent(1);
				checkbox.setEnabled(flag);
			}
		}

		int defaultParamCount = ((VerticalLayout) appDefaultParamGrid.getParent()).getComponentCount();
		for (int i = 0; i < defaultParamCount; i++) {
			Component component = ((VerticalLayout) appDefaultParamGrid.getParent()).getComponent(i);
			component.setEnabled(flag);
		}
	}

	// private void loadAppGrid() {
	// treeDataService.getTreeDataForDebugAndAlert();
	// List<ExtendedNode> nodeList = treeDataService.getDebugAndAlertNodeList();
	// Set<ExtendedNode> nodeSet = nodeTree.getSelectionModel().getSelectedItems();
	// ExtendedNode nodeSelected = nodeSet.iterator().next();
	// for(ExtendedNode node : nodeList) {
	// if(node.getLabel().equals(nodeSelected.getLabel())) {
	// DataProvider data = new ListDataProvider(node.getExtendedList());
	// alertGrid.setDataProvider(data);
	// }
	// }
	//
	// }

	private VerticalLayout getAppicationDetailsLayout() throws ApiException {

		packageName = new TextField("Application Package Name");
		packageName.addStyleNames("role-textbox", "v-textfield-font", ValoTheme.TEXTFIELD_BORDERLESS, 
				"asset-debugComboBox", "v-textfield-lineHeight");
		packageName.setCaptionAsHtml(true);
		packageName.setEnabled(false);
		packageName.setWidth("96%");
		TextField description = new TextField("Description");
		description.addStyleNames("role-textbox", "v-textfield-font", ValoTheme.TEXTFIELD_BORDERLESS,
				"asset-debugComboBox", "v-textfield-lineHeight");
		description.setEnabled(false);
		description.setId(DESCRIPTION);
		description.setWidth("96%");
		fileButton = new Button("Files", VaadinIcons.UPLOAD);
		fileButton.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY);
		fileButton.setEnabled(false);
		fileButton.addClickListener(click -> {
			Window fileListWindow = null;
			try {
				fileListWindow = getSmallListWindow(true, profileField);
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (fileListWindow.getParent() == null)
				UI.getCurrent().addWindow(fileListWindow);
		});
		TextField packageVersion = new TextField("Version");
		packageVersion.addStyleNames("role-textbox", "v-textfield-font", ValoTheme.TEXTFIELD_BORDERLESS,
				"asset-debugComboBox", "v-textfield-lineHeight");
		packageVersion.setEnabled(false);
		packageVersion.setWidth("96%");
		// FIXME: put the list of organization
		applicationOwner = new ComboBox<User>("Application Owner");
		applicationOwner.setEnabled(false);
		applicationOwner.setCaptionAsHtml(true);
		applicationOwner.setDataProvider(new ListDataProvider<>(appStoreService.getOwnerList()));
		applicationOwner.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", 
				"asset-debugComboBox", "small");
		devices = new ComboBox<Devices>("Device");
		devices.setDataProvider(deviceService.getListDataProvider());
		devices.setEnabled(false);
		devices.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size");
		
		HorizontalLayout activeBoxLayout = new HorizontalLayout();
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
			if(applicationOwner.getValue()==null) {
				Notification.show(NotificationUtil.SAVE, Type.ERROR_MESSAGE);
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
				// Check if Profile is set and get all Default Params to be set
				app.setAppDefaultParamList(
						appDefaultParamGrid.getDataProvider().fetch(new Query<>()).collect(Collectors.toList()));
				app.setProfile(new ArrayList());
				//FIXME: figureout how to set all the list of Profiles added. or save only one
				appStoreService.saveApp(app);
				appGrid.setDataProvider(appStoreService.getAppListDataProvider());
				appGrid.getDataProvider().refreshAll();
				appGrid.deselectAll();
			}

			}});
		saveForm.setEnabled(true );
		saveForm.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY);
		cancelForm = new Button("Cancel", click -> {
			packageName.clear();
			//packageName.setEnabled(false);
			description.clear();
			//packageFile.setEnabled(false);
			packageVersion.clear();
			//packageVersion.setEnabled(false);
			applicationOwner.clear();
			//applicationOwner.setEnabled(false);
			devices.clear();
			//devices.setEnabled(false);
			activeApplication.clear();
			//activeApplication.setEnabled(false);
			
			setApplicationFormComponentsEnable(false);
			description.setEnabled(false);
			
			appDefaultParamGrid.setDataProvider(new ListDataProvider<>(Arrays.asList()));
			((TextField) ((HorizontalLayout) ((VerticalLayout) appDefaultParamGrid.getParent()).getComponent(1))
					.getComponent(2)).clear();

		});
		cancelForm.setEnabled(true);
		cancelForm.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY);
		HorizontalLayout appDetailsSaveCancleAndLabelLayout = new HorizontalLayout();
		appDetailsSaveCancleAndLabelLayout.addStyleName("applicationStore-cancelsaveApplicationLabelLayout");
		HorizontalLayout fileButtonLayout = new HorizontalLayout();
		fileButtonLayout.setCaption("Upload Files");
		fileButtonLayout.addStyleName("asset-debugComboBox");
		fileButtonLayout.addComponent(fileButton);
		appDetailsSaveCancleAndLabelLayout.setWidth("100%");
		applicationDetailsForm = new FormLayout(packageName, description, packageVersion, applicationOwner,
				 activeBoxLayout, fileButtonLayout);
		applicationDetailsForm.addStyleNames("applicationStore-FormLayout", "system-LabelAlignment");
		applicationDetailsForm.setCaptionAsHtml(true);
		//applicationDetailsForm.setComponentAlignment(fileButton, Alignment.BOTTOM_RIGHT);
		applicationDetailsLabel = new HorizontalLayout();
		applicationDetailsLabel.setWidth("100%");
		Label appDetailsLabel = new Label("Application Details");
		appDetailsLabel.addStyleName("label-style");
		appDetailsLabel.addStyleNames(ValoTheme.LABEL_BOLD, ValoTheme.LABEL_H3);
		applicationDetailsLabel.addComponent(appDetailsLabel);
		buttonLayout = new HorizontalLayout();
		buttonLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		buttonLayout.addComponents(cancelForm, saveForm);
		buttonLayout.setResponsive(true);
		//buttonLayout.setStyleName("save-cancelButtonsAlignment");
		//buttonLayout.addStyleName("applicationStore-cancelsaveLayout");

		appDetailsSaveCancleAndLabelLayout.addComponents(applicationDetailsLabel, buttonLayout);
		appDetailsSaveCancleAndLabelLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_RIGHT);
		VerticalLayout applicationDetailsLayout = new VerticalLayout(appDetailsSaveCancleAndLabelLayout,
				applicationDetailsForm);
		applicationDetailsLayout.addStyleName("applicationStore-ApplicationDetailsLayout");
		applicationDetailsLayout.setComponentAlignment(appDetailsSaveCancleAndLabelLayout, Alignment.TOP_RIGHT);
		return applicationDetailsLayout;
	}

	private VerticalLayout getApplicationDefaulParametersLayout() throws ApiException {
		appDefaultParamGrid = new Grid<>(AppDefaultParam.class);
		appDefaultParamGrid.addStyleName("applicationStore-horizontalAlignment");
		appDefaultParamGrid.setColumns("parameter", "description", "type", "value");
		appDefaultParamGrid.setWidth("100%");
		appDefaultParamGrid.setHeightByRows(7);;
		appDefaultParamGrid.getColumn("parameter").setEditorComponent(new TextField());
		appDefaultParamGrid.getColumn("description").setEditorComponent(new TextField());
		//ComboBox<ParameterType> paramComboBox = new ComboBox<>();
		//parameterType.setDataProvider(new ListDataProvider<>(appStoreService.getAppParamTypeList(selectedApp.getId())));
		appDefaultParamGrid.getColumn("type").setEditorComponent(parameterType);
		appDefaultParamGrid.getColumn("value").setEditorComponent(new TextField());
		appDefaultParamGrid.getEditor().setEnabled(true).addSaveListener(save -> {
			try {
				AppParamFormat appParamFormat = appStoreService.getAppParamFormatByType(parameterType.getValue());
				appStoreService.saveAppDefaultParam(selectedApp, appDefaultParamGrid.getSelectedItems().iterator().next(), appParamFormat);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
		
		/*appDefaultParamGrid.getEditor().setEnabled(true).addCancelListener(cancel -> {
			appStoreService.saveAppDefaultParam(selectedApp, appDefaultParamGrid.getSelectedItems().iterator().next());

		});*/

		appDefaultParamSearch = new TextField();
		appDefaultParamSearch.setWidth("100%");
		appDefaultParamSearch.setIcon(VaadinIcons.SEARCH);
		appDefaultParamSearch.setStyleName("small inline-icon search");
		appDefaultParamSearch.addStyleNames("v-textfield-font");
		appDefaultParamSearch.setHeight("37px");
		appDefaultParamSearch.setPlaceholder("Search");
		appDefaultParamSearch.setResponsive(true);
		appDefaultParamSearch.addShortcutListener(new ShortcutListener("Clear", KeyCode.ESCAPE, null) {

			@Override
			public void handleAction(Object sender, Object target) {
				if (target == appDefaultParamSearch) {
					appDefaultParamSearch.clear();
				}

			}
		});
		appDefaultParamSearch.addValueChangeListener(valueChange -> {
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
		});
		Button createAppDefaultParamGridRow = new Button(VaadinIcons.FOLDER_ADD, click -> {
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
				e.printStackTrace();
			}

		});
		createAppDefaultParamGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		createAppDefaultParamGridRow.setResponsive(true);
		Button editAppDefaultParamGridRow = new Button(VaadinIcons.PENCIL, click -> {
			// Try to add the inline Grid edit function
			if (appDefaultParamGrid.getSelectedItems().size() > 0)
				appDefaultParamGrid.getEditor().setEnabled(true);
		});
		editAppDefaultParamGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		editAppDefaultParamGridRow.setResponsive(true);

		Button deleteAppDefaultParamGridRow = new Button(VaadinIcons.TRASH, clicked -> {
			if(appGrid.getSelectedItems().size()==0) {
				Notification.show(NotificationUtil.APPLICATIONSTORE_PARAM_DELETE_APPGRID, Type.ERROR_MESSAGE);
			}else {
			if (appDefaultParamGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.APPLICATIONSTORE_PARAM_DELETE_PARAMGRID, Type.ERROR_MESSAGE);
			} else {
				confirmDeleteAppDefaultParam(appDefaultParamGrid.getSelectedItems().iterator().next().getId(), selectedApp.getId(), appDefaultParamSearch);
			}
			}
		});
		deleteAppDefaultParamGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		deleteAppDefaultParamGridRow.setResponsive(true);
		Button clearAllParams = new Button("Clear All", click -> {
			try {
				appStoreService.removeAPPParamAll(selectedApp.getId());
				appDefaultParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(appStoreService.getAppDefaultParamListNew(selectedApp.getId())));
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		clearAllParams.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY);
		Button profileDropDown = new Button("Profile", VaadinIcons.PLUS_CIRCLE);
		profileDropDown.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY);
		profileField.addStyleNames("role-textbox", "v-textfield-font", ValoTheme.TEXTFIELD_BORDERLESS);
		profileField.setEnabled(false);
		Window openProfileWindow = getSmallListWindow(false, profileField);
		profileDropDown.addClickListener(click -> {
			if (appGrid.getSelectedItems().size() > 0) {
			if (openProfileWindow.getParent() == null) {
				UI.getCurrent().addWindow(openProfileWindow);
				/*ListSelect<Profile> optionList = (ListSelect<Profile>) ((VerticalLayout) openProfileWindow.getContent())
						.getComponent(0);
				if (selectedApp.getProfile() != null)
					optionList.select(selectedApp.getProfile().stream().filter(profile -> profile.isActive()).findFirst().get());*/
			}
			}else {
				Notification.show("Save the app/ select any app from app list", Type.ERROR_MESSAGE);
			}
		});

		HorizontalLayout appParamHeaderButtonLayout = new HorizontalLayout(clearAllParams, profileDropDown,
				profileField);
		appParamHeaderButtonLayout.addStyleName("applicationStore-horizontalAlignment");
		//appParamHeaderButtonLayout.setWidth("100%");
		appParamHeaderButtonLayout.setEnabled(false);
		HorizontalLayout appParamSearchLayout = new HorizontalLayout(appDefaultParamSearch);
		appParamSearchLayout.setWidth("100%");
		HorizontalLayout appParamButtonsLayout = new HorizontalLayout(createAppDefaultParamGridRow,
				deleteAppDefaultParamGridRow);
		// appParamButtonsLayout.setWidth("100%");
		HorizontalLayout appParamGridMenuLayout = new HorizontalLayout(appParamSearchLayout, appParamButtonsLayout);
		appParamGridMenuLayout.setWidth("100%");
		appParamGridMenuLayout.addStyleName("applicationStore-horizontalAlignment");

		applicationParamLabelLayout = new HorizontalLayout();
		applicationParamLabelLayout.setWidth("100%");
		Label appParamLabel = new Label("Application Default Parameters");
		appParamLabel.addStyleName("label-style");
		appParamLabel.addStyleNames(ValoTheme.LABEL_BOLD, ValoTheme.LABEL_H3);
		applicationParamLabelLayout.addComponents(appParamLabel);
		appParamGridMenuLayout.setEnabled(false);
		appDefaultParamGrid.setEnabled(false);
		VerticalLayout applicationDefaultParametersLayout = new VerticalLayout(applicationParamLabelLayout,
				appParamHeaderButtonLayout, appParamGridMenuLayout, appDefaultParamGrid);
		applicationDefaultParametersLayout.setCaptionAsHtml(true);
		applicationDefaultParametersLayout.addStyleName("applicationStore-VerticalLayout");
		return applicationDefaultParametersLayout;
	}

	private void confirmDeleteApp(TextField appSearch) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?", "Ok", "Cancel",
				new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							// Confirmed to continue
							// appService.removeApp(appGrid.getSelectedItems().iterator().next());
							appSearch.clear();
							setApplicationFormComponentsEnable(false);
							appStoreService.removeApp(appGrid.getSelectedItems().iterator().next());
							appGrid.setDataProvider(appStoreService.getAppListDataProvider());
							appGrid.getDataProvider().refreshAll();
							appGrid.deselectAll();
							// TODO add Grid reload
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
							 
							 try {
								 appStoreService.removeAPPParam(appId, appParamId);
								appDefaultParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(appStoreService.getAppDefaultParamListNew(appId)));
							} catch (ApiException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							 appParamSearch.clear();
							// TODO add Grid reload
						} else {
							// User did not confirm

						}
					}
				});
	}

	private Window getSmallListWindow(boolean isFileChoose, TextField field) throws ApiException {

		// FIXME: put generics for List items. Also put data in method parameter to
		// populate the list
		optionList.setRows(3);
		optionList.setResponsive(true);
		optionList.setWidth(100, Unit.PERCENTAGE);
		optionList.addSelectionListener(selection -> {
			if (selection.getFirstSelectedItem().isPresent()) {
				// FIXME: selection of value
				if (field != null) {
					String value = selection.getValue().toString();
					field.setValue(value.substring(1, value.length() - 1));
				}
				if (selection.getValue() instanceof Profile) {
					selectedProfile = (Profile) selection.getValue();
				}
			}
		});
		// FIXME: for button caption pass the value full or partial in method paramter
		Button addNewButton, deleteButton;
		if (isFileChoose) {
			if (appGrid.getSelectedItems().size() > 0) {
				optionList.setDataProvider(new ListDataProvider<ApplicationFile>(appStoreService.getAllAppFileList(appGrid.getSelectedItems().iterator().next().getId())));
			}
			
			optionList.setId(FILE_CHOOSE_LIST);
			addNewButton = new Button("Add New File", click -> {
				Window fileUpload = openFileUploadWindow(optionList);
				if (fileUpload.getParent() == null) {
					UI.getCurrent().addWindow(fileUpload);
				} else if (fileUpload.getComponentCount() > 0) {
					fileUpload.close();
				}
			});
			deleteButton = new Button("Delete File", click -> {
				if (optionList.getSelectedItems().size() == 1) {
				
					ApplicationFile file = (ApplicationFile) optionList.getSelectedItems().iterator().next();
					appStoreService.deleteAppFiles(appGrid.getSelectedItems().iterator().next().getId(), file.getId());
					optionList.setDataProvider(new ListDataProvider<ApplicationFile>(appStoreService.getAllAppFileList(appGrid.getSelectedItems().iterator().next().getId())));
					// delete the file from location and list
				}
			});
		} else {
			Window createProfileWindow = openProfileWindow(optionList);
			createProfileWindow.setCaption("Create Profile");
			addNewButton = new Button("Add New Profile", click -> {
				profileName.clear();
				if (createProfileWindow.getParent() == null)
					UI.getCurrent().addWindow(createProfileWindow);

			});
			deleteButton = new Button("Delete Profile", click -> {
				if (optionList.getSelectedItems().size() == 1) {
					Profile selected = (Profile) optionList.getSelectedItems().iterator().next();
					appStoreService.removeAppProfile(selectedApp.getId(), selected.getId());
					try {
						optionList.setDataProvider(appStoreService.getAppProfileListDataProvider(selectedApp.getId()));
					} catch (ApiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					field.clear();

				}
			});
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
				
				try {
					optionList.setDataProvider(new ListDataProvider<>(appStoreService.getAppProfileListByAppId(selectedApp.getId())));
				} catch (ApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				profileWindow.close();
			}
		});
		saveProfile.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		Button cancelProfile = new Button("Reset", click -> {
			profileName.clear();
		});
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
		TextField parameterName = new TextField("Name");
		parameterName.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS, "role-textbox","v-textfield-font", "v-grid-cell");
		TextField parameterDescription = new TextField("Description");
		parameterDescription.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS, "role-textbox","v-textfield-font", "v-grid-cell");
		parameterType.setCaptionAsHtml(true);
		parameterType.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", 
				"asset-debugComboBox", "small");
		TextField parameterActive = new TextField("Active");
		parameterActive.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS, "role-textbox","v-textfield-font", "v-grid-cell");
		
		//Clear Previously Entered Values
		parameterName.clear();
		parameterActive.clear();
		parameterDescription.clear();
		parameterType.clear();
		
		Button saveParameter = new Button("Save", click -> {
			if (StringUtils.isNotEmpty(parameterType.getValue())
					&& StringUtils.isNotEmpty(parameterName.getValue()) &&
					StringUtils.isNotEmpty(parameterDescription.getValue()) &&
					StringUtils.isNotEmpty(parameterActive.getValue())) {
				AppDefaultParam appDefaultParam = new AppDefaultParam(null, parameterName.getValue(),
						parameterDescription.getValue(), parameterType.getValue(), parameterActive.getValue());
				 try {
					 AppParamFormat appParamFormat = appStoreService.getAppParamFormatByType(parameterType.getValue());
					appStoreService.saveAppDefaultParam(selectedApp, appDefaultParam, appParamFormat);
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ApiException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					appDefaultParamGrid.setDataProvider(new ListDataProvider<AppDefaultParam>(appStoreService.getAppDefaultParamListNew(selectedApp.getId())));
				} catch (ApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				appDefaultWindow.close();
			}
		});
		saveParameter.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		Button cancelParameter = new Button("Reset", click -> {
			parameterName.clear();
			parameterActive.clear();
			parameterDescription.clear();
			parameterType.clear();
		});
		cancelParameter.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		HorizontalLayout buttonLayout = new HorizontalLayout(saveParameter, cancelParameter);
		FormLayout profileFormLayout = new FormLayout(parameterName, parameterDescription,parameterType,
				parameterActive);

		// Window Setup
		appDefaultWindow.setContent(new VerticalLayout(profileFormLayout, buttonLayout));
		appDefaultWindow.center();
		appDefaultWindow.setModal(true);
		appDefaultWindow.setClosable(true);
		appDefaultWindow.setWidth(30, Unit.PERCENTAGE);
		return appDefaultWindow;
	}
	
	private Window openFileUploadWindow(ListSelect optionList) {
		LineBreakCounter lineBreakCounter = new LineBreakCounter();
		lineBreakCounter.setSlow(true);

		FileUploadReceiver fileUploadReceiver = new FileUploadReceiver();
		// fileUploadReceiver.

		Upload uploadFile = new Upload("Upload File here", lineBreakCounter);
		uploadFile.setImmediateMode(false);
		uploadFile.setButtonCaption("Upload File");
		uploadFile.addStyleName("applicationStore-UploadButton");
		uploadFile.setReceiver(fileUploadReceiver);

		Window fileUploadWindow = new Window("File Upload", uploadFile);
		UploadInfoWindow uploadInfoWindow = new UploadInfoWindow(uploadFile, lineBreakCounter, fileUploadReceiver,
				optionList, fileUploadWindow,appStoreService);

		uploadFile.addStartedListener(event -> {
			if (uploadInfoWindow.getParent() == null) {
				UI.getCurrent().addWindow(uploadInfoWindow);
				// fileUploadWindow.close();
			}
			uploadInfoWindow.setClosable(false);
		});
		uploadFile.addFinishedListener(event -> uploadInfoWindow.setClosable(true));
		fileUploadWindow.addStyleName("applicationStore-UploadWindow");
		// fileUploadWindow.setCaption("<h3 style=color:216C2A;>File Upload</h3>");
		fileUploadWindow.setWidth(30, Unit.PERCENTAGE);
		fileUploadWindow.setHeight(10, Unit.PERCENTAGE);
		fileUploadWindow.setClosable(true);
		fileUploadWindow.setDraggable(true);
		fileUploadWindow.setResizable(false);
		fileUploadWindow.setModal(true);
		fileUploadWindow.center();

		// uploadFile.addFinishedListener(listener)
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

		private final ListSelect optionList;

		private UploadInfoWindow(final Upload upload, final LineBreakCounter lineBreakCounter,
				final FileUploadReceiver fileUploadReceiver, final ListSelect optionList, Window uploadWindow,final ApplicationStoreService applicationStoreService) {
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
			//uploadedFileList.add(fileUploadReceiver.file);
			if (optionList.getId().equalsIgnoreCase(FILE_CHOOSE_LIST)) {
				//optionList.setDataProvider(new ListDataProvider<ApplicationFile>(uploadedFileList));
				//TODO: call integration fileupload service
				applicationStoreService.uploadAppFiles(ApplicationStoreView.selectedApp.getId(), "Description111", ApplicationStoreView.applicationFilePath);
				//optionList.setDataProvider(new ListDataProvider<ApplicationFile>(appStoreService.getAllAppFileList(appGrid.getSelectedItems().iterator().next().getId())));
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

			// Instead of linebreaker use a new reciver for getting the uploaded file.
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
				file = new ApplicationFile("C:/temp" + File.separator + filename);
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
