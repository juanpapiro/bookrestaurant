package br.com.bookrestaurant.external.api;

import br.com.bookrestaurant.external.dto.RestaurantDto;
import br.com.bookrestaurant.utilsbytests.Util;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class RestaurantApiControllerIT {

    @LocalServerPort
    private int port;

    private static final String URL = "http://localhost";
    private static final String ENDPOINT = "/restaurant";
    private static final String ENDPOINT_TYPE_OF_CUISINE = "/by-type-of-cuisine";

    private static final String ENDPOINT_LOCALE = "/by-locale";

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }


    @Nested
    class RegisterRestaurant {
        @Test
        void testShoudRegisterRestaurant() throws Exception {
            given()
                    .filter(new AllureRestAssured())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(Util.buildRestaurantDtoRequest())
                    .when()
                    .post(ENDPOINT)
                    .then()
                    .statusCode(HttpStatus.CREATED.value());
        }

        @Test
        void testShoudRegisterRestaurantBadRequest() throws Exception {
            RestaurantDto dto = Util.buildRestaurantDtoRequest();
            dto.setOpeningHours(Arrays.asList(dto.getOpeningHours().get(0),dto.getOpeningHours().get(0)));
            given()
                    .filter(new AllureRestAssured())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(dto)
                    .when()
                    .post(ENDPOINT)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void testShoudRegisterRestaurantBadRequestArgumentsNotValid() throws Exception {
            given()
                    .filter(new AllureRestAssured())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new RestaurantDto())
                    .when()
                    .post(ENDPOINT)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }


    @Nested
    class FindRestaurant {
        @Test
        void testShouldPermitFindRestaurantByName() throws Exception {
            String name = "Restaurante da Mama";
            given()
                    .filter(new AllureRestAssured())
                    .queryParam("name", name)
                    .when().get(ENDPOINT + "/by-name")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("jsonschemas/restaurants.schema.json"));
        }

        @Test
        void testShouldFindRestaurantByBadRequest() throws Exception {
            String name = "R";
            given()
                    .filter(new AllureRestAssured())
                    .queryParam("name", name)
                    .when().get(ENDPOINT + "/by-name")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(matchesJsonSchemaInClasspath("jsonschemas/error.schema.json"));
        }

        @Test
        void testShouldFindRestaurantByNotFound() throws Exception {
            String name = "Mamamia";
            given()
                    .filter(new AllureRestAssured())
                    .queryParam("name", name)
                    .when().get(ENDPOINT + "/by-name")
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }
        @Test
        void testShouldPermitFindRestaurantByTypeCousine() {
            String typeOfCuisine = "Italiana";
            given()
                    .filter(new AllureRestAssured())
                    .queryParam("typeOfCuisine", typeOfCuisine)
                    .when().get(ENDPOINT + ENDPOINT_TYPE_OF_CUISINE)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("jsonschemas/restaurants.schema.json"));
        }

        @Test
        void testShouldPermitFindRestaurantByTypeCousineBadRequest() {
            String typeOfCuisine = "por";
            given()
                    .filter(new AllureRestAssured())
                    .queryParam("typeOfCuisine", typeOfCuisine)
                    .when().get(ENDPOINT + ENDPOINT_TYPE_OF_CUISINE)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(matchesJsonSchemaInClasspath("jsonschemas/error.schema.json"));
        }
        @Test
        void testShouldPermitFindRestaurantByTypeCousineNotFound() {
            String typeOfCuisine = "Portuguesa";
            given()
                    .filter(new AllureRestAssured())
                    .queryParam("typeOfCuisine", typeOfCuisine)
                    .when().get(ENDPOINT + ENDPOINT_TYPE_OF_CUISINE)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        void testShouldPermitFindRestaurantByLocation() {
            RequestSpecification requestSpecification = given()
                    .queryParam("uf", "SP")
                    .queryParam("city", "Embu")
                    .queryParam("neighborhood", "Jardim São Vicente")
                    .request();
            requestSpecification.filter(new AllureRestAssured());
            requestSpecification.basePath(ENDPOINT);
            Response response = requestSpecification.get(ENDPOINT_LOCALE);
            response.then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("jsonschemas/restaurants.schema.json"));
        }

        @Test
        void testShouldPermitFindRestaurantByLocationBadRequest() {
            given()
                    .filter(new AllureRestAssured())
                    .when().get(ENDPOINT + ENDPOINT_LOCALE)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(matchesJsonSchemaInClasspath("jsonschemas/error.schema.json"))
                    .body("$", hasKey("message"))
                    .body("message", equalTo("UF ou cidade ou bairro deve ser informado."));
        }

        @Test
        void testShouldPermitFindRestaurantByLocationNotFound() {
            String uf = "RJ";
            given()
                    .filter(new AllureRestAssured())
                    .queryParam("uf", uf)
                    .when().get(ENDPOINT + ENDPOINT_LOCALE)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }
    }

}
