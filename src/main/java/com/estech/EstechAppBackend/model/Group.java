package com.estech.EstechAppBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "groups")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer year;

//  CONEXION CON USUARIOS
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups")
    private List<User> users;

//  CONEXION CON FILES
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "groups_files",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "file_id", referencedColumnName = "id")
    )
    private List<FileEntity> files;

    //  CONEXION CON CURSOS
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

//    CONEXION CON TIMETABLE
    @OneToMany(mappedBy = "group")
    private List<TimeTable> timeTables;
}
