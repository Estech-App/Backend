package com.estech.EstechAppBackend.converter;

import com.estech.EstechAppBackend.converter.course.CourseConverter;
import com.estech.EstechAppBackend.dto.module.CreationModuleDTO;
import com.estech.EstechAppBackend.dto.module.ModuleDTO;
import com.estech.EstechAppBackend.exceptions.AppException;
import com.estech.EstechAppBackend.model.Course;
import com.estech.EstechAppBackend.model.Module;
import com.estech.EstechAppBackend.model.UserEntity;
import com.estech.EstechAppBackend.repository.CourseRepository;
import com.estech.EstechAppBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ModuleConverter {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseConverter courseConverter;
    @Autowired
    private UserRepository userRepository;

    public ModuleDTO convertModuleEntityToModuleDTO(Module module) {
        ModuleDTO moduleDTO = new ModuleDTO();
        List<String> usersName = new ArrayList<>();

        moduleDTO.setId(module.getId());
        moduleDTO.setYear(module.getYear());
        moduleDTO.setName(module.getName());
        moduleDTO.setAcronym(module.getAcronym());
        if (module.getCourse() != null) {
            moduleDTO.setCourseAcronym(module.getCourse().getAcronym());
        }
        if (module.getCourse() != null) {
            moduleDTO.setCourseDTO(courseConverter.convertCourseEntityToCreatedCourseDTO(module.getCourse()));
        }
        if (module.getUsers() != null) {
            module.getUsers().forEach(user -> {
                usersName.add(user.getName());
            });
            moduleDTO.setUsersName(usersName);
        }
        return moduleDTO;
    }

    public Module creationModuleDtoToModule(CreationModuleDTO moduleDTO) {
        Module module = new Module();

        if (moduleDTO.getId() != null) {
            module.setId(moduleDTO.getId());
        }
        module.setAcronym(moduleDTO.getAcronym());
        module.setYear(moduleDTO.getYear());
        module.setName(moduleDTO.getName());
        if (moduleDTO.getCourse() != null) {
            Course course = courseRepository.findById(moduleDTO.getCourse().getId())
                    .orElseThrow(() -> new AppException("Course with id " + moduleDTO.getCourse().getId() + " not found", HttpStatus.NOT_FOUND));
            module.setCourse(course);
        }
        List<UserEntity> users = new ArrayList<>();
        if (moduleDTO.getUsers() != null && !moduleDTO.getUsers().isEmpty()) {
            moduleDTO.getUsers().forEach(user -> {
                UserEntity userEntity = userRepository.findById(user.getId())
                        .orElseThrow(() -> new AppException("User with id " + user.getId() + " not found", HttpStatus.NOT_FOUND));
                users.add(userEntity);
            });
        }
        module.setUsers(users);

        return module;
    }

    public void updateModule(Module target, Module source) {
        if (source == null) {
            return ;
        }

        target.setId(source.getId());
        target.setName(source.getName());
        target.setYear(source.getYear());
        target.setAcronym(source.getAcronym());
        target.setCourse(source.getCourse());
        target.setUsers(source.getUsers());
        target.setTimeTables(source.getTimeTables());
    }

}
