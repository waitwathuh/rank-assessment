package com.rank.assessment.exception;

public class PlayerNotFoundException extends RuntimeException {

	public PlayerNotFoundException() {
		super();
	}

	public PlayerNotFoundException(String message) {
		super(message);
	}

}
