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
    @NotBlank
    private Date date;

    @NotNull
    @NotBlank
    private Long roomId;

    @NotNull
    @NotBlank
    private String status;

    @NotNull
    @NotBlank
    private Long teacherId;

    @NotNull
    @NotBlank
    private Long studentId;

}
