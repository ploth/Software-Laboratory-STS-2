package io;

// This is an exception class which get thrown on converter errors.
public class ConverterException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message = null;

	public ConverterException() {
		super();
	}

	public ConverterException(String message) {
		super(message);
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
