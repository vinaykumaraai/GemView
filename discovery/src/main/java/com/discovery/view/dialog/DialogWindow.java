package com.discovery.view.dialog;

import com.vaadin.ui.Window;

public class DialogWindow extends Window {

	public DialogWindow() {
		this.setModal(true);
		this.setClosable(true);
		this.setResizable(true);
		this.setDraggable(true);
		this.setHeight("30%");
		this.setWidth("50%");
//		this.setPosition(50,50);
	}
}
