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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.StatusType;
import com.vaadin.data.provider.Query;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class MockOdometerDeviceService  extends CrudService<Devices>{

	private Map<Long,Devices> deviceDirectory = new HashMap<Long, Devices>();
	private LocalDate currentLocalDate = LocalDate.now();
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	
	@PostConstruct
	public void createInitialOdometerDeviceData() throws ParseException
	{
		//TODO add the Date time
		Devices device = new Devices(StatusType.CARDSWIPES,"Cards Swiped Performed",25);
		device.setDeviceDate(dateFormatter.parse(currentLocalDate.toString()));
		device.setId(System.currentTimeMillis()+2);
		deviceDirectory.put(device.getId(), device);
		
		device = new Devices(StatusType.INVALIDCARDSWIPES, "Invalid Card Swipes", 2);
		device.setDeviceDate(dateFormatter.parse(currentLocalDate.plusDays(2).toString()));
		device.setId(System.currentTimeMillis()+3);
		deviceDirectory.put(device.getId(), device);
		
		device = new Devices(StatusType.REBOOT, "Times Unit Has been rebooted", 21);
		device.setDeviceDate(dateFormatter.parse(currentLocalDate.plusDays(4).toString()));
		device.setId(System.currentTimeMillis()+4);
		deviceDirectory.put(device.getId(), device);
		
		device = new Devices(StatusType.TRANSACTIONS, "Transactions performed from the unit", 209);
		device.setDeviceDate(dateFormatter.parse(currentLocalDate.plusDays(3).toString()));
		device.setId(System.currentTimeMillis()+5);
		deviceDirectory.put(device.getId(), device);
		
		device = new Devices(StatusType.INVALIDCARDSWIPES, "Invalid Card Swipes", 8);
		device.setDeviceDate(dateFormatter.parse(currentLocalDate.plusDays(3).toString()));
		device.setId(System.currentTimeMillis()+6);
		deviceDirectory.put(device.getId(), device);
		
		device = new Devices(StatusType.REBOOT, "Times Unit Has been rebooted", 44);
		device.setDeviceDate(dateFormatter.parse(currentLocalDate.minusDays(2).toString()));
		device.setId(System.currentTimeMillis()+7);
		deviceDirectory.put(device.getId(), device);
		
		device = new Devices(StatusType.CARDSWIPES, "Cards Swiped Performed", 89);
		device.setDeviceDate(dateFormatter.parse(currentLocalDate.minusDays(4).toString()));
		device.setId(System.currentTimeMillis()+8);
		deviceDirectory.put(device.getId(), device);
		
		device = new Devices(StatusType.TRANSACTIONS, "Transactions performed from the unit", 566);
		device.setDeviceDate(dateFormatter.parse(currentLocalDate.minusDays(3).toString()));
		device.setId(System.currentTimeMillis()+9);
		deviceDirectory.put(device.getId(), device);
		
	}
	
	public void addDeviceOdometer(Devices device)
	{
		if(!deviceDirectory.containsKey(device.getId()))
		{
			deviceDirectory.put(device.getId(), device);
		}
		else
			throw new RuntimeException("device Already present");
		
	}
	
	public void deleteDeviceOdometer(Devices device)
	{
		if(deviceDirectory.containsValue(device)) {
			deviceDirectory.remove(device.getId());
		}
	}

	public void setDeviceOdometer(List<Devices> deviceList) {
		for (Devices device : deviceList) {
			if(!deviceDirectory.containsKey(device.getId()))
				deviceDirectory.put(device.getId(), device);
		}
	
	}

	@Override
	protected Map<Long, Devices> getRepository() {
		// TODO Auto-generated method stub
		return deviceDirectory;
	}

	@Override
	public Stream<Devices> fetchFromBackEnd(Query<Devices, String> query) {
		// TODO Auto-generated method stub
		return deviceDirectory.values().stream();
	}

	@Override
	protected int sizeInBackEnd(Query<Devices, String> query) {
		// TODO Auto-generated method stub
		return deviceDirectory.size();
	}

	@Override
	protected List<Devices> getSavedList() {
		// TODO Auto-generated method stub
		return deviceDirectory.values().stream().collect(Collectors.toList());
	}

}
