package br.com.bookrestaurant.external.model;

import br.com.bookrestaurant.entity.reserve.Client;
import br.com.bookrestaurant.utilsbytests.Util;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ClientModelTest {

    @Test
    void shouldCreateInstanceClientModelWithClientInConstructor() {
        Client client = Util.buildClient();
        UUID id = UUID.randomUUID();
        ClientModel clientModel = new ClientModel(client, UUID.randomUUID());

        clientModel.setReserve(new ReserveModel());
        clientModel.setCdClient(id);

        assertThat(clientModel.getName()).isNotBlank();
        assertThat(clientModel.getPhone()).isNotNull();
        assertThat(clientModel.getCdClient()).isEqualTo(id);
        assertThat(clientModel.getReserve()).isNotNull().isInstanceOf(ReserveModel.class);
    }

    @Test
    void testAllArgsConstructor() {
        ClientModel clientModel = new ClientModel(UUID.randomUUID(), "Zeinho", 999991234,
                Util.buildReserveModel(Util.getUUID(), LocalDateTime.now()));
        Client client = clientModel.toClient();
        assertThat(clientModel).isNotNull().isInstanceOf(ClientModel.class);
        assertThat(clientModel.getCdClient()).isInstanceOf(UUID.class);
        assertThat(client).isNotNull().isInstanceOf(Client.class);
    }
}
