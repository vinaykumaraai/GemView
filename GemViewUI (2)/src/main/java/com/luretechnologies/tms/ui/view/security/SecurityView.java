package com.luretechnologies.tms.ui.view.security;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = SecurityView.VIEW_NAME)
public class SecurityView extends VerticalLayout implements Serializable, View{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1635564334511078896L;
	public static final String VIEW_NAME = "security";
	@Autowired
	public SecurityView() {
		
	}
	
	@PostConstruct
	private void inti() {
		setSpacing(false);
		setMargin(false);
		setResponsive(true);
		Panel panel = new Panel();
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setCaption("<h1 style=color:#216C2A;font-weight:bold;>Security</h1>");
		panel.setResponsive(true);
		panel.setSizeFull();
        addComponent(panel);
	}
}
