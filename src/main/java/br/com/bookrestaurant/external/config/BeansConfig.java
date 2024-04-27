package br.com.bookrestaurant.external.config;

import br.com.bookrestaurant.infraestructure.controller.RestaurantController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public RestaurantController restautantController() {
        return new RestaurantController();
    }

}
