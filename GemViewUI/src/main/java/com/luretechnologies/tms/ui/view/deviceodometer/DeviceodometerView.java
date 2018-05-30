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
package com.luretechnologies.tms.ui.view.deviceodometer;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.tms.backend.data.entity.Debug;
import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.Node;
import com.luretechnologies.tms.backend.service.DebugService;
import com.luretechnologies.tms.backend.service.OdometerDeviceService;
import com.luretechnologies.tms.backend.service.TreeDataService;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = DeviceodometerView.VIEW_NAME)
public class DeviceodometerView extends VerticalLayout implements Serializable, View{

	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateTimeFormatter  dateFormatter1 = DateTimeFormatter.ofPattern("MM-dd-YYYY");
	/**
	 * 
	 */
	private static final long serialVersionUID = -8746130479258235216L;
	public static final String VIEW_NAME = "deviceodometer";
	private static final LocalDateTime localTimeNow = LocalDateTime.now();
	private static Grid<Devices> odometerDeviceGrid;
	private static Tree<Node> nodeTree;
	private static Button deleteGridRow;
	private static TextField treeNodeSearch, odometerDeviceSearch;
	private static HorizontalSplitPanel splitScreen;
	private static DateField odometerStartDateField, odometerEndDateField;
	//private static VerticalLayout odometerDeviceLayout;
	private static VerticalLayout odometerDeviceLayout;
	private static HorizontalLayout optionsLayoutHorizontalDesktop;
	private static HorizontalLayout odometerSearchLayout ;
	private static VerticalLayout optionsLayoutVerticalTab;
	private static VerticalLayout optionsLayoutVerticalPhone;
	private static HorizontalLayout dateDeleteLayout;
	private static VerticalLayout dateDeleteLayoutPhone;
	
	private static HorizontalLayout panelTools; 
	
	@Autowired
	public DeviceodometerView() {
		
	}
	
	@Autowired
	public TreeDataService treeDataService;

	@Autowired
	public OdometerDeviceService odometerDeviceService;
	
	@PostConstruct
	private void inti() {
		
		Page.getCurrent().addBrowserWindowResizeListener(r->{
			System.out.println("Height "+ r.getHeight() + "Width:  " + r.getWidth()+ " in pixel");
			if(r.getWidth()<=1450 && r.getWidth()>=700) {
				tabMode();
				splitScreen.setSplitPosition(30);
				odometerStartDateField.setHeight("100%");
				odometerEndDateField.setHeight("100%");
				treeNodeSearch.setHeight(37, Unit.PIXELS);
				odometerDeviceSearch.setHeight(37, Unit.PIXELS);
				
			}else if(r.getWidth()<=699 && r.getWidth()> 0){
				phoneMode();
				splitScreen.setSplitPosition(35);
				odometerStartDateField.setHeight(28,Unit.PIXELS);
				odometerEndDateField.setHeight(28, Unit.PIXELS);
				treeNodeSearch.setHeight(28,Unit.PIXELS);
				odometerDeviceSearch.setHeight(28, Unit.PIXELS);
				
			} else {
				desktopMode();
				splitScreen.setSplitPosition(20);
				odometerStartDateField.setHeight("100%");
				odometerEndDateField.setHeight("100%");
				treeNodeSearch.setHeight(37, Unit.PIXELS);
				odometerDeviceSearch.setHeight(37, Unit.PIXELS);
			}
		});
		setSpacing(false);
		setMargin(false);
		setResponsive(true);
		setHeight("100%");
		treeNodeSearch = new TextField();
		treeNodeSearch.setWidth("100%");
		treeNodeSearch.setIcon(VaadinIcons.SEARCH);
		treeNodeSearch.setStyleName("small inline-icon search");
		//treeNodeSearch.addStyleName("search-Height");
		treeNodeSearch.setPlaceholder("Search");
		treeNodeSearch.addStyleName("v-textfield-font");
		treeNodeSearch.setHeight(37, Unit.PIXELS);
		configureTreeNodeSearch();
		
		Panel panel = getAndLoadOdometerPanel();
		VerticalLayout verticalPanelLayout = new VerticalLayout();
		verticalPanelLayout.setHeight("100%");
		verticalPanelLayout.setStyleName("split-height");
		VerticalLayout treeSearchPanelLayout = new VerticalLayout();
		treeSearchPanelLayout.addComponent(treeNodeSearch);
		nodeTree = new Tree<Node>();
		nodeTree.setTreeData(treeDataService.getTreeDataForDeviceOdometer());
		nodeTree.setItemIconGenerator(item -> {
			switch (item.getLevel()) {
			case ENTITY:
				return VaadinIcons.BUILDING_O;
			case MERCHANT:
				return VaadinIcons.SHOP;
			case REGION:
				return VaadinIcons.OFFICE;
			case TERMINAL:
				return VaadinIcons.LAPTOP;
			case DEVICE:
				return VaadinIcons.MOBILE_BROWSER;
			default:
				return null;
			}
		});
		
		treeSearchPanelLayout.addComponent(nodeTree);
		treeSearchPanelLayout.setMargin(true);
		//treeSearchPanelLayout.setHeight("100%");
		treeSearchPanelLayout.setStyleName("split-Height-ButtonLayout");
		verticalPanelLayout.addComponent(treeSearchPanelLayout);
		splitScreen = new HorizontalSplitPanel();
		splitScreen.setFirstComponent(verticalPanelLayout);
		splitScreen.setSplitPosition(20);
		splitScreen.addComponent(getOdometerDeviceLayout());
		splitScreen.setHeight("100%");
		panel.setContent(splitScreen);
		int width = Page.getCurrent().getBrowserWindowWidth();
		if(width >0 && width <=699) {
			phoneMode();
			splitScreen.setSplitPosition(35);
			odometerStartDateField.setHeight(28,Unit.PIXELS);
			odometerEndDateField.setHeight(28, Unit.PIXELS);
			treeNodeSearch.setHeight(28,Unit.PIXELS);
			odometerDeviceSearch.setHeight(28, Unit.PIXELS);
		} else if(width>=700 && width<=1400) {
			tabMode();
			splitScreen.setSplitPosition(30);
			odometerStartDateField.setHeight("100%");
			odometerEndDateField.setHeight("100%");
			treeNodeSearch.setHeight(37, Unit.PIXELS);
			odometerDeviceSearch.setHeight(37, Unit.PIXELS);
		}
		else {
			desktopMode();
			splitScreen.setSplitPosition(20);
			odometerStartDateField.setHeight("100%");
			odometerEndDateField.setHeight("100%");
			treeNodeSearch.setHeight(37, Unit.PIXELS);
			odometerDeviceSearch.setHeight(37, Unit.PIXELS);
			
		}
	}
	
	public Panel getAndLoadOdometerPanel() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setCaption("Device Odometer");
		panel.setResponsive(true);
		panel.setSizeFull();
		//panel.setStyleName("odometer-verticalLayout");
        addComponent(panel);
       return panel;
	}
	
	private void tabMode() {
		optionsLayoutVerticalTab.addStyleName("audit-tabAlignment");
		//odometerSearchLayout.addStyleName("audit-phoneAlignment");
		dateDeleteLayout.addComponent(odometerStartDateField);
		dateDeleteLayout.setComponentAlignment(odometerStartDateField, Alignment.TOP_RIGHT);
		dateDeleteLayout.addComponent(odometerEndDateField);
		dateDeleteLayout.setComponentAlignment(odometerEndDateField, Alignment.TOP_RIGHT);
		dateDeleteLayout.addComponent(deleteGridRow);
		dateDeleteLayout.setComponentAlignment(deleteGridRow, Alignment.TOP_LEFT);
		optionsLayoutHorizontalDesktop.addComponent(dateDeleteLayout);
		optionsLayoutVerticalTab.addComponents(odometerSearchLayout,dateDeleteLayout);
		
		optionsLayoutHorizontalDesktop.setVisible(false);
		optionsLayoutVerticalPhone.setVisible(false);
		optionsLayoutVerticalTab.setVisible(true);
	}
	
	private void phoneMode() {
		dateDeleteLayoutPhone.addComponents(odometerStartDateField, odometerEndDateField,deleteGridRow);
		optionsLayoutVerticalPhone.addStyleName("audit-phoneAlignment");
		odometerSearchLayout.addStyleName("audit-phoneAlignment");
		optionsLayoutVerticalPhone.addComponents(odometerSearchLayout,dateDeleteLayoutPhone);
		optionsLayoutVerticalPhone.setVisible(true);
		optionsLayoutHorizontalDesktop.setVisible(false);
		optionsLayoutVerticalTab.setVisible(false);
	}
	
	private void desktopMode() {
		optionsLayoutHorizontalDesktop.addComponent(odometerSearchLayout);
		optionsLayoutHorizontalDesktop.setComponentAlignment(odometerSearchLayout, Alignment.MIDDLE_LEFT);
		dateDeleteLayout.addComponent(odometerStartDateField);
		dateDeleteLayout.setComponentAlignment(odometerStartDateField, Alignment.TOP_RIGHT);
		dateDeleteLayout.addComponent(odometerEndDateField);
		dateDeleteLayout.setComponentAlignment(odometerEndDateField, Alignment.TOP_RIGHT);
		dateDeleteLayout.addComponent(deleteGridRow);
		dateDeleteLayout.setComponentAlignment(deleteGridRow, Alignment.TOP_LEFT);
		optionsLayoutHorizontalDesktop.addComponent(dateDeleteLayout);
		//optionsLayoutHorizontalDesktop.addComponent(dateDeleteLayout);
		optionsLayoutHorizontalDesktop.setComponentAlignment(dateDeleteLayout, Alignment.TOP_RIGHT);
		optionsLayoutHorizontalDesktop.setVisible(true);
		optionsLayoutVerticalTab.setVisible(false);
		optionsLayoutVerticalPhone.setVisible(false);
	}
	
	private void clearCalenderDates() {
		odometerStartDateField.clear();
		odometerEndDateField.clear();
	}
	
	private void configureTreeNodeSearch() {
		treeNodeSearch.addValueChangeListener(changed -> {
			String valueInLower = changed.getValue().toLowerCase();
			nodeTree.setTreeData(treeDataService.getFilteredTreeByNodeName(treeDataService.getTreeDataForDeviceOdometer(), valueInLower));
			//FIXME: only works for root node labels
//			TreeDataProvider<Node> nodeDataProvider = (TreeDataProvider<Node>) nodeTree.getDataProvider();
//			nodeDataProvider.setFilter(filter -> {
//				return filter.getLabel().toLowerCase().contains(valueInLower);
//			});
		});
		
		treeNodeSearch.addShortcutListener(new ShortcutListener("Clear",KeyCode.ESCAPE,null) {
			
			@Override
			public void handleAction(Object sender, Object target) {
				if (target == treeNodeSearch) {
					treeNodeSearch.clear();
				}
			}
		});
	}
	
	private void confirmDialog() {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?",
		        "Ok", "Cancel", new ConfirmDialog.Listener() {

		            public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		                    // Confirmed to continue
		                	odometerDeviceService.removeOdometerDevice(odometerDeviceGrid.getSelectedItems().iterator().next());
		    				nodeTree.getDataProvider().refreshAll();
		    				odometerDeviceSearch.clear();
		    				loadGrid();
		    				clearCalenderDates();
		    				
		                } else {
		                    // User did not confirm
		                    
		                }
		            }
		        });
	}
	
	private void loadGrid() {
		treeDataService.getTreeDataForDeviceOdometer();
		List<Node> nodeList = treeDataService.getOdometerDeviceList();
		Set<Node> nodeSet = nodeTree.getSelectionModel().getSelectedItems();
		Node nodeSelected = nodeSet.iterator().next();
		for(Node node : nodeList) {
			if(node.getLabel().equals(nodeSelected.getLabel())) {
				DataProvider data = new ListDataProvider(node.getEntityList());
				odometerDeviceGrid.setDataProvider(data);
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private VerticalLayout getOdometerDeviceLayout() {
		//VerticalLayout verticalLayout = new VerticalLayout();
		//verticalLayout.setWidth("100%");
		//verticalLayout.setHeight("100%");
		//verticalLayout.setStyleName("split-height");
		 //odometerDeviceLayout = new VerticalLayout();
		odometerDeviceLayout = new VerticalLayout();
		odometerDeviceLayout.addStyleName(ValoTheme.LAYOUT_CARD);
		
		odometerDeviceLayout.setWidth("100%");
		odometerDeviceLayout.setHeight("100%");
		//odometerDeviceLayout.setStyleName("odometer-verticalLayout");
		odometerDeviceLayout.addStyleName("audit-DeviceVerticalAlignment");
		odometerDeviceLayout.setResponsive(true);
		odometerDeviceGrid = new Grid<>(Devices.class);
		odometerDeviceGrid.setWidth("100%");
		//odometerDeviceGrid.setHeightByRows(16);
		odometerDeviceGrid.setHeight("100%");
		odometerDeviceGrid.addStyleName("grid-AuditOdometerAlignment");
		//odometerDeviceGrid.setSizeFull();
		odometerDeviceGrid.setResponsive(true);
		odometerDeviceGrid.setSelectionMode(SelectionMode.SINGLE);
		odometerDeviceGrid.setColumns("statusType", "description", "statistics");
		odometerDeviceSearch = new TextField();
		odometerDeviceSearch.setWidth("100%");
		odometerDeviceSearch.setIcon(VaadinIcons.SEARCH);
		odometerDeviceSearch.setStyleName("small inline-icon search");
		//odometerDeviceSearch.addStyleName("search-Height");
		odometerDeviceSearch.setHeight(37, Unit.PIXELS);
		odometerDeviceSearch.addStyleName("v-textfield-font");
		odometerDeviceSearch.setPlaceholder("Search");
		odometerDeviceSearch.setResponsive(true);
		odometerDeviceSearch.addShortcutListener(new ShortcutListener("Clear",KeyCode.ESCAPE,null) {
			
			@Override
			public void handleAction(Object sender, Object target) {
				if(target == odometerDeviceSearch) {
					odometerDeviceSearch.clear();
				}
				
			}
		}		);
		odometerDeviceSearch.addValueChangeListener(valueChange -> {
			String valueInLower = valueChange.getValue().toLowerCase();
			ListDataProvider<Devices> odometerDeviceDataProvider = (ListDataProvider<Devices>) odometerDeviceGrid.getDataProvider();
			odometerDeviceDataProvider.setFilter(filter -> {
				String descriptionInLower = filter.getDescription().toLowerCase();
				String typeInLower = filter.getStatusType().name().toLowerCase();
				String statistics = filter.getStatistics().toString().toLowerCase();
				return ((typeInLower.contains(valueInLower)) || (descriptionInLower.contains(valueInLower)) || (statistics.contains(valueInLower)));
			});
		});

			deleteGridRow = new Button(VaadinIcons.TRASH);
			//deleteGridRow.setWidth("100%");
			deleteGridRow.addStyleName(ValoTheme.BUTTON_FRIENDLY);
			deleteGridRow.setResponsive(true);
			deleteGridRow.addClickListener(clicked -> {
				if(odometerDeviceGrid.getSelectedItems().isEmpty()) {
					Notification.show("Select any Statistic type to delete", Notification.Type.ERROR_MESSAGE).setDelayMsec(3000);;
				}else {
					confirmDialog();
				}
			});
		
		optionsLayoutHorizontalDesktop = new HorizontalLayout();
		optionsLayoutHorizontalDesktop.setWidth("100%");
		//optionsLayoutHorizontalDesktop.setHeight("50%");
		optionsLayoutHorizontalDesktop.setResponsive(true);
		optionsLayoutHorizontalDesktop.addStyleName("audit-DeviceSearch");
		
		odometerSearchLayout = new HorizontalLayout();
		odometerSearchLayout.setWidth("100%");
		odometerSearchLayout.addComponent(odometerDeviceSearch);
		odometerSearchLayout.setComponentAlignment(odometerDeviceSearch, Alignment.TOP_LEFT);
		//odometerSearchLayout.addStyleName("audit-tabAlignment");
		
		dateDeleteLayout = new HorizontalLayout();
		dateDeleteLayout.setWidth("100%");
		
		odometerStartDateField = new DateField();
		odometerStartDateField.setWidth("100%");
		odometerStartDateField.setHeight("100%");
		odometerStartDateField.setPlaceholder("Start Date");
		odometerStartDateField.setResponsive(true);
		odometerStartDateField.setDateFormat(DATE_FORMAT);
		odometerStartDateField.setRangeEnd(localTimeNow.toLocalDate());
		odometerStartDateField.setDescription("Start Date");
		odometerStartDateField.addStyleName("v-textfield-font");
		
		odometerEndDateField = new DateField();
		odometerEndDateField.setWidth("100%");
		odometerEndDateField.setHeight("100%");
		odometerEndDateField.setPlaceholder("End Date");
		odometerEndDateField.setResponsive(true);
		odometerEndDateField.setDateFormat(DATE_FORMAT);
		odometerEndDateField.setRangeEnd(localTimeNow.toLocalDate().plusDays(1));
		odometerEndDateField.setDateOutOfRangeMessage("Same Date cannot be selected");
		odometerEndDateField.setDescription("End Date");
		odometerEndDateField.addStyleName("v-textfield-font");
		
		//Vertical Initialization
				optionsLayoutVerticalTab = new VerticalLayout();
//				optionsLayoutVertical.addComponent(debugSearchLayout);
				optionsLayoutVerticalTab.setVisible(false);
				optionsLayoutVerticalTab.addStyleName("audit-DeviceSearch");
				
				//Vertical Phone Mode 
				optionsLayoutVerticalPhone= new VerticalLayout();
				optionsLayoutVerticalPhone.setVisible(false);
				optionsLayoutVerticalPhone.addStyleName("audit-DeviceSearch");
				dateDeleteLayoutPhone = new VerticalLayout();
				dateDeleteLayoutPhone.setVisible(true);
				dateDeleteLayoutPhone.addStyleName("audit-phoneAlignment");
				
				dateDeleteLayout = new HorizontalLayout();
				dateDeleteLayout.setVisible(true);
				dateDeleteLayout.setWidth("100%");
		
		optionsLayoutHorizontalDesktop.addComponent(odometerSearchLayout);
		optionsLayoutHorizontalDesktop.setComponentAlignment(odometerSearchLayout, Alignment.MIDDLE_LEFT);
		dateDeleteLayout.addComponent(odometerStartDateField);
		dateDeleteLayout.setComponentAlignment(odometerStartDateField, Alignment.TOP_LEFT);
		dateDeleteLayout.addComponent(odometerEndDateField);
		dateDeleteLayout.setComponentAlignment(odometerEndDateField, Alignment.TOP_LEFT);
		dateDeleteLayout.addComponent(deleteGridRow);
		dateDeleteLayout.setComponentAlignment(deleteGridRow, Alignment.TOP_LEFT);
		optionsLayoutHorizontalDesktop.addComponent(dateDeleteLayout);
		optionsLayoutHorizontalDesktop.setComponentAlignment(dateDeleteLayout, Alignment.TOP_LEFT);
		
		
		odometerDeviceLayout.addComponent(optionsLayoutHorizontalDesktop);
		odometerDeviceLayout.setComponentAlignment(optionsLayoutHorizontalDesktop, Alignment.TOP_LEFT);
		odometerDeviceLayout.addComponent(optionsLayoutVerticalTab);
		odometerDeviceLayout.setComponentAlignment(optionsLayoutVerticalTab, Alignment.TOP_LEFT);
		odometerDeviceLayout.addComponent(optionsLayoutVerticalPhone);
		odometerDeviceLayout.setComponentAlignment(optionsLayoutVerticalPhone, Alignment.TOP_LEFT);
		odometerDeviceLayout.addComponent(odometerDeviceGrid);
		odometerDeviceLayout.setExpandRatio(odometerDeviceGrid, 2);
		
		
		//end Date listner
		odometerEndDateField.addValueChangeListener(change ->{
         if(!(change.getValue() == null)) {
        	 if(!nodeTree.getSelectedItems().isEmpty()) {
        		 	if(odometerStartDateField.getValue()!=null) {
        	 		if(change.getValue().compareTo(odometerStartDateField.getValue()) >= 0 ) {
        	 			String date = odometerEndDateField.getValue().format(dateFormatter1);
        	 			odometerEndDateField.setDescription(date);
        	 				ListDataProvider<Devices> odometerDeviceDataProvider = (ListDataProvider<Devices>) odometerDeviceGrid.getDataProvider();
        	 				odometerDeviceDataProvider.setFilter(filter -> {
        	 				Date odometerDeviceDate = filter.getDeviceDate();
        	 				try {
        	 					return odometerDeviceDate.after(dateFormatter.parse(odometerStartDateField.getValue().minusDays(1).toString())) && odometerDeviceDate.before(dateFormatter.parse(change.getValue().plusDays(1).toString()));
        	 				} catch (ParseException e) {
        	 					System.out.println(e.getMessage() + filter.toString());
						
        	 					return false;
        	 				}
				});
			} 
        		 	}else {
        		 		Notification.show("Select Start Date to filter the data", Notification.Type.WARNING_MESSAGE).setDelayMsec(3000);
        		 		odometerEndDateField.clear();
        		 	}
        }else {
        	Notification.show("Please Select any Node to filter the data", Notification.Type.WARNING_MESSAGE).setDelayMsec(3000);
        	clearCalenderDates();
        }
	}
	});
		odometerStartDateField.addValueChangeListener(change ->{
			if(change.getValue()!=null) {
				odometerEndDateField.clear();
				odometerEndDateField.setRangeStart(odometerStartDateField.getValue().plusDays(1));
				odometerEndDateField.setRangeEnd(localTimeNow.toLocalDate().plusDays(1));
				String date = odometerStartDateField.getValue().format(dateFormatter1);
				odometerStartDateField.setDescription(date);
			}
	});
		
			nodeTree.addItemClickListener(selection ->{			
			treeDataService.getTreeDataForDeviceOdometer();
			List<Node> nodeList = treeDataService.getOdometerDeviceList();
			for(Node node : nodeList) {
				if(node.getLabel().equals(selection.getItem().getLabel())) {
					DataProvider data = new ListDataProvider(node.getEntityList());
					odometerDeviceGrid.setDataProvider(data);
					odometerDeviceSearch.clear();
				}
			}
			clearCalenderDates();
				
		});
			//verticalLayout.addComponent(odometerDeviceLayout);
		return odometerDeviceLayout;
	}
}
