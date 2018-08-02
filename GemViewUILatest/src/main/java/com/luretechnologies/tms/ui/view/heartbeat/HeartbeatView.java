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

import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.Heartbeat;
import com.luretechnologies.tms.backend.data.entity.TerminalClient;
import com.luretechnologies.tms.backend.service.HeartbeatService;
import com.luretechnologies.tms.ui.NotificationUtil;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
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
	private static final DateTimeFormatter  dateFormatter1 = DateTimeFormatter.ofPattern("yyMMdd");
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
	private  List<TerminalClient> terminalList = new LinkedList<TerminalClient>();
	private static TerminalClient selectedTerminal;
	private static TextField search, deviceSearch;
	private static DateField startDateField, endDateField;
	private static Button delete;
	private static Grid<Heartbeat> hbHistoryGrid;
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
	private List<Button> terminalButtonList;
	
	@Autowired
	private HeartbeatService heartBeatService;
	
	@Autowired
	public HeartbeatView() {
	}
	
	@PostConstruct
	private void inti() throws ApiException {
		selectedTerminal = new TerminalClient();
		terminalList = heartBeatService.getTerminalList();
		setSpacing(false);
		setMargin(false);
		setResponsive(true);
		setHeight("100%");
		panel = getAndLoadSystemPanel();
		horizontalPanel =  new HorizontalSplitPanel();
		secondPanelLayout = new VerticalLayout();
		secondPanelLayout.setStyleName("heartbeat-verticalLayout");
		verticalDeviceFormLayout = new VerticalLayout();
		verticalDeviceFormLayout.setWidth("100%");
		verticalDeviceFormLayout.setStyleName("heartbeat-verticalFormLayout");
		HL = new VerticalLayout();
		VL = new VerticalLayout();
		
		deviceSearch = new TextField();
		deviceSearch.setWidth("100%");
		deviceSearch.setIcon(VaadinIcons.SEARCH);
		deviceSearch.setStyleName("small inline-icon search");
		deviceSearch.addStyleName("v-textfield-font");
		deviceSearch.setPlaceholder("Search");
		
		addDeviceLabel(secondPanelLayout);
		getDeviceFromLayout(verticalDeviceFormLayout,selectedTerminal, false );
		secondPanelLayout.addComponent(verticalDeviceFormLayout);
		addDeviceHistoryLabel(secondPanelLayout);
		getSearchAndDelete(secondPanelLayout);
		getHBHistoryGrid(secondPanelLayout);
		
		horizontalPanel.setHeight("100%");
		terminalButtonList = getTerminals(verticalDeviceFormLayout,terminalList);
		
		Page.getCurrent().addBrowserWindowResizeListener(r->{
			System.out.println("Height "+ r.getHeight() + "Width:  " + r.getWidth()+ " in pixel");
			if(r.getWidth()< 1600 && r.getWidth() > 1150) {
				List<HorizontalLayout> HLList = getButtonsLayout(terminalButtonList,2);
				desktopMode(HLList, secondPanelLayout);
				deletCalendarTabMode();
				/*deviceSearch.setHeight(37, Unit.PIXELS);
				search.setHeight("37px");
				startDateField.setHeight("37px");
				endDateField.setHeight("37px");*/
			}else if(r.getWidth()<=1150 && r.getWidth()> 1000){
				List<HorizontalLayout> HLList = getButtonsLayout(terminalButtonList, 1);
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
				List<HorizontalLayout> HLList = getButtonsLayout(terminalButtonList, 2);
				desktopMode(HLList, secondPanelLayout);
				deletCalendarPhoneMode();
				/*search.setHeight("37px");
				deviceSearch.setHeight(37, Unit.PIXELS);
				startDateField.setHeight("37px");
				endDateField.setHeight("37px");*/
				
			}else if(r.getWidth()<=600 && r.getWidth()>0) {
				List<HorizontalLayout> HLList = getButtonsLayout(terminalButtonList, 1);
				desktopMode(HLList, secondPanelLayout);
				deletCalendarPhoneMode();
				//deviceSearch.setWidth("100%");
				/*deviceSearch.setHeight(28, Unit.PIXELS);
				search.setHeight("28px");
				startDateField.setHeight("28px");
				endDateField.setHeight("28px");*/
			}else {
				List<HorizontalLayout> HLList = getButtonsLayout(terminalButtonList,3);
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
		
		List<HorizontalLayout> HLList = getButtonsLayout(terminalButtonList, 2);
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
		
		differentModesLoad(secondPanelLayout, terminalButtonList);
	}
	
	/*private void configureDeviceSearchForFilter(TextField deviceSearch) {
		deviceSearch.addValueChangeListener(changed -> {
			
			try {
				List<Terminal> terminalList = new LinkedList<Terminal>();
				deviceSearch.setWidth("100%");
				String valueInLower = changed.getValue().toLowerCase();
				terminalList= heartBeatService.searchTerminal(valueInLower);
				List<Button> searchTerminalList = getTerminals(verticalDeviceFormLayout,terminalList);
				differentModesLoad(secondPanelLayout, searchTerminalList);
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
		//deleteLayoutHorizontal.addComponents(startDateField,endDateField,delete);
		deleteLayoutHorizontal.addComponents(startDateField,endDateField);
		
		searchAndDeleteLayoutHorizontal.addComponents(searchLayout, deleteLayoutHorizontal);
		searchLayout.removeStyleName("audit-phoneAlignment");
		searchAndDeleteLayoutVertical.setVisible(false);
		searchAndDeleteLayoutVerticalPhomeMode.setVisible(false);
		searchAndDeleteLayoutHorizontal.setVisible(true);
	}
	
	private void deletCalendarTabMode() {
		//deleteLayoutHorizontal.addComponents(startDateField,endDateField,delete);
		deleteLayoutHorizontal.addComponents(startDateField,endDateField);
		searchLayout.addStyleName("audit-phoneAlignment");
		searchAndDeleteLayoutVertical.addComponents(searchLayout, deleteLayoutHorizontal);;
		searchAndDeleteLayoutHorizontal.setVisible(false);
		searchAndDeleteLayoutVerticalPhomeMode.setVisible(false);
		searchAndDeleteLayoutVertical.setVisible(true);
	}
	
	private void deletCalendarPhoneMode() {
		//deleteLayoutVertical.addComponents(startDateField,endDateField,delete);
		deleteLayoutVertical.addComponents(startDateField,endDateField);
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
		deviceSearch.addValueChangeListener(changed -> {
			String valueInLower = changed.getValue().toLowerCase();
			try {
				List<TerminalClient> terminalList = new LinkedList<TerminalClient>();
				deviceSearch.setWidth("100%");
				terminalList= heartBeatService.searchTerminal(valueInLower);
				List<Button> searchTerminalList = getTerminals(verticalDeviceFormLayout,terminalList);
				differentModesLoad(secondPanelLayout, searchTerminalList);
				//deviceSearch.setValue(valueInLower);
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
		deviceSearch.addFocusListener(listener -> {
			clearAll();
			
		});
		
		/*deviceSearch.addShortcutListener(new ShortcutListener("Clear",KeyCode.ESCAPE,null) {
			
			@Override
			public void handleAction(Object sender, Object target) {
				if (target == deviceSearch) {
					deviceSearch.clear();
				}
			}
		});*/
		
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
	
	/*private void getDeviceData() {
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
	}*/
	
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
		                	try {
		                		List<Heartbeat> heartbeatHistortListServer = heartBeatService.deleteHeartbeat(hbHistoryGrid.getSelectedItems().iterator().next().getId());
								DataProvider data = new ListDataProvider(heartbeatHistortListServer);
								hbHistoryGrid.setDataProvider(data);	
								search.clear();
			                	hbHistoryGrid.getDataProvider().refreshAll();
			    				clearCalenderDates();
							} catch (ApiException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		    				
		                } else {
		                    // User did not confirm
		                    
		                }
		            }
		        });
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
			
			try {
				String valueInLower = valueChange.getValue().toLowerCase();
				String endDate =null;
				String startDate=null;
				if(endDateField.getValue()!=null && startDateField.getValue()!=null) {
					endDate = endDateField.getValue().format(dateFormatter1);
					startDate = startDateField.getValue().format(dateFormatter1);
				}
				List<Heartbeat> heartbeatHistortListServer = heartBeatService.searchHBHistoryByText(selectedTerminal.getEntityId(), valueInLower, startDate, endDate);
				DataProvider data  = new ListDataProvider<>( heartbeatHistortListServer);
				hbHistoryGrid.setDataProvider(data);
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*ListDataProvider<HeartBeatHistory> debugDataProvider = (ListDataProvider<Heartbeat>) hbHistoryGrid.getDataProvider();
			if(!debugDataProvider.getItems().isEmpty()) {
			debugDataProvider.setFilter(filter -> {
				String dateTime = filter.getDateTime().toLowerCase();
				String IP = filter.getIP().toLowerCase();
				String status = filter.getStatus().toLowerCase();
				String process = filter.getProcess().toLowerCase();
				return dateTime.contains(valueInLower) || IP.contains(valueInLower) || status.contains(valueInLower) || process.contains(valueInLower);
			});
			}*/
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
		//endDateField.setRangeEnd(localTimeNow.toLocalDate().plusDays(1));
		endDateField.setDescription("End Date");
		endDateField.setPlaceholder("End Date");
		endDateField.addStyleName("v-textfield-font");
		
		//Vertical Initialization
		searchAndDeleteLayoutVertical = new VerticalLayout();
		searchAndDeleteLayoutVertical.setVisible(false);
		searchAndDeleteLayoutVertical.addStyleNames("heartbeat-historySearchLayout", "heartbeat-historySearchVerticalLayout");
		
		//deleteLayout Vertical Initialization
		deleteLayoutVertical = new VerticalLayout();
		deleteLayoutVertical.setVisible(true);
		deleteLayoutVertical.setStyleName("heartbeat-verticalLayout");
		
		//Phone Mode
		searchAndDeleteLayoutVerticalPhomeMode = new VerticalLayout();
		searchAndDeleteLayoutVerticalPhomeMode.setVisible(false);
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
		//deleteLayoutHorizontal.addComponent(delete);
		deleteLayoutHorizontal.setVisible(true);
		
		searchAndDeleteLayoutHorizontal.addComponents(searchLayout, deleteLayoutHorizontal );
		searchAndDeleteLayoutHorizontal.setComponentAlignment(searchLayout, Alignment.TOP_LEFT);
		searchAndDeleteLayoutHorizontal.setComponentAlignment(deleteLayoutHorizontal, Alignment.MIDDLE_RIGHT);
		
		layout.addComponent(searchAndDeleteLayoutHorizontal);
		layout.addComponent(searchAndDeleteLayoutVertical);
		layout.addComponent(searchAndDeleteLayoutVerticalPhomeMode);
		
		endDateField.addValueChangeListener(change ->{
	         if(!(change.getValue() == null)) {
	        	 if(entityTypeTextFiled.getValue()!=null && !entityTypeTextFiled.getValue().isEmpty()) {
	        		 	if(startDateField.getValue()!=null) {
	        	 		if(change.getValue().compareTo(startDateField.getValue()) >0 ) {
	        	 			String endDate = endDateField.getValue().format(dateFormatter1);
	        	 			String startDate = startDateField.getValue().format(dateFormatter1);
	        	 			String filter = search.getValue();
	        	 			try {
	        					List<Heartbeat> heartbeatHistortListServer = heartBeatService.searchHBHistoryByDate(selectedTerminal.getEntityId(), filter, startDate, endDate);
	        					DataProvider data  = new ListDataProvider<>( heartbeatHistortListServer);
	        					hbHistoryGrid.setDataProvider(data);
	        				} catch (ApiException e) {
	        					// TODO Auto-generated catch block
	        					e.printStackTrace();
	        				}
	        	 			
	        	 				ListDataProvider<Heartbeat> hbHistoryDataProvider = (ListDataProvider<Heartbeat>) hbHistoryGrid.getDataProvider();
	        	 				/*hbHistoryDataProvider.setFilter(filter -> {
	        	 				Date hbHistoryDate = filter.getDate();
	        	 				try {
	        	 					return hbHistoryDate.after(dateFormatter.parse(startDateField.getValue().minusDays(1).toString())) && hbHistoryDate.before(dateFormatter.parse(change.getValue().plusDays(1).toString()));
	        	 				} catch (ParseException e) {
	        	 					System.out.println(e.getMessage() + filter.toString());
							
	        	 					return false;
	        	 				}
					});*/
				} else {
					Notification.show(NotificationUtil.AUDIT_SAMEDATE, Notification.Type.ERROR_MESSAGE);
				}
	        		 	}else {
	        		 		Notification.show(NotificationUtil.HEARTBEAT_STARTDATE, Notification.Type.ERROR_MESSAGE);
	        		 		endDateField.clear();
	        		 	}
	        }else {
	        	Notification.show(NotificationUtil.HEARTBEAT_SELECTDEVICE, Notification.Type.ERROR_MESSAGE);
	        	clearCalenderDates();
	        }
		}else {
			try {
				loadGridData(selectedTerminal.getEntityId());
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		});
		
		startDateField.addValueChangeListener(change ->{
			if(change.getValue()!=null) {
				endDateField.clear();
				endDateField.setRangeStart(change.getValue().plusDays(1));
				//endDateField.setRangeEnd(localTimeNow.toLocalDate().plusDays(1));
			}else {
				try {
					loadGridData(selectedTerminal.getEntityId());
				} catch (ApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	});
	}
	
	private void clearCalenderDates() {
		startDateField.clear();
		endDateField.clear();
	}

	
	private void getHBHistoryGrid(VerticalLayout layout) {
		VerticalLayout VL = new VerticalLayout();
		VL.setStyleName("heartbeat-verticalLayout");
		hbHistoryGrid= new Grid<>(Heartbeat.class);
		hbHistoryGrid.setWidth("100%");
		hbHistoryGrid.setHeightByRows(6);
		hbHistoryGrid.setResponsive(true);
		hbHistoryGrid.setSelectionMode(SelectionMode.SINGLE);
		hbHistoryGrid.setColumns("occurred", "ip", "status", "message");
		hbHistoryGrid.getColumn("occurred").setCaption("Date Time");
		hbHistoryGrid.getColumn("message").setCaption("Process");
		VL.addComponent(hbHistoryGrid);
		layout.addComponent(VL);
	}
	
	private void loadGridData(String entityId) throws ApiException {
		DataProvider data = new ListDataProvider<>(heartBeatService.getHeartbeatHistory(entityId));
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
	
	private List<Button> getTerminals(VerticalLayout verticalDeviceFormLayout, List<TerminalClient> terminalList){
		List<Button> buttons = new ArrayList<Button>();
		verticalDeviceFormLayout.addLayoutClickListener(listener->{
			clearAll();
		});
		for(TerminalClient terminal:terminalList) {
				if(terminal.isActive()==true) {
					Button terminalButton = new Button();
					int length = terminal.getLabel().length();
					if(length<15) {
						terminalButton.setCaption(terminal.getLabel());
					}else {
						terminalButton.setCaption(terminal.getLabel().substring(0, 15));
					}
					terminalButton.setDescription(terminal.getLabel());
						terminalButton.setIcon(new ThemeResource("terminalGood1.png"));
						terminalButton.addClickListener(new ClickListener() {
							public void buttonClick(ClickEvent event) {
								clearCalenderDates();
								verticalDeviceFormLayout.removeAllComponents();
								getDeviceFromLayout(verticalDeviceFormLayout, terminal, false);
								selectedTerminal=terminal;
									try {
										loadGridData(terminal.getEntityId());
									} catch (ApiException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							}
						});
					terminalButton.setPrimaryStyleName("v-heartbeat-Button");
					buttons.add(terminalButton);
				} else {
					Button terminalButton1 = new Button();
					int length = terminal.getLabel().length();
					if(length<15) {
						terminalButton1.setCaption(terminal.getLabel());
					}else {
						terminalButton1.setCaption(terminal.getLabel().substring(0, 15));
					}
					terminalButton1.setDescription(terminal.getLabel());
						terminalButton1.setIcon(new ThemeResource("terminalBad1.png"));
						terminalButton1.addClickListener(new ClickListener() {
							public void buttonClick(ClickEvent event) {
								clearCalenderDates();
								verticalDeviceFormLayout.removeAllComponents();
								getDeviceFromLayout(verticalDeviceFormLayout, terminal, false);
								selectedTerminal=terminal;
								try {
									loadGridData(terminal.getEntityId());
								} catch (ApiException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
					terminalButton1.setPrimaryStyleName("v-heartbeat-Button");
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
	
	private void getDeviceFromLayout(VerticalLayout layout,TerminalClient terminal, boolean isEditableOnly) {
		
		FormLayout deviceFormLayout = new FormLayout();
		deviceFormLayout.addStyleName("system-LabelAlignment");
		deviceFormLayout.setWidth("100%");
		
		getEntityType(terminal, deviceFormLayout, isEditableOnly);
		
		getDeviceName(terminal, deviceFormLayout, isEditableOnly);
		
		getDescription(terminal, deviceFormLayout, isEditableOnly);
		
		getActive(terminal, deviceFormLayout, isEditableOnly);
		
		getSerailNumber(terminal, deviceFormLayout, isEditableOnly);
		
		getHBAndFrqncy(terminal, deviceFormLayout, isEditableOnly);
		
		getLastSeen(terminal, deviceFormLayout, isEditableOnly);
		
		layout.addComponent(deviceFormLayout);
		
	}
	
	private void getEntityType(TerminalClient terminal, FormLayout deviceFormLayout, boolean isEditableOnly) {
		String entityType = terminal.getType()!= null ? terminal.getType(): "";
		entityTypeTextFiled = new TextField("Entity Type", entityType);
		terminal.setType(entityTypeTextFiled.getValue());
		entityTypeTextFiled.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		entityTypeTextFiled.setWidth("100%");
		entityTypeTextFiled.setStyleName("role-textbox");
		entityTypeTextFiled.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		entityTypeTextFiled.addStyleName("v-textfield-font");
		entityTypeTextFiled.setEnabled(isEditableOnly);
		deviceFormLayout.addComponent(entityTypeTextFiled);
	}
	
	private void getDeviceName(TerminalClient terminal, FormLayout deviceFormLayout, boolean isEditableOnly) {
		String name = terminal.getLabel() != null ? terminal.getLabel(): "";
		deviceName = new TextField("Name", name);
		terminal.setLabel(deviceName.getValue());
		deviceName.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		deviceName.setWidth("100%");
		deviceName.setStyleName("role-textbox");
		deviceName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		deviceName.addStyleName("v-textfield-font");
		deviceName.setEnabled(isEditableOnly);
		deviceFormLayout.addComponent(deviceName);
	}
	
	private void getDescription(TerminalClient terminal, FormLayout deviceFormLayout, boolean isEditableOnly) {
		String description = terminal.getDescription() != null ? terminal.getDescription(): "";
		deviceDescription = new TextField("Description", description);
		terminal.setDescription(deviceDescription.getValue());
		deviceDescription.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		deviceDescription.setWidth("100%");
		deviceDescription.setStyleName("role-textbox");
		deviceDescription.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		deviceDescription.addStyleName("v-textfield-font");
		deviceDescription.setEnabled(isEditableOnly);
		deviceFormLayout.addComponent(deviceDescription);
	}
	
	private void getActive(TerminalClient terminal, FormLayout deviceFormLayout, boolean isEditableOnly) {
		HorizontalLayout activeHL = new HorizontalLayout();
		activeHL.addStyleName("heartbeat-activeLayout");
		Label label = new Label("Active");
		boolean activeBoxValue = terminal.isActive();
		activeBox = new CheckBox("Allow Access", activeBoxValue);
		activeBox.setEnabled(isEditableOnly);
		activeBox.addStyleName("v-textfield-font");
		terminal.setActive(activeBox.getValue());
		//activeLabel = new Label("Active");
		label.setStyleName("role-activeLable");
		label.addStyleNames("v-textfield-font");
		label.addStyleName("heartbeat-checkbox");
		activeHL.addComponents(label, activeBox);
		deviceFormLayout.addComponent(activeHL);
	}
	
	private void getSerailNumber(TerminalClient terminal, FormLayout deviceFormLayout, boolean isEditableOnly) {
		String serailNumber = terminal.getSerialNumber() != null ? terminal.getSerialNumber(): "";
		deviceSerialNumber = new TextField("Serial Number", serailNumber);
		terminal.setSerialNumber(deviceSerialNumber.getValue());
		deviceSerialNumber.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		deviceSerialNumber.setWidth("100%");
		deviceSerialNumber.setStyleName("role-textbox");
		deviceSerialNumber.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		deviceSerialNumber.addStyleName("v-textfield-font");
		deviceSerialNumber.setEnabled(isEditableOnly);
		deviceFormLayout.addComponent(deviceSerialNumber);
	}
	
	private void getHBAndFrqncy(TerminalClient terminal, FormLayout deviceFormLayout, boolean isEditableOnly) {
		HorizontalLayout HL = new HorizontalLayout();
		HorizontalLayout heartBeatHL = new HorizontalLayout();
		HL.addStyleName("heartbeat-heartBeatLayout");
		
		Label label = new Label("Heartbeat");
		label.setStyleName("role-activeLable");
		label.addStyleNames("v-textfield-font");
		label.addStyleName("heartbeat-checkbox");
		
		boolean activateHBBoxValue = terminal.isActivateHeartbeat();
		activateHeartBeatBox = new CheckBox("Activate Heartbeat", activateHBBoxValue);
		activateHeartBeatBox.setEnabled(isEditableOnly);
		activateHeartBeatBox.addStyleName("v-textfield-font");
		terminal.setActivateHeartbeat(activateHeartBeatBox.getValue());
		heartBeatHL.addComponents(label, activateHeartBeatBox);
		
		String freqncy = terminal.getFrequency() != null ? terminal.getFrequency().toString(): "";
		deviceFrequency = new TextField("Frequency", freqncy.toString());
		//terminal.setFrequency(Long.valueOf(deviceFrequency.getValue()));
		deviceFrequency.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		deviceFrequency.setWidth("100%");
		deviceFrequency.setStyleName("role-textbox");
		deviceFrequency.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		deviceFrequency.addStyleName("v-textfield-font");
		deviceFrequency.setEnabled(isEditableOnly);
		HL.addComponent(heartBeatHL);
		deviceFormLayout.addComponent(HL);
		deviceFormLayout.addComponent(deviceFrequency);
	}
	
	private void getLastSeen(TerminalClient terminal, FormLayout deviceFormLayout, boolean isEditableOnly) {
		//HorizontalLayout descriptionHL = new HorizontalLayout();
		String lastSeen = terminal.getLastSeen() != null ? terminal.getLastSeen(): "";
		deviceLastSeen = new TextField("Last Seen", lastSeen);
		terminal.setLastSeen(deviceLastSeen.getValue());
		deviceLastSeen.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		deviceLastSeen.setWidth("100%");
		deviceLastSeen.setStyleName("role-textbox");
		deviceLastSeen.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS);
		deviceLastSeen.addStyleName("v-textfield-font");
		deviceLastSeen.setEnabled(isEditableOnly);
		//descriptionHL.addComponent(deviceDescription);
		deviceFormLayout.addComponent(deviceLastSeen);
	}
	
	private void clearAll() {
		clearCalenderDates();
		entityTypeTextFiled.clear();
		deviceName.clear();
		deviceDescription.clear();
		deviceSerialNumber.clear();
		deviceFrequency.clear();
		deviceLastSeen.clear();
		activeBox.clear();
		activateHeartBeatBox.clear();
		search.clear();
		hbHistoryGrid.setDataProvider(new ListDataProvider<>(Arrays.asList()));
		
	}
}
