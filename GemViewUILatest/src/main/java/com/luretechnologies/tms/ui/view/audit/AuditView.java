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
import java.text.SimpleDateFormat;
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

import com.luretechnologies.client.restlib.service.model.AuditUserLog;
import com.luretechnologies.tms.backend.data.entity.Audit;
import com.luretechnologies.tms.backend.data.entity.Permission;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.service.AuditService;
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

@SpringView(name = AuditView.VIEW_NAME)
public class AuditView extends VerticalLayout implements Serializable, View {

	private static final String DATE_FORMAT = "MM/dd/yyyy";
	private static final DateTimeFormatter dateFormatter1 = DateTimeFormatter.ofPattern("yyMMdd");
	private static final long serialVersionUID = -7983511214106963682L;
	public static final String VIEW_NAME = "audit";
	private static final LocalDateTime localTimeNow = LocalDateTime.now();
	private static Grid<Audit> debugGrid;
	private static TreeGrid<TreeNode> nodeTreeGrid;
	private static Button search, clearSearch, clearAuditSearch;
	private static TextField treeNodeSearch, debugSearch;
	private static HorizontalSplitPanel splitScreen;
	private static DateField debugStartDateField, debugEndDateField;
	private static VerticalLayout debugLayoutFull;
	private static HorizontalLayout optionsLayoutHorizontalDesktop;
	private static VerticalLayout optionsLayoutVerticalTab;
	private static VerticalLayout optionsLayoutVerticalPhone;
	private static HorizontalLayout debugSearchLayout,header;
	private static HorizontalLayout dateDeleteLayout;
	private static VerticalLayout dateDeleteLayoutPhone;
	private static CssLayout auditSearchLayout;
	private static final String error = "error";
	private static final String warn = "warn";
	private static final String info = "info";
	private static final String debug = "debug";
	private ContextMenuWindow deleteContextWindow;
	private Button deleteAuditGrid;
	private boolean deleteRow, addEntity, updateEntity, accessEntity, removeEntity;

	private  Button createEntity, editEntity, deleteEntity, copyEntity, pasteEntity;
	
	@Autowired
	public AuditView() {

	}

	@Autowired
	private RolesService roleService;

	@Autowired
	public AuditService auditService;

	@Autowired
	public MainView mainView;

	@Autowired
	UserService userService;

	@Autowired
	TreeDataNodeService treeDataNodeService;
	
	@Autowired
	NavigationManager navigationManager;

	@PostConstruct
	private void init() {
		try {
			header = new Header(userService, roleService, navigationManager, "Audit", new Label());
			Page.getCurrent().addBrowserWindowResizeListener(r -> {
				System.out.println("Height " + r.getHeight() + "Width:  " + r.getWidth() + " in pixel");
				if (r.getWidth() <= 1400 && r.getWidth() >= 700) {
					tabMode();
					splitScreen.setSplitPosition(30);
				} else if (r.getWidth() <= 699 && r.getWidth() > 0) {
					phoneMode();

				} else {
					desktopMode();
				}

				if (r.getWidth() <= 600) {
					debugStartDateField.setHeight("28px");
					debugEndDateField.setHeight("28px");
					treeNodeSearch.setHeight(28, Unit.PIXELS);
					debugSearch.setHeight(28, Unit.PIXELS);
					clearAuditSearch.removeStyleNames("v-button-customstyle", "audit-AuditSearchClearDesktop");
					clearAuditSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "audit-AuditSearchClearPhone");
					mainView.getTitle().setValue(userService.getLoggedInUserName());
					removeComponent(header);
					debugEndDateField.setWidth("100%");
					splitScreen.setSplitPosition(20);
					MainViewIconsLoad.iconsOnPhoneMode(mainView);
				} else if (r.getWidth() > 600 && r.getWidth() <= 1000) {
					debugStartDateField.setHeight("32px");
					debugEndDateField.setHeight("32px");
					treeNodeSearch.setHeight(32, Unit.PIXELS);
					debugSearch.setHeight(32, Unit.PIXELS);
					clearAuditSearch.removeStyleNames("audit-AuditSearchClearDesktop", "audit-AuditSearchClearPhone");
					clearAuditSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
					mainView.getTitle().setValue("gemView");
					addComponentAsFirst(header);
					debugEndDateField.setWidth("100%");
					MainViewIconsLoad.iconsOnTabMode(mainView);
				} else {
					debugStartDateField.setHeight("100%");
					debugEndDateField.setHeight("100%");
					treeNodeSearch.setHeight(37, Unit.PIXELS);
					debugSearch.setHeight(37, Unit.PIXELS);
					clearAuditSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
					clearAuditSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "audit-AuditSearchClearDesktop");
					mainView.getTitle().setValue("gemView");
					addComponentAsFirst(header);
					splitScreen.setSplitPosition(30);
					debugEndDateField.setWidth("94%");
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
			treeNodeSearch.addStyleName("v-textfield-font");
			treeNodeSearch.setPlaceholder("Search");
			treeNodeSearch.setMaxLength(50);
			clearSearch = new Button(VaadinIcons.CLOSE);
			clearSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
			configureTreeNodeSearch();
			
			Permission EntityPermission = roleService.getLoggedInUserRolePermissions().stream()
					.filter(per -> per.getPageName().equals("ENTITY")).findFirst().get();
			addEntity = EntityPermission.getAdd();
			updateEntity = EntityPermission.getEdit();
			accessEntity = EntityPermission.getAccess();
			removeEntity = EntityPermission.getDelete();

			Panel panel = getAndLoadAuditPanel();
			VerticalLayout verticalPanelLayout = new VerticalLayout();
			verticalPanelLayout.setHeight("100%");
			verticalPanelLayout.setStyleName("split-height");
			CssLayout searchLayout = new CssLayout();
			searchLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
			searchLayout.addComponents(treeNodeSearch, clearSearch);
			searchLayout.setWidth("85%");
			VerticalLayout treeSearchPanelLayout = new VerticalLayout(searchLayout);
			treeSearchPanelLayout.setHeight("100%");
			nodeTreeGrid = new TreeGrid<TreeNode>(TreeNode.class);
			nodeTreeGrid.setHeight("100%");
			nodeTreeGrid.setTreeData(auditService.getTreeData());
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
			
			ContextMenuWindow auditTreeGridMenu = new ContextMenuWindow();
			auditTreeGridMenu.addMenuItems(createEntity,editEntity,deleteEntity, copyEntity, pasteEntity);
			nodeTreeGrid.addContextClickListener(click->{
				UI.getCurrent().getWindows().forEach(Window::close);
				if(click.getClientY() > 750) {
					auditTreeGridMenu.setPosition(click.getClientX(), click.getClientY()-220);
				}else {
					auditTreeGridMenu.setPosition(click.getClientX(), click.getClientY());
				}
				UI.getCurrent().addWindow(auditTreeGridMenu);
			});
			EntityOperations.entityOperations(nodeTreeGrid, createEntity, editEntity, deleteEntity, copyEntity, pasteEntity, treeDataNodeService, auditService, auditTreeGridMenu);
			treeSearchPanelLayout.addComponent(nodeTreeGrid);
			treeSearchPanelLayout.setExpandRatio(nodeTreeGrid, 14);
			treeSearchPanelLayout.setMargin(true);
			treeSearchPanelLayout.setStyleName("split-Height-ButtonLayout");
			verticalPanelLayout.addComponent(treeSearchPanelLayout);
			splitScreen = new HorizontalSplitPanel();
			splitScreen.setFirstComponent(verticalPanelLayout);
			splitScreen.setSplitPosition(20);
			splitScreen.addComponent(getDebugLayout());
			splitScreen.setHeight("100%");
			panel.setContent(splitScreen);
			int width = Page.getCurrent().getBrowserWindowWidth();
			if (width > 0 && width <= 699) {
				phoneMode();
				splitScreen.setSplitPosition(35);
			} else if (width >= 700 && width <= 1400) {
				tabMode();
			} else {
				desktopMode();
			}

			if (width <= 600) {
				debugStartDateField.setHeight("28px");
				debugEndDateField.setHeight("28px");
				treeNodeSearch.setHeight(28, Unit.PIXELS);
				debugSearch.setHeight(28, Unit.PIXELS);
				clearAuditSearch.removeStyleNames("v-button-customstyle", "audit-AuditSearchClearDesktop");
				clearAuditSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "audit-AuditSearchClearPhone");
				mainView.getTitle().setValue(userService.getLoggedInUserName());
				removeComponent(header);
				debugEndDateField.setWidth("100%");
				splitScreen.setSplitPosition(20);
				MainViewIconsLoad.iconsOnPhoneMode(mainView);
			} else if (width > 600 && width <= 1000) {
				debugStartDateField.setHeight("32px");
				debugEndDateField.setHeight("32px");
				treeNodeSearch.setHeight(32, Unit.PIXELS);
				debugSearch.setHeight(32, Unit.PIXELS);
				clearAuditSearch.removeStyleNames("audit-AuditSearchClearDesktop", "audit-AuditSearchClearPhone");
				clearAuditSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
				mainView.getTitle().setValue("gemView");
				addComponentAsFirst(header);
				debugEndDateField.setWidth("100%");
				MainViewIconsLoad.iconsOnTabMode(mainView);
			} else {
				debugStartDateField.setHeight("100%");
				debugEndDateField.setHeight("100%");
				treeNodeSearch.setHeight(37, Unit.PIXELS);
				debugSearch.setHeight(37, Unit.PIXELS);
				clearAuditSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
				clearAuditSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "audit-AuditSearchClearDesktop");
				mainView.getTitle().setValue("gemView");
				addComponentAsFirst(header);
				splitScreen.setSplitPosition(30);
				debugEndDateField.setWidth("94%");
				MainViewIconsLoad.noIconsOnDesktopMode(mainView);
				
			}

			Permission appStorePermission = roleService.getLoggedInUserRolePermissions().stream()
					.filter(per -> per.getPageName().equals("AUDIT")).findFirst().get();
			disableAllComponents();
			deleteRow = appStorePermission.getDelete();
			allowAccessBasedOnPermission(appStorePermission.getAdd(), appStorePermission.getEdit(),
					deleteRow);
			
			this.getUI().getCurrent().addShortcutListener(new ShortcutListener("", KeyCode.ENTER, null) {
				
				@Override
				public void handleAction(Object sender, Object target) {
					treeNodeSearch.focus();
					
				}
			});
			
		} catch (Exception ex) {
			auditService.logAuditScreenErrors(ex);
		}
		
		mainView.getAudit().setEnabled(true);
	}

	private void disableAllComponents() throws Exception {
		deleteAuditGrid.setEnabled(false);
	}

	private void allowAccessBasedOnPermission(Boolean addBoolean, Boolean editBoolean, Boolean deleteBoolean) {
		deleteAuditGrid.setEnabled(deleteBoolean);
	}

	private void tabMode() {
		optionsLayoutVerticalTab.addStyleName("audit-tabAlignment");
		dateDeleteLayout.addComponent(debugStartDateField);
		dateDeleteLayout.setComponentAlignment(debugStartDateField, Alignment.TOP_RIGHT);
		dateDeleteLayout.addComponent(debugEndDateField);
		dateDeleteLayout.setComponentAlignment(debugEndDateField, Alignment.TOP_RIGHT);
		optionsLayoutVerticalTab.addComponents(debugSearchLayout, dateDeleteLayout);

		optionsLayoutHorizontalDesktop.setVisible(false);
		optionsLayoutVerticalPhone.setVisible(false);
		optionsLayoutVerticalTab.setVisible(true);
	}

	private void phoneMode() {
		dateDeleteLayoutPhone.addComponents(debugStartDateField, debugEndDateField);
		optionsLayoutVerticalPhone.addStyleName("audit-phoneAlignment");
		debugSearchLayout.addStyleName("audit-phoneAlignment");
		optionsLayoutVerticalPhone.addComponents(debugSearchLayout, dateDeleteLayoutPhone);
		optionsLayoutVerticalPhone.setVisible(true);
		optionsLayoutHorizontalDesktop.setVisible(false);
		optionsLayoutVerticalTab.setVisible(false);
	}

	private void desktopMode() {
		optionsLayoutHorizontalDesktop.addComponent(debugSearchLayout);
		optionsLayoutHorizontalDesktop.setComponentAlignment(debugSearchLayout, Alignment.MIDDLE_LEFT);
		dateDeleteLayout.addComponent(debugStartDateField);
		dateDeleteLayout.addComponent(debugEndDateField);
		optionsLayoutHorizontalDesktop.addComponent(dateDeleteLayout);
		optionsLayoutHorizontalDesktop.setVisible(true);
		optionsLayoutVerticalTab.setVisible(false);
		optionsLayoutVerticalPhone.setVisible(false);
	}

	public Panel getAndLoadAuditPanel() {
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

	private void clearCalenderDates() {
		debugStartDateField.clear();
		debugEndDateField.clear();
	}

	private void confirmDialog(Set<Audit> auditList, TreeNode treeNode) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?", "Ok", "Cancel",
				new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							// Confirmed to continue
							for(Audit audit: auditList) {
								auditService.deleteGridData(audit.getId());
							}
							List<Audit> auditListNew = auditService.auditGridData(treeNode.getEntityId());
							DataProvider data = new ListDataProvider(auditListNew);
							debugGrid.setDataProvider(data);
							nodeTreeGrid.getDataProvider().refreshAll();
							debugSearch.clear();
							clearCalenderDates();
						} else {
							// User did not confirm

						}
					}
				});
	}


	@SuppressWarnings("serial")
	private VerticalLayout getDebugLayout() {
		debugLayoutFull = new VerticalLayout();
		debugLayoutFull.addStyleName(ValoTheme.LAYOUT_CARD);
		debugLayoutFull.setWidth("100%");
		debugLayoutFull.setHeight("100%");
		debugLayoutFull.addStyleName("audit-DeviceVerticalAlignment");
		debugLayoutFull.setResponsive(true);
		debugGrid = new Grid<>(Audit.class);
		debugGrid.setWidth("100%");
		debugGrid.setHeight("97%");
		debugGrid.addStyleName("grid-AuditOdometerAlignment");
		debugGrid.setResponsive(true);
		debugGrid.setSelectionMode(SelectionMode.MULTI);
		debugGrid.setColumns("type", "description", "dateTime");
		debugGrid.getColumn("dateTime").setCaption("Date Time");
		debugGrid.getColumn("type").setCaption("Debug Type").setStyleGenerator(style -> {
			switch (style.getType()) {
			case error:
				return "error";
			case warn:
				return "warn";
			case info:
				return "info";
			case debug:
				return "debug";

			default:
				return "";
			}
		});
		
		deleteAuditGrid = new Button("Delete Record/s", click -> {
			deleteContextWindow.close();
			Set<Audit> auditList = debugGrid.getSelectedItems();
			
			if (debugGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.AUDIT_DELETE, Notification.Type.ERROR_MESSAGE);
			} else {
				confirmDialog(auditList,
						nodeTreeGrid.getSelectedItems().iterator().next());
			}
		});
		deleteAuditGrid.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		deleteAuditGrid.setEnabled(false);
		
		deleteContextWindow = new ContextMenuWindow();
		deleteContextWindow.addMenuItems(deleteAuditGrid);
		debugGrid.addContextClickListener(click->{
			if(debugGrid.getSelectedItems().size()>0) {
				deleteAuditGrid.setEnabled(deleteRow);
			}else {
				deleteAuditGrid.setEnabled(false);
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

		debugSearch = new TextField();
		debugSearch.setWidth("100%");
		debugSearch.setIcon(VaadinIcons.SEARCH);
		debugSearch.setStyleName("small inline-icon search");
		debugSearch.setPlaceholder("Search");
		debugSearch.setResponsive(true);
		debugSearch.setMaxLength(50);
		debugSearch.addShortcutListener(new ShortcutListener("Clear", KeyCode.ESCAPE, null) {

			@Override
			public void handleAction(Object sender, Object target) {
				if (target == debugSearch) {
					debugSearch.clear();
				}

			}
		});
		debugSearch.addValueChangeListener(valueChange -> {
			if (valueChange.getValue().length() == 50) {
				Notification search = Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
				search.addCloseListener(listener -> {
					if (valueChange.getValue() != "" && valueChange.getValue() != null) {
						String filter = debugSearch.getValue();
						String endDate = null;
						String startDate = null;
						if (debugEndDateField.getValue() != null && debugStartDateField.getValue() != null) {
							endDate = debugEndDateField.getValue().format(dateFormatter1);
							startDate = debugStartDateField.getValue().format(dateFormatter1);
						}
						String entityId = nodeTreeGrid.getSelectedItems().iterator().next().getEntityId();
						List<AuditUserLog> searchGridData = auditService.searchGridData(filter, entityId, startDate, endDate);
						List<Audit> auditListSearch = new ArrayList<>();
						for (AuditUserLog auditUserLog : searchGridData) {
							Audit audit = new Audit(auditUserLog.getId(), auditUserLog.getAuditUserLogType().getName(),
									auditUserLog.getDescription(), auditUserLog.getDateAt().toString());
							auditListSearch.add(audit);
						}
						DataProvider data = new ListDataProvider(auditListSearch);
						debugGrid.setDataProvider(data);
					}
				});
			} else {
				if (valueChange.getValue() != null) {
					String filter = debugSearch.getValue();
					String endDate = null;
					String startDate = null;
					if (debugEndDateField.getValue() != null && debugStartDateField.getValue() != null) {
						endDate = debugEndDateField.getValue().format(dateFormatter1);
						startDate = debugStartDateField.getValue().format(dateFormatter1);
					}
					String entityId = nodeTreeGrid.getSelectedItems().iterator().next().getEntityId();
					List<AuditUserLog> searchGridData = auditService.searchGridData(filter, entityId, startDate, endDate);
					List<Audit> auditListSearch = new ArrayList<>();
					for (AuditUserLog auditUserLog : searchGridData) {
						Audit audit = new Audit(auditUserLog.getId(), auditUserLog.getAuditUserLogType().getName(),
								auditUserLog.getDescription(), auditUserLog.getDateAt().toString());
						auditListSearch.add(audit);
					}
					DataProvider data = new ListDataProvider(auditListSearch);
					debugGrid.setDataProvider(data);
				}
			}

			//Search on UI side.valueChange Useful for future
			/*
			 * String valueInLower = valueChange.getValue().toLowerCase();
			 * ListDataProvider<Audit> debugDataProvider = (ListDataProvider<Audit>)
			 * debugGrid.getDataProvider(); debugDataProvider.setFilter(filter -> { String
			 * descriptionInLower = filter.getDescription().toLowerCase(); String
			 * typeInLower = filter.getType().toLowerCase(); String dateTime =
			 * filter.getDateTime().toLowerCase();
			 * //RestServiceUtil.getInstance().getClient().getOrganizationApi(). return
			 * typeInLower.equals(valueInLower) || descriptionInLower.contains(valueInLower)
			 * || dateTime.contains(valueInLower); });
			 */
		});
		auditSearchLayout = new CssLayout();
		auditSearchLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		auditSearchLayout.setWidth("90%");

		clearAuditSearch = new Button(VaadinIcons.CLOSE);
		clearAuditSearch.addClickListener(listener -> {
			debugSearch.clear();
		});
		auditSearchLayout.addComponents(debugSearch, clearAuditSearch); 
		optionsLayoutHorizontalDesktop = new HorizontalLayout();
		optionsLayoutHorizontalDesktop.setWidth("100%");
		optionsLayoutHorizontalDesktop.addStyleName("audit-DeviceSearch");
		optionsLayoutHorizontalDesktop.setResponsive(true);

		debugSearchLayout = new HorizontalLayout();
		debugSearchLayout.setVisible(true);
		debugSearchLayout.setWidth("100%");
		debugSearchLayout.addComponent(auditSearchLayout);
		debugSearchLayout.setComponentAlignment(auditSearchLayout, Alignment.TOP_LEFT);

		debugStartDateField = new DateField();
		debugStartDateField.setWidth("100%");
		debugStartDateField.setHeight("100%");
		debugStartDateField.setPlaceholder("Start Date");
		debugStartDateField.setResponsive(true);
		debugStartDateField.setDateFormat(DATE_FORMAT);
		debugStartDateField.setRangeEnd(localTimeNow.toLocalDate());
		debugStartDateField.setDescription("Start Date");
		debugStartDateField.addStyleName("v-textfield-font");

		debugEndDateField = new DateField();
		debugEndDateField.setWidth("94%");
		debugEndDateField.setHeight("100%");
		debugEndDateField.setPlaceholder("End Date");
		debugEndDateField.setResponsive(true);
		debugEndDateField.setDateFormat(DATE_FORMAT);
		debugEndDateField.setDescription("End Date");
		debugEndDateField.addStyleName("v-textfield-font");

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

		optionsLayoutHorizontalDesktop.addComponent(debugSearchLayout);
		optionsLayoutHorizontalDesktop.setComponentAlignment(debugSearchLayout, Alignment.MIDDLE_LEFT);
		dateDeleteLayout.addComponent(debugStartDateField);
		dateDeleteLayout.addComponent(debugEndDateField);
		optionsLayoutHorizontalDesktop.addComponent(dateDeleteLayout);
		optionsLayoutHorizontalDesktop.setComponentAlignment(dateDeleteLayout, Alignment.TOP_LEFT);

		debugLayoutFull.addComponent(optionsLayoutHorizontalDesktop);
		debugLayoutFull.setComponentAlignment(optionsLayoutHorizontalDesktop, Alignment.TOP_LEFT);
		debugLayoutFull.addComponent(optionsLayoutVerticalTab);
		debugLayoutFull.setComponentAlignment(optionsLayoutVerticalTab, Alignment.TOP_LEFT);
		debugLayoutFull.addComponent(optionsLayoutVerticalPhone);
		debugLayoutFull.setComponentAlignment(optionsLayoutVerticalPhone, Alignment.TOP_LEFT);
		debugLayoutFull.addComponent(debugGrid);
		debugLayoutFull.setExpandRatio(debugGrid, 2);

		// end Date listner
		debugEndDateField.addValueChangeListener(change -> {
			if (!(change.getValue() == null)) {
				if (!nodeTreeGrid.getSelectedItems().isEmpty()) {
					if (debugStartDateField.getValue() != null) {
						if (change.getValue().compareTo(debugStartDateField.getValue()) > 0) {
							String endDate = debugEndDateField.getValue().format(dateFormatter1);
							String startDate = debugStartDateField.getValue().format(dateFormatter1);
							String filter = debugSearch.getValue();
							List<Audit> auditListFilterBydates = new ArrayList<>();
							List<AuditUserLog> auditList;
							auditList = auditService.searchByDates(filter, startDate, endDate);
							for (AuditUserLog auditUserLog : auditList) {
								Audit audit = new Audit(auditUserLog.getId(),
										auditUserLog.getAuditUserLogType().getName(), auditUserLog.getDescription(),
										auditUserLog.getDateAt().toString());
								auditListFilterBydates.add(audit);
							}
							DataProvider data = new ListDataProvider(auditListFilterBydates);
							debugGrid.setDataProvider(data);
						} else {
							Notification.show(NotificationUtil.AUDIT_SAMEDATE, Notification.Type.ERROR_MESSAGE);
							debugStartDateField.clear();
							debugEndDateField.clear();
						}
					} else {
						Notification.show(NotificationUtil.AUDIT_STARTDATE, Notification.Type.ERROR_MESSAGE);
						debugEndDateField.clear();
					}
				} else {
					Notification.show(NotificationUtil.AUDIT_SELECTNODE, Notification.Type.ERROR_MESSAGE);
					clearCalenderDates();
				}
			}
		});
		
		debugStartDateField.addValueChangeListener(change -> {
			if (change.getValue() != null) {
				debugEndDateField.clear();
				debugEndDateField.setRangeStart(change.getValue().plusDays(1));
				String date = debugStartDateField.getValue().format(dateFormatter1);
			} else {
				debugEndDateField.clear();
				debugSearch.clear();
				if (nodeTreeGrid.getSelectedItems().size() > 0) {
					List<Audit> auditListNew;
					auditListNew = auditService
							.auditGridData(nodeTreeGrid.getSelectedItems().iterator().next().getEntityId());
					DataProvider data = new ListDataProvider(auditListNew);
					debugGrid.setDataProvider(data);

				}
			}
		});

		nodeTreeGrid.addSelectionListener(selection -> {
			if (!selection.getFirstSelectedItem().isPresent()) {
				List<Audit> auditListNew = new ArrayList<>();
				DataProvider data = new ListDataProvider(auditListNew);
				debugGrid.setDataProvider(data);
			} else {
				List<Audit> auditListNew = auditService
						.auditGridData(selection.getFirstSelectedItem().get().getEntityId());
				DataProvider data = new ListDataProvider(auditListNew);
				debugGrid.setDataProvider(data);

			}
			debugSearch.clear();
			clearCalenderDates();

		});

		return debugLayoutFull;
	}
}
