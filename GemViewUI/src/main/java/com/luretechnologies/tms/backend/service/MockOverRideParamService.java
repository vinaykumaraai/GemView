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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.luretechnologies.tms.backend.data.entity.AppDefaultParam;
import com.luretechnologies.tms.backend.data.entity.OverRideParameters;
import com.luretechnologies.tms.backend.data.entity.ParameterType;
import com.vaadin.data.provider.Query;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class MockOverRideParamService extends CrudService<OverRideParameters>{
	private Map<Long,OverRideParameters> overRideParamDirectory = new HashMap<Long, OverRideParameters>();
	@PostConstruct
	public void createInitialOverRideParams() throws ParseException
	{
		/*OverRideParameters overRideParams = new OverRideParameters(1L,"properties", "This is for properties", ParameterType.TEXT, "Value 1");
		overRideParams.setId(overRideParams.getId()+2);
		overRideParamDirectory.put(overRideParams.getId(), overRideParams);
		overRideParams = new OverRideParameters(2L,"flag", "This is for Flag", ParameterType.BOOLEAN, "Value 2");
		overRideParams.setId(overRideParams.getId()+3);
		overRideParamDirectory.put(overRideParams.getId(), overRideParams);
		overRideParams = new OverRideParameters(3L,"message", "This is for message", ParameterType.TEXT, "Value 3");
		overRideParams.setId(overRideParams.getId()+4);
		overRideParamDirectory.put(overRideParams.getId(), overRideParams);
		overRideParams = new OverRideParameters(4L,"Header 1", "This is for message 1", ParameterType.TEXT, "Value 4");
		overRideParams.setId(overRideParams.getId()+5);
		overRideParamDirectory.put(overRideParams.getId(), overRideParams);
		overRideParams = new OverRideParameters(5L,"message 1", "This is for message 2", ParameterType.NUMERIC, "Value 5");
		overRideParams.setId(overRideParams.getId()+6);
		overRideParamDirectory.put(overRideParams.getId(), overRideParams);
		overRideParams = new OverRideParameters(6L,"Headrer 2", "This is for message 3", ParameterType.NUMERIC, "Value 6");
		overRideParams.setId(overRideParams.getId()+7);
		overRideParamDirectory.put(overRideParams.getId(), overRideParams);
		overRideParams = new OverRideParameters(7L,"Headrer 3", "This is for message 4", ParameterType.TEXT, "Value 7");
		overRideParams.setId(overRideParams.getId()+8);
		overRideParamDirectory.put(overRideParams.getId(), overRideParams);
		overRideParams = new OverRideParameters(8L,"Header 4", "This is for message 5", ParameterType.NUMERIC, "Value 8");
		overRideParams.setId(overRideParams.getId()+9);
		overRideParamDirectory.put(overRideParams.getId(), overRideParams);
		overRideParams = new OverRideParameters(9L,"Header 5", "This is for message 6", ParameterType.TEXT, "Value 9");
		overRideParams.setId(overRideParams.getId()+10);
		overRideParamDirectory.put(overRideParams.getId(), overRideParams);*/
	}
	
	public void overRideParam(OverRideParameters overRideParam)
	{
		if(!overRideParamDirectory.containsKey(overRideParam.getId()))
		{
			overRideParamDirectory.put(overRideParam.getId(), overRideParam);
		}
		else
			throw new RuntimeException("Parameter Already present");
		
	}
	
	public void deleteOverRideParam(OverRideParameters overRideParam)
	{
		if(overRideParamDirectory.containsValue(overRideParam)) {
			overRideParamDirectory.remove(overRideParam.getId());
		}
	}

	public void setApp(List<OverRideParameters> overRideParamList) {
		for (OverRideParameters overRideParam : overRideParamList) {
			if(!overRideParamDirectory.containsKey(overRideParam.getId()))
				overRideParamDirectory.put(overRideParam.getId(), overRideParam);
		}
	
	}

	@Override
	protected Map<Long, OverRideParameters> getRepository() {
		// TODO Auto-generated method stub
		return overRideParamDirectory;
	}

	@Override
	public Stream<OverRideParameters> fetchFromBackEnd(Query<OverRideParameters, String> query) {
		// TODO Auto-generated method stub
		return overRideParamDirectory.values().stream();
	}

	@Override
	protected int sizeInBackEnd(Query<OverRideParameters, String> query) {
		// TODO Auto-generated method stub
		return overRideParamDirectory.size();
	}

	@Override
	protected List<OverRideParameters> getSavedList() {
		// TODO Auto-generated method stub
		return overRideParamDirectory.values().stream().collect(Collectors.toList());
	}


}
