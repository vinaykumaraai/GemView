package com.luretechnologies.tms.backend.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.luretechnologies.tms.backend.data.entity.Devices;
import com.vaadin.data.provider.ListDataProvider;

@Service
public class OdometerDeviceService {
	
	private final MockOdometerDeviceService mockOdometerDeviceService;
	//private PasswordEncoder passwordEncoder;
	//DataGenerator db = new DataGenerator();
	
	@Autowired
	public OdometerDeviceService(MockOdometerDeviceService mockRepository) {
		this.mockOdometerDeviceService = mockRepository;
	}
	@SuppressWarnings("unused")
	private List<Devices> getSortedUserList(Collection<Devices> unsortedCollection){
		List<Devices> sortedList = unsortedCollection.stream().sorted((o1,o2)->{
			return o1.getStatusType().compareTo(o2.getStatusType());
		}).collect(Collectors.toList());
		return sortedList;
	}
	
	public ListDataProvider<Devices> getListDataProvider(){
		ListDataProvider<Devices> odometerDeviceDataProvider = new ListDataProvider<>(getSortedUserList(mockOdometerDeviceService.getSavedList()));
		return odometerDeviceDataProvider;
	}
	
	public void removeOdometerDevice(Devices device) {
		mockOdometerDeviceService.deleteDeviceOdometer(device);
	}


}
