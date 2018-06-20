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
import java.sql.Timestamp;

public class HeartbeatOdometer {

    private Long id;
    private String component;
    private String name;
    private String label;
    private String description;
    private String value;
    private Long entity;
    private Timestamp currentTime;
    

    /**
     * No args constructor for use in serialization
     *
     */
    public HeartbeatOdometer() {
    }

    /**
     *
     * @param component
     * @param name
     * @param label
     * @param description
     * @param value
     */
    public HeartbeatOdometer(String component, String name, String label, String description, String value) {
        super();
        this.component = component;
        this.name = name;
        this.label = label;
        this.description = description;
        this.value = value;
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
     * @return the name
     */
    @ApiModelProperty(value = "Name")
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
     * @return the label
     */
    @ApiModelProperty(value = "Label")
    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the description
     */
    @ApiModelProperty(value = "Description")
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
     * @return the value
     */
    @ApiModelProperty(value = "Value")
    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }


    /**
     * @return the currentTime
     */
    @ApiModelProperty(value = "CurrentTime")
    @JsonProperty("currentTime")
    public Timestamp getCurrentTime() {
        return currentTime;
    }

    /**
     * @param currentTime the currentTime to set
     */
    public void setCurrentTime(Timestamp currentTime) {
        this.currentTime = currentTime;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class HeartbeatOdometer {\n");
        sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
        sb.append("    component: ").append(StringUtil.toIndentedString(component)).append("\n");
        sb.append("    name: ").append(StringUtil.toIndentedString(name)).append("\n");
        sb.append("    label: ").append(StringUtil.toIndentedString(label)).append("\n");
        sb.append("    description: ").append(StringUtil.toIndentedString(description)).append("\n");
        sb.append("    value: ").append(StringUtil.toIndentedString(value)).append("\n");
        sb.append("    entity: ").append(StringUtil.toIndentedString(getEntity())).append("\n");
        sb.append("    currentTime: ").append(StringUtil.toIndentedString(currentTime)).append("\n");
        sb.append("}");
        return sb.toString();
    }
    
}
