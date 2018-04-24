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
package com.luretechnologies.tms.ui.view.devices;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.Roles;
import com.luretechnologies.tms.ui.components.ConfirmDialogFactory;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = DevicesView.VIEW_NAME)
public class DevicesView extends VerticalLayout implements Serializable, View{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String VIEW_NAME = "devices";
	
	Map<Integer, Devices> devicesRepo = new LinkedHashMap<>();
	Grid<Devices> devicesGrid = new Grid<Devices>();
	private volatile Devices selectedDevice;
	private TextField deviceDescription;
	private TextField deviceName;
	private CheckBox activeBox;
	private CheckBox rkiBox;
	private CheckBox osBox;
	private TextField deviceManufacturer;
	private Label activeLabel;
	private Label rkiLabel;
	private Label osLabel;
	
	@Autowired
	public ConfirmDialogFactory confirmDialogFactory;
	
	@Autowired
	public DevicesView() {
		selectedDevice = new Devices();
		
		Devices device = new Devices();
		device.setActive(true);
		device.setRki(true);
		device.setOsUpdate(false);
		device.setDescription("PINPAD1");
		device.setDeviceName("QQ90001");
		device.setManufacturer("IDTECH1");
		device.setIdnumber(1);
		
		Devices device1 = new Devices();
		device1.setActive(true);
		device1.setRki(false);
		device1.setOsUpdate(true);
		device1.setDescription("PINPAD2");
		device1.setDeviceName("QQ90002");
		device1.setManufacturer("IDTECH2");
		device1.setIdnumber(2);
		
		Devices device2 = new Devices();
		device2.setActive(true);
		device2.setRki(true);
		device2.setOsUpdate(false);
		device2.setDescription("PINPAD3");
		device2.setDeviceName("QQ90003");
		device2.setManufacturer("IDTECH3");
		device2.setIdnumber(3);
		
		Devices device3 = new Devices();
		device3.setActive(false);
		device3.setRki(true);
		device3.setOsUpdate(false);
		device3.setDescription("PINPAD4");
		device3.setDeviceName("QQ90004");
		device3.setManufacturer("IDTECH4");
		device3.setIdnumber(4);
		
		
		devicesRepo.put(device.getIdnumber(), device);
		devicesRepo.put(device1.getIdnumber(), device1);
		devicesRepo.put(device2.getIdnumber(), device2);
		devicesRepo.put(device3.getIdnumber(), device3);
		
	}
	
	@PostConstruct
	private void inti() {
		setHeight("100%");
		setSpacing(false);
		setMargin(false);
		setResponsive(true);
		Panel panel = getAndLoadDevicePanel();
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSpacing(false);
		verticalLayout.setMargin(false);
		Label availableDevices = new Label("Available Devices", ContentMode.HTML);
		availableDevices.addStyleName("label-style");
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSizeFull();
		
		HorizontalLayout layout1 = new HorizontalLayout();
		layout1.addComponent(availableDevices);
		layout1.setComponentAlignment(availableDevices, Alignment.MIDDLE_LEFT);
		layout1.setSizeFull();
		
		HorizontalLayout layout2 = new HorizontalLayout();
		layout2.setSizeUndefined();
		layout2.setResponsive(true);
		
		verticalLayout.addComponent(horizontalLayout);
		VerticalLayout deviceInfoLayout = new VerticalLayout();
		verticalLayout.addComponent(deviceInfoLayout);
		deviceInfoLayout.setMargin(false);
		deviceInfoLayout.setSpacing(true);
		deviceInfoLayout.setStyleName("form-layout");
		
		getAndLoadDeviceForm(deviceInfoLayout, false);
		
		Button cancel = new Button("Cancel");
		cancel.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		cancel.addStyleName("v-button-customstyle");
		cancel.setResponsive(true);
		cancel.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {	
				deviceInfoLayout.removeAllComponents();
				devicesGrid.getDataProvider().refreshAll();
				devicesGrid.deselectAll();
				selectedDevice = new Devices();
				getAndLoadDeviceForm(deviceInfoLayout, false);	
			}
		});
		layout2.addComponent(cancel);
		
		Button save = new Button("Save");
		save.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		save.addStyleName("v-button-customstyle");
		save.setResponsive(true);
		save.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				String description = deviceDescription.getValue();
				String devicename = deviceName.getValue();
				String deviceManfactr = deviceManufacturer.getValue();
				boolean activeValue = activeBox.getValue();
				boolean rkiValue = rkiBox.getValue();
				boolean osUpdateValue = osBox.getValue();
				selectedDevice.setDescription(description);
				selectedDevice.setDeviceName(devicename);
				selectedDevice.setManufacturer(deviceManfactr);
				selectedDevice.setActive(activeValue);
				selectedDevice.setRki(rkiValue);
				selectedDevice.setOsUpdate(osUpdateValue);
				if(description.isEmpty() || description== null|| devicename.isEmpty() || devicename== null 
						|| deviceManfactr.isEmpty() || deviceManfactr== null) {
					Notification.show("Fill all details", Notification.Type.WARNING_MESSAGE).setDelayMsec(3000);
				} else {
					Random rand = new Random();
					int  n = rand.nextInt(50) + 1;
					if(selectedDevice.getIdnumber()==null) {
						selectedDevice.setIdnumber(n);
					}
				devicesRepo.put(selectedDevice.getIdnumber(), selectedDevice);
				devicesGrid.getDataProvider().refreshAll();
				devicesGrid.select(selectedDevice);
				deviceInfoLayout.removeAllComponents();
				getAndLoadDeviceForm(deviceInfoLayout, false);
				}
			}
		});
		
		layout2.addComponent(save);
		layout2.setComponentAlignment(cancel, Alignment.MIDDLE_RIGHT);
		layout2.setComponentAlignment(save, Alignment.MIDDLE_RIGHT);
		layout2.setResponsive(true);
		layout2.setStyleName("save-cancelButtonsAlignment");
		
		horizontalLayout.addComponents(layout1, layout2);
		horizontalLayout.setComponentAlignment(layout2, Alignment.MIDDLE_RIGHT);
		
		getDevicesGrid(verticalLayout, deviceInfoLayout);
		panel.setContent(verticalLayout);
	}
	
	public Panel getAndLoadDevicePanel() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setCaption("Devices");
		panel.setResponsive(true);
		panel.setSizeFull();
        addComponent(panel);
       return panel;
	}
	
	public void getAndLoadDeviceForm(VerticalLayout verticalLayout, boolean isEditableOnly) {
		FormLayout formLayout = new FormLayout();
		
		CssLayout osUpdateLayout = new CssLayout();
		
		CssLayout rkiLayout = new CssLayout();
		
		CssLayout activeLayout = new CssLayout(); 
		
		getDeviceNameWithActiceCheck(formLayout, isEditableOnly);
		
		getDescription(formLayout, isEditableOnly);
		
		getManufacturer(formLayout, isEditableOnly);
		
		getActiveCheck(formLayout, activeLayout, isEditableOnly);
		
		getRKICapable(formLayout, rkiLayout, isEditableOnly);
		
		getOSUpdate(formLayout, osUpdateLayout, isEditableOnly);
		
		verticalLayout.addComponent(formLayout);
		
		verticalLayout.addComponent(activeLayout);
		
		verticalLayout.addComponent(rkiLayout);
		
		verticalLayout.addComponent(osUpdateLayout);
	}
	
	private void getDescription(FormLayout formLayout, boolean isEditableOnly) {
		String description = selectedDevice.getDescription() != null ? selectedDevice.getDescription(): "";
		deviceDescription = new TextField("Description", description);
		selectedDevice.setDescription(deviceDescription.getValue());
		deviceDescription.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		deviceDescription.setWidth("48%");
		deviceDescription.setStyleName("role-textbox");
		deviceDescription.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		deviceDescription.addStyleName("v-textfield-font");
		deviceDescription.setEnabled(isEditableOnly);
		formLayout.addComponent(deviceDescription);
	}
	
	private void getManufacturer(FormLayout formLayout, boolean isEditableOnly) {
		String manufacturer = selectedDevice.getManufacturer() != null ? selectedDevice.getManufacturer(): "";
		deviceManufacturer = new TextField("Manufacturer", manufacturer);
		selectedDevice.setManufacturer(deviceManufacturer.getValue());
		deviceManufacturer.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		deviceManufacturer.setWidth("48%");
		deviceManufacturer.setStyleName("role-textbox");
		deviceManufacturer.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		deviceManufacturer.addStyleName("v-textfield-font");
		deviceManufacturer.setEnabled(isEditableOnly);
		formLayout.addComponent(deviceManufacturer);
	}
	
	private void getDeviceNameWithActiceCheck(FormLayout formLayout, boolean isEditableOnly) {
		String device = selectedDevice.getDeviceName() != null ? selectedDevice.getDeviceName(): "";
		deviceName = new TextField("Device Name", device);
		deviceName.setValue(device);
		deviceName.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		deviceName.setWidth("48%");
		deviceName.setStyleName("role-textbox");
		deviceName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		deviceName.addStyleName("v-textfield-font");
		selectedDevice.setDeviceName(deviceName.getValue());
		deviceName.setEnabled(isEditableOnly);
		
//		boolean activeBoxValue = selectedDevice.isActive();
//		activeBox = new CheckBox("Allow Access", activeBoxValue);
//		activeBox.addStyleName("v-textfield-font");
//		activeBox.setEnabled(isEditableOnly);
//		selectedDevice.setActive(activeBox.getValue());
//		activeLabel = new Label("Active");
//		activeLabel.setStyleName("role-activeLable");
//		activeLabel.addStyleName("v-textfield-font");
//		
//		HorizontalLayout deviceActiveHL = new HorizontalLayout();
//		FormLayout deviceNameFL = new FormLayout();
//		HorizontalLayout activeCheckHL = new HorizontalLayout();
//		deviceActiveHL.setWidth("100%");
//		deviceActiveHL.setSpacing(false);
//		deviceActiveHL.setMargin(false);
//		deviceNameFL.addComponent(deviceName);
//		deviceNameFL.setComponentAlignment(deviceName, Alignment.MIDDLE_LEFT);
//		deviceNameFL.setSpacing(false);
//		deviceNameFL.setMargin(false);
//		deviceNameFL.setStyleName("device-name-layout");
//		activeCheckHL.setSpacing(false);
//		activeCheckHL.setMargin(false);
//		activeCheckHL.addComponent(activeLabel);
//		activeCheckHL.addComponent(activeBox);
//		activeCheckHL.setSizeUndefined();
//		deviceActiveHL.addComponent(deviceNameFL);
//		deviceActiveHL.addComponent(activeCheckHL);
//		deviceActiveHL.setComponentAlignment(deviceNameFL, Alignment.MIDDLE_LEFT);
//		deviceActiveHL.setComponentAlignment(activeCheckHL, Alignment.MIDDLE_LEFT);
		formLayout.addComponent(deviceName);
	}
	
	private void getActiveCheck(FormLayout formLayout, CssLayout activeLayout, boolean isEditableOnly) {
		boolean activeBoxValue = selectedDevice.isActive();
		activeBox = new CheckBox("Allow Access", activeBoxValue);
		activeBox.addStyleName("v-textfield-font");
		activeBox.setEnabled(isEditableOnly);
		selectedDevice.setActive(activeBox.getValue());
		activeLabel = new Label("Active");
		activeLabel.setStyleName("role-activeLable");
		activeLabel.addStyleName("v-textfield-font");
		
		HorizontalLayout activeCheckHL = new HorizontalLayout();
		activeCheckHL.setSpacing(false);
		activeCheckHL.setMargin(false);
		activeCheckHL.addComponent(activeLabel);
		activeCheckHL.addComponent(activeBox);
		activeCheckHL.setSizeUndefined();
		activeCheckHL.setStyleName("role-activeLable");
		activeCheckHL.addStyleName("device-activeButton-Layout");
		activeLayout.addComponent(activeCheckHL);
	}
	
	private void getRKICapable(FormLayout formLayout, CssLayout rkiLayout, boolean isEditableOnly) {
		HorizontalLayout rkiCapableHL = new HorizontalLayout();
		
		boolean rkiBoxValue = selectedDevice.isRki();
		rkiBox = new CheckBox("Device is RKICapable (Remote Key Injection)", rkiBoxValue);
		rkiBox.setEnabled(isEditableOnly);
		rkiBox.addStyleName("v-textfield-font");
		selectedDevice.setRki(rkiBox.getValue());
		rkiLabel = new Label("RKI Capable");
		rkiLabel.addStyleName("v-textfield-font");
		rkiCapableHL.addComponent(rkiLabel);
		rkiCapableHL.addComponent(rkiBox);
		rkiCapableHL.setSizeUndefined();
		rkiCapableHL.setStyleName("role-activeLable");
		rkiLayout.addComponent(rkiCapableHL);
	}
	
	private void getOSUpdate(FormLayout formLayout, CssLayout osUpdateLayout, boolean isEditableOnly) {
		HorizontalLayout osUpdateHL = new HorizontalLayout();
		
		boolean osBoxValue = selectedDevice.isOsUpdate();
		osBox = new CheckBox("Device can accept O/S Update",osBoxValue);
		osBox.addStyleName("v-textfield-font");
		osBox.setEnabled(isEditableOnly);
		selectedDevice.setOsUpdate(osBox.getValue());
		osLabel = new Label("O/S Update");
		osLabel.addStyleName("v-textfield-font");
		osUpdateHL.addComponent(osLabel);
		osUpdateHL.addComponent(osBox);
		osUpdateHL.setStyleName("role-activeLable");
		osUpdateHL.setSizeUndefined();
		osUpdateLayout.addComponent(osUpdateHL);
	}
	
	private void getDevicesGrid(VerticalLayout verticalLayout, VerticalLayout deviceInfoLayout) {
		Button addNewDevice = new Button(VaadinIcons.FOLDER_ADD);
		addNewDevice.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		addNewDevice.addStyleName("v-button-customstyle");
		addNewDevice.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				devicesGrid.deselectAll();
				deviceInfoLayout.removeAllComponents();
				selectedDevice = new Devices();
				getAndLoadDeviceForm(deviceInfoLayout, true);	
				deviceName.focus();
			}
		});
		
		Button editDevice = new Button(VaadinIcons.EDIT);
		editDevice.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		editDevice.addStyleName("v-button-customstyle");
		editDevice.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				if(deviceDescription.getValue()==null || deviceDescription.getValue().isEmpty() || 
						deviceName.getValue()==null ||  deviceName.getValue().isEmpty() ||
								deviceManufacturer.getValue()==null ||  deviceManufacturer.getValue().isEmpty()) {
					Notification.show("Select any Role to Modify", Notification.Type.WARNING_MESSAGE).setDelayMsec(3000);;
				}else {
					deviceInfoLayout.removeAllComponents();
					getAndLoadDeviceForm(deviceInfoLayout, true);	
					deviceName.focus();
				}
			}
		});
		
		Button deleteDevice = new Button(VaadinIcons.TRASH);
		deleteDevice.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		deleteDevice.addStyleName("v-button-customstyle");
		deleteDevice.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(deviceDescription.getValue()==null || deviceDescription.getValue().isEmpty() || 
						deviceName.getValue()==null ||  deviceName.getValue().isEmpty() ||
						deviceManufacturer.getValue()==null ||  deviceManufacturer.getValue().isEmpty()) {
					Notification.show("Select role to delete", Notification.Type.ERROR_MESSAGE);
				}else {
				
				confirmDialog( deviceInfoLayout);
				}
			}
		});
		
		HorizontalLayout buttonGroup =  new HorizontalLayout();
		buttonGroup.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		buttonGroup.addComponent(addNewDevice);
		buttonGroup.addComponent(editDevice);
		buttonGroup.addComponent(deleteDevice);
		
		devicesGrid.setCaptionAsHtml(true);
		devicesGrid.setHeightByRows(5);
		devicesGrid.addColumn(Devices::getDeviceName).setCaption("Device Name");
		devicesGrid.addColumn(Devices::getDescription).setCaption("Description");
		devicesGrid.addColumn(Devices:: isActive).setCaption("Active");
		
		devicesGrid.setItems(devicesRepo.values());
		devicesGrid.setWidth("100%");
		devicesGrid.setResponsive(true);
		
		devicesGrid.addSelectionListener(e -> {
			if (!e.isUserOriginated()) {
				return;
			}

			if (e.getFirstSelectedItem().isPresent()) {
				//Load and update the data in permission grid.
				deviceInfoLayout.removeAllComponents();
				Devices selectedDevice = e.getFirstSelectedItem().get();
				this.selectedDevice = selectedDevice;
				getAndLoadDeviceForm(deviceInfoLayout, false);
			} else {
				throw new IllegalStateException("This should never happen as deselection is not allowed");
			}
		});
		VerticalLayout deviceGridLayout = new VerticalLayout();
		deviceGridLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		deviceGridLayout.addComponent(buttonGroup);
		deviceGridLayout.addComponent(devicesGrid);
		verticalLayout.addComponent(deviceGridLayout);
		
	}
	
	private void confirmDialog(VerticalLayout deviceInfoLayout) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?",
		        "Ok", "Cancel", new ConfirmDialog.Listener() {

		            public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		                    // Confirmed to continue
		                	deviceInfoLayout.removeAllComponents();
		                	devicesRepo.remove(selectedDevice.getDeviceName());
		                	devicesGrid.getDataProvider().refreshAll();
		                	selectedDevice = new Devices();
		                	getAndLoadDeviceForm(deviceInfoLayout, false);
		                } else {
		                    // User did not confirm
		                    
		                }
		            }
		        });
	}
	

}
