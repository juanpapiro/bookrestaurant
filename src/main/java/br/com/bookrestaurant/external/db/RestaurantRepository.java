package br.com.bookrestaurant.external.db;

import br.com.bookrestaurant.external.model.RestaurantModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<RestaurantModel, UUID> {
}
