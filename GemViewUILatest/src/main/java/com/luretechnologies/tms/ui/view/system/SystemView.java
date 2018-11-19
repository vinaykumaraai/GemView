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
package com.luretechnologies.tms.ui.view.system;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.extension.gridscroll.GridScrollExtension;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.tms.backend.data.entity.Permission;
import com.luretechnologies.tms.backend.data.entity.Systems;
import com.luretechnologies.tms.backend.service.RolesService;
import com.luretechnologies.tms.backend.service.SystemService;
import com.luretechnologies.tms.backend.service.UserService;
import com.luretechnologies.tms.ui.MainView;
import com.luretechnologies.tms.ui.components.ComponentUtil;
import com.luretechnologies.tms.ui.components.ConfirmDialogFactory;
import com.luretechnologies.tms.ui.components.MainViewIconsLoad;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.luretechnologies.tms.ui.navigation.NavigationManager;
import com.luretechnologies.tms.ui.view.ContextMenuWindow;
import com.luretechnologies.tms.ui.view.Header;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;	
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 * 
 * @author Vinay
 *
 */

@SpringView(name = SystemView.VIEW_NAME)
public class SystemView extends VerticalLayout implements Serializable, View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String VIEW_NAME = "system";

	Map<Integer, Systems> systemRepo = new LinkedHashMap<>();
	Grid<Systems> systemGrid;
	private volatile Systems selectedSystem;
	private static TextField systemDescription;
	private static TextField parameterName;
	private static TextField systemValue;
	private static ComboBox<String> comboBoxType;
	private Button save;
	private Button cancel;
	private Header header;
	private ContextMenuWindow createWindow;
	private ContextMenuWindow paramContextWindow ;
	private ContextMenuWindow editWindow;
	private Button createSystemGridMenu, editSystemGridMenu, deleteSystemGridMenu;
	private boolean add, edit , delete;
	private GridScrollExtension extension;

	@Autowired
	public ConfirmDialogFactory confirmDialogFactory;

	@Autowired
	public SystemService systemService;

	@Autowired
	private RolesService roleService;

	@Autowired
	MainView mainView;

	@Autowired
	UserService userService;

	@Autowired
	public SystemView() {
		selectedSystem = new Systems();
	}

	@Autowired
	public NavigationManager navigationManager;

	@PostConstruct
	private void inti() {
		try {
			header = new Header(userService, roleService, navigationManager, "System", new Label());
			setHeight("100%");
			setSpacing(false);
			setMargin(false);
			setResponsive(true);
			Panel panel = getAndLoadSystemPanel();
			VerticalLayout verticalLayout = new VerticalLayout();
			verticalLayout.addStyleName("heartbeat-secondComponent");
			panel.setContent(verticalLayout);
			verticalLayout.setSpacing(false);
			verticalLayout.setMargin(false);
			verticalLayout.setHeight("100%");
			
			parameterName = new TextField("Parameter Name");
			parameterName.addStyleNames("v-textfield-font","textfiled-height");
			
			systemDescription = new TextField("Description");
			systemDescription.addStyleNames("v-textfield-font","textfiled-height");
			
			comboBoxType = new ComboBox<String>("Type");
			comboBoxType.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "textfiled-height");
			
			systemValue = new TextField("Value");
			systemValue.addStyleNames("v-textfield-font","textfiled-height");
			
			save = new Button("Save");
			save.addStyleName(ValoTheme.BUTTON_FRIENDLY);
			save.addStyleName("v-button-customstyle");
			
			cancel = new Button("Cancel");
			cancel.addStyleName(ValoTheme.BUTTON_FRIENDLY);
			cancel.addStyleName("v-button-customstyle");
			
			getSystemGrid(verticalLayout);

			Permission appStorePermission = roleService.getLoggedInUserRolePermissions().stream()
					.filter(per -> per.getPageName().equals("SYSTEM")).findFirst().get();

			disableAllComponents();
			add = appStorePermission.getAdd();
			edit = appStorePermission.getEdit();
			delete = appStorePermission.getDelete();
			
			allowAccessBasedOnPermission(add,edit,
					delete);

			Page.getCurrent().addBrowserWindowResizeListener(r -> {
				System.out.println("Height " + r.getHeight() + "Width:  " + r.getWidth() + " in pixel");

				if (r.getWidth() <= 600) {
					removeComponent(header);
					mainView.getTitle().setValue(userService.getLoggedInUserName());
					parameterName.setHeight("27px");
					systemDescription.setHeight("27px");
					comboBoxType.setHeight("27px");
					systemValue.setHeight("27px");
					save.setHeight("27px");
					cancel.setHeight("27px");
					
					createSystemGridMenu.setHeight("27px");
					createSystemGridMenu.removeStyleNames("button-TabFont", "button-DesktopFont");
					createSystemGridMenu.addStyleName("button-PhoneFont");
					
					editSystemGridMenu.setHeight("27px");
					editSystemGridMenu.removeStyleNames("button-TabFont", "button-DesktopFont");
					editSystemGridMenu.addStyleName("button-PhoneFont");
					
					deleteSystemGridMenu.setHeight("27px");
					deleteSystemGridMenu.removeStyleNames("button-TabFont", "button-DesktopFont");
					deleteSystemGridMenu.addStyleName("button-PhoneFont");
					
					if(createWindow!=null) {
						createWindow.setPosition(180, 220);
						createWindow.setWidth("180px");
					}
					if(editWindow!=null) {
						editWindow.setPosition(180, 220);
						editWindow.setWidth("180px");
					}
					MainViewIconsLoad.iconsOnPhoneMode(mainView);
				} else if (r.getWidth() > 600 && r.getWidth() <= 1000) {
					addComponentAsFirst(header);
					mainView.getTitle().setValue("gemView");
					parameterName.setHeight("32px");
					systemDescription.setHeight("32px");
					comboBoxType.setHeight("32px");
					systemValue.setHeight("32px");
					save.setHeight("32px");
					cancel.setHeight("32px");
					
					createSystemGridMenu.setHeight("32px");
					createSystemGridMenu.removeStyleNames("button-DesktopFont", "button-PhoneFont");
					createSystemGridMenu.addStyleName("button-TabFont");
					
					editSystemGridMenu.setHeight("32px");
					editSystemGridMenu.removeStyleNames("button-PhoneFont", "button-DesktopFont");
					editSystemGridMenu.addStyleName("button-TabFont");
					
					deleteSystemGridMenu.setHeight("32px");
					deleteSystemGridMenu.removeStyleNames("button-PhoneFont", "button-DesktopFont");
					deleteSystemGridMenu.addStyleName("button-TabFont");
					
					
					if(createWindow!=null) {
						createWindow.center();
						createWindow.setWidth("30%");
						createWindow.setHeight("42%");
					}
					if(editWindow!=null) {
						editWindow.center();
						editWindow.setSizeUndefined();
					}
					MainViewIconsLoad.iconsOnTabMode(mainView);
				} else {
					addComponentAsFirst(header);
					mainView.getTitle().setValue("gemView");
					parameterName.setHeight("37px");
					systemDescription.setHeight("37px");
					comboBoxType.setHeight("37px");
					systemValue.setHeight("37px");
					save.setHeight("37px");
					cancel.setHeight("37px");
					
					createSystemGridMenu.setHeight("37px");
					createSystemGridMenu.removeStyleNames("button-TabFont", "button-PhoneFont");
					createSystemGridMenu.addStyleName("button-DesktopFont");
					
					editSystemGridMenu.setHeight("37px");
					editSystemGridMenu.removeStyleNames("button-PhoneFont", "button-TabFont");
					editSystemGridMenu.addStyleName("button-DesktopFont");
					
					deleteSystemGridMenu.setHeight("37px");
					deleteSystemGridMenu.removeStyleNames("button-PhoneFont", "button-TabFont");
					deleteSystemGridMenu.addStyleName("button-DesktopFont");
					
					if(createWindow!=null) {
						createWindow.center();
						createWindow.setWidth("30%");
						createWindow.setHeight("42%");
					}
					if(editWindow!=null) {
						editWindow.center();
						editWindow.setSizeUndefined();
					}
					MainViewIconsLoad.noIconsOnDesktopMode(mainView);
				}
			});

			int width = Page.getCurrent().getBrowserWindowWidth();
			if (width <= 600) {
				removeComponent(header);
				mainView.getTitle().setValue(userService.getLoggedInUserName());
				parameterName.setHeight("27px");
				systemDescription.setHeight("27px");
				comboBoxType.setHeight("27px");
				systemValue.setHeight("27px");
				save.setHeight("27px");
				cancel.setHeight("27px");
				
				createSystemGridMenu.setHeight("27px");
				createSystemGridMenu.removeStyleNames("button-TabFont", "button-DesktopFont");
				createSystemGridMenu.addStyleName("button-PhoneFont");
				
				editSystemGridMenu.setHeight("27px");
				editSystemGridMenu.removeStyleNames("button-TabFont", "button-DesktopFont");
				editSystemGridMenu.addStyleName("button-PhoneFont");
				
				deleteSystemGridMenu.setHeight("27px");
				deleteSystemGridMenu.removeStyleNames("button-TabFont", "button-DesktopFont");
				deleteSystemGridMenu.addStyleName("button-PhoneFont");
				
				
				if(createWindow!=null) {
					createWindow.setPosition(180, 220);
					createWindow.setWidth("180px");
				}
				if(editWindow!=null) {
					editWindow.setPosition(180, 220);
					editWindow.setWidth("180px");
				}
				MainViewIconsLoad.iconsOnPhoneMode(mainView);
			} else if (width > 600 && width <= 1000) {
				addComponentAsFirst(header);
				mainView.getTitle().setValue("gemView");
				parameterName.setHeight("32px");
				systemDescription.setHeight("32px");
				comboBoxType.setHeight("32px");
				systemValue.setHeight("32px");
				save.setHeight("32px");
				cancel.setHeight("32px");
				
				createSystemGridMenu.setHeight("32px");
				createSystemGridMenu.removeStyleNames("button-DesktopFont", "button-PhoneFont");
				createSystemGridMenu.addStyleName("button-TabFont");
				
				editSystemGridMenu.setHeight("32px");
				editSystemGridMenu.removeStyleNames("button-PhoneFont", "button-DesktopFont");
				editSystemGridMenu.addStyleName("button-TabFont");
				
				deleteSystemGridMenu.setHeight("32px");
				deleteSystemGridMenu.removeStyleNames("button-PhoneFont", "button-DesktopFont");
				deleteSystemGridMenu.addStyleName("button-TabFont");
				
				if(createWindow!=null) {
					createWindow.setWidth("30%");
					createWindow.setHeight("42%");
					createWindow.center();
				}
				if(editWindow!=null) {
					editWindow.setSizeUndefined();
					editWindow.center();
				}
				MainViewIconsLoad.iconsOnTabMode(mainView);
			} else {
				addComponentAsFirst(header);
				mainView.getTitle().setValue("gemView");
				parameterName.setHeight("37px");
				systemDescription.setHeight("37px");
				comboBoxType.setHeight("37px");
				systemValue.setHeight("37px");
				save.setHeight("37px");
				cancel.setHeight("37px");
				
				createSystemGridMenu.setHeight("37px");
				createSystemGridMenu.removeStyleNames("button-TabFont", "button-PhoneFont");
				createSystemGridMenu.addStyleName("button-DesktopFont");
				
				editSystemGridMenu.setHeight("37px");
				editSystemGridMenu.removeStyleNames("button-PhoneFont", "button-TabFont");
				editSystemGridMenu.addStyleName("button-DesktopFont");
				
				deleteSystemGridMenu.setHeight("37px");
				deleteSystemGridMenu.removeStyleNames("button-PhoneFont", "button-TabFont");
				deleteSystemGridMenu.addStyleName("button-DesktopFont");
				
				if(createWindow!=null) {
					createWindow.center();
					createWindow.setWidth("30%");
					createWindow.setHeight("42%");
				}
				if(editWindow!=null) {
					editWindow.center();
					editWindow.setSizeUndefined();
				}
				MainViewIconsLoad.noIconsOnDesktopMode(mainView);
			}

		} catch (Exception ex) {
			systemService.logSystemScreenErrors(ex);
		}

		mainView.getSystem().setEnabled(true);
	}

	private void disableAllComponents() throws Exception {
		createSystemGridMenu.setEnabled(false);
		editSystemGridMenu.setEnabled(false);
		deleteSystemGridMenu.setEnabled(false);
		save.setEnabled(false);
		cancel.setEnabled(false);
	}

	private void allowAccessBasedOnPermission(Boolean addBoolean, Boolean editBoolean, Boolean deleteBoolean) {
		createSystemGridMenu.setEnabled(addBoolean);
		editSystemGridMenu.setEnabled(editBoolean);
		deleteSystemGridMenu.setEnabled(deleteBoolean);
		save.setEnabled(editBoolean || addBoolean);
		cancel.setEnabled(editBoolean || addBoolean);
	}

	public Panel getAndLoadSystemPanel() {
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

	private void clearParamComponents() {
		parameterName.clear();
		systemDescription.clear();
		systemValue.clear();
		comboBoxType.clear();
	}

	private boolean checkParamNameInList(String parametername) {
		ListDataProvider<Systems> provider = (ListDataProvider<Systems>) systemGrid.getDataProvider();
		for (Systems systemParam : provider.getItems()) {
			String paramName = systemParam.getParameterName();
			if (paramName.equalsIgnoreCase(parametername)) {
				return true;
			}
		}
		return false;

	}

	private boolean containsSystemInList(List<Systems> systemList, Systems system) {
		for (Systems subSystem : systemList) {
			if (subSystem.getId().equals(system.getId())) {
				return true;
			}
		}
		return false;

	}

	private Component getAndLoadSystemForm(boolean isEditableOnly) {
		VerticalLayout verticalLayoutSystem = new VerticalLayout();
		verticalLayoutSystem.addStyleNames("system-LabelAlignment", "heartbeat-verticalLayout", "header-Components");

		getSystemParameterName(verticalLayoutSystem, isEditableOnly);

		getSystemDescription(verticalLayoutSystem, isEditableOnly);

		getSystemType(verticalLayoutSystem, isEditableOnly);

		getSystemValue(verticalLayoutSystem, isEditableOnly);

		getSaveCancel(verticalLayoutSystem, isEditableOnly);
		return verticalLayoutSystem;
	}

	private void getSystemParameterName(VerticalLayout verticalLayoutSystem, boolean isEditableOnly) {

		String parametername = selectedSystem.getParameterName() != null ? selectedSystem.getParameterName() : "";
		parameterName.setValue(parametername.toUpperCase());
		parameterName.addStyleName("v-textfield-font");
		parameterName.addStyleName("textfiled-height");
		parameterName.setWidth("100%");
		parameterName.setEnabled(isEditableOnly);
		parameterName.setId("systemParamName");
		parameterName.setRequiredIndicatorVisible(true);
		parameterName.setMaxLength(50);
		parameterName.addValueChangeListener(listener -> {
			if (listener.getValue().length() == 50) {
				Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});
		verticalLayoutSystem.addComponent(parameterName);
	}

	private void getSystemDescription(VerticalLayout verticalLayoutSystem, boolean isEditableOnly) {
		String description = selectedSystem.getDescription() != null ? selectedSystem.getDescription() : "";
		systemDescription.setValue(description);
		selectedSystem.setDescription(systemDescription.getValue());
		systemDescription.addStyleNames("v-textfield-font", "textfiled-height");
		systemDescription.setWidth("100%");
		systemDescription.setEnabled(isEditableOnly);
		systemDescription.setId("systemParamDescription");
		systemDescription.setRequiredIndicatorVisible(true);
		systemDescription.setMaxLength(50);
		systemDescription.addValueChangeListener(listener -> {
			if (listener.getValue().length() == 50) {
				Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});

		verticalLayoutSystem.addComponent(systemDescription);
	}

	private void getSystemType(VerticalLayout verticalLayoutSystem, boolean isEditableOnly) {
		String type = selectedSystem.getType() != null ? selectedSystem.getType() : "";
		comboBoxType.setEnabled(isEditableOnly);
		comboBoxType.setCaptionAsHtml(true);
		comboBoxType.setWidth("100%");
		comboBoxType.setDataProvider(new ListDataProvider<>(Arrays.asList("Text", "Numeric", "Boolean")));
		comboBoxType.setValue(type);
		comboBoxType.setId("systemParamType");
		verticalLayoutSystem.addComponent(comboBoxType);
	}

	private void getSystemValue(VerticalLayout verticalLayoutSystem, boolean isEditableOnly) {
		String value = selectedSystem.getSystemValue() != null ? selectedSystem.getSystemValue() : "";
		systemValue.setValue(value);
		selectedSystem.setSystemValue(systemValue.getValue());
		systemValue.setWidth("100%");
		systemValue.setEnabled(isEditableOnly);
		systemValue.setId("systemParamValue");
		systemValue.setRequiredIndicatorVisible(true);
		systemValue.setMaxLength(50);
		systemValue.addValueChangeListener(listener -> {
			if (listener.getValue().length() == 50) {
				Notification.show(NotificationUtil.TEXTFIELD_LIMIT, Type.ERROR_MESSAGE);
			}
		});
		verticalLayoutSystem.addComponent(systemValue);
	}

	private void getSaveCancel(VerticalLayout verticalLayoutSystem, boolean isEditableOnly) {
		HorizontalLayout layout2 = new HorizontalLayout();
		layout2.setSizeUndefined();
		layout2.setResponsive(true);
		
		cancel.setResponsive(true);
		cancel.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				selectedSystem = new Systems();
				clearParamComponents();
			}
		});
		layout2.addComponent(cancel);
		
		save = new Button("Save");
		save.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		save.addStyleName("v-button-customstyle");
		save.setResponsive(true);
		save.setId("systemSave");
		save.setDescription("Save");
		save.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				String description = systemDescription.getValue();
				String parametername = parameterName.getValue();
				String type = comboBoxType.getValue();
				String value = systemValue.getValue();
				if (!(ComponentUtil.validatorTextField(parameterName)
						&& ComponentUtil.validatorTextField(systemDescription)
						&& ComponentUtil.validatorComboBox(comboBoxType)
						&& ComponentUtil.validatorTextField(systemValue))) {

				} else {
					if (systemGrid.getSelectedItems().size() > 0) {
						Systems system = systemGrid.getSelectedItems().iterator().next();
						List<Systems> systemList = systemService.getAllSystemParam();
						if (containsSystemInList(systemList, system)) {
							selectedSystem = systemService.updateSystem(system, description, parametername, type,
									value);
							DataProvider data = new ListDataProvider(systemService.getAllSystemParam());
							systemGrid.setDataProvider(data);
							clearParamComponents();
						}
					} else {
						if (!checkParamNameInList(parametername)) {
							selectedSystem = systemService.createSystemParam(parametername, description, value,
									type);
							DataProvider data = new ListDataProvider(systemService.getAllSystemParam());
							systemGrid.setDataProvider(data);
							clearParamComponents();
						} else {
							Notification.show(NotificationUtil.SYSTEM_PARAM_EXIST, Type.ERROR_MESSAGE);
							parameterName.focus();
						}
					}
					if(createWindow!=null) {
						createWindow.close();
					}else if(editWindow!=null) {
						editWindow.close();
					}
				}
				
			}
		});

		layout2.addComponent(save);
		layout2.setComponentAlignment(cancel, Alignment.MIDDLE_RIGHT);
		layout2.setComponentAlignment(save, Alignment.MIDDLE_RIGHT);
		layout2.setResponsive(true);
		layout2.addStyleNames("save-cancelButtonsAlignmentSystems");
		
		verticalLayoutSystem.addComponent(layout2);
	}
	
	private Focusable getFirstFormField() {
		return parameterName;
	}
	
	private void window() {
		
		
	}

	private void getSystemGrid(VerticalLayout verticalLayout) throws ApiException {
		
		createSystemGridMenu = new Button("Create Parameter");
		createSystemGridMenu.addClickListener(listener->{
			selectedSystem = new Systems();
			systemGrid.deselectAll();
			createWindow = new ContextMenuWindow();
			createWindow.addMenuItems(getAndLoadSystemForm(true));
			createWindow.center();
			createWindow.setWidth("30%");
			createWindow.setHeight("42%");
			createWindow.setResizable(true);
			createWindow.setClosable(true);
			createWindow.setDraggable(true);
			
			UI.getCurrent().getWindows().forEach(Window::close);
			UI.getCurrent().addWindow(createWindow);
			parameterName.focus();
		});
		createSystemGridMenu.addStyleNames(ValoTheme.BUTTON_BORDERLESS, "v-textfield-font");
		
		editSystemGridMenu = new Button("Edit Parameter");
		editSystemGridMenu.addClickListener(listener-> {
			if (selectedSystem == null || selectedSystem.getId() == null) {
				Notification.show(NotificationUtil.SYSTEM_EDIT, Notification.Type.ERROR_MESSAGE);
			} else {
				editWindow = new ContextMenuWindow();
				editWindow.addMenuItems(getAndLoadSystemForm(true));
				editWindow.center();
				editWindow.setWidth("30%");
				editWindow.setHeight("42%");
				editWindow.setResizable(true);
				editWindow.setClosable(true);
				editWindow.setDraggable(true);
				
				UI.getCurrent().getWindows().forEach(Window::close);
				UI.getCurrent().addWindow(editWindow);
				parameterName.setEnabled(false);
			}
		});
		editSystemGridMenu.addStyleNames(ValoTheme.BUTTON_BORDERLESS, "v-textfield-font");
		editSystemGridMenu.setEnabled(false);
		
		deleteSystemGridMenu = new Button("Delete Parameter", click -> {
			paramContextWindow.close();
			Set<Systems> systemList = systemGrid.getSelectedItems();
			if (systemList == null || systemList.isEmpty()) {
				Notification.show(NotificationUtil.SYSTEM_DELETE, Notification.Type.ERROR_MESSAGE);
			} else {
				confirmDialog( systemList);
			}
		});
		deleteSystemGridMenu.setStyleName("v-textfield-font");
		deleteSystemGridMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		deleteSystemGridMenu.setEnabled(false);
		
		
		paramContextWindow = new ContextMenuWindow();
		paramContextWindow.addMenuItems(createSystemGridMenu, editSystemGridMenu, deleteSystemGridMenu);

		UI.getCurrent().addClickListener(Listener -> {
			if(paramContextWindow!=null) {
				paramContextWindow.close();
			}
			if(createWindow!=null) {
				createWindow.close();
			}
		});
				
		systemGrid  = new Grid<Systems>();
		systemGrid.setCaptionAsHtml(true);
		systemGrid.addStyleName("v-grid-cell-fontSize");
		systemGrid.setId("systemGrid");
		systemGrid.addColumn(Systems::getParameterName).setCaption("Parameter Name");
		systemGrid.addColumn(Systems::getDescription).setCaption("Description");
		systemGrid.addColumn(Systems::getType).setCaption("Type");
		systemGrid.addColumn(Systems::getSystemValue).setCaption("Value");
		
		extension = new GridScrollExtension(systemGrid);
		extension.addGridScrolledListener(event -> {
		    UI.getCurrent().getWindows().forEach(Window::close);
		});

		DataProvider data = new ListDataProvider(systemService.getAllSystemParam());
		systemGrid.setDataProvider(data);
		systemGrid.setHeight("100%");
		systemGrid.setWidth("100%");
		systemGrid.addStyleName("heartbeat-secondComponent");
		systemGrid.setResponsive(true);
		systemGrid.setSelectionMode(SelectionMode.MULTI);
		systemGrid.addSelectionListener(e -> {
			paramContextWindow.close();
			if (!e.isUserOriginated()) {
				return;
			}

			if (e.getFirstSelectedItem().isPresent()) {
				Systems selectedSystem = e.getFirstSelectedItem().get();
				this.selectedSystem = selectedSystem;
			} else {
				clearParamComponents();
			}
		});
		
		systemGrid.addContextClickListener(click -> {
			if(systemGrid.getSelectedItems().size()>0 && delete) {
				deleteSystemGridMenu.setEnabled(true);
			}else {
				deleteSystemGridMenu.setEnabled(false);
			}
			if(systemGrid.getSelectedItems().size()==1 && edit) {
				editSystemGridMenu.setEnabled(true);
			}else {
				editSystemGridMenu.setEnabled(false);
			}
			UI.getCurrent().getWindows().forEach(Window::close);
			paramContextWindow.setPosition(click.getClientX(), click.getClientY());
			UI.getCurrent().addWindow(paramContextWindow);
		});
		
		VerticalLayout systemGridLayout = new VerticalLayout();
		systemGridLayout.setHeight("100%");
		systemGridLayout.addStyleName("system-GridAlignment");
		systemGridLayout.addComponent(systemGrid);
		verticalLayout.addComponent(systemGridLayout);
	}

	private void confirmDialog(Set<Systems> systemList) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?", "Ok", "Cancel",
				new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							// Confirmed to continue
							for(Systems system : systemList) {
								systemService.deleteSystem(system);
							}
								DataProvider data = new ListDataProvider(systemService.getAllSystemParam());
								systemGrid.setDataProvider(data);
								selectedSystem = new Systems();
								systemGrid.getDataProvider().refreshAll();
						} else {
							// User did not confirm

						}
					}
				});
	}

}
