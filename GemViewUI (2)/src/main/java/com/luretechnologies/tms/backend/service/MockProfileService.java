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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.luretechnologies.tms.backend.data.entity.Debug;
import com.luretechnologies.tms.backend.data.entity.DebugType;
import com.luretechnologies.tms.backend.data.entity.Profile;
import com.luretechnologies.tms.backend.data.entity.ProfileType;
import com.vaadin.data.provider.Query;
import com.vaadin.spring.annotation.SpringComponent;


@SpringComponent
public class MockProfileService extends CrudService<Profile>{
	private Map<Long,Profile> debugDirectory = new HashMap<Long, Profile>();
	@PostConstruct
	public void createInitialProfiles() throws ParseException
	{
		Profile profile = new Profile(ProfileType.FOOD,"Restrurant");
		profile.setId(profile.getId()+2);
		debugDirectory.put(profile.getId(), profile);
		 profile = new Profile(ProfileType.RETAIL,"SuperMarket");
		 profile.setId(profile.getId()+3);
		debugDirectory.put(profile.getId(), profile);
		profile = new Profile(ProfileType.MOTO,"Automobile");
		 profile.setId(profile.getId()+4);
		debugDirectory.put(profile.getId(), profile);
		 
	}
	
	public void addProfile(Profile profile)
	{
		if(!debugDirectory.containsKey(profile.getId()))
		{
			debugDirectory.put(profile.getId(), profile);
		}
		else
			throw new RuntimeException("Debug Already present");
		
	}
	
	public void deleteProfile(Profile profile)
	{
		if(debugDirectory.containsValue(profile)) {
			debugDirectory.remove(profile.getId());
		}
	}

	public void setDebug(List<Profile> profileList) {
		for (Profile profile : profileList) {
			if(!debugDirectory.containsKey(profile.getId()))
				debugDirectory.put(profile.getId(), profile);
		}
	
	}

	@Override
	protected Map<Long, Profile> getRepository() {
		// TODO Auto-generated method stub
		return debugDirectory;
	}

	@Override
	public Stream<Profile> fetchFromBackEnd(Query<Profile, String> query) {
		// TODO Auto-generated method stub
		return debugDirectory.values().stream();
	}

	@Override
	protected int sizeInBackEnd(Query<Profile, String> query) {
		// TODO Auto-generated method stub
		return debugDirectory.size();
	}

	@Override
	protected List<Profile> getSavedList() {
		// TODO Auto-generated method stub
		return debugDirectory.values().stream().collect(Collectors.toList());
	}
}
