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
import com.luretechnologies.client.restlib.service.model.AuditUserLog;
import com.luretechnologies.client.restlib.service.model.SystemParam;
import com.luretechnologies.tms.backend.data.entity.Systems;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.rest.util.RestClient;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.luretechnologies.tms.ui.components.ComponentUtil;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Tree.ItemClick;

@SpringComponent
@Service
public class SystemService {
	private final static Logger systemLogger = Logger.getLogger(SystemService.class);

	public Systems createSystemParam(String name, String description, String value, String systemParamType){
		try {
			if(RestServiceUtil.getSESSION()!=null) {
	
				SystemParam systemParam = RestServiceUtil.getInstance().getClient().getSystemParamsApi().
						createbyParams(name, description, value, systemParamType);
				Systems selectedSystem = new Systems(systemParam.getId(), systemParam.getName(), systemParam.getDescription(),
						 systemParam.getSystemParamType().getName(), systemParam.getValue());
				return selectedSystem;
			}
		}catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  creating System Params",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			systemLogger.error("API Error Occured while creating System Params",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			systemLogger.error("Error Occured while creating System Params ",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  creating System Params",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		return null;
	}
	
	public List<Systems> getAllSystemParam(){
		try {
			if(RestServiceUtil.getSESSION()!=null) {
				
				List<SystemParam> systemParamList = RestServiceUtil.getInstance().getClient().getSystemParamsApi().
						search(null, null, null);
				List<Systems> systemsList = new ArrayList<>();
				for(SystemParam systemParam : systemParamList) {
					Systems selectedSystem = new Systems(systemParam.getId(), systemParam.getName().toUpperCase(), systemParam.getDescription(),
							systemParam.getSystemParamType().getName(), systemParam.getValue());
					systemsList.add(selectedSystem);
				}
				return systemsList;
			}
		}catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  getting the System Params",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			systemLogger.error("API Error Occured while getting the System Params ",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			systemLogger.error("Error Occured while getting the System Params",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  getting the System Params",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		return null;
	}
	
	public Systems updateSystem(Systems system, String description, String paramName, String type, String value){
		try {
			if(RestServiceUtil.getSESSION()!=null) {
				
				SystemParam systemParam = RestServiceUtil.getInstance().getClient().getSystemParamsApi().getById(system.getId());
				systemParam.setName(paramName);
				systemParam.setDescription(description);
				systemParam.getSystemParamType().setName(type);
				systemParam.setValue(value);
				RestServiceUtil.getInstance().getClient().getSystemParamsApi().update(systemParam);
				
				system = new Systems(systemParam.getId(), systemParam.getName(), systemParam.getDescription(),
						systemParam.getSystemParamType().getName(), systemParam.getValue());
				return system;
			}
		}catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  updating the System Params",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			systemLogger.error("API Error Occured while updating the System Params",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			systemLogger.error("Error Occured while updating the System Params",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  updating the System Params",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		return system;
	}
	
	public void deleteSystem(Systems system) {
		try {
			if(RestServiceUtil.getSESSION()!=null) {
				
				RestServiceUtil.getInstance().getClient().getSystemParamsApi().delete(system.getId());
			}
		}catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  deleting the System",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			systemLogger.error("API Error Occured while deleting the System",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			systemLogger.error("Error Occured  while deleting the System",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  deleting the System",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
	}
	
	public void logSystemScreenErrors(Exception e) {
		systemLogger.error("Error Occured", e);
	}
}
