package com.estech.EstechAppBackend.controller;

import com.estech.EstechAppBackend.dto.module.CreationModuleDTO;
import com.estech.EstechAppBackend.dto.module.ModuleCourseDTO;
import com.estech.EstechAppBackend.dto.module.ModuleDTO;
import com.estech.EstechAppBackend.dto.module.ModuleUserDTO;
import com.estech.EstechAppBackend.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/module")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllModules() {
        List<ModuleDTO> modules = moduleService.getAllModulesDTO();
        if (modules.isEmpty()) {
            return new ResponseEntity<>("empty set", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(modules, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('STUDENT') || hasRole('TEACHER')")
    public ResponseEntity<?> getModuleById(@PathVariable Long id) {
        ModuleDTO dto = moduleService.getModuleById(id);

        if (dto == null) {
            return new ResponseEntity<>("error: wrong id", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/by-course/{id}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('STUDENT') || hasRole('TEACHER')")
    public ResponseEntity<List<ModuleDTO>> getModulesByCourse(@PathVariable Integer id) {
        return ResponseEntity.ok(moduleService.getModulesByCourseId(id));
    }

    @PostMapping("/new-module")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ModuleDTO> saveModule(@RequestBody CreationModuleDTO module) {
        ModuleDTO created = moduleService.saveModule(module);
        return ResponseEntity.created(URI.create("/api/module/new-module/" + module.getId())).body(created);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ModuleDTO> updateModule(@RequestBody CreationModuleDTO moduleDTO) {
        return ResponseEntity.ok(moduleService.updateModule(moduleDTO));
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteModules(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
