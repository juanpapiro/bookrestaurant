package br.com.bookrestaurant.entity.reserve;

import br.com.bookrestaurant.entity.EntityUtil;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReserveEntity {
    private UUID id;
    private UUID restaurantId;
    private LocalDateTime date;
    private Integer seats;
    private String status;
    private Client client;

    public ReserveEntity (UUID restaurantId, LocalDateTime date, Integer seats, String status) {
        this.restaurantId = EntityUtil.isNull(restaurantId, "Identificação do restaurante é obrigatória");
        this.date = EntityUtil.isNull(date, "Data e hora da reserva é obrigatória");
        this.seats = EntityUtil.isNull(seats, "Número de pessoas é obrigatório");
        this.status = EntityUtil.isNullOrBlank(status, "Situação da reserva é obrigatória");
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getSeats() {
        return seats;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
