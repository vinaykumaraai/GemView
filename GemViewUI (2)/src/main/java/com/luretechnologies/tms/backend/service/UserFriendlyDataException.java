package com.luretechnologies.tms.backend.service;


/**
 * A data integraty violation exception containing a message intended to be
 * shown to the end user.
 */
public class UserFriendlyDataException extends RuntimeException {

	public UserFriendlyDataException(String message) {
		super(message);
	}

}
