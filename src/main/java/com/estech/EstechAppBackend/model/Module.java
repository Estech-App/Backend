package com.estech.EstechAppBackend.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "modules")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer year;
    @Nullable
    private String acronym;

    //  CONEXION CON CURSOS
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    //  CONEXION CON USUARIOS
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "modules")
    private List<User> users;

    //    CONEXION CON TIMETABLE
    @OneToMany(mappedBy = "module")
    private List<TimeTable> timeTables;

}
