package usta.parking.domain.exception;

/** Error de regla de negocio (mensaje en español para mostrar en la vista). */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) { super(message); }
}
