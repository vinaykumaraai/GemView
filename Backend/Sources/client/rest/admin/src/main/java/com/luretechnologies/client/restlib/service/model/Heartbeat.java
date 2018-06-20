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
import java.util.ArrayList;
import java.util.List;

public class Heartbeat {

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

    private Long id;
    private String serialNumber;
    private String sequence;
    private String hwModel;
    private Integer status;
    private String message;

    private List<HeartbeatAppInfo> swComponents;
    private List<HeartbeatOdometer> heartbeatOdometers;
    private List<HeartbeatAlert> heartbeatAlerts;
    private List<HeartbeatAudit> heartbeatAudits;

    /**
     * No args constructor for use in serialization
     *
     */
    public Heartbeat() {
        swComponents = new ArrayList<>();
        heartbeatOdometers = new ArrayList<>();
        heartbeatAlerts = new ArrayList<>();
        heartbeatAudits = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Heartbeat {\n");
        sb.append("    id: ").append(StringUtil.toIndentedString(getId())).append("\n");
        sb.append("    serialNumber: ").append(StringUtil.toIndentedString(getSerialNumber())).append("\n");
        sb.append("    sequence: ").append(StringUtil.toIndentedString(getSequence())).append("\n");
        sb.append("    hwModel: ").append(StringUtil.toIndentedString(getHwModel())).append("\n");
        sb.append("    status: ").append(StringUtil.toIndentedString(getStatus())).append("\n");
        sb.append("    message: ").append(StringUtil.toIndentedString(getMessage())).append("\n");
        sb.append("    heartbeatAudits: ").append(StringUtil.toIndentedString(heartbeatAudits)).append("\n");
        sb.append("}");
        return sb.toString();
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
     * @return the serialNumber
     */
    @ApiModelProperty(value = "Serial Number")
    @JsonProperty("serialNumber")
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
    @ApiModelProperty(value = "Hard Ware Model")
    @JsonProperty("hwModel")
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
     * @return the status
     */
    @ApiModelProperty(value = "Status")
    @JsonProperty("status")
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
    @ApiModelProperty(value = "Message")
    @JsonProperty("message")
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
     * @return the swComponents
     */
    @ApiModelProperty(value = "Software components")
    @JsonProperty("swComponents")
    public List<HeartbeatAppInfo> getSwComponents() {
        return swComponents;
    }

    /**
     * @param swComponents the swComponents to set
     */
    public void setSwComponents(List<HeartbeatAppInfo> swComponents) {
        this.swComponents = swComponents;
    }

    /**
     * @return the heartbeatOdometers
     */
    @ApiModelProperty(value = "Odometers")
    @JsonProperty("heartbeatOdometers")
    public List<HeartbeatOdometer> getHeartbeatOdometers() {
        return heartbeatOdometers;
    }

    /**
     * @param heartbeatOdometers the heartbeatOdometers to set
     */
    public void setHeartbeatOdometers(List<HeartbeatOdometer> heartbeatOdometers) {
        this.heartbeatOdometers = heartbeatOdometers;
    }

    /**
     * @return the heartbeatAlerts
     */
    public List<HeartbeatAlert> getHeartbeatAlerts() {
        return heartbeatAlerts;
    }

    /**
     * @param heartbeatAlerts the heartbeatAlerts to set
     */
    public void setHeartbeatAlerts(List<HeartbeatAlert> heartbeatAlerts) {
        this.heartbeatAlerts = heartbeatAlerts;
    }

    /**
     * @return the heartbeatAudits
     */
    public List<HeartbeatAudit> getHeartbeatAudits() {
        return heartbeatAudits;
    }

    /**
     * @param heartbeatAudits the heartbeatAudits to set
     */
    public void setHeartbeatAudits(List<HeartbeatAudit> heartbeatAudits) {
        this.heartbeatAudits = heartbeatAudits;
    }
}
