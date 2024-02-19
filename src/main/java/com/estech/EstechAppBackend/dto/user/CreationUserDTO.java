package com.estech.EstechAppBackend.dto.user;

import com.estech.EstechAppBackend.model.Group;
import com.estech.EstechAppBackend.model.Module;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreationUserDTO {

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
