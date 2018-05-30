package com.luretechnologies.tms.backend.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luretechnologies.tms.backend.data.entity.Alert;
import com.luretechnologies.tms.backend.data.entity.Debug;
import com.vaadin.data.provider.ListDataProvider;

@Service
public class AlertService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final MockAlertService mockAlertService;
	//private PasswordEncoder passwordEncoder;
	//DataGenerator db = new DataGenerator();
	
	@Autowired
	public AlertService(MockAlertService mockRepository) {
		this.mockAlertService = mockRepository;
	}
	private List<Alert> getSortedAlertList(Collection<Alert> unsortedCollection){
		List<Alert> sortedList = unsortedCollection.stream().sorted((o1,o2)->{
			return o1.getType().compareTo(o2.getType());
		}).collect(Collectors.toList());
		return sortedList;
	}
	
	public ListDataProvider<Alert> getListDataProvider(){
		ListDataProvider<Alert> alertDataProvider = new ListDataProvider<>(getSortedAlertList(mockAlertService.getSavedList()));
		return alertDataProvider;
	}
	
	public void removeAlert(Alert alert) {
		mockAlertService.deleteAlert(alert);
	}
	
	public void saveAlert(Alert alert) {
		mockAlertService.save(alert);
	}
}
