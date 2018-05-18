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
package com.luretechnologies.server.data.model.tms;

/**
 *
 *
 */
import java.io.Serializable;

public class Email implements Serializable {

    private String account;
    private String from;
    private String fromName;
    private String to;
    private String cc;
    private String subject;
    private String body;
    private String contentType;

    /**
     * Default constructor
     */
    public Email() {
        this.contentType = "text/html";
    }

    /**
     * Enhanced constructor
     *
     * @param account
     * @param from
     * @param fromName
     * @param to
     * @param cc
     * @param subject
     * @param body
     * @param contentType
     */
    public Email(String account, String from, String fromName, String to, String cc, String subject, String body, String contentType) {
        this.account = account;
        this.from = from;
        this.fromName = fromName;
        this.to = to;
        this.cc = cc;
        this.subject = subject;
        this.body = body;
        this.contentType = contentType;
    }

    /**
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the fromName
     */
    public String getFromName() {
        return fromName;
    }

    /**
     * @param fromName the fromName to set
     */
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    /**
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @return the cc
     */
    public String getCC() {
        return cc;
    }

    /**
     * @param cc the cc to set
     */
    public void setCC(String cc) {
        this.cc = cc;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @return status
     */
    public boolean hasCC() {
        return (cc != null && !cc.isEmpty());
    }

    /**
     * @return status
     */
    public boolean hasAccount() {

        return (account != null && !account.isEmpty());
    }

    /**
     * @return status
     */
    public boolean hasFrom() {

        return (from != null && !from.isEmpty());
    }

    /**
     * @return status
     */
    public boolean isValid() {

        // cc is optional
        return (account != null && !account.isEmpty()
                && from != null && !from.isEmpty()
                && fromName != null && !fromName.isEmpty()
                && to != null && !to.isEmpty()
                && subject != null && !subject.isEmpty()
                && body != null && !body.isEmpty()
                && contentType != null && !contentType.isEmpty());
    }
}
