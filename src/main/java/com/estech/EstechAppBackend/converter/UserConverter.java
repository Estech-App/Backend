package com.estech.EstechAppBackend.converter;

import com.estech.EstechAppBackend.converter.group.GroupConverter;
import com.estech.EstechAppBackend.dto.module.ModuleDTO;
import com.estech.EstechAppBackend.dto.user.*;
import com.estech.EstechAppBackend.exceptions.AppException;
import com.estech.EstechAppBackend.model.Group;
import com.estech.EstechAppBackend.model.Module;
import com.estech.EstechAppBackend.model.UserEntity;
import com.estech.EstechAppBackend.model.enums.RoleEnum;
import com.estech.EstechAppBackend.repository.GroupRepository;
import com.estech.EstechAppBackend.repository.ModuleRepository;
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
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private ModuleConverter moduleConverter;

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
        if (studentUserDTO.getPassword() != null) {
            userEntity.setPassword(passwordEncoder.encode(studentUserDTO.getPassword()));
        } else {
            userEntity.setPassword(passwordEncoder.encode("1234"));
        }
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

        studentUserDTO.setRole(user.getRole().getRolName().name());

        return studentUserDTO;
    }

    public UserEntity teacherDtoToUserEntity(TeacherUserDTO teacherUserDTO) {
        UserEntity user = new UserEntity();

        if (teacherUserDTO.getId() != null) {
            user.setId(teacherUserDTO.getId());
        }
        user.setEmail(teacherUserDTO.getEmail());
        user.setName(teacherUserDTO.getName());
        user.setLastname(teacherUserDTO.getLastname());
        if (teacherUserDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(teacherUserDTO.getPassword()));
        } else {
            user.setPassword(passwordEncoder.encode("1234"));
        }
        user.setIsActive(true);
        user.setRole(roleRepository.findByRolName(RoleEnum.TEACHER).get());

        List<Module> modules = new ArrayList<>();
        teacherUserDTO.getModules().forEach(moduleDTO -> {
            Module module = moduleRepository.findById(moduleDTO.getId())
                    .orElseThrow(() -> new AppException("Module with id " + moduleDTO.getId() + " not found", HttpStatus.NOT_FOUND));
            modules.add(module);
        });
        user.setModules(modules);

        return user;
    }

    public TeacherUserDTO toTeacherUserDto(UserEntity user) {
        TeacherUserDTO teacherUserDTO = new TeacherUserDTO();

        teacherUserDTO.setId(user.getId());
        teacherUserDTO.setName(user.getName());
        teacherUserDTO.setEmail(user.getEmail());
        teacherUserDTO.setLastname(user.getLastname());
        teacherUserDTO.setPassword("");
        teacherUserDTO.setRole(user.getRole().getRolName().name());

        List<ModuleDTO> moduleDTOS = new ArrayList<>();
        user.getModules().forEach(module -> {
            moduleDTOS.add(moduleConverter.convertModuleEntityToModuleDTO(module));
        });
        teacherUserDTO.setModules(moduleDTOS);

        return teacherUserDTO;
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
