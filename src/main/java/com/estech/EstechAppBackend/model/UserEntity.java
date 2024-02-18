package com.estech.EstechAppBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String password;
    @Column(name = "is_active")
    private Boolean isActive;

    //  CONEXION CON ROLES
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    //  CONEXION CON GRUPOS
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_groups",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id")
    )
    private List<Group> groups;

    //  CONEXION CON MODULOS
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_modules",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "module_id", referencedColumnName = "id")
    )
    private List<Module> modules;

    //    CONEXION CON FREE USAGES
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<FreeUsages> freeUsages;

    //    CONEXION CON MENTORING (TEACHER)
    @OneToMany(mappedBy = "teacher")
    private List<Mentoring> teacherMentorings;

    //    CONEXION CON MENTORING (STUDENT)
    @OneToMany(mappedBy = "student")
    private List<Mentoring> studentMentorings;

    //    CONEXION CON MENTORING (STUDENT)
    @OneToMany(mappedBy = "user")
    private List<CheckIn> checkIns;

}
