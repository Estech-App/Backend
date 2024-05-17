package com.estech.EstechAppBackend.dto.freeUsages;

import com.estech.EstechAppBackend.dto.room.RoomDTO;
import com.estech.EstechAppBackend.dto.user.UserInfoDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FreeUsagesDTO {

    private Long id;

    @NotNull
    private Date start;
    @NotNull
    private Date end;

    @NotNull
    @NotBlank
    private String status;

    @NotNull
    private RoomDTO room;

    @NotNull
    private UserInfoDTO user;

}
