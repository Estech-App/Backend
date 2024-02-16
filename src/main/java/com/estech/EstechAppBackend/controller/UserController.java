package com.estech.EstechAppBackend.controller;

import com.estech.EstechAppBackend.model.UserEntity;
import com.estech.EstechAppBackend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Api(value = "Estech User", protocols = "http")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "To get all the users that are registered on the DB", response = UserEntity.class, code = 200)
    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

}
