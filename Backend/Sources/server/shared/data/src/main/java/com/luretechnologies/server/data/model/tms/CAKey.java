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
import java.util.Objects;
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
 * Stores Certification Authority Keys.
 */
@Entity
@Table(name = "CA_Key")
public class CAKey implements Serializable {

    /**
     *
     */
    public CAKey() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(min = 2, max = 2, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "`index`", nullable = false, unique = true, length = 2)
    private String index;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(min = 2, max = 2, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "hash_algorithm", nullable = false, length = 2)
    private String hashAlgorithm;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(min = 2, max = 2, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "public_key_algorithm", nullable = false, length = 2)
    private String publicKeyAlgorithm;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(min = 40, max = 40, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "key_check_sum", nullable = false, length = 40)
    private String keyCheckSum;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 496, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "modulus", nullable = false, length = 496)
    private String modulus;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(min = 2, max = 6, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_HEXADECIMAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "exponent", nullable = false, length = 6)
    private String exponent;

    @JsonIgnore
    @Column(name = "version", nullable = false, length = 11)
    private int version;

    @JsonIgnore
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Timestamp createdAt;

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

//    @ManyToMany(mappedBy = "CAKeys", targetEntity = PaymentSystemProfile.class)
//    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.LOCK})
//    @LazyCollection(LazyCollectionOption.TRUE)
//    private Set<PaymentSystemProfile> paymentSystemProfiles = new HashSet<>();
    /**
     *
     * @return
     */
    public String getIndex() {
        return index;
    }

    /**
     *
     * @param index
     */
    public void setIndex(String index) {
        this.index = index;
    }

    /**
     *
     * @return
     */
    public String getHashAlgorithm() {
        return hashAlgorithm;
    }

    /**
     *
     * @param hashAlgorithm
     */
    public void setHashAlgorithm(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    /**
     *
     * @return
     */
    public String getPublicKeyAlgorithm() {
        return publicKeyAlgorithm;
    }

    /**
     *
     * @param publicKeyAlgorithm
     */
    public void setPublicKeyAlgorithm(String publicKeyAlgorithm) {
        this.publicKeyAlgorithm = publicKeyAlgorithm;
    }

    /**
     *
     * @return
     */
    public String getKeyCheckSum() {
        return keyCheckSum;
    }

    /**
     *
     * @param keyCheckSum
     */
    public void setKeyCheckSum(String keyCheckSum) {
        this.keyCheckSum = keyCheckSum;
    }

    /**
     *
     * @return
     */
    public String getModulus() {
        return modulus;
    }

    /**
     *
     * @param modulus
     */
    public void setModulus(String modulus) {
        this.modulus = modulus;
    }

    /**
     *
     * @return
     */
    public String getExponent() {
        return exponent;
    }

    /**
     *
     * @param exponent
     */
    public void setExponent(String exponent) {
        this.exponent = exponent;
    }

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
     * @param createdAt
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
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
     * Last update day.
     *
     * @param value
     */
    public void setUpdatedAt(Timestamp value) {
        this.updatedAt = value;
    }

    /**
     * Last update day.
     *
     * @return
     */
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

//    public Set<PaymentSystemProfile> getPaymentSystemProfiles() {
//        return paymentSystemProfiles;
//    }
//
//    public void setPaymentSystemProfiles(Set<PaymentSystemProfile> paymentSystemProfiles) {
//        this.paymentSystemProfiles = paymentSystemProfiles;
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
        final CAKey other = (CAKey) obj;
        return this.index.equals(other.index);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.index);
        return hash;
    }

}
