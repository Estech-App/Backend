package com.estech.EstechAppBackend.converter.group;

import com.estech.EstechAppBackend.dto.group.TimeTableDTO;
import com.estech.EstechAppBackend.exceptions.AppException;
import com.estech.EstechAppBackend.model.Group;
import com.estech.EstechAppBackend.model.Module;
import com.estech.EstechAppBackend.model.TimeTable;
import com.estech.EstechAppBackend.repository.GroupRepository;
import com.estech.EstechAppBackend.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TimeTableConverter {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ModuleRepository moduleRepository;

    public TimeTableDTO toTimeTableDto(TimeTable timeTable) {
        return TimeTableDTO.builder()
                .id(timeTable.getId())
                .groupId(timeTable.getGroup().getId())
                .moduleId(timeTable.getModule().getId())
                .start(timeTable.getStart())
                .end(timeTable.getEnd())
                .weekday(timeTable.getWeekday())
                .build();
    }

    public TimeTable toTimeTable(TimeTableDTO timeTableDTO) {
        Group group = groupRepository.findById(timeTableDTO.getGroupId())
                .orElseThrow(() -> new AppException("Group with id " + timeTableDTO.getGroupId() + " not found", HttpStatus.NOT_FOUND));

        Module module = moduleRepository.findById(timeTableDTO.getModuleId())
                .orElseThrow(() -> new AppException("Module with id " + timeTableDTO.getModuleId() + " not found", HttpStatus.NOT_FOUND));

        TimeTable timeTable = TimeTable.builder()
                .group(group)
                .module(module)
                .start(timeTableDTO.getStart())
                .end(timeTableDTO.getEnd())
                .weekday(timeTableDTO.getWeekday())
                .build();

        if (timeTableDTO.getId() != null) {
            timeTable.setId(timeTableDTO.getId());
        }

        return timeTable;
    }

    public List<TimeTableDTO> toTimeTableDtos(List<TimeTable> timeTables) {
        List<TimeTableDTO> dtos = new ArrayList<>();

        timeTables.forEach(timeTable -> {
            dtos.add(this.toTimeTableDto(timeTable));
        });
        return dtos;
    }

    public List<TimeTable> toTimeTables(List<TimeTableDTO> timeTableDTOS) {
        List<TimeTable> timeTables = new ArrayList<>();

        timeTableDTOS.forEach(timeTableDTO -> {
            timeTables.add(this.toTimeTable(timeTableDTO));
        });
        return timeTables;
    }

    public void updateTimeTable(TimeTable target, TimeTable source) {
        if (source == null) {
            return ;
        }

        target.setGroup(source.getGroup());
        target.setModule(source.getModule());
        target.setStart(source.getStart());
        target.setEnd(source.getEnd());
        target.setWeekday(source.getWeekday());
    }

}
