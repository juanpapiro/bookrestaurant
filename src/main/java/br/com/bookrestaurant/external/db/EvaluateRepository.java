package br.com.bookrestaurant.external.db;

import br.com.bookrestaurant.external.model.EvaluateModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EvaluateRepository extends JpaRepository<EvaluateModel, UUID> {
}
