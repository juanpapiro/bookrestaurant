package br.com.bookrestaurant.external.dto;

import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.infraestructure.presenter.restaurant.RestaurantRecord;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantDto {

    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String typeOfCuisine;

    @NotNull
    private Integer capacity;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateCreate;

    @NotNull
    private AddressDto address;

    @NotEmpty
    private List<OpeningHourDto> openingHours;

    public RestaurantRecord toRecord() {
        return new RestaurantRecord(name, typeOfCuisine, capacity);
    }


    public RestaurantDto(RestaurantEntity restaurantEntity) {
        this.id = restaurantEntity.getId();
        this.name = restaurantEntity.getName();
        this.typeOfCuisine = restaurantEntity.getTypeOfCuisine();
        this.capacity = restaurantEntity.getCapacity();
        this.dateCreate = restaurantEntity.getDateCreate();
        this.address = new AddressDto(restaurantEntity.getAddress());
        this.openingHours = restaurantEntity.getOpeningHours().stream().map(OpeningHourDto::new).toList();
    }


}
