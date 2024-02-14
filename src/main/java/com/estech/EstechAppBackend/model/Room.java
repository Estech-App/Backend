package com.estech.EstechAppBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "rooms")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Boolean mentoringRoom;
    private Boolean studyRoom;

    //    CONEXION CON FREE USAGES
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<FreeUsages> freeUsages;

    //    CONEXION CON MENTORING
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<Mentoring> mentorings;

    //    CONEXION CON MENTORING
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<Stock> stocks;
}
