package br.com.bookrestaurant.external.dto;

import br.com.bookrestaurant.entity.reserve.ReserveEntity;
import br.com.bookrestaurant.infraestructure.presenter.reserve.ReserveRecord;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReserveDto {
    private UUID id;
    @NotNull
    private UUID restaurantId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;
    @NotNull
    private Integer seats;
    @NotNull
    private String status;
    @NotNull
    private ClientDto client;

    public ReserveDto(ReserveEntity reserveEntity) {
        this.id = reserveEntity.getId();
        this.restaurantId = reserveEntity.getRestaurantId();
        this.date = reserveEntity.getDate();
        this.seats = reserveEntity.getSeats();
        this.status = reserveEntity.getStatus();
        this.client = new ClientDto(reserveEntity.getClient());
    }

    public ReserveRecord toRecord() {
        return new ReserveRecord(restaurantId, date, seats, status);
    }
}
