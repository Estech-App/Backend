package com.estech.EstechAppBackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "rooms")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Boolean mentoringRoom;
    @NotNull
    private Boolean studyRoom;

    //    CONEXION CON FREE USAGES
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<FreeUsages> freeUsages;

    //    CONEXION CON MENTORING
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<Mentoring> mentorings;

    //    CONEXION CON STOCK
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<Stock> stocks;

    //    CONEXION CON GROUP
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<Group> groups;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<RoomTimeTable> roomTimeTables;
}
