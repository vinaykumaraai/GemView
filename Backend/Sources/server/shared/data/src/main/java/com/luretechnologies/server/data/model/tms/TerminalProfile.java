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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Stores terminal's profiles.
 */
@Entity
@Table(name = "Terminal_Profile")
public class TerminalProfile implements Serializable {

    /**
     *
     */
    public TerminalProfile() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_NAME, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Size(max = 255, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_DESC, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "description", nullable = true, length = 255)
    private String description;

    @JsonIgnore
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Timestamp createdAt;

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @ManyToMany(targetEntity = Model.class, fetch = FetchType.LAZY)
    @JoinTable(name = "Model_Terminal_Profile", joinColumns = {
        @JoinColumn(name = "terminal_profile")}, inverseJoinColumns = {
        @JoinColumn(name = "model")})
    @OrderBy
    private Set<Model> models = new HashSet<>();

    @OneToMany(targetEntity = TerminalProfileApplication.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
        @JoinColumn(name = "terminal_profile", nullable = false)})
    @OrderBy
    private Set<TerminalProfileApplication> terminalProfileApplications = new HashSet<>();

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
     * Terminal profile's name.
     *
     * @param value
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Terminal profile's name.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Terminal profile's description.
     *
     * @param value
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Terminal profile's description.
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
     *
     * @param value
     */
    public void setModels(Set<Model> value) {
        this.models = value;
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
     * @param value
     */
    public void setTerminalProfileApplications(Set<TerminalProfileApplication> value) {
        this.terminalProfileApplications = value;
    }

    /**
     *
     * @return
     */
    public Set<TerminalProfileApplication> getTerminalProfileApplications() {
        return terminalProfileApplications;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
