package br.com.bookrestaurant.external.db;

import br.com.bookrestaurant.external.model.RestaurantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<RestaurantModel, UUID> {

    @Query("SELECT restaurant FROM RestaurantModel restaurant " +
            "JOIN FETCH restaurant.address " +
            "JOIN FETCH restaurant.openingHours " +
            "WHERE lower(restaurant.name) LIKE %:name%")
    List<RestaurantModel> findByName(@Param("name") String name);

    @Query("SELECT restaurant FROM RestaurantModel restaurant " +
            "JOIN FETCH restaurant.address " +
            "JOIN FETCH restaurant.openingHours " +
            "WHERE lower(restaurant.typeOfCuisine) LIKE %:typeOfCuisine%")
    List<RestaurantModel> findByTypeOfCuisine(@Param("typeOfCuisine") String typeOfCuisine);

    @Query("SELECT restaurant FROM RestaurantModel restaurant " +
            "JOIN FETCH restaurant.address " +
            "JOIN FETCH restaurant.openingHours " +
            "WHERE lower(address.uf) LIKE %:uf% AND lower(address.city) LIKE %:city% " +
            "AND lower(address.neighborhood) LIKE %:neighborhood%")
    List<RestaurantModel> findByLocale(@Param("uf") String uf,
                                       @Param("city") String city,
                                       @Param("neighborhood") String neighborhood);


}
