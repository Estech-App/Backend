package com.estech.EstechAppBackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "free_usages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FreeUsages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date;

    //    CONEXION CON STATUS
    @ManyToOne(fetch = FetchType.LAZY)
    private Status status;

    //    CONEXION CON ROOM
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    //    CONEXION CON USER
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;


}
