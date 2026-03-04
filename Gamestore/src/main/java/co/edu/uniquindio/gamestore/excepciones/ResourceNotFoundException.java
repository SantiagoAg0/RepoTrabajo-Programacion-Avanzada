package co.edu.uniquindio.gamestore.excepciones;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " con id " + id + " no fue encontrado.");
    }
}
