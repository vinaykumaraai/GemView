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

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "serialNumber",
    "message",
    "heartbeatOdometers",
    "heartbeatAlerts",
    "heartbeatAudits"
})
public class Heartbeat {

    @JsonProperty("serialNumber")
    private String serialNumber;
    @JsonProperty("message")
    private String message;
    @JsonProperty("apps")
    private List<App> apps = null;
    @JsonProperty("kernels")
    private List<App> kernels = null;
    @JsonProperty("os")
    private List<App> os = null;
    @JsonProperty("heartbeatOdometers")
    private List<HeartbeatOdometer> heartbeatOdometers = null;
    @JsonProperty("heartbeatAlerts")
    private List<HeartbeatAlert> heartbeatAlerts = null;
    @JsonProperty("heartbeatAudits")
    private List<HeartbeatAudit> heartbeatAudits = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Heartbeat() {
    }

    /**
     *
     * @param heartbeatAlerts
     * @param message
     * @param serialNumber
     * @param heartbeatAudits
     * @param heartbeatOdometers
     */
    public Heartbeat(String serialNumber, String message, List<HeartbeatOdometer> heartbeatOdometers, List<HeartbeatAlert> heartbeatAlerts, List<HeartbeatAudit> heartbeatAudits) {
        super();
        this.serialNumber = serialNumber;
        this.message = message;
        this.heartbeatOdometers = heartbeatOdometers;
        this.heartbeatAlerts = heartbeatAlerts;
        this.heartbeatAudits = heartbeatAudits;
    }

    @JsonProperty("serialNumber")
    public String getSerialNumber() {
        return serialNumber;
    }

    @JsonProperty("serialNumber")
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("heartbeatOdometers")
    public List<HeartbeatOdometer> getHeartbeatOdometers() {
        return heartbeatOdometers;
    }

    @JsonProperty("heartbeatOdometers")
    public void setHeartbeatOdometers(List<HeartbeatOdometer> heartbeatOdometers) {
        this.heartbeatOdometers = heartbeatOdometers;
    }

    @JsonProperty("apps")
    public List<App> getApps() {
        return apps;
    }

    @JsonProperty("apps")
    public void setApps(List<App> apps) {
        this.apps = apps;
    }

    @JsonProperty("kernels")
    public List<App> getKernels() {
        return kernels;
    }

    @JsonProperty("kernels")
    public void setKernels(List<App> kernels) {
        this.kernels = kernels;
    }

    @JsonProperty("os")
    public List<App> getOS() {
        return os;
    }

    @JsonProperty("os")
    public void setOS(List<App> os) {
        this.os = os;
    }

    @JsonProperty("heartbeatAlerts")
    public List<HeartbeatAlert> getHeartbeatAlerts() {
        return heartbeatAlerts;
    }

    @JsonProperty("heartbeatAlerts")
    public void setHeartbeatAlerts(List<HeartbeatAlert> heartbeatAlerts) {
        this.heartbeatAlerts = heartbeatAlerts;
    }

    @JsonProperty("heartbeatAudits")
    public List<HeartbeatAudit> getHeartbeatAudits() {
        return heartbeatAudits;
    }

    @JsonProperty("heartbeatAudits")
    public void setHeartbeatAudits(List<HeartbeatAudit> heartbeatAudits) {
        this.heartbeatAudits = heartbeatAudits;
    }
}
