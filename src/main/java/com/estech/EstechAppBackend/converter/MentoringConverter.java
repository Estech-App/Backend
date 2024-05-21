package com.estech.EstechAppBackend.converter;

import com.estech.EstechAppBackend.dto.mentoring.MentoringDTO;
import com.estech.EstechAppBackend.exceptions.AppException;
import com.estech.EstechAppBackend.model.Mentoring;
import com.estech.EstechAppBackend.model.Room;
import com.estech.EstechAppBackend.model.UserEntity;
import com.estech.EstechAppBackend.repository.RoomRepository;
import com.estech.EstechAppBackend.repository.UserRepository;
import com.estech.EstechAppBackend.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MentoringConverter {

    @Autowired
    private StatusService statusService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;

    public MentoringDTO convertMentoringEntityToMentoringDTO(Mentoring mentoring) {
        MentoringDTO mentoringDTO = new MentoringDTO();

        mentoringDTO.setId(mentoring.getId());
        mentoringDTO.setStart(mentoring.getStart());
        mentoringDTO.setEnd(mentoring.getEnd());
        mentoringDTO.setStatus(mentoring.getStatus().getStatus().toString());
        mentoringDTO.setRoomId(mentoring.getRoom().getId());
        mentoringDTO.setTeacherId(mentoring.getTeacher().getId());
        mentoringDTO.setStudentId(mentoring.getStudent().getId());

        return mentoringDTO;
    }

    public Mentoring convertMentoringDTOToMentoringEntity(MentoringDTO mentoringDTO) {
        Mentoring mentoring = new Mentoring();

        if (mentoringDTO.getId() != null) {
            mentoring.setId(mentoringDTO.getId());
        }
        mentoring.setStart(mentoringDTO.getStart());
        mentoring.setEnd(mentoringDTO.getEnd());

        Room room = roomRepository.findById(mentoringDTO.getRoomId())
                        .orElseThrow(() -> new AppException("Room with id " + mentoringDTO.getRoomId() + " not found", HttpStatus.NOT_FOUND));
        mentoring.setRoom(room);

        statusService.getAllStatusEntities().forEach(status -> {
            if (status.getStatus().toString().equals(mentoringDTO.getStatus())) {
                mentoring.setStatus(status);
            }
        });

        UserEntity student = userRepository.findById(mentoringDTO.getStudentId())
                .orElseThrow(() -> new AppException("User with id " + mentoringDTO.getStudentId() + " not found", HttpStatus.NOT_FOUND));
        mentoring.setStudent(student);

        UserEntity teacher = userRepository.findById(mentoringDTO.getTeacherId())
                .orElseThrow(() -> new AppException("User with id " + mentoringDTO.getTeacherId() + " not found", HttpStatus.NOT_FOUND));
        mentoring.setTeacher(teacher);

        return mentoring;
    }

    public List<MentoringDTO> fromMentoringsToMentoringDTOs(List<Mentoring> mentorings) {
        List<MentoringDTO> dtos = new ArrayList<>();

        mentorings.forEach(mentoring -> {
            dtos.add(this.convertMentoringEntityToMentoringDTO(mentoring));
        });
        return dtos;
    }

    public void updateMentoring(Mentoring target, Mentoring source) {
        if (source == null) {
            return ;
        }

        target.setId(source.getId());
        target.setStart(source.getStart());
        target.setEnd(source.getEnd());
        target.setRoom(source.getRoom());
        target.setStatus(source.getStatus());
        target.setStudent(source.getStudent());
        target.setTeacher(source.getTeacher());
    }

}
