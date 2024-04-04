package com.estech.EstechAppBackend.model;

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
    private String name;
    @NotNull
    private Integer year;
    private String acronym;

    //  CONEXION CON CURSOS
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    //  CONEXION CON USUARIOS
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_modules",
            joinColumns = @JoinColumn(name = "module_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private List<UserEntity> users;

    //    CONEXION CON TIMETABLE
    @OneToMany(mappedBy = "module")
    private List<TimeTable> timeTables;

}
