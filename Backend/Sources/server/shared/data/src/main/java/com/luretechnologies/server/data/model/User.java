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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.luretechnologies.common.enums.TwoFactorFreqEnum;
import com.luretechnologies.common.validator.RegExp;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.constraints.FieldNotBlank;
import com.luretechnologies.server.constraints.PasswordFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@PasswordFormat(message = Messages.INVALID_PASSWORD_FORMAT)
@javax.persistence.Entity
@Table(name = "User")
public class User implements Serializable {

    /**
     *
     */
    public User() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "The database identification.")
    private long id;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @ManyToOne(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinColumns({
        @JoinColumn(name = "role", referencedColumnName = "id", nullable = false)})
    @ApiModelProperty(value = "The role.", required = true)
    private Role role;

    //@NotNull(message = Messages.VALUE_IS_EMPTY)
    @ManyToOne(targetEntity = Entity.class, fetch = FetchType.EAGER)
    @JoinColumns({
        @JoinColumn(name = "entity", referencedColumnName = "id")})
    @ApiModelProperty(value = "The entity to which the user belongs. Ex: organization, region, merchant")
    private Entity entity;

    @FieldNotBlank
    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_PARTIAL_SPECIAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "username", nullable = false, unique = true, length = 100)
    @ApiModelProperty(value = "The username.", required = true)
    private String username;

    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "password", nullable = false, length = 100)
    @ApiModelProperty(value = "The password.")
    private String password;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @FieldNotBlank
    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_NAME, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "first_name", nullable = false, length = 100)
    @ApiModelProperty(value = "The first  name.", required = true)
    private String firstName;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @FieldNotBlank
    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_NAME, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "last_name", nullable = false, length = 100)
    @ApiModelProperty(value = "The last  name.", required = true)
    private String lastName;

    @Column(name = "active", nullable = false, length = 1)
    @ApiModelProperty(value = "Specifies if the record is active.")
    private boolean active = true;

    @Size(max = 50, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EMAIL_REG_EXP, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "email", nullable = true, length = 50)
    @ApiModelProperty(value = "The email.")
    private String email;

    @JsonIgnore
    @Column(name = "logout_time", nullable = false)
    private Timestamp logoutTime;

    @JsonIgnore
    @Column(name = "password_expiration", nullable = false)
    private Timestamp passwordExpiration;

    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "verification_code", nullable = false, length = 100)
    @ApiModelProperty(value = "The verification code.")
    private String verificationCode;

    @JsonIgnore
    @Column(name = "verification_code_expiration", nullable = false)
    private Timestamp verificationCodeExpiration;

    @JsonIgnore
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Timestamp createdAt;

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "user", targetEntity = AuditLog.class, fetch = FetchType.LAZY)
    private Set<AuditLog> auditLogs = new HashSet<>();

    @Column(name = "require_password_update", nullable = false, length = 1)
    @ApiModelProperty(value = "Specifies if password update is required.")
    private boolean requirePasswordUpdate = true;
    
    @Column(name = "two_factor_freq", nullable = false, length = 11)
    @ApiModelProperty(value = "The two factor frequency.")
    private TwoFactorFreqEnum twoFactorFrequency;

    @JsonIgnore
    @Column(name = "two_factor_at", nullable = false)
    private Timestamp twoFactorAt;

    @Size(max = 45, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "ip_assigned", nullable = true, length = 45)
    @ApiModelProperty(value = "The assigned IP.")
    private String assignedIP;

    @Size(max = 45, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "ip_last_access", nullable = true, length = 45)
    @ApiModelProperty(value = "The IP of last access.")
    private String lastAccessIP;
    
    @Column(name = "Available", nullable = false)
    @ApiModelProperty(value = "If is available or not")
    private Boolean available; 
    
    @Column(name = "password_frequency", nullable = false)
    @ApiModelProperty(value = "Password frequency")
    private Integer passwordFrequency; 

    
    /**
     * User database identification.
     *
     * @param value
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * User database identification.
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * User's username.
     *
     * @param value
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * User's username.
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * User's password.
     *
     * @param value
     */
    @JsonProperty
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * User's password.
     *
     * @return
     */
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    /**
     * User's last update date.
     *
     * @param value
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * User's last update date.
     *
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param value
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     *
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param value
     */
    public void setActive(boolean value) {
        this.active = value;
    }

    /**
     *
     * @return
     */
    public boolean getActive() {
        return active;
    }

    /**
     * User's email.
     *
     * @param value
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * User's email.
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * User's logout time.
     *
     * @param value
     */
    public void setLogoutTime(Timestamp value) {
        this.logoutTime = value;
    }

    /**
     * User's logout time.
     *
     * @return
     */
    public Timestamp getLogoutTime() {
        return logoutTime;
    }

    /**
     * User's creation date.
     *
     * @param value
     */
    public void setCreatedAt(Timestamp value) {
        this.createdAt = value;
    }

    /**
     * User's creation date.
     *
     * @return
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param value
     */
    public void setUpdatedAt(Timestamp value) {
        this.updatedAt = value;
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
     * @param value
     */
    public void setRole(Role value) {
        this.role = value;
    }

    /**
     *
     * @return
     */
    public Role getRole() {
        return role;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public Set<AuditLog> getAuditLogs() {
        return auditLogs;
    }

    /**
     *
     * @param auditLogs
     */
    @JsonIgnore
    public void setAuditLog(Set<AuditLog> auditLogs) {
        this.auditLogs = auditLogs;
    }

    /**
     *
     * @return
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     *
     * @param entity
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

    /**
     * @return the twoFactor status
     */
    public boolean isRequireTwoFactor() {
        return twoFactorFrequency != TwoFactorFreqEnum.OFF;
    }

    /**
     * @return the twoFactorFrequency
     */
    @JsonIgnore
    public TwoFactorFreqEnum getTwoFactorFrequency() {
        return twoFactorFrequency;
    }

    /**
     * @param twoFactorFrequency the twoFactorFrequency to set
     */
    public void setTwoFactorFrequency(TwoFactorFreqEnum twoFactorFrequency) {
        this.twoFactorFrequency = twoFactorFrequency;
    }

    /**
     * @return the twoFactorAt
     */
    @JsonIgnore
    public Timestamp getTwoFactorAt() {
        return twoFactorAt;
    }

    /**
     * @param twoFactorAt the twoFactorAt to set
     */
    public void setTwoFactorAt(Timestamp twoFactorAt) {
        this.twoFactorAt = twoFactorAt;
    }

    /**
     * @return the assignedIP
     */
    public String getAssignedIP() {
        return assignedIP;
    }

    /**
     * @param assignedIP the assignedIP to set
     */
    public void setAssignedIP(String assignedIP) {
        this.assignedIP = assignedIP;
    }

    /**
     * @return the lastAccessIP
     */
    public String getLastAccessIP() {
        return lastAccessIP;
    }

    /**
     * @param lastAccessIP the lastAccessIP to set
     */
    public void setLastAccessIP(String lastAccessIP) {
        this.lastAccessIP = lastAccessIP;
    }

    /**
     * @return the requirePasswordUpdate
     */
    public boolean isRequirePasswordUpdate() {
        return requirePasswordUpdate;
    }

    /**
     * @param requirePasswordUpdate the requirePasswordUpdate to set
     */
    public void setRequirePasswordUpdate(boolean requirePasswordUpdate) {
        this.requirePasswordUpdate = requirePasswordUpdate;
    }

    /**
     * @return the passwordExpiration
     */
    public Timestamp getPasswordExpiration() {
        return passwordExpiration;
    }

    /**
     * @param passwordExpiration the passwordExpiration to set
     */
    public void setPasswordExpiration(Timestamp passwordExpiration) {
        this.passwordExpiration = passwordExpiration;
    }

    /**
     * @return the verificationCode
     */
    @JsonIgnore
    public String getVerificationCode() {
        return verificationCode;
    }

    /**
     * @param verificationCode the verificationCode to set
     */
    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    /**
     * @return the verificationCodeExpiration
     */
    @JsonIgnore
    public Timestamp getVerificationCodeExpiration() {
        return verificationCodeExpiration;
    }

    /**
     * @param verificationCodeExpiration the verificationCodeExpiration to set
     */
    public void setVerificationCodeExpiration(Timestamp verificationCodeExpiration) {
        this.verificationCodeExpiration = verificationCodeExpiration;
    }
    /**
     * @return the available
     */
    public Boolean getAvailable() {
        return available;
    }

    /**
     * @param available the available to set
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }
    /**
     * @return the passwordFrequency
     */
    public Integer getPasswordFrequency() {
        return passwordFrequency;
    }

    /**
     * @param passwordFrequency the passwordFrequency to set
     */
    public void setPasswordFrequency(Integer passwordFrequency) {
        this.passwordFrequency = passwordFrequency;
    }    
}
