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
package com.luretechnologies.client.restlib.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luretechnologies.client.restlib.common.StringUtil;
import com.luretechnologies.common.enums.MerchantSettingEnum;
import com.luretechnologies.common.enums.MerchantSettingGroupEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 *
 * Gemstone
 */
@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-11-24T17:14:23.156-05:00")
public class MerchantSettingValue {

    private final Long id = null;
    private MerchantSettingEnum merchantSetting = null;
    private Merchant merchant = null;
    private String value = null;
    private String defaultValue = null;
    private MerchantSettingGroupEnum merchantSettingGroup = null;

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("merchantSetting")
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
    @ApiModelProperty(value = "")
    @JsonProperty("merchantSettingGroup")
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
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("merchant")
    public Merchant getMerchant() {
        return merchant;
    }

    /**
     *
     * @param merchant
     */
    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("value")
    public String getValue() {
        return value;
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
    @ApiModelProperty(value = "")
    @JsonProperty("defaultValue")
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class MerchantSettingValue {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
        sb.append("    merchantSetting: ").append(StringUtil.toIndentedString(merchantSetting)).append("\n");
        sb.append("    merchantSettingGroup: ").append(StringUtil.toIndentedString(merchantSettingGroup)).append("\n");
        sb.append("    merchant: ").append(StringUtil.toIndentedString(merchant)).append("\n");
        sb.append("    value: ").append(StringUtil.toIndentedString(value)).append("\n");
        sb.append("    defaultValue: ").append(StringUtil.toIndentedString(defaultValue)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
