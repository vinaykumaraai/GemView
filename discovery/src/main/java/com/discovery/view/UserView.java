package com.discovery.view;

import java.io.Serializable;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = UserView.VIEW_NAME)
public class UserView extends VerticalLayout  implements Serializable, View{
	public static final String VIEW_NAME = "user";

}
