package br.com.bookrestaurant.entity.reserve;

import br.com.bookrestaurant.entity.reserve.exception.ReserveInvalidException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

public class ReserveEntityBuilder {
    private Optional<ReserveEntity> reserveEntity = Optional.ofNullable(null);

    public ReserveEntityBuilder addInfos(UUID restaurantId, LocalDateTime date, Integer seats, String status) {
        this.reserveEntity = Optional.of(new ReserveEntity(restaurantId, date, seats, status));
        return this;
    }

    public ReserveEntityBuilder addClient(Client client) {
        reserveEntity.ifPresent(resv -> resv.setClient(client));
        return this;
    }

    public ReserveEntity build() {
        return reserveEntity.orElseThrow(() ->
                new ReserveInvalidException("Falha ao criar nova reserva"));
    }
}
