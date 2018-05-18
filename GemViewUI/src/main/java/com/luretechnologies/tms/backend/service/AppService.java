package com.luretechnologies.tms.backend.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luretechnologies.tms.backend.data.entity.Alert;
import com.luretechnologies.tms.backend.data.entity.App;
import com.vaadin.data.provider.ListDataProvider;

@Service
public class AppService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final MockAppService mockAppService;
	
	@Autowired
	public AppService(MockAppService mockRepository) {
		this.mockAppService = mockRepository;
	}
	private List<App> getSortedAppList(Collection<App> unsortedCollection){
		List<App> sortedList = unsortedCollection.stream().sorted((o1,o2)->{
			return o1.getPackageName().compareTo(o2.getPackageName());
		}).collect(Collectors.toList());
		return sortedList;
	}
	
	public ListDataProvider<App> getListDataProvider(){
		ListDataProvider<App> appDataProvider = new ListDataProvider<>(getSortedAppList(mockAppService.getSavedList()));
		return appDataProvider;
	}
	
	public void removeApp(App app) {
		mockAppService.deleteApp(app);
	}
	
	public void saveApp(App app) {
		mockAppService.save(app);
	}
}
