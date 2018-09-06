package com.discovery.ui.dialog;

import org.springframework.stereotype.Component;

import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

@Component
public class DialogManager {

	public void open(Window window) {
		if (UI.getCurrent().getWindows().contains(window)) {
			Notification.show("Window already added");
		} else {
			UI.getCurrent().addWindow(window);
		}
	}

}
