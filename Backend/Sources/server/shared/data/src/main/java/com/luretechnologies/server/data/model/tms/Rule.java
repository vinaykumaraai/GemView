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
import com.luretechnologies.common.enums.DayEnum;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.jpa.converters.DayEnumConverter;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * Stores schedule rules.
 */
@Entity
@Table(name = "Rule", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"day", "time_interval", "schedule_group"})})
public class Rule implements Serializable {

    /**
     *
     */
    public Rule() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "The database identification.")
    private long id;

    @OneToOne(targetEntity = TimeInterval.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
        @JoinColumn(name = "time_interval", referencedColumnName = "id", nullable = false)})
    @ApiModelProperty(value = "The time interval.")
    private TimeInterval timeInterval;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Column(name = "day", nullable = false, length = 2)
    @Convert(converter = DayEnumConverter.class)
    @ApiModelProperty(value = "The day.", required = true)
    private DayEnum day;

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
     * Schedule's day.
     *
     * @param value
     */
    public void setDay(DayEnum value) {
        this.day = value;
    }

    /**
     * Schedule's day.
     *
     * @return
     */
    public DayEnum getDay() {
        return day;
    }

    /**
     *
     * @param value
     */
    public void setTimeInterval(TimeInterval value) {
        this.timeInterval = value;
    }

    /**
     *
     * @return
     */
    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    /**
     *
     * @param createdAt
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}