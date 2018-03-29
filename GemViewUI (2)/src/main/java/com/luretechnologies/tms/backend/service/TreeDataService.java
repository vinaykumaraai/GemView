package com.luretechnologies.tms.backend.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.luretechnologies.tms.backend.ITreeDataEntity;
import com.luretechnologies.tms.backend.UserRepository;
import com.luretechnologies.tms.backend.data.entity.Node;
import com.luretechnologies.tms.backend.data.entity.NodeLevel;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.ui.view.admin.AbstractCrudView;
import com.luretechnologies.tms.ui.view.admin.user.UserAdminView;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Tree;

@Service
public class TreeDataService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final UserRepository userRepository;
	
	@Autowired
	public TreeDataService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public TreeData<Node> getTreeData() {
		
		//Tree<Node> tree = new Tree<>();
		TreeData<Node> treeData = new TreeData<>();
		List<User> userList = userRepository.findAll();
		userList.remove(1);
		
		Node node = new Node();
		node.setLabel("Enterprise Entity");
		node.setLevel(NodeLevel.ENTITY);
		node.setUserList(userList);
		
		Node node1 = new Node();
		node1.setLabel("Region West");
		node1.setLevel(NodeLevel.REGION);
		node1.setUserList(userList);

		Node node2 = new Node();
		node2.setLabel("Merchant 1");
		node2.setLevel(NodeLevel.MERCHANT);
		node2.setUserList(userList);
		
		
		treeData.addItem(null,node);
		treeData.addItem(node,node1);
		treeData.addItem(node1,node2);
		/*treeData.addItem("Enterprise Entity","Region West");
		treeData.addItem("Enterprise Entity","Region East");
		treeData.addItem("Enterprise Entity","Region North");
		treeData.addItem("Enterprise Entity","Region South");
		treeData.addItem("Region North","Merchant 1");
		treeData.addItem("Region North","Merchant 2");
		treeData.addItem("Region North","Merchant 3");*/
		
		//TreeDataProvider<Node> dataProvider = new TreeDataProvider<>(treeData);
		//tree.setDataProvider(inMemoryDataProvider);
		/*tree.setItemIconGenerator(e ->{
			
		});*/
		//tree.setIcon(VaadinIcons.BUILDING_O);
//		tree.addItemClickListener(e -> {
//			//getPresenter().getLevelUsers(e.getItem());
//			//getGrid().setData(new User("carlos@gmail.com", "carlos", passwordEncoder.encode("carlos"), Role.HR, "carlos", "romero", true));
//			e.getItem().getUserList();
//			Grid grid = new Grid();
//			DataProvider data = new ListDataProvider(e.getItem().getUserList());
////			grid.setDataProvider(data);
//			//setGrid(grid);
//			userAdminView.getGrid().setDataProvider(data);
//			System.out.println("end");
//			Notification.show("Success"+ e.getMouseEventDetails().getButtonName()).setDelayMsec(5000);;
//		});
		
		return treeData;
	}
	
}
