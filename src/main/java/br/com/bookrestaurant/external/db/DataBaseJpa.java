package br.com.bookrestaurant.external.db;

import br.com.bookrestaurant.entity.evaluate.EvaluateEntity;
import br.com.bookrestaurant.entity.reserve.Client;
import br.com.bookrestaurant.entity.reserve.ReserveEntity;
import br.com.bookrestaurant.entity.restaurant.Address;
import br.com.bookrestaurant.entity.restaurant.OpeningHour;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.external.model.*;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.IDataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DataBaseJpa implements IDataBase {

    private OpeningHourRepository openingHourRepository;
    private AddressRepository addressRepository;
    private RestaurantRepository restaurantRepository;
    private EvaluateRepository evaluateRepository;
    private ClientRepository clientRepository;
    private ReserveRepository reserveRepository;

    @Autowired
    public DataBaseJpa(
            OpeningHourRepository openingHourRepository,
            RestaurantRepository restaurantRepository,
            AddressRepository addressRepository,
            EvaluateRepository evaluateRepository,
            ClientRepository clientRepository,
            ReserveRepository reserveRepository

    ) {
        this.openingHourRepository = openingHourRepository;
        this.addressRepository = addressRepository;
        this.restaurantRepository = restaurantRepository;
        this.evaluateRepository = evaluateRepository;
        this.clientRepository = clientRepository;
        this.reserveRepository = reserveRepository;
    }

    @Override
    public RestaurantEntity registerRestaurant(RestaurantEntity restaurantEntity) {
        RestaurantModel restaurantModel = new RestaurantModel(restaurantEntity);
        restaurantModel = restaurantRepository.save(restaurantModel);
        restaurantEntity.setId(restaurantModel.getId());
        return restaurantEntity;
    }

    @Override
    public List<RestaurantEntity> findRestaurantByName(String name) {
        List<RestaurantModel> restaurantModels = restaurantRepository.findByName(name);
        return restaurantModels.stream().map(RestaurantModel::toEntity).toList();
    }

    @Override
    public List<RestaurantEntity> findRestaurantByTypeOfCuisine(String typeOfCuisine) {
        List<RestaurantModel> restaurantModels = restaurantRepository.findByTypeOfCuisine(typeOfCuisine);
        return restaurantModels.stream().map(RestaurantModel::toEntity).toList();
    }

    @Override
    public List<RestaurantEntity> findRestaurantByLocale(String uf, String city, String neighborhood) {
        List<RestaurantModel> restaurantModels = restaurantRepository.findByLocale(uf, city, neighborhood);
        return restaurantModels.stream().map(RestaurantModel::toEntity).toList();
    }

    @Override
    public RestaurantEntity findRestaurantById(UUID id) {
        return restaurantRepository.findById(id)
                .map(RestaurantModel::toEntity)
                .orElse(null);
    }

    @Override
    public Address registerAddress(Address address, UUID restaurantId) {
        AddressModel addressModel = new AddressModel(address, restaurantId);
        addressRepository.save(addressModel);
        return address;
    }

    @Override
    public OpeningHour registerOpeningHour(OpeningHour openingHour, UUID restaurantId) {
        OpeningHourModel openingHourModel = new OpeningHourModel(openingHour, restaurantId);
        openingHourRepository.save(openingHourModel);
        return openingHour;
    }

    @Override
    public List<OpeningHour> registerOpeningHours(List<OpeningHour> openingHours, UUID restaurantId) {
        List<OpeningHourModel> openingHourModels = openingHours.stream()
                .map(openingHour -> new OpeningHourModel(openingHour, restaurantId)).toList();
        openingHourRepository.saveAll(openingHourModels);
        return openingHours;
    }

    @Override
    public EvaluateEntity registryEvaluate(EvaluateEntity evaluateEntity) {
        EvaluateModel evaluateModel = new EvaluateModel(evaluateEntity);
        evaluateModel = evaluateRepository.save(evaluateModel);
        evaluateEntity.setEvaluateId(evaluateModel.getEvaluateId());
        return evaluateEntity;
    }

    @Override
    public ReserveEntity registerReserve(ReserveEntity reserveEntity) {
        ReserveModel reserveModel = new ReserveModel(reserveEntity);
        reserveModel = reserveRepository.save(reserveModel);
        reserveEntity.setId(reserveModel.getId());
        return reserveEntity;
    }

    @Override
    public Client registerClient(Client client, UUID reserveId) {
        ClientModel clientModel = new ClientModel(client, reserveId);
        clientRepository.save(clientModel);
        return client;
    }

    @Override
    public List<ReserveEntity> findReserveByRestaurantAndDate(UUID restaurantId,
                                                              LocalDateTime date) {
        List<ReserveModel> reserveModels = reserveRepository
                .findByRestaurantAndDate(restaurantId, date);
        return reserveModels.stream().map(ReserveModel::toEntity).toList();
    }

    @Override
    public void updateStatus(UUID id, String status) {
        reserveRepository.updateStatus(id, status);
    }

}
