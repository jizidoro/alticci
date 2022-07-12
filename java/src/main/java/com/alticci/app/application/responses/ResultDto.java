package com.alticci.app.application.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResultDto {

    Boolean success;
    Integer code;
    String message;
}
