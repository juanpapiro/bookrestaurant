package br.com.bookrestaurant.entity.reserve.exception;

public class ReserveInvalidException extends RuntimeException {
    public ReserveInvalidException (String message) {
        super(message);
    }
}
