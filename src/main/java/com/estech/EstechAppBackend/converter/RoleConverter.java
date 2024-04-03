package com.estech.EstechAppBackend.converter;

import com.estech.EstechAppBackend.dto.role.RoleDTO;
import com.estech.EstechAppBackend.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter {

    public RoleDTO convertRoleEntityToRoleDTO(Role role) {
        RoleDTO dto = new RoleDTO();

        dto.setId(role.getId());
        dto.setRoleName(role.getRolName().toString());
        dto.setDescription(role.getDescription());
        return dto;
    }

}
