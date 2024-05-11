package br.com.bookrestaurant.external.dto;

import br.com.bookrestaurant.entity.reserve.Client;
import br.com.bookrestaurant.infraestructure.presenter.reserve.ClientRecord;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private Integer phone;

    public ClientRecord toRecord() {
        return new ClientRecord(name, phone);
    }

    public ClientDto(Client client) {
        BeanUtils.copyProperties(client, this);
    }
}
