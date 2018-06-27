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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.App;
import com.luretechnologies.client.restlib.service.model.AppFile;
import com.luretechnologies.client.restlib.service.model.AppParam;
import com.luretechnologies.client.restlib.service.model.AppProfile;
import com.luretechnologies.client.restlib.service.model.Device;
import com.luretechnologies.tms.backend.data.entity.AppClient;
import com.luretechnologies.tms.backend.data.entity.AppDefaultParam;
import com.luretechnologies.tms.backend.data.entity.AppMock;
import com.luretechnologies.tms.backend.data.entity.ApplicationFile;
import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.ParameterType;
import com.luretechnologies.tms.backend.data.entity.Profile;
import com.luretechnologies.tms.backend.data.entity.ProfileType;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.vaadin.data.provider.ListDataProvider;

@Service
public class ApplicationStoreService {

	public List<AppClient> getAppListForGrid() throws ApiException {
		List<AppClient> appClientList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {

				List<App> appsList = RestServiceUtil.getInstance().getClient().getAppApi().getApps();
				for (App app : appsList) {
					AppClient appClient = new AppClient(app.getId(), app.getName(), app.getDescription(),
							app.getVersion(), app.getAvailable(), getAppDefaultParamList(app.getAppparamCollection()),
							null, getOwner(app.getOwnerId()), getAppProfileList(app.getAppprofileCollection()),
							getApplicationFileList(app.getAppfileCollection()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appClientList;
	}

	private List<AppDefaultParam> getAppDefaultParamList(List<AppParam> appParamList) {
		List<AppDefaultParam> appDefaultParamList = new ArrayList<>();
		for (AppParam appParam : appParamList) {
			AppDefaultParam appDefaultParam = new AppDefaultParam(appParam.getId(), appParam.getName(),
					appParam.getDescription(), ParameterType.BOOLEAN, appParam.getModifiable());
			appDefaultParamList.add(appDefaultParam);
		}
		return appDefaultParamList;
	}

	private User getOwner(Long id) {
		User owner = null;
		try {
			if (RestServiceUtil.getSESSION() != null) {
				com.luretechnologies.client.restlib.service.model.User serverUser = RestServiceUtil.getInstance()
						.getClient().getUserApi().getUser(id);
				owner = new User(serverUser.getId(), serverUser.getEmail(), serverUser.getUsername(), null,
						serverUser.getRole().getName(), serverUser.getFirstName(), serverUser.getLastName(),
						serverUser.getAvailable());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return owner;

	}

	private List<Profile> getAppProfileList(List<AppProfile> appProfileList) {
		List<Profile> profileList = new ArrayList<>();
		for (AppProfile appProfile : appProfileList) {
			Profile profile = new Profile(appProfile.getId(), ProfileType.MOTO, appProfile.getName());
			profileList.add(profile);
		}
		return profileList;
	}

	private List<ApplicationFile> getApplicationFileList(List<AppFile> appFileList) {
		List<ApplicationFile> fileList = new ArrayList<>();
		for (AppFile appFile : appFileList) {
			ApplicationFile file = new ApplicationFile(appFile.getId(), appFile.getName(), appFile.getDescription(),
					appFile.getDefaultValue());
			fileList.add(file);
		}
		return fileList;
	}

	private List<AppClient> getSortedAppClientList(Collection<AppClient> unsortedCollection) {
		List<AppClient> sortedList = unsortedCollection.stream().sorted((o1, o2) -> {
			return o1.getPackageName().compareTo(o2.getPackageName());
		}).collect(Collectors.toList());
		return sortedList;
	}

	public List<Profile> getAllAppProfileList(Long appId, Long entityId) {
		List<Profile> appProfileList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				List<AppProfile> appProfileServerList = RestServiceUtil.getInstance().getClient().getAppProfileApi()
						.getAppProfileListByEntity(appId, entityId);
				for (AppProfile appProfile : appProfileServerList) {
					Profile profile = new Profile(appProfile.getId(), ProfileType.MOTO, appProfile.getName());
					appProfileList.add(profile);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appProfileList;
	}

	public List<ApplicationFile> getAllAppFileList() {
		List<ApplicationFile> applicationFileList = new ArrayList<>();
		try {
			if (RestServiceUtil.getSESSION() != null) {
				List<AppFile> appFileList = RestServiceUtil.getInstance().getClient().getAppFileApi().getAppFiles();
				for (AppFile appFile : appFileList) {
					ApplicationFile file = new ApplicationFile(appFile.getId(), appFile.getName(),
							appFile.getDescription(), appFile.getDefaultValue());
					applicationFileList.add(file);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return applicationFileList;
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deviceList;
	}

	public ListDataProvider<AppClient> getAppListDataProvider() {
		ListDataProvider<AppClient> appDataProvider = null;
		try {
			appDataProvider = new ListDataProvider<>(getSortedAppClientList(getAppListForGrid()));
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return appDataProvider;
	}

	public void saveApp(AppClient appClinet) {
		if (appClinet != null) {
			try {
				if (RestServiceUtil.getSESSION() != null) {
					App serverApp = new App();
					serverApp.setAvailable(appClinet.isAvailable());
					serverApp.setName(appClinet.getPackageName());
					serverApp.setDescription(appClinet.getDescription());
					serverApp.setVersion(appClinet.getPackageVersion());
					// set the lists e.g appFile, appProfile
					List<AppParam> appParamList = new ArrayList<>();
					for (AppDefaultParam appDefaultParam : appClinet.getAppDefaultParamList()) {
						AppParam appParam = new AppParam();
						appParam.setId(appDefaultParam.getId());
						appParam.setDescription(appDefaultParam.getDescription());
						appParam.setName(appDefaultParam.getParameter());
						// Add action and other params .. how to get them for UI
						appParamList.add(appParam);
					}
					serverApp.setAppparamCollection(appParamList);
					List<AppProfile> appProfileList = new ArrayList<>();
					for (Profile profile : appClinet.getProfile()) {
						AppProfile appProfile = new AppProfile();
						appProfile.setId(profile.getId());
						appProfile.setName(profile.getName());
						// How to set appprofileparams and other elements
						appProfileList.add(appProfile);
					}
					serverApp.setAppprofileCollection(appProfileList);
					// Add all the other attributes. //FIXME: figure out how and what are the
					// required attributes to be set
					RestServiceUtil.getInstance().getClient().getAppApi().createApp(serverApp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void removeApp(AppClient appClient) {
		if (appClient != null) {
			try {
				if (RestServiceUtil.getSESSION() != null) {
					RestServiceUtil.getInstance().getClient().getAppApi().deleteApp(appClient.getId());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void saveAppProfile(AppClient appClient, Profile profile) {
		if (appClient != null && profile != null) {
			try {
				if (RestServiceUtil.getSESSION() != null) {
					AppProfile serverProfile = new AppProfile();
					serverProfile.setAppId(appClient.getId());
					serverProfile.setId(profile.getId());
					serverProfile.setName(profile.getName());
					//FIXME: find the correct app profile create method
//					RestServiceUtil.getInstance().getClient().getAppProfileApi().addEntityAppProfile(serverProfile.getId(), entityId);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void removeAppProfile(AppClient appClient, Profile profile) {
		if (appClient != null && profile != null) {
			try {
				if (RestServiceUtil.getSESSION() != null) {
					//FIXME: find the correct app profile remove method
//					RestServiceUtil.getInstance().getClient().getAppProfileApi().deleteEntityAppProfile(profile.getId(), entityId);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void saveAppDefaultParam(AppClient appClient, AppDefaultParam defaultParam) {
		if (appClient != null && defaultParam != null) {
			try {
				if (RestServiceUtil.getSESSION() != null) {
					AppParam appParam = new AppParam();
					appParam.setAppId(appClient.getId());
					appParam.setDescription(defaultParam.getDescription());
					appParam.setId(defaultParam.getId());
					appParam.setName(defaultParam.getParameter());
					//FIXME: find out App Default Param create 
//					RestServiceUtil.getInstance().getClient().getApp
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void removeAppDefaultParam(AppClient appClient, AppDefaultParam defaultParam) {
		if (appClient != null && defaultParam != null) {
			try {
				if (RestServiceUtil.getSESSION() != null) {
					//FIXME: No delete Param api is available
//					RestServiceUtil.getInstance().getClient().getAppParamApi();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


}
