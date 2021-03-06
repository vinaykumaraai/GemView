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
import com.luretechnologies.common.enums.TerminalSettingEnum;
import com.luretechnologies.common.enums.TerminalSettingGroupEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 *
 * Gemstone
 */
@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-11-24T17:14:23.156-05:00")
public class TerminalSettingValue {

    private final Long id = null;
    private TerminalSettingEnum terminalSetting = null;
    private Terminal terminal = null;
    private String value = null;
    private String defaultValue = null;
    private TerminalSettingGroupEnum terminalSettingGroup = null;

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
    @JsonProperty("terminalSetting")
    public TerminalSettingEnum getTerminalSetting() {
        return terminalSetting;
    }

    /**
     *
     * @param terminalSetting
     */
    public void setTerminalSetting(TerminalSettingEnum terminalSetting) {
        this.terminalSetting = terminalSetting;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("terminalSettingGroup")
    public TerminalSettingGroupEnum getTerminalSettingGroup() {
        return terminalSettingGroup;
    }

    /**
     *
     * @param terminalSettingGroup
     */
    public void setTerminalSettingGroup(TerminalSettingGroupEnum terminalSettingGroup) {
        this.terminalSettingGroup = terminalSettingGroup;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("terminal")
    public Terminal getTerminal() {
        return terminal;
    }

    /**
     *
     * @param terminal
     */
    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    /**
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("defaultValue")
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     *
     * @param defaultValue
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TerminalSettingValue {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
        sb.append("    terminalSetting: ").append(StringUtil.toIndentedString(terminalSetting)).append("\n");
        sb.append("    terminalSettingGroup: ").append(StringUtil.toIndentedString(terminalSettingGroup)).append("\n");
        sb.append("    terminal: ").append(StringUtil.toIndentedString(terminal)).append("\n");
        sb.append("    value: ").append(StringUtil.toIndentedString(value)).append("\n");
        sb.append("    defaultValue: ").append(StringUtil.toIndentedString(defaultValue)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
