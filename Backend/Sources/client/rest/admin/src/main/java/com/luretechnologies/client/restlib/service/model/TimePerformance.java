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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 *
 * Gemstone
 */
@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-11-16T15:50:13.952-05:00")
public class TimePerformance {

    private final Long id = null;
    private Integer rqstInFront = null;
    private Integer rqstInCoreQueue = null;
    private Integer rqstInCore = null;
    private Integer rqstInHostQueue = null;
    private Integer rqstInHost = null;
    private Integer rqstInProcessor = null;
    private Integer rspsInHost = null;
    private Integer rspsInHostQueue = null;
    private Integer rspsInCore = null;
    private Integer rspsInCoreQueue = null;
    private Integer rspsInFront = null;

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("rqstInFront")
    public Integer getRqstInFront() {
        return rqstInFront;
    }

    /**
     *
     * @param rqstInFront
     */
    public void setRqstInFront(Integer rqstInFront) {
        this.rqstInFront = rqstInFront;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("rqstInCoreQueue")
    public Integer getRqstInCoreQueue() {
        return rqstInCoreQueue;
    }

    /**
     *
     * @param rqstInCoreQueue
     */
    public void setRqstInCoreQueue(Integer rqstInCoreQueue) {
        this.rqstInCoreQueue = rqstInCoreQueue;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("rqstInCore")
    public Integer getRqstInCore() {
        return rqstInCore;
    }

    /**
     *
     * @param rqstInCore
     */
    public void setRqstInCore(Integer rqstInCore) {
        this.rqstInCore = rqstInCore;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("rqstInHostQueue")
    public Integer getRqstInHostQueue() {
        return rqstInHostQueue;
    }

    /**
     *
     * @param rqstInHostQueue
     */
    public void setRqstInHostQueue(Integer rqstInHostQueue) {
        this.rqstInHostQueue = rqstInHostQueue;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("rqstInHost")
    public Integer getRqstInHost() {
        return rqstInHost;
    }

    /**
     *
     * @param rqstInHost
     */
    public void setRqstInHost(Integer rqstInHost) {
        this.rqstInHost = rqstInHost;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("rqstInProcessor")
    public Integer getRqstInProcessor() {
        return rqstInProcessor;
    }

    /**
     *
     * @param rqstInProcessor
     */
    public void setRqstInProcessor(Integer rqstInProcessor) {
        this.rqstInProcessor = rqstInProcessor;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("rspsInHost")
    public Integer getRspsInHost() {
        return rspsInHost;
    }

    /**
     *
     * @param rspsInHost
     */
    public void setRspsInHost(Integer rspsInHost) {
        this.rspsInHost = rspsInHost;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("rspsInHostQueue")
    public Integer getRspsInHostQueue() {
        return rspsInHostQueue;
    }

    /**
     *
     * @param rspsInHostQueue
     */
    public void setRspsInHostQueue(Integer rspsInHostQueue) {
        this.rspsInHostQueue = rspsInHostQueue;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("rspsInCore")
    public Integer getRspsInCore() {
        return rspsInCore;
    }

    /**
     *
     * @param rspsInCore
     */
    public void setRspsInCore(Integer rspsInCore) {
        this.rspsInCore = rspsInCore;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("rspsInCoreQueue")
    public Integer getRspsInCoreQueue() {
        return rspsInCoreQueue;
    }

    /**
     *
     * @param rspsInCoreQueue
     */
    public void setRspsInCoreQueue(Integer rspsInCoreQueue) {
        this.rspsInCoreQueue = rspsInCoreQueue;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("rspsInFront")
    public Integer getRspsInFront() {
        return rspsInFront;
    }

    /**
     *
     * @param rspsInFront
     */
    public void setRspsInFront(Integer rspsInFront) {
        this.rspsInFront = rspsInFront;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TimePerformance {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
        sb.append("    rqstInFront: ").append(StringUtil.toIndentedString(rqstInFront)).append("\n");
        sb.append("    rqstInCoreQueue: ").append(StringUtil.toIndentedString(rqstInCoreQueue)).append("\n");
        sb.append("    rqstInCore: ").append(StringUtil.toIndentedString(rqstInCore)).append("\n");
        sb.append("    rqstInHostQueue: ").append(StringUtil.toIndentedString(rqstInHostQueue)).append("\n");
        sb.append("    rqstInHost: ").append(StringUtil.toIndentedString(rqstInHost)).append("\n");
        sb.append("    rqstInProcessor: ").append(StringUtil.toIndentedString(rqstInProcessor)).append("\n");
        sb.append("    rspsInHost: ").append(StringUtil.toIndentedString(rspsInHost)).append("\n");
        sb.append("    rspsInHostQueue: ").append(StringUtil.toIndentedString(rspsInHostQueue)).append("\n");
        sb.append("    rspsInCore: ").append(StringUtil.toIndentedString(rspsInCore)).append("\n");
        sb.append("    rspsInCoreQueue: ").append(StringUtil.toIndentedString(rspsInCoreQueue)).append("\n");
        sb.append("    rspsInFront: ").append(StringUtil.toIndentedString(rspsInFront)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
