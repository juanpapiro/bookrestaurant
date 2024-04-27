package br.com.bookrestaurant.external.dto;

import br.com.bookrestaurant.entity.restaurant.Address;
import br.com.bookrestaurant.infraestructure.presenter.restaurant.AddressRecord;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private UUID id;

    @NotNull
    private String street;

    @NotNull
    private Integer number;

    @NotNull
    private String neighborhood;

    @NotNull
    private String city;

    @NotNull
    private String uf;

    @NotNull
    private String cep;


    public AddressRecord toRecord() {
        return new AddressRecord(street, number, neighborhood, city, uf, cep);
    }

    public AddressDto(Address address) {
        BeanUtils.copyProperties(address, this);
    }

}
