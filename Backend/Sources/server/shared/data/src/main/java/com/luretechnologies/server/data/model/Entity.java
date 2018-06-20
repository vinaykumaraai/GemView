/**
 * COPYRIGHT @ Lure Technologies, LLC. ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or form
 * other than in accordance with and subject to the terms of a written license
 * from Lure or with the prior written consent of Lure or as permitted by
 * applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure. If you are neither the intended
 * recipient, nor an agent, employee, nor independent contractor responsible for
 * delivering this message to the intended recipient, you are prohibited from
 * copying, disclosing, distributing, disseminating, and/or using the
 * information in this email in any manner. If you have received this message in
 * error, please advise us immediately at legal@luretechnologies.com by return
 * email and then delete the message from your computer and all other records
 * (whether electronic, hard copy, or otherwise).
 *
 * Any copies or reproductions of this software program (in whole or in part)
 * made by any method must also include a copy of this legend.
 *
 * Inquiries should be made to legal@luretechnologies.com
 *
 */
package com.luretechnologies.server.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luretechnologies.common.enums.EntityTypeEnum;
import com.luretechnologies.common.validator.RegExp;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.constraints.FieldNotBlank;
import com.luretechnologies.server.jpa.converters.EntityTypeEnumConverter;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 *
 * @author developer
 */
@javax.persistence.Entity(name = "Entity")
@Table(name = "Entity")
@Inheritance(strategy = InheritanceType.JOINED)
public class Entity implements Serializable {

    /**
     * @return the available
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * @param available the available to set
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     *
     */
    public Entity() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "The database identification.")
    protected long id;

    @Column(name = "entity_id", nullable = true, unique = true, length = 13)
    @ApiModelProperty(value = "The entity identifier.")
    protected String entityId;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @FieldNotBlank
    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_NAME, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "name", nullable = false, length = 100)
    @ApiModelProperty(value = "The name.", required = true)
    protected String name;

    @Size(max = 255, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_DESC, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "description", nullable = true, length = 255)
    @ApiModelProperty(value = "The description.")
    protected String description;

    @JsonIgnore
    @Column(name = "created_at", nullable = false)
    protected Timestamp createdAt;

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    protected Timestamp updatedAt;

    @JsonIgnore
    @ManyToOne(targetEntity = Entity.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "parent", referencedColumnName = "id", nullable = true)})
    @ApiModelProperty(value = "The parent entity.")
    protected Entity parent;

    @Column(name = "parent", updatable = false, insertable = false)
    protected Long parentId;

    @Column(name = "entitytype", nullable = false, length = 2)
    @Convert(converter = EntityTypeEnumConverter.class)
    @ApiModelProperty(value = "The entity type.")
    protected EntityTypeEnum type;

    @JsonIgnore
    @OneToMany(mappedBy = "entity", targetEntity = User.class, fetch = FetchType.LAZY)
    protected Set<User> users = new HashSet<>();

    @JsonIgnore
    @Column(name = "path", nullable = true, columnDefinition = "TEXT")
    @ApiModelProperty(value = "The entity path.")
    protected String path;

    @Column(name = "active", nullable = false, length = 1)
    @ApiModelProperty(value = "Specifies if the record is active.")
    private boolean active = true;

    @Column(name = "available", nullable = false, length = 1)
    @ApiModelProperty(value = "Specifies if the record is available.")
    private boolean available = true;

    protected void setId(long value) {
        this.id = value;
    }

    /**
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public String getEntityId() {
        return entityId;
    }

    /**
     *
     * @param entityId
     */
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    /**
     *
     * @param value
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param value
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Organization's creation date.
     *
     * @param value
     */
    public void setCreatedAt(Timestamp value) {
        this.createdAt = value;
    }

    /**
     * Organization's creation date.
     *
     * @return
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Organization's last update date.
     *
     * @param value
     */
    public void setUpdatedAt(Timestamp value) {
        this.updatedAt = value;
    }

    /**
     * Organization's last update date.
     *
     * @return
     */
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param value
     */
    public void setParent(Entity value) {
        this.parent = value;
    }

    /**
     *
     * @return
     */
    public Entity getParent() {
        return parent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     *
     * @return
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     *
     * @param users
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public EntityTypeEnum getType() {
        return type;
    }

    public void setType(EntityTypeEnum type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /*
     * Flag which says if the entity is active or not.
     */
    /**
     *
     * @param value
     */
    public void setActive(boolean value) {
        this.active = value;
    }

    /**
     * Flag which says if the entity is active or not.
     *
     * @return
     */
    public boolean getActive() {
        return active;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
