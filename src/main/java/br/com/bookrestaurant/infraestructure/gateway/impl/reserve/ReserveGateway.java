package br.com.bookrestaurant.infraestructure.gateway.impl.reserve;

import br.com.bookrestaurant.entity.reserve.ReserveEntity;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.IDataBase;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.reserve.IReserveGateway;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ReserveGateway implements IReserveGateway {
    private final IDataBase dataBase;

    public ReserveGateway (IDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public ReserveEntity registerReserve(ReserveEntity reserveEntity) {
        return this.dataBase.registerReserve(reserveEntity);
    }

    @Override
    public List<ReserveEntity> findReserveByRestaurantAndDate(UUID restaurantId, LocalDateTime date) {
        return this.dataBase.findReserveByRestaurantAndDate(restaurantId, date);
    }

    @Override
    public void updateStatus(UUID id, String status) {
        this.dataBase.updateStatus(id, status);
    }
}
