package com.estech.EstechAppBackend.converter.group;

import com.estech.EstechAppBackend.converter.UserConverter;
import com.estech.EstechAppBackend.dto.group.GroupDTO;
import com.estech.EstechAppBackend.dto.user.UserInfoDTO;
import com.estech.EstechAppBackend.exceptions.AppException;
import com.estech.EstechAppBackend.model.Course;
import com.estech.EstechAppBackend.model.Group;
import com.estech.EstechAppBackend.model.Room;
import com.estech.EstechAppBackend.model.UserEntity;
import com.estech.EstechAppBackend.repository.CourseRepository;
import com.estech.EstechAppBackend.repository.GroupRepository;
import com.estech.EstechAppBackend.repository.RoomRepository;
import com.estech.EstechAppBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupConverter {

//    @Autowired
//    private UserConverter userConverter;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private GroupRepository groupRepository;

    public GroupDTO toGroupDto(Group group) {
//        List<UserInfoDTO> userInfoDtos = new ArrayList<>();
//
//        group.getUsers().forEach(userEntity -> {
//            userInfoDtos.add(userConverter.convertUserEntityToUserInfoDTO(userEntity));
//        });

        return GroupDTO.builder()
                .id(group.getId())
                .name(group.getName())
                .description(group.getDescription())
                .year(group.getYear())
                .courseId(group.getCourse().getId())
                .evening(group.getEvening())
                .roomId(group.getRoom().getId())
//                .users(userInfoDtos)
                // TODO - timeTableDtos()
                // ! filesDtos -> Not applicable
                .build();
    }

    public Group toGroup(GroupDTO groupDTO) {
        Course course = courseRepository.findById(groupDTO.getCourseId())
                .orElseThrow(() -> new AppException("Course with id " + groupDTO.getCourseId() + " not found", HttpStatus.NOT_FOUND));

        Room room = null;
        if (groupDTO.getRoomId() != null) {
             room = roomRepository.findById(groupDTO.getRoomId())
                    .orElseThrow(() -> new AppException("Room with id " + groupDTO.getRoomId() + " not found", HttpStatus.NOT_FOUND));
        }

//        List<UserEntity> users = new ArrayList<>();
//        if (groupDTO.getUsers() != null) {
//            users = userConverter.fromUserInfoDtostoUserEntities(groupDTO.getUsers());
//        }

        Group group = Group.builder()
                .name(groupDTO.getName())
                .description(groupDTO.getDescription())
                .year(groupDTO.getYear())
                .evening(groupDTO.getEvening())
//                .users(users)
                .course(course)
                .room(room)
                // TODO - timeTables()
                // ! files -> Not applicable
                .build();

        if (groupDTO.getId() != null) {
            group.setId(group.getId());
        }

        return group;
    }

    public List<GroupDTO> toGroupDtos(List<Group> groups) {
        List<GroupDTO> dtos = new ArrayList<>();

        groups.forEach(group -> {
            dtos.add(this.toGroupDto(group));
        });
        return dtos;
    }

    public void updateGroup(Group target, Group source) {
        if (source == null) {
            return ;
        }

        target.setId(source.getId());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setYear(source.getYear());
        target.setEvening(source.getEvening());
        target.setUsers(source.getUsers());
        target.setCourse(source.getCourse());
        // TODO - target.setTimeTables(source.getTimeTables());
        target.setRoom(source.getRoom());
    }

    public List<Group> fromGroupIdsToGroups(List<Long> groupIds) {
        List<Group> groups = new ArrayList<>();
        groupIds.forEach(groupId -> {
            Group group = groupRepository.findById(groupId)
                    .orElseThrow(() -> new AppException("Group with id " + groupId + " not found", HttpStatus.NOT_FOUND));
            groups.add(group);
        });
        return groups;
    }

}
