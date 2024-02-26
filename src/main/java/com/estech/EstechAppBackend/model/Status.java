package com.estech.EstechAppBackend.model;

import com.estech.EstechAppBackend.model.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "status")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private StatusEnum status;

    //    CONEXION CON FREE USAGES
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
    private List<FreeUsages> freeUsages;

    //    CONEXION CON MENTORINGS
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
    private List<Mentoring> mentorings;
}
