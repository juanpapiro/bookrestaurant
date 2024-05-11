package br.com.bookrestaurant.external.model;


import br.com.bookrestaurant.entity.reserve.ReserveEntity;
import br.com.bookrestaurant.entity.reserve.ReserveEntityBuilder;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_reserve")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReserveModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID restaurantId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;
    private Integer seats;
    private String status;
    @OneToOne(mappedBy = "reserve", fetch = FetchType.LAZY)
    private ClientModel client;

    public ReserveModel(ReserveEntity reserveEntity) {
        this.restaurantId = reserveEntity.getRestaurantId();
        this.date = reserveEntity.getDate();
        this.seats = reserveEntity.getSeats();
        this.status = reserveEntity.getStatus();
    }

    public ReserveEntity toEntity() {
        ReserveEntity entity = new ReserveEntityBuilder()
                .addInfos(this.restaurantId, this.date, this.seats, this.status)
                .build();
        entity.setId(this.id);
        entity.setClient(this.client.toClient());
        return entity;
    }

}
