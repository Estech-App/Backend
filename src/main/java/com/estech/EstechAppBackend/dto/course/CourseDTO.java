package com.estech.EstechAppBackend.dto.course;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseDTO {

    private Integer id;
    @NotBlank
    private String name;
    private String acronym;
    private String description;

}
