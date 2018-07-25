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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.HeartBeatHistory;

@Service
public class HeartBeatDeviceService {
	
	private final HeartBeatHistoryService heartBeatHistoryService;
	
	@Autowired
	public HeartBeatDeviceService(HeartBeatHistoryService heartBeatHistoryService) {
		this.heartBeatHistoryService=heartBeatHistoryService;
	}
	
	public List<Devices> getDeviceData() {
		List<Devices> deviceList = new ArrayList<>();
		List<HeartBeatHistory> hbHistoryListActive = heartBeatHistoryService.getHBHistoryList();
		List<HeartBeatHistory> hbHistoryListFail = heartBeatHistoryService.getHBHistoryList();
		Collections.reverse(hbHistoryListFail);
		Date date = new Date();
		for (int index=1; index <=15; index++) {
			if(index % 2 == 0) {
				Devices device = new Devices();
				device.setActive(true);
				device.setHeartBeat(true);
				device.setDescription("Device Terminal "+index);
				device.setDeviceName("Device "+index);
				device.setManufacturer("Device Entity "+index );
				device.setSerialNumber(RandomStringUtils.random(10, true, true));
				device.setFrequency("12"+index+" seconds");
				device.setLastSeen(date.toString());
				device.setHbHistoryList(hbHistoryListActive);
				deviceList.add(device);
				} else {
					Devices device1 = new Devices();
					device1.setActive(false);
					device1.setHeartBeat(false);
					device1.setDescription("Device Terminal "+index);
					device1.setDeviceName("Device "+index);
					device1.setManufacturer("Device Entity "+index );
					device1.setSerialNumber(RandomStringUtils.random(10, true, true));
					device1.setFrequency("12"+index+" seconds");
					device1.setLastSeen(date.toString());
					device1.setHbHistoryList( hbHistoryListFail);
					deviceList.add(device1);
					
				}
		}
		return deviceList;
	}
	
	public List<Devices> getFilteredHeartBeatDevicesByNameOrDescription(String filterTextInLower,List<Devices> devicesList){
		if(StringUtils.isNotEmpty(filterTextInLower)) {
			return devicesList.stream().filter(devices -> {
				return devices.getDeviceName().toLowerCase().contains(filterTextInLower) || devices.getDescription().toLowerCase().contains(filterTextInLower);
				}).collect(Collectors.toList());
		}
		System.out.println(devicesList);
		return devicesList;
	}
}
