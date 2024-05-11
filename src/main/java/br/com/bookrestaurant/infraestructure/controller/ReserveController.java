package br.com.bookrestaurant.infraestructure.controller;

import br.com.bookrestaurant.entity.reserve.Client;
import br.com.bookrestaurant.entity.reserve.ReserveEntity;
import br.com.bookrestaurant.infraestructure.gateway.impl.reserve.ClientGateway;
import br.com.bookrestaurant.infraestructure.gateway.impl.reserve.ReserveGateway;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.reserve.IClientGateway;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.IDataBase;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.reserve.IReserveGateway;
import br.com.bookrestaurant.infraestructure.presenter.reserve.ClientRecord;
import br.com.bookrestaurant.infraestructure.presenter.reserve.ReserveRecord;
import br.com.bookrestaurant.usecase.reserve.ReserveUseCase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ReserveController {
    public ReserveEntity register(ReserveRecord reserveRecord,
                                  ClientRecord clientRecord,
                                  IDataBase database) {
        IReserveGateway reserveGateway = new ReserveGateway(database);
        IClientGateway clientGateway = new ClientGateway(database);

        Client client = new Client(clientRecord.name(), clientRecord.phone());
        ReserveEntity reserveEntity = ReserveUseCase.registerReserve(reserveRecord.restaurantId(),
                reserveRecord.date(), reserveRecord.seats(), client);

        reserveEntity = reserveGateway.registerReserve(reserveEntity);
        clientGateway.registerClient(client, reserveEntity.getId());

        return reserveEntity;
    }

    public List<ReserveEntity> findByRestaurantAndDate(UUID restaurantId, LocalDateTime date,
                                                              IDataBase database) {
        Map<String, Object> params = ReserveUseCase.findByRestaurantAndDate(restaurantId, date);
        List<ReserveEntity> reserves = database.
                findReserveByRestaurantAndDate((UUID) params.get("restaurantId"),
                        (LocalDateTime) params.get("date"));
        return ReserveUseCase.locateReserves(reserves);
    }

    public void updateStatus(UUID id, String status, IDataBase database) {
        Map<String, Object> params = ReserveUseCase.updateStatus(id, status);
        database.updateStatus((UUID) params.get("id"), (String) params.get("status"));
    }
}
