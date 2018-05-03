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

import java.io.OutputStream;
import java.io.Serializable;
import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.tms.backend.data.entity.App;
import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.service.AppService;
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
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = ApplicationStoreView.VIEW_NAME)
public class ApplicationStoreView extends VerticalLayout implements Serializable, View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1714335680383962006L;
	public static final String VIEW_NAME = "applicationstore";
	private FormLayout applicationDetailsForm;
	private HorizontalLayout buttonLayout;

	@Autowired
	public ApplicationStoreView() {

	}

	@Autowired
	private AppService appService;
	
	@Autowired
	private OdometerDeviceService deviceService;

	@PostConstruct
	private void init() {

		Page.getCurrent().addBrowserWindowResizeListener(r -> {
			System.out.println("Height " + r.getHeight() + "Width:  " + r.getWidth() + " in pixel");
			if (r.getWidth() <= 1450 && r.getWidth() >= 700) {
				// tabMode();
			} else if (r.getWidth() <= 699 && r.getWidth() > 0) {
				// phoneMode();

			} else {
				// desktopMode();
			}
		});

		setSpacing(false);
		setMargin(false);
		setResponsive(true);
		Panel panel = getAndLoadApplicationStorePanel();
		GridLayout appStoreGridLayout = new GridLayout(2, 2, getAppStoreComponents());
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
		Component[] components = { getApplicationListLayout(), getAppicationDetailsLayout(),
				getApplicationDefaulParametersLayout() };
		return components;
	}

	private VerticalLayout getApplicationListLayout() {
		Grid<App> appGrid = new Grid<>(App.class);
		appGrid.setDataProvider(appService.getListDataProvider());
		appGrid.setColumns("packageName", "file", "packageVersion");
		appGrid.getColumn("packageName").setCaption("Name");
		appGrid.getColumn("file").setCaption("File");
		appGrid.getColumn("packageVersion").setCaption("Version");
		appGrid.addColumn(c -> c.isActive() ? VaadinIcons.CHECK.getHtml() : VaadinIcons.CLOSE_CIRCLE_O.getHtml(),
				new HtmlRenderer()).setCaption("Active");
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
		createAppGridRow.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		createAppGridRow.setResponsive(true);
		Button editAppGridRow = new Button(VaadinIcons.PENCIL, click -> {
			setApplicationFormComponentsEnable(true);
			
		});
		editAppGridRow.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		editAppGridRow.setResponsive(true);

		Button deleteAppGridRow = new Button(VaadinIcons.TRASH, clicked -> {
			if (appGrid.getSelectedItems().isEmpty()) {
				Notification.show("Select any App type to delete", Notification.Type.WARNING_MESSAGE)
						.setDelayMsec(3000);
			} else {
				confirmDeleteApp(appGrid, applicationSearch);
			}

		});
		deleteAppGridRow.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		deleteAppGridRow.setResponsive(true);
		HorizontalLayout appGridMenuLayout = new HorizontalLayout(applicationSearch, createAppGridRow, editAppGridRow,
				deleteAppGridRow);
		VerticalLayout applicationListLayout = new VerticalLayout(appGridMenuLayout, appGrid);
		appGrid.addSelectionListener(selection->{
			if(selection.getFirstSelectedItem().isPresent()) {
				App selectedItem = selection.getFirstSelectedItem().get();
				((TextField)applicationDetailsForm.getComponent(0)).setValue(selectedItem.getPackageName());
				((TextField)applicationDetailsForm.getComponent(1)).setValue(selectedItem.getFile());
				((TextField)applicationDetailsForm.getComponent(3)).setValue(selectedItem.getPackageVersion());
				((CheckBox)applicationDetailsForm.getComponent(6)).setValue(selectedItem.isActive());
			}else {
				((TextField)applicationDetailsForm.getComponent(0)).clear();
				((TextField)applicationDetailsForm.getComponent(1)).clear();
				((TextField)applicationDetailsForm.getComponent(3)).clear();
				((CheckBox)applicationDetailsForm.getComponent(6)).clear();
			}
		});
		return applicationListLayout;
	}

	/**
	 * 
	 */
	private void setApplicationFormComponentsEnable(boolean flag) {
		int count = applicationDetailsForm.getComponentCount();
		for (int i = 0;i<count ; i++) {
			Component component = applicationDetailsForm.getComponent(i);
			component.setEnabled(flag);
		}
		buttonLayout.getComponent(0).setEnabled(flag);
		buttonLayout.getComponent(1).setEnabled(flag);
	}

	private Component getAppicationDetailsLayout() {

		TextField packageName = new TextField("Application Package<br/>Name");
		packageName.addStyleNames("role-textbox", "v-grid-cell",ValoTheme.TEXTFIELD_BORDERLESS);
		packageName.setCaptionAsHtml(true);
		packageName.setEnabled(false);
		TextField packageFile = new TextField("Package File");
		packageFile.addStyleNames("role-textbox", "v-grid-cell",ValoTheme.TEXTFIELD_BORDERLESS);
		packageFile.setEnabled(false);
		Window fileListWindow = getSmallListWindow(true,packageFile);
		Button fileButton = new Button("Files", VaadinIcons.CARET_DOWN);
		fileButton.setEnabled(false);
		fileButton.addClickListener(click->{
			if(fileListWindow.getParent() == null)
				UI.getCurrent().addWindow(fileListWindow);
		});
		TextField packageVersion = new TextField("Version");
		packageVersion.addStyleNames("role-textbox", "v-grid-cell",ValoTheme.TEXTFIELD_BORDERLESS);
		packageVersion.setEnabled(false);
		//FIXME: put the list of organization
		ComboBox<String> applicationOwner = new ComboBox<String>("Application <br/>Owner");
		applicationOwner.setEnabled(false);
		applicationOwner.setCaptionAsHtml(true);
		ComboBox<Devices> devices = new ComboBox<Devices>("Device");
		devices.setDataProvider(deviceService.getListDataProvider());
		devices.setEnabled(false);
		CheckBox activeApplication = new CheckBox("Application Available", false);
		activeApplication.setEnabled(false);
		Button saveForm = new Button("Save", click -> {

		});
		saveForm.setEnabled(false);
		Button cancelForm = new Button("Cancel", click -> {
			packageName.clear();
			packageFile.clear();
			packageVersion.clear();
			
		});
		cancelForm.setEnabled(false);

		applicationDetailsForm = new FormLayout(packageName,packageFile,fileButton,packageVersion,applicationOwner,devices,activeApplication);
		applicationDetailsForm.setComponentAlignment(fileButton, Alignment.BOTTOM_RIGHT);
		buttonLayout = new HorizontalLayout(cancelForm,saveForm);
		VerticalLayout applicationDetailsLayout = new VerticalLayout(buttonLayout,applicationDetailsForm);
		applicationDetailsLayout.setComponentAlignment(buttonLayout, Alignment.TOP_RIGHT);
		return applicationDetailsLayout;
	}

	private Component getApplicationDefaulParametersLayout() {
		VerticalLayout applicationDefaultParametersLayout = new VerticalLayout();
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

	private Window getSmallListWindow(boolean isFileChoose,TextField field) {

		// FIXME: put generics for List items. Also put data in method parameter to
		// populate the list
		ListSelect optionList = new ListSelect<>();
		optionList.setRows(3);
		optionList.setResponsive(true);
		optionList.setWidth(100, Unit.PERCENTAGE);
		optionList.addSelectionListener(selection->{
			if(selection.getFirstSelectedItem().isPresent()) {
				//FIXME: selection of value
				field.setValue(selection.getValue().toString());
			}
		});
		// FIXME: for button caption pass the value full or partial in method paramter
		Button addNewButton, deleteButton;
		if (isFileChoose) {
			addNewButton = new Button("Add New File", click -> {
				Window fileUpload = openFileUploadWindow();
				if(fileUpload.getParent() == null)
					UI.getCurrent().addWindow(fileUpload);
			});
			deleteButton = new Button("Delete File", click -> {
				if (optionList.getSelectedItems().size() == 1) {
					// FIXME: call delete service of File checking if its instace of file
					optionList.getSelectedItems().iterator().next();
				}
			});
		} else {
			addNewButton = new Button("Add New Profile", click -> openFileUploadWindow());
			deleteButton = new Button("Delete Profile", click -> {
				if (optionList.getSelectedItems().size() == 1) {
					// FIXME: call delete service of Profile checking if its instace of Profile
					optionList.getSelectedItems().iterator().next();
				}
			});
		}

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

	private Window openFileUploadWindow() {
		LineBreakCounter lineBreakCounter = new LineBreakCounter();
		lineBreakCounter.setSlow(true);

		Upload uploadFile = new Upload(null, lineBreakCounter);
		uploadFile.setImmediateMode(false);
		uploadFile.setButtonCaption("Upload File");

		UploadInfoWindow uploadInfoWindow = new UploadInfoWindow(uploadFile, lineBreakCounter);

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

		private UploadInfoWindow(final Upload upload, final LineBreakCounter lineBreakCounter) {
			super("Status");
			this.counter = lineBreakCounter;

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
		 * return an OutputStream that simply counts lineends
		 */
		@Override
		public OutputStream receiveUpload(final String filename, final String MIMEType) {
			counter = 0;
			total = 0;
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
}
