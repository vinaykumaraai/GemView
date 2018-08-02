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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.AuditUserLog;
import com.luretechnologies.client.restlib.service.model.HeartbeatOdometer;
import com.luretechnologies.client.restlib.service.model.Terminal;
import com.luretechnologies.tms.backend.data.entity.DeviceOdometer;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Tree.ItemClick;

@SpringComponent
@Service
public class OdometerService {
	
	@Autowired
	TreeDataNodeService treeDataNodeService;
	
	public TreeData<TreeNode> getDeviceOdometerTreeData() throws ApiException{
		try {
			TreeData<TreeNode> treeData = treeDataNodeService.getTreeData();
			return treeData;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<DeviceOdometer> getOdometerGridData(String id, String type) throws ApiException{
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
		}catch(Exception e) {
			e.printStackTrace();
		}
		return odometerListNew;
	}
	
	public void deleteGridData(Long id) throws ApiException{
		try {
			if(RestServiceUtil.getSESSION()!=null) {
			
				RestServiceUtil.getInstance().getClient().getHeartbeatApi().deleteOdometer(id);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<DeviceOdometer> searchOdometerGridData(String entityId, String filter,String startDate, String endDate) throws ApiException{
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
		}catch(Exception e) {
			e.printStackTrace();
		}
		return odometerListSearch;
	}
	
	public List<HeartbeatOdometer> searchTreeData(String filter) throws ApiException{
		try {
			if(RestServiceUtil.getSESSION()!=null) {
			
				List<HeartbeatOdometer> odometerList = null;/*RestServiceUtil.getInstance().getClient().getAuditUserLogApi().searchLogs(null, null, filter, 
						null, null, 1, 20);*/
				//RestServiceUtil.getInstance().getClient().getAuditUserLogApi().searchLogs(userId, entityId, filter, dateFrom, dateTo, pageNumber, rowsPerPage)
				return odometerList;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<HeartbeatOdometer> searchByDates(String entityId, String filter, String startDate, String endDate) throws ApiException{
		try {
			if(RestServiceUtil.getSESSION()!=null) {
			
				List<HeartbeatOdometer> odometerList = RestServiceUtil.getInstance().getClient().getHeartbeatApi().searchOdometer(entityId, filter, 
						startDate, endDate, null, null);
				return odometerList;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
