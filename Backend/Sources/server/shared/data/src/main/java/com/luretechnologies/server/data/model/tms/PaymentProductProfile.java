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
import java.io.Serializable;
import java.sql.Timestamp;
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
 *
 *
 * @author developer
 */
@Entity
@Table(name = "Payment_Product_Profile")
public class PaymentProductProfile implements Serializable {

    /**
     *
     */
    public PaymentProductProfile() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(targetEntity = PaymentProduct.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "payment_product", referencedColumnName = "id", nullable = false)})
    private PaymentProduct paymentProduct;

    @JsonIgnore
    @ManyToOne(targetEntity = PaymentSystemProfile.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "payment_system_profile", referencedColumnName = "id", nullable = false)})
    private PaymentSystemProfile paymentSystemProfile;

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
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "terminal_app_label", nullable = false, length = 16)
    private String terminalAppLabel;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(min = 2, max = 2, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "fallback_control", nullable = false, length = 2)
    private String fallbackControl;

    @Column(name = "version", nullable = false, length = 11)
    private int version;

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
     * Represents the Card Application Version as supported by the terminal for
     * the given Payment Application.
     *
     * @param value
     */
    public void setAppVersion(String value) {
        this.appVersion = value;
    }

    /**
     * Represents the Card Application Version as supported by the terminal for
     * the given Payment Application.
     *
     * @return
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * Represents Terminal Floor Limit as defined by EMV and used in terminal
     * risk management.
     *
     * @param value
     */
    public void setFloorLimit(String value) {
        this.floorLimit = value;
    }

    /**
     * Represents Terminal Floor Limit as defined by EMV and used in terminal
     * risk management.
     *
     * @return
     */
    public String getFloorLimit() {
        return floorLimit;
    }

    /**
     * Represents the Target Percentage used for Random Selection, EMV
     * attribute. This value is used by the terminal during the risk management
     * assessment
     *
     * @param value
     */
    public void setTprs(String value) {
        this.tprs = value;
    }

    /**
     * Represents the Target Percentage used for Random Selection, EMV
     * attribute. This value is used by the terminal during the risk management
     * assessment
     *
     * @return
     */
    public String getTprs() {
        return tprs;
    }

    /**
     * Represents the Threshold Value for Based Random Selection, EMV attribute.
     * This value is used by the terminal during the risk management assessment.
     *
     * @param value
     */
    public void setTvbrs(String value) {
        this.tvbrs = value;
    }

    /**
     * Represents the Threshold Value for Based Random Selection, EMV attribute.
     * This value is used by the terminal during the risk management assessment.
     *
     * @return
     */
    public String getTvbrs() {
        return tvbrs;
    }

    /**
     * Represents the Maximum Target Percentage for Based Random Selection, EMV
     * attribute. This value is used by the terminal during the risk management
     * assessment.
     *
     * @param value
     */
    public void setMtpbrs(String value) {
        this.mtpbrs = value;
    }

    /**
     * Represents the Maximum Target Percentage for Based Random Selection, EMV
     * attribute. This value is used by the terminal during the risk management
     * assessment.
     *
     * @return
     */
    public String getMtpbrs() {
        return mtpbrs;
    }

    /**
     * Represents the Partial Selection Indicator. This attributes determines if
     * the Partial Selection as defined by EMV is supported for the associated
     * Payment Application. This is a 1 byte binary encoded field. Possible
     * values: 00 – Not supported 01 – Supported
     *
     * @param value
     */
    public void setPartialSelection(String value) {
        this.partialSelection = value;
    }

    /**
     * Represents the Partial Selection Indicator. This attributes determines if
     * the Partial Selection as defined by EMV is supported for the associated
     * Payment Application. This is a 1 byte binary encoded field. Possible
     * values: 00 – Not supported 01 – Supported
     *
     * @return
     */
    public String getPartialSelection() {
        return partialSelection;
    }

    /**
     * Represents the terminal default Application Label as defined by EMV.
     *
     * @param value
     */
    public void setTerminalAppLabel(String value) {
        this.terminalAppLabel = value;
    }

    /**
     * Represents the terminal default Application Label as defined by EMV.
     *
     * @return
     */
    public String getTerminalAppLabel() {
        return terminalAppLabel;
    }

    /**
     * Represents the application fallback control. Allows enabling or disabling
     * fallback for specific applications.
     *
     * @param value
     */
    public void setFallbackControl(String value) {
        this.fallbackControl = value;
    }

    /**
     * Represents the application fallback control. Allows enabling or disabling
     * fallback for specific applications.
     *
     * @return
     */
    public String getFallbackControl() {
        return fallbackControl;
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
     * @param value
     */
    public void setPaymentProduct(PaymentProduct value) {
        this.paymentProduct = value;
    }

    /**
     *
     * @return
     */
    public PaymentProduct getPaymentProduct() {
        return paymentProduct;
    }

    /**
     *
     * @param value
     */
    public void setPaymentSystemProfile(PaymentSystemProfile value) {
        this.paymentSystemProfile = value;
    }

    /**
     *
     * @return
     */
    public PaymentSystemProfile getPaymentSystemProfile() {
        return paymentSystemProfile;
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
     * @param createdAt
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
