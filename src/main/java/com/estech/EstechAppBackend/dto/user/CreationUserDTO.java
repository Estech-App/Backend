package com.estech.EstechAppBackend.dto.user;

import com.estech.EstechAppBackend.dto.group.GroupDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreationUserDTO {

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

}
