/**
 * 
 */
package com.luretechnologies.tms.backend.data.entity;

import java.util.List;

/**
 * @author Vinay
 *
 */
public class Node {

	public NodeLevel level;
	
	public String label;
	
	public List<? extends AbstractEntity> entityList;
	
	public NodeLevel getLevel() {
		return level;
	}

	public void setLevel(NodeLevel level) {
		this.level = level;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<? extends AbstractEntity> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<? extends AbstractEntity> entityList) {
		this.entityList = entityList;
	}

	@Override
	public String toString() {
		return label;
	}	
	 
}
