package com.luretechnologies.tms.backend.data.entity;

public class Devices {

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
		return deviceName;
	}
	
}
