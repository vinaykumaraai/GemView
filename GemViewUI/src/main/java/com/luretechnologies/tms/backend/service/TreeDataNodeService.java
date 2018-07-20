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

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.service.model.Entity;
import com.luretechnologies.client.restlib.service.model.Terminal;
import com.luretechnologies.tms.backend.data.entity.Node;
import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.luretechnologies.tms.backend.rest.util.RestServiceUtil;
import com.vaadin.data.TreeData;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@Service
public class TreeDataNodeService {
	
	public TreeData<TreeNode> getTreeData() throws ApiException{
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
		}catch(Exception e) {
			e.printStackTrace();
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
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private List<TreeNode> getChildNodes(List<Entity> entityList){
		List<TreeNode> nodeChildList = new ArrayList<>();
		for(Entity entity : entityList) {
			TreeNode node = new TreeNode(entity.getName(), entity.getId(), entity.getType(), entity.getEntityId(), entity.getDescription()
					,true);
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

	public void copyTreeNode(TreeNode entity) {
		try {
			if(RestServiceUtil.getSESSION()!=null) {
				Entity parentEntity = RestServiceUtil.getInstance().getClient().getEntityApi().getEntityHierarchy(entity.getId());
				RestServiceUtil.getInstance().getClient().getEntityApi().copyEntity(entity.getEntityId(), parentEntity.getId());
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public void pasteTreeNode(TreeNode entity, TreeNode parentEntity) {
		try {
			if(RestServiceUtil.getSESSION()!=null) {
				RestServiceUtil.getInstance().getClient().getEntityApi().moveEntity(entity.getId(), parentEntity.getId());
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
