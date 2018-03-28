package com.luretechnologies.tms.backend.data.entity;




//@Entity(name = "Permission")
public class Permission  {
	
	private String pageName;
	private Boolean access;
	private Boolean edit;
	private Boolean delete;
	private Boolean add;
	
	
	public Permission() {
		this.pageName = "Dashboard";
		this.access = false;
		this.edit = false;
		this.delete = false;
		this.add = false;
	}


	public Permission(String pageName, Boolean access, Boolean edit, Boolean delete, Boolean add) {
		super();
		this.pageName = pageName;
		this.access = access;
		this.edit = edit;
		this.delete = delete;
		this.add = add;
	}


	/**
	 * @return the pageName
	 */
	public String getPageName() {
		return pageName;
	}


	/**
	 * @param pageName the pageName to set
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}


	/**
	 * @return the access
	 */
	public Boolean getAccess() {
		return access;
	}


	/**
	 * @param access the access to set
	 */
	public void setAccess(Boolean access) {
		this.access = access;
	}


	/**
	 * @return the edit
	 */
	public Boolean getEdit() {
		return edit;
	}


	/**
	 * @param edit the edit to set
	 */
	public void setEdit(Boolean edit) {
		this.edit = edit;
	}


	/**
	 * @return the delete
	 */
	public Boolean getDelete() {
		return delete;
	}


	/**
	 * @param delete the delete to set
	 */
	public void setDelete(Boolean delete) {
		this.delete = delete;
	}


	/**
	 * @return the add
	 */
	public Boolean getAdd() {
		return add;
	}


	/**
	 * @param add the add to set
	 */
	public void setAdd(Boolean add) {
		this.add = add;
	}


	
	
}
