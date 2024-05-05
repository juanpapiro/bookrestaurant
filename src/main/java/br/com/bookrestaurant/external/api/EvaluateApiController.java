package br.com.bookrestaurant.external.api;


import br.com.bookrestaurant.entity.evaluate.EvaluateEntity;
import br.com.bookrestaurant.external.db.DataBaseJpa;
import br.com.bookrestaurant.external.dto.EvaluateDto;
import br.com.bookrestaurant.infraestructure.controller.EvaluateController;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/evaluate")
public class EvaluateApiController {

    private final DataBaseJpa database;
    private final EvaluateController evaluateController;

    @Autowired
    public EvaluateApiController(DataBaseJpa database,
                                 EvaluateController evaluateController) {
        this.database = database;
        this.evaluateController = evaluateController;
    }

    @PostMapping
    public ResponseEntity<EvaluateDto> evaluateRestaurant(@RequestBody @Valid EvaluateDto evaluateDto) {
        log.info("Registrando nova avaliação de restaurante...", evaluateDto);
        EvaluateEntity evaluateEntity = evaluateController.register(evaluateDto.toRecord(), database);
        log.info("Avaliação de restaurante finalizada com sucesso", evaluateEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(new EvaluateDto(evaluateEntity));
    }

}
