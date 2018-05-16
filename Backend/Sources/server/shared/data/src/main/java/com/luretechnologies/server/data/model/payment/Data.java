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
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.constraints.FieldNotBlank;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Stores sensitive information.
 */
@Entity
@Table(name = "Data")
@Inheritance(strategy = InheritanceType.JOINED)
public class Data implements Serializable {

    /**
     *
     */
    public Data() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "The database identification.")
    private long id;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @FieldNotBlank
    @Size(max = 64, message = Messages.INVALID_DATA_LENGHT)
    //@Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_NAME, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "data", nullable = false, length = 64)
    @ApiModelProperty(value = "The number.", required = true)
    private String data;

    @Size(max = 200, message = Messages.INVALID_DATA_LENGHT)
    //@Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_NAME, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "key_serial_number", nullable = true, length = 200)
    @ApiModelProperty(value = "The encryption key serial number.")
    private String keySerialNumber;

    @Digits(integer = 2, fraction = 0, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "key_id", nullable = true, length = 2)
    @ApiModelProperty(value = "The encryption key identification.")
    private int keyId;

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
     * Data.
     *
     * @param value
     */
    public void setData(String value) {
        this.data = value;
    }

    /**
     * Data.
     *
     * @return
     */
    public String getData() {
        return data;
    }

    /**
     * Key's serial number.
     *
     * @param value
     */
    public void setKeySerialNumber(String value) {
        this.keySerialNumber = value;
    }

    /**
     * Key's serial number.
     *
     * @return
     */
    public String getKeySerialNumber() {
        return keySerialNumber;
    }

    /**
     * Key which is used to encrypt the data.
     *
     * @param value
     */
    public void setKeyId(int value) {
        this.keyId = value;
    }

    /**
     * Key which is used to encrypt the data.
     *
     * @return
     */
    public int getKeyId() {
        return keyId;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
