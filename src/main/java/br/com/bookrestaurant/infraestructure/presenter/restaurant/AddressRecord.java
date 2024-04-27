package br.com.bookrestaurant.infraestructure.presenter.restaurant;

public record AddressRecord(
        String street,
        Integer number,
        String neighborhood,
        String city,
        String uf,
        String cep
) {
}
