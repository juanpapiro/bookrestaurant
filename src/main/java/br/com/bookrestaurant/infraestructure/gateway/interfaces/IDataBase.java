package br.com.bookrestaurant.infraestructure.gateway.interfaces;


import br.com.bookrestaurant.infraestructure.gateway.interfaces.evaluate.IEvaluateGateway;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.reserve.IClientGateway;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.reserve.IReserveGateway;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IAddressGateway;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IOpeningHourGateway;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IRestaurantGateway;

public interface IDataBase extends
        IRestaurantGateway, IOpeningHourGateway, IAddressGateway, IEvaluateGateway,
        IReserveGateway, IClientGateway {
    
}
