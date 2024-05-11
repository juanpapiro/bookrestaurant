package br.com.bookrestaurant.infraestructure.gateway.impl.reserve;

import br.com.bookrestaurant.entity.reserve.Client;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.reserve.IClientGateway;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.IDataBase;

import java.util.UUID;

public class ClientGateway implements IClientGateway {
    private final IDataBase dataBase;

    public ClientGateway(IDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Client registerClient(Client client, UUID reserveId) {
        return dataBase.registerClient(client, reserveId);
    }
}
