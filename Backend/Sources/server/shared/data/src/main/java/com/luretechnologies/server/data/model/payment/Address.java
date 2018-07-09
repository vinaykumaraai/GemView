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
import com.luretechnologies.common.enums.AddressTypeEnum;
import com.luretechnologies.common.validator.RegExp;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.constraints.FieldNotBlank;
import com.luretechnologies.server.data.model.Merchant;
import com.luretechnologies.server.jpa.converters.AddressTypeEnumConverter;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Store addresses.
 */
@Entity
@Table(name = "Address")
public class Address implements Serializable {

    /**
     *
     */
    public Address() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "The database identification.")
    private long id;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @ManyToOne(targetEntity = State.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "state", referencedColumnName = "id", nullable = false)})
    @ApiModelProperty(value = "The state.", required = true)
    private State state;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "country", referencedColumnName = "id", nullable = false)})
    @ApiModelProperty(value = "The country.", required = true)
    private Country country;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @FieldNotBlank
    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "address1", nullable = false, length = 100)
    @ApiModelProperty(value = "The street and number.", required = true)
    private String address1;

    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "address2", nullable = true, length = 100)
    @ApiModelProperty(value = "The apartment, floor number, etc.")
    private String address2;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @FieldNotBlank
    @Size(max = 50, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_NAME, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "city", nullable = false, length = 50)
    @ApiModelProperty(value = "The city.", required = true)
    private String city;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @FieldNotBlank
    @Size(max = 10, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ZIP_CODE, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "zip", nullable = false, length = 10)
    @ApiModelProperty(value = "The zip code.", required = true)
    private String zip;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Column(name = "type", nullable = false, length = 2)
    @Convert(converter = AddressTypeEnumConverter.class)
    @ApiModelProperty(value = "The address type.", required = true)
    private AddressTypeEnum type;

    @JsonIgnore
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Timestamp createdAt;

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;
    
    @JsonIgnore
    @ManyToMany(targetEntity = Merchant.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "Merchant_Address", joinColumns = {
        @JoinColumn(name = "address")}, inverseJoinColumns = {
        @JoinColumn(name = "merchant")})
    @ApiModelProperty(value = "The merchants.")
    private Set<Merchant> merchants = new HashSet<>();
    
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
     * Address's first part information.
     *
     * @param value
     */
    public void setAddress1(String value) {
        this.address1 = value;
    }

    /**
     * Address's first part information.
     *
     * @return
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Address's second part information.
     *
     * @param value
     */
    public void setAddress2(String value) {
        this.address2 = value;
    }

    /**
     * Address's second part information.
     *
     * @return
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Address's city.
     *
     * @param value
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Address's city.
     *
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     * Address's zip.
     *
     * @param value
     */
    public void setZip(String value) {
        this.zip = value;
    }

    /**
     * Address's zip.
     *
     * @return
     */
    public String getZip() {
        return zip;
    }

    /**
     *
     * @return
     */
    public AddressTypeEnum getType() {
        return type;
    }

    /**
     * Address's type. Ex: Billing, Domicile.
     *
     * @param type
     */
    public void setType(AddressTypeEnum type) {
        this.type = type;
    }

    /**
     *
     * @param value
     */
    public void setState(State value) {
        this.state = value;
    }

    /**
     *
     * @return
     */
    public State getState() {
        return state;
    }

    /**
     *
     * @param value
     */
    public void setCountry(Country value) {
        this.country = value;
    }

    /**
     *
     * @return
     */
    public Country getCountry() {
        return country;
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
    
    public Set<Merchant> getMerchants() {
        return merchants;
    }

    public void setMerchants(Set<Merchant> merchants) {
        this.merchants = merchants;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
