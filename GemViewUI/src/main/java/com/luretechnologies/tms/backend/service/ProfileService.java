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

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luretechnologies.tms.backend.data.entity.Profile;
import com.vaadin.data.provider.ListDataProvider;

@Service
public class ProfileService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final MockProfileService mockProfileService;
	@Autowired
	public ProfileService(MockProfileService mockRepository) {
		this.mockProfileService = mockRepository;
	}
	private List<Profile> getSortedUserList(Collection<Profile> unsortedCollection){
		List<Profile> sortedList = unsortedCollection.stream().sorted((o1,o2)->{
			return o1.getType().compareTo(o2.getType());
		}).collect(Collectors.toList());
		return sortedList;
	}
	
	public ListDataProvider<Profile> getListDataProvider(){
		//ListDataProvider<Profile> debugDataProvider = new ListDataProvider<>(getSortedUserList(mockProfileService.getSavedList()));
		ListDataProvider<Profile> debugDataProvider = new ListDataProvider<>(mockProfileService.getSavedList());
		return debugDataProvider;
	}
	
	public void removeProfile(Profile profile) {
		mockProfileService.deleteProfile(profile);
	}
	
	public void saveProfile(Profile profile) {
		mockProfileService.save(profile);
	}
}
