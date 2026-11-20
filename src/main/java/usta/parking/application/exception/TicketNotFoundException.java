package usta.parking.application.exception;

import usta.parking.domain.exception.BusinessException;

public class TicketNotFoundException extends BusinessException {
    public TicketNotFoundException() { super("El ticket no existe"); }
}
