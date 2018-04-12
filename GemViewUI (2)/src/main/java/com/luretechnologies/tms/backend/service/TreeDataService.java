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

@Service
public class TreeDataService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final MockUserService mockUserService;
	private final MockDebugService mockDebugService;
	//private PasswordEncoder passwordEncoder;
	
	//DataGenerator db = new DataGenerator();
	
	@Autowired
	public TreeDataService(MockUserService userRepository,MockDebugService mockDebugService) {
		this.mockUserService = userRepository;
		this.mockDebugService = mockDebugService;
	}
	private List<User> getSortedUserList(Collection<User> unsortedCollection){
		List<User> sortedList = unsortedCollection.stream().sorted((o1,o2)->{
			return o1.getName().compareTo(o2.getName());
		}).collect(Collectors.toList());
		return sortedList;
	}
	private List<Debug> getSortedDebugList(Collection<Debug> unsortedCollection){
		List<Debug> sortedList = unsortedCollection.stream().sorted((o1,o2)->{
			return o1.getDateOfDebug().compareTo(o2.getDateOfDebug());
		}).collect(Collectors.toList());
		return sortedList;
	}
	public TreeData<Node> getTreeDataForUser() {
		
		//Tree<Node> tree = new Tree<>();
		TreeData<Node> treeData = new TreeData<>();
		List<User> userList1 = new ArrayList<User>(getSortedUserList(mockUserService.getRepository().values())) ; //getSortedUserList()
		List<User> userList2 = new ArrayList<User>(getSortedUserList(mockUserService.getRepository().values())) ;
		List<User> userList3 = new ArrayList<User>(getSortedUserList(mockUserService.getRepository().values())) ;
		List<User> userList4 = new ArrayList<User>(getSortedUserList(mockUserService.getRepository().values())) ;
		List<User> userList5 = new ArrayList<User>(getSortedUserList(mockUserService.getRepository().values())) ;
		
		for(int index =0 ; index<3; index++) {
			userList1.remove(index);
			userList2.remove(index+1);
			userList3.remove(index+2);
			userList4.remove(index+3);
			userList5.remove(index+2);
			
		}
		
		Node node = new Node();
		node.setLabel("Enterprise Entity");
		node.setLevel(NodeLevel.ENTITY);
		node.setEntityList(userList1);
		
		Node node1 = new Node();
		node1.setLabel("Region West");
		node1.setLevel(NodeLevel.REGION);
		node1.setEntityList(userList2);

		Node node2 = new Node();
		node2.setLabel("Merchant 1");
		node2.setLevel(NodeLevel.MERCHANT);
		node2.setEntityList(userList3);
		
		Node node3 = new Node();
		node3.setLabel("Terminal Entity 1");
		node3.setLevel(NodeLevel.TERMINAL);
		node3.setEntityList(userList4);
		
		Node node4 = new Node();
		node4.setLabel("Device 1");
		node4.setLevel(NodeLevel.DEVICE);
		node4.setEntityList(userList5);
		
		treeData.addItem(null,node);
		treeData.addItem(node,node1);
		treeData.addItem(node1,node2);
		treeData.addItem(node2,node3);
		treeData.addItem(node3,node4);
		
		return treeData;
	}
public TreeData<Node> getTreeDataForDebug() {
		
		//Tree<Node> tree = new Tree<>();
		TreeData<Node> treeData = new TreeData<>();
		List<Debug> debugList1 = new ArrayList<Debug>(getSortedDebugList(mockDebugService.getRepository().values())) ; 
		List<Debug> debugList2 = new ArrayList<Debug>(getSortedDebugList(mockDebugService.getRepository().values())) ;
		List<Debug> debugList3 = new ArrayList<Debug>(getSortedDebugList(mockDebugService.getRepository().values())) ;
		List<Debug> debugList4 = new ArrayList<Debug>(getSortedDebugList(mockDebugService.getRepository().values())) ;
		List<Debug> debugList5 = new ArrayList<Debug>(getSortedDebugList(mockDebugService.getRepository().values())) ;
		
		for(int index =0 ; index<3; index++) {
			if(debugList1.size()>index)
			debugList1.remove(index);
			if(debugList2.size()>index+1)
			debugList2.remove(index+1);
			if(debugList3.size()>index+2)
			debugList3.remove(index+2);
			if(debugList4.size()>index+3)
			debugList4.remove(index+3);
			if(debugList5.size()>index+2)
			debugList5.remove(index+2);
			
		}
		
		Node node = new Node();
		node.setLabel("Enterprise Entity");
		node.setLevel(NodeLevel.ENTITY);
		node.setEntityList(debugList1);
		
		Node node1 = new Node();
		node1.setLabel("Region West");
		node1.setLevel(NodeLevel.REGION);
		node1.setEntityList(debugList2);

		Node node2 = new Node();
		node2.setLabel("Merchant 1");
		node2.setLevel(NodeLevel.MERCHANT);
		node2.setEntityList(debugList3);
		
		Node node3 = new Node();
		node3.setLabel("Terminal Entity 1");
		node3.setLevel(NodeLevel.TERMINAL);
		node3.setEntityList(debugList4);
		
		Node node4 = new Node();
		node4.setLabel("Device 1");
		node4.setLevel(NodeLevel.DEVICE);
		node4.setEntityList(debugList5);
		
		treeData.addItem(null,node);
		treeData.addItem(node,node1);
		treeData.addItem(node1,node2);
		treeData.addItem(node2,node3);
		treeData.addItem(node3,node4);
		
		return treeData;
	}
//	private List<User> mockUserData(int count) {
//		User user;
//		List<List<User>> userData = new ArrayList<>();
//		List<User> mockUserList = new ArrayList<>();
//		for(int index=1; index <=10 ;index ++) {
//			for(int j=1; j<=index;j++) {
//				user = new User("Mock@gmail.com", "Mock", passwordEncoder.encode("Mock"), Role.HR, "Vinay", "Raai", true);
//				mockUserList.add(user);
//			}
//			userData.add(mockUserList);
//		}
//		return userData.get(count);
//	}
	
}
