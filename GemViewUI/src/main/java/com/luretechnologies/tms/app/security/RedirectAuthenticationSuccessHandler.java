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

package com.luretechnologies.tms.app.security;

import com.luretechnologies.client.restlib.service.model.UserSession;
import com.luretechnologies.tms.app.Application;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.luretechnologies.tms.ui.MainView;
import com.luretechnologies.tms.ui.view.dashboard.DashboardView;
import com.vaadin.spring.annotation.SpringComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties.Session.Cookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.context.annotation.ApplicationScope;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Redirects to the application after successful authentication.
 */
@SpringComponent
@ApplicationScope
public class RedirectAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private String location;
	private final String TWO_FACTOR = "doTwoFactor";
	private final String PASSWORD_REQUIRED = "doPasswordUpdate";
	private final String TWOFACTOR_PASSWORD = "doTwoFactorAndPassword";
	private List<String> authList = new ArrayList<>();

	@Autowired
	private ServletContext servletContext;
	
	private MainView mainView;

	public RedirectAuthenticationSuccessHandler() {
		UserSession session = RestServiceUtil.getSESSION();
		if(session!=null) {

			if(!(session.isPerformTwoFactor() || session.isRequirePasswordUpdate())) {
				  location = Application.APP_URL+"home";
			}
			
			if(session.isPerformTwoFactor()) {
				authList.add(TWO_FACTOR);
			}
			
			if(session.isRequirePasswordUpdate()) {
				authList.add(PASSWORD_REQUIRED);
			}
			
			if(session.isPerformTwoFactor() && session.isRequirePasswordUpdate()) {
				authList.add(TWOFACTOR_PASSWORD);
			}
		}
		
		  for(String operation:authList){
			  switch(operation) {
			  case TWO_FACTOR:
				  location = Application.APP_URL + "twofactorauthenticationhome";
				  break;
			  case PASSWORD_REQUIRED:
				  location = Application.APP_URL + "updatepassword";
				  break;
			  case TWOFACTOR_PASSWORD:
				  location = Application.APP_URL + "updatepassword";
				  break;
			  default:
				  break;
			  }
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

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		response.sendRedirect(getAbsoluteUrl(location));

	}

}
