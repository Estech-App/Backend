package com.estech.EstechAppBackend.dto.module.aux;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ModuleCreationDTO {

    private Integer year;
    private String name;
    private String acronym;

}
