package com.estech.EstechAppBackend.model;

import com.estech.EstechAppBackend.model.enums.CategoryEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private CategoryEnum name;
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<FileEntity> fileEntities;


}
