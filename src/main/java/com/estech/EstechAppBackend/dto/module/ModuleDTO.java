package com.estech.EstechAppBackend.dto.module;

import com.estech.EstechAppBackend.dto.course.CourseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ModuleDTO {

    private Long id;
    private Integer year;
    private String name;
    private String acronym;
    private String courseAcronym;
    private CourseDTO courseDTO;
    private List<String> usersName;

}
