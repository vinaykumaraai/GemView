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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashboardWidgetDisplay {

    @ApiModelProperty(value = "The current connections ", required = false)
    private Integer connectionsNumber;

    @ApiModelProperty(value = "How many heartbeat per (System paramter that define the time in seconds)", required = false)
    private Integer heartbeatPer;

    @ApiModelProperty(value = "Successful downloads (24 hours)", required = false)
    private Integer downloadSuccess;

    @ApiModelProperty(value = "Fail downloads (24 hours)", required = false)
    private Integer downloadFail;

    @ApiModelProperty(value = "Heartbeats per hours (1 hours each)", required = false)
    private List<Long> heartbeatPerHour;

    @ApiModelProperty(value = "Current downloads", required = false)
    private List<DashboardCurrentDownLoadsDisplay> currentDownLoadsDisplays;

    /**
     * No args constructor for use in serialization
     *
     */
    public DashboardWidgetDisplay() {
        heartbeatPerHour = new ArrayList<>();
    }

    /**
     * @return the connectionsNumber
     */
    public Integer getConnectionsNumber() {
        return connectionsNumber;
    }

    /**
     * @param connectionsNumber the connectionsNumber to set
     */
    public void setConnectionsNumber(Integer connectionsNumber) {
        this.connectionsNumber = connectionsNumber;
    }

    /**
     * @return the heartbeatPer
     */
    public Integer getHeartbeatPer() {
        return heartbeatPer;
    }

    /**
     * @param heartbeatPer the heartbeatPer to set
     */
    public void setHeartbeatPer(Integer heartbeatPer) {
        this.heartbeatPer = heartbeatPer;
    }

    /**
     * @return the downloadSuccess
     */
    public Integer getDownloadSuccess() {
        return downloadSuccess;
    }

    /**
     * @param downloadSuccess the downloadSuccess to set
     */
    public void setDownloadSuccess(Integer downloadSuccess) {
        this.downloadSuccess = downloadSuccess;
    }

    /**
     * @return the downloadFail
     */
    public Integer getDownloadFail() {
        return downloadFail;
    }

    /**
     * @param downloadFail the downloadFail to set
     */
    public void setDownloadFail(Integer downloadFail) {
        this.downloadFail = downloadFail;
    }

    /**
     * @return the heartbeatPerHour
     */
    public List<Long> getHeartbeatPerHour() {
        return heartbeatPerHour;
    }

    /**
     * @param heartbeatPerHour the heartbeatPerHour to set
     */
    public void setHeartbeatPerHour(List<Long> heartbeatPerHour) {
        this.heartbeatPerHour = heartbeatPerHour;
    }

    /**
     * @return the currentDownLoadsDisplays
     */
    public List<DashboardCurrentDownLoadsDisplay> getCurrentDownLoadsDisplays() {
        return currentDownLoadsDisplays;
    }

    /**
     * @param currentDownLoadsDisplays the currentDownLoadsDisplays to set
     */
    public void setCurrentDownLoadsDisplays(List<DashboardCurrentDownLoadsDisplay> currentDownLoadsDisplays) {
        this.currentDownLoadsDisplays = currentDownLoadsDisplays;
    }

   

}
