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
package com.luretechnologies.server.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luretechnologies.server.data.model.payment.Address;
import com.luretechnologies.server.data.model.payment.MerchantHost;
import com.luretechnologies.server.data.model.payment.MerchantHostModeOperation;
import com.luretechnologies.server.data.model.payment.MerchantSettingValue;
import com.luretechnologies.server.data.model.payment.Telephone;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Stores all Merchants.
 */
@Entity
@Table(name = "Merchant")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class Merchant extends com.luretechnologies.server.data.model.Entity implements Serializable {

    /**
     *
     */
    public Merchant() {
    }

    //@JsonIgnore
    @OneToMany(mappedBy = "merchant", targetEntity = MerchantHost.class, fetch = FetchType.LAZY)
    @ApiModelProperty(value = "The hosts.")
    private Set<MerchantHost> merchantHosts = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "merchant", targetEntity = MerchantHostModeOperation.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MerchantHostModeOperation> merchantHostModeOperations = new HashSet<>();

    @OneToMany(mappedBy = "merchant", targetEntity = MerchantSettingValue.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ApiModelProperty(value = "The settings.")
    private Set<MerchantSettingValue> merchantSettingValues = new HashSet<>();

    @ManyToMany(targetEntity = Address.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "Merchant_Address", joinColumns = {
        @JoinColumn(name = "merchant")}, inverseJoinColumns = {
        @JoinColumn(name = "address")})
    @ApiModelProperty(value = "The addresses.")
    private Set<Address> addresses = new HashSet<>();

    @ManyToMany(targetEntity = Telephone.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "Merchant_Telephone", joinColumns = {
        @JoinColumn(name = "merchant")}, inverseJoinColumns = {
        @JoinColumn(name = "telephone")})
    @ApiModelProperty(value = "The telephones.")
    private Set<Telephone> telephones = new HashSet<>();

    /**
     *
     * @return
     */
//    @JsonIgnore
//    public Set<EntitySurvey> getEntitySurveys() {
//        return entitySurveys;
//    }
//
//    /**
//     *
//     * @param entitySurveys
//     */
//    public void setEntitySurveys(Set<EntitySurvey> entitySurveys) {
//        this.entitySurveys = entitySurveys;
//    }
    /**
     *
     * @return
     */
    public Set<MerchantHost> getMerchantHosts() {
        return merchantHosts;
    }

    /**
     *
     * @param merchantHosts
     */
    public void setMerchantHosts(Set<MerchantHost> merchantHosts) {
        this.merchantHosts = merchantHosts;
    }

    /**
     *
     * @return
     */
    public Set<MerchantHostModeOperation> getMerchantHostModeOperations() {
        return merchantHostModeOperations;
    }

    /**
     *
     * @param merchantHostModeOperations
     */
    public void setMerchantHostModeOperations(Set<MerchantHostModeOperation> merchantHostModeOperations) {
        this.merchantHostModeOperations = merchantHostModeOperations;
    }

    /**
     *
     * @return
     */
    public Set<MerchantSettingValue> getMerchantSettingValues() {
        return merchantSettingValues;
    }

    /**
     *
     * @param merchantSettingValues
     */
    public void setMerchantSettingValues(Set<MerchantSettingValue> merchantSettingValues) {
        this.merchantSettingValues = merchantSettingValues;
    }

    /**
     *
     * @return
     */
    public Set<Address> getAddresses() {
        return addresses;
    }

    /**
     *
     * @param addresses
     */
    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    /**
     *
     * @return
     */
    public Set<Telephone> getTelephones() {
        return telephones;
    }

    /**
     *
     * @param telephones
     */
    public void setTelephones(Set<Telephone> telephones) {
        this.telephones = telephones;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
