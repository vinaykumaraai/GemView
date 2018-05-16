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

/**
 *
 *
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luretechnologies.common.validator.RegExp;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.constraints.FieldNotBlank;
import com.luretechnologies.server.data.model.tms.Application;
import com.luretechnologies.server.data.model.tms.TerminalProfile;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Stores terminal's model.
 */
@Entity
@Table(name = "Model")
public class Model implements Serializable {

    /**
     *
     */
    public Model() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "The database identification.")
    private long id;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @FieldNotBlank
    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_NAME, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "name", nullable = false, unique = true, length = 100)
    @ApiModelProperty(value = "The name.", required = true)
    private String name;

    @Size(max = 255, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_DESC, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "description", nullable = true, length = 255)
    @ApiModelProperty(value = "The description.")
    private String description;

    @Column(name = "multi_app", nullable = false, length = 1)
    @ApiModelProperty(value = "Flag which says if the model supports multiple applications.")
    private boolean multiApp = true;

    @Digits(integer = 10, fraction = 0, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "max_apps", nullable = true, length = 10)
    @ApiModelProperty(value = "Maximun number of applications supported.")
    private Integer maxApps;

    @Digits(integer = 10, fraction = 0, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "max_keys", nullable = true, length = 10)
    @ApiModelProperty(value = "Maximun number of keys.")
    private Integer maxKeys;

    @JsonIgnore
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Timestamp createdAt;

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @JsonIgnore
    @ManyToMany(targetEntity = Application.class, fetch = FetchType.LAZY)
    @JoinTable(name = "Model_Application", joinColumns = {
        @JoinColumn(name = "model")}, inverseJoinColumns = {
        @JoinColumn(name = "application")})
    private Set<Application> applications = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "models", targetEntity = TerminalProfile.class, fetch = FetchType.LAZY)
    private Set<TerminalProfile> profiles = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "model", targetEntity = Terminal.class, fetch = FetchType.LAZY)
    private Set<Terminal> terminals = new HashSet<>();

    /**
     * Database identification.
     */
    private void setId(long value) {
        this.id = value;
    }

    /**
     * Database identification.
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * Model's name.
     *
     * @param value
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Model's name.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Model's description.
     *
     * @param value
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Model's description.
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Flag which say if the model supports multiple applications.
     *
     * @param value
     */
    public void setMultiApp(boolean value) {
        this.multiApp = value;
    }

    /**
     * Flag which say if the model supports multiple applications.
     *
     * @return
     */
    public boolean getMultiApp() {
        return multiApp;
    }

    /**
     * Maximum number of applications that the model supports.
     *
     * @param value
     */
    public void setMaxApps(int value) {
        setMaxAppsInteger(new Integer(value));
    }

    /**
     * Maximum number of applications that the model supports.
     *
     * @param value
     */
    @JsonIgnore
    public void setMaxAppsInteger(Integer value) {
        this.maxApps = value;
    }

    /**
     * Maximum number of applications that the model supports.
     *
     * @return
     */
    public Integer getMaxApps() {
        return maxApps;
    }

    /**
     * Maximum number of keys that the model supports.
     *
     * @param value
     */
    public void setMaxKeys(int value) {
        setMaxKeysInteger(new Integer(value));
    }

    /**
     * Maximum number of keys that the model supports.
     *
     * @param value
     */
    @JsonIgnore
    public void setMaxKeysInteger(Integer value) {
        this.maxKeys = value;
    }

    /**
     * Maximum number of keys that the model supports.
     *
     * @return
     */
    public Integer getMaxKeys() {
        return maxKeys;
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
     *
     * @param value
     */
    public void setApplications(Set<Application> value) {
        this.applications = value;
    }

    /**
     *
     * @return
     */
    public Set<Application> getApplications() {
        return applications;
    }

    /**
     *
     * @param value
     */
    public void setProfiles(Set<TerminalProfile> value) {
        this.profiles = value;
    }

    /**
     *
     * @return
     */
    public Set<TerminalProfile> getProfiles() {
        return profiles;
    }

    /**
     *
     * @param value
     */
    public void setTerminals(Set<Terminal> value) {
        this.terminals = value;
    }

    /**
     *
     * @return
     */
    public Set<Terminal> getTerminals() {
        return terminals;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
