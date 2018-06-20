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
import java.sql.Timestamp;
import java.util.Date;

public class HeartbeatAuditDisplay {

    @ApiModelProperty(value = "The table primary key", required = true)
    private Long id;

    private Date occurred;

    @ApiModelProperty(value = "The component", required = false)
    private String component;

    @ApiModelProperty(value = "The label", required = false)
    private String label;

    @ApiModelProperty(value = "The Description", required = false)
    private String description;

    @ApiModelProperty(value = "The Message", required = false)
    private String message;

    @ApiModelProperty(value = "The entity", required = false)
    private Long entity;

    @ApiModelProperty(value = "Last updated", required = false)
    private Date updatedAt;

    /**
     * No args constructor for use in serialization
     *
     */
    public HeartbeatAuditDisplay() {
    }

    /**
     *
     * @param message
     * @param label
     * @param occurred
     */
    public HeartbeatAuditDisplay(Timestamp occurred, String component, String label, String message) {
        super();
        this.occurred = occurred;
        this.component = component;
        this.label = label;
        this.description = message;
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
     * @return the component
     */
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
     * @return the label
     */
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
     * @return the updatedAt
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt the updatedAt to set
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }    
}
