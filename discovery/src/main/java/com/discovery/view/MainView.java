package com.discovery.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.navigator.SpringViewProvider;

@Theme("apptheme")
@SpringUI(path=MainView.HOME_VIEW)
@Title("Discovery")
public class MainView extends UI {
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
			getLogger().error("Error during request", t);
		});

		viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
		setContent(mainView);

		navigationManager.navigateToDefaultView();
	}
}
