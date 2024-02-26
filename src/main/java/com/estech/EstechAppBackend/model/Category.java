package com.estech.EstechAppBackend.model;

import com.estech.EstechAppBackend.model.enums.CategoryEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private CategoryEnum name;
    private String description;

    //  CONEXION CON FILES
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<FileEntity> files;


}
