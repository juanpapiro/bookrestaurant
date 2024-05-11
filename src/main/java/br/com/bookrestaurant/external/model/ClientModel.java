package br.com.bookrestaurant.external.model;

import br.com.bookrestaurant.entity.reserve.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Entity
@Table(name = "tb_client")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID cdClient;
    private String name;
    private Integer phone;

    @OneToOne(fetch = FetchType.LAZY)
    private ReserveModel reserve;

    public ClientModel(Client client, UUID reserveId) {
        BeanUtils.copyProperties(client, this);
        ReserveModel reserveModel = new ReserveModel();
        reserveModel.setId(reserveId);
        this.reserve = reserveModel;
    }

    public Client toClient() {
        return new Client(this.name, this.phone);
    }

}
