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
package com.luretechnologies.server.data.model;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "system_params")
@NamedQueries({
    @NamedQuery(name = "SystemParam.findAll", query = "SELECT a FROM SystemParam a")})
public class SystemParam implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @ApiModelProperty(value = "The table primary key", required = true)
    private Long id;

    @Size(max = 45)
    @Column(name = "name")
    @ApiModelProperty(value = "The name", required = false)
    private String name;

    @Size(max = 128)
    @Column(name = "description")
    @ApiModelProperty(value = "The description", required = false)
    private String description;

    @Column(name = "occurred", nullable = false)
    @ApiModelProperty(value = "The time in which the heartbeat was performed.")
    private Timestamp occurred;

    @Column(name = "active", nullable = false)
    @ApiModelProperty(value = "if is active or not")
    private Boolean active;

    @Column(name = "Available", nullable = false)
    @ApiModelProperty(value = "if is available or not")
    private Boolean available;

    @Size(max = 4092)
    @Column(name = "value")
    @ApiModelProperty(value = "The value", required = false)
    private String value;

    @JoinColumn(name = "system_params_type", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SystemParamType systemParamType;

    /**
     * No args constructor for use in serialization
     *
     */
    public SystemParam() {
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SystemParam)) {
            return false;
        }
        SystemParam other = (SystemParam) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.luretechnologies.server.data.model.tms.AlertAction[ id=" + getId() + " ]";
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
     * @return the name
     */
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
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * @return the available
     */
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
     * @return the value
     */
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
     * @return the systemParamType
     */
    public SystemParamType getSystemParamType() {
        return systemParamType;
    }

    /**
     * @param systemParamType the systemParamType to set
     */
    public void setSystemParamType(SystemParamType systemParamType) {
        this.systemParamType = systemParamType;
    }
}
