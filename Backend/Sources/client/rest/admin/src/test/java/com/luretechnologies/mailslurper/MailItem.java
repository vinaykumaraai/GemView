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
package com.luretechnologies.mailslurper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "id",
    "dateSent",
    "fromAddress",
    "toAddresses",
    "subject",
    "xmailer",
    "mimeVersion",
    "body",
    "contentType",
    "boundary",
    "attachments",
    "transferEncoding",
    "Message",
    "InlineAttachments",
    "TextBody",
    "HTMLBody"
})
public class MailItem {

    @JsonProperty("id")
    private String id;
    @JsonProperty("dateSent")
    private String dateSent;
    @JsonProperty("fromAddress")
    private String fromAddress;
    @JsonProperty("toAddresses")
    private List<String> toAddresses = null;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("xmailer")
    private String xmailer;
    @JsonProperty("mimeVersion")
    private String mimeVersion;
    @JsonProperty("body")
    private String body;
    @JsonProperty("contentType")
    private String contentType;
    @JsonProperty("boundary")
    private String boundary;
    @JsonProperty("attachments")
    private List<Object> attachments = null;
    @JsonProperty("transferEncoding")
    private String transferEncoding;
    @JsonProperty("Message")
    private Object message = null;
    @JsonProperty("InlineAttachments")
    private Object inlineAttachments;
    @JsonProperty("TextBody")
    private String textBody;
    @JsonProperty("HTMLBody")
    private String hTMLBody;
    @JsonIgnore
    private Map<String, Object> additionalProperties;

    /**
     * No args constructor for use in serialization
     *
     */
    public MailItem() {
        this.additionalProperties = new HashMap<>();
    }

    /**
     *
     * @param hTMLBody
     * @param body
     * @param inlineAttachments
     * @param subject
     * @param textBody
     * @param contentType
     * @param boundary
     * @param message
     * @param id
     * @param xmailer
     * @param toAddresses
     * @param fromAddress
     * @param dateSent
     * @param mimeVersion
     * @param attachments
     * @param transferEncoding
     */
    public MailItem(String id, String dateSent, String fromAddress, List<String> toAddresses, String subject, String xmailer, String mimeVersion, String body, String contentType, String boundary, List<Object> attachments, String transferEncoding, Object message, Object inlineAttachments, String textBody, String hTMLBody) {
        super();
        this.additionalProperties = new HashMap<>();
        this.id = id;
        this.dateSent = dateSent;
        this.fromAddress = fromAddress;
        this.toAddresses = toAddresses;
        this.subject = subject;
        this.xmailer = xmailer;
        this.mimeVersion = mimeVersion;
        this.body = body;
        this.contentType = contentType;
        this.boundary = boundary;
        this.attachments = attachments;
        this.transferEncoding = transferEncoding;
        this.message = message;
        this.inlineAttachments = inlineAttachments;
        this.textBody = textBody;
        this.hTMLBody = hTMLBody;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public MailItem withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("dateSent")
    public String getDateSent() {
        return dateSent;
    }

    @JsonProperty("dateSent")
    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }

    public MailItem withDateSent(String dateSent) {
        this.dateSent = dateSent;
        return this;
    }

    @JsonProperty("fromAddress")
    public String getFromAddress() {
        return fromAddress;
    }

    @JsonProperty("fromAddress")
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public MailItem withFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
        return this;
    }

    @JsonProperty("toAddresses")
    public List<String> getToAddresses() {
        return toAddresses;
    }

    @JsonProperty("toAddresses")
    public void setToAddresses(List<String> toAddresses) {
        this.toAddresses = toAddresses;
    }

    public MailItem withToAddresses(List<String> toAddresses) {
        this.toAddresses = toAddresses;
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

    public MailItem withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    @JsonProperty("xmailer")
    public String getXmailer() {
        return xmailer;
    }

    @JsonProperty("xmailer")
    public void setXmailer(String xmailer) {
        this.xmailer = xmailer;
    }

    public MailItem withXmailer(String xmailer) {
        this.xmailer = xmailer;
        return this;
    }

    @JsonProperty("mimeVersion")
    public String getMimeVersion() {
        return mimeVersion;
    }

    @JsonProperty("mimeVersion")
    public void setMimeVersion(String mimeVersion) {
        this.mimeVersion = mimeVersion;
    }

    public MailItem withMimeVersion(String mimeVersion) {
        this.mimeVersion = mimeVersion;
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

    public MailItem withBody(String body) {
        this.body = body;
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

    public MailItem withContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    @JsonProperty("boundary")
    public String getBoundary() {
        return boundary;
    }

    @JsonProperty("boundary")
    public void setBoundary(String boundary) {
        this.boundary = boundary;
    }

    public MailItem withBoundary(String boundary) {
        this.boundary = boundary;
        return this;
    }

    @JsonProperty("attachments")
    public List<Object> getAttachments() {
        return attachments;
    }

    @JsonProperty("attachments")
    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
    }

    public MailItem withAttachments(List<Object> attachments) {
        this.attachments = attachments;
        return this;
    }

    @JsonProperty("transferEncoding")
    public String getTransferEncoding() {
        return transferEncoding;
    }

    @JsonProperty("transferEncoding")
    public void setTransferEncoding(String transferEncoding) {
        this.transferEncoding = transferEncoding;
    }

    public MailItem withTransferEncoding(String transferEncoding) {
        this.transferEncoding = transferEncoding;
        return this;
    }

    @JsonProperty("Message")
    public Object getMessage() {
        return message;
    }

    @JsonProperty("Message")
    public void setMessage(Object message) {
        this.message = message;
    }

    public MailItem withMessage(Object message) {
        this.message = message;
        return this;
    }

    @JsonProperty("InlineAttachments")
    public Object getInlineAttachments() {
        return inlineAttachments;
    }

    @JsonProperty("InlineAttachments")
    public void setInlineAttachments(Object inlineAttachments) {
        this.inlineAttachments = inlineAttachments;
    }

    public MailItem withInlineAttachments(Object inlineAttachments) {
        this.inlineAttachments = inlineAttachments;
        return this;
    }

    @JsonProperty("TextBody")
    public String getTextBody() {
        return textBody;
    }

    @JsonProperty("TextBody")
    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public MailItem withTextBody(String textBody) {
        this.textBody = textBody;
        return this;
    }

    @JsonProperty("HTMLBody")
    public String getHTMLBody() {
        return hTMLBody;
    }

    @JsonProperty("HTMLBody")
    public void setHTMLBody(String hTMLBody) {
        this.hTMLBody = hTMLBody;
    }

    public MailItem withHTMLBody(String hTMLBody) {
        this.hTMLBody = hTMLBody;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public MailItem withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
