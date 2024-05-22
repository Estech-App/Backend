package com.estech.EstechAppBackend.dto.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RoomTimeTableDTO {

    private Long id;

    @NotBlank
    @NotNull
    private String status;

    @NotNull
    private Date start;

    @NotNull
    private Date end;

    private Boolean reccurence;

    private Long roomId;


}
