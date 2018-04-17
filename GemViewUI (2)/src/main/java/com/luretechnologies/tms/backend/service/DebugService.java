package com.luretechnologies.tms.backend.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luretechnologies.tms.backend.data.entity.Debug;
import com.luretechnologies.tms.backend.data.entity.Node;
import com.luretechnologies.tms.backend.data.entity.NodeLevel;
import com.luretechnologies.tms.backend.data.entity.User;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.ListDataProvider;

@Service
public class DebugService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final MockDebugService mockDebugService;
	//private PasswordEncoder passwordEncoder;
	//DataGenerator db = new DataGenerator();
	
	@Autowired
	public DebugService(MockDebugService mockRepository) {
		this.mockDebugService = mockRepository;
	}
	private List<Debug> getSortedUserList(Collection<Debug> unsortedCollection){
		List<Debug> sortedList = unsortedCollection.stream().sorted((o1,o2)->{
			return o1.getType().compareTo(o2.getType());
		}).collect(Collectors.toList());
		return sortedList;
	}
	
	public ListDataProvider<Debug> getListDataProvider(){
		ListDataProvider<Debug> debugDataProvider = new ListDataProvider<>(getSortedUserList(mockDebugService.getSavedList()));
		return debugDataProvider;
	}
	
	public void removeDebug(Debug debug) {
		mockDebugService.deleteDebug(debug);
	}
}
