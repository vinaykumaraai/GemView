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
import com.luretechnologies.client.restlib.service.model.AuditUserLog;
import com.luretechnologies.client.restlib.service.model.HeartbeatOdometer;
import com.luretechnologies.client.restlib.service.model.Terminal;
import com.luretechnologies.tms.backend.data.entity.DeviceOdometer;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.rest.util.RestClient;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.luretechnologies.tms.ui.components.ComponentUtil;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Tree.ItemClick;

@SpringComponent
@Service
public class OdometerService {
	
	private final static Logger odometerLogger = Logger.getLogger(OdometerService.class);
	
	@Autowired
	TreeDataNodeService treeDataNodeService;
	
	public TreeData<TreeNode> getDeviceOdometerTreeData(){
		try {
			TreeData<TreeNode> treeData = treeDataNodeService.getTreeData();
			return treeData;
		}catch (Exception e) {
			odometerLogger.error("Error Occured while retrieving Odometer Tree data",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  retrieving Odometer Tree data",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		return null;
	}
	
	public List<DeviceOdometer> getOdometerGridData(String id, String type){
		List<DeviceOdometer> odometerListNew = new ArrayList<>();
		try {
			if(RestServiceUtil.getSESSION()!=null) {
				if(type.equalsIgnoreCase("Terminal")) {
					List<HeartbeatOdometer> odometerList = RestServiceUtil.getInstance().getClient().getHeartbeatApi().searchOdometer(id,
							null, null, null, null, null);
					
					if(odometerList!=null && !odometerList.isEmpty()) {
						for(HeartbeatOdometer heartbeatOdometer: odometerList) {
							DeviceOdometer deviceOdodmeter = new DeviceOdometer(heartbeatOdometer.getId(), heartbeatOdometer.getLabel(), heartbeatOdometer.getDescription(), 
									heartbeatOdometer.getValue());
							odometerListNew.add(deviceOdodmeter);
						}
					}
					
					return odometerListNew;
				}
				
			}
		}catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  retrieving Odometer Grid Data",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			odometerLogger.error("API Error Occured while retrieving Odometer Grid Data",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			odometerLogger.error("Error Occured while retrieving Odometer Grid Data",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  retrieving Odometer Grid Data",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		return odometerListNew;
	}
	
	public void deleteGridData(Long id){
		try {
			if(RestServiceUtil.getSESSION()!=null) {
			
				RestServiceUtil.getInstance().getClient().getHeartbeatApi().deleteOdometer(id);
			}
		}catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  deleting Odometer Grid Data",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			odometerLogger.error("API Error Occured while deleting Odometer Grid Data",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			odometerLogger.error("Error Occured while deleting Odometer Grid Data",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  deleting Odometer Grid Data",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
	}
	
	public List<DeviceOdometer> searchOdometerGridData(String entityId, String filter,String startDate, String endDate){
		List<DeviceOdometer> odometerListSearch = new ArrayList<>();
		try {
			if(RestServiceUtil.getSESSION()!=null) {
			
				List<HeartbeatOdometer> searchList = RestServiceUtil.getInstance().getClient().getHeartbeatApi().searchOdometer(entityId, filter, 
						startDate, endDate, null, null);
				if(searchList!=null && !searchList.isEmpty()) {
					for(HeartbeatOdometer heartBeatOdometer: searchList) {
						DeviceOdometer deviceOdometer = new DeviceOdometer(heartBeatOdometer.getId(), heartBeatOdometer.getLabel(), heartBeatOdometer.getDescription(), 
							heartBeatOdometer.getValue());
						odometerListSearch.add(deviceOdometer);
				}
				}
				return odometerListSearch;
			}
		}catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  searching Odometer Grid Data",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			odometerLogger.error("API Error Occured while searching Odometer Grid Data",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			odometerLogger.error("Error Occured while searching Odometer Grid Data",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  searching Odometer Grid Data",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		return odometerListSearch;
	}
	
	public List<HeartbeatOdometer> searchByDates(String entityId, String filter, String startDate, String endDate){
		try {
			if(RestServiceUtil.getSESSION()!=null) {
			
				List<HeartbeatOdometer> odometerList = RestServiceUtil.getInstance().getClient().getHeartbeatApi().searchOdometer(entityId, filter, 
						startDate, endDate, null, null);
				return odometerList;
			}
		}catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  searching Odometer Grid Data by dates",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			odometerLogger.error("API Error Occured while searching Odometer Grid Data by dates",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			odometerLogger.error("Error Occured while searching Odometer Grid Data by dates",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  searching Odometer Grid Data by dates",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		return null;
	}
	public void logOdometerScreenErrors(Exception e) {
		odometerLogger.error("Error Occured", e);
	}
}
