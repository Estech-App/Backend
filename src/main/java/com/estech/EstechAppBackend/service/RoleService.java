package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.model.Role;
import com.estech.EstechAppBackend.model.enums.RoleEnum;
import com.estech.EstechAppBackend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleByRoleName(RoleEnum roleEnum) {
        return roleRepository.findByRolName(roleEnum).orElse(null);
    }
}
