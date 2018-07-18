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
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.App;
import com.luretechnologies.client.restlib.service.model.AppFile;
import com.luretechnologies.client.restlib.service.model.AppParam;
import com.luretechnologies.client.restlib.service.model.AppProfile;
import com.luretechnologies.client.restlib.service.model.Device;
import com.luretechnologies.client.restlib.service.model.Entity;
import com.luretechnologies.client.restlib.service.model.Organization;
import com.luretechnologies.client.restlib.service.model.Region;
import com.luretechnologies.client.restlib.service.model.Terminal;
import com.luretechnologies.tms.backend.data.entity.AppClient;
import com.luretechnologies.tms.backend.data.entity.AppDefaultParam;
import com.luretechnologies.tms.backend.data.entity.ApplicationFile;
import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.OverRideParameters;
import com.luretechnologies.tms.backend.data.entity.ParameterType;
import com.luretechnologies.tms.backend.data.entity.Profile;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.ListDataProvider;

@Service
public class PersonalizationService {

	@Autowired
	TreeDataNodeService treeDataNodeService;
	Terminal terminalForPersonalizationView;
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
				getTerminalByEntityId(entityId);
				if (deviceForPersonalizationView != null)
					return deviceForPersonalizationView.getSerialNumber();
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
		if (deviceForPersonalizationView == null & !deviceForPersonalizationView.getId().toString().equals(entityId))
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
					overRideParamList.add(new OverRideParameters(appParam.getId(),appParam.getName(), appParam.getDescription(), ParameterType.OTHER, appParam.getAppParamFormat().getValue()));
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
	public String getTerminalSerialNumberByEntityId(String entityId) {
		try {
			if (RestServiceUtil.getSESSION() != null) {
				getTerminalByEntityId(entityId);
				if (terminalForPersonalizationView != null)
					return terminalForPersonalizationView.getSerialNumber();
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
				getTerminalByEntityId(entityId);
				if (terminalForPersonalizationView != null)
					return terminalForPersonalizationView.getFrequency();
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
				getTerminalByEntityId(entityId);
				if (terminalForPersonalizationView != null)
					return terminalForPersonalizationView.isHeartbeat();
				else
					return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

public List<AppClient> getAppListByLoggedUserEntity() {
	List<AppClient> allAppList = new ArrayList<>();
	try {
		if(RestServiceUtil.getSESSION()!=null) {
			for(App app : RestServiceUtil.getInstance().getClient().getAppApi().getApps()) {
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

	/**
	 * @param entityId
	 * @throws ApiException
	 */
	private Terminal getTerminalByEntityId(String entityId) throws ApiException {
		if (terminalForPersonalizationView == null
				&& !terminalForPersonalizationView.getId().toString().equals(entityId))
			terminalForPersonalizationView = RestServiceUtil.getInstance().getClient().getTerminalApi()
					.getTerminal(entityId);

		return terminalForPersonalizationView;
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