package br.com.bookrestaurant.external.api;

import br.com.bookrestaurant.external.dto.EvaluateDto;
import br.com.bookrestaurant.utilsbytests.Util;
import io.qameta.allure.restassured.AllureRestAssured;
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

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class EvaluateApiControllerIT {

    @LocalServerPort
    private int port;

    private static final String ENDPOINT = "/evaluate";

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    class RegisterEvaluate {
        @Test
        void testShouldPermitRegisterEvaluate() {
            given()
                    .filter(new AllureRestAssured())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(Util.buildEvaluateDto())
                    .when()
//                    .log().all()
                    .post(ENDPOINT)
                    .then()
//                    .log().all()
                    .statusCode(HttpStatus.CREATED.value())
                    .body(matchesJsonSchemaInClasspath("jsonschemas/evaluate.schema.json"));
        }
        @Test
        void testShouldNotPermitRegisterEvaluateWhenRestaurantNotFound() {
            EvaluateDto evaluateDto = Util.buildEvaluateDto();
            evaluateDto.setRestaurantId(Util.getUUID());
            given()
                    .filter(new AllureRestAssured())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(evaluateDto)
                    .when()
                    .post(ENDPOINT)
                    .then()
//                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(matchesJsonSchemaInClasspath("jsonschemas/error.schema.json"));
        }
    }

}
