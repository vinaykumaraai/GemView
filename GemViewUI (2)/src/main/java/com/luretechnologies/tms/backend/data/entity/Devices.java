package com.luretechnologies.tms.backend.data.entity;

import java.util.Date;
import java.util.Objects;

public class Devices extends AbstractEntity{

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
	
	public Devices() {
		
	}
	public Devices(StatusType statusType, String description,Integer statistics) {
		super(false);
		Objects.requireNonNull(statusType);
		Objects.requireNonNull(description);
		Objects.requireNonNull(statistics);
		this.statusType = statusType;
		this.description = description;
		this.statistics = statistics;
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
	@Override
	public String toString() {
		return "Devices [deviceName=" + deviceName + ", description=" + description + ", active=" + active
				+ ", manufacturer=" + manufacturer + ", rki=" + rki + ", osUpdate=" + osUpdate + ", serialNumber="
				+ serialNumber + ", heartBeat=" + heartBeat + ", lastSeen=" + lastSeen + ", frequency=" + frequency
				+ ", statusType=" + statusType + ", statistics=" + statistics + ", deviceDate=" + deviceDate + "]";
	}

	
}
