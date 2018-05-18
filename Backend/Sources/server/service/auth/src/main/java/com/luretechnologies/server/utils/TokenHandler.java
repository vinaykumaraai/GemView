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
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.util.Date;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;

public class TokenHandler {

    private final byte[] secret_key = Base64.getDecoder().decode(Utils.HASH_SIGNING_KEY);
    private final UserAuthService userAuthService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *
     * @param userAuthService
     */
    public TokenHandler(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    /**
     *
     * @param user
     * @param audience
     * @return
     * @throws ExpiredJwtException
     * @throws MalformedJwtException
     * @throws SignatureException
     */
    public String createTokenForUser(User user, String audience) throws ExpiredJwtException, MalformedJwtException, SignatureException {

        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject(user.getUsername())
                .setAudience(audience)
                .signWith(SignatureAlgorithm.HS512, secret_key)
                .setExpiration(new Date(Utils.fromNow(Utils.USER_SESSION_TIME_LIMIT)));

        //Builds the JWT and serializes it to a compact, URL-safe string
        String token = builder.compact();

        logger.info("--------------------------------------------------------");
        logger.info("GENERATING TOKEN: " + token);
        logger.info("SUBJECT: " + user.getUsername());
        logger.info("AUDIENCE: " + audience);
        logger.info("--------------------------------------------------------");

        return token;
    }

    /**
     *
     * @param token
     * @param audience
     * @return
     * @throws ExpiredJwtException
     * @throws MalformedJwtException
     * @throws SignatureException
     */
    public String refreshTokenForUser(String token, String audience) throws ExpiredJwtException, MalformedJwtException, SignatureException {

        Claims claims = Jwts.parser()
                .setSigningKey(secret_key)
                .parseClaimsJws(token).getBody();

        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(claims.getIssuedAt())
                .setSubject(claims.getSubject())
                .setAudience(audience)
                .signWith(SignatureAlgorithm.HS512, secret_key)
                .setExpiration(new Date(Utils.fromNow(Utils.USER_SESSION_TIME_LIMIT)));

        //Builds the JWT and serializes it to a compact, URL-safe string
        String newToken = builder.compact();

        logger.info("r--------------------------------------------------------");
        logger.info("SUBJECT: " + claims.getSubject());
        logger.info("REFRESHING TOKEN: (old) --> " + token);
        logger.info("AUDIENCE: (old) --> " + claims.getAudience());
        logger.info("--------------");
        logger.info("REFRESHING TOKEN: (new) --> " + newToken);
        logger.info("AUDIENCE: (new) --> " + audience);
        logger.info("r--------------------------------------------------------");

        return newToken;
    }

    /**
     *
     * @param token
     * @return
     * @throws MalformedJwtException
     * @throws SignatureException
     */
    public Date getIssuedAtFromToken(String token) throws ExpiredJwtException, MalformedJwtException, SignatureException {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(secret_key)
                .parseClaimsJws(token).getBody();

        return claims.getIssuedAt();
    }

    /**
     *
     * @param token
     * @return
     * @throws MalformedJwtException
     * @throws SignatureException
     */
    public Date getExpirationFromToken(String token) throws ExpiredJwtException, MalformedJwtException, SignatureException {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(secret_key)
                .parseClaimsJws(token).getBody();

        return claims.getExpiration();
    }

    /**
     *
     * @param token
     * @return
     * @throws MalformedJwtException
     * @throws SignatureException
     */
    public boolean isTokenExpired(String token) {
        return getExpirationFromToken(token).getTime() < System.currentTimeMillis();
    }

    /**
     *
     * @param token
     * @return
     * @throws MalformedJwtException
     * @throws SignatureException
     * @throws Exception
     */
    public String getUsernameFromToken(String token) throws ExpiredJwtException, MalformedJwtException, SignatureException, Exception {
        String username = Jwts.parser()
                .setSigningKey(secret_key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return username;
    }

    /**
     *
     * @param token
     * @return
     * @throws MalformedJwtException
     * @throws SignatureException
     * @throws Exception
     */
    public String getAudienceFromToken(String token) throws ExpiredJwtException, MalformedJwtException, SignatureException, Exception {
        String audience = Jwts.parser()
                .setSigningKey(secret_key)
                .parseClaimsJws(token)
                .getBody()
                .getAudience();
        return audience;
    }

    /**
     *
     * @param token
     * @return
     * @throws SignatureException
     */
    public UserAuth getUserFromToken(String token) throws ExpiredJwtException, MalformedJwtException, SignatureException {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(secret_key)
                .parseClaimsJws(token).getBody();

        logger.info("--------------------------------------------------------");
        logger.info("AUTHORIZING TOKEN: " + token);
        logger.info("SUBJECT: " + claims.getSubject());
        logger.info("AUDIENCE: " + claims.getAudience());
        logger.info("ISSUED_AT: " + claims.getIssuedAt());
        logger.info("EXPIRATION: " + claims.getExpiration());
        logger.info("--------------------------------------------------------");

        String username = claims.getSubject();
        return userAuthService.loadUserByUsername(username);

    }
}
