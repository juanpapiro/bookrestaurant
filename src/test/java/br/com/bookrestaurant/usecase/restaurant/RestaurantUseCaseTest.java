package br.com.bookrestaurant.usecase.restaurant;

import br.com.bookrestaurant.entity.restaurant.OpeningHour;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.entity.restaurant.exception.RestaurantNotFoundException;
import br.com.bookrestaurant.utilsbytests.Util;
import br.com.bookrestaurant.entity.restaurant.exception.RestaurantInvalidException;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.*;

public class RestaurantUseCaseTest {


    @Nested
    class RegisterRestaurant {
        @Test
        @Severity(SeverityLevel.BLOCKER)
        void testShouldPermitRegisterRestaurant() {
            RestaurantEntity restaurant = RestaurantUseCase.registerRestaurant(
                    "Nome restaurant", "Italiana", 20,
                    Util.buildAddres(), Util.buildOpeningHours());
            assertThat(restaurant).isNotNull().isInstanceOf(RestaurantEntity.class);
        }

        @Test
        @Severity(SeverityLevel.MINOR)
        void testShouldThrowExceptionWhenTryRegisterRestaurantWithDayOpeningHoursPlusSize() {
            List<OpeningHour> openingHours = Util.buildOpeningHours();
            openingHours.add(Util.buildOpeningHour(1));
            assertThatThrownBy(() -> RestaurantUseCase.registerRestaurant(
                    "Nome restaurant", "Italiana", 20,
                    Util.buildAddres(), openingHours))
                    .isInstanceOf(RestaurantInvalidException.class)
                    .hasMessage("Lista de horários de funcionamento deve conter no máximo 7 dias");
        }

        @Test
        @Severity(SeverityLevel.MINOR)
        void testShouldThrowExceptionWhenTryRegisterRestaurantWithDayOpeningDuplication() {
            assertThatThrownBy(() -> RestaurantUseCase.registerRestaurant(
                    "Nome restaurant", "Italiana", 20,
                    Util.buildAddres(), Arrays.asList(Util.buildOpeningHour(), Util.buildOpeningHour())))
                    .isInstanceOf(RestaurantInvalidException.class)
                    .hasMessage("Lista de horários de funcionamento não deve conter dias repetidos");
        }
    }

    @Nested
    class FindRestaurant {
        @Test
        @Severity(SeverityLevel.BLOCKER)
        void testShouldPermitFindRestaurantByName() {
            String name = "Restaurante da Mama";
            name = RestaurantUseCase.findByName(name);
            List<RestaurantEntity> restaurants = Arrays.asList(
                    Util.buildRestaurantEntityForName(name));
            restaurants = RestaurantUseCase.locateRestaurants(restaurants);
            assertThat(restaurants).isNotNull().asList()
                    .hasSize(1)
                    .element(0)
                    .extracting("name").isEqualTo(name);
        }

        @Test
        @Severity(SeverityLevel.MINOR)
        void testShouldThrowExceptionFindRestaurantByNameWhenNotValidName() {
            String name = "R";
            assertThatThrownBy(() -> RestaurantUseCase.findByName(name))
                    .isInstanceOf(RestaurantInvalidException.class)
                    .hasMessage("Nome inválido!");
        }

        @Test
        @Severity(SeverityLevel.MINOR)
        void testShouldThrowExceptionFindRestaurantByNameWhenListNullOrEmpty() {
            List<RestaurantEntity> restaurants = Arrays.asList();
            assertThatThrownBy(() -> RestaurantUseCase.locateRestaurants(restaurants))
                    .isInstanceOf(RestaurantNotFoundException.class)
                    .hasMessage("Restaurante não localizado!");
            assertThatThrownBy(() -> RestaurantUseCase.locateRestaurants(null))
                    .isInstanceOf(RestaurantNotFoundException.class)
                    .hasMessage("Restaurante não localizado!");
        }

        @Test
        @Severity(SeverityLevel.BLOCKER)
        void testShouldPermitFindRestaurantByTypeCousine() {
            String typeOfCuisine = "Italiana";
            typeOfCuisine = RestaurantUseCase.findByLocale(typeOfCuisine);
            List<RestaurantEntity> restaurants = Arrays.asList(
                    Util.buildRestaurantEntityForTypeOfCuisine(typeOfCuisine));
            restaurants = RestaurantUseCase.locateRestaurants(restaurants);
            assertThat(restaurants).isNotNull().asList()
                    .hasSize(1)
                    .element(0)
                    .extracting("typeOfCuisine").isEqualTo(typeOfCuisine);
        }

        @Test
        @Severity(SeverityLevel.MINOR)
        void testShouldPermitFindRestaurantByTypeCousineWhenListNullOrEmpty() {
            String typeOfCuisine = "Ital";
            typeOfCuisine = RestaurantUseCase.findByLocale(typeOfCuisine);
            List<RestaurantEntity> restaurants = Arrays.asList();
            assertThatThrownBy(() ->  RestaurantUseCase.locateRestaurants(restaurants))
                    .isInstanceOf(RestaurantNotFoundException.class)
                    .hasMessage("Restaurante não localizado!");
            assertThatThrownBy(() ->  RestaurantUseCase.locateRestaurants(null))
                    .isInstanceOf(RestaurantNotFoundException.class)
                    .hasMessage("Restaurante não localizado!");
        }

        @Test
        @Severity(SeverityLevel.MINOR)
        void testShouldPermitFindRestaurantByTypeCousineWhenNotValidTypeOfCuisine() {
            String typeOfCuisine = "Ita";
            assertThatThrownBy(() -> RestaurantUseCase.findByLocale(typeOfCuisine))
                    .isInstanceOf(RestaurantInvalidException.class)
                    .hasMessage("Tipo de cozinha inválido!");
        }

        @Test
        @Severity(SeverityLevel.BLOCKER)
        void testShouldPermitFindRestaurantByLocation() {
            String uf = null;
            String city = "Embu das Artes  ";
            String neighborhood = "Jardim São Vicente";
            Map<String,String> params =  RestaurantUseCase.findByLocale(uf, city, neighborhood);
            assertThat(params).isNotNull();
            assertThat(params.get("uf")).isNotNull().isEqualTo("");
            assertThat(params.get("city")).isNotNull().isEqualTo("embu das artes");
            assertThat(params.get("neighborhood")).isNotNull().isEqualTo("jardim são vicente");
        }
        @Test
        @Severity(SeverityLevel.MINOR)
        void testShouldPermitFindRestaurantByLocationParamNotValid() {
            assertThatThrownBy(() -> RestaurantUseCase.findByLocale(null, null, null))
                    .isInstanceOf(RestaurantInvalidException.class)
                    .hasMessage("UF ou cidade ou bairro deve ser informado.");
        }

    }

}
