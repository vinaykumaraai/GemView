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

public class DebugItem {

    private Long id;
    private String component;
    private Integer level;
    private String message;
    private String occurred;

    /**
     * No args constructor for use in serialization
     *
     */
    public DebugItem() {
    }

    /**
     * @return the id
     */
    @ApiModelProperty(value = "The id")
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
     * @return the component
     */
    @ApiModelProperty(value = "Component")
    @JsonProperty("component")
    public String getComponent() {
        return component;
    }

    /**
     * @param component the component to set
     */
    public void setComponent(String component) {
        this.component = component;
    }

    /**
     * @return the level
     */
    @ApiModelProperty(value = "Level")
    @JsonProperty("level")
    public Integer getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(Integer level) {
        this.level = level;
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
     * @return the occurred
     */
    @ApiModelProperty(value = "When it occurred")
    @JsonProperty("occurred")
    public String getOccurred() {
        return occurred;
    }

    /**
     * @param occurred the occurred to set
     */
    public void setOccurred(String occurred) {
        this.occurred = occurred;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DebugItem {\n");
        sb.append("    id: ").append(StringUtil.toIndentedString(getId())).append("\n");
        sb.append("    component:").append(StringUtil.toIndentedString(component)).append("\n");
        sb.append("    level:").append(StringUtil.toIndentedString(level)).append("\n");
        sb.append("    message:").append(StringUtil.toIndentedString(message)).append("\n");
        sb.append("    occurred:").append(StringUtil.toIndentedString(getOccurred())).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
