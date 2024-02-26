package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.group.GroupConverter;
import com.estech.EstechAppBackend.dto.group.GroupDTO;
import com.estech.EstechAppBackend.model.Group;
import com.estech.EstechAppBackend.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupConverter groupConverter;

    public GroupDTO createNewGroup(Group group) {
        return groupConverter.convertGroupEntityToGroupDTO(groupRepository.save(group));
    }

    public List<GroupDTO> getAllGroups() {
        List<GroupDTO> groupsDTO = new ArrayList<>();

        groupRepository.findAll().forEach(group -> {
            groupsDTO.add(groupConverter.convertGroupEntityToGroupDTO(group));
        });
        return groupsDTO;
    }

}
