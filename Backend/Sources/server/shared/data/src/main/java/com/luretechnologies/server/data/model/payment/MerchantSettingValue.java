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
import com.luretechnologies.common.enums.MerchantSettingEnum;
import com.luretechnologies.common.enums.MerchantSettingGroupEnum;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.data.model.Merchant;
import com.luretechnologies.server.jpa.converters.MerchantSettingEnumConverter;
import com.luretechnologies.server.jpa.converters.MerchantSettingGroupEnumConverter;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Stores merchant setting's value.
 */
@Entity
@Table(name = "Merchant_Setting_Value")
public class MerchantSettingValue implements Serializable {

    /**
     *
     */
    public MerchantSettingValue() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "The database identifier.")
    private long id;

    @Column(name = "merchant_setting", nullable = false, length = 2)
    @Convert(converter = MerchantSettingEnumConverter.class)
    @ApiModelProperty(value = "The merchant settings.")
    private MerchantSettingEnum merchantSetting;

    @Column(name = "`group`", nullable = false, length = 2)
    @Convert(converter = MerchantSettingGroupEnumConverter.class)
    @ApiModelProperty(value = "The merchant settings group.")
    private MerchantSettingGroupEnum merchantSettingGroup;

    @JsonIgnore
    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @ManyToOne(targetEntity = Merchant.class, fetch = FetchType.EAGER)
    @JoinColumns({
        @JoinColumn(name = "merchant", referencedColumnName = "id", nullable = false)})
    private Merchant merchant;

    @Size(max = 200, message = Messages.INVALID_DATA_LENGHT)
    //@Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "value", nullable = false, length = 200)
    @ApiModelProperty(value = "The merchant settings value.")
    private String value;

    @Size(max = 200, message = Messages.INVALID_DATA_LENGHT)
    //@Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "default_value", nullable = false, length = 200)
    @ApiModelProperty(value = "The merchant settings default value.")
    private String defaultValue;

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
     * Setting's value.
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Setting's value.
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
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     *
     * @param defaultValue
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     *
     * @return
     */
    public MerchantSettingEnum getMerchantSetting() {
        return merchantSetting;
    }

    /**
     *
     * @param merchantSetting
     */
    public void setMerchantSetting(MerchantSettingEnum merchantSetting) {
        this.merchantSetting = merchantSetting;
    }

    /**
     *
     * @return
     */
    public MerchantSettingGroupEnum getMerchantSettingGroup() {
        return merchantSettingGroup;
    }

    /**
     *
     * @param merchantSettingGroup
     */
    public void setMerchantSettingGroup(MerchantSettingGroupEnum merchantSettingGroup) {
        this.merchantSettingGroup = merchantSettingGroup;
    }

    /**
     *
     * @param value
     */
    public void setMerchant(Merchant value) {
        this.merchant = value;
    }

    /**
     *
     * @return
     */
    public Merchant getMerchant() {
        return merchant;
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
