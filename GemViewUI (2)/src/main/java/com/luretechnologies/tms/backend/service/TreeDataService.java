/**
 * COPYRIGHT @ Lure Technologies, LLC.
 * ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or
 * form other than in accordance with and subject to the terms of a written
 * license from Lure or with the prior written consent of Lure or as
 * permitted by applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure.  If you are neither the
 * intended recipient, nor an agent, employee, nor independent contractor
 * responsible for delivering this message to the intended recipient, you are
 * prohibited from copying, disclosing, distributing, disseminating, and/or
 * using the information in this email in any manner. If you have received
 * this message in error, please advise us immediately at
 * legal@luretechnologies.com by return email and then delete the message from your
 * computer and all other records (whether electronic, hard copy, or
 * otherwise).
 *
 * Any copies or reproductions of this software program (in whole or in part)
 * made by any method must also include a copy of this legend.
 *
 * Inquiries should be made to legal@luretechnologies.com
 *
 */

package com.luretechnologies.tms.backend.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luretechnologies.tms.backend.data.entity.Alert;
import com.luretechnologies.tms.backend.data.entity.Debug;
import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.ExtendedNode;
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
	private final MockOdometerDeviceService mockOdometerDeviceService;
	private final MockAlertService mockAlertService;
	private List<Node> userNodeList;
	private List<Node> debugNodeList;
	private List<ExtendedNode> debugAndAlertNodeList;
	private List<Node> odometerDeviceNodeList;

	@Autowired
	public TreeDataService(MockUserService userRepository, MockDebugService mockDebugService,
			MockOdometerDeviceService mockOdometerDeviceService, MockAlertService mockAlertService) {
		this.mockUserService = userRepository;
		this.mockDebugService = mockDebugService;
		this.mockOdometerDeviceService = mockOdometerDeviceService;
		this.mockAlertService = mockAlertService;
	}

	private List<User> getSortedUserList(Collection<User> unsortedCollection) {
		List<User> sortedList = unsortedCollection.stream().sorted((o1, o2) -> {
			return o1.getName().compareTo(o2.getName());
		}).collect(Collectors.toList());
		return sortedList;
	}

	private List<Debug> getSortedDebugList(Collection<Debug> unsortedCollection) {
		List<Debug> sortedList = unsortedCollection.stream().sorted((o1, o2) -> {
			return o1.getDateOfDebug().compareTo(o2.getDateOfDebug());
		}).collect(Collectors.toList());
		return sortedList;
	}

	private List<Devices> getSortedOdometerDeviceList(Collection<Devices> unsortedCollection) {
		List<Devices> sortedList = unsortedCollection.stream().sorted((o1, o2) -> {
			return o1.getDeviceDate().compareTo(o2.getDeviceDate());
		}).collect(Collectors.toList());
		return sortedList;
	}

	private List<Alert> getSortedAlertList(Collection<Alert> unsortedCollection) {
		List<Alert> sortedList = unsortedCollection.stream().sorted((o1, o2) -> {
			return o1.getType().compareTo(o2.getType());
		}).collect(Collectors.toList());
		return sortedList;
	}

	public TreeData<Node> getTreeDataForUser() {

		// Tree<Node> tree = new Tree<>();
		TreeData<Node> treeData = new TreeData<>();
		List<User> userList1 = new ArrayList<User>((mockUserService.getRepository().values())); // getSortedUserList()
		List<User> userList2 = new ArrayList<User>((mockUserService.getRepository().values()));
		List<User> userList3 = new ArrayList<User>((mockUserService.getRepository().values()));
		List<User> userList4 = new ArrayList<User>((mockUserService.getRepository().values()));
		List<User> userList5 = new ArrayList<User>((mockUserService.getRepository().values()));

		for (int index = 0; index < 3; index++) {
			userList1.remove(index);
			userList2.remove(index + 1);
			userList3.remove(index + 2);
			userList4.remove(index + 3);
			userList5.remove(index + 2);

		}
		userNodeList = new ArrayList();

		Node node = new Node();
		node.setLabel("Enterprise Entity");
		node.setLevel(NodeLevel.ENTITY);
		node.setEntityList(userList1);
		userNodeList.add(node);

		Node node1 = new Node();
		node1.setLabel("Region West");
		node1.setLevel(NodeLevel.REGION);
		node1.setEntityList(userList2);
		userNodeList.add(node1);

		Node node2 = new Node();
		node2.setLabel("Merchant 1");
		node2.setLevel(NodeLevel.MERCHANT);
		node2.setEntityList(userList3);
		userNodeList.add(node2);

		Node node3 = new Node();
		node3.setLabel("Terminal Entity 1");
		node3.setLevel(NodeLevel.TERMINAL);
		node3.setEntityList(userList4);
		userNodeList.add(node3);

		Node node4 = new Node();
		node4.setLabel("Device 1");
		node4.setLevel(NodeLevel.DEVICE);
		node4.setEntityList(userList5);
		userNodeList.add(node4);

		treeData.addItem(null, node);
		treeData.addItem(node, node1);
		treeData.addItem(node1, node2);
		treeData.addItem(node2, node3);
		treeData.addItem(node3, node4);

		return treeData;
	}

	public List<Node> getUserNodeList() {
		return this.userNodeList;
	}

public TreeData<ExtendedNode> getTreeDataForDebugAndAlert() {
		
		//Tree<Node> tree = new Tree<>();
		TreeData<ExtendedNode> treeData = new TreeData<>();
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
		List<Alert> alertList1 = new ArrayList<Alert>(getSortedAlertList(mockAlertService.getRepository().values())) ; 
		List<Alert> alertList2 = new ArrayList<Alert>(getSortedAlertList(mockAlertService.getRepository().values())) ;
		List<Alert> alertList3 = new ArrayList<Alert>(getSortedAlertList(mockAlertService.getRepository().values())) ;
		List<Alert> alertList4 = new ArrayList<Alert>(getSortedAlertList(mockAlertService.getRepository().values())) ;
		List<Alert> alertList5 = new ArrayList<Alert>(getSortedAlertList(mockAlertService.getRepository().values())) ;
		for(int index =0 ; index<3; index++) {
			if(alertList1.size()>index)
				alertList1.remove(index);
			if(alertList2.size()>index+1)
				alertList2.remove(index+1);
			if(alertList3.size()>index+2)
				alertList3.remove(index+2);
			if(alertList4.size()>index+3)
				alertList4.remove(index+3);
			if(alertList5.size()>index+2)
				alertList5.remove(index+2);
		}
		
		debugAndAlertNodeList = new ArrayList<>(); 
		
		ExtendedNode node = new ExtendedNode();
		node.setLabel("Enterprise Entity");
		node.setLevel(NodeLevel.ENTITY);
		node.setEntityList(debugList1);
		node.setExtendedList(alertList1);
		debugAndAlertNodeList.add(node);
		
		ExtendedNode node1 = new ExtendedNode();
		node1.setLabel("Region West");
		node1.setLevel(NodeLevel.REGION);
		node1.setEntityList(debugList2);
		node1.setExtendedList(alertList2);
		debugAndAlertNodeList.add(node1);

		ExtendedNode node2 = new ExtendedNode();
		node2.setLabel("Merchant 1");
		node2.setLevel(NodeLevel.MERCHANT);
		node2.setEntityList(debugList3);
		node2.setExtendedList(alertList2);
		debugAndAlertNodeList.add(node2);
		
		ExtendedNode node3 = new ExtendedNode();
		node3.setLabel("Terminal Entity 1");
		node3.setLevel(NodeLevel.TERMINAL);
		node3.setEntityList(debugList3);
		node3.setExtendedList(alertList3);
		debugAndAlertNodeList.add(node3);
		
		ExtendedNode node4 = new ExtendedNode();
		node4.setLabel("Device 1");
		node4.setLevel(NodeLevel.DEVICE);
		node4.setEntityList(debugList4);
		node4.setExtendedList(alertList4);
		debugAndAlertNodeList.add(node4);
		
		treeData.addItem(null,node);
		treeData.addItem(node,node1);
		treeData.addItem(node1,node2);
		treeData.addItem(node2,node3);
		treeData.addItem(node3,node4);
		
		return treeData;
	}

	public TreeData<Node> getTreeDataForDebug() {

		// Tree<Node> tree = new Tree<>();
		TreeData<Node> treeData = new TreeData<>();
		List<Debug> debugList1 = new ArrayList<Debug>(getSortedDebugList(mockDebugService.getRepository().values()));
		List<Debug> debugList2 = new ArrayList<Debug>(getSortedDebugList(mockDebugService.getRepository().values()));
		List<Debug> debugList3 = new ArrayList<Debug>(getSortedDebugList(mockDebugService.getRepository().values()));
		List<Debug> debugList4 = new ArrayList<Debug>(getSortedDebugList(mockDebugService.getRepository().values()));
		List<Debug> debugList5 = new ArrayList<Debug>(getSortedDebugList(mockDebugService.getRepository().values()));

		for (int index = 0; index < 3; index++) {
			if (debugList1.size() > index)
				debugList1.remove(index);
			if (debugList2.size() > index + 1)
				debugList2.remove(index + 1);
			if (debugList3.size() > index + 2)
				debugList3.remove(index + 2);
			if (debugList4.size() > index + 3)
				debugList4.remove(index + 3);
			if (debugList5.size() > index + 2)
				debugList5.remove(index + 2);

		}
		debugNodeList = new ArrayList();

		Node node = new Node();
		node.setLabel("Enterprise Entity");
		node.setLevel(NodeLevel.ENTITY);
		node.setEntityList(debugList1);
		debugNodeList.add(node);

		Node node1 = new Node();
		node1.setLabel("Region West");
		node1.setLevel(NodeLevel.REGION);
		node1.setEntityList(debugList2);
		debugNodeList.add(node1);

		Node node2 = new Node();
		node2.setLabel("Merchant 1");
		node2.setLevel(NodeLevel.MERCHANT);
		node2.setEntityList(debugList3);
		debugNodeList.add(node2);

		Node node3 = new Node();
		node3.setLabel("Terminal Entity 1");
		node3.setLevel(NodeLevel.TERMINAL);
		node3.setEntityList(debugList4);
		debugNodeList.add(node3);

		Node node4 = new Node();
		node4.setLabel("Device 1");
		node4.setLevel(NodeLevel.DEVICE);
		node4.setEntityList(debugList5);
		debugNodeList.add(node4);

		treeData.addItem(null, node);
		treeData.addItem(node, node1);
		treeData.addItem(node1, node2);
		treeData.addItem(node2, node3);
		treeData.addItem(node3, node4);

		return treeData;
	}

	public List<Node> getDebugNodeList() {
		return this.debugNodeList;
	}

	public List<ExtendedNode> getDebugAndAlertNodeList() {
		return debugAndAlertNodeList;
	}

	public TreeData<Node> getTreeDataForDeviceOdometer() {

		// Tree<Node> tree = new Tree<>();
		TreeData<Node> treeData = new TreeData<>();
		List<Devices> odometerDeviceList1 = new ArrayList<Devices>(
				getSortedOdometerDeviceList(mockOdometerDeviceService.getRepository().values()));
		List<Devices> odometerDeviceList2 = new ArrayList<Devices>(
				getSortedOdometerDeviceList(mockOdometerDeviceService.getRepository().values()));
		List<Devices> odometerDeviceList3 = new ArrayList<Devices>(
				getSortedOdometerDeviceList(mockOdometerDeviceService.getRepository().values()));
		List<Devices> odometerDeviceList4 = new ArrayList<Devices>(
				getSortedOdometerDeviceList(mockOdometerDeviceService.getRepository().values()));
		List<Devices> odometerDeviceList5 = new ArrayList<Devices>(
				getSortedOdometerDeviceList(mockOdometerDeviceService.getRepository().values()));

		for (int index = 0; index < 3; index++) {
			if (odometerDeviceList1.size() > index)
				odometerDeviceList1.remove(index);
			if (odometerDeviceList2.size() > index + 1)
				odometerDeviceList2.remove(index + 1);
			if (odometerDeviceList3.size() > index + 2)
				odometerDeviceList3.remove(index + 2);
			if (odometerDeviceList4.size() > index + 3)
				odometerDeviceList4.remove(index + 3);
			if (odometerDeviceList5.size() > index + 2)
				odometerDeviceList5.remove(index + 2);

		}
		odometerDeviceNodeList = new ArrayList();

		Node node = new Node();
		node.setLabel("Enterprise Entity");
		node.setLevel(NodeLevel.ENTITY);
		node.setEntityList(odometerDeviceList1);
		odometerDeviceNodeList.add(node);

		Node node1 = new Node();
		node1.setLabel("Region West");
		node1.setLevel(NodeLevel.REGION);
		node1.setEntityList(odometerDeviceList2);
		odometerDeviceNodeList.add(node1);

		Node node2 = new Node();
		node2.setLabel("Merchant 1");
		node2.setLevel(NodeLevel.MERCHANT);
		node2.setEntityList(odometerDeviceList3);
		odometerDeviceNodeList.add(node2);

		Node node3 = new Node();
		node3.setLabel("Terminal Entity 1");
		node3.setLevel(NodeLevel.TERMINAL);
		node3.setEntityList(odometerDeviceList4);
		odometerDeviceNodeList.add(node3);

		Node node4 = new Node();
		node4.setLabel("Device 1");
		node4.setLevel(NodeLevel.DEVICE);
		node4.setEntityList(odometerDeviceList5);
		odometerDeviceNodeList.add(node4);

		treeData.addItem(null, node);
		treeData.addItem(node, node1);
		treeData.addItem(node1, node2);
		treeData.addItem(node2, node3);
		treeData.addItem(node3, node4);

		return treeData;
	}

	public List<Node> getOdometerDeviceList() {
		return this.odometerDeviceNodeList;
	}

}
