package com.luretechnologies.tms.backend.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.luretechnologies.tms.backend.data.Role;
import com.luretechnologies.tms.backend.data.entity.App;
import com.luretechnologies.tms.backend.data.entity.AppDefaultParam;
import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.StatusType;
import com.luretechnologies.tms.backend.data.entity.User;
import com.vaadin.data.provider.Query;
import com.vaadin.spring.annotation.SpringComponent;


@SpringComponent
public class MockAppService extends CrudService<App>{
	private Map<Long,App> appDirectory = new HashMap<Long, App>();
	@Autowired
	private MockAppDefaultParamService mockAppDefaultParamService;
	@Autowired
	private MockUserService mockUserService;
	@Autowired
	private MockOdometerDeviceService mockDeviceService;
	@PostConstruct
	public void createInitialAlertss() throws ParseException
	{	List<Devices> deviceList = mockDeviceService.getSavedList();
		List<User> userList = mockUserService.getSavedList();
		
		App app = new App("Payment Application","PAYAPP.tgz","1.0",true,mockAppDefaultParamService.getSavedList(),deviceList.get(0),userList.get(0));
		app.setId(app.getId()+2);
		appDirectory.put(app.getId(), app);
		List<AppDefaultParam> listParam1 = mockAppDefaultParamService.getSavedList().subList(1, 2);
		 app = new App("Gift Card Application","GIFTAPP.tgz","2.0",true,listParam1,deviceList.get(1),userList.get(1));
		 app.setId(app.getId()+3);
		appDirectory.put(app.getId(), app);
		List<AppDefaultParam> listParam2 = mockAppDefaultParamService.getSavedList().subList(0, 1);
		 app = new App("Operating System","OSAPP.tgz","1.4",false,listParam2,deviceList.get(2),userList.get(2));
		 app.setId(app.getId()+4);
		appDirectory.put(app.getId(), app);
	}
	
	public void addApp(App app)
	{
		if(!appDirectory.containsKey(app.getId()))
		{
			appDirectory.put(app.getId(), app);
		}
		else
			throw new RuntimeException("Alert Already present");
		
	}
	
	public void deleteApp(App app)
	{
		if(appDirectory.containsValue(app)) {
			appDirectory.remove(app.getId());
		}
	}

	public void setApp(List<App> appList) {
		for (App app : appList) {
			if(!appDirectory.containsKey(app.getId()))
				appDirectory.put(app.getId(), app);
		}
	
	}

	@Override
	protected Map<Long, App> getRepository() {
		// TODO Auto-generated method stub
		return appDirectory;
	}

	@Override
	public Stream<App> fetchFromBackEnd(Query<App, String> query) {
		// TODO Auto-generated method stub
		return appDirectory.values().stream();
	}

	@Override
	protected int sizeInBackEnd(Query<App, String> query) {
		// TODO Auto-generated method stub
		return appDirectory.size();
	}

	@Override
	protected List<App> getSavedList() {
		// TODO Auto-generated method stub
		return appDirectory.values().stream().collect(Collectors.toList());
	}



	

}
