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

package com.luretechnologies.tms.backend.data.entity;

public class Permission  {
	
	private String pageName;
	private Boolean read;
	private Boolean update;
	private Boolean delete;
	private Boolean create;
	
	
	public Permission() {
		this.pageName = null;
		this.read = false;
		this.update = false;
		this.delete = false;
		this.create = false;
	}


	public Permission(String pageName, Boolean access, Boolean edit, Boolean delete, Boolean add) {
		super();
		this.pageName = pageName;
		this.read = access;
		this.update = edit;
		this.delete = delete;
		this.create = add;
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
		return read;
	}


	/**
	 * @param access the access to set
	 */
	public void setAccess(Boolean access) {
		this.read = access;
	}


	/**
	 * @return the edit
	 */
	public Boolean getEdit() {
		return update;
	}


	/**
	 * @param edit the edit to set
	 */
	public void setEdit(Boolean edit) {
		this.update = edit;
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
		return create;
	}


	/**
	 * @param add the add to set
	 */
	public void setAdd(Boolean add) {
		this.create = add;
	}


	@Override
	public String toString() {
		return "pageName=" + pageName + ", read=" + read + ", update=" + update + ", delete=" + delete
				+ ", create=" + create;
	}
	
}
