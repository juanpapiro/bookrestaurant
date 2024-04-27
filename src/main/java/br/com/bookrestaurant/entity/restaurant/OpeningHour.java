package br.com.bookrestaurant.entity.restaurant;

import br.com.bookrestaurant.entity.EntityUtil;

import java.time.LocalTime;
import java.util.Objects;

public class OpeningHour {

    private Integer dayOfTheWeekCode;
    private String dayOfTheWeek;
    private LocalTime hourOpen;
    private LocalTime hourClose;


    public OpeningHour(Integer dayOfTheWeekCode, LocalTime hourOpen, LocalTime hourClose) {
        this.dayOfTheWeekCode = EntityUtil.isNull(dayOfTheWeekCode, "Dia da semana é obrigatório");
        this.dayOfTheWeek = DayOfWeekEnum.findByCode(dayOfTheWeekCode).getDesc();
        this.hourOpen = EntityUtil.isNull(hourOpen, "Horário de abertura é obrigatório");
        this.hourClose = EntityUtil.isNull(hourClose, "Horário de fechamento é obrigatório");
    }

    public Integer getDayOfTheWeekCode() {
        return dayOfTheWeekCode;
    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public LocalTime getHourOpen() {
        return hourOpen;
    }

    public LocalTime getHourClose() {
        return hourClose;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpeningHour that = (OpeningHour) o;
        return dayOfTheWeekCode.equals(that.dayOfTheWeekCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOfTheWeekCode);
    }
}
