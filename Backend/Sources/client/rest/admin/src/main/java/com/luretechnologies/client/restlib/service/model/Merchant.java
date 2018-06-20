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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

/**
 *
 *
 *
 * @author developer
 */
@ApiModel(description = "")
@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-09-15T15:20:45.337-04:00")
public class Merchant extends Entity {

    private List<Address> addresses = new ArrayList<>();
    private List<Telephone> telephones = new ArrayList<>();
    private List<MerchantHost> merchantHosts = new ArrayList<>();
    private List<MerchantSettingValue> merchantSettingValues = new ArrayList<>();

    /**
     *
     * @return
     */
    public List<Address> getAddresses() {
        return addresses;
    }

    /**
     *
     * @param addresses
     */
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    /**
     *
     * @return
     */
    public List<Telephone> getTelephones() {
        return telephones;
    }

    /**
     *
     * @param telephones
     */
    public void setTelephones(List<Telephone> telephones) {
        this.telephones = telephones;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("merchantHosts")
    public List<MerchantHost> getMerchantHosts() {
        return merchantHosts;
    }

    /**
     *
     * @param merchantHosts
     */
    public void setMerchantHosts(List<MerchantHost> merchantHosts) {
        this.merchantHosts = merchantHosts;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("merchantSettingValues")
    public List<MerchantSettingValue> getMerchantSettingValues() {
        return merchantSettingValues;
    }

    /**
     *
     * @param merchantSettingValues
     */
    public void setMerchantSettingValues(List<MerchantSettingValue> merchantSettingValues) {
        this.merchantSettingValues = merchantSettingValues;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Merchant {\n");
        sb.append("    name: ").append(StringUtil.toIndentedString(name)).append("\n");
        sb.append("    description: ").append(StringUtil.toIndentedString(description)).append("\n");
        sb.append("    parentId: ").append(StringUtil.toIndentedString(parentId)).append("\n");
        sb.append("    entityId: ").append(StringUtil.toIndentedString(entityId)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
