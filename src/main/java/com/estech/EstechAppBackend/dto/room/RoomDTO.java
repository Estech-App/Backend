package com.estech.EstechAppBackend.dto.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RoomDTO {

    private Long id;

    @NotNull
    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Boolean mentoringRoom;

    @NotNull
    private Boolean studyRoom;

    private List<RoomTimeTableDTO> timeTables;

}
