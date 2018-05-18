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
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "mailId",
    "headers",
    "contents"
})
public class Attachment {

    @JsonProperty("id")
    private String id;
    @JsonProperty("mailId")
    private String mailId;
    @JsonProperty("headers")
    private Headers headers;
    @JsonProperty("contents")
    private String contents;
    @JsonIgnore
    private Map<String, Object> additionalProperties;

    /**
     * No args constructor for use in serialization
     *
     */
    public Attachment() {
        this.additionalProperties = new HashMap<>();
    }

    /**
     *
     * @param headers
     * @param id
     * @param contents
     * @param mailId
     */
    public Attachment(String id, String mailId, Headers headers, String contents) {
        super();
        this.additionalProperties = new HashMap<>();
        this.id = id;
        this.mailId = mailId;
        this.headers = headers;
        this.contents = contents;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Attachment withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("mailId")
    public String getMailId() {
        return mailId;
    }

    @JsonProperty("mailId")
    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public Attachment withMailId(String mailId) {
        this.mailId = mailId;
        return this;
    }

    @JsonProperty("headers")
    public Headers getHeaders() {
        return headers;
    }

    @JsonProperty("headers")
    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public Attachment withHeaders(Headers headers) {
        this.headers = headers;
        return this;
    }

    @JsonProperty("contents")
    public String getContents() {
        return contents;
    }

    @JsonProperty("contents")
    public void setContents(String contents) {
        this.contents = contents;
    }

    public Attachment withContents(String contents) {
        this.contents = contents;
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

    public Attachment withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
