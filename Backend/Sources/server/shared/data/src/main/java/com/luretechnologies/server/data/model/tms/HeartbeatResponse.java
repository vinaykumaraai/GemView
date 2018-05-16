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
    "status",
    "message",
    "interval",
    "debug",
    "downloadInfo"
})
public class HeartbeatResponse {

    @JsonProperty("status")
    private Long status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("interval")
    private Long interval;
    @JsonProperty("debug")
    private Boolean debug;
    @JsonProperty("downloadInfo")
    private DownloadInfo downloadInfo;

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
    public HeartbeatResponse(Long status, String message, Long interval, Boolean debug, DownloadInfo downloadInfo) {
        super();
        this.status = status;
        this.message = message;
        this.interval = interval;
        this.debug = debug;
        this.downloadInfo = downloadInfo;
    }

    @JsonProperty("status")
    public Long getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Long status) {
        this.status = status;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("interval")
    public Long getInterval() {
        return interval;
    }

    @JsonProperty("interval")
    public void setInterval(Long interval) {
        this.interval = interval;
    }

    @JsonProperty("debug")
    public Boolean getDebug() {
        return debug;
    }

    @JsonProperty("debug")
    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    @JsonProperty("downloadInfo")
    public DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }

    @JsonProperty("downloadInfo")
    public void setDownloadInfo(DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
    }
}
