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

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@javax.persistence.Entity
@Table(name = "Entity_App_Profile_Param")
public class EntityAppProfileParam implements Serializable{

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the entityAppProfile
     */
    public Long getEntityAppProfileId() {
        return entityAppProfileId;
    }

    /**
     * @param entityAppProfileId
     */
    public void setEntityAppProfileId(Long entityAppProfileId) {
        this.entityAppProfileId = entityAppProfileId;
    }

    /**
     * @return the appProfileParamValue
     */
    public Long getAppProfileParamValueId() {
        return appProfileParamValueId;
    }

    /**
     * @param appProfileParamValueId
     */
    public void setAppProfileParamValueId(Long appProfileParamValueId) {
        this.appProfileParamValueId = appProfileParamValueId;
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
    
    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "The database identification.")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "entity_app_profile")
    @ApiModelProperty(value = "The entityAppProfile id.", required = true)
    private Long entityAppProfileId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "app_profile_param_value")
    @ApiModelProperty(value = "The app profile param value id.", required = true)
    private Long appProfileParamValueId;
    
    @Column(name = "value", nullable = false, length = 128)
    @ApiModelProperty(value = "The value.")
    private String value;
    
    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;
    
    public EntityAppProfileParam() {
    }
}
