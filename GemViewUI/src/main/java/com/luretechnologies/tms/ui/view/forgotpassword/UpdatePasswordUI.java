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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.luretechnologies.client.restlib.service.model.UserSession;
import com.luretechnologies.tms.app.Application;
import com.luretechnologies.tms.app.HasLogger;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.luretechnologies.tms.ui.navigation.NavigationManager;
import com.luretechnologies.tms.ui.view.AccessDeniedView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Viewport;
import com.vaadin.icons.VaadinIcons;
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
import com.vaadin.ui.Notification.CloseEvent;
import com.vaadin.ui.Notification.CloseListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.themes.ValoTheme;

@Theme("apptheme")
@SpringUI(path = UpdatePasswordUI.VIEW_NAME)
@Viewport("width=device-width,initial-scale=1.0,user-scalable=no")
@Title("Gem View")
@SpringView
public class UpdatePasswordUI extends UI implements HasLogger, View{

	private static final long serialVersionUID = 1L;
	public static final String VIEW_NAME = "updatepassword";
	
	private final SpringViewProvider viewProvider;
	private final NavigationManager navigationManager;
	//private final TwoFactorView twoFactorview;
	private VerticalLayout vl;
	private Button updatePassword;
	private RestServiceUtil restUtil;
	private HorizontalSplitPanel horizontalPanel;
	private VerticalSplitPanel verticalPanel;
	private ServletContext servletContext;
	private HttpServletResponse response;
	

	@Autowired
	public UpdatePasswordUI(ServletContext servletContext, SpringViewProvider viewProvider, NavigationManager navigationManager/*, TwoFactorView twoFactorview*/) {
		this.navigationManager = navigationManager;
		this.viewProvider = viewProvider;
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
		panel.setHeight("502px");
		panel.setWidth("800px");
		panel.addStyleName("twofactor-splitpanel");
		panel.setFirstComponent(firstPanelLayout);
		panel.setSecondComponent(secondPanelLayout);
		firstPanelLayout.addStyleName("updatePassword-logo");
		panel.getFirstComponent().addStyleName("twofactor-firstPanel");
		panel.getSecondComponent().addStyleName("twofactor-secondPanel");
		panel.setSplitPosition(43);
	
		Label welcome = new Label("Welcome");
		welcome.addStyleName("twofactor-welcomelabel");
		welcome.setWidth("100%");
		secondPanelLayout.addComponent(welcome);
	
		Label twofactorMailText = new Label("Please enter below details");
		twofactorMailText.addStyleName("forgotpassword-labelHorizontal");
		twofactorMailText.setWidth("100%");
		secondPanelLayout.addComponent(twofactorMailText);
		
		FormLayout verificationCodeLayout = new FormLayout();
		verificationCodeLayout.addStyleNames("system-LabelAlignment", "updatePassword-formLayout");
		
		PasswordField tempPassword = new PasswordField();
		tempPassword.setCaptionAsHtml(true);
		tempPassword.focus();
		tempPassword.setCaption("<h4 style=color:white;font-weight:bold !important;>Temporary<br>Password</h4>");
		tempPassword.addStyleNames(ValoTheme.BUTTON_BORDERLESS, "forgotpassword-emaillabel", "verfication-password-lineheight");
		
		PasswordField newPassword = new PasswordField();
		newPassword.setCaptionAsHtml(true);
		newPassword.setCaption("<h4 style=color:white;font-weight:bold !important;>New<br>Passowrd</h4>");
		newPassword.addStyleNames(ValoTheme.BUTTON_BORDERLESS, "forgotpassword-emaillabel", "verfication-password-lineheight");
		
		PasswordField confirmPassword = new PasswordField();
		confirmPassword.setCaptionAsHtml(true);
		confirmPassword.setCaption("<h4 style=color:white;font-weight:bold !important;>Confirm<br>Password</h4>");
		confirmPassword.addStyleNames(ValoTheme.BUTTON_BORDERLESS, "forgotpassword-emaillabel", "verfication-password-lineheight");
		
		verificationCodeLayout.addComponents(tempPassword, newPassword, confirmPassword);
		secondPanelLayout.addComponent(verificationCodeLayout);
	
		HorizontalLayout buttonlayout = new HorizontalLayout();
		buttonlayout.setWidth("100%");
		buttonlayout.addStyleName("updatePassword-buttonsLayout");
	
		updatePassword = new Button("Update Password");
		updatePassword.addStyleName("twofactor-buttons");
		updatePassword.addClickListener(click -> {
			try {
				if(newPassword.getValue().equals(confirmPassword.getValue())) {
					RestServiceUtil.getInstance().getClient().getAuthApi().updatePassword(session.getMaskedEmailAddress(), tempPassword.getValue(), newPassword.getValue());
					Notification passUpdateNotify = Notification.show("Passoword is Updated", Type.ERROR_MESSAGE);
					passUpdateNotify.setPosition(Position.TOP_CENTER);
					passUpdateNotify.setDelayMsec(5000);
					passUpdateNotify.addCloseListener(new CloseListener() {
						
						@Override
						public void notificationClose(CloseEvent e) {
							Page.getCurrent().setLocation(getAbsoluteUrl(Application.APP_URL+"home"));
							
						}
					});
				}else {
					Notification passNotMatch = Notification.show("New Password and Confirm Password Doesn't match", Type.ERROR_MESSAGE);
					passNotMatch.setPosition(Position.TOP_CENTER);
					passNotMatch.setDelayMsec(5000);
				}
			} catch (Exception e) {
				//Dont Navigate.
				Notification.show("Entered Temp Password is wrong", Type.ERROR_MESSAGE).setPosition(Position.TOP_CENTER);
				e.printStackTrace();
			
			}
		});
	
		buttonlayout.addComponents(updatePassword);
		buttonlayout.setComponentAlignment(updatePassword, Alignment.MIDDLE_RIGHT);
		secondPanelLayout.addComponent(buttonlayout);
	
		vl.addComponent(panel);
		vl.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		setContent(vl);
	  }
		else {
			throw new NullPointerException("Session is null");
			
		}
	  
	  }
	
	public void getVerticalPanel(VerticalSplitPanel panel, UserSession session,
			VerticalLayout vl) {
		 if(session!=null) {
				VerticalLayout firstvl = new VerticalLayout();
				firstvl.addStyleName("twofactor-firstverticalLayout");
				firstvl.setSizeFull();
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
				firstvl.addComponent(secondPanelLayout);
				firstvl.setComponentAlignment(secondPanelLayout, Alignment.MIDDLE_CENTER);
				secondPanelLayout.setWidth("350px");
				secondPanelLayout.addStyleName("twofactor-secondPanelVertical");
				//secondPanelLayout.setSizeFull();
				//secondPanelLayout.setHeight("395px");
				panel.setSizeFull();
				panel.addStyleName("twofactor-splitpanel");
				panel.setFirstComponent(firstPanelLayout);
				panel.setSecondComponent(firstvl);
				firstPanelLayout.addStyleName("twofactor-logoVertical");
				panel.getFirstComponent().addStyleName("twofactor-firstPanel");
				panel.getSecondComponent().addStyleName("twofactor-secondPanelVertical");
				panel.setSplitPosition(43);
			
				Label welcome = new Label("Welcome");
				welcome.addStyleName("twofactor-welcomelabel");
				welcome.setWidth("100%");
				secondPanelLayout.addComponent(welcome);
			
			
				Label twofactorMailText = new Label("Please enter below details");
				twofactorMailText.addStyleName("forgotpassword-labelVertical");
				twofactorMailText.setWidth("100%");
				secondPanelLayout.addComponent(twofactorMailText);
			
				FormLayout verificationCodeLayout = new FormLayout();
				verificationCodeLayout.addStyleNames("updatepassword-formLayoutVertical", "system-LabelAlignment");
				PasswordField tempPassword = new PasswordField();
				tempPassword.setCaptionAsHtml(true);
				tempPassword.focus();
				tempPassword.setCaption("<h4 style=color:white;font-weight:bold !important;>Temporary<br>Password</h4>");
				tempPassword.addStyleNames(ValoTheme.BUTTON_BORDERLESS, "forgotpassword-emaillabel", "verfication-password-lineheight");
				
				PasswordField newPassword = new PasswordField();
				newPassword.setCaptionAsHtml(true);
				newPassword.setCaption("<h4 style=color:white;font-weight:bold !important;>New<br>Passowrd</h4>");
				newPassword.addStyleNames(ValoTheme.BUTTON_BORDERLESS, "forgotpassword-emaillabel", "verfication-password-lineheight");
				
				PasswordField confirmPassword = new PasswordField();
				confirmPassword.setCaptionAsHtml(true);
				confirmPassword.setCaption("<h4 style=color:white;font-weight:bold !important;>Confirm<br>Password</h4>");
				confirmPassword.addStyleNames(ValoTheme.BUTTON_BORDERLESS, "forgotpassword-emaillabel", "verfication-password-lineheight");
				
				verificationCodeLayout.addComponents(tempPassword, newPassword, confirmPassword);
				secondPanelLayout.addComponent(verificationCodeLayout);
			
				HorizontalLayout buttonlayout = new HorizontalLayout();
				buttonlayout.setWidth("100%");
				buttonlayout.addStyleName("twofactor-buttonsLayout");
			
				updatePassword = new Button("Update Password");
				updatePassword.addStyleName("twofactor-buttons");
				updatePassword.addClickListener(click -> {
					try {
						if(newPassword.getValue().equals(confirmPassword.getValue())) {
							RestServiceUtil.getInstance().getClient().getAuthApi().updatePassword(session.getMaskedEmailAddress(), tempPassword.getValue(), newPassword.getValue());
							Notification passUpdateNotify = Notification.show("Passoword is Updated", Type.ERROR_MESSAGE);
							passUpdateNotify.setPosition(Position.TOP_CENTER);
							passUpdateNotify.setDelayMsec(5000);
							passUpdateNotify.addCloseListener(new CloseListener() {
								
								@Override
								public void notificationClose(CloseEvent e) {
									Page.getCurrent().setLocation(getAbsoluteUrl(Application.APP_URL+"home"));
									
								}
							});
						}else {
							Notification passNotMatch = Notification.show("New Password and Confirm Password Doesn't match", Type.ERROR_MESSAGE);
							passNotMatch.setPosition(Position.TOP_CENTER);
							passNotMatch.setDelayMsec(5000);
						}
					} catch (Exception e) {
						//Dont Navigate.
						Notification.show("Entered Temp Password is wrong", Type.ERROR_MESSAGE).setPosition(Position.TOP_CENTER);
						e.printStackTrace();
					
					}
				});
			
			
				buttonlayout.addComponents(updatePassword);
				buttonlayout.setComponentAlignment(updatePassword, Alignment.MIDDLE_RIGHT);
				secondPanelLayout.addComponent(buttonlayout);
			
				vl.addComponent(panel);
				vl.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
				setContent(vl);
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
