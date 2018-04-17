package com.luretechnologies.tms.backend.data.entity;

public enum StatusType {
	CARDSWIPES("CARD SWIPES"),INVALIDCARDSWIPES("INVALID CARD SWIPES"),REBOOT("REBOOT"),TRANSACTIONS("TRANSACTIONS");
	String value;
	StatusType(String value) {
			this.value = value;
		}
}
