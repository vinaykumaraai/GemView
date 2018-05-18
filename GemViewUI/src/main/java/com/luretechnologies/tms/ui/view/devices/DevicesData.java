package com.luretechnologies.tms.ui.view.devices;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import com.luretechnologies.tms.backend.data.entity.Devices;

public class DevicesData {

	List<Devices> deviceList = new LinkedList<Devices>();
	
	public List<Devices> getDeviceData() {
		Date date = new Date();
		for (int index=0; index < 30; index++) {
			if(index % 2 == 0) {
				Devices device = new Devices();
				device.setActive(true);
				device.setHeartBeat(true);
				device.setDescription("Device Terminal "+index);
				device.setDeviceName("Device "+index);
				device.setManufacturer("Device Entity "+index );
				device.setSerialNumber(RandomStringUtils.random(10, true, true));
				device.setLastSeen(date.toString());
				deviceList.add(device);
				} else {
					Devices device1 = new Devices();
					device1.setActive(false);
					device1.setHeartBeat(false);
					device1.setDescription("Device Terminal "+index);
					device1.setDeviceName("Device "+index);
					device1.setManufacturer("Device Entity "+index );
					device1.setSerialNumber(RandomStringUtils.random(10, true, true));
					device1.setLastSeen(date.toString());
					deviceList.add(device1);
					
				}
		}
		return deviceList;
	}
}
