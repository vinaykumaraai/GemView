package com.luretechnologies.tms.backend.data;

public class Downloads {

	private String serialNumber;
	
	private String organizationName;
	
	private String OS;
	
	private String incomingIP;
	
	private String device;
	
	private String completion;
	
	
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOS() {
		return OS;
	}

	public void setOS(String oS) {
		OS = oS;
	}

	public String getIncomingIP() {
		return incomingIP;
	}

	public void setIncomingIP(String incomingIP) {
		this.incomingIP = incomingIP;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getCompletion() {
		return completion;
	}

	public void setCompletion(String completion) {
		this.completion = completion;
	}
}
