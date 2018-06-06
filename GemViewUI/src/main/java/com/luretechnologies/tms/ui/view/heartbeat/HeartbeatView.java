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
package com.luretechnologies.tms.ui.view.heartbeat;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.tms.backend.data.DevicesData;
import com.luretechnologies.tms.backend.data.entity.Debug;
import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.HeartBeatHistory;
import com.luretechnologies.tms.backend.data.entity.Node;
import com.luretechnologies.tms.backend.service.HeartBeatDeviceService;
import com.luretechnologies.tms.backend.service.HeartBeatHistoryService;
import com.luretechnologies.tms.backend.service.MockHeartBeatHistory;
import com.luretechnologies.tms.ui.NotificationUtil;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = HeartbeatView.VIEW_NAME)
public class HeartbeatView extends VerticalLayout implements Serializable, View {

	/**
	 * 
	 */
	private static final LocalDateTime localTimeNow = LocalDateTime.now();
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final long serialVersionUID = 4663357355780258257L;
	public static final String VIEW_NAME = "heartbeat";
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
	private static TextField search, deviceSearch;
	private static DateField startDateField, endDateField;
	private static Button delete;
	private static Grid<HeartBeatHistory> hbHistoryGrid;
	private MockHeartBeatHistory mockHBHistory;
	private static HorizontalSplitPanel horizontalPanel;
	private static VerticalLayout HL = new VerticalLayout();
	private static VerticalLayout VL = new VerticalLayout();
	private static Panel panel;
	private static HorizontalLayout searchAndDeleteLayoutHorizontal;
	private static VerticalLayout searchAndDeleteLayoutVertical;
	private static HorizontalLayout searchLayout;
	private static HorizontalLayout deleteLayoutHorizontal;
	private static VerticalLayout deleteLayoutVertical;
	private static VerticalLayout searchAndDeleteLayoutVerticalPhomeMode, verticalDeviceFormLayout,secondPanelLayout;;
	private List<Button> terminalList;

	
	@Autowired
	private HeartBeatHistoryService hbHistoryService;
	
	@Autowired
	private HeartBeatDeviceService heartBeatDeviceService;
	
	@Autowired
	public HeartbeatView() {
		//selectedDevice = new Devices();
		emptyDevice.setManufacturer("");
		emptyDevice.setDeviceName("");
		emptyDevice.setDescription("");
		emptyDevice.setActive(false);
		emptyDevice.setSerialNumber("");
		emptyDevice.setHeartBeat(false);
		emptyDevice.setLastSeen("");
	}
	
	@PostConstruct
	private void inti() {
		
		//getDeviceData();
		deviceList = heartBeatDeviceService.getDeviceData();
		setSpacing(false);
		setMargin(false);
		setResponsive(true);
		setHeight("100%");
		panel = getAndLoadSystemPanel();
		//HorizontalSplitPanel horizontalPanel =  new HorizontalSplitPanel();
		horizontalPanel =  new HorizontalSplitPanel();
		secondPanelLayout = new VerticalLayout();
		//secondPanelLayout.setSizeFull();
		//secondPanelLayout.setWidth("100%");
		secondPanelLayout.setStyleName("heartbeat-verticalLayout");
		verticalDeviceFormLayout = new VerticalLayout();
		verticalDeviceFormLayout.setWidth("100%");
		verticalDeviceFormLayout.setStyleName("heartbeat-verticalFormLayout");
		//VerticalLayout HL = new VerticalLayout();
		HL = new VerticalLayout();
		//VerticalLayout VL = new VerticalLayout();
		VL = new VerticalLayout();
		
		deviceSearch = new TextField();
		deviceSearch.setWidth("100%");
		deviceSearch.setIcon(VaadinIcons.SEARCH);
		deviceSearch.setStyleName("small inline-icon search");
		deviceSearch.addStyleName("v-textfield-font");
		deviceSearch.setPlaceholder("Search");
		
		//configureDeviceSearchForFilter(deviceSearch);
		
		addDeviceLabel(secondPanelLayout);
		getDeviceFromLayout(verticalDeviceFormLayout,emptyDevice, false );
		secondPanelLayout.addComponent(verticalDeviceFormLayout);
		addDeviceHistoryLabel(secondPanelLayout);
		getSearchAndDelete(secondPanelLayout);
		getHBHistoryGrid(secondPanelLayout);
		
		horizontalPanel.setHeight("100%");
		//List<Button> terminalList = getTerminals(verticalDeviceFormLayout);
		terminalList = getTerminals(verticalDeviceFormLayout,deviceList);
		
		Page.getCurrent().addBrowserWindowResizeListener(r->{
			System.out.println("Height "+ r.getHeight() + "Width:  " + r.getWidth()+ " in pixel");
			if(r.getWidth()< 1600 && r.getWidth() > 1150) {
				List<HorizontalLayout> HLList = getButtonsLayout(terminalList,2);
				desktopMode(HLList, secondPanelLayout);
				deletCalendarTabMode();
				/*deviceSearch.setHeight(37, Unit.PIXELS);
				search.setHeight("37px");
				startDateField.setHeight("37px");
				endDateField.setHeight("37px");*/
			}else if(r.getWidth()<=1150 && r.getWidth()> 1000){
				List<HorizontalLayout> HLList = getButtonsLayout(terminalList, 1);
				desktopMode(HLList, secondPanelLayout);
				deletCalendarPhoneMode();
				/*search.setHeight("37px");
				deviceSearch.setHeight(37, Unit.PIXELS);
				startDateField.setHeight("37px");
				endDateField.setHeight("37px");*/
			} else if(r.getWidth()<=1000 && r.getWidth()> 900) {
				deletCalendarPhoneMode();
				/*search.setHeight("37px");
				deviceSearch.setHeight(37, Unit.PIXELS);
				startDateField.setHeight("37px");
				endDateField.setHeight("37px");*/
				VL.addStyleName("heartbeat-SearchLayout");
			} else if(r.getWidth()<=900 && r.getWidth()>600){
				List<HorizontalLayout> HLList = getButtonsLayout(terminalList, 2);
				desktopMode(HLList, secondPanelLayout);
				deletCalendarPhoneMode();
				/*search.setHeight("37px");
				deviceSearch.setHeight(37, Unit.PIXELS);
				startDateField.setHeight("37px");
				endDateField.setHeight("37px");*/
				
			}else if(r.getWidth()<=600 && r.getWidth()>0) {
				List<HorizontalLayout> HLList = getButtonsLayout(terminalList, 1);
				desktopMode(HLList, secondPanelLayout);
				deletCalendarPhoneMode();
				//deviceSearch.setWidth("100%");
				/*deviceSearch.setHeight(28, Unit.PIXELS);
				search.setHeight("28px");
				startDateField.setHeight("28px");
				endDateField.setHeight("28px");*/
			}else {
				List<HorizontalLayout> HLList = getButtonsLayout(terminalList,3);
				desktopMode(HLList, secondPanelLayout);
				deleteCalendarDesktopMode();
				/*deviceSearch.setHeight(37, Unit.PIXELS);
				search.setHeight("37px");
				startDateField.setHeight("37px");
				endDateField.setHeight("37px");*/
			}
			
//			if(r.getWidth()<=700 && r.getWidth()>600) {
//				deviceSearch.setHeight(28, Unit.PIXELS);
//				search.setHeight("28px");
//				startDateField.setHeight("28px");
//				endDateField.setHeight("28px");
//			}
			
			if(r.getWidth()<=600) {
				deviceSearch.setHeight(28, Unit.PIXELS);
				search.setHeight("28px");
				startDateField.setHeight("28px");
				endDateField.setHeight("28px");
			} else if(r.getWidth()>600 && r.getWidth()<=1000) {
				deviceSearch.setHeight(32, Unit.PIXELS);
				search.setHeight("32px");
				startDateField.setHeight("32px");
				endDateField.setHeight("32px");
			}else {
				deviceSearch.setHeight(37, Unit.PIXELS);
				search.setHeight("37px");
				startDateField.setHeight("37px");
				endDateField.setHeight("37px");
			}
		});
		
		List<HorizontalLayout> HLList = getButtonsLayout(terminalList, 2);
		for(HorizontalLayout hL:HLList) {
			hL.setHeight("60px");
			hL.setResponsive(true);
			VL.addComponents(hL);
		}
		
		HL.setHeight("100%");
		VL.setResponsive(true);
		VL.setWidth("100%");
		HL.setResponsive(true);
		HL.addComponent(VL);
		horizontalPanel.setFirstComponent(HL);
		horizontalPanel.getFirstComponent().setStyleName("split-height");
		horizontalPanel.addComponent(secondPanelLayout);
		horizontalPanel.setSplitPosition(40, Unit.PERCENTAGE);
		
		//configureDeviceSearchForFilter(deviceSearch);
		
		panel.setContent(horizontalPanel);
		
		differentModesLoad(secondPanelLayout, terminalList);
	}
	
	/*private void configureDeviceSearchForFilter(TextField deviceSearch) {
		deviceSearch.addValueChangeListener(changed -> {
			deviceSearch.setWidth("100%");
			String valueInLower = changed.getValue().toLowerCase();
			//FIXME: if required to be filtered based on Device name and Descriptions
			//terminalList = getTerminals(verticalDeviceFormLayout, heartBeatDeviceService.getFilteredHeartBeatDevicesByNameOrDescription(valueInLower, deviceList));
			List<Button> filteredButtons = terminalList.stream().filter(button -> {
					return button.getCaption().toLowerCase().contains(valueInLower);
			}).collect(Collectors.toList());
//			differentModesLoad(secondPanelLayout, terminalList);
			differentModesLoad(secondPanelLayout, filteredButtons);
		});
		
		deviceSearch.addShortcutListener(new ShortcutListener("Clear",KeyCode.ESCAPE,null) {
			
			@Override
			public void handleAction(Object sender, Object target) {
				if (target == deviceSearch) {
					deviceSearch.clear();
				}
			}
		});
	}*/
	
	private void deleteCalendarDesktopMode() {
		deleteLayoutHorizontal.addComponents(startDateField,endDateField,delete);
		
		searchAndDeleteLayoutHorizontal.addComponents(searchLayout, deleteLayoutHorizontal);
		searchLayout.removeStyleName("audit-phoneAlignment");
		searchAndDeleteLayoutVertical.setVisible(false);
		searchAndDeleteLayoutVerticalPhomeMode.setVisible(false);
		searchAndDeleteLayoutHorizontal.setVisible(true);
	}
	
	private void deletCalendarTabMode() {
		deleteLayoutHorizontal.addComponents(startDateField,endDateField,delete);
		searchLayout.addStyleName("audit-phoneAlignment");
		searchAndDeleteLayoutVertical.addComponents(searchLayout, deleteLayoutHorizontal);;
		searchAndDeleteLayoutHorizontal.setVisible(false);
		searchAndDeleteLayoutVerticalPhomeMode.setVisible(false);
		searchAndDeleteLayoutVertical.setVisible(true);
	}
	
	private void deletCalendarPhoneMode() {
		deleteLayoutVertical.addComponents(startDateField,endDateField,delete);
		searchLayout.removeStyleName("audit-phoneAlignment");
		searchAndDeleteLayoutVerticalPhomeMode.addStyleName("audit-tabAlignment");
		searchAndDeleteLayoutVerticalPhomeMode.addComponents(searchLayout, deleteLayoutVertical);;
		searchAndDeleteLayoutHorizontal.setVisible(false);
		searchAndDeleteLayoutVertical.setVisible(false);
		searchAndDeleteLayoutVerticalPhomeMode.setVisible(true);
	}
	
	private void differentModesLoad(VerticalLayout secondPanelLayout, List<Button> terminalList) {
		int width = Page.getCurrent().getBrowserWindowWidth();
		if(width< 1600 && width > 1150) {
			List<HorizontalLayout> HLList1 = getButtonsLayout(terminalList,2);
			desktopMode(HLList1, secondPanelLayout);
			deletCalendarTabMode();
			deviceSearch.setHeight(37, Unit.PIXELS);
			deviceSearch.setWidth("100%");
			search.setHeight("37px");
			startDateField.setHeight("37px");
			endDateField.setHeight("37px");
		}else if(width<=1150 && width> 1000){
			List<HorizontalLayout> HLList1 = getButtonsLayout(terminalList, 1);
			desktopMode(HLList1, secondPanelLayout);
			deletCalendarPhoneMode();
			deviceSearch.setHeight(37, Unit.PIXELS);
			search.setHeight("37px");
			startDateField.setHeight("37px");
			endDateField.setHeight("37px");
		} else if(width<=900 && width>600){
			List<HorizontalLayout> HLList1 = getButtonsLayout(terminalList, 1);
			desktopMode(HLList1, secondPanelLayout);
			deletCalendarPhoneMode();
			deviceSearch.setHeight(37, Unit.PIXELS);
			search.setHeight("37px");
			startDateField.setHeight("37px");
			endDateField.setHeight("37px");
		}else if(width<=600 && width>0) {
			List<HorizontalLayout> HLList1 = getButtonsLayout(terminalList, 1);
			desktopMode(HLList1, secondPanelLayout);
			deletCalendarPhoneMode();
			deviceSearch.setHeight(28, Unit.PIXELS);
			search.setHeight("28px");
			startDateField.setHeight("28px");
			endDateField.setHeight("28px");
		}else {
			List<HorizontalLayout> HLList1 = getButtonsLayout(terminalList,3);
			desktopMode(HLList1, secondPanelLayout);
			deleteCalendarDesktopMode();
			deviceSearch.setHeight(37, Unit.PIXELS);
			search.setHeight("37px");
			startDateField.setHeight("37px");
			endDateField.setHeight("37px");
		}
		/*if(width<=700 && width>600) {
			deviceSearch.setHeight(28, Unit.PIXELS);
			search.setHeight("28px");
			startDateField.setHeight("28px");
			endDateField.setHeight("28px");
		}*/
		
		if(width<=600) {
			deviceSearch.setHeight(28, Unit.PIXELS);
			search.setHeight("28px");
			startDateField.setHeight("28px");
			endDateField.setHeight("28px");
		} else if(width>600 && width<=1000){
			deviceSearch.setHeight(32, Unit.PIXELS);
			search.setHeight("32px");
			startDateField.setHeight("32px");
			endDateField.setHeight("32px");
		}else {
			deviceSearch.setHeight(37, Unit.PIXELS);
			search.setHeight("37px");
			startDateField.setHeight("37px");
			endDateField.setHeight("37px");
		}
	}
	
	private void desktopMode(List<HorizontalLayout> HLList, VerticalLayout secondPanelLayout) {
		HL = new VerticalLayout();
		horizontalPanel  =  new HorizontalSplitPanel();
		VL = new VerticalLayout();
		VL.setWidth("100%");
		VL.addStyleName("heartbeat-SearchLayout");
		HorizontalLayout searchLayout = new HorizontalLayout();
		searchLayout.setWidth("100%");
		
		deviceSearch = new TextField();
		deviceSearch.setWidth("100%");
		deviceSearch.setIcon(VaadinIcons.SEARCH);
		deviceSearch.setStyleName("small inline-icon search");
		deviceSearch.addStyleName("v-textfield-font");
		deviceSearch.setPlaceholder("Search");
		//deviceSearch.setValue(deviceSearch.getValue());
		
		searchLayout.addComponent(deviceSearch);
		VL.addComponent(searchLayout);
		
		for(HorizontalLayout hL:HLList) {
			hL.setHeight("60px");
			hL.setResponsive(true);
			VL.addComponents(hL);
		}
		HL.setHeight("100%");
		VL.setResponsive(true);
		HL.setResponsive(true);
		HL.addComponent(VL);
		horizontalPanel.setFirstComponent(HL);
		horizontalPanel.getFirstComponent().setStyleName("split-height");
		horizontalPanel.addComponent(secondPanelLayout);
		horizontalPanel.setSplitPosition(40, Unit.PERCENTAGE);
		
		panel.setContent(horizontalPanel);
		
		//configureDeviceSearchForFilter(deviceSearch);
	}
	
//	private void desktopMode1(List<HorizontalLayout> HLList, VerticalLayout secondPanelLayout) {
//		HL = new VerticalLayout();
//		horizontalPanel  =  new HorizontalSplitPanel();
//		VL = new VerticalLayout();
//		VL.setWidth("100%");
//		VL.addStyleName("heartbeat-SearchLayout");
//		
//		deviceSearch = new TextField();
//		deviceSearch.setWidth("100%");
//		deviceSearch.setIcon(VaadinIcons.SEARCH);
//		deviceSearch.setStyleName("small inline-icon search");
//		deviceSearch.addStyleName("v-textfield-font");
//		deviceSearch.setPlaceholder("Search");
//		configureDeviceSearchForFilter(deviceSearch);
//
//		VL.addComponent(deviceSearch);
//		
//		for(HorizontalLayout hL:HLList) {
//			hL.setHeight("60px");
//			hL.setResponsive(true);
//			VL.addComponents(hL);
//		}
//		HL.setHeight("100%");
//		VL.setResponsive(true);
//		HL.setResponsive(true);
//		HL.addComponent(VL);
//		horizontalPanel.setFirstComponent(HL);
//		horizontalPanel.getFirstComponent().setStyleName("split-height");
//		horizontalPanel.addComponent(secondPanelLayout);
//		horizontalPanel.setSplitPosition(40, Unit.PERCENTAGE);
//		
//		panel.setContent(horizontalPanel);
//	}
//	
//	private void desktopMode2(List<HorizontalLayout> HLList, VerticalLayout secondPanelLayout) {
//		HL = new VerticalLayout();
//		horizontalPanel  =  new HorizontalSplitPanel();
//		VL = new VerticalLayout();
//		VL.setWidth("100%");
//		VL.addStyleName("heartbeat-SearchLayout");
//		
//		deviceSearch = new TextField();
//		deviceSearch.setWidth("100%");
//		deviceSearch.setIcon(VaadinIcons.SEARCH);
//		deviceSearch.setStyleName("small inline-icon search");
//		deviceSearch.addStyleName("v-textfield-font");
//		deviceSearch.setPlaceholder("Search");
//		
//		configureDeviceSearchForFilter(deviceSearch);
//		VL.addComponent(deviceSearch);
//		
//		for(HorizontalLayout hL:HLList) {
//			hL.setHeight("60px");
//			hL.setResponsive(true);
//			VL.addComponents(hL);
//		}
//		HL.setHeight("100%");
//		VL.setResponsive(true);
//		HL.setResponsive(true);
//		HL.addComponent(VL);
//		horizontalPanel.setFirstComponent(HL);
//		horizontalPanel.getFirstComponent().setStyleName("split-height");
//		horizontalPanel.addComponent(secondPanelLayout);
//		horizontalPanel.setSplitPosition(40, Unit.PERCENTAGE);
//		panel.setContent(horizontalPanel);
//	}
	
	private void getDeviceData() {
		List<HeartBeatHistory> hbHistoryListActive = hbHistoryService.getHBHistoryList();
		List<HeartBeatHistory> hbHistoryListFail = hbHistoryService.getHBHistoryList();
		Collections.reverse(hbHistoryListFail);
		Date date = new Date();
		for (int index=1; index <=15; index++) {
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
				device.setHbHistoryList(hbHistoryListActive);
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
					device1.setHbHistoryList( hbHistoryListFail);
					deviceList.add(device1);
					
				}
		}
	}
	
	public Panel getAndLoadSystemPanel() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setCaption("Heartbeat");
		panel.setResponsive(true);
		panel.setSizeFull();
        addComponent(panel);
       return panel;
	}
	
	private void addDeviceLabel(VerticalLayout layout) {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setStyleName("heartbeat-horizontalLayout-DeviceLabel");
		Label deviceInformation = new Label("Device Information", ContentMode.HTML);
		deviceInformation.addStyleName("label-style");
		horizontalLayout.addComponent(deviceInformation);
		layout.addComponent(horizontalLayout);
		layout.setComponentAlignment(horizontalLayout, Alignment.TOP_LEFT);
	}
	
	private void confirmDialog() {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?",
		        "Ok", "Cancel", new ConfirmDialog.Listener() {

		            public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		                    // Confirmed to continue
		                	hbHistoryService.removeHBHistoryDevice(hbHistoryGrid.getSelectedItems().iterator().next());
		    				//nodeTree.getDataProvider().refreshAll();
		                	search.clear();
		                	loadGrid();
		                	hbHistoryGrid.getDataProvider().refreshAll();
		    				clearCalenderDates();
		                } else {
		                    // User did not confirm
		                    
		                }
		            }
		        });
	}
	
	private void loadGrid() {
		DataProvider data = new ListDataProvider(hbHistoryService.getHBHistoryList());
		hbHistoryGrid.setDataProvider(data);		
	}
	
	private void getSearchAndDelete(VerticalLayout layout) {
		searchAndDeleteLayoutHorizontal = new HorizontalLayout();
		searchAndDeleteLayoutHorizontal.addStyleName("heartbeat-historySearchLayout");
		searchAndDeleteLayoutHorizontal.setWidth("100%");
		searchLayout = new HorizontalLayout();
		searchLayout.setWidth("100%");
		deleteLayoutHorizontal = new HorizontalLayout();
		deleteLayoutHorizontal.setWidth("100%");
		deleteLayoutHorizontal.setVisible(true);
		
		search = new TextField();
		search.setIcon(VaadinIcons.SEARCH);
		search.setWidth("100%");
		search.setHeight("37px");
		search.setStyleName("small inline-icon search");
		search.setPlaceholder("Search");
		search.setResponsive(true);
		search.addStyleName("v-textfield-font");
		search.addShortcutListener(new ShortcutListener("Clear",KeyCode.ESCAPE,null) {
			
			@Override
			public void handleAction(Object sender, Object target) {
				if(target == search) {
					search.clear();
				}
				
			}
		});
		search.addValueChangeListener(valueChange -> {
			String valueInLower = valueChange.getValue().toLowerCase();
			ListDataProvider<HeartBeatHistory> debugDataProvider = (ListDataProvider<HeartBeatHistory>) hbHistoryGrid.getDataProvider();
			if(!debugDataProvider.getItems().isEmpty()) {
			debugDataProvider.setFilter(filter -> {
				String dateTime = filter.getDateTime().toLowerCase();
				String IP = filter.getIP().toLowerCase();
				String status = filter.getStatus().toLowerCase();
				String process = filter.getProcess().toLowerCase();
				return dateTime.contains(valueInLower) || IP.contains(valueInLower) || status.contains(valueInLower) || process.contains(valueInLower);
			});
			}
		});
		searchLayout.addComponent(search);
		
		startDateField = new DateField();
		startDateField.setWidth("100%");
		startDateField.setResponsive(true);
		startDateField.setDateFormat(DATE_FORMAT);
		startDateField.setRangeEnd(localTimeNow.toLocalDate());
		startDateField.setDescription("Start Date");
		startDateField.setPlaceholder("Start Date");
		startDateField.addStyleName("v-textfield-font");
		
		endDateField = new DateField();
		endDateField.setWidth("100%");
		endDateField.setResponsive(true);
		endDateField.setDateFormat(DATE_FORMAT);
		endDateField.setRangeEnd(localTimeNow.toLocalDate().plusDays(1));
		endDateField.setDescription("End Date");
		endDateField.setPlaceholder("End Date");
		endDateField.setDateOutOfRangeMessage("Same Date cannot be selected");
		endDateField.addStyleName("v-textfield-font");
		
		//Vertical Initialization
		searchAndDeleteLayoutVertical = new VerticalLayout();
		searchAndDeleteLayoutVertical.setVisible(false);
		//searchAndDeleteLayoutVertical.setStyleName("heartbeat-verticalLayout");
		searchAndDeleteLayoutVertical.addStyleNames("heartbeat-historySearchLayout", "heartbeat-historySearchVerticalLayout");
		
		//deleteLayout Vertical Initialization
		deleteLayoutVertical = new VerticalLayout();
		deleteLayoutVertical.setVisible(true);
		deleteLayoutVertical.setStyleName("heartbeat-verticalLayout");
		
		//Phone Mode
		searchAndDeleteLayoutVerticalPhomeMode = new VerticalLayout();
		searchAndDeleteLayoutVerticalPhomeMode.setVisible(false);
		//searchAndDeleteLayoutVerticalPhomeMode.setStyleName("heartbeat-verticalLayout");
		searchAndDeleteLayoutVerticalPhomeMode.addStyleNames("heartbeat-historySearchLayout", "heartbeat-historySearchVerticalLayout");
		
		deleteLayoutHorizontal.addComponent(startDateField);
		deleteLayoutHorizontal.setComponentAlignment(startDateField, Alignment.MIDDLE_LEFT);
		deleteLayoutHorizontal.addComponent(endDateField);
		deleteLayoutHorizontal.setComponentAlignment(endDateField, Alignment.MIDDLE_CENTER);
		
		delete = new Button(VaadinIcons.TRASH);
		delete.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		delete.setResponsive(true);
		delete.addClickListener(clicked -> {
			if(hbHistoryGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.HEARTBEAT_DELETE, Notification.Type.ERROR_MESSAGE);
			}else {
				confirmDialog();
			}
		});
		deleteLayoutHorizontal.addComponent(delete);
		deleteLayoutHorizontal.setVisible(true);
		
		//deleteLayout.setComponentAlignment(delete, Alignment.MIDDLE_RIGHT);
		
		searchAndDeleteLayoutHorizontal.addComponents(searchLayout, deleteLayoutHorizontal );
		searchAndDeleteLayoutHorizontal.setComponentAlignment(searchLayout, Alignment.TOP_LEFT);
		searchAndDeleteLayoutHorizontal.setComponentAlignment(deleteLayoutHorizontal, Alignment.MIDDLE_RIGHT);
		
		layout.addComponent(searchAndDeleteLayoutHorizontal);
		layout.addComponent(searchAndDeleteLayoutVertical);
		layout.addComponent(searchAndDeleteLayoutVerticalPhomeMode);
		//layout.setComponentAlignment(horizontalLayout, Alignment.TOP_LEFT);
		
		endDateField.addValueChangeListener(change ->{
	         if(!(change.getValue() == null)) {
	        	 if(entityTypeTextFiled.getValue()!=null && !entityTypeTextFiled.getValue().isEmpty()) {
	        		 	if(startDateField.getValue()!=null) {
	        	 		if(change.getValue().compareTo(startDateField.getValue()) >= 0 ) {
	        	 				ListDataProvider<HeartBeatHistory> hbHistoryDataProvider = (ListDataProvider<HeartBeatHistory>) hbHistoryGrid.getDataProvider();
	        	 				hbHistoryDataProvider.setFilter(filter -> {
	        	 				Date hbHistoryDate = filter.getDate();
	        	 				try {
	        	 					return hbHistoryDate.after(dateFormatter.parse(startDateField.getValue().minusDays(1).toString())) && hbHistoryDate.before(dateFormatter.parse(change.getValue().plusDays(1).toString()));
	        	 				} catch (ParseException e) {
	        	 					System.out.println(e.getMessage() + filter.toString());
							
	        	 					return false;
	        	 				}
					});
				} 
	        		 	}else {
	        		 		Notification.show(NotificationUtil.HEARTBEAT_STARTDATE, Notification.Type.ERROR_MESSAGE);
	        		 		endDateField.clear();
	        		 	}
	        }else {
	        	Notification.show(NotificationUtil.HEARTBEAT_SELECTDEVICE, Notification.Type.ERROR_MESSAGE);
	        	clearCalenderDates();
	        }
		}
		});
		
		startDateField.addValueChangeListener(change ->{
			if(change.getValue()!=null) {
				endDateField.clear();
				endDateField.setRangeStart(change.getValue().plusDays(1));
				endDateField.setRangeEnd(localTimeNow.toLocalDate().plusDays(1));
			}
	});
	}
	
	private void clearCalenderDates() {
		startDateField.clear();
		endDateField.clear();
	}

	
	private void getHBHistoryGrid(VerticalLayout layout) {
		VerticalLayout VL = new VerticalLayout();
		//VL.setHeight("100%");
		VL.setStyleName("heartbeat-verticalLayout");
		hbHistoryGrid= new Grid<>(HeartBeatHistory.class);
		hbHistoryGrid.setWidth("100%");
		hbHistoryGrid.setHeightByRows(6);
		//hbHistoryGrid.setHeight("100%");
		hbHistoryGrid.setResponsive(true);
		hbHistoryGrid.setSelectionMode(SelectionMode.SINGLE);
		hbHistoryGrid.setColumns("dateTime", "IP", "status", "process");
		VL.addComponent(hbHistoryGrid);
		layout.addComponent(VL);
		//layout.setExpandRatio(VL, 4);
	}
	
	private void loadGridData(Devices device) {
		//DataProvider data = hbHistoryService.getListDataProvider().getId(device.getId());
		DataProvider data = new ListDataProvider<>(device.getHbHistoryList());
		hbHistoryGrid.setDataProvider(data);
	}
	
	private void addDeviceHistoryLabel(VerticalLayout layout) {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.addStyleName("heartbeat-historyLabel");
		horizontalLayout.addStyleName("horizontalLayout");
		Label deviceInformation = new Label("Heartbeat History", ContentMode.HTML);
		deviceInformation.addStyleName("label-style");
		horizontalLayout.addComponent(deviceInformation);
		layout.addComponent(horizontalLayout);
		layout.setComponentAlignment(horizontalLayout, Alignment.TOP_LEFT);
	}
	
	private List<Button> getTerminals(VerticalLayout verticalDeviceFormLayout, List<Devices> devicesList){
		int count=1;
		List<Button> buttons = new ArrayList<Button>();
		DevicesData deviceData = new DevicesData();
		for(Devices device:devicesList) {
				if(device.isActive()==true) {
					Button terminalButton = new Button();
					terminalButton.setCaption("Terminal ABC "+count);
						terminalButton.setIcon(new ThemeResource("terminalGood1.png"));
						terminalButton.addClickListener(new ClickListener() {
							public void buttonClick(ClickEvent event) {
								clearCalenderDates();
								verticalDeviceFormLayout.removeAllComponents();
								getDeviceFromLayout(verticalDeviceFormLayout, device, false);
								if(hbHistoryService.getHBHistoryList().size()==10) {
									loadGridData(device);
								}else {
									loadGrid();
								}
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
								clearCalenderDates();
								verticalDeviceFormLayout.removeAllComponents();
								getDeviceFromLayout(verticalDeviceFormLayout, device, false);
								if(hbHistoryService.getHBHistoryList().size()==10) {
									loadGridData(device);
								}else {
									loadGrid();
								}
							}
						});
					terminalButton1.setPrimaryStyleName("v-heartbeat-Button");
					count++;
					buttons.add(terminalButton1);
				}
				
		}
		return buttons;
	}
	

	private List<HorizontalLayout> getButtonsLayout(List<Button> terminalButtons, int component) {
		
		List<HorizontalLayout> hLList = new ArrayList<HorizontalLayout>();
		HorizontalLayout hL = new HorizontalLayout();
		for(Button button:terminalButtons) {
			if(hL.getComponentCount()<component) {
				hL.addComponents(button);
			} else if(hL.getComponentCount()==component) {
				hLList.add(hL);
				hL=new HorizontalLayout();
				hL.addComponents(button);
			}
		}
		hLList.add(hL);
		return hLList;
		
	}
	

	private List<HorizontalLayout> getButtonsLayoutSecondMode(List<Button> terminalButtons) {
		
		List<HorizontalLayout> hLList = new ArrayList<HorizontalLayout>();
		HorizontalLayout hL = new HorizontalLayout();
		for(Button button:terminalButtons) {
			if(hL.getComponentCount()<2) {
				hL.addComponents(button);
			} else if(hL.getComponentCount()==2) {
				hLList.add(hL);
				hL=new HorizontalLayout();
				hL.addComponents(button);
			}
		}
		hLList.add(hL);
		return hLList;
		
	}
	
private List<HorizontalLayout> getButtonsLayoutThirdMode(List<Button> terminalButtons) {
		
		List<HorizontalLayout> hLList = new ArrayList<HorizontalLayout>();
		HorizontalLayout hL = new HorizontalLayout();
		for(Button button:terminalButtons) {
			if(hL.getComponentCount()<1) {
				hL.addComponents(button);
			} else if(hL.getComponentCount()==1) {
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
		deviceFormLayout.addStyleName("system-LabelAlignment");
		deviceFormLayout.setWidth("100%");
		
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
		entityTypeTextFiled.addStyleName("v-textfield-font");
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
		deviceName.addStyleName("v-textfield-font");
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
		deviceDescription.addStyleName("v-textfield-font");
		deviceDescription.setEnabled(isEditableOnly);
		//descriptionHL.addComponent(deviceDescription);
		deviceFormLayout.addComponent(deviceDescription);
	}
	
	private void getActive(Devices device, FormLayout deviceFormLayout, boolean isEditableOnly) {
		HorizontalLayout activeHL = new HorizontalLayout();
		activeHL.addStyleName("heartbeat-activeLayout");
		Label label = new Label("Active");
		boolean activeBoxValue = device.isActive();
		activeBox = new CheckBox("Allow Access", activeBoxValue);
		activeBox.setEnabled(isEditableOnly);
		activeBox.addStyleName("v-textfield-font");
		device.setActive(activeBox.getValue());
		//activeLabel = new Label("Active");
		label.setStyleName("role-activeLable");
		label.addStyleNames("v-textfield-font");
		label.addStyleName("heartbeat-checkbox");
		activeHL.addComponents(label, activeBox);
		deviceFormLayout.addComponent(activeHL);
	}
	
	private void getSerailNumber(Devices device, FormLayout deviceFormLayout, boolean isEditableOnly) {
		String serailNumber = device.getSerialNumber() != null ? device.getSerialNumber(): "";
		deviceSerialNumber = new TextField("Serial Number", serailNumber);
		device.setSerialNumber(deviceSerialNumber.getValue());
		deviceSerialNumber.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		deviceSerialNumber.setWidth("100%");
		deviceSerialNumber.setStyleName("role-textbox");
		deviceSerialNumber.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		deviceSerialNumber.addStyleName("v-textfield-font");
		deviceSerialNumber.setEnabled(isEditableOnly);
		deviceFormLayout.addComponent(deviceSerialNumber);
	}
	
	private void getHBAndFrqncy(Devices device, FormLayout deviceFormLayout, boolean isEditableOnly) {
		HorizontalLayout HL = new HorizontalLayout();
		HorizontalLayout heartBeatHL = new HorizontalLayout();
		HL.addStyleName("heartbeat-heartBeatLayout");
		//FormLayout freqncyFL = new FormLayout();
		//freqncyFL.setWidth("100%");
		//freqncyFL.setStyleName("heartbeat-frequency");
		
		Label label = new Label("Heartbeat");
		label.setStyleName("role-activeLable");
		label.addStyleNames("v-textfield-font");
		label.addStyleName("heartbeat-checkbox");
		
		boolean activateHBBoxValue = device.isHeartBeat();
		activateHeartBeatBox = new CheckBox("Activate Heartbeat", activateHBBoxValue);
		activateHeartBeatBox.setEnabled(isEditableOnly);
		activateHeartBeatBox.addStyleName("v-textfield-font");
		device.setHeartBeat(activateHeartBeatBox.getValue());
		heartBeatHL.addComponents(label, activateHeartBeatBox);
		
		String freqncy = device.getFrequency() != null ? device.getFrequency(): "";
		deviceFrequency = new TextField("Frequency", freqncy);
		device.setFrequency(deviceFrequency.getValue());
		deviceFrequency.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		deviceFrequency.setWidth("100%");
		deviceFrequency.setStyleName("role-textbox");
		deviceFrequency.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		deviceFrequency.addStyleName("v-textfield-font");
		deviceFrequency.setEnabled(isEditableOnly);
		//freqncyFL.addComponent(	deviceFrequency);
		//HL.addComponents(heartBeatHL,freqncyFL);
		HL.addComponent(heartBeatHL);
		deviceFormLayout.addComponent(HL);
		deviceFormLayout.addComponent(deviceFrequency);
	}
	
	private void getLastSeen(Devices device, FormLayout deviceFormLayout, boolean isEditableOnly) {
		//HorizontalLayout descriptionHL = new HorizontalLayout();
		String lastSeen = device.getLastSeen() != null ? device.getLastSeen(): "";
		deviceLastSeen = new TextField("Last Seen", lastSeen);
		device.setLastSeen(deviceLastSeen.getValue());
		deviceLastSeen.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		deviceLastSeen.setWidth("100%");
		deviceLastSeen.setStyleName("role-textbox");
		deviceLastSeen.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS);
		deviceLastSeen.addStyleName("v-textfield-font");
		deviceLastSeen.setEnabled(isEditableOnly);
		//descriptionHL.addComponent(deviceDescription);
		deviceFormLayout.addComponent(deviceLastSeen);
	}
}
