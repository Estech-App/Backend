package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.RoleConverter;
import com.estech.EstechAppBackend.dto.role.RoleDTO;
import com.estech.EstechAppBackend.model.Role;
import com.estech.EstechAppBackend.model.enums.RoleEnum;
import com.estech.EstechAppBackend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleConverter roleConverter;

    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    public List<RoleDTO> getAllRoles() {
        List<RoleDTO> roles = new ArrayList<>();

        roleRepository.findAll().forEach(role -> {
            roles.add(roleConverter.convertRoleEntityToRoleDTO(role));
        });
        return roles;
    }

    public Role getRoleByRoleName(RoleEnum roleEnum) {
        return roleRepository.findByRolName(roleEnum).orElse(null);
    }
}
