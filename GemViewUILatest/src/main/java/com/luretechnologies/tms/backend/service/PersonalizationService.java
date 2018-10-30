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
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.App;
import com.luretechnologies.client.restlib.service.model.AppFile;
import com.luretechnologies.client.restlib.service.model.AppParam;
import com.luretechnologies.client.restlib.service.model.AppProfile;
import com.luretechnologies.client.restlib.service.model.AppProfileParamValue;
import com.luretechnologies.client.restlib.service.model.Device;
import com.luretechnologies.client.restlib.service.model.Entity;
import com.luretechnologies.client.restlib.service.model.EntityAppProfile;
import com.luretechnologies.client.restlib.service.model.EntityAppProfileParam;
import com.luretechnologies.client.restlib.service.model.Merchant;
import com.luretechnologies.client.restlib.service.model.Organization;
import com.luretechnologies.client.restlib.service.model.Region;
import com.luretechnologies.client.restlib.service.model.Terminal;
import com.luretechnologies.tms.backend.data.entity.AppClient;
import com.luretechnologies.tms.backend.data.entity.AppDefaultParam;
import com.luretechnologies.tms.backend.data.entity.ApplicationFile;
import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.Profile;
import com.luretechnologies.tms.backend.data.entity.TerminalClient;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.rest.util.RestClient;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.luretechnologies.tms.ui.components.ComponentUtil;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

@Service
public class PersonalizationService extends CommonService {
	private final static Logger personlizationServiceLogger = Logger.getLogger(PersonalizationService.class);

	@Autowired
	TreeDataNodeService treeDataNodeService;
	Terminal terminalForPV;
	Device deviceForPersonalizationView;
	Region regionForPersonalizationView;
	Organization organizationForPersonalizationView;

	public TreeData<TreeNode> getTreeData() {
		try {
			if (RestServiceUtil.getSESSION() != null) {
				TreeData<TreeNode> treeData = treeDataNodeService.getTreeData();
				return treeData;
			}
		} catch (Exception e) {
			personlizationServiceLogger.error("Error Occured while retrieving Personlization Tree data",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving Personlization Tree data",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		return null;
	}

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
			personlizationServiceLogger.error("API Error has occured while creating an Entity in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				personlizationServiceLogger.error("Error occured while creating an Entity in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" creating an Entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}

	}

	
	public void updateEntity(TreeNode node) {
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
					if(node.getFrequency()!=null) {
						terminal.setFrequency(Long.parseLong(node.getFrequency()));
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
			personlizationServiceLogger.error("API Error has occured while updating an Entity in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				personlizationServiceLogger.error("Error occured while updating an Entity in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" updating an Entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
	}

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
			personlizationServiceLogger.error("API Error has occured while deleting an Entity in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				personlizationServiceLogger.error("Error occured while deleting an Entity in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting an Entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
	}
	
	public Devices getDevicesByEntityId(String entityId) {
		try {
			if (RestServiceUtil.getSESSION() != null) {
				getDeviceByEntityId(entityId);
				return new Devices(deviceForPersonalizationView.getId(), deviceForPersonalizationView.getName(),
						deviceForPersonalizationView.getDescription(), deviceForPersonalizationView.getSerialNumber(),
						deviceForPersonalizationView.getAvailable());
			}
		} catch (Exception e) {
				personlizationServiceLogger.error("Error occured while retrieving devices by Entity ID in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving devices by Entity ID in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return null;
	}

	public String getDeviceSerialNumberByEntityId(String entityId) {
		try {
			if (RestServiceUtil.getSESSION() != null) {
				getDeviceByEntityId(entityId);
				Devices device = new Devices(deviceForPersonalizationView.getId(), deviceForPersonalizationView.getName(),
						deviceForPersonalizationView.getDescription(), deviceForPersonalizationView.getSerialNumber(),
						deviceForPersonalizationView.getAvailable());
				if (device != null)
					return device.getSerialNumber();
				else
					return "";
			}
		} catch (Exception e) {
				personlizationServiceLogger.error("Error occured while retrieving device Serial Number by Entity ID in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving device Serial Number by Entity ID in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return null;

	}

	private Device getDeviceByEntityId(String entityId) {
		if (deviceForPersonalizationView == null)
			try {
				deviceForPersonalizationView = RestServiceUtil.getInstance().getClient().getDeviceApi().getDevice(entityId);
			} catch (ApiException e) {
				if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
					Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}else {
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving Device by Entity ID in the Personlization Screen",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
				personlizationServiceLogger.error("API Error has occured while retrieving Device by Entity ID in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			}
				catch (Exception e) {
					personlizationServiceLogger.error("Error occured while retrieving Device by Entity ID in the Personlization Screen",e);
					RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving Device by Entity ID in the Personlization Screen",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
		return deviceForPersonalizationView;
	}

	public ListDataProvider<Devices> getAllDevices() {
		List<Devices> allDevices = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				for (Device device : RestServiceUtil.getInstance().getClient().getDeviceApi().searchDevices("", null,
						null)) {
					Devices clientDevice = new Devices(device.getId(), device.getName(), device.getDescription(),
							device.getSerialNumber(), device.getAvailable());
					allDevices.add(clientDevice);
				}
			}

		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving All Devices in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			personlizationServiceLogger.error("API Error has occured while retrieving All Devices in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				personlizationServiceLogger.error("Error occured while retrieving All Devices in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving All Devices in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return new ListDataProvider<Devices>(allDevices);
	}
	
	public List<Profile> getProfileListWithOutEntity(Long appId, Long entityId){
		List<Profile> profileList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				for(AppProfile appProfile : RestServiceUtil.getInstance().getClient().getAppProfileApi().getAppProfileListWithoutEntity(appId, entityId)){
					profileList.add(new Profile(appProfile.getId(),appProfile.getName(), appProfile.getAppProfileParamValueCollection()));
				}
			}

		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving profile list without entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			personlizationServiceLogger.error("API Error has occured while retrieving profile list without entity in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				personlizationServiceLogger.error("Error occured while retrieving profile list without entity in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving profile list without entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}

		return profileList;
	}
	
	public List<Profile> getProfileListForEntity(Long appId,Long entityId){
		List<Profile> profileList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				for(AppProfile appProfile : RestServiceUtil.getInstance().getClient().getAppProfileApi().getAppProfileListByEntity(appId, entityId)) {
					profileList.add(new Profile(appProfile.getId(),appProfile.getName(), appProfile.getAppProfileParamValueCollection()));
				}
			}

		}catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving profile list with entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			personlizationServiceLogger.error("API Error has occured while retrieving profile list with entity in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				personlizationServiceLogger.error("Error occured while retrieving profile list with entity in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving profile list with entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}

		return profileList;
	}
	
	public List<AppDefaultParam> getFileListWithEntity(Long appProfileId, Long entityId){
		List<AppDefaultParam> fileList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				for(AppParam appParamServer : RestServiceUtil.getInstance().getClient().getAppProfileApi().getAppFileListByEntity(appProfileId, entityId))
				fileList.add(new AppDefaultParam(appParamServer.getId() ,appParamServer.getName(), appParamServer.getDescription(), appParamServer.getAppParamFormat().getName(),
						appParamServer.getDefaultValue()));
				}

		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving file list with entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			personlizationServiceLogger.error("API Error has occured while retrieving file list with entity in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				personlizationServiceLogger.error("Error occured while retrieving file list with entity in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving file list with entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}

		return fileList;
	}
	
	public List<AppDefaultParam> getFileListWithOutEntity(Long appProfileId , Long entityId){
		List<AppDefaultParam> fileList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				List<AppParam> appParamList = RestServiceUtil.getInstance().getClient().getAppProfileApi().getAppFileListWithoutAppProfile(appProfileId);
				for(AppParam appParamServer : RestServiceUtil.getInstance().getClient().getAppProfileApi().getAppFileListWithoutEntity(appProfileId, entityId))
				fileList.add(new AppDefaultParam(appParamServer.getId() ,appParamServer.getName(), appParamServer.getDescription(), appParamServer.getAppParamFormat().getName(),
						appParamServer.getDefaultValue()));
				}

		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving file list without entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			personlizationServiceLogger.error("API Error has occured while retrieving file list without entity in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				personlizationServiceLogger.error("Error occured while retrieving file list without entity in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving file list without entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}

		return fileList;
	}
	
	public String getTerminalSerialNumberByEntityId(String entityId) {
		try {
			if (RestServiceUtil.getSESSION() != null) {
				TerminalClient terminalClient = getTerminalByEntityId(entityId);
				if (terminalClient != null)
					return terminalClient.getSerialNumber();
				else
					return "";
			}
		} catch (Exception e) {
				personlizationServiceLogger.error("Error occured while retrieving Terminal Serial Number by Entity ID in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving Terminal Serial Number by Entity ID in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return null;
	}

	public Long getFrequencyByEntityId(String entityId) {
		try {
			if (RestServiceUtil.getSESSION() != null) {
				TerminalClient terminalClient = getTerminalByEntityId(entityId);
				if (terminalClient != null)
					return terminalClient.getFrequency();
				else
					return null;
			}
		} catch (Exception e) {
				personlizationServiceLogger.error("Error occured while retrieving frequency by Entity ID in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving frequency by Entity ID in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return null;
	}

	public Boolean getHeartbeatByEntityId(String entityId) {
		try {
			if (RestServiceUtil.getSESSION() != null) {
				TerminalClient terminalClient = getTerminalByEntityId(entityId);
				if (terminalClient != null)
					return terminalClient.isActivateHeartbeat();
				else
					return null;
			}
		} catch (Exception e) {
				personlizationServiceLogger.error("Error occured while retrieving Heartbeat by Entity ID in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving Heartbeat by Entity ID in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return null;
	}

	public List<AppClient> getAppListByLoggedUserEntity(Long id) {
	List<AppClient> allAppList = new ArrayList<>();
	try {
		if(RestServiceUtil.getSESSION()!=null) {
			for(App app : RestServiceUtil.getInstance().getClient().getAppApi().getAppsByEntityHierarchy(id)) {
				allAppList.add(new AppClient(app.getId(), app.getName(), app.getDescription(),
						app.getVersion(), app.getAvailable(), app.getActive(),getAppDefaultParamList(app.getAppParamCollection()),
						null, getOwner(app.getOwnerId()), getAppProfileList(app.getAppprofileCollection()),
						getApplicationFileList(app.getAppfileCollection())));
			}
		}
	}catch (ApiException e) {
		if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
			Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}else {
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving App list by logged in user in the Personlization Screen",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
		personlizationServiceLogger.error("API Error has occured while retrieving App list by logged in user in the Personlization Screen",e);
		RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
	}
		catch (Exception e) {
			personlizationServiceLogger.error("Error occured while retrieving App list by logged in user in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving App list by logged in user in the Personlization Screen",Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);
		}
	return allAppList;
}
	public void addProfileParam(Long appProfileId,Long entityId,Set<AppDefaultParam> paramSet) {
	try {
		if(RestServiceUtil.getSESSION()!=null) {
			for (AppDefaultParam param : paramSet) {
			RestServiceUtil.getInstance().getClient().getAppProfileApi().addEntityAppProfileParam(appProfileId, entityId, param.getId());
			}
		}
		}catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" adding the profile param in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			personlizationServiceLogger.error("API Error has occured while adding the profile param in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				personlizationServiceLogger.error("Error occured while adding the profile param in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" adding the profile param in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
}

	public void updateOverRideParam(AppClient app, AppDefaultParam param) {
		try {
			if(RestServiceUtil.getSESSION()!=null) {
				AppParam appParam = new AppParam();
				appParam.setAppId(app.getId());
				appParam.setName(param.getParameter());
				appParam.setDescription(param.getDescription());
				appParam.setDefaultValue(param.getValue());
				RestServiceUtil.getInstance().getClient().getAppApi().updateAppParam(app.getId(), appParam);
			}
		}catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" updating the override param in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			personlizationServiceLogger.error("API Error has occured while updating the override param in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				personlizationServiceLogger.error("Error occured while updating the override param in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" updating the override param in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
}

	private TerminalClient getTerminalByEntityId(String entityId) {
		try {
			terminalForPV = RestServiceUtil.getInstance().getClient().getTerminalApi().getTerminal(entityId);
			TerminalClient terminal = new TerminalClient(terminalForPV.getId(), terminalForPV.getType().name(),
					terminalForPV.getName(), terminalForPV.getDescription(), terminalForPV.getSerialNumber(),
					terminalForPV.getAvailable(), terminalForPV.isHeartbeat(), terminalForPV.getDebugActive(),
					terminalForPV.getFrequency(), terminalForPV.getLastContact().toString(),
					terminalForPV.getEntityId());
			return terminal;
		} catch (ApiException e) {
			if (e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED, Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			} else {
				Notification notification = Notification.show(
						NotificationUtil.SERVER_EXCEPTION + " updating the override param in the Personlization Screen",
						Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			personlizationServiceLogger.error(
					"API Error has occured while retrieving Terminal by Entity ID in the Personlization Screen", e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		} catch (Exception e) {
			personlizationServiceLogger
					.error("Error occured while retrieving Terminal by Entity ID in the Personlization Screen", e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			Notification notification = Notification.show(
					NotificationUtil.SERVER_EXCEPTION + " updating the override param in the Personlization Screen",
					Type.ERROR_MESSAGE);
			ComponentUtil.sessionExpired(notification);

		}

		return null;
	}
	private TreeNode getOwner(Long id) {
		TreeNode owner = null;
		try {
			if (RestServiceUtil.getSESSION() != null && id!=null) {
				Entity entity = RestServiceUtil.getInstance().getClient().getEntityApi().getEntityById(id);
				owner = new TreeNode(entity.getName(), entity.getId(), entity.getType(), entity.getEntityId(), entity.getDescription(), entity.getChildrenEntities(),
						entity.getSerialNumber(),true);
			}
		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving owner in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			personlizationServiceLogger.error("API Error has occured while retrieving owner in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				personlizationServiceLogger.error("Error occured while retrieving owner in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving owner in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return owner;
	}
	private List<AppDefaultParam> getAppDefaultParamList(List<AppParam> appParamList) {
		List<AppDefaultParam> appDefaultParamList = new ArrayList<>();
		if(appParamList!=null) {
			for (AppParam appParam : appParamList) {
				AppDefaultParam appDefaultParam = new AppDefaultParam(appParam.getId(), appParam.getName(),
					appParam.getDescription(), appParam.getAppParamFormat().getValue(), appParam.getDefaultValue());
				appDefaultParamList.add(appDefaultParam);
		}
			return appDefaultParamList;
		}
		return appDefaultParamList;
	}
	
	public List<AppDefaultParam> getAppDefaultParamListWithoutEntity(Long appProfileId, Long entityId) {
		List<AppDefaultParam> appDefaultParamList = new ArrayList<>();
		try {
		if (RestServiceUtil.getSESSION() != null) {
		List<AppParam> appParamService = RestServiceUtil.getInstance().getClient().getAppProfileApi().getAppParamListWithoutEntity(appProfileId, entityId);
			for (AppParam appParam : appParamService) {
				AppDefaultParam appDefaultParam = new AppDefaultParam(appParam.getId(), appParam.getName(),
					appParam.getDescription(), appParam.getAppParamFormat().getValue(), appParam.getDefaultValue());
				appDefaultParamList.add(appDefaultParam);
		}
		
		}
		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving default param list without entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			personlizationServiceLogger.error("API Error has occured while retrieving default param list without entity in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				personlizationServiceLogger.error("Error occured while retrieving default param list without entity in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving default param list without entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return appDefaultParamList;
	}
	
	public List<AppDefaultParam> getAppDefaultParamListWithEntity(Long appProfileId, Long entityId) {
		List<AppDefaultParam> appDefaultParamList = new ArrayList<>();
		try {
		if (RestServiceUtil.getSESSION() != null) {
		List<AppParam> appParamService = RestServiceUtil.getInstance().getClient().getAppProfileApi().getAppParamListByEntity(appProfileId, entityId);
			for (AppParam appParam : appParamService) {
				AppDefaultParam appDefaultParam = new AppDefaultParam(appParam.getId(), appParam.getName(),
					appParam.getDescription(), appParam.getAppParamFormat().getValue(), appParam.getDefaultValue());
				appDefaultParamList.add(appDefaultParam);
		}
		
		}
		}catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving App  default param list with entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			personlizationServiceLogger.error("API Error has occured while retrieving App  default param list with entity in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				personlizationServiceLogger.error("Error occured while retrieving default App param list with entity in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving App  default param list with entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return appDefaultParamList;
	}

	private List<Profile> getAppProfileList(List<AppProfile> appProfileList) {
		List<Profile> profileList = new ArrayList<>();
		if(appProfileList!=null) {
			for (AppProfile appProfile : appProfileList) {
				Profile profile = new Profile(appProfile.getId(), appProfile.getName(), appProfile.getAppProfileParamValueCollection());
				profileList.add(profile);
			}
			return profileList;
		}
		return profileList;
	}
	private List<AppDefaultParam> getApplicationFileList(List<AppFile> appFileList) {
		List<AppDefaultParam> fileList = new ArrayList<>();
		if(appFileList!=null) {
			for (AppFile appFile : appFileList) {
				AppDefaultParam appParamFileClient = new AppDefaultParam(appFile.getId(), appFile.getName(),
						appFile.getDescription(), appFile.getAppFileFormat().getName(), appFile.getDefaultValue());
				fileList.add(appParamFileClient);
			}
			return fileList;
		}
		
		return fileList;
	}
	
	public ListDataProvider<Profile> getProfileForEntityDataProvider(Long appId,Long entityId){
		List<Profile> profileList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				for(AppProfile appProfile : RestServiceUtil.getInstance().getClient().getAppProfileApi().getAppProfileListByEntity(appId, entityId)) {
					profileList.add(new Profile(appProfile.getId(),appProfile.getName(), appProfile.getAppProfileParamValueCollection()));
				}
			}

		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving profile for Entity Data Provider in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			personlizationServiceLogger.error("API Error has occured while retrieving profile for Entity Data Provider in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				personlizationServiceLogger.error("Error occured while retrieving profile for Entity Data Provider in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving profile for Entity Data Provider in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}

		return new ListDataProvider<>(profileList);
	}
	
	public void saveProfileForEntity(List<Profile> profileListClient,Long entityId, Long appId) {
		List<Profile> profileList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				List<AppProfile> profileListServer = RestServiceUtil.getInstance().getClient().getAppProfileApi().getAppProfileListByEntity(appId, entityId);
				for(AppProfile appProfile : profileListServer) {
					profileList.add(new Profile(appProfile.getId(),appProfile.getName(), appProfile.getAppProfileParamValueCollection()));
				}
				for(Profile profile : profileListClient) {
					if(profileList.contains(profile)) {
						
					}else {
					
					RestServiceUtil.getInstance().getClient().getAppProfileApi().addEntityAppProfile(profile.getId(), entityId);
					}
				}
			}

		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" saving profile for entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			personlizationServiceLogger.error("API Error has occured while saving profile for entity  in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				personlizationServiceLogger.error("Error occured while saving profile for entity in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" saving profile for entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}

	}
	
	public void saveFileForEntity(Long appProfileId, List<AppDefaultParam> fileList,Long entityId) {
		try {
			if (RestServiceUtil.getSESSION() != null) {
				for(AppDefaultParam appDefaultParamFile : fileList) {
					RestServiceUtil.getInstance().getClient().getAppProfileApi().addEntityAppProfileParam(appProfileId, entityId, appDefaultParamFile.getId());
				}
			}

		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" saving file for entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			personlizationServiceLogger.error("API Error has occured while saving file for entity in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				personlizationServiceLogger.error("Error occured while saving file for entity in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" saving file for entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}

	}
	
	public void updateParamOfEntity(Long profileId, Long entityId, AppDefaultParam param, Long appId ) {
		EntityAppProfileParam entityAppProfileParam = new EntityAppProfileParam();
		AppProfileParamValue appProfileParamValue = new AppProfileParamValue();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				 AppProfile appProfile = RestServiceUtil.getInstance().getClient().getAppProfileApi().getAppProfile(profileId);
				 for(AppProfileParamValue appProfileParamValueList:appProfile.getAppProfileParamValueCollection()) {
					 if(appProfileParamValueList.getAppParamId().equals(param.getId()))
						 appProfileParamValue = appProfileParamValueList;
				 }
				 
				 for(EntityAppProfile entityAppProfileList:appProfile.getEntityAppProfileCollection()) {
					if(entityAppProfileList.getEntity().getId().equals(entityId)) {
						if(entityAppProfileList.getEntityappprofileparamCollection()!=null) {
							for(EntityAppProfileParam entityAppProfileParamList :entityAppProfileList.getEntityappprofileparamCollection()) {
								if(entityAppProfileParamList.getAppProfileParamValueId().equals(appProfileParamValue.getId())) {
									entityAppProfileParam = entityAppProfileParamList;
									entityAppProfileParam.setValue(param.getValue());
									RestServiceUtil.getInstance().getClient().getAppProfileApi().updateEntityAppProfileParam(profileId, entityId, entityAppProfileParam);
									break;
								}
							}
						 }
					}
				 }
			}
		}catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" updating param of entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			personlizationServiceLogger.error("API Error has occured while updating param of entity in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				personlizationServiceLogger.error("Error occured while updating param of entity in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" updating param of entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
	}
	
	public void deleteProfileForEntity(List<Profile> profileList,Long entityId) {
		try {
			if (RestServiceUtil.getSESSION() != null) {
				for(Profile profile : profileList) {
					RestServiceUtil.getInstance().getClient().getAppProfileApi().deleteEntityAppProfile(profile.getId(), entityId);
				}
			}

		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting profile for entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			personlizationServiceLogger.error("API Error has occured while deleting profile for entity in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				personlizationServiceLogger.error("Error occured while deleting profile for entity in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting profile for entity in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}

	}
	
	public void deleteFileForEntity(Long profileId, List<AppDefaultParam> fileList,Long entityId) {
		try {
			if (RestServiceUtil.getSESSION() != null) {
				for(AppDefaultParam file : fileList) {
					RestServiceUtil.getInstance().getClient().getAppProfileApi().deleteEntityAppProfileFile(profileId, entityId, file.getId());
				}
			}

		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting file for entityy in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			personlizationServiceLogger.error("API Error has occured while deleting file for entity in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				personlizationServiceLogger.error("Error occured while deleting file for entity in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting file for entityy in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}

	}
	
	public void deleteEntityAppProfileParam(Long profileId, List<AppDefaultParam> paramList,Long entityId) {
		try {
			if (RestServiceUtil.getSESSION() != null) {
				for(AppDefaultParam param : paramList) {
					RestServiceUtil.getInstance().getClient().getAppProfileApi().deleteEntityAppProfileParam(profileId, entityId, param.getId());
				}
			}

		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting App Profile Param in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			personlizationServiceLogger.error("API Error has occured while deleting App Profile Param in the Personlization Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				personlizationServiceLogger.error("Error occured while deleting App Profile Param in the Personlization Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting App Profile Param in the Personlization Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}

	}

	public void logPersonlizationStoreScreenErrors(Exception e) {
		personlizationServiceLogger.error("Error Occured", e);
	}
}