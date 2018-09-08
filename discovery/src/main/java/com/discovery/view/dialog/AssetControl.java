package com.discovery.view.dialog;

import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class AssetControl extends DialogWindow{
	VerticalLayout verticalPanelLayout;
	public AssetControl() {
		super();
		this.setCaption("Asset Control");
		this.setContent(getAssetControlView());
	}
	
	public Panel getAssetControlView() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setCaption("Asset Control");
		panel.setResponsive(true);
		panel.setSizeFull();
		verticalPanelLayout = new VerticalLayout();
		verticalPanelLayout.setHeight("100%");
		//Add content
		panel.setContent(verticalPanelLayout);
		return panel;
	}

	@Override
	public Component getContent() {
		// TODO Auto-generated method stub
		return verticalPanelLayout;
	}
	
	

}
