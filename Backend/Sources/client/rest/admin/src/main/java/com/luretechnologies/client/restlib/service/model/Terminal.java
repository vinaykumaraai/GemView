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
public class Terminal extends Entity {

    private String serialNumber = null;
    private Model model = null;
    private KeyBlock keyBlock = null;
    private ScheduleGroup scheduleGroup = null;
    private List<TerminalHost> terminalHosts = new ArrayList<>();
    private List<TerminalSettingValue> terminalSettingValues = new ArrayList<>();

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("serialNumber")
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     *
     * @param serialNumber
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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
    @JsonProperty("model")
    public Model getModel() {
        return model;
    }

    /**
     *
     * @param model
     */
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("keyBlock")
    public KeyBlock getKeyBlock() {
        return keyBlock;
    }

    /**
     *
     * @param keyBlock
     */
    public void setKeyBlock(KeyBlock keyBlock) {
        this.keyBlock = keyBlock;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("scheduleGroup")
    public ScheduleGroup getScheduleGroup() {
        return scheduleGroup;
    }

    /**
     *
     * @param scheduleGroup
     */
    public void setScheduleGroup(ScheduleGroup scheduleGroup) {
        this.scheduleGroup = scheduleGroup;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("terminalHosts")
    public List<TerminalHost> getTerminalHosts() {
        return terminalHosts;
    }

    /**
     *
     * @param terminalHosts
     */
    public void setTerminalHosts(List<TerminalHost> terminalHosts) {
        this.terminalHosts = terminalHosts;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("terminalSettingValues")
    public List<TerminalSettingValue> getTerminalSettingValues() {
        return terminalSettingValues;
    }

    /**
     *
     * @param terminalSettingValues
     */
    public void setTerminalSettingValues(List<TerminalSettingValue> terminalSettingValues) {
        this.terminalSettingValues = terminalSettingValues;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Terminal {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
        sb.append("    name: ").append(StringUtil.toIndentedString(name)).append("\n");
        sb.append("    description: ").append(StringUtil.toIndentedString(description)).append("\n");
        sb.append("    parentId: ").append(StringUtil.toIndentedString(parentId)).append("\n");
        sb.append("    serialNumber: ").append(StringUtil.toIndentedString(serialNumber)).append("\n");
        sb.append("    active: ").append(StringUtil.toIndentedString(active)).append("\n");
        sb.append("    model: ").append(StringUtil.toIndentedString(model)).append("\n");
        sb.append("    keyBlock: ").append(StringUtil.toIndentedString(keyBlock)).append("\n");
        sb.append("    scheduleGroup: ").append(StringUtil.toIndentedString(scheduleGroup)).append("\n");
        sb.append("    terminalHosts: ").append(StringUtil.toIndentedString(terminalHosts)).append("\n");
        sb.append("    terminalSettingValues: ").append(StringUtil.toIndentedString(terminalSettingValues)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
