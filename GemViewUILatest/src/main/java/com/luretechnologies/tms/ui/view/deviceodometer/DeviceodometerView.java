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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.client.restlib.service.model.HeartbeatOdometer;
import com.luretechnologies.tms.backend.data.entity.DeviceOdometer;
import com.luretechnologies.tms.backend.data.entity.Permission;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.service.OdometerService;
import com.luretechnologies.tms.backend.service.RolesService;
import com.luretechnologies.tms.backend.service.TreeDataNodeService;
import com.luretechnologies.tms.backend.service.UserService;
import com.luretechnologies.tms.ui.MainView;
import com.luretechnologies.tms.ui.components.EntityOperations;
import com.luretechnologies.tms.ui.components.MainViewIconsLoad;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.luretechnologies.tms.ui.navigation.NavigationManager;
import com.luretechnologies.tms.ui.view.ContextMenuWindow;
import com.luretechnologies.tms.ui.view.Header;
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
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TreeGrid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 * 
 * @author Vinay
 *
 */

@SpringView(name = DeviceodometerView.VIEW_NAME)
public class DeviceodometerView extends VerticalLayout implements Serializable, View {

	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final DateTimeFormatter dateFormatter1 = DateTimeFormatter.ofPattern("yyMMdd");
	private static final long serialVersionUID = -8746130479258235216L;
	public static final String VIEW_NAME = "deviceodometer";
	private static final LocalDateTime localTimeNow = LocalDateTime.now();
	private static Grid<DeviceOdometer> odometerDeviceGrid;
	private static TreeGrid<TreeNode> nodeTreeGrid;
	private static Button clearSearch, clearOdometerSearch;
	private static TextField treeNodeSearch, odometerDeviceSearch;
	private static HorizontalSplitPanel splitScreen;
	private static DateField odometerStartDateField, odometerEndDateField;
	private static VerticalLayout odometerDeviceLayout;
	private static HorizontalLayout optionsLayoutHorizontalDesktop;
	private static HorizontalLayout odometerSearchLayout;
	private static VerticalLayout optionsLayoutVerticalTab;
	private static VerticalLayout optionsLayoutVerticalPhone;
	private static HorizontalLayout dateDeleteLayout;
	private static VerticalLayout dateDeleteLayoutPhone;
	private static CssLayout deviceOdometerSearchLayout;
	private static Button createEntity, editEntity, deleteEntity, copyEntity, pasteEntity;
	private static HorizontalLayout header;
	private ContextMenuWindow deleteContextWindow;
	private Button deleteOdometerGrid;
	private boolean deleteRow;

	@Autowired
	public NavigationManager navigationManager; 
	
	@Autowired
	public DeviceodometerView() {

	}

	@Autowired
	public OdometerService odometerDeviceService;

	@Autowired
	private RolesService roleService;

	@Autowired
	MainView mainView;

	@Autowired
	UserService userService;

	@Autowired
	TreeDataNodeService treeDataNodeService;

	@PostConstruct
	private void init() {
		try {
			header = new Header(userService, roleService, navigationManager, "Odometer", new Label());
		Page.getCurrent().addBrowserWindowResizeListener(r->{
			System.out.println("Height "+ r.getHeight() + "Width:  " + r.getWidth()+ " in pixel");
			if(r.getWidth()<=1450 && r.getWidth()>=700) {
				tabMode();
				splitScreen.setSplitPosition(30);

			}else if(r.getWidth()<=699 && r.getWidth()> 0){
				phoneMode();
				
			} else {
				desktopMode();
			}
			
			if(r.getWidth()<=600) {
				odometerStartDateField.setHeight("28px");
				odometerEndDateField.setHeight("28px");
				treeNodeSearch.setHeight(28, Unit.PIXELS);
				odometerDeviceSearch.setHeight(28, Unit.PIXELS);
				clearOdometerSearch.removeStyleNames("v-button-customstyle", "audit-AuditSearchClearDesktop");
				clearOdometerSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"odometer-OdometerSearchClearPhone");
				mainView.getTitle().setValue(userService.getLoggedInUserName());
				removeComponent(header);
				odometerEndDateField.setWidth("100%");
				MainViewIconsLoad.iconsOnPhoneMode(mainView);
			} else if(r.getWidth()>600 && r.getWidth()<=1000){
				odometerStartDateField.setHeight("32px");
				odometerEndDateField.setHeight("32px");
				treeNodeSearch.setHeight(32, Unit.PIXELS);
				odometerDeviceSearch.setHeight(32, Unit.PIXELS);
				mainView.getTitle().setValue("gemView");
				clearOdometerSearch.removeStyleNames("audit-AuditSearchClearDesktop", "odometer-OdometerSearchClearPhone");
				clearOdometerSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
				addComponentAsFirst(header);
				MainViewIconsLoad.iconsOnTabMode(mainView);
				odometerEndDateField.setWidth("100%");
			}else {
				odometerStartDateField.setHeight("100%");
				odometerEndDateField.setHeight("100%");
				treeNodeSearch.setHeight(37, Unit.PIXELS);
				odometerDeviceSearch.setHeight(37, Unit.PIXELS);
				mainView.getTitle().setValue("gemView");
				clearOdometerSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
				clearOdometerSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearDesktop");
				addComponentAsFirst(header);
				splitScreen.setSplitPosition(30);
				odometerEndDateField.setWidth("94%");
				MainViewIconsLoad.noIconsOnDesktopMode(mainView);
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
		treeNodeSearch.setPlaceholder("Search");
		treeNodeSearch.addStyleName("v-textfield-font");
		treeNodeSearch.setMaxLength(50);
		treeNodeSearch.setHeight(37, Unit.PIXELS);
		treeNodeSearch.setCursorPosition(0);
		clearSearch = new Button(VaadinIcons.CLOSE);
		clearSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
		configureTreeNodeSearch();
		
		Panel panel = getAndLoadOdometerPanel();
		VerticalLayout verticalPanelLayout = new VerticalLayout();
		verticalPanelLayout.setHeight("100%");
		verticalPanelLayout.setStyleName("split-height");
		CssLayout treeSeachLayout = new CssLayout();
		treeSeachLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		treeSeachLayout.setWidth("85%");
		treeSeachLayout.addComponents(treeNodeSearch,clearSearch);
		VerticalLayout treeSearchPanelLayout = new VerticalLayout(treeSeachLayout);
		treeSearchPanelLayout.setHeight("100%");
		nodeTreeGrid = new TreeGrid<TreeNode>(TreeNode.class);
		nodeTreeGrid.setTreeData(odometerDeviceService.getTreeData());
		nodeTreeGrid.setHeight("100%");
		nodeTreeGrid.addColumn(entity -> {
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
		nodeTreeGrid.setColumns("entity","serialNum");
		nodeTreeGrid.getColumn("serialNum").setCaption("Serial");
		nodeTreeGrid.setHierarchyColumn("entity");
		
		createEntity = new Button("Add Entity");
		createEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		
		editEntity = new Button("Edit Entity");
		editEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		
		deleteEntity = new Button("Delete Entity");
		deleteEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		
		copyEntity = new Button("Copy Entity");
		copyEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		
		pasteEntity = new Button("Paste Entity");
		pasteEntity.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		pasteEntity.setEnabled(false);
		
		ContextMenuWindow odometerTreeGridMenu = new ContextMenuWindow();
		odometerTreeGridMenu.addMenuItems(createEntity,editEntity,deleteEntity, copyEntity, pasteEntity);
		nodeTreeGrid.addContextClickListener(click->{
			UI.getCurrent().getWindows().forEach(Window::close);
			if(click.getClientY() > 750) {
				odometerTreeGridMenu.setPosition(click.getClientX(), click.getClientY()-220);
			}else {
				odometerTreeGridMenu.setPosition(click.getClientX(), click.getClientY());
			}
			UI.getCurrent().addWindow(odometerTreeGridMenu);
		});
		EntityOperations.entityOperations(nodeTreeGrid, createEntity, editEntity, deleteEntity, copyEntity, pasteEntity, treeDataNodeService, odometerDeviceService, odometerTreeGridMenu);
		treeSearchPanelLayout.addComponent(nodeTreeGrid);
		treeSearchPanelLayout.setExpandRatio(nodeTreeGrid, 14);
		treeSearchPanelLayout.setMargin(true);
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
		} else if(width>=700 && width<=1400) {
			tabMode();
			splitScreen.setSplitPosition(30);
		}
		else {
			desktopMode();
			splitScreen.setSplitPosition(20);
		}
		
		if(width<=600) {
			odometerStartDateField.setHeight("28px");
			odometerEndDateField.setHeight("28px");
			treeNodeSearch.setHeight(28, Unit.PIXELS);
			odometerDeviceSearch.setHeight(28, Unit.PIXELS);
			clearOdometerSearch.removeStyleNames("v-button-customstyle", "audit-AuditSearchClearDesktop");
			clearOdometerSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"odometer-OdometerSearchClearPhone");
			mainView.getTitle().setValue(userService.getLoggedInUserName());
			odometerEndDateField.setWidth("100%");
			MainViewIconsLoad.iconsOnPhoneMode(mainView);
		} else if(width>600 && width<=1000){
			odometerStartDateField.setHeight("32px");
			odometerEndDateField.setHeight("32px");
			treeNodeSearch.setHeight(32, Unit.PIXELS);
			odometerDeviceSearch.setHeight(32, Unit.PIXELS);
			clearOdometerSearch.removeStyleNames("audit-AuditSearchClearDesktop", "audit-AuditSearchClearPhone");
			clearOdometerSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
			mainView.getTitle().setValue("gemView");
			odometerEndDateField.setWidth("100%");
			MainViewIconsLoad.iconsOnTabMode(mainView);
		}else {
			odometerStartDateField.setHeight("100%");
			odometerEndDateField.setHeight("100%");
			treeNodeSearch.setHeight(37, Unit.PIXELS);
			odometerDeviceSearch.setHeight(37, Unit.PIXELS);
			mainView.getTitle().setValue("gemView");
			clearOdometerSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
			clearOdometerSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"audit-AuditSearchClearDesktop");
			addComponentAsFirst(header);
			splitScreen.setSplitPosition(30);
			odometerEndDateField.setWidth("94%");
			MainViewIconsLoad.noIconsOnDesktopMode(mainView);
		}
		
		Permission appStorePermission = roleService.getLoggedInUserRolePermissions().stream().filter(per -> per.getPageName().equals("ODOMETER")).findFirst().get();
			disableAllComponents();
		deleteRow = appStorePermission.getDelete();
			allowAccessBasedOnPermission(appStorePermission.getAdd(),appStorePermission.getEdit(),appStorePermission.getDelete());
			
			UI.getCurrent().addShortcutListener(new ShortcutListener("", KeyCode.ENTER, null) {
				
				@Override
				public void handleAction(Object sender, Object target) {
					treeNodeSearch.focus();
					
				}
			});
			
		} catch(Exception ex) {
			odometerDeviceService.logOdometerScreenErrors(ex);
		}
		
		mainView.getDeviceOdometer().setEnabled(true);
	}

	private void disableAllComponents() throws Exception {
		deleteOdometerGrid.setEnabled(false);
	}

	private void allowAccessBasedOnPermission(Boolean addBoolean, Boolean editBoolean, Boolean deleteBoolean) {
		deleteOdometerGrid.setEnabled(deleteBoolean);
	}

	public Panel getAndLoadOdometerPanel() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setResponsive(true);
		panel.setSizeFull();
		addComponent(header);
		addComponent(panel);
		setExpandRatio(header, 1);
		setExpandRatio(panel, 11);
		return panel;
	}

	private void tabMode() {
		optionsLayoutVerticalTab.addStyleName("audit-tabAlignment");
		dateDeleteLayout.addComponent(odometerStartDateField);
		dateDeleteLayout.setComponentAlignment(odometerStartDateField, Alignment.TOP_RIGHT);
		dateDeleteLayout.addComponent(odometerEndDateField);
		dateDeleteLayout.setComponentAlignment(odometerEndDateField, Alignment.TOP_RIGHT);
		optionsLayoutHorizontalDesktop.addComponent(dateDeleteLayout);
		optionsLayoutVerticalTab.addComponents(odometerSearchLayout, dateDeleteLayout);
		
		optionsLayoutHorizontalDesktop.setVisible(false);
		optionsLayoutVerticalPhone.setVisible(false);
		optionsLayoutVerticalTab.setVisible(true);
	}

	private void phoneMode() {
		dateDeleteLayoutPhone.addComponents(odometerStartDateField, odometerEndDateField);
		optionsLayoutVerticalPhone.addStyleName("audit-phoneAlignment");
		odometerSearchLayout.addStyleName("audit-phoneAlignment");
		optionsLayoutVerticalPhone.addComponents(odometerSearchLayout, dateDeleteLayoutPhone);
		optionsLayoutVerticalPhone.setVisible(true);
		optionsLayoutHorizontalDesktop.setVisible(false);
		optionsLayoutVerticalTab.setVisible(false);
	}

	private void desktopMode() {
		optionsLayoutHorizontalDesktop.addComponent(odometerSearchLayout);
		optionsLayoutHorizontalDesktop.setComponentAlignment(odometerSearchLayout, Alignment.MIDDLE_LEFT);
		dateDeleteLayout.addComponent(odometerStartDateField);
		dateDeleteLayout.addComponent(odometerEndDateField);
		optionsLayoutHorizontalDesktop.addComponent(dateDeleteLayout);
		optionsLayoutHorizontalDesktop.setVisible(true);
		optionsLayoutVerticalTab.setVisible(false);
		optionsLayoutVerticalPhone.setVisible(false);
		
		
	}

	private void clearCalenderDates() {
		odometerStartDateField.clear();
		odometerEndDateField.clear();
	}

	// Search on Tree by using UI logic. May be useful in future.

	private void configureTreeNodeSearch() {
		treeNodeSearch.addValueChangeListener(changed -> {
			if (changed.getValue().length() == 50) {
				Notification search = Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
				search.addCloseListener(listener -> {
					String valueInLower = changed.getValue().toLowerCase();
					if (!valueInLower.isEmpty() && valueInLower != null) {
						nodeTreeGrid.setTreeData(treeDataNodeService.searchTreeData(valueInLower));
					} else {
						nodeTreeGrid.setTreeData(treeDataNodeService.getTreeData());
					}
				});
			} else {
				String valueInLower = changed.getValue().toLowerCase();
				if (!valueInLower.isEmpty() && valueInLower != null) {
					nodeTreeGrid.setTreeData(treeDataNodeService.searchTreeData(valueInLower));
				} else {
					nodeTreeGrid.setTreeData(treeDataNodeService.getTreeData());
				}
			}
		});

		treeNodeSearch.addShortcutListener(new ShortcutListener("Clear", KeyCode.ESCAPE, null) {

			@Override
			public void handleAction(Object sender, Object target) {
				if (target == treeNodeSearch) {
					treeNodeSearch.clear();
				}
			}
		});
		clearSearch.addClickListener(click -> treeNodeSearch.clear());
	}

	private void confirmDialog(Set<DeviceOdometer> odometerList, String entityId, String type) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?", "Ok", "Cancel",
				new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							// Confirmed to continue
							for(DeviceOdometer odometer : odometerList) {
								odometerDeviceService.deleteGridData(odometer.getId());
							}
							List<DeviceOdometer> odometerList = odometerDeviceService.getOdometerGridData(entityId,
									type);
							DataProvider data = new ListDataProvider(odometerList);
							odometerDeviceGrid.setDataProvider(data);
							nodeTreeGrid.getDataProvider().refreshAll();
							odometerDeviceSearch.clear();
							clearCalenderDates();

						} else {
							// User did not confirm

						}
					}
				});
	}

	@SuppressWarnings("unchecked")
	private VerticalLayout getOdometerDeviceLayout() {
		odometerDeviceLayout = new VerticalLayout();
		odometerDeviceLayout.addStyleName(ValoTheme.LAYOUT_CARD);

		odometerDeviceLayout.setWidth("100%");
		odometerDeviceLayout.setHeight("100%");
		odometerDeviceLayout.addStyleName("audit-DeviceVerticalAlignment");
		odometerDeviceLayout.setResponsive(true);
		odometerDeviceGrid = new Grid<>(DeviceOdometer.class);
		odometerDeviceGrid.setWidth("100%");
		odometerDeviceGrid.setHeight("97%");
		odometerDeviceGrid.addStyleName("grid-AuditOdometerAlignment");
		odometerDeviceGrid.setResponsive(true);
		odometerDeviceGrid.setSelectionMode(SelectionMode.MULTI);
		odometerDeviceGrid.setColumns("statusType", "description", "statistics");
		
		deleteOdometerGrid = new Button("Delete Record/s", click -> {
			deleteContextWindow.close();
			Set<DeviceOdometer> odometerList = odometerDeviceGrid.getSelectedItems();
			
			if (odometerDeviceGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.AUDIT_DELETE, Notification.Type.ERROR_MESSAGE);
			} else {
				confirmDialog(odometerList,
						nodeTreeGrid.getSelectedItems().iterator().next().getEntityId(),
						nodeTreeGrid.getSelectedItems().iterator().next().getType().name());
			}
		});
		deleteOdometerGrid.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		deleteOdometerGrid.setEnabled(false);
		
		deleteContextWindow = new ContextMenuWindow();
		deleteContextWindow.addMenuItems(deleteOdometerGrid);
		odometerDeviceGrid.addContextClickListener(click->{
			if(odometerDeviceGrid.getSelectedItems().size()>0) {
				deleteOdometerGrid.setEnabled(deleteRow);
			}else {
				deleteOdometerGrid.setEnabled(false);
			}
	
			UI.getCurrent().getWindows().forEach(Window::close);
			deleteContextWindow.setPosition(click.getClientX(), click.getClientY());
			UI.getCurrent().addWindow(deleteContextWindow);
			
		});
		
		UI.getCurrent().addClickListener(listener->{
			if(deleteContextWindow!=null) {
				deleteContextWindow.close();
			}
		});
		
		odometerDeviceSearch = new TextField();
		odometerDeviceSearch.setWidth("100%");
		odometerDeviceSearch.setIcon(VaadinIcons.SEARCH);
		odometerDeviceSearch.setStyleName("small inline-icon search");
		odometerDeviceSearch.setHeight(37, Unit.PIXELS);
		odometerDeviceSearch.addStyleName("v-textfield-font");
		odometerDeviceSearch.setPlaceholder("Search");
		odometerDeviceSearch.setResponsive(true);
		odometerDeviceSearch.setMaxLength(50);
		odometerDeviceSearch.addShortcutListener(new ShortcutListener("Clear", KeyCode.ESCAPE, null) {

			@Override
			public void handleAction(Object sender, Object target) {
				if (target == odometerDeviceSearch) {
					odometerDeviceSearch.clear();
				}

			}
		});
		odometerDeviceSearch.addValueChangeListener(valueChange -> {
			// Search on Grid by using UI logic. May be useful in future.

			/*
			 * String valueInLower = valueChange.getValue().toLowerCase();
			 * ListDataProvider<Devices> odometerDeviceDataProvider =
			 * (ListDataProvider<Devices>) odometerDeviceGrid.getDataProvider();
			 * odometerDeviceDataProvider.setFilter(filter -> { String descriptionInLower =
			 * filter.getDescription().toLowerCase(); String typeInLower =
			 * filter.getStatusType().name().toLowerCase(); String statistics =
			 * filter.getStatistics().toString().toLowerCase(); return
			 * ((typeInLower.contains(valueInLower)) ||
			 * (descriptionInLower.contains(valueInLower)) ||
			 * (statistics.contains(valueInLower))); });
			 */
			if (valueChange.getValue().length() == 50) {
				Notification search = Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
				search.addCloseListener(listener -> {
					if (valueChange.getValue() != "" && valueChange.getValue() != null) {
						String filter = odometerDeviceSearch.getValue();
						String endDate = null;
						String startDate = null;
						if (odometerEndDateField.getValue() != null && odometerStartDateField.getValue() != null) {
							endDate = odometerEndDateField.getValue().format(dateFormatter1);
							startDate = odometerStartDateField.getValue().format(dateFormatter1);
						}
						List<DeviceOdometer> searchGridData = odometerDeviceService.searchOdometerGridData(
								nodeTreeGrid.getSelectedItems().iterator().next().getEntityId(), filter, startDate,
								endDate);
						DataProvider data = new ListDataProvider(searchGridData);
						odometerDeviceGrid.setDataProvider(data);
					}
				});
			} else {
				if (valueChange.getValue() != null) {
					String filter = odometerDeviceSearch.getValue();
					String endDate = null;
					String startDate = null;
					if (odometerEndDateField.getValue() != null && odometerStartDateField.getValue() != null) {
						endDate = odometerEndDateField.getValue().format(dateFormatter1);
						startDate = odometerStartDateField.getValue().format(dateFormatter1);
					}
					List<DeviceOdometer> searchGridData = odometerDeviceService.searchOdometerGridData(
							nodeTreeGrid.getSelectedItems().iterator().next().getEntityId(), filter, startDate, endDate);
					DataProvider data = new ListDataProvider(searchGridData);
					odometerDeviceGrid.setDataProvider(data);
				}
			}
		});
		
		deviceOdometerSearchLayout = new CssLayout();
		deviceOdometerSearchLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		deviceOdometerSearchLayout.setWidth("90%");
		
		clearOdometerSearch = new Button(VaadinIcons.CLOSE);
		clearOdometerSearch.addClickListener(listener->{
			odometerDeviceSearch.clear();
		});
		deviceOdometerSearchLayout.addComponents(odometerDeviceSearch, clearOdometerSearch);

		optionsLayoutHorizontalDesktop = new HorizontalLayout();
		optionsLayoutHorizontalDesktop.setWidth("100%");
		optionsLayoutHorizontalDesktop.setResponsive(true);
		optionsLayoutHorizontalDesktop.addStyleName("audit-DeviceSearch");

		odometerSearchLayout = new HorizontalLayout();
		odometerSearchLayout.setWidth("100%");
		odometerSearchLayout.addComponent(deviceOdometerSearchLayout);
		odometerSearchLayout.setComponentAlignment(deviceOdometerSearchLayout, Alignment.TOP_LEFT);

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
		odometerEndDateField.setWidth("95%");
		odometerEndDateField.setHeight("100%");
		odometerEndDateField.setPlaceholder("End Date");
		odometerEndDateField.setResponsive(true);
		odometerEndDateField.setDateFormat(DATE_FORMAT);
		odometerEndDateField.setDescription("End Date");
		odometerEndDateField.addStyleName("v-textfield-font");

		// Vertical Initialization
		optionsLayoutVerticalTab = new VerticalLayout();
		optionsLayoutVerticalTab.setVisible(false);
		optionsLayoutVerticalTab.addStyleName("audit-DeviceSearch");

		// Vertical Phone Mode
		optionsLayoutVerticalPhone = new VerticalLayout();
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
		dateDeleteLayout.addComponent(odometerEndDateField);
		optionsLayoutHorizontalDesktop.addComponent(dateDeleteLayout);

		odometerDeviceLayout.addComponent(optionsLayoutHorizontalDesktop);
		odometerDeviceLayout.setComponentAlignment(optionsLayoutHorizontalDesktop, Alignment.TOP_LEFT);
		odometerDeviceLayout.addComponent(optionsLayoutVerticalTab);
		odometerDeviceLayout.setComponentAlignment(optionsLayoutVerticalTab, Alignment.TOP_LEFT);
		odometerDeviceLayout.addComponent(optionsLayoutVerticalPhone);
		odometerDeviceLayout.setComponentAlignment(optionsLayoutVerticalPhone, Alignment.TOP_LEFT);
		odometerDeviceLayout.addComponent(odometerDeviceGrid);
		odometerDeviceLayout.setExpandRatio(odometerDeviceGrid, 2);

		// end Date listner
		odometerEndDateField.addValueChangeListener(change -> {
			if (!(change.getValue() == null)) {
				if (!nodeTreeGrid.getSelectedItems().isEmpty()) {
					if (odometerStartDateField.getValue() != null) {
						if (change.getValue().compareTo(odometerStartDateField.getValue()) > 0) {
							String endDate = odometerEndDateField.getValue().format(dateFormatter1);
							String startDate = odometerStartDateField.getValue().format(dateFormatter1);
							String filter = odometerDeviceSearch.getValue();
							List<DeviceOdometer> odometerListFilterBydates = new ArrayList<>();
							List<HeartbeatOdometer> odometerList;
							odometerList = odometerDeviceService.searchByDates(
									nodeTreeGrid.getSelectedItems().iterator().next().getEntityId(), filter, startDate,
									endDate);
							for (HeartbeatOdometer heartBeatOdometer : odometerList) {
								DeviceOdometer deviceOdometer = new DeviceOdometer(heartBeatOdometer.getId(),
										heartBeatOdometer.getLabel(), heartBeatOdometer.getDescription(),
										heartBeatOdometer.getValue());
								odometerListFilterBydates.add(deviceOdometer);
							}
							DataProvider data = new ListDataProvider(odometerListFilterBydates);
							odometerDeviceGrid.setDataProvider(data);
						} else {
							Notification.show(NotificationUtil.AUDIT_SAMEDATE, Notification.Type.ERROR_MESSAGE);
							odometerStartDateField.clear();
							odometerEndDateField.clear();
						}
					} else {
						Notification.show(NotificationUtil.ODOMETER_STARTDATE, Notification.Type.ERROR_MESSAGE);
						odometerEndDateField.clear();
					}
				} else {
					Notification.show(NotificationUtil.ODOMETER_SELECTNODE, Notification.Type.ERROR_MESSAGE);
					clearCalenderDates();
				}
			}
		});
		odometerStartDateField.addValueChangeListener(change -> {
			if (change.getValue() != null) {
				odometerEndDateField.clear();
				odometerEndDateField.setRangeStart(change.getValue().plusDays(1));
			} else {
				odometerDeviceSearch.clear();
				odometerEndDateField.clear();
				List<DeviceOdometer> odometerListNew;
				if (nodeTreeGrid.getSelectedItems().size() > 0) {
					odometerListNew = odometerDeviceService.getOdometerGridData(
							nodeTreeGrid.getSelectedItems().iterator().next().getEntityId(),
							nodeTreeGrid.getSelectedItems().iterator().next().getType().name());
					DataProvider data = new ListDataProvider(odometerListNew);
					odometerDeviceGrid.setDataProvider(data);
				}

			}
		});

		nodeTreeGrid.addSelectionListener(selection -> {
			if (!selection.getFirstSelectedItem().isPresent()) {
				List<DeviceOdometer> auditListNew = new ArrayList<>();
				DataProvider data = new ListDataProvider(auditListNew);
				odometerDeviceGrid.setDataProvider(data);
			} else {
				List<DeviceOdometer> odometerListNew = odometerDeviceService.getOdometerGridData(
						selection.getFirstSelectedItem().get().getEntityId(),
						selection.getFirstSelectedItem().get().getType().name());
				DataProvider data = new ListDataProvider(odometerListNew);
				odometerDeviceGrid.setDataProvider(data);

			}
			odometerDeviceSearch.clear();
			clearCalenderDates();
		});
		return odometerDeviceLayout;
	}
}
