package fii.dip.api.exceptions;

public class InvalidEmailFormatException extends RuntimeException{
    public InvalidEmailFormatException(String message) {
        super(message);
    }
}
