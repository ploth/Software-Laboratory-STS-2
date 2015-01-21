package algorithm;

public class AlgorithmException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message = null;

	public AlgorithmException() {
		super();
	}

	public AlgorithmException(String message) {
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
