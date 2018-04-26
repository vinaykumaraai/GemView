package com.luretechnologies.tms.backend.data.entity;

public enum AlertType {
	VOID("Void"),NOT_ACTIVE("Not Active"),REBOOT("Reboot"),REFUND("Refund"),MISC("Misc");
	String value;
	AlertType(String value) {
			this.value = value;
		}
}
