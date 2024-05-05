package br.com.bookrestaurant.external.model;

import br.com.bookrestaurant.entity.restaurant.DayOfWeekEnum;
import br.com.bookrestaurant.utilsbytests.Util;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OpeningHourTest {

    @Test
    void testInstanceAllArgsConstrunctor() {
        UUID id = UUID.randomUUID();
        OpeningHourModel openingHourModel = new OpeningHourModel(id, 1,
                "DOMINGO", LocalTime.MIN, LocalTime.MAX,
                Util.buildRestaurantModelForName("Algum restaurante"));
        assertThat(openingHourModel.getCdOpeningHour()).isEqualTo(id);
        assertThat(openingHourModel.getDayOfTheWeekCode()).isEqualTo(1);
        assertThat(openingHourModel.getDayOfTheWeek()).isEqualTo(DayOfWeekEnum.findByCode(1).getDesc());
        assertThat(openingHourModel.getHourOpen()).isEqualTo(LocalTime.MIN);
        assertThat(openingHourModel.getHourClose()).isEqualTo(LocalTime.MAX);
        assertThat(openingHourModel.getRestaurant()).isNotNull().isInstanceOf(RestaurantModel.class);
    }

}
