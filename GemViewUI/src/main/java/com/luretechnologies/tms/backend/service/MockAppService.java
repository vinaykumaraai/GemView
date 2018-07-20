package com.luretechnologies.tms.backend.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.luretechnologies.tms.backend.data.Role;
import com.luretechnologies.tms.backend.data.entity.AppMock;
import com.luretechnologies.tms.backend.data.entity.AppDefaultParam;
import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.Profile;
import com.luretechnologies.tms.backend.data.entity.StatusType;
import com.luretechnologies.tms.backend.data.entity.User;
import com.vaadin.data.provider.Query;
import com.vaadin.spring.annotation.SpringComponent;


@SpringComponent
public class MockAppService{
	/*private Map<Long,AppMock> appDirectory = new HashMap<Long, AppMock>();
	@Autowired
	private MockAppDefaultParamService mockAppDefaultParamService;
	@Autowired
	private MockUserService mockUserService;
	@Autowired
	private MockOdometerDeviceService mockDeviceService;
	@Autowired
	private MockProfileService mockProfileService;
	@PostConstruct
	public void createInitialAlertss() throws ParseException
	{	List<Devices> deviceList = mockDeviceService.getSavedList();
		//List<User> userList = mockUserService.getSavedList();
		List<Profile> profileList = mockProfileService.getSavedList();
		
		AppMock app = new AppMock("Payment Application","This is Payment Application","1.0",true,mockAppDefaultParamService.getSavedList(),deviceList.get(0),userList.get(0),profileList.get(0));
		app.setId(app.getId()+2);
		appDirectory.put(app.getId(), app);
		//List<AppDefaultParam> listParam1 = mockAppDefaultParamService.getSavedList().subList(1, 2);
		List<AppDefaultParam> listParam1= new ArrayList<>();
		app = new AppMock("Gift Card Application","This is Gift Card","2.0",true,listParam1,deviceList.get(1),userList.get(1),profileList.get(1));
		 app.setId(app.getId()+3);
		appDirectory.put(app.getId(), app);
		//List<AppDefaultParam> listParam2 = mockAppDefaultParamService.getSavedList().subList(0, 1);
		List<AppDefaultParam> listParam2= new ArrayList<>();
		app = new AppMock("Operating System","This is Operating System","1.4",false,listParam2,deviceList.get(2),userList.get(2),profileList.get(0));
		 app.setId(app.getId()+4);
		appDirectory.put(app.getId(), app);
	}
	
	public void addApp(AppMock app)
	{
		if(!appDirectory.containsKey(app.getId()))
		{
			appDirectory.put(app.getId(), app);
		}
		else
			throw new RuntimeException("Alert Already present");
		
	}
	
	public void deleteApp(AppMock app)
	{
		if(appDirectory.containsValue(app)) {
			appDirectory.remove(app.getId());
		}
	}

	public void setApp(List<AppMock> appList) {
		for (AppMock app : appList) {
			if(!appDirectory.containsKey(app.getId()))
				appDirectory.put(app.getId(), app);
		}
	
	}

	@Override
	protected Map<Long, AppMock> getRepository() {
		// TODO Auto-generated method stub
		return appDirectory;
	}

	@Override
	public Stream<AppMock> fetchFromBackEnd(Query<AppMock, String> query) {
		// TODO Auto-generated method stub
		return appDirectory.values().stream();
	}

	@Override
	protected int sizeInBackEnd(Query<AppMock, String> query) {
		// TODO Auto-generated method stub
		return appDirectory.size();
	}

	@Override
	protected List<AppMock> getSavedList() {
		// TODO Auto-generated method stub
		return appDirectory.values().stream().collect(Collectors.toList());
	}
*/


	

}
