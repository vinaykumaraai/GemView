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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HeartbeatDisplay {

    @ApiModelProperty(value = "The table primary key", required = true)
    private Long id;

    @ApiModelProperty(value = "The serial number", required = false)
    private String serialNumber;

    @ApiModelProperty(value = "The sequence", required = false)
    private String sequence;

    @ApiModelProperty(value = "The hard ware model", required = false)
    private String hwModel;

    @ApiModelProperty(value = "The heartbeat ip", required = false)
    private String ip;

    @ApiModelProperty(value = "The status", required = true)
    private Integer status;

    @ApiModelProperty(value = "The message", required = false)
    private String message;

    @ApiModelProperty(value = "Entity", required = true)
    private Long entity;

    @ApiModelProperty(value = "Software components.", required = false)
    private List<HeartbeatAppInfoDisplay> swComponents = null;

    @ApiModelProperty(value = "Heartbeat Odometers", required = false)
    private List<HeartbeatOdometerDisplay> heartbeatOdometers = null;

    @ApiModelProperty(value = "Heartbeat Audits", required = false)
    private List<HeartbeatAuditDisplay> heartbeatAudits = null;

    @ApiModelProperty(value = "Heartbeat Alerts", required = false)
    private List<HeartbeatAlertDisplay> heartbeatAlerts = null;

    @ApiModelProperty(value = "Heartbeat update paramter list", required = false)
    private List<HeartbeatUpdateParamDisplay> heartbeatUpdateParams = null;
    

    @ApiModelProperty(value = "The time in which the heartbeat was performed.")
    private Date occurred;

    /**
     * No args constructor for use in serialization
     *
     */
    public HeartbeatDisplay() {
        swComponents = new ArrayList<>();
        heartbeatOdometers = new ArrayList<>();
        heartbeatAlerts = new ArrayList<>();
        heartbeatAudits = new ArrayList<>();        
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
     * @return the serialNumber
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * @param serialNumber the serialNumber to set
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * @return the hwModel
     */
    public String getHwModel() {
        return hwModel;
    }

    /**
     * @param hwModel the hwModel to set
     */
    public void setHwModel(String hwModel) {
        this.hwModel = hwModel;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
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
    public Long getEntity() {
        return entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(Long entity) {
        this.entity = entity;
    }

    /**
     * @return the swComponents
     */
    public List<HeartbeatAppInfoDisplay> getSwComponents() {
        return swComponents;
    }

    /**
     * @param swComponents the swComponents to set
     */
    public void setSwComponents(List<HeartbeatAppInfoDisplay> swComponents) {
        this.swComponents = swComponents;
    }

    /**
     * @return the heartbeatOdometers
     */
    public List<HeartbeatOdometerDisplay> getHeartbeatOdometers() {
        return heartbeatOdometers;
    }

    /**
     * @param heartbeatOdometers the heartbeatOdometers to set
     */
    public void setHeartbeatOdometers(List<HeartbeatOdometerDisplay> heartbeatOdometers) {
        this.heartbeatOdometers = heartbeatOdometers;
    }

    /**
     * @return the heartbeatAudits
     */
    public List<HeartbeatAuditDisplay> getHeartbeatAudits() {
        return heartbeatAudits;
    }

    /**
     * @param heartbeatAudits the heartbeatAudits to set
     */
    public void setHeartbeatAudits(List<HeartbeatAuditDisplay> heartbeatAudits) {
        this.heartbeatAudits = heartbeatAudits;
    }

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

    /**
     * @return the heartbeatAlerts
     */
    public List<HeartbeatAlertDisplay> getHeartbeatAlerts() {
        return heartbeatAlerts;
    }

    /**
     * @param heartbeatAlerts the heartbeatAlerts to set
     */
    public void setHeartbeatAlerts(List<HeartbeatAlertDisplay> heartbeatAlerts) {
        this.heartbeatAlerts = heartbeatAlerts;
    }

    /**
     * @return the sequence
     */
    public String getSequence() {
        return sequence;
    }

    /**
     * @param sequence the sequence to set
     */
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
    /**
     * @return the heartbeatUpdateParams
     */
    public List<HeartbeatUpdateParamDisplay> getHeartbeatUpdateParams() {
        return heartbeatUpdateParams;
    }

    /**
     * @param heartbeatUpdateParams the heartbeatUpdateParams to set
     */
    public void setHeartbeatUpdateParams(List<HeartbeatUpdateParamDisplay> heartbeatUpdateParams) {
        this.heartbeatUpdateParams = heartbeatUpdateParams;
    }
    
}
