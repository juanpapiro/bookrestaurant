package br.com.bookrestaurant.infraestructure.presenter.restaurant;

import java.time.LocalTime;

public record OpeningHourRecord(
        Integer dayOfTheWeekCode,
        String dayOfTheWeek,
        LocalTime hourOpen,
        LocalTime hourClose
) {
}
