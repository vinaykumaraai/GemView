/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.server.service.supporting.email;

import com.luretechnologies.server.data.model.tms.Email;
import java.io.UnsupportedEncodingException;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * COPYRIGHT @ Lure Technologies, LLC. ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or form
 * other than in accordance with and subject to the terms of a written license
 * from Lure or with the prior written consent of Lure or as permitted by
 * applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure. If you are neither the intended
 * recipient, nor an agent, employee, nor independent contractor responsible for
 * delivering this message to the intended recipient, you are prohibited from
 * copying, disclosing, distributing, disseminating, and/or using the
 * information in this email in any manner. If you have received this message in
 * error, please advise us immediately at legal@luretechnologies.com by return
 * email and then delete the message from your computer and all other records
 * (whether electronic, hard copy, or otherwise).
 *
 * Any copies or reproductions of this software program (in whole or in part)
 * made by any method must also include a copy of this legend.
 *
 * Inquiries should be made to legal@luretechnologies.com
 *
 */
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailSenderSMTP {

    private static class Credentials {

        private final String address;
        private final String username;
        private final String password;
        private final String port;

        public Credentials(String account) throws NoSuchElementException {

            address = System.getProperty("SMTP_SERVER_ADDRESS_" + account);
            username = System.getProperty("SMTP_USERNAME_" + account);
            password = System.getProperty("SMTP_PASSWORD_" + account);
            port = System.getProperty("SMTP_SERVER_PORT_" + account);

            if (address == null || port == null || username == null || password == null) {
                throw new NoSuchElementException("Missing SMTP Credentials in server configuration.");
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

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderSMTP.class);

    public static boolean sendEmail(Email email) {

        try {

            if (!email.hasAccount()) {
                email.setAccount(getDefaultAccount());
            }

            if (!email.hasFrom()) {
                email.setFrom(getDefaultFrom(email.getAccount()));
            }

            Credentials credentials = new Credentials(email.getAccount());
            Session session = Session.getDefaultInstance(getProperties(credentials));
            MimeMessage message = getMessage(session, email);
            send(credentials, session, message);
            return true;

        } catch (UnsupportedEncodingException | IllegalArgumentException | NoSuchElementException | MessagingException ex) {
            LOGGER.info("sendEmail failed: " + ex.getMessage());
            return false;
        }
    }

    private static void send(Credentials credentials, Session session, MimeMessage message) throws MessagingException {

        try (Transport transport = session.getTransport()) {

            int port = 587;
            try {
                port = Integer.parseInt(credentials.port);
            } catch (NumberFormatException nex) {
            }

            transport.connect(credentials.getAddress(), port, credentials.username, credentials.getPassword());
            transport.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException ex) {
            throw new MessagingException(ex.getMessage());
        }
    }

    private static MimeMessage getMessage(Session session, Email email) throws IllegalArgumentException, AddressException, MessagingException, UnsupportedEncodingException {

        // String CONFIGSET = "ConfigSet";
        if (!email.isValid()) {
            throw new IllegalArgumentException();
        }

        MimeMessage msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(email.getFrom(), email.getFromName()));

        msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo()));

        if (email.hasCC()) {
            msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(email.getCC()));
        }

        msg.setSubject(email.getSubject());
        msg.setContent(email.getBody(), email.getContentType());

        // Add a configuration set header. Comment or delete the 
        // next line if you are not using a configuration set
        // msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
        return msg;
    }

    private static Properties getProperties(Credentials credentials) throws NoSuchElementException {

        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", credentials.port);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        return props;
    }

    private static String getDefaultAccount() throws NoSuchElementException {

        String account = System.getProperty("SMTP_DEFAULT_ACCOUNT");

        if (account == null) {
            throw new NoSuchElementException("Missing SMTP default account in server configuration.");
        }

        return account;
    }

    private static String getDefaultFrom(String account) throws NoSuchElementException {

        String from = System.getProperty("SMTP_SERVER_FROM_" + account);

        if (from == null) {
            throw new NoSuchElementException("Missing SMTP default from address in server configuration.");
        }

        return from;
    }
}
