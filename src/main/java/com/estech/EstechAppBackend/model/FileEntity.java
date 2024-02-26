package com.estech.EstechAppBackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "files")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String path;

    //  CONEXION CON CATEGORY
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    //  CONEXION CON GROUPS
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "files")
    private List<Group> groups;

}
