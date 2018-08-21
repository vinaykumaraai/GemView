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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.AuditUserLog;
import com.luretechnologies.client.restlib.service.model.Heartbeat;
import com.luretechnologies.client.restlib.service.model.HeartbeatAudit;
import com.luretechnologies.tms.backend.data.entity.AssetHistory;
import com.luretechnologies.tms.backend.data.entity.Audit;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.luretechnologies.tms.ui.NotificationUtil;
import com.vaadin.data.TreeData;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class AssetControlHistoryService {
private static final Logger assetControlHistoryLogger = Logger.getLogger(AssetControlHistoryService.class);
	@Autowired
	TreeDataNodeService treeDataNodeService;
	
	public TreeData<TreeNode> auditTreeData() {
		try {
			if(RestServiceUtil.getSESSION()!=null) {
			TreeData<TreeNode> treeData = treeDataNodeService.getTreeData();
			return treeData;
			}
		}catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
			}
			assetControlHistoryLogger.error("API Error Occured ",ae);
		} catch (Exception e) {
			assetControlHistoryLogger.error("Error Occured ",e);
		}
		return null;
	}
	
	public List<AssetHistory> historyGridData(String id){
		List<AssetHistory> assetHistoryListNew = new ArrayList<>();
		try {
			if(RestServiceUtil.getSESSION()!=null) {
				List<HeartbeatAudit> historyList = RestServiceUtil.getInstance().getClient().getHeartbeatApi().searchAudits(id, null, null, null, null, null);
				if(historyList!=null && !historyList.isEmpty()) {
					for(HeartbeatAudit heartbeatAudit: historyList) {
						AssetHistory history = new AssetHistory(heartbeatAudit.getId(), heartbeatAudit.getComponent(), heartbeatAudit.getDescription());
						assetHistoryListNew.add(history);
				}
				}
				return assetHistoryListNew;
			}
		}catch (ApiException ae) {
			if(ae.getMessage().contains("EXPIRED HEADER TOKEN RECEIVED")) {
				Notification.show(NotificationUtil.SESSION_EXPIRED,Type.ERROR_MESSAGE);
			}
			assetControlHistoryLogger.error("API Error Occured ",ae);
		} catch (Exception e) {
			assetControlHistoryLogger.error("Error Occured ",e);
		}
		return assetHistoryListNew;
	}
}
