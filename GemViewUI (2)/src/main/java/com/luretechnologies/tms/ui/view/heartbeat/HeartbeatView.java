package com.luretechnologies.tms.ui.view.heartbeat;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = HeartbeatView.VIEW_NAME)
public class HeartbeatView extends VerticalLayout implements Serializable, View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4663357355780258257L;
	public static final String VIEW_NAME = "heartbeat";
	
	@Autowired
	public HeartbeatView() {
		
	}
	
	@PostConstruct
	private void inti() {
		setSpacing(false);
		setMargin(false);
		setResponsive(true);
		Panel panel = new Panel();
		panel.addStyleName(ValoTheme.PANEL_WELL);
		panel.setCaptionAsHtml(true);
		panel.setCaption("<h1 style=color:#216C2A;font-weight:bold;>Heartbeat</h1>");
		panel.setResponsive(true);
		panel.setSizeFull();
        addComponent(panel);
	}
}
