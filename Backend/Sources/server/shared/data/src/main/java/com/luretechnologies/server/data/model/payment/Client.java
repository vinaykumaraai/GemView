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
import com.luretechnologies.server.constraints.FieldNotBlank;
import io.swagger.annotations.ApiModelProperty;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Stores clients information.
 */
@Entity
@Table(name = "Client")
public class Client implements Serializable {

    /**
     *
     */
    public Client() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "The database identifier.")
    private long id;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @FieldNotBlank
    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_NAME, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "first_name", nullable = false, length = 100)
    @ApiModelProperty(value = "The first name.", required = true)
    private String firstName;

    @Size(max = 2, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_NAME, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "middle_initial", nullable = true, length = 2)
    @ApiModelProperty(value = "The middle initial name.")
    private String middleInitial;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @FieldNotBlank
    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_NAME, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "last_name", nullable = false, length = 100)
    @ApiModelProperty(value = "The last name.", required = true)
    private String lastName;

    @Size(max = 50, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EMAIL_REG_EXP, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "email", nullable = true, length = 50)
    @ApiModelProperty(value = "The email.")
    private String email;

    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_NAME, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "company", nullable = true, length = 100)
    @ApiModelProperty(value = "The company name.")
    private String company;

    //@NotNull(message = Messages.VALUE_IS_EMPTY)
    @ManyToOne(targetEntity = com.luretechnologies.server.data.model.Entity.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "entity", referencedColumnName = "id")})
    @ApiModelProperty(value = "The entity to which the user belongs. Ex: organization, region, merchant")
    private com.luretechnologies.server.data.model.Entity entity;

    @Column(name = "active", nullable = false, length = 1)
    @ApiModelProperty(value = "Specifies if the record is active.")
    private boolean active = true;

    @OneToOne(targetEntity = DriverLicense.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
        @JoinColumn(name = "dl_data", referencedColumnName = "data")})
    @ApiModelProperty(value = "The driver license.")
    private DriverLicense driverLicense;

    @OneToOne(targetEntity = SocialSecurity.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
        @JoinColumn(name = "ss_data", referencedColumnName = "data")})
    @ApiModelProperty(value = "The social security.")
    private SocialSecurity socialSecurity;

    @ManyToMany(targetEntity = Telephone.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Client_Telephone", joinColumns = {
        @JoinColumn(name = "client")}, inverseJoinColumns = {
        @JoinColumn(name = "telephone")})
    @ApiModelProperty(value = "The telephone list.")
    private Set<Telephone> telephones = new HashSet<>();

    @OneToMany(mappedBy = "client", targetEntity = Account.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ApiModelProperty(value = "The account list.")
    private Set<Account> accounts = new HashSet<>();

    @ManyToMany(targetEntity = Address.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Client_Address", joinColumns = {
        @JoinColumn(name = "client")}, inverseJoinColumns = {
        @JoinColumn(name = "address")})
    @ApiModelProperty(value = "The address list.")
    private Set<Address> addresses = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "client", targetEntity = Transaction.class, fetch = FetchType.LAZY)
    private Set<Transaction> transactions = new HashSet<>();

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
     * Client's first name.
     *
     * @param value
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Client's first name.
     *
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Client's last name.
     *
     * @param value
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Client's last name.
     *
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Client's middle name.
     *
     * @param value
     */
    public void setMiddleInitial(String value) {
        this.middleInitial = value;
    }

    /**
     * Client's middle name.
     *
     * @return
     */
    public String getMiddleInitial() {
        return middleInitial;
    }

    /**
     * Client's email.
     *
     * @param value
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Client's email.
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Client's company.
     *
     * @param value
     */
    public void setCompany(String value) {
        this.company = value;
    }

    /**
     *
     * @return
     */
    public com.luretechnologies.server.data.model.Entity getEntity() {
        return entity;
    }

    /**
     *
     * @param entity
     */
    public void setEntity(com.luretechnologies.server.data.model.Entity entity) {
        this.entity = entity;
    }

    /**
     * Client's company.
     *
     * @return
     */
    public String getCompany() {
        return company;
    }

    /**
     * Flag which says if the client is active or not.
     *
     * @param value
     */
    public void setActive(boolean value) {
        this.active = value;
    }

    /**
     * Flag which says if the client is active or not.
     *
     * @return
     */
    public boolean getActive() {
        return active;
    }

    /**
     *
     * @param value
     */
    public void setDriverLicense(DriverLicense value) {
        this.driverLicense = value;
    }

    /**
     *
     * @return
     */
    public DriverLicense getDriverLicense() {
        return driverLicense;
    }

    /**
     *
     * @param value
     */
    public void setSocialSecurity(SocialSecurity value) {
        this.socialSecurity = value;
    }

    /**
     *
     * @return
     */
    public SocialSecurity getSocialSecurity() {
        return socialSecurity;
    }

    /**
     *
     * @param value
     */
    public void setTelephones(Set<Telephone> value) {
        this.telephones = value;
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
     * @param value
     */
    public void setAccounts(Set<Account> value) {
        this.accounts = value;
    }

    /**
     *
     * @return
     */
    public Set<Account> getAccounts() {
        return accounts;
    }

    /**
     *
     * @param value
     */
    public void setTransactions(Set<Transaction> value) {
        this.transactions = value;
    }

    /**
     *
     * @return
     */
    public Set<Transaction> getTransactions() {
        return transactions;
    }

    /**
     *
     * @param value
     */
    public void setAddresses(Set<Address> value) {
        this.addresses = value;
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
        return String.valueOf(getId());
    }

}
