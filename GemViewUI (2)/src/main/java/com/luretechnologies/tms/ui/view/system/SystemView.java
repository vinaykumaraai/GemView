package com.luretechnologies.tms.ui.view.system;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.Systems;
import com.luretechnologies.tms.ui.components.ConfirmDialogFactory;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = SystemView.VIEW_NAME)
public class SystemView extends VerticalLayout implements Serializable, View{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String VIEW_NAME = "system";
	
	Map<String, Systems> systemRepo = new LinkedHashMap<>();
	Grid<Systems> systemGrid = new Grid<Systems>();
	private volatile Systems selectedSystem;
	private TextField systemDescription;
	private TextField parameterName;
	private TextField systemType;
	private TextField systemValue;
	
	@Autowired
	public ConfirmDialogFactory confirmDialogFactory;
	
	
	@Autowired
	public SystemView() {
		selectedSystem = new Systems();
		
		Systems system = new Systems();
		system.setParameterName("SERVER1 IP");
		system.setDescription("Server1 IP Address");
		system.setType("Text");
		system.setValue("1.1.1.1");
		
		Systems system1 = new Systems();
		system1.setParameterName("SERVER2 IP");
		system1.setDescription("Server2 IP Address");
		system1.setType("Text");
		system1.setValue("1.0.1.0");
		
		Systems system2 = new Systems();
		system2.setParameterName("AVAILABLE CON");
		system2.setDescription("Available Connections");
		system2.setType("Numeric");
		system2.setValue("23");
		
		Systems system3 = new Systems();
		system3.setParameterName("SYSTEM MESSAGE");
		system3.setDescription("Welcome Message");
		system3.setType("Text");
		system3.setValue("Welcome to GemView");
		
		
		systemRepo.put(system.getParameterName(), system);
		systemRepo.put(system1.getParameterName(), system1);
		systemRepo.put(system2.getParameterName(), system2);
		systemRepo.put(system3.getParameterName(), system3);
		
	}
	
	@PostConstruct
	private void inti() {
		setHeight("100%");
		setSpacing(false);
		setMargin(false);
		setResponsive(true);
		Panel panel = getAndLoadSystemPanel();
		VerticalLayout verticalLayout = new VerticalLayout();
		panel.setContent(verticalLayout);
		verticalLayout.setSpacing(false);
		verticalLayout.setMargin(false);
		Label availableSystem = new Label("<h2 style=font-weight:bold;padding-left:12px;word-wrap:break-word;>Parameters & Settings</h2>", ContentMode.HTML);
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSizeFull();
		
		HorizontalLayout layout1 = new HorizontalLayout();
		layout1.addComponent(availableSystem);
		layout1.setComponentAlignment(availableSystem, Alignment.MIDDLE_LEFT);
		layout1.setSizeFull();
		
		HorizontalLayout layout2 = new HorizontalLayout();
		layout2.setSizeUndefined();
		layout2.setResponsive(true);
		
		verticalLayout.addComponent(horizontalLayout);
		VerticalLayout systemInfoLayout = new VerticalLayout();
		verticalLayout.addComponent(systemInfoLayout);
		systemInfoLayout.setMargin(false);
		systemInfoLayout.setSpacing(true);
		systemInfoLayout.setStyleName("form-layout");
		
		getAndLoadSystemForm(systemInfoLayout, false);
		
		Button cancel = new Button("Cancel");
		cancel.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		cancel.setResponsive(true);
		cancel.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {	
				systemInfoLayout.removeAllComponents();
				systemGrid.getDataProvider().refreshAll();
				systemGrid.deselectAll();
				selectedSystem = new Systems();
				getAndLoadSystemForm(systemInfoLayout, false);	
			}
		});
		layout2.addComponent(cancel);
		
		Button save = new Button("Save");
		save.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		save.setResponsive(true);
		save.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				String description = systemDescription.getValue();
				String parametername = parameterName.getValue();
				String type = systemType.getValue();
				String value = systemValue.getValue();
				selectedSystem.setParameterName(parametername);
				selectedSystem.setDescription(description);
				selectedSystem.setType(type);
				selectedSystem.setValue(value);
				if(description.isEmpty() || description== null|| parametername.isEmpty() || parametername== null 
						|| type.isEmpty() || type== null || value.isEmpty() || value== null) {
					Notification.show("Fill all details", Notification.Type.WARNING_MESSAGE).setDelayMsec(3000);
				} else {
				systemRepo.put(selectedSystem.getParameterName(), selectedSystem);
				systemGrid.getDataProvider().refreshAll();
				systemGrid.select(selectedSystem);
				systemInfoLayout.removeAllComponents();
				getAndLoadSystemForm(systemInfoLayout, false);
				}
			}
		});
		
		layout2.addComponent(save);
		layout2.setComponentAlignment(cancel, Alignment.MIDDLE_RIGHT);
		layout2.setComponentAlignment(save, Alignment.MIDDLE_RIGHT);
		layout2.setResponsive(true);
		layout2.setStyleName("save-cancelButtonsAlignment");
		
		horizontalLayout.addComponents(layout1, layout2);
		horizontalLayout.setComponentAlignment(layout2, Alignment.MIDDLE_RIGHT);
		
		getSystemGrid(verticalLayout, systemInfoLayout);
	}
	
	public Panel getAndLoadSystemPanel() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setCaption("<h1 style=color:#216C2A;font-weight:bold;>System</h1>");
		panel.setResponsive(true);
		panel.setSizeFull();
        addComponent(panel);
       return panel;
	}
	
	private void getAndLoadSystemForm(VerticalLayout verticalLayout , boolean isEditableOnly) {
		FormLayout formLayout = new FormLayout();
		
		getSystemParameterName(formLayout, isEditableOnly);
		
		getSystemDescription(formLayout, isEditableOnly);
		
		getSystemType(formLayout, isEditableOnly);
		
		getSystemValue(formLayout, isEditableOnly);
		
		verticalLayout.addComponent(formLayout);
	}
	
	private void getSystemParameterName(FormLayout formLayout , boolean isEditableOnly) {
		
		String parametername = selectedSystem.getParameterName() != null ? selectedSystem.getParameterName(): "";
		parameterName = new TextField("Parameter Name", parametername);
		selectedSystem.setParameterName(parameterName.getValue());
		parameterName.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		parameterName.setWidth("48%");
		parameterName.setStyleName("role-textbox");
		parameterName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		parameterName.setEnabled(isEditableOnly);
		//descriptionHL.addComponent(deviceDescription);
		formLayout.addComponent(parameterName);
	}
	
	private void getSystemDescription(FormLayout formLayout, boolean isEditableOnly) {
		//HorizontalLayout descriptionHL = new HorizontalLayout();
		String description = selectedSystem.getDescription() != null ? selectedSystem.getDescription(): "";
		systemDescription = new TextField("Description", description);
		selectedSystem.setDescription(systemDescription.getValue());
		systemDescription.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		systemDescription.setWidth("48%");
		systemDescription.setStyleName("role-textbox");
		systemDescription.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		systemDescription.setEnabled(isEditableOnly);
		//descriptionHL.addComponent(deviceDescription);
		formLayout.addComponent(systemDescription);
	}
	
	private void getSystemType(FormLayout formLayout, boolean isEditableOnly) {
		//HorizontalLayout descriptionHL = new HorizontalLayout();
		String type = selectedSystem.getType() != null ? selectedSystem.getType(): "";
		systemType = new TextField("Type", type);
		selectedSystem.setDescription(systemType.getValue());
		systemType.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		systemType.setWidth("48%");
		systemType.setStyleName("role-textbox");
		systemType.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		systemType.setEnabled(isEditableOnly);
		//descriptionHL.addComponent(deviceDescription);
		formLayout.addComponent(systemType);
	}
	
	private void getSystemValue(FormLayout formLayout, boolean isEditableOnly) {
		//HorizontalLayout descriptionHL = new HorizontalLayout();
		String value = selectedSystem.getValue() != null ? selectedSystem.getValue(): "";
		systemValue = new TextField("Value", value);
		selectedSystem.setDescription(systemValue.getValue());
		systemValue.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		systemValue.setWidth("48%");
		systemValue.setStyleName("role-textbox");
		systemValue.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		systemValue.setEnabled(isEditableOnly);
		//descriptionHL.addComponent(deviceDescription);
		formLayout.addComponent(systemValue);
	}
	
	private void getSystemGrid(VerticalLayout verticalLayout, VerticalLayout systemInfoLayout) {
		Button addNewDevice = new Button(VaadinIcons.FOLDER_ADD);
		addNewDevice.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		addNewDevice.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				systemInfoLayout.removeAllComponents();
				selectedSystem = new Systems();
				getAndLoadSystemForm(systemInfoLayout, true);				
			}
		});
		
		Button editDevice = new Button(VaadinIcons.EDIT);
		editDevice.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		editDevice.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				if(systemDescription.getValue()==null || systemDescription.getValue().isEmpty() || 
						parameterName.getValue()==null ||  parameterName.getValue().isEmpty() ||
								systemType.getValue()==null ||  systemType.getValue().isEmpty() ||
								systemValue.getValue()==null ||  systemValue.getValue().isEmpty()) {
					Notification.show("Select any Role to Modify", Notification.Type.WARNING_MESSAGE).setDelayMsec(3000);;
				}else {
					systemInfoLayout.removeAllComponents();
					getAndLoadSystemForm(systemInfoLayout, true);		
				}
			}
		});
		
		Button deleteDevice = new Button(VaadinIcons.TRASH);
		deleteDevice.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		deleteDevice.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(systemDescription.getValue()==null || systemDescription.getValue().isEmpty() || 
						parameterName.getValue()==null ||  parameterName.getValue().isEmpty() ||
						systemType.getValue()==null ||  systemType.getValue().isEmpty() ||
						systemValue.getValue()==null ||  systemValue.getValue().isEmpty()) {
					Notification.show("Select role to delete", Notification.Type.ERROR_MESSAGE);
				}else {
				
				confirmDialog( systemInfoLayout);
				}
			}
		});
		
		HorizontalLayout buttonGroup =  new HorizontalLayout();
		buttonGroup.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		buttonGroup.addComponent(addNewDevice);
		buttonGroup.addComponent(editDevice);
		buttonGroup.addComponent(deleteDevice);
		
		systemGrid.setCaptionAsHtml(true);
		systemGrid.setHeightByRows(5);
		systemGrid.addColumn(Systems::getParameterName).setCaption("Paramater");
		systemGrid.addColumn(Systems::getDescription).setCaption("Description");
		systemGrid.addColumn(Systems:: getType).setCaption("Type");
		systemGrid.addColumn(Systems:: getValue).setCaption("Value");
		
		//devicesGrid.setHeightByRows(5);
		systemGrid.setItems(systemRepo.values());
		systemGrid.setWidth("100%");
		systemGrid.setResponsive(true);
		
		systemGrid.addSelectionListener(e -> {
			if (!e.isUserOriginated()) {
				return;
			}

			if (e.getFirstSelectedItem().isPresent()) {
				//Load and update the data in permission grid.
				systemInfoLayout.removeAllComponents();
				Systems selectedSystem = e.getFirstSelectedItem().get();
				this.selectedSystem = selectedSystem;
				getAndLoadSystemForm(systemInfoLayout, false);
			} else {
				throw new IllegalStateException("This should never happen as deselection is not allowed");
			}
		});
		VerticalLayout systemGridLayout = new VerticalLayout();
		systemGridLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		systemGridLayout.addComponent(buttonGroup);
		systemGridLayout.addComponent(systemGrid);
		verticalLayout.addComponent(systemGridLayout);
		
	}
	
	private void confirmDialog(VerticalLayout systemInfoLayout) {
		ConfirmDialog.show(this.getUI(), "Please Confirm:", "Are you sure you want to delete?",
		        "Ok", "Cancel", new ConfirmDialog.Listener() {

		            public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		                    // Confirmed to continue
		                	systemInfoLayout.removeAllComponents();
		                	systemRepo.remove(selectedSystem.getParameterName());
		                	systemGrid.getDataProvider().refreshAll();
		                	selectedSystem = new Systems();
		                	getAndLoadSystemForm(systemInfoLayout, false);
		                } else {
		                    // User did not confirm
		                    
		                }
		            }
		        });
	}
	
}
