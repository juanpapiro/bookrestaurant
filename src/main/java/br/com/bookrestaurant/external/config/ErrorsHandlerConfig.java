package br.com.bookrestaurant.external.config;

import br.com.bookrestaurant.entity.restaurant.exception.RestaurantInvalidException;
import br.com.bookrestaurant.entity.restaurant.exception.RestaurantNotFoundException;
import br.com.bookrestaurant.external.dto.ErrorApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class ErrorsHandlerConfig {

    @ExceptionHandler(RestaurantInvalidException.class)
    public ResponseEntity<ErrorApi> errorRestaurantInvalid(RestaurantInvalidException ex) {
        log.error(ex);
        return ResponseEntity.badRequest().body(new ErrorApi(ex.getMessage()));
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorApi> errorRestaurantNotFound(RestaurantNotFoundException ex) {
        log.error(ex);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorApi> errorRestaurant(Exception ex) {
        log.error(ex);
        return ResponseEntity.internalServerError().build();
    }

}
