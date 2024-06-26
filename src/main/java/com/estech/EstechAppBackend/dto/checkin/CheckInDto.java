package com.estech.EstechAppBackend.dto.checkin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link com.estech.EstechAppBackend.model.CheckIn}
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CheckInDto {
    Long id;
    @NotBlank
    Date date;
    @NotNull
    Boolean checkIn;
    String user;
    Long userId;
}