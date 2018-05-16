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
package com.luretechnologies.conf.spring;

import com.luretechnologies.server.service.SessionService;
import com.luretechnologies.server.utils.StatelessAuthFilter;
import com.luretechnologies.server.utils.TokenAuthService;
import com.luretechnologies.server.utils.UserAuthService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author developer
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SessionService sessionService;

    private final UserAuthService userAuthService;
    private final TokenAuthService tokenAuthService;
    public final List<RestrictedPath> restrictedPaths;

    /**
     *
     */
    public SecurityConfig() {
        super(true);
        this.userAuthService = new UserAuthService();
        this.tokenAuthService = new TokenAuthService(userAuthService);
        restrictedPaths = new ArrayList<>();
    }

    /**
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().and()
                .anonymous().and()
                .servletApi().and()
                .headers().cacheControl();

        // Allow anonymous resource requests
        http.authorizeRequests()
                //                .antMatchers("/").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("**/*.html").permitAll()
                .antMatchers("**/*.css").permitAll()
                .antMatchers("**/*.js").permitAll()
                // Allow anonymous logins
                //                .antMatchers("/auth/**").permitAll()
                //                .antMatchers("/health/**").permitAll()
                //                .antMatchers("/api-docs/**").permitAll()

                // All other request need to be authenticated
                .anyRequest().authenticated().and()
                // Custom Token based authentication based on the header previously given to the client
                .addFilterBefore(new StatelessAuthFilter(tokenAuthService, this, sessionService), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                // All of Spring Security will ignore the requests
                .antMatchers("/api-docs/**")
                .antMatchers("/debug/**")
                .antMatchers("/heartbeat/**")
                .antMatchers("/health/**")
                .antMatchers("/auth/**")
                .antMatchers("/");

        restrictedPaths.add(new RestrictedPath("/auth", tokenAuthService.AUDIENCE_TWO_FACTOR));
    }

    /**
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     *
     * @return @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     *
     * @return
     */
    @Bean
    @Override
    public UserAuthService userDetailsService() {
        return userAuthService;
    }

    /**
     *
     * @return
     */
    @Bean
    public TokenAuthService tokenAuthenticationService() {
        return tokenAuthService;
    }
}
