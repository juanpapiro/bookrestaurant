package br.com.bookrestaurant.entity.restaurant;

import br.com.bookrestaurant.entity.EntityUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class RestaurantEntity {

    private UUID id;
    private String name;
    private String typeOfCusine;
    private Integer capacity;
    private Address address;
    private List<OpeningHour> openingHours;
    private LocalDateTime dateCreate;


    public RestaurantEntity(String name, String typeOfCusine, Integer capacity) {
        this.name = EntityUtil.isNullOrBlank(name, "Nome é obrigatório");
        this.typeOfCusine = EntityUtil.isNullOrBlank(typeOfCusine, "Tipo de culinária é obrigatório");
        this.capacity = EntityUtil.isNull(capacity, "Capacidade é obrigatória");
        this.dateCreate = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeOfCusine() {
        return typeOfCusine;
    }

    public void setTypeOfCusine(String typeOfCusine) {
        this.typeOfCusine = typeOfCusine;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<OpeningHour> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(List<OpeningHour> openingHours) {
        this.openingHours = openingHours;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

}
