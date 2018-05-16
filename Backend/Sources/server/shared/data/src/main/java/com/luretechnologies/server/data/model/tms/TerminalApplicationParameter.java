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
import com.luretechnologies.server.common.Messages;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Stores parameters's value for an application in a terminal.
 */
@Entity
@Table(name = "Terminal_Application_Parameter", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"parameter", "application_package"})})
public class TerminalApplicationParameter implements Serializable {

    /**
     *
     */
    public TerminalApplicationParameter() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(targetEntity = Parameter.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "parameter", referencedColumnName = "id", nullable = false)})
    private Parameter parameter;

    @JsonIgnore
    @ManyToOne(targetEntity = ApplicationPackage.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "application_package", referencedColumnName = "id", nullable = false)})
    private ApplicationPackage applicationPackage;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 255, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "value", nullable = false, length = 255)
    private String value;

    @JsonIgnore
    @Column(name = "version", nullable = false, length = 11)
    private int version;

    @JsonIgnore
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Timestamp createdAt;

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

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
     * Parameter's value.
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Parameter's value.
     *
     * @return
     */
    public String getValue() {
        return value;
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
     * @return
     */
    public ApplicationPackage getApplicationPackage() {
        return applicationPackage;
    }

    /**
     *
     * @param applicationPackage
     */
    public void setApplicationPackage(ApplicationPackage applicationPackage) {
        this.applicationPackage = applicationPackage;
    }

    /**
     *
     * @param value
     */
    public void setParameter(Parameter value) {
        this.parameter = value;
    }

    /**
     *
     * @return
     */
    public Parameter getParameter() {
        return parameter;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

    /**
     *
     * @param createdAt
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
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

}
