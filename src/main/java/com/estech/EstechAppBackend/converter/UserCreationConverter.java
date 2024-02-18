package com.estech.EstechAppBackend.converter;

import com.estech.EstechAppBackend.dto.CreatedUserDTO;
import com.estech.EstechAppBackend.dto.CreationUserDTO;
import com.estech.EstechAppBackend.model.UserEntity;
import com.estech.EstechAppBackend.model.enums.RoleEnum;
import com.estech.EstechAppBackend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserCreationConverter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public UserEntity convertCreationUserDTOToUserEntity(CreationUserDTO creationUserDTO) {
        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(creationUserDTO.getEmail());
        userEntity.setName(creationUserDTO.getName());
        userEntity.setLastname(creationUserDTO.getLastname());
        userEntity.setPassword(passwordEncoder.encode(creationUserDTO.getPassword()));
        userEntity.setIsActive(true);

        switch (creationUserDTO.getRole().toUpperCase()) {
            case "ADMIN":
                userEntity.setRole(roleRepository.findByRolName(RoleEnum.ADMIN).get());
                break;
            case "SECRETARY":
                userEntity.setRole(roleRepository.findByRolName(RoleEnum.SECRETARY).get());
                break;
            case "TEACHER":
                userEntity.setRole(roleRepository.findByRolName(RoleEnum.TEACHER).get());
                break;
            default:
                userEntity.setRole(roleRepository.findByRolName(RoleEnum.STUDENT).get());
                break;
        }

        return userEntity;
    }

    public CreatedUserDTO convertCreationUserDTOToCreatedUserDTO(CreationUserDTO creationUserDTO) {
        CreatedUserDTO createdUserDTO = new CreatedUserDTO();

        createdUserDTO.setEmail(creationUserDTO.getEmail());
        createdUserDTO.setName(creationUserDTO.getName());
        createdUserDTO.setLastname(creationUserDTO.getLastname());
        createdUserDTO.setRole(creationUserDTO.getRole());

        return createdUserDTO;
    }

    public CreatedUserDTO convertUserEntityToCreatedUserDTO(UserEntity userEntity) {
        CreatedUserDTO createdUserDTO = new CreatedUserDTO();

        createdUserDTO.setEmail(userEntity.getEmail());
        createdUserDTO.setName(userEntity.getName());
        createdUserDTO.setLastname(userEntity.getLastname());
        createdUserDTO.setRole(userEntity.getRole().getRolName().name());

        return createdUserDTO;
    }

}
