package com.nt.rookies.asset.exception;

public class ExistException extends RuntimeException {
	public ExistException() {
	}

	public ExistException(Throwable cause) {
		super(cause);
	}

	public ExistException(String msg) {
		super(msg);
	}
}
