package com.luretechnologies.tms.ui.view.assetcontrol;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.luretechnologies.tms.backend.data.entity.Alert;
import com.luretechnologies.tms.backend.data.entity.Debug;
import com.luretechnologies.tms.backend.data.entity.Node;
import com.luretechnologies.tms.backend.service.AlertService;
import com.luretechnologies.tms.backend.service.DebugService;
import com.luretechnologies.tms.backend.service.TreeDataService;
import com.luretechnologies.tms.ui.components.FormFieldType;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = AssetcontrolView.VIEW_NAME)
public class AssetcontrolView extends VerticalLayout implements Serializable, View{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3410929503924583215L;
	public static final String VIEW_NAME = "assetcontrol";
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private static final LocalDateTime localTimeNow = LocalDateTime.now();
	private static Grid<Debug> debugGrid;
	private static Grid<Alert> alertGrid;
	private static Button deleteHistoryGridRow, deleteAlertGridRow, editAlertGridRow, createAlertGridRow;
	private static Tree<Node> nodeTree;
	private static TextField treeNodeSearch, debugSearch;
	private static HorizontalSplitPanel splitScreen;
	private static TabSheet assetTabSheet;
	private static DateField debugStartDateField, debugEndDateField;
	
	@Autowired
	public AssetcontrolView() {
		
	}

	@Autowired
	public TreeDataService treeDataService;

	@Autowired
	public DebugService debugService;
	
	@Autowired
	public AlertService alertService;
	
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
		treeNodeSearch.setVisible(false);
		configureTreeNodeSearch();
		
		Panel panel = getAndLoadAuditPanel();
		HorizontalLayout treeButtonLayout = new HorizontalLayout();
		VerticalLayout treePanelLayout = new VerticalLayout();
		treePanelLayout.addComponentAsFirst(treeNodeSearch);
		treePanelLayout.addComponent(treeButtonLayout);
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
		treePanelLayout.setComponentAlignment(nodeTree, Alignment.BOTTOM_LEFT);
		splitScreen = new HorizontalSplitPanel();
		splitScreen.setFirstComponent(treePanelLayout);
		splitScreen.setSplitPosition(35);
		splitScreen.addComponent(getTabSheet());
		splitScreen.setHeight("100%");
		panel.setContent(splitScreen);
	}
	
	public Panel getAndLoadAuditPanel() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setCaption("<h1 style=color:#216C2A;font-weight:bold;>Asset Control</h1>");
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

	private TabSheet getTabSheet() {
		
		//debugLayout.setSizeUndefined();
		assetTabSheet = new TabSheet();
		assetTabSheet.setHeight(100.0f, Unit.PERCENTAGE);
		assetTabSheet.setSizeFull();
		assetTabSheet.addStyleNames(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS,ValoTheme.TABSHEET_CENTERED_TABS,ValoTheme.TABSHEET_ICONS_ON_TOP,ValoTheme.TABSHEET_COMPACT_TABBAR,ValoTheme.TABSHEET_PADDED_TABBAR);
		assetTabSheet.addTab(getHistory(), "History");
		assetTabSheet.addTab(getAlert(), "Alert");
		assetTabSheet.addTab(getDebug(), "Debug");
		
		return assetTabSheet;
	}

	/**
	 * @param debugLayout
	 */
	private VerticalLayout getHistory() {
		VerticalLayout debugLayout = new VerticalLayout();
		debugLayout.setWidth("100%");
		debugLayout.setResponsive(true);
		debugGrid = new Grid<>(Debug.class);
		debugGrid.setWidth("100%");
		debugGrid.setResponsive(true);
		debugGrid.setSelectionMode(SelectionMode.SINGLE);
		debugGrid.setColumns("type", "description");
		debugGrid.setDataProvider(debugService.getListDataProvider());
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
				return typeInLower.equals(valueInLower) || descriptionInLower.contains(valueInLower);
			});
		});

			deleteHistoryGridRow = new Button(VaadinIcons.TRASH);
			deleteHistoryGridRow.addStyleName(ValoTheme.BUTTON_FRIENDLY);
			deleteHistoryGridRow.setResponsive(true);
			deleteHistoryGridRow.addClickListener(clicked -> {
				debugService.removeDebug(debugGrid.getSelectedItems().iterator().next());
				ListDataProvider<Debug> refreshDebugDataProvider = debugService.getListDataProvider();
				debugGrid.setDataProvider(refreshDebugDataProvider);
				//Refreshing
				debugGrid.getDataProvider().refreshAll();
				nodeTree.getSelectionModel().deselectAll();
				nodeTree.getDataProvider().refreshAll();
				debugStartDateField.setPlaceholder("Start Date");
				debugStartDateField.setRangeEnd(localTimeNow.toLocalDate());
				debugEndDateField.setRangeEnd(localTimeNow.toLocalDate());
				debugEndDateField.setPlaceholder("End Date");
				debugSearch.clear();

			});
		
		HorizontalLayout optionsLayout = new HorizontalLayout();
		//optionsLayout.setComponentAlignment(childComponent, alignment);
		optionsLayout.setWidth("100%");
		optionsLayout.setHeight("50%");
		//optionsLayout.setSizeUndefined();
		optionsLayout.setResponsive(true);
		
		HorizontalLayout debugSearchLayout = new HorizontalLayout();
		debugSearchLayout.setWidth("100%");
		debugSearchLayout.addComponent(debugSearch);
		debugSearchLayout.setComponentAlignment(debugSearch, Alignment.TOP_LEFT);
		
		HorizontalLayout dateDeleteLayout = new HorizontalLayout();
		dateDeleteLayout.setSizeUndefined();
		//dateDeleteLayout.setWidth("100%");
		//dateDeleteLayout.setSizeFull();
		
		debugStartDateField = new DateField();
		debugStartDateField.setResponsive(true);
		debugStartDateField.setDateFormat(DATE_FORMAT);
		debugStartDateField.setRangeEnd(LocalDateTime.now().toLocalDate());
		debugStartDateField.setPlaceholder("Start Date");
		debugEndDateField = new DateField();
		debugEndDateField.setResponsive(true);
		debugEndDateField.setDateFormat(DATE_FORMAT);
		debugEndDateField.setRangeEnd(LocalDateTime.now().toLocalDate());
		debugEndDateField.setPlaceholder("End Date");
		optionsLayout.addComponent(debugSearchLayout);
		optionsLayout.setComponentAlignment(debugSearchLayout, Alignment.MIDDLE_LEFT);
		dateDeleteLayout.addComponent(debugStartDateField);
		dateDeleteLayout.setComponentAlignment(debugStartDateField, Alignment.MIDDLE_LEFT);
		dateDeleteLayout.addComponent(debugEndDateField);
		dateDeleteLayout.setComponentAlignment(debugEndDateField, Alignment.MIDDLE_RIGHT);
		dateDeleteLayout.addComponent(deleteHistoryGridRow);
		dateDeleteLayout.setComponentAlignment(deleteHistoryGridRow, Alignment.MIDDLE_RIGHT);
		optionsLayout.addComponent(dateDeleteLayout);
		optionsLayout.setComponentAlignment(dateDeleteLayout, Alignment.MIDDLE_RIGHT);
		debugLayout.addComponent(optionsLayout);
		debugLayout.setComponentAlignment(optionsLayout, Alignment.TOP_LEFT);
		debugLayout.addComponent(debugGrid);
		
		
		//end Date listner
		debugEndDateField.addValueChangeListener(change ->{
			if(change.getValue().compareTo(debugStartDateField.getValue()) > 0) {
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
		});
		
		nodeTree.addItemClickListener(selection ->{
				if(nodeTree.getSelectionModel().isSelected(selection.getItem())) {
					debugGrid.setDataProvider(debugService.getListDataProvider());
				}else {
					DataProvider data = new ListDataProvider(selection.getItem().getEntityList());
					debugGrid.setDataProvider(data);
				}
				
		});
		return debugLayout;
	}
	
	private VerticalLayout getAlert() {
		VerticalLayout alertLayout = new VerticalLayout();
		Label alertCommands = new Label("Alert Commands");
		alertCommands.addStyleNames(ValoTheme.LABEL_BOLD,ValoTheme.LABEL_H3);
		alertLayout.addComponent(alertCommands);
		alertLayout.addComponent(getFormFieldWithLabel("Alert Type", FormFieldType.TEXTBOX));
		alertLayout.addComponent(getFormFieldWithLabel("Name", FormFieldType.TEXTBOX));
		alertLayout.addComponent(getFormFieldWithLabel("Description", FormFieldType.TEXTBOX));
		alertLayout.addComponent(getFormFieldWithLabel("Active", FormFieldType.CHECKBOX));
		alertLayout.addComponent(getFormFieldWithLabel("Email to:", FormFieldType.TEXTBOX));
		//Add,Delete,Edit Button Layout
		alertLayout.addComponent(getAlertGridButtonLayout());
		alertLayout.setComponentAlignment(alertLayout.getComponent(6), Alignment.TOP_RIGHT);
		//Alert Grid
		alertLayout.addComponent(getAlertGrid());
		return alertLayout;
	}
	private VerticalLayout getDebug() {
		VerticalLayout debugLayout = new VerticalLayout();
		return debugLayout;
	}
	
	
	private HorizontalLayout getAlertGridButtonLayout() {
		HorizontalLayout alertGridButtonLayout = new HorizontalLayout();
		//FIXME: make the button green colored icon
		createAlertGridRow = new Button(VaadinIcons.FOLDER_ADD,click ->{
			//TODO: add the click event 
		});
		createAlertGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
		editAlertGridRow = new Button(VaadinIcons.PENCIL,click ->{
			//TODO: add the click event 
			if(alertGrid.getSelectedItems().size() > 0) {
				//FIXME: add edit code
			}
		});
		editAlertGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
		deleteAlertGridRow = new Button(VaadinIcons.TRASH,click ->{
			//TODO: add the click event 
			if(alertGrid.getSelectedItems().size() > 0) {
				//FIXME: put confirmation Dialog
				alertService.removeAlert(alertGrid.getSelectedItems().iterator().next());
				ListDataProvider<Alert> refreshAlertDataProvider = alertService.getListDataProvider();
				alertGrid.setDataProvider(refreshAlertDataProvider);
				//Refreshing
				alertGrid.getDataProvider().refreshAll();
			}
		});
		deleteAlertGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
		alertGridButtonLayout.addComponent(createAlertGridRow);
		alertGridButtonLayout.addComponent(editAlertGridRow);
		alertGridButtonLayout.addComponent(deleteAlertGridRow);
		
		return alertGridButtonLayout;
	}
	private HorizontalLayout getFormFieldWithLabel(String labelName, FormFieldType type) {
		HorizontalLayout fieldLayout = new HorizontalLayout();
		Label label = new Label(labelName);
		label.setResponsive(true);
		fieldLayout.addComponent(label);
		switch (type) {
		case TEXTBOX:
			TextField textField = new TextField();
			textField.setSizeFull();
			textField.addStyleNames(ValoTheme.TEXTFIELD_ALIGN_CENTER);
			textField.setResponsive(true);
			fieldLayout.addComponent(textField);
			break;
		case CHECKBOX:
			CheckBox checkBox = new CheckBox();
			checkBox.setSizeFull();
			fieldLayout.addComponent(checkBox);
			break;
		default:
			break;
		}
		return fieldLayout;
	}
	
	private Grid<Alert> getAlertGrid(){
		alertGrid = new Grid<>(Alert.class);
		alertGrid.setWidth("100%");
		alertGrid.setResponsive(true);
		alertGrid.setSelectionMode(SelectionMode.SINGLE);
		alertGrid.setColumns("type", "description","active","email");
		alertGrid.getColumn("type").setCaption("Alert Type");
		alertGrid.setDataProvider(alertService.getListDataProvider());
		
		return alertGrid;
	}
}
