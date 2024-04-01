package com.estech.EstechAppBackend.controller;

import com.estech.EstechAppBackend.dto.module.ModuleDTO;
import com.estech.EstechAppBackend.dto.module.aux.ModuleCourseDTO;
import com.estech.EstechAppBackend.dto.module.aux.ModuleUserDTO;
import com.estech.EstechAppBackend.model.Module;
import com.estech.EstechAppBackend.model.UserEntity;
import com.estech.EstechAppBackend.repository.ModuleRepository;
import com.estech.EstechAppBackend.repository.UserRepository;
import com.estech.EstechAppBackend.service.ModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/module")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAllModules() {
        List<ModuleDTO> modules = moduleService.getAllModulesDTO();
        if (modules.isEmpty()) {
            return new ResponseEntity<>("empty set", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(modules, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveModule(@RequestBody Module module) {
        return new ResponseEntity<>(moduleService.saveModule(module), HttpStatus.CREATED);
    }

    @PatchMapping("/add-course")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addCourseToModule(@RequestBody ModuleCourseDTO dto) {
        ModuleDTO module = moduleService.addCourseToModule(dto.getModuleId(), dto.getCourseId());
        if (module == null) {
            return new ResponseEntity<>("error: wrong id's received", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(module, HttpStatus.OK);
    }

/*    @PatchMapping("/add-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addUserToModule(@RequestBody ModuleUserDTO dto) {
        ModuleDTO module = moduleService.addUserToModule(dto.getModuleId(), dto.getUserId());
        if (module == null) {
            return new ResponseEntity<>("error: wrong id's received", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(module, HttpStatus.OK);
    } */

    private static final Logger logger = LoggerFactory.getLogger(ModuleController.class);

    @PostMapping("/add-user/{moduleId}/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addUserToModule(@PathVariable Long moduleId, @PathVariable Long userId) {
        /* ModuleDTO module = moduleService.addUserToModule(moduleId, userId);
        if (module == null) {
            return new ResponseEntity<>("error: wrong id's received", HttpStatus.BAD_REQUEST);
        } */
        /* UserEntity userEntity = userRepository.findById(userId).orElse(null);
        List<UserEntity> users = new ArrayList<>();
        Module module = moduleRepository.findById(moduleId).orElse(null);
        users.add(userEntity);
        module.setUsers(users);
        moduleRepository.save(module); */
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
