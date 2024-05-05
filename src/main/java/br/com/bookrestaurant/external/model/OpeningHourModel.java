package br.com.bookrestaurant.external.model;

import br.com.bookrestaurant.entity.restaurant.OpeningHour;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "tb_openinghours")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpeningHourModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID cdOpeningHour;

    private Integer dayOfTheWeekCode;

    private String dayOfTheWeek;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalTime hourOpen;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalTime hourClose;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private RestaurantModel restaurant;


    public OpeningHourModel(OpeningHour openingHour, UUID restaurantId) {
        BeanUtils.copyProperties(openingHour, this);
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setId(restaurantId);
        this.restaurant = restaurantModel;
    }

    public OpeningHour toOpeningHour() {
        return new OpeningHour(this.dayOfTheWeekCode, this.hourOpen, this.hourClose);
    }

}
