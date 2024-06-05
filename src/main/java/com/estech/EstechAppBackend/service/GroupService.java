package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.UserConverter;
import com.estech.EstechAppBackend.converter.group.GroupConverter;
import com.estech.EstechAppBackend.dto.group.GroupDTO;
import com.estech.EstechAppBackend.dto.idDTO;
import com.estech.EstechAppBackend.exceptions.AppException;
import com.estech.EstechAppBackend.model.*;
import com.estech.EstechAppBackend.repository.CourseRepository;
import com.estech.EstechAppBackend.repository.GroupRepository;
import com.estech.EstechAppBackend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private CourseRepository courseRepository;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private GroupConverter groupConverter;

    public List<GroupDTO> getAllGroups() {
        return groupConverter.toGroupDtos(groupRepository.findAll());
    }

    public GroupDTO createNewGroup(GroupDTO groupDTO) {
        Group group = groupConverter.toGroup(groupDTO);

        Group saved = groupRepository.save(group);

        // createTimeTables(saved, groupDTO);

        return groupConverter.toGroupDto(saved);
    }

    public GroupDTO updateGroup(GroupDTO groupDTO) {
        if (groupDTO.getId() == null) {
            throw new AppException("Group id must be provided for updating", HttpStatus.BAD_REQUEST);
        }

        Group group = groupRepository.findById(groupDTO.getId())
                .orElseThrow(() -> new AppException("Group with id " + groupDTO.getId() + " not found", HttpStatus.NOT_FOUND));

        groupConverter.updateGroup(group, groupConverter.toGroup(groupDTO));

        Group saved = groupRepository.save(group);

        return groupConverter.toGroupDto(saved);
    }

    public GroupDTO modifyGroup(Long id, GroupDTO groupDTO) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new AppException("Group with id " + id + " not found", HttpStatus.NOT_FOUND));

        if (groupDTO.getName() != null) {
            group.setName(groupDTO.getName());
        }
        if (groupDTO.getDescription() != null) {
            group.setDescription(groupDTO.getDescription());
        }
        if (groupDTO.getYear() != null) {
            group.setYear(groupDTO.getYear());
        }
        if (groupDTO.getEvening() != null) {
            group.setEvening(groupDTO.getEvening());
        }
        if (groupDTO.getUsers() != null && !groupDTO.getUsers().isEmpty()) {
            group.setUsers(userConverter.fromUserInfoDtostoUserEntities(groupDTO.getUsers()));
        }
        if (groupDTO.getCourseId() != null) {
            Course course = courseRepository.findById(groupDTO.getCourseId())
                    .orElseThrow(() -> new AppException("Course with id " + groupDTO.getCourseId() + " not found", HttpStatus.NOT_FOUND));
            group.setCourse(course);
        }
        if (groupDTO.getRoomId() != null) {
            Room room = roomRepository.findById(groupDTO.getRoomId())
                    .orElseThrow(() -> new AppException("Room with id " + groupDTO.getRoomId() + " not found", HttpStatus.NOT_FOUND));
            group.setRoom(room);
        }

        Group saved = groupRepository.save(group);

        return groupConverter.toGroupDto(saved);
    }

//    private void createTimeTables(Group group, GroupDTO groupDTO) {
//        List<TimeTable> timeTables = new ArrayList<>();
//        groupDTO.getTimeTableDTOS().forEach(timeTableDTO -> {
//
//        });
//    }

}
