package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.UserConverter;
import com.estech.EstechAppBackend.converter.group.GroupConverter;
import com.estech.EstechAppBackend.converter.group.TimeTableConverter;
import com.estech.EstechAppBackend.dto.group.GroupDTO;
import com.estech.EstechAppBackend.dto.idDTO;
import com.estech.EstechAppBackend.exceptions.AppException;
import com.estech.EstechAppBackend.model.*;
import com.estech.EstechAppBackend.model.Module;
import com.estech.EstechAppBackend.model.enums.RoleEnum;
import com.estech.EstechAppBackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private TimeTableRepository timeTableRepository;
    @Autowired
    private TimeTableConverter timeTableConverter;

    public List<GroupDTO> getAllGroups() {
        return groupConverter.toGroupDtos(groupRepository.findAll());
    }

    public GroupDTO getGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new AppException("Group with id " + id + " not found", HttpStatus.NOT_FOUND));

        return groupConverter.toGroupDto(group);
    }

    public List<GroupDTO> getGroupsByUserId(Long userId) {
        List<Group> groups = groupRepository.findAll();
        List<GroupDTO> groupDTOS = new ArrayList<>();

        groups.forEach(group ->  {
            if (group.getUsers() != null) {
                group.getUsers().forEach(user -> {
                    if (Objects.equals(user.getId(), userId)) {
                        groupDTOS.add(groupConverter.toGroupDto(group));
                    }
                });
            }
        });

        return groupDTOS;
    }

    public GroupDTO createNewGroup(GroupDTO groupDTO) {
        Group group = groupConverter.toGroup(groupDTO);

        Group saved = groupRepository.save(group);

        createTimeTables(saved, groupDTO);

        Group updated = groupRepository.save(saved);

        return groupConverter.toGroupDto(updated);
    }

    public GroupDTO updateGroup(GroupDTO groupDTO) {
        if (groupDTO.getId() == null) {
            throw new AppException("Group id must be provided for updating", HttpStatus.BAD_REQUEST);
        }

        Group group = groupRepository.findById(groupDTO.getId())
                .orElseThrow(() -> new AppException("Group with id " + groupDTO.getId() + " not found", HttpStatus.NOT_FOUND));

        groupConverter.updateGroup(group, groupConverter.toGroup(groupDTO));

        updateTimeTables(group, groupDTO);

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

    private void createTimeTables(Group group, GroupDTO groupDTO) {
        List<TimeTable> timeTables = new ArrayList<>();
        List<UserEntity> users = new ArrayList<>();
        groupDTO.getTimeTables().forEach(timeTableDTO -> {
            Module module = moduleRepository.findById(timeTableDTO.getModuleId())
                    .orElseThrow(() -> new AppException("Module with id " + timeTableDTO.getModuleId() + " not found", HttpStatus.NOT_FOUND));
            if (module.getUsers() != null) {
                module.getUsers().forEach(user -> {
                    if (user.getRole().getRolName() == RoleEnum.TEACHER) {
                        if (!users.contains(user)) {
                            users.add(user);
                        }
                    }
                });
            }
            TimeTable timeTable = TimeTable.builder()
                    .group(group)
                    .module(module)
                    .start(timeTableDTO.getStart())
                    .end(timeTableDTO.getEnd())
                    .weekday(timeTableDTO.getWeekday())
                    .build();
            timeTableRepository.save(timeTable);
            timeTables.add(timeTable);
        });
        group.setTimeTables(timeTables);
        group.setUsers(users);
    }

    private void updateTimeTables(Group group, GroupDTO groupDTO) {
        List<TimeTable> timeTables = new ArrayList<>();
        List<UserEntity> users;
        if (group.getUsers() != null) {
            users = group.getUsers();
        } else {
            users = new ArrayList<>();
        }
        groupDTO.getTimeTables().forEach(timeTableDTO -> {
            if (timeTableDTO.getId() != null) {
                TimeTable timeTable = timeTableRepository.findById(timeTableDTO.getId())
                                .orElseThrow(() -> new AppException("Time table with id " + timeTableDTO.getId() + " not found", HttpStatus.NOT_FOUND));
                timeTable.setEnd(timeTableDTO.getEnd());
                timeTable.setStart(timeTableDTO.getStart());
                timeTable.setWeekday(timeTableDTO.getWeekday());
                timeTableRepository.save(timeTable);
                timeTables.add(timeTable);
            } else {
                Module module = moduleRepository.findById(timeTableDTO.getModuleId())
                        .orElseThrow(() -> new AppException("Module with id " + timeTableDTO.getModuleId() + " not found", HttpStatus.NOT_FOUND));
                if (module.getUsers() != null) {
                    for (UserEntity user : module.getUsers()) {
                        if (user.getRole().getRolName() == RoleEnum.TEACHER) {
                            if (!users.contains(user)) {
                                users.add(user);
                            }
                        }
                    }
                }
                TimeTable timeTable = TimeTable.builder()
                        .group(group)
                        .module(module)
                        .start(timeTableDTO.getStart())
                        .end(timeTableDTO.getEnd())
                        .weekday(timeTableDTO.getWeekday())
                        .build();
                timeTableRepository.save(timeTable);
                timeTables.add(timeTable);
            }
        });
        group.setTimeTables(timeTables);
        group.setUsers(users);
    }

}
