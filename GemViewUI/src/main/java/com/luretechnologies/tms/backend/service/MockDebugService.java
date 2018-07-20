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
import com.vaadin.data.provider.Query;
import com.vaadin.spring.annotation.SpringComponent;


@SpringComponent
public class MockDebugService extends CrudService<Debug>{
	private Map<Long,Debug> debugDirectory = new HashMap<Long, Debug>();
	private Date now = new Date();
	private LocalDate currentLocalDate = LocalDate.now();
	private LocalDateTime currentLocalDateTime = LocalDateTime.now();
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat sdfDate = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	private static final DateTimeFormatter  dateFormatter1 = DateTimeFormatter.ofPattern("MM-dd-YYYY HH:mm:ss");
	@PostConstruct
	public void createInitialDebugs() throws ParseException
	{
		//TODO add the Date time
		Debug debug = new Debug(DebugType.ERROR,"There is an error 1",dateFormatter.parse(currentLocalDate.toString()), dateFormatter1.format(currentLocalDateTime),"Error",true,false);
		debug.setId(debug.getId()+2);
		debugDirectory.put(debug.getId(), debug);
		 debug = new Debug(DebugType.WARN,"WARNNNNN 1",dateFormatter.parse(currentLocalDate.plusDays(2).toString()), dateFormatter1.format(currentLocalDateTime.plusDays(2)),"Warnning",true,false);
		 debug.setId(debug.getId()+3);
		debugDirectory.put(debug.getId(), debug);
		 debug = new Debug(DebugType.INFO,"It is an Info 1",dateFormatter.parse(currentLocalDate.plusDays(4).toString()),dateFormatter1.format(currentLocalDateTime.plusDays(4)),"Information",false,true);
		 debug.setId(debug.getId()+4);
		debugDirectory.put(debug.getId(), debug);
		debug = new Debug(DebugType.ERROR,"Again is an error 2",dateFormatter.parse(currentLocalDate.plusDays(3).toString()),dateFormatter1.format(currentLocalDateTime.plusDays(3)),"Error",true,true);
		debug.setId(debug.getId()+5);
		debugDirectory.put(debug.getId(), debug);
		debug = new Debug(DebugType.INFO,"It is an Info 2",dateFormatter.parse(currentLocalDate.plusDays(3).toString()),dateFormatter1.format(currentLocalDateTime.plusDays(3)),"Information", true,true);
		debug.setId(debug.getId()+6);
		debugDirectory.put(debug.getId(), debug);
		 debug = new Debug(DebugType.WARN,"WARNNNNN 2",dateFormatter.parse(currentLocalDate.minusDays(2).toString()),dateFormatter1.format(currentLocalDateTime.minusDays(2)),"Warnning",false,true);
		 debug.setId(debug.getId()+7);
		debugDirectory.put(debug.getId(), debug);
		 debug = new Debug(DebugType.INFO,"It is an Info 3",dateFormatter.parse(currentLocalDate.minusDays(4).toString()),dateFormatter1.format(currentLocalDateTime.minusDays(4)),"Information",false,false);
		 debug.setId(debug.getId()+8);
		debugDirectory.put(debug.getId(), debug);
		debug = new Debug(DebugType.ERROR,"Again is an error 3",dateFormatter.parse(currentLocalDate.minusDays(3).toString()),dateFormatter1.format(currentLocalDateTime.minusDays(3)),"Error",true,true);
		debug.setId(debug.getId()+9);
		debugDirectory.put(debug.getId(), debug);
		for(int index=0; index<40; index++) {
			debug = new Debug(DebugType.ERROR,"Again is an error"+index, dateFormatter.parse(currentLocalDate.minusDays(3).toString()),dateFormatter1.format(currentLocalDateTime.minusDays(3)),"Error",true,true);
			debug.setId(debug.getId()+index+10);
			debugDirectory.put(debug.getId(), debug);
		}
	}
	
	public void addDebug(Debug debug)
	{
		if(!debugDirectory.containsKey(debug.getId()))
		{
			debugDirectory.put(debug.getId(), debug);
		}
		else
			throw new RuntimeException("Debug Already present");
		
	}
	
	public void deleteDebug(Debug debug)
	{
		if(debugDirectory.containsValue(debug)) {
			debugDirectory.remove(debug.getId());
		}
	}

	public void setDebug(List<Debug> debugList) {
		for (Debug debug : debugList) {
			if(!debugDirectory.containsKey(debug.getId()))
				debugDirectory.put(debug.getId(), debug);
		}
	
	}

	@Override
	protected Map<Long, Debug> getRepository() {
		// TODO Auto-generated method stub
		return debugDirectory;
	}

	@Override
	public Stream<Debug> fetchFromBackEnd(Query<Debug, String> query) {
		// TODO Auto-generated method stub
		return debugDirectory.values().stream();
	}

	@Override
	protected int sizeInBackEnd(Query<Debug, String> query) {
		// TODO Auto-generated method stub
		return debugDirectory.size();
	}

	@Override
	protected List<Debug> getSavedList() {
		// TODO Auto-generated method stub
		return debugDirectory.values().stream().collect(Collectors.toList());
	}
}
