package br.com.bookrestaurant.entity.restaurant;

import br.com.bookrestaurant.entity.restaurant.exception.RestaurantInvalidException;
import br.com.bookrestaurant.utilsbytests.Util;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OpeningHourTest {

    @Test
    void testShouldPermitRegisterDayOpeningHour() {
        OpeningHour oppeningHour = Util.buildOpeningHour();
        assertThat(oppeningHour).isNotNull();
        assertThat(oppeningHour.getDayOfTheWeek())
                .isNotNull()
                .isEqualTo(DayOfWeekEnum.findByCode(1).getDesc());
    }

    @Test
    void testShouldThrowExceptionWhenRegisterDayOpeningHourWithCodeNull() {
        assertThatThrownBy(() ->  new OpeningHour(
                null, LocalTime.of(10,0), LocalTime.of(20,0)))
                .isInstanceOf(RestaurantInvalidException.class)
                .hasMessage("Dia da semana é obrigatório");
    }

    @Test
    void testShouldThrowExceptionWhenRegisterDayOpeningHourWithDayCodeInvalid() {
        assertThatThrownBy(() ->  new OpeningHour(
                8, LocalTime.of(10,0), LocalTime.of(20,0)))
                .isInstanceOf(RestaurantInvalidException.class)
                .hasMessage("Dia da semana inválido");
    }

    @Test
    void testShouldThrowExceptionWhenRegisterDayOpeningHourWithHourOpenNull() {
        assertThatThrownBy(() ->  new OpeningHour(
                1, null, LocalTime.of(20,0)))
                .isInstanceOf(RestaurantInvalidException.class)
                .hasMessage("Horário de abertura é obrigatório");
    }

    @Test
    void testShouldThrowExceptionWhenRegisterDayOpeningHourWithHourCloseNull() {
        assertThatThrownBy(() ->  new OpeningHour(
                1, LocalTime.of(10,0), null))
                .isInstanceOf(RestaurantInvalidException.class)
                .hasMessage("Horário de fechamento é obrigatório");
    }

}
