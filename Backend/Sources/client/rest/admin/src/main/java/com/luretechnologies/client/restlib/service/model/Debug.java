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
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

public class Debug {

    private Long id;

    private String serialNumber;

    private List<DebugItem> debugItems = new ArrayList<>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Debug() {
    }

    /**
     * @return the id
     */
    @ApiModelProperty(value = "Id")
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    /**
     * @return the debugItems
     */
    @ApiModelProperty(value = "Debug Items List")
    @JsonProperty("debugItems")
    public List<DebugItem> getDebugItems() {
        return debugItems;
    }

    /**
     * @param debugItems the debugItems to set
     */
    public void setDebugItems(List<DebugItem> debugItems) {
        this.debugItems = debugItems;
    }

    /**
     * @return the serialNumber
     */
    @ApiModelProperty(value = "Serial number")
    @JsonProperty("serialNumber")
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * @param serialNumber the serialNumber to set
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Debug {\n");
        sb.append("    id: ").append(StringUtil.toIndentedString(getId())).append("\n");
        sb.append("    serialNumber: ").append(StringUtil.toIndentedString(getSerialNumber())).append("\n");
        if (debugItems != null) {
            sb.append("    debugItems:[ ").append("\n");
            for (DebugItem temp : debugItems) {
                sb.append(StringUtil.toIndentedString(temp)).append("\n");
                System.out.println(temp.toString());
            }
            sb.append(" ] ").append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
