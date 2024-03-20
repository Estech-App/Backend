package com.estech.EstechAppBackend.converter;

import com.estech.EstechAppBackend.dto.module.ModuleDTO;
import com.estech.EstechAppBackend.model.Module;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ModuleConverter {

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
        if (module.getUsers() != null) {
            module.getUsers().forEach(user -> {
                usersName.add(user.getName());
            });
            moduleDTO.setUsersName(usersName);
        }
        return moduleDTO;
    }

}
