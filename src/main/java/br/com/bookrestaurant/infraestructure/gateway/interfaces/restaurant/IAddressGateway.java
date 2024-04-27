package br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant;

import br.com.bookrestaurant.entity.restaurant.Address;

import java.util.UUID;

public interface IAddressGateway {

    Address registerAddress(Address address, UUID restaurantId);

}
