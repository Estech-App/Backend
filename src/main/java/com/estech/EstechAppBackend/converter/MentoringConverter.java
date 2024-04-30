package com.estech.EstechAppBackend.converter;

import com.estech.EstechAppBackend.dto.mentoring.MentoringDTO;
import com.estech.EstechAppBackend.model.Mentoring;
import com.estech.EstechAppBackend.service.StatusService;
import com.estech.EstechAppBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MentoringConverter {

    @Autowired
    private StatusService statusService;
    @Autowired
    private UserService userService;

    public MentoringDTO convertMentoringEntityToMentoringDTO(Mentoring mentoring) {
        MentoringDTO mentoringDTO = new MentoringDTO();

        mentoringDTO.setId(mentoring.getId());
        mentoringDTO.setDate(mentoring.getDate());
        mentoringDTO.setStatus(mentoring.getStatus().getStatus().toString());
        mentoringDTO.setRoomId(mentoring.getRoom().getId());
        mentoringDTO.setTeacherId(mentoring.getTeacher().getId());
        mentoringDTO.setStudentId(mentoring.getStudent().getId());

        return mentoringDTO;
    }

    public Mentoring convertMentoringDTOToMentoringEntity(MentoringDTO mentoringDTO) {
        Mentoring mentoring = new Mentoring();

        mentoring.setId(mentoringDTO.getId());
        mentoring.setDate(mentoringDTO.getDate());

        statusService.getAllStatusEntities().forEach(status -> {
            if (status.getStatus().toString().equals(mentoringDTO.getStatus())) {
                mentoring.setStatus(status);
            }
        });

        // TODO - Set Teacher and Set Student.

        return mentoring;
    }

}
