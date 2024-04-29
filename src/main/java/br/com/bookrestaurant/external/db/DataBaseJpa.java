package br.com.bookrestaurant.external.db;

import br.com.bookrestaurant.entity.restaurant.Address;
import br.com.bookrestaurant.entity.restaurant.OpeningHour;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.external.model.AddressModel;
import br.com.bookrestaurant.external.model.OpeningHourModel;
import br.com.bookrestaurant.external.model.RestaurantModel;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IDataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DataBaseJpa implements IDataBase {

    private OpeningHourRepository openingHourRepository;
    private AddressRepository addressRepository;
    private RestaurantRepository restaurantRepository;

    @Autowired
    public DataBaseJpa(
            OpeningHourRepository openingHourRepository,
            RestaurantRepository restaurantRepository,
            AddressRepository addressRepository
    ) {
        this.openingHourRepository = openingHourRepository;
        this.addressRepository = addressRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public RestaurantEntity registerRestaurant(RestaurantEntity restaurantEntity) {
        RestaurantModel restaurantModel = new RestaurantModel(restaurantEntity);
        restaurantModel = restaurantRepository.save(restaurantModel);
        restaurantEntity.setId(restaurantModel.getId());
        return restaurantEntity;
    }

    @Override
    public List<RestaurantEntity> findByName(String name) {
        List<RestaurantModel> restaurantModels = restaurantRepository.findByName(name);
        return restaurantModels.stream().map(RestaurantModel::toEntity).toList();
    }

    @Override
    public List<RestaurantEntity> findByTypeOfCuisine(String typeOfCuisine) {
        List<RestaurantModel> restaurantModels = restaurantRepository.findByTypeOfCuisine(typeOfCuisine);
        return restaurantModels.stream().map(RestaurantModel::toEntity).toList();
    }

    @Override
    public List<RestaurantEntity> findByLocale(String uf, String city, String neighborhood) {
        List<RestaurantModel> restaurantModels = restaurantRepository.findByLocale(uf, city, neighborhood);
        return restaurantModels.stream().map(RestaurantModel::toEntity).toList();
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


}
