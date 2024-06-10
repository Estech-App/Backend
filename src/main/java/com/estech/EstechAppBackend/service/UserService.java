package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.UserConverter;
import com.estech.EstechAppBackend.dto.user.*;
import com.estech.EstechAppBackend.exceptions.AppException;
import com.estech.EstechAppBackend.model.Group;
import com.estech.EstechAppBackend.model.Module;
import com.estech.EstechAppBackend.model.Role;
import com.estech.EstechAppBackend.model.UserEntity;
import com.estech.EstechAppBackend.repository.GroupRepository;
import com.estech.EstechAppBackend.repository.ModuleRepository;
import com.estech.EstechAppBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ModuleRepository moduleRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public CreatedUserDTO createOrUpdateNewUser(CreationUserDTO creationUserDTO) {
        userRepository.save(userConverter.convertCreationUserDTOToUserEntity(creationUserDTO));
        return userConverter.convertCreationUserDTOToCreatedUserDTO(creationUserDTO);
    }

    public StudentUserDTO createStudentUser(StudentUserDTO studentUserDTO) {
        UserEntity student = userConverter.studentDTOToUserEntity(studentUserDTO);

        UserEntity saved = userRepository.save(student);

        return userConverter.toStudentUserDto(saved);
    }

    public StudentUserDTO updateStudentUser(StudentUserDTO studentUserDTO) {
        if (studentUserDTO.getId() == null) {
            throw new AppException("id must be provided for updating student", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = userRepository.findById(studentUserDTO.getId())
                .orElseThrow(() -> new AppException("User with id " + studentUserDTO.getId() + " not found", HttpStatus.NOT_FOUND));

        user.setPassword(studentUserDTO.getPassword());
        user.setName(studentUserDTO.getName());
        user.setEmail(studentUserDTO.getEmail());
        user.setLastname(studentUserDTO.getLastname());
        List<Group> groups = new ArrayList<>();
        if (studentUserDTO.getGroups() != null) {
            studentUserDTO.getGroups().forEach(groupDTO -> {
                Group group = groupRepository.findById(groupDTO.getId())
                        .orElseThrow(() -> new AppException("Group with id " + groupDTO.getId() + " not found", HttpStatus.NOT_FOUND));
                groups.add(group);
            });
        }
        user.setGroups(groups);
        userRepository.save(user);

        return userConverter.toStudentUserDto(user);
    }

    public TeacherUserDTO createTeacherUser(TeacherUserDTO teacherUserDTO) {
        UserEntity teacher = userConverter.teacherDtoToUserEntity(teacherUserDTO);

        UserEntity saved = userRepository.save(teacher);

        return userConverter.toTeacherUserDto(saved);
    }

    public TeacherUserDTO updateTeacherUser(TeacherUserDTO teacherUserDTO) {
        if (teacherUserDTO.getId() == null) {
            throw new AppException("id must be provided for updating teacher", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userRepository.findById(teacherUserDTO.getId())
                .orElseThrow(() -> new AppException("User with id " + teacherUserDTO.getId() + " not found", HttpStatus.NOT_FOUND));

        user.setPassword(teacherUserDTO.getPassword());
        user.setName(teacherUserDTO.getName());
        user.setEmail(teacherUserDTO.getEmail());
        user.setLastname(teacherUserDTO.getLastname());
        List<Module> modules = new ArrayList<>();
        if (teacherUserDTO.getModules() != null) {
            teacherUserDTO.getModules().forEach(moduleDTO -> {
                Module module = moduleRepository.findById(moduleDTO.getId())
                        .orElseThrow(() -> new AppException("Module with id " + moduleDTO.getId() + " not found", HttpStatus.NOT_FOUND));
                modules.add(module);
            });
        }
        user.setModules(modules);
        userRepository.save(user);

        return userConverter.toTeacherUserDto(user);
    }

    public List<CreatedUserDTO> getAllUsersAsCreatedUserDTO() {
        List<CreatedUserDTO> list = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            list.add(userConverter.convertUserEntityToCreatedUserDTO(user));
        });
        return list;
    }

    public UserInfoDTO getUserInfo(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElse(null);

        if (userEntity != null) {
            return userConverter.convertUserEntityToUserInfoDTO(userEntity);
        }
        return null;
    }

    public CreationUserDTO getUserById(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return null;
        }
        return userConverter.convertUserEntityToCreationUserDTO(user);
    }

    public StudentUserDTO getStudentById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User with id " + id + " not found", HttpStatus.NOT_FOUND));

        return userConverter.toStudentUserDto(user);
    }

    public TeacherUserDTO getTeacherById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User with id " + id + " not found", HttpStatus.NOT_FOUND));

        return userConverter.toTeacherUserDto(user);
    }

    public List<UserInfoDTO> getAllUsersByRole(Role role) {
        List<UserEntity> users = userRepository.findAllByRole(role).orElse(null);
        List<UserInfoDTO> dto = new ArrayList<>();

        if (users == null || users.isEmpty()) {
            return null;
        }
        users.forEach(user -> {
            dto.add(userConverter.convertUserEntityToUserInfoDTO(user));
        });
        return dto;
    }

    /**
     * This method can only be used to create the first Admin user.
     * DO NOT USE IT TO CREATE NEW USERS. It would be not secured.
     * @param userEntity
     */
    public void saveFirstAdminUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

}
