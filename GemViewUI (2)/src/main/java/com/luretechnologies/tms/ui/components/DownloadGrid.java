package com.luretechnologies.tms.ui.components;

import com.vaadin.ui.Grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.vaadin.spring.annotation.PrototypeScope;

import com.luretechnologies.tms.backend.data.Downloads;
import com.vaadin.spring.annotation.SpringComponent;

@SuppressWarnings("serial")
@SpringComponent
@PrototypeScope
public class DownloadGrid extends Grid<Downloads>{
	
	List<HashMap<String, String>> rows = new ArrayList<>();
	
	private List<Downloads> getDownloadsList() {
		
		List<Downloads> downloadsList = new ArrayList<>();
		for(int index = 0; index <10; index++) {
			
			Downloads dl = new Downloads();
			dl.setSerialNumber("123"+index);
			dl.setOrganizationName("Entity Holdings "+index);
			dl.setOS("V.123."+index);
			dl.setIncomingIP("1.21.213."+index);
			dl.setDevice("IDTECH "+index);
			dl.setCompletion("1"+index+"%");
			downloadsList.add(dl);
		}
		return downloadsList;
	}
	
	public List<HashMap<String, String>> getFakeData() {
		for (Downloads dl : getDownloadsList()) {
			  HashMap<String, String> fakeBean = new LinkedHashMap<>();
			  fakeBean.put("Serial Number", dl.getSerialNumber());
			  fakeBean.put("Organization Name", dl.getOrganizationName());
			  fakeBean.put("OS", dl.getOS());
			  fakeBean.put("Incoming IP", dl.getIncomingIP());
			  fakeBean.put("Device", dl.getDevice());
			  fakeBean.put("Completion", dl.getCompletion());
			  rows.add(fakeBean);
			}
		
		return rows;
		
	}
	
}
