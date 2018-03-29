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
	
	public List<User> userList;
	
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

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	@Override
	public String toString() {
		return label;
	}	
	 
}
