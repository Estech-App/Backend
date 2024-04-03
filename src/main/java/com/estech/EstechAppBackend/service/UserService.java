package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.UserConverter;
import com.estech.EstechAppBackend.dto.user.CreatedUserDTO;
import com.estech.EstechAppBackend.dto.user.CreationUserDTO;
import com.estech.EstechAppBackend.dto.user.UserInfoDTO;
import com.estech.EstechAppBackend.model.UserEntity;
import com.estech.EstechAppBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * This method can only be used to create the first Admin user.
     * DO NOT USE IT TO CREATE NEW USERS. It would be not secured.
     * @param userEntity
     */
    public void saveFirstAdminUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

}
