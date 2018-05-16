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
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Stores Key Block.
 */
@Entity
@Table(name = "Key_Block")
public class KeyBlock implements Serializable {

    /**
     *
     */
    public KeyBlock() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "The database identification.")
    private long id;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 512, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "encrypted_key", nullable = false, length = 512)
    @ApiModelProperty(value = "The encrypted key.", required = true)
    private String encryptedKey;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 2, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "type", nullable = false, length = 2)
    @ApiModelProperty(value = "The type.", required = true)
    private String type;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 2, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "length", nullable = false, length = 2)
    @ApiModelProperty(value = "The length.", required = true)
    private String length;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 6, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "kcv", nullable = false, length = 6)
    @ApiModelProperty(value = "The key check value.", required = true)
    private String kcv;

    @Size(max = 20, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "ksn", nullable = false, length = 20)
    @ApiModelProperty(value = "The key serial number.", required = true)
    private String ksn;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 512, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "signature", nullable = false, length = 512)
    @ApiModelProperty(value = "The signature.", required = true)
    private String signature;

    @JsonIgnore
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Timestamp createdAt;

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @JsonIgnore
    @Column(name = "version", nullable = false, length = 11)
    private int version;

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
     *
     * @return
     */
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
        return String.valueOf(getId());
    }

}
