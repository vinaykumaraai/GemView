package com.luretechnologies.tms.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.luretechnologies.tms.app.DataGenerator;
import com.luretechnologies.tms.backend.UserRepository;
import com.luretechnologies.tms.backend.data.Role;
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
	private final UserRepository userRepository;
	//private PasswordEncoder passwordEncoder;
	DataGenerator db = new DataGenerator();
	
	@Autowired
	public TreeDataService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public TreeData<Node> getTreeData() {
		
		//Tree<Node> tree = new Tree<>();
		TreeData<Node> treeData = new TreeData<>();
		List<User> userList1 = userRepository.findAll();
		List<User> userList2 = userRepository.findAll();
		List<User> userList3 = userRepository.findAll();
		List<User> userList4 = userRepository.findAll();
		List<User> userList5 = userRepository.findAll();
		
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
