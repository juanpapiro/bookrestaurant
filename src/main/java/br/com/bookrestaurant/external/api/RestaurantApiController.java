package br.com.bookrestaurant.external.api;

import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.external.db.DataBaseJpa;
import br.com.bookrestaurant.external.dto.OpeningHourDto;
import br.com.bookrestaurant.external.dto.RestaurantDto;
import br.com.bookrestaurant.infraestructure.controller.RestaurantController;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<RestaurantDto> register(@RequestBody @Valid RestaurantDto restaurantDto) {
        RestaurantEntity restaurantEntity = controller.register(restaurantDto.toRecord(), restaurantDto.getAddress().toRecord(),
                restaurantDto.getOpeningHours().stream().map(OpeningHourDto::toRecord).toList(), database);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RestaurantDto(restaurantEntity));
    }

    @GetMapping("/by-name")
    public ResponseEntity<List<RestaurantDto>> findByName(@RequestParam String name) {
        List<RestaurantEntity> restaurantEntities = controller.findByName(name, database);
        return ResponseEntity.ok(restaurantEntities.stream().map(RestaurantDto::new).toList());
    }


    @GetMapping("/by-type-of-cuisine")
    public ResponseEntity<List<RestaurantDto>> findByTypeOfCuisine(@RequestParam String typeOfCuisine) {
        List<RestaurantEntity> restaurantEntities = controller.findByTypeOfCuisine(typeOfCuisine, database);
        return ResponseEntity.ok(restaurantEntities.stream().map(RestaurantDto::new).toList());
    }

    @GetMapping("/by-locale")
    public ResponseEntity<List<RestaurantDto>> findByLocale(@RequestParam(required = false) String uf,
                                                            @RequestParam(required = false) String city,
                                                            @RequestParam(required = false) String neighborhood) {
        List<RestaurantEntity> restaurantEntities = controller.findByLocale(uf, city, neighborhood, database);
        return ResponseEntity.ok(restaurantEntities.stream().map(RestaurantDto::new).toList());
    }

}
