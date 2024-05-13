package br.com.bookrestaurant.utilsbytests;

import br.com.bookrestaurant.entity.evaluate.EvaluateEntity;
import br.com.bookrestaurant.entity.evaluate.EvaluateEntityBuilder;
import br.com.bookrestaurant.entity.reserve.Client;
import br.com.bookrestaurant.entity.reserve.ReserveEntity;
import br.com.bookrestaurant.entity.reserve.ReserveEntityBuilder;
import br.com.bookrestaurant.entity.restaurant.Address;
import br.com.bookrestaurant.entity.restaurant.OpeningHour;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntityBuilder;
import br.com.bookrestaurant.external.dto.*;
import br.com.bookrestaurant.external.model.*;
import br.com.bookrestaurant.infraestructure.presenter.evaluate.EvaluateRecord;
import br.com.bookrestaurant.infraestructure.presenter.reserve.ClientRecord;
import br.com.bookrestaurant.infraestructure.presenter.reserve.ReserveRecord;
import br.com.bookrestaurant.infraestructure.presenter.restaurant.AddressRecord;
import br.com.bookrestaurant.infraestructure.presenter.restaurant.OpeningHourRecord;
import br.com.bookrestaurant.infraestructure.presenter.restaurant.RestaurantRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.IntStream;

public class Util {

    public static Address buildAddres() {
        return new Address("Rua Teodoro Sampaio", 100, "Pinheiros",
                "São Paulo", "SP", "01000-000");
    }

    public static OpeningHour buildOpeningHour() {
        return new OpeningHour(
                1, LocalTime.of(10,0),
                LocalTime.of(20,0));
    }

    public static OpeningHour buildOpeningHour(Integer day) {
        return new OpeningHour(
                day, LocalTime.of(10,0),
                LocalTime.of(20,0));
    }

    public static List<OpeningHour> buildOpeningHours() {
        List<OpeningHour> openingHours = new ArrayList<>();
        IntStream.rangeClosed(1,7).forEach(i -> openingHours.add(buildOpeningHour(i)));
        return openingHours;
    }

    public static RestaurantEntity buildRestaurantEntity() {
        return new RestaurantEntity("Nome do restaurante", "Italiana", 20);
    }

    public static RestaurantEntity buildRestaurantEntityForName(String name) {
        RestaurantEntity restaurantEntity = buildRestaurantEntitySaved();
        restaurantEntity.setName(name);
        return restaurantEntity;
    }

    public static RestaurantEntity buildRestaurantEntityForTypeOfCuisine(String typeOfCuisine) {
        RestaurantEntity restaurantEntity = buildRestaurantEntitySaved();
        restaurantEntity.setTypeOfCuisine(typeOfCuisine);
        return restaurantEntity;
    }

    public static RestaurantModel buildRestaurantModelForName(String name) {
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setId(UUID.randomUUID());
        restaurantModel.setName(name);
        restaurantModel.setCapacity(20);
        restaurantModel.setDateCreate(LocalDateTime.now());
        restaurantModel.setTypeOfCuisine("Italiana");
        OpeningHourModel openingHourModel = new OpeningHourModel(
                UUID.randomUUID(), 1, "DOMINGO",LocalTime.of(10,0),
                LocalTime.of(20,0), restaurantModel);
        HashSet<OpeningHourModel> openingHourModels = new HashSet<>();
        openingHourModels.add(openingHourModel);
        AddressModel addressModel = new AddressModel(UUID.randomUUID(), "Rua Teodoro Sampaio", 100, "Pinheiros",
                "São Paulo", "SP", "01000-000", restaurantModel);
        restaurantModel.setAddress(addressModel);
        restaurantModel.setOpeningHours(openingHourModels);
        return restaurantModel;
    }

    public static RestaurantEntity buildRestaurantEntitySaved() {
        RestaurantEntity restaurantEntity = new RestaurantEntityBuilder()
                .addInfos("Nome do restaurante", "Italiana", 20)
                .addAddress(buildAddres())
                .addOpeningHours(buildOpeningHours())
                .build();
        restaurantEntity.setId(getUUID());
        return restaurantEntity;
    }

    public static OpeningHourRecord buildOpeningHourRecord() {
        return new OpeningHourRecord(1, null, LocalTime.of(10,0),
                LocalTime.of(20,0));
    }

    public static List<OpeningHourRecord> buildOpeningHoursRecords() {
        OpeningHourRecord openingHourRecord = new OpeningHourRecord(1, null, LocalTime.of(10,0),
                LocalTime.of(20,0));
        ArrayList<OpeningHourRecord> list = new ArrayList<>();
        list.add(openingHourRecord);
        return list;
    }

    public static AddressRecord buildAddressRecord() {
        return new AddressRecord("Rua Teodoro Sampaio", 100, "Pinheiros",
                "São Paulo", "SP", "01000-000");
    }

    public static RestaurantRecord buildRestaurantRecord() {
        return new RestaurantRecord("Restaurante da Mama", "Italiana", 20);
    }

    public static RestaurantDto buildRestaurantDtoRequest() {
        AddressDto addressDto = new AddressDto(null, "Rua Teodoro Sampaio", 100,
                "Pinheiros", "São Paulo", "SP", "01000-000");
        OpeningHourDto openingHourDto = new OpeningHourDto(null, 1, null,
                LocalTime.of(10,0,0), LocalTime.of(22,0,0));
        RestaurantDto dto = new RestaurantDto(null, "Restauranté", "Francês", 20,
                null, addressDto, Arrays.asList(openingHourDto));
        return dto;
    }

    public static UUID getUUID() {
        return UUID.fromString("b7ef6c1c-0293-41d9-acb8-406309568ab0");
    }

    public static String toJson(Object obj) {
        try {
            return new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .writeValueAsString(obj);
        } catch (Exception e) {
            return "{}";
        }
    }

    public static EvaluateModel buildEvaluateModel() {
        return new EvaluateModel(getUUID(),
                "Muito bom", 5, Util.buildRestaurantModelForName("Mama Mia"),
                LocalDateTime.of(2024,12,1,10,0,0));
    }

    public static EvaluateEntity buildEvaluateEntity() {
        return new EvaluateEntityBuilder()
                .addInfos("Muito bom", 4, buildRestaurantEntitySaved())
                .build();
    }

    public static EvaluateEntity buildEvaluateEntitySaved() {
        return new EvaluateEntityBuilder()
                .addInfos("Muito bom", 4, buildRestaurantEntitySaved())
                .addId(getUUID())
                .build();
    }

    public static EvaluateRecord buildEvaluateRecord() {
        return new EvaluateRecord("Muito bom", 4, getUUID());
    }

    public static EvaluateDto buildEvaluateDto() {
        EvaluateDto evaluateDto = new EvaluateDto();
        evaluateDto.setEvaluation(4);
        evaluateDto.setComment("Muito bom");
        evaluateDto.setRestaurantId(UUID.fromString("118cd4e6-73fb-43a4-bc36-ed777289c28f"));
        return evaluateDto;
    }

    public static EvaluateDto buildEvaluateDto(String restaurantId) {
        EvaluateDto evaluateDto = new EvaluateDto();
        evaluateDto.setEvaluation(4);
        evaluateDto.setComment("Muito bom");
        evaluateDto.setRestaurantId(UUID.fromString(restaurantId));
        return evaluateDto;
    }

    public static Client buildClient() {
        return new Client("João", 998648471);
    }

    public static ReserveEntity buildReserve() {
        return new ReserveEntity(UUID.fromString("ba94f4d5-b0a6-4745-adac-1456619ecca8"),
                LocalDateTime.now(), 4, "A");
    }

    public static ReserveEntity buildReserveEntity() {
        return new ReserveEntity(UUID.fromString("ba94f4d5-b0a6-4745-adac-1456619ecca8"),
                LocalDateTime.now(), 4, "A");
    }

    public static ReserveEntity buildReserveEntitySaved() {
        ReserveEntity reserveEntity = new ReserveEntityBuilder()
                .addInfos(UUID.fromString("ba94f4d5-b0a6-4745-adac-1456619ecca8"),
                        LocalDateTime.now(), 4, "A")
                .addClient(buildClient())
                .build();
        reserveEntity.setId(getUUID());
        return reserveEntity;
    }

    public static ReserveRecord buildReserveRecord() {
        return new ReserveRecord(UUID.fromString("ba94f4d5-b0a6-4745-adac-1456619ecca8"),
                LocalDateTime.now(), 4, "A");
    }

    public static ClientRecord buildClientRecord() {
        return new ClientRecord("João", 984488778);
    }

    public static ReserveDto buildReserveDtoRequest() {
        ClientDto clientDto = new ClientDto(null, "João", 912341234);
        return new ReserveDto(UUID.fromString("ba94f4d5-b0a6-4745-adac-1456619ecca8"),
                UUID.fromString("ba94f4d5-b0a6-4745-adac-1456619ecca8"),
                LocalDateTime.now(), 4, "A",
                clientDto);
    }

    public static ReserveModel buildReserveModel(UUID id, LocalDateTime date) {
        ReserveModel reserveModel = new ReserveModel();
        reserveModel.setId(id);
        reserveModel.setRestaurantId(UUID.randomUUID());
        reserveModel.setDate(date);
        reserveModel.setSeats(4);
        reserveModel.setStatus("A");
        reserveModel.setClient(new ClientModel(buildClient(), UUID.randomUUID()));
        return reserveModel;
    }

}
