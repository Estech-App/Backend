package com.estech.EstechAppBackend.model;

import com.estech.EstechAppBackend.model.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "status")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    //    CONEXION CON FREE USAGES
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
    private List<FreeUsages> freeUsages;

    //    CONEXION CON MENTORINGS
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
    private List<Mentoring> mentorings;
}
