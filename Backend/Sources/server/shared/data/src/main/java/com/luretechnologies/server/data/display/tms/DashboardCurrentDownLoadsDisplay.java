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

import io.swagger.annotations.ApiModelProperty;

public class DashboardCurrentDownLoadsDisplay {


    @ApiModelProperty(value = "The terminal's serial number.")
    private String serialTerminal;

    @ApiModelProperty(value = "The organization owner.", required = false)
    private String organization;

    @ApiModelProperty(value = "The oprating system.", required = false)
    private String operatingSystem;

    @ApiModelProperty(value = "The ip.", required = false)
    private Long ip;

    @ApiModelProperty(value = "if the alert was sent or not", required = false)
    private String device;

    @ApiModelProperty(value = "Last updated", required = false)
    private Integer completion;

    /**
     * No args constructor for use in serialization
     *
     */
    public DashboardCurrentDownLoadsDisplay() {
    }

    /**
     * @return the serialTerminal
     */
    public String getSerialTerminal() {
        return serialTerminal;
    }

    /**
     * @param serialTerminal the serialTerminal to set
     */
    public void setSerialTerminal(String serialTerminal) {
        this.serialTerminal = serialTerminal;
    }

    /**
     * @return the organization
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * @param organization the organization to set
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * @return the operatingSystem
     */
    public String getOperatingSystem() {
        return operatingSystem;
    }

    /**
     * @param operatingSystem the operatingSystem to set
     */
    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    /**
     * @return the ip
     */
    public Long getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(Long ip) {
        this.ip = ip;
    }

    /**
     * @return the device
     */
    public String getDevice() {
        return device;
    }

    /**
     * @param device the device to set
     */
    public void setDevice(String device) {
        this.device = device;
    }

    /**
     * @return the completion
     */
    public Integer getCompletion() {
        return completion;
    }

    /**
     * @param completion the completion to set
     */
    public void setCompletion(Integer completion) {
        this.completion = completion;
    }

   
   
}
