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
package com.luretechnologies.server.data.model.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luretechnologies.server.common.Messages;
import io.swagger.annotations.ApiModelProperty;
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
import javax.validation.constraints.Size;

/**
 *
 *
 * @author developer
 */
@Entity
@Table(name = "Terminal_Host_Setting_Value")
public class TerminalHostSettingValue implements Serializable {

    /**
     *
     */
    public TerminalHostSettingValue() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "The database identification.")
    private long id;

    @JsonIgnore
    @ManyToOne(targetEntity = TerminalHost.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "terminal_host", referencedColumnName = "id")})
    private TerminalHost terminalHost;

    @ManyToOne(targetEntity = TerminalHostSetting.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "terminal_host_setting", referencedColumnName = "id")})
    @ApiModelProperty(value = "The setting.")
    private TerminalHostSetting setting;

    @JsonIgnore
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Timestamp createdAt;

    @Size(max = Integer.MAX_VALUE, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "value", nullable = true, length = Integer.MAX_VALUE)
    @ApiModelProperty(value = "The terminal host setting value.")
    private String value;

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    /**
     *
     * @param value
     */
    public void setId(long value) {
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
    public TerminalHost getTerminalHost() {
        return terminalHost;
    }

    /**
     *
     * @param terminalHost
     */
    public void setTerminalHost(TerminalHost terminalHost) {
        this.terminalHost = terminalHost;
    }

    /**
     *
     * @return
     */
    public TerminalHostSetting getSetting() {
        return setting;
    }

    /**
     *
     * @param setting
     */
    public void setSetting(TerminalHostSetting setting) {
        this.setting = setting;
    }

    /**
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     *
     * @return
     */
    public Timestamp getCreatedAt() {
        return createdAt;
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
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     */
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
