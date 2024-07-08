package fr.diginamic.hello.exceptions;

public class DepartementNotFoundException extends RuntimeException {
	public DepartementNotFoundException(String message) {
		super(message);
	}
}
