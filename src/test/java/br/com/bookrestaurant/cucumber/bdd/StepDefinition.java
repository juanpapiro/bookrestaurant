package br.com.bookrestaurant.cucumber.bdd;

import br.com.bookrestaurant.external.dto.EvaluateDto;
import br.com.bookrestaurant.external.dto.RestaurantDto;
import br.com.bookrestaurant.utilsbytests.Util;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StepDefinition {

    private Response response;

    private Response responseEvaluate;

    private RestaurantDto restaurantDtoResponse;

    private EvaluateDto evaluateDtoResponse;

    private static final String URL = "http://localhost:8015";

    private static final String ENDPOINT_RESTAURANT = "/restaurant";
    private static final String ENDPOINT_FIND_BY_NAME = "/restaurant/by-name";
    private static final String ENDPOINT_FIND_TYPE_OF_CUISINE = "/restaurant/by-type-of-cuisine";
    private static final String ENDPOINT_FIND_LOCALE = "/restaurant/by-locale";

    private static final String ENDPOINT_EVALUATE = "/evaluate";

    @Quando("registrar um novo restaurante")
    public RestaurantDto registrar_um_novo_restaurante() {
        RestaurantDto restaurantRequest = Util.buildRestaurantDtoRequest();
        response = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(restaurantRequest)
                    .when()
                    .post(URL + ENDPOINT_RESTAURANT);
        return response.then().extract().as(RestaurantDto.class);
    }
    @Então("o restaurante é registrado com sucesso")
    public void o_restaurante_eh_registrado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }
    @Então("deve ser apresentado")
    public void deve_ser_apresentada() {
        response.then()
                .body(matchesJsonSchemaInClasspath("jsonschemas/restaurant.schema.json"));
    }

    @Dado("que um restaurante já foi registrado")
    public void que_um_restaurante_ja_foi_registrado() {
        restaurantDtoResponse = registrar_um_novo_restaurante();
    }
    @Quando("efetuar a busca do restaurante por nome")
    public void efetuar_a_busca_do_restaurante_por_nome() {
        response = given()
                .queryParam("name", restaurantDtoResponse.getName())
                .when()
                .get(URL + ENDPOINT_FIND_BY_NAME);
    }
    @Então("o restaurante é exibido com sucesso")
    public void o_restaurante_eh_exibido_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("jsonschemas/restaurants.schema.json"));
    }

    @Quando("efetuar a busca do restaurante por nome inválido")
    public void efetuar_a_busca_do_restaurante_por_nome_inválido() {
        response = given()
                .queryParam("name", "n")
                .when()
                .get(URL + ENDPOINT_FIND_BY_NAME);
    }
    @Então("o response status é bad request")
    public void o_response_status_é_bad_request() {
        response.then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Quando("efetuar a busca do restaurante por nome inexistente")
    public void efetuar_a_busca_do_restaurante_por_nome_inexistente() {
        response = given()
                .queryParam("name", "nome")
                .when()
                .get(URL + ENDPOINT_FIND_BY_NAME);
    }
    @Então("o response status é not found")
    public void o_response_status_é_not_found() {
        response.then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Quando("efetuar a busca do restaurante por tipo de cozinha")
    public void efetuar_a_busca_do_restaurante_por_tipo_de_cozinha() {
        response = given()
                .queryParam("typeOfCuisine", restaurantDtoResponse.getTypeOfCuisine())
                .when()
                .get(URL + ENDPOINT_FIND_TYPE_OF_CUISINE);
    }

    @Quando("efetuar a busca do restaurante por uf ou cidade ou bairro")
    public void efetuar_a_busca_do_restaurante_por_uf_ou_cidade_ou_bairro() {
        response = given()
                .queryParam("uf", restaurantDtoResponse.getAddress().getUf())
                .queryParam("city", restaurantDtoResponse.getAddress().getCity())
                .queryParam("neighborhood", restaurantDtoResponse.getAddress().getNeighborhood())
                .when()
                .get(URL + ENDPOINT_FIND_LOCALE);
      }





    @Dado("uma avaliação sobre ele for registrada")
    public void uma_avaliacao_sobre_ele_for_registrada() {
        EvaluateDto evaluateDto = new EvaluateDto("Muito bom", 4, restaurantDtoResponse.getId());
        responseEvaluate = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(evaluateDto)
                .when()
                .post(URL + ENDPOINT_EVALUATE);
    }
    @Então("a avaliação é registrada com sucesso")
    public void a_avaliacao_eh_registrada_com_sucesso() {
        responseEvaluate.then().statusCode(HttpStatus.CREATED.value());
    }

    @Então("avaliação deve ser apresentada")
    public void avaliacao_deve_ser_apresentada() {
        responseEvaluate.then()
                .body(matchesJsonSchemaInClasspath("jsonschemas/evaluate.schema.json"));
    }

    @Quando("feita uma nova solicitação de registro de avaliação")
    public void feita_uma_nova_solicitacao_de_registro_de_avaliacao() {
        EvaluateDto evaluateDto = new EvaluateDto("Muito bom", 4, UUID.randomUUID());
        responseEvaluate = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(evaluateDto)
                .when()
                .post(URL + ENDPOINT_EVALUATE);
    }
    @Quando("nenhum restaurante correspondente for localizado")
    public void nenhum_restaurante_correspondente_for_localizado() {
        assertThat(responseEvaluate.then().extract().body().jsonPath().getString("message"))
                .isEqualTo("Restaurante avaliado não existe");
    }
    @Então("a avaliação não é registrada com sucesso")
    public void a_avaliacao_nao_eh_registrada_com_sucesso() {
        responseEvaluate.then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

}
