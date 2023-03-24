package at.spengergasse.friseursalon.service;

public class ServiceException extends RuntimeException{

    private ServiceException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    public static ServiceException forEntity(String entityName, Throwable rootCause) {
        String message = "Error while communicating with DB to access entity of type '%s' .\n" +
                "Root cause was: %s";
        return new ServiceException(message.formatted(entityName, rootCause.getMessage()), rootCause);
    }
}
