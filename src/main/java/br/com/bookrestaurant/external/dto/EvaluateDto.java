package br.com.bookrestaurant.external.dto;

import br.com.bookrestaurant.entity.evaluate.EvaluateEntity;
import br.com.bookrestaurant.infraestructure.presenter.evaluate.EvaluateRecord;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EvaluateDto {

    private UUID evaluateId;

    @NotBlank
    private String comment;

    @NotNull
    private Integer evaluation;

    @NotNull
    private UUID restaurantId;

    private LocalDateTime dateCreate;

    public EvaluateRecord toRecord() {
        return new EvaluateRecord(this.comment, this.evaluation, this.restaurantId);
    }

    public EvaluateDto(String comment, Integer evaluation, UUID restaurantId) {
        this.comment = comment;
        this.evaluation = evaluation;
        this.restaurantId = restaurantId;
    }

    public EvaluateDto(EvaluateEntity evaluateEntity) {
        this.evaluateId = evaluateEntity.getEvaluateId();
        this.comment = evaluateEntity.getComment();
        this.evaluation = evaluateEntity.getEvaluation();
        this.restaurantId = evaluateEntity.getRestaurantEntity().getId();
        this.dateCreate = evaluateEntity.getDateCreate();
    }

}
