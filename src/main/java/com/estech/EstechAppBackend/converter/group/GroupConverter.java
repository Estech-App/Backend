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
import com.estech.EstechAppBackend.repository.RoomRepository;
import com.estech.EstechAppBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupConverter {

    @Autowired
    private UserConverter userConverter;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private CourseRepository courseRepository;

    public GroupDTO toGroupDto(Group group) {
        List<UserInfoDTO> userInfoDtos = new ArrayList<>();

        group.getUsers().forEach(userEntity -> {
            userInfoDtos.add(userConverter.convertUserEntityToUserInfoDTO(userEntity));
        });

        return GroupDTO.builder()
                .id(group.getId())
                .name(group.getName())
                .description(group.getDescription())
                .year(group.getYear())
                .courseId(group.getCourse().getId())
                .roomId(group.getRoom().getId())
                .users(userInfoDtos)
                // TODO - timeTableDtos()
                // ! filesDtos -> Not applicable
                .build();
    }

    public Group toGroup(GroupDTO groupDTO) {
        Course course = courseRepository.findById(groupDTO.getCourseId())
                .orElseThrow(() -> new AppException("Course with id " + groupDTO.getCourseId() + " not found", HttpStatus.NOT_FOUND));

        Room room = roomRepository.findById(groupDTO.getRoomId())
                .orElseThrow(() -> new AppException("Room with id " + groupDTO.getRoomId() + " not found", HttpStatus.NOT_FOUND));

        Group group = Group.builder()
                .name(groupDTO.getName())
                .description(groupDTO.getDescription())
                .year(groupDTO.getYear())
                .users(userConverter.fromUserInfoDtostoUserEntities(groupDTO.getUsers()))
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
        target.setUsers(source.getUsers());
        target.setCourse(source.getCourse());
        // TODO - target.setTimeTables(source.getTimeTables());
        target.setRoom(source.getRoom());
    }

}
