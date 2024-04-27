package br.com.bookrestaurant.external.api;

import br.com.bookrestaurant.external.dto.RestaurantDto;
import br.com.bookrestaurant.utilsbytests.Util;
import io.restassured.RestAssured;
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

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class RestaurantApiControllerIT {

    @LocalServerPort
    private int port;

    private static final String URL = "http://localhost";
    private static final String ENDPOINT = "/restaurant";

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
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(dto)
                    .when()
                    .post(ENDPOINT)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

}
