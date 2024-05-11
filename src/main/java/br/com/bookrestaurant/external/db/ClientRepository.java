package br.com.bookrestaurant.external.db;

import br.com.bookrestaurant.external.model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientModel, UUID> {
}
