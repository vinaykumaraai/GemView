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
import com.luretechnologies.common.validator.RegExp;
import com.luretechnologies.server.common.Messages;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Stores check information.
 */
@Entity
@Table(name = "CKA_Data")
@Inheritance(strategy = InheritanceType.JOINED)
public class CheckAccount extends Account implements Serializable {

    /**
     *
     */
    public CheckAccount() {
    }

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @ManyToOne(targetEntity = Bank.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "bank", referencedColumnName = "id", nullable = false)})
    private Bank bank;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Digits(integer = 4, fraction = 0, message = Messages.INVALID_DATA_LENGHT)
    //@Pattern(regexp = RegExp.VALID_TEXT_IIII_REG_EXP, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "number", nullable = false, length = 4)
    private int number;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 8, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "`date`", nullable = false, length = 8)
    private String date;

    @Size(max = 255, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_DESC, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "memo", nullable = true, length = 255)
    private String memo;

    @JsonIgnore
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Timestamp createdAt;

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    /**
     * Check's number.
     *
     * @param value
     */
    public void setNumber(int value) {
        this.number = value;
    }

    /**
     * Check's number.
     *
     * @return
     */
    public int getNumber() {
        return number;
    }

    /**
     * Check's date.
     *
     * @param value
     */
    public void setDate(String value) {
        this.date = value;
    }

    /**
     * Check's date.
     *
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     * Check's memo.
     *
     * @param value
     */
    public void setMemo(String value) {
        this.memo = value;
    }

    /**
     * Check's memo.
     *
     * @return
     */
    public String getMemo() {
        return memo;
    }

    /**
     *
     * @param value
     */
    public void setBank(Bank value) {
        this.bank = value;
    }

    /**
     *
     * @return
     */
    public Bank getBank() {
        return bank;
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
        return super.toString();
    }

}
