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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.luretechnologies.tms.backend.data.entity.HeartBeatHistory;
import com.vaadin.data.provider.Query;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class MockHeartBeatHistory extends CrudService<HeartBeatHistory>{

	private Map<Long,HeartBeatHistory> hbHistoryDirectory = new HashMap<Long, HeartBeatHistory>();
	private LocalDate currentLocalDate = LocalDate.now();
	//private LocalDate currentLocalDate = LocalDate.now();
	private SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss MM/dd/YYYY");
	private List<String> processList = Arrays.asList("Update","DL Started","DL Completed","Parameters Sent","DL Failed"
													,"Parameters Sent", "DL Started", "Update","DL Failed", "DL Completed");
	private List<String> statusList = Arrays.asList("HeartBeat","Remove DL req.","HeartBeat Down", "HeartBeat", "Updated DL Req"
													,"HeartBeat Down", "Remove DL req.", "HeartBeat", "Updated DL Req", "HeartBeat Down");

	@PostConstruct
	public void createInitialDebugs() throws ParseException
	{
		//TODO add the Date time
		for(int index=0; index<10; index++) {
			Date now = new Date();
			String dateTime = dateFormatter.format(now);
			HeartBeatHistory hbHistory = null;
			if(index % 2 ==0) {
				hbHistory = new HeartBeatHistory(dateFormatter1.parse(currentLocalDate.plusDays(index).toString()));
			}else {
				hbHistory = new HeartBeatHistory(dateFormatter1.parse(currentLocalDate.minusDays(index).toString()));
			}
			hbHistory.setDateTime(dateTime);
			hbHistory.setIP("10.10.10.1"+index);
			hbHistory.setProcess(processList.get(index));
			hbHistory.setStatus(statusList.get(index));
			hbHistory.setId(System.currentTimeMillis()+index);
			hbHistoryDirectory.put(hbHistory.getId(), hbHistory);
		}
	}
	
	public void addHBHistory(HeartBeatHistory hbHistory)
	{
		if(!hbHistoryDirectory.containsKey(hbHistory.getId()))
		{
			hbHistoryDirectory.put(hbHistory.getId(), hbHistory);
		}
		else
			throw new RuntimeException("Debug Already present");
		
	}
	
	public void deleteHBHistory(HeartBeatHistory hbHistory)
	{
		if(hbHistoryDirectory.containsValue(hbHistory)) {
			hbHistoryDirectory.remove(hbHistory.getId());
		}
	}

	public void setHBHistory(List<HeartBeatHistory> hbHistoryList) {
		for (HeartBeatHistory hbHistory : hbHistoryList) {
			if(!hbHistoryDirectory.containsKey(hbHistory.getId()))
				hbHistoryDirectory.put(hbHistory.getId(), hbHistory);
		}
	
	}

	@Override
	protected Map<Long, HeartBeatHistory> getRepository() {
		// TODO Auto-generated method stub
		return hbHistoryDirectory;
	}

	@Override
	public Stream<HeartBeatHistory> fetchFromBackEnd(Query<HeartBeatHistory, String> query) {
		// TODO Auto-generated method stub
		return hbHistoryDirectory.values().stream();
	}

	@Override
	protected int sizeInBackEnd(Query<HeartBeatHistory, String> query) {
		// TODO Auto-generated method stub
		return hbHistoryDirectory.size();
	}

	@Override
	protected List<HeartBeatHistory> getSavedList() {
		// TODO Auto-generated method stub
		return hbHistoryDirectory.values().stream().collect(Collectors.toList());
	}
}
