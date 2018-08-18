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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.AlertAction;
import com.luretechnologies.client.restlib.service.model.AuditUserLog;
import com.luretechnologies.client.restlib.service.model.DebugItem;
import com.luretechnologies.client.restlib.service.model.Heartbeat;
import com.luretechnologies.client.restlib.service.model.HeartbeatAlert;
import com.luretechnologies.client.restlib.service.model.HeartbeatAudit;
import com.luretechnologies.client.restlib.service.model.HeartbeatOdometer;
import com.luretechnologies.tms.backend.data.entity.Alert;
import com.luretechnologies.tms.backend.data.entity.AssetHistory;
import com.luretechnologies.tms.backend.data.entity.Audit;
import com.luretechnologies.tms.backend.data.entity.DebugItems;
import com.luretechnologies.tms.backend.data.entity.DeviceOdometer;
import com.luretechnologies.tms.backend.data.entity.TerminalClient;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.vaadin.data.TreeData;

@Service
public class AssetControlService {

	@Autowired
	public TreeDataNodeService treeDataNodeService;
	
	public TreeData<TreeNode> getTreeData() throws ApiException{
		try {
			if(RestServiceUtil.getSESSION()!=null) {
			TreeData<TreeNode> treeData = treeDataNodeService.getTreeData();
			return treeData;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<AssetHistory> getHistoryGridData(String id) throws ApiException{
		List<AssetHistory> assetHistoryListNew = new ArrayList<>();
		try {
			if(RestServiceUtil.getSESSION()!=null) {			
				List<HeartbeatAudit> historyList = RestServiceUtil.getInstance().getClient().getHeartbeatApi().searchAudits(id, null, null, null, null, null);
				if(historyList!=null && !historyList.isEmpty()) {
					for(HeartbeatAudit heartbeatAudit: historyList) {
						AssetHistory history = new AssetHistory(heartbeatAudit.getId(), heartbeatAudit.getComponent(), heartbeatAudit.getDescription());
						assetHistoryListNew.add(history);
						//RestServiceUtil.getInstance().getClient().getAppProfileApi().		
						}
				}
				return assetHistoryListNew;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return assetHistoryListNew;
	}
	
	public void deleteHistoryGridData(Long id) throws ApiException{
		try {
			if(RestServiceUtil.getSESSION()!=null) {
			
				RestServiceUtil.getInstance().getClient().getHeartbeatApi().deleteAudit(id);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<AssetHistory> searchHistoryGridDataByText(String entityId, String filter, String startDate, String endDate) throws ApiException{
		List<AssetHistory> historyListSearch = new ArrayList<>();
		try {
			if(RestServiceUtil.getSESSION()!=null) {
			
				List<HeartbeatAudit> searchList = RestServiceUtil.getInstance().getClient().getHeartbeatApi().searchAudits(entityId, filter, 
							startDate, endDate, null, null);
				if(searchList!=null && !searchList.isEmpty()) {
					for(HeartbeatAudit heartbeatAudit: searchList) {
						AssetHistory assetHistory = new AssetHistory(heartbeatAudit.getId(), heartbeatAudit.getComponent(), heartbeatAudit.getDescription());
						historyListSearch.add(assetHistory);
					}
				}
				return historyListSearch;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return historyListSearch;
	}
	
	public List<HeartbeatAudit> searchTreeData(String filter) throws ApiException{
		try {
			if(RestServiceUtil.getSESSION()!=null) {
			
				List<HeartbeatAudit> odometerList = null;/*RestServiceUtil.getInstance().getClient().getAuditUserLogApi().searchLogs(null, null, filter, 
						null, null, 1, 20);*/
				//RestServiceUtil.getInstance().getClient().getAuditUserLogApi().searchLogs(userId, entityId, filter, dateFrom, dateTo, pageNumber, rowsPerPage)
				return odometerList;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<HeartbeatAudit> searchHistoryByDates(String entityId, String filter, String startDate, String endDate) throws ApiException{
		try {
			if(RestServiceUtil.getSESSION()!=null) {
			if(filter!=null && !filter.isEmpty()) {
				List<HeartbeatAudit> historySearchList = RestServiceUtil.getInstance().getClient().getHeartbeatApi().searchAudits(entityId, filter, 
						startDate, endDate, null, null);
				return historySearchList;
			} else {
				List<HeartbeatAudit> historySearchList = RestServiceUtil.getInstance().getClient().getHeartbeatApi().searchAudits(entityId, null, 
						startDate, endDate, null, null);
				return historySearchList;
			}
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Alert> getAlertGridData(String id){
		List<Alert> assetAlertListNew = new ArrayList<>();
		try {
			if(RestServiceUtil.getSESSION()!=null) {			
				List<AlertAction> historyList = RestServiceUtil.getInstance().getClient().getAlertActionApi().search(id, null, null, null, null, null);
					for(AlertAction alertAction: historyList) {
						Alert alert = new Alert(alertAction.getId(), alertAction.getLabel(), alertAction.getName(), 
								alertAction.getDescription(), alertAction.getActive(), alertAction.getEmail());
						assetAlertListNew.add(alert);	
						}
				}
				return assetAlertListNew;
			}
		catch(Exception e) {
			e.printStackTrace();
		}
		return assetAlertListNew;
	}
	
	public Alert createAlertCommands(String entityId, AlertAction alertAction){
		Alert newAlert = new Alert();
		try {
			if(RestServiceUtil.getSESSION()!=null) {			
				AlertAction newAlertAction = RestServiceUtil.getInstance().getClient().getAlertActionApi().create(entityId,alertAction);
					
				newAlert = new Alert(newAlertAction.getId(), newAlertAction.getLabel(), newAlertAction.getName(), 
						newAlertAction.getDescription(), newAlertAction.getAvailable(), newAlertAction.getEmail());
						
				}
				return newAlert;
			}
		catch(Exception e) {
			e.printStackTrace();
		}
		return newAlert;
	}
	
	public Alert updateAlertCommands(AlertAction alertAction){
		Alert newAlert = new Alert();
		try {
			if(RestServiceUtil.getSESSION()!=null) {	
				AlertAction newAlertAction = RestServiceUtil.getInstance().getClient().getAlertActionApi().update(alertAction);
					
				newAlert = new Alert(newAlertAction.getId(), newAlertAction.getLabel(), newAlertAction.getName(), 
						newAlertAction.getDescription(), newAlertAction.getAvailable(), newAlertAction.getEmail());
						
				}
				return newAlert;
			}
		catch(Exception e) {
			e.printStackTrace();
		}
		return newAlert;
	}
	
	public void deleteAlertCommands(Long id){
		try {
			if(RestServiceUtil.getSESSION()!=null) {	
				RestServiceUtil.getInstance().getClient().getAlertActionApi().delete(id);
				}
				
			}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<DebugItems> getDeviceDebugList(String entityId, String type){
		List<DebugItems> debugItemsList = new ArrayList<>();
		try {
			if(RestServiceUtil.getSESSION()!=null) {
				if(type.equalsIgnoreCase("Terminal")) {
				List<DebugItem> debugItemList = RestServiceUtil.getInstance().getClient().getDebugApi().searchDebugItems(entityId, null, null, null, null, null);
				for(DebugItem debugItem : debugItemList) {
					DebugItems debugItems = new DebugItems(debugItem.getId(), getType(debugItem.getLevel()), debugItem.getMessage());
					debugItemsList.add(debugItems);
				}
				return debugItemsList;
				}
			}
				
			}
		catch(Exception e) {
			e.printStackTrace();
		}
		return debugItemsList;
	}
	
	public String getType(Integer level){
		switch(level) {
		case 3:
			return "info";
		case 6:
			return "warn";
		case 7:
			return "error";
		default:
			return "";
		}
	}
	
	public List<DebugItems> getDeviceDebugBySearch(String entityId, String filter, String startDate, String endDate){
		List<DebugItems> debugItemsList = new ArrayList<>();
		try {
			if(RestServiceUtil.getSESSION()!=null) {
				List<DebugItem> debugItemList = RestServiceUtil.getInstance().getClient().getDebugApi().searchDebugItems(entityId, filter, startDate, endDate, null, null);
				for(DebugItem debugItem : debugItemList) {
					DebugItems debugItems = new DebugItems(debugItem.getId(), getType(debugItem.getLevel()), debugItem.getMessage());
					debugItemsList.add(debugItems);
				}
				return debugItemsList;
				}
			}
		catch(Exception e) {
			e.printStackTrace();
		}
		return debugItemsList;
	}
	
	public List<DebugItem> searchDeviceDebugByDates(String entityId, String filter, String startDate, String endDate) throws ApiException{
		try {
			if(RestServiceUtil.getSESSION()!=null) {
			if(filter!=null && !filter.isEmpty()) {
				List<DebugItem> deviceDebugSearchList = RestServiceUtil.getInstance().getClient().getDebugApi().searchDebugItems(entityId, filter, 
						startDate, endDate, null, null);
				return deviceDebugSearchList;
			} else {
				List<DebugItem> deviceDebugSearchList = RestServiceUtil.getInstance().getClient().getDebugApi().searchDebugItems(entityId, null, 
						startDate, endDate, null, null);
				return deviceDebugSearchList;
			}
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteDeviceDebugItem(Long id) {
		try {
			if(RestServiceUtil.getSESSION()!=null) {
				RestServiceUtil.getInstance().getClient().getDebugApi().deleteDebugItem(id);;
			}
				
			}
		catch(Exception e) {
			e.printStackTrace();
			
		}
	}
	
	public void saveDebugAndDuration(String entityId, boolean value, String date) {
		try {
			if(RestServiceUtil.getSESSION()!=null) {
				
				com.luretechnologies.client.restlib.service.model.Terminal terminal = RestServiceUtil.getInstance().getClient().getTerminalApi().getTerminal(entityId);
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            Date tempHelp = format.parse(date +" 00:00:00");
				terminal.setAvailable(value);
				terminal.setDebugExpirationDate(tempHelp);
				RestServiceUtil.getInstance().getClient().getTerminalApi().updateTerminal(entityId, terminal);
			}
				
			}
		catch(Exception e) {
			e.printStackTrace();
			
		}
	}
	
	public TerminalClient getTerminal(String entityId){
		try {
			if(RestServiceUtil.getSESSION()!=null) {
				com.luretechnologies.client.restlib.service.model.Terminal terminal = RestServiceUtil.getInstance().getClient().getTerminalApi().getTerminal(entityId);
				if(terminal!=null) {
					String date = new SimpleDateFormat("yyyy-MM-dd").format(terminal.getDebugExpirationDate());
					TerminalClient mockTerminal = new TerminalClient(terminal.getSerialNumber(), terminal.getAvailable(), date);
					return mockTerminal;
				}
			}
				
			}
		catch(Exception e) {
			e.printStackTrace();
			
		}
		return null;
	}
}