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
import javax.annotation.Generated;

/**
 *
 *
 *
 * @author developer
 */
@ApiModel(description = "")
@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-09-15T15:20:45.337-04:00")
public class KeyBlock {

    private final Long id = null;
    private String encryptedKey = null;
    private String type = null;
    private String length = null;
    private String kcv = null;
    private String ksn = null;
    private String signature = null;

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
    @JsonProperty("encryptedKey")
    public String getEncryptedKey() {
        return encryptedKey;
    }

    /**
     *
     * @param encryptedKey
     */
    public void setEncryptedKey(String encryptedKey) {
        this.encryptedKey = encryptedKey;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("length")
    public String getLength() {
        return length;
    }

    /**
     *
     * @param length
     */
    public void setLength(String length) {
        this.length = length;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("kcv")
    public String getKcv() {
        return kcv;
    }

    /**
     *
     * @param kcv
     */
    public void setKcv(String kcv) {
        this.kcv = kcv;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("ksn")
    public String getKsn() {
        return ksn;
    }

    /**
     *
     * @param ksn
     */
    public void setKsn(String ksn) {
        this.ksn = ksn;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("signature")
    public String getSignature() {
        return signature;
    }

    /**
     *
     * @param signature
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class KeyBlock {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
        sb.append("    encryptedKey: ").append(StringUtil.toIndentedString(encryptedKey)).append("\n");
        sb.append("    type: ").append(StringUtil.toIndentedString(type)).append("\n");
        sb.append("    length: ").append(StringUtil.toIndentedString(length)).append("\n");
        sb.append("    kcv: ").append(StringUtil.toIndentedString(kcv)).append("\n");
        sb.append("    ksn: ").append(StringUtil.toIndentedString(ksn)).append("\n");
        sb.append("    signature: ").append(StringUtil.toIndentedString(signature)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
