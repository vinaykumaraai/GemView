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
package com.luretechnologies.tms.ui.components;

import com.vaadin.ui.Grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.vaadin.spring.annotation.PrototypeScope;

import com.luretechnologies.tms.backend.data.Downloads;
import com.vaadin.spring.annotation.SpringComponent;

@SuppressWarnings("serial")
@SpringComponent
@PrototypeScope
public class DownloadGrid extends Grid<Downloads>{
	
	List<HashMap<String, String>> rows = new ArrayList<>();
	
	private List<Downloads> getDownloadsList() {
		
		List<Downloads> downloadsList = new ArrayList<>();
		for(int index = 0; index <6; index++) {
			
			Downloads dl = new Downloads();
			dl.setSerialNumber("123"+index);
			dl.setOrganizationName("Entity Holdings "+index);
			dl.setOS("V.123."+index);
			dl.setIncomingIP("1.21.213."+index);
			dl.setDevice("IDTECH "+index);
			dl.setCompletion("1"+index+"%");
			downloadsList.add(dl);
		}
		return downloadsList;
	}
	
	public List<HashMap<String, String>> getFakeData() {
		for (Downloads dl : getDownloadsList()) {
			  HashMap<String, String> fakeBean = new LinkedHashMap<>();
			  fakeBean.put("Serial Number", dl.getSerialNumber());
			  fakeBean.put("Organization Name", dl.getOrganizationName());
			  fakeBean.put("OS", dl.getOS());
			  fakeBean.put("Incoming IP", dl.getIncomingIP());
			  fakeBean.put("Device", dl.getDevice());
			  fakeBean.put("Completion", dl.getCompletion());
			  rows.add(fakeBean);
			}
		
		return rows;
		
	}
	
}
