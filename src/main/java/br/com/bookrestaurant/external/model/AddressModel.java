package br.com.bookrestaurant.external.model;

import br.com.bookrestaurant.entity.restaurant.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID cdAddress;

    private String street;

    private Integer number;

    private String neighborhood;

    private String city;

    private String uf;

    private String cep;

    @OneToOne(fetch = FetchType.LAZY)
    private RestaurantModel restaurant;


    public AddressModel(Address address, UUID restaurantId) {
        BeanUtils.copyProperties(address, this);
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setId(restaurantId);
        this.restaurant = restaurantModel;
    }

    public Address toAddress() {
        return new Address(this.street, this.number,
                this.neighborhood, this.city, this.uf, this.cep);
    }

}
