package br.com.bookrestaurant.entity;

import br.com.bookrestaurant.entity.reserve.Client;
import br.com.bookrestaurant.entity.reserve.exception.ReserveInvalidException;
import br.com.bookrestaurant.entity.restaurant.Address;
import br.com.bookrestaurant.entity.restaurant.OpeningHour;
import br.com.bookrestaurant.entity.restaurant.exception.RestaurantInvalidException;
import br.com.bookrestaurant.utilsbytests.Util;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class EntityUtilTest {

    @Test
    void testUUIDisNotNull() {
        UUID id = UUID.randomUUID();
        assertThat(EntityUtil.isNull(id, "")).isEqualTo(id);
    }

    @Test
    void testUUIDisNull() {
        UUID id = null;
        assertThatThrownBy(() -> EntityUtil.isNull(id, ""))
                .isInstanceOf(RestaurantInvalidException.class);
    }

    @Test
    void testAddressIsNotNull() {
        Address address = Util.buildAddres();
        assertThat(EntityUtil.isNull(address, "")).isEqualTo(address);
    }

    @Test
    void testAddressIsNull() {
        Address address = null;
        assertThatThrownBy(() -> EntityUtil.isNull(address, ""))
                .isInstanceOf(RestaurantInvalidException.class);
    }

    @Test
    void testIsNullListOpeningHour() {
        List<OpeningHour> openingHourList = null;
        assertThatThrownBy(() -> EntityUtil.isNull(
                openingHourList, "Horários de funcionamento não podem ser nulos."))
                .isInstanceOf(RestaurantInvalidException.class);
    }

    @Test
    void testIsEmptyListOpeningHour() {
        List<OpeningHour> openingHourList = new ArrayList<>();
        assertThatThrownBy(() -> EntityUtil.isNull(
                openingHourList, "Horários de funcionamento não podem ser nulos."))
                .isInstanceOf(RestaurantInvalidException.class);
    }


    @Test
    void testUUIDReserveisNull() {
        UUID id = null;
        assertThatThrownBy(() -> EntityUtil.isNullReserve(id, ""))
                .isInstanceOf(ReserveInvalidException.class);
    }

    @Test
    void testLocalDateTimeIsNull() {
        LocalDateTime date = null;
        assertThatThrownBy(() -> EntityUtil.isNull(date, ""))
                .isInstanceOf(ReserveInvalidException.class);
    }

    @Test
    void testClientIsNull() {
        Client client = null;
        assertThatThrownBy(() -> EntityUtil.isNull(client, ""))
                .isInstanceOf(ReserveInvalidException.class);
    }
}
