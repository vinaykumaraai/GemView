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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "heartbeat_response")
public class HeartbeatResponse implements Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @ApiModelProperty(value = "The table primary key", required = true)
    private Long id;

    @Column(name = "status")
    @ApiModelProperty(value = "The Status is an integer value specifying a result condition 0=OK 1- FAIL ", required = true)
    private Integer status;

    @Column(name = "interval_at")
    @ApiModelProperty(value = "The interval", required = false)
    private Long intervalAt;

    @Column(name = "debug")
    @ApiModelProperty(value = "The debug", required = false)
    private Boolean debug;

    @Size(max = 128)
    @Column(name = "message")
    @ApiModelProperty(value = "The message.", required = false)
    private String message;

    @JoinColumn(name = "entity", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private com.luretechnologies.server.data.model.Entity entity;

    @Column(name = "occurred", nullable = false)
    @ApiModelProperty(value = "The time in which the heartbeat was performed.")
    private Timestamp occurred;

    @OneToOne(mappedBy = "heartbeatResponse", targetEntity = DownloadInfo.class, fetch = FetchType.LAZY,cascade = CascadeType.REMOVE )
    @ApiModelProperty(value = "Download Info", required = false)
    private DownloadInfo downloadInfo;

    @OneToOne(mappedBy = "heartbeatResponse", targetEntity = RKIInfo.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @ApiModelProperty(value = "RKI Info", required = false)
    private RKIInfo rkiInfo;

    /**
     * No args constructor for use in serialization
     *
     */
    public HeartbeatResponse() {
    }

    /**
     *
     * @param message
     * @param interval
     * @param status
     * @param downloadInfo
     * @param debug
     */
    public HeartbeatResponse(Integer status, String message, Long interval, Boolean debug, DownloadInfo downloadInfo) {
        super();
        this.status = status;
        this.message = message;
        this.intervalAt = interval;
        this.debug = debug;
        this.downloadInfo = downloadInfo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HeartbeatResponse)) {
            return false;
        }
        HeartbeatResponse other = (HeartbeatResponse) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.luretechnologies.server.data.model.tms.HeartbeatResponse[ id=" + getId() + " ]";
    }

    /**
     * @return the sequence
     */
    public String getSequence() {
        return id.toString();
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
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the debug
     */
    public Boolean getDebug() {
        return debug;
    }

    /**
     * @param debug the debug to set
     */
    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the entity
     */
    public com.luretechnologies.server.data.model.Entity getEntity() {
        return entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(com.luretechnologies.server.data.model.Entity entity) {
        this.entity = entity;
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

    /**
     * @return the rkiInfo
     */
    public RKIInfo getRkiInfo() {
        return rkiInfo;
    }

    /**
     * @param rkiInfo the rkiInfo to set
     */
    public void setRkiInfo(RKIInfo rkiInfo) {
        this.rkiInfo = rkiInfo;
    }
    /**
     * @return the downloadInfo
     */
    public DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }

    /**
     * @param downloadInfo the downloadInfo to set
     */
    public void setDownloadInfo(DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
    }

    /**
     * @return the intervalAt
     */
    public Long getIntervalAt() {
        return intervalAt;
    }

    /**
     * @param intervalAt the intervalAt to set
     */
    public void setIntervalAt(Long intervalAt) {
        this.intervalAt = intervalAt;
    }

    /**
     * @return the intervalAt
     */
    public Long getInterval() {
        return intervalAt;
    }

    /**
     * @param intervalAt the intervalAt to set
     */
    public void setInterval(Long intervalAt) {
        this.intervalAt = intervalAt;
    }
}
