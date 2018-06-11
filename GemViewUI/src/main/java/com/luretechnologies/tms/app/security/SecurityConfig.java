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

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.luretechnologies.tms.app.Application;
import com.luretechnologies.tms.backend.data.Role;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements Serializable{

//	private final UserDetailsService userDetailsService;

	private final PasswordEncoder passwordEncoder;

	private final RedirectAuthenticationSuccessHandler successHandler;
	
	private final ForgotPasswordHandler forgotPasswordHandler;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//                   .withUser("test").password("123456").roles("ADMIN");
		super.configure(auth);
		auth.authenticationProvider(new BackendAuthenticationProvider());
		//getHttp().antMatcher("/gemview/forgotpassowrd").anonymous();
		
	}


	//FIXME: This config is not required anymore.
	@Autowired
	public SecurityConfig( PasswordEncoder passwordEncoder,
			RedirectAuthenticationSuccessHandler successHandler, ForgotPasswordHandler forgotPasswordHandler) {
//		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
		this.successHandler = successHandler;
		this.forgotPasswordHandler = forgotPasswordHandler;
	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		super.configure(auth);
//		auth.authenticationProvider(new BackendAuthenticationProvider());
//		//auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Not using Spring CSRF here to be able to use plain HTML for the login
		// page
		http.csrf().disable();
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry reg = http
				.authorizeRequests();
		

		// Allow access to static resources ("/VAADIN/**")
		reg = reg.antMatchers("/VAADIN/**").permitAll();
		//reg = reg.antMatchers("/forgotPassword.html").permitAll();
		reg = reg.antMatchers("/gemview/forgotpassword").permitAll();
		reg = reg.antMatchers("/**").hasAnyAuthority(Role.getAllRoles());
		//reg= reg.antMatchers("/login*").anonymous().anyRequest().authenticated();
		//reg = reg.antMatchers("/**").authenticated();
		//reg = reg.antMatchers("/gemview/forgotpassword").permitAll();
		HttpSecurity sec = reg.and();

		// Allow access to login page without login
			FormLoginConfigurer<HttpSecurity> login = sec.formLogin().permitAll();
			login = login.loginPage(Application.LOGIN_URL).loginProcessingUrl(Application.LOGIN_PROCESSING_URL)
				.failureUrl(Application.LOGIN_FAILURE_URL).successHandler(successHandler);
			login.and().logout().logoutSuccessUrl(Application.LOGOUT_URL);
		
		//sec.antMatcher("/gemview/forgotpassowrd");
	}

}
