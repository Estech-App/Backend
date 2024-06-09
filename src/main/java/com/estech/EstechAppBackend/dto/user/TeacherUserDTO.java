package com.estech.EstechAppBackend.dto.user;

import com.estech.EstechAppBackend.dto.group.GroupDTO;
import com.estech.EstechAppBackend.dto.module.ModuleDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TeacherUserDTO {

    private Long id;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String lastname;

    @NotBlank
    private String password;

    @NotBlank
    private String role;

    private List<ModuleDTO> modules;

}
