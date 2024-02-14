package com.estech.EstechAppBackend.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "courses")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String acronym;
    @Nullable
    private String description;

    //  CONEXION CON GRUPOS
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
    private List<Group> groups;

    //  CONEXION CON MODULOS
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
    private List<Module> modules;

}
