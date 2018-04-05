package com.luretechnologies.tms.ui.view.audit;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.luretechnologies.tms.backend.data.entity.Debug;
import com.luretechnologies.tms.backend.data.entity.Node;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.service.DebugService;
import com.luretechnologies.tms.backend.service.TreeDataService;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = AuditView.VIEW_NAME)
public class AuditView extends VerticalLayout implements Serializable, View {

	private static final String DATE_FORMAT = "dd/MM/yyyy";
	/**
	 * 
	 */
	private static final long serialVersionUID = -7983511214106963682L;
	public static final String VIEW_NAME = "audit";

	private static Grid<Debug> debugGrid;
	private static Tree<Node> nodeTree;
	private static Button deleteGridRow;
	private static TextField treeNodeSearch, debugSearch;
	private static HorizontalSplitPanel splitScreen;
	private static DateTimeField debugStartDateField, debugEndDateField;

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
		treeNodeSearch = new TextField();
		configureTreeNodeSearch();

		HorizontalLayout treeButtonLayout = new HorizontalLayout();
		VerticalLayout treePanelLayout = new VerticalLayout();
		treePanelLayout.addComponentAsFirst(treeNodeSearch);
		treePanelLayout.addComponent(treeButtonLayout);
		nodeTree = new Tree<Node>();
		nodeTree.setTreeData(treeDataService.getTreeData());
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
		splitScreen.setSplitPosition(30);
		splitScreen.addComponent(getDebugLayout());
		addComponent(splitScreen);
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

	private VerticalLayout getDebugLayout() {
		VerticalLayout debugLayout = new VerticalLayout();
		debugLayout.setResponsive(true);
		debugLayout.setSizeFull();
		debugGrid = new Grid<>(Debug.class);
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
		debugSearch.setWidth(20,Unit.PERCENTAGE);
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
			deleteGridRow.addClickListener(clicked -> {
				debugService.removeDebug(debugGrid.getSelectedItems().iterator().next());
				ListDataProvider<Debug> refreshDebugDataProvider = debugService.getListDataProvider();
				//FIXME delete not working
				debugGrid.setDataProvider(refreshDebugDataProvider);

			});
		
		HorizontalLayout debugSearchLayout = new HorizontalLayout();
		
		debugStartDateField = new DateTimeField();
		debugStartDateField.setDateFormat(DATE_FORMAT);
		debugStartDateField.setValue(LocalDateTime.now());
		debugStartDateField.setDescription("Start Date");
		debugEndDateField = new DateTimeField();
		debugEndDateField.setDateFormat(DATE_FORMAT);
		debugEndDateField.setValue(LocalDateTime.now());
		debugEndDateField.setDescription("End Date");
		debugSearchLayout.addComponent(debugSearch);
		debugSearchLayout.addComponent(debugStartDateField);
		debugSearchLayout.addComponent(debugEndDateField);
		debugSearchLayout.addComponent(deleteGridRow);
		debugLayout.addComponent(debugSearchLayout);
		debugLayout.addComponent(debugGrid);
		return debugLayout;
	}
}
