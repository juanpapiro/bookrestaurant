package br.com.bookrestaurant.external.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FieldError {

    private String field;
    private String message;

}
