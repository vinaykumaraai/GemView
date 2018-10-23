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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.DashboardCurrentDownLoads;
import com.luretechnologies.client.restlib.service.model.DashboardWidget;
import com.luretechnologies.tms.backend.data.entity.ConnectionStats;
import com.luretechnologies.tms.backend.data.entity.Downloads;
import com.luretechnologies.tms.backend.rest.util.RestClient;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.luretechnologies.tms.ui.components.ComponentUtil;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

@Service
public class DashboardService {

	private final static Logger dashboardServiceLogger = Logger.getLogger(DashboardService.class);
	public ConnectionStats getLabelData(){
		final String CURRENT_CONNECTIONS = "currentConnectionsData";
		final String SUCESSFUL_DOWNLOADS = "successfulDownloads";
		final String REQUEST_PER_SECOND = "requestPerSecond";
		final String DOWNLOAD_FAILURE = "downloadFailure";
		ConnectionStats labelData = new ConnectionStats();
		List<String> labelDataList = new ArrayList<>();
		Integer currentConnections = null, successfulDownloads = null, requestPerSecond = null, downloadFailure = null;
		labelDataList.add(CURRENT_CONNECTIONS);labelDataList.add(SUCESSFUL_DOWNLOADS);labelDataList.add(REQUEST_PER_SECOND);
		labelDataList.add(DOWNLOAD_FAILURE);
		try {
			if (RestServiceUtil.getSESSION() != null) {
				 DashboardWidget dashboardWidget = RestServiceUtil.getInstance().getClient().getDashboardApi().getDashboardWidget();
				for(String string: labelDataList) {
					switch(string)	{
					case CURRENT_CONNECTIONS:
						if(dashboardWidget.getConnectionsNumber()!=null) {
							currentConnections = dashboardWidget.getConnectionsNumber();
						}else {
							currentConnections=0;
						}
						labelData.setCurrentConnections(currentConnections);
						break;
					case SUCESSFUL_DOWNLOADS:
						if(dashboardWidget.getDownloadSuccess()!=null) {
							successfulDownloads = dashboardWidget.getDownloadSuccess();
						}else {
							successfulDownloads=0;
						}
						labelData.setSuccessfulDownloads(successfulDownloads);
						break;
					case REQUEST_PER_SECOND:
						if(dashboardWidget.getHeartbeatPer()!=null) {
							requestPerSecond = dashboardWidget.getHeartbeatPer();
						}else {
							requestPerSecond=0;
						}
						labelData.setRequestPerSeconds(requestPerSecond);
						break;
					case DOWNLOAD_FAILURE:
						if(dashboardWidget.getDownloadFail()!=null) {
							downloadFailure = dashboardWidget.getDownloadFail();
						}else {
							downloadFailure=0;
						}
						labelData.setDownloadFaliures(downloadFailure);
						break;
					}
				}
			}
		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the Dashboard Label Data",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			dashboardServiceLogger.error("API Error has occured while retrieving the Label Data in the Dashboard Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				dashboardServiceLogger.error("Error occured while retrieving the Label Data in the Dashboard Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the Dashboard Label Data",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return labelData;
		
	}
	
	public List<Downloads> getDownloadsData() {
		List<DashboardCurrentDownLoads> currentDownloadsDataServer =null;
		List<Downloads> currentDownloadsData = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				 DashboardWidget dashboardWidget = RestServiceUtil.getInstance().getClient().getDashboardApi().getDashboardWidget();
				 currentDownloadsDataServer = dashboardWidget.getCurrentDownLoadsDisplays();
				 dashboardWidget.getHeartbeatPerHour();
				 for(DashboardCurrentDownLoads dashboardCurrentDownLoads : currentDownloadsDataServer) {
					 Downloads download = new Downloads(dashboardCurrentDownLoads.getSerialTerminal(),
							 				dashboardCurrentDownLoads.getOrganization(), dashboardCurrentDownLoads.getOperatingSystem(),
							 				dashboardCurrentDownLoads.getIp(), dashboardCurrentDownLoads.getDevice(), 
							 				dashboardCurrentDownLoads.getCompletion().toString()+"%");
					 currentDownloadsData.add(download);
				 }
				 return currentDownloadsData;
			}
		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the Dashboard download Data",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			dashboardServiceLogger.error("API Error has occured while retrieving the Downloads in the Dashboard Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				dashboardServiceLogger.error("Error occured while retrieving the Downloads in the Dashboard Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the Dashboard download Data",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return currentDownloadsData;
		
	}
	
	public List<Number> getHeartBeatDataPerDay() {
		List<Long> heartBeatPerDayLong = new ArrayList<>();
		List<Number> heartBeatPerDay = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				 DashboardWidget dashboardWidget = RestServiceUtil.getInstance().getClient().getDashboardApi().getDashboardWidget();
				 heartBeatPerDayLong = dashboardWidget.getHeartbeatPerHour();
				 for(Long value: heartBeatPerDayLong) {
					 Number number = value.intValue();
					 heartBeatPerDay.add(number);
				 }
				 return heartBeatPerDay;
			}
		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the Dashboard Heartbeat Data per day",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			dashboardServiceLogger.error("API Error has occured while retrieving the Hearbeat Data Per day in the Dashboard Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				dashboardServiceLogger.error("Error occured while retrieving the Hearbeat Data Per day in the Dashboard Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the Dashboard Heartbeat Data per day",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return heartBeatPerDay;
		
	}
	
	public List<Number> getHeartBeatDataPerWeek() {
		List<Long> heartBeatPerWeekLong = new ArrayList<>();
		List<Number> heartBeatPerWeek = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				 DashboardWidget dashboardWidget = RestServiceUtil.getInstance().getClient().getDashboardApi().getDashboardWidget();
				 heartBeatPerWeekLong = dashboardWidget.getHeartbeatPerWeekDays();
				 for(Long value: heartBeatPerWeekLong) {
					 Number number = value.intValue();
					 heartBeatPerWeek.add(number);
				 }
				 return heartBeatPerWeek;
			}
		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the Dashboard Heartbeat Data per week",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			dashboardServiceLogger.error("API Error has occured while retrieving the Hearbeat data Per week in the Dashboard Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				dashboardServiceLogger.error("Error occured while retrieving the Hearbeat data Per week in the Dashboard Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the Dashboard Heartbeat Data per week",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return heartBeatPerWeek;
		
	}
	
	public List<Number> getDownloadDataPerDay() {
		List<Long> downloadPerDayLong = new ArrayList<>();
		List<Number> downloadPerDay = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				 DashboardWidget dashboardWidget = RestServiceUtil.getInstance().getClient().getDashboardApi().getDashboardWidget();
				 downloadPerDayLong = dashboardWidget.getDownloadPerHour();
				 for(Long value: downloadPerDayLong) {
					 Number number = value.intValue();
					 downloadPerDay.add(number);
				 }
				 return downloadPerDay;
			}
		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the Dashboard download Data per day",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			dashboardServiceLogger.error("API Error has occured while retrieving the download data Per day in the Dashboard Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				dashboardServiceLogger.error("Error occured while retrieving the download data Per day in the Dashboard Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the Dashboard download Data per day",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return downloadPerDay;
	}
	
	public List<Number> getDownloadDataPerWeek() {
		List<Long> downloadDataPerWeekLong = new ArrayList<>();
		List<Number> downloadDataPerWeek = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				 DashboardWidget dashboardWidget = RestServiceUtil.getInstance().getClient().getDashboardApi().getDashboardWidget();
				 downloadDataPerWeekLong = dashboardWidget.getHeartbeatPerWeekDays();
				 for(Long value: downloadDataPerWeekLong) {
					 Number number = value.intValue();
					 downloadDataPerWeek.add(number);
				 }
				 return downloadDataPerWeek;
			}
		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the Dashboard download Data per week",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			dashboardServiceLogger.error("API Error has occured while retrieving the download data Per week in the Dashboard Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				dashboardServiceLogger.error("Error occured while retrieving the download data Per week in the Dashboard Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the Dashboard download Data per week",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return downloadDataPerWeek;
		
	}
}
