package com.luretechnologies.tms.backend.data.entity;

public enum AlertType {
	VOID("Void"),NOT_ACTIVE("Not Active"),REBOOT("Reboot"),REFUND("Refund");
	String value;
	AlertType(String value) {
			this.value = value;
		}
}
