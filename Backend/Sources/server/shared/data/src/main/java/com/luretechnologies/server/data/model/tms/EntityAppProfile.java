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
import com.luretechnologies.server.data.model.Entity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@javax.persistence.Entity
@Table(name = "Entity_App_Profile")
public class EntityAppProfile implements Serializable{
    
    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "The database identification.")
    private Long id;
    
    @ManyToOne(targetEntity = Entity.class, fetch = FetchType.EAGER)
    @JoinColumns({
        @JoinColumn(name = "entity", referencedColumnName = "id")})
    @ApiModelProperty(value = "The entity to which the app belongs. Ex: organization, region, merchant")
    private Entity entity;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "app_profile")
    @ApiModelProperty(value = "The appProfile id.", required = true)
    private Long appProfileId;
    
    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;
    
    @OneToMany(mappedBy = "entityAppProfileId", targetEntity = EntityAppProfileParam.class, fetch = FetchType.LAZY)
    private Set<EntityAppProfileParam> entityappprofileparamCollection = new HashSet<>();
    
    public EntityAppProfile() {
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
     * @return the entity
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * @return the appProfile
     */
    public Long getAppProfileId() {
        return appProfileId;
    }

    /**
     * @param appProfileId
     */
    public void setAppProfileId(Long appProfileId) {
        this.appProfileId = appProfileId;
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
     * @return the entityappprofileparamCollection
     */
    public Set<EntityAppProfileParam> getEntityappprofileparamCollection() {
        return entityappprofileparamCollection;
    }

    /**
     * @param entityappprofileparamCollection the entityappprofileparamCollection to set
     */
    public void setEntityappprofileparamCollection(Set<EntityAppProfileParam> entityappprofileparamCollection) {
        this.entityappprofileparamCollection = entityappprofileparamCollection;
    }
    
}
