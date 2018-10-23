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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.AuditUserLog;
import com.luretechnologies.tms.backend.data.entity.AppClient;
import com.luretechnologies.tms.backend.data.entity.Audit;
import com.luretechnologies.tms.backend.data.entity.Permission;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.service.AuditService;
import com.luretechnologies.tms.backend.service.RolesService;
import com.luretechnologies.tms.backend.service.TreeDataNodeService;
import com.luretechnologies.tms.backend.service.UserService;
import com.luretechnologies.tms.ui.MainView;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.luretechnologies.tms.ui.view.deviceodometer.DeviceodometerView;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;
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
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = AuditView.VIEW_NAME)
public class AuditView extends VerticalLayout implements Serializable, View {

	private static final String DATE_FORMAT = "MM/dd/yyyy";
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateTimeFormatter dateFormatter1 = DateTimeFormatter.ofPattern("yyMMdd");
	private static final long serialVersionUID = -7983511214106963682L;
	public static final String VIEW_NAME = "audit";
	private static final LocalDateTime localTimeNow = LocalDateTime.now();
	private static Grid<Audit> debugGrid;
	private static Tree<TreeNode> nodeTree;
	private static Button deleteGridRow;
	private static Button search, clearSearch, clearAuditSearch;
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
	private static CssLayout auditSearchLayout;
	private static final String error = "error";
	private static final String warn = "warn";
	private static final String info = "info";

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

	@PostConstruct
	private void init() {
		try {
			Page.getCurrent().addBrowserWindowResizeListener(r -> {
				System.out.println("Height " + r.getHeight() + "Width:  " + r.getWidth() + " in pixel");
				if (r.getWidth() <= 1400 && r.getWidth() >= 700) {
					tabMode();
					splitScreen.setSplitPosition(30);
				} else if (r.getWidth() <= 699 && r.getWidth() > 0) {
					phoneMode();
					splitScreen.setSplitPosition(35);

				} else {
					desktopMode();
					splitScreen.setSplitPosition(20);
				}

				if (r.getWidth() <= 600) {
					debugStartDateField.setHeight("28px");
					debugEndDateField.setHeight("28px");
					treeNodeSearch.setHeight(28, Unit.PIXELS);
					debugSearch.setHeight(28, Unit.PIXELS);
					clearAuditSearch.removeStyleNames("v-button-customstyle", "audit-AuditSearchClearDesktop");
					clearAuditSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "audit-AuditSearchClearPhone");
					mainView.getTitle().setValue(userService.getLoggedInUserName());
				} else if (r.getWidth() > 600 && r.getWidth() <= 1000) {
					debugStartDateField.setHeight("32px");
					debugEndDateField.setHeight("32px");
					treeNodeSearch.setHeight(32, Unit.PIXELS);
					debugSearch.setHeight(32, Unit.PIXELS);
					clearAuditSearch.removeStyleNames("audit-AuditSearchClearDesktop", "audit-AuditSearchClearPhone");
					clearAuditSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
					mainView.getTitle().setValue("gemView  " + userService.getLoggedInUserName());
				} else {
					debugStartDateField.setHeight("100%");
					debugEndDateField.setHeight("100%");
					treeNodeSearch.setHeight(37, Unit.PIXELS);
					debugSearch.setHeight(37, Unit.PIXELS);
					clearAuditSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
					clearAuditSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "audit-AuditSearchClearDesktop");
					mainView.getTitle().setValue("gemView  " + userService.getLoggedInUserName());
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

			Panel panel = getAndLoadAuditPanel();
			VerticalLayout verticalPanelLayout = new VerticalLayout();
			verticalPanelLayout.setHeight("100%");
			verticalPanelLayout.setStyleName("split-height");
			CssLayout searchLayout = new CssLayout();
			searchLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
			searchLayout.addComponents(treeNodeSearch, clearSearch);
			searchLayout.setWidth("85%");
			VerticalLayout treeSearchPanelLayout = new VerticalLayout(searchLayout);
			nodeTree = new Tree<TreeNode>();
			nodeTree.setTreeData(auditService.auditTreeData());
			nodeTree.setItemIconGenerator(item -> {
				switch (item.getType()) {
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
			splitScreen.addComponent(getDebugLayout());
			splitScreen.setHeight("100%");
			panel.setContent(splitScreen);
			int width = Page.getCurrent().getBrowserWindowWidth();
			if (width > 0 && width <= 699) {
				phoneMode();
				splitScreen.setSplitPosition(35);
			} else if (width >= 700 && width <= 1400) {
				tabMode();
				splitScreen.setSplitPosition(30);
			} else {
				desktopMode();
				splitScreen.setSplitPosition(20);
			}

			if (width <= 600) {
				debugStartDateField.setHeight("28px");
				debugEndDateField.setHeight("28px");
				treeNodeSearch.setHeight(28, Unit.PIXELS);
				debugSearch.setHeight(28, Unit.PIXELS);
				clearAuditSearch.removeStyleNames("v-button-customstyle", "audit-AuditSearchClearDesktop");
				clearAuditSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "audit-AuditSearchClearPhone");
				mainView.getTitle().setValue(userService.getLoggedInUserName());
			} else if (width > 600 && width <= 1000) {
				debugStartDateField.setHeight("32px");
				debugEndDateField.setHeight("32px");
				treeNodeSearch.setHeight(32, Unit.PIXELS);
				debugSearch.setHeight(32, Unit.PIXELS);
				clearAuditSearch.removeStyleNames("audit-AuditSearchClearDesktop", "audit-AuditSearchClearPhone");
				clearAuditSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
				mainView.getTitle().setValue("gemView  " + userService.getLoggedInUserName());
			} else {
				debugStartDateField.setHeight("100%");
				debugEndDateField.setHeight("100%");
				treeNodeSearch.setHeight(37, Unit.PIXELS);
				debugSearch.setHeight(37, Unit.PIXELS);
				clearAuditSearch.removeStyleNames("audit-AuditSearchClearPhone", "v-button-customstyle");
				clearAuditSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "audit-AuditSearchClearDesktop");
				mainView.getTitle().setValue("gemView  " + userService.getLoggedInUserName());
			}

			Permission appStorePermission = roleService.getLoggedInUserRolePermissions().stream()
					.filter(per -> per.getPageName().equals("AUDIT")).findFirst().get();
			disableAllComponents();
			allowAccessBasedOnPermission(appStorePermission.getAdd(), appStorePermission.getEdit(),
					appStorePermission.getDelete());
		} catch (Exception ex) {
			auditService.logAuditScreenErrors(ex);
		}
		
		mainView.getAudit().setEnabled(true);
	}

	private void disableAllComponents() throws Exception {
		deleteGridRow.setEnabled(false);
	}

	private void allowAccessBasedOnPermission(Boolean addBoolean, Boolean editBoolean, Boolean deleteBoolean) {
		deleteGridRow.setEnabled(deleteBoolean);
	}

	private void tabMode() {
		optionsLayoutVerticalTab.addStyleName("audit-tabAlignment");
		dateDeleteLayout.addComponent(debugStartDateField);
		dateDeleteLayout.setComponentAlignment(debugStartDateField, Alignment.TOP_RIGHT);
		dateDeleteLayout.addComponent(debugEndDateField);
		dateDeleteLayout.setComponentAlignment(debugEndDateField, Alignment.TOP_RIGHT);
		dateDeleteLayout.addComponent(deleteGridRow);
		dateDeleteLayout.setComponentAlignment(deleteGridRow, Alignment.TOP_LEFT);
		optionsLayoutVerticalTab.addComponents(debugSearchLayout, dateDeleteLayout);

		optionsLayoutHorizontalDesktop.setVisible(false);
		optionsLayoutVerticalPhone.setVisible(false);
		optionsLayoutVerticalTab.setVisible(true);
	}

	private void phoneMode() {
		dateDeleteLayoutPhone.addComponents(debugStartDateField, debugEndDateField, deleteGridRow);
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
		dateDeleteLayout.setComponentAlignment(debugStartDateField, Alignment.TOP_RIGHT);
		dateDeleteLayout.addComponent(debugEndDateField);
		dateDeleteLayout.setComponentAlignment(debugEndDateField, Alignment.TOP_RIGHT);
		dateDeleteLayout.addComponent(deleteGridRow);
		dateDeleteLayout.setComponentAlignment(deleteGridRow, Alignment.TOP_LEFT);
		optionsLayoutHorizontalDesktop.addComponent(dateDeleteLayout);
		// optionsLayoutHorizontalDesktop.addComponent(dateDeleteLayout);
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
		treeNodeSearch.addValueChangeListener(changed -> {
			if (changed.getValue().length() == 50) {
				Notification search = Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
				search.addCloseListener(listener -> {
					String valueInLower = changed.getValue().toLowerCase();
					if (!valueInLower.isEmpty() && valueInLower != null) {
						nodeTree.setTreeData(treeDataNodeService.searchTreeData(valueInLower));
					} else {
						nodeTree.setTreeData(treeDataNodeService.getTreeData());
					}
				});
			} else {
				String valueInLower = changed.getValue().toLowerCase();
				if (!valueInLower.isEmpty() && valueInLower != null) {
					nodeTree.setTreeData(treeDataNodeService.searchTreeData(valueInLower));
				} else {
					nodeTree.setTreeData(treeDataNodeService.getTreeData());
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
		// clearSearch.addStyleNames(ValoTheme.BUTTON_FRIENDLY,"v-button-customstyle");
	}

	private void clearCalenderDates() {
		debugStartDateField.clear();
		debugEndDateField.clear();
	}

	private void confirmDialog(Long id, TreeNode treeNode) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?", "Ok", "Cancel",
				new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							// Confirmed to continue
							auditService.deleteGridData(id);
							List<Audit> auditListNew = auditService.auditGridData(treeNode.getEntityId());
							DataProvider data = new ListDataProvider(auditListNew);
							debugGrid.setDataProvider(data);
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
		/*
		 * treeDataService.getTreeDataForDebug(); List<Node> nodeList =
		 * treeDataService.getDebugNodeList(); //Set<Node> nodeSet =
		 * nodeTree.getSelectionModel().getSelectedItems(); Node nodeSelected =
		 * nodeSet.iterator().next(); for(Node node : nodeList) {
		 * if(node.getLabel().equals(nodeSelected.getLabel())) { DataProvider data = new
		 * ListDataProvider(node.getEntityList()); debugGrid.setDataProvider(data); } }
		 */

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
		debugGrid.setSelectionMode(SelectionMode.SINGLE);
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

			default:
				return "";
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
						List<AuditUserLog> searchGridData = auditService.searchGridData(filter, startDate, endDate);
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
				if (valueChange.getValue() != "" && valueChange.getValue() != null) {
					String filter = debugSearch.getValue();
					String endDate = null;
					String startDate = null;
					if (debugEndDateField.getValue() != null && debugStartDateField.getValue() != null) {
						endDate = debugEndDateField.getValue().format(dateFormatter1);
						startDate = debugStartDateField.getValue().format(dateFormatter1);
					}
					List<AuditUserLog> searchGridData = auditService.searchGridData(filter, startDate, endDate);
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

		deleteGridRow = new Button(VaadinIcons.TRASH);
		deleteGridRow.addStyleNames(ValoTheme.BUTTON_FRIENDLY, "v-button-customstyle");
		deleteGridRow.setResponsive(true);
		deleteGridRow.addClickListener(clicked -> {
			if (debugGrid.getSelectedItems().isEmpty()) {
				Notification.show(NotificationUtil.AUDIT_DELETE, Notification.Type.ERROR_MESSAGE);
			} else {
				confirmDialog(debugGrid.getSelectedItems().iterator().next().getId(),
						nodeTree.getSelectedItems().iterator().next());
			}
		});

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
		debugEndDateField.setWidth("100%");
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
		dateDeleteLayout.setComponentAlignment(debugStartDateField, Alignment.TOP_RIGHT);
		dateDeleteLayout.addComponent(debugEndDateField);
		dateDeleteLayout.setComponentAlignment(debugEndDateField, Alignment.TOP_RIGHT);
		dateDeleteLayout.addComponent(deleteGridRow);
		dateDeleteLayout.setComponentAlignment(deleteGridRow, Alignment.TOP_LEFT);
		optionsLayoutHorizontalDesktop.addComponent(dateDeleteLayout);
		optionsLayoutHorizontalDesktop.setComponentAlignment(dateDeleteLayout, Alignment.TOP_RIGHT);

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
				if (!nodeTree.getSelectedItems().isEmpty()) {
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
				if (nodeTree.getSelectedItems().size() > 0) {
					List<Audit> auditListNew;
					auditListNew = auditService
							.auditGridData(nodeTree.getSelectedItems().iterator().next().getEntityId());
					DataProvider data = new ListDataProvider(auditListNew);
					debugGrid.setDataProvider(data);

				}
			}
		});

		nodeTree.addSelectionListener(selection -> {
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
