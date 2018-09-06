package com.discovery.view;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.discovery.ui.navigation.NavigationManager;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;

@Theme("apptheme")
@SpringUI(path=MainView.HOME_VIEW)
@Title("Discovery")
public class MainView extends UI {
	
	public static final Logger mainViewLogger = Logger.getLogger(MainView.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String HOME_VIEW = "home";
	
	private final SpringViewProvider viewProvider;

	private final NavigationManager navigationManager;

	private final MenuView menuView;

	@Autowired
	public MainView(SpringViewProvider viewProvider, NavigationManager navigationManager, MenuView menuView) {
		this.viewProvider = viewProvider;
		this.navigationManager = navigationManager;
		this.menuView = menuView;
	}

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		setErrorHandler(event -> {
			Throwable t = DefaultErrorHandler.findRelevantThrowable(event.getThrowable());
			mainViewLogger.error("Error during request", t);
		});

		viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
		setContent(menuView);

		navigationManager.navigateToDefaultView();
	}
}
