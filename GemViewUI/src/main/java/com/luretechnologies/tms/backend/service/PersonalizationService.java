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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.App;
import com.luretechnologies.client.restlib.service.model.AppFile;
import com.luretechnologies.client.restlib.service.model.AppParam;
import com.luretechnologies.client.restlib.service.model.AppProfile;
import com.luretechnologies.client.restlib.service.model.Device;
import com.luretechnologies.client.restlib.service.model.Entity;
import com.luretechnologies.client.restlib.service.model.Merchant;
import com.luretechnologies.client.restlib.service.model.Organization;
import com.luretechnologies.client.restlib.service.model.Region;
import com.luretechnologies.client.restlib.service.model.Terminal;
import com.luretechnologies.tms.backend.data.entity.AppClient;
import com.luretechnologies.tms.backend.data.entity.AppDefaultParam;
import com.luretechnologies.tms.backend.data.entity.ApplicationFile;
import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.OverRideParameters;
import com.luretechnologies.tms.backend.data.entity.Profile;
import com.luretechnologies.tms.backend.data.entity.TerminalClient;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.ListDataProvider;

@Service
public class PersonalizationService {

	@Autowired
	TreeDataNodeService treeDataNodeService;
	Terminal terminalForPV;
	Device deviceForPersonalizationView;
	Region regionForPersonalizationView;
	Organization organizationForPersonalizationView;

	public TreeData<TreeNode> getTreeData() throws ApiException {
		try {
			if (RestServiceUtil.getSESSION() != null) {
				TreeData<TreeNode> treeData = treeDataNodeService.getTreeData();
				return treeData;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createEntity(TreeNode parentNode, TreeNode treeNewNode) {
		try {
			if (RestServiceUtil.getSESSION() != null) {
				switch (parentNode.getType()) {

				case ENTERPRISE:
					Organization organization = new Organization();
					organization.setAvailable(treeNewNode.isActive());
					organization.setDescription(treeNewNode.getDescription());
					organization.setName(treeNewNode.getLabel());
					organization.setParentId(parentNode.getId());
					organization.setType(treeNewNode.getType());
					organization.setEntityId(treeNewNode.getEntityId());
					RestServiceUtil.getInstance().getClient().getOrganizationApi().createOrganization(organization);
					break;
				case ORGANIZATION:
					Region region = new Region();
					region.setAvailable(treeNewNode.isActive());
					region.setDescription(treeNewNode.getDescription());
					region.setName(treeNewNode.getLabel());
					region.setParentId(parentNode.getId());
					region.setType(treeNewNode.getType());
					region.setEntityId(treeNewNode.getEntityId());
					RestServiceUtil.getInstance().getClient().getRegionApi().createRegion(region);
					break;
				case REGION:
					Merchant merchant = new Merchant();
					merchant.setAvailable(treeNewNode.isActive());
					merchant.setDescription(treeNewNode.getDescription());
					merchant.setName(treeNewNode.getLabel());
					merchant.setParentId(parentNode.getId());
					merchant.setType(treeNewNode.getType());
					merchant.setEntityId(treeNewNode.getEntityId());
					RestServiceUtil.getInstance().getClient().getMerchantApi().createMerchant(merchant);
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

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
					Merchant merchant = new Merchant();
					merchant.setAvailable(node.isActive());
					merchant.setDescription(node.getDescription());
					merchant.setName(node.getLabel());
					merchant.setType(node.getType());
					merchant.setEntityId(node.getEntityId());
					RestServiceUtil.getInstance().getClient().getMerchantApi().updateMerchant(merchant.getEntityId(), merchant);
					break;
				case TERMINAL:
					Terminal terminal = new Terminal();
					terminal.setAvailable(node.isActive());
					terminal.setDescription(node.getDescription());
					terminal.setName(node.getLabel());
					terminal.setType(node.getType());
					terminal.setFrequency(Long.parseLong(node.getFrequency()));
					terminal.setHeartbeat(node.isHeartBeat());
					terminal.setSerialNumber(node.getSerialNum());
					terminal.setEntityId(terminal.getEntityId());
					RestServiceUtil.getInstance().getClient().getTerminalApi().updateTerminal(terminal.getEntityId(), terminal);
					break;
					
				case DEVICE:
					Device device = new Device();
					device.setAvailable(node.isActive());
					device.setDescription(node.getDescription());
					device.setName(node.getLabel());
					device.setType(node.getType());
					device.setEntityId(node.getEntityId());
					RestServiceUtil.getInstance().getClient().getDeviceApi().updateDevice(device.getEntityId(), device);
					break;
				default:
					break;
				}
			}
			
		}catch (Exception e) {
			// TODO: handle exception
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
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
			e.printStackTrace();
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
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;

	}

	private Device getDeviceByEntityId(String entityId) throws ApiException {
		if (deviceForPersonalizationView == null)
			deviceForPersonalizationView = RestServiceUtil.getInstance().getClient().getDeviceApi().getDevice(entityId);
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

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ListDataProvider<Devices>(allDevices);
	}
	
	public ListDataProvider<OverRideParameters> getOverrideParamDataProvider(Long appId){
		List<OverRideParameters> overRideParamList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				for(AppParam appParam : RestServiceUtil.getInstance().getClient().getAppApi().getAppParamList(appId)) {
					overRideParamList.add(new OverRideParameters(appParam.getId(),appParam.getName(), appParam.getDescription(),appParam.getAppParamFormat().getValue(), appParam.getDefaultValue()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ListDataProvider<>(overRideParamList);
	}
	

	
	public ListDataProvider<ApplicationFile> getApplicationFileDataProvider(Long appId){
		List<ApplicationFile> applicationFileList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				for(AppFile appFile : RestServiceUtil.getInstance().getClient().getAppApi().getAppFileList(appId)) {
					applicationFileList.add(new ApplicationFile(appFile.getId(),appFile.getName(), appFile.getDescription(), appFile.getDefaultValue()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ListDataProvider<>(applicationFileList);
	}
	public ListDataProvider<Profile> getProfileDataProvider(Long appId){
		List<Profile> profileList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				for(AppProfile appProfile : RestServiceUtil.getInstance().getClient().getAppApi().getAppProfileList(appId)) {
					profileList.add(new Profile(appProfile.getId(),appProfile.getName()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ListDataProvider<>(profileList);
	}
	public ListDataProvider<Profile> getProfileForEntityDataProvider(Long appId,Long entityId){
		List<Profile> profileList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				for(AppProfile appProfile : RestServiceUtil.getInstance().getClient().getAppProfileApi().getAppProfileListByEntity(appId, entityId)) {
					profileList.add(new Profile(appProfile.getId(),appProfile.getName()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ListDataProvider<>(profileList);
	}
	
	public void saveProfileForEntity(List<Profile> profileList,Long entityId) {
		try {
			if (RestServiceUtil.getSESSION() != null) {
				for(Profile profile : profileList) {
					RestServiceUtil.getInstance().getClient().getAppProfileApi().addEntityAppProfile(profile.getId(), entityId);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void deleteProfileForEntity(List<Profile> profileList,Long entityId) {
		try {
			if (RestServiceUtil.getSESSION() != null) {
				for(Profile profile : profileList) {
					RestServiceUtil.getInstance().getClient().getAppProfileApi().deleteEntityAppProfile(profile.getId(), entityId);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

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
			// TODO: handle exception
			e.printStackTrace();
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
			// TODO: handle exception
			e.printStackTrace();
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
			// TODO: handle exception
			e.printStackTrace();
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
		
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return allAppList;
}
public void createOverRideParam(AppClient app, OverRideParameters param) {
	try {
		if(RestServiceUtil.getSESSION()!=null) {
			AppParam appParam = new AppParam();
			appParam.setAppId(app.getId());
			appParam.setName(param.getParameter());
			appParam.setDescription(param.getDescription());
			appParam.setDefaultValue(param.getValue());
			RestServiceUtil.getInstance().getClient().getAppApi().addAppParam(app.getId(), appParam);
		}
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
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
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
}

public void deleteOverRideParam(AppClient app, OverRideParameters param) {
	try {
		if(RestServiceUtil.getSESSION()!=null) {
			RestServiceUtil.getInstance().getClient().getAppApi().deleteAppParam(app.getId(), param.getId());
		}
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
}

	/**
	 * @param entityId
	 * @throws ApiException
	 */
	private TerminalClient getTerminalByEntityId(String entityId) throws ApiException {
			terminalForPV = RestServiceUtil.getInstance().getClient().getTerminalApi()
					.getTerminal(entityId);
			TerminalClient terminal = new TerminalClient(terminalForPV.getId(), terminalForPV.getType().name(), terminalForPV.getName(), terminalForPV.getDescription(),
					terminalForPV.getSerialNumber(), terminalForPV.getAvailable(),terminalForPV.getDebugActive(),
					terminalForPV.getFrequency(), terminalForPV.getLastContact().toString());

		return terminal;
	}
	private TreeNode getOwner(Long id) {
		TreeNode owner = null;
		try {
			if (RestServiceUtil.getSESSION() != null && id!=null) {
				Entity entity = RestServiceUtil.getInstance().getClient().getEntityApi().getEntityById(id);
				owner = new TreeNode(entity.getName(), entity.getId(), entity.getType(), entity.getEntityId(), entity.getDescription()
						,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	private List<Profile> getAppProfileList(List<AppProfile> appProfileList) {
		List<Profile> profileList = new ArrayList<>();
		if(appProfileList!=null) {
			for (AppProfile appProfile : appProfileList) {
				Profile profile = new Profile(appProfile.getId(), appProfile.getName());
				profileList.add(profile);
			}
			return profileList;
		}
		return profileList;
	}
	private List<ApplicationFile> getApplicationFileList(List<AppFile> appFileList) {
		List<ApplicationFile> fileList = new ArrayList<>();
		if(appFileList!=null) {
			for (AppFile appFile : appFileList) {
				ApplicationFile file = new ApplicationFile(appFile.getId(), appFile.getName(), appFile.getDescription(),
					appFile.getDefaultValue());
				fileList.add(file);
			}
			return fileList;
		}
		
		return fileList;
	}

}