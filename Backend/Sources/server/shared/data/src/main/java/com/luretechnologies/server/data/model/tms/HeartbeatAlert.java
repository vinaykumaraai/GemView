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

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Heartbeat_Alert")
public class HeartbeatAlert implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @ApiModelProperty(value = "The table primary key", required = true)
    private Long id;

    @Column(name = "occurred", nullable = false)
    @ApiModelProperty(value = "The time in which the heartbeat was performed.")
    private Timestamp occurred;

    @Size(max = 128)
    @Column(name = "component")
    @ApiModelProperty(value = "The component.", required = false)
    private String component;

    @Size(max = 128)
    @Column(name = "label")
    @ApiModelProperty(value = "The label.", required = false)
    private String label;

    @JoinColumn(name = "entity", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private com.luretechnologies.server.data.model.Entity entity;
    
    @Column(name = "done")
    @ApiModelProperty(value = "If the alert was process or not.", required = false)
    private Boolean done;
    

    @Column(name = "updated_at", nullable = false)
    @ApiModelProperty(value = "The time in which the heartbeat was performed.")
    private Timestamp updatedAt;
    
    

    /**
     * No args constructor for use in serialization
     *
     */
    public HeartbeatAlert() {
    }

    /**
     *
     * @param occurred
     * @param component
     * @param label
     */
    public HeartbeatAlert(Timestamp occurred, String component, String label) {
        super();
        this.occurred = occurred;
        this.component = component;
        this.label = label;
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
     * @return the occurred
     */
    public Timestamp getOccurred() {
        return occurred;
    }

    /**
     * @param occurred the occurred to set
     */
    public void setOccurred(Timestamp occurred) {
        this.occurred = occurred;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HeartbeatAlert)) {
            return false;
        }
        HeartbeatAlert other = (HeartbeatAlert) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.luretechnologies.server.data.model.tms.HeartbeatAlert[ id=" + id + " ]";
    }

    /**
     * @return the entity
     */
    public com.luretechnologies.server.data.model.Entity getEntity() {
        return entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(com.luretechnologies.server.data.model.Entity entity) {
        this.entity = entity;
    }
    
        /**
     * @return the updatedAt
     */
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt the updatedAt to set
     */
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    /**
     * @return the done
     */
    public Boolean getDone() {
        return done;
    }

    /**
     * @param done the done to set
     */
    public void setDone(Boolean done) {
        this.done = done;
    }    
}
