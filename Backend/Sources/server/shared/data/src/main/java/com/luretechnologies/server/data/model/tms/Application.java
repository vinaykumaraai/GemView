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

/**
 *
 *
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.luretechnologies.common.enums.TypeEnum;
import com.luretechnologies.common.validator.RegExp;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.data.model.Model;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Stores applications.
 */
@Entity
@Table(name = "Application")
public class Application implements Serializable {

    /**
     *
     */
    public Application() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Column(name = "type", nullable = false, length = 2)
//    @org.hibernate.annotations.Type(type = "com.luretechnologies.server.hibernate.enums.TypeUserType")
    private TypeEnum type;

    @JsonIgnore
    @OneToOne(targetEntity = Software.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
        @JoinColumn(name = "software", referencedColumnName = "id", nullable = true)})
    private Software software;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_NAME, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "name", nullable = false, length = 100, unique = true)
    private String name;

    @Size(max = 255, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_DESC, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "description", nullable = true, length = 255)
    private String description;

    @Column(name = "active", nullable = false, length = 1)
    private boolean active = true;

    @JsonIgnore
    @Column(name = "version", nullable = false, length = 11)
    private int version;

    @JsonIgnore
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Timestamp createdAt;

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @JsonIgnore
    @OneToMany(targetEntity = File.class, orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
        @JoinColumn(name = "application", nullable = false)})
    @OrderBy
    private Set<File> files = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, targetEntity = Parameter.class, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumns({
        @JoinColumn(name = "application", nullable = false)})
    @OrderBy
    private Set<Parameter> parameters = new HashSet<>();

    @ManyToMany(targetEntity = Model.class, fetch = FetchType.LAZY)
    @JoinTable(name = "Model_Application", joinColumns = {
        @JoinColumn(name = "application")}, inverseJoinColumns = {
        @JoinColumn(name = "model")})
    @OrderBy
    private Set<Model> models = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "application", targetEntity = ApplicationPackage.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy
    private Set<ApplicationPackage> applicationPackages = new HashSet<>();

    /**
     * Database identification.
     */
    @JsonIgnore
    private void setId(long value) {
        this.id = value;
    }

    /**
     * Database identification.
     *
     * @return
     */
    @JsonProperty
    public long getId() {
        return id;
    }

    /**
     * Application's name.
     *
     * @param value
     */
    @JsonIgnore
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Application's name.
     *
     * @return
     */
    @JsonProperty
    public String getName() {
        return name;
    }

    /**
     * Application's description.
     *
     * @param value
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Application's description.
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Creation date.
     *
     * @return
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Last update date.
     *
     * @param value
     */
    public void setUpdatedAt(Timestamp value) {
        this.updatedAt = value;
    }

    /**
     * Last update date.
     *
     * @return
     */
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Flag which says if the application is active or not.
     *
     * @param value
     */
    public void setActive(boolean value) {
        this.active = value;
    }

    /**
     * Flag which says if the application is active or not.
     *
     * @return
     */
    public boolean getActive() {
        return active;
    }

    /**
     *
     * @param value
     */
    public void setVersion(int value) {
        this.version = value;
    }

    /**
     *
     * @return
     */
    public int getVersion() {
        return version;
    }

    /**
     *
     * @param value
     */
    @JsonIgnore
    public void setSoftware(Software value) {
        this.software = value;
    }

    /**
     *
     * @return
     */
    @JsonIgnore //@JsonProperty
    public Software getSoftware() {
        return software;
    }

    /**
     *
     * @param value
     */
    @JsonIgnore
    public void setFiles(Set<File> value) {
        this.files = value;
    }

    /**
     *
     * @return
     */
    @JsonIgnore //@JsonProperty
    public Set<File> getFiles() {
        return files;
    }

    /**
     *
     * @param value
     */
    @JsonIgnore
    public void setParameters(Set<Parameter> value) {
        this.parameters = value;
    }

    /**
     *
     * @return
     */
    @JsonProperty
    public Set<Parameter> getParameters() {
        return parameters;
    }

    /**
     *
     * @return
     */
    public TypeEnum getType() {
        return type;
    }

    /**
     *
     * @return
     */
    public boolean isActive() {
        return active;
    }

    /**
     *
     * @param type
     */
    public void setType(TypeEnum type) {
        this.type = type;
    }

    /**
     *
     * @param createdAt
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     */
    public Set<Model> getModels() {
        return models;
    }

    /**
     *
     * @param models
     */
    public void setModels(Set<Model> models) {
        this.models = models;
    }

    /**
     *
     * @return
     */
    public Set<ApplicationPackage> getApplicationPackages() {
        return applicationPackages;
    }

    /**
     *
     * @param applicationPackages
     */
    public void setApplicationPackages(Set<ApplicationPackage> applicationPackages) {
        this.applicationPackages = applicationPackages;

    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
