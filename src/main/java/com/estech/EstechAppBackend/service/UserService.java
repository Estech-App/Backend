package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.UserConverter;
import com.estech.EstechAppBackend.dto.user.CreatedUserDTO;
import com.estech.EstechAppBackend.dto.user.CreationUserDTO;
import com.estech.EstechAppBackend.dto.user.StudentUserDTO;
import com.estech.EstechAppBackend.dto.user.UserInfoDTO;
import com.estech.EstechAppBackend.exceptions.AppException;
import com.estech.EstechAppBackend.model.Role;
import com.estech.EstechAppBackend.model.UserEntity;
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
