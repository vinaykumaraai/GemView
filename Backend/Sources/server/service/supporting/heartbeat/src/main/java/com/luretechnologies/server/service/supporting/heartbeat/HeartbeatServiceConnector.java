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
package com.luretechnologies.server.service.supporting.heartbeat;

import com.luretechnologies.server.data.display.tms.HeartbeatDisplay;
import com.luretechnologies.server.data.display.tms.HeartbeatAlertDisplay;
import com.luretechnologies.server.data.display.tms.HeartbeatAuditDisplay;
import com.luretechnologies.server.data.display.tms.HeartbeatOdometerDisplay;
import com.luretechnologies.server.data.display.tms.HeartbeatUpdateParamDisplay;
import com.luretechnologies.server.data.model.tms.AlertAction;
import com.luretechnologies.server.service.AlertActionService;
import com.luretechnologies.server.service.HeartbeatAlertService;
import com.luretechnologies.server.service.HeartbeatService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeartbeatServiceConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatServiceConnector.class);

    @Autowired
    HeartbeatService heartbeatService;

    @Autowired
    HeartbeatAlertService heartbeatAlertService;

    @Autowired
    AlertActionService alertActionService;

    /**
     *
     * @param heartbeat
     * @param correlationId
     * @return
     * @throws Exception
     */
    public Boolean process(HeartbeatDisplay heartbeat, String correlationId) throws Exception {

        LOGGER.info("HeartbeatServiceConnector " + correlationId);
        LOGGER.info("  --> Serial Number: " + heartbeat.getSerialNumber());
        LOGGER.info("  --> Message: " + heartbeat.getMessage());

        heartbeatService.create(heartbeat);

        if (heartbeat.getHeartbeatAlerts() != null) {
            for (HeartbeatAlertDisplay alert : heartbeat.getHeartbeatAlerts()) {
               
                
            }
        }

        if (heartbeat.getHeartbeatAudits() != null) {
            for (HeartbeatAuditDisplay audit : heartbeat.getHeartbeatAudits()) {
                LOGGER.info("  --> Audit: " + audit.getLabel());
            }
        }

        if (heartbeat.getHeartbeatOdometers() != null) {
            for (HeartbeatOdometerDisplay odometer : heartbeat.getHeartbeatOdometers()) {
                LOGGER.info("  --> Odometer: " + odometer.getLabel());
            }
        }

        if (heartbeat.getHeartbeatUpdateParams() != null) {
            for (HeartbeatUpdateParamDisplay updateParamDisplay : heartbeat.getHeartbeatUpdateParams()) {
                LOGGER.info("  --> Parameter Updated in the terminal: " + updateParamDisplay.getAppName() + " Param: " + updateParamDisplay.getName());
                // TODO call Application service that with the parameters ( Terminal serial number, App name, Param name, param value ) 
                // update the terminal applicaion params.
            }

        }

        if (heartbeat.getSwComponents() != null) {

        }

        return true;
    }
}
