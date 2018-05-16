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
package com.luretechnologies.server.data.display.tms;

import com.luretechnologies.common.enums.FormatEnum;
import com.luretechnologies.server.data.model.tms.Group;
import com.luretechnologies.server.data.model.tms.Parameter;
import com.luretechnologies.server.data.model.tms.TerminalApplicationParameter;
import java.sql.Timestamp;

/**
 *
 *
 * @author developer
 */
public class ParameterDisplay {

    private final int version;

    private final Group group;

    private final FormatEnum format;

    private final String name;

    private String defaultValue;

    private final String description;

    private final boolean applicationWide;

    private final boolean convertToHex;

    private final int minLength;

    private final Integer maxLength;

    private final String additionalData;

    private final Timestamp createdAt;

    private final Timestamp updatedAt;

    /**
     *
     * @param parameter
     */
    public ParameterDisplay(Parameter parameter) {
        this.name = parameter.getName();
        this.description = parameter.getDescription();
        this.group = parameter.getGroup();
        this.format = parameter.getFormat();
        this.defaultValue = parameter.getDefaultValue();
        this.applicationWide = parameter.getApplicationWide();
        this.convertToHex = parameter.getConvertToHex();
        this.minLength = parameter.getMinLength();
        this.maxLength = parameter.getMaxLength();
        this.additionalData = parameter.getAdditionalData();

        if (parameter.getApplicationWide()) {
            this.defaultValue = parameter.getDefaultValue();

            this.createdAt = parameter.getCreatedAt();
            this.updatedAt = parameter.getUpdatedAt();
        } else {
            this.defaultValue = parameter.getDefaultValue();

            this.createdAt = null; // TODO terminalApplicationParameter.getCreatedAt();
            this.updatedAt = null; // TODO terminalApplicationParameter.getUpdatedAt();
        }

        this.version = parameter.getVersion();
    }

    /**
     *
     * @param terminalApplicationParameter
     */
    public ParameterDisplay(TerminalApplicationParameter terminalApplicationParameter) {
        Parameter parameter = terminalApplicationParameter.getParameter();

        this.name = parameter.getName();
        this.description = parameter.getDescription();
        this.group = parameter.getGroup();
        this.format = parameter.getFormat();
        this.defaultValue = parameter.getDefaultValue();
        this.applicationWide = parameter.getApplicationWide();
        this.convertToHex = parameter.getConvertToHex();
        this.minLength = parameter.getMinLength();
        this.maxLength = parameter.getMaxLength();
        this.additionalData = parameter.getAdditionalData();

        if (parameter.getApplicationWide()) {
            this.defaultValue = parameter.getDefaultValue();

            this.createdAt = parameter.getCreatedAt();
            this.updatedAt = parameter.getUpdatedAt();
        } else {
            this.defaultValue = terminalApplicationParameter.getValue();

            this.createdAt = null; // TODO terminalApplicationParameter.getCreatedAt();
            this.updatedAt = null; // TODO terminalApplicationParameter.getUpdatedAt();
        }

        this.version = terminalApplicationParameter.getVersion();
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
     * @return
     */
    public String getDefaultValue() {
        return defaultValue;
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
     * @return
     */
    public int getMinLength() {
        return minLength;
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
     * @return
     */
    public Timestamp getUpdatedAt() {
        return updatedAt;
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
     * @return
     */
    public int getVersion() {
        return version;
    }

}
