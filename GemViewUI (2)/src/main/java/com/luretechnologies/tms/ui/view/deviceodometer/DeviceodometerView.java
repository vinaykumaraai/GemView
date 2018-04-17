package com.luretechnologies.tms.ui.view.deviceodometer;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
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
	
	@Autowired
	public DeviceodometerView() {
		
	}
	
	@Autowired
	public TreeDataService treeDataService;

	@Autowired
	public OdometerDeviceService odometerDeviceService;
	
	@PostConstruct
	private void inti() {
		setSpacing(false);
		setMargin(false);
		setResponsive(true);
		setHeight("100%");
		treeNodeSearch = new TextField();
		treeNodeSearch.setIcon(VaadinIcons.SEARCH);
		treeNodeSearch.setStyleName("small inline-icon search");
		treeNodeSearch.setPlaceholder("Search");
		configureTreeNodeSearch();
		
		Panel panel = getAndLoadOdometerPanel();
		//HorizontalLayout treeButtonLayout = new HorizontalLayout();
		VerticalLayout treePanelLayout = new VerticalLayout();
		//treePanelLayout.addComponentAsFirst(treeNodeSearch);
		//treePanelLayout.addComponent(treeButtonLayout);
		//treePanelLayout.addComponentAsFirst(treeButtonLayout);
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
		
		treePanelLayout.addComponent(nodeTree);
		treePanelLayout.setMargin(true);
		treePanelLayout.setHeight("100%");
		treePanelLayout.setStyleName("split-height");
		//treePanelLayout.setComponentAlignment(nodeTree, Alignment.BOTTOM_LEFT);
		splitScreen = new HorizontalSplitPanel();
		splitScreen.setFirstComponent(treePanelLayout);
		//splitScreen.setFirstComponent(nodeTree);
		splitScreen.setSplitPosition(20);
		splitScreen.addComponent(getOdometerDeviceLayout());
		splitScreen.setHeight("100%");
		panel.setContent(splitScreen);
	}
	
	public Panel getAndLoadOdometerPanel() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setCaption("<h1 style=color:#216C2A;font-weight:bold;>Device Odometer</h1>");
		panel.setResponsive(true);
		panel.setSizeFull();
        addComponent(panel);
       return panel;
	}
	
	private void configureTreeNodeSearch() {
		// FIXME Not able to put Tree Search since its using a Hierarchical
		// Dataprovider.
		treeNodeSearch.addValueChangeListener(changed -> {
			String valueInLower = changed.getValue().toLowerCase();
//			ListDataProvider<Node> nodeDataProvider = (ListDataProvider<Node>) nodeTree.getDataProvider();
//			nodeDataProvider.setFilter(filter -> {
//				return filter.getLabel().toLowerCase().contains(valueInLower);
//			});
		});
	}
	
	private void confirmDialog() {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?",
		        "Ok", "Cancel", new ConfirmDialog.Listener() {

		            public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		                    // Confirmed to continue
		                	odometerDeviceService.removeOdometerDevice(odometerDeviceGrid.getSelectedItems().iterator().next());
//		    				ListDataProvider<Debug> refreshDebugDataProvider = debugService.getListDataProvider();
//		    				debugGrid.setDataProvider(refreshDebugDataProvider);
		    				//Refreshing
		    				//debugGrid.getDataProvider().refreshAll();
		    				//nodeTree.getSelectionModel().deselectAll();
		    				nodeTree.getDataProvider().refreshAll();
		    				//debugStartDateField.setDateFormat(dateFormat);;
		    				//debugEndDateField.setValue(localTimeNow.toLocalDate());
		    				odometerDeviceSearch.clear();
		    				//debugGrid.getDataProvider().refreshAll();
		    				//debugGrid.deselectAll();
		    				loadGrid();
		    				odometerStartDateField.clear();
		    				odometerEndDateField.clear();
		    				
		    				//debugGrid.getSelectedItems()
		    				//Page.getCurrent().reload();
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
		VerticalLayout odometerDeviceLayout = new VerticalLayout();
		odometerDeviceLayout.setWidth("100%");
		odometerDeviceLayout.setResponsive(true);
		//debugLayout.setSizeUndefined();
		odometerDeviceGrid = new Grid<>(Devices.class);
		odometerDeviceGrid.setWidth("100%");
		odometerDeviceGrid.setResponsive(true);
		odometerDeviceGrid.setSelectionMode(SelectionMode.SINGLE);
		odometerDeviceGrid.setColumns("statusType", "description", "statistics");
		//debugGrid.setDataProvider(debugService.getListDataProvider());

		// debugGrid.setData();
		odometerDeviceSearch = new TextField();
		odometerDeviceSearch.setWidth("100%");
		odometerDeviceSearch.setIcon(VaadinIcons.SEARCH);
		odometerDeviceSearch.setStyleName("small inline-icon search");
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
			//ListDataProvider<Devices> odometerDeviceDataProvider = odometerDeviceService.getListDataProvider() ;
			odometerDeviceDataProvider.setFilter(filter -> {
				String descriptionInLower = filter.getDescription().toLowerCase();
				String typeInLower = filter.getStatusType().name().toLowerCase();
				String statistics = filter.getStatistics().toString().toLowerCase();
				Boolean condition = ((typeInLower.equals(valueInLower)) || (descriptionInLower.contains(valueInLower)));
				return ((typeInLower.contains(valueInLower)) || (descriptionInLower.contains(valueInLower)) || (statistics.contains(valueInLower)));
			});
		});

			deleteGridRow = new Button(VaadinIcons.TRASH);
			deleteGridRow.addStyleName(ValoTheme.BUTTON_FRIENDLY);
			deleteGridRow.setResponsive(true);
			deleteGridRow.addClickListener(clicked -> {
				if(odometerDeviceGrid.getSelectedItems().isEmpty()) {
					Notification.show("Select any Statistic type to delete", Notification.Type.WARNING_MESSAGE).setDelayMsec(3000);;
				}else {
					confirmDialog();
				}
			});
		
		HorizontalLayout optionsLayout = new HorizontalLayout();
		//optionsLayout.setComponentAlignment(childComponent, alignment);
		optionsLayout.setWidth("100%");
		optionsLayout.setHeight("50%");
		//optionsLayout.setSizeUndefined();
		optionsLayout.setResponsive(true);
		
		HorizontalLayout odometerSearchLayout = new HorizontalLayout();
		odometerSearchLayout.setWidth("100%");
		odometerSearchLayout.addComponent(odometerDeviceSearch);
		odometerSearchLayout.setComponentAlignment(odometerDeviceSearch, Alignment.TOP_LEFT);
		
		HorizontalLayout dateDeleteLayout = new HorizontalLayout();
		dateDeleteLayout.setSizeUndefined();
		//dateDeleteLayout.setWidth("100%");
		//dateDeleteLayout.setSizeFull();
		
		odometerStartDateField = new DateField();
		//debugStartDateField.setWidth("100%");
		odometerStartDateField.setPlaceholder("Start Date");
		odometerStartDateField.setResponsive(true);
		odometerStartDateField.setDateFormat(DATE_FORMAT);
		odometerStartDateField.setRangeEnd(localTimeNow.toLocalDate());
		//debugStartDateField.setValue(LocalDateTime.now());
		odometerStartDateField.setDescription("Start Date");
		odometerEndDateField = new DateField();
		//debugEndDateField.setWidth("100%");
		odometerEndDateField.setPlaceholder("End Date");
		odometerEndDateField.setResponsive(true);
		odometerEndDateField.setDateFormat(DATE_FORMAT);
		odometerEndDateField.setRangeEnd(localTimeNow.toLocalDate().plusDays(1));
		//debugEndDateField.setValue(LocalDateTime.now());
		odometerEndDateField.setDescription("End Date");
		optionsLayout.addComponent(odometerSearchLayout);
		optionsLayout.setComponentAlignment(odometerSearchLayout, Alignment.MIDDLE_LEFT);
		dateDeleteLayout.addComponent(odometerStartDateField);
		dateDeleteLayout.setComponentAlignment(odometerStartDateField, Alignment.MIDDLE_LEFT);
		dateDeleteLayout.addComponent(odometerEndDateField);
		dateDeleteLayout.setComponentAlignment(odometerEndDateField, Alignment.MIDDLE_RIGHT);
		dateDeleteLayout.addComponent(deleteGridRow);
		dateDeleteLayout.setComponentAlignment(deleteGridRow, Alignment.MIDDLE_RIGHT);
		optionsLayout.addComponent(dateDeleteLayout);
		optionsLayout.setComponentAlignment(dateDeleteLayout, Alignment.MIDDLE_RIGHT);
		odometerDeviceLayout.addComponent(optionsLayout);
		odometerDeviceLayout.setComponentAlignment(optionsLayout, Alignment.TOP_LEFT);
		odometerDeviceLayout.addComponent(odometerDeviceGrid);
		
		
		//end Date listner
		odometerEndDateField.addValueChangeListener(change ->{
         if(!(change.getValue() == null)) {
        	 if(!nodeTree.getSelectedItems().isEmpty()) {
        		 	if(odometerStartDateField.getValue()!=null) {
        	 		if(change.getValue().compareTo(odometerStartDateField.getValue()) >= 0 ) {
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
        	odometerStartDateField.clear();
        	odometerEndDateField.clear();
        }
	}
	});
		odometerStartDateField.addValueChangeListener(change ->{
			if(change.getValue()!=null) {
				odometerEndDateField.setRangeStart(odometerStartDateField.getValue().plusDays(1));
				odometerEndDateField.setRangeEnd(localTimeNow.toLocalDate().plusDays(1));
			}
	});
		
			nodeTree.addItemClickListener(selection ->{
//				if(nodeTree.getSelectionModel().isSelected(selection.getItem())) {
//					debugGrid.setDataProvider(debugService.getListDataProvider());
//				}else {
//					DataProvider data = new ListDataProvider(selection.getItem().getEntityList());
//					debugGrid.setDataProvider(data);
//				}
			
			treeDataService.getTreeDataForDeviceOdometer();
			List<Node> nodeList = treeDataService.getOdometerDeviceList();
			for(Node node : nodeList) {
				if(node.getLabel().equals(selection.getItem().getLabel())) {
					DataProvider data = new ListDataProvider(node.getEntityList());
					odometerDeviceGrid.setDataProvider(data);
					odometerDeviceSearch.clear();
				}
			}
			odometerStartDateField.clear();
			odometerEndDateField.clear();
				
		});
		return odometerDeviceLayout;
	}
}
