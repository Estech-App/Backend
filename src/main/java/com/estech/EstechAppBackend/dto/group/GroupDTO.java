package com.estech.EstechAppBackend.dto.group;

import com.estech.EstechAppBackend.dto.course.CourseDTO;
import com.estech.EstechAppBackend.dto.user.UserInfoDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupDTO {

    private Long id;

    @NotNull
    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Integer year;

    @NotNull
    private Long courseId;

    private Long roomId;

    private List<UserInfoDTO> users;

}
