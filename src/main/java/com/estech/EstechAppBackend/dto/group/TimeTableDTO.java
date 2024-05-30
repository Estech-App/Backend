package com.estech.EstechAppBackend.dto.group;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TimeTableDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long groupId;

    @NotNull
    private Long moduleId;

    @NotNull
    private LocalTime hour;

    @NotNull
    @NotBlank
    private String weekday;

}
