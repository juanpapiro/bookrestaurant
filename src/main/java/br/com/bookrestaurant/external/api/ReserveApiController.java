package br.com.bookrestaurant.external.api;


import br.com.bookrestaurant.entity.reserve.ReserveEntity;
import br.com.bookrestaurant.external.db.DataBaseJpa;
import br.com.bookrestaurant.external.dto.ReserveDto;
import br.com.bookrestaurant.infraestructure.controller.ReserveController;
import com.sun.net.httpserver.HttpsServer;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/reserve")
public class ReserveApiController {
    private DataBaseJpa database;
    private ReserveController controller;

    @Autowired
    public ReserveApiController(DataBaseJpa database,
                              ReserveController controller) {
        this.database = database;
        this.controller = controller;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ReserveDto> register(@RequestBody @Valid ReserveDto reserveDto) {
        ReserveEntity reserveEntity = controller.register(reserveDto.toRecord(),
                reserveDto.getClient().toRecord(), database);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ReserveDto(reserveEntity));
    }

    @Transactional
    @PutMapping
    public ResponseEntity<Void> updateStatus(@RequestParam @Valid UUID id,
                                             @RequestParam @Valid String status) {
        controller.updateStatus(id, status, database);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-restaurant-and-date")
    public ResponseEntity<List<ReserveDto>> findByRestaurantAndDate(@RequestParam @Valid UUID restaurantId,
                                                                    @RequestParam @Valid LocalDateTime date) {
        List<ReserveEntity> reserveEntities = controller.
                findByRestaurantAndDate(restaurantId, date, database);
        return ResponseEntity.ok(reserveEntities.stream().map(ReserveDto::new).toList());
    }
}
