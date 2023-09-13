package exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message, String request) {
        super("Bad request. Request: " + request + ". Message: " + message);
    }

}
