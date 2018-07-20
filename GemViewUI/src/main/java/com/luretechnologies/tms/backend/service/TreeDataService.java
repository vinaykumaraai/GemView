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
import org.springframework.util.StringUtils;

import com.luretechnologies.tms.backend.data.entity.Alert;
import com.luretechnologies.tms.backend.data.entity.AppMock;
import com.luretechnologies.tms.backend.data.entity.Debug;
import com.luretechnologies.tms.backend.data.entity.Devices;
import com.luretechnologies.tms.backend.data.entity.ExtendedNode;
import com.luretechnologies.tms.backend.data.entity.Node;
import com.luretechnologies.tms.backend.data.entity.NodeLevel;
import com.luretechnologies.tms.backend.data.entity.OverRideParameters;
import com.luretechnologies.tms.backend.data.entity.Profile;
import com.luretechnologies.tms.backend.data.entity.ProfileType;
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
	private final MockOverRideParamService mockOverRideParamService;
	private final MockAppService mockAppService;
	private List<Node> userNodeList;
	private List<Node> persliztnNodeList;
	private List<Node> debugNodeList;
	private List<ExtendedNode> debugAndAlertNodeList;
	private List<Node> odometerDeviceNodeList;

	@Autowired
	public TreeDataService(MockUserService userRepository, MockDebugService mockDebugService,
			MockOdometerDeviceService mockOdometerDeviceService, MockAlertService mockAlertService, 
			MockOverRideParamService mockOverRideParamService, MockAppService mockAppService) {
		this.mockUserService = userRepository;
		this.mockDebugService = mockDebugService;
		this.mockOdometerDeviceService = mockOdometerDeviceService;
		this.mockAlertService = mockAlertService;
		this.mockOverRideParamService = mockOverRideParamService;
		this.mockAppService = mockAppService;
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
	
	private List<OverRideParameters> getSortedParamList(Collection<OverRideParameters> unsortedCollection) {
		List<OverRideParameters> sortedList = unsortedCollection.stream().sorted((o1, o2) -> {
			return o1.getType().compareTo(o2.getType());
		}).collect(Collectors.toList());
		return sortedList;
	}

	public TreeData<Node> getTreeDataForUser() {

		// Tree<Node> tree = new Tree<>();
		TreeData<Node> treeData = new TreeData<>();
		//List<User> userList1 = new ArrayList<User>((mockUserService.getRepository().values())); // getSortedUserList()
		//List<User> userList2 = new ArrayList<User>((mockUserService.getRepository().values()));
		//List<User> userList3 = new ArrayList<User>((mockUserService.getRepository().values()));
		//List<User> userList4 = new ArrayList<User>((mockUserService.getRepository().values()));
		//List<User> userList5 = new ArrayList<User>((mockUserService.getRepository().values()));

		for (int index = 0; index < 3; index++) {
			/*userList1.remove(index);
			userList2.remove(index + 1);
			userList3.remove(index + 0);
			userList4.remove(index + 0);
			userList5.remove(index + 0);*/

		}
		userNodeList = new ArrayList();

		Node node = new Node();
		node.setLabel("Enterprise Entity");
		node.setLevel(NodeLevel.ENTITY);
		//node.setEntityList(userList1);
		userNodeList.add(node);

		Node node1 = new Node();
		node1.setLabel("Region West");
		node1.setLevel(NodeLevel.REGION);
		//node1.setEntityList(userList2);
		userNodeList.add(node1);

		Node node2 = new Node();
		node2.setLabel("Merchant 1");
		node2.setLevel(NodeLevel.MERCHANT);
		//node2.setEntityList(userList3);
		userNodeList.add(node2);

		Node node3 = new Node();
		node3.setLabel("Terminal Entity 1");
		node3.setLevel(NodeLevel.TERMINAL);
		//node3.setEntityList(userList4);
		userNodeList.add(node3);

		Node node4 = new Node();
		node4.setLabel("Device 1");
		node4.setLevel(NodeLevel.DEVICE);
		//node4.setEntityList(userList5);
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
		
		for(int index=0; index<10; index++) {
			Node node5 = new Node();
			node5.setLabel("Device "+index);
			node5.setLevel(NodeLevel.DEVICE);
			node5.setEntityList(odometerDeviceList5);
			odometerDeviceNodeList.add(node5);
			treeData.addItem(node3, node5);
		}
		for(int index=0; index<10; index++) {
			Node node6 = new Node();
			node6.setLabel("Terminal "+index);
			node6.setLevel(NodeLevel.TERMINAL);
			node6.setEntityList(odometerDeviceList4);
			odometerDeviceNodeList.add(node6);
			treeData.addItem(node2, node6);
		}
		
		for(int index=0; index<10; index++) {
			Node node7 = new Node();
			node7.setLabel("Merchant "+index);
			node7.setLevel(NodeLevel.MERCHANT);
			node7.setEntityList(odometerDeviceList3);
			odometerDeviceNodeList.add(node7);
			treeData.addItem(node1, node7);
		}

		
//		treeData.addItem(null, node);
//		treeData.addItem(node, node1);
//		treeData.addItem(node1, node2);
//		treeData.addItem(node2, node3);
//		treeData.addItem(node3, node4);

		return treeData;
	}

	public List<Node> getOdometerDeviceList() {
		return this.odometerDeviceNodeList;
	}
	
	public TreeData<Node> getTreeDataForPersonlization() {

		// Tree<Node> tree = new Tree<>();
		TreeData<Node> treeData = new TreeData<>();
		List<OverRideParameters> paramList1 = new ArrayList<OverRideParameters>(getSortedParamList(mockOverRideParamService.getRepository().values())); // getSortedUserList()
		List<OverRideParameters> paramList2 = new ArrayList<OverRideParameters>(getSortedParamList(mockOverRideParamService.getRepository().values()));
		List<OverRideParameters> paramList3 = new ArrayList<OverRideParameters>(getSortedParamList(mockOverRideParamService.getRepository().values()));
		List<OverRideParameters> paramList4 = new ArrayList<OverRideParameters>(getSortedParamList(mockOverRideParamService.getRepository().values()));
		List<OverRideParameters> paramList5 = new ArrayList<OverRideParameters>(getSortedParamList(mockOverRideParamService.getRepository().values()));

		for (int index = 0; index < 3; index++) {
			paramList1.remove(index);
			paramList2.remove(index + 1);
			paramList3.remove(index + 2);
			paramList4.remove(index + 3);
			paramList5.remove(index + 2);

		}
		persliztnNodeList = new ArrayList();
		List<Devices> deviceList = new ArrayList<Devices>(mockOdometerDeviceService.getRepository().values());
		//List<AppMock> appList = new ArrayList<AppMock>(mockAppService.getRepository().values());
		
		Node node = new Node();
		node.setLabel("Enterprise Entity");
		node.setLevel(NodeLevel.ENTITY);
		node.setDevice(deviceList.get(1));
		node.setDescription("This is Entity Description");
		node.setActive(true);
		node.setSerialNum("12AFDTH598U");
		node.setHeartBeat(false);
		node.setFrequency("120 Seconds");
		//node.setApp(appList.get(0));
		node.setAdditionaFiles("Entity Files ");
		//node.setProfile(new Profile(1L,ProfileType.FOOD, "Subway"));
		node.setUpdate("update 1");
		node.setOverRideParamList(paramList1);
		persliztnNodeList.add(node);

		Node node1 = new Node();
		node1.setLabel("Region West");
		node1.setLevel(NodeLevel.REGION);
		node1.setDevice(deviceList.get(2));
		node1.setDescription("This is Region Description");
		node1.setActive(true);
		node1.setSerialNum("67AFDETG670U");
		node1.setHeartBeat(true);
		node1.setFrequency("1 Hr");
		//node1.setApp(appList.get(1));
		node1.setAdditionaFiles("Region Files ");
		//node1.setProfile(new Profile(2L,ProfileType.FOOD, "Papa Jhones"));
		node1.setUpdate("update 2");
		node1.setOverRideParamList(paramList2);
		persliztnNodeList.add(node1);

		Node node2 = new Node();
		node2.setLabel("Merchant 1");
		node2.setLevel(NodeLevel.MERCHANT);
		node2.setDevice(deviceList.get(3));
		node2.setDescription("This is Merchant Description");
		node2.setActive(false);
		node2.setSerialNum("8967TYHTG670U");
		node2.setHeartBeat(true);
		node2.setFrequency("24 Hr");
		//node2.setApp(appList.get(2));
		node2.setAdditionaFiles("Merchant Files ");
		//node2.setProfile(new Profile(4L,ProfileType.FOOD, "Star Bucks"));
		node2.setUpdate("update 4");
		node2.setOverRideParamList(paramList3);
		persliztnNodeList.add(node2);

		Node node3 = new Node();
		node3.setLabel("Terminal Entity 1");
		node3.setLevel(NodeLevel.TERMINAL);
		node3.setDevice(deviceList.get(4));
		node3.setDescription("This is Terminal Description");
		node3.setActive(false);
		node3.setSerialNum("3457TYHFR70U");
		node3.setHeartBeat(true);
		node3.setFrequency("1 week");
		//node3.setApp(appList.get(0));
		node3.setAdditionaFiles("Terminal Files");
		//node3.setProfile(new Profile(5L,ProfileType.RETAIL, "Walmart"));
		node3.setUpdate("update 5");
		node3.setOverRideParamList(paramList4);
		persliztnNodeList.add(node3);

		Node node4 = new Node();
		node4.setLabel("Device 1");
		node4.setLevel(NodeLevel.DEVICE);
		node4.setDevice(deviceList.get(5));
		node4.setDescription("This is Device Description");
		node4.setActive(false);
		node4.setSerialNum("9087TYHFEFT");
		node4.setHeartBeat(true);
		node4.setFrequency("1 month");
		//node4.setApp(appList.get(2));
		node4.setAdditionaFiles("Device Files");
		//node4.setProfile(new Profile(6L,ProfileType.RETAIL, "Target"));
		node4.setUpdate("update 6");
		node4.setOverRideParamList(paramList5);
		persliztnNodeList.add(node4);

		treeData.addItem(null, node);
		treeData.addItem(node, node1);
		treeData.addItem(node1, node2);
		treeData.addItem(node2, node3);
		treeData.addItem(node3, node4);

		return treeData;
	}
	
	public List<Node> getPersonlizationList() {
		return this.persliztnNodeList;
	}
	
	public TreeData<Node> getFilteredTreeByNodeName(TreeData<Node> allTreeData, String filterTextInLower) {
		if (StringUtils.isEmpty(filterTextInLower)) {
			return allTreeData;
		} else {
			TreeData<Node> treeData = new TreeData<>();
			Node rootNode = allTreeData.getRootItems().get(0);
			if (checkIfLabelStartsWith(filterTextInLower, rootNode)) {
				treeData.addItem(null, rootNode);
			}

			for (Node childNode : allTreeData.getChildren(rootNode)) {

				if (!treeData.contains(rootNode) && checkIfLabelStartsWith(filterTextInLower, childNode)) {
					treeData.addItem(null, childNode);
				}else if(treeData.contains(rootNode))
					treeData.addItem(rootNode, childNode);

				for (Node subChildNode : allTreeData.getChildren(childNode)) {

					if (!treeData.contains(childNode) && checkIfLabelStartsWith(filterTextInLower, subChildNode)) {
						treeData.addItem(null, subChildNode);
					}else if(treeData.contains(childNode))
						treeData.addItem(childNode, subChildNode);
					
					for (Node subSubChildNode : allTreeData.getChildren(subChildNode)) {

						if (!treeData.contains(subChildNode) && checkIfLabelStartsWith(filterTextInLower, subSubChildNode)) {
							treeData.addItem(null, subSubChildNode);
						}else if(treeData.contains(subChildNode))
							treeData.addItem(subChildNode,subSubChildNode);
						
						for (Node subSubSubChildNode : allTreeData.getChildren(subSubChildNode)) {

							if (!treeData.contains(subSubChildNode) && checkIfLabelStartsWith(filterTextInLower, subSubSubChildNode)) {
								treeData.addItem(null, subSubSubChildNode);
							}else if(treeData.contains(subSubChildNode))
								treeData.addItem(subSubChildNode,subSubSubChildNode);
							
						}
					}

				}
			}
			return treeData;
		}
	}
	
	public TreeData<ExtendedNode> getFilteredTreeByExtendedNodeName(TreeData<ExtendedNode> allTreeData, String filterTextInLower) {
		if (StringUtils.isEmpty(filterTextInLower)) {
			return allTreeData;
		} else {
			TreeData<ExtendedNode> treeData = new TreeData<>();
			ExtendedNode rootNode = allTreeData.getRootItems().get(0);
			if (checkIfLabelStartsWith(filterTextInLower, rootNode)) {
				treeData.addItem(null, rootNode);
			}

			for (ExtendedNode childNode : allTreeData.getChildren(rootNode)) {

				if (!treeData.contains(rootNode) && checkIfLabelStartsWith(filterTextInLower, childNode)) {
					treeData.addItem(null, childNode);
				}else if(treeData.contains(rootNode))
					treeData.addItem(rootNode, childNode);

				for (ExtendedNode subChildNode : allTreeData.getChildren(childNode)) {

					if (!treeData.contains(childNode) && checkIfLabelStartsWith(filterTextInLower, subChildNode)) {
						treeData.addItem(null, subChildNode);
					}else if(treeData.contains(childNode))
						treeData.addItem(childNode, subChildNode);
					
					for (ExtendedNode subSubChildNode : allTreeData.getChildren(subChildNode)) {

						if (!treeData.contains(subChildNode) && checkIfLabelStartsWith(filterTextInLower, subSubChildNode)) {
							treeData.addItem(null, subSubChildNode);
						}else if(treeData.contains(subChildNode))
							treeData.addItem(subChildNode,subSubChildNode);
						
						for (ExtendedNode subSubSubChildNode : allTreeData.getChildren(subSubChildNode)) {

							if (!treeData.contains(subSubChildNode) && checkIfLabelStartsWith(filterTextInLower, subSubSubChildNode)) {
								treeData.addItem(null, subSubSubChildNode);
							}else if(treeData.contains(subSubChildNode))
								treeData.addItem(subSubChildNode,subSubSubChildNode);
							
						}
					}

				}
			}
			return treeData;
		}
	}

	/**
	 * @param filterTextInLower
	 * @param node
	 * @return
	 */
	private boolean checkIfLabelStartsWith(String filterTextInLower, Node node) {
		return StringUtils.startsWithIgnoreCase(node.getLabel().toLowerCase(), filterTextInLower);
	}
	
}
