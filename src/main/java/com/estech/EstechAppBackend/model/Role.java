package com.estech.EstechAppBackend.model;

import com.estech.EstechAppBackend.model.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private RoleEnum rolName;
    private String description;

    //  CONEXION CON USUARIOS
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private List<UserEntity> users;

}
