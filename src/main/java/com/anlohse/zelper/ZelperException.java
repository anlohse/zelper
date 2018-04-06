package com.anlohse.zelper;

public class ZelperException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ZelperException() {
	}

	public ZelperException(String message) {
		super(message);
	}

	public ZelperException(Throwable cause) {
		super(cause);
	}

	public ZelperException(String message, Throwable cause) {
		super(message, cause);
	}

}
