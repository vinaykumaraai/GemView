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

/**
 *
 *
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luretechnologies.common.enums.ModeEnum;
import com.luretechnologies.common.enums.OperationEnum;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.jpa.converters.ModeEnumConverter;
import com.luretechnologies.server.jpa.converters.OperationEnumConverter;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
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

/**
 * Stores relation between HostEnum, ModeEnum and OperationEnum.
 */
@Entity
@Table(name = "Host_Mode_Operation")
public class HostModeOperation implements Serializable {

    /**
     *
     */
    public HostModeOperation() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(targetEntity = Host.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "host", referencedColumnName = "id")})
    @ApiModelProperty(value = "The host.")
    private Host host;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Column(name = "mode", nullable = false, length = 2)
    @Convert(converter = ModeEnumConverter.class)
    private ModeEnum mode;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Column(name = "operation", nullable = false, length = 2)
    @Convert(converter = OperationEnumConverter.class)
    private OperationEnum operation;

    @JsonIgnore
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Timestamp createdAt;

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "hostModeOperation", targetEntity = MerchantHostModeOperation.class, fetch = FetchType.LAZY)
    private Set<MerchantHostModeOperation> merchantHostModeOperation = new HashSet<>();

    private void setId(long value) {
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
     * HostEnum's identification.
     *
     * @param value
     */
    public void setHost(Host value) {
        this.host = value;
    }

    /**
     * HostEnum's identification.
     *
     * @return
     */
    public Host getHost() {
        return host;
    }

    /**
     * ModeEnum's identification.
     *
     * @param value
     */
    public void setMode(ModeEnum value) {
        this.mode = value;
    }

    /**
     * ModeEnum's identification.
     *
     * @return
     */
    public ModeEnum getMode() {
        return mode;
    }

    /**
     * OperationEnum's identification.
     *
     * @param value
     */
    public void setOperation(OperationEnum value) {
        this.operation = value;
    }

    /**
     * OperationEnum's identification.
     *
     * @return
     */
    public OperationEnum getOperation() {
        return operation;
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

    /**
     *
     * @return
     */
    public Set<MerchantHostModeOperation> getMerchantHostModeOperation() {
        return merchantHostModeOperation;
    }

    /**
     *
     * @param merchantHostModeOperation
     */
    public void setMerchantHostModeOperation(Set<MerchantHostModeOperation> merchantHostModeOperation) {
        this.merchantHostModeOperation = merchantHostModeOperation;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
