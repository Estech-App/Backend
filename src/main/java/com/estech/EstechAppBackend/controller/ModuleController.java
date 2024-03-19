package com.estech.EstechAppBackend.controller;

import com.estech.EstechAppBackend.dto.module.ModuleDTO;
import com.estech.EstechAppBackend.dto.module.aux.ModuleCourseDTO;
import com.estech.EstechAppBackend.dto.module.aux.ModuleCreationDTO;
import com.estech.EstechAppBackend.dto.module.aux.ModuleUserDTO;
import com.estech.EstechAppBackend.model.Module;
import com.estech.EstechAppBackend.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/module")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

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

    @PatchMapping("/add-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addUserToModule(@RequestBody ModuleUserDTO dto) {
        ModuleDTO module = moduleService.addUserToModule(dto.getModuleId(), dto.getUserId());
        if (module == null) {
            return new ResponseEntity<>("error: wrong id's received", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(module, HttpStatus.OK);
    }

}
