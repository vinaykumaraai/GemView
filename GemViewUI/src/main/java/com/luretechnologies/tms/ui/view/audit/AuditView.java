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
package com.luretechnologies.tms.ui.view.audit;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.tms.backend.data.entity.Debug;
import com.luretechnologies.tms.backend.data.entity.Node;
import com.luretechnologies.tms.backend.data.entity.Systems;
import com.luretechnologies.tms.backend.service.DebugService;
import com.luretechnologies.tms.backend.service.TreeDataService;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.StyleGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = AuditView.VIEW_NAME)
public class AuditView extends VerticalLayout implements Serializable, View {

	private static final String DATE_FORMAT = "MM/dd/yyyy";
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateTimeFormatter  dateFormatter1 = DateTimeFormatter.ofPattern("MM-dd-YYYY");
	/**
	 * 
	 */
	private static final long serialVersionUID = -7983511214106963682L;
	public static final String VIEW_NAME = "audit";
	private static final LocalDateTime localTimeNow = LocalDateTime.now();
	private static Grid<Debug> debugGrid;
	private static Tree<Node> nodeTree;
	private static Button deleteGridRow;
	private static Button search;
	private static TextField treeNodeSearch, debugSearch;
	private static HorizontalSplitPanel splitScreen;
	private static DateField debugStartDateField, debugEndDateField;
	private static VerticalLayout debugLayoutFull;
	private static HorizontalLayout optionsLayoutHorizontalDesktop;
	private static VerticalLayout optionsLayoutVerticalTab;
	private static VerticalLayout optionsLayoutVerticalPhone;
	private static HorizontalLayout debugSearchLayout;
	private static HorizontalLayout dateDeleteLayout;
	private static VerticalLayout dateDeleteLayoutPhone;
	
	

	@Autowired
	public AuditView() {

	}

	@Autowired
	public TreeDataService treeDataService;

	@Autowired
	public DebugService debugService;

	@PostConstruct
	private void init() {
		//FIXME : Sample code for browser resizing .
		Page.getCurrent().addBrowserWindowResizeListener(r->{
			System.out.println("Height "+ r.getHeight() + "Width:  " + r.getWidth()+ " in pixel");
			if(r.getWidth()<=1450 && r.getWidth()>=700) {
				tabMode();
				splitScreen.setSplitPosition(30);
			}else if(r.getWidth()<=699 && r.getWidth()> 0){
				phoneMode();
				splitScreen.setSplitPosition(35);
				
			} else {
				desktopMode();
				splitScreen.setSplitPosition(20);
			}
		});
		setSpacing(false);
		setMargin(false);
		setResponsive(true);
		setHeight("100%");
		treeNodeSearch = new TextField();
		treeNodeSearch.setIcon(VaadinIcons.SEARCH);
		treeNodeSearch.setStyleName("small inline-icon search");
		treeNodeSearch.addStyleName("v-textfield-font");
		treeNodeSearch.setPlaceholder("Search");
		configureTreeNodeSearch();
		
		Panel panel = getAndLoadAuditPanel();
		VerticalLayout treePanelLayout = new VerticalLayout();
		nodeTree = new Tree<Node>();
		nodeTree.setTreeData(treeDataService.getTreeDataForDebug());
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
		
		treePanelLayout.addComponent(nodeTree);
		treePanelLayout.setMargin(true);
		treePanelLayout.setHeight("100%");
		treePanelLayout.setStyleName("split-height");
		splitScreen = new HorizontalSplitPanel();
		splitScreen.setFirstComponent(treePanelLayout);
		splitScreen.setSplitPosition(20);
		splitScreen.addComponent(getDebugLayout());
		splitScreen.setHeight("100%");
		panel.setContent(splitScreen);
		int width = Page.getCurrent().getBrowserWindowWidth();
		if(width >0 && width <=699) {
			phoneMode();
			splitScreen.setSplitPosition(35);
		} else if(width>=700 && width<=1400) {
			tabMode();
			splitScreen.setSplitPosition(30);
		}
		else {
			desktopMode();
			splitScreen.setSplitPosition(20);
		}
	}
	
	
	
	private void tabMode() {
		optionsLayoutVerticalTab.addStyleName("heartbeat-verticalLayout");
		dateDeleteLayout.addComponent(debugStartDateField);
		dateDeleteLayout.setComponentAlignment(debugStartDateField, Alignment.TOP_RIGHT);
		dateDeleteLayout.addComponent(debugEndDateField);
		dateDeleteLayout.setComponentAlignment(debugEndDateField, Alignment.TOP_RIGHT);
		dateDeleteLayout.addComponent(deleteGridRow);
		dateDeleteLayout.setComponentAlignment(deleteGridRow, Alignment.TOP_LEFT);
		optionsLayoutVerticalTab.addComponents(debugSearchLayout,dateDeleteLayout);
		
		optionsLayoutHorizontalDesktop.setVisible(false);
		optionsLayoutVerticalPhone.setVisible(false);
		optionsLayoutVerticalTab.setVisible(true);
	}
	
	private void phoneMode() {
		dateDeleteLayoutPhone.addComponents(debugStartDateField, debugEndDateField,deleteGridRow);
		optionsLayoutVerticalPhone.addStyleName("heartbeat-verticalLayout");
		optionsLayoutVerticalPhone.addComponents(debugSearchLayout,dateDeleteLayoutPhone);
		optionsLayoutVerticalPhone.setVisible(true);
		optionsLayoutHorizontalDesktop.setVisible(false);
		optionsLayoutVerticalTab.setVisible(false);
	}
	
	private void desktopMode() {
		optionsLayoutHorizontalDesktop.addComponent(debugSearchLayout);
		optionsLayoutHorizontalDesktop.setComponentAlignment(debugSearchLayout, Alignment.MIDDLE_LEFT);
		dateDeleteLayout.addComponent(debugStartDateField);
		dateDeleteLayout.setComponentAlignment(debugStartDateField, Alignment.TOP_RIGHT);
		dateDeleteLayout.addComponent(debugEndDateField);
		dateDeleteLayout.setComponentAlignment(debugEndDateField, Alignment.TOP_RIGHT);
		dateDeleteLayout.addComponent(deleteGridRow);
		dateDeleteLayout.setComponentAlignment(deleteGridRow, Alignment.TOP_LEFT);
		optionsLayoutHorizontalDesktop.addComponent(dateDeleteLayout);
		//optionsLayoutHorizontalDesktop.addComponent(dateDeleteLayout);
		optionsLayoutHorizontalDesktop.setComponentAlignment(dateDeleteLayout, Alignment.TOP_RIGHT);
		optionsLayoutHorizontalDesktop.setVisible(true);
		optionsLayoutVerticalTab.setVisible(false);
		optionsLayoutVerticalPhone.setVisible(false);
	}
	public Panel getAndLoadAuditPanel() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setCaption("Audit");
		panel.setResponsive(true);
		panel.setSizeFull();
        addComponent(panel);
       return panel;
	}

	private void configureTreeNodeSearch() {
		// FIXME Not able to put Tree Search since its using a Hierarchical
		treeNodeSearch.addValueChangeListener(changed -> {
			String valueInLower = changed.getValue().toLowerCase();
//			ListDataProvider<Node> nodeDataProvider = (ListDataProvider<Node>) nodeTree.getDataProvider();
//			nodeDataProvider.setFilter(filter -> {
//				return filter.getLabel().toLowerCase().contains(valueInLower);
//			});
		});
	}
	
	private void clearCalenderDates() {
		debugStartDateField.clear();
		debugEndDateField.clear();
	}

	private void confirmDialog() {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?",
		        "Ok", "Cancel", new ConfirmDialog.Listener() {

		            public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		                    // Confirmed to continue
		                	debugService.removeDebug(debugGrid.getSelectedItems().iterator().next());
		    				nodeTree.getDataProvider().refreshAll();
		    				debugSearch.clear();
		    				loadGrid();
		    				clearCalenderDates();
		                } else {
		                    // User did not confirm
		                    
		                }
		            }
		        });
	}
	
	private void loadGrid() {
		treeDataService.getTreeDataForDebug();
		List<Node> nodeList = treeDataService.getDebugNodeList();
		Set<Node> nodeSet = nodeTree.getSelectionModel().getSelectedItems();
		Node nodeSelected = nodeSet.iterator().next();
		for(Node node : nodeList) {
			if(node.getLabel().equals(nodeSelected.getLabel())) {
				DataProvider data = new ListDataProvider(node.getEntityList());
				debugGrid.setDataProvider(data);
			}
		}
		
	}
	
	private VerticalLayout getDebugLayout() {
		//debugLayoutFull.removeAllComponents();
		debugLayoutFull =  new VerticalLayout();
		debugLayoutFull.setWidth("100%");
		debugLayoutFull.setResponsive(true);
		debugGrid = new Grid<>(Debug.class);
		debugGrid.setWidth("100%");
		debugGrid.setHeightByRows(20);
		debugGrid.setResponsive(true);
		debugGrid.setSelectionMode(SelectionMode.SINGLE);
		debugGrid.setColumns("type", "description", "dateTime");
		debugGrid.getColumn("type").setCaption("Debug Type").setStyleGenerator(style -> {
			switch (style.getType()) {
			case ERROR:
				return "error";
			case WARN:
				return "warn";

			default:
				return "";
			}
		});

		debugSearch = new TextField();
		//debugSearch
		debugSearch.setWidth("100%");
		debugSearch.setIcon(VaadinIcons.SEARCH);
		debugSearch.setStyleName("small inline-icon search");
		debugSearch.setPlaceholder("Search");
		debugSearch.setResponsive(true);
		debugSearch.addShortcutListener(new ShortcutListener("Clear",KeyCode.ESCAPE,null) {
			
			@Override
			public void handleAction(Object sender, Object target) {
				if(target == debugSearch) {
					debugSearch.clear();
				}
				
			}
		}		);
		debugSearch.addValueChangeListener(valueChange -> {
			String valueInLower = valueChange.getValue().toLowerCase();
			ListDataProvider<Debug> debugDataProvider = (ListDataProvider<Debug>) debugGrid.getDataProvider();
			debugDataProvider.setFilter(filter -> {
				String descriptionInLower = filter.getDescription().toLowerCase();
				String typeInLower = filter.getType().name().toLowerCase();
				String dateTime = filter.getDateTime().toLowerCase();
				return typeInLower.equals(valueInLower) || descriptionInLower.contains(valueInLower) || dateTime.contains(valueInLower);
			});
		});

			deleteGridRow = new Button(VaadinIcons.TRASH);
			deleteGridRow.addStyleName(ValoTheme.BUTTON_FRIENDLY);
			//deleteGridRow.addStyleName("v-button-customstyle");
			deleteGridRow.setResponsive(true);
			//deleteGridRow.setWidth("100%");
			deleteGridRow.addClickListener(clicked -> {
				if(debugGrid.getSelectedItems().isEmpty()) {
					Notification.show("Select any Debug type to delete", Notification.Type.WARNING_MESSAGE).setDelayMsec(3000);;
				}else {
					confirmDialog();
				}
			});
		
		
		optionsLayoutHorizontalDesktop = new HorizontalLayout();
		optionsLayoutHorizontalDesktop.setWidth("100%");
		optionsLayoutHorizontalDesktop.setHeight("50%");
		optionsLayoutHorizontalDesktop.setResponsive(true);
		
		debugSearchLayout = new HorizontalLayout();
		debugSearchLayout.setVisible(true);
		debugSearchLayout.setWidth("100%");
		debugSearchLayout.addComponent(debugSearch);
		debugSearchLayout.setComponentAlignment(debugSearch, Alignment.TOP_LEFT);
		
		
		//dateDeleteLayout.setSizeUndefined();
		
		debugStartDateField = new DateField();
		debugStartDateField.setWidth("100%");
		//debugStartDateField.setCaption("Start Date");
		debugStartDateField.setPlaceholder("Start Date");
		debugStartDateField.setResponsive(true);
		debugStartDateField.setDateFormat(DATE_FORMAT);
		debugStartDateField.setRangeEnd(localTimeNow.toLocalDate());
		debugStartDateField.setDescription("Start Date");
		debugStartDateField.addStyleName("v-textfield-font");
		
		debugEndDateField = new DateField();
		debugEndDateField.setWidth("100%");
		//debugEndDateField.setCaption("End Date");
		debugEndDateField.setPlaceholder("End Date");
		debugEndDateField.setResponsive(true);
		debugEndDateField.setDateFormat(DATE_FORMAT);
		//debugEndDateField.setRangeEnd(localTimeNow.toLocalDate().plusDays(1));
		debugEndDateField.setDateOutOfRangeMessage("Same Date cannot be selected");
		debugEndDateField.setDescription("End Date");
		debugEndDateField.addStyleName("v-textfield-font");
		
		//Vertical Initialization
		optionsLayoutVerticalTab = new VerticalLayout();
//		optionsLayoutVertical.addComponent(debugSearchLayout);
		optionsLayoutVerticalTab.setVisible(false);
		
		//Vertical Phone Mode 
		optionsLayoutVerticalPhone= new VerticalLayout();
		optionsLayoutVerticalPhone.setVisible(false);
		dateDeleteLayoutPhone = new VerticalLayout();
		dateDeleteLayoutPhone.setVisible(true);
		dateDeleteLayoutPhone.addStyleName("heartbeat-verticalLayout");
		
		dateDeleteLayout = new HorizontalLayout();
		dateDeleteLayout.setVisible(true);
		dateDeleteLayout.setWidth("100%");
		
		optionsLayoutHorizontalDesktop.addComponent(debugSearchLayout);
		optionsLayoutHorizontalDesktop.setComponentAlignment(debugSearchLayout, Alignment.MIDDLE_LEFT);
		dateDeleteLayout.addComponent(debugStartDateField);
		dateDeleteLayout.setComponentAlignment(debugStartDateField, Alignment.TOP_RIGHT);
		dateDeleteLayout.addComponent(debugEndDateField);
		dateDeleteLayout.setComponentAlignment(debugEndDateField, Alignment.TOP_RIGHT);
		dateDeleteLayout.addComponent(deleteGridRow);
		dateDeleteLayout.setComponentAlignment(deleteGridRow, Alignment.TOP_LEFT);
		optionsLayoutHorizontalDesktop.addComponent(dateDeleteLayout);
		optionsLayoutHorizontalDesktop.setComponentAlignment(dateDeleteLayout, Alignment.TOP_RIGHT);
		
		
		//optionsLayout.addComponents(debugSearch,debugStartDateField,debugEndDateField, deleteGridRow);
		debugLayoutFull.addComponent(optionsLayoutHorizontalDesktop);
		debugLayoutFull.setComponentAlignment(optionsLayoutHorizontalDesktop, Alignment.TOP_LEFT);
		debugLayoutFull.addComponent(optionsLayoutVerticalTab);
		debugLayoutFull.setComponentAlignment(optionsLayoutVerticalTab, Alignment.TOP_LEFT);
		debugLayoutFull.addComponent(optionsLayoutVerticalPhone);
		debugLayoutFull.setComponentAlignment(optionsLayoutVerticalPhone, Alignment.TOP_LEFT);
		debugLayoutFull.addComponent(debugGrid);
		
		
		//end Date listner
		debugEndDateField.addValueChangeListener(change ->{
         if(!(change.getValue() == null)) {
        	 if(!nodeTree.getSelectedItems().isEmpty()) {
        		 	if(debugStartDateField.getValue()!=null) {
        	 		if(change.getValue().compareTo(debugStartDateField.getValue()) >= 0 ) {
        	 			String date = debugEndDateField.getValue().format(dateFormatter1);
        	 			debugEndDateField.setDescription(date);
        	 				ListDataProvider<Debug> debugDataProvider = (ListDataProvider<Debug>) debugGrid.getDataProvider();
        	 				debugDataProvider.setFilter(filter -> {
        	 				Date debugDate = filter.getDateOfDebug();
        	 				try {
        	 					return debugDate.after(dateFormatter.parse(debugStartDateField.getValue().minusDays(1).toString())) && debugDate.before(dateFormatter.parse(change.getValue().plusDays(1).toString()));
        	 				} catch (ParseException e) {
        	 					System.out.println(e.getMessage() + filter.toString());
						
        	 					return false;
        	 				}
				});
			} 
        		 	}else {
        		 		Notification.show("Select Start Date to filter the data", Notification.Type.WARNING_MESSAGE).setDelayMsec(3000);
        		 		debugEndDateField.clear();
        		 	}
        }else {
        	Notification.show("Please Select any Node to filter the data", Notification.Type.WARNING_MESSAGE).setDelayMsec(3000);
        	clearCalenderDates();
        }
	}
	});
		debugStartDateField.addValueChangeListener(change ->{
			if(change.getValue()!=null) {
				debugEndDateField.clear();
				debugEndDateField.setRangeStart(change.getValue().plusDays(1));
				//debugEndDateField.setRangeEnd(localTimeNow.toLocalDate().plusDays(1));
				String date = debugStartDateField.getValue().format(dateFormatter1);
				debugStartDateField.setDescription(date);
			}
	});
		
			nodeTree.addItemClickListener(selection ->{			
			treeDataService.getTreeDataForDebug();
			List<Node> nodeList = treeDataService.getDebugNodeList();
			for(Node node : nodeList) {
				if(node.getLabel().equals(selection.getItem().getLabel())) {
					DataProvider data = new ListDataProvider(node.getEntityList());
					debugGrid.setDataProvider(data);
					debugSearch.clear();
				}
			}
			clearCalenderDates();
				
		});
		return debugLayoutFull;
	}
}
