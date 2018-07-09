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
package com.luretechnologies.server.data.display.tms;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 *
 * @author 
 */
public class DownloadInfoDisplay {

    /**
     * @return the occurred
     */
    public Date getOccurred() {
        return occurred;
    }

    /**
     * @param occurred the occurred to set
     */
    public void setOccurred(Date occurred) {
        this.occurred = occurred;
    }


    @ApiModelProperty(value = "The table primary key", required = true)
    private Long id;

    @ApiModelProperty(value = "The username.", required = false)
    private String username;

    @ApiModelProperty(value = "The password.", required = false)
    private String password;

    @ApiModelProperty(value = "The filename.", required = false)
    private String filename;

    @ApiModelProperty(value = "The ftpsUrl.", required = false)
    private String ftpsUrl;

    @ApiModelProperty(value = "The httpsUrl.", required = false)
    private String httpsUrl;

    //  private Long window;
    @ApiModelProperty(value = "The time in which the heartbeat was performed.")
    private Date occurred;
    

    /**
     * No args constructor for use in serialization
     *
     */
    public DownloadInfoDisplay() {
    }

    /**
     *
     * @param username
     * @param password
     * @param filename
     * @param ftpsUrl
     * @param httpsUrl
     */
    public DownloadInfoDisplay(String username, String password, String filename, String ftpsUrl, String httpsUrl) {
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
    public String getHttpsUrl() {
        return httpsUrl;
    }

    /**
     * @param httpsUrl the httpsUrl to set
     */
    public void setHttpsUrl(String httpsUrl) {
        this.httpsUrl = httpsUrl;
    }

}
