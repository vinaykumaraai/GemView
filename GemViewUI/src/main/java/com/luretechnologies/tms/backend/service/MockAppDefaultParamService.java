package com.luretechnologies.tms.backend.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.luretechnologies.tms.backend.data.entity.Alert;
import com.luretechnologies.tms.backend.data.entity.AlertType;
import com.luretechnologies.tms.backend.data.entity.AppMock;
import com.luretechnologies.tms.backend.data.entity.AppDefaultParam;
import com.luretechnologies.tms.backend.data.entity.ParameterType;
import com.vaadin.data.provider.Query;
import com.vaadin.spring.annotation.SpringComponent;


@SpringComponent
public class MockAppDefaultParamService extends CrudService<AppDefaultParam>{
	private Map<Long,AppDefaultParam> appDefaultParamDirectory = new HashMap<Long, AppDefaultParam>();
	@PostConstruct
	public void createInitialAppParams() throws ParseException
	{
		AppDefaultParam defaultParams = new AppDefaultParam("properties", "This is for properties", ParameterType.TEXT, true);
		defaultParams.setId(defaultParams.getId()+2);
		appDefaultParamDirectory.put(defaultParams.getId(), defaultParams);
		defaultParams = new AppDefaultParam("flag", "This is for Flag", ParameterType.BOOLEAN, true);
		defaultParams.setId(defaultParams.getId()+3);
		appDefaultParamDirectory.put(defaultParams.getId(), defaultParams);
		defaultParams = new AppDefaultParam("message", "This is for message", ParameterType.TEXT, true);
		defaultParams.setId(defaultParams.getId()+4);
		appDefaultParamDirectory.put(defaultParams.getId(), defaultParams);
	}
	
	public void addAppDefaultParam(AppDefaultParam appDefaultParam)
	{
		if(!appDefaultParamDirectory.containsKey(appDefaultParam.getId()))
		{
			appDefaultParamDirectory.put(appDefaultParam.getId(), appDefaultParam);
		}
		else
			throw new RuntimeException("Parameter Already present");
		
	}
	
	public void deleteAppDefaultParam(AppDefaultParam appDefaultParam)
	{
		if(appDefaultParamDirectory.containsValue(appDefaultParam)) {
			appDefaultParamDirectory.remove(appDefaultParam.getId());
		}
	}

	public void setApp(List<AppDefaultParam> appDefaultParamList) {
		for (AppDefaultParam appDefaultParam : appDefaultParamList) {
			if(!appDefaultParamDirectory.containsKey(appDefaultParam.getId()))
				appDefaultParamDirectory.put(appDefaultParam.getId(), appDefaultParam);
		}
	
	}

	@Override
	protected Map<Long, AppDefaultParam> getRepository() {
		// TODO Auto-generated method stub
		return appDefaultParamDirectory;
	}

	@Override
	public Stream<AppDefaultParam> fetchFromBackEnd(Query<AppDefaultParam, String> query) {
		// TODO Auto-generated method stub
		return appDefaultParamDirectory.values().stream();
	}

	@Override
	protected int sizeInBackEnd(Query<AppDefaultParam, String> query) {
		// TODO Auto-generated method stub
		return appDefaultParamDirectory.size();
	}

	@Override
	protected List<AppDefaultParam> getSavedList() {
		// TODO Auto-generated method stub
		return appDefaultParamDirectory.values().stream().collect(Collectors.toList());
	}



	

}
