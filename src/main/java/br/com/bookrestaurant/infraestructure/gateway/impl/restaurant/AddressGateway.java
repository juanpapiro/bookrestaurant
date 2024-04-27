package br.com.bookrestaurant.infraestructure.gateway.impl.restaurant;

import br.com.bookrestaurant.entity.restaurant.Address;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IAddressGateway;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IDataBase;

import java.util.UUID;

public class AddressGateway implements IAddressGateway {

    private final IDataBase dataBase;

    public AddressGateway(IDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Address registerAddress(Address address, UUID restaurantId) {
        return dataBase.registerAddress(address, restaurantId);
    }
}
