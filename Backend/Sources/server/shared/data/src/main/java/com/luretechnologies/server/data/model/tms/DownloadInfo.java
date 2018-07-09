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

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author romer
 */
@Entity
@Table(name = "Download_Info")
public class DownloadInfo implements Serializable {

    /**
     * @return the heartbeatResponse
     */
    public HeartbeatResponse getHeartbeatResponse() {
        return heartbeatResponse;
    }

    /**
     * @param heartbeatResponse the heartbeatResponse to set
     */
    public void setHeartbeatResponse(HeartbeatResponse heartbeatResponse) {
        this.heartbeatResponse = heartbeatResponse;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @ApiModelProperty(value = "The table primary key", required = true)
    private Long id;

    @Size(max = 128)
    @Column(name = "username")
    @ApiModelProperty(value = "The username.", required = false)
    private String username;

    @Size(max = 128)
    @Column(name = "password")
    @ApiModelProperty(value = "The password.", required = false)
    private String password;

    @Size(max = 256)
    @Column(name = "filename")
    @ApiModelProperty(value = "The filename.", required = false)
    private String filename;

    @Size(max = 256)
    @Column(name = "ftps_url")
    @ApiModelProperty(value = "The ftpsUrl.", required = false)
    private String ftpsUrl;

    @Size(max = 256)
    @Column(name = "http_url")
    @ApiModelProperty(value = "The httpsUrl.", required = false)
    private String httpsUrl;

    //  private Long window;
    @Column(name = "occurred", nullable = false)
    @ApiModelProperty(value = "The time in which the heartbeat was performed.")
    private Timestamp occurred;
    
    
    @OneToOne(targetEntity = HeartbeatResponse.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "heartbeat_response", referencedColumnName = "id", nullable = true)
    private HeartbeatResponse heartbeatResponse;


    /**
     * No args constructor for use in serialization
     *
     */
    public DownloadInfo() {
    }

    /**
     *
     * @param username
     * @param password
     * @param filename
     * @param ftpsUrl
     * @param httpsUrl
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

    /**
     * @return the occurred
     */
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
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DownloadInfo)) {
            return false;
        }
        DownloadInfo other = (DownloadInfo) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.luretechnologies.server.data.model.tms.DownloadInfo[ id=" + id + " ]";
    }

}
