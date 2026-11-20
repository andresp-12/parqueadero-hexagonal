package usta.parking.application.exception;

import usta.parking.domain.exception.BusinessException;

public class NoAvailableSpotException extends BusinessException {
    public NoAvailableSpotException(String vehicle) { super("No hay cupos disponibles para " + vehicle); }
}
