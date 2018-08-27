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
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.AuditUserLog;
import com.luretechnologies.client.restlib.service.model.Entity;
import com.luretechnologies.client.restlib.service.model.User;
import com.luretechnologies.tms.backend.data.entity.Audit;
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
public class AuditService {
	private final static Logger auditLogger = Logger.getLogger(AuditService.class);
	
	@Autowired
	TreeDataNodeService treeDataNodeService;
	
	public TreeData<TreeNode> auditTreeData(){
		try {
			if(RestServiceUtil.getSESSION()!=null) {
			TreeData<TreeNode> treeData = treeDataNodeService.getTreeData();
			return treeData;
			}
		}catch (Exception e) {
			auditLogger.error("Error Occured while retrieving Audit Tree data",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  retrieving Audit Tree data",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		return null;
	}

	
	public List<Audit> auditGridData(String id){
		List<Audit> auditListNew = new ArrayList<>();
		try {
			if(RestServiceUtil.getSESSION()!=null) {
				List<AuditUserLog> auditList = RestServiceUtil.getInstance().getClient().getAuditUserLogApi().searchLogs(null, id, 
						null, null, null, null, null);
				if(auditList!=null && !auditList.isEmpty()) {
					for(AuditUserLog auditUserLog: auditList) {
						Audit audit = new Audit(auditUserLog.getId(), auditUserLog.getAuditUserLogType().getName(), auditUserLog.getDescription(), auditUserLog.getDateAt().toString());
						auditListNew.add(audit);
				}
				}
				return auditListNew;
			}
		}catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  retrieving Audit Grid Data",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			auditLogger.error("API Error Occured while retrieving Audit Grid Data",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			auditLogger.error("Error Occured while retrieving Audit Grid Data",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  retrieving Audit Grid Data",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		return auditListNew;
	}
	
	public void deleteGridData(Long id){
		try {
			if(RestServiceUtil.getSESSION()!=null) {
			
				RestServiceUtil.getInstance().getClient().getAuditUserLogApi().delete(id);
			}
		}catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  deleting Audit Grid Data",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			auditLogger.error("API Error Occured while deleting Audit Grid Data",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			auditLogger.error("Error Occured while deleting Audit Grid Data",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  deleting Audit Grid Data",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
	}
	
	public List<AuditUserLog> searchGridData(String filter, String startDate, String endDate){
		try {
			if(RestServiceUtil.getSESSION()!=null) {
			
				List<AuditUserLog> auditList = RestServiceUtil.getInstance().getClient().getAuditUserLogApi().searchLogs(null, null, filter, 
						startDate, endDate, null, null);
				return auditList;
			}
		}catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  searching Audit Grid Data",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			auditLogger.error("API Error Occured while searching Audit Grid Data",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			auditLogger.error("Error Occured while searching Audit Grid Data",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  searching Audit Grid Data",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		return null;
	}
	
	public List<AuditUserLog> searchByDates(String filter, String startDate, String endDate){
		try {
			if(RestServiceUtil.getSESSION()!=null) {
			
				List<AuditUserLog> auditList = RestServiceUtil.getInstance().getClient().getAuditUserLogApi().searchLogs(null, null, filter, 
						startDate, endDate, null, null);
				return auditList;
			}
		}catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  searching Audit Grid Data by dates",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			auditLogger.error("API Error Occured while searching Audit Grid Data by dates",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			auditLogger.error("Error Occured while searching Audit Grid Data by dates",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+"  searching Audit Grid Data by dates",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		return null;
	}
	
	public void logAuditScreenErrors(Exception e) {
		auditLogger.error("Error Occured", e);
	}
}
