package com.estech.EstechAppBackend.dto.room;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoomIdDTO {

    @NotNull
    private Long id;

}
