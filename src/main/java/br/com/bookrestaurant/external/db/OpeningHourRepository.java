package br.com.bookrestaurant.external.db;

import br.com.bookrestaurant.external.model.OpeningHourModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OpeningHourRepository extends JpaRepository<OpeningHourModel, UUID> {
}
