package br.com.bookrestaurant.entity.reserve.exception;

public class ReserveNotFoundException extends RuntimeException {
    public ReserveNotFoundException() {
        super("Reserva não localizada");
    }
}
