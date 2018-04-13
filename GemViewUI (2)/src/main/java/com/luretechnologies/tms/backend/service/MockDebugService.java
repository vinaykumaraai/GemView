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

import com.luretechnologies.tms.backend.data.entity.Debug;
import com.luretechnologies.tms.backend.data.entity.DebugType;
import com.vaadin.data.provider.Query;
import com.vaadin.spring.annotation.SpringComponent;


@SpringComponent
public class MockDebugService extends CrudService<Debug>{
	private Map<Long,Debug> debugDirectory = new HashMap<Long, Debug>();
	private LocalDate currentLocalDate = LocalDate.now();
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	@PostConstruct
	public void createInitialDebugs() throws ParseException
	{
		//TODO add the Date time
		Debug debug = new Debug(DebugType.ERROR,"There is an error 1",dateFormatter.parse(currentLocalDate.toString()));
		debug.setId(debug.getId()+2);
		debugDirectory.put(debug.getId(), debug);
		 debug = new Debug(DebugType.WARN,"WARNNNNN 1",dateFormatter.parse(currentLocalDate.plusDays(2).toString()));
		 debug.setId(debug.getId()+3);
		debugDirectory.put(debug.getId(), debug);
		 debug = new Debug(DebugType.INFO,"It is an Info 1",dateFormatter.parse(currentLocalDate.plusDays(4).toString()));
		 debug.setId(debug.getId()+4);
		debugDirectory.put(debug.getId(), debug);
		debug = new Debug(DebugType.ERROR,"Again is an error 2",dateFormatter.parse(currentLocalDate.plusDays(3).toString()));
		debug.setId(debug.getId()+5);
		debugDirectory.put(debug.getId(), debug);
		debug = new Debug(DebugType.INFO,"It is an Info 2",dateFormatter.parse(currentLocalDate.plusDays(3).toString()));
		debug.setId(debug.getId()+6);
		debugDirectory.put(debug.getId(), debug);
		 debug = new Debug(DebugType.WARN,"WARNNNNN 2",dateFormatter.parse(currentLocalDate.minusDays(2).toString()));
		 debug.setId(debug.getId()+7);
		debugDirectory.put(debug.getId(), debug);
		 debug = new Debug(DebugType.INFO,"It is an Info 3",dateFormatter.parse(currentLocalDate.minusDays(4).toString()));
		 debug.setId(debug.getId()+8);
		debugDirectory.put(debug.getId(), debug);
		debug = new Debug(DebugType.ERROR,"Again is an error 3",dateFormatter.parse(currentLocalDate.minusDays(3).toString()));
		debug.setId(debug.getId()+9);
		debugDirectory.put(debug.getId(), debug);
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
			debugDirectory.remove(debug.getId());
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
