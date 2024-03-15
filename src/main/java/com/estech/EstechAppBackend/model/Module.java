package com.estech.EstechAppBackend.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "modules")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Integer year;
    private String acronym;

    //  CONEXION CON CURSOS
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    //  CONEXION CON USUARIOS
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "modules")
    private List<UserEntity> users;

    //    CONEXION CON TIMETABLE
    @OneToMany(mappedBy = "module")
    private List<TimeTable> timeTables;

}
