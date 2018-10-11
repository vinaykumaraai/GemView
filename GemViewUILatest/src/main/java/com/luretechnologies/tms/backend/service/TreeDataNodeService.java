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
import org.springframework.util.StringUtils;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.Device;
import com.luretechnologies.client.restlib.service.model.Entity;
import com.luretechnologies.client.restlib.service.model.Merchant;
import com.luretechnologies.client.restlib.service.model.Organization;
import com.luretechnologies.client.restlib.service.model.Region;
import com.luretechnologies.client.restlib.service.model.Terminal;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.rest.util.RestClient;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.luretechnologies.tms.ui.components.ComponentUtil;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.vaadin.data.TreeData;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

@SpringComponent
@Service
public class TreeDataNodeService {
	private final static Logger treeDataLogger = Logger.getLogger(TreeDataNodeService.class);
	
	public TreeData<TreeNode> getTreeData(){
		
		try {
			if(RestServiceUtil.getSESSION()!=null) {
				TreeData<TreeNode> treeData = new TreeData<>();
				Entity entity = RestServiceUtil.getInstance().getClient().getEntityApi().getEntityHierarchy();
				List<Entity> entityList = RestServiceUtil.getInstance().getClient().getEntityApi().getEntityChildren(entity.getId());
				TreeNode node = new TreeNode(entity.getName(), entity.getId(), entity.getType(), entity.getEntityId(), entity.getDescription()
						, true);
				List<TreeNode> treeNodeChildList = getChildNodes(entityList);		
				treeData.addItems(null, node);
				treeData.addItems(node, treeNodeChildList);
				treeDataRecursive(treeNodeChildList, treeData);
		
			return treeData;
			}
		}catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving Tree data or entities",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			treeDataLogger.error("API Error Occured while retrieving Tree data or entities",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			treeDataLogger.error("Error Occured has while retrieving Tree data or entities",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving Tree data or entities",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		return null;
	}
	
	private void treeDataRecursive(List<TreeNode> entityList, TreeData<TreeNode> treeData) {
		for(TreeNode entity : entityList) {
			List<Entity> entityListSub;
			try {
				entityListSub = RestServiceUtil.getInstance().getClient().getEntityApi().getEntityChildren(entity.getId());
				if(entityListSub!=null && !entityListSub.isEmpty()) {
					List<TreeNode> subChildList = getChildNodes(entityListSub);
					treeData.addItems(entity, subChildList);
					treeDataRecursive(subChildList,treeData);
				}
			} catch (ApiException ae) {
				if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
					Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}else {
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" doing tree data recursion",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
				treeDataLogger.error("API Error Occured while doing tree data recursion",ae);
				RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
			} catch (Exception e) {
				treeDataLogger.error("Error Occured has while doing tree data recursion",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" doing tree data recursion",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		}
	}
	
	private List<TreeNode> getChildNodes(List<Entity> entityList) throws ApiException{
		List<TreeNode> nodeChildList = new ArrayList<>();
		for(Entity entity : entityList) {
			TreeNode node = new TreeNode(entity.getName(), entity.getId(), entity.getType(), entity.getEntityId(), entity.getDescription()
					,true);
//			switch (entity.getType()) {
//			case TERMINAL:
//				node.setSerialNum(RestServiceUtil.getInstance().getClient().getTerminalApi().getTerminal(entity.getId().toString()).getSerialNumber());
//				break;
//			case DEVICE:
//				node.setSerialNum(RestServiceUtil.getInstance().getClient().getDeviceApi().getDevice(entity.getId().toString()).getSerialNumber());
//				break;
//			default:
//				break;
//			}
			nodeChildList.add(node);
		}
		return nodeChildList;
	}
	/**
	 * @param filterTextInLower
	 * @param node
	 * @return
	 */
	private boolean checkIfLabelStartsWith(String filterTextInLower, TreeNode node) {
		return StringUtils.startsWithIgnoreCase(node.getLabel().toLowerCase(), filterTextInLower);
	}
	
	
	public TreeData<TreeNode> getFilteredTreeByNodeName(TreeData<TreeNode> allTreeData, String filterTextInLower) {
		if (StringUtils.isEmpty(filterTextInLower)) {
			return allTreeData;
		} else {
			TreeData<TreeNode> treeData = new TreeData<>();
			TreeNode rootNode = allTreeData.getRootItems().get(0);
			if (checkIfLabelStartsWith(filterTextInLower, rootNode)) {
				treeData.addItem(null, rootNode);
			}

			for (TreeNode childNode : allTreeData.getChildren(rootNode)) {

				if (!treeData.contains(rootNode) && checkIfLabelStartsWith(filterTextInLower, childNode)) {
					treeData.addItem(null, childNode);
				}else if(treeData.contains(rootNode))
					treeData.addItem(rootNode, childNode);

				for (TreeNode subChildNode : allTreeData.getChildren(childNode)) {

					if (!treeData.contains(childNode) && checkIfLabelStartsWith(filterTextInLower, subChildNode)) {
						treeData.addItem(null, subChildNode);
					}else if(treeData.contains(childNode))
						treeData.addItem(childNode, subChildNode);
					
					for (TreeNode subSubChildNode : allTreeData.getChildren(subChildNode)) {

						if (!treeData.contains(subChildNode) && checkIfLabelStartsWith(filterTextInLower, subSubChildNode)) {
							treeData.addItem(null, subSubChildNode);
						}else if(treeData.contains(subChildNode))
							treeData.addItem(subChildNode,subSubChildNode);
						
						for (TreeNode subSubSubChildNode : allTreeData.getChildren(subSubChildNode)) {

							if (!treeData.contains(subSubChildNode) && checkIfLabelStartsWith(filterTextInLower, subSubSubChildNode)) {
								treeData.addItem(null, subSubSubChildNode);
							}else if(treeData.contains(subSubChildNode))
								treeData.addItem(subSubChildNode,subSubSubChildNode);
							
						}
					}

				}
			}
			return treeData;
		}
	}

	public void moveTreeNode(TreeNode entity, TreeNode parentEntity) {
		try {
			if(RestServiceUtil.getSESSION()!=null) {
				RestServiceUtil.getInstance().getClient().getEntityApi().moveEntity(entity.getId(), parentEntity.getId());
			}
		}catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" moving tree data or entities",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			treeDataLogger.error("API Error Occured while moving tree data or entities",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			treeDataLogger.error("Error Occured has while moving tree data or entities",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" moving tree data or entities",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		
	}
	
	public void pasteTreeNode(TreeNode entity, TreeNode parentEntity) {
		try {
			if(RestServiceUtil.getSESSION()!=null) {
				RestServiceUtil.getInstance().getClient().getEntityApi().copyEntity(entity.getEntityId(), parentEntity.getId());
				}
		}catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" pasting tree data or entities",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			treeDataLogger.error("API Error Occured while pasting tree data or entities",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			treeDataLogger.error("Error Occured has while pasting tree data or entities",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" pasting tree data or entities",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		
	}
	
	public TreeData<TreeNode> searchTreeData(String filter){
		try {
			if(RestServiceUtil.getSESSION()!=null) {
				List<TreeNode> treeNodeChildList = new ArrayList();
				TreeData<TreeNode> treeData = new TreeData<>();
					List<Organization> organizationList = RestServiceUtil.getInstance().getClient().getOrganizationApi().searchOrganizations(filter, null, null);
					List<Region> regionList = RestServiceUtil.getInstance().getClient().getRegionApi().searchRegions(filter, null, null);
					List<Merchant> merchantList = RestServiceUtil.getInstance().getClient().getMerchantApi().searchMerchants(filter, null, null);
					List<Terminal> terminalList = RestServiceUtil.getInstance().getClient().getTerminalApi().searchTerminals(filter, null, null);
					List<Device> deviceList = RestServiceUtil.getInstance().getClient().getDeviceApi().searchDevices(filter, null, null);
				
				for(Organization organization : organizationList) {
					TreeNode node = new TreeNode(organization.getName(), organization.getId(), organization.getType(), organization.getEntityId(), organization.getDescription()
							, true);
					treeNodeChildList.add(node);
				}
				
				for(Region region : regionList) {
					TreeNode node = new TreeNode(region.getName(), region.getId(), region.getType(), region.getEntityId(), region.getDescription()
							, true);
					treeNodeChildList.add(node);
				}
				
				for(Merchant merchant : merchantList) {
					TreeNode node = new TreeNode(merchant.getName(), merchant.getId(), merchant.getType(), merchant.getEntityId(), merchant.getDescription()
							, true);
					treeNodeChildList.add(node);
				}
				
				for(Terminal terminal : terminalList) {
					TreeNode node = new TreeNode(terminal.getName(), terminal.getId(), terminal.getType(), terminal.getEntityId(), terminal.getDescription()
							, true);
					treeNodeChildList.add(node);
				}
				
				for(Device device : deviceList) {
					TreeNode node = new TreeNode(device.getName(), device.getId(), device.getType(), device.getEntityId(), device.getDescription()
							, true);
					treeNodeChildList.add(node);
				}
				
				treeData.addItems(null, treeNodeChildList);
		
			return treeData;
			}
		}catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" searching tree data or entities",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			treeDataLogger.error("API Error Occured while searching tree data or entities",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			treeDataLogger.error("Error Occured has while searching tree data or entities",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" searching tree data or entities",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		return null;
	}
	public TreeNode findEntityData(TreeNode newNode){
		List<TreeNode> nodeList = new ArrayList();
		TreeNode foundNode = null;
		try {
			if(RestServiceUtil.getSESSION()!=null) {
				Entity entity = RestServiceUtil.getInstance().getClient().getEntityApi().getEntityHierarchy();
				List<Entity> entityList = RestServiceUtil.getInstance().getClient().getEntityApi().getEntityChildren(entity.getId());
				TreeNode node = new TreeNode(entity.getName(), entity.getId(), entity.getType(), entity.getEntityId(), entity.getDescription()
						, true);
				List<TreeNode> treeNodeChildList = getChildNodes(entityList);		
				
				nodeList.add(node);
				nodeList.addAll(treeNodeChildList);
				treeNodeRecursive(treeNodeChildList, nodeList);
				
				for(TreeNode dataNode : nodeList) {
					if(dataNode.getId().equals(newNode.getId()))
						return dataNode;
				}
			return foundNode;
			}
		}catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving Tree data or entities",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			treeDataLogger.error("API Error Occured while retrieving Tree data or entities",ae);
			RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
		} catch (Exception e) {
			treeDataLogger.error("Error Occured has while retrieving Tree data or entities",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving Tree data or entities",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		return null;
	}
	
	private void treeNodeRecursive(List<TreeNode> entityList, List<TreeNode> mainList) {
		for(TreeNode entity : entityList) {
			List<Entity> entityListSub;
			try {
				entityListSub = RestServiceUtil.getInstance().getClient().getEntityApi().getEntityChildren(entity.getId());
				if(entityListSub!=null && !entityListSub.isEmpty()) {
					List<TreeNode> subChildList = getChildNodes(entityListSub);
					mainList.addAll(subChildList);
					treeNodeRecursive(subChildList, mainList);
				}
			} catch (ApiException ae) {
				if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
					Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}else {
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" doing tree data recursion",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
				treeDataLogger.error("API Error Occured while doing tree data recursion",ae);
				RestClient.sendMessage(ae.getMessage(), ExceptionUtils.getStackTrace(ae));
			} catch (Exception e) {
				treeDataLogger.error("Error Occured has while doing tree data recursion",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" doing tree data recursion",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		}
	}
}
