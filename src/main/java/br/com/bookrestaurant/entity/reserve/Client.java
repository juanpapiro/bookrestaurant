package br.com.bookrestaurant.entity.reserve;

import br.com.bookrestaurant.entity.EntityUtil;

public class Client {
    private String name;
    private Integer phone;

    public Client(String name, Integer phone) {
        this.name = EntityUtil.isNullOrBlank(name, "Nome é obrigatório");
        this.phone = EntityUtil.isNull(phone, "Contato é obrigatório");
    }

    public String getName() {
        return this.name;
    }

    public Integer getPhone() {
        return this.phone;
    }
}
