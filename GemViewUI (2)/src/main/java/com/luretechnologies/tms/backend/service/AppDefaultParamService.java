package com.luretechnologies.tms.backend.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luretechnologies.tms.backend.data.entity.Alert;
import com.luretechnologies.tms.backend.data.entity.App;
import com.luretechnologies.tms.backend.data.entity.AppDefaultParam;
import com.vaadin.data.provider.ListDataProvider;

@Service
public class AppDefaultParamService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final MockAppDefaultParamService mockAppDefaultParamService;
	
	@Autowired
	public AppDefaultParamService(MockAppDefaultParamService mockRepository) {
		this.mockAppDefaultParamService = mockRepository;
	}
	private List<AppDefaultParam> getSortedAppList(Collection<AppDefaultParam> unsortedCollection){
		List<AppDefaultParam> sortedList = unsortedCollection.stream().sorted((o1,o2)->{
			return o1.getParameter().compareTo(o2.getParameter());
		}).collect(Collectors.toList());
		return sortedList;
	}
	
	public ListDataProvider<AppDefaultParam> getListDataProvider(){
		ListDataProvider<AppDefaultParam> appDataProvider = new ListDataProvider<>(getSortedAppList(mockAppDefaultParamService.getSavedList()));
		return appDataProvider;
	}
	
	public void removeAppDefaultParam(AppDefaultParam appDefaultParam) {
		mockAppDefaultParamService.deleteAppDefaultParam(appDefaultParam);
	}
	
	public void saveAppDefaultParam(AppDefaultParam appDefaultParam) {
		mockAppDefaultParamService.save(appDefaultParam);
	}
}
