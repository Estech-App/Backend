package com.estech.EstechAppBackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String name;
    private String acronym;
    private String description;

    //  CONEXION CON GRUPOS
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
    private List<Group> groups;

    //  CONEXION CON MODULOS
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
    private List<Module> modules;

}
