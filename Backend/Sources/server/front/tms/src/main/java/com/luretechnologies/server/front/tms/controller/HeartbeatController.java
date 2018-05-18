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
package com.luretechnologies.server.front.tms.controller;

import com.luretechnologies.server.data.model.tms.DownloadInfo;
import com.luretechnologies.server.data.model.tms.Heartbeat;
import com.luretechnologies.server.data.model.tms.HeartbeatResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Heartbeat Controller Class
 *
 *
 */
@RestController
@RequestMapping("/heartbeat")
@Api(value = "Heartbeats")
public class HeartbeatController {

    /**
     * Creates a new heartbeat
     *
     * @param heartbeat
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Heartbeats", httpMethod = "POST", value = "Create heartbeat", notes = "Creates a new heartbeat")
    public HeartbeatResponse create(@ApiParam(value = "Heartbeat object", required = true) @RequestBody Heartbeat heartbeat) throws Exception {

        DownloadInfo dl = new DownloadInfo();
        dl.setUsername("0fVoCdSvlD0Zw7AI");
        dl.setPassword("S6JD$AYRLihz@5H7ftKLZF4-4iEpnv!TMVe5");
        dl.setFilename("Gtt4SuzVxUpxUdI4jbSix4KYaEni39PK.zip");
        dl.setFtpsUrl("downloadcentral.lure68.net:8022");
        dl.setHttpsUrl(null);
        dl.setWindow(8329251L);

        return new HeartbeatResponse(1L, "OK", 86400L, false, dl);
    }
}
