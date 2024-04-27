package br.com.bookrestaurant.entity.restaurant;

import br.com.bookrestaurant.entity.restaurant.exception.RestaurantInvalidException;

import java.util.stream.Stream;

public enum DayOfWeekEnum {

    DOM(1,"DOMINGO"),
    SEG(2,"SEGUNDA"),
    TER(3,"TERÇA"),
    QUA(4,"QUARTA"),
    QUI(5,"QUINTA"),
    SEX(6,"SEXTA"),
    SAB(7,"SÁBADO");

    private Integer code;
    private String desc;

    private DayOfWeekEnum(Integer code, String description) {
        this.code = code;
        this.desc = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static DayOfWeekEnum findByCode(int code) {
        return Stream.of(DayOfWeekEnum.values())
                .filter(dayOfWeek -> dayOfWeek.getCode().intValue() == code)
                .findFirst()
                .orElseThrow(() -> new RestaurantInvalidException("Dia da semana inválido"));
    }

}
