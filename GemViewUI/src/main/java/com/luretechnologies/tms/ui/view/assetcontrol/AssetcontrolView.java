package com.luretechnologies.tms.ui.view.assetcontrol;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.tms.backend.data.entity.Alert;
import com.luretechnologies.tms.backend.data.entity.AssetHistory;
import com.luretechnologies.tms.backend.data.entity.Debug;
import com.luretechnologies.tms.backend.data.entity.ExtendedNode;
import com.luretechnologies.tms.backend.data.entity.Node;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.service.AlertService;
import com.luretechnologies.tms.backend.service.AssetControlService;
import com.luretechnologies.tms.backend.service.DebugService;
import com.luretechnologies.tms.backend.service.TreeDataService;
import com.luretechnologies.tms.ui.ComponentUtil;
import com.luretechnologies.tms.ui.NotificationUtil;
import com.luretechnologies.tms.ui.components.FormFieldType;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = AssetcontrolView.VIEW_NAME)
public class AssetcontrolView extends VerticalLayout implements Serializable, View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3410929503924583215L;
	public static final String VIEW_NAME = "assetcontrolview";
	private static final String DATE_FORMAT = "MM/dd/yyyy";
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateTimeFormatter  dateFormatter1 = DateTimeFormatter.ofPattern("MM-dd-YYYY");
	private static final LocalDateTime localTimeNow = LocalDateTime.now();
	private static Grid<AssetHistory> debugGrid;
	private static Grid<Debug> deviceDebugGrid;
	private static Grid<Alert> alertGrid =new Grid<>(Alert.class);
	private static Button deleteHistoryGridRow, deleteAlertGridRow, editAlertGridRow, createAlertGridRow,saveAlertForm,cancelAlertForm,deleteDeviceDebugGridRow;
	private static Tree<TreeNode> nodeTree;
	private static TextField treeNodeSearch, historySearch,deviceDebugGridSearch;
	private static HorizontalSplitPanel splitScreen;
	private static TabSheet assetTabSheet;
	private static DateField debugStartDateField, debugEndDateField,deviceDebugStartDateField,deviceDebugEndDateField;
	private static FormLayout deviceDebugFormLayout;
	private static VerticalLayout historyLayoutFull;
	private static HorizontalLayout optionsLayoutHistoryHorizontalDesktop;
	private static VerticalLayout optionsLayoutHistoryVerticalTab;
	private static VerticalLayout optionsLayoutHistoryVerticalPhone;
	private static HorizontalLayout historySearchLayout;
	private static HorizontalLayout dateDeleteHistoryLayout;
	private static VerticalLayout dateDeleteHistoryLayoutPhone;
	
	private static VerticalLayout debugLayout;
	private static HorizontalLayout optionsLayoutDebugHorizontalDesktop;
	private static VerticalLayout optionsLayoutDebugVerticalTab;
	private static VerticalLayout optionsLayoutDebugVerticalPhone;
	private static HorizontalLayout debugSearchLayout;
	private static HorizontalLayout dateDeleteDebugLayout;
	private static VerticalLayout dateDeleteDebugLayoutPhone;
	private static final String error = "error";
	private static final String warn = "warn";
	private static final String info="info";
	
	private static List<Debug> debugList = new ArrayList<Debug>();

	@Autowired
	public AssetcontrolView() {

	}

	
	@Autowired
	public AssetControlService assetControlService;
	
	@Autowired
	public TreeDataService treeDataService;

	@Autowired
	public DebugService debugService;

	@Autowired
	public AlertService alertService;

	@PostConstruct
	private void init() throws ApiException {
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
			
			if(r.getWidth()<=600) {
				treeNodeSearch.setHeight(28, Unit.PIXELS);
				historySearch.setHeight("28px");
				deviceDebugGridSearch.setHeight("28px");
				debugStartDateField.setHeight("28px");
				debugEndDateField.setHeight("28px");
				deviceDebugStartDateField.setHeight("28px");
				deviceDebugEndDateField.setHeight("28px");
			} else if(r.getWidth()>600 && r.getWidth()<=1000){
				treeNodeSearch.setHeight(32, Unit.PIXELS);
				historySearch.setHeight("32px");
				deviceDebugGridSearch.setHeight("32px");
				debugStartDateField.setHeight("32px");
				debugEndDateField.setHeight("32px");
				deviceDebugStartDateField.setHeight("32px");
				deviceDebugEndDateField.setHeight("32px");
			}else {
				treeNodeSearch.setHeight(37, Unit.PIXELS);
				historySearch.setHeight("37px");
				deviceDebugGridSearch.setHeight("37px");
				debugStartDateField.setHeight("37px");
				debugEndDateField.setHeight("37px");
				deviceDebugStartDateField.setHeight("37px");
				deviceDebugEndDateField.setHeight("37px");
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
		treeNodeSearch.addStyleName("v-textfield-font");
		treeNodeSearch.setPlaceholder("Search");		
		configureTreeNodeSearch();

		Panel panel = getAndLoadAssetControlPanel();
		VerticalLayout verticalPanelLayout = new VerticalLayout();
		verticalPanelLayout.setHeight("100%");
		verticalPanelLayout.setStyleName("split-height");
		VerticalLayout treeSearchPanelLayout = new VerticalLayout();
		treeSearchPanelLayout.addComponentAsFirst(treeNodeSearch);
		nodeTree = new Tree<TreeNode>();
		nodeTree.setTreeData(assetControlService.getTreeData());
		nodeTree.setItemIconGenerator(item -> {
			switch (item.getType()){
			case ENTERPRISE:
				return VaadinIcons.ORIENTATION;
			case ORGANIZATION:
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
		treeSearchPanelLayout.setStyleName("split-Height-ButtonLayout");
		verticalPanelLayout.addComponent(treeSearchPanelLayout);
		splitScreen = new HorizontalSplitPanel();
		splitScreen.setFirstComponent(verticalPanelLayout);
		splitScreen.setSplitPosition(20);
		splitScreen.addComponent(getTabSheet());
		splitScreen.setHeight("100%");
		panel.setContent(splitScreen);
		
		int width = Page.getCurrent().getBrowserWindowWidth();
		int height = Page.getCurrent().getBrowserWindowHeight();
		if(width >0 && width <=699) {
			phoneMode();
			splitScreen.setSplitPosition(35);
//			treeNodeSearch.setHeight(28, Unit.PIXELS);
//			historySearch.setHeight("28px");
//			deviceDebugGridSearch.setHeight("28px");
//			debugStartDateField.setHeight("28px");
//			debugEndDateField.setHeight("28px");
//			deviceDebugStartDateField.setHeight("28px");
//			deviceDebugEndDateField.setHeight("28px");
		} else if(width>=700 && width<=1400) {
			tabMode();
			splitScreen.setSplitPosition(30);
//			treeNodeSearch.setHeight(37, Unit.PIXELS);
//			historySearch.setHeight("37px");
//			deviceDebugGridSearch.setHeight("37px");
//			debugStartDateField.setHeight("37px");
//			debugEndDateField.setHeight("37px");
//			deviceDebugStartDateField.setHeight("37px");
//			deviceDebugEndDateField.setHeight("37px");
		}
		else {
			desktopMode();
			splitScreen.setSplitPosition(20);
//			treeNodeSearch.setHeight(37, Unit.PIXELS);
//			historySearch.setHeight("37px");
//			deviceDebugGridSearch.setHeight("37px");
//			debugStartDateField.setHeight("37px");
//			debugEndDateField.setHeight("37px");
//			deviceDebugStartDateField.setHeight("37px");
//			deviceDebugEndDateField.setHeight("37px");
		}
		
		if(width<=600) {
			treeNodeSearch.setHeight(28, Unit.PIXELS);
			historySearch.setHeight("28px");
			deviceDebugGridSearch.setHeight("28px");
			debugStartDateField.setHeight("28px");
			debugEndDateField.setHeight("28px");
			deviceDebugStartDateField.setHeight("28px");
			deviceDebugEndDateField.setHeight("28px");
		} else if(width>600 && width<=1000){
			treeNodeSearch.setHeight(32, Unit.PIXELS);
			historySearch.setHeight("32px");
			deviceDebugGridSearch.setHeight("32px");
			debugStartDateField.setHeight("32px");
			debugEndDateField.setHeight("32px");
			deviceDebugStartDateField.setHeight("32px");
			deviceDebugEndDateField.setHeight("32px");
		}else {
			treeNodeSearch.setHeight(37, Unit.PIXELS);
			historySearch.setHeight("37px");
			deviceDebugGridSearch.setHeight("37px");
			debugStartDateField.setHeight("37px");
			debugEndDateField.setHeight("37px");
			deviceDebugStartDateField.setHeight("37px");
			deviceDebugEndDateField.setHeight("37px");
		}
		
//		if(height<=580) {
//			historyLayoutFull.addStyleName("asset-historyDebugLayout");
//		}
		
		assetTabSheet.addSelectedTabChangeListener(new SelectedTabChangeListener() {
			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				clearGridData();
				treeNodeSearch.clear();
				if(nodeTree.getSelectionModel().getSelectedItems().isEmpty()) {					
				}else {
					nodeTree.deselect(nodeTree.getSelectedItems().iterator().next());
				}
			}
		});
	}

	private void clearGridData() {
		switch (assetTabSheet.getTab(assetTabSheet.getSelectedTab()).getCaption().toLowerCase()) {
		case "history":
				DataProvider data = new ListDataProvider(Collections.EMPTY_LIST);
				debugGrid.setDataProvider(data);
				historySearch.clear();
				clearCalenderDates();
			break;
		case "alert":
				DataProvider data1 = new ListDataProvider(Collections.EMPTY_LIST);
				alertGrid.setDataProvider(data1);
				clearCalenderDates();
			break;
		case "debug":
			
				DataProvider data2 = new ListDataProvider(Collections.EMPTY_LIST);
				deviceDebugGrid.setDataProvider(data2);
				deviceDebugGridSearch.clear();
				clearCalenderDates();
			break;
		default:
			break;
		}
	}
	
	public Panel getAndLoadAssetControlPanel() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setCaption("Asset Control");
		panel.setResponsive(true);
		panel.setSizeFull();
		addComponent(panel);
		return panel;
	}
	private void tabMode() {
		//For History Screen
		optionsLayoutHistoryVerticalTab.addStyleName("audit-tabAlignment");
		dateDeleteHistoryLayout.addComponent(debugStartDateField);
		dateDeleteHistoryLayout.setComponentAlignment(debugStartDateField, Alignment.TOP_RIGHT);
		dateDeleteHistoryLayout.addComponent(debugEndDateField);
		dateDeleteHistoryLayout.setComponentAlignment(debugEndDateField, Alignment.TOP_RIGHT);
		dateDeleteHistoryLayout.addComponent(deleteHistoryGridRow);
		dateDeleteHistoryLayout.setComponentAlignment(deleteHistoryGridRow, Alignment.TOP_LEFT);
		optionsLayoutHistoryVerticalTab.addComponents(historySearchLayout,dateDeleteHistoryLayout);
		
		optionsLayoutHistoryHorizontalDesktop.setVisible(false);
		optionsLayoutHistoryVerticalPhone.setVisible(false);
		optionsLayoutHistoryVerticalTab.setVisible(true);
		
		//Debug Screen
		optionsLayoutDebugVerticalTab.addStyleName("audit-tabAlignment");
		debugSearchLayout.addStyleName("audit-phoneAlignment");
		dateDeleteDebugLayout.addComponent(deviceDebugStartDateField);
		dateDeleteDebugLayout.setComponentAlignment(deviceDebugStartDateField, Alignment.TOP_RIGHT);
		dateDeleteDebugLayout.addComponent(deviceDebugEndDateField);
		dateDeleteDebugLayout.setComponentAlignment(deviceDebugEndDateField, Alignment.TOP_RIGHT);
		dateDeleteDebugLayout.addComponent(deleteDeviceDebugGridRow);
		dateDeleteDebugLayout.setComponentAlignment(deleteDeviceDebugGridRow, Alignment.TOP_LEFT);
		optionsLayoutDebugVerticalTab.addComponents(debugSearchLayout,dateDeleteDebugLayout);
		
		optionsLayoutDebugHorizontalDesktop.setVisible(false);
		optionsLayoutDebugVerticalPhone.setVisible(false);
		optionsLayoutDebugVerticalTab.setVisible(true);
		
	}
	
	private void phoneMode() {
		//History Screen
		dateDeleteHistoryLayoutPhone.addComponents(debugStartDateField, debugEndDateField,deleteHistoryGridRow);
		optionsLayoutHistoryVerticalPhone.addStyleName("audit-phoneAlignment");
		historySearchLayout.addStyleName("audit-phoneAlignment");
		optionsLayoutHistoryVerticalPhone.addComponents(historySearchLayout,dateDeleteHistoryLayoutPhone);
		optionsLayoutHistoryVerticalPhone.setVisible(true);
		optionsLayoutHistoryHorizontalDesktop.setVisible(false);
		optionsLayoutHistoryVerticalTab.setVisible(false);
		
		//Debug Screen
		dateDeleteDebugLayoutPhone.addComponents(deviceDebugStartDateField, deviceDebugEndDateField,deleteDeviceDebugGridRow);
		optionsLayoutDebugVerticalPhone.addStyleName("audit-phoneAlignment");
		debugSearchLayout.addStyleName("audit-phoneAlignment");
		optionsLayoutDebugVerticalPhone.addComponents(debugSearchLayout,dateDeleteDebugLayoutPhone);
		optionsLayoutDebugVerticalPhone.setVisible(true);
		optionsLayoutDebugHorizontalDesktop.setVisible(false);
		optionsLayoutDebugVerticalTab.setVisible(false);
	}
	
	private void desktopMode() {
		//History Screen
		optionsLayoutHistoryHorizontalDesktop.addComponent(historySearchLayout);
		optionsLayoutHistoryHorizontalDesktop.setComponentAlignment(historySearchLayout, Alignment.MIDDLE_LEFT);
		dateDeleteHistoryLayout.addComponent(debugStartDateField);
		dateDeleteHistoryLayout.setComponentAlignment(debugStartDateField, Alignment.TOP_RIGHT);
		dateDeleteHistoryLayout.addComponent(debugEndDateField);
		dateDeleteHistoryLayout.setComponentAlignment(debugEndDateField, Alignment.TOP_RIGHT);
		dateDeleteHistoryLayout.addComponent(deleteHistoryGridRow);
		dateDeleteHistoryLayout.setComponentAlignment(deleteHistoryGridRow, Alignment.TOP_LEFT);
		optionsLayoutHistoryHorizontalDesktop.addComponent(dateDeleteHistoryLayout);
		optionsLayoutHistoryHorizontalDesktop.setComponentAlignment(dateDeleteHistoryLayout, Alignment.TOP_RIGHT);
		optionsLayoutHistoryHorizontalDesktop.setVisible(true);
		optionsLayoutHistoryVerticalTab.setVisible(false);
		optionsLayoutHistoryVerticalPhone.setVisible(false);
		historySearchLayout.removeStyleName("audit-phoneAlignment");
		
		//Debug Screen
		debugSearchLayout.removeStyleName("audit-phoneAlignment");
		optionsLayoutDebugHorizontalDesktop.addComponent(debugSearchLayout);
		optionsLayoutDebugHorizontalDesktop.setComponentAlignment(debugSearchLayout, Alignment.MIDDLE_LEFT);
		dateDeleteDebugLayout.addComponent(deviceDebugStartDateField);
		dateDeleteDebugLayout.setComponentAlignment(deviceDebugStartDateField, Alignment.TOP_RIGHT);
		dateDeleteDebugLayout.addComponent(deviceDebugEndDateField);
		dateDeleteDebugLayout.setComponentAlignment(deviceDebugEndDateField, Alignment.TOP_RIGHT);
		dateDeleteDebugLayout.addComponent(deleteDeviceDebugGridRow);
		dateDeleteDebugLayout.setComponentAlignment(deleteDeviceDebugGridRow, Alignment.TOP_LEFT);
		optionsLayoutDebugHorizontalDesktop.addComponent(dateDeleteDebugLayout);
		optionsLayoutDebugHorizontalDesktop.setComponentAlignment(dateDeleteDebugLayout, Alignment.TOP_RIGHT);
		optionsLayoutDebugHorizontalDesktop.setVisible(true);
		optionsLayoutDebugVerticalTab.setVisible(false);
		optionsLayoutDebugVerticalPhone.setVisible(false);
	}
	
	private void configureTreeNodeSearch() {
		// FIXME Not able to put Tree Search since its using a Hierarchical
		// Dataprovider.
		/*treeNodeSearch.addValueChangeListener(changed -> {
			String valueInLower = changed.getValue().toLowerCase();
			nodeTree.setTreeData(treeDataService.getFilteredTreeByExtendedNodeName(treeDataService.getTreeDataForDebugAndAlert(), valueInLower));
		});
		
		treeNodeSearch.addShortcutListener(new ShortcutListener("Clear",KeyCode.ESCAPE,null) {
			
			@Override
			public void handleAction(Object sender, Object target) {
				if (target == treeNodeSearch) {
					treeNodeSearch.clear();
				}
			}
		});*/
	}

	private TabSheet getTabSheet() {

		// debugLayout.setSizeUndefined();
		assetTabSheet = new TabSheet();
		assetTabSheet.setHeight(100.0f, Unit.PERCENTAGE);
		assetTabSheet.setSizeFull();
		assetTabSheet.addStyleNames(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS, ValoTheme.TABSHEET_CENTERED_TABS,
				ValoTheme.TABSHEET_ICONS_ON_TOP, ValoTheme.TABSHEET_COMPACT_TABBAR, ValoTheme.TABSHEET_PADDED_TABBAR);
		assetTabSheet.addTab(getHistory(), "History");
		assetTabSheet.addTab(getAlert(), "Alert");
		assetTabSheet.addTab(getDebug(), "Debug");
		
		return assetTabSheet;
	}
	
	private void clearCalenderDates() {
		debugStartDateField.clear();
		debugEndDateField.clear();
		deviceDebugEndDateField.clear();
		deviceDebugStartDateField.clear();
	}
	
	private void confirmDialog() {
		/*ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?",
		        "Ok", "Cancel", new ConfirmDialog.Listener() {

		            public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		                    // Confirmed to continue
		                	debugService.removeDebug(debugGrid.getSelectedItems().iterator().next());
		    				nodeTree.getDataProvider().refreshAll();
		    				historySearch.clear();
		    				loadGrid();
		    				clearCalenderDates();
		                } else {
		                    // User did not confirm
		                    
		                }
		            }
		        });*/
	}
	
	private void confirmDialogDebug() {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?",
		        "Ok", "Cancel", new ConfirmDialog.Listener() {

		            public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		                    // Confirmed to continue
		                	debugService.removeDebug(deviceDebugGrid.getSelectedItems().iterator().next());
		        			//ListDataProvider<Debug> refreshDebugDataProvider = debugService.getListDataProvider();
		        			//deviceDebugGrid.setDataProvider(refreshDebugDataProvider);
		        			// Refreshing
		        			deviceDebugGrid.getDataProvider().refreshAll();
		        			deviceDebugGridSearch.clear();
		        			loadDeviceDebugGrid();
		        			clearCalenderDates();
		        			//nodeTree.deselect(item);
		        			//deviceDebugGridSearch.clear();
		                } else {
		                    // User did not confirm
		                    
		                }
		            }
		        });
	}
	
	
	private void loadAlertGrid() {
		treeDataService.getTreeDataForDebugAndAlert();
		List<ExtendedNode> nodeList = treeDataService.getDebugAndAlertNodeList();
		Set<TreeNode> nodeSet = nodeTree.getSelectionModel().getSelectedItems();
		TreeNode nodeSelected = nodeSet.iterator().next();
		for(ExtendedNode node : nodeList) {
			if(node.getLabel().equals(nodeSelected.getLabel())) {
				DataProvider data = new ListDataProvider(node.getExtendedList());
				alertGrid.setDataProvider(data);
			}
		}
		
	}
	
	private void loadGrid() {
		treeDataService.getTreeDataForDebugAndAlert();
		List<ExtendedNode> nodeList = treeDataService.getDebugAndAlertNodeList();
		Set<TreeNode> nodeSet = nodeTree.getSelectionModel().getSelectedItems();
		TreeNode nodeSelected = nodeSet.iterator().next();
		for(ExtendedNode node : nodeList) {
			if(node.getLabel().equals(nodeSelected.getLabel())) {
				DataProvider data = new ListDataProvider(node.getEntityList());
				debugGrid.setDataProvider(data);
			}
		}
		
	}
	
	private void loadDeviceDebugGrid() {
		treeDataService.getTreeDataForDebugAndAlert();
		List<ExtendedNode> nodeList = treeDataService.getDebugAndAlertNodeList();
		Set<TreeNode> nodeSet = nodeTree.getSelectionModel().getSelectedItems();
		TreeNode nodeSelected = nodeSet.iterator().next();
		for(ExtendedNode node : nodeList) {
			if(node.getLabel().equals(nodeSelected.getLabel())) {
				DataProvider data = new ListDataProvider(node.getEntityList());
				deviceDebugGrid.setDataProvider(data);
			}
		}
		
	}

	/**
	 * @param debugLayout
	 */
	private VerticalLayout getHistory() {
		historyLayoutFull = new VerticalLayout();
		historyLayoutFull.addStyleName(ValoTheme.LAYOUT_CARD);
		historyLayoutFull.setWidth("100%");
		historyLayoutFull.setHeight("100%");
		historyLayoutFull.setResponsive(true);
		historyLayoutFull.addStyleNames("audit-DeviceVerticalAlignment", "asset-historyDebugLayout");
		debugGrid = new Grid<>(AssetHistory.class);
		debugGrid.setWidth("100%");
		debugGrid.setHeight("97%");
		//debugGrid.setHeightByRows(20);
		debugGrid.setResponsive(true);
		debugGrid.addStyleName("grid-AuditOdometerAlignment");
		debugGrid.setSelectionMode(SelectionMode.SINGLE);
		debugGrid.setColumns("type", "description");
		//debugGrid.setDataProvider(debugService.getListDataProvider());
		debugGrid.getColumn("type").setCaption("Debug Type").setStyleGenerator(style -> {
			switch (style.getType()) {
			case error:
				return "error";
			case warn:
				return "warn";
			case info:
				return "info";

			default:
				return "";
			}
		});

		// debugGrid.setData();
		historySearch = new TextField();
		historySearch.setWidth("100%");
		historySearch.setHeight("37px");
		historySearch.setIcon(VaadinIcons.SEARCH);
		historySearch.setStyleName("small inline-icon search");
		historySearch.setPlaceholder("Search");
		historySearch.setResponsive(true);
		historySearch.addShortcutListener(new ShortcutListener("Clear", KeyCode.ESCAPE, null) {

			@Override
			public void handleAction(Object sender, Object target) {
				if (target == historySearch) {
					historySearch.clear();
				}

			}
		});
		historySearch.addValueChangeListener(valueChange -> {
			/*String valueInLower = valueChange.getValue().toLowerCase();
			ListDataProvider<Debug> debugDataProvider = (ListDataProvider<Debug>) debugGrid.getDataProvider();
			debugDataProvider.setFilter(filter -> {
				String descriptionInLower = filter.getDescription().toLowerCase();
				String typeInLower = filter.getType().name().toLowerCase();
				return typeInLower.equals(valueInLower) || descriptionInLower.contains(valueInLower);
			});*/
		});

		deleteHistoryGridRow = new Button(VaadinIcons.TRASH);
		deleteHistoryGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		deleteHistoryGridRow.setResponsive(true);
		deleteHistoryGridRow.addClickListener(clicked -> {
//			debugService.removeDebug(debugGrid.getSelectedItems().iterator().next());
//			ListDataProvider<Debug> refreshDebugDataProvider = debugService.getListDataProvider();
//			debugGrid.setDataProvider(refreshDebugDataProvider);
//			// Refreshing
//			debugGrid.getDataProvider().refreshAll();
//			nodeTree.getSelectionModel().deselectAll();
//			nodeTree.getDataProvider().refreshAll();
//			debugStartDateField.setPlaceholder("Start Date");
//			debugStartDateField.setRangeEnd(localTimeNow.toLocalDate());
//			debugEndDateField.setRangeEnd(localTimeNow.toLocalDate());
//			debugEndDateField.setPlaceholder("End Date");
//			debugSearch.clear();
			
			if(debugGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.ASSET_HISTORY_DELETE, Notification.Type.ERROR_MESSAGE);
			}else {
				confirmDialog();
			}

		});

		 optionsLayoutHistoryHorizontalDesktop = new HorizontalLayout();
		// optionsLayout.setComponentAlignment(childComponent, alignment);
		optionsLayoutHistoryHorizontalDesktop.setWidth("100%");
		//optionsLayoutHistoryHorizontalDesktop.svinayetHeight("50%");
		// optionsLayout.setSizeUndefined();
		optionsLayoutHistoryHorizontalDesktop.setResponsive(true);
		optionsLayoutHistoryHorizontalDesktop.addStyleName("audit-DeviceSearch");

		historySearchLayout = new HorizontalLayout();
		historySearchLayout.setWidth("100%");
		historySearchLayout.addComponent(historySearch);
		historySearchLayout.setComponentAlignment(historySearch, Alignment.TOP_LEFT);

//		dateDeleteLayout = new HorizontalLayout();
//		dateDeleteLayout.setSizeUndefined();
//		// dateDeleteLayout.setWidth("100%");
//		// dateDeleteLayout.setSizeFull();

		debugStartDateField = new DateField();
		debugStartDateField.setWidth("100%");
		debugStartDateField.setHeight("37px");
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
		debugEndDateField.setHeight("37px");
		debugEndDateField.setPlaceholder("End Date");
		debugEndDateField.setResponsive(true);
		debugEndDateField.setDateFormat(DATE_FORMAT);
		//debugEndDateField.setRangeEnd(localTimeNow.toLocalDate().plusDays(1));
		debugEndDateField.setDateOutOfRangeMessage("Same Date cannot be selected");
		debugEndDateField.setDescription("End Date");
		debugEndDateField.addStyleName("v-textfield-font");
		
		//Vertical Initialization
		optionsLayoutHistoryVerticalTab = new VerticalLayout();
		optionsLayoutHistoryVerticalTab.setVisible(false);
		optionsLayoutHistoryVerticalTab.addStyleName("audit-DeviceSearch");
		
		//Vertical Phone Mode 
		optionsLayoutHistoryVerticalPhone= new VerticalLayout();
		optionsLayoutHistoryVerticalPhone.setVisible(false);
		optionsLayoutHistoryVerticalPhone.addStyleName("audit-DeviceSearch");
		dateDeleteHistoryLayoutPhone = new VerticalLayout();
		dateDeleteHistoryLayoutPhone.setVisible(true);
		dateDeleteHistoryLayoutPhone.addStyleName("audit-phoneAlignment");
		
		dateDeleteHistoryLayout = new HorizontalLayout();
		dateDeleteHistoryLayout.setVisible(true);
		dateDeleteHistoryLayout.setWidth("100%");
		
		optionsLayoutHistoryHorizontalDesktop.addComponent(historySearchLayout);
		optionsLayoutHistoryHorizontalDesktop.setComponentAlignment(historySearchLayout, Alignment.MIDDLE_LEFT);
		dateDeleteHistoryLayout.addComponent(debugStartDateField);
		dateDeleteHistoryLayout.setComponentAlignment(debugStartDateField, Alignment.MIDDLE_LEFT);
		dateDeleteHistoryLayout.addComponent(debugEndDateField);
		dateDeleteHistoryLayout.setComponentAlignment(debugEndDateField, Alignment.MIDDLE_RIGHT);
		dateDeleteHistoryLayout.addComponent(deleteHistoryGridRow);
		dateDeleteHistoryLayout.setComponentAlignment(deleteHistoryGridRow, Alignment.MIDDLE_RIGHT);
		optionsLayoutHistoryHorizontalDesktop.addComponent(dateDeleteHistoryLayout);
		optionsLayoutHistoryHorizontalDesktop.setComponentAlignment(dateDeleteHistoryLayout, Alignment.MIDDLE_RIGHT);
		
		
		historyLayoutFull.addComponent(optionsLayoutHistoryHorizontalDesktop);
		historyLayoutFull.setComponentAlignment(optionsLayoutHistoryHorizontalDesktop, Alignment.TOP_LEFT);
		historyLayoutFull.addComponent(optionsLayoutHistoryVerticalTab);
		historyLayoutFull.setComponentAlignment(optionsLayoutHistoryVerticalTab, Alignment.TOP_LEFT);
		historyLayoutFull.addComponent(optionsLayoutHistoryVerticalPhone);
		historyLayoutFull.setComponentAlignment(optionsLayoutHistoryVerticalPhone, Alignment.TOP_LEFT);
		historyLayoutFull.addComponent(debugGrid);
		historyLayoutFull.setExpandRatio(debugGrid, 2);

		//end Date listner
		debugEndDateField.addValueChangeListener(change ->{
	         if(!(change.getValue() == null)) {
	        	 if(!nodeTree.getSelectedItems().isEmpty()) {
	        		 	if(debugStartDateField.getValue()!=null) {
	        	 		if(change.getValue().compareTo(debugStartDateField.getValue()) >= 0 ) {
	        	 			/*String date = debugEndDateField.getValue().format(dateFormatter1);
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
					});*/
				} 
	        		 	}else {
	        		 		Notification.show(NotificationUtil.ASSET_HISTORY_STARTDATE, Notification.Type.ERROR_MESSAGE);
	        		 		debugEndDateField.clear();
	        		 	}
	        }else {
	        	Notification.show(NotificationUtil.ASSET_HISTORY_SELECTNODE, Notification.Type.ERROR_MESSAGE);
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

		nodeTree.addSelectionListener(selection -> {
				switch (assetTabSheet.getTab(assetTabSheet.getSelectedTab()).getCaption().toLowerCase()) {
				case "history":
					List<AssetHistory> assetHistoryList;
					try {
						assetHistoryList = assetControlService.getHistoryGridData(nodeTree.getSelectedItems().iterator().next().getEntityId());
						DataProvider data = new ListDataProvider(assetHistoryList);
						debugGrid.setDataProvider(data);
						historySearch.clear();
					} catch (ApiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//debugGrid.setDataProvider(debugService.getListDataProvider());
					break;
				case "alert":
					/*if(node.getLabel().equals(selection.getItem().getLabel())) {
						DataProvider data = new ListDataProvider(node.getExtendedList());
						alertGrid.setDataProvider(data);
					}*/
					//alertGrid.setDataProvider(alertService.getListDataProvider());
					break;
				case "debug":
					/*if(node.getLabel().equals(selection.getItem().getLabel())) {
						DataProvider data = new ListDataProvider(node.getEntityList());
						deviceDebugGrid.setDataProvider(data);
						deviceDebugGridSearch.clear();
					}*/
					//deviceDebugGrid.setDataProvider(debugService.getListDataProvider());
					break;
				default:
					break;
				}

			
			clearCalenderDates();
			historySearch.clear();
			deviceDebugGridSearch.clear();

		});
		return historyLayoutFull;
	}

	private VerticalLayout getAlert() {
		Button[] buttons= {createAlertGridRow,editAlertGridRow,deleteAlertGridRow,saveAlertForm,cancelAlertForm};
		AlertTab alertTab  = new AlertTab(alertGrid, alertService, nodeTree,Page.getCurrent().getUI(), treeDataService,buttons);
		return alertTab.getAlert();
	}

	private VerticalLayout getDebug() {
		debugLayout = new VerticalLayout();
		HorizontalLayout debugLabel = new HorizontalLayout();
		HorizontalLayout debugMonitoringLabel = new HorizontalLayout();
		
		debugLayout.addStyleName("heartbeat-verticalLayout");
		Label entityInformation = new Label("Entity Information");
		entityInformation.addStyleNames(ValoTheme.LABEL_BOLD, ValoTheme.LABEL_H3);
		entityInformation.addStyleName("label-style");
		debugLabel.setWidth("100%");
		debugLabel.addComponent(entityInformation);
		
		
		debugLayout.addComponent(entityInformation);
		deviceDebugFormLayout = new FormLayout();
		deviceDebugFormLayout.addStyleNames("heartbeat-verticalLayout", "asset-debugSFormLayout","system-LabelAlignment");
		deviceDebugFormLayout.addComponent(ComponentUtil.getFormFieldWithLabel("Entity Type", FormFieldType.TEXTBOX));
		deviceDebugFormLayout.addComponent(ComponentUtil.getFormFieldWithLabel("Name", FormFieldType.TEXTBOX));
		deviceDebugFormLayout.addComponent(ComponentUtil.getFormFieldWithLabel("Description", FormFieldType.TEXTBOX));
		deviceDebugFormLayout.addComponent(ComponentUtil.getFormFieldWithLabel("Entity Active", FormFieldType.HORIZONTALLAYOUT));
		deviceDebugFormLayout.addComponent(ComponentUtil.getFormFieldWithLabel("Serial Num.", FormFieldType.TEXTBOX));
		deviceDebugFormLayout.addComponent(ComponentUtil.getFormFieldWithLabel("Receive Debug", FormFieldType.HORIZONTALLAYOUT));
		ComboBox<String> combox = (ComboBox<String>)ComponentUtil.getFormFieldWithLabel("", FormFieldType.COMBOBOX);
		combox.setCaptionAsHtml(true);
		combox.setCaption("Debug Duration"); 
		//combobox.addStyleName();
		combox.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size");
		combox.setDataProvider(new ListDataProvider<>(Arrays.asList("24 Hours", "1 Hour", " 30 Minutes")));

		deviceDebugFormLayout.addComponent(combox);
		deviceDebugFormLayout.setComponentAlignment(combox, Alignment.TOP_RIGHT); // FIXME : alignment issue
		debugLayout.addComponent(deviceDebugFormLayout);
		// Grid
		Label debugMonitoring = new Label("Device Debug Monitoring");
		debugMonitoring.addStyleNames(ValoTheme.LABEL_BOLD, ValoTheme.LABEL_H3);
		debugMonitoring.addStyleName("label-style");
		debugMonitoringLabel.addStyleName("assetDebug-label");
		debugMonitoringLabel.addComponent(debugMonitoring);
		debugLayout.addComponent(debugMonitoringLabel);
		getDeviceDebugGridSearchLayout();
		debugLayout.addComponent(getDeviceDebugGrid());
		
		deviceDebugGrid.addSelectionListener(selection->{
			if(selection.getFirstSelectedItem().isPresent()) {
				Debug selectedDebug = selection.getFirstSelectedItem().get();
				((TextField)deviceDebugFormLayout.getComponent(0)).setValue(selectedDebug.getType().name());
				((TextField)deviceDebugFormLayout.getComponent(1)).setValue(selectedDebug.getName());
				((TextField)deviceDebugFormLayout.getComponent(2)).setValue(selectedDebug.getDescription());
				HorizontalLayout HL= (HorizontalLayout)deviceDebugFormLayout.getComponent(3);
				CheckBox checkbox = (CheckBox) HL.getComponent(1);
				checkbox.setValue(selectedDebug.isActive());
				//((CheckBox)deviceDebugFormLayout.getComponent(3)).setValue(selectedDebug.isActive());
				((TextField)deviceDebugFormLayout.getComponent(4)).setValue(selectedDebug.getId().toString());
				HorizontalLayout HL1= (HorizontalLayout)deviceDebugFormLayout.getComponent(5);
				CheckBox checkbox1 = (CheckBox) HL1.getComponent(1);
				checkbox1.setValue(selectedDebug.isDebug());
				//((CheckBox)deviceDebugFormLayout.getComponent(5)).setValue(selectedDebug.isDebug());
				//FIXME: provide combo option based on dateOfDebug
				LocalDateTime debugTime = LocalDateTime.ofInstant(selectedDebug.getDateOfDebug().toInstant(), ZoneId.systemDefault());
				if(debugTime.isBefore(LocalDateTime.now()))
					((ComboBox<String>)deviceDebugFormLayout.getComponent(6)).setValue("1 Hour");	
				else
					((ComboBox<String>)deviceDebugFormLayout.getComponent(6)).setValue("30 Minutes");	
				
				
				
			}else {
				for(int index=0; index<7;index++) {
					if(deviceDebugFormLayout.getComponent(index) instanceof TextField) {
						TextField textField = (TextField) deviceDebugFormLayout.getComponent(index);
						textField.clear();
					} else if(deviceDebugFormLayout.getComponent(index) instanceof HorizontalLayout) {
						HorizontalLayout HL = (HorizontalLayout) deviceDebugFormLayout.getComponent(index);
						CheckBox checkbox = (CheckBox) HL.getComponent(1);
						checkbox.clear();
					}else if(deviceDebugFormLayout.getComponent(index) instanceof ComboBox) {
						ComboBox comboBox = (ComboBox) deviceDebugFormLayout.getComponent(index);
						comboBox.clear();
					}
				}
			}
		});
		return debugLayout;
	}

	private void getDeviceDebugGridSearchLayout() {
		
		optionsLayoutDebugHorizontalDesktop = new HorizontalLayout();
		optionsLayoutDebugHorizontalDesktop.setWidth("100%");
		optionsLayoutDebugHorizontalDesktop.addStyleName("asset-debugSearch");
		//optionsLayoutDebugHorizontalDesktop.setHeight("50%");
		optionsLayoutDebugHorizontalDesktop.setResponsive(true);
		
		deviceDebugGridSearch = new TextField();
		deviceDebugGridSearch.setWidth("100%");
		deviceDebugGridSearch.setHeight("37px");
		deviceDebugGridSearch.setIcon(VaadinIcons.SEARCH);
		deviceDebugGridSearch.setStyleName("small inline-icon search");
		deviceDebugGridSearch.setPlaceholder("Search");
		deviceDebugGridSearch.setResponsive(true);
		deviceDebugGridSearch.addShortcutListener(new ShortcutListener("Clear", KeyCode.ESCAPE, null) {

			@Override
			public void handleAction(Object sender, Object target) {
				if (target == deviceDebugGridSearch) {
					deviceDebugGridSearch.clear();
				}

			}
		});
		deviceDebugGridSearch.addValueChangeListener(valueChange -> {
			String valueInLower = valueChange.getValue().toLowerCase();
			ListDataProvider<Debug> debugDataProvider = (ListDataProvider<Debug>) deviceDebugGrid.getDataProvider();
			debugDataProvider.setFilter(filter -> {
				String descriptionInLower = filter.getDescription().toLowerCase();
				String typeInLower = filter.getType().name().toLowerCase();
				return typeInLower.equals(valueInLower) || descriptionInLower.contains(valueInLower);
			});
		});
		
		debugSearchLayout = new HorizontalLayout();
		debugSearchLayout.setWidth("100%");
		//debugSearchLayout.addStyleName("heartbeat-historySearchLayout");
		debugSearchLayout.addComponent(deviceDebugGridSearch);
		debugSearchLayout.setComponentAlignment(deviceDebugGridSearch, Alignment.TOP_LEFT);
		
		deviceDebugStartDateField = new DateField();
		deviceDebugStartDateField.setHeight("37px");
		deviceDebugEndDateField = new DateField();
		deviceDebugEndDateField.setHeight("37px");

		deleteDeviceDebugGridRow = new Button(VaadinIcons.TRASH);
		deleteDeviceDebugGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		deleteDeviceDebugGridRow.setResponsive(true);
		deleteDeviceDebugGridRow.addClickListener(clicked -> {
			if(deviceDebugGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.ASSET_DEBUG_DELETE, Notification.Type.ERROR_MESSAGE);
			}else {
				confirmDialogDebug();
			}
			

		});
		
		deviceDebugStartDateField.setWidth("100%");
		deviceDebugStartDateField.setResponsive(true);
		deviceDebugStartDateField.setDateFormat(DATE_FORMAT);
		deviceDebugStartDateField.setRangeEnd(LocalDateTime.now().toLocalDate());
		deviceDebugStartDateField.setPlaceholder("Start Date");
		deviceDebugStartDateField.addStyleName("v-textfield-font");
		
		deviceDebugEndDateField.setResponsive(true);
		deviceDebugEndDateField.setWidth("100%");
		deviceDebugEndDateField.setDateFormat(DATE_FORMAT);
		///deviceDebugEndDateField.setRangeEnd(LocalDateTime.now().toLocalDate());
		deviceDebugEndDateField.setPlaceholder("End Date");
		deviceDebugEndDateField.setDateOutOfRangeMessage("Same Date cannot be selected");
		deviceDebugEndDateField.addStyleName("v-textfield-font");

		//Vertical Initialization
		optionsLayoutDebugVerticalTab = new VerticalLayout();
		optionsLayoutDebugVerticalTab.addStyleNames("asset-debugSearch");
		optionsLayoutDebugVerticalTab.setVisible(false);
		
		//Vertical Phone Mode 
		optionsLayoutDebugVerticalPhone= new VerticalLayout();
		optionsLayoutDebugVerticalPhone.addStyleNames("asset-debugSearch");
		optionsLayoutDebugVerticalPhone.setVisible(false);
		dateDeleteDebugLayoutPhone = new VerticalLayout();
		dateDeleteDebugLayoutPhone.setVisible(true);
		dateDeleteDebugLayoutPhone.addStyleName("audit-phoneAlignment");
		
		dateDeleteDebugLayout = new HorizontalLayout();
		dateDeleteDebugLayout.setVisible(true);
		dateDeleteDebugLayout.setWidth("100%");
		
		optionsLayoutDebugHorizontalDesktop.addComponent(debugSearchLayout);
		optionsLayoutDebugHorizontalDesktop.setComponentAlignment(debugSearchLayout, Alignment.MIDDLE_LEFT);
		dateDeleteDebugLayout.addComponent(deviceDebugStartDateField);
		dateDeleteDebugLayout.setComponentAlignment(deviceDebugStartDateField, Alignment.MIDDLE_LEFT);
		dateDeleteDebugLayout.addComponent(deviceDebugEndDateField);
		dateDeleteDebugLayout.setComponentAlignment(deviceDebugEndDateField, Alignment.MIDDLE_RIGHT);
		dateDeleteDebugLayout.addComponent(deleteDeviceDebugGridRow);
		dateDeleteDebugLayout.setComponentAlignment(deleteDeviceDebugGridRow, Alignment.MIDDLE_RIGHT);
		optionsLayoutDebugHorizontalDesktop.addComponent(dateDeleteDebugLayout);
		optionsLayoutDebugHorizontalDesktop.setComponentAlignment(dateDeleteDebugLayout, Alignment.MIDDLE_RIGHT);
		
		debugLayout.addComponent(optionsLayoutDebugHorizontalDesktop);
		debugLayout.setComponentAlignment(optionsLayoutDebugHorizontalDesktop, Alignment.TOP_LEFT);
		debugLayout.addComponent(optionsLayoutDebugVerticalTab);
		debugLayout.setComponentAlignment(optionsLayoutDebugVerticalTab, Alignment.TOP_LEFT);
		debugLayout.addComponent(optionsLayoutDebugVerticalPhone);
		debugLayout.setComponentAlignment(optionsLayoutDebugVerticalPhone, Alignment.TOP_LEFT);

//		// end Date listner
//		deviceDebugEndDateField.addValueChangeListener(change -> {
//			if (change.getValue().compareTo(deviceDebugStartDateField.getValue()) > 0) {
//				ListDataProvider<Debug> deviceDebugDataProvider = (ListDataProvider<Debug>) deviceDebugGrid
//						.getDataProvider();
//				deviceDebugDataProvider.setFilter(filter -> {
//					Date debugDate = filter.getDateOfDebug();
//					try {
//						return debugDate.after(
//								dateFormatter.parse(deviceDebugStartDateField.getValue().minusDays(1).toString()))
//								&& debugDate.before(dateFormatter.parse(change.getValue().plusDays(1).toString()));
//					} catch (ParseException e) {
//						System.out.println(e.getMessage() + filter.toString());
//
//						return false;
//					}
//				});
//			}
//		});

		deviceDebugEndDateField.addShortcutListener(new ShortcutListener("Clear", KeyCode.ESCAPE, null) {

			@Override
			public void handleAction(Object sender, Object target) {
				if (target == deviceDebugGridSearch) {
					deviceDebugGridSearch.clear();
					deviceDebugStartDateField.clear();
					deviceDebugEndDateField.clear();
				}
			}
		});
		
		deviceDebugEndDateField.addValueChangeListener(change ->{
	         if(!(change.getValue() == null)) {
	        	 if(!nodeTree.getSelectedItems().isEmpty()) {
	        		 	if(deviceDebugStartDateField.getValue()!=null) {
	        	 		if(change.getValue().compareTo(deviceDebugStartDateField.getValue()) >= 0 ) {
	        	 			String date = deviceDebugEndDateField.getValue().format(dateFormatter1);
	        	 			deviceDebugEndDateField.setDescription(date);
	        	 				ListDataProvider<Debug> debugDataProvider = (ListDataProvider<Debug>) deviceDebugGrid.getDataProvider();
	        	 				debugDataProvider.setFilter(filter -> {
	        	 				Date debugDate = filter.getDateOfDebug();
	        	 				try {
	        	 					return debugDate.after(dateFormatter.parse(deviceDebugStartDateField.getValue().minusDays(1).toString())) && debugDate.before(dateFormatter.parse(change.getValue().plusDays(1).toString()));
	        	 				} catch (ParseException e) {
	        	 					System.out.println(e.getMessage() + filter.toString());
							
	        	 					return false;
	        	 				}
					});
				} 
	        		 	}else {
	        		 		Notification.show(NotificationUtil.ASSET_DEBUG_STARTDATE, Notification.Type.ERROR_MESSAGE);
	        		 		deviceDebugEndDateField.clear();
	        		 	}
	        }else {
	        	Notification.show(NotificationUtil.ASSET_DEBUG_SELECTNODE, Notification.Type.ERROR_MESSAGE);
	        	clearCalenderDates();
	        }
		}
	});
		
		deviceDebugStartDateField.addValueChangeListener(change ->{
			if(change.getValue()!=null) {
				deviceDebugEndDateField.clear();
				deviceDebugEndDateField.setRangeStart(change.getValue().plusDays(1));
				//debugEndDateField.setRangeEnd(localTimeNow.toLocalDate().plusDays(1));
				String date = deviceDebugStartDateField.getValue().format(dateFormatter1);
				deviceDebugStartDateField.setDescription(date);
			}
	});

	}

	private Grid<Debug> getDeviceDebugGrid() {
		deviceDebugGrid = new Grid<>(Debug.class);
		deviceDebugGrid.setWidth("100%");
		deviceDebugGrid.setHeightByRows(6);
		deviceDebugGrid.setResponsive(true);
		deviceDebugGrid.setSelectionMode(SelectionMode.SINGLE);
		deviceDebugGrid.setColumns("type", "description");
		//deviceDebugGrid.setDataProvider(debugService.getListDataProvider());
		deviceDebugGrid.getColumn("type").setCaption("Debug Type").setStyleGenerator(style -> {
			switch (style.getType()) {
			case ERROR:
				return "error";
			case WARN:
				return "warn";

			default:
				return "";
			}
		});

		return deviceDebugGrid;
	}

}
