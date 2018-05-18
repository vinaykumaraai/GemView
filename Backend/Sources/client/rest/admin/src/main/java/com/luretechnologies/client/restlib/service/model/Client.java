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
package com.luretechnologies.client.restlib.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luretechnologies.client.restlib.common.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

/**
 *
 *
 *
 * @author developer
 */
@ApiModel(description = "")
@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-09-15T15:20:45.337-04:00")
public class Client {

    private final Long id = null;
    private String firstName = null;
    private String lastName = null;
    private String middleInitial = null;
    private String email = null;
    private String company = null;
    private Boolean active = null;
    private DriverLicense driverLicense = null;
    private SocialSecurity socialSecurity = null;
    private List<Telephone> telephones = new ArrayList<>();
    private List<Account> accounts = new ArrayList<>();
    private List<Address> addresses = new ArrayList<>();

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("middleInitial")
    public String getMiddleInitial() {
        return middleInitial;
    }

    /**
     *
     * @param middleInitial
     */
    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("company")
    public String getCompany() {
        return company;
    }

    /**
     *
     * @param company
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("active")
    public Boolean getActive() {
        return active;
    }

    /**
     *
     * @param active
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("driverLicense")
    public DriverLicense getDriverLicense() {
        return driverLicense;
    }

    /**
     *
     * @param driverLicense
     */
    public void setDriverLicense(DriverLicense driverLicense) {
        this.driverLicense = driverLicense;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("socialSecurity")
    public SocialSecurity getSocialSecurity() {
        return socialSecurity;
    }

    /**
     *
     * @param socialSecurity
     */
    public void setSocialSecurity(SocialSecurity socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("telephones")
    public List<Telephone> getTelephones() {
        return telephones;
    }

    /**
     *
     * @param telephones
     */
    public void setTelephones(List<Telephone> telephones) {
        this.telephones = telephones;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("accounts")
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     *
     * @param accounts
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("addresses")
    public List<Address> getAddresses() {
        return addresses;
    }

    /**
     *
     * @param addresses
     */
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Client {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
        sb.append("    firstName: ").append(StringUtil.toIndentedString(firstName)).append("\n");
        sb.append("    lastName: ").append(StringUtil.toIndentedString(lastName)).append("\n");
        sb.append("    middleInitial: ").append(StringUtil.toIndentedString(middleInitial)).append("\n");
        sb.append("    email: ").append(StringUtil.toIndentedString(email)).append("\n");
        sb.append("    company: ").append(StringUtil.toIndentedString(company)).append("\n");
        sb.append("    active: ").append(StringUtil.toIndentedString(active)).append("\n");
        sb.append("    driverLicense: ").append(StringUtil.toIndentedString(driverLicense)).append("\n");
        sb.append("    socialSecurity: ").append(StringUtil.toIndentedString(socialSecurity)).append("\n");
        sb.append("    telephones: ").append(StringUtil.toIndentedString(telephones)).append("\n");
        sb.append("    accounts: ").append(StringUtil.toIndentedString(accounts)).append("\n");
        sb.append("    addresses: ").append(StringUtil.toIndentedString(addresses)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
