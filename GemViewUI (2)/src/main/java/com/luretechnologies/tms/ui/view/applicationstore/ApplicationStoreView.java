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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.tms.backend.data.entity.App;
import com.luretechnologies.tms.backend.data.entity.AppDefaultParam;
import com.luretechnologies.tms.backend.data.entity.ApplicationFile;
import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.service.AppService;
import com.luretechnologies.tms.backend.service.MockUserService;
import com.luretechnologies.tms.backend.service.OdometerDeviceService;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.data.provider.ListDataProvider;
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
	private static final String FILE_CHOOSE_LIST = "fileChooseList";
	/**
	 * 
	 */
	private static final long serialVersionUID = -1714335680383962006L;
	public static final String VIEW_NAME = "applicationstore";
	private FormLayout applicationDetailsForm;
	private HorizontalLayout buttonLayout;
	private HorizontalLayout applicationDetailsLabel;
	private HorizontalLayout applicationParamLabel;
	private Grid<AppDefaultParam> appDefaultParamGrid;
	private static List<ApplicationFile> uploadedFileList = new ArrayList<>();

	@Autowired
	public ApplicationStoreView() {

	}

	@Autowired
	private AppService appService;

	@Autowired
	private OdometerDeviceService deviceService;

	@Autowired
	private MockUserService userService;

	@PostConstruct
	private void init() {
		setSpacing(false);
		setMargin(false);
		setResponsive(true);
		Panel panel = getAndLoadApplicationStorePanel();
		GridLayout appStoreGridLayout = new GridLayout(2, 2, getAppStoreComponents());
		appStoreGridLayout.setWidth("100%");
		appStoreGridLayout.setMargin(true);
		appStoreGridLayout.addStyleName("applicatioStore-GridLayout");
		panel.setContent(appStoreGridLayout);

		Page.getCurrent().addBrowserWindowResizeListener(r -> {
			System.out.println("Height " + r.getHeight() + "Width:  " + r.getWidth() + " in pixel");
			if (r.getWidth() <= 1450 && r.getWidth() >= 700) {
				// tabMode();
				appStoreGridLayout.removeAllComponents();
				appStoreGridLayout.setColumns(1);
				appStoreGridLayout.setRows(4);
				appStoreGridLayout.addComponents(getAppStoreComponents());
			} else if (r.getWidth() <= 699 && r.getWidth() > 0) {
				// phoneMode();

			} else {
				// desktopMode();
			}
		});
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

		return new VerticalLayout();
	}

	private VerticalLayout getApplicationListLayout() {
		Grid<App> appGrid = new Grid<>(App.class);
		appGrid.setWidth("100%");
		appGrid.setDataProvider(appService.getListDataProvider());
		appGrid.setColumns("packageName", "file", "packageVersion");
		appGrid.getColumn("packageName").setCaption("Name");
		appGrid.getColumn("file").setCaption("File");
		appGrid.getColumn("packageVersion").setCaption("Version");
		appGrid.addColumn("active").setCaption("Active");
		// appGrid.addColumn(c -> c.isActive() ? VaadinIcons.CHECK.getHtml() :
		// VaadinIcons.CLOSE_CIRCLE_O.getHtml(),
		// new HtmlRenderer()).setCaption("Active");
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
		});
		createAppGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		createAppGridRow.setResponsive(true);
		Button editAppGridRow = new Button(VaadinIcons.PENCIL, click -> {
			setApplicationFormComponentsEnable(true);

		});
		editAppGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		editAppGridRow.setResponsive(true);

		Button deleteAppGridRow = new Button(VaadinIcons.TRASH, clicked -> {
			if (appGrid.getSelectedItems().isEmpty()) {
				Notification.show("Select any App type to delete", Notification.Type.WARNING_MESSAGE)
						.setDelayMsec(3000);
			} else {
				confirmDeleteApp(appGrid, applicationSearch);
			}

		});
		deleteAppGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		deleteAppGridRow.setResponsive(true);
		HorizontalLayout appSearchLayout = new HorizontalLayout(applicationSearch);
		appSearchLayout.setWidth("100%");
		HorizontalLayout appButtonsLayout = new HorizontalLayout(createAppGridRow, editAppGridRow,
				deleteAppGridRow);
		//appButtonsLayout.setWidth("100%");
		HorizontalLayout appGridMenuLayout = new HorizontalLayout(appSearchLayout, appButtonsLayout);
		appGridMenuLayout.setWidth("100%");
		VerticalLayout applicationListLayout = new VerticalLayout(appGridMenuLayout, appGrid);
		applicationListLayout.addStyleName("applicatioStore-VerticalLayout");
		appGrid.addSelectionListener(selection -> {
			if (selection.getFirstSelectedItem().isPresent()) {
				App selectedItem = selection.getFirstSelectedItem().get();
				((TextField) applicationDetailsForm.getComponent(0)).setValue(selectedItem.getPackageName());
				((TextField) applicationDetailsForm.getComponent(1)).setValue(selectedItem.getFile());
				((TextField) applicationDetailsForm.getComponent(3)).setValue(selectedItem.getPackageVersion());
				((ComboBox) applicationDetailsForm.getComponent(4)).setValue(selectedItem.getOwner());
				((ComboBox) applicationDetailsForm.getComponent(5)).setValue(selectedItem.getDevice());
				((CheckBox) applicationDetailsForm.getComponent(6)).setValue(selectedItem.isActive());
				appDefaultParamGrid.setDataProvider(new ListDataProvider<>(selectedItem.getAppDefaultParamList()));

			} else {
				((TextField) applicationDetailsForm.getComponent(0)).clear();
				((TextField) applicationDetailsForm.getComponent(1)).clear();
				((TextField) applicationDetailsForm.getComponent(3)).clear();
				((ComboBox) applicationDetailsForm.getComponent(4)).clear();
				((ComboBox) applicationDetailsForm.getComponent(5)).clear();
				((CheckBox) applicationDetailsForm.getComponent(6)).clear();
				appDefaultParamGrid.setDataProvider(new ListDataProvider<>(Arrays.asList()));
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
			if (component.getId() == null || !component.getId().equalsIgnoreCase(PACKAGE_FILE))
				component.setEnabled(flag);
		}
		buttonLayout.getComponent(0).setEnabled(flag);
		buttonLayout.getComponent(1).setEnabled(flag);
	}

	private VerticalLayout getAppicationDetailsLayout() {

		TextField packageName = new TextField("Application Package<br/>Name");
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
		Button fileButton = new Button("Files", VaadinIcons.CARET_DOWN);
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
		ComboBox<User> applicationOwner = new ComboBox<User>("Application <br/>Owner");
		applicationOwner.setEnabled(false);
		applicationOwner.setCaptionAsHtml(true);
		applicationOwner.setDataProvider(new ListDataProvider<>(userService.getUsers()));
		ComboBox<Devices> devices = new ComboBox<Devices>("Device");
		devices.setDataProvider(deviceService.getListDataProvider());
		devices.setEnabled(false);
		// devices.addSelectionListener(selection -> {
		//
		// });
		CheckBox activeApplication = new CheckBox("Application Available", false);
		activeApplication.setEnabled(false);
		Button saveForm = new Button("Save", click -> {
			// TODO: get all the info and save it
		});
		saveForm.setEnabled(false);
		saveForm.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY);
		Button cancelForm = new Button("Cancel", click -> {
			packageName.clear();
			packageFile.clear();
			packageVersion.clear();

		});
		cancelForm.setEnabled(false);
		cancelForm.addStyleNames("v-button-customstyle", ValoTheme.BUTTON_FRIENDLY);
		HorizontalLayout appDetailsSaveCancleAndLabelLayout = new HorizontalLayout();
		appDetailsSaveCancleAndLabelLayout.setWidth("100%");
		applicationDetailsForm = new FormLayout(packageName, packageFile, fileButton, packageVersion, applicationOwner,
				devices, activeApplication);
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
		VerticalLayout applicationDetailsLayout = new VerticalLayout(appDetailsSaveCancleAndLabelLayout, applicationDetailsForm);
		applicationDetailsLayout.addStyleName("applicatioStore-VerticalLayout");
		applicationDetailsLayout.setComponentAlignment(appDetailsSaveCancleAndLabelLayout, Alignment.TOP_RIGHT);
		return applicationDetailsLayout;
	}

	private VerticalLayout getApplicationDefaulParametersLayout() {
		appDefaultParamGrid = new Grid<>(AppDefaultParam.class);
		appDefaultParamGrid.setColumns("parameter", "description", "type", "active");

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
		Button createAppDefaultParamGridRow = new Button(VaadinIcons.FOLDER_ADD, click -> {
			appDefaultParamGrid.deselectAll();
		});
		createAppDefaultParamGridRow.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		createAppDefaultParamGridRow.setResponsive(true);
		Button editAppDefaultParamGridRow = new Button(VaadinIcons.PENCIL, click -> {

		});
		editAppDefaultParamGridRow.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		editAppDefaultParamGridRow.setResponsive(true);

		Button deleteAppDefaultParamGridRow = new Button(VaadinIcons.TRASH, clicked -> {
			if (appDefaultParamGrid.getSelectedItems().isEmpty()) {
				Notification.show("Select any App type to delete", Notification.Type.WARNING_MESSAGE)
						.setDelayMsec(3000);
			} else {
				confirmDeleteAppDefaultParam(appDefaultParamGrid, appDefaultParamSearch);
			}

		});
		deleteAppDefaultParamGridRow.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		deleteAppDefaultParamGridRow.setResponsive(true);
		HorizontalLayout appDefaultParamGridMenuLayout = new HorizontalLayout(appDefaultParamSearch,
				createAppDefaultParamGridRow, editAppDefaultParamGridRow, deleteAppDefaultParamGridRow);
		
		applicationParamLabel = new HorizontalLayout();
		applicationParamLabel.setWidth("100%");
		//applicationDefaultParametersLayout.setCaption("<b>Application Default Parameters</b>");
		Label appParamLabel = new Label("Application Default Parameters");
		appParamLabel.addStyleName("label-style");
		appParamLabel.addStyleNames(ValoTheme.LABEL_BOLD, ValoTheme.LABEL_H3);
		applicationParamLabel.addComponent(appParamLabel);
		
		VerticalLayout applicationDefaultParametersLayout = new VerticalLayout(applicationParamLabel, appDefaultParamGridMenuLayout,
				appDefaultParamGrid);
		applicationDefaultParametersLayout.setCaptionAsHtml(true);
		applicationDefaultParametersLayout.addStyleName("applicatioStore-VerticalLayout");
		return applicationDefaultParametersLayout;
	}

	private void confirmDeleteApp(Grid appGrid, TextField appSearch) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?", "Ok", "Cancel",
				new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							// Confirmed to continue
							// appService.removeApp(appGrid.getSelectedItems().iterator().next());
							appSearch.clear();
							setApplicationFormComponentsEnable(false);
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
				field.setValue(selection.getValue().toString());
			}
		});
		// FIXME: for button caption pass the value full or partial in method paramter
		Button addNewButton, deleteButton;
		if (isFileChoose) {
			optionList.setDataProvider(new ListDataProvider<ApplicationFile>(uploadedFileList));
			optionList.setId(FILE_CHOOSE_LIST);
			addNewButton = new Button("Add New File", click -> {
				Window fileUpload = openFileUploadWindow(optionList);
				if (fileUpload.getParent() == null)
					UI.getCurrent().addWindow(fileUpload);
			});
			deleteButton = new Button("Delete File", click -> {
				if (optionList.getSelectedItems().size() == 1) {
					// FIXME: call delete service of File checking if its instace of file
					optionList.getSelectedItems().iterator().next();
				}
			});
		} else {
			addNewButton = new Button("Add New Profile", click -> openFileUploadWindow(optionList));
			deleteButton = new Button("Delete Profile", click -> {
				if (optionList.getSelectedItems().size() == 1) {
					// FIXME: call delete service of Profile checking if its instace of Profile
					optionList.getSelectedItems().iterator().next();
				}
			});
		}
		addNewButton.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL);
		deleteButton.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL);

		VerticalLayout listLayout = new VerticalLayout(optionList, addNewButton, deleteButton);
		Window smaillListWindow = new Window("", listLayout);
		smaillListWindow.setWidth(250, Unit.PIXELS);
		smaillListWindow.setHeight(271, Unit.PIXELS);
		smaillListWindow.setClosable(true);
		smaillListWindow.setDraggable(false);
		smaillListWindow.setResizable(false);
		smaillListWindow.setPositionX(800);
		smaillListWindow.setPositionY(150);
		return smaillListWindow;
	}

	private Window openFileUploadWindow(ListSelect optionList) {
		LineBreakCounter lineBreakCounter = new LineBreakCounter();
		lineBreakCounter.setSlow(true);

		FileUploadReceiver fileUploadReceiver = new FileUploadReceiver();

		Upload uploadFile = new Upload("Upload File here", lineBreakCounter);
		uploadFile.setImmediateMode(false);
		uploadFile.setButtonCaption("Upload File");
		uploadFile.setReceiver(fileUploadReceiver);

		UploadInfoWindow uploadInfoWindow = new UploadInfoWindow(uploadFile, lineBreakCounter, fileUploadReceiver,
				optionList);

		uploadFile.addStartedListener(event -> {
			if (uploadInfoWindow.getParent() == null) {
				UI.getCurrent().addWindow(uploadInfoWindow);
			}
			uploadInfoWindow.setClosable(false);
		});
		uploadFile.addFinishedListener(event -> uploadInfoWindow.setClosable(true));
		Window fileUploadWindow = new Window("File Upload", uploadFile);
		fileUploadWindow.setWidth(80, Unit.PERCENTAGE);
		fileUploadWindow.setClosable(true);
		fileUploadWindow.setDraggable(false);
		fileUploadWindow.setResizable(true);
		fileUploadWindow.setModal(true);
		fileUploadWindow.center();
		return fileUploadWindow;
	}

	@StyleSheet("uploadexample.css")
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

		private final ListSelect optionList;

		private UploadInfoWindow(final Upload upload, final LineBreakCounter lineBreakCounter,
				final FileUploadReceiver fileUploadReceiver, final ListSelect optionList) {
			super("Status");
			this.counter = lineBreakCounter;
			this.fileUploadReceiver = fileUploadReceiver;
			this.optionList = optionList;
			addStyleName("upload-info");

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
				//Application.applicationProperties.getProperty("upload.file.location")  not working.
				file = new ApplicationFile("C:/temp"+ File.separator
						+ filename);
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
