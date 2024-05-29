package com.estech.EstechAppBackend.converter;

import com.estech.EstechAppBackend.converter.group.GroupConverter;
import com.estech.EstechAppBackend.dto.user.CreatedUserDTO;
import com.estech.EstechAppBackend.dto.user.CreationUserDTO;
import com.estech.EstechAppBackend.dto.user.StudentUserDTO;
import com.estech.EstechAppBackend.dto.user.UserInfoDTO;
import com.estech.EstechAppBackend.exceptions.AppException;
import com.estech.EstechAppBackend.model.Group;
import com.estech.EstechAppBackend.model.UserEntity;
import com.estech.EstechAppBackend.model.enums.RoleEnum;
import com.estech.EstechAppBackend.repository.GroupRepository;
import com.estech.EstechAppBackend.repository.RoleRepository;
import com.estech.EstechAppBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupConverter groupConverter;

    public UserEntity convertCreationUserDTOToUserEntity(CreationUserDTO creationUserDTO) {
        UserEntity userEntity = new UserEntity();

        if (creationUserDTO.getId() != null) {
            userEntity.setId(creationUserDTO.getId());
        }
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

    public UserEntity studentDTOToUserEntity(StudentUserDTO studentUserDTO) {
        UserEntity userEntity = new UserEntity();

        if (studentUserDTO.getId() != null) {
            userEntity.setId(studentUserDTO.getId());
        }
        userEntity.setEmail(studentUserDTO.getEmail());
        userEntity.setName(studentUserDTO.getName());
        userEntity.setLastname(studentUserDTO.getLastname());
        userEntity.setPassword(passwordEncoder.encode(studentUserDTO.getPassword()));
        userEntity.setIsActive(true);

        List<Group> groups = new ArrayList<>();
        if (studentUserDTO.getGroups() != null) {
            studentUserDTO.getGroups().forEach(groupDTO -> {
                Group group = groupRepository.findById(groupDTO.getId())
                        .orElseThrow(() -> new AppException("Group with id " + groupDTO.getId() + " not found", HttpStatus.NOT_FOUND));
                groups.add(group);
            });
        }
        userEntity.setGroups(groups);

        userEntity.setRole(roleRepository.findByRolName(RoleEnum.STUDENT).get());

        return userEntity;
    }

    public StudentUserDTO toStudentUserDto(UserEntity user) {

        StudentUserDTO studentUserDTO = new StudentUserDTO();

        studentUserDTO.setId(user.getId());
        studentUserDTO.setName(user.getName());
        studentUserDTO.setLastname(user.getLastname());
        studentUserDTO.setEmail(user.getEmail());
        studentUserDTO.setPassword("");

        if (user.getGroups() != null) {
            studentUserDTO.setGroups(groupConverter.toGroupDtos(user.getGroups()));
        }

        studentUserDTO.setRole(RoleEnum.STUDENT.name());

        return StudentUserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .lastname(user.getLastname())
                .password("")
                .groups(groupConverter.toGroupDtos(user.getGroups()))
                .build();
    }

    public CreatedUserDTO convertCreationUserDTOToCreatedUserDTO(CreationUserDTO creationUserDTO) {
        CreatedUserDTO createdUserDTO = new CreatedUserDTO();

        createdUserDTO.setId(createdUserDTO.getId());
        createdUserDTO.setEmail(creationUserDTO.getEmail());
        createdUserDTO.setName(creationUserDTO.getName());
        createdUserDTO.setLastname(creationUserDTO.getLastname());
        createdUserDTO.setRole(creationUserDTO.getRole());

        return createdUserDTO;
    }

    public CreatedUserDTO convertUserEntityToCreatedUserDTO(UserEntity userEntity) {
        CreatedUserDTO createdUserDTO = new CreatedUserDTO();

        createdUserDTO.setId(userEntity.getId());
        createdUserDTO.setEmail(userEntity.getEmail());
        createdUserDTO.setName(userEntity.getName());
        createdUserDTO.setLastname(userEntity.getLastname());
        createdUserDTO.setRole(userEntity.getRole().getRolName().name());

        return createdUserDTO;
    }

    public UserInfoDTO convertUserEntityToUserInfoDTO(UserEntity userEntity) {
        UserInfoDTO user = new UserInfoDTO();

        user.setId(userEntity.getId());
        user.setEmail(userEntity.getEmail());
        user.setName(userEntity.getName());
        user.setLastname(userEntity.getLastname());
        return user;
    }

    public List<UserEntity> fromUserInfoDtostoUserEntities(List<UserInfoDTO> userEntities) {
        List<UserEntity> users = new ArrayList<>();
        userEntities.forEach(userInfoDTO -> {
            UserEntity user = userRepository.findById(userInfoDTO.getId())
                    .orElseThrow(() -> new AppException("User with id " + userInfoDTO.getId() + " not found", HttpStatus.NOT_FOUND));
            users.add(user);
        });
        return users;
    }

    public CreationUserDTO convertUserEntityToCreationUserDTO(UserEntity user) {
        CreationUserDTO dto = new CreationUserDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setLastname(user.getLastname());
        dto.setPassword(user.getPassword());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().getRolName().toString());
        return dto;
    }

}
