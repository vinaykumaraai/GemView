package com.discovery.view.dialog;

import java.io.Serializable;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class User extends DialogWindow{
	VerticalLayout verticalPanelLayout = new VerticalLayout();
	public User() {
		super();
		this.setCaption("Users");
		
	}

	@Override
	public Component getContent() {
		// TODO Auto-generated method stub
		return verticalPanelLayout;
	}
	

}
