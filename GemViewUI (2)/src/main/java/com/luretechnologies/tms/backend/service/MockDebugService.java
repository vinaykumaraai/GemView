package com.luretechnologies.tms.backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.luretechnologies.tms.backend.data.entity.Debug;
import com.luretechnologies.tms.backend.data.entity.DebugType;
import com.vaadin.data.provider.Query;
import com.vaadin.spring.annotation.SpringComponent;


@SpringComponent
public class MockDebugService extends CrudService<Debug>{
	private Map<Long,Debug> debugDirectory = new HashMap<Long, Debug>();
	
	@PostConstruct
	public void createInitialDebugs()
	{
		//TODO add the Date time
		Debug debug = new Debug(DebugType.ERROR,"There is a error");
		debugDirectory.put(debug.getId()+1, debug);
		 debug = new Debug(DebugType.WARN,"WARNNNNN");
		debugDirectory.put(debug.getId()+3, debug);
		 debug = new Debug(DebugType.INFO,"It is a Info");
		debugDirectory.put(debug.getId()+5, debug);
		debug = new Debug(DebugType.ERROR,"Again is a error");
		debugDirectory.put(debug.getId()+8, debug);
		//Why its only addin last message not alls
		System.out.println("Debug messages "+debugDirectory.size());
	}
	
	public void addDebug(Debug debug)
	{
		if(!debugDirectory.containsKey(debug.getId()))
		{
			debugDirectory.put(debug.getId(), debug);
		}
		else
			throw new RuntimeException("Debug Already present");
		
	}
	
	public void deleteDebug(Debug debug)
	{
		if(debugDirectory.containsValue(debug)) {
			
			debugDirectory.remove(debugDirectory.values().stream().filter(predicate -> {return predicate.equals(debug);}).findFirst().get().getId());
		}
	}

	public void setDebug(List<Debug> debugList) {
		for (Debug debug : debugList) {
			if(!debugDirectory.containsKey(debug.getId()))
				debugDirectory.put(debug.getId(), debug);
		}
	
	}

	@Override
	protected Map<Long, Debug> getRepository() {
		// TODO Auto-generated method stub
		return debugDirectory;
	}

	@Override
	public Stream<Debug> fetchFromBackEnd(Query<Debug, String> query) {
		// TODO Auto-generated method stub
		return debugDirectory.values().stream();
	}

	@Override
	protected int sizeInBackEnd(Query<Debug, String> query) {
		// TODO Auto-generated method stub
		return debugDirectory.size();
	}

	@Override
	protected List<Debug> getSavedList() {
		// TODO Auto-generated method stub
		return debugDirectory.values().stream().collect(Collectors.toList());
	}



	

}
