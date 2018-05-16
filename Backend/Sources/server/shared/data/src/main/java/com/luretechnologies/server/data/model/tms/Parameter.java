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
import com.luretechnologies.common.enums.FormatEnum;
import com.luretechnologies.common.validator.RegExp;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.constraints.FieldFormat;
import com.luretechnologies.server.constraints.FieldSize;
import com.luretechnologies.server.jpa.converters.FormatEnumConverter;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
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
 * Stores application's parameters.
 */
@FieldSize(message = Messages.DEFAULTVALUE_INVALID_DATA_LENGHT)
@FieldFormat(message = Messages.DEFAULTVALUE_INVALID_DATA_ENTRY)
@Entity
@Table(name = "Parameter")
public class Parameter implements Serializable {

    /**
     *
     */
    public Parameter() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(targetEntity = Group.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "`group`", referencedColumnName = "id", nullable = false)})
    private Group group;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Column(name = "format", nullable = false, length = 2)
    @Convert(converter = FormatEnumConverter.class)
    private FormatEnum format;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_NAME, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 255, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "default_value", nullable = false, length = 255)
    private String defaultValue;

    @Size(max = 255, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_DESC, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "description", nullable = true, length = 255)
    private String description;

    @Column(name = "application_wide", nullable = false, length = 1)
    private boolean applicationWide = false;

    @Column(name = "convert_to_hex", nullable = false, length = 1)
    private boolean convertToHex = false;

    @Column(name = "min_length", nullable = true, length = 10)
    private int minLength;

    @Column(name = "max_length", nullable = true, length = 10)
    private Integer maxLength;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Column(name = "additional_data", nullable = false)
    private String additionalData;

    @JsonIgnore
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
     * Parameter's name.
     *
     * @param value
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Parameter's name.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Parameter's default value.
     *
     * @param value
     */
    public void setDefaultValue(String value) {
        this.defaultValue = value;
    }

    /**
     * Parameter's default value.
     *
     * @return
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Parameter's description.
     *
     * @param value
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Parameter's description.
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return
     */
    public boolean getApplicationWide() {
        return applicationWide;
    }

    /**
     *
     * @param applicationWide
     */
    public void setApplicationWide(boolean applicationWide) {
        this.applicationWide = applicationWide;
    }

    /**
     * Flag which say if the parameter should be converted to hex.
     *
     * @param value
     */
    public void setConvertToHex(boolean value) {
        this.convertToHex = value;
    }

    /**
     * Flag which say if the parameter should be converted to hex.
     *
     * @return
     */
    public boolean getConvertToHex() {
        return convertToHex;
    }

    /**
     * Parameter's minimun length.
     *
     * @param value
     */
    public void setMinLength(int value) {
        this.minLength = value;
    }

    /**
     * Parameter's minimun length.
     *
     * @return
     */
    public int getMinLength() {
        return minLength;
    }

    /**
     * Parameter's maximum length.
     *
     * @param value
     */
    public void setMaxLength(int value) {
        setMaxLengthInteger(new Integer(value));
    }

    /**
     * Parameter's maximum length.
     *
     * @param value
     */
    @JsonIgnore
    public void setMaxLengthInteger(Integer value) {
        this.maxLength = value;
    }

    /**
     * Parameter's maximum length.
     *
     * @return
     */
    public Integer getMaxLength() {
        return maxLength;
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
     * Parameter's format.
     *
     * @param value
     */
    public void setFormat(FormatEnum value) {
        this.format = value;
    }

    /**
     * Parameter's format.
     *
     * @return
     */
    public FormatEnum getFormat() {
        return format;
    }

    /**
     *
     * @param value
     */
    public void setGroup(Group value) {
        this.group = value;
    }

    /**
     *
     * @return
     */
    public Group getGroup() {
        return group;
    }

    /**
     *
     * @return
     */
    public String getAdditionalData() {
        return additionalData;
    }

    /**
     *
     * @param additionalData
     */
    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
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
        final Parameter other = (Parameter) obj;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.name);
        return hash;
    }

    /**
     *
     * @param parameter
     */
    public void setParameterId(Parameter parameter) {
        parameter.setId(this.getId());
    }

}
