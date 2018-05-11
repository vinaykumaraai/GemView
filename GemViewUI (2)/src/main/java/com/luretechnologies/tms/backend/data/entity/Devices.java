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

package com.luretechnologies.tms.backend.data.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Devices extends AbstractEntity{
	
	private Integer idnumber;
	
	public Integer getIdnumber() {
		return idnumber;
	}
	public void setIdnumber(Integer idnumber) {
		this.idnumber = idnumber;
	}
	private String deviceName;

	private String description;

	private boolean active;
	
	private String manufacturer;
	
	private boolean rki;
	
	private boolean osUpdate;
	
	private String serialNumber;
	
	private boolean heartBeat;
	
	private String lastSeen;
	
	private String frequency;
	
	private StatusType statusType;
	
	private Integer statistics;
	
	private Date deviceDate;
	
	private List<HeartBeatHistory> hbHistoryList;
	
	private List<OverRideParameters> overRideParamList;

	public Devices() {
		
	}
	public Devices(StatusType statusType, String description,Integer statistics,String deviceName) {
		super(false);
		Objects.requireNonNull(statusType);
		Objects.requireNonNull(description);
		Objects.requireNonNull(statistics);
		Objects.requireNonNull(deviceName);
		this.statusType = statusType;
		this.description = description;
		this.statistics = statistics;
		this.deviceName = deviceName;
	}
	
	
	
	public List<OverRideParameters> getOverRideParamList() {
		return overRideParamList;
	}
	public void setOverRideParamList(List<OverRideParameters> overRideParamList) {
		this.overRideParamList = overRideParamList;
	}
	public List<HeartBeatHistory> getHbHistoryList() {
		return hbHistoryList;
	}
	public void setHbHistoryList(List<HeartBeatHistory> hbHistoryList) {
		this.hbHistoryList = hbHistoryList;
	}
	
	public Date getDeviceDate() {
		return deviceDate;
	}

	public void setDeviceDate(Date deviceDate) {
		this.deviceDate = deviceDate;
	}

	public StatusType getStatusType() {
		return statusType;
	}

	public void setStatusType(StatusType statusType) {
		this.statusType = statusType;
	}

	public Integer getStatistics() {
		return statistics;
	}

	public void setStatistics(Integer statistics) {
		this.statistics = statistics;
	}
	
	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public boolean isHeartBeat() {
		return heartBeat;
	}

	public void setHeartBeat(boolean heartBeat) {
		this.heartBeat = heartBeat;
	}

	public String getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(String lastSeen) {
		this.lastSeen = lastSeen;
	}
	
	public boolean isRki() {
		return rki;
	}

	public void setRki(boolean rki) {
		this.rki = rki;
	}

	public boolean isOsUpdate() {
		return osUpdate;
	}

	public void setOsUpdate(boolean osUpdate) {
		this.osUpdate = osUpdate;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
//	@Override
//	public String toString() {
//		return "Devices [deviceName=" + deviceName + ", description=" + description + ", active=" + active
//				+ ", manufacturer=" + manufacturer + ", rki=" + rki + ", osUpdate=" + osUpdate + ", serialNumber="
//				+ serialNumber + ", heartBeat=" + heartBeat + ", lastSeen=" + lastSeen + ", frequency=" + frequency
//				+ ", statusType=" + statusType + ", statistics=" + statistics + ", deviceDate=" + deviceDate + "]";
//	}

	@Override
	public String toString() {
		return deviceName + " : "+statusType+" : "+statistics;
	}
}
