package com.estech.EstechAppBackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class idDTO {

    @NotNull
    private Long id;

}
