package com.estech.EstechAppBackend.dto.group;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupDTO {

    private Long id;
    private String name;
    private String description;
    private Integer year;
    private String courseName;
    private String roomName;

}
