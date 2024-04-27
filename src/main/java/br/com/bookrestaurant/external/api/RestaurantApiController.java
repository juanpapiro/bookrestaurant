package br.com.bookrestaurant.external.api;

import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.external.db.DataBaseJpa;
import br.com.bookrestaurant.external.dto.OpeningHourDto;
import br.com.bookrestaurant.external.dto.RestaurantDto;
import br.com.bookrestaurant.infraestructure.controller.RestaurantController;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
public class RestaurantApiController {

    private DataBaseJpa database;
    private RestaurantController controller;

    @Autowired
    public RestaurantApiController(DataBaseJpa database,
                                   RestaurantController controller) {
        this.database = database;
        this.controller = controller;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<RestaurantDto> register(@RequestBody RestaurantDto restaurantDto) {
        RestaurantEntity restaurantEntity = controller.register(restaurantDto.toRecord(), restaurantDto.getAddress().toRecord(),
                restaurantDto.getOpeningHours().stream().map(OpeningHourDto::toRecord).toList(), database);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RestaurantDto(restaurantEntity));
    }


}
