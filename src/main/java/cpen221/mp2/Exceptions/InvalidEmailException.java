package cpen221.mp2.Exceptions;

public class InvalidEmailException extends Exception {
    public InvalidEmailException(String message, Throwable error){
        super(message, error);
    }
}
