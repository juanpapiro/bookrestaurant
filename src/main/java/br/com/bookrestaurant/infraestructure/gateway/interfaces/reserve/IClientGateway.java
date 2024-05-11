package br.com.bookrestaurant.infraestructure.gateway.interfaces.reserve;

import br.com.bookrestaurant.entity.reserve.Client;

import java.util.UUID;

public interface IClientGateway {
    Client registerClient(Client client, UUID reserveId);
}
