package com.discovery.view.dialog;

import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class AssetControl extends DialogWindow{
	
	public AssetControl() {
		super();
		this.setCaption("AssetControl");
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
		VerticalLayout verticalPanelLayout = new VerticalLayout();
		verticalPanelLayout.setHeight("100%");
		//Add content
		panel.setContent(verticalPanelLayout);
		return panel;
	}

}
