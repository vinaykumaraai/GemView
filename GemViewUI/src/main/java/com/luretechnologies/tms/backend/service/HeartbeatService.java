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
package com.luretechnologies.tms.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.App;
import com.luretechnologies.client.restlib.service.model.Heartbeat;
import com.luretechnologies.tms.backend.data.entity.AppClient;
import com.luretechnologies.tms.backend.data.entity.TerminalClient;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;

@Service
public class HeartbeatService {

	public List<TerminalClient> getTerminalList() throws ApiException {
		List<TerminalClient> terminalList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				List<com.luretechnologies.client.restlib.service.model.Terminal> terminalListServer = RestServiceUtil.getInstance().getClient().getTerminalApi().getTerminals(null, null);
				for(com.luretechnologies.client.restlib.service.model.Terminal terminalServer :terminalListServer) {
					TerminalClient terminal = new TerminalClient(terminalServer.getId(), terminalServer.getType().name(), terminalServer.getName(), terminalServer.getDescription(),
							terminalServer.getSerialNumber(), terminalServer.getAvailable(),terminalServer.getDebugActive(),
							terminalServer.getFrequency(), terminalServer.getLastContact().toString());
					terminalList.add(terminal);
				}
				return terminalList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return terminalList;
	}
	
	public List<TerminalClient> searchTerminal(String filter) throws ApiException {
		List<TerminalClient> terminalList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				List<com.luretechnologies.client.restlib.service.model.Terminal> terminalListServer = RestServiceUtil.getInstance().getClient().getTerminalApi().searchTerminals(filter, null, null);
				for(com.luretechnologies.client.restlib.service.model.Terminal terminalServer :terminalListServer) {
					TerminalClient terminal = new TerminalClient(terminalServer.getId(), terminalServer.getType().name(), terminalServer.getName(), terminalServer.getDescription(),
							terminalServer.getSerialNumber(), terminalServer.getAvailable(),terminalServer.getDebugActive(),
							terminalServer.getFrequency(), terminalServer.getLastContact().toString());
					terminalList.add(terminal);
				}
				return terminalList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return terminalList;
	}
	
	public List<Heartbeat> getHeartbeatHistory(Long entityId) throws ApiException {
		List<Heartbeat> heartbeatHistortListServer = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				heartbeatHistortListServer = RestServiceUtil.getInstance().getClient().getHeartbeatApi().search(entityId.toString(), null, null, null, null, null);
				return heartbeatHistortListServer;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return heartbeatHistortListServer;
	}
	
	public List<Heartbeat> searchHBHistoryByText(Long id, String filter, String startDate, String endDate) throws ApiException {
		List<Heartbeat> heartbeatHistortListServer = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				heartbeatHistortListServer = RestServiceUtil.getInstance().getClient().getHeartbeatApi().search(id.toString(), filter, startDate, endDate, null, null);
				return heartbeatHistortListServer;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return heartbeatHistortListServer;
	}
	
	public List<Heartbeat> searchHBHistoryByDate(Long entityId, String startdate, String endDate) throws ApiException {
		List<Heartbeat> heartbeatHistortListServer = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				heartbeatHistortListServer = RestServiceUtil.getInstance().getClient().getHeartbeatApi().search(entityId.toString(), null, startdate, endDate, null, null);
				return heartbeatHistortListServer;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return heartbeatHistortListServer;
	}
	
	public List<Heartbeat> deleteHeartbeat(Long id) throws ApiException {
		List<Heartbeat> heartbeatHistortListServer = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				//RestServiceUtil.getInstance().getClient().getHeartbeatApi().
				heartbeatHistortListServer = RestServiceUtil.getInstance().getClient().getHeartbeatApi().search(id.toString(), null, null, null, null, null);
				return heartbeatHistortListServer;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return heartbeatHistortListServer;
	}
	
	
	
}
