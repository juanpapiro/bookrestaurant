package br.com.bookrestaurant.usecase.reserve;

import br.com.bookrestaurant.entity.EntityUtil;
import br.com.bookrestaurant.entity.reserve.Client;
import br.com.bookrestaurant.entity.reserve.ReserveEntity;
import br.com.bookrestaurant.entity.reserve.ReserveEntityBuilder;
import br.com.bookrestaurant.entity.reserve.exception.ReserveInvalidException;
import br.com.bookrestaurant.entity.reserve.exception.ReserveNotFoundException;

import java.time.LocalDateTime;
import java.util.*;

public class ReserveUseCase {
    public static ReserveEntity registerReserve (UUID restaurantId, LocalDateTime date, Integer seats, Client client) {
        validClient(client);
        return new ReserveEntityBuilder()
                .addInfos(restaurantId, date, seats, "A")
                .addClient(client)
                .build();
    }

    public static Client validClient(Client client) {
        return Optional.ofNullable(client)
                .orElseThrow(() -> new ReserveInvalidException("Cliente é obrigatório"));
    }

    public static Map<String,Object> findByRestaurantAndDate(UUID restaurantId, LocalDateTime date) {
        Map<String, Object> params = new HashMap<>();
        params.put("restaurantId", restaurantId);
        params.put("date", date);
        EntityUtil.isNullReserve(params, "Restaurante e data data devem ser informado.");
        return params;
    }

    public static List<ReserveEntity> locateReserves(List<ReserveEntity> restaurants) {
        return Optional.ofNullable(restaurants)
                .filter(list -> !list.isEmpty())
                .orElseThrow(ReserveNotFoundException::new);
    }

    public static Map<String, Object> updateStatus(UUID id, String status) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("status", status);
        EntityUtil.isNullReserve(params, "Situação da reserva dev ser informada.");
        return params;
    }

    protected ReserveUseCase() {
        throw new IllegalStateException("Class util not instanec.");
    }

}
