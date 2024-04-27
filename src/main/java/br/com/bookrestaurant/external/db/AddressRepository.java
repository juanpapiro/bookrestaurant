package br.com.bookrestaurant.external.db;

import br.com.bookrestaurant.external.model.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<AddressModel, UUID> {
}
