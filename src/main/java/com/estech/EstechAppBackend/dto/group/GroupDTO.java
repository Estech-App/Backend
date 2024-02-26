package com.estech.EstechAppBackend.dto.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GroupDTO {

    private String name;
    private String description;
    private Integer year;
    private String courseName;
    private String roomName;

}
