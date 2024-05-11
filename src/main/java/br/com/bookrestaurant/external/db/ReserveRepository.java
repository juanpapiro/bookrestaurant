package br.com.bookrestaurant.external.db;

import br.com.bookrestaurant.external.model.ReserveModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

public interface ReserveRepository extends JpaRepository<ReserveModel, UUID> {

    @Query("SELECT reserve FROM ReserveModel reserve " +
            "JOIN FETCH reserve.client " +
            "WHERE reserve.restaurantId = %:restaurantId% " +
            "AND reserve.date >= %:date%")
    List<ReserveModel> findByRestaurantAndDate(@Param("restaurantId") UUID restaurantId,
                                               @Param("date") LocalDateTime date);

    @Modifying
    @Query("UPDATE ReserveModel reserve " +
            "  SET reserve.status = %:status% " +
            "WHERE reserve.id = %:id% ")
    void updateStatus(@Param("id") UUID id, @Param("status") String status);
}
