package com.luretechnologies.tms.ui.view.assetcontrol;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.tms.backend.data.entity.Alert;
import com.luretechnologies.tms.backend.data.entity.Debug;
import com.luretechnologies.tms.backend.data.entity.ExtendedNode;
import com.luretechnologies.tms.backend.data.entity.Node;
import com.luretechnologies.tms.backend.service.AlertService;
import com.luretechnologies.tms.backend.service.DebugService;
import com.luretechnologies.tms.backend.service.TreeDataService;
import com.luretechnologies.tms.ui.ComponentUtil;
import com.luretechnologies.tms.ui.components.FormFieldType;
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
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = AssetControlView.VIEW_NAME)
public class AssetControlView extends VerticalLayout implements Serializable, View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3410929503924583215L;
	public static final String VIEW_NAME = "assetcontrolview";
	private static final String DATE_FORMAT = "MM/dd/yyyy";
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateTimeFormatter  dateFormatter1 = DateTimeFormatter.ofPattern("MM-dd-YYYY");
	private static final LocalDateTime localTimeNow = LocalDateTime.now();
	private static Grid<Debug> debugGrid, deviceDebugGrid;
	private static Grid<Alert> alertGrid;
	private static Button deleteHistoryGridRow, deleteAlertGridRow, editAlertGridRow, createAlertGridRow,saveAlertForm,resetAlertForm;
	private static Tree<ExtendedNode> nodeTree;
	private static TextField treeNodeSearch, debugSearch;
	private static HorizontalSplitPanel splitScreen;
	private static TabSheet assetTabSheet;
	private static DateField debugStartDateField, debugEndDateField;
	private static VerticalLayout debugLayoutFull;
	private static HorizontalLayout optionsLayoutHorizontalDesktop;
	private static VerticalLayout optionsLayoutVerticalTab;
	private static VerticalLayout optionsLayoutVerticalPhone;
	private static HorizontalLayout debugSearchLayout;
	private static HorizontalLayout dateDeleteLayout;
	private static VerticalLayout dateDeleteLayoutPhone;

	@Autowired
	public AssetControlView() {

	}

	@Autowired
	public TreeDataService treeDataService;

	@Autowired
	public DebugService debugService;

	@Autowired
	public AlertService alertService;

	@PostConstruct
	private void init() {
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
		treeNodeSearch.setVisible(false);
		configureTreeNodeSearch();

		Panel panel = getAndLoadAuditPanel();
		HorizontalLayout treeButtonLayout = new HorizontalLayout();
		VerticalLayout treePanelLayout = new VerticalLayout();
		//treePanelLayout.addComponentAsFirst(treeNodeSearch);
		//treePanelLayout.addComponent(treeButtonLayout);
		nodeTree = new Tree<ExtendedNode>();
		nodeTree.setTreeData(treeDataService.getTreeDataForDebugAndAlert());
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
		//treePanelLayout.setComponentAlignment(nodeTree, Alignment.TOP_LEFT);
		splitScreen = new HorizontalSplitPanel();
		splitScreen.setFirstComponent(treePanelLayout);
		splitScreen.setSplitPosition(20);
		splitScreen.addComponent(getTabSheet());
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

	public Panel getAndLoadAuditPanel() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		//panel.setCaption("<h3 style=color:#216C2A;font-weight:bold;>Asset Control</h3>");
		panel.setCaption("Asset Control");
		panel.setResponsive(true);
		panel.setSizeFull();
		addComponent(panel);
		return panel;
	}
	private void tabMode() {
		optionsLayoutVerticalTab.addStyleName("heartbeat-verticalLayout");
		dateDeleteLayout.addComponent(debugStartDateField);
		dateDeleteLayout.setComponentAlignment(debugStartDateField, Alignment.TOP_RIGHT);
		dateDeleteLayout.addComponent(debugEndDateField);
		dateDeleteLayout.setComponentAlignment(debugEndDateField, Alignment.TOP_RIGHT);
		dateDeleteLayout.addComponent(deleteHistoryGridRow);
		dateDeleteLayout.setComponentAlignment(deleteHistoryGridRow, Alignment.TOP_LEFT);
		optionsLayoutVerticalTab.addComponents(debugSearchLayout,dateDeleteLayout);
		
		optionsLayoutHorizontalDesktop.setVisible(false);
		optionsLayoutVerticalPhone.setVisible(false);
		optionsLayoutVerticalTab.setVisible(true);
	}
	
	private void phoneMode() {
		dateDeleteLayoutPhone.addComponents(debugStartDateField, debugEndDateField,deleteHistoryGridRow);
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
		dateDeleteLayout.addComponent(deleteHistoryGridRow);
		dateDeleteLayout.setComponentAlignment(deleteHistoryGridRow, Alignment.TOP_LEFT);
		optionsLayoutHorizontalDesktop.addComponent(dateDeleteLayout);
		//optionsLayoutHorizontalDesktop.addComponent(dateDeleteLayout);
		optionsLayoutHorizontalDesktop.setComponentAlignment(dateDeleteLayout, Alignment.TOP_RIGHT);
		optionsLayoutHorizontalDesktop.setVisible(true);
		optionsLayoutVerticalTab.setVisible(false);
		optionsLayoutVerticalPhone.setVisible(false);
	}
	
	private void configureTreeNodeSearch() {
		// FIXME Not able to put Tree Search since its using a Hierarchical
		// Dataprovider.
		treeNodeSearch.addValueChangeListener(changed -> {
			String valueInLower = changed.getValue().toLowerCase();
			// ListDataProvider<Node> nodeDataProvider = (ListDataProvider<Node>)
			// nodeTree.getDataProvider();
			// nodeDataProvider.setFilter(filter -> {
			// return filter.getLabel().toLowerCase().contains(valueInLower);
			// });
		});
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
		List<ExtendedNode> nodeList = treeDataService.getDebugAndAlertNodeList();
		Set<ExtendedNode> nodeSet = nodeTree.getSelectionModel().getSelectedItems();
		ExtendedNode nodeSelected = nodeSet.iterator().next();
		for(ExtendedNode node : nodeList) {
			if(node.getLabel().equals(nodeSelected.getLabel())) {
				DataProvider data = new ListDataProvider(node.getEntityList());
				debugGrid.setDataProvider(data);
			}
		}
		
	}

	/**
	 * @param debugLayout
	 */
	private VerticalLayout getHistory() {
		debugLayoutFull = new VerticalLayout();
		debugLayoutFull.setWidth("100%");
		debugLayoutFull.setResponsive(true);
		debugGrid = new Grid<>(Debug.class);
		debugGrid.setWidth("100%");
		debugGrid.setHeightByRows(20);
		debugGrid.setResponsive(true);
		debugGrid.setSelectionMode(SelectionMode.SINGLE);
		debugGrid.setColumns("type", "description");
		//debugGrid.setDataProvider(debugService.getListDataProvider());
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

		// debugGrid.setData();
		debugSearch = new TextField();
		debugSearch.setWidth("100%");
		debugSearch.setIcon(VaadinIcons.SEARCH);
		debugSearch.setStyleName("small inline-icon search");
		debugSearch.setPlaceholder("Search");
		debugSearch.setResponsive(true);
		debugSearch.addShortcutListener(new ShortcutListener("Clear", KeyCode.ESCAPE, null) {

			@Override
			public void handleAction(Object sender, Object target) {
				if (target == debugSearch) {
					debugSearch.clear();
				}

			}
		});
		debugSearch.addValueChangeListener(valueChange -> {
			String valueInLower = valueChange.getValue().toLowerCase();
			ListDataProvider<Debug> debugDataProvider = (ListDataProvider<Debug>) debugGrid.getDataProvider();
			debugDataProvider.setFilter(filter -> {
				String descriptionInLower = filter.getDescription().toLowerCase();
				String typeInLower = filter.getType().name().toLowerCase();
				return typeInLower.equals(valueInLower) || descriptionInLower.contains(valueInLower);
			});
		});

		deleteHistoryGridRow = new Button(VaadinIcons.TRASH);
		deleteHistoryGridRow.addStyleName(ValoTheme.BUTTON_FRIENDLY);
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
				Notification.show("Select any Debug type to delete", Notification.Type.WARNING_MESSAGE).setDelayMsec(3000);;
			}else {
				confirmDialog();
			}

		});

		 optionsLayoutHorizontalDesktop = new HorizontalLayout();
		// optionsLayout.setComponentAlignment(childComponent, alignment);
		optionsLayoutHorizontalDesktop.setWidth("100%");
		optionsLayoutHorizontalDesktop.setHeight("50%");
		// optionsLayout.setSizeUndefined();
		optionsLayoutHorizontalDesktop.setResponsive(true);

		debugSearchLayout = new HorizontalLayout();
		debugSearchLayout.setWidth("100%");
		debugSearchLayout.addComponent(debugSearch);
		debugSearchLayout.setComponentAlignment(debugSearch, Alignment.TOP_LEFT);

//		dateDeleteLayout = new HorizontalLayout();
//		dateDeleteLayout.setSizeUndefined();
//		// dateDeleteLayout.setWidth("100%");
//		// dateDeleteLayout.setSizeFull();

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
		dateDeleteLayout.setComponentAlignment(debugStartDateField, Alignment.MIDDLE_LEFT);
		dateDeleteLayout.addComponent(debugEndDateField);
		dateDeleteLayout.setComponentAlignment(debugEndDateField, Alignment.MIDDLE_RIGHT);
		dateDeleteLayout.addComponent(deleteHistoryGridRow);
		dateDeleteLayout.setComponentAlignment(deleteHistoryGridRow, Alignment.MIDDLE_RIGHT);
		optionsLayoutHorizontalDesktop.addComponent(dateDeleteLayout);
		optionsLayoutHorizontalDesktop.setComponentAlignment(dateDeleteLayout, Alignment.MIDDLE_RIGHT);
		
		
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

		nodeTree.addItemClickListener(selection -> {
			List<ExtendedNode> nodeList = treeDataService.getDebugAndAlertNodeList();
			if (nodeTree.getSelectionModel().isSelected(selection.getItem())) {
				switch (assetTabSheet.getTab(assetTabSheet.getSelectedTab()).getCaption().toLowerCase()) {
				case "history":
					debugGrid.setDataProvider(debugService.getListDataProvider());
					break;
				case "alert":
					alertGrid.setDataProvider(alertService.getListDataProvider());
					break;
				case "debug":
					deviceDebugGrid.setDataProvider(debugService.getListDataProvider());
					break;
				default:
					break;
				}

			} else {
				switch (assetTabSheet.getTab(assetTabSheet.getSelectedTab()).getCaption().toLowerCase()) {
				case "history":
					DataProvider dataHistory = new ListDataProvider(selection.getItem().getEntityList());
					debugGrid.setDataProvider(dataHistory);
					break;
				case "alert":
					DataProvider dataAlert = new ListDataProvider(selection.getItem().getExtendedList());
					alertGrid.setDataProvider(dataAlert);
					break;
				case "debug":
					DataProvider dataDevice = new ListDataProvider(selection.getItem().getEntityList());
					deviceDebugGrid.setDataProvider(dataDevice);
					break;
				default:
					break;
				}
			}

		});
		return debugLayoutFull;
	}

	private VerticalLayout getAlert() {
		Button[] buttons= {createAlertGridRow,editAlertGridRow,deleteAlertGridRow,saveAlertForm,resetAlertForm};
		AlertTab alertTab  = new AlertTab(alertGrid, alertService, nodeTree, buttons);
		return alertTab.getAlert();
	}

	private VerticalLayout getDebug() {
		VerticalLayout debugLayout = new VerticalLayout();
		Label entityInformation = new Label("Entity Information");
		entityInformation.addStyleNames(ValoTheme.LABEL_BOLD, ValoTheme.LABEL_H3);
		debugLayout.addComponent(entityInformation);
		// Form
		FormLayout deviceDebugFormLayout = new FormLayout();
		deviceDebugFormLayout.addComponent(ComponentUtil.getFormFieldWithLabel("Entity Type", FormFieldType.TEXTBOX));
		deviceDebugFormLayout.addComponent(ComponentUtil.getFormFieldWithLabel("Name", FormFieldType.TEXTBOX));
		deviceDebugFormLayout.addComponent(ComponentUtil.getFormFieldWithLabel("Description", FormFieldType.TEXTBOX));
		deviceDebugFormLayout.addComponent(ComponentUtil.getFormFieldWithLabel("Active", FormFieldType.CHECKBOX));
		deviceDebugFormLayout.addComponent(ComponentUtil.getFormFieldWithLabel("Serial Num.", FormFieldType.TEXTBOX));
		deviceDebugFormLayout.addComponent(ComponentUtil.getFormFieldWithLabel("Debug", FormFieldType.CHECKBOX));
		ComboBox<String> combox = (ComboBox<String>)ComponentUtil.getFormFieldWithLabel("", FormFieldType.COMBOBOX);
		combox.setCaptionAsHtml(true);
		combox.setCaption("Debug<br/>Duration"); 
		combox.setStyleName(ValoTheme.LABEL_LIGHT);
		combox.setDataProvider(new ListDataProvider<>(Arrays.asList("24 Hours", "1 Hour", " 30 Minutes")));

		deviceDebugFormLayout.addComponent(combox);
		deviceDebugFormLayout.setComponentAlignment(combox, Alignment.TOP_RIGHT); // FIXME : alignment issue
		debugLayout.addComponent(deviceDebugFormLayout);
		// Grid
		Label debugMonitoring = new Label("Device Debug Monitoring");
		debugMonitoring.addStyleNames(ValoTheme.LABEL_BOLD, ValoTheme.LABEL_H3);
		debugLayout.addComponent(debugMonitoring);
		debugLayout.addComponent(getDeviceDebugGridSearchLayout());
		debugLayout.addComponent(getDeviceDebugGrid());
		
		deviceDebugGrid.addSelectionListener(selection->{
			if(selection.getFirstSelectedItem().isPresent()) {
				Debug selectedDebug = selection.getFirstSelectedItem().get();
				((TextField)deviceDebugFormLayout.getComponent(0)).setValue(selectedDebug.getType().name());
				((TextField)deviceDebugFormLayout.getComponent(1)).setValue(selectedDebug.getName());
				((TextField)deviceDebugFormLayout.getComponent(2)).setValue(selectedDebug.getDescription());
				((CheckBox)deviceDebugFormLayout.getComponent(3)).setValue(selectedDebug.isActive());
				((TextField)deviceDebugFormLayout.getComponent(4)).setValue(selectedDebug.getId().toString());
				((CheckBox)deviceDebugFormLayout.getComponent(5)).setValue(selectedDebug.isDebug());
				//FIXME: provide combo option based on dateOfDebug
				LocalDateTime debugTime = LocalDateTime.ofInstant(selectedDebug.getDateOfDebug().toInstant(), ZoneId.systemDefault());
				if(debugTime.isBefore(LocalDateTime.now()))
					((ComboBox<String>)deviceDebugFormLayout.getComponent(6)).setValue("1 Hour");	
				else
					((ComboBox<String>)deviceDebugFormLayout.getComponent(6)).setValue("30 Minutes");	
				
				
				
			}
		});
		return debugLayout;
	}

	private HorizontalLayout getDeviceDebugGridSearchLayout() {
		TextField deviceDebugGridSearch = new TextField();
		deviceDebugGridSearch.setWidth("100%");
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
		final DateField deviceDebugStartDateField = new DateField();
		final DateField deviceDebugEndDateField = new DateField();

		Button deleteDeviceDebugGridRow = new Button(VaadinIcons.TRASH);
		deleteDeviceDebugGridRow.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		deleteDeviceDebugGridRow.setResponsive(true);
		deleteDeviceDebugGridRow.addClickListener(clicked -> {
			debugService.removeDebug(deviceDebugGrid.getSelectedItems().iterator().next());
			ListDataProvider<Debug> refreshDebugDataProvider = debugService.getListDataProvider();
			deviceDebugGrid.setDataProvider(refreshDebugDataProvider);
			// Refreshing
			deviceDebugGrid.getDataProvider().refreshAll();
			deviceDebugStartDateField.setPlaceholder("Start Date");
			deviceDebugStartDateField.setRangeEnd(localTimeNow.toLocalDate());
			deviceDebugEndDateField.setRangeEnd(localTimeNow.toLocalDate());
			deviceDebugEndDateField.setPlaceholder("End Date");
			deviceDebugGridSearch.clear();

		});

		deviceDebugStartDateField.setResponsive(true);
		deviceDebugStartDateField.setDateFormat(DATE_FORMAT);
		deviceDebugStartDateField.setRangeEnd(LocalDateTime.now().toLocalDate());
		deviceDebugStartDateField.setPlaceholder("Start Date");
		deviceDebugEndDateField.setResponsive(true);
		deviceDebugEndDateField.setDateFormat(DATE_FORMAT);
		deviceDebugEndDateField.setRangeEnd(LocalDateTime.now().toLocalDate());
		deviceDebugEndDateField.setPlaceholder("End Date");

		// end Date listner
		deviceDebugEndDateField.addValueChangeListener(change -> {
			if (change.getValue().compareTo(deviceDebugStartDateField.getValue()) > 0) {
				ListDataProvider<Debug> deviceDebugDataProvider = (ListDataProvider<Debug>) deviceDebugGrid
						.getDataProvider();
				deviceDebugDataProvider.setFilter(filter -> {
					Date debugDate = filter.getDateOfDebug();
					try {
						return debugDate.after(
								dateFormatter.parse(deviceDebugStartDateField.getValue().minusDays(1).toString()))
								&& debugDate.before(dateFormatter.parse(change.getValue().plusDays(1).toString()));
					} catch (ParseException e) {
						System.out.println(e.getMessage() + filter.toString());

						return false;
					}
				});
			}
		});

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
		return new HorizontalLayout(deviceDebugGridSearch, deviceDebugStartDateField, deviceDebugEndDateField,
				deleteDeviceDebugGridRow);
	}

	private Grid<Debug> getDeviceDebugGrid() {
		deviceDebugGrid = new Grid<>(Debug.class);
		deviceDebugGrid.setWidth("100%");
		deviceDebugGrid.setResponsive(true);
		deviceDebugGrid.setSelectionMode(SelectionMode.SINGLE);
		deviceDebugGrid.setColumns("type", "description");
		deviceDebugGrid.setDataProvider(debugService.getListDataProvider());
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
