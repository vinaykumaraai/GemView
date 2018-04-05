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
	
	/*public TreeData<Node> getTreeData() {
		
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
		node.setUserList(userList1);
		
		Node node1 = new Node();
		node1.setLabel("Region West");
		node1.setLevel(NodeLevel.REGION);
		node1.setUserList(userList2);

		Node node2 = new Node();
		node2.setLabel("Merchant 1");
		node2.setLevel(NodeLevel.MERCHANT);
		node2.setUserList(userList3);
		
		Node node3 = new Node();
		node3.setLabel("Terminal Entity 1");
		node3.setLevel(NodeLevel.TERMINAL);
		node3.setUserList(userList4);
		
		Node node4 = new Node();
		node4.setLabel("Device 1");
		node4.setLevel(NodeLevel.DEVICE);
		node4.setUserList(userList5);
		
		treeData.addItem(null,node);
		treeData.addItem(node,node1);
		treeData.addItem(node1,node2);
		treeData.addItem(node2,node3);
		treeData.addItem(node3,node4);
		
		return treeData;
	}*/
	

	
}
