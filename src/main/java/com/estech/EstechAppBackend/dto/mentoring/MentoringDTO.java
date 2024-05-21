package com.estech.EstechAppBackend.dto.mentoring;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MentoringDTO {

    private Long id;

    @NotNull
    private Date start;

    @NotNull
    private Date end;

    @NotNull
    private Long roomId;

    @NotNull
    @NotBlank
    private String status;

    @NotNull
    private Long teacherId;

    @NotNull
    private Long studentId;

}
