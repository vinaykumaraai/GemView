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
package com.luretechnologies.client.restlib.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luretechnologies.client.restlib.common.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;

/**
 *
 * @author romer
 */
public class DownloadInfo  {

    private Long id;
    private String username;
    private String password;
    private String filename;
    private String ftpsUrl;
    private String httpsUrl;
    //  private Long window;
    private Timestamp occurred;

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
    public DownloadInfo(String username, String password, String filename, String ftpsUrl, String httpsUrl) {
        super();
        this.username = username;
        this.password = password;
        this.filename = filename;
        this.ftpsUrl = ftpsUrl;
        this.httpsUrl = httpsUrl;
        //   this.window = window;
    }

    /**
     * @return the id
     */
    @ApiModelProperty(value = "Id")
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    @ApiModelProperty(value = "Username")
    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    @ApiModelProperty(value = "Password")
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the filename
     */
    @ApiModelProperty(value = "Filename")
    @JsonProperty("filename")
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return the ftpsUrl
     */
    @ApiModelProperty(value = "FtpsUrl")
    @JsonProperty("ftpsUrl")
    public String getFtpsUrl() {
        return ftpsUrl;
    }

    /**
     * @param ftpsUrl the ftpsUrl to set
     */
    public void setFtpsUrl(String ftpsUrl) {
        this.ftpsUrl = ftpsUrl;
    }

    /**
     * @return the httpsUrl
     */
    @ApiModelProperty(value = "HttpsUrl")
    @JsonProperty("httpsUrl")
    public String getHttpsUrl() {
        return httpsUrl;
    }

    /**
     * @param httpsUrl the httpsUrl to set
     */
    public void setHttpsUrl(String httpsUrl) {
        this.httpsUrl = httpsUrl;
    }

    /**
     * @return the occurred
     */
    @ApiModelProperty(value = "Occurred")
    @JsonProperty("occurred")
    public Timestamp getOccurred() {
        return occurred;
    }

    /**
     * @param occurred the occurred to set
     */
    public void setOccurred(Timestamp occurred) {
        this.occurred = occurred;
    }

    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DownloadInfo {\n");
        sb.append("    id: ").append(StringUtil.toIndentedString(getId())).append("\n");
        sb.append("    username: ").append(StringUtil.toIndentedString(getUsername())).append("\n");
        sb.append("    password: ").append(StringUtil.toIndentedString(getPassword())).append("\n");
        sb.append("    filename: ").append(StringUtil.toIndentedString(getFilename())).append("\n");
        sb.append("    ftpsUrl: ").append(StringUtil.toIndentedString(getFtpsUrl())).append("\n");
        sb.append("    httpsUrl: ").append(StringUtil.toIndentedString(getHttpsUrl())).append("\n");
        sb.append("    occurred: ").append(StringUtil.toIndentedString(getOccurred())).append("\n");
        sb.append("}");
        return sb.toString();
    }

}
