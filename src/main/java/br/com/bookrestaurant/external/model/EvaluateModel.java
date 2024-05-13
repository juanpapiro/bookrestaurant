package br.com.bookrestaurant.external.model;

import br.com.bookrestaurant.entity.evaluate.EvaluateEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_evaluates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvaluateModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID evaluateId;

    private String comment;

    private Integer evaluation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private RestaurantModel restaurant;

    private LocalDateTime dateCreate;


    public EvaluateModel(EvaluateEntity evaluateEntity) {
        this.comment = evaluateEntity.getComment();
        this.evaluation = evaluateEntity.getEvaluation();
        this.restaurant = new RestaurantModel(evaluateEntity.getRestaurantEntity().getId());
        this.dateCreate = evaluateEntity.getDateCreate();
    }

}
