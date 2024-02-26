package com.estech.EstechAppBackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "mentorings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Mentoring {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date;

    //    CONEXION CON ROOM
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    //    CONEXION CON STATUS
    @ManyToOne(fetch = FetchType.LAZY)
    private Status status;

    //    CONEXION CON USER (TEACHER)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private UserEntity teacher;

    //    CONEXION CON USER (STUDENT)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private UserEntity student;

}
