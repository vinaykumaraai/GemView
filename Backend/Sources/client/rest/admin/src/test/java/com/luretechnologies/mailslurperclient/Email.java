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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "account",
    "body",
    "cc",
    "contentType",
    "from",
    "fromName",
    "subject",
    "to"
})
public class Email {

    @JsonProperty("account")
    private String account;
    @JsonProperty("body")
    private String body;
    @JsonProperty("cc")
    private String cc;
    @JsonProperty("contentType")
    private String contentType;
    @JsonProperty("from")
    private String from;
    @JsonProperty("fromName")
    private String fromName;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("to")
    private String to;

    /**
     * No args constructor for use in serialization
     *
     */
    public Email() {
    }

    /**
     *
     * @param to
     * @param body
     * @param subject
     * @param account
     * @param fromName
     * @param from
     * @param contentType
     * @param cc
     */
    public Email(String account, String body, String cc, String contentType, String from, String fromName, String subject, String to) {
        super();
        this.account = account;
        this.body = body;
        this.cc = cc;
        this.contentType = contentType;
        this.from = from;
        this.fromName = fromName;
        this.subject = subject;
        this.to = to;
    }

    @JsonProperty("account")
    public String getAccount() {
        return account;
    }

    @JsonProperty("account")
    public void setAccount(String account) {
        this.account = account;
    }

    public Email withAccount(String account) {
        this.account = account;
        return this;
    }

    @JsonProperty("body")
    public String getBody() {
        return body;
    }

    @JsonProperty("body")
    public void setBody(String body) {
        this.body = body;
    }

    public Email withBody(String body) {
        this.body = body;
        return this;
    }

    @JsonProperty("cc")
    public String getCc() {
        return cc;
    }

    @JsonProperty("cc")
    public void setCc(String cc) {
        this.cc = cc;
    }

    public Email withCc(String cc) {
        this.cc = cc;
        return this;
    }

    @JsonProperty("contentType")
    public String getContentType() {
        return contentType;
    }

    @JsonProperty("contentType")
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Email withContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    @JsonProperty("from")
    public String getFrom() {
        return from;
    }

    @JsonProperty("from")
    public void setFrom(String from) {
        this.from = from;
    }

    public Email withFrom(String from) {
        this.from = from;
        return this;
    }

    @JsonProperty("fromName")
    public String getFromName() {
        return fromName;
    }

    @JsonProperty("fromName")
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public Email withFromName(String fromName) {
        this.fromName = fromName;
        return this;
    }

    @JsonProperty("subject")
    public String getSubject() {
        return subject;
    }

    @JsonProperty("subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Email withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    @JsonProperty("to")
    public String getTo() {
        return to;
    }

    @JsonProperty("to")
    public void setTo(String to) {
        this.to = to;
    }

    public Email withTo(String to) {
        this.to = to;
        return this;
    }

    @Override
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (IOException ex) {
            return "";
        }
    }
}
