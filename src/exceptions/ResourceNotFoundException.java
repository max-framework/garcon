package exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String fileName) {
        super("Resource not found: " + fileName);
    }
}
