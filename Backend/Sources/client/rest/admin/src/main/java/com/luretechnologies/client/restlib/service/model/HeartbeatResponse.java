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


public class HeartbeatResponse  {

    private Long id;
    private Integer status;
    private String sequence;
    private Long interval;
    private Boolean debug;
    private DownloadInfo downloadInfo;
    private RKIInfo rkiInfo;
    
    private String message;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class HeartbeatResponse {\n");
        sb.append("    id: ").append(StringUtil.toIndentedString(getId())).append("\n");
        sb.append("    status: ").append(StringUtil.toIndentedString(getStatus())).append("\n");
        sb.append("    sequence: ").append(StringUtil.toIndentedString(getSequence())).append("\n");
        sb.append("    interval: ").append(StringUtil.toIndentedString(getInterval())).append("\n");
        sb.append("    debug: ").append(StringUtil.toIndentedString(getDebug())).append("\n");
        sb.append("    downloadInfo: ").append(StringUtil.toIndentedString(getDownloadInfo())).append("\n");
        sb.append("    rkiInfo: ").append(StringUtil.toIndentedString(getRkiInfo())).append("\n");
        sb.append("    message: ").append(StringUtil.toIndentedString(getMessage())).append("\n");
        sb.append("}");
        return sb.toString();
    }
    

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
        this.interval = interval;
        this.debug = debug;
        this.downloadInfo = downloadInfo;
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
     * @return the sequence
     */
    @ApiModelProperty(value = "Sequence")
    @JsonProperty("sequence")
    public String getSequence() {
        return sequence;
    }


    /**
     * @return the interval
     */
    @ApiModelProperty(value = "Interval")
    @JsonProperty("interval")
    public Long getInterval() {
        return interval;
    }

    /**
     * @param interval the interval to set
     */
    public void setInterval(Long interval) {
        this.interval = interval;
    }

    /**
     * @return the debug
     */
    @ApiModelProperty(value = "Debug")
    @JsonProperty("debug")
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
     * @return the downloadInfo
     */
    @ApiModelProperty(value = "Download Info")
    @JsonProperty("downloadInfo")
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
     * @return the rkiInfo
     */
    @ApiModelProperty(value = "RKI Info")
    @JsonProperty("rkiInfo")
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
}
