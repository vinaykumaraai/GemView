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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luretechnologies.common.validator.RegExp;
import com.luretechnologies.server.common.Messages;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 *
 * @author developer
 */
@Entity
@Table(name = "Payment_System_Profile")
public class PaymentSystemProfile implements Serializable {

    /**
     *
     */
    public PaymentSystemProfile() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(targetEntity = PaymentSystem.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "payment_system", referencedColumnName = "id", nullable = false)})
    private PaymentSystem paymentSystem;

    @JsonIgnore
    @ManyToOne(targetEntity = PaymentProfile.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "payment_profile", referencedColumnName = "id", nullable = false)})
    private PaymentProfile paymentProfile;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 255, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "tac_default", nullable = false, length = 255)
    private String tacDefault;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(min = 10, max = 10, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "tac_denial", nullable = false, length = 10)
    private String tacDenial;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(min = 10, max = 10, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "tac_online", nullable = false, length = 10)
    private String tacOnline;

    @Size(min = 2, max = 2, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "tcc", nullable = true, length = 2)
    private String tcc;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 255, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "default_tdol", nullable = false, length = 255)
    private String defaultTDOL;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 255, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "default_ddol", nullable = false, length = 255)
    private String defaultDDOL;

    @Column(name = "version", nullable = false, length = 11)
    private int version;

    @JsonIgnore
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @ManyToMany(targetEntity = CAKey.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "CAKey_Profile", joinColumns = {
        @JoinColumn(name = "payment_system_profile")}, inverseJoinColumns = {
        @JoinColumn(name = "cakey")})
    @OrderBy
    private Set<CAKey> CAKeys = new HashSet<>();

    @OneToMany(mappedBy = "paymentSystemProfile", targetEntity = PaymentProductProfile.class, orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy
    private Set<PaymentProductProfile> paymentProductProfiles = new HashSet<>();

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
     * Represents the TAC Default to be supported by the terminals.
     *
     * @param value
     */
    public void setTacDefault(String value) {
        this.tacDefault = value;
    }

    /**
     * Represents the TAC Default to be supported by the terminals.
     *
     * @return
     */
    public String getTacDefault() {
        return tacDefault;
    }

    /**
     * Represents the TAC Denial to be supported by the terminals.
     *
     * @param value
     */
    public void setTacDenial(String value) {
        this.tacDenial = value;
    }

    /**
     * Represents the TAC Denial to be supported by the terminals.
     *
     * @return
     */
    public String getTacDenial() {
        return tacDenial;
    }

    /**
     * Represents the TAC Online to be supported by the terminals.
     *
     * @param value
     */
    public void setTacOnline(String value) {
        this.tacOnline = value;
    }

    /**
     * Represents the TAC Online to be supported by the terminals.
     *
     * @return
     */
    public String getTacOnline() {
        return tacOnline;
    }

    /**
     * Represents the Transaction Category Code to be supported by the
     * terminals.
     *
     * @param value
     */
    public void setTcc(String value) {
        this.tcc = value;
    }

    /**
     * Represents the Transaction Category Code to be supported by the
     * terminals.
     *
     * @return
     */
    public String getTcc() {
        return tcc;
    }

    /**
     * Represents the Default TDOL as defined by EMV.
     *
     * @param value
     */
    public void setDefaultTDOL(String value) {
        this.defaultTDOL = value;
    }

    /**
     * Represents the Default TDOL as defined by EMV.
     *
     * @return
     */
    public String getDefaultTDOL() {
        return defaultTDOL;
    }

    /**
     * Represents the Default DDOL as defined by EMV.
     *
     * @param value
     */
    public void setDefaultDDOL(String value) {
        this.defaultDDOL = value;
    }

    /**
     * Represents the Default DDOL as defined by EMV.
     *
     * @return
     */
    public String getDefaultDDOL() {
        return defaultDDOL;
    }

    /**
     * Payment System's version.
     *
     * @param value
     */
    public void setVersion(int value) {
        this.version = value;
    }

    /**
     * Payment System's version.
     *
     * @return
     */
    public int getVersion() {
        return version;
    }

    /**
     * Creation date
     *
     * @param value
     */
    public void setCreatedAt(Timestamp value) {
        this.createdAt = value;
    }

    /**
     * Creation date
     *
     * @return
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Last update.
     *
     * @param value
     */
    public void setUpdatedAt(Timestamp value) {
        this.updatedAt = value;
    }

    /**
     * Last update.
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
    public void setPaymentSystem(PaymentSystem value) {
        this.paymentSystem = value;
    }

    /**
     *
     * @return
     */
    public com.luretechnologies.server.data.model.tms.PaymentSystem getPaymentSystem() {
        return paymentSystem;
    }

    /**
     *
     * @param value
     */
    public void setPaymentProfile(PaymentProfile value) {
        this.paymentProfile = value;
    }

    /**
     *
     * @return
     */
    public PaymentProfile getPaymentProfile() {
        return paymentProfile;
    }

    /**
     *
     * @param value
     */
    public void setCAKeys(Set<CAKey> value) {
        this.CAKeys = value;
    }

    /**
     *
     * @return
     */
    public Set<CAKey> getCAKeys() {
        return CAKeys;
    }

    /**
     *
     * @param value
     */
    public void setPaymentProductProfiles(Set<PaymentProductProfile> value) {
        this.paymentProductProfiles = value;
    }

    /**
     *
     * @return
     */
    public Set<PaymentProductProfile> getPaymentProductProfiles() {
        return paymentProductProfiles;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
