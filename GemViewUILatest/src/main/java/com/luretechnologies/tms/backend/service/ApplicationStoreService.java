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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.Action;
import com.luretechnologies.client.restlib.service.model.App;
import com.luretechnologies.client.restlib.service.model.AppFile;
import com.luretechnologies.client.restlib.service.model.AppParam;
import com.luretechnologies.client.restlib.service.model.AppParamFormat;
import com.luretechnologies.client.restlib.service.model.AppProfile;
import com.luretechnologies.client.restlib.service.model.AppProfileParamValue;
import com.luretechnologies.client.restlib.service.model.Device;
import com.luretechnologies.client.restlib.service.model.Entity;
import com.luretechnologies.client.restlib.service.model.EntityLevel;
import com.luretechnologies.tms.app.security.BackendAuthenticationProvider;
import com.luretechnologies.tms.backend.data.entity.AppClient;
import com.luretechnologies.tms.backend.data.entity.AppDefaultParam;
import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.Profile;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.rest.util.RestClient;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.luretechnologies.tms.ui.components.ComponentUtil;
import com.luretechnologies.tms.ui.components.NotificationUtil;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

/**
 * 
 * @author Vinay
 *
 */


@Service
public class ApplicationStoreService {
	private final static Logger aplicationStoreServiceLogger = Logger.getLogger(ApplicationStoreService.class);
	BackendAuthenticationProvider provider = new BackendAuthenticationProvider();

	public List<AppClient> getAppListForGrid() {
		List<AppClient> appClientList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				List<App> appsList = RestServiceUtil.getInstance().getClient().getAppApi().getAppListByEntityChildren();
				for (App app : appsList) {
					AppClient appClient = new AppClient(app.getId(), app.getName(), app.getDescription(),
							app.getVersion(), app.getAvailable(), app.getActive(),getAppDefaultParamList(app.getAppParamCollection()),
							null, app.getOwnerId(), getAppProfileList(app.getAppprofileCollection()),
							getApplicationFileList(app.getAppfileCollection()));
					appClientList.add(appClient);
				}
				return appClientList;
			}
		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" getting the App List",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			aplicationStoreServiceLogger.error("API Error has occured while retrieving the App List",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				aplicationStoreServiceLogger.error("Error occured while retrieving the App List",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" getting the App List",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return appClientList;
	}
	
	public AppClient getAppById(Long id){
		AppClient appClient = new AppClient();
		try {
		if (RestServiceUtil.getSESSION() != null) {
			App appService = RestServiceUtil.getInstance().getClient().getAppApi().getApp(id);
			appClient = new AppClient(appService.getId(), appService.getName(), appService.getDescription(),
					appService.getVersion(), appService.getAvailable(), appService.getActive(),getAppDefaultParamList(appService.getAppParamCollection()),
					null, appService.getOwnerId(), getAppProfileList(appService.getAppprofileCollection()),
					getApplicationFileList(appService.getAppfileCollection()));
		}
		}catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the App by APP ID",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			aplicationStoreServiceLogger.error("API Error has occured while rretrieving the App by APP ID",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				aplicationStoreServiceLogger.error("Error occured while retrieving the App by APP ID",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the App by APP ID",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return appClient;
	}	
	
	public List<AppClient> getAppListForEntityId(Long id) {
		List<AppClient> appClientList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				List<App> appsList = RestServiceUtil.getInstance().getClient().getAppApi().getAppsByEntityHierarchy(id);
				for (App app : appsList) {
					AppClient appClient = new AppClient(app.getId(), app.getName(), app.getDescription(),
							app.getVersion(), app.getAvailable(), app.getActive(),getAppDefaultParamList(app.getAppParamCollection()),
							null, app.getOwnerId(), getAppProfileList(app.getAppprofileCollection()),
							getApplicationFileList(app.getAppfileCollection()));
					appClientList.add(appClient);
				}
				return appClientList;
			}
		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" getting the App List",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			aplicationStoreServiceLogger.error("API Error has occured while retrieving the App List",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				aplicationStoreServiceLogger.error("Error occured while retrieving the App List",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" getting the App List",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return appClientList;
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
	
	public List<AppDefaultParam> getAppDefaultParamListByAppId(Long id) {
		List<AppDefaultParam> appDefaultParamList = new ArrayList<>();
		try {
		if (RestServiceUtil.getSESSION() != null) {
		List<AppParam> appParamService;
			appParamService = RestServiceUtil.getInstance().getClient().getAppApi().getAppParamList(id);
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
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the App Default Param list by App ID",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			aplicationStoreServiceLogger.error("API Error has occured while retrieving the App Default Param list by App ID",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				aplicationStoreServiceLogger.error("Error occured while retrieving the App Default Param list by App ID",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the App Default Param list by App ID",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return appDefaultParamList;
	}
	
	public List<AppDefaultParam> getAppParamListByAppProfileId(Long id){
		List<AppDefaultParam> appDefaultParamList = new ArrayList<>();
		try {
		if (RestServiceUtil.getSESSION() != null) {
		List<AppParam> appParamService = RestServiceUtil.getInstance().getClient().getAppProfileApi().getAppParamListByAppProfile(id);
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
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the App Param list by App Profile ID",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			aplicationStoreServiceLogger.error("API Error has occured while retrieving the App Param list by App Profile ID",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				aplicationStoreServiceLogger.error("Error occured while retrieving the App Param list by App Profile ID",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the App Param list by App Profile ID",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return appDefaultParamList;
	}	
	
	public List<TreeNode> getOwnerList() {
		List<TreeNode> nodeChildList = new ArrayList<>();
		Long entityId=null;
		try {
			if (RestServiceUtil.getSESSION() != null) {
				entityId = RestServiceUtil.getSESSION().getEntityId();
				if(entityId!=null ) {
					nodeChildList = getOwnerListByEntityId(entityId);
					return nodeChildList;
				}
			}
		}
			catch (Exception e) {
				aplicationStoreServiceLogger.error("Error occured while retrieving the owners list in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the owners list in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		
		return nodeChildList;
	}
	
	public TreeNode getOwner(Long id) {
		TreeNode owner = null;
		try {
			if (RestServiceUtil.getSESSION() != null && id!=null) {
				Entity entity = RestServiceUtil.getInstance().getClient().getEntityApi().getEntityById(id);
				owner = new TreeNode(entity.getName(), entity.getId(), entity.getType(), entity.getEntityId(), entity.getDescription(), entity.getChildrenEntities(), entity.getSerialNumber()
						,true);
			}
		}  catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the owner in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			aplicationStoreServiceLogger.error("API Error has occured while retrieving the owner in Application Store Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				aplicationStoreServiceLogger.error("Error occured while retrieving the owner in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the owner in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return owner;

	}

	private List<TreeNode> getOwnerListByEntityId(Long id) {
		List<TreeNode> nodeChildList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				Entity entity = RestServiceUtil.getInstance().getClient().getEntityApi().getEntityHierarchy(id);
				List<Entity> entityList = entity.getChildrenEntities();
					TreeNode node = new TreeNode(entity.getName(), entity.getId(), entity.getType(), entity.getEntityId(), entity.getDescription(), entity.getChildrenEntities()
							, entity.getSerialNumber(),true);
					List<TreeNode> childList = getChildNodes(entityList);
					nodeChildList.addAll(childList);
					ownerListRecursive(childList, nodeChildList);
					nodeChildList.add(node);

			}
		}  catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the owners list by Entity ID in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			aplicationStoreServiceLogger.error("API Error has occured while retrieving the owners list by Entity ID in Application Store Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				aplicationStoreServiceLogger.error("Error occured while retrieving the owners list by Entity ID in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the owners list by Entity ID in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return nodeChildList;

	}
	
	private void ownerListRecursive(List<TreeNode> entityList, List<TreeNode> nodeList) {
		for(TreeNode entity : entityList) {
			List<Entity> entityListSub;
			try {
				entityListSub = entity.getEntityChildrenList();				
				if(entityListSub!=null && !entityListSub.isEmpty()) {
					List<TreeNode> subChildList = getChildNodes(entityListSub);
					nodeList.addAll(subChildList);
					ownerListRecursive(subChildList, nodeList);
				}
			} catch (Exception e) {
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" doing tree data recursion",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		}
	}
	
	private List<TreeNode> getChildNodes(List<Entity> entityList) throws ApiException{
		List<TreeNode> nodeChildList = new ArrayList<>();
		for(Entity entity : entityList) {
			TreeNode node = new TreeNode(entity.getName(), entity.getId(), entity.getType(), entity.getEntityId(), entity.getDescription(), entity.getChildrenEntities(), entity.getSerialNumber(),
					true);
			nodeChildList.add(node);
		}
		return nodeChildList;
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
	
	public List<Profile> getAppProfileListByAppId(Long id) {
		List<Profile> profileList = new ArrayList<>();
		try {
		List<AppProfile> appProfileList = RestServiceUtil.getInstance().getClient().getAppApi().getAppProfileList(id);
		if(appProfileList!=null) {
			profileList=getAppProfileList(appProfileList);
			return profileList;
		}
		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the App Profile List by App ID",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			aplicationStoreServiceLogger.error("API Error has occured while retrieving the App Profile List by App ID",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				aplicationStoreServiceLogger.error("Error occured while retrieving the App Profile List by App ID",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the App Profile List by App ID",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return profileList;
	}
	
	public ListDataProvider<Profile> getAppProfileListDataProvider(Long id){
		ListDataProvider<Profile> debugDataProvider = new ListDataProvider<>(getAppProfileListByAppId(id));
		return debugDataProvider;
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

	private List<AppClient> getSortedAppClientList(Collection<AppClient> unsortedCollection) {
		List<AppClient> sortedList = unsortedCollection.stream().sorted((o1, o2) -> {
			return o1.getPackageName().compareTo(o2.getPackageName());
		}).collect(Collectors.toList());
		return sortedList;
	}
	
	public List<AppDefaultParam> getAllAppFileList(Long id) {
		List<AppDefaultParam> applicationParamFileList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				List<AppParam> appParamFileList = RestServiceUtil.getInstance().getClient().getAppApi().getAppFileList(id);
				for (AppParam appParamFile : appParamFileList) {
					if(appParamFile.getAppParamFormat().getName().toString().equals("file")) {
						AppDefaultParam appParamFileClient = new AppDefaultParam(appParamFile.getId(), appParamFile.getName(),
								appParamFile.getDescription(), appParamFile.getAppParamFormat().getValue(), appParamFile.getDefaultValue());
						applicationParamFileList.add(appParamFileClient);
					}
			}
			}
		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the App file list in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			aplicationStoreServiceLogger.error("API Error has occured while retrieving the App file list in Application Store Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				aplicationStoreServiceLogger.error("Error occured while retrieving the App file list in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the App file list in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return applicationParamFileList;
	}
	
	public List<AppDefaultParam> getAppFileListByAppProfileId(Long id){
		List<AppDefaultParam> appProfileFileList = new ArrayList<>();
		try {
		if (RestServiceUtil.getSESSION() != null) {
		List<AppParam> appParamService = RestServiceUtil.getInstance().getClient().getAppProfileApi().getAppFileListByAppProfile(id);
			for (AppParam appParam : appParamService) {
				if(appParam.getAppParamFormat().getName().toString().equals("file")) {
					AppDefaultParam appProfileFile = new AppDefaultParam(appParam.getId(), appParam.getName(),
							appParam.getDescription(), appParam.getAppParamFormat().getValue(), appParam.getDefaultValue());
					appProfileFileList.add(appProfileFile);
				}
		}
		
		}
		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the App file list by App Profile ID in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			aplicationStoreServiceLogger.error("API Error has occured while retrieving the App file list by App Profile ID in Application Store Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				aplicationStoreServiceLogger.error("Error occured while retrieving the App file list by App Profile ID in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the App file list by App Profile ID in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return appProfileFileList;
	}

	public List<Devices> getAllDeviceList() {
		List<Devices> deviceList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				List<Device> deviceServerList = RestServiceUtil.getInstance().getClient().getDeviceApi()
						.getDevices(null, null);
				for (Device device : deviceServerList) {
					Devices deviceClient = new Devices(device.getId(), device.getName(), device.getDescription(),
							device.getSerialNumber(), device.getAvailable());
					deviceList.add(deviceClient);
				}
			}
		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the devices list in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			aplicationStoreServiceLogger.error("API Error has occured while retrieving the devices list in Application Store Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				aplicationStoreServiceLogger.error("Error occured while retrieving the devices list in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the devices list in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return deviceList;
	}

	public ListDataProvider<AppClient> getAppListDataProvider() {
		ListDataProvider<AppClient> appDataProvider = null;
			appDataProvider = new ListDataProvider<>(getSortedAppClientList(getAppListForGrid()));
		return appDataProvider;
	}

	public void saveApp(AppClient appClient) {
		if (appClient != null) {
			try {
				if (RestServiceUtil.getSESSION() != null) {
					App oldServerApp = null;
					App serverApp = new App();
					if(appClient.getId()!=null) {
					oldServerApp = RestServiceUtil.getInstance().getClient().getAppApi().getApp(appClient.getId());
					serverApp.setId(appClient.getId());
					}
					boolean isOld =  oldServerApp != null ? true : false;
					serverApp.setAvailable(appClient.isAvailable());
					serverApp.setName(appClient.getPackageName());
					serverApp.setDescription(appClient.getDescription());
					serverApp.setVersion(appClient.getPackageVersion());
					serverApp.setOwnerId(appClient.getOwnerId());
					serverApp.setActive(appClient.isActive());
					List<AppParam> appParamList = new ArrayList<>();
					for (AppDefaultParam appDefaultParam : appClient.getAppDefaultParamList()) {
						AppParam appParam = new AppParam();
						appParam.setId(appDefaultParam.getId());
						appParam.setDescription(appDefaultParam.getDescription());
						appParam.setName(appDefaultParam.getParameter());
						if(isOld) {
							for(AppParam oldAppParam : oldServerApp.getAppParamCollection()) {
								if(oldAppParam.getId() != appParam.getId()) {
									appParamList.add(appParam);
								}
							}
						}else {
						appParamList.add(appParam);
						}
					}
					serverApp.setAppParamCollection(appParamList);
					List<AppProfile> appProfileList = new ArrayList<>();
					for (Profile profile : appClient.getProfileList()) {
						AppProfile appProfile = new AppProfile();
						appProfile.setId(profile.getId());
						appProfile.setName(profile.getName());
						// How to set appprofileparams and other elements
						if(isOld) {
							for(AppProfile oldAppProfile : oldServerApp.getAppprofileCollection()) {
								if(oldAppProfile.getId() != appProfile.getId()) {
									appProfileList.add(appProfile);
								}
							}
						}else {
							appProfileList.add(appProfile);
						}
					}
					serverApp.setAppprofileCollection(appProfileList);
					if(isOld) {
						serverApp.setActive(oldServerApp.getActive());
						RestServiceUtil.getInstance().getClient().getAppApi().updateApp(serverApp.getId(), serverApp);
					}else {
						RestServiceUtil.getInstance().getClient().getAppApi().createApp(serverApp);
					}
				}
			} catch (ApiException e) {
				if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
					Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}else {
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" saving the App in Application Store Screen",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
				aplicationStoreServiceLogger.error("API Error has occured while saving the App in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			}
				catch (Exception e) {
					aplicationStoreServiceLogger.error("Error occured while saving the App in Application Store Screen",e);
					RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" saving the App in Application Store Screen",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}

		}
	}

	public void removeApp(AppClient appClient) {
		if (appClient != null) {
			try {
				if (RestServiceUtil.getSESSION() != null) {
					RestServiceUtil.getInstance().getClient().getAppApi().deleteApp(appClient.getId());
				}
			} catch (ApiException e) {
				if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
					Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}else {
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting the app in Application Store Screen",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
				aplicationStoreServiceLogger.error("API Error has occured while deleting the app in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			}
				catch (Exception e) {
					aplicationStoreServiceLogger.error("Error occured while deleting the app in Application Store Screen",e);
					RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting the app in Application Store Screen",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
		}
	}
	
	public void uploadAppFiles(Long appId, String description, String path) {
		try {
			if (RestServiceUtil.getSESSION() != null) {
				RestServiceUtil.getInstance().getClient().getAppApi().addFile(appId, description, path);
				File toDeleteFile = new File(path);
				toDeleteFile.delete();
			}
		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" uploading the files in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			aplicationStoreServiceLogger.error("API Error has occured while uploading the files in Application Store Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				aplicationStoreServiceLogger.error("Error occured while uploading the files in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" uploading the files in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
	}
	
	public void deleteAppFiles(Long appId, Long appParamId) {
		try {
			if (RestServiceUtil.getSESSION() != null) {
				AppParam appParam = RestServiceUtil.getInstance().getClient().getAppParamApi().getAppParam(appParamId);
				if(appParam.getAppParamFormat().getValue().equals("file")) {
					RestServiceUtil.getInstance().getClient().getAppApi().deleteAppFile(appId, appParamId);
				}
			}
		} catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting the files in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			aplicationStoreServiceLogger.error("API Error has occured while deleting the files in Application Store Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				aplicationStoreServiceLogger.error("Error occured while deleting the files in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting the files in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
	}
	
	public void deleteAppProfileFiles(Long appProfileId, Long appParamId) {
		try {
			if (RestServiceUtil.getSESSION() != null) {
				AppParam appParam = RestServiceUtil.getInstance().getClient().getAppParamApi().getAppParam(appParamId);
				if(appParam.getAppParamFormat().getValue().equals("file")) {
					RestServiceUtil.getInstance().getClient().getAppProfileApi().deleteAppProfileFileValue(appProfileId, appParamId);
				}
			}
		}catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting the App Profile files in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			aplicationStoreServiceLogger.error("API Error has occured while deleting the App Profile files in Application Store Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				aplicationStoreServiceLogger.error("Error occured while deleting the App Profile files in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting the App Profile files in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
	}
	
	public void saveAppProfile(AppClient appClient, Profile profile) {
		if (appClient != null && profile != null) {
			try {
				if (RestServiceUtil.getSESSION() != null) {
					AppProfile serverProfile = new AppProfile();
					serverProfile.setAppId(appClient.getId());
					serverProfile.setName(profile.getName());
					serverProfile.setActive(true);
					
					RestServiceUtil.getInstance().getClient().getAppApi().addAppProfile(appClient.getId(), serverProfile);
				}
			}catch (ApiException e) {
				if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
					Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}else {
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" saving the App profile in Application Store Screen",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
				aplicationStoreServiceLogger.error("API Error has occured while saving the App profile in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			}
				catch (Exception e) {
					aplicationStoreServiceLogger.error("Error occured while saving the App Profile in Application Store Screen",e);
					RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" saving the App profile in Application Store Screen",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
		}
	}
	
	public void updateAppProfile(String name, Profile profile) {
		if (profile != null) {
			try {
				if (RestServiceUtil.getSESSION() != null) {
					AppProfile serverProfile = RestServiceUtil.getInstance().getClient().getAppProfileApi().getAppProfile(profile.getId());
					serverProfile.setName(name);
					
					RestServiceUtil.getInstance().getClient().getAppProfileApi().updateAppProfile(serverProfile.getId(), serverProfile);
				}
			}catch (ApiException e) {
				if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
					Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}else {
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" updating the App profile in Application Store Screen",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
				aplicationStoreServiceLogger.error("API Error has occured while updating the App profile in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			}
				catch (Exception e) {
					aplicationStoreServiceLogger.error("Error occured while updating the App Profile in Application Store Screen",e);
					RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" updating the App profile in Application Store Screen",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
		}
	}
	
	public void removeAppProfile(Long appId, Long profileId) {
			try {
				if (RestServiceUtil.getSESSION() != null) {
					List<AppDefaultParam> appDefaultParamList = getAppDefaultParamListByAppId(appId);
					for(AppDefaultParam appDefaultParam:appDefaultParamList) {
						RestServiceUtil.getInstance().getClient().getAppProfileApi().deleteAppProfileParamValue(profileId, appDefaultParam.getId());
					}
					RestServiceUtil.getInstance().getClient().getAppApi().deleteAppProfile(appId, profileId);
				}
			}catch (ApiException e) {
				if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
					Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}else {
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting the App Profile in Application Store Screen",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
				aplicationStoreServiceLogger.error("API Error has occured while deleting the App Profile in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			}
				catch (Exception e) {
					aplicationStoreServiceLogger.error("Error occured while deleting the App Profile in Application Store Screen",e);
					RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting the App Profile in Application Store Screen",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
	}
	
	public List<String> getAppParamTypeList(Long id){
		List<String> appParamTypeList = new ArrayList<>();
		try {
		if (RestServiceUtil.getSESSION() != null) {
			List<AppParamFormat> appParamServiceList = RestServiceUtil.getInstance().getClient().getAppParamFormatApi().getAppParamFormatList();
				for (AppParamFormat appParamFormat : appParamServiceList) {
					String value = appParamFormat.getValue().toUpperCase();
					if(!value.equalsIgnoreCase("file")) {
						appParamTypeList.add(value);
					}
			}
				return appParamTypeList;
			}
		}catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the App Param Type List in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			aplicationStoreServiceLogger.error("API Error has occured while retrieving the App Param Type List in Application Store Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				aplicationStoreServiceLogger.error("Error occured while retrieving the App Param Type List in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the App Param Type List in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return appParamTypeList;
	}
	
	public AppParamFormat getAppParamFormatByType(String type){
		AppParamFormat appParamFormatNew = new AppParamFormat();
		try {
		if (RestServiceUtil.getSESSION() != null) {
			List<AppParamFormat> appParamServiceList = RestServiceUtil.getInstance().getClient().getAppParamFormatApi().getAppParamFormatList();
				for (AppParamFormat appParamFormat : appParamServiceList) {
					if(type.equalsIgnoreCase(appParamFormat.getValue())){
						return appParamFormat;
					}	
				}
			}
		}catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the App Param format by type in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			aplicationStoreServiceLogger.error("API Error has occured while retrieving the App Param format by type in Application Store Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				aplicationStoreServiceLogger.error("Error occured while retrieving the App Param format by type in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" retrieving the App Param format by type in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
		return appParamFormatNew;
	}
	
	public void saveAppDefaultParam(AppClient appClient, AppDefaultParam defaultParam, AppParamFormat appParamFormat) throws NumberFormatException {
		EntityLevel entityLevel = new EntityLevel();
        entityLevel.setId(Long.valueOf(1));
        entityLevel.setName("EntityLevel1");
		if (appClient != null && defaultParam != null) {
			try {
				if (RestServiceUtil.getSESSION() != null) {
					Action action = RestServiceUtil.getInstance().getClient().getActionApi().getAction(Long.valueOf("1"));
					AppParam appParam = new AppParam();
					appParam.setAppId(appClient.getId());
					appParam.setDescription(defaultParam.getDescription());
					appParam.setName(defaultParam.getParameter());
					appParam.setDefaultValue(defaultParam.getValue());
					appParam.setAppParamFormat(appParamFormat);
					appParam.setForceUpdate(false);
					appParam.setModifiable(true);
					appParam.setAction(action);
					appParam.setEntityLevel(entityLevel);
					
					if(defaultParam.getId()!=null) {
						appParam.setId(defaultParam.getId());
						RestServiceUtil.getInstance().getClient().getAppApi().updateAppParam(appClient.getId(), appParam);
					}
					else {
						RestServiceUtil.getInstance().getClient().getAppApi().addAppParam(appClient.getId(), appParam);
					}
				}
			}catch (ApiException e) {
				if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
					Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}else {
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" saving the App default param in Application Store Screen",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
				aplicationStoreServiceLogger.error("API Error has occured while saving the App default param in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			}
				catch (Exception e) {
					aplicationStoreServiceLogger.error("Error occured while saving the App default param in Application Store Screen",e);
					RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" saving the App default param in Application Store Screen",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
		}
	}
	
	public void updateAppParamOfAppProfile(Profile profile, AppDefaultParam defaultParam, AppParamFormat appParamFormat) {
		AppProfileParamValue appProfileParamValue = new AppProfileParamValue();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				AppProfile appProfile = RestServiceUtil.getInstance().getClient().getAppProfileApi().getAppProfile(profile.getId());
				for(AppProfileParamValue appProfileParamValueList : appProfile.getAppProfileParamValueCollection()){
				       if(appProfileParamValueList.getAppParamId().equals(defaultParam.getId())){
				    	   appProfileParamValue = appProfileParamValueList;
				  }
				       appProfileParamValue.setDefaultValue(defaultParam.getValue());
				}
				RestServiceUtil.getInstance().getClient().getAppProfileApi().updateAppProfileParamValue(profile.getId(), appProfileParamValue);
			}
		}catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" updating the App param of App Profile in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			aplicationStoreServiceLogger.error("API Error has occured while updating the App param of App Profile in Application Store Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				aplicationStoreServiceLogger.error("Error occured while supdating the App param of App Profile in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" updating the App param of App Profile in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
	}
	
	public void removeAPPParamAll(Long appId)  {
		try {
			if (RestServiceUtil.getSESSION() != null) {
			
			RestServiceUtil.getInstance().getClient().getAppApi().deleteAllAppParam(appId);
			
			}
		}catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting all App param in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			aplicationStoreServiceLogger.error("API Error has occured while deleting all App param in Application Store Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				aplicationStoreServiceLogger.error("Error occured while deleting all App param in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting all App param in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
}
	
	public void removeAPPParam(Long appId, Long appParamId) {
		try {
			if (RestServiceUtil.getSESSION() != null) {
			RestServiceUtil.getInstance().getClient().getAppApi().deleteAppParam(appId, appParamId);
			
			}
		}catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting App param in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			aplicationStoreServiceLogger.error("API Error has occured while deleting App param in Application Store Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				aplicationStoreServiceLogger.error("Error occured while deleting App param in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting App param in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
}
	
	public void removeAppProfileParam(Long appProfileId, Long appParamId){
		try {
			if (RestServiceUtil.getSESSION() != null) {
			RestServiceUtil.getInstance().getClient().getAppProfileApi().deleteAppProfileParamValue(appProfileId, appParamId);
			
			}
		}catch (ApiException e) {
			if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}else {
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting all App profile param in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
			aplicationStoreServiceLogger.error("API Error has occured while deleting all App profile param in Application Store Screen",e);
			RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
			catch (Exception e) {
				aplicationStoreServiceLogger.error("Error occured while deleting all App profile param in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
				Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" deleting all App profile param in Application Store Screen",Type.ERROR_MESSAGE);
				ComponentUtil.sessionExpired(notification);
			}
}
	
	public List<AppClient> searchApps(String filter) {
		List<AppClient> appClientList = new ArrayList<>();
			try {
				if (RestServiceUtil.getSESSION() != null) {
					List<App> appList = RestServiceUtil.getInstance().getClient().getAppApi().searchApps(filter, null, null);
					for (App app : appList) {
						AppClient appClient = new AppClient(app.getId(), app.getName(), app.getDescription(),
								app.getVersion(), app.getAvailable(), app.getActive(),getAppDefaultParamList(app.getAppParamCollection()),
								null, app.getOwnerId(), getAppProfileList(app.getAppprofileCollection()),
								getApplicationFileList(app.getAppfileCollection()));
						appClientList.add(appClient);
					}
					return appClientList;
				}
			}catch (ApiException e) {
				if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
					Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}else {
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" searching Apps in Application Store Screen",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
				aplicationStoreServiceLogger.error("API Error has occured while searching Apps in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			}
				catch (Exception e) {
					aplicationStoreServiceLogger.error("Error occured while searching Apps in Application Store Screen",e);
					RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" searching Apps in Application Store Screen",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
			return appClientList;
		}
	
	public List<AppDefaultParam> searchParams(Long id, String filter) {
		List<AppDefaultParam> appDefaultParamList = new ArrayList<>();
			try {
				if (RestServiceUtil.getSESSION() != null) {
					List<AppParam> appParamList = RestServiceUtil.getInstance().getClient().getAppApi().searchAppParam(id, filter, null, null);
					appDefaultParamList=getAppDefaultParamList(appParamList);
					return appDefaultParamList;
				}
			}catch (ApiException e) {
				if(e.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
					Notification notification = Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}else {
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" searching  App params in Application Store Screen",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
				aplicationStoreServiceLogger.error("API Error has occured while searching  App params in Application Store Screen",e);
				RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
			}
				catch (Exception e) {
					aplicationStoreServiceLogger.error("Error occured while searching App params in Application Store Screen",e);
					RestClient.sendMessage(e.getMessage(), ExceptionUtils.getStackTrace(e));
					Notification notification = Notification.show(NotificationUtil.SERVER_EXCEPTION+" searching  App params in Application Store Screen",Type.ERROR_MESSAGE);
					ComponentUtil.sessionExpired(notification);
				}
			return appDefaultParamList;
		}

	public void logApplicationStoreScreenErrors(Exception e) {
		aplicationStoreServiceLogger.error("Error Occured", e);
	}

}
