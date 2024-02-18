package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.UserCreationConverter;
import com.estech.EstechAppBackend.dto.CreatedUserDTO;
import com.estech.EstechAppBackend.dto.CreationUserDTO;
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
    private UserCreationConverter userCreationConverter;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public CreatedUserDTO createNewUser(CreationUserDTO creationUserDTO) {
        userRepository.save(userCreationConverter.convertCreationUserDTOToUserEntity(creationUserDTO));
        return userCreationConverter.convertCreationUserDTOToCreatedUserDTO(creationUserDTO);
    }

    public List<CreatedUserDTO> getAllUsersAsCreatedUserDTO() {
        List<CreatedUserDTO> list = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            list.add(userCreationConverter.convertUserEntityToCreatedUserDTO(user));
        });
        return list;
    }

}
