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

import com.luretechnologies.server.common.utils.Utils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import java.sql.Timestamp;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

public class TokenAuthService {

    public final String AUDIENCE_NO_ACCESS = "NO_ACCESS";
    public final String AUDIENCE_GENERAL_USE = "GENERAL_USE";
    public final String AUDIENCE_TWO_FACTOR = "TWO_FACTOR";
    public final String AUDIENCE_PASSWORD_UPDATE = "UPDATE_PW";

    public static final String AUTH_HEADER_NAME = "X-Auth-Token";
    public static final String REFRESH_HEADER_NAME = "X-Auth-Token";
    private final TokenHandler tokenHandler;

    /**
     *
     * @param userAuthService
     */
    public TokenAuthService(UserAuthService userAuthService) {
        this.tokenHandler = new TokenHandler(userAuthService);
    }

    /**
     *
     * @param authentication
     * @param accessIP
     * @param audience
     * @return
     * @throws Exception
     */
    public String createToken(UserAuthentication authentication, String accessIP, String audience) throws MalformedJwtException, SignatureException, Exception {
        final User user = authentication.getDetails();
        return tokenHandler.createTokenForUser(user, accessIP, audience);
    }

    /**
     * The authentication filter is calling getAuthentication(...) though, which
     * looks for a header that uses the same key “X-AUTH-TOKEN” and uses that
     * token to re-establish the authenticating user. If this method throws an
     * exception or returns null then the user will not be authenticated and the
     * request will ultimately fail.
     *
     * @param request
     * @return
     *
     */
    public Authentication getAuthentication(HttpServletRequest request) throws MalformedJwtException, SignatureException, ExpiredJwtException {
        final String token = request.getHeader(AUTH_HEADER_NAME);

        if (token != null) {
            final UserAuth user = tokenHandler.getUserFromToken(token);
            if (user != null) {
                Timestamp logout = user.getSystemUser().getLogoutTime();

                if (logout != null) {
                    //Date logoutDate = new java.sql.Date(logout.getTime());
                    Timestamp issuedAt = new Timestamp(tokenHandler.getIssuedAtFromToken(token).getTime());
                    // if issuedAt >= logout then the token is correct, in other case the token is invalid and the user needs to do a login.
                    if (issuedAt.compareTo(logout) >= 0) {
                        return new UserAuthentication(user);
                    } else {
                        throw new SignatureException("INVALID TOKEN");
                    }
                } else { // if logout == null it hasn't done logout
                    return new UserAuthentication(user);
                }
            }
        }
        return null;
    }

    /**
     *
     * @param request
     * @return
     * @throws ExpiredJwtException
     * @throws MalformedJwtException
     * @throws SignatureException
     * @throws java.lang.Exception
     */
    public UserAuth getUser(HttpServletRequest request) throws ExpiredJwtException, MalformedJwtException, SignatureException, Exception {
        String token = request.getHeader(AUTH_HEADER_NAME);
        return tokenHandler.getUserFromToken(token);
    }

    /**
     *
     * @param request
     * @return
     * @throws ExpiredJwtException
     * @throws MalformedJwtException
     * @throws SignatureException
     */
    public String getAccessIP(HttpServletRequest request) throws ExpiredJwtException, MalformedJwtException, SignatureException {
        String token = request.getHeader(AUTH_HEADER_NAME);
        return tokenHandler.getAccessIPFromToken(token);
    }

    /**
     *
     * @param token
     * @return
     * @throws ExpiredJwtException
     * @throws MalformedJwtException
     * @throws SignatureException
     * @throws java.lang.Exception
     */
    public UserAuth getUser(String token) throws ExpiredJwtException, MalformedJwtException, SignatureException, Exception {
        return tokenHandler.getUserFromToken(token);
    }

    /**
     *
     * @param request
     * @return
     * @throws ExpiredJwtException
     * @throws MalformedJwtException
     * @throws SignatureException
     */
    public String getAudience(HttpServletRequest request) throws MalformedJwtException, SignatureException {
        try {
            String token = request.getHeader(AUTH_HEADER_NAME);
            return tokenHandler.getAudienceFromToken(token);
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     *
     * @param request
     * @return
     */
    public boolean isTokenExpired(HttpServletRequest request) {
        return tokenHandler.isTokenExpired(request.getHeader(AUTH_HEADER_NAME));
    }

    /**
     *
     * @param request
     * @return
     * @throws ExpiredJwtException
     */
    public String refreshToken(HttpServletRequest request) throws ExpiredJwtException, MalformedJwtException, SignatureException {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null && !isTokenEmpty(request) && !isTokenExpired(request)) {
//            Date current = new Date();
//            Date expiration = tokenHandler.getExpirationFromToken(token);
            // if (current.compareTo(expiration) < 0 && (current.getTime() + Utils.USER_SESSION_TIME_LIMIT) >= expiration.getTime()) {
            return tokenHandler.refreshTokenForUser(token, AUDIENCE_GENERAL_USE);
            //}
        }

        return null;
    }

    /**
     *
     * @param request
     * @return
     */
    public boolean isTokenEmpty(HttpServletRequest request) {
        return request.getHeader(AUTH_HEADER_NAME) == null;
    }
}
