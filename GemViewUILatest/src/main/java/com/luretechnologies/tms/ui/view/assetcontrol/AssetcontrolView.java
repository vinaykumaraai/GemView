package com.luretechnologies.tms.ui.view.assetcontrol;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.DebugItem;
import com.luretechnologies.client.restlib.service.model.HeartbeatAudit;
import com.luretechnologies.tms.backend.data.entity.Alert;
import com.luretechnologies.tms.backend.data.entity.AssetHistory;
import com.luretechnologies.tms.backend.data.entity.DebugItems;
import com.luretechnologies.tms.backend.data.entity.Permission;
import com.luretechnologies.tms.backend.data.entity.TerminalClient;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.service.AssetControlService;
import com.luretechnologies.tms.backend.service.PersonalizationService;
import com.luretechnologies.tms.backend.service.RolesService;
import com.luretechnologies.tms.backend.service.TreeDataNodeService;
import com.luretechnologies.tms.backend.service.UserService;
import com.luretechnologies.tms.ui.MainView;
import com.luretechnologies.tms.ui.components.ComponentUtil;
import com.luretechnologies.tms.ui.components.EntityOperations;
import com.luretechnologies.tms.ui.components.FormFieldType;
import com.luretechnologies.tms.ui.components.MainViewIconsLoad;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.luretechnologies.tms.ui.navigation.NavigationManager;
import com.luretechnologies.tms.ui.view.ContextMenuWindow;
import com.luretechnologies.tms.ui.view.Header;
import com.vaadin.addon.onoffswitch.OnOffSwitch;
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
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TreeGrid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

/**
 * 
 * @author Vinay
 *
 */

@SpringView(name = AssetcontrolView.VIEW_NAME)
public class AssetcontrolView extends VerticalLayout implements Serializable, View {
	
	private static final long serialVersionUID = 3410929503924583215L;
	public static final String VIEW_NAME = "assetcontrolview";
	private static final String DATE_FORMAT = "MM/dd/yyyy";
	private static final DateTimeFormatter  dateFormatter1 = DateTimeFormatter.ofPattern("yyMMdd");
	private static final DateTimeFormatter  dateFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final LocalDateTime localTimeNow = LocalDateTime.now();
	private static Grid<AssetHistory> debugGrid;
	private static Grid<DebugItems> deviceDebugGrid;
	private static Grid<Alert> alertGrid =new Grid<>(Alert.class);
	private static Button deleteHistoryGridRow,saveAlertForm,cancelAlertForm,deleteDeviceDebugGridRow, saveDeviceDebug,clearSearch, clearHistorySeach, clearDebugSearch;
	private static TreeGrid<TreeNode> nodeTree;
	private static TextField treeNodeSearch, historySearch,deviceDebugGridSearch;
	private static HorizontalSplitPanel splitScreen;
	private static TabSheet assetTabSheet;
	private static DateField debugStartDateField, debugEndDateField,deviceDebugStartDateField,deviceDebugEndDateField ,deviceDebugDuration;
	private static FormLayout deviceDebugFormLayout;
	private static VerticalLayout historyLayoutFull;
	private static HorizontalLayout optionsLayoutHistoryHorizontalDesktop;
	private static VerticalLayout optionsLayoutHistoryVerticalTab;
	private static VerticalLayout optionsLayoutHistoryVerticalPhone;
	private static HorizontalLayout historySearchLayout;
	private static HorizontalLayout dateDeleteHistoryLayout;
	private static VerticalLayout dateDeleteHistoryLayoutPhone;
	private static HorizontalLayout debugDeleteSaveLayout;
	
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
	private static Permission assetControlPermission;
	private static Permission entityPermission;
	private static CssLayout assetControlHistorySearchLayout;
	private static CssLayout assetControlDebugSearchLayout;
	private static Button createEntity, editEntity, deleteEntity, copyEntity, pasteEntity;
	private Header header;
	private ContextMenuWindow paramContextWindow ;
	private boolean addEntity, updateEntity, accessEntity, removeEntity, add, update, delete, access;
	private OnOffSwitch  s;
	
	@Autowired
	public AssetcontrolView() {

	}

	@Autowired
	private PersonalizationService personalizationService;
	
	@Autowired
	RolesService roleService;
	
	@Autowired
	AssetControlService assetControlService;
	
	@Autowired
	MainView mainView;
	
	@Autowired
	UserService userService;
	
	@Autowired
	public TreeDataNodeService treeDataNodeService;
	
	@Autowired
	public NavigationManager navigationManager;
	

	@PostConstruct
	private void init() {
		try {
		assetControlPermission = roleService.getLoggedInUserRolePermissions().stream().filter(check -> check.getPageName().equals("ASSET")).findFirst().get();
		entityPermission = roleService.getLoggedInUserRolePermissions().stream().filter(check -> check.getPageName().equals("ENTITY")).findFirst().get();
		add = assetControlPermission.getAdd();
		update = assetControlPermission.getEdit();
		delete = assetControlPermission.getDelete();
		access = assetControlPermission.getAccess();
		header = new Header(userService, roleService, navigationManager, "Asset", new Label());
		
		setSpacing(false);
		setMargin(false);
		setResponsive(true);
		setHeight("100%");
		treeNodeSearch = new TextField();
		treeNodeSearch.setWidth("100%");
		treeNodeSearch.setIcon(VaadinIcons.SEARCH);
		treeNodeSearch.setStyleName("small inline-icon search");
		treeNodeSearch.addStyleNames("v-textfield-font", "searchBar-Textfield");
		treeNodeSearch.setPlaceholder("Search");	
		treeNodeSearch.setMaxLength(50);
		treeNodeSearch.setCursorPosition(0);
		clearSearch = new Button(VaadinIcons.CLOSE);
		clearSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		configureTreeNodeSearch();

		Panel panel = getAndLoadAssetControlPanel();
		VerticalLayout verticalPanelLayout = new VerticalLayout();
		verticalPanelLayout.setHeight("100%");
		verticalPanelLayout.setStyleName("split-height");
		CssLayout searchLayout = new CssLayout();
		searchLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		searchLayout.addComponents(treeNodeSearch,clearSearch);
		searchLayout.setWidth("85%");
		VerticalLayout treeSearchPanelLayout = new VerticalLayout(searchLayout);
		treeSearchPanelLayout.setHeight("100%");
		treeSearchPanelLayout.setExpandRatio(searchLayout, 1);
		nodeTree = new TreeGrid<TreeNode>(TreeNode.class);
		nodeTree.setTreeData(assetControlService.getTreeData());
		nodeTree.setSizeFull();
		nodeTree.addStyleName("treeGrid");
		
		nodeTree.addColumn(entity -> {
			String iconHtml="";
			switch (entity.getType()){
			case ENTERPRISE:
				iconHtml =  VaadinIcons.ORIENTATION.getHtml();
				break;
			case ORGANIZATION:
				iconHtml = VaadinIcons.BUILDING_O.getHtml();
				break;
			case MERCHANT:
				iconHtml =  VaadinIcons.SHOP.getHtml();
				break;
			case REGION:
				iconHtml = VaadinIcons.OFFICE.getHtml();
				break;
			case TERMINAL:
				iconHtml = VaadinIcons.LAPTOP.getHtml();
				break;
			case DEVICE:
				iconHtml = VaadinIcons.MOBILE_BROWSER.getHtml();
				break;
			default:
				break;
			}
			return iconHtml +" "+ Jsoup.clean(entity.getLabel(),Whitelist.simpleText());
		},new HtmlRenderer()).setCaption("Entity").setId("entity");	
		nodeTree.getColumn("serialNum").setCaption("Serial");
		nodeTree.setHierarchyColumn("entity");
		
		nodeTree.setColumns("entity","serialNum");
		
		Permission EntityPermission = roleService.getLoggedInUserRolePermissions().stream()
				.filter(per -> per.getPageName().equals("ENTITY")).findFirst().get();
		addEntity = EntityPermission.getAdd();
		updateEntity = EntityPermission.getEdit();
		accessEntity = EntityPermission.getAccess();
		removeEntity = EntityPermission.getDelete();
		
		createEntity = new Button("Add Entity");
		createEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		createEntity.setEnabled(addEntity);
		
		editEntity = new Button("Edit Entity");
		editEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		editEntity.setEnabled(updateEntity);
		
		deleteEntity = new Button("Delete Entity");
		deleteEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		deleteEntity.setEnabled(removeEntity);
		
		copyEntity = new Button("Copy Entity");
		copyEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		copyEntity.setEnabled(addEntity || updateEntity);
		
		pasteEntity = new Button("Paste Entity");
		pasteEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		pasteEntity.setEnabled(false);
		
		ContextMenuWindow assestControlTreeGridMenu = new ContextMenuWindow();
		assestControlTreeGridMenu.addMenuItems(createEntity,editEntity,deleteEntity, copyEntity, pasteEntity);
		nodeTree.addContextClickListener(click->{
			UI.getCurrent().getWindows().forEach(Window::close);
			if(click.getClientY() > 750) {
				assestControlTreeGridMenu.setPosition(click.getClientX(), click.getClientY()-220);
			}else {
				assestControlTreeGridMenu.setPosition(click.getClientX(), click.getClientY());
			}
			UI.getCurrent().addWindow(assestControlTreeGridMenu);
		});
		EntityOperations.entityOperations(nodeTree, createEntity, editEntity, deleteEntity, copyEntity, pasteEntity, treeDataNodeService, personalizationService, assestControlTreeGridMenu);
		
		treeSearchPanelLayout.addComponent(nodeTree);
		treeSearchPanelLayout.setExpandRatio(nodeTree, 14);
		treeSearchPanelLayout.setMargin(true);
		treeSearchPanelLayout.setStyleName("split-Height-ButtonLayout");
		verticalPanelLayout.addComponent(treeSearchPanelLayout);
		splitScreen = new HorizontalSplitPanel();
		splitScreen.setFirstComponent(verticalPanelLayout);
		splitScreen.addStyleName("heartbeat-secondComponent");
		splitScreen.addComponent(getTabSheet());
		splitScreen.setHeight("100%");
		panel.setContent(splitScreen);
		clearGridData();
		
		Page.getCurrent().addBrowserWindowResizeListener(r->{
			if(r.getWidth()<=1575 && r.getWidth()>855) {
				tabMode();
				splitScreen.setSplitPosition(40);
			}else if(r.getWidth()<=855 && r.getWidth()> 0){
				phoneMode();
				splitScreen.setSplitPosition(30);
			} else {
				desktopMode();
				splitScreen.setSplitPosition(31);
			}
			
			if(r.getWidth()<=600) {
				treeNodeSearch.setHeight(28, Unit.PIXELS);
				historySearch.setHeight("28px");
				deviceDebugGridSearch.setHeight("28px");
				debugStartDateField.setHeight("28px");
				debugEndDateField.setHeight("28px");
				deviceDebugStartDateField.setHeight("28px");
				deviceDebugEndDateField.setHeight("28px");
				deviceDebugDuration.setHeight("28px");
				clearSearch.removeStyleNames("v-button-customstyle", "audit-AuditSearchClearDesktop");
				clearSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearPhone");
				clearHistorySeach.removeStyleNames("v-button-customstyle", "audit-AuditSearchClearDesktop");
				clearHistorySeach.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearPhone");
				clearDebugSearch.removeStyleNames("v-button-customstyle", "audit-AuditSearchClearDesktop");
				clearDebugSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearPhone");
				assetTabSheet.getTab(0).setCaption("<h3 style=font-weight:400;width:90px;height:15px;>AssetLog</h3>");
				assetTabSheet.getTab(1).setCaption("<h3 style=font-weight:400;width:90px;height:15px;>Alert</h3>");
				assetTabSheet.getTab(2).setCaption("<h3 style=font-weight:400;width:90px;height:15px;>Debug</h3>");
				removeComponent(header);
				mainView.getTitle().setValue(userService.getLoggedInUserName());
				MainViewIconsLoad.iconsOnPhoneMode(mainView);
			} else if(r.getWidth()>600 && r.getWidth()<=1000){
				treeNodeSearch.setHeight(32, Unit.PIXELS);
				historySearch.setHeight("32px");
				deviceDebugGridSearch.setHeight("32px");
				debugStartDateField.setHeight("32px");
				debugEndDateField.setHeight("32px");
				deviceDebugStartDateField.setHeight("32px");
				deviceDebugEndDateField.setHeight("32px");
				deviceDebugDuration.setHeight("32px");
				clearSearch.removeStyleNames("audit-AuditSearchClearDesktop", "audit-AuditSearchClearPhone");
				clearSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
				clearHistorySeach.removeStyleNames("audit-AuditSearchClearDesktop", "audit-AuditSearchClearPhone");
				clearHistorySeach.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
				clearDebugSearch.removeStyleNames("audit-AuditSearchClearDesktop", "audit-AuditSearchClearPhone");
				clearDebugSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
				addComponentAsFirst(header);
				assetTabSheet.getTab(0).setCaption("<h3 style=font-weight:400;width:120px;height:20px;>AssetLog</h3>");
				assetTabSheet.getTab(1).setCaption("<h3 style=font-weight:400;width:120px;height:20px;>Alert</h3>");
				assetTabSheet.getTab(2).setCaption("<h3 style=font-weight:400;width:120px;height:20px;>Debug</h3>");
				mainView.getTitle().setValue("gemView");
				MainViewIconsLoad.iconsOnTabMode(mainView);
			}else {
				treeNodeSearch.setHeight(37, Unit.PIXELS);
				historySearch.setHeight("37px");
				deviceDebugGridSearch.setHeight("37px");
				debugStartDateField.setHeight("37px");
				debugEndDateField.setHeight("37px");
				deviceDebugStartDateField.setHeight("37px");
				deviceDebugEndDateField.setHeight("37px");
				deviceDebugDuration.setHeight("37px");
				clearHistorySeach.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
				clearHistorySeach.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearDesktop");
				clearDebugSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
				clearDebugSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearDesktop");
				assetTabSheet.getTab(0).setCaption("<h3 style=font-weight:400;width:150px;height:25px;>AssetLog</h3>");
				assetTabSheet.getTab(1).setCaption("<h3 style=font-weight:400;width:150px;height:25px;>Alert</h3>");
				assetTabSheet.getTab(2).setCaption("<h3 style=font-weight:400;width:150px;height:25px;>Debug</h3>");
				MainViewIconsLoad.noIconsOnDesktopMode(mainView);
				addComponentAsFirst(header);
				mainView.getTitle().setValue("gemView");
			}
		});
		
		
		int width = Page.getCurrent().getBrowserWindowWidth();
		if(width >0 && width <=855) {
			phoneMode();
			splitScreen.setSplitPosition(35);
		} else if(width>855 && width<=1575) {
			tabMode();
			splitScreen.setSplitPosition(40);
		}
		else {
			desktopMode();
			splitScreen.setSplitPosition(31);
		}
		
		if(width<=600) {
			treeNodeSearch.setHeight(28, Unit.PIXELS);
			historySearch.setHeight("28px");
			deviceDebugGridSearch.setHeight("28px");
			debugStartDateField.setHeight("28px");
			debugEndDateField.setHeight("28px");
			deviceDebugStartDateField.setHeight("28px");
			deviceDebugEndDateField.setHeight("28px");
			clearHistorySeach.removeStyleNames("v-button-customstyle", "audit-AuditSearchClearDesktop");
			clearHistorySeach.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearPhone");
			clearDebugSearch.removeStyleNames("v-button-customstyle", "audit-AuditSearchClearDesktop");
			clearDebugSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearPhone");
			assetTabSheet.getTab(0).setCaption("<h3 style=font-weight:400;width:90px;height:15px;>AssetLog</h3>");
			assetTabSheet.getTab(1).setCaption("<h3 style=font-weight:400;width:90px;height:15px;>Alert</h3>");
			assetTabSheet.getTab(2).setCaption("<h3 style=font-weight:400;width:90px;height:15px;>Debug</h3>");
			MainViewIconsLoad.iconsOnPhoneMode(mainView);
			removeComponent(header);
			mainView.getTitle().setValue(userService.getLoggedInUserName());
		} else if(width>600 && width<=1000){
			treeNodeSearch.setHeight(32, Unit.PIXELS);
			historySearch.setHeight("32px");
			deviceDebugGridSearch.setHeight("32px");
			debugStartDateField.setHeight("32px");
			debugEndDateField.setHeight("32px");
			deviceDebugStartDateField.setHeight("32px");
			deviceDebugEndDateField.setHeight("32px");
			clearHistorySeach.removeStyleNames("audit-AuditSearchClearDesktop", "audit-AuditSearchClearPhone");
			clearHistorySeach.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
			clearDebugSearch.removeStyleNames("audit-AuditSearchClearDesktop", "audit-AuditSearchClearPhone");
			clearDebugSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
			assetTabSheet.getTab(0).setCaption("<h3 style=font-weight:400;width:120px;height:20px;>AssetLog</h3>");
			assetTabSheet.getTab(1).setCaption("<h3 style=font-weight:400;width:120px;height:20px;>Alert</h3>");
			assetTabSheet.getTab(2).setCaption("<h3 style=font-weight:400;width:120px;height:20px;>Debug</h3>");
			MainViewIconsLoad.iconsOnTabMode(mainView);
			addComponentAsFirst(header);
			mainView.getTitle().setValue("gemView");
		}else {
			treeNodeSearch.setHeight(37, Unit.PIXELS);
			historySearch.setHeight("37px");
			deviceDebugGridSearch.setHeight("37px");
			debugStartDateField.setHeight("37px");
			debugEndDateField.setHeight("37px");
			deviceDebugStartDateField.setHeight("37px");
			deviceDebugEndDateField.setHeight("37px");
			clearHistorySeach.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
			clearHistorySeach.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearDesktop");
			clearDebugSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
			clearDebugSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearDesktop");
			assetTabSheet.getTab(0).setCaption("<h3 style=font-weight:400;width:150px;height:25px;>AssetLog</h3>");
			assetTabSheet.getTab(1).setCaption("<h3 style=font-weight:400;width:150px;height:25px;>Alert</h3>");
			assetTabSheet.getTab(2).setCaption("<h3 style=font-weight:400;width:150px;height:25px;>Debug</h3>");
			MainViewIconsLoad.noIconsOnDesktopMode(mainView);
			addComponentAsFirst(header);
			mainView.getTitle().setValue("gemView");
		}
		
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
		
		this.getUI().getCurrent().addShortcutListener(new ShortcutListener("", KeyCode.ENTER, null) {
			
			@Override
			public void handleAction(Object sender, Object target) {
				treeNodeSearch.focus();
				
			}
		});
		
		}catch(Exception ex) {
			assetControlService.logAssetControlScreenErrors(ex);
		}
		
		mainView.getAssetControl().setEnabled(true);
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
		panel.setResponsive(true);
		panel.setSizeFull();
		panel.setSizeFull();
		addComponent(header);
		addComponent(panel);
		setExpandRatio(header, 1);
	    setExpandRatio(panel, 11);
		return panel;
	}
	private void tabMode() {
		//For History Screen
		optionsLayoutHistoryVerticalTab.addStyleName("audit-tabAlignment");
		dateDeleteHistoryLayout.addComponent(debugStartDateField);
		dateDeleteHistoryLayout.setComponentAlignment(debugStartDateField, Alignment.TOP_RIGHT);
		dateDeleteHistoryLayout.addComponent(debugEndDateField);
		dateDeleteHistoryLayout.setComponentAlignment(debugEndDateField, Alignment.TOP_RIGHT);
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
		dateDeleteDebugLayout.addComponent(saveDeviceDebug);
		dateDeleteDebugLayout.setComponentAlignment(saveDeviceDebug, Alignment.TOP_LEFT);
		optionsLayoutDebugVerticalTab.addComponents(debugSearchLayout,dateDeleteDebugLayout);
		
		optionsLayoutDebugHorizontalDesktop.setVisible(false);
		optionsLayoutDebugVerticalPhone.setVisible(false);
		optionsLayoutDebugVerticalTab.setVisible(true);
		
	}
	
	private void phoneMode() {
		//History Screen
		dateDeleteHistoryLayoutPhone.addComponents(debugStartDateField, debugEndDateField);
		optionsLayoutHistoryVerticalPhone.addStyleName("audit-phoneAlignment");
		historySearchLayout.addStyleName("audit-phoneAlignment");
		optionsLayoutHistoryVerticalPhone.addComponents(historySearchLayout,dateDeleteHistoryLayoutPhone);
		optionsLayoutHistoryVerticalPhone.setVisible(true);
		optionsLayoutHistoryHorizontalDesktop.setVisible(false);
		optionsLayoutHistoryVerticalTab.setVisible(false);
		
		//Debug Screen
		debugDeleteSaveLayout.addComponents(deleteDeviceDebugGridRow,saveDeviceDebug);
		dateDeleteDebugLayoutPhone.addComponents(deviceDebugStartDateField, deviceDebugEndDateField,debugDeleteSaveLayout);
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
//		dateDeleteHistoryLayout.addComponent(deleteHistoryGridRow);
//		dateDeleteHistoryLayout.setComponentAlignment(deleteHistoryGridRow, Alignment.TOP_LEFT);
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
		dateDeleteDebugLayout.addComponent(saveDeviceDebug);
		dateDeleteDebugLayout.setComponentAlignment(saveDeviceDebug, Alignment.TOP_LEFT);
		optionsLayoutDebugHorizontalDesktop.addComponent(dateDeleteDebugLayout);
		optionsLayoutDebugHorizontalDesktop.setComponentAlignment(dateDeleteDebugLayout, Alignment.TOP_RIGHT);
		optionsLayoutDebugHorizontalDesktop.setVisible(true);
		optionsLayoutDebugVerticalTab.setVisible(false);
		optionsLayoutDebugVerticalPhone.setVisible(false);
	}
	
	private void configureTreeNodeSearch() {
		// FIXME Not able to put Tree Search since its using a Hierarchical
		// Dataprovider.
		treeNodeSearch.addValueChangeListener(changed -> {
			if(changed.getValue().length()==50) {
				Notification search = Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
				search.addCloseListener(listener->{
					String valueInLower = changed.getValue().toLowerCase();
					if(!valueInLower.isEmpty() && valueInLower!=null) {
						nodeTree.setTreeData(treeDataNodeService.searchTreeData(valueInLower));
					}else {
						nodeTree.setTreeData(treeDataNodeService.getTreeData());
					}
				});
			}else {
			String valueInLower = changed.getValue().toLowerCase();
				if(!valueInLower.isEmpty() && valueInLower!=null) {
					nodeTree.setTreeData(treeDataNodeService.searchTreeData(valueInLower));
				}else {
					nodeTree.setTreeData(treeDataNodeService.getTreeData());
				}
			}
		});
		
		treeNodeSearch.addShortcutListener(new ShortcutListener("Clear",KeyCode.ESCAPE,null) {
			
			@Override
			public void handleAction(Object sender, Object target) {
				if (target == treeNodeSearch) {
					treeNodeSearch.clear();
				}
			}
		});
		
		clearSearch.addClickListener(click->treeNodeSearch.clear());
	}

	private TabSheet getTabSheet() throws ApiException {
		assetTabSheet = new TabSheet();
		assetTabSheet.setHeight(91.0f, Unit.PERCENTAGE);
		assetTabSheet.addStyleNames(ValoTheme.TABSHEET_FRAMED, ValoTheme.TABSHEET_PADDED_TABBAR, "tabsheet-margin");
		assetTabSheet.setCaptionAsHtml(true);
		assetTabSheet.setTabCaptionsAsHtml(true);
		assetTabSheet.addTab(getHistory());
		assetTabSheet.getTab(0).setId("assetlog");
		assetTabSheet.addTab(getAlert());
		assetTabSheet.getTab(1).setId("alert");
		assetTabSheet.addTab(getDebug());
		assetTabSheet.getTab(2).setId("debug");
		
		return assetTabSheet;
	}
	
	private void clearCalenderDates() {
		debugStartDateField.clear();
		debugEndDateField.clear();
		deviceDebugEndDateField.clear();
		deviceDebugStartDateField.clear();
	}
	
	private void confirmDialog(Set<AssetHistory> historyList, TreeNode treeNode) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?",
		        "Ok", "Cancel", new ConfirmDialog.Listener() {

		            public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		                    // Confirmed to continue
		                	for(AssetHistory history : historyList) {
								assetControlService.deleteHistoryGridData(history.getId());
		                	}
								List<AssetHistory> historyListNew = assetControlService.getHistoryGridData(treeNode.getEntityId());
			                	DataProvider data = new ListDataProvider(historyListNew);
			                	debugGrid.setDataProvider(data);
			                	nodeTree.getDataProvider().refreshAll();
			    				historySearch.clear();
			    				clearCalenderDates();
		                } else {
		                    // User did not confirm
		                    
		                }
		            }
		        });
	}
	
	private void confirmDialogDebug(Long id, TreeNode treeNode) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?",
		        "Ok", "Cancel", new ConfirmDialog.Listener() {

		            public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		                    // Confirmed to continue
		                	assetControlService.deleteDeviceDebugItem(id);
		                	List<DebugItems> debugItemsList = assetControlService.getDeviceDebugList(treeNode.getEntityId(), treeNode.getType().name());
		                	DataProvider data = new ListDataProvider(debugItemsList);
		                	deviceDebugGrid.setDataProvider(data);
		                	nodeTree.getDataProvider().refreshAll();
		        			deviceDebugGrid.getDataProvider().refreshAll();
		        			deviceDebugGridSearch.clear();
		        			clearCalenderDates();
		                } else {
		                    // User did not confirm
		                    
		                }
		            }
		        });
	}
	
	private VerticalLayout getHistory() throws ApiException {
		historyLayoutFull = new VerticalLayout();
		historyLayoutFull.addStyleName(ValoTheme.LAYOUT_CARD);
		historyLayoutFull.setWidth("100%");
		historyLayoutFull.setHeight("100%");
		historyLayoutFull.setResponsive(true);
		historyLayoutFull.addStyleNames("audit-DeviceVerticalAlignment", "asset-historyDebugLayout");
		debugGrid = new Grid<>(AssetHistory.class);
		debugGrid.setWidth("100%");
		debugGrid.setHeight("97%");
		debugGrid.setResponsive(true);
		debugGrid.addStyleName("grid-AuditOdometerAlignment");
		debugGrid.setSelectionMode(SelectionMode.MULTI);
		debugGrid.setColumns("type", "description");
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
		
		debugGrid.addSelectionListener(listener->{
			UI.getCurrent().getWindows().forEach(Window::close);
		});

		// debugGrid.setData();
		historySearch = new TextField();
		historySearch.setWidth("100%");
		historySearch.setHeight("37px");
		historySearch.setIcon(VaadinIcons.SEARCH);
		historySearch.setStyleName("small inline-icon search");
		historySearch.setPlaceholder("Search");
		historySearch.setResponsive(true);
		historySearch.setMaxLength(50);
		historySearch.addShortcutListener(new ShortcutListener("Clear", KeyCode.ESCAPE, null) {

			@Override
			public void handleAction(Object sender, Object target) {
				if (target == historySearch) {
					historySearch.clear();
				}

			}
		});
		historySearch.addValueChangeListener(valueChange -> {
			if(valueChange.getValue().length()==50) {
				Notification search = Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
				search.addCloseListener(listener->{
					String filter = valueChange.getValue().toLowerCase();
					String endDate =null;
					String startDate=null;
					if(debugEndDateField.getValue()!=null && debugStartDateField!=null) {
					endDate = debugEndDateField.getValue().format(dateFormatter1);
		 			startDate = debugStartDateField.getValue().format(dateFormatter1);
					}
						List<AssetHistory> searchGridData = assetControlService.searchHistoryGridDataByText(nodeTree.getSelectedItems().iterator().next().getEntityId(),filter, startDate, endDate);
						DataProvider data = new ListDataProvider(searchGridData);
						debugGrid.setDataProvider(data);
				});
			}else {
			String filter = valueChange.getValue().toLowerCase();
			String endDate =null;
			String startDate=null;
			if(debugEndDateField.getValue()!=null && debugStartDateField!=null) {
			endDate = debugEndDateField.getValue().format(dateFormatter1);
 			startDate = debugStartDateField.getValue().format(dateFormatter1);
			}
				List<AssetHistory> searchGridData = assetControlService.searchHistoryGridDataByText(nodeTree.getSelectedItems().iterator().next().getEntityId(),filter, startDate, endDate);
				DataProvider data = new ListDataProvider(searchGridData);
				debugGrid.setDataProvider(data);
				
				//Search on UI Side. May be Useful in future
				
			/*ListDataProvider<Debug> debugDataProvider = (ListDataProvider<Debug>) debugGrid.getDataProvider();
			debugDataProvider.setFilter(filter -> {
				String descriptionInLower = filter.getDescription().toLowerCase();
				String typeInLower = filter.getType().name().toLowerCase();
				return typeInLower.equals(valueInLower) || descriptionInLower.contains(valueInLower);
			});*/
			}
		});
		
		assetControlHistorySearchLayout = new CssLayout();
		assetControlHistorySearchLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		assetControlHistorySearchLayout.setWidth("90%");
		
		clearHistorySeach = new Button(VaadinIcons.CLOSE);
		clearHistorySeach.addClickListener(listener->{
			historySearch.clear();
		});
		assetControlHistorySearchLayout.addComponents(historySearch, clearHistorySeach);
		
		deleteHistoryGridRow = new Button("Delete Record/s");
		deleteHistoryGridRow.addStyleNames(ValoTheme.BUTTON_BORDERLESS, "v-button-customstyle");
		deleteHistoryGridRow.setResponsive(true);
		deleteHistoryGridRow.addClickListener(clicked -> {
			if(debugGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.ASSET_HISTORY_DELETE, Notification.Type.ERROR_MESSAGE);
			}else {
				confirmDialog(debugGrid.getSelectedItems(), nodeTree.getSelectedItems().iterator().next());
			}
			paramContextWindow.close();
		});
		deleteHistoryGridRow.setEnabled(delete);
		
		paramContextWindow = new ContextMenuWindow();
		paramContextWindow.addMenuItems(deleteHistoryGridRow);
		debugGrid.addContextClickListener(click -> {
			UI.getCurrent().getWindows().forEach(Window::close);
			paramContextWindow.setPosition(click.getClientX(), click.getClientY());
			if(debugGrid.getSelectedItems().size()>0) {
				deleteHistoryGridRow.setEnabled(delete);
			}else {
				deleteHistoryGridRow.setEnabled(false);
			}
			UI.getCurrent().addWindow(paramContextWindow);
		});
		
		UI.getCurrent().addClickListener(listener->{
			if(paramContextWindow!=null) {
				paramContextWindow.close();
			}
		});

		 optionsLayoutHistoryHorizontalDesktop = new HorizontalLayout();
		optionsLayoutHistoryHorizontalDesktop.setWidth("100%");
		optionsLayoutHistoryHorizontalDesktop.setResponsive(true);
		optionsLayoutHistoryHorizontalDesktop.addStyleName("audit-DeviceSearch");

		historySearchLayout = new HorizontalLayout();
		historySearchLayout.setWidth("100%");
		historySearchLayout.addComponent(assetControlHistorySearchLayout);
		historySearchLayout.setComponentAlignment(assetControlHistorySearchLayout, Alignment.TOP_LEFT);

		debugStartDateField = new DateField();
		debugStartDateField.setWidth("100%");
		debugStartDateField.setHeight("37px");
		debugStartDateField.setPlaceholder("Start Date");
		debugStartDateField.setResponsive(true);
		debugStartDateField.setDateFormat(DATE_FORMAT);
		debugStartDateField.setRangeEnd(localTimeNow.toLocalDate());
		debugStartDateField.setDescription("Start Date");
		debugStartDateField.addStyleName("v-textfield-font");
		
		debugEndDateField = new DateField();
		debugEndDateField.setWidth("100%");
		debugEndDateField.setHeight("37px");
		debugEndDateField.setPlaceholder("End Date");
		debugEndDateField.setResponsive(true);
		debugEndDateField.setDateFormat(DATE_FORMAT);
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
	        	 		if(change.getValue().compareTo(debugStartDateField.getValue()) > 0 ) {
	        	 			String endDate = debugEndDateField.getValue().format(dateFormatter1);
	        	 			String startDate = debugStartDateField.getValue().format(dateFormatter1);
	        	 			String filter = historySearch.getValue();
        	 					List<AssetHistory> historyListFilterBydates = new ArrayList<>();
    	        	 			List<HeartbeatAudit> searchList = assetControlService.searchHistoryByDates(nodeTree.getSelectedItems().iterator().next().getEntityId(), filter, startDate, endDate);
    	        	 			if(searchList!=null && !searchList.isEmpty()) {
    	        	 				for(HeartbeatAudit heartbeatAudit: searchList) {
    	        	 					AssetHistory assetHistory = new AssetHistory(heartbeatAudit.getId(),heartbeatAudit.getComponent(), heartbeatAudit.getDescription());
    	        	 					historyListFilterBydates.add(assetHistory);
    	        	 				}
    	        	 			}
    	        	 				DataProvider data = new ListDataProvider(historyListFilterBydates);
    	        	 				debugGrid.setDataProvider(data);
	        	 			
				} else {
					Notification.show(NotificationUtil.AUDIT_SAMEDATE, Notification.Type.ERROR_MESSAGE);
				}
	        		 	}else {
	        		 		Notification.show(NotificationUtil.ASSET_HISTORY_STARTDATE, Notification.Type.ERROR_MESSAGE);
	        		 		debugEndDateField.clear();
	        		 	}
	        }else {
	        	Notification.show(NotificationUtil.ASSET_HISTORY_SELECTNODE, Notification.Type.ERROR_MESSAGE);
	        	clearCalenderDates();
	        }
		}else {
			List<AssetHistory> assetHistoryList = new ArrayList<>();
				assetHistoryList = assetControlService.getHistoryGridData(nodeTree.getSelectedItems().iterator().next().getEntityId());
				DataProvider data = new ListDataProvider(assetHistoryList);
				debugGrid.setDataProvider(data);
		}
	});
		
		debugStartDateField.addValueChangeListener(change ->{
			if(change.getValue()!=null) {
				debugEndDateField.clear();
				debugEndDateField.setRangeStart(change.getValue().plusDays(1));
			}else {
				historySearch.clear();
				debugEndDateField.clear();
				List<AssetHistory> assetHistoryList = new ArrayList<>();
					assetHistoryList = assetControlService.getHistoryGridData(nodeTree.getSelectedItems().iterator().next().getEntityId());
					DataProvider data = new ListDataProvider(assetHistoryList);
					debugGrid.setDataProvider(data);
			}
	});

		nodeTree.addSelectionListener(selection -> {
			if(!selection.getFirstSelectedItem().isPresent()) {
				
				switch (assetTabSheet.getTab(assetTabSheet.getSelectedTab()).getId().toLowerCase()) {
				case "assetlog":
					
					List<AssetHistory> assetHistoryList = new ArrayList<>();
					DataProvider historyData = new ListDataProvider(assetHistoryList);
					debugGrid.setDataProvider(historyData);
					historySearch.clear();
					break;
				case "alert":
					List<Alert> assetAlertList = new ArrayList<>();
					DataProvider alertData = new ListDataProvider(assetAlertList);
					alertGrid.setDataProvider(alertData);
					break;
				case "debug":
					List<DebugItems> debugItemsList = new ArrayList<>();
					DataProvider debugItemsData = new ListDataProvider(debugItemsList);
					//deviceDebugGrid.setData(debugItemsData);
					//deviceDebugGridSearch.clear();
					s.setEnabled(false);
					break;
				default:
					break;
				}
				
			} else {
				switch (assetTabSheet.getTab(assetTabSheet.getSelectedTab()).getId().toLowerCase()) {
				case "assetlog":
					List<AssetHistory> assetHistoryList = new ArrayList<>();
						assetHistoryList = assetControlService.getHistoryGridData(nodeTree.getSelectedItems().iterator().next().getEntityId());
						DataProvider data = new ListDataProvider(assetHistoryList);
						debugGrid.setDataProvider(data);
						historySearch.clear();
					break;
				case "alert":
					List<Alert> alertList = new ArrayList<>();
					alertList = assetControlService.getAlertGridData(nodeTree.getSelectedItems().iterator().next().getEntityId());
					DataProvider alertData = new ListDataProvider(alertList);
					alertGrid.setDataProvider(alertData);
					break;
				case "debug":
					if(nodeTree.getSelectedItems().iterator().next().getType().name().equalsIgnoreCase("Terminal")) {
						TerminalClient terminal = assetControlService.getTerminal(nodeTree.getSelectedItems().iterator().next().getEntityId());
						if(terminal!=null) {
							if((assetControlPermission.getAdd() || assetControlPermission.getEdit() || 
									assetControlPermission.getDelete()) && (entityPermission.getAdd() || entityPermission.getEdit())) {
								s.setEnabled(true);
								s.setValue(terminal.isDebugActive());
								if(s.getValue()) {
									deviceDebugDuration.setEnabled(true);
									String date = terminal.getDuration();
									LocalDate localDate = LocalDate.parse(date);
									deviceDebugDuration.setValue(localDate);
								}else {
									deviceDebugDuration.clear();
									deviceDebugDuration.setEnabled(false);
								}
							}else {
								Notification.show("User don't have access to active debug", Type.ERROR_MESSAGE);
							}
						}
						
					}else {
						s.setValue(false);
						s.setEnabled(false);
						deviceDebugDuration.clear();
						deviceDebugDuration.setEnabled(false);
					}
	
					break;
				default:
					break;
				}
			}
			
			clearCalenderDates();
			historySearch.clear();
			deviceDebugGridSearch.clear();

		});
		return historyLayoutFull;
	}

	private VerticalLayout getAlert() {
		Button[] buttons= {saveAlertForm,cancelAlertForm};
		AlertTab alertTab  = new AlertTab(alertGrid, nodeTree,Page.getCurrent().getUI(), assetControlService,assetControlPermission, buttons);
		return alertTab.getAlert();
	}

	private VerticalLayout getDebug() throws ApiException {
		debugLayout = new VerticalLayout();
		HorizontalLayout debugLabel = new HorizontalLayout();
		HorizontalLayout debugMonitoringLabel = new HorizontalLayout();
		
		s = new OnOffSwitch ();
		s.setValue(false);
		s.setCaption("Activate Debug");
		s.setEnabled(false);
		
		Label entityInformation = new Label("Entity Information");
		entityInformation.addStyleNames(ValoTheme.LABEL_BOLD, ValoTheme.LABEL_H3);
		entityInformation.addStyleName("label-style");
		debugLabel.setWidth("100%");
		debugLabel.addComponent(entityInformation);
		
		deviceDebugFormLayout = new FormLayout();
		deviceDebugFormLayout.addStyleNames("heartbeat-verticalLayout", "asset-debugSFormLayout","system-LabelAlignment");
		deviceDebugFormLayout.addComponent(ComponentUtil.getFormFieldWithLabel("Entity Type", FormFieldType.TEXTBOX));
		deviceDebugFormLayout.addComponent(ComponentUtil.getFormFieldWithLabel("Name", FormFieldType.TEXTBOX));
		deviceDebugFormLayout.addComponent(ComponentUtil.getFormFieldWithLabel("Description", FormFieldType.TEXTBOX));
		deviceDebugFormLayout.addComponent(ComponentUtil.getFormFieldWithLabel("Entity Active", FormFieldType.HORIZONTALLAYOUT));
		deviceDebugFormLayout.addComponent(ComponentUtil.getFormFieldWithLabel("Serial Num.", FormFieldType.TEXTBOX));
		deviceDebugFormLayout.addComponent(ComponentUtil.getFormFieldWithLabel("Receive Debug", FormFieldType.HORIZONTALLAYOUT));
		
		deviceDebugDuration =  new DateField();
		
		deviceDebugDuration.setResponsive(true);
		deviceDebugDuration.setDateFormat(DATE_FORMAT);
		deviceDebugDuration.setPlaceholder("End Date");
		deviceDebugDuration.addStyleNames("v-textfield-font", "personlization-formAlignment");
		deviceDebugDuration.setCaptionAsHtml(true);
		deviceDebugDuration.setCaption("Debug Duration");
		deviceDebugDuration.setEnabled(false);
		deviceDebugDuration.setRangeStart(localTimeNow.toLocalDate().plusDays(1));

		deviceDebugFormLayout.addComponent(deviceDebugDuration);
		deviceDebugFormLayout.setComponentAlignment(deviceDebugDuration, Alignment.TOP_RIGHT);
		Label debugMonitoring = new Label("Device Debug Monitoring");
		debugMonitoring.addStyleNames(ValoTheme.LABEL_BOLD, ValoTheme.LABEL_H3);
		debugMonitoring.addStyleName("label-style");
		debugMonitoringLabel.addStyleName("assetDebug-label");
		debugMonitoringLabel.addComponent(debugMonitoring);
		getDeviceDebugGridSearchLayout();
			
		s.addValueChangeListener(listener -> {
			if (nodeTree.getSelectedItems().size() > 0) {
				if (nodeTree.getSelectedItems().iterator().next().getType().name().equalsIgnoreCase("Terminal")) {
					if (listener.getValue()) {
						deviceDebugDuration.setEnabled(true);

						deviceDebugDuration.addValueChangeListener(change -> {
							if (change.getValue() != null) {
								String dateValue = change.getValue().format(dateFormatter2);
								assetControlService.saveDebugAndDuration(
										nodeTree.getSelectedItems().iterator().next().getEntityId(), true, dateValue);
							}
						});
					} else {
						deviceDebugDuration.setEnabled(false);
						deviceDebugDuration.clear();
						assetControlService.saveDebugAndDuration(
								nodeTree.getSelectedItems().iterator().next().getEntityId(), false, null);
					}

				}
			} else {
				Notification.show(NotificationUtil.PERSONALIZATION_DEBUG_DURATION, Type.ERROR_MESSAGE);
			}
		});
		
		FormLayout fL = new FormLayout(s,deviceDebugDuration);
		debugLayout.addComponents(fL);
		return debugLayout;
	}

	private void getDeviceDebugGridSearchLayout() throws ApiException {
		
		optionsLayoutDebugHorizontalDesktop = new HorizontalLayout();
		optionsLayoutDebugHorizontalDesktop.setWidth("100%");
		optionsLayoutDebugHorizontalDesktop.addStyleName("asset-debugSearch");
		optionsLayoutDebugHorizontalDesktop.setResponsive(true);
		
		deviceDebugGridSearch = new TextField();
		deviceDebugGridSearch.setWidth("100%");
		deviceDebugGridSearch.setHeight("37px");
		deviceDebugGridSearch.setIcon(VaadinIcons.SEARCH);
		deviceDebugGridSearch.setStyleName("small inline-icon search");
		deviceDebugGridSearch.setPlaceholder("Search");
		deviceDebugGridSearch.setResponsive(true);
		deviceDebugGridSearch.setMaxLength(50);
		deviceDebugGridSearch.addShortcutListener(new ShortcutListener("Clear", KeyCode.ESCAPE, null) {

			@Override
			public void handleAction(Object sender, Object target) {
				if (target == deviceDebugGridSearch) {
					deviceDebugGridSearch.clear();
				}

			}
		});
		deviceDebugGridSearch.addValueChangeListener(valueChange -> {
			if (valueChange.getValue().length() == 50) {
				Notification search = Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
				search.addCloseListener(listener->{
					String filter = valueChange.getValue().toLowerCase();
					String endDate = null;
					String startDate = null;
					if (deviceDebugEndDateField.getValue() != null && deviceDebugStartDateField != null) {
						endDate = deviceDebugEndDateField.getValue().format(dateFormatter1);
						startDate = deviceDebugStartDateField.getValue().format(dateFormatter1);
					}
					List<DebugItems> searchGridData = assetControlService.getDeviceDebugBySearch(
							nodeTree.getSelectedItems().iterator().next().getEntityId(), filter, startDate, endDate);
					DataProvider data = new ListDataProvider(searchGridData);
					deviceDebugGrid.setDataProvider(data);
				});
			} else {
				String filter = valueChange.getValue().toLowerCase();
				String endDate = null;
				String startDate = null;
				if (deviceDebugEndDateField.getValue() != null && deviceDebugStartDateField != null) {
					endDate = deviceDebugEndDateField.getValue().format(dateFormatter1);
					startDate = deviceDebugStartDateField.getValue().format(dateFormatter1);
				}
				List<DebugItems> searchGridData = assetControlService.getDeviceDebugBySearch(
						nodeTree.getSelectedItems().iterator().next().getEntityId(), filter, startDate, endDate);
				DataProvider data = new ListDataProvider(searchGridData);
				deviceDebugGrid.setDataProvider(data);
			}
		});
		
		assetControlDebugSearchLayout = new CssLayout();
		assetControlDebugSearchLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		assetControlDebugSearchLayout.setWidth("90%");
		
		clearDebugSearch = new Button(VaadinIcons.CLOSE);
		clearDebugSearch.addClickListener(listener->{
			deviceDebugGridSearch.clear();
		});
		assetControlDebugSearchLayout.addComponents(deviceDebugGridSearch, clearDebugSearch);
		
		debugSearchLayout = new HorizontalLayout();
		debugSearchLayout.setWidth("100%");
		debugSearchLayout.addComponent(assetControlDebugSearchLayout);
		debugSearchLayout.setComponentAlignment(assetControlDebugSearchLayout, Alignment.TOP_LEFT);
		
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
				confirmDialogDebug(deviceDebugGrid.getSelectedItems().iterator().next().getId(),nodeTree.getSelectedItems().iterator().next());
			}
			

		});
		
		saveDeviceDebug = new Button("Save");
		saveDeviceDebug.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		saveDeviceDebug.setResponsive(true);
		saveDeviceDebug.addClickListener(clicked -> {
			HorizontalLayout HL1= (HorizontalLayout)deviceDebugFormLayout.getComponent(5);
			CheckBox checkbox1 = (CheckBox) HL1.getComponent(1);
			boolean value = checkbox1.getValue();
			DateField dateField = (DateField)deviceDebugFormLayout.getComponent(6);
			String dateValue = dateField.getValue().format(dateFormatter2);
			assetControlService.saveDebugAndDuration(nodeTree.getSelectedItems().iterator().next().getEntityId(), value, dateValue);
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
		deviceDebugEndDateField.setPlaceholder("End Date");
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
		dateDeleteDebugLayout.addStyleName("personlization-formAlignment");
		debugDeleteSaveLayout= new HorizontalLayout();
		
		optionsLayoutDebugHorizontalDesktop.addComponent(debugSearchLayout);
		optionsLayoutDebugHorizontalDesktop.setComponentAlignment(debugSearchLayout, Alignment.MIDDLE_LEFT);
		dateDeleteDebugLayout.addComponent(deviceDebugStartDateField);
		dateDeleteDebugLayout.setComponentAlignment(deviceDebugStartDateField, Alignment.MIDDLE_LEFT);
		dateDeleteDebugLayout.addComponent(deviceDebugEndDateField);
		dateDeleteDebugLayout.setComponentAlignment(deviceDebugEndDateField, Alignment.MIDDLE_RIGHT);
		dateDeleteDebugLayout.addComponent(deleteDeviceDebugGridRow);
		dateDeleteDebugLayout.setComponentAlignment(deleteDeviceDebugGridRow, Alignment.MIDDLE_RIGHT);
		dateDeleteDebugLayout.addComponent(saveDeviceDebug);
		dateDeleteDebugLayout.setComponentAlignment(saveDeviceDebug, Alignment.MIDDLE_RIGHT);
		optionsLayoutDebugHorizontalDesktop.addComponent(dateDeleteDebugLayout);
		optionsLayoutDebugHorizontalDesktop.setComponentAlignment(dateDeleteDebugLayout, Alignment.MIDDLE_LEFT);

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
	        	 		if(change.getValue().compareTo(deviceDebugStartDateField.getValue()) > 0 ) {
	        	 			String endDate = deviceDebugEndDateField.getValue().format(dateFormatter1);
	        	 			String startDate = deviceDebugStartDateField.getValue().format(dateFormatter1);
	        	 			String filter = deviceDebugGridSearch.getValue();
        	 					List<DebugItems> deviceDebugItemsList = new ArrayList<>();
    	        	 			List<DebugItem> searchList = assetControlService.searchDeviceDebugByDates(nodeTree.getSelectedItems().iterator().next().getEntityId(), filter, startDate, endDate);
    	        	 			if(searchList!=null && !searchList.isEmpty()) {
    	        	 				for(DebugItem debugItem: searchList) {
    	        	 					DebugItems debugItems = new DebugItems(debugItem.getId(), assetControlService.getType(debugItem.getLevel()),debugItem.getMessage());
    	        	 					deviceDebugItemsList.add(debugItems);
    	        	 				}
    	        	 			}
    	        	 				DataProvider data = new ListDataProvider(deviceDebugItemsList);
    	        	 				deviceDebugGrid.setDataProvider(data);
	        	 			
				} 
	        		 	}else {
	        		 		Notification.show(NotificationUtil.ASSET_DEBUG_STARTDATE, Notification.Type.ERROR_MESSAGE);
	        		 		deviceDebugEndDateField.clear();
	        		 	}
	        }else {
	        	Notification.show(NotificationUtil.ASSET_DEBUG_SELECTNODE, Notification.Type.ERROR_MESSAGE);
	        	clearCalenderDates();
	        }
		}else {
			List<DebugItems> debugItemsList = new ArrayList<>();
			debugItemsList= assetControlService.getDeviceDebugList(nodeTree.getSelectedItems().iterator().next().getEntityId(), 
					nodeTree.getSelectedItems().iterator().next().getType().name());
			DataProvider debugItemsData = new ListDataProvider(debugItemsList);
			deviceDebugGrid.setDataProvider(debugItemsData);
		}
	});
		
		deviceDebugStartDateField.addValueChangeListener(change ->{
			if(change.getValue()!=null) {
				deviceDebugEndDateField.clear();
				deviceDebugEndDateField.setRangeStart(change.getValue().plusDays(1));
			}else {
				deviceDebugGridSearch.clear();
				deviceDebugEndDateField.clear();
				List<DebugItems> debugItemsList = new ArrayList<>();
				debugItemsList= assetControlService.getDeviceDebugList(nodeTree.getSelectedItems().iterator().next().getEntityId(), 
						nodeTree.getSelectedItems().iterator().next().getType().name());
				DataProvider debugItemsData = new ListDataProvider(debugItemsList);
				deviceDebugGrid.setDataProvider(debugItemsData);
			}
	});

	}

}
