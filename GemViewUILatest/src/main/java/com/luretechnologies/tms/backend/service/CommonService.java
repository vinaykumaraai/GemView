package com.luretechnologies.tms.backend.service;

import com.luretechnologies.tms.backend.data.entity.TreeNode;
import com.vaadin.data.TreeData;

/**
 * 
 * @author Vinay
 *
 */

public abstract class CommonService {
	public abstract TreeData<TreeNode> getTreeData();
	public abstract void createEntity(TreeNode parentNode, TreeNode treeNewNode);
	public abstract void updateEntity(TreeNode node, Long frequency);
	public abstract void deleteEntity(TreeNode node);
}
