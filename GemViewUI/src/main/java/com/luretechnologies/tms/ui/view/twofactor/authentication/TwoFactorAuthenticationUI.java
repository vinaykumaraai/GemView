/**
 * COPYRIGHT @ Lure Technologies, LLC.
 * ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or
 * form other than in accordance with and subject to the terms of a written
 * license from Lure or with the prior written consent of Lure or as
 * permitted by applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure.  If you are neither the
 * intended recipient, nor an agent, employee, nor independent contractor
 * responsible for delivering this message to the intended recipient, you are
 * prohibited from copying, disclosing, distributing, disseminating, and/or
 * using the information in this email in any manner. If you have received
 * this message in error, please advise us immediately at
 * legal@luretechnologies.com by return email and then delete the message from your
 * computer and all other records (whether electronic, hard copy, or
 * otherwise).
 *
 * Any copies or reproductions of this software program (in whole or in part)
 * made by any method must also include a copy of this legend.
 *
 * Inquiries should be made to legal@luretechnologies.com
 *
 */
package com.luretechnologies.tms.ui.view.twofactor.authentication;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.UserSession;
import com.luretechnologies.tms.app.Application;
import com.luretechnologies.tms.app.HasLogger;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.luretechnologies.tms.ui.AppUI;
import com.luretechnologies.tms.ui.navigation.NavigationManager;
import com.luretechnologies.tms.ui.view.AccessDeniedView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Viewport;
import com.vaadin.navigator.View;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@Theme("apptheme")
@SpringUI(path = TwoFactorAuthenticationUI.VIEW_NAME)
//@Viewport("width=device-width,initial-scale=1.0,user-scalable=no")
@Title("Gem View")
public class TwoFactorAuthenticationUI extends UI implements HasLogger{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String VIEW_NAME = "twofactorauthenticationhome";
//	private final SpringViewProvider viewProvider;
//	private final NavigationManager navigationManager;
	//private final TwoFactorView twoFactorview;
	
	private HttpServletResponse response;
	private ServletContext servletContext;
	private VerticalLayout vl;
	private Button resendEmail, authenticate;
	private RestServiceUtil restUtil;
	private HorizontalSplitPanel horizontalPanel;
	private VerticalSplitPanel verticalPanel;
	@Autowired
	public TwoFactorAuthenticationUI(ServletContext servletContext,SpringViewProvider viewProvider,NavigationManager navigationManager/*, TwoFactorView twoFactorview*/) {
//		this.navigationManager = navigationManager;
		//this.twoFactorview=twoFactorview;
//		this.viewProvider = viewProvider;
		this.servletContext = servletContext;
	}

	@Override
	protected void init(VaadinRequest request) {
		UserSession session = restUtil.getSESSION();
		Page.getCurrent().addBrowserWindowResizeListener(r->{
			if(r.getWidth()>=1000) {
				getHorizontalPanel(horizontalPanel, session, vl);
			} else if(r.getWidth()<1000) {
				getVerticalPanel(verticalPanel, session, vl);
			}
		});
	
		int width = Page.getCurrent().getBrowserWindowWidth();
		if(width>=1000) {
			getHorizontalPanel(horizontalPanel, session, vl);
		} else if(width<1000) {
			getVerticalPanel(verticalPanel, session, vl);
		}
		
		setErrorHandler(event -> {
			Throwable t = DefaultErrorHandler.findRelevantThrowable(event.getThrowable());
			getLogger().error("Error during request", t);
		});

//		viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
		
		//vl= new VerticalLayout();
	} 
	
	public void getHorizontalPanel(HorizontalSplitPanel panel, UserSession session,
			VerticalLayout vl) {
	  if(session!=null) {
		vl = new VerticalLayout();
		vl.setSpacing(false);
		vl.setMargin(false);
		vl.setResponsive(true);
		vl.setSizeFull();
		vl.addStyleName("twofactor-verticalLayout");
		panel = new HorizontalSplitPanel();
		VerticalLayout firstPanelLayout = new VerticalLayout();
		firstPanelLayout.setSizeFull();
		VerticalLayout secondPanelLayout = new VerticalLayout();
		secondPanelLayout.setWidth("100%");;
		panel.setHeight("420px");
		panel.setWidth("700px");
		panel.addStyleName("twofactor-splitpanel");
		panel.setFirstComponent(firstPanelLayout);
		panel.setSecondComponent(secondPanelLayout);
		firstPanelLayout.addStyleName("twofactor-logo");
		panel.getFirstComponent().addStyleName("twofactor-firstPanel");
		panel.getSecondComponent().addStyleName("twofactor-secondPanel");
		panel.setSplitPosition(43);
	
		Label welcome = new Label("Welcome");
		welcome.addStyleName("twofactor-welcomelabel");
		welcome.setWidth("100%");
		secondPanelLayout.addComponent(welcome);
	
		Label twofactorAuthentication = new Label("Two Factor Authentication");
		twofactorAuthentication.addStyleName("twofactor-authlabel");
		twofactorAuthentication.setWidth("100%");
		secondPanelLayout.addComponent(twofactorAuthentication);
	
		Label twofactorMailText = new Label("A verification code has been sent to "
			+ "your email at "+session.getMaskedEmailAddress());
		twofactorMailText.addStyleName("twofactor-maillabel");
		twofactorMailText.setWidth("100%");
		secondPanelLayout.addComponent(twofactorMailText);
	
		FormLayout verificationCodeLayout = new FormLayout();
		verificationCodeLayout.addStyleName("twofactor-formLayout");
		TextField verificationCode = new TextField();
		verificationCode.focus();
		verificationCode.setCaptionAsHtml(true);
		verificationCode.setCaption("<h4 style=color:white;font-weight:bold !important;> Verification Code</h4>");
		verificationCode.addStyleNames(ValoTheme.BUTTON_BORDERLESS, "twofactor-label");
		verificationCodeLayout.addComponent(verificationCode);
		secondPanelLayout.addComponent(verificationCodeLayout);
	
		HorizontalLayout buttonlayout = new HorizontalLayout();
		buttonlayout.setWidth("100%");
		buttonlayout.addStyleName("twofactor-buttonsLayout");
		resendEmail = new Button("Resend Email");
		resendEmail.setWidth("100%");
		resendEmail.addStyleName("twofactor-buttons");
		resendEmail.addClickListener(new ClickListener() {
		public void buttonClick(ClickEvent event) {
			
			}
		});
	
		authenticate = new Button("Authenticate");
		authenticate.setWidth("100%");
		authenticate.addStyleName("twofactor-buttons");
		authenticate.addClickListener(click -> {
			//FIXME: Add Two Factor Code Check
//			navigationManager.navigateToDefaultView();
			try {
				//RestServiceUtil.getInstance().getClient().getAuthApi().verifyCode(verificationCode.getValue());
				Page.getCurrent().setLocation(getAbsoluteUrl(Application.APP_URL+"home"));
			} catch (Exception e) {
				//Dont Navigate.
				e.printStackTrace();
			}
		});
	
		buttonlayout.addComponents(resendEmail, authenticate);
		secondPanelLayout.addComponent(buttonlayout);
	
		vl.addComponent(panel);
		vl.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		setContent(vl);
	//navigationManager.navigateToTwoFactorView();
	  }
		else {
			throw new NullPointerException("Session is null");
			
		}
	  
	  }
	
	public void getVerticalPanel(VerticalSplitPanel panel, UserSession session,
			VerticalLayout vl) {
		 if(session!=null) {
			 	vl = new VerticalLayout();
			 	vl.setSpacing(false);
				vl.setMargin(false);
				vl.setResponsive(true);
				vl.setSizeFull();
				vl.addStyleName("twofactor-verticalLayout");
				panel = new VerticalSplitPanel();
				VerticalLayout firstPanelLayout = new VerticalLayout();
				firstPanelLayout.setSizeFull();
				VerticalLayout secondPanelLayout = new VerticalLayout();
				secondPanelLayout.setWidth("100%");;
				panel.setHeight("420px");
				panel.setWidth("700px");
				panel.addStyleName("twofactor-splitpanel");
				panel.setFirstComponent(firstPanelLayout);
				panel.setSecondComponent(secondPanelLayout);
				firstPanelLayout.addStyleName("twofactor-logo");
				panel.getFirstComponent().addStyleName("twofactor-firstPanel");
				panel.getSecondComponent().addStyleName("twofactor-secondPanel");
				panel.setSplitPosition(43);
			
				Label welcome = new Label("Welcome");
				welcome.addStyleName("twofactor-welcomelabel");
				welcome.setWidth("100%");
				secondPanelLayout.addComponent(welcome);
			
				Label twofactorAuthentication = new Label("Two Factor Authentication");
				twofactorAuthentication.addStyleName("twofactor-authlabel");
				twofactorAuthentication.setWidth("100%");
				secondPanelLayout.addComponent(twofactorAuthentication);
			
				Label twofactorMailText = new Label("A verification code has been sent to "
					+ "your email at "+session.getMaskedEmailAddress());
				twofactorMailText.addStyleName("twofactor-maillabel");
				twofactorMailText.setWidth("100%");
				secondPanelLayout.addComponent(twofactorMailText);
			
				FormLayout verificationCodeLayout = new FormLayout();
				verificationCodeLayout.addStyleName("twofactor-formLayout");
				TextField verificationCode = new TextField();
				verificationCode.focus();
				verificationCode.setCaptionAsHtml(true);
				verificationCode.setCaption("<h4 style=color:white;font-weight:bold !important;> Verification Code</h4>");
				verificationCode.addStyleNames(ValoTheme.BUTTON_BORDERLESS, "twofactor-label");
				verificationCodeLayout.addComponent(verificationCode);
				secondPanelLayout.addComponent(verificationCodeLayout);
			
				HorizontalLayout buttonlayout = new HorizontalLayout();
				buttonlayout.setWidth("100%");
				buttonlayout.addStyleName("twofactor-buttonsLayout");
				resendEmail = new Button("Resend Email");
				resendEmail.setWidth("100%");
				resendEmail.addStyleName("twofactor-buttons");
				resendEmail.addClickListener(new ClickListener() {
				public void buttonClick(ClickEvent event) {
					
					}
				});
			
				authenticate = new Button("Authenticate");
				authenticate.setWidth("100%");
				authenticate.addStyleName("twofactor-buttons");
				authenticate.addClickListener(new ClickListener() {
				public void buttonClick(ClickEvent event) {
					
					}
				});
			
				buttonlayout.addComponents(resendEmail, authenticate);
				secondPanelLayout.addComponent(buttonlayout);
			
				vl.addComponent(panel);
				vl.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
				setContent(vl);
			//navigationManager.navigateToTwoFactorView();
			  }
				else {
					throw new NullPointerException("Session is null");
					
				}
	}
	  
	private String getAbsoluteUrl(String url) {
		final String relativeUrl;
		if (url.startsWith("/")) {
			relativeUrl = url.substring(1);
		} else {
			relativeUrl = url;
		}
		return servletContext.getContextPath() + "/" + relativeUrl;
	}	
}
