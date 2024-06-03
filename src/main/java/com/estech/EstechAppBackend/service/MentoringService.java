package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.MentoringConverter;
import com.estech.EstechAppBackend.dto.mentoring.MentoringDTO;
import com.estech.EstechAppBackend.exceptions.AppException;
import com.estech.EstechAppBackend.model.Mentoring;
import com.estech.EstechAppBackend.model.Room;
import com.estech.EstechAppBackend.model.UserEntity;
import com.estech.EstechAppBackend.model.enums.StatusEnum;
import com.estech.EstechAppBackend.repository.MentoringRepository;
import com.estech.EstechAppBackend.repository.RoomRepository;
import com.estech.EstechAppBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MentoringService {

    @Autowired
    private MentoringRepository mentoringRepository;
    @Autowired
    private MentoringConverter mentoringConverter;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private StatusService statusService;

    public List<MentoringDTO> getAllMentorings() {
        return mentoringConverter.fromMentoringsToMentoringDTOs(mentoringRepository.findAll());
    }

    public List<MentoringDTO> getMentoringsByRoomId(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException("Room with id " + roomId + " not found", HttpStatus.NOT_FOUND));

        return mentoringConverter.fromMentoringsToMentoringDTOs(mentoringRepository.findMentoringByRoom(room));
    }

    public MentoringDTO createMentoring(MentoringDTO mentoringDTO) {
        Mentoring createdMentoring = mentoringConverter.convertMentoringDTOToMentoringEntity(mentoringDTO);

        Mentoring saved = mentoringRepository.save(createdMentoring);

        return mentoringConverter.convertMentoringEntityToMentoringDTO(saved);
    }

    public MentoringDTO updateMentoring(MentoringDTO mentoringDTO) {
        if (mentoringDTO.getId() == null) {
            throw new AppException("Mentoring id must be provided for updating", HttpStatus.BAD_REQUEST);
        }

        Mentoring mentoring = mentoringRepository.findById(mentoringDTO.getId())
                .orElseThrow(() -> new AppException("Mentoring with id " + mentoringDTO.getId() + " not found", HttpStatus.NOT_FOUND));

        mentoringConverter.updateMentoring(mentoring, mentoringConverter.convertMentoringDTOToMentoringEntity(mentoringDTO));

        Mentoring saved = mentoringRepository.save(mentoring);

        return mentoringConverter.convertMentoringEntityToMentoringDTO(saved);
    }

    public List<MentoringDTO> getMentoringsByUserId(Long id, Boolean teacher) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User with id " + id + " not found", HttpStatus.NOT_FOUND));

        List<Mentoring> mentorings;
        if (teacher) {
            mentorings = mentoringRepository.findMentoringByTeacher(user);
        } else {
            mentorings = mentoringRepository.findMentoringByStudent(user);
        }

        return mentoringConverter.fromMentoringsToMentoringDTOs(mentorings);
    }

    public MentoringDTO modifyMentoring(Long id, MentoringDTO mentoringDTO) {
        Mentoring mentoring = mentoringRepository.findById(id)
                .orElseThrow(() -> new AppException("Mentoring with id " + mentoringDTO.getId() + " not found", HttpStatus.NOT_FOUND));

        if (mentoringDTO.getRoomId() != null) {
            Room room = roomRepository.findById(mentoringDTO.getRoomId())
                            .orElseThrow(() -> new AppException("Room with id " + mentoringDTO.getRoomId() + " not found", HttpStatus.NOT_FOUND));
            mentoring.setRoom(room);
        }
        if (mentoringDTO.getStatus() != null) {
            mentoring.setStatus(statusService.getStatusByStatusName(StatusEnum.valueOf(mentoringDTO.getStatus())));
        }
        if (mentoringDTO.getStart() != null) {
            mentoring.setStart(mentoringDTO.getStart());
        }
        if (mentoringDTO.getEnd() != null) {
            mentoring.setEnd(mentoringDTO.getEnd());
        }
        if (mentoringDTO.getStudent() != null) {
            UserEntity user = userRepository.findById(mentoringDTO.getStudent().getId())
                    .orElseThrow(() -> new AppException("User with id " + mentoringDTO.getStudent().getId() + " not found", HttpStatus.NOT_FOUND));
            mentoring.setStudent(user);
        }
        if (mentoringDTO.getTeacher() != null) {
            UserEntity user = userRepository.findById(mentoringDTO.getTeacher().getId())
                    .orElseThrow(() -> new AppException("User with id " + mentoringDTO.getTeacher().getId() + " not found", HttpStatus.NOT_FOUND));
            mentoring.setTeacher(user);
        }

        Mentoring saved = mentoringRepository.save(mentoring);

        return mentoringConverter.convertMentoringEntityToMentoringDTO(saved);
    }

    public MentoringDTO deleteMentoring(Long id) {
        Mentoring mentoring = mentoringRepository.findById(id)
                .orElseThrow(() -> new AppException("Mentoring with id " + id + " not found", HttpStatus.NOT_FOUND));

        // TODO - Delete related info in Status table.

        mentoringRepository.deleteById(id);

        return mentoringConverter.convertMentoringEntityToMentoringDTO(mentoring);
    }

}
