package br.com.bookrestaurant.infraestructure.presenter.reserve;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public record ReserveRecord(
        UUID restaurantId,
        LocalDateTime date,
        Integer seats,
        String status
) {
}
