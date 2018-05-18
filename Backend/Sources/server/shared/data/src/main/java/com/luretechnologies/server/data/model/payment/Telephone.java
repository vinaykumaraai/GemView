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
import com.luretechnologies.common.enums.TelephoneTypeEnum;
import com.luretechnologies.common.validator.RegExp;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.constraints.FieldNotBlank;
import com.luretechnologies.server.data.model.Merchant;
import com.luretechnologies.server.jpa.converters.TelephoneTypeEnumConverter;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Stores all telephones.
 */
@Entity
@Table(name = "Telephone")
public class Telephone implements Serializable {

    /**
     *
     */
    public Telephone() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "The database identification.")
    private long id;

    @FieldNotBlank
    @Size(max = 30, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.PHONE_REG_EXP, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "number", nullable = false, length = 30)
    @ApiModelProperty(value = "The number.", required = true)
    private String number;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Column(name = "type", nullable = false, length = 2)
    @Convert(converter = TelephoneTypeEnumConverter.class)
    @ApiModelProperty(value = "The type.", required = true)
    private TelephoneTypeEnum type;
    
    @JsonIgnore
    @ManyToMany(targetEntity = Merchant.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "Merchant_Telephone", joinColumns = {
        @JoinColumn(name = "telephone")}, inverseJoinColumns = {
        @JoinColumn(name = "merchant")})
    @ApiModelProperty(value = "The merchants.")
    private Set<Merchant> merchants = new HashSet<>();
    
    @JsonIgnore
    @ManyToMany(targetEntity = Client.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "Client_Telephone", joinColumns = {
        @JoinColumn(name = "telephone")}, inverseJoinColumns = {
        @JoinColumn(name = "client")})
    @ApiModelProperty(value = "The clients.")
    private Set<Client> clients = new HashSet<>();

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
     * Telephone's number.
     *
     * @param value
     */
    public void setNumber(String value) {
        this.number = value;
    }

    /**
     * Telephone's number.
     *
     * @return
     */
    public String getNumber() {
        return number;
    }

    /**
     * Telephone's type. Ex: cellular, home.
     *
     * @param value
     */
    public void setType(TelephoneTypeEnum value) {
        this.type = value;
    }

    /**
     * Telephone's type. Ex: cellular, home.
     *
     * @return
     */
    public TelephoneTypeEnum getType() {
        return type;
    }

    public Set<Merchant> getMerchants() {
        return merchants;
    }

    public void setMerchants(Set<Merchant> merchants) {
        this.merchants = merchants;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }
    
    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
