package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.group.TimeTableConverter;
import com.estech.EstechAppBackend.dto.group.TimeTableDTO;
import com.estech.EstechAppBackend.exceptions.AppException;
import com.estech.EstechAppBackend.model.Group;
import com.estech.EstechAppBackend.model.Module;
import com.estech.EstechAppBackend.model.TimeTable;
import com.estech.EstechAppBackend.repository.GroupRepository;
import com.estech.EstechAppBackend.repository.ModuleRepository;
import com.estech.EstechAppBackend.repository.TimeTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeTableService {

    @Autowired
    private TimeTableRepository timeTableRepository;
    @Autowired
    private TimeTableConverter timeTableConverter;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ModuleRepository moduleRepository;

    public List<TimeTableDTO> getAllTimeTables() {
        return timeTableConverter.toTimeTableDtos(timeTableRepository.findAll());
    }

    public List<TimeTableDTO> getAllTimeTablesByGroupId(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new AppException("Group with id " + id + " not found", HttpStatus.NOT_FOUND));

        return timeTableConverter.toTimeTableDtos(group.getTimeTables());
    }

    public TimeTableDTO createTimeTable(TimeTableDTO timeTableDTO) {
        TimeTable timeTable = timeTableConverter.toTimeTable(timeTableDTO);

        TimeTable saved = timeTableRepository.save(timeTable);

        return timeTableConverter.toTimeTableDto(saved);
    }

    public TimeTableDTO updateTimeTable(TimeTableDTO timeTableDTO) {
        if (timeTableDTO.getId() == null) {
            throw new AppException("Time Table id must be provided for updating", HttpStatus.BAD_REQUEST);
        }

        TimeTable timeTable = timeTableRepository.findById(timeTableDTO.getId())
                .orElseThrow(() -> new AppException("Time Table with id " + timeTableDTO.getId() + " not found", HttpStatus.NOT_FOUND));

        timeTableConverter.updateTimeTable(timeTable, timeTableConverter.toTimeTable(timeTableDTO));

        TimeTable saved = timeTableRepository.save(timeTable);

        return timeTableConverter.toTimeTableDto(saved);
    }

    public TimeTableDTO modifyTimeTable(Long id, TimeTableDTO timeTableDTO) {
        TimeTable timeTable = timeTableRepository.findById(id)
                .orElseThrow(() -> new AppException("Time Table with id " + id + " not found", HttpStatus.NOT_FOUND));

        if (timeTableDTO.getGroupId() != null) {
            Group group = groupRepository.findById(timeTableDTO.getGroupId())
                    .orElseThrow(() -> new AppException("Group with id " + timeTableDTO.getGroupId() + " not found", HttpStatus.NOT_FOUND));
            timeTable.setGroup(group);
        }
        if (timeTableDTO.getModuleId() != null) {
            Module module = moduleRepository.findById(timeTableDTO.getModuleId())
                    .orElseThrow(() -> new AppException("Module with id " + timeTableDTO.getModuleId() + " not found", HttpStatus.NOT_FOUND));
            timeTable.setModule(module);
        }
        if (timeTableDTO.getStart() != null) {
            timeTable.setStart(timeTableDTO.getStart());
        }
        if (timeTableDTO.getEnd() != null) {
            timeTable.setEnd(timeTableDTO.getEnd());
        }
        if (timeTableDTO.getWeekday() != null) {
            timeTable.setWeekday(timeTableDTO.getWeekday());
        }

        TimeTable saved = timeTableRepository.save(timeTable);

        return timeTableConverter.toTimeTableDto(saved);
    }

}
