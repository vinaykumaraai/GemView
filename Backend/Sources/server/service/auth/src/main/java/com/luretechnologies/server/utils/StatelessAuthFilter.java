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
package com.luretechnologies.server.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luretechnologies.common.Constants;
import com.luretechnologies.conf.spring.RestrictedPath;
import com.luretechnologies.conf.spring.SecurityConfig;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.data.display.ErrorResponse;
import com.luretechnologies.server.service.SessionService;
import static com.luretechnologies.server.utils.TokenAuthService.AUTH_HEADER_NAME;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class StatelessAuthFilter extends GenericFilterBean {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    private final TokenAuthService tokenAuthService;
    private final SecurityConfig securityConfig;
    private final SessionService sessionService;

    /**
     *
     * @param tokenAuthService
     * @param securityConfig
     * @param sessionService
     */
    public StatelessAuthFilter(TokenAuthService tokenAuthService, SecurityConfig securityConfig, SessionService sessionService) {
        this.tokenAuthService = tokenAuthService;
        this.securityConfig = securityConfig;
        this.sessionService = sessionService;
    }

    /**
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     * @throws SignatureException
     * @throws ExpiredJwtException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException, MalformedJwtException, SignatureException, ExpiredJwtException {

        boolean validated = false;

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            if (tokenAuthService.isTokenEmpty(httpRequest)) {
                logger.info("Token is empty.");
                httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                ErrorResponse res = new ErrorResponse(Constants.CODE_BADLOGIN, Messages.HEADER_TOKEN_MISSING);
                setResponse(res, httpResponse);
            } else {

                if (tokenAuthService.isTokenExpired(httpRequest)) {
                    logger.info("Token is expired.");
                    httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                    ErrorResponse res = new ErrorResponse(Constants.CODE_SESSION_EXPIRED, Messages.EXPIRED_TOKEN);
                    setResponse(res, httpResponse);
                    return;
                }

                String tokenIP = tokenAuthService.getAccessIP(httpRequest);

                if (!tokenIP.contentEquals(httpRequest.getRemoteAddr())) {
                    logger.info("IP switch not allowed during session: " + tokenIP + " -> " + httpRequest.getRemoteAddr());
                    httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                    ErrorResponse res = new ErrorResponse(Constants.CODE_IP_SWITCH_IS_NOT_ALLOWED, Messages.IP_SWITCH_NOT_ALLOWED);
                    setResponse(res, httpResponse);
                    return;
                }

                logger.info("--------------------------------------------------------");
                logger.info("SERVLET PATH: " + httpRequest.getServletPath());
                logger.info("TOKEN AUDIENCE: " + tokenAuthService.getAudience(httpRequest));

                // if a restricted path that was validated, ensure we have a correct audience
                for (RestrictedPath rp : securityConfig.restrictedPaths) {
                    if (httpRequest.getServletPath().startsWith(rp.getPath())) {
                        if (!tokenAuthService.getAudience(httpRequest).equals(rp.getAudience())) {
                            logger.info("INVALID AUDIENCE: " + rp.getAudience());
                            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                            ErrorResponse res = new ErrorResponse(Constants.CODE_INVALID_TOKEN_AUDIENCE, Messages.INVALID_TOKEN_AUDIENCE);
                            setResponse(res, httpResponse);
                            return;
                        } else {
                            validated = true;
                        }
                    }
                }

                // if not a restricted path that was validated, ensure we have a correct default audience
                if (!validated) {

                    String audience = tokenAuthService.getAudience(httpRequest);
                    logger.info("CURRENT AUDIENCE: " + audience);

                    // we should have GENERAL_USE audience active.
                    if (!audience.equals(tokenAuthService.AUDIENCE_GENERAL_USE)) {
                        logger.info("SHOULD BE GENERAL_USE, BUT ISN'T: " + audience);
                        httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                        ErrorResponse res = new ErrorResponse(Constants.CODE_INVALID_TOKEN_AUDIENCE, Messages.INVALID_TOKEN_AUDIENCE);
                        setResponse(res, httpResponse);
                        return;
                    }
                }

                try {

                    String token = httpRequest.getHeader(AUTH_HEADER_NAME);
                    UserAuth user = tokenAuthService.getUser(token);
                    sessionService.validateSession(token);

                    Authentication authentication = tokenAuthService.getAuthentication(httpRequest);

                    if (authentication != null) {

                        token = tokenAuthService.refreshToken(httpRequest);

                        if (token != null) {
                            httpResponse.addHeader(TokenAuthService.REFRESH_HEADER_NAME, token);
                            sessionService.updateSession(user.getSystemUser(), token);
                        }
                    }

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                    SecurityContextHolder.getContext().setAuthentication(null);

                } catch (Exception ex) {
                    
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                    
                    httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                    ErrorResponse res = new ErrorResponse(Constants.CODE_SESSION_LOST, ex.getMessage());
                    setResponse(res, httpResponse);
                    return;
                }
            }

        } catch (JwtException jwtException) {
            httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
            ErrorResponse res = ExceptionHandler.handler(jwtException, httpResponse);
            setResponse(res, httpResponse);
        }
    }

    private void setResponse(ErrorResponse res, HttpServletResponse httResponse) throws IOException {
        httResponse.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        httResponse.getOutputStream().println(mapper.writeValueAsString(res));
    }
}
