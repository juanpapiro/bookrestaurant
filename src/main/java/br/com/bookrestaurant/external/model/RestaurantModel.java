package br.com.bookrestaurant.external.model;

import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntityBuilder;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_restaurants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String typeOfCuisine;

    private Integer capacity;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateCreate;

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private AddressModel address;

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @Fetch(FetchMode.SUBSELECT)

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private Set<OpeningHourModel> openingHours;


    public RestaurantModel(RestaurantEntity restaurantEntity) {
        this.name = restaurantEntity.getName();
        this.typeOfCuisine = restaurantEntity.getTypeOfCuisine();
        this.capacity = restaurantEntity.getCapacity();
        this.dateCreate = LocalDateTime.now();
    }

    public RestaurantEntity toEntity() {
        RestaurantEntity entity = new RestaurantEntityBuilder()
                .addInfos(this.name, this.typeOfCuisine, this.capacity)
                .build();
        entity.setId(this.id);
        entity.setDateCreate(this.dateCreate);
        entity.setAddress(this.address.toAddress());
        entity.setOpeningHours(this.openingHours.stream().map(OpeningHourModel::toOpeningHour).toList());
        return entity;
    }

}
