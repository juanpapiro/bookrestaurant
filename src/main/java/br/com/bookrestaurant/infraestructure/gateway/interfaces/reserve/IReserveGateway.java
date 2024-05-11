package br.com.bookrestaurant.infraestructure.gateway.interfaces.reserve;

import br.com.bookrestaurant.entity.reserve.ReserveEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IReserveGateway {
    ReserveEntity registerReserve (ReserveEntity reserveEntity);
    List<ReserveEntity> findReserveByRestaurantAndDate(UUID restaurantId, LocalDateTime date);
    void updateStatus(UUID id, String status);
}
