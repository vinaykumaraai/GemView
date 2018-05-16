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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "username",
    "password",
    "filename",
    "ftps_url",
    "https_url",
    "window"
})
public class DownloadInfo {

    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("filename")
    private String filename;
    @JsonProperty("ftps_url")
    private String ftpsUrl;
    @JsonProperty("https_url")
    private String httpsUrl;
    @JsonProperty("window")
    private Long window;

    /**
     * No args constructor for use in serialization
     *
     */
    public DownloadInfo() {
    }

    /**
     *
     * @param ftpsUrl
     * @param username
     * @param window
     * @param httpsUrl
     * @param filename
     * @param password
     */
    public DownloadInfo(String username, String password, String filename, String ftpsUrl, String httpsUrl, Long window) {
        super();
        this.username = username;
        this.password = password;
        this.filename = filename;
        this.ftpsUrl = ftpsUrl;
        this.httpsUrl = httpsUrl;
        this.window = window;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("filename")
    public String getFilename() {
        return filename;
    }

    @JsonProperty("filename")
    public void setFilename(String filename) {
        this.filename = filename;
    }

    @JsonProperty("ftps_url")
    public String getFtpsUrl() {
        return ftpsUrl;
    }

    @JsonProperty("ftps_url")
    public void setFtpsUrl(String ftpsUrl) {
        this.ftpsUrl = ftpsUrl;
    }

    @JsonProperty("https_url")
    public String getHttpsUrl() {
        return httpsUrl;
    }

    @JsonProperty("https_url")
    public void setHttpsUrl(String httpsUrl) {
        this.httpsUrl = httpsUrl;
    }

    @JsonProperty("window")
    public Long getWindow() {
        return window;
    }

    @JsonProperty("window")
    public void setWindow(Long window) {
        this.window = window;
    }
}
