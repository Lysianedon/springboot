package fr.diginamic.hello.exceptions;

public class EntityException extends RuntimeException {

	public EntityException(String message) {
		super(message);
	}
	
    public static EntityException alreadyExists() {
        return new EntityException("L'entité existe déjà");
    }
}
