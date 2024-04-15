package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.ModuleConverter;
import com.estech.EstechAppBackend.dto.module.ModuleDTO;
import com.estech.EstechAppBackend.model.Course;
import com.estech.EstechAppBackend.model.Module;
import com.estech.EstechAppBackend.model.UserEntity;
import com.estech.EstechAppBackend.repository.CourseRepository;
import com.estech.EstechAppBackend.repository.ModuleRepository;
import com.estech.EstechAppBackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModuleConverter moduleConverter;

    public List<ModuleDTO> getAllModulesDTO() {
        List<ModuleDTO> modulesDTO = new ArrayList<>();

        moduleRepository.findAll().forEach(module -> {
            modulesDTO.add(moduleConverter.convertModuleEntityToModuleDTO(module));
        });
        return modulesDTO;
    }

    public ModuleDTO getModuleById(Long id) {
        Module module = moduleRepository.findById(id).orElse(null);

        if (module == null) {
            return null;
        }
        return moduleConverter.convertModuleEntityToModuleDTO(module);
    }

    public ModuleDTO saveModule(Module module) {
        return moduleConverter.convertModuleEntityToModuleDTO(moduleRepository.save(module));
    }

    public ModuleDTO addCourseToModule(Long moduleId, Integer courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return null;
        }
        Module module = moduleRepository.findById(moduleId).orElse(null);
        if (module == null) {
            return null;
        }

        module.setCourse(course);
        moduleRepository.save(module);
        return moduleConverter.convertModuleEntityToModuleDTO(module);
    }

    public ModuleDTO addUserToModule(Long moduleId, Long userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }

        Module module = moduleRepository.findById(moduleId).orElse(null);
        if (module == null) {
            return null;
        }

        List<UserEntity> users = module.getUsers();
        for (UserEntity userEntity : users) {
            if (Objects.equals(userEntity.getId(), user.getId())) {
                return null;
            }
        }

        users.add(user);
        module.setUsers(users);
        moduleRepository.save(module);
        return moduleConverter.convertModuleEntityToModuleDTO(module);
    }
}
