package br.com.bookrestaurant.external.dto;

import lombok.*;

@Getter
@AllArgsConstructor
public class FieldError {

    private String field;
    private String message;

}
