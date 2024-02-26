package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.RoomConverter;
import com.estech.EstechAppBackend.converter.group.GroupConverter;
import com.estech.EstechAppBackend.dto.group.GroupDTO;
import com.estech.EstechAppBackend.dto.room.RoomIdDTO;
import com.estech.EstechAppBackend.model.Group;
import com.estech.EstechAppBackend.model.Room;
import com.estech.EstechAppBackend.repository.GroupRepository;
import com.estech.EstechAppBackend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private RoomRepository roomRepository;
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

    public GroupDTO addRoomToGroup(Long groupId, RoomIdDTO room) {
        Group group = groupRepository.findById(groupId).orElse(null);

        if (group == null) {
            return null;
        }

        Room roomEntity = roomRepository.findById(room.getId()).orElse(null);
        if (roomEntity == null) {
            return null;
        }

        group.setRoom(roomEntity);
        return groupConverter.convertGroupEntityToGroupDTO(groupRepository.save(group));

    }

}
