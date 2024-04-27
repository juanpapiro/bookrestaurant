package br.com.bookrestaurant.usecase.restaurant;

import br.com.bookrestaurant.entity.restaurant.OpeningHour;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.utilsbytests.Util;
import br.com.bookrestaurant.entity.restaurant.exception.RestaurantInvalidException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class RestaurantUseCaseTest {

    @InjectMocks
    private RestarantUseCase restarantUseCase;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void close() throws Exception {
        mocks.close();
    }


    @Test
    void testShouldPermitRegisterRestaurant() {
        RestaurantEntity restaurant = restarantUseCase.registerRestaurant(
                "Nome restaurant", "Italiana", 20,
                Util.buildAddres(), Util.buildOpeningHours());
        assertThat(restaurant).isNotNull().isInstanceOf(RestaurantEntity.class);
    }

    @Test
    void testShouldThrowExceptionWhenTryRegisterRestaurantWithDayOpeningHoursPlusSize() {
        List<OpeningHour> openingHours = Util.buildOpeningHours();
        openingHours.add(Util.buildOpeningHour(1));
        assertThatThrownBy(() -> restarantUseCase.registerRestaurant(
                "Nome restaurant", "Italiana", 20,
                Util.buildAddres(), openingHours))
                .isInstanceOf(RestaurantInvalidException.class)
                .hasMessage("Lista de horários de funcionamento deve conter no máximo 7 dias");
    }

    @Test
    void testShouldThrowExceptionWhenTryRegisterRestaurantWithDayOpeningDuplication() {
        assertThatThrownBy(() -> restarantUseCase.registerRestaurant(
                "Nome restaurant", "Italiana", 20,
                Util.buildAddres(), Arrays.asList(Util.buildOpeningHour(), Util.buildOpeningHour())))
                .isInstanceOf(RestaurantInvalidException.class)
                .hasMessage("Lista de horários de funcionamento não deve conter dias repetidos");
    }

}
