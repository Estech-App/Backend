package com.estech.EstechAppBackend.dto.module;

import com.estech.EstechAppBackend.dto.course.CourseDTO;
import com.estech.EstechAppBackend.dto.user.UserInfoDTO;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CreationModuleDTO {

    private Long id;
    private Integer year;
    private String name;
    private String acronym;
    private CourseDTO course;
    private List<UserInfoDTO> users;

}
