package com.estech.EstechAppBackend.dto.room;

import com.estech.EstechAppBackend.dto.group.GroupDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

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
