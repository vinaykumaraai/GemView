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
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Stores a Payment System (for example AMEX or MasterCard).
 */
@Entity
@Table(name = "Payment_System")
public class PaymentSystem implements Serializable {

    /**
     *
     */
    public PaymentSystem() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(min = 10, max = 10, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "rid", nullable = false, unique = true, length = 10)
    private String rid;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(min = 10, max = 10, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "tac_default", nullable = false, length = 10)
    private String tacDefault;

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

    @JsonIgnore
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Timestamp createdAt;

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @OneToMany(targetEntity = CAKey.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
        @JoinColumn(name = "payment_system", nullable = false)})
    @OrderBy
    private Set<CAKey> CAKeys = new HashSet<>();

    @OneToMany(mappedBy = "paymentSystem", targetEntity = PaymentProduct.class, orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy
    private Set<PaymentProduct> paymentProducts = new HashSet<>();

//    @JsonIgnore
//    @OneToMany(mappedBy = "paymentSystem", targetEntity = PaymentSystemProfile.class)
//    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.LOCK})
//    @LazyCollection(LazyCollectionOption.TRUE)
//    private Set<PaymentSystemProfile> paymentSystemProfiles = new HashSet<>();
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
     * Represents the Registered Identifier unique per Payment System and
     * assigned by ISO.
     *
     * @param value
     */
    public void setRid(String value) {
        this.rid = value;
    }

    /**
     * Represents the Registered Identifier unique per Payment System and
     * assigned by ISO.
     *
     * @return
     */
    public String getRid() {
        return rid;
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
    public void setPaymentProducts(Set<PaymentProduct> value) {
        this.paymentProducts = value;
    }

    /**
     *
     * @return
     */
    public Set<PaymentProduct> getPaymentProducts() {
        return paymentProducts;
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
    public Timestamp getCreatedAt() {
        return createdAt;
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
     * @param createdAt
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @param updatedAt
     */
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     */
    public String getTacDefault() {
        return tacDefault;
    }

    /**
     *
     * @return
     */
    public String getTacDenial() {
        return tacDenial;
    }

    /**
     *
     * @return
     */
    public String getTacOnline() {
        return tacOnline;
    }

    /**
     *
     * @return
     */
    public String getTcc() {
        return tcc;
    }

    /**
     *
     * @param tacDefault
     */
    public void setTacDefault(String tacDefault) {
        this.tacDefault = tacDefault;
    }

    /**
     *
     * @param tacDenial
     */
    public void setTacDenial(String tacDenial) {
        this.tacDenial = tacDenial;
    }

    /**
     *
     * @param tacOnline
     */
    public void setTacOnline(String tacOnline) {
        this.tacOnline = tacOnline;
    }

    /**
     *
     * @param tcc
     */
    public void setTcc(String tcc) {
        this.tcc = tcc;
    }

    /**
     *
     * @return
     */
    public String getDefaultTDOL() {
        return defaultTDOL;
    }

    /**
     *
     * @param defaultTDOL
     */
    public void setDefaultTDOL(String defaultTDOL) {
        this.defaultTDOL = defaultTDOL;
    }

    /**
     *
     * @return
     */
    public String getDefaultDDOL() {
        return defaultDDOL;
    }

    /**
     *
     * @param defaultDDOL
     */
    public void setDefaultDDOL(String defaultDDOL) {
        this.defaultDDOL = defaultDDOL;
    }

//    public void setPaymentSystemProfiles(Set<PaymentSystemProfile> value) {
//        this.paymentSystemProfiles = value;
//    }
//
//    public Set<PaymentSystemProfile> getPaymentSystemProfiles() {
//        return paymentSystemProfiles;
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
        final PaymentSystem other = (PaymentSystem) obj;
        return this.rid.equals(other.rid);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.rid);
        return hash;
    }
}
