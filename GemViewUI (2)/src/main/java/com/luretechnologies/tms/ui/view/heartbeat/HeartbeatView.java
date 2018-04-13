package com.luretechnologies.tms.ui.view.heartbeat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.ui.view.devices.DevicesData;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = HeartbeatView.VIEW_NAME)
public class HeartbeatView extends VerticalLayout implements Serializable, View {

	/**
	 * 
	 */
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final long serialVersionUID = 4663357355780258257L;
	public static final String VIEW_NAME = "heartbeat";
	private volatile Devices selectedDevice;
	private static TextField entityTypeTextFiled;
	private static TextField deviceDescription;
	private static TextField deviceName;
	private static TextField deviceSerialNumber;
	private static TextField deviceFrequency;
	private static TextField deviceLastSeen;
	private static CheckBox activeBox;
	private static CheckBox activateHeartBeatBox;
	private  List<Devices> deviceList = new LinkedList<Devices>();
	private static Devices emptyDevice = new Devices();
	private static TextField search;
	private static DateTimeField startDateField, endDateField;
	private static Button delete;
	
	
	
	@Autowired
	public HeartbeatView() {
		selectedDevice = new Devices();
		emptyDevice.setManufacturer("");
		emptyDevice.setDeviceName("");
		emptyDevice.setDescription("");
		emptyDevice.setActive(false);
		emptyDevice.setSerialNumber("");
		emptyDevice.setHeartBeat(false);
		emptyDevice.setLastSeen("");
		
		Date date = new Date();
		for (int index=1; index <=30; index++) {
			if(index % 2 == 0) {
				Devices device = new Devices();
				device.setActive(true);
				device.setHeartBeat(true);
				device.setDescription("Device Terminal "+index);
				device.setDeviceName("Device "+index);
				device.setManufacturer("Device Entity "+index );
				device.setSerialNumber(RandomStringUtils.random(10, true, true));
				device.setFrequency("12"+index+" seconds");
				device.setLastSeen(date.toString());
				deviceList.add(device);
				} else {
					Devices device1 = new Devices();
					device1.setActive(false);
					device1.setHeartBeat(false);
					device1.setDescription("Device Terminal "+index);
					device1.setDeviceName("Device "+index);
					device1.setManufacturer("Device Entity "+index );
					device1.setSerialNumber(RandomStringUtils.random(10, true, true));
					device1.setFrequency("12"+index+" seconds");
					device1.setLastSeen(date.toString());
					deviceList.add(device1);
					
				}
		}
	}
	
	@PostConstruct
	private void inti() {
		setSpacing(false);
		setMargin(false);
		setResponsive(true);
		setHeight("100%");
		Panel panel = getAndLoadSystemPanel();
		HorizontalSplitPanel horizontalPanel =  new HorizontalSplitPanel();
		VerticalLayout secondPanelLayout = new VerticalLayout();
		secondPanelLayout.setStyleName("heartbeat-verticalLayout");
		VerticalLayout verticalDeviceFormLayout = new VerticalLayout();
		verticalDeviceFormLayout.setStyleName("heartbeat-verticalFormLayout");
		VerticalLayout HL = new VerticalLayout();
		VerticalLayout VL = new VerticalLayout();
		
		addDeviceLabel(secondPanelLayout);
		getDeviceFromLayout(verticalDeviceFormLayout,emptyDevice, false );
		secondPanelLayout.addComponent(verticalDeviceFormLayout);
		addDeviceHistoryLabel(secondPanelLayout);
		getSearchAndDelete(secondPanelLayout);
		
		horizontalPanel.setHeight("100%");
		List<Button> terminalList = getTerminals(verticalDeviceFormLayout);
		List<HorizontalLayout> HLList = getButtonsLayout(terminalList);
		for(HorizontalLayout hL:HLList) {
			hL.setHeight("60px");;
			VL.addComponents(hL);
		}
		HL.setHeight("100%");
		HL.addComponent(VL);
		horizontalPanel.setFirstComponent(HL);
		horizontalPanel.getFirstComponent().setStyleName("split-height");
		horizontalPanel.addComponent(secondPanelLayout);
		horizontalPanel.setSplitPosition(52, Unit.PERCENTAGE);
		
		panel.setContent(horizontalPanel);
	}
	
	
	public Panel getAndLoadSystemPanel() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setCaption("<h1 style=color:#216C2A;font-weight:bold;>Heartbeat</h1>");
		panel.setResponsive(true);
		panel.setSizeFull();
        addComponent(panel);
       return panel;
	}
	
	private void addDeviceLabel(VerticalLayout layout) {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setStyleName("heartbeat-horizontalLayout-DeviceLabel");
		Label deviceInformation = new Label("<h2 style=font-weight:bold;padding-left:12px>Device Information</h2>", ContentMode.HTML);
		horizontalLayout.addComponent(deviceInformation);
		layout.addComponent(horizontalLayout);
		layout.setComponentAlignment(horizontalLayout, Alignment.TOP_LEFT);
	}
	
	private void getSearchAndDelete(VerticalLayout layout) {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		HorizontalLayout searchLayout = new HorizontalLayout();
		HorizontalLayout deleteLayout = new HorizontalLayout();
		
		search = new TextField();
		search.setIcon(VaadinIcons.SEARCH);
		search.setWidth("100%");
		search.setStyleName("small inline-icon search");
		search.setPlaceholder("Search");
		search.setResponsive(true);
		searchLayout.addComponent(search);
		
		startDateField = new DateTimeField();
		startDateField.setResponsive(true);
		startDateField.setDateFormat(DATE_FORMAT);
		startDateField.setValue(LocalDateTime.now());
		startDateField.setDescription("Start Date");
		endDateField = new DateTimeField();
		endDateField.setResponsive(true);
		endDateField.setDateFormat(DATE_FORMAT);
		endDateField.setValue(LocalDateTime.now());
		endDateField.setDescription("End Date");
		
		deleteLayout.addComponent(startDateField);
		//deleteLayout.setComponentAlignment(startDateField, Alignment.MIDDLE_LEFT);
		deleteLayout.addComponent(endDateField);
		//deleteLayout.setComponentAlignment(endDateField, Alignment.MIDDLE_RIGHT);
		
		delete = new Button(VaadinIcons.TRASH);
		delete.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		delete.setResponsive(true);
		deleteLayout.addComponent(delete);
		//deleteLayout.setComponentAlignment(delete, Alignment.MIDDLE_RIGHT);
		
		horizontalLayout.addComponents(searchLayout, deleteLayout );
		horizontalLayout.setComponentAlignment(searchLayout, Alignment.TOP_LEFT);
		horizontalLayout.setComponentAlignment(deleteLayout, Alignment.MIDDLE_RIGHT);
		
		layout.addComponent(horizontalLayout);
		//layout.setComponentAlignment(horizontalLayout, Alignment.TOP_LEFT);
	}
	
	private void addDeviceHistoryLabel(VerticalLayout layout) {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		Label deviceInformation = new Label("<h2 style=font-weight:bold;padding-left:12px>Heartbeat History</h2>", ContentMode.HTML);
		horizontalLayout.addComponent(deviceInformation);
		layout.addComponent(horizontalLayout);
		layout.setComponentAlignment(horizontalLayout, Alignment.TOP_LEFT);
	}
	
	private List<Button> getTerminals(VerticalLayout verticalDeviceFormLayout){
		int count=1;
		List<Button> buttons = new ArrayList<Button>();
		DevicesData deviceData = new DevicesData();
		for(Devices device:deviceList) {
				if(device.isActive()==true) {
					Button terminalButton = new Button();
					terminalButton.setCaption("Terminal ABC "+count);
						terminalButton.setIcon(new ThemeResource("terminalGood1.png"));
						terminalButton.addClickListener(new ClickListener() {
							public void buttonClick(ClickEvent event) {
								verticalDeviceFormLayout.removeAllComponents();
								getDeviceFromLayout(verticalDeviceFormLayout, device, false);
								
							}
						});
					
					terminalButton.setPrimaryStyleName("v-heartbeat-Button");
					count++;
					buttons.add(terminalButton);
				} else {
					Button terminalButton1 = new Button();
					terminalButton1.setCaption("Terminal ABC "+count);
						terminalButton1.setIcon(new ThemeResource("terminalBad1.png"));
						terminalButton1.addClickListener(new ClickListener() {
							public void buttonClick(ClickEvent event) {
								verticalDeviceFormLayout.removeAllComponents();
								getDeviceFromLayout(verticalDeviceFormLayout, device, false);
							}
						});
					terminalButton1.setPrimaryStyleName("v-heartbeat-Button");
					count++;
					buttons.add(terminalButton1);
				}
				
		}
		return buttons;
	}
	

	private List<HorizontalLayout> getButtonsLayout(List<Button> terminalButtons) {
		
		List<HorizontalLayout> hLList = new ArrayList<HorizontalLayout>();
		HorizontalLayout hL = new HorizontalLayout();
		for(Button button:terminalButtons) {
			if(hL.getComponentCount()<4) {
				hL.addComponents(button);
			} else if(hL.getComponentCount()==4) {
				hLList.add(hL);
				hL=new HorizontalLayout();
				hL.addComponents(button);
			}
		}
		hLList.add(hL);
		return hLList;
		
	}
	
	private void getDeviceFromLayout(VerticalLayout layout,Devices device, boolean isEditableOnly) {
		
		FormLayout deviceFormLayout = new FormLayout();
		
		getEntityType(device, deviceFormLayout, isEditableOnly);
		
		getDeviceName(device, deviceFormLayout, isEditableOnly);
		
		getDescription(device, deviceFormLayout, isEditableOnly);
		
		getActive(device, deviceFormLayout, isEditableOnly);
		
		getSerailNumber(device, deviceFormLayout, isEditableOnly);
		
		getHBAndFrqncy(device, deviceFormLayout, isEditableOnly);
		
		getLastSeen(device, deviceFormLayout, isEditableOnly);
		
		layout.addComponent(deviceFormLayout);
		
	}
	
	private void getEntityType(Devices device, FormLayout deviceFormLayout, boolean isEditableOnly) {
		String entityType = device.getManufacturer() != null ? device.getManufacturer(): "";
		entityTypeTextFiled = new TextField("Entity Type", entityType);
		device.setManufacturer(entityTypeTextFiled.getValue());
		entityTypeTextFiled.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		entityTypeTextFiled.setWidth("100%");
		entityTypeTextFiled.setStyleName("role-textbox");
		entityTypeTextFiled.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		entityTypeTextFiled.setEnabled(isEditableOnly);
		deviceFormLayout.addComponent(entityTypeTextFiled);
	}
	
	private void getDeviceName(Devices device, FormLayout deviceFormLayout, boolean isEditableOnly) {
		String name = device.getDeviceName() != null ? device.getDeviceName(): "";
		deviceName = new TextField("Name", name);
		device.setDeviceName(deviceName.getValue());
		deviceName.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		deviceName.setWidth("100%");
		deviceName.setStyleName("role-textbox");
		deviceName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		deviceName.setEnabled(isEditableOnly);
		deviceFormLayout.addComponent(deviceName);
	}
	
	private void getDescription(Devices device, FormLayout deviceFormLayout, boolean isEditableOnly) {
		//HorizontalLayout descriptionHL = new HorizontalLayout();
		String description = device.getDescription() != null ? device.getDescription(): "";
		deviceDescription = new TextField("Description", description);
		device.setDescription(deviceDescription.getValue());
		deviceDescription.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		deviceDescription.setWidth("100%");
		deviceDescription.setStyleName("role-textbox");
		deviceDescription.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		deviceDescription.setEnabled(isEditableOnly);
		//descriptionHL.addComponent(deviceDescription);
		deviceFormLayout.addComponent(deviceDescription);
	}
	
	private void getActive(Devices device, FormLayout deviceFormLayout, boolean isEditableOnly) {
		HorizontalLayout activeHL = new HorizontalLayout();
		Label label = new Label("Active");
		boolean activeBoxValue = device.isActive();
		activeBox = new CheckBox("Allow Access", activeBoxValue);
		activeBox.setEnabled(isEditableOnly);
		device.setActive(activeBox.getValue());
		//activeLabel = new Label("Active");
		label.setStyleName("role-activeLable");
		activeHL.addComponents(label, activeBox);
		deviceFormLayout.addComponent(activeHL);
		
		
	}
	
	private void getSerailNumber(Devices device, FormLayout deviceFormLayout, boolean isEditableOnly) {
		//HorizontalLayout descriptionHL = new HorizontalLayout();
		String serailNumber = device.getSerialNumber() != null ? device.getSerialNumber(): "";
		deviceSerialNumber = new TextField("Serail Num.", serailNumber);
		device.setSerialNumber(deviceSerialNumber.getValue());
		deviceSerialNumber.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		deviceSerialNumber.setWidth("100%");
		deviceSerialNumber.setStyleName("role-textbox");
		deviceSerialNumber.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		deviceSerialNumber.setEnabled(isEditableOnly);
		//descriptionHL.addComponent(deviceDescription);
		deviceFormLayout.addComponent(deviceSerialNumber);
	}
	
	private void getHBAndFrqncy(Devices device, FormLayout deviceFormLayout, boolean isEditableOnly) {
		HorizontalLayout HL = new HorizontalLayout();
		HorizontalLayout heartBeatHL = new HorizontalLayout();
		FormLayout freqncyHL = new FormLayout();
		
		Label label = new Label("Heartbeat");
		label.setStyleName("role-activeLable");
		
		boolean activateHBBoxValue = device.isHeartBeat();
		activateHeartBeatBox = new CheckBox("Activate HeartBeat", activateHBBoxValue);
		activateHeartBeatBox.setEnabled(isEditableOnly);
		device.setHeartBeat(activateHeartBeatBox.getValue());
		heartBeatHL.addComponents(label, activateHeartBeatBox);
		
		String freqncy = device.getFrequency() != null ? device.getFrequency(): "";
		deviceFrequency = new TextField("Frequency", freqncy);
		device.setFrequency(deviceFrequency.getValue());
		deviceFrequency.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		deviceFrequency.setWidth("50%");
		deviceFrequency.setStyleName("role-textbox");
		deviceFrequency.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		deviceFrequency.setEnabled(isEditableOnly);
		freqncyHL.addComponent(	deviceFrequency);
		HL.addComponents(heartBeatHL,freqncyHL);
		deviceFormLayout.addComponent(HL);
	}
	
	private void getLastSeen(Devices device, FormLayout deviceFormLayout, boolean isEditableOnly) {
		//HorizontalLayout descriptionHL = new HorizontalLayout();
		String lastSeen = device.getLastSeen() != null ? device.getLastSeen(): "";
		deviceLastSeen = new TextField("Last Seen", lastSeen);
		device.setLastSeen(deviceLastSeen.getValue());
		deviceLastSeen.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		deviceLastSeen.setWidth("100%");
		deviceLastSeen.setStyleName("role-textbox");
		deviceLastSeen.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		deviceLastSeen.setEnabled(isEditableOnly);
		//descriptionHL.addComponent(deviceDescription);
		deviceFormLayout.addComponent(deviceLastSeen);
	}
}
