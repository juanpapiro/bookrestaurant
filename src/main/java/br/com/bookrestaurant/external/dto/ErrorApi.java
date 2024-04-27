package br.com.bookrestaurant.external.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ErrorApi {

    private String message;
    private List<FieldError> fieldErrors = new ArrayList<>();

    public ErrorApi(String message) {
        this.message = message;
    }

    public void addError(FieldError fieldError) {
        fieldErrors.add(fieldError);
    }

}
