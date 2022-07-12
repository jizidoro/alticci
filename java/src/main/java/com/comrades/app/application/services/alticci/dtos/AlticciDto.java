package com.comrades.app.application.services.alticci.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class AlticciDto {

    @NotNull
    @NotEmpty
    private String calculatedValue;

}
