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
package com.luretechnologies.mailslurperclient;

import java.io.IOException;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

public class MailApiClient {

    Session session = null;
    String address;
    int port;
    String username;
    String password;

    public MailApiClient(String address, int port, String username, String password) throws Exception {
        session = Session.getDefaultInstance(System.getProperties());
        this.address = address;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public String GetMailItemContent() throws IOException, Exception {

        try (Store store = session.getStore("pop3")) {

            store.connect(address, port, username, password);
            Folder inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_WRITE);

            Message[] messages = inbox.getMessages();

            if (messages == null || messages.length == 0) {
                System.out.println("No messages found.");
                return null;
            }

            String content = (String) messages[messages.length - 1].getContent();
            inbox.close(true);

            return content;
        }
    }

    public int GetEmailCount() throws IOException, Exception {

        try (Store store = session.getStore("pop3")) {

            store.connect(address, port, username, password);
            Folder inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_WRITE);

            Message[] messages = inbox.getMessages();

            inbox.close(true);
            return messages == null ? 0 : messages.length;
        }
    }

    public void DeleteAllEmail() throws IOException, Exception {

        try (Store store = session.getStore("pop3")) {

            store.connect(address, port, username, password);
            Folder inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_WRITE);

            Message[] messages = inbox.getMessages();

            if (messages != null && messages.length > 0) {
                for (Message message : messages) {
                    message.setFlag(Flags.Flag.DELETED, true);
                }
            }

            inbox.close(true);
        }
    }

    public void ListAllEmail() throws Exception {

        try (Store store = session.getStore("pop3")) {

            store.connect(address, port, username, password);
            Folder inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_WRITE);

            Message[] messages = inbox.getMessages();

            for (Message message : messages) {
                System.out.println("Message: " + message.getMessageNumber());
                System.out.println("From : " + message.getFrom()[0]);
                System.out.println("Subject : " + message.getSubject());
                System.out.println("Sent Date : " + message.getSentDate());
                System.out.println("Content : " + (String) message.getContent());
            }

            inbox.close(true);
        }
    }
}
