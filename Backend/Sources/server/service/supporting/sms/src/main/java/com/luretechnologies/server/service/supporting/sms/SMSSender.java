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
package com.luretechnologies.server.service.supporting.sms;

import com.luretechnologies.server.data.model.tms.SMS;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMSSender {

    private static class Credentials {

        private final String address;
        private final String username;
        private final String password;
        private final String port;

        public Credentials(String account) throws NoSuchElementException {

            address = System.getProperty("SMS_SERVER_ADDRESS_" + account);
            username = System.getProperty("SMS_USERNAME_" + account);
            password = System.getProperty("SMS_PASSWORD_" + account);
            port = System.getProperty("SMS_SERVER_PORT_" + account);

            if (address == null || port == null || username == null || password == null) {
                throw new NoSuchElementException("Missing SMS Credentials in server configuration.");
            }
        }

        /**
         * @return the address
         */
        public String getAddress() {
            return address;
        }

        /**
         * @return the username
         */
        public String getUsername() {
            return username;
        }

        /**
         * @return the password
         */
        public String getPassword() {
            return password;
        }

        /**
         * @return the port
         */
        public String getPort() {
            return port;
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(SMSSender.class);

    public static boolean sendSMS(SMS sms) {

        try {

            if (!sms.hasAccount()) {
                sms.setAccount(getDefaultAccount());
            }

            if (!sms.hasFrom()) {
                sms.setFrom(getDefaultFrom(sms.getAccount()));
            }

            Credentials credentials = new Credentials(sms.getAccount());
            send(credentials, sms);
            return true;

        } catch (Exception ex) {
            LOGGER.info("sendSMS failed: " + ex.getMessage());
            return false;
        }
    }

    private static void send(Credentials credentials, SMS sms) throws Exception {

        try {
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private static String getDefaultAccount() throws NoSuchElementException {

        String account = System.getProperty("SMS_DEFAULT_ACCOUNT");

        if (account == null) {
            throw new NoSuchElementException("Missing SMS default account in server configuration.");
        }

        return account;
    }

    private static String getDefaultFrom(String account) throws NoSuchElementException {

        String from = System.getProperty("SMS_SERVER_FROM_" + account);

        if (from == null) {
            throw new NoSuchElementException("Missing SMS default from address in server configuration.");
        }

        return from;
    }
}
