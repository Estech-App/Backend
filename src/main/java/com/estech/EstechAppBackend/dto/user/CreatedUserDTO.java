package com.estech.EstechAppBackend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreatedUserDTO {

    private String email;
    private String name;
    private String lastname;
    private String role;

}
