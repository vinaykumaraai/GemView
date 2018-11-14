package com.luretechnologies.tms.ui.view;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * 
 * @author Vinay
 *
 */

public class ContextMenuWindow extends Window {

	private VerticalLayout menuLayout;
	public ContextMenuWindow() {
		this.setClosable(false);
		this.setResizable(false);
		this.setSizeUndefined();
		this.setDraggable(false);
		this.setPosition(0, 0);
		this.setContent(getMenuLayout());
	}
	
	private Component getMenuLayout() {
		menuLayout = new VerticalLayout();
		return menuLayout;
	}
	public void addMenuItems(Component...components) {
		if(menuLayout != null) {
			menuLayout.addComponents(components);
		}
	}
}
