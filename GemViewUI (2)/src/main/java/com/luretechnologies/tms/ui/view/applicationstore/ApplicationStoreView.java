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

import com.luretechnologies.tms.backend.data.entity.App;
import com.luretechnologies.tms.backend.data.entity.AppDefaultParam;
import com.luretechnologies.tms.backend.data.entity.ApplicationFile;
import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.ParameterType;
import com.luretechnologies.tms.backend.data.entity.Profile;
import com.luretechnologies.tms.backend.data.entity.ProfileType;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.service.AppDefaultParamService;
import com.luretechnologies.tms.backend.service.AppService;
import com.luretechnologies.tms.backend.service.MockUserService;
import com.luretechnologies.tms.backend.service.OdometerDeviceService;
import com.luretechnologies.tms.backend.service.ProfileService;
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
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
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
	private static final String PACKAGE_FILE = "packageFile";
	private static final String ACTIVE_LAYOUT = "ActiveLayout";
	private static final String FILE_CHOOSE_LIST = "fileChooseList";
	/**
	 * 
	 */
	private static final long serialVersionUID = -1714335680383962006L;
	public static final String VIEW_NAME = "applicationstore";
	private FormLayout applicationDetailsForm;
	private HorizontalLayout buttonLayout;
	private HorizontalLayout applicationDetailsLabel;
	private HorizontalLayout applicationParamLabelLayout;
	private Grid<AppDefaultParam> appDefaultParamGrid;
	private GridLayout appStoreGridLayout;
	private static List<ApplicationFile> uploadedFileList = new ArrayList<>();
	private Grid<App> appGrid;
	private App selectedApp;
	private Profile selectedProfile;
	private ComboBox<User> applicationOwner;
	private ComboBox<Devices> devices;
	private CheckBox activeApplication;
	private TextField packageName;

	@Autowired
	public ApplicationStoreView() {

	}

	@Autowired
	private AppService appService;

	@Autowired
	private OdometerDeviceService deviceService;

	@Autowired
	private MockUserService userService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private AppDefaultParamService appDefaultParamService;

	@PostConstruct
	private void init() {
		setSpacing(false);
		setMargin(false);
		setResponsive(true);
		Panel panel = getAndLoadApplicationStorePanel();
		appStoreGridLayout = new GridLayout(2, 2, getAppStoreComponents());
		appStoreGridLayout.setWidth("100%");
		appStoreGridLayout.setMargin(true);
		appStoreGridLayout.addStyleName("applicatioStore-GridLayout");
		panel.setContent(appStoreGridLayout);

		Page.getCurrent().addBrowserWindowResizeListener(r -> {
			System.out.println("Height " + r.getHeight() + "Width:  " + r.getWidth() + " in pixel");
			if (r.getWidth() <= 1300 && r.getWidth() >= 700) {
				phoneAndTabMode();
			} else if (r.getWidth() <= 699 && r.getWidth() > 0) {
				phoneAndTabMode();
			} else {
				desktopMode();

			}
		});
		differentModesLoad();
	}

	private void differentModesLoad() {
		int width = Page.getCurrent().getBrowserWindowWidth();
		if (width <= 1300 && width >= 700) {
			phoneAndTabMode();

		} else if (width <= 699 && width > 0) {
			phoneAndTabMode();

		} else {
			desktopMode();
		}
	}

	private void phoneAndTabMode() {
		appStoreGridLayout.removeAllComponents();
		appStoreGridLayout.setColumns(1);
		appStoreGridLayout.setRows(4);
		appStoreGridLayout.addComponents(getAppStoreComponents());
	}

	private void desktopMode() {
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

	private Component[] getAppStoreComponents() {
		Component[] components = { getApplicationListLayout(), getAppicationDetailsLayout(), getEmptyLayout(),
				getApplicationDefaulParametersLayout() };
		return components;
	}

	private VerticalLayout getEmptyLayout() {
		VerticalLayout vl = new VerticalLayout();
		vl.addStyleName("applicatioStore-GridLayout");
		return vl;
	}

	private VerticalLayout getApplicationListLayout() {
		appGrid = new Grid<>(App.class);
		appGrid.setWidth("100%");
		appGrid.setDataProvider(appService.getListDataProvider());
		appGrid.setColumns("packageName", "file", "packageVersion");
		appGrid.getColumn("packageName").setCaption("Name");
		appGrid.getColumn("file").setCaption("File");
		appGrid.getColumn("packageVersion").setCaption("Version");
		appGrid.addColumn("active").setCaption("Active");
		appGrid.setSelectionMode(SelectionMode.SINGLE);
		
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
				return packageVersionInLower.equals(valueInLower) || packageNameInLower.contains(valueInLower)
						|| fileInLower.contains(valueInLower);
			});
		});
		Button createAppGridRow = new Button(VaadinIcons.FOLDER_ADD, click -> {
			setApplicationFormComponentsEnable(true);
			appGrid.deselectAll();
			packageName.focus();
			
		});
		createAppGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		createAppGridRow.setResponsive(true);
		Button editAppGridRow = new Button(VaadinIcons.PENCIL, click -> {
			if (appGrid.getSelectedItems().isEmpty()) {
				Notification.show("Please select any app to delete", Notification.Type.WARNING_MESSAGE);
			} else {
				setApplicationFormComponentsEnable(true);
			}

		});
		editAppGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		editAppGridRow.setResponsive(true);

		Button deleteAppGridRow = new Button(VaadinIcons.TRASH, clicked -> {
			if (appGrid.getSelectedItems().isEmpty()) {
				Notification.show("Select any App type to delete", Notification.Type.WARNING_MESSAGE)
						.setDelayMsec(3000);
			} else {
				confirmDeleteApp(applicationSearch);
			}

		});
		deleteAppGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		deleteAppGridRow.setResponsive(true);
		HorizontalLayout appSearchLayout = new HorizontalLayout(applicationSearch);
		appSearchLayout.setWidth("100%");
		HorizontalLayout appButtonsLayout = new HorizontalLayout(createAppGridRow, editAppGridRow, deleteAppGridRow);
		// appButtonsLayout.setWidth("100%");
		HorizontalLayout appGridMenuLayout = new HorizontalLayout(appSearchLayout, appButtonsLayout);
		appGridMenuLayout.setWidth("100%");
		VerticalLayout applicationListLayout = new VerticalLayout(appGridMenuLayout, appGrid);
		applicationListLayout.addStyleName("applicatioStore-VerticalLayout");
		appGrid.addSelectionListener(selection -> {
			if (selection.getFirstSelectedItem().isPresent()) {
				selectedApp = selection.getFirstSelectedItem().get();
				((TextField) applicationDetailsForm.getComponent(0)).setValue(selectedApp.getPackageName());
				((TextField) applicationDetailsForm.getComponent(1)).setValue(selectedApp.getFile());
				((TextField) applicationDetailsForm.getComponent(3)).setValue(selectedApp.getPackageVersion());
				((ComboBox) applicationDetailsForm.getComponent(4)).setValue(selectedApp.getOwner());
				((ComboBox) applicationDetailsForm.getComponent(5)).setValue(selectedApp.getDevice());
				HorizontalLayout HL = (HorizontalLayout) applicationDetailsForm.getComponent(6);
				CheckBox checkbox = (CheckBox) HL.getComponent(1);
				checkbox.setValue(selectedApp.isActive());
				// ((CheckBox)
				// applicationDetailsForm.getComponent(6)).setValue(selectedItem.isActive());
				appDefaultParamGrid.setDataProvider(new ListDataProvider<>(selectedApp.getAppDefaultParamList()));
				if (selectedApp.getProfile() != null)
					((TextField) ((HorizontalLayout) ((VerticalLayout) appDefaultParamGrid.getParent()).getComponent(1))
							.getComponent(2)).setValue(selectedApp.getProfile().toString());
				else
					((TextField) ((HorizontalLayout) ((VerticalLayout) appDefaultParamGrid.getParent()).getComponent(1))
							.getComponent(2)).setValue("");

			} else {
				((TextField) applicationDetailsForm.getComponent(0)).clear();
				((TextField) applicationDetailsForm.getComponent(1)).clear();
				((TextField) applicationDetailsForm.getComponent(3)).clear();
				((ComboBox) applicationDetailsForm.getComponent(4)).clear();
				((ComboBox) applicationDetailsForm.getComponent(5)).clear();
				HorizontalLayout HL = (HorizontalLayout) applicationDetailsForm.getComponent(6);
				CheckBox checkbox = (CheckBox) HL.getComponent(1);
				checkbox.clear();
				// ((CheckBox) applicationDetailsForm.getComponent(6)).clear();
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

			} else if (component.getId().equalsIgnoreCase(PACKAGE_FILE)) {
				component.setEnabled(false);

			} else if (component.getId().equalsIgnoreCase(ACTIVE_LAYOUT)) {
				HorizontalLayout HL = (HorizontalLayout) component;
				CheckBox checkbox = (CheckBox) HL.getComponent(1);
				checkbox.setEnabled(flag);
			}
		}
		buttonLayout.getComponent(0).setEnabled(flag);
		buttonLayout.getComponent(1).setEnabled(flag);

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

	private VerticalLayout getAppicationDetailsLayout() {

		packageName = new TextField("Application Package Name");
		packageName.addStyleNames("role-textbox", "v-grid-cell", ValoTheme.TEXTFIELD_BORDERLESS);
		packageName.setCaptionAsHtml(true);
		packageName.setEnabled(false);
		packageName.setWidth("80%");
		TextField packageFile = new TextField("Package File");
		packageFile.addStyleNames("role-textbox", "v-grid-cell", ValoTheme.TEXTFIELD_BORDERLESS);
		packageFile.setEnabled(false);
		packageFile.setId(PACKAGE_FILE);
		packageFile.setWidth("80%");
		Window fileListWindow = getSmallListWindow(true, packageFile);
		Button fileButton = new Button("Files", VaadinIcons.UPLOAD);
		fileButton.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY);
		fileButton.setEnabled(false);
		fileButton.addClickListener(click -> {
			if (fileListWindow.getParent() == null)
				UI.getCurrent().addWindow(fileListWindow);
		});
		TextField packageVersion = new TextField("Version");
		packageVersion.addStyleNames("role-textbox", "v-grid-cell", ValoTheme.TEXTFIELD_BORDERLESS);
		packageVersion.setEnabled(false);
		packageVersion.setWidth("80%");
		// FIXME: put the list of organization
		applicationOwner = new ComboBox<User>("Application Owner");
		applicationOwner.setEnabled(false);
		applicationOwner.setCaptionAsHtml(true);
		applicationOwner.setDataProvider(new ListDataProvider<>(userService.getUsers()));
		devices = new ComboBox<Devices>("Device");
		devices.setDataProvider(deviceService.getListDataProvider());
		devices.setEnabled(false);
		// devices.addSelectionListener(selection -> {
		//
		// });
		HorizontalLayout activeBoxLayout = new HorizontalLayout();
		activeBoxLayout.setId(ACTIVE_LAYOUT);
		Label active = new Label("Active");
		active.setStyleName("role-activeLable");
		active.addStyleName("v-textfield-font");
		activeApplication = new CheckBox("Application Available", false);
		activeApplication.setEnabled(false);
		activeApplication.addStyleName("v-textfield-font");
		activeBoxLayout.addComponents(active, activeApplication);
		activeBoxLayout.setStyleName("role-activeLable");
		activeBoxLayout.addStyleName("assetAlert-activeCheckBox");
		Button saveForm = new Button("Save");
		saveForm.addClickListener(click -> {
			App app;
			if (appGrid.getSelectedItems().size() > 0) {
				app = appGrid.getSelectedItems().iterator().next();
			} else {
				app = new App();
			}
			app.setPackageName(packageName.getValue());
			app.setFile(packageFile.getValue());
			app.setPackageVersion(packageVersion.getValue());
			app.setOwner(applicationOwner.getValue());
			app.setDevice(devices.getValue());
			app.setActive(activeApplication.getValue());
			// Check if Profile is set and get all Default Params to be set
			app.setAppDefaultParamList(
					appDefaultParamGrid.getDataProvider().fetch(new Query<>()).collect(Collectors.toList()));
			if (selectedProfile != null)
				app.setProfile(selectedProfile);
			appService.saveApp(app);
			appGrid.setDataProvider(appService.getListDataProvider());
			appGrid.getDataProvider().refreshAll();
			appGrid.deselectAll();

		});
		saveForm.setEnabled(false);
		saveForm.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY);
		Button cancelForm = new Button("Cancel", click -> {
			packageName.clear();
			//packageName.setEnabled(false);
			packageFile.clear();
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
			
			appDefaultParamGrid.setDataProvider(new ListDataProvider<>(Arrays.asList()));
			((TextField) ((HorizontalLayout) ((VerticalLayout) appDefaultParamGrid.getParent()).getComponent(1))
					.getComponent(2)).clear();

		});
		cancelForm.setEnabled(false);
		cancelForm.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY);
		HorizontalLayout appDetailsSaveCancleAndLabelLayout = new HorizontalLayout();
		appDetailsSaveCancleAndLabelLayout.setWidth("100%");
		applicationDetailsForm = new FormLayout(packageName, packageFile, fileButton, packageVersion, applicationOwner,
				devices, activeBoxLayout);
		applicationDetailsForm.addStyleName("applicatioStore-FormLayout");
		applicationDetailsForm.setCaptionAsHtml(true);
		applicationDetailsForm.setComponentAlignment(fileButton, Alignment.BOTTOM_RIGHT);
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
		buttonLayout.setStyleName("save-cancelButtonsAlignment");

		appDetailsSaveCancleAndLabelLayout.addComponents(applicationDetailsLabel, buttonLayout);
		appDetailsSaveCancleAndLabelLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_RIGHT);
		VerticalLayout applicationDetailsLayout = new VerticalLayout(appDetailsSaveCancleAndLabelLayout,
				applicationDetailsForm);
		applicationDetailsLayout.addStyleName("applicatioStore-VerticalLayout");
		applicationDetailsLayout.setComponentAlignment(appDetailsSaveCancleAndLabelLayout, Alignment.TOP_RIGHT);
		return applicationDetailsLayout;
	}

	private VerticalLayout getApplicationDefaulParametersLayout() {
		appDefaultParamGrid = new Grid<>(AppDefaultParam.class);
		appDefaultParamGrid.setColumns("parameter", "description", "type", "active");
		appDefaultParamGrid.setWidth("100%");
		appDefaultParamGrid.setHeightByRows(5);
		appDefaultParamGrid.getColumn("parameter").setEditorComponent(new TextField());
		appDefaultParamGrid.getColumn("description").setEditorComponent(new TextField());
		ComboBox<ParameterType> paramComboBox = new ComboBox<>();
		paramComboBox.setDataProvider(new ListDataProvider<>(Arrays.asList(ParameterType.values())));
		appDefaultParamGrid.getColumn("type").setEditorComponent(paramComboBox);
		appDefaultParamGrid.getColumn("active").setEditorComponent(new CheckBox());
		appDefaultParamGrid.getEditor().setEnabled(true).addSaveListener(save -> {
			// Save listner. Typically value is set to selected App
			// If not happening then get the Object from list of Selected App and set the
			// edited value

		});

		TextField appDefaultParamSearch = new TextField();
		appDefaultParamSearch.setWidth("100%");
		appDefaultParamSearch.setIcon(VaadinIcons.SEARCH);
		appDefaultParamSearch.setStyleName("small inline-icon search");
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
			String valueInLower = valueChange.getValue().toLowerCase();
			ListDataProvider<AppDefaultParam> appDataProvider = (ListDataProvider<AppDefaultParam>) appDefaultParamGrid
					.getDataProvider();
			appDataProvider.setFilter(filter -> {
				String parameterInLower = filter.getParameter().toLowerCase();
				String descriptionInLower = filter.getDescription().toLowerCase();
				String typeLower = filter.getType().name().toLowerCase();
				return descriptionInLower.equals(valueInLower) || parameterInLower.contains(valueInLower)
						|| typeLower.contains(valueInLower);
			});
		});
		Window createParamGridWindow = openAppDefaultParamWindow(appDefaultParamGrid);
		Button createAppDefaultParamGridRow = new Button(VaadinIcons.FOLDER_ADD, click -> {
			appDefaultParamGrid.deselectAll();
			if (createParamGridWindow.getParent() == null)
				UI.getCurrent().addWindow(createParamGridWindow);

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
			if (appDefaultParamGrid.getSelectedItems().isEmpty()) {
				Notification.show("Select any App Default Parameter type to delete", Notification.Type.WARNING_MESSAGE)
						.setDelayMsec(3000);
			} else {
				confirmDeleteAppDefaultParam(appDefaultParamGrid, appDefaultParamSearch);
			}

		});
		deleteAppDefaultParamGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		deleteAppDefaultParamGridRow.setResponsive(true);
		Button clearAllParams = new Button("Clear All", click -> {
			appDefaultParamGrid.setDataProvider(new ListDataProvider<>(Arrays.asList()));
		});
		Button profileDropDown = new Button("Profile", VaadinIcons.CARET_DOWN);
		TextField profileField = new TextField();
		profileField.setEnabled(false);
		Window openProfileWindow = getSmallListWindow(false, profileField);
		profileDropDown.addClickListener(click -> {
			if (openProfileWindow.getParent() == null) {
				UI.getCurrent().addWindow(openProfileWindow);
				ListSelect<Profile> optionList = (ListSelect<Profile>) ((VerticalLayout) openProfileWindow.getContent())
						.getComponent(0);
				if (selectedApp.getProfile() != null)
					optionList.select(selectedApp.getProfile());
			}
		});

		HorizontalLayout appParamHeaderButtonLayout = new HorizontalLayout(clearAllParams, profileDropDown,
				profileField);
		appParamHeaderButtonLayout.setWidth("50%");
		appParamHeaderButtonLayout.setEnabled(false);
		HorizontalLayout appParamSearchLayout = new HorizontalLayout(appDefaultParamSearch);
		appParamSearchLayout.setWidth("100%");
		HorizontalLayout appParamButtonsLayout = new HorizontalLayout(createAppDefaultParamGridRow,
				deleteAppDefaultParamGridRow);
		// appParamButtonsLayout.setWidth("100%");
		HorizontalLayout appParamGridMenuLayout = new HorizontalLayout(appParamSearchLayout, appParamButtonsLayout);
		appParamGridMenuLayout.setWidth("100%");
		// HorizontalLayout appDefaultParamGridMenuLayout = new
		// HorizontalLayout(appDefaultParamSearch,
		// createAppDefaultParamGridRow, editAppDefaultParamGridRow,
		// deleteAppDefaultParamGridRow);

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
		applicationDefaultParametersLayout.addStyleName("applicatioStore-VerticalLayout");
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
							appService.removeApp(appGrid.getSelectedItems().iterator().next());
							appGrid.setDataProvider(appService.getListDataProvider());
							appGrid.getDataProvider().refreshAll();
							appGrid.deselectAll();
							// TODO add Grid reload
						} else {
							// User did not confirm

						}
					}
				});
	}

	private void confirmDeleteAppDefaultParam(Grid appDefaultParamGrid, TextField appSearch) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?", "Ok", "Cancel",
				new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							// Confirmed to continue
							// appDefaultParamService.removeAppDefaultParam((AppDefaultParam)appDefaultParamGrid.getSelectionModel().getFirstSelectedItem().get());
							// Save Form should take care what is deleted
							selectedApp.getAppDefaultParamList().remove((AppDefaultParam) appDefaultParamGrid
									.getSelectionModel().getFirstSelectedItem().get());
							appDefaultParamGrid
									.setDataProvider(new ListDataProvider<>(selectedApp.getAppDefaultParamList()));
							// appService.removeApp(appGrid.getSelectedItems().iterator().next());
							appSearch.clear();
							// TODO add Grid reload
						} else {
							// User did not confirm

						}
					}
				});
	}

	private Window getSmallListWindow(boolean isFileChoose, TextField field) {

		// FIXME: put generics for List items. Also put data in method parameter to
		// populate the list
		ListSelect optionList = new ListSelect<>();
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
				// } else {
				// //set profile
				// selectedApp.setProfile((Profile)selection.getValue());
				// }

			}
		});
		// FIXME: for button caption pass the value full or partial in method paramter
		Button addNewButton, deleteButton;
		if (isFileChoose) {
			optionList.setDataProvider(new ListDataProvider<ApplicationFile>(uploadedFileList));
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
					// FIXME: call delete service of File checking if its instace of file
					optionList.getSelectedItems().iterator().next();
					// delete the file from location and list
				}
			});
		} else {
			optionList.setDataProvider(profileService.getListDataProvider());
			Window createProfileWindow = openProfileWindow(optionList);
			addNewButton = new Button("Add New Profile", click -> {
				if (createProfileWindow.getParent() == null)
					UI.getCurrent().addWindow(createProfileWindow);

			});
			deleteButton = new Button("Delete Profile", click -> {
				if (optionList.getSelectedItems().size() == 1) {
					// FIXME: call delete service of Profile checking if its instace of Profile
					Profile selected = (Profile) optionList.getSelectedItems().iterator().next();
					selectedApp.setProfile(null);
					profileService.removeProfile(selected);
					optionList.setDataProvider(profileService.getListDataProvider());
					field.clear();

				}
			});
		}
		addNewButton.addStyleNames(ValoTheme.BUTTON_SMALL, "v-button-customstyle", ValoTheme.BUTTON_FRIENDLY);
		deleteButton.addStyleNames(ValoTheme.BUTTON_SMALL, "v-button-customstyle", ValoTheme.BUTTON_FRIENDLY);

		VerticalLayout listLayout = new VerticalLayout(optionList, addNewButton, deleteButton);
		listLayout.addStyleName("applicatioStore-ListLayout");
		Window smaillListWindow = new Window("", listLayout);
		smaillListWindow.setWidth(250, Unit.PIXELS);
		smaillListWindow.setHeight(220, Unit.PIXELS);
		// smaillListWindow.setWindowMode(WindowMode.NORMAL);
		smaillListWindow.setClosable(true);
		smaillListWindow.setDraggable(true);
		smaillListWindow.setModal(true);
		smaillListWindow.setResizable(true);
		// smaillListWindow.setPositionX(800);
		// smaillListWindow.setPositionY(150);
		smaillListWindow.center();
		return smaillListWindow;
	}

	private Window openProfileWindow(ListSelect optionList) {
		Window profileWindow = new Window("Add Profile");
		TextField profileName = new TextField("Name");
		ComboBox<ProfileType> profileType = new ComboBox<>("Type");
		profileType.setDataProvider(new ListDataProvider<>(Arrays.asList(ProfileType.values())));

		Button saveProfile = new Button("Save", click -> {
			if (profileType.getSelectedItem().isPresent() && StringUtils.isNotEmpty(profileName.getValue())) {
				Profile pToSave = new Profile(profileType.getValue(), profileName.getValue());
				profileService.saveProfile(pToSave);
				optionList.setDataProvider(profileService.getListDataProvider());
				profileWindow.close();
			}
		});
		Button cancelProfile = new Button("Reset", click -> {
			profileName.clear();
			profileType.clear();
		});
		HorizontalLayout buttonLayout = new HorizontalLayout(saveProfile, cancelProfile);
		FormLayout profileFormLayout = new FormLayout(profileName, profileType);

		// Window Setup
		profileWindow.setContent(new VerticalLayout(profileFormLayout, buttonLayout));
		profileWindow.center();
		profileWindow.setModal(true);
		profileWindow.setClosable(true);
		profileWindow.setWidth(30, Unit.PERCENTAGE);
		return profileWindow;
	}

	private Window openAppDefaultParamWindow(Grid<AppDefaultParam> appDefaultParamGrid) {
		Window appDefaultWindow = new Window("Add Default Parameter");
		TextField parameterName = new TextField("Name");
		TextField parameterDescription = new TextField("Description");
		ComboBox<ParameterType> parameterType = new ComboBox<>("Type");
		parameterType.setDataProvider(new ListDataProvider<>(Arrays.asList(ParameterType.values())));
		CheckBox parameterActive = new CheckBox("Active");
		Button saveParameter = new Button("Save", click -> {
			if (StringUtils.isNotEmpty(parameterType.getValue().name())
					&& StringUtils.isNotEmpty(parameterName.getValue())) {
				AppDefaultParam appDefaultParam = new AppDefaultParam(parameterName.getValue(),
						parameterDescription.getValue(), parameterType.getValue(), parameterActive.getValue());
				// appDefaultParamService.saveAppDefaultParam(appDefaultParam);
				selectedApp.getAppDefaultParamList().add(appDefaultParam);
				// appService.saveApp(app);
				appDefaultParamGrid.setDataProvider(new ListDataProvider<>(selectedApp.getAppDefaultParamList()));
				appDefaultWindow.close();
			}
		});
		Button cancelParameter = new Button("Reset", click -> {
			parameterName.clear();
			parameterActive.clear();
			parameterDescription.clear();
			parameterType.clear();
		});
		HorizontalLayout buttonLayout = new HorizontalLayout(saveParameter, cancelParameter);
		FormLayout profileFormLayout = new FormLayout(parameterName, parameterType, parameterDescription,
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
		uploadFile.addStyleName("applicatioStore-UploadButton");
		uploadFile.setReceiver(fileUploadReceiver);

		Window fileUploadWindow = new Window("File Upload", uploadFile);
		UploadInfoWindow uploadInfoWindow = new UploadInfoWindow(uploadFile, lineBreakCounter, fileUploadReceiver,
				optionList, fileUploadWindow);

		uploadFile.addStartedListener(event -> {
			if (uploadInfoWindow.getParent() == null) {
				UI.getCurrent().addWindow(uploadInfoWindow);
				// fileUploadWindow.close();
			}
			uploadInfoWindow.setClosable(false);
		});
		uploadFile.addFinishedListener(event -> uploadInfoWindow.setClosable(true));
		fileUploadWindow.addStyleName("applicatioStore-UploadWindow");
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

		private final ListSelect optionList;

		private UploadInfoWindow(final Upload upload, final LineBreakCounter lineBreakCounter,
				final FileUploadReceiver fileUploadReceiver, final ListSelect optionList, Window uploadWindow) {
			super("Status");
			this.counter = lineBreakCounter;
			this.fileUploadReceiver = fileUploadReceiver;
			this.optionList = optionList;
			addStyleName("upload-info");
			this.uploadWindow = uploadWindow;

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
			uploadedFileList.add(fileUploadReceiver.file);
			if (optionList.getId().equalsIgnoreCase(FILE_CHOOSE_LIST)) {
				optionList.setDataProvider(new ListDataProvider<ApplicationFile>(uploadedFileList));
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
				// Open the file for writing.
				// Application.applicationProperties.getProperty("upload.file.location") not
				// working.
				file = new ApplicationFile("C:/temp" + File.separator + filename);
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
