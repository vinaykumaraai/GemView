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
import com.luretechnologies.client.restlib.service.model.Device;
import com.luretechnologies.client.restlib.service.model.Merchant;
import com.luretechnologies.client.restlib.service.model.Organization;
import com.luretechnologies.client.restlib.service.model.Region;
import com.luretechnologies.client.restlib.service.model.Terminal;
import com.luretechnologies.tms.backend.data.entity.Audit;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.rest.util.RestClient;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.luretechnologies.tms.ui.components.ComponentUtil;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.vaadin.data.TreeData;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

/**
 * 
 * @author Vinay
 *
 */

@SpringComponent
@Service
public class AuditService extends CommonService {
	private final static Logger auditLogger = Logger.getLogger(AuditService.class);
	
	@Autowired
	TreeDataNodeService treeDataNodeService;
	
	@Autowired
	UserService userService;
	
	@Override
	public TreeData<TreeNode> getTreeData(){
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
	
	public List<AuditUserLog> searchGridData(String filter, String entityId, String startDate, String endDate){
		try {
			if(RestServiceUtil.getSESSION()!=null) {
			
				List<AuditUserLog> auditList = RestServiceUtil.getInstance().getClient().getAuditUserLogApi().searchLogs(null, entityId, filter, 
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
	
	@Override
	public void createEntity(TreeNode parentNode, TreeNode treeNewNode) {
		try {
			if (RestServiceUtil.getSESSION() != null) {
				switch (parentNode.getType()) {

				case ENTERPRISE:
					if(treeNewNode.getType().toString().equals("ORGANIZATION")) {
						Organization organization = new Organization();
						organization.setAvailable(treeNewNode.isActive());
						organization.setDescription(treeNewNode.getDescription());
						organization.setName(treeNewNode.getLabel());
						organization.setParentId(parentNode.getId());
						organization.setType(treeNewNode.getType());
						organization.setEntityId(treeNewNode.getEntityId());
						RestServiceUtil.getInstance().getClient().getOrganizationApi().createOrganization(organization);
					}
					break;
				case ORGANIZATION:
					if(treeNewNode.getType().toString().equals("REGION")) {
					Region region = new Region();
					region.setAvailable(treeNewNode.isActive());
					region.setDescription(treeNewNode.getDescription());
					region.setName(treeNewNode.getLabel());
					region.setParentId(parentNode.getId());
					region.setType(treeNewNode.getType());
					region.setEntityId(treeNewNode.getEntityId());
					RestServiceUtil.getInstance().getClient().getRegionApi().createRegion(region);
					} else if(treeNewNode.getType().toString().equals("ORGANIZATION")) {
						Organization organizationSelf = new Organization();
						organizationSelf.setAvailable(treeNewNode.isActive());
						organizationSelf.setDescription(treeNewNode.getDescription());
						organizationSelf.setName(treeNewNode.getLabel());
						organizationSelf.setParentId(parentNode.getId());
						organizationSelf.setType(treeNewNode.getType());
						organizationSelf.setEntityId(treeNewNode.getEntityId());
						RestServiceUtil.getInstance().getClient().getOrganizationApi().createOrganization(organizationSelf);
					}
					break;
				case REGION:
					 if(treeNewNode.getType().toString().equals("MERCHANT")) {
						Merchant merchant = new Merchant();
						merchant.setAvailable(treeNewNode.isActive());
						merchant.setDescription(treeNewNode.getDescription());
						merchant.setName(treeNewNode.getLabel());
						merchant.setParentId(parentNode.getId());
						merchant.setType(treeNewNode.getType());
						merchant.setEntityId(treeNewNode.getEntityId());
						RestServiceUtil.getInstance().getClient().getMerchantApi().createMerchant(merchant);
					}
					break;
				case MERCHANT:
					Terminal terminal = new Terminal();
					terminal.setAvailable(treeNewNode.isActive());
					terminal.setDescription(treeNewNode.getDescription());
					terminal.setName(treeNewNode.getLabel());
					terminal.setParentId(parentNode.getId());
					terminal.setType(treeNewNode.getType());
					terminal.setEntityId(treeNewNode.getEntityId());
					terminal.setSerialNumber(treeNewNode.getSerialNum());
					RestServiceUtil.getInstance().getClient().getTerminalApi().createTerminal(terminal);
					break;

				case TERMINAL:
					Device device = new Device();
					device.setAvailable(treeNewNode.isActive());
					device.setDescription(treeNewNode.getDescription());
					device.setName(treeNewNode.getLabel());
					device.setParentId(parentNode.getId());
					device.setType(treeNewNode.getType());
					device.setEntityId(treeNewNode.getEntityId());
					device.setSerialNumber(treeNewNode.getSerialNum());
					RestServiceUtil.getInstance().getClient().getDeviceApi().createDevice(device);
					break;
				default:
					break;
				}
			}

		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" creating an Entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			auditLogger.error("API Error has occured while creating an Entity in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				auditLogger.error("Error occured while creating an Entity in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" creating an Entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
	}
	@Override
	public void updateEntity(TreeNode node, Long frequency) {
		try {
			if(RestServiceUtil.getSESSION() != null) {
				switch (node.getType()) {
				case ORGANIZATION:
					Organization organization = new Organization();
					organization.setAvailable(node.isActive());
					organization.setDescription(node.getDescription());
					organization.setName(node.getLabel());
					organization.setType(node.getType());
					organization.setEntityId(node.getEntityId());
					RestServiceUtil.getInstance().getClient().getOrganizationApi().updateOrganization(organization.getEntityId(), organization);
					break;
				case REGION:
					Region region = new Region();
					region.setAvailable(node.isActive());
					region.setDescription(node.getDescription());
					region.setName(node.getLabel());
					region.setType(node.getType());
					region.setEntityId(node.getEntityId());
					RestServiceUtil.getInstance().getClient().getRegionApi().updateRegion(region.getEntityId(), region);
					break;
				case MERCHANT:
					Merchant merchant = RestServiceUtil.getInstance().getClient().getMerchantApi().getMerchant(node.getEntityId());
					merchant.setAvailable(node.isActive());
					merchant.setDescription(node.getDescription());
					merchant.setName(node.getLabel());
					RestServiceUtil.getInstance().getClient().getMerchantApi().updateMerchant(merchant.getEntityId(), merchant);
					break;
				case TERMINAL:
					Terminal terminal = RestServiceUtil.getInstance().getClient().getTerminalApi().getTerminal(node.getEntityId());
					terminal.setAvailable(node.isActive());
					terminal.setDescription(node.getDescription());
					terminal.setName(node.getLabel());
					if(frequency!=null) {
						terminal.setFrequency(frequency);
					}
					terminal.setHeartbeat(node.isHeartBeat());
					terminal.setSerialNumber(node.getSerialNum());
					
					RestServiceUtil.getInstance().getClient().getTerminalApi().updateTerminal(terminal.getEntityId(), terminal);
					break;
					
				case DEVICE:
					Device device = new Device();
					device.setAvailable(node.isActive());
					device.setDescription(node.getDescription());
					device.setName(node.getLabel());
					device.setType(node.getType());
					device.setEntityId(node.getEntityId());
					device.setSerialNumber(node.getSerialNum());
					RestServiceUtil.getInstance().getClient().getDeviceApi().updateDevice(device.getEntityId(), device);
					break;
				default:
					break;
				}
			}
			
		}catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" updating an Entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			auditLogger.error("API Error has occured while updating an Entity in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				auditLogger.error("Error occured while updating an Entity in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" updating an Entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		
	}
	@Override
	public void deleteEntity(TreeNode node) {
		try {
			if(RestServiceUtil.getSESSION()!=null) {
				switch (node.getType()) {
				case ORGANIZATION:
					RestServiceUtil.getInstance().getClient().getOrganizationApi().deleteOrganization(node.getEntityId());
					break;
				case REGION:
					RestServiceUtil.getInstance().getClient().getRegionApi().deleteRegion(node.getEntityId());
					break;
				case MERCHANT:
					RestServiceUtil.getInstance().getClient().getMerchantApi().deleteMerchant(node.getEntityId());
					break;
				case TERMINAL:
					RestServiceUtil.getInstance().getClient().getTerminalApi().deleteTerminal(node.getEntityId());
					break;
				case DEVICE:
					RestServiceUtil.getInstance().getClient().getDeviceApi().deleteDevice(node.getEntityId());
					break;
					default:
					break;
			}
			}
			
		}catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting an Entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			auditLogger.error("API Error has occured while deleting an Entity in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				auditLogger.error("Error occured while deleting an Entity in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting an Entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		
	}
}
