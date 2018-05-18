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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.luretechnologies.common.validator.RegExp;
import com.luretechnologies.server.common.Messages;
import java.io.Serializable;
import java.util.Objects;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Stores Products (Payment Applications). Ex Maestro, Electron, etc
 */
@Entity
@Table(name = "Payment_Product")
public class PaymentProduct implements Serializable {

    /**
     *
     */
    public PaymentProduct() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonIgnore
    @ManyToOne(targetEntity = PaymentSystem.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "payment_system", referencedColumnName = "id", nullable = false)})
    private PaymentSystem paymentSystem;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(min = 10, max = 32, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "aid", nullable = false, unique = true, length = 32)
    private String aid;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_NAME, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Size(max = 255, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_DESC, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "description", nullable = true, length = 255)
    private String description;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(min = 4, max = 4, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "app_version", nullable = false, length = 4)
    private String appVersion;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(min = 8, max = 8, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "floor_limit", nullable = false, length = 8)
    private String floorLimit;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(min = 2, max = 2, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "tprs", nullable = false, length = 2)
    private String tprs;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(min = 8, max = 8, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "tvbrs", nullable = false, length = 8)
    private String tvbrs;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(min = 2, max = 2, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "mtpbrs", nullable = false, length = 2)
    private String mtpbrs;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(min = 2, max = 2, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "partial_selection", nullable = false, length = 2)
    private String partialSelection;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 16, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "terminal_app_label", nullable = false, length = 16)
    private String terminalAppLabel;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(min = 2, max = 2, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "fallback_control", nullable = false, length = 2)
    private String fallbackControl;

//    @OneToMany(mappedBy = "paymentProduct", targetEntity = PaymentProductProfile.class)
//    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.LOCK})
//    @LazyCollection(LazyCollectionOption.TRUE)
//    private Set<PaymentProductProfile> paymentProductProfiles = new HashSet<>();
    /**
     * Database identification.
     */
    @JsonIgnore
    private void setId(long value) {
        this.id = value;
    }

    /**
     * Database identification.
     *
     * @return
     */
    @JsonProperty
    public long getId() {
        return id;
    }

    /**
     * Represents the Application Identifier as defined by EMV and assigned by
     * ISO to identify EMV card applications.
     *
     * @param value
     */
    public void setAid(String value) {
        this.aid = value;
    }

    /**
     * Represents the Application Identifier as defined by EMV and assigned by
     * ISO to identify EMV card applications.
     *
     * @return
     */
    public String getAid() {
        return aid;
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
    @JsonIgnore
    public PaymentSystem getPaymentSystem() {
        return paymentSystem;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     *
     * @return
     */
    public String getFloorLimit() {
        return floorLimit;
    }

    /**
     *
     * @return
     */
    public String getTprs() {
        return tprs;
    }

    /**
     *
     * @return
     */
    public String getTvbrs() {
        return tvbrs;
    }

    /**
     *
     * @return
     */
    public String getMtpbrs() {
        return mtpbrs;
    }

    /**
     *
     * @return
     */
    public String getPartialSelection() {
        return partialSelection;
    }

    /**
     *
     * @return
     */
    public String getTerminalAppLabel() {
        return terminalAppLabel;
    }

    /**
     *
     * @return
     */
    public String getFallbackControl() {
        return fallbackControl;
    }

    /**
     *
     * @param appVersion
     */
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    /**
     *
     * @param floorLimit
     */
    public void setFloorLimit(String floorLimit) {
        this.floorLimit = floorLimit;
    }

    /**
     *
     * @param tprs
     */
    public void setTprs(String tprs) {
        this.tprs = tprs;
    }

    /**
     *
     * @param tvbrs
     */
    public void setTvbrs(String tvbrs) {
        this.tvbrs = tvbrs;
    }

    /**
     *
     * @param mtpbrs
     */
    public void setMtpbrs(String mtpbrs) {
        this.mtpbrs = mtpbrs;
    }

    /**
     *
     * @param partialSelection
     */
    public void setPartialSelection(String partialSelection) {
        this.partialSelection = partialSelection;
    }

    /**
     *
     * @param terminalAppLabel
     */
    public void setTerminalAppLabel(String terminalAppLabel) {
        this.terminalAppLabel = terminalAppLabel;
    }

    /**
     *
     * @param fallbackControl
     */
    public void setFallbackControl(String fallbackControl) {
        this.fallbackControl = fallbackControl;
    }

//    public void setPaymentProductProfiles(Set<PaymentProductProfile> value) {
//        this.paymentProductProfiles = value;
//    }
//
//    public Set<PaymentProductProfile> getPaymentProductProfiles() {
//        return paymentProductProfiles;
//    }
    @Override
    public String toString() {
        return String.valueOf(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PaymentProduct other = (PaymentProduct) obj;
        return this.aid.equals(other.aid);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.aid);
        return hash;
    }

}
