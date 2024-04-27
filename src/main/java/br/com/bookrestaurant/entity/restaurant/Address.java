package br.com.bookrestaurant.entity.restaurant;

import br.com.bookrestaurant.entity.EntityUtil;

import java.util.Optional;

public class Address {

    private String street;
    private Integer number;
    private String neighborhood;
    private String city;
    private String uf;
    private String cep;

    private static final String REGEX_CEP = "\\d{5}\\-\\d{3}";


    public Address(String street, Integer number, String neighborhood, String city, String uf, String cep) {
        this.street = EntityUtil.isNullOrBlank(street, "Nome da rua é obrigatório");
        this.number = EntityUtil.isNull(number, "Número é obrigatório");
        this.neighborhood = EntityUtil.isNullOrBlank(neighborhood, "Bairro é obrigatório");
        this.city = EntityUtil.isNullOrBlank(city, "Cidade é obrigatória");
        this.uf = EntityUtil.isNullOrBlank(uf, "Estado é obrigatório");
        this.cep = EntityUtil.isNullOrBlank(cep, "Cep é obrigatório");
        EntityUtil.validMatches(cep, REGEX_CEP, "Formato de cep inválido");
    }

    public String getStreet() {
        return street;
    }

    public Integer getNumber() {
        return number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() {
        return city;
    }

    public String getUf() {
        return uf;
    }

    public String getCep() {
        return cep;
    }
}
