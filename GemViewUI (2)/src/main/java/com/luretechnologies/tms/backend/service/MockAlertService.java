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

import com.luretechnologies.tms.backend.data.entity.Alert;
import com.luretechnologies.tms.backend.data.entity.AlertType;
import com.luretechnologies.tms.backend.data.entity.Debug;
import com.luretechnologies.tms.backend.data.entity.DebugType;
import com.vaadin.data.provider.Query;
import com.vaadin.spring.annotation.SpringComponent;


@SpringComponent
public class MockAlertService extends CrudService<Alert>{
	private Map<Long,Alert> alertDirectory = new HashMap<Long, Alert>();
	@PostConstruct
	public void createInitialAlertss() throws ParseException
	{
		//TODO add the Date time
		Alert alert = new Alert(AlertType.REBOOT,"Reboot Happend","xyz@abc.com",true);
		alert.setId(alert.getId()+2);
		alertDirectory.put(alert.getId(), alert);
		 alert = new Alert(AlertType.REFUND,"Refund Happend","abc@xyz.com",true);
		 alert.setId(alert.getId()+3);
		alertDirectory.put(alert.getId(), alert);
		 alert = new Alert(AlertType.NOT_ACTIVE,"This is not active","xyz@abc.com",false);
		 alert.setId(alert.getId()+4);
		alertDirectory.put(alert.getId(), alert);
		alert = new Alert(AlertType.VOID,"This is void","xyz@abc.com",true);
		alert.setId(alert.getId()+5);
		alertDirectory.put(alert.getId(), alert);
		alert = new Alert(AlertType.REBOOT,"Reboot Happend2","xyz@abc.com",true);
		alert.setId(alert.getId()+6);
		alertDirectory.put(alert.getId(), alert);
		 alert = new Alert(AlertType.REFUND,"Refund Happend2","xyz@abc.com",true);
		 alert.setId(alert.getId()+7);
		alertDirectory.put(alert.getId(), alert);
		 alert = new Alert(AlertType.VOID,"void","xyz@abc.com",true);
		 alert.setId(alert.getId()+8);
		alertDirectory.put(alert.getId(), alert);
	}
	
	public void addAlert(Alert alert)
	{
		if(!alertDirectory.containsKey(alert.getId()))
		{
			alertDirectory.put(alert.getId(), alert);
		}
		else
			throw new RuntimeException("Alert Already present");
		
	}
	
	public void deleteAlert(Alert alert)
	{
		if(alertDirectory.containsValue(alert)) {
			alertDirectory.remove(alert.getId());
		}
	}

	public void setAlert(List<Alert> alertList) {
		for (Alert alert : alertList) {
			if(!alertDirectory.containsKey(alert.getId()))
				alertDirectory.put(alert.getId(), alert);
		}
	
	}

	@Override
	protected Map<Long, Alert> getRepository() {
		// TODO Auto-generated method stub
		return alertDirectory;
	}

	@Override
	public Stream<Alert> fetchFromBackEnd(Query<Alert, String> query) {
		// TODO Auto-generated method stub
		return alertDirectory.values().stream();
	}

	@Override
	protected int sizeInBackEnd(Query<Alert, String> query) {
		// TODO Auto-generated method stub
		return alertDirectory.size();
	}

	@Override
	protected List<Alert> getSavedList() {
		// TODO Auto-generated method stub
		return alertDirectory.values().stream().collect(Collectors.toList());
	}



	

}
