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
package com.luretechnologies.tms.ui.view.forgotpassword;

import javax.management.Attribute;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.luretechnologies.client.restlib.service.model.UserSession;
import com.luretechnologies.tms.app.Application;
import com.luretechnologies.tms.app.HasLogger;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.luretechnologies.tms.ui.navigation.NavigationManager;
import com.luretechnologies.tms.ui.view.AccessDeniedView;
import com.luretechnologies.tms.ui.view.twofactor.authentication.TwoFactorAuthenticationUI;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Viewport;
import com.vaadin.navigator.View;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.CloseEvent;
import com.vaadin.ui.Notification.CloseListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

@Theme("apptheme")
@SpringUI(path = ForgotPasswordUI.VIEW_NAME)
@Viewport("width=device-width,initial-scale=1.0,user-scalable=no")
@Title("Gem View")
@SpringView
public class ForgotPasswordUI extends UI implements HasLogger, View{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String VIEW_NAME = "forgotpassword";
	
	private final SpringViewProvider viewProvider;
	private final NavigationManager navigationManager;
	//private final TwoFactorView twoFactorview;
	private VerticalLayout vl;
	private Button cancel, sendTempPassword;
	private RestServiceUtil restUtil;
	private HorizontalSplitPanel horizontalPanel;
	private VerticalSplitPanel verticalPanel;
	private ServletContext servletContext;
	private HttpServletResponse response;
	
	@Autowired
	public ForgotPasswordUI(ServletContext servletContext, SpringViewProvider viewProvider, NavigationManager navigationManager/*, TwoFactorView twoFactorview*/) {
		this.navigationManager = navigationManager;
		this.viewProvider = viewProvider;
		this.servletContext = servletContext;
	}

	@Override
	protected void init(VaadinRequest request) {
		// TODO Auto-generated method stub
		
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

		viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
		
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
	
		/*Label twofactorAuthentication = new Label("Two Factor Authentication");
		twofactorAuthentication.addStyleName("twofactor-authlabel");
		twofactorAuthentication.setWidth("100%");
		secondPanelLayout.addComponent(twofactorAuthentication);*/
	
		Label twofactorMailText = new Label("Please type your email address for Temporary Password");
		twofactorMailText.addStyleName("forgotpassword-labelHorizontal");
		twofactorMailText.setWidth("100%");
		secondPanelLayout.addComponent(twofactorMailText);
	
		FormLayout verificationCodeLayout = new FormLayout();
		verificationCodeLayout.addStyleName("twofactor-formLayout");
		TextField emailId = new TextField();
		/*Attribute attribute = new Attribute("spellcheck", "false");
		attribute.e*/
		emailId.focus();
		emailId.setCaptionAsHtml(true);
		emailId.setCaption("<h4 style=color:white;font-weight:bold !important;> Email ID</h4>");
		emailId.addStyleNames(ValoTheme.BUTTON_BORDERLESS, "forgotpassword-emaillabel", "v-textfield-lineHeight");
		verificationCodeLayout.addComponent(emailId);
		secondPanelLayout.addComponent(verificationCodeLayout);
	
		HorizontalLayout buttonlayout = new HorizontalLayout();
		buttonlayout.setWidth("100%");
		buttonlayout.addStyleName("twofactor-buttonsLayout");
		cancel = new Button("Cancel");
		cancel.setWidth("100%");
		cancel.addStyleName("twofactor-buttons");
		cancel.addClickListener(click -> {
			try {
				Page.getCurrent().setLocation(getAbsoluteUrl(Application.LOGIN_URL));
			} catch(Exception e) {
				Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
			}
		});
	
		sendTempPassword = new Button("Continue");
		sendTempPassword.setWidth("100%");
		sendTempPassword.addStyleName("twofactor-buttons");
		sendTempPassword.addClickListener(click -> {
			try {
				RestServiceUtil.getInstance().getClient().getAuthApi().forgotPassword(emailId.getValue());
				Notification tmpPassSentNotify = Notification.show("Temporary Password is sent", Type.ERROR_MESSAGE);
				tmpPassSentNotify.setPosition(Position.TOP_CENTER);
				tmpPassSentNotify.setDelayMsec(5000);
				tmpPassSentNotify.addCloseListener(new CloseListener() {
					
					@Override
					public void notificationClose(CloseEvent e) {
						Page.getCurrent().setLocation(getAbsoluteUrl(Application.LOGIN_URL));
						
					}
				});
			} catch (Exception e) {
				//Dont Navigate.
				Notification.show("Given Email ID is wrong. Please check once again", Type.ERROR_MESSAGE).setPosition(Position.TOP_CENTER);
				e.printStackTrace();
			
			}
		});
	
		buttonlayout.addComponents(cancel, sendTempPassword);
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
				vl.setWidth("100%");
				vl.setHeight("750px");
				vl.addStyleName("twofactor-verticalLayout");
				panel = new VerticalSplitPanel();
				VerticalLayout firstPanelLayout = new VerticalLayout();
				//firstPanelLayout.setHeight("300px");
				firstPanelLayout.setSizeFull();
				VerticalLayout secondPanelLayout = new VerticalLayout();
				secondPanelLayout.setSizeFull();
				//secondPanelLayout.setHeight("395px");
				panel.setSizeFull();
				panel.addStyleName("twofactor-splitpanel");
				panel.setFirstComponent(firstPanelLayout);
				panel.setSecondComponent(secondPanelLayout);
				firstPanelLayout.addStyleName("twofactor-logoVertical");
				panel.getFirstComponent().addStyleName("twofactor-firstPanel");
				panel.getSecondComponent().addStyleName("twofactor-secondPanelVertical");
				panel.setSplitPosition(43);
			
				Label welcome = new Label("Welcome");
				welcome.addStyleName("twofactor-welcomelabel");
				welcome.setWidth("100%");
				secondPanelLayout.addComponent(welcome);
			
				/*Label twofactorAuthentication = new Label("Two Factor Authentication");
				twofactorAuthentication.addStyleName("twofactor-authlabelVertical");
				twofactorAuthentication.setWidth("100%");
				secondPanelLayout.addComponent(twofactorAuthentication);*/
			
				Label twofactorMailText = new Label("Please type your email address for Temporary Password");
				twofactorMailText.addStyleName("forgotpassword-labelHorizontal");
				twofactorMailText.setWidth("100%");
				secondPanelLayout.addComponent(twofactorMailText);
			
				FormLayout verificationCodeLayout = new FormLayout();
				verificationCodeLayout.addStyleName("twofactor-formLayoutVertical");
				TextField emailID = new TextField();
				emailID.focus();
				emailID.setCaptionAsHtml(true);
				emailID.setCaption("<h4 style=color:white;font-weight:bold !important;>Email ID</h4>");
				emailID.addStyleNames(ValoTheme.BUTTON_BORDERLESS, "forgotpassword-emaillabel", "v-textfield-lineHeight");
				verificationCodeLayout.addComponent(emailID);
				secondPanelLayout.addComponent(verificationCodeLayout);
			
				HorizontalLayout buttonlayout = new HorizontalLayout();
				buttonlayout.setWidth("100%");
				buttonlayout.addStyleName("twofactor-buttonsLayout");
				cancel = new Button("Cancel");
				cancel.setWidth("100%");
				cancel.addStyleName("twofactor-buttons");
				cancel.addClickListener(click -> {
					try {
						Page.getCurrent().setLocation(getAbsoluteUrl(Application.LOGIN_URL));
					} catch(Exception e) {
						Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
					}
				});
			
				sendTempPassword = new Button("Continue");
				sendTempPassword.setWidth("100%");
				sendTempPassword.addStyleName("twofactor-buttons");
				sendTempPassword.addClickListener(click -> {
					try {
						RestServiceUtil.getInstance().getClient().getAuthApi().forgotPassword(emailID.getValue());
						Notification tmpPassSentNotify = Notification.show("Temporary Password is sent", Type.ERROR_MESSAGE);
						tmpPassSentNotify.setPosition(Position.TOP_CENTER);
						tmpPassSentNotify.setDelayMsec(5000);
						tmpPassSentNotify.addCloseListener(new CloseListener() {
							
							@Override
							public void notificationClose(CloseEvent e) {
								Page.getCurrent().setLocation(getAbsoluteUrl(Application.LOGIN_URL));
								
							}
						});
					} catch (Exception e) {
						//Dont Navigate.
						Notification.show("Given Email ID is wrong. Please check once again", Type.ERROR_MESSAGE).setPosition(Position.TOP_CENTER);
						e.printStackTrace();
					
					}
				});
			
				buttonlayout.addComponents(cancel, sendTempPassword);
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
