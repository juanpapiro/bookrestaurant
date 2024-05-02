package br.com.bookrestaurant.performance;

import br.com.bookrestaurant.utilsbytests.Util;
import io.gatling.javaapi.core.ActionBuilder;
import io.gatling.javaapi.core.PopulationBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.apache.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class PerformSimulation extends Simulation {

    private static final String ENDPOINT = "/restaurant";
    private static final String FYND_BY_NAME = "/by-name";

    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8015");

    private final HttpProtocolBuilder httpProtocolWithBody = http
            .baseUrl("http://localhost:8015")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

    ActionBuilder actionBuilderAddRestaurant = http("registrar novo restaurante")
            .post(ENDPOINT)
            .body(StringBody(Util.toJson(Util.buildRestaurantDtoRequest())))
            .check(status().is(HttpStatus.CREATED.value()))
            .check(jsonPath("$.name").saveAs("restaurantName"))
            .check(jsonPath("$.typeOfCuisine").saveAs("restaurantTypeOfCuisine"))
            .check(jsonPath("$.address.uf").saveAs("addressUf"))
            .check(jsonPath("$.address.city").saveAs("addressCity"))
            .check(jsonPath("$.address.neighborhood").saveAs("addressNeighborhood"));

    ScenarioBuilder scenarioBuilderAddRestaurant = scenario("Registrar novo restaurante")
            .exec(actionBuilderAddRestaurant);


    ActionBuilder actionBuilderFindRestaurantByName = http("Buscar restaurante por nome")
            .get(ENDPOINT + FYND_BY_NAME)
            .queryParam("name", "#{restaurantName}")
            .check(status().is(HttpStatus.OK.value()));

    ScenarioBuilder scenarioBuilderFindRestaurantByName = scenario("Buscar restaurante por nome")
            .exec(actionBuilderAddRestaurant)
            .exec(actionBuilderFindRestaurantByName);



    {
        setUp(
                configInjectOpenAddRestaurant(scenarioBuilderAddRestaurant, 1, 2, 10, 10, 10, 20),
                configInjectOpenAddRestaurant(scenarioBuilderFindRestaurantByName, 1, 10, 10, 10, 10, 20)
        )
        .protocols(httpProtocolWithBody)
        .assertions(
                global().responseTime().max().lt(2000)
        );
    }

    private PopulationBuilder configInjectOpenAddRestaurant(ScenarioBuilder scenarioBuilder,
                                                            int min, int max, int constant, int timeRampUp,
                                                            int timeRumpDown, int timeConstant) {
        return scenarioBuilder.injectOpen(
                rampUsersPerSec(min).to(max).during(Duration.ofSeconds(timeRampUp)),
                constantUsersPerSec(constant).during(Duration.ofSeconds(timeConstant)),
                rampUsersPerSec(max).to(min).during(Duration.ofSeconds(timeRumpDown))
        );
    }

}
