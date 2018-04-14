package com.luretechnologies.tms.ui.view.audit;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

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
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = AuditView.VIEW_NAME)
public class AuditView extends VerticalLayout implements Serializable, View {

	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 
	 */
	private static final long serialVersionUID = -7983511214106963682L;
	public static final String VIEW_NAME = "audit";
	private static final LocalDateTime localTimeNow = LocalDateTime.now();
	private static Grid<Debug> debugGrid;
	private static Tree<Node> nodeTree;
	private static Button deleteGridRow;
	private static TextField treeNodeSearch, debugSearch;
	private static HorizontalSplitPanel splitScreen;
	private static DateField debugStartDateField, debugEndDateField;
	private OffsetDateTime odt = OffsetDateTime.now ();
	private ZoneOffset zoneOffset = odt.getOffset ();

	@Autowired
	public AuditView() {

	}

	@Autowired
	public TreeDataService treeDataService;

	@Autowired
	public DebugService debugService;

	@PostConstruct
	private void init() {
		setSpacing(false);
		setMargin(false);
		setResponsive(true);
		setHeight("100%");
		treeNodeSearch = new TextField();
		treeNodeSearch.setIcon(VaadinIcons.SEARCH);
		treeNodeSearch.setStyleName("small inline-icon search");
		treeNodeSearch.setPlaceholder("Search");
		configureTreeNodeSearch();
		
		Panel panel = getAndLoadAuditPanel();
		//HorizontalLayout treeButtonLayout = new HorizontalLayout();
		VerticalLayout treePanelLayout = new VerticalLayout();
		//treePanelLayout.addComponentAsFirst(treeNodeSearch);
		//treePanelLayout.addComponent(treeButtonLayout);
		//treePanelLayout.addComponentAsFirst(treeButtonLayout);
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
		//treePanelLayout.setComponentAlignment(nodeTree, Alignment.BOTTOM_LEFT);
		splitScreen = new HorizontalSplitPanel();
		splitScreen.setFirstComponent(treePanelLayout);
		//splitScreen.setFirstComponent(nodeTree);
		splitScreen.setSplitPosition(20);
		splitScreen.addComponent(getDebugLayout());
		splitScreen.setHeight("100%");
		panel.setContent(splitScreen);
	}
	
	public Panel getAndLoadAuditPanel() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setCaption("<h1 style=color:#216C2A;font-weight:bold;>Audit</h1>");
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
		                	debugService.removeDebug(debugGrid.getSelectedItems().iterator().next());
//		    				ListDataProvider<Debug> refreshDebugDataProvider = debugService.getListDataProvider();
//		    				debugGrid.setDataProvider(refreshDebugDataProvider);
		    				//Refreshing
		    				//debugGrid.getDataProvider().refreshAll();
		    				//nodeTree.getSelectionModel().deselectAll();
		    				nodeTree.getDataProvider().refreshAll();
		    				debugStartDateField.setValue(localTimeNow.toLocalDate());
		    				debugEndDateField.setValue(localTimeNow.toLocalDate());
		    				debugSearch.clear();
		    				//Page.getCurrent().reload();
		                } else {
		                    // User did not confirm
		                    
		                }
		            }
		        });
	}
	
	private VerticalLayout getDebugLayout() {
		VerticalLayout debugLayout = new VerticalLayout();
		debugLayout.setWidth("100%");
		debugLayout.setResponsive(true);
		//debugLayout.setSizeUndefined();
		debugGrid = new Grid<>(Debug.class);
		debugGrid.setWidth("100%");
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

			deleteGridRow = new Button(VaadinIcons.TRASH);
			deleteGridRow.addStyleName(ValoTheme.BUTTON_FRIENDLY);
			deleteGridRow.setResponsive(true);
			deleteGridRow.addClickListener(clicked -> {
				if(debugGrid.getSelectedItems().isEmpty()) {
					Notification.show("Select any Debug type to delete", Notification.Type.WARNING_MESSAGE).setDelayMsec(3000);;
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
		debugStartDateField.setDateOutOfRangeMessage("Future Dates Cannot be selected" );
		debugStartDateField.setRangeEnd(localTimeNow.toLocalDate());
		//debugStartDateField.setValue(LocalDateTime.now());
		debugStartDateField.setDescription("Start Date");
		debugEndDateField = new DateField();
		debugEndDateField.setResponsive(true);
		debugEndDateField.setDateFormat(DATE_FORMAT);
		debugEndDateField.setDateOutOfRangeMessage("Future Dates Cannot be selected" );
		debugEndDateField.setRangeEnd(localTimeNow.toLocalDate());
		//debugEndDateField.setValue(LocalDateTime.now());
		debugEndDateField.setDescription("End Date");
		optionsLayout.addComponent(debugSearchLayout);
		optionsLayout.setComponentAlignment(debugSearchLayout, Alignment.MIDDLE_LEFT);
		dateDeleteLayout.addComponent(debugStartDateField);
		dateDeleteLayout.setComponentAlignment(debugStartDateField, Alignment.MIDDLE_LEFT);
		dateDeleteLayout.addComponent(debugEndDateField);
		dateDeleteLayout.setComponentAlignment(debugEndDateField, Alignment.MIDDLE_RIGHT);
		dateDeleteLayout.addComponent(deleteGridRow);
		dateDeleteLayout.setComponentAlignment(deleteGridRow, Alignment.MIDDLE_RIGHT);
		optionsLayout.addComponent(dateDeleteLayout);
		optionsLayout.setComponentAlignment(dateDeleteLayout, Alignment.MIDDLE_RIGHT);
		debugLayout.addComponent(optionsLayout);
		debugLayout.setComponentAlignment(optionsLayout, Alignment.TOP_LEFT);
		debugLayout.addComponent(debugGrid);
		
		
		//end Date listner
		debugEndDateField.addValueChangeListener(change ->{
//			if(change.getValue().toEpochSecond(zoneOffset) > LocalDateTime.now().toEpochSecond(zoneOffset)) {
//				Notification.show("Future Dates Cannot be selected", Notification.Type.ERROR_MESSAGE);
//			}else if(change.getValue().toEpochSecond(zoneOffset) < debugStartDateField.getValue().toEpochSecond(zoneOffset)){
//				Notification.show("End Data cannot be less than Start Date", Notification.Type.ERROR_MESSAGE);
//			}else {
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
//		}
	});
		debugStartDateField.addValueChangeListener(change ->{
//			if(change.getValue().toEpochSecond(zoneOffset) > LocalDateTime.now().toEpochSecond(zoneOffset)) {
//				Notification.show("Future Dates Cannot be selected", Notification.Type.ERROR_MESSAGE);
//			}else if(change.getValue().toEpochSecond(zoneOffset) > debugEndDateField.getValue().toEpochSecond(zoneOffset)){
//				Notification.show("Start Data cannot be more than End Date", Notification.Type.ERROR_MESSAGE);
//			}
			
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
}
