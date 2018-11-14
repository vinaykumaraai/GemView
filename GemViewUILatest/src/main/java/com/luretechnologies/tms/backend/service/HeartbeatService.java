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

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.Heartbeat;
import com.luretechnologies.tms.backend.data.entity.TerminalClient;
import com.luretechnologies.tms.backend.rest.util.RestClient;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.luretechnologies.tms.ui.components.ComponentUtil;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

/**
 * 
 * @author Vinay
 *
 */

@Service
public class HeartbeatService {
	private final static Logger heartbeatLogger = Logger.getLogger(HeartbeatService.class);

	public List<TerminalClient> getTerminalList(){
		List<TerminalClient> terminalList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				List<com.luretechnologies.client.restlib.service.model.Terminal> terminalListServer = RestServiceUtil.getInstance().getClient().getTerminalApi().getTerminals(null, null);
				for(com.luretechnologies.client.restlib.service.model.Terminal terminalServer :terminalListServer) {
					TerminalClient terminal = new TerminalClient(terminalServer.getId(), terminalServer.getType().name(), terminalServer.getName(), terminalServer.getDescription(),
							terminalServer.getSerialNumber(), terminalServer.getAvailable(),terminalServer.isHeartbeat(),
							terminalServer.getDebugActive(), terminalServer.getFrequency(), terminalServer.getLastContact().toString(), terminalServer.getEntityId());
					terminalList.add(terminal);
				}
				return terminalList;
			}
		} catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving Terminal List for Hearbeat Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			heartbeatLogger.error("API Error Occured while retrieving Terminal List for Hearbeat Screen",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			heartbeatLogger.error("Error Occured while retrieving Terminal List for Hearbeat Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving Terminal List for Hearbeat Screen",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		return terminalList;
	}
	
	public List<TerminalClient> searchTerminal(String filter) {
		List<TerminalClient> terminalList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				List<com.luretechnologies.client.restlib.service.model.Terminal> terminalListServer = RestServiceUtil.getInstance().getClient().getTerminalApi().searchTerminals(filter, null, null);
				for(com.luretechnologies.client.restlib.service.model.Terminal terminalServer :terminalListServer) {
					TerminalClient terminal = new TerminalClient(terminalServer.getId(), terminalServer.getType().name(), terminalServer.getName(), terminalServer.getDescription(),
							terminalServer.getSerialNumber(), terminalServer.getAvailable(),terminalServer.isHeartbeat(),
							terminalServer.getDebugActive(),
							terminalServer.getFrequency(), terminalServer.getLastContact()==null ? "" : terminalServer.getLastContact().toString(), terminalServer.getEntityId());
					terminalList.add(terminal);
				}
				return terminalList;
			}
		} catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" searching Terminal List for Hearbeat Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			heartbeatLogger.error("API Error Occured while searching Terminal List for Hearbeat Screen",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			heartbeatLogger.error("Error Occured while searching Terminal List for Hearbeat Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" searching Terminal List for Hearbeat Screen",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		return terminalList;
	}
	
	public List<Heartbeat> getHeartbeatHistory(String entityId) {
		List<Heartbeat> heartbeatHistortListServer = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				heartbeatHistortListServer = RestServiceUtil.getInstance().getClient().getHeartbeatApi().search(entityId, null, null, null, null, null);
				return heartbeatHistortListServer;
			}
		} catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving Heartbeat History",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			heartbeatLogger.error("API Error Occured while retrieving Heartbeat History",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			heartbeatLogger.error("Error Occured while retrieving Heartbeat History",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving Heartbeat History",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		return heartbeatHistortListServer;
	}
	
	public List<Heartbeat> searchHBHistoryByText(String entityId, String filter, String startDate, String endDate) {
		List<Heartbeat> heartbeatHistortListServer = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				heartbeatHistortListServer = RestServiceUtil.getInstance().getClient().getHeartbeatApi().search(entityId, filter, startDate, endDate, null, null);
				return heartbeatHistortListServer;
			}
		} catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" searching Heartbeat History",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			heartbeatLogger.error("API Error Occured while searching Heartbeat History",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			heartbeatLogger.error("Error Occured while searching Heartbeat History",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" searching Heartbeat History",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		return heartbeatHistortListServer;
	}
	
	public List<Heartbeat> searchHBHistoryByDate(String entityId, String filter, String startdate, String endDate) {
		List<Heartbeat> heartbeatHistortListServer = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				heartbeatHistortListServer = RestServiceUtil.getInstance().getClient().getHeartbeatApi().search(entityId, filter, startdate, endDate, null, null);
				return heartbeatHistortListServer;
			}
		} catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" searching Heartbeat History by dates",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			heartbeatLogger.error("API Error Occured while searching Heartbeat History by dates",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			heartbeatLogger.error("Error Occured while searching Heartbeat History by dates",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" searching Heartbeat History by dates",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		return heartbeatHistortListServer;
	}
	
	/* We can you the below Service for future use */
	
	/*public List<Heartbeat> deleteHeartbeat(Long id) {
		List<Heartbeat> heartbeatHistortListServer = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				//RestServiceUtil.getInstance().getClient().getHeartbeatApi().
				//heartbeatHistortListServer = RestServiceUtil.getInstance().getClient().getHeartbeatApi().d
				return heartbeatHistortListServer;
			}
		} catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			heartbeatLogger.error("API Error Occured while deleting Heartbeat History",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			heartbeatLogger.error("Error Occured while deleting Heartbeat History",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
		return heartbeatHistortListServer;
	}*/
	
	public void logHeartbeatScreenErrors(Exception e) {
		heartbeatLogger.error("Error Occured", e);
	}
	
}
