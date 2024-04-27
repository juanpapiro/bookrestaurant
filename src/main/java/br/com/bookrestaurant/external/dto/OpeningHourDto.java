package br.com.bookrestaurant.external.dto;

import br.com.bookrestaurant.entity.restaurant.OpeningHour;
import br.com.bookrestaurant.external.model.RestaurantModel;
import br.com.bookrestaurant.infraestructure.presenter.restaurant.OpeningHourRecord;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpeningHourDto {

    private UUID id;

    @NotNull
    private Integer dayOfTheWeekCode;

    private String dayOfTheWeek;

    @NotNull
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime hourOpen;

    @NotNull
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime hourClose;

    public OpeningHourRecord toRecord() {
        return new OpeningHourRecord(dayOfTheWeekCode, dayOfTheWeek, hourOpen, hourClose);
    }

    public OpeningHourDto(OpeningHour openingHour) {
        BeanUtils.copyProperties(openingHour, this);
    }

}
