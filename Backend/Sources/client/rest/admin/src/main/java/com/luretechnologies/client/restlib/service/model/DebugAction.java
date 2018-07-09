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

public class DebugAction {

    private Long id;
    private String name;
    private String description;
    private String serialNumber;
    private Long entity;
    private Boolean available;
    private Boolean debug;
    private Boolean debugDuration;

    /**
     * No args constructor for use in serialization
     *
     */
    public DebugAction() {
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
     * @return the name
     */
    @ApiModelProperty(value = "The name")
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    @ApiModelProperty(value = "The description")
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the serialNumber
     */
    @ApiModelProperty(value = "The serial number")
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
     * @return the available
     */
    @ApiModelProperty(value = "Available")
    @JsonProperty("available")
    public Boolean getAvailable() {
        return available;
    }

    /**
     * @param available the available to set
     */
    public void setAvailable(Boolean available) {
        this.available = available;
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
     * @return the debugDuration
     */
    @ApiModelProperty(value = "Debug Duration")
    @JsonProperty("debugDuration")
    public Boolean getDebugDuration() {
        return debugDuration;
    }

    /**
     * @param debugDuration the debugDuration to set
     */
    public void setDebugDuration(Boolean debugDuration) {
        this.debugDuration = debugDuration;
    }

 @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DebugAction {\n");
        sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
        sb.append("    name:").append(StringUtil.toIndentedString(name)).append("\n");
        sb.append("    description:").append(StringUtil.toIndentedString(description)).append("\n");
        sb.append("    serialNumber:").append(StringUtil.toIndentedString(serialNumber)).append("\n");
        sb.append("    entity:").append(StringUtil.toIndentedString(getEntity())).append("\n");
        sb.append("    available:").append(StringUtil.toIndentedString(available)).append("\n");
        sb.append("    debug:").append(StringUtil.toIndentedString(debug)).append("\n");
        sb.append("    debugDuration:").append(StringUtil.toIndentedString(debugDuration)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * @return the entity
     */
    @ApiModelProperty(value = "The entity id")
    @JsonProperty("entity")    
    public Long getEntity() {
        return entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(Long entity) {
        this.entity = entity;
    }

    
}
